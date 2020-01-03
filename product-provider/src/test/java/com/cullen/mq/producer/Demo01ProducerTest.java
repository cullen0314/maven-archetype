package com.cullen.mq.producer;

import javafx.application.Application;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo01ProducerTest {

    @Autowired
    private Demo01Producer producer;

    @Test
    public void syncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        //log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
        System.out.println("[testSyncSend][发送编号：[{}] 发送结果：[{}]]"+id+result);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void asyncSend() {
    }

    @Test
    public void onewaySend() {
    }
}