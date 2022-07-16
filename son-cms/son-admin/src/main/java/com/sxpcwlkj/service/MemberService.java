package com.sxpcwlkj.service;

import com.sxpcwlkj.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * @description member
 * @author xijue
 * @date 2021-04-22
 */
public interface MemberService {

    public List<Member> selectmemberList();


    public Member selectOne(int id);



}
