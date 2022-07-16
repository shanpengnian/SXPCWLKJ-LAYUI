package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private Integer departmentId;

    private String userPhone;

    private String userAccount;

    private String userPassword;

    private String userEmail;

    private String userName;

    private Integer userCheck;

    private Integer isSuper;

    private String icon;

    private String userQq;

    private Date creationTime;

    private String wechatUid;

    private String userDescribe;

    @TableField(exist = false) //非数据库字段
    private BigDecimal balance;  ///余额

    @TableField(exist = false) //非数据库字段
    private List<Role> roles;   //角色
    @TableField(exist = false) //非数据库字段
    private String strRoles;   //角色

    @TableField(exist = false) //非数据库字段
    private String roleName;   //角色名
    @TableField(exist = false)
    private List<Function> functionList;
    @TableField(exist = false)
    private String strfunction;


    public User(String userPhone, String userAccount, String userPassword) {
        this.userPhone = userPhone;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }

}