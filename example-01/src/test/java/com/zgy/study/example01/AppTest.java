package com.zgy.study.example01;

import com.zgy.study.example01.config.ActiveMQConfig;
import com.zgy.study.example01.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: ZGY <br>
 * @date: 2019-12-12 20:10 <br>
 * @description: AppTest <br>
 * @version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, ActiveMQConfig.class})
public class AppTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void test() {
        jmsTemplate.send(session -> session.createTextMessage("好好学习，天天向上！"));
    }
}
