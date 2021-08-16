package com.lagou.rabbitmq.queue;

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
 * @ClassName Producer
 * @Description
 * @Author wb
 * @Date 2021/8/16 0016 下午 6:26
 */
public class Producer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个消息队列
        channel.queueDeclare("queue.exe", true, false, false, null);
        //声明一个交换器
        channel.exchangeDeclare("exchange.bz", BuiltinExchangeType.DIRECT, true, false, null);
        //将消息队列绑定到指定的交换器 并指定路由键
        channel.queueBind("queue.exe", "exchange.bz", "luyou.ly");
        //发送消息
        for (int i = 1; i <= 15; i++) {
            channel.basicPublish("exchange.bz", "luyou.ly", null, ("消息队列发送:" + i).getBytes("utf-8"));
        }
        channel.close();
        connection.close();
    }

}
