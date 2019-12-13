package com.zgy.study.example03;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @author ZGY
 * @date 2019/12/13 13:21
 * @description ServerProvider，服务提供者。
 */
public class ServerProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerProvider.class);

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.166:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue");

            JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.send(queue, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage("Hello ActiveMQ");
                }
            });
            LOGGER.info("发送成功");

            session.close();
        } catch (JMSException e) {
            LOGGER.error("创建连接异常", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOGGER.error("关闭连接异常", e);
                }
            }
        }
    }
}
