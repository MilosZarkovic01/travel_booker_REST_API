package com.somika.travelbooker.listeners.event;

import com.somika.travelbooker.config.RabbitMQConfig;
import com.somika.travelbooker.dto.request.ReservationEmailRequestDto;
import com.somika.travelbooker.dto.request.VerificationEmailRequestDto;
import com.somika.travelbooker.dto.response.ForgotPasswordResponseDto;
import com.somika.travelbooker.rabbitmq.RabbitMQMessageProducer;
import com.somika.travelbooker.repository.entity.AccountEntity;
import com.somika.travelbooker.repository.entity.PasswordResetTokenEntity;
import com.somika.travelbooker.repository.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InsertEventListenerImpl implements PostInsertEventListener {

    private final RabbitMQMessageProducer producer;
    private final RabbitMQConfig rabbitMQConfig;

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        if (postInsertEvent.getEntity() instanceof AccountEntity savedAccount) {
            Long id = savedAccount.getAccountId();
            producer.publish(
                    new VerificationEmailRequestDto(savedAccount.getEmail(),
                            rabbitMQConfig.getVerificationEmailPath() + id),
                    rabbitMQConfig.getExchange(),
                    rabbitMQConfig.getVerificationRoutingKey());
            log.info("Verification email is sent");
        } else if (postInsertEvent.getEntity() instanceof PasswordResetTokenEntity passwordResetToken) {
            producer.publish(
                    new ForgotPasswordResponseDto(passwordResetToken.getAccount().getEmail(), passwordResetToken.getId()),
                    rabbitMQConfig.getExchange(),
                    rabbitMQConfig.getForgotPasswordRoutingKey()
            );
            log.info("Forgot password email is sent");
        } else if (postInsertEvent.getEntity() instanceof ReservationEntity reservationEntity) {
            producer.publish(
                    new ReservationEmailRequestDto(
                            reservationEntity.getAccount().getEmail(),
                            reservationEntity.getTravelPlan().getArrivalDate(),
                            reservationEntity.getTravelPlan().getDepartureDate(),
                            reservationEntity.getAccommodation().getDestination().getCountry() + " " + reservationEntity.getAccommodation().getDestination().getCity(),
                            reservationEntity.getTotalCost()
                    ),
                    rabbitMQConfig.getExchange(),
                    rabbitMQConfig.getReservationRoutingKey()
            );
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
