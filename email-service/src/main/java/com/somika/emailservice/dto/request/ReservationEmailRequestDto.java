package com.somika.emailservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationEmailRequestDto(
        String email,
        LocalDate from,
        LocalDate to,
        String destination,
        BigDecimal totalAmount
) {
}

