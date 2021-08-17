package com.lagou.rabbitmq.theme;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName BeijingConsumer
 * @Description
 * @Author wb
 * @Date 2021/8/17 0017 下午 2:15
 */
public class ErrorConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个交换器
        channel.exchangeDeclare("theme.te", BuiltinExchangeType.TOPIC, true, false, null);
        //临时队列名称
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "theme.te", "#."+LogLevel.ERROR.getDesc(), null);
        channel.basicConsume(queueName, (consumerTag, message) -> {
            System.out.println(new String(message.getBody(),"utf-8"));
        }, consumerTag->{

        });
    }
}
