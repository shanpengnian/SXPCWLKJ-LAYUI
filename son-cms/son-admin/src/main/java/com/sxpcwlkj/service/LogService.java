package com.sxpcwlkj.service;

import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.entity.Log;

public interface LogService {

    void addLog(Log log);

    /**
     * 日志分页
     *
     * @param page
     * @return
     */
    Page queryLogPage(Page page) throws Exception;
}
