package com.sxpcwlkj.service.impl;

import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.mapper.ObjectEntityMapper;
import com.sxpcwlkj.service.ObjectEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ObjectEntityServiceImpl implements ObjectEntityService {

    @Autowired
    private ObjectEntityMapper objectEntityMapper;



    @Override
    public boolean isToken(Map<String, Object> map) {
        return true;
    }

    @Override
    public Page queryLogPage(Page page) {
        StringBuffer countSql = new StringBuffer();
        countSql.append("SELECT count(0) FROM `p_object`  WHERE  1 ");
        StringBuffer selectSql = new StringBuffer();
        selectSql.append("SELECT * FROM `p_object`  WHERE 1  ");

        selectSql.append(" ORDER BY `sort` ASC ");
        page = Page.PageUtil(page, selectSql.toString(), countSql.toString());                        //调用分页工具类
        page.setItem(objectEntityMapper.queryLogPage(page.getSelectCountSql()));        //查询信息
        page.setTotalRow(objectEntityMapper.queryLogCount(page.getSelectCountSql()));    //查询总条数
        return Page.PageUtil(page);
    }
}
