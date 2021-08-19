package com.lagou.myself.asck;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/** 同步确认等待的机制
 * @ClassName RabbitBatchPlush1
 * @Description
 * @Author wb
 * @Date 2021/8/19 0019 下午 5:02
 */
public class RabbitBatchPlush1 {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI.create("amqp://guest:guest@nas.scz.pub:5672/%2f"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //向RabbitNQ服务器发送AMQP命令，将当前通道标记为发送方确认通道
         channel.confirmSelect();
         channel.queueDeclare("que.batch",true,false,false,null);
         channel.exchangeDeclare("exchagen.batch", BuiltinExchangeType.DIRECT,true,false,null);
         channel.queueBind("que.batch","exchagen.batch","key.batch");
         String message="hello-";
         int batchSize=10;
         int outStrandingConfirms=0;

         for (int i=1;i<=1005;i++){
             channel.basicPublish("exchagen.batch","key.batch",null,(message+i).getBytes("utf-8"));
             outStrandingConfirms++;
             if (outStrandingConfirms==batchSize){
                 channel.waitForConfirmsOrDie(5000);
                 System.out.println("消息被确认");
                 outStrandingConfirms=0;
             }
         }
         if (outStrandingConfirms>0){
             channel.waitForConfirmsOrDie(5000);
             System.out.println("剩余的消息被确认");
         }
        channel.close();
        connection.close();
    }
}
