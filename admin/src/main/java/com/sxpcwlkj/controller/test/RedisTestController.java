package com.sxpcwlkj.controller.test;


import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.service.MemberService;
import com.sxpcwlkj.utils.JsonUtil;
import com.sxpcwlkj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guest")
@Slf4j
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MemberService  memberService;

    /**
     * 对象保存到缓存
     * @return
     */
    @RequestMapping("/set")
    public String set(){
        try {
            Member member=new Member();
            member.setName("xijue");
            member.setPassword("123456");
            String jsonstr= JsonUtil.entityToJsonString(member);
            System.out.println(redisUtil.hasKey("stringTest1"));
            //保存
            redisUtil.set("stringTest1",jsonstr);
            //保存并设置过期时间
            //redisUtil.set("stringTest1",jsonstr,1000);
            //设置过期时间
            redisUtil.expire("stringTest1",1000);
            //获取过期时间
            System.out.println(redisUtil.getExpire("stringTest1"));
            return "success";
        }catch (Exception e){
            log.info(e.toString());
            return "error";
        }
    }

    /**
     * 删除缓存
     * @param key
     * @return
     */
    @RequestMapping("/del")
    public String del(String key){
        try {
            //删除
            redisUtil.del(key);
            return "success";
        }catch (Exception e){
            log.info(e.toString());
            return "error";
        }
    }

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Object selectList(){

        List<Member> list=memberService.selectmemberList();

        return list;
    }

    @GetMapping("selectMember")
    @ResponseBody
    public Object selectMember(int id){

        return memberService.selectOne(id);
    }
}