package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
