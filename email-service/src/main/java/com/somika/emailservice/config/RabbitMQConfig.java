package com.somika.emailservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.email.registration}")
    private String emailRegistrationQueue;

    @Value("${rabbitmq.queues.email.verification}")
    private String emailVerificationQueue;

    @Value("${rabbitmq.queues.email.forgot-password}")
    private String emailForgotPasswordQueue;

    @Value("${rabbitmq.routing-keys.internal-email-registration}")
    private String internalEmailRegistrationRoutingKey;

    @Value("${rabbitmq.routing-keys.internal-email-verification}")
    private String internalEmailVerificationRoutingKey;

    @Value("${rabbitmq.routing-keys.internal-email-forgot-password}")
    private String internalEmailForgotPasswordRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue emailRegistrationQueue() {
        return new Queue(emailRegistrationQueue);
    }


    @Bean
    public Queue emailVerificationQueue() {
        return new Queue(emailVerificationQueue);
    }

    @Bean
    public Queue emailForgotPasswordQueue() {
        return new Queue(emailForgotPasswordQueue);
    }


    @Bean
    public Binding internalToEmailRegistrationBinding() {
        return BindingBuilder
                .bind(emailRegistrationQueue())
                .to(internalTopicExchange())
                .with(internalEmailRegistrationRoutingKey);
    }

    @Bean
    public Binding internalToEmailVerificationBinding() {
        return BindingBuilder
                .bind(emailVerificationQueue())
                .to(internalTopicExchange())
                .with(internalEmailVerificationRoutingKey);
    }

    @Bean
    public Binding internalToEmailForgotPasswordBinding() {
        return BindingBuilder
                .bind(emailForgotPasswordQueue())
                .to(internalTopicExchange())
                .with(internalEmailForgotPasswordRoutingKey);
    }

    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter());
        return factory;
    }
}
