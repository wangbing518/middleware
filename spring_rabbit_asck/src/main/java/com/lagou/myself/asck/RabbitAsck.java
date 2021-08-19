package com.lagou.myself.asck;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitAsck
 * @Description
 * @Author wb
 * @Date 2021/8/19 0019 下午 2:19
 */
public class RabbitAsck {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //向RabbitNQ服务器发送AMQP命令，将当前通道标记为发送方确认通道
        channel.confirmSelect();
        //声明一个队列
        channel.queueDeclare("queue.asck",true,false,false,null);
        //交换器
        channel.exchangeDeclare("exchange.asck", BuiltinExchangeType.DIRECT,true,false,null);
        //绑定
        channel.queueBind("queue.asck","exchange.asck","routing.asck");
        //发布消息
        channel.basicPublish("exchange.asck","routing.asck",null,"是否已经自动确认".getBytes("utf-8"));
        try {
            channel.waitForConfirmsOrDie(5000);
            System.out.println("发送的消息已经得到确认");
        } catch (IllegalStateException e) {
            System.out.println("发送的消息的通道不是PublishConfirms通道");
        }catch (IOException e){
            System.out.println("发送的消息被拒收");
        }catch (TimeoutException e){
            System.out.println("等待消息确认超时");
        } catch (InterruptedException e) {
            System.out.println("发送消息的通道不是PublishConfirms通道");
        }

    }
}
