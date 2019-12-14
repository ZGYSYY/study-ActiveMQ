package com.zgy.study.example04.service.impl;

import com.zgy.study.example04.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author: ZGY
 * @date: 2019-12-14 18:05
 * @description: ProducerServiceImpl
 * @version: 1.0
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicJmsTemplate")
    private JmsTemplate topicJmsTemplate;

   @Override
    public void sendMessage(Destination destination, String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
        LOGGER.info("向指定的目标发送消息, message: [{}]", message);
    }

    @Override
    public void sendMessage(String message) {
        Destination defaultDestination = jmsTemplate.getDefaultDestination();
        LOGGER.info("defaultDestination: [{}]", defaultDestination);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
        LOGGER.info("向默认的目标发送消息, message: [{}]", message);
    }

    @Override
    public void publish(String message) {
        topicJmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
        LOGGER.info("向默认目标广播消息成功，广播消息为：{}", message);
    }
}
