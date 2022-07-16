package com.sxpcwlkj.service;

/**
 * (短信接口)
 */
public interface SmsService {


    /**
     * 短信接口
     */
    String querySmsInfo(String userPhone) throws Exception;


    /**
     * 营销短信发送
     */
    int userSessionOffMsg(String userPhone) throws Exception;


    /**
     * 综合短信接口
     */
    String querySmsInfo(String userPhone, String codeId, int type) throws Exception;


}
