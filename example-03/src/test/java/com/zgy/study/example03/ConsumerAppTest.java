package com.zgy.study.example03;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

/**
 * @author ZGY
 * @date 2019/12/13 16:06
 * @description ConsumerAppTest，将消息监听器配置在代码中。
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConsumerConfig.class)
public class ConsumerAppTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerAppTest.class);

    @Autowired
    private JmsTemplate jmsTemplate;


    @Test
    public void test() throws IOException {
        System.in.read();
    }

    @Test
    public void test2() {
        String message = (String)jmsTemplate.receiveAndConvert();
        LOGGER.info("message: [{}]", message);
    }
}
