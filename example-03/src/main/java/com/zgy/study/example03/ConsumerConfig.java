package com.zgy.study.example03;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author ZGY
 * @date 2019/12/13 15:19
 * @description ConsumerConfig
 */
@Configuration
@EnableJms
public class ConsumerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.1.166:61616");
        return factory;
    }

    @Bean
    public SingleConnectionFactory singleConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        SingleConnectionFactory factory = new SingleConnectionFactory();
        factory.setTargetConnectionFactory(activeMQConnectionFactory);
        return factory;
    }

    @Bean
    public Destination queueDestination() {
        return new ActiveMQQueue("queue-anno");
    }

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(SingleConnectionFactory factory, Destination destination) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setMessageListener((MessageListener) message -> {
            if (message instanceof TextMessage) {
                try {
                    LOGGER.info("监听的信息为：{}", ((TextMessage) message).getText());
                } catch (JMSException e) {
                    LOGGER.error("JMSException!", e);
                }
            } else {
                LOGGER.error("消息必须为文本！");
            }
        });
        container.setConnectionFactory(factory);
        container.setDestination(destination);
        return container;
    }

    @Bean
    public JmsTemplate jmsTemplate(SingleConnectionFactory factory, Destination destination) {
        JmsTemplate jmsTemplate = new JmsTemplate(factory);
        jmsTemplate.setDefaultDestination(destination);
        jmsTemplate.setMessageConverter(new SimpleMessageConverter());
        return jmsTemplate;
    }
}
