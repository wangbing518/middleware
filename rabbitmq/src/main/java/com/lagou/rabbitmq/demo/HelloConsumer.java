package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

//消息推的模式
public class HelloConsumer {

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
        final Connection connection = factory.newConnection();
        //创建一个通道
        final Channel channel = connection.createChannel();
       //确保MQ有该队列 没有则重新创建
        channel.queueDeclare("queue.biz",false,false,true,null);

        //监听消息,一旦有消息推送过来,就调用第一个lambael表达式  (consumerTag, message)(consumerTag)
       channel.basicConsume("queue.biz", (consumerTag, message)->{
           System.out.println(new String(message.getBody()));
       },(consumerTag)->{});
//       channel.close();
//       connection.close();
    }
}
