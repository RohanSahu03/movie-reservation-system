package com.movie.auth_service.service.impl;

import com.movie.auth_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void sendVerificationEmail(String to,
                                      String firstName,
                                      String verificationToken) {

        String verificationUrl =
                frontendUrl + "/verify-email?token=" + verificationToken;

        String subject = "Verify your email address";

        String body = buildVerificationEmail(firstName, verificationUrl);

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

            log.info("Verification email sent to {}", to);

        } catch (MessagingException | MailException ex) {

            log.error("Failed to send verification email to {}", to, ex);

            throw new RuntimeException("Unable to send verification email.");
        }
    }

    private String buildVerificationEmail(String firstName,
                                          String verificationUrl) {

        return """
                <html>
                <body style="font-family: Arial, sans-serif;">
                
                    <h2>Welcome to Movie Reservation System 🎬</h2>
                
                    <p>Hello <b>%s</b>,</p>
                
                    <p>
                        Thank you for registering.
                        Please click the button below to verify your email.
                    </p>
                
                    <a href="%s"
                       style="
                           background:#1976d2;
                           color:white;
                           padding:12px 20px;
                           text-decoration:none;
                           border-radius:6px;">
                           Verify Email
                    </a>
                
                    <p>
                        If you didn't create this account,
                        please ignore this email.
                    </p>
                
                </body>
                </html>
                """.formatted(firstName, verificationUrl);
    }

    private String buildPasswordResetEmail(
            String firstName,
            String resetUrl) {

        return """
            <html>
            <body style="font-family: Arial, sans-serif;">

                <h2>Password Reset Request</h2>

                <p>Hello <b>%s</b>,</p>

                <p>
                    We received a request to reset your password.
                    Click the button below to create a new password.
                </p>

                <a href="%s"
                   style="
                       background:#d32f2f;
                       color:white;
                       padding:12px 20px;
                       text-decoration:none;
                       border-radius:6px;">
                       Reset Password
                </a>

                <p>
                    This link will expire in 15 minutes.
                </p>

                <p>
                    If you didn't request a password reset,
                    you can safely ignore this email.
                </p>

            </body>
            </html>
            """.formatted(firstName, resetUrl);
    }

    @Override
    public void sendPasswordResetEmail(
            String to,
            String firstName,
            String resetToken) {

        String resetUrl =
                frontendUrl + "/reset-password?token=" + resetToken;

        String subject = "Reset your password";

        String body = buildPasswordResetEmail(firstName, resetUrl);

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

            log.info("Password reset email sent to {}", to);

        } catch (MessagingException | MailException ex) {

            log.error("Failed to send password reset email to {}", to, ex);

            throw new RuntimeException("Unable to send password reset email.");
        }
    }
}