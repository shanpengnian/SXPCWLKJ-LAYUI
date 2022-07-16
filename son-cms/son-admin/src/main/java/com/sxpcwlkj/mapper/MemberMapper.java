package com.sxpcwlkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxpcwlkj.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Mapper
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 更新用户状态
     * @param memberState
     * @param memberId
     * @return
     */
    @Update("UPDATE `p_member` SET `member_state`=#{memberState}  WHERE  `member_id`=#{memberId}")
    int updateState(@Param("memberState") int memberState,@Param("memberId") long memberId);


}
