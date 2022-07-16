package com.sxpcwlkj.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.controller.common.CommonController;
import com.sxpcwlkj.entity.AlipayConfigEntity;
import com.sxpcwlkj.entity.ApiEntity;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.entity.WxPayConfig;
import com.sxpcwlkj.mapper.AlipayMapper;
import com.sxpcwlkj.mapper.WxPayConfigMapper;
import com.sxpcwlkj.service.ApiService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.controller.admin
 * @date 2019/7/4
 * API管理
 */
@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiController extends CommonController {
	
    @Autowired
    private ApiService apiService;
	@Autowired
	private AlipayMapper alipayMapper;
	@Autowired
	private WxPayConfigMapper wxPayConfigMapper;
    /**
   	 * api列表
   	 * @param req
   	 * @param resp
   	 * @return
   	 */


   	@GetMapping("queryApiPage")
	@AuthLoginAnnotation(authorityCode = "/api/queryApiPage")
   	public JsonResultPage<Object> queryApiPage(HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
   		int currpage = DataUtil.getInt(req.getParameter("currpage"));
   		String keyword = DataUtil.getString(req.getParameter("keyword"));
   		Page page = new Page();
   		page.setCurPage(currpage);
   		page.setShowSize(DataUtil.getInt(EnumUtil.PageSize.PAGE_FIF_TEN.getValue()));
   		Hashtable<String,String> condition= new Hashtable<String,String>();
   		condition.put("keyword", keyword);
   		page.setCondition(condition);
   		try {
   			page = apiService.queryApiPage(page);
   			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
   			resultJson.setMsg("查询api信息成功！");
   			page.setSelectCountSql(null);
   			resultJson.setData(page.getItem());
			resultJson.setCount(page.getTotalRow());    //总条数
			resultJson.setCurrpage(page.getCurPage());  //当前页码
			resultJson.setShowSize(page.getShowSize()); //每页显示数量
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		return resultJson;
   	}
    
    /**
   	 * 新增api
   	 * @param req
   	 * @param resp
   	 * @return
   	 */
   	@PostMapping("insertApi")
	@AuthLoginAnnotation(authorityCode = "/api/insertApi")
   	public JsonResultPage<Object> insertNavigation(ApiEntity api, HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
   		User user=this.getUser();
   		api.setUserId(user.getUserId());
   		try {
   			int i = apiService.insertApi(api);
   			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
   			resultJson.setMsg("新增api成功！");
   		} catch (Exception e) {
   			e.printStackTrace();
   			resultJson.setCode(EnumUtil.Result.ERROR.getValue());
   		}
   		return resultJson;
   	}
    
    /**
   	 * 修改查询 回显
   	 * @param req
   	 * @param resp
   	 * @return
   	 */
   	@GetMapping("selectApiByApiId")
	@AuthLoginAnnotation(authorityCode = "/api/selectApiByApiId")
   	public JsonResultPage<Object> selectApiByApiId(HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
   		int apiId = DataUtil.getInt(req.getParameter("apiId"));
   		try {
   			ApiEntity apiEntity = apiService.selectApiByApiId(apiId);
   			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
   			resultJson.setMsg("查询成功！");
   			resultJson.setData(apiEntity);
   		} catch (Exception e) {
   			e.printStackTrace();
   			resultJson.setCode(EnumUtil.Result.ERROR.getValue());
   		}
   		return resultJson;
   	}
    
    /**
   	 * 修改api
   	 * @param req
   	 * @param resp
   	 * @return
   	 */
   	@PostMapping("updateApiByApiId")
	@AuthLoginAnnotation(authorityCode = "/api/updateApiByApiId")
   	public JsonResultPage<Object> updateApiByApiId(ApiEntity api, HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
   		try {
   			int i = apiService.updateApiByApiId(api);
   			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
   			resultJson.setMsg("修改api成功！");
   		} catch (Exception e) {
   			e.printStackTrace();
   			resultJson.setCode(EnumUtil.Result.ERROR.getValue());
   		}
   		return resultJson;
   	}
    
    /**
   	 * 删除api
   	 * @param req
   	 * @param resp
   	 * @return
   	 */
   	@GetMapping("deleteApiByApiId")
	@AuthLoginAnnotation(authorityCode = "/api/deleteApiByApiId")
   	public JsonResultPage<Object> deleteApiByApiId(HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
   		int apiId = DataUtil.getInt(req.getParameter("apiId"));
   		try {
   			int i = apiService.deleteApiByApiId(apiId);
   			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
   			resultJson.setMsg("删除成功！");
   		} catch (Exception e) {
   			e.printStackTrace();
   			resultJson.setCode(EnumUtil.Result.ERROR.getValue());
   		}
   		return resultJson;
   	}

	/**
	 * 支付宝接口更新配置
	 * @param req
	 * @param resp
	 * @return
	 */
	@PostMapping("setAlipay")
	@AuthLoginAnnotation(authorityCode = "/api/setAlipay")
	public JsonResultPage<Object> setAlipay(AlipayConfigEntity alipayConfigEntity, HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
		try {
			User user=this.getUser();
			AlipayConfigEntity isalipayConfigEntity = alipayMapper.selsetAlipayKeyId(user.getUserId());
			alipayConfigEntity.setUserId(isalipayConfigEntity.getUserId());
			alipayConfigEntity.setId(isalipayConfigEntity.getId());
			alipayMapper.updateById(alipayConfigEntity);
			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
			resultJson.setMsg("设置成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.setCode(EnumUtil.Result.ERROR.getValue());
		}
		return resultJson;
	}

	/**
	 * 查询支付宝配置
	 * @param req
	 * @param resp
	 * @return
	 */
	@GetMapping("getAlipay")
	@AuthLoginAnnotation(authorityCode = "/api/getAlipay")
	public JsonResultObject<Object> getAlipay(HttpServletRequest req, HttpServletResponse resp) {
		try {
			User user=this.getUser();
			AlipayConfigEntity isalipayConfigEntity = alipayMapper.selsetAlipayKeyId(user.getUserId());
			return JsonResultObject.getSuccessResult(isalipayConfigEntity,"查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultObject.getErroeResult(e.getMessage());
		}

	}

	/**
	 * 更新微信支付接口
	 * @param req
	 * @param resp
	 * @return
	 */
	@PostMapping("setwxPayConfig")
	@AuthLoginAnnotation(authorityCode = "/api/setwxPayConfig")
	public JsonResultPage<Object> setwxPayConfig(WxPayConfig wxPayConfig, HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
		try {
			User user=this.getUser();
			WxPayConfig iswxPayConfig = wxPayConfigMapper.selectOne(new QueryWrapper<WxPayConfig>().eq("user_id",user.getUserId()));
			wxPayConfig.setUserId(iswxPayConfig.getUserId());
			wxPayConfig.setWxId(iswxPayConfig.getWxId());
			wxPayConfigMapper.updateById(wxPayConfig);
			resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
			resultJson.setMsg("设置成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.setCode(EnumUtil.Result.ERROR.getValue());
		}
		return resultJson;
	}

	/**
	 * 查询微信支付配置
	 * @param req
	 * @param resp
	 * @return
	 */
	@GetMapping("getwxPayConfig")
	@AuthLoginAnnotation(authorityCode = "/api/getwxPayConfig")
	public JsonResultObject<Object> getwxPayConfig(HttpServletRequest req, HttpServletResponse resp) {
		JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
		try {
			User user=this.getUser();
			WxPayConfig iswxPayConfig = wxPayConfigMapper.selectOne(new QueryWrapper<WxPayConfig>().eq("user_id",user.getUserId()));
			return JsonResultObject.getSuccessResult(iswxPayConfig,"查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultObject.getErroeResult(e.getMessage());
		}

	}


}
