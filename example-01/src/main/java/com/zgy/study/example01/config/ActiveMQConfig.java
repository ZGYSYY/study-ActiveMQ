package com.zgy.study.example01.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author ZGY
 * @date 2019/12/12 17:42
 * @description ActiveMQConfig
 */
@Configuration
@PropertySource("classpath:activemq.properties")
public class ActiveMQConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQConfig.class);

    @Value("${activemq.brokerURL}")
    private String brokerURL;

    @Value("${activemq.userName}")
    private String userName;

    @Value("${activemq.password}")
    private String password;

    @Value("${activemq.pool.maxConnections}")
    private Integer maxConnections;

    @Value("${activemq.queueName}")
    private String queueName;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerURL);
        factory.setUserName(userName);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setMaxConnections(maxConnections);
        return factory;
    }

    @Bean
    public SingleConnectionFactory singleConnectionFactory() {
        SingleConnectionFactory factory = new SingleConnectionFactory();
        factory.setTargetConnectionFactory(pooledConnectionFactory());
        return factory;
    }

    @Bean
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue(queueName);
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(singleConnectionFactory());
        template.setDefaultDestinationName(queueName);
        return template;
    }

    @Bean
    public MsgQueueMessageListener msgQueueMessageListener() {
        return new MsgQueueMessageListener();
    }

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(singleConnectionFactory());
        container.setDestination(activeMQQueue());
        container.setMessageListener(msgQueueMessageListener());
        return container;
    }

    private class MsgQueueMessageListener implements SessionAwareMessageListener<Message> {
        @Override
        public void onMessage(Message message, Session session) throws JMSException {
            LOGGER.info("消费者获取到的信息：[{}]", ((TextMessage)message).getText());
        }
    }
}
