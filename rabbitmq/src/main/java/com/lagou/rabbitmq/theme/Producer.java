package com.lagou.rabbitmq.theme;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description
 * @Author wb
 * @Date 2021/8/17 0017 上午 11:34
 */
public class Producer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@nas.scz.pub:5672/%2f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个交换器
        channel.exchangeDeclare("theme.te", BuiltinExchangeType.TOPIC,true,false,null);
        String area,level,biz;
        String message,routingKey;
        Random random = new Random();
        for (int i=1;i<=100;i++){
            area = LogArea.getLogArea(random.nextInt(3)).getDesc();
            biz= LogBiz.getLogBiz(random.nextInt(3)).getDesc();
            level = LogLevel.getLogLevel(random.nextInt(3)).getDesc();
            routingKey=new StringBuilder().append(area).append(".").append(biz).append(".").append(level).toString();
            message=new StringBuilder("LOG: [").append(level).append("]:这是[").append(area).append("]地区[").append(biz).append("]服务器发送的消息:").append(i).toString();
            channel.basicPublish("theme.te",routingKey,null,message.getBytes("utf-8"));
        }
        channel.close();
        connection.close();
    }
}
