package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description member
 * @author xijue
 * @date 2021-04-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * member_id
     */
    private Long memberId;

    /**
     * member_name 昵称
     */
    private String memberName;

    /**
     * member_name 账号
     */
    private String memberAccount;

    /**
     * member_phone  手机号
     */
    private String memberPhone;

    /**
     * member_icon  头像
     */
    private String memberIcon;

    /**
     * member_passwore  密码
     */
    private String memberPassword;

    /**
     * member_state  状态  1：正常  2：禁用
     */
    private Integer memberState;

    /**
     * member_sex  性别  1：保密  2：男  3：女
     */
    private Integer memberSex;

    /**
     * update_time  最后登录/更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  updateTime;

    /**
     * add_time   注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * wx_open_id  微信openid
     */
    private String wxOpenId;

    /**
     * member_note  备注
     */
    private String memberNote;


}