package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "p_user_money")
public class UserMoney {

    private int userMoneyId;

    private int userId;

    private BigDecimal availableMoney;    //剩余余额

    private BigDecimal totalMoney;        //总余额

    private BigDecimal hasBeenMoney;      //已用金额

    private Date addTime;
}
