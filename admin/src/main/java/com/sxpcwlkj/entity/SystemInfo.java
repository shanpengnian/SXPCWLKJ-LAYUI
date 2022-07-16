package com.sxpcwlkj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfo {

    //系统名称
    private String sysName;

    //版本信息
    private String sysVersion;

    //系统
    private String sysXiTong;

    //系统版本
    private String getSysXiTongVersion;

    //CUP核数
    private String sysCpu;

    //Cpu负载
    private double sysCupFuzai;

    //jvm线程负载
    private double sysJvmlv;

    //系统内存
    private long sysNeicun;

    //已使用内存
    private long sysShenyuNeicun;

    //内存使用率
    private double sysNeicunlv;


    //系统语言
    private String sysYanyan;

    //IP
    private String sysIp;

    //JVM信息
    private String sysJvm;

    //jvm版本
    private String sysJvmVersion;

    //JVM占用内存
    private String sysJvmneicun;

    //JVM占用内存空闲
    private String sysJvmneicunKonxian;

    //JVM内存
    private String sysJvmMaxneicun;

    //时区
    private String sysShiqu;

    //宽带
    private float netInfo;


}
