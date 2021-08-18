package com.lagou.myself.springboot_rabbit_listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @RabbitListener(queues = "queue.boot")
    public void getMyMessage(@Payload String message, @Header(name = "hello") String value) {
        System.out.println(message);
        System.out.println("header hello=" + value);
    }
}
