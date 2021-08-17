package com.lagou.rabbitmq.pubsub;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class OneConsumer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("生成临时队列的名字:"+queueName);
        channel.exchangeDeclare("exchange.ec",
                BuiltinExchangeType.FANOUT,
                true,
                false,
                null);
        channel.queueBind(queueName,"exchange.ec","");
        channel.basicConsume(queueName,(consumerTag,  message)->{
            System.out.println("one"+new String(message.getBody(),"utf-8"));
        }, consumerTag->{

        });
    }
}
