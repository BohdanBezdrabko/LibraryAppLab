package com.example.booklibrary.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "book_notifications";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }
}
