package com.sxpcwlkj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sxpcwlkj.entity.ApiEntity;
import com.sxpcwlkj.entity.Log;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.service.ApiService;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.service.SmsService;
import com.sxpcwlkj.service.UserService;
import com.sxpcwlkj.utils.DataUtil;
import com.sxpcwlkj.utils.JsonUtil;
import com.sxpcwlkj.utils.MiaoDiHttpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;

@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    ApiService apiService;

    @Resource
    LogService logService;

    @Resource
    UserService userService;

    /**
     * 短信验证码 接口
     */
    @Override
    public String querySmsInfo(String userPhone) throws Exception {
        String phoneCode = DataUtil.getString((int) ((Math.random() * 9 + 1) * 100000));                //生成验证码
        ApiEntity apiEntity = apiService.selectApiByApiId(1);                                      //接口
        StringBuilder sb = new StringBuilder();
        sb.append("accountSid").append("=").append(apiEntity.getApiKey());                       //参数
        sb.append("&to").append("=").append(userPhone);                                          //发送对象手机号
        sb.append("&param").append("=").append(URLEncoder.encode(phoneCode, "UTF-8"));       //模板变量
        sb.append("&templateid").append("=").append(apiEntity.getApiTwo());                      //模板ID
        //sb.append("&smsContent").append("=").append( URLEncoder.encode("【秒嘀科技】您的验证码为123456，该验证码5分钟内有效。请勿泄漏于他人。","UTF-8"));
        String body = sb.toString() + MiaoDiHttpUtil.createCommonParam(apiEntity.getApiKey(), apiEntity.getApiOne());   //接口+接口地址
        String result = MiaoDiHttpUtil.post(apiEntity.getApiUrl() + apiEntity.getApiSite(), body);   //请求
        //System.out.println(result);
        JSONObject jsonObject = JsonUtil.getStrToJsion(result);
        logService.addLog(new Log("增:手机号为" + userPhone + "短信验证:" + phoneCode + "信息" + jsonObject.getString("respDesc") + "smsId:" + jsonObject.getString("smsId")));
        return phoneCode;
    }

    /**
     * 营销短信发送
     */
    @Override
    public int userSessionOffMsg(String userPhone) throws Exception {

        ApiEntity apiEntity = apiService.selectApiByApiId(2);                                     //接口
        StringBuilder sb = new StringBuilder();
        sb.append("accountSid").append("=").append(apiEntity.getApiKey());                       //参数
        sb.append("&to").append("=").append(userPhone);                                          //发送对象手机号
        sb.append("&templateid").append("=").append(apiEntity.getApiTwo());                      //模板ID
        String body = sb.toString() + MiaoDiHttpUtil.createCommonParam(apiEntity.getApiKey(), apiEntity.getApiOne());   //接口+接口地址
        String result = MiaoDiHttpUtil.post(apiEntity.getApiUrl() + apiEntity.getApiSite(), body);   //请求
        //System.out.println(result);
        JSONObject jsonObject = JsonUtil.getStrToJsion(result);
        logService.addLog(new Log("增:会员" + userPhone + "Sessionkey失效提醒，信息" + jsonObject.getString("respDesc") + "smsId:" + jsonObject.getString("smsId")));
        return 1;
    }

    /**
     * 综合短信接口
     */
    @Override
    public String querySmsInfo(String userPhone, String codeId, int type) throws Exception {

        if (type == 1) {
            String phoneCode = DataUtil.getString((int) ((Math.random() * 9 + 1) * 100000));                            //生成验证码
            ApiEntity apiEntity = apiService.selectForCodeId(codeId);                                                   //接口
            StringBuilder sb = new StringBuilder();
            sb.append("accountSid").append("=").append(apiEntity.getApiKey());                                          //参数
            sb.append("&to").append("=").append(userPhone);                                                             //发送对象手机号
            sb.append("&param").append("=").append(URLEncoder.encode(phoneCode, "UTF-8"));                         //模板变量
            sb.append("&templateid").append("=").append(apiEntity.getApiTwo());                                         //模板ID
            //sb.append("&smsContent").append("=").append( URLEncoder.encode("【秒嘀科技】您的验证码为123456，该验证码5分钟内有效。请勿泄漏于他人。","UTF-8"));
            String body = sb.toString() + MiaoDiHttpUtil.createCommonParam(apiEntity.getApiKey(), apiEntity.getApiOne());   //接口+接口地址
            String result = MiaoDiHttpUtil.post(apiEntity.getApiUrl() + apiEntity.getApiSite(), body);   //请求
            //System.out.println(result);
            JSONObject jsonObject = JsonUtil.getStrToJsion(result);
            logService.addLog(new Log("增:手机号为" + userPhone + "短信验证:" + phoneCode + "信息" + jsonObject.getString("respDesc") + "smsId:" + jsonObject.getString("smsId")));
            return phoneCode;
        } else {
            ApiEntity apiEntity = apiService.selectForCodeId(codeId);                                                   //接口
            StringBuilder sb = new StringBuilder();
            sb.append("accountSid").append("=").append(apiEntity.getApiKey());                                          //参数
            sb.append("&to").append("=").append(userPhone);                                                             //发送对象手机号
            sb.append("&templateid").append("=").append(apiEntity.getApiTwo());                                         //模板ID
            String body = sb.toString() + MiaoDiHttpUtil.createCommonParam(apiEntity.getApiKey(), apiEntity.getApiOne());//接口+接口地址
            String result = MiaoDiHttpUtil.post(apiEntity.getApiUrl() + apiEntity.getApiSite(), body);   //请求
            //System.out.println(result);
            JSONObject jsonObject = JsonUtil.getStrToJsion(result);
            logService.addLog(new Log("增:会员" + userPhone + "Sessionkey失效提醒，信息" + jsonObject.getString("respDesc") + "smsId:" + jsonObject.getString("smsId")));
            return 1 + "";
        }
    }

}
