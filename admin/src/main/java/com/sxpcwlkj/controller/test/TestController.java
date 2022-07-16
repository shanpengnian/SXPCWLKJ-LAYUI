package com.sxpcwlkj.controller.test;


import com.sxpcwlkj.config.MqttPushClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author: lxw
 * @Date: 2018/10/19 19:36
 * @email: 1229703575@qq.com
 * @Description: 测试文件
 */
@RestController
@RequestMapping("/guest")

public class TestController {

    @Autowired
    private MqttPushClient mqttPushClient;

    /**
     * 发布主题
     * @return
     */
    @GetMapping(value = "/publishTopic")
    public Object publishTopic() {
        mqttPushClient.publish(0,false,"test/test","测试一下发布消息");
        mqttPushClient.publish(0,false,"smartroom","123456789");
        //test的所有 订阅者  都能收到通知
        return null;
    }
}