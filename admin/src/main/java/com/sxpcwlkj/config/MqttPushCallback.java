package com.sxpcwlkj.config;

import com.sxpcwlkj.utils.MqttJsionUtils;
import net.sf.json.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname PushCallback
 * @Description 消费监听类
 * @Date 2019/4/11 23:31
 * @Created by Jack
 */
@Component
public class MqttPushCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttPushClient.class);

    @Autowired
    private MqttConfig mqttConfig;

    private static MqttClient client;

    /**
     * 连接断开，可以做重连
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        // 连接丢失后，一般在这里面进行重连
        logger.info("连接断开，可以做重连");
        if (client == null || !client.isConnected()) {
            mqttConfig.getMqttPushClient();
        }
    }

    /**
     * 接收订阅的消息后,缺省回调
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // subscribe后得到的消息会执行到这里面
        logger.info("接收消息主题 : " + topic);
        logger.info("接收消息Qos : " + mqttMessage.getQos());
        logger.info("接收消息内容 : " + new String(mqttMessage.getPayload()));
        if("topic".equals(topic)){
            JSONObject jsonObject=JSONObject.fromObject(new String(mqttMessage.getPayload()));
            MqttJsionUtils mqtt=(MqttJsionUtils)JSONObject.toBean(jsonObject, MqttJsionUtils. class );
            System.out.println(mqtt.toString());

        }else {

        }

    }

    /**
     * 接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
    }
}