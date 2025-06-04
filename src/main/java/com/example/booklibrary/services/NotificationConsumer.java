package com.example.booklibrary.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.example.booklibrary.config.RabbitConfig;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String message) {
        System.out.println("📩 Отримано повідомлення: " + message);
    }
}
