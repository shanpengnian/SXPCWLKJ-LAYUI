package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.AlipayConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.mapper
 * @date 2019/9/6
 */
@Mapper
@Repository
public interface AlipayMapper extends BaseMapper<AlipayConfigEntity> {

    @Select("SELECT *  FROM `p_alipay_config` WHERE  id=1")
    AlipayConfigEntity selsetAlipay();

    @Select("SELECT *  FROM `p_alipay_config` WHERE  id=#{id}")
    AlipayConfigEntity selsetAlipayKeyId(int id);
}
