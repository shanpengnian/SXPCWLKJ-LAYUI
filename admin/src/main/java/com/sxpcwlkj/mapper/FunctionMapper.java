package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.Function;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FunctionMapper extends BaseMapper<Function> {

    @Select("select  ff.*  from  `p_function`  ff  left  join  `p_role_fun`  rf  on  ff.`fun_id`=rf.`fun_id`  where  `role_id`=#{roleId}  ")
    List<Function>  listFunction(int  roleId);


    @Select("SELECT  ff.*  FROM  `p_function`  ff  LEFT  JOIN  `p_role_fun`  rf  ON  ff.`fun_id`=rf.`fun_id` LEFT JOIN  `p_user_role`  ur  ON  rf.`role_id`=ur.`role_id`  WHERE  ur.`user_id`=#{userId} ORDER BY ff.`sort` ")
    List<Function>  listFunctionForUserId(int  userId);
}
