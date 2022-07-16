package com.sxpcwlkj.service.impl;

import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.mapper.MemberMapper;
import com.sxpcwlkj.service.MemberService;
import com.sxpcwlkj.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "memberInfoCache") // 主要是用于和其他类的冲突
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @return
     * @Cacheable 的逻辑是：查找缓存 - 有就返回 -没有就执行方法体 - 将结果缓存起来； 适用于查询数据的方法
     * @CachePut 的逻辑是：执行方法体 - 将结果缓存起来；                           适用于更新数据的方法
     * @CachePut(key="#p0.id") //#p0表示第一个参数    #参数名”或者“#p参数index下标”
     *
     * @Cacheable(value="users", key="#id")          参数为id的座位key
     * @Cacheable(value="users", key="#p0")          第一个参数座位key
     * @Cacheable(value="users", key="#user.id")     对象User的id属性值座位key
     * @Cacheable(value="users", key="#p0.id")       对象User的id属性值座位key
     * 属性 condition:
     * 当为true时表示进行缓存处理；当为false时表示不进行缓存处理,即每次都执行方法体
     * @Cacheable(value={"users"}, key="#user.id", condition="#user.id%2==0")
     * 则 示例表示只有当user的id为偶数时才会进行缓存
     */


    @Override
    public List<Member> selectmemberList() {
        String key = "selectmemberList";
        List<Member> list = null;
        if (redisUtil.hasKey(key)) {
            list = (List<Member>) redisUtil.get(key);
        } else {
            list = memberMapper.selectList(null);
            redisUtil.set(key, list, 1000);
        }
        return list;
    }


    /**
     * 注解 @Cacheable  缓存有就返回 没有执行方法体查询,返回并保存到缓存
     * @param id
     * @return
     */

    @Override
    @Cacheable(value = {"selectOne"},key ="#root.methodName" )
    public Member selectOne(int id) {
        Member member = memberMapper.selectById(id);
        return member;
    }


    /**
     * 改写 解决高并发下的击穿
     */

    /**
     * 缓存击穿是指缓存中没有但数据库中有的数据（一般是缓存时间到期），
     * 这时由于并发用户特别多，同时读缓存没读到数据，又同时去数据库去取数据，引起数据库压力瞬间增大，造成过大压力
     * 解决方案：
     *
     * 设置热点数据永远不过期。
     * 加互斥锁，互斥锁参考代码如下
     */


    public Member selectTwo(int id) {
        Member member=null;
        if(redisUtil.hasKey(id+"")){
            member=(Member) redisUtil.get(id+"");
        }else {
            member = memberMapper.selectById(id);
            redisUtil.set(id+"",member,10000);
        }

        return member;
    }


}
