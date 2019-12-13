package com.zgy.study.example02;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * @author ZGY
 * @date 2019/12/13 11:51
 * @description JmsReceiver，消费端。
 */
public class JmsReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.166:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("queue");
            // 创建消费之
            MessageConsumer consumer = session.createConsumer(destination);
            // 获取消息
            TextMessage textMessage = (TextMessage) consumer.receive();
            LOGGER.info("textMessage: [{}]", textMessage.getText());
            session.commit();
            session.close();
        } catch (JMSException e) {
            LOGGER.error("创建连接异常！", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOGGER.error("关闭连接异常！", e);
                }
            }
        }
    }
}
