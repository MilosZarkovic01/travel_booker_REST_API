package com.somika.emailservice.rabbitmq;

import com.somika.emailservice.dto.request.ForgotPasswordRequestDto;
import com.somika.emailservice.dto.request.RegistrationEmailRequestDto;
import com.somika.emailservice.dto.request.ReservationEmailRequestDto;
import com.somika.emailservice.dto.request.VerificationEmailRequestDto;
import com.somika.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.email.registration}")
    public void registrationConsumer(RegistrationEmailRequestDto registrationRequest) {
        log.info("Consumed {} from queue", registrationRequest);
        emailService.sendRegistrationEmail(registrationRequest);
    }

    @RabbitListener(queues = "${rabbitmq.queues.email.verification}")
    public void verificationConsumer(VerificationEmailRequestDto verificationRequest) {
        log.info("Consumed {} from queue", verificationRequest);
        emailService.sendVerificationEmail(verificationRequest);
    }

    @RabbitListener(queues = "${rabbitmq.queues.email.forgot-password}")
    public void forgotPasswordConsumer(ForgotPasswordRequestDto forgotPasswordRequest) {
        log.info("Consumed {} from queue", forgotPasswordRequest);
        emailService.sendForgotPasswordEmail(forgotPasswordRequest);
    }

    @RabbitListener(queues = "${rabbitmq.queues.email.reservation}")
    public void reservationConsumer(ReservationEmailRequestDto reservationEmailRequest) {
        log.info("Consumed {} from queue", reservationEmailRequest);
        emailService.sendReservationEmail(reservationEmailRequest);
    }

}
