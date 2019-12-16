package com.zgy.study.example04.config;

import com.zgy.study.example04.message.listener.MyMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * @author: ZGY
 * @date: 2019-12-14 17:10
 * @description: ActiveMqConfig
 * @version: 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.zgy.study.example04.service.impl")
public class ActiveMqConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqConfig.class);

    private ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.1.166:61616");
        return factory;
    }

    private Destination destination() {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("test-queue");
        return activeMQQueue;
    }

    private Destination topicDestination() {
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("test-topic");
        return activeMQTopic;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestination(destination());
        jmsTemplate.setReceiveTimeout(10000L);
        return jmsTemplate;
    }

    @Bean
    public JmsTemplate topicJmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestination(new ActiveMQTopic("test-topic"));
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setReceiveTimeout(10000L);
        return jmsTemplate;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setDestination(destination());
        listenerContainer.setMessageListener(new MyMessageListener());
        return listenerContainer;
    }

    @Bean
    public MessageListenerContainer topicMessageListener() {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setDestination(topicDestination());
        listenerContainer.setMessageListener(new MyMessageListener());
        return listenerContainer;
    }
}
