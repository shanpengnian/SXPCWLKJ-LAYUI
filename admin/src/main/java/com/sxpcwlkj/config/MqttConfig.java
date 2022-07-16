package com.sxpcwlkj.config;

import com.sxpcwlkj.utils.DataUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Classname MtqqEntity
 * @Description mqtt相关配置信息
 * @Date 2019/4/11 23:00
 * @Created by Jack
 */
@Component
@ConfigurationProperties(prefix = "mqtt")
@Setter
@Getter
public class MqttConfig {
    @Autowired
    private MqttPushClient mqttPushClient;

    /**
     * 用户名
     */

    private String username;
    /**
     * 密码
     */

    private String password;
    /**
     * 连接地址
     */

    private String hostUrl;
    /**
     * 客户Id
     */

    private String clientId;
    /**
     * 默认连接话题
     */

    private String defaultTopic;
    /**
     * 超时时间
     */

    private int timeout;
    /**
     * 保持连接数
     */

    private int keepalive;


    @Bean
    public MqttPushClient getMqttPushClient() {
        //客户端连接
        clientId= DataUtil.getRandomString(3);
        mqttPushClient.connect(hostUrl, clientId, username, password, timeout, keepalive);

        //订阅某个主题

        // 以/#结尾表示订阅所有以test开头的主题
        mqttPushClient.subscribe(defaultTopic, 0);

        mqttPushClient.subscribe("text", 0);
        return mqttPushClient;
    }
}