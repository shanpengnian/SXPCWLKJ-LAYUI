package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties(prefix = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = -2380176315197843793L;
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String password;


}
