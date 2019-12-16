package com.zgy.study.example04;

import com.zgy.study.example04.config.ActiveMqConfig;
import com.zgy.study.example04.service.ConsumerService;
import com.zgy.study.example04.service.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: ZGY
 * @date: 2019-12-14 18:17
 * @description: AppTest
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ActiveMqConfig.class})
public class AppTest {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ConsumerService consumerService;

    /**
     * 发送消息到默认目标测试
     */
    @Test
    public void test() {
        producerService.sendMessage("好好学习，天天向上！");
    }

    /**
     * 发送消息到指定目标测试
     */
    @Test
    public void test2() {
        ActiveMQQueue queue = new ActiveMQQueue("test-queue2");
        producerService.sendMessage(queue, "老子明天不上班！");
    }

    /**
     * 接收指定目标的消息测试
     */
    @Test
    public void test3() {
        consumerService.receive(new ActiveMQQueue("test-queue2"));
    }

    /**
     * 向指定目标广播消息测试
     */
    @Test
    public void test4() {
        producerService.publish("同志们辛苦了！");
    }
}
