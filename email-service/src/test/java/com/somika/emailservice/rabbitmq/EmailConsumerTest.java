package com.somika.emailservice.rabbitmq;


import com.somika.emailservice.config.RabbitMQConfig;
import com.somika.emailservice.dto.request.ForgotPasswordRequestDto;
import com.somika.emailservice.dto.request.RegistrationEmailRequestDto;
import com.somika.emailservice.dto.request.VerificationEmailRequestDto;
import com.somika.emailservice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@ExtendWith(MockitoExtension.class)
public class EmailConsumerTest {

    @Container
    static RabbitMQContainer rabbitContainer = new RabbitMQContainer("rabbitmq:3.7.25-management-alpine");

    @Autowired
    RabbitMQConfig rabbitMQConfig;
    @Autowired
    AmqpTemplate amqpTemplate;

    @Mock
    EmailService emailService;


    @Test
    void testRegistrationConsumer() {
        doNothing().when(emailService).sendRegistrationEmail(any());
        RegistrationEmailRequestDto registrationRequest = new RegistrationEmailRequestDto("test@example.com");

        amqpTemplate.convertAndSend(rabbitMQConfig.getEmailRegistrationQueue(), createMessage(registrationRequest));

        EmailConsumer emailConsumer = new EmailConsumer(emailService);

        emailConsumer.registrationConsumer(registrationRequest);

        verify(emailService, times(1)).sendRegistrationEmail(registrationRequest);
    }


    @Test
    void testVerificationConsumer() {
        doNothing().when(emailService).sendVerificationEmail(any());
        VerificationEmailRequestDto verificationRequest = new VerificationEmailRequestDto("test@example.com", "http://localhost:8080/accounts/verify-email");

        amqpTemplate.convertAndSend(rabbitMQConfig.getEmailVerificationQueue(), createMessage(verificationRequest));

        EmailConsumer emailConsumer = new EmailConsumer(emailService);

        emailConsumer.verificationConsumer(verificationRequest);

        verify(emailService, times(1)).sendVerificationEmail(verificationRequest);
    }

    @Test
    void testForgotPasswordConsumer() {
        doNothing().when(emailService).sendForgotPasswordEmail(any());
        ForgotPasswordRequestDto forgotPasswordRequest = new ForgotPasswordRequestDto("test@example.com", UUID.randomUUID());

        amqpTemplate.convertAndSend(rabbitMQConfig.getEmailVerificationQueue(), createMessage(forgotPasswordRequest));

        EmailConsumer emailConsumer = new EmailConsumer(emailService);

        emailConsumer.forgotPasswordConsumer(forgotPasswordRequest);

        verify(emailService, times(1)).sendForgotPasswordEmail(forgotPasswordRequest);
    }

    private Message createMessage(Object payload) {
        return MessageBuilder.withBody(payload.toString().getBytes()).build();
    }
}
