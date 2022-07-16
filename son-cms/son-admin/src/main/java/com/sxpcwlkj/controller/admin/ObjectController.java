package com.sxpcwlkj.controller.admin;

import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.entity.ObjectEntity;
import com.sxpcwlkj.mapper.ObjectEntityMapper;
import com.sxpcwlkj.service.ObjectEntityService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@RestController
@RequestMapping("/obj")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ObjectController {

    @Autowired
    private ObjectEntityService objectEntityService;
    @Autowired
    private ObjectEntityMapper objectEntityMapper;

    /**
     * 查询分页
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("queryPage")
    @AuthLoginAnnotation(authorityCode = "/obj/queryPage")
    public JsonResultPage<Object> queryPage(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int currpage = DataUtil.getInt(req.getParameter("currpage"));

        Page page = new Page();
        page.setCurPage(currpage);
        page.setShowSize(DataUtil.getInt(EnumUtil.PageSize.PAGE_FIF_TEN.getValue()));
        Hashtable<String, String> condition = new Hashtable<String, String>();
        page.setCondition(condition);
        try {
            page = objectEntityService.queryLogPage(page);
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


    /**
     * 删除api
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("deleteById")
    @AuthLoginAnnotation(authorityCode = "/obj/deleteById")
    public JsonResultPage<Object> deleteobjectId(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int objectId = DataUtil.getInt(req.getParameter("objectId"));
        try {
            int i = objectEntityMapper.deleteById(objectId);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 新增api
     *
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("insertObject")
    @AuthLoginAnnotation(authorityCode = "/obj/insertObject")
    public JsonResultPage<Object> insertNavigation(ObjectEntity objectEntity, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();

        try {
            int i = objectEntityMapper.insert(objectEntity);
            Map map=new HashMap();
            map.put("objectCode",objectEntity.getObjectCode());
            map.put("objectId",objectEntity.getObjectId());
            String token=TokenUtil.getCreateJwt(objectEntity.getObjectId()+"",map,objectEntity.getObjectEndTime());
            objectEntity.setObjectKey(token);
            objectEntityMapper.updateById(objectEntity);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 修改查询 回显
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("selectById")
    @AuthLoginAnnotation(authorityCode = "/obj/selectById")
    public JsonResultPage<Object> selectById(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int objectId = DataUtil.getInt(req.getParameter("objectId"));
        try {
            ObjectEntity objectEntity = objectEntityMapper.selectById(objectId);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("查询成功！");
            resultJson.setData(objectEntity);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 修改api
     *
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("updateById")
    @AuthLoginAnnotation(authorityCode = "/obj/updateById")
    public JsonResultPage<Object> updateById(ObjectEntity objectEntity, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        try {
            int i = objectEntityMapper.updateById(objectEntity);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    @PostMapping("updateKey")
    @AuthLoginAnnotation(authorityCode = "/obj/updateKey")
    public JsonResultPage<Object> updateKey(int objectId, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        try {
            ObjectEntity objectEntity=objectEntityMapper.selectById(objectId);
            Map map=new HashMap();
            map.put("objectCode",objectEntity.getObjectCode());
            map.put("objectId",objectEntity.getObjectId());
            String token=TokenUtil.getCreateJwt(objectEntity.getObjectId()+"",map,objectEntity.getObjectEndTime());
            objectEntity.setObjectKey(token);
            int i = objectEntityMapper.updateById(objectEntity);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setData(objectEntity);
            resultJson.setMsg("秘钥已更新");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


}
