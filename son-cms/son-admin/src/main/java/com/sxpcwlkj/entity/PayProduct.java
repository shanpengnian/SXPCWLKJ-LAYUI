package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 支付  产品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_pay_product")
public class PayProduct {
    @TableId(type = IdType.AUTO)
    private int productId;                   //产品id
    private String productName;              //产品名称
    private BigDecimal productPice;          //产品价格
    private int productLevel;                //级别  0：充值 1：普通版  2：月版基础  3年版基础  4:月版旗舰 5：年版旗舰  9：永久版
    private int  numDay;                     //服务周期 0：充值/基础     月=30   年=365  季=90  永久：999999999
}
