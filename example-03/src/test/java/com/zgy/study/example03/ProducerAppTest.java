package com.zgy.study.example03;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author ZGY
 * @date 2019/12/13 14:14
 * @description ProducerAppTest
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProducerConfig.class})
public class ProducerAppTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerAppTest.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "queueDestination")
    private Destination queueDestination;

    @Test
    public void test() {
        jmsTemplate.send(queueDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("好好学学，天天向上！");
            }
        });
        LOGGER.info("发送成功！");
    }
}
