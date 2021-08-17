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

/**
 * 订阅发布模式
 */
public class Producter {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明fanout类型的交换器
        channel.exchangeDeclare("exchange.ec", BuiltinExchangeType.FANOUT,true,false,null);

        for (int i=1;i<=15;i++){
            channel.basicPublish("exchange.ec","",null,("hello word fanout"+i).getBytes());
        }
        channel.close();
        connection.close();
    }

}

