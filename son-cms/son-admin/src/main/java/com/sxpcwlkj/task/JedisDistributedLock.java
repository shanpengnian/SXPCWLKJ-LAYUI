package com.sxpcwlkj.task;

import com.sxpcwlkj.utils.IpUtils;
import com.sxpcwlkj.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
//@Configuration
//@EnableScheduling
public class JedisDistributedLock {

    private final Logger logger = LoggerFactory.getLogger(JedisDistributedLock.class);

    private static String LOCK_PREFIX = "JedisDistributedLock_";

    private DefaultRedisScript<Boolean> lockScript;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    //@Scheduled(cron = "0/5 * * * * *")
    public void lockJob() {

        String lock = LOCK_PREFIX + "JedisNxExJob111";
        boolean lockRet = false;
        try {
            lockRet = this.setLock(lock, 600);

            //获取锁失败
            if (!lockRet) {
                String value = (String) redisUtil.get(lock);
                //打印当前占用锁的服务器IP
                logger.info("jedisLockJob 当前占用锁PI:"+value);
                return;
            } else {
                //获取锁成功
                logger.info("jedisLockJob 获取锁成功执行方法体");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            logger.error("jedisLockJob 出错了", e);

        } finally {
            if (lockRet) {
                logger.info("jedisLockJob 释放锁");
                releaseLock(lock, IpUtils.getHostIp());
            }
        }
    }

    /**
     * 设置
     * @param key
     * @param expire
     * @return
     */
    public boolean setLock(String key, long expire) {
        try {
            Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(key.getBytes(), IpUtils.getHostIp().getBytes(), Expiration.seconds(expire) , RedisStringCommands.SetOption.ifAbsent());
                }
            });
            return result;
        } catch (Exception e) {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }


    /**
     * 释放锁操作
     * @param key
     * @param value
     * @return
     */
    private boolean releaseLock(String key, String value) {
        lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("unlock.lua")));
        lockScript.setResultType(Boolean.class);
        // 封装参数
        List<Object> keyList = new ArrayList<Object>();
        keyList.add(key);
        keyList.add(value);
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        return result;
    }

}
