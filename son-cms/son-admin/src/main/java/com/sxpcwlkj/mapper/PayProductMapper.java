package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.PayOrder;
import com.sxpcwlkj.entity.PayProduct;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PayProductMapper extends BaseMapper<PayProduct> {
    /**
     * 查询商品
     * @return
     */
    @Select("SELECT *  FROM `p_pay_product`")
    List<PayProduct> listPayProduct();

    /**
     * 创建订单   返回自增主键
     * @param payOrder
     * @return
     */
    @Insert("INSERT INTO `p_pay_order`(`order_num`,`order_state`,`order_pice`,`order_paid_pice`,`product_id`,`order_count`,`order_add_time`,`order_pay_time`,`user_id`,`sys_code`)VALUES(#{orderNum},#{orderState},#{orderPice},#{orderPaidPice},#{productId},#{orderCount},#{orderAddTime},#{orderPayTime},#{userId},#{sysCode})")
    @Options(useGeneratedKeys = true,keyProperty = "orderId")
    int  addPayOrder(PayOrder payOrder);

    /**
     * 查询订单
     */
    @Select("SELECT *  FROM `p_pay_order` where order_id=#{orderId}")
    PayOrder selectPayOrder(int orderId);

    /**
     * 产线产品
     */
    @Select("SELECT *  FROM `p_pay_product` where `product_id`=#{productId}")
    PayProduct selectPayProduct(int productId);

    /**
     * 付款成功后，根据订单编号，修改订单状态
     * @return
     */
    @Update("UPDATE `p_pay_order`  SET `order_state`=#{orderState} ,`order_paid_pice`=#{orderPaidPice},`order_pay_time`=#{orderPayTime},`trade_no`=#{tradeNo}  WHERE `order_num`=#{orderNum}  AND  `user_id`=#{userId}")
    int updatePayOrdeType(PayOrder payOrder);

    /**
     *
     * @param code
     * @return
     */
    @Select("SELECT  *  FROM  `p_pay_order` WHERE `order_num`=#{code}")
    PayOrder selectForCode(String code);
}
