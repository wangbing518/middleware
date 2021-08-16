package com.lagou.rabbitmq.queue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName consumer
 * @Description
 * @Author wb
 * @Date 2021/8/16 0016 下午 6:13
 */
public class ConsumerMq {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("queue.exe", true, false, false, null);
        channel.basicConsume("queue.exe", (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), "utf-8"));
        }, (consumerTag) -> {
            System.out.println("consumerTag=" + consumerTag);
        });
    }
}
