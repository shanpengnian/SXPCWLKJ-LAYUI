package com.sxpcwlkj.service;

public interface MqttService {

    /**
     * 发送短信
     * @param phone  发送手机号
     * @return       返回短信码
     */
    String  sendNote(String phone);
}
