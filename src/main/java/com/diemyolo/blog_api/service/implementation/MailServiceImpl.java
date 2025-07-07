package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.domain.url}")
    private String domainUrl;

    public void sendVerificationEmail(String toEmail, String verificationCode) {
        String subject = "Verify Your Account";
        String verifyLink = domainUrl + "/api/authentications/verify?email=" + toEmail + "&code=" + verificationCode;

        String content = "<p>Hello,</p>"
                + "<p>Thank you for registering an account with us.</p>"
                + "<p>Please click the link below to verify your email address:</p>"
                + "<p><a href=\"" + verifyLink + "\">Verify your account</a></p>"
                + "<br><p>This link will expire in 10 minutes for security reasons.</p>"
                + "<p>If you did not sign up, you can safely ignore this email.</p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (CustomException e) {
            throw e;
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email.", e);
        }
    }
}
