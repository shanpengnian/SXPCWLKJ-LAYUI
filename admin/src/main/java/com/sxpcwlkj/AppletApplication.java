package com.sxpcwlkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching  //打开redis缓存注解功能
@EnableTransactionManagement //开启事务注解
//扫描mapper目录
@MapperScan("com.sxpcwlkj.mapper")
@SpringBootApplication
public class AppletApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppletApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AppletApplication.class, args);
        System.out.println("===============启动成功==============");
    }


}
