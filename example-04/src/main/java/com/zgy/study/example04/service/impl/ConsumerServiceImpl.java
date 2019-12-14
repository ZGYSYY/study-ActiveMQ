package com.zgy.study.example04.service.impl;

import com.zgy.study.example04.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author: ZGY
 * @date: 2019-12-14 18:12
 * @description: ConsumerServiceImpl
 * @version: 1.0
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicJmsTemplate")
    private JmsTemplate topicJmsTemplate;

    @Override
    public void receive(Destination destination) {
        TextMessage message = (TextMessage) jmsTemplate.receive(destination);
        try {
            LOGGER.info("从目标 destination: [{}]，收到的消息为{}", destination, message.getText());
        } catch (JMSException e) {
            LOGGER.error("从目标获取消息发生异常！", e);
        }
    }

    @Override
    public void topicReceive(Destination destination) {
        TextMessage message = (TextMessage) topicJmsTemplate.receive(destination);
        try {
            LOGGER.info("从目标 destination: [{}]，收到的消息为{}", destination, message.getText());
        } catch (JMSException e) {
            LOGGER.error("从目标获取消息发生异常！", e);
        }
    }
}
