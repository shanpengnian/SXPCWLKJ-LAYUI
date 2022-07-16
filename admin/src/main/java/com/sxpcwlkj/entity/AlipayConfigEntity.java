package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.entity
 * @date 2019/9/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_alipay_config")
public class AlipayConfigEntity {
    private int id;
    private int userId;
    private String appId;                         //支付应用ID
    private String merchantPrivateKey;            //商户私钥
    private String alipayPublicKey;               //支付宝公钥
    private String notifyUrl;                     //支付宝通知返回链接
    private String returnUrl;                     //支付宝同步通知链接
    private String signType;                      //签名方式
    private String alipayCharset;                 //字符串编码格式
    private String gatewayUrl;                    //字符串编码格式
}
