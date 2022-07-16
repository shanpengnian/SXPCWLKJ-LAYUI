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
@TableName(value = "p_role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private Integer roleType;

    private Integer roleSort;

    private Integer roleRank;

    private Integer codeId;

    @TableField(exist = false) //非数据库字段
    private String codeCode;


}