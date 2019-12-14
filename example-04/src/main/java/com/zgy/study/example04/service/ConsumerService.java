package com.zgy.study.example04.service;

import javax.jms.Destination;

/**
 * @author: ZGY
 * @date: 2019-12-14 18:02
 * @description: ConsumerService
 * @version: 1.0
 */
public interface ConsumerService {

    /**
     * 接收消息
     * @param destination
     */
    void receive(Destination destination);
}
