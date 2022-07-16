package com.sxpcwlkj.mapper;

import com.sxpcwlkj.entity.ApiEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.mapper
 * @date 2019/6/29
 */
@Mapper
@Repository
public interface ApiMapper {

	 /**
     * 查询Api列表
     * @param map
     * @return
     */
	@Select("${selectsql}")
    List<ApiEntity> queryApiPage(Map map);
    
    /**
     * 查询Api条数
     * @param map
     * @return
     */
	@Select("${countsql}")
    int queryApiCount(Map map);
	
	 /**
	 * 新增Api
     * @param
     * @return
     */
	@Insert("insert into p_api (user_id,api_name,api_url,api_site,api_key,api_one,api_two,api_three) values (#{userId},#{apiName},#{apiUrl},#{apiSite},#{apiKey},#{apiOne},#{apiTwo},#{apiThree} ) ")
    int insertApi(ApiEntity apiEntity);
	
	/**                                                  
	 * 修改Api
     * @param
     * @return
     */
	@Update("update p_api set api_name = #{apiName},api_url = #{apiUrl},api_site = #{apiSite},api_key = #{apiKey},api_one = #{apiOne},api_two = #{apiTwo},api_three = #{apiThree} where api_id = #{apiId}")
    int updateApiByApiId(ApiEntity apiEntity);
	
	/**
     * 根据ApiId修改查询
     * @param apiId
     * @return
     */
    @Select("SELECT * from  p_api  WHERE  api_id=#{apiId}")
    ApiEntity selectApiByApiId(int apiId);
    
    /**                                                  
	 * 删除
     * @param
     * @return
     */
	@Delete("delete from p_api where api_id = #{apiId}")
    int deleteApiByApiId(int apiId);

    /**
     * 根据id查询接口
     * @param apiId
     * @return
     */
    @Select("SELECT * from  p_api  WHERE  api_id=#{apiId}")
    ApiEntity getApi(int apiId);
    /**
     * 短信Api查询 根据模板ID
     * @param codeId
     * @return
     */
    @Select("SELECT * FROM `p_api`  WHERE `api_two`=#{codeId}")
    ApiEntity selectForCodeId(String codeId);
}
