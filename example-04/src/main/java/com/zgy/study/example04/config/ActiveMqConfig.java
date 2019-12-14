package com.zgy.study.example04.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

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

    @Bean
    public JmsTemplate jmsTemplate() {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.31.66:61616");
        JmsTemplate jmsTemplate = new JmsTemplate(factory);
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("test-queue");
        jmsTemplate.setDefaultDestination(activeMQQueue);
        jmsTemplate.setReceiveTimeout(10000L);
        return jmsTemplate;
    }
}
