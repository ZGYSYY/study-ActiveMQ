package com.zgy.study.example03;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import javax.jms.Destination;

/**
 * @author ZGY
 * @date 2019/12/13 13:58
 * @description ProducerConfig
 */
@Configuration
public class ProducerConfig {

    /**
     * 连接工厂。
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://192.168.1.166:61616");
    }

    /**
     * 将 ConnectionFactory 交给 Spring 来管理。
     * @return
     */
    @Bean
    public SingleConnectionFactory singleConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        SingleConnectionFactory factory = new SingleConnectionFactory();
        factory.setTargetConnectionFactory(activeMQConnectionFactory);
        return factory;
    }

    /**
     * 将 SingleConnectionFactory 注入 JmsTemplate。
     * @param singleConnectionFactory
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(SingleConnectionFactory singleConnectionFactory) {
        return new JmsTemplate(singleConnectionFactory);
    }

    /**
     * 创建目标对象 —— queue（点对点）
     * @return
     */
    @Bean
    public Destination queueDestination() {
        return new ActiveMQQueue("queue-anno");
    }

    @Bean
    public Destination topicDestination() {
        return new ActiveMQTopic("topic-anno");
    }
}
