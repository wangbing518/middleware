package com.lagou.myself.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = "queue.anono")
    public void myMessageCom(@Payload String message){
        System.out.println(message);
    }
}
