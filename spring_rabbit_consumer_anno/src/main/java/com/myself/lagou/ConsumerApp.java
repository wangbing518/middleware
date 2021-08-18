package com.myself.lagou;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName ConsumerApp
 * @Description
 * @Author wb
 * @Date 2021/8/18 0018 下午 5:53
 */
public class ConsumerApp {

    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Message message = rabbitTemplate.receive("queue.anono");
        System.out.println(new String(message.getBody(),message.getMessageProperties().getContentEncoding()));
        context.close();
    }
}
