package com.sxpcwlkj.service.impl;


import com.sxpcwlkj.entity.PayOrder;
import com.sxpcwlkj.entity.PayProduct;
import com.sxpcwlkj.mapper.MemberMapper;

import com.sxpcwlkj.mapper.PayOrderMapper;
import com.sxpcwlkj.mapper.PayProductMapper;

import com.sxpcwlkj.service.PayService;
import com.sxpcwlkj.service.UserService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PayServiceImpl implements PayService {
    @Resource
    private PayProductMapper payMapper;
    @Resource
    private UserService userService;
    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public List<PayProduct> listPayProduct() {
        return payMapper.listPayProduct();
    }

    @Override
    public int addPayOrder(PayOrder payOrder) {
        return payOrderMapper.insert(payOrder);
    }

    @Override
    public PayOrder selectPayOrder(int orderId) {
        return payMapper.selectPayOrder(orderId);
    }

    @Override
    public PayProduct selectPayProduct(int productId) {
        return payMapper.selectPayProduct(productId);
    }

    @Override
    public int updatePayOrdeType(int userId,String outTradeNo, String totalAmount, String tradeNo) {
        PayOrder payOrder = new PayOrder();
        //订单号
        payOrder.setOrderNum(outTradeNo);
        //交易付款金额
        payOrder.setOrderPaidPice(DataUtil.getBigDecimal(totalAmount));
        //交易号
        payOrder.setTradeNo(tradeNo);
        //会员id
        payOrder.setUserId(userId);
        //订单状态
        payOrder.setOrderState(EnumUtil.Pay.已付款.getValue());
        //付款时间
        payOrder.setOrderPayTime(new Date());


        PayOrder ispay=payMapper.selectForCode(outTradeNo);
        if(ispay==null){
            return -1;
        }else if(ispay.getOrderState()==2){
            return -2;
        }
        int i = payMapper.updatePayOrdeType(payOrder);
        if (i == 1) {
            try {
                i = userService.updateUserMony(userId, DataUtil.getBigDecimal(totalAmount), "用户余额充值");
                return i;
            } catch (Exception e) {
                e.printStackTrace();
                return -3;
            }
        }
        return 0;
    }


}
