package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Enumberable.Status;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.authentication.AuthenticationResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.repository.UserRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.JWTService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ModelMapper modelMapper;

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

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setStatus(Status.ACTIVE);
            user.setVerificationCode(randomCode);

            var response = modelMapper.map(userRepository.save(user), UserResponse.class);

            return response;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            // Check if the user account is enabled
            if (!user.isEnabled()) {
                throw new DisabledException("This account is not activated. Please verify your email.");
            }

            if (user.getStatus() != Status.INACTIVE) {
                var token = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(token)
                        .build();
            } else {
                throw new CustomException(
                        "This account is blocked. Please contact the administrator for more information.",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid email or password.", HttpStatus.UNAUTHORIZED);
        } catch (DisabledException e) {
            // Send verification email if the account is disabled
            // userService.sendVerificationEmail(user);
            throw new CustomException(
                    "This account is not activated. Please verify your email.",
                    HttpStatus.BAD_REQUEST);
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
}
