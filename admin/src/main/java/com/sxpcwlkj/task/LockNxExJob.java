package com.sxpcwlkj.task;

import com.sxpcwlkj.utils.IpUtils;
import com.sxpcwlkj.utils.RedisUtil;
import com.sxpcwlkj.utils.SysouUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class LockNxExJob {


    private static String LOCK_PREFIX="prefix_";
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 分布式锁:
     * 执行前 先获取锁  获取成功,执行方法体
     *                 获取失败,直接返回
     *setIfAbsent:如果键不存在则新增,存在则不改变已经有的值
     *  意思就是:一个定义的key 在同一个redis中
     *                  返回 false    说明有一个服务器在用,已经被锁起来
     *                  返回 true     说明没有任何服务器在使用,用此服务创建缓存,value=IP
     *
     *
     *                  但是存在一个问题,如果在获取锁之后,释放锁之前  中间这个过程发生宕机  将出现死锁状态
     *                  出现这种情况只能手动解锁
     */

    //@Scheduled(fixedRate=1000)
//    public  void test(){
//        String lock=LOCK_PREFIX+"LOCKNxExJob123";
//        boolean nxRet=redisUtil.setIfAbsent(lock, IpUtils.getHostIp());
//        SysouUtil.sysou(nxRet);
//    }


    @Scheduled(fixedRate=5000)
    public  void lockjob(){
        String lock=LOCK_PREFIX+"LOCKNxExJob";
        try {
            boolean nxRet=redisUtil.setIfAbsent(lock, IpUtils.getHostIp());
            //获取锁失败
            if(!nxRet){
                String value=(String)redisUtil.get(lock);
                //打印当前占用锁的服务器IP
                log.info("当前占用锁的服务器IP",value);
                return;
            }
            redisUtil.set(IpUtils.getHostIp(),3600);
            //获取锁成功
            log.info("获取锁: "+lock+" 成功!");
            Thread.sleep(5000);
        }catch (Exception e){
           log.error("获取锁出错 ",e);
        }finally {
          redisUtil.remove(lock);
        }
    }

}