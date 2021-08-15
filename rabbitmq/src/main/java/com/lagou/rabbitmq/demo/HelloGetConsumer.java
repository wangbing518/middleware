package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
//消息拉的方式
public class HelloGetConsumer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //指定的协议 amqp
        //指定的用户名 guest
        //指定的密码 guest
        //指定的host nas.scz.pub
        //指定的端口号 5672
        //指定虚拟主机 %2f
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        //创建一个TCP连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //指定从哪个消费者去消费
        //指定是否自动确认消息 true表示自动确认
        GetResponse getResponse = channel.basicGet("queue.biz", true);
        byte[] body = getResponse.getBody();
        System.out.println(new String(body));
        channel.close();
        connection.close();
    }
}
