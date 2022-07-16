package com.sxpcwlkj.controller.common;


import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommonController {

    @Autowired
    private RedisUtil redisUtil;

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
     * 本地服务 上传单图 图片
     *
     * @param headImg
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("fileUpload")
    @AuthLoginAnnotation(authorityCode = "/fileUpload")
    public JsonResultObject<Object> fileUpload(@RequestParam(value = "file", required = false) MultipartFile headImg, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();

        try {
            String path = DataUtil.getString(req.getParameter("path"));
            String filepath = "common";
            if (path != null && path.length() > 0) {
                filepath = path;
            }
            Map<String, String> mapimg = FileUtil.uploadImage(req, headImg, filepath);
            Map map = new HashMap();
            map.put("src", mapimg.get("sqlPate"));
            return JsonResultObject.getSuccessResult(map,"上传成功",1);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultObject.getErroeResult(e.getMessage());
        }

    }


    /**
     * 上传单图到阿里云存储
     *
     * @param file
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("fileUploadToQiniuyun")
    @AuthLoginAnnotation(authorityCode = "/fileUploadToQiniuyun")
    public JsonResultPage<Object> fileUploadToQiniuyun(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        try {
            String img = AlipayOSSUtil.uploadFile("", file);
            if (img != null && img.length() > 0) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("修改图像成功！");
                resultJson.setData(img);
                resultJson.setState(1);
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("图片上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }


    /**
     * 富文本 图片上传接口  阿里云OSS 存储
     *
     * @param file
     * @param req
     */
    @PostMapping("txtFileUpload")
    @AuthLoginAnnotation(authorityCode = "/txtFileUpload")
    public Map txtFileUpload(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        Map map = new HashMap();
        try {
            String img = AlipayOSSUtil.uploadFile("", file);
            if (img != null && img.length() > 0) {
                map.put("location", img);
                return map;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
