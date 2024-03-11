package com.somika.emailservice.service;

import com.somika.emailservice.dto.request.ForgotPasswordRequestDto;
import com.somika.emailservice.dto.request.RegistrationEmailRequestDto;
import com.somika.emailservice.dto.request.ReservationEmailRequestDto;
import com.somika.emailservice.dto.request.VerificationEmailRequestDto;

public interface EmailService {

    void sendRegistrationEmail(RegistrationEmailRequestDto registrationEmailRequest);

    void sendVerificationEmail(VerificationEmailRequestDto verificationEmailRequest);

    void sendForgotPasswordEmail(ForgotPasswordRequestDto forgotPasswordRequest);

    void sendReservationEmail(ReservationEmailRequestDto reservationEmailRequest);
}
