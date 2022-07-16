package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sxpcwlkj.controller.admin.UserController;
import com.sxpcwlkj.utils.IpUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_log")
public class Log {
    private int logId;
    private int logType;
    private String logDesc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;
    private int logLevel;   //1：普通日志  2：重要日志   3：系统报错  4：一级漏洞
    private int userId;
    private String logIp;
    private String userName;
    private int isCollect;


    public Log(String logDesc) {
        super();
        this.userId = UserController.userId;
        if(this.userId==0){
            this.userId=1;
        }
        String str = logDesc.substring(0,1);
        if(str.equals("增")){
            this.logType = 1;
        }
        if(str.equals("删")){
            this.logType = 2;
        }
        if(str.equals("改")){
            this.logType = 3;
        }
        if(str.equals("查")){
            this.logType = 4;
        }

        this.logDesc = logDesc.substring(2);
        this.logTime = new Date();
        if(logType == 1|| logType == 4){
            this.logLevel = 1;
        }
        if(logType == 2|| logType == 3){
            this.logLevel = 2;
        }
        try {
            this.logIp = IpUtils.getHostIp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.logLevel=1;
    }

    public Log(int logLevel,String logDesc) {
        super();
        this.userId = UserController.userId;
        if(this.userId==0){
            this.userId=1;
        }
        String str = logDesc.substring(0,1);
        if(str.equals("增")){
            this.logType = 1;
        }
        if(str.equals("删")){
            this.logType = 2;
        }
        if(str.equals("改")){
            this.logType = 3;
        }
        if(str.equals("查")){
            this.logType = 4;
        }

        this.logDesc = logDesc.substring(2);
        this.logTime = new Date();
        this.logLevel = logLevel;
        try {
            this.logIp = IpUtils.getHostIp()+":"+IpUtils.getHttpPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
