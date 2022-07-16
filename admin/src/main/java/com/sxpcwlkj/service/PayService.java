package com.sxpcwlkj.service;

import com.sxpcwlkj.entity.PayOrder;
import com.sxpcwlkj.entity.PayProduct;

import java.util.List;
import java.util.Map;

public interface PayService {

    /**
     * 查询商品  集合
     * @return
     */
    List<PayProduct> listPayProduct();

    /**
     * 创建订单
     * @param payOrder
     * @return
     */
    int  addPayOrder(PayOrder payOrder);
    /**
     * 查询订单
     */
    PayOrder selectPayOrder(int orderId);
    /**
     * 产线产品
     */
    PayProduct selectPayProduct(int productId);

    /**
     * 系统服务 付款成功后，根据订单编号，修改订单状态
     * out_trade_no,  订单号
     * total_amount,  交易金额
     * trade_no       交易号
     * @return
     */
    int updatePayOrdeType(int userId,String outTradeNo, String totalAmount, String tradeNo);


}
