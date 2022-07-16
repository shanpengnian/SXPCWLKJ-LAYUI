package com.sxpcwlkj.controller.admin;

import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.service.SmsService;
import com.sxpcwlkj.utils.EnumUtil;
import com.sxpcwlkj.utils.JsonResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Plugin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PluginController {

    @Autowired
    private SmsService smsService;


    /**
     * 验证码发送
     * @param phone
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("sendPhone")
    @AuthLoginAnnotation(authorityCode = "/Plugin/sendPhone")
    public JsonResultPage<Object> sendPhone(String phone, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        try {
            String code= smsService.querySmsInfo(phone);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("发送验证码["+code+"]成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 营销短信
     * @param phone
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("sendYinxiao")
    @AuthLoginAnnotation(authorityCode = "/Plugin/sendYinxiao")
    public JsonResultPage<Object> sendYinxiao(String phone, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        try {
            smsService.userSessionOffMsg(phone);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }
}
