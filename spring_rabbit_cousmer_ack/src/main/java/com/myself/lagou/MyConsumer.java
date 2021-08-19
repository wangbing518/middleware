package com.myself.lagou;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者端确认机制
 */
public class MyConsumer {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("queue.ca", true, false, false, null);
        //autoACK:false 表示手动确认消息 DefaultConsumer 回调函数
        channel.basicConsume("queue.ca",false,"myConsumer",new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               System.out.println(new String(body));
               //确认消息
              // channel.basicAck(envelope.getDeliveryTag(),false);
               //第一个参数表示消息的标签，第二个参数表示不确认是多个消息还是一个消息
                //第三个消息表示 不确认的消息是否要重新入列然后重发
                //用于拒收多条消息
               //channel.basicNack(envelope.getDeliveryTag(),false,true);
               //用于拒收一条消息
                //第二个参数表示 是否重新入列然后重发
               channel.basicReject(envelope.getDeliveryTag(),true);
            }
        });

   /*     channel.close();
        connection.close();*/
    }
}
