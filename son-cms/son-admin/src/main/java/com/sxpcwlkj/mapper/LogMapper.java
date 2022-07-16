package com.sxpcwlkj.mapper;

import com.sxpcwlkj.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface LogMapper {

    /**
     * 添加日志
     *
     * @param log
     */
    @Insert("INSERT INTO `p_log` (`log_type`,`log_desc`,`log_time`,`user_id`,`log_level`,`log_ip`,is_collect) VALUE(#{logType},#{logDesc},#{logTime},#{userId},#{logLevel},#{logIp},#{isCollect})")
    void addLog(Log log);

    @Select("${selectsql}")
    List<Log> queryLogPage(Map map);

    @Select("${countsql}")
    int queryLogCount(Map map);
}
