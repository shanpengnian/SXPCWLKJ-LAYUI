package com.sxpcwlkj.service;

import com.sxpcwlkj.common.Page;

import java.util.Map;

public interface ObjectEntityService {

    /**
     * 拦截器业务验证
     * @param map
     * @return
     */
    boolean isToken(Map<String,Object> map);

    /**
     * 列表分页
     * @param page
     * @return
     */
    Page queryLogPage(Page page);
}
