package com.lagou.rabbitmq.route;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class WarnConsumer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个交换器
        channel.exchangeDeclare("routing.rt", BuiltinExchangeType.DIRECT,true,false,null);
        channel.queueDeclare("warn.qe",true,false,false,null);
        channel.queueBind("warn.qe","routing.rt","WARN");
        channel.basicConsume("warn.qe",( consumerTag,  message)->{
            System.out.println("warn收到消息:"+new String(message.getBody(),"utf-8"));
        }, consumerTag->{

        });
    }
}
