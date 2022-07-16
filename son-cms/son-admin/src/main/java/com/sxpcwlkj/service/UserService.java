package com.sxpcwlkj.service;


import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.entity.*;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    Map loginUser(User user);

    /**
     * 员工列表分页
     *
     * @param page
     * @return
     * @throws Exception
     */
    Page queryUserPage(Page page);

    /**
     * 角色列表分页
     *
     * @param page
     * @return
     * @throws Exception
     */
    Page queryRolePage(Page page);


    /**
     * 根绝角色编号查询角色信息
     *
     * @param roleId
     * @throws Exception
     */
    Role queryRoleByRoleId(int roleId);

    /**
     * 修改角色
     *
     * @param role
     * @throws Exception
     */
    void updateRoleByRoleId(Role role);

    /**
     * 删除角色
     *
     * @param roleId
     * @throws Exception
     */
    void deleteRoleByRoleId(int roleId);

    /**
     * 编辑角色权限
     *
     * @param funIds
     * @param roleId
     */
    int updateRoleFunction(int[] funIds, int roleId);

    /**
     * 根据角色编号查询权限
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    List<Function> queryFunctionByRoleId(int roleId);

    /**
     * 查询所有权限
     *
     * @return
     */
    List<Function> Allfunction();

    /**
     * 查询编码
     *
     * @return
     */
    List<Code> selsetRoleCode(int type);

    /**
     * 查询权限
     *
     * @return
     * @throws Exception
     */
    List<Function> queryFunctionAll();

    /**
     * 删除权限
     *
     * @param functionId
     * @throws Exception
     */
    int deleteFunctionByFunId(int functionId);

    /**
     * 用户修改基础资料
     *
     * @param user
     * @return
     * @throws Exception
     */
    int updateUserByUserInfo(User user);

    /**
     * 根据用户名查询用户
     */
    User selectUserForPhone(String userPhone);

    /**
     * 员工修改密码
     *
     * @param user
     */
    int forgetUserPassWord(User user);

    /**
     * 修改头像
     *
     * @param user
     */
    int updateUserImgByUserId(User user);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Map addUser(User user, int roleId);

    /**
     * 根据员工编号查询员工信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    User queryUserByUserId(int userId);

    /**
     * 重置密码
     *
     * @param user
     * @return
     * @throws Exception
     */
    int updateUserPassWord(User user);

    /**
     * 用户修改基础资料
     *
     * @param user
     * @return
     * @throws Exception
     */
    int updateUserByUserId(User user, int roleId);

    /**
     * 修改用户状态
     * @param userId
     * @return
     * @throws Exception
     */
    int updateUserType(int userId,int type);

    /**
     * 会员余额  增/减
     */
    int updateUserMony(int userId, BigDecimal pice, String desc);


}
