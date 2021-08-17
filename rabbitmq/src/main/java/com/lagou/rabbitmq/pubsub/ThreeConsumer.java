package com.lagou.rabbitmq.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class ThreeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("生成临时队列的名字:" + queueName);
        channel.exchangeDeclare("exchange.ec", BuiltinExchangeType.FANOUT,
                true, false,
                null);
        channel.queueBind(queueName, "exchange.ec", "");
        channel.basicConsume(queueName, (consumerTag, message) -> {
            System.out.println("two：" + new String(message.getBody(), "utf-8"));
        }, consumerTag -> {

        });
    }
}
