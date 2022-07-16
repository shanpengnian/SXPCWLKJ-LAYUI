package com.sxpcwlkj.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.controller.common.CommonController;
import com.sxpcwlkj.entity.EmaiConfig;
import com.sxpcwlkj.entity.Log;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.mapper.EmaiConfigMapper;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmailController extends CommonController{

    @Autowired
    private EmaiConfigMapper emaiConfigMapper;

    @Autowired
    private LogService logService;




    @GetMapping("selectEmail")
    @AuthLoginAnnotation(authorityCode = "/email/selectEmail")
    public JsonResultObject<Object> selectEmail(HttpServletRequest req, HttpServletResponse resp) {

        User user = this.getUser();
        EmaiConfig isEmail = emaiConfigMapper.selectOne(new QueryWrapper<EmaiConfig>().eq("user_id", user.getUserId()));

        return JsonResultObject.getSuccessResult(isEmail,"查询配置");
    }



    /**
     * 邮件配置
     *
     * @param emaiConfig
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("updateEmail")
    @AuthLoginAnnotation(authorityCode = "/email/updateEmail")
    public JsonResultPage<Object> updateEmail(EmaiConfig emaiConfig, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> result = new JsonResultPage();
        User user = this.getUser();
        emaiConfig.setUserId(user.getUserId());
        EmaiConfig isEmail = emaiConfigMapper.selectOne(new QueryWrapper<EmaiConfig>().eq("user_id", user.getUserId()));
        if (isEmail == null) {
            emaiConfigMapper.insert(emaiConfig);
        } else {
            emaiConfig.setEmailId(isEmail.getEmailId());
            emaiConfigMapper.updateById(emaiConfig);
        }
        logService.addLog(new Log("增,配置邮箱配置"));
        result.setMsg("保存成功");
        result.setCode(EnumUtil.Result.SUCCESS.getValue());
        return result;
    }

    /**
     * 发送邮件
     *
     * @param content
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("sendEmail")
    @AuthLoginAnnotation(authorityCode = "/email/sendEmail")
    public JsonResultPage<Object> sendEmail(String content, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> result = new JsonResultPage();
        User user = this.getUser();
        EmaiConfig isEmail = emaiConfigMapper.selectOne(new QueryWrapper<EmaiConfig>().eq("user_id", user.getUserId()));
        if (isEmail == null) {
            result.setMsg("发送失败");
            result.setCode(EnumUtil.Result.SUCCESS.getValue());
        } else {
            String mail = DataUtil.getString(req.getParameter("receptionEmail")); //收件邮箱
            String title = DataUtil.getString(req.getParameter("subject"));       //邮件标题
            isEmail.setReceptionEmail(mail);         //发送对象的邮箱
            isEmail.setSubject(title);               //发送邮件标题
            isEmail.setContent(content);             //发送内容
            try {
                //MailSendUtil.sendTextMail(isEmail);
                MailSendUtil.sendHtmlMail(isEmail);
                result.setState(1);
                result.setMsg("发送成功");
                result.setCode(EnumUtil.Result.SUCCESS.getValue());
                logService.addLog(new Log("增,发送邮件["+title+"]成功"));
            } catch (AuthenticationFailedException e) {
                //e.printStackTrace();
                result.setState(-1);
                result.setMsg("请检查:账号与密码授权失败");
                result.setCode(EnumUtil.Result.SUCCESS.getValue());
                logService.addLog(new Log(3, "增,发送邮件["+title+"]报错" + e));
            } catch (MessagingException e) {
                //e.printStackTrace();
                result.setState(-1);
                result.setMsg("请检查:邮箱发送协议/端口");
                result.setCode(EnumUtil.Result.SUCCESS.getValue());
                logService.addLog(new Log(3, "增,发送邮件["+title+"]报错" + e));
            } catch (Exception e) {
                //e.printStackTrace();
                result.setState(-1);
                result.setMsg(DataUtil.getString(e));
                result.setCode(EnumUtil.Result.FAILURE.getValue());
                logService.addLog(new Log(3, "增,["+title+"]发送邮件报错" + e));
            }
        }

        return result;
    }

}
