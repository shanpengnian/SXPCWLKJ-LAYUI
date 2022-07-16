package com.sxpcwlkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.mapper.MemberMapper;
import com.sxpcwlkj.mapper.UserMapper;
import com.sxpcwlkj.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberMapper  memberMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Member getUserByName(String getMapByName) {
        return memberMapper.selectOne(new QueryWrapper<Member>().eq("name",getMapByName));
    }

    @Override
    public User getUserById(long userId) {
        return userMapper.selectById(userId);
    }
}
