package com.zgy.study.example04.service;

import javax.jms.Destination;

/**
 * @author: ZGY
 * @date: 2019-12-14 18:02
 * @description: ProducerService
 * @version: 1.0
 */
public interface ProducerService {

    /**
     * 向指定目标发送消息
     * @param destination
     * @param message
     */
    void sendMessage(Destination destination, String message);

    /**
     * 向默认目标发送消息
     * @param message
     */
    void sendMessage(String message);
}
