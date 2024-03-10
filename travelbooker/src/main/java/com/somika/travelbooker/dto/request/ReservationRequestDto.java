package com.somika.travelbooker.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationRequestDto(
        Long accountId,
        Long accommodationId,
        Long travelPlanId
) {
}
