package com.zgy.study.example02;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * @author ZGY
 * @date 2019/12/13 11:14
 * @description JmsSender，发送端
 */
public class JmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);

    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.166:61616");
        Connection connection = null;
        try {
            // 创建连接
            connection = connectionFactory.createConnection();

            // 开始连接
            connection.start();

            // 创建会话
            Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

            // 创建目标
            Destination destination = session.createQueue("queue");

            // 创建生产者
            MessageProducer producer = session.createProducer(destination);

            // 创建消息
            TextMessage textMessage = session.createTextMessage("Hello ActiveMQ");

            // 发送消息
            producer.send(textMessage);

            // 关闭会话
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
