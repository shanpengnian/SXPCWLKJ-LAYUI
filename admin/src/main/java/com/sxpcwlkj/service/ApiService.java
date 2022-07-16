package com.sxpcwlkj.service;


import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.entity.ApiEntity;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.mapper
 * @date 2019/5/10 0015
 */
public interface ApiService {
	
	/**
	 *Api分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	 Page queryApiPage(Page page);
	
	 /**
	 * 新增Api
     * @param
     * @return
     */
	 int insertApi(ApiEntity apiEntity);
	
	/**                                                  
	 * 修改Api
     * @param
     * @return
     */
	 int updateApiByApiId(ApiEntity apiEntity);
	
	/**
     * 根据ApiId修改查询
     * @param apiId
     * @return
     */
	 ApiEntity selectApiByApiId(int apiId);
    
    /**                                                  
	 * 删除
     * @param
     * @return
     */
	 int deleteApiByApiId(int apiId);

	/**
	 * 短信Api查询 根据模板ID
	 * @param codeId
	 * @return
	 */
	ApiEntity selectForCodeId(String codeId);
}
