package com.sxpcwlkj.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttJsionUtils {

    //唯一ID
    private int id;
    //系统编码
    private String sysCode;
    //执行标识
    private int index;
    //执行的data
    private Map<String,Object> data;
}
