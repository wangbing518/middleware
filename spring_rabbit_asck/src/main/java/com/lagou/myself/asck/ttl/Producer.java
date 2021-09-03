package com.lagou.myself.asck.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.rabbitmq.client.BuiltinExchangeType.DIRECT;

/**
 * @ClassName Producer
 * @Description
 * @Author wb
 * @Date 2021/9/3 0003 下午 3:06
 */
public class Producer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        try{
            Map<String,Object> arguments=new HashMap<>();
            arguments.put("x-message-ttl",3000);//即设置当前队列消息的过期时间。ttl即为time to live
            arguments.put("x-expires",1000);//设置队列的空闲存活时间
            channel.queueDeclare("eq.ttl",true,false,false,arguments);
            channel.exchangeDeclare("ex.ttl",DIRECT,true,false,null);
            channel.queueBind("eq.ttl","ex.ttl","key.ttl");
            channel.basicPublish("ex.ttl","key.ttl",new AMQP.BasicProperties().builder().contentEncoding("utf-8").deliveryMode(2).build(),"等待的订单号".getBytes("utf-8"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.close();
            channel.close();
        }
    }
}
