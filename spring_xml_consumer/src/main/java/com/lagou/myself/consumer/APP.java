package com.lagou.myself.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;

public class APP {

    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        //这个使用的是拉去的方式
        Message message = rabbitTemplate.receive("queue.q1");
        System.out.println(new String(message.getBody(),message.getMessageProperties().getContentEncoding()));
        context.close();
    }
}
