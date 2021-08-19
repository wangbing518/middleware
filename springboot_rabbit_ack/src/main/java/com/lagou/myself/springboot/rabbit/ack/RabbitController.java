package com.lagou.myself.springboot.rabbit.ack;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class RabbitController {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback((correlationData, flag, cause) -> {
            if (flag) {
                try {
                    System.out.println("消息确认:" + correlationData.getId() + " " + new String(correlationData.getReturnedMessage().getBody(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(cause);
            }
        });
    }

    @GetMapping("/biz")
    public String doBiz() throws UnsupportedEncodingException {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("utf-8")
                .setCorrelationId("1234")
                .build();
        messageProperties.setConsumerTag("msg1");
        Message message = MessageBuilder.withBody("这是等待确认的消息".getBytes("utf-8")).andProperties(messageProperties).build();
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("msg1");
        correlationData.setReturnedMessage(new Message("这是msg1的响应".getBytes("utf-8"), null));
        rabbitTemplate.convertAndSend("exchange.ackc", "key.ackc", message, correlationData);
        return "ok";
    }


}
