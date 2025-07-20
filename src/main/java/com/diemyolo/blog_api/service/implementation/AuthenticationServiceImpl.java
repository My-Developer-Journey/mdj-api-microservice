package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Enumberable.Provider;
import com.diemyolo.blog_api.entity.Enumberable.Status;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.repository.UserRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.JWTService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String PHONE_NUMBER_REGEX = "^\\d{10}$";
    private static final String EMAIL_REGEX = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
    private static final String PASSWORD_REGEX = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?/~-])(?=.{8,}).*$";

    private final UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserResponse signUp(SignUpRequest request) {
        try {
            String randomCode = RandomString.make(64);

            Map<String, String> errors = validateSignUpRequest(request);
            if (!errors.isEmpty()) {
                throw new CustomException("Validation failed", errors, HttpStatus.BAD_REQUEST);
            }

            User user = modelMapper.map(request, User.class);

            user.setProvider(Provider.MDJ);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setStatus(Status.ACTIVE);
            redisTemplate.opsForValue().set(
                    "verify:" + user.getEmail(),
                    randomCode,
                    Duration.ofMinutes(10)
            );

            mailServiceImpl.sendVerificationEmail(user.getEmail(), randomCode);

            return modelMapper.map(userRepository.save(user), UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public String signIn(SignInRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new CustomException("Invalid email or password.", HttpStatus.UNAUTHORIZED);
            }

            if (!user.isEnabled()) {
                String email = user.getEmail();
                String verifyAttemptKey = "verify_attempts:" + email;

                Integer attempts = Optional.ofNullable(redisTemplate.opsForValue().get(verifyAttemptKey))
                        .map(Integer::valueOf)
                        .orElse(0);

                if (attempts >= 5) {
                    throw new CustomException(
                            "You have exceeded the number of verification attempts. Please contact support.",
                            HttpStatus.TOO_MANY_REQUESTS);
                }

                String randomCode = RandomString.make(64);
                redisTemplate.opsForValue().set("verify:" + email, randomCode, Duration.ofMinutes(10));

                mailServiceImpl.sendVerificationEmail(email, randomCode);
                redisTemplate.opsForValue().increment(verifyAttemptKey);
                redisTemplate.expire(verifyAttemptKey, Duration.ofHours(24));

                throw new CustomException(
                        "Your account is not verified. A new verification email has been sent.",
                        HttpStatus.BAD_REQUEST);
            }

            if (user.getStatus() == Status.INACTIVE) {
                throw new CustomException(
                        "This account is blocked. Please contact the administrator.",
                        HttpStatus.BAD_REQUEST);
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            return jwtService.generateToken(user);

        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid email or password.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    private Map<String, String> validateSignUpRequest(SignUpRequest request) {
        Map<String, String> errors = new HashMap<>();

        Pattern phonePattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

        Matcher phoneMatcher = phonePattern.matcher(request.getPhoneNumber());
        Matcher emailMatcher = emailPattern.matcher(request.getEmail());
        Matcher passwordMatcher = passwordPattern.matcher(request.getPassword());

        // check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            errors.put("email", "Email is already in use.");
        }

        // check confirmed password
        if (!request.getPassword().equals(request.getConfirmedPassword())) {
            errors.put("confirmedPassword", "Confirmed password does not match.");
        }

        // check phone number format: 10 digits
        if (!phoneMatcher.matches()) {
            errors.put("phoneNumber", "Invalid phone number.");
        }

        // check email format
        if (!emailMatcher.matches()) {
            errors.put("email", "Invalid email.");
        }

        // check password format
        if (!passwordMatcher.matches()) {
            errors.put("password", "Password must be at least 8 characters and have a special character.");
        }

        return errors;
    }

    @Override
    public User findUserByJwt() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null
                    && authentication.getPrincipal() != null
                    && authentication.getPrincipal() instanceof User) {
                UserDetails userDetails = (User) authentication.getPrincipal();
                String email = userDetails.getUsername();
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new CustomException("User not found!", HttpStatus.NOT_FOUND));
                ;
                return user;
            } else {
                throw new CustomException("Cannot receive JWT token!", HttpStatus.BAD_REQUEST);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Cannot get user info from JWT token!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void verifyEmail(String email, String code) {
        try{
            String redisKey = "verify:" + email;
            String savedCode = redisTemplate.opsForValue().get(redisKey);

            if (savedCode == null) {
                throw new CustomException("Verification code not existed or expired!", HttpStatus.BAD_REQUEST);
            }

            if (!savedCode.equals(code)) {
                throw new CustomException("Verification code not valid!", HttpStatus.BAD_REQUEST);
            }

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException("User not found!", HttpStatus.NOT_FOUND));

            user.setEnabled(true);
            userRepository.save(user);

            redisTemplate.delete(redisKey);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Cannot get user info from JWT token!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
