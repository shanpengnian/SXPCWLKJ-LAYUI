package com.sxpcwlkj.controller.admin;

import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.service.SysService;
import com.sxpcwlkj.utils.JsonResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {

    @Autowired
    private SysService sysService;


    @GetMapping("selectSysinfo")
    @AuthLoginAnnotation(authorityCode = "/home/selectSysinfo")
    public JsonResultObject<Object> selectSysinfo(HttpServletRequest req, HttpServletResponse resp) {

        return JsonResultObject.getSuccessResult(sysService.getSys(), "查询配置");
    }

    @GetMapping("selectSysMoney")
    @AuthLoginAnnotation(authorityCode = "/home/selectSysMoney")
    public JsonResultObject<Object> selectSysMoney(HttpServletRequest req, HttpServletResponse resp) {

        return JsonResultObject.getSuccessResult(sysService.getSys(), "查询配置");
    }

}
