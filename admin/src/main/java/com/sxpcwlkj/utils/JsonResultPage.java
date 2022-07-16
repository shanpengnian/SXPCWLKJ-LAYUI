package com.sxpcwlkj.utils;

import lombok.Getter;
import lombok.Setter;

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
	private int count;

	//分页 当前页码
	private int currpage=1;

	//每页显示
	private int showSize=20;

	//数据详情的字段名称
	private T data;


}
