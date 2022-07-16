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
@TableName(value = "p_wx_pay_config")
public class WxPayConfig {
    @TableId(type = IdType.AUTO)
    private Integer wxId;

    private Integer userId;

    private String appId;

    private String mchId;

    private String wxKey;

    private String notieyUrl;

}