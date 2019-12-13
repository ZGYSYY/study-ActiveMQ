package com.zgy.study.example01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author ZGY
 * @date 2019/12/12 18:20
 * @description ActiveMQProvider
 */
@Component
public class ActiveMQProvider {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void senMessage(String message) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
