package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_department")
public class Department {
    @TableId(type = IdType.AUTO)
    private Integer departmentId;

    private String departmentName;

    private String departmentDesc;

    private Integer departmentFathe;

    private Integer departmentState;

    private Integer departmentSort;

    private String departmentOuther;

}