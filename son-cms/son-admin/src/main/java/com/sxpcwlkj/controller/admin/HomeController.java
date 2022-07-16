package com.sxpcwlkj.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.mapper.MemberMapper;
import com.sxpcwlkj.service.FatherService;
import com.sxpcwlkj.service.SysService;
import com.sxpcwlkj.utils.DateUtil;
import com.sxpcwlkj.utils.JsonResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {

    @Autowired
    private SysService sysService;

    @Autowired
    private FatherService fatherService;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询系统信息
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("selectSysinfo")
    @AuthLoginAnnotation(authorityCode = "/home/selectSysinfo")
    public JsonResultObject<Object> selectSysinfo(HttpServletRequest req, HttpServletResponse resp) {

        return JsonResultObject.getSuccessResult(sysService.getSys(), "查询配置");
    }

    /**
     * 查询系余额
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("selectSysMoney")
    @AuthLoginAnnotation(authorityCode = "/home/selectSysMoney")
    public JsonResultObject<Object> selectSysMoney(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultObject jsonResultObject = fatherService.selectMoney();
        return jsonResultObject;
    }

    /**
     * 查询会员一周内的新增分布图
     *
     * @return
     */
    @GetMapping("getUserAddNum")
    @AuthLoginAnnotation()
    public JsonResultObject getUserAddNum() {

        //获取当日的  星期一   至 星期日  日期
        Date oneDay = DateUtil.getFirstDayOfWeek(new Date());
        Date twoDay = DateUtil.getAddNumDay(oneDay, 1);
        Date threeDay = DateUtil.getAddNumDay(oneDay, 2);
        Date fourDay = DateUtil.getAddNumDay(oneDay, 3);
        Date fiveDay = DateUtil.getAddNumDay(oneDay, 4);
        Date sixDay = DateUtil.getAddNumDay(oneDay, 5);
        Date sevenDay = DateUtil.getAddNumDay(oneDay, 6);
        Date lastDay = DateUtil.getAddNumDay(oneDay, 7);

        Map<String, Object> datamap = new HashMap();

        String day[]=new String[7];
               day[0]=DateUtil.getDateToStr(oneDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(oneDay, DateUtil.DATE_FORMAT_DD);
               day[1]=DateUtil.getDateToStr(twoDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(twoDay, DateUtil.DATE_FORMAT_DD);
               day[2]=DateUtil.getDateToStr(oneDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(threeDay, DateUtil.DATE_FORMAT_DD);
               day[3]=DateUtil.getDateToStr(fourDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(fourDay, DateUtil.DATE_FORMAT_DD);
               day[4]=DateUtil.getDateToStr(fiveDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(fiveDay, DateUtil.DATE_FORMAT_DD);
               day[5]=DateUtil.getDateToStr(sixDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(sixDay, DateUtil.DATE_FORMAT_DD);
               day[6]=DateUtil.getDateToStr(sevenDay, DateUtil.DATE_FORMAT_MM) + "-" + DateUtil.getDateToStr(sevenDay, DateUtil.DATE_FORMAT_DD);

        datamap.put("day", day);

        int num[]=new int[7];
        num[0]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(oneDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        num[1]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(twoDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        num[2]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(threeDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        num[3]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(fourDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        num[4]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(fiveDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        num[5]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(sixDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        num[6]=memberMapper.selectCount(new QueryWrapper<Member>().likeRight("add_time", DateUtil.getDateToStr(sevenDay, DateUtil.DATE_FORMAT_YYYY_MM_DD)));


        datamap.put("num", num);
        return JsonResultObject.getSuccessResult(datamap);
    }

}
