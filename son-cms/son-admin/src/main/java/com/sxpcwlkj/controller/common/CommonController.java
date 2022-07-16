package com.sxpcwlkj.controller.common;


import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.service.FatherService;
import com.sxpcwlkj.utils.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommonController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FatherService fatherService;


    public User getUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = DataUtil.getString(request.getHeader("token"));
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("非法请求");
        }
        Map<String, Object> getMap = TokenUtil.getpareJwt(token);
        return redisUtil.getUserKey(DataUtil.getString(getMap.get("id")));
    }

    /**
     * 上传单图到阿里云存储
     * @param file
     * @param req
     * @param resp
     * @return
     */
    @AuthLoginAnnotation
    @PostMapping("fileUploadToQiniuyun")
    @ResponseBody
    public JsonResultObject<Object> fileUploadToQiniuyun(@RequestParam(value = "file",required = false)MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {

        try {
            File files=MultipartFileToFileUtils.multipartFileToFile(file);
            JsonResultObject jsonResultObject=fatherService.uploadFile(files);
            //MultipartFileToFileUtils.delteTempFile(files);
            String img=jsonResultObject.getData().toString();
            // AlipayOSSUtil.uploadFile("",file);
            if(img!=null&&img.length()>0) {
               return JsonResultObject.getSuccessResult(img,"操作成功",1);
            }else {
               return JsonResultObject.getFailureResult("失败了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }


}
