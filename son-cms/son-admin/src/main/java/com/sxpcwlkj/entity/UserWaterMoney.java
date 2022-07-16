package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.controller.admin
 * @date 2019/07/11 0015
 * 会员资金流水
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_user_water_money")
public class UserWaterMoney {

    private int userWaterId;  
    
    private int userId;
    
    private int userWaterType;            //类型  1：支出 2：收入
    
    private BigDecimal userWaterMoney;    //金额

    private String userWaterIp;           //ip

    private String userWaterDesc;         //描述
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date userWaterTime;           //时间
    
}
