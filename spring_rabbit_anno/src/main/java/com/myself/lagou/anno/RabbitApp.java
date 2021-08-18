package com.myself.lagou.anno;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName RabbitApp
 * @Description
 * @Author wb
 * @Date 2021/8/18 0018 下午 5:37
 */
public class RabbitApp {

    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
        RabbitTemplate template = context.getBean(RabbitTemplate.class);
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("utf-8")
                .setHeader("nihao", "shijie").build();
        for (int i=1;i<=1000;i++){
            Message message= MessageBuilder.withBody(("注解的中间件:"+i).getBytes("utf-8"))
                    .andProperties(messageProperties).build();
            template.send("ex.anno.f","key.anno",message);
        }
        context.close();
    }
}
