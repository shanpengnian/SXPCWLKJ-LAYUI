package com.sxpcwlkj.service.impl;

import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.entity.ApiEntity;
import com.sxpcwlkj.mapper.ApiMapper;
import com.sxpcwlkj.service.ApiService;
import com.sxpcwlkj.utils.DataUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.service.impl
 * @date 2019/3/15 0015
 */

@Service
public class ApiServiceImpl implements ApiService {


    @Resource
    private ApiMapper apiMapper;
    
    /**
	 *Api分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
    @Override
	public Page queryApiPage(Page page){
		StringBuffer countSql = new StringBuffer();
		countSql.append("SELECT count(0) FROM `p_api` b LEFT JOIN p_user u ON u.`user_id`=b.`user_id` WHERE 1=1");
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("SELECT b.*,u.`user_name`,u.`user_phone`,u.`user_email` FROM `p_api` b LEFT JOIN p_user u ON u.`user_id`=b.`user_id` WHERE 1=1");
		String keyword = DataUtil.getString(page.getCondition().get("keyword"));
		if(""!=keyword) {
			countSql.append(" and (b.api_key like CONVERT( '%"+keyword+"%', CHAR(50))  or b.api_name like CONVERT( '%"+keyword+"%', CHAR(50))  or b.api_url like CONVERT( '%"+keyword+"%', CHAR(50)) )");
			selectSql.append(" and (b.api_key like CONVERT( '%"+keyword+"%', CHAR(50))  or b.api_name like CONVERT( '%"+keyword+"%', CHAR(50))  or b.api_url like CONVERT( '%"+keyword+"%', CHAR(50)) )");
		}
		page = Page.PageUtil(page, selectSql.toString(), countSql.toString());						//调用分页工具类
		page.setItem(apiMapper.queryApiPage(page.getSelectCountSql()));		//查询信息
		page.setTotalRow(apiMapper.queryApiCount(page.getSelectCountSql()));	//查询总条数
    	return Page.PageUtil(page);
	}

    /**
	 * 新增Api
     * @param
     * @return
     */
	@Override
	public int insertApi(ApiEntity apiEntity) {
		return apiMapper.insertApi(apiEntity);
	}

	/**                                                  
	 * 修改Api
     * @param
     * @return
     */
	@Override
	public int updateApiByApiId(ApiEntity apiEntity) {

		return apiMapper.updateApiByApiId(apiEntity);
	}

	/**
     * 根据ApiId修改查询
     * @param apiId
     * @return
     */
	@Override
	public ApiEntity selectApiByApiId(int apiId){

		return apiMapper.selectApiByApiId(apiId);
	}

	/**                                                  
	 * 删除
     * @param
     * @return
     */
	@Override
	public int deleteApiByApiId(int apiId){

		return apiMapper.deleteApiByApiId(apiId);
	}

	@Override
	public ApiEntity selectForCodeId(String codeId) {
		return apiMapper.selectForCodeId(codeId);
	}


}
