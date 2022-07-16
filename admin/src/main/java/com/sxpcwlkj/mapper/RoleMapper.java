package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.Role;
import com.sxpcwlkj.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT  r.* ,c.`code_code` AS codeCode FROM  `p_user_role`  ur LEFT  JOIN `p_role` r ON r.`role_id`=ur.`role_id` LEFT  JOIN  `p_code` c  ON  c.`code_id`=r.`code_id`  WHERE ur.`user_id`=#{userId}")
    List<Role> listUserRole(int userId);

    @Select("SELECT  *  FROM  `p_user_role`  ur  LEFT  JOIN  `p_role` r  ON  ur.`role_id`=r.`role_id`  WHERE  ur.`user_id`=#{userId}  ORDER BY  r.`role_rank` DESC")
    List<Role> lestUserRoleOne(int userId);
}
