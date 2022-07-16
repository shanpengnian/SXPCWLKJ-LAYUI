package com.sxpcwlkj.common;

import java.util.Hashtable;
import java.util.List;

import lombok.Data;

@Data
public class Page {
	private int curPage = 1;           // 当前页
	private int showSize = 10;         // 每页多少行
	private int stateSize = 0;
	private int totalPage = 1;        // 共多少页
	private int totalRow;             // 共多少数据
	private List item;                //查询结果
	private String orderField;        // 排序字段
    private String orderDirection;    // 排序方式
    private Hashtable<String, String> condition; // 查询条件
    private Hashtable<String,String> selectCountSql;

	public static Page PageUtil(Page pagetwo, String selectSql, String countSql){
    	Hashtable<String,String> selectCountSql = new Hashtable<String,String>();
    	pagetwo.setStateSize((pagetwo.getCurPage()-1)*pagetwo.getShowSize());
    	String sql =selectSql + " limit "+pagetwo.getStateSize() + "," + pagetwo.getShowSize();
    	selectCountSql.put("selectsql", sql);
    	selectCountSql.put("countsql", countSql);
    	pagetwo.setSelectCountSql(selectCountSql);
    	if(pagetwo.getItem()!=null){
    		// 计算最大页码（总页码）
            int totalPage = pagetwo.getTotalRow() % pagetwo.getShowSize() == 0 ? pagetwo.getTotalRow() / pagetwo.getShowSize() : (pagetwo.getTotalRow() / pagetwo.getShowSize() + 1);
            pagetwo.setTotalPage(totalPage);
    	}
    	return pagetwo;
    }


	public static Page PageUtil(Page pagetwo){
		if(pagetwo.getItem()!=null){
			// 计算最大页码（总页码）
			int totalPage = pagetwo.getTotalRow() % pagetwo.getShowSize() == 0 ? pagetwo.getTotalRow() / pagetwo.getShowSize() : (pagetwo.getTotalRow() / pagetwo.getShowSize() + 1);
			pagetwo.setTotalPage(totalPage);
		}
		pagetwo.setSelectCountSql(null);   //处理sql 不在前台显示
		return pagetwo;
	}
}
