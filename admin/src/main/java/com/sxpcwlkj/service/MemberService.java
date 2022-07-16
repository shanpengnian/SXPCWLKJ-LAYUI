package com.sxpcwlkj.service;

import com.sxpcwlkj.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {

    List<Member> selectmemberList();

    Member selectOne(int id);
}
