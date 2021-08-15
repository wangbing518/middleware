package com.lagou.rabbitmq.demo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class HelloWorldSender {

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置主机名 hostmane
        factory.setHost("nas.scz.pub");
        //设置虚拟机主机名称 /在url中的转义字符为%2f
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        //建立TCP的连接
        Connection connection = factory.newConnection();
       //获取通道
        Channel channel = connection.createChannel();
        //声明消息队列 消息名称
        //是否持久化
        //是否是排他的
        //是否是自动删除的
        //消息队列的属性信息
        channel.queueDeclare("queue.biz",false,false,true,null);
        //声明一个交换器 交换器名称
        //交换器的类型
        //交换器是否持久化
        //交换器是否自动删除
        //交换器的属性map
        channel.exchangeDeclare("ex.biz", BuiltinExchangeType.DIRECT,false,false,null);
        //将交换器和消息队列就行绑定 并指定其路由键
        channel.queueBind("queue.biz","ex.biz","hello.word");
        //发送消息
        //交换器的名字
        //该消息的路由键
        //该消息属性BasicProperties的对象
        //消息的字节数字
        channel.basicPublish("ex.biz","hello.word",null,"bei jing2".getBytes());
        //关闭通道
        channel.close();
        //关闭连接
        connection.close();
    }
}
