package com.sxpcwlkj.exception;

import com.sxpcwlkj.entity.Log;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.utils.JsonResultObject;
import com.sxpcwlkj.utils.SysouUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.lhym.applet.config
 * @date 2020/11/2
 */
@ControllerAdvice
public class MyControllerAdvice {

    @Autowired
    private LogService logService;

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResultObject errorHandler(Exception ex, Model model) {
        SysouUtil.sysou(ex.getMessage());
        logService.addLog(new Log(3, "增,"+ex.getMessage()));
        return JsonResultObject.getErroeResult(ex.getMessage());
    }

}
