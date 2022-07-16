package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sxpcwlkj.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//建议两个注解一起使用
@Mapper
@Repository
public interface TestEntityMapper  extends BaseMapper<TestEntity> {

    List<TestEntity> selectUser();

    IPage<TestEntity> selectPageVo(@Param("page") IPage<?> page, @Param("name") String name);
}
