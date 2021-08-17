package com.lagou.rabbitmq.route;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Product {

    public final static String[] LEVEl = {
            "ERROR",
            "FATAL",
            "WARN"
    };

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个交换器 提前声明比较好一点
        channel.exchangeDeclare("routing.rt", BuiltinExchangeType.DIRECT,true,false,null);
        for (int i=1;i<=100;i++){
            String level=LEVEl[new Random().nextInt(100)%LEVEl.length];
            channel.basicPublish("routing.rt",level,null,("这是"+level+"的消息").getBytes("utf-8"));
        }
    }
}
