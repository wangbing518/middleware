package com.lagou.myself.listener;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

public class MyMessageListener implements ChannelAwareMessageListener {

    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
    }

}
