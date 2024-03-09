package com.somika.travelbooker.listeners.event;

import com.somika.travelbooker.config.RabbitMQConfig;
import com.somika.travelbooker.dto.request.RegistrationEmailRequestDto;
import com.somika.travelbooker.rabbitmq.RabbitMQMessageProducer;
import com.somika.travelbooker.repository.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateStatusEventListenerImpl implements PostUpdateEventListener {

    private final RabbitMQMessageProducer producer;
    private final RabbitMQConfig rabbitMQConfig;

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        AccountEntity account = (AccountEntity) postUpdateEvent.getEntity();

        int activeColumnIndex = postUpdateEvent.getPersister().getPropertyIndex("active");

        if (activeColumnIndex != -1) {
            Object currentState = postUpdateEvent.getState()[activeColumnIndex];
            Object previousState = postUpdateEvent.getOldState()[activeColumnIndex];

            if (!currentState.equals(previousState)) {
                producer.publish(
                        new RegistrationEmailRequestDto(account.getEmail()),
                        rabbitMQConfig.getExchange(),
                        rabbitMQConfig.getRegistrationRoutingKey());
                log.info("Registration email is sent");
            }
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
