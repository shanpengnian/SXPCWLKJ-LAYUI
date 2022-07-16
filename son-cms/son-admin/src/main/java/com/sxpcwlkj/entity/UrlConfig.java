package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "request")
public class UrlConfig {

    @TableId(type = IdType.AUTO)
    private String url;

    private String code;

    private String token;
}
