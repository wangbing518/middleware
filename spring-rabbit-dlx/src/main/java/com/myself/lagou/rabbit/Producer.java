package com.myself.lagou.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description
 * @Author wb
 * @Date 2021/9/3 0003 下午 6:09
 */
public class Producer {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        try (final Connection connection=factory.newConnection();
                Channel channel=connection.createChannel()){
            //声明一个正常的交换器
            channel.exchangeDeclare("ex.biz", BuiltinExchangeType.DIRECT,true);
            //TODO 还未写完
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
