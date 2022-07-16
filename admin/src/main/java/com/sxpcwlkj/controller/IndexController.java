package com.sxpcwlkj.controller;

import com.sxpcwlkj.utils.JsonResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@EnableConfigurationProperties
public class IndexController {

    /**
     * 启动页
     * @param map
     * @return
     */
    @GetMapping("/")
    @ResponseBody
    public Object start( Model map) {
        return JsonResultObject.getSuccessResult("系统启动成功啦！");
    }


}
