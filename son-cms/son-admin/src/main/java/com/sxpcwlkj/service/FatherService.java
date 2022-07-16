package com.sxpcwlkj.service;

import com.sxpcwlkj.utils.JsonResultObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 父 系统接口
 */
public interface FatherService {

    /**
     * 发生短信验证码
     * @param phone
     * @return
     */
    JsonResultObject sendMessages(String phone, String codeId, int type);

    /**
     * 充值下单
     * @param piceNum                 价格
     * @return
     */
    JsonResultObject voucherOrder(BigDecimal piceNum,int productId,int num);

    /**
     * 微信下单
     * @param orderId
     * @param describe
     * @return
     */
    JsonResultObject weChatPay(int orderId, String describe);


    /**
     * 微信回调
     * @param orderId
     * @return
     */
    JsonResultObject notifyWeChatPnPay(int orderId);

    /**
     * 支付宝下单起调
     * @param orderId
     * @param describe
     * @return
     */
    JsonResultObject AlipayPay(int orderId, String describe);

    /**
     * 支付宝异步回调
     * @param
     * @return
     */
    JsonResultObject alipayReturnNotice();

    /**
     * 支付宝回调更新订单
     * 返回 子系统通知地址
     * @return
     */
    JsonResultObject updatealipayReturnNotice(String outTradeNo,String totalAmount, String tradeNo);

    /**
     * 查询系统余额
     * @return
     */
    JsonResultObject selectMoney();
    /**
     * 上传图片
     * @param file
     * @return
     */
    JsonResultObject uploadFile(File file);
}
