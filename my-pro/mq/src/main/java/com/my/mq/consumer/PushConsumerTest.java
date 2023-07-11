package com.my.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class PushConsumerTest {

    public static void main(String[] args){
        String mqInfo = args[0];
        String[] arrs = mqInfo.split(",");
        String nameserv = arrs[0];
        String topic = arrs[1];
        String tag = arrs[2];

        String group = args[1];
        int timeout = Integer.parseInt(args[2]);

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(nameserv);
        consumer.setInstanceName("ConsumerTest");
        consumer.setConsumeTimeout(timeout);
        try {
            consumer.subscribe(topic, tag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context)->{
                for(MessageExt messageExt : list){
                    System.out.println("message="+new String(messageExt.getBody()));
                    System.out.println("messageExt="+messageExt);
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            consumer.start();

            System.out.println("begin to consumer...");
        } catch (MQClientException e) {
            e.printStackTrace();
            consumer.shutdown();
        }
    }

}
