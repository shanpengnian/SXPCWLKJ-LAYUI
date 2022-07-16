package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付 订单
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_pay_order")
public class PayOrder {
    @TableId(type = IdType.AUTO)
    private int orderId;                            //订单主键
    private String orderNum;                        //订单号
    private int orderState;                         //1:待支付  2：已支付
    private BigDecimal orderPice;                   //订单金额
    private BigDecimal orderPaidPice;               //实付金额
    private int productId;                          //产品ID
    private int orderCount;                         //下单数量
    private String tradeNo;                         //支付交易号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderAddTime;                      //订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderPayTime;                      //支付时间
    private int userId;                             //会员id
    private String sysCode;                         //系统编码
}
