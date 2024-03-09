package com.somika.emailservice.service.impl;

import com.somika.emailservice.dto.request.ForgotPasswordRequestDto;
import com.somika.emailservice.dto.request.RegistrationEmailRequestDto;
import com.somika.emailservice.dto.request.VerificationEmailRequestDto;
import com.somika.emailservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

import static java.nio.file.Files.readString;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${email.file-path.registration}")
    private String registrationFilePath;

    @Value("${templates.name.verification}")
    private String verificationTemplateName;

    @Value("${templates.name.forgot-password}")
    private String forgotPasswordTemplateName;

    @Override
    public void sendRegistrationEmail(RegistrationEmailRequestDto registrationEmailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlContent = readString(ResourceUtils.getFile(registrationFilePath).toPath());

            helper.setText(htmlContent, true);
            helper.setTo(registrationEmailRequest.to());
            helper.setSubject("Successful registration");

            javaMailSender.send(mimeMessage);
            log.info("Registration email is sent to {}", registrationEmailRequest);
        } catch (MessagingException ex) {
            log.error("Failed to send email", ex);
            throw new IllegalArgumentException("Failed to send email");
        } catch (IOException e) {
            log.error("Bad file path");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendVerificationEmail(VerificationEmailRequestDto verificationEmailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            Context context = new Context();
            context.setVariable("link", verificationEmailRequest.link());

            String htmlContent = templateEngine.process(verificationTemplateName, context);

            helper.setText(htmlContent, true);
            helper.setTo(verificationEmailRequest.to());
            helper.setSubject("Email verification");

            javaMailSender.send(mimeMessage);
            log.info("Verification email is sent to {}", verificationEmailRequest.to());
        } catch (MessagingException ex) {
            log.error("Failed to send email", ex);
            throw new IllegalArgumentException("Failed to send email");
        }
    }

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordRequestDto forgotPasswordRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            Context context = new Context();
            context.setVariable("resetCode", forgotPasswordRequest.id());

            String htmlContent = templateEngine.process(forgotPasswordTemplateName, context);

            helper.setText(htmlContent, true);
            helper.setTo(forgotPasswordRequest.email());
            helper.setSubject("Forgot password");

            javaMailSender.send(mimeMessage);
            log.info("Forgot password email is sent to {}", forgotPasswordRequest.email());
        } catch (MessagingException ex) {
            log.error("Failed to send email", ex);
            throw new IllegalArgumentException("Failed to send email");
        }
    }
}
