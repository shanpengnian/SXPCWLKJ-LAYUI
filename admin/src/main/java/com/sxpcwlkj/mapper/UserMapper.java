package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sxpcwlkj.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

//建议两个注解一起使用
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询员工列表
     *
     * @param map
     * @return
     */
    List<User> queryUserPage(Map map);

    /**
     * 查询员工总条数
     *
     * @param map
     * @return
     */
    int queryUserCount(Map map);

    /**
     * 根据用户Id查询角色
     *
     * @param userId
     * @return
     */
    @Select("SELECT  r.* FROM  `p_user_role` p, `p_role` r  WHERE p.`user_id` =#{userId}  AND p.`role_id` = r.`role_id`")
    List<Role> squeryRole(int userId);

    /**
     * 查询权限列表
     *
     * @param map
     * @return
     */
    List<Role> queryRolePage(Map map);

    /**
     * 查询权限总条数
     *
     * @param map
     * @return
     */
    int queryRoleCount(Map map);

    /**
     * 添加角色
     *
     * @param role
     * @throws Exception
     */
    int addRole(Role role);

    /**
     * 根据角色编码查询角色编号
     *
     * @param roleCode
     * @return
     */
    @Select("SELECT r.`role_id` FROM `p_role` r INNER JOIN `p_code` rc ON rc.`code_id` = r.`code_id` WHERE rc.`task_code`  = #{roleCode}")
    int queryRoleCodeIdByCodeName(String roleCode);

    /**
     * 根绝角色编号查询角色信息
     *
     * @param roleId
     * @throws Exception
     */
    Role queryRoleByRoleId(int roleId);

    @Select("SELECT * FROM `p_role` r LEFT JOIN `p_code` c ON r.`code_id`=c.`code_id`  WHERE r.`role_id`=#{roleId}")
    Role queryRoleByRoleIdAll(int roleId);

    /**
     * 修改角色
     *
     * @param role
     * @throws Exception
     */
    int updateRoleByRoleId(Role role);

    /**
     * 删除角色
     *
     * @param roleId
     * @throws Exception
     */
    int deleteRoleByRoleId(int roleId);

    /**
     * @param
     * @throws Exception
     */
    @Delete("DELETE FROM p_role_fun WHERE role_id = #{roleId}")
    int deleteRoleFunRoleByRoleId(int roleId);

    /**
     * @param
     * @throws Exception 员工和角色关系表
     */
    @Delete("DELETE FROM p_user_role WHERE role_id = #{roleId}")
    int deletepUserRoleByRoleId(int roleId);

    /**
     * @param userId
     * @throws Exception
     */
    @Delete("DELETE FROM p_user_role WHERE user_id = #{userId}")
    int deleteUserRoleById(int userId);


    /**
     * 根据角色编号删除角色和权限的关系
     */
    int deleteRoleFunctionByRoleId(int roleId);

    /**
     * 修改角色权限
     *
     * @param funId
     * @param roleId
     */
    int addRoleFunction(@Param("roleId") int roleId, @Param("funId") int funId);

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
    @Select("SELECT *  FROM  `p_function` ORDER BY sort")
    List<Function> Allfunction();

    /**
     * 查询编码
     *
     * @return
     */
    @Select("SELECT *  FROM  `p_code`  WHERE  code_type=#{type}")
    List<Code> selsetRoleCode(int type);

    /**
     * 查询总权限
     *
     * @return
     * @throws Exception
     */
    List<Function> queryFunctionAll();

    /**
     * 根据权限编号查询子权限
     *
     * @param funId
     * @return
     */
    List<Function> queryFunctionByFatherIdGetSonFun(int funId);

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    User queryUserName(String username);

    /**
     * 根据手机号查询员工
     *
     * @param userPhone
     * @return
     */
    User queryUserPhone(String userPhone);

    /**
     * 员工授权
     *
     * @param
     * @throws Exception
     */
    int updateAuthorizationUser(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 用户修改基础资料
     *
     * @param user
     * @return
     * @throws Exception
     */
    int updateUserByUserInfo(User user);

    /**
     * 修改员工密码
     *
     * @param user
     * @return
     */
    int forgetUserPassWord(User user);

    /**
     * 修改头像
     *
     * @param user
     */
    int updateUserImgByUserId(User user);


    /**
     * 添加用户余额表
     *
     * @param userMoney
     * @return
     */
    @Insert("INSERT INTO `p_user_money`(`user_id`,`add_time`,`available_money`,`total_money`,`has_been_money`)  VALUE(#{userId},#{addTime},0,0,0)")
    int addUserMoney(UserMoney userMoney);

    /**
     * 根据员工编号查询员工信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    User queryUserByUserId(int userId);

    /**
     * 删除用户角色
     *
     * @return
     */
    @Delete("DELETE FROM `p_user_role` WHERE `user_id`= #{userId}")
    int delUserRole(int userId);

    /**
     * 新增用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Insert("INSERT `p_user_role`   (`user_id`,`role_id`)VALUES(#{userId},#{roleId})")
    int addUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    @Update("update p_user set user_password = #{userPassword} where user_id = #{userId}")
    int updateUserPassWord(User user);

    /**
     * 更新角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Update("UPDATE `p_user_role`  SET `role_id`=#{roleId}  WHERE `user_id`=#{userId}")
    int updateUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 修改用户状态
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Update("UPDATE  `p_user`  SET `user_check`=#{type} WHERE `user_id`=#{userId}")
    int updateUserType(@Param("userId") int userId, @Param("type") int type);

    /**
     * 查询会员余额表
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM `p_user_money`  WHERE  `user_id`=#{userId}")
    UserMoney getuserMony(int userId);

    /**
     * 修改会员余额
     * 可用余额和  总余额都发生变化
     * pic  可以是  正数   负数
     */
    @Update("UPDATE  `p_user_money`  SET `available_money`=`available_money`+(#{pic}),`total_money`=`total_money`+(#{pic})  WHERE  `user_id`=#{userId}")
    int upadetUserMony(@Param("userId") int userId, @Param("pic") BigDecimal pic);

    /**
     * 新增余额表
     */
    @Insert("INSERT  INTO `p_user_money` (`user_id`,`available_money`,`total_money`,`add_time`)VALUES(#{userId},#{availableMoney},#{totalMoney},#{addTime})")
    int addUserMony(UserMoney userMoney);

    @Insert("INSERT  INTO `p_user_water_money` (`user_id`,`user_water_money`,`user_water_type`,`user_water_time`,`user_water_ip`,`user_water_desc`)VALUES(#{userId},#{userWaterMoney},#{userWaterType},#{userWaterTime},#{userWaterIp},#{userWaterDesc})")
    int addUserMonyWater(UserWaterMoney userWaterMoney);
}
