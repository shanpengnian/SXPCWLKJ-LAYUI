package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_emai_config")
public class EmaiConfig {
    @TableId(type = IdType.AUTO)
    private Integer emailId;

    private Integer userId;

    private String smtpServer;

    private Integer port;

    private String sendEmail;

    private String sendNickname;
    //用户授权码 不是用户名密码 可以自行查看相关邮件服务器怎么查看
    private String sendPassword;

    //发送地址
    @TableField(exist = false)
    private String receptionEmail ;
    //发送主题
    @TableField(exist = false)
    private String subject ;
    //发送内容
    @TableField(exist = false)
    private String content ;

}