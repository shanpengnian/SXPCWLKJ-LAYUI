package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "user")
public class TestEntity {

    /**
     * 更改策略：主键自增AUTO,同时要在数据库中设置主键id自增，然后保存。否则会报错
     * 主键自增AUTO       @TableId(type = IdType.AUTO)
     * 手动输入INPUT      @TableId(type = IdType.INPUT)   //setId(1L)
     */

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private int age;
    private String email;
    //字段添加填充内容
    @Column(name = "crateTime")
    @TableField(fill = FieldFill.INSERT)
    private Date crateTime;

    @Column(name = "updateTime")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //@TableField(exist = false) //非数据库字段

    //属性名名和数据库的字段映射不一致时可以用此注解指定
    @TableField("user_sex")
    private int sex;



}
