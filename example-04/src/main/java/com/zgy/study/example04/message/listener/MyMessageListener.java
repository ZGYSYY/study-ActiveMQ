package com.zgy.study.example04.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author: ZGY
 * @date: 2019-12-14 18:38
 * @description: MyMessageListener，自定义消息监听器
 * @version: 1.0
 */
public class MyMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMessageListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            String msg = ((TextMessage) message).getText();
            LOGGER.info("自定义消息监听器从默认目标获取的消息为：{}", msg);
        } catch (JMSException e) {
            LOGGER.error("获取消息发送异常！", e);
        }
    }
}
