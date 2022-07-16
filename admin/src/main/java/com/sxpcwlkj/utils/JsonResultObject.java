package com.sxpcwlkj.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResultObject<T> {
	//返回 提示信息
	private String msg;

	//返回 JAAX是否成功编码
	private int code;

	//返回  状态
	private int state;

	//返回 类型
	//private int type;

	//返回 map 对象
	//private Map<String, Object> jsonMap;

	//返回  泛型对象
	private T data;

	public static JsonResultObject getSuccessResult(Object data){
		JsonResultObject jsonResultObject=new JsonResultObject();
		jsonResultObject.setCode(EnumUtil.Result.SUCCESS.getValue());
		jsonResultObject.setMsg("OK");
		jsonResultObject.setState(1);
		jsonResultObject.setData(data);
		return jsonResultObject;
	}

	public static JsonResultObject getSuccessResult(Object data,String msg){
		JsonResultObject jsonResultObject=new JsonResultObject();
		jsonResultObject.setCode(EnumUtil.Result.SUCCESS.getValue());
		jsonResultObject.setMsg(msg);
		jsonResultObject.setState(1);
		jsonResultObject.setData(data);
		return jsonResultObject;
	}
	public static JsonResultObject getSuccessResult(Object data,String msg,int state){
		JsonResultObject jsonResultObject=new JsonResultObject();
		jsonResultObject.setCode(EnumUtil.Result.SUCCESS.getValue());
		jsonResultObject.setMsg(msg);
		jsonResultObject.setState(state);
		jsonResultObject.setData(data);
		return jsonResultObject;
	}

	public static JsonResultObject getErroeResult(String msg){
		JsonResultObject jsonResultObject=new JsonResultObject();
		jsonResultObject.setCode(EnumUtil.Result.ERROR.getValue());
		jsonResultObject.setMsg("服务器异常");
		jsonResultObject.setState(-1);
		jsonResultObject.setData(null);
		return jsonResultObject;
	}

	public static JsonResultObject getFailureResult(String msg){
		JsonResultObject jsonResultObject=new JsonResultObject();
		jsonResultObject.setCode(EnumUtil.Result.FAILURE.getValue());
		jsonResultObject.setMsg(msg);
		jsonResultObject.setState(-1);
		jsonResultObject.setData(null);
		return jsonResultObject;
	}


}
