package com.sxpcwlkj.controller.admin;

import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.utils.DataUtil;
import com.sxpcwlkj.utils.DateUtil;
import com.sxpcwlkj.utils.EnumUtil;
import com.sxpcwlkj.utils.JsonResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Hashtable;

@RestController
@RequestMapping("/log")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LogController {
    @Autowired
    LogService logService;


    /**
     * 查询日志分页
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("queryLogPage")
    @AuthLoginAnnotation(authorityCode = "/log/queryLogPage")
    public JsonResultPage<Object> queryLogPage(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int currpage = DataUtil.getInt(req.getParameter("currpage"));
        String keyword = DataUtil.getString(req.getParameter("keyword"));
        int iscoll = DataUtil.getInt(req.getParameter("iscoll"));
        String selectTime = DataUtil.getString(req.getParameter("selectTime"));
        if (selectTime.length() == 0) {
            selectTime = DateUtil.getDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
        }
        Page page = new Page();
        page.setCurPage(currpage);
        page.setShowSize(DataUtil.getInt(EnumUtil.PageSize.PAGE_FIF_TEN.getValue()));
        Hashtable<String, String> condition = new Hashtable<String, String>();
        condition.put("keyword", keyword);
        condition.put("selectTime", selectTime);
        condition.put("iscoll", iscoll + "");
        page.setCondition(condition);
        try {
            page = logService.queryLogPage(page);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("查询信息成功！");
            resultJson.setData(page.getItem());
            resultJson.setCount(page.getTotalRow());    //总条数
            resultJson.setCurrpage(page.getCurPage());  //当前页码
            resultJson.setShowSize(page.getShowSize()); //每页显示数量
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
}
