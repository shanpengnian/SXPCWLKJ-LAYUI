package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.Log;
import com.sxpcwlkj.entity.ObjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ObjectEntityMapper  extends BaseMapper<ObjectEntity> {
    @Select("${selectsql}")
    List<ObjectEntity> queryLogPage(Map map);

    @Select("${countsql}")
    int queryLogCount(Map map);
}
