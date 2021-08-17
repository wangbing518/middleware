package com.lagou.myself;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;

public class App {

    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessagePropertiesBuilder builder=MessagePropertiesBuilder.newInstance();
        builder.setContentEncoding("gbk");
        builder.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        Message message= MessageBuilder.withBody("你好 世界2".getBytes("gbk"))
                .andProperties(builder.build()).build();
        rabbitTemplate.send("ex.direct","routing.q1",message);
        context.close();
    }
}
