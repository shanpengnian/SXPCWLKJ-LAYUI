package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.SysMoney;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysMoneyMapper extends BaseMapper<SysMoney> {

    /**
     * 充值系统余额
     * @param isSysMoney
     */
    @Update("UPDATE `p_sys_money` SET `sys_pice`=#{sysPice},`sys_allpice`=#{sysAllpice} WHERE `sys_code`=#{sysCode}")
    void updateForSyscode(SysMoney isSysMoney);
}
