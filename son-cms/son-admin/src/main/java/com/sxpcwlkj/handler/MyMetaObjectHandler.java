package com.sxpcwlkj.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component//放置IOC容器中
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入时的填充策略

    /**
     *  https://blog.csdn.net/as849167276/article/details/105216940
     * mybatis Plus 自动实现 数据赋值  例如 新增 crateTime  添加时间
     *                                      更新 updateTime 更新时间
     * @param metaObject
     */

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        //在插入时填充`crateTime`和`updateTime`字段
        this.setFieldValByName("crateTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    //更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        //在更新时填充`updateTime`字段
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}