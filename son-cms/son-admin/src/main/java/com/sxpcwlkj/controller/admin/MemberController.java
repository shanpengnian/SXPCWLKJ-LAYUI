package com.sxpcwlkj.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.entity.TestEntity;
import com.sxpcwlkj.mapper.MemberMapper;
import com.sxpcwlkj.service.MemberService;
import com.sxpcwlkj.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author xijue
 * @description member
 * @date 2021-04-22
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/adminMember")
public class MemberController {

    @Autowired
    private MemberMapper memberMapper;


    /**
     * 用户列表分页
     *
     * @param currpage
     * @param showSize
     * @return
     */
    @GetMapping("selectPage")
    @AuthLoginAnnotation
    public JsonResultPage selectPage(@RequestParam(required = false, defaultValue = "1") int currpage,
                                     @RequestParam(required = false, defaultValue = "10") int showSize,
                                     HttpServletRequest request, HttpServletResponse response) {

        String objName = DataUtil.getString(request.getParameter("objName"));
        String objPhone = DataUtil.getString(request.getParameter("objPhone"));
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(objName)) {
            queryWrapper.eq("member_name", objName);
        }
        if (!StringUtils.isEmpty(objPhone)) {
            queryWrapper.eq("member_phone", objPhone);
        }
        IPage<Member> page = new Page<>(currpage, showSize);//参数一是当前页，参数二是每页个数
        memberMapper.selectPage(page, queryWrapper);

        return JsonResultPage.getSuccessResult(page);

    }


    /**
     * 查询用户
     *
     * @param memberId
     * @return
     */
    @GetMapping("selectById")
    @AuthLoginAnnotation
    public JsonResultObject selectmemberGetId(long memberId) {
        Member member = memberMapper.selectById(memberId);
        if (member == null) {
            return JsonResultObject.getFailureResult();
        }
        return JsonResultObject.getSuccessResult(member);
    }


    /**
     * 更新用户
     *
     * @param member
     * @return
     */
    @PostMapping("updateById")
    @AuthLoginAnnotation
    public JsonResultObject updateById(Member member) {

        Member isMember = memberMapper.selectById(member.getMemberId());

        if (isMember == null) {
            return JsonResultObject.getFailureResult("该用户不存在");
        }

        memberMapper.updateById(member);
        return JsonResultObject.getSuccessResult(null, "已更新");

    }


    /**
     * 新增用户
     *
     * @param member
     * @return
     */
    @GetMapping("insertMember")
    @AuthLoginAnnotation
    public JsonResultObject insertMember(Member member) {

        if (!DataUtil.getIsEnglish(member.getMemberAccount())) {
            return JsonResultObject.getFailureResult("账号不能有汉字");
        }

        Member isMember = memberMapper.selectOne(new QueryWrapper<Member>().eq("member_phone", member.getMemberPhone()).or().eq("member_account", member.getMemberAccount()));

        if (isMember != null) {
            return JsonResultObject.getFailureResult("账号或手机号已存在");
        }
        member.setAddTime(new Date());
        member.setUpdateTime(new Date());
        member.setMemberPassword(Md5Util.getPassword("123456"));
        memberMapper.insert(member);
        return JsonResultObject.getSuccessResult(null, "添加成功");

    }

    /**
     * 删除用户
     *
     * @param memberId
     * @return
     */
    @GetMapping("deleteById")
    @AuthLoginAnnotation
    public JsonResultObject deleteById(long memberId) {

        Member isMember = memberMapper.selectById(memberId);

        if (isMember == null) {
            return JsonResultObject.getFailureResult("该用户不存在");
        }
        memberMapper.deleteById(memberId);
        return JsonResultObject.getSuccessResult(null, "已删除");

    }

    /**
     * 启用/禁用用户
     *
     * @param memberId
     * @return
     */
    @PostMapping("updateState")
    @AuthLoginAnnotation
    public JsonResultObject updateState(int type, @RequestParam(value = "memberId[]") int[] memberId) {

        if (memberId.length == 0) {
            return JsonResultObject.getFailureResult("请选择数据再操作");
        }
        for (int i = 0; i < memberId.length; i++) {
            if (type == 1) {
                memberMapper.updateState(1, memberId[i]);
            } else {
                memberMapper.updateState(2, memberId[i]);
            }

        }
        return JsonResultObject.getSuccessResult(null, "已更新");

    }


}