package com.sxpcwlkj.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class JsonResultPage<T> {
	//数据状态的字段名称
	private int code;

	//状态二级区分
	private int state;

	//状态信息的字段名称
	private String msg;

	//返回  数据总条数
	private long count;

	//分页 当前页码
	private long currpage=1;

	//每页显示
	private long showSize=20;

	//数据详情的字段名称
	private T data;


	public static JsonResultPage getSuccessResult(IPage page){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.SUCCESS.getValue());
		jsonResultPage.setMsg("OK");
		jsonResultPage.setState(1);
		jsonResultPage.setCount(page.getTotal());
		jsonResultPage.setCurrpage(page.getCurrent());
		jsonResultPage.setShowSize(page.getSize());
		jsonResultPage.setData(page.getRecords());

		return  jsonResultPage;

	}

	public static JsonResultPage getSuccessResult(IPage page,String msg){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.SUCCESS.getValue());
		jsonResultPage.setMsg(msg);
		jsonResultPage.setState(1);
		jsonResultPage.setCount(page.getTotal());
		jsonResultPage.setCurrpage(page.getCurrent());
		jsonResultPage.setShowSize(page.getSize());
		jsonResultPage.setData(page.getRecords());

		return  jsonResultPage;

	}

	public static JsonResultPage getSuccessResult(IPage page,String msg,int state){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.SUCCESS.getValue());
		jsonResultPage.setMsg(msg);
		jsonResultPage.setState(state);
		jsonResultPage.setCount(page.getTotal());
		jsonResultPage.setCurrpage(page.getCurrent());
		jsonResultPage.setShowSize(page.getSize());
		jsonResultPage.setData(page.getRecords());

		return  jsonResultPage;

	}

	public static JsonResultPage getErroeResult(){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.ERROR.getValue());
		jsonResultPage.setMsg("服务器异常");
		jsonResultPage.setState(-1);
		jsonResultPage.setCount(0);
		jsonResultPage.setCurrpage(0);
		jsonResultPage.setShowSize(0);
		jsonResultPage.setData(null);

		return  jsonResultPage;

	}

	public static JsonResultPage getErroeResult(String msg){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.ERROR.getValue());
		jsonResultPage.setMsg(msg);
		jsonResultPage.setState(-1);
		jsonResultPage.setCount(0);
		jsonResultPage.setCurrpage(0);
		jsonResultPage.setShowSize(0);
		jsonResultPage.setData(null);

		return  jsonResultPage;

	}

	public static JsonResultPage getFailureResult(){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.FAILURE.getValue());
		jsonResultPage.setMsg("稍后再试");
		jsonResultPage.setState(1);
		jsonResultPage.setCount(0);
		jsonResultPage.setCurrpage(0);
		jsonResultPage.setShowSize(0);
		jsonResultPage.setData(null);

		return  jsonResultPage;

	}

	public static JsonResultPage getFailureResult(String msg){
		JsonResultPage jsonResultPage=new JsonResultPage();
		jsonResultPage.setCode(EnumUtil.Result.FAILURE.getValue());
		jsonResultPage.setMsg(msg);
		jsonResultPage.setState(1);
		jsonResultPage.setCount(0);
		jsonResultPage.setCurrpage(0);
		jsonResultPage.setShowSize(0);
		jsonResultPage.setData(null);

		return  jsonResultPage;

	}

}
