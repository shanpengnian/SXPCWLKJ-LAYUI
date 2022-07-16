package com.sxpcwlkj.task;


import com.sxpcwlkj.utils.IpUtils;
import com.sxpcwlkj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class LuaDistributeLock {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    private static String LOCK_PREFIX = "lua_";

    private DefaultRedisScript<Boolean> lockScript;


    //@Scheduled(cron = "0/5 * * * * *")
    public void lockJob() {

        String lock = LOCK_PREFIX + "LockNxExJob123456";

        boolean luaRet = false;
        try {
            luaRet = luaExpress(lock, IpUtils.getHostIp());

            //获取锁失败  通过lua脚本获取锁
            if (!luaRet) {
                String value = (String) redisUtil.get(lock);
//                if(value.equals("")){
//                    redisUtil.remove(lock);
//                }else {
//                    //打印当前占用锁的服务器IP
//                    log.info("当前占用锁的服务器ip="+IpUtils.getHostIp()+" value:"+value);
//                }
                log.info("当前占用锁的服务器ip="+IpUtils.getHostIp()+" value:"+value);
                return;
            } else {
                //获取锁成功
                log.info("获取锁成功,执行方法..........");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            log.error("出错了", e);

        } finally {
            if (luaRet) {
                log.info("释放锁");
                redisUtil.remove(lock);
            }
        }
    }


    /**
     * 获取lua结果
     * @param key
     * @param value
     * @return
     */
    public Boolean luaExpress(String key,String value) {
        lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("add.lua")));
        lockScript.setResultType(Boolean.class);
        // 封装参数
        List<Object> keyList = new ArrayList<Object>();
        keyList.add(key);
        keyList.add(value);
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        return result;
    }


}
