package com.sxpcwlkj.controller.admin;


import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.common.Layuitree;
import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.controller.common.CommonController;
import com.sxpcwlkj.entity.*;
import com.sxpcwlkj.mapper.FunctionMapper;
import com.sxpcwlkj.mapper.RoleMapper;
import com.sxpcwlkj.mapper.UserMapper;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.service.UserService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.controller.admin
 * @date 2019/3/15
 * 用户管理
 */
@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private FunctionMapper functionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogService logService;
    @Autowired
    private RedisUtil redisUtil;

    public static int userId = 0;

    /**
     * 退出登录
     *
     * @param map
     * @return
     */
    @GetMapping("/userLogout")
    @AuthLoginAnnotation(authorityCode = "/user/userLogout")
    public JsonResultPage<Object> userLogout(Model map) {
        JsonResultPage<Object> resultJson = new JsonResultPage();
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isAuthenticated()) {
//            subject.logout();
//        }
        redisUtil.delUserKey(this.getUser().getUserId()+"");
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
        resultJson.setState(1);
        resultJson.setMsg("已退出");
        return resultJson;
    }

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/imageCodeEx", method = RequestMethod.GET)
    @AuthLoginAnnotation(login = false)
    public JsonResultObject getCodeEx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCodeUtil vCode = new ImageCodeUtil(146, 46, 4, 150);
        int codeType = DataUtil.getInt(request.getParameter("type"));
        String random=DataUtil.getString(request.getParameter("random"));
        String img=ImageToBase64Util.BufferedImageToBase64( vCode.getBuffImg());
        redisUtil.set(EnumUtil.StaticUtil.CODE.getValue()+"_"+random, vCode.getCode(), 1000 * 60);
        return JsonResultObject.getSuccessResult(img);
    }


    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("userLogin")
    @AuthLoginAnnotation(login = false)
    public JsonResultPage<Object> userLogin(HttpServletRequest request, HttpServletResponse response) {
        JsonResultPage<Object> resultJson = new JsonResultPage();
        String random=DataUtil.getString(request.getParameter("random"));
        String loginCodeText = DataUtil.getString(request.getParameter("verCode"));
        String userAccount = DataUtil.getString(request.getParameter("userAccount"));
        String userPassword = DataUtil.getString(request.getParameter("userPassword"));
        String sessionCode = (String) redisUtil.get(EnumUtil.StaticUtil.CODE.getValue()+"_"+random);
        if (loginCodeText.equalsIgnoreCase(sessionCode)) {
            User user = new User("", userAccount, Md5Util.getPassword(userPassword));
            Map resMap = userService.loginUser(user);
            resultJson.setCode(DataUtil.getInt(resMap.get("code")));
            resultJson.setState(DataUtil.getInt(resMap.get("state")));
            resultJson.setMsg(DataUtil.getString(resMap.get("msg")));
            Map map = new HashMap();
            map.put("token", DataUtil.getString(resMap.get("token")));
            map.put("user", (User) resMap.get("user"));
            resultJson.setData(map);
            if (DataUtil.getInt(resMap.get("state")) == 1) {
                this.userId = DataUtil.getInt(resMap.get("userId"));
                logService.addLog(new Log("增," + userAccount + "登录成功"));
            }
        } else {
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setState(-4);
            resultJson.setMsg("验证码不正确！");
        }
        return resultJson;

    }


    /**
     * 根据角色 和userId  查询 权限树  左菜单 只显示2级
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */


    @GetMapping("getNumeTree")
    @AuthLoginAnnotation(authorityCode = "/user/getNumeTree")
    public JsonResultPage<Object> getNumeTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonResultPage jsonResultPage = new JsonResultPage();

        List<Function> list = this.getUser().getFunctionList();
        List listmap = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLevel() == 2 && list.get(i).getFunId() != 6) {
                Map mapOne = new HashMap();
                mapOne.put("title", list.get(i).getFunName());
                mapOne.put("icon", list.get(i).getIcon());
                int isTwo = 1;
                int isOpen = 1;
                List<Function> flistTwo = new ArrayList<Function>();
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getLevel() == 3 && list.get(j).getFatherId() == list.get(i).getFunId()) {
                        isTwo = 2;
                        if (list.get(j).getIsOpen().equals("true")) {
                            isOpen = 2;
                        }
                        flistTwo.add(list.get(j));
                    }
                }

                if (isTwo == 1) {
                    /*没有二级菜单*/
                    mapOne.put("url", list.get(i).getLinkPath());
                } else {
                    /* list.get(i) 有二级菜单*/
                    List<Object> listtwo = new ArrayList<Object>();
                    /*遍历二级菜单*/
                    for (int k = 0; k < flistTwo.size(); k++) {
                        Map mapTwo = new HashMap();
                        mapTwo.put("title", flistTwo.get(k).getFunName());
                        /*查询是否有3级菜单*/
                        int isThree = 1;
                        int isThreeOpen = 1;
                        List<Function> flistThree = new ArrayList<Function>();
                        for (int r = 0; r < list.size(); r++) {
                            if (list.get(r).getLevel() == 4 && list.get(r).getFatherId() == flistTwo.get(k).getFunId()) {
                                isThree = 2;
                                if (list.get(r).getIsOpen().equals("true")) {
                                    isThreeOpen = 2;
                                }
                                flistThree.add(list.get(r));
                            }
                        }

                        if (isThreeOpen == 2) {
                            mapTwo.put("spread", true);
                        }

                        /*判断是否有3级菜单，这里不需要展示3级权限，详情可以在 权限树中查看*/
                        //if (isThree == 1) {
                        if (true) {
                            /*没有3级*/
                            mapTwo.put("url", flistTwo.get(k).getLinkPath());
                        } else {
                            mapTwo.put("url", flistTwo.get(k).getLinkPath());

                            /*3级菜单  zan暂规划为权限菜单*/
                            List<Object> listThree = new ArrayList<Object>();
                            for (int t = 0; t < flistThree.size(); t++) {
                                Map mapThree = new HashMap();
                                mapThree.put("title", flistThree.get(t).getFunName());
                                //mapThree.put("url", flistThree.get(t).getLinkPath());
                                if (flistThree.get(t).getIsOpen().equals("true")) {
                                    mapThree.put("spread", true);
                                }
                                listThree.add(mapThree);
                            }
                            mapTwo.put("list", listThree);
                        }
                        listtwo.add(mapTwo);
                    }
                    mapOne.put("list", listtwo);
                }
                if (isOpen == 2) {
                    mapOne.put("spread", true);
                }
                listmap.add(mapOne);
            }
        }
        jsonResultPage.setCode(EnumUtil.Result.SUCCESS.getValue());
        jsonResultPage.setMsg("查询菜单成功");
        jsonResultPage.setData(listmap);
        return jsonResultPage;
    }


    /**
     * 查询所有角色
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("selectAllRole")
    @AuthLoginAnnotation(authorityCode = "/user/selectAllRole")
    public JsonResultPage<Object> selectAllRole(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        List<Role> list = roleMapper.selectList(null);
        resultJson.setMsg("查询角色成功");
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
        resultJson.setData(list);
        return resultJson;
    }

    /**
     * 员工列表
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("queryUserInfoPage")
    @AuthLoginAnnotation(authorityCode = "/user/queryUserInfoPage")
    public JsonResultPage<Object> queryUserPage(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int currpage = DataUtil.getInt(req.getParameter("currpage"));
        int roleType = DataUtil.getInt(req.getParameter("roleType"));
        String userName = DataUtil.getString(req.getParameter("userName"));
        String userPhone = DataUtil.getString(req.getParameter("userPhone"));
        String userQq = DataUtil.getString(req.getParameter("userQq"));
        Page page = new Page();
        if (currpage == 0) {
            currpage = 1;
        }
        page.setCurPage(currpage);
        page.setShowSize(DataUtil.getInt(EnumUtil.PageSize.PAGE_NUMBER_TWENTY.getValue()));
        Hashtable<String, String> condition = new Hashtable<String, String>();
        condition.put("roleType", roleType + "");
        condition.put("userName", userName);
        condition.put("userPhone", userPhone);
        condition.put("userQq", userQq);
        page.setCondition(condition);
        try {
            page = userService.queryUserPage(page);
            List<User> list = page.getItem();
            List<Object> runList = new ArrayList<>();
            for (User user : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", user.getUserId());
                map.put("loginname", DataUtil.getString(user.getUserAccount()));
                map.put("telphone", DataUtil.getString(user.getUserPhone()));
                map.put("email", DataUtil.getString(user.getUserEmail()));
                map.put("qq", DataUtil.getString(user.getUserQq()));
                map.put("role", DataUtil.getString(user.getRoles().get(0).getRoleName()));
                map.put("balance", DataUtil.getBigDecimal(user.getBalance()));
                map.put("creationTime", DateUtil.getDateToStr(user.getCreationTime(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
                map.put("check", DataUtil.getString(user.getUserCheck()));
                map.put("describe", user.getUserDescribe());
                runList.add(map);
            }

            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("查询员工信息成功！");
            resultJson.setData(runList);
            resultJson.setCount(page.getTotalRow());    //总条数
            resultJson.setCurrpage(page.getCurPage());  //当前页码
            resultJson.setShowSize(page.getShowSize()); //每页显示数量

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }


    /**
     * 查询角色分页
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("queryRolePage")
    @AuthLoginAnnotation(authorityCode = "/user/queryRolePage")
    public JsonResultPage<Object> queryRolePage(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int currpage = DataUtil.getInt(req.getParameter("currpage"));
        String keyword = DataUtil.getString(req.getParameter("keyword"));
        String roletype = DataUtil.getString(req.getParameter("roletype"));
        if (currpage == 0) {
            currpage = 1;
        }
        Page page = new Page();
        page.setCurPage(currpage);
        page.setShowSize(DataUtil.getInt(EnumUtil.PageSize.PAGE_FIF_TEN.getValue()));
        Hashtable<String, String> condition = new Hashtable<String, String>();
        condition.put("keyword", keyword);
        condition.put("roletype", roletype);
        page.setCondition(condition);
        try {
            page = userService.queryRolePage(page);
            List<Role> list = page.getItem();
            List<Object> mapList = new ArrayList<Object>();
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                map.put("id", list.get(i).getRoleId());
                map.put("rolename", list.get(i).getRoleName());
                map.put("limits", "查角色权限");
                map.put("roleRank", list.get(i).getRoleRank());
                map.put("descr", list.get(i).getRoleDesc());
                map.put("check", true);
                mapList.add(map);
            }
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("获取角色信息成功！");
            resultJson.setCount(page.getTotalRow());
            resultJson.setData(mapList);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 查看角色编码
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("getRoleCode")
    @AuthLoginAnnotation(authorityCode = "/user/getRoleCode")
    public JsonResultPage<Object> getRoleCode(HttpServletRequest request, HttpServletResponse response) {
        JsonResultPage jsonResultPage = new JsonResultPage();
        List<Code> list = userService.selsetRoleCode(1);
        jsonResultPage.setMsg("OK");
        jsonResultPage.setCode(EnumUtil.Result.SUCCESS.getValue());
        jsonResultPage.setData(list);
        return jsonResultPage;
    }

    /**
     * 查询所有权限
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */

    @GetMapping("getNumeTreeAll")
    @AuthLoginAnnotation(authorityCode = "/user/getNumeTreeAll")
    public JsonResultPage<Object> getNumeTreeAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonResultPage jsonResultPage = new JsonResultPage();
        /*权限*/
        List<Function> list = userService.Allfunction();
        List listmap = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLevel() == 2) {
                Map mapOne = new HashMap();
                mapOne.put("title", "[主]" + list.get(i).getFunName());
                mapOne.put("funId", list.get(i).getFunId());
                int isTwo = 1;
                int isOpen = 1;
                List<Function> flistTwo = new ArrayList<Function>();
                /*寻找当前一级的所有 二级*/
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getLevel() == 3 && list.get(j).getFatherId() == list.get(i).getFunId()) {
                        isTwo = 2;
                        if (list.get(j).getIsOpen().equals("true")) {
                            isOpen = 2;
                        }
                        flistTwo.add(list.get(j));
                    }
                }

                if (isTwo == 1) {
                    /*没有二级菜单*/
                    mapOne.put("url", list.get(i).getLinkPath());
                    mapOne.put("funId", list.get(i).getFunId());
                } else {
                    mapOne.put("funId", list.get(i).getFunId());
                    /* list.get(i) 有二级菜单*/
                    List<Object> listtwo = new ArrayList<Object>();
                    /*遍历寻找当前一级的 二级菜单*/
                    for (int k = 0; k < flistTwo.size(); k++) {
                        Map mapTwo = new HashMap();
                        mapTwo.put("title", "[子]" + flistTwo.get(k).getFunName());
                        mapTwo.put("funId", flistTwo.get(k).getFunId());
                        /*查询是否有3级菜单*/
                        int isThree = 1;
                        int isThreeOpen = 1;
                        List<Function> flistThree = new ArrayList<Function>();
                        /*寻找当前二级菜单的  三级*/
                        for (int r = 0; r < list.size(); r++) {
                            if (list.get(r).getLevel() == 4 && list.get(r).getFatherId() == flistTwo.get(k).getFunId()) {
                                isThree = 2;
                                if (list.get(r).getIsOpen().equals("true")) {
                                    isThreeOpen = 2;
                                }
                                flistThree.add(list.get(r));
                            }
                        }

                        if (isThreeOpen == 2) {
                            mapTwo.put("spread", true);
                        }
                        if (isThree == 1) {
                            /*没有3级*/
                            mapTwo.put("url", flistTwo.get(k).getLinkPath());
                            mapTwo.put("funId", flistTwo.get(k).getFunId());
                        } else {
                            //有无三级都加url
                            //mapTwo.put("url", flistTwo.get(k).getLinkPath());
                            mapTwo.put("funId", flistTwo.get(k).getFunId());
                            List<Object> listThree = new ArrayList<Object>();
                            for (int t = 0; t < flistThree.size(); t++) {
                                Map mapThree = new HashMap();
                                mapThree.put("title", "[权]" + flistThree.get(t).getFunName());
                                mapThree.put("funId", flistThree.get(t).getFunId());
                                mapThree.put("url", flistThree.get(t).getLinkPath());
                                if (flistThree.get(t).getIsOpen().equals("true")) {
                                    mapThree.put("spread", true);
                                }
                                listThree.add(mapThree);
                            }
                            mapTwo.put("list", listThree);
                        }
                        listtwo.add(mapTwo);
                    }
                    mapOne.put("list", listtwo);
                }
                if (isOpen == 2) {
                    mapOne.put("spread", true);
                }
                listmap.add(mapOne);
            }
        }
        jsonResultPage.setCode(EnumUtil.Result.SUCCESS.getValue());
        jsonResultPage.setMsg("查询菜单成功");
        jsonResultPage.setData(listmap);
        return jsonResultPage;
    }


    /**
     * 添加角色
     *
     * @param req
     * @param resp
     * @param
     * @return
     */
    @PostMapping("addRoleInfo")
    @AuthLoginAnnotation(authorityCode = "/user/addRoleInfo")
    public JsonResultPage<Object> addRole(@RequestParam(value = "checkbox[]") int[] checkbox, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String roleName = DataUtil.getString(req.getParameter("name"));     //标题
        String roleDesc = DataUtil.getString(req.getParameter("descr"));    //描述
        int roleCodeId = DataUtil.getInt(req.getParameter("codeId"));       //角色编码
        int roletype = DataUtil.getInt(req.getParameter("roletype"));       //角色类型
        int roleRank = DataUtil.getInt(req.getParameter("roleRank"));       //角色级别
        int roleSort = DataUtil.getInt(req.getParameter("roleSort"));       //排序
        Role role = new Role();
        role.setRoleDesc(roleDesc);
        role.setRoleName(roleName);
        role.setCodeId(roleCodeId);
        role.setRoleType(roletype);
        role.setRoleRank(roleRank);
        role.setRoleSort(roleSort);
        try {
            /*添加角色*/
            roleMapper.insert(role);
            /*角色授权*/
            userService.updateRoleFunction(checkbox, role.getRoleId());
            logService.addLog(new Log("增,添加角色" + roleName));
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("添加角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 删除角色
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("deleteRole")
    @AuthLoginAnnotation(authorityCode = "/user/deleteRole")
    public JsonResultPage<Object> deleteRole(@RequestParam(value = "checkData[]") int[] checkData, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        try {
            if (checkData != null && checkData.length > 0) {
                for (int i = 0; i < checkData.length; i++) {
                    int roleId = checkData[i];
                    userService.deleteRoleByRoleId(roleId);
                }
                logService.addLog(new Log("删,删除角色"));
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("删除角色成功！");
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("删除角色失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 角色编辑  回显
     *
     * @param req
     * @param resp
     * @return
     */

    @GetMapping("queryRoleByRoleId")
    @AuthLoginAnnotation(authorityCode = "/user/queryRoleByRoleId")
    public JsonResultPage<Object> queryRoleByRoleId(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int roleId = DataUtil.getInt(req.getParameter("roleId"));
        try {
            Role role = userService.queryRoleByRoleId(roleId);
            List<Function> listing = userService.queryFunctionByRoleId(roleId);

            List<Function> list = userService.Allfunction();
            List listmap = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getLevel() == 2) {
                    Map mapOne = new HashMap();
                    mapOne.put("title", "[主]" + list.get(i).getFunName());
                    mapOne.put("funId", list.get(i).getFunId());
                    for (int j = 0; j < listing.size(); j++) {
                        if (list.get(i).getFunId() == listing.get(j).getFunId()) {
                            mapOne.put("type", 1);
                            break;
                        } else {
                            mapOne.put("type", 0);
                        }
                    }
                    listmap.add(mapOne);

                    /*寻找这个一级菜单的二级*/
                    for (int k = 0; k < list.size(); k++) {
                        if (list.get(k).getFatherId() == list.get(i).getFunId()) {
                            Map mapTwo = new HashMap();
                            mapTwo.put("title", "[子]" + list.get(k).getFunName());
                            mapTwo.put("funId", list.get(k).getFunId());
                            for (int l = 0; l < listing.size(); l++) {
                                if (list.get(k).getFunId() == listing.get(l).getFunId()) {
                                    mapTwo.put("type", 1);
                                    break;
                                } else {
                                    mapTwo.put("type", 0);
                                }
                            }
                            listmap.add(mapTwo);

                            /*寻找3级*/
                            for (int q = 0; q < list.size(); q++) {
                                if (list.get(q).getFatherId() == list.get(k).getFunId()) {
                                    Map mapTHree = new HashMap();
                                    mapTHree.put("title", "[权]" + list.get(q).getFunName());
                                    mapTHree.put("funId", list.get(q).getFunId());
                                    for (int l = 0; l < listing.size(); l++) {
                                        if (list.get(q).getFunId() == listing.get(l).getFunId()) {
                                            mapTHree.put("type", 1);
                                            break;
                                        } else {
                                            mapTHree.put("type", 0);
                                        }
                                    }
                                    listmap.add(mapTHree);
                                }
                            }
                        }
                    }
                }
            }


            Map map = new HashMap();
            map.put("role", role);
            map.put("list", listmap);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("查询角色信息成功！");
            resultJson.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 修改角色
     *
     * @param req
     * @param resp
     * @param
     * @return
     */
    @PostMapping("updateRole")
    @AuthLoginAnnotation(authorityCode = "/user/updateRole")
    public JsonResultPage<Object> updateRole(@RequestParam(value = "checkbox[]") int[] checkbox, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String roleName = DataUtil.getString(req.getParameter("name"));     //标题
        String roleDesc = DataUtil.getString(req.getParameter("descr"));    //描述
        int roleCodeId = DataUtil.getInt(req.getParameter("codeId"));       //角色编码
        int roleId = DataUtil.getInt(req.getParameter("roleId"));           //角色id
        int roleRank = DataUtil.getInt(req.getParameter("roleRank"));       //角色级别
        int roletype = DataUtil.getInt(req.getParameter("roletype"));       //角色级别
        int roleSort = DataUtil.getInt(req.getParameter("roleSort"));       //排序

        Role role = new Role();
        role.setRoleDesc(roleDesc);
        role.setRoleName(roleName);
        role.setCodeId(roleCodeId);
        role.setRoleId(roleId);
        role.setRoleRank(roleRank);
        role.setRoleType(roletype);
        role.setRoleSort(roleSort);
        try {
            userService.updateRoleByRoleId(role);
            /*角色授权*/
            userService.updateRoleFunction(checkbox, role.getRoleId());
            logService.addLog(new Log("改,修改角色"));
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("修改角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 查询权限树
     *
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("loadTree")
    @AuthLoginAnnotation(authorityCode = "/user/loadTree")
    public JsonResultPage<Object> loadTree(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        List<Function> functions;
        try {
            functions = userService.queryFunctionAll();
            List<Layuitree> layuitrees = new ArrayList<>();
            for (Function function : functions) {
                if (function.getFatherId() == 0) {
                    Layuitree layuitree = new Layuitree();
                    layuitree.setId(function.getFunId() + "");
                    layuitree.setTitle(function.getFunName());
                    /*查询是否有子级，返回 一个状态  和 list */
                    layuitree = selectSun(layuitree, functions);
                    List<Layuitree> runList = layuitree.getChildren();
                    if (runList != null && runList.size() > 0) {
                        layuitree.setChildren(runList);
                        layuitree.setLast(false);
                    } else {
                        layuitree.setLast(true);
                    }
                    layuitrees.add(layuitree);
                }
            }
            resultJson.setData(layuitrees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultJson.setMsg("OK");
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
        return resultJson;
    }

    /*递归*/
    private Layuitree selectSun(Layuitree layuitree, List<Function> functions) {
        List<Layuitree> listTrees = new ArrayList<>();
        for (Function function : functions) {
            if ((function.getFatherId() + "").equals(layuitree.getId())) {
                Layuitree layuiT = new Layuitree();
                layuiT.setId(function.getFunId() + "");    //id
                layuiT.setTitle(function.getFunName());  //title
                layuiT.setParentId(layuitree.getId());   //父 id
                layuiT = selectSun(layuiT, functions);
                List<Layuitree> runList = layuiT.getChildren();
                if (runList != null && runList.size() > 0) {
                    layuiT.setChildren(runList);
                    layuiT.setLast(false);
                } else {
                    layuiT.setLast(true);
                }
                listTrees.add(layuiT);
            }
        }
        layuitree.setChildren(listTrees);
        return layuitree;
    }

    /**
     * 新增权限
     *
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("addFunction")
    @AuthLoginAnnotation(authorityCode = "/user/addFunction")
    public JsonResultPage<Object> addFunction(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String funName = DataUtil.getString(req.getParameter("funName"));  //标题
        String funCode = DataUtil.getString(req.getParameter("funCode"));  //权限码
        int fatherId = DataUtil.getInt(req.getParameter("fatherId"));      //父级id
        int level = DataUtil.getInt(req.getParameter("level"));            //权限级别
        String machinePath = DataUtil.getString("");
        String linkPath = DataUtil.getString(req.getParameter("linkPath"));//权限链接
        String externalPath = DataUtil.getString("");
        int sort = DataUtil.getInt(req.getParameter("sort"));              //排序
        int pathType = DataUtil.getInt("");                                  //链接类型 打开方式
        String icon = DataUtil.getString(req.getParameter("icon"));        //图标
        String isOpen = DataUtil.getString(req.getParameter("isOpen"));      //是否默认菜单 展开
        Function fun = new Function();
        fun.setFunName(funName);
        fun.setFunCode(funCode);
        fun.setFatherId(fatherId);
        fun.setLevel(level);
        fun.setMachinePath(machinePath);
        fun.setLinkPath(linkPath);
        fun.setExternalPath(externalPath);
        fun.setPathType(pathType);
        fun.setSort(sort);
        fun.setIcon(icon);
        fun.setIsOpen(isOpen);
        try {
            functionMapper.insert(fun);
            logService.addLog(new Log("增,新增权限" + fun.getFunName()));
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("新增权限成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setMsg("新增权限出错了！");
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 删除权限
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("deleteFunction")
    @AuthLoginAnnotation(authorityCode = "/user/deleteFunction")
    public JsonResultPage<Object> deleteFunction(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int funId = DataUtil.getInt(req.getParameter("funId"));
        try {
            User user =this.getUser();
            if (user.getUserId() != 1) {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("你不是管理员无权操作！");
                return resultJson;
            }
            int i = userService.deleteFunctionByFunId(funId);
            if (i == 0) {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("此权限有子权限，不可以删除！");
            } else {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("删除权限成功！");
                logService.addLog(new Log("删,删除权限"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 编辑权限 回显
     *
     * @param req
     * @param resp
     * @return
     */

    @GetMapping("queryFunctionByFunId")
    @AuthLoginAnnotation(authorityCode = "/user/queryFunctionByFunId")
    public JsonResultPage<Object> queryFunctionByFunId(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int funId = DataUtil.getInt(req.getParameter("funId"));
        try {
            Function fun = functionMapper.selectById(funId);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("获取权限信息成功！");
            resultJson.setData(fun);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 修改权限
     *
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("updateFunction")
    @AuthLoginAnnotation(authorityCode = "/user/updateFunction")
    public JsonResultPage<Object> updateFunction(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String funName = DataUtil.getString(req.getParameter("funName"));                     //标题
        String funCode = DataUtil.getString(req.getParameter("funCode"));                      //编码
        int level = DataUtil.getInt(req.getParameter("level"));                //级别
        String machinePath = DataUtil.getString("");
        String linkPath = DataUtil.getString(req.getParameter("linkPath"));   //链接
        String externalPath = DataUtil.getString("");
        int sort = DataUtil.getInt(req.getParameter("sort"));                  //排序
        int funId = DataUtil.getInt(req.getParameter("funId"));               //id
        int pathType = DataUtil.getInt("");
        String icon = DataUtil.getString(req.getParameter("icon"));            //图标
        Function fun = new Function();
        fun.setFunName(funName);
        fun.setFunCode(funCode);
        fun.setLevel(level);
        fun.setMachinePath(machinePath);
        fun.setLinkPath(linkPath);
        fun.setExternalPath(externalPath);
        fun.setFunId(funId);
        fun.setSort(sort);
        fun.setPathType(pathType);
        fun.setIcon(icon);
        fun.setOpenType(1);
        fun.setIsOpen("false");
        try {
            functionMapper.updateById(fun);
            logService.addLog(new Log("改,修改权限"));
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("修改权限成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 用户自己修改资料
     *
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("updateUserSelf")
    @AuthLoginAnnotation(authorityCode = "/user/updateUserSelf")
    public JsonResultPage<Object> updateUserSelf(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String userAccount = DataUtil.getString(req.getParameter("username"));       //登录账户
        String userName = DataUtil.getString(req.getParameter("nickname"));          //昵称
        String userPhone = DataUtil.getString(req.getParameter("phone"));            //手机号
        String userEmail = DataUtil.getString(req.getParameter("email"));            //邮箱
        int departmentId = DataUtil.getInt(req.getParameter("departmentId"));        //部门
        String avatar = DataUtil.getString(req.getParameter("avatar"));                //sql  头像地址
        int roleId = DataUtil.getInt(req.getParameter("roleId"));                      //角色id
        if (roleId == 1) {
            return null;
        }
        if (!DataUtil.isLetterDigit(userAccount)) {
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("用户名只能是字母或数字");
            return resultJson;
        }
        String qq = DataUtil.getString(req.getParameter("qq"));                        //qq
        User user = this.getUser();
        user.setUserAccount(userAccount);
        user.setUserEmail(userEmail);
        user.setUserName(userName);
        //users.setUserPassword(Md5Util.getPassword("123456"));
        user.setDepartmentId(departmentId);
        //users.setUserPhone(userPhone);
        user.setIcon(avatar);
        user.setUserQq(qq);
        //users.setUserCheck(1);                   //状态正常
        //users.setAddTime(new Date());
        user.setUserId(user.getUserId());
        try {
            // 获取登录客户端的IP地址
            int i = userService.updateUserByUserInfo(user);
            if (i == 1) {
                User newUser=userMapper.selectById(user.getUserId());
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                newUser.setUserPassword("");
                List<Role> list = roleMapper.lestUserRoleOne(newUser.getUserId());
                newUser.setStrRoles(JsonUtil.entityToJsonString(list));
                List<Function> funlist = functionMapper.listFunctionForUserId(newUser.getUserId());
                newUser.setStrfunction(JsonUtil.entityToJsonString(funlist));
                redisUtil.setUserKey(newUser);
                resultJson.setData(newUser);
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("修改资料成功");
                logService.addLog(new Log("改,自己资料"));
            } else if (i == -2) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("用户名已有人使用");
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("修改资料失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


    /**
     * 修改密码
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("updatePassword")
    @AuthLoginAnnotation(authorityCode = "/user/updatePassword")
    public JsonResultPage<Object> updatePassword(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String oldPassword = DataUtil.getString(req.getParameter("oldPassword"));
        String newPassword = DataUtil.getString(req.getParameter("newPassword"));
        User user =this.getUser();
        try {
            user = userService.selectUserForPhone(user.getUserPhone());
            if (!user.getUserPassword().equals(Md5Util.getPassword(oldPassword))) {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("旧密码输入错误！");
            } else {
                user.setUserPassword(Md5Util.getPassword(newPassword));
                int num = userService.forgetUserPassWord(user);
                if (num > 0) {

                    resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                    resultJson.setMsg("修改密码成功！");
                    logService.addLog(new Log("改,修改密码"));
                } else {
                    resultJson.setCode(EnumUtil.Result.ERROR.getValue());
                    resultJson.setMsg("修改密码失败！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 添加员工
     *
     * @param req
     * @param resp
     * @return
     */

    @GetMapping("addUserInfo")
    @AuthLoginAnnotation(authorityCode = "/user/addUserInfo")
    public JsonResultPage<Object> addUser(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String userAccount = DataUtil.getString(req.getParameter("username"));       //登录账户
        String userName = DataUtil.GenerateCode();                                      //昵称
        String userPhone = DataUtil.getString(req.getParameter("phone"));            //手机号
        String userEmail = DataUtil.getString(req.getParameter("email"));            //邮箱
        int departmentId = DataUtil.getInt(2);        //部门
        String avatar = DataUtil.getString(req.getParameter("avatar"));              //sql  头像地址
        int roleId = DataUtil.getInt(req.getParameter("roleId"));                    //角色id
        if (roleId == 1) {
            return null;
        }
        String qq = DataUtil.getString(req.getParameter("qq"));                        //qq
        User users = new User();
        users.setUserAccount(userAccount);
        users.setUserEmail(userEmail);
        users.setUserName(userName);
        users.setUserPassword(Md5Util.getPassword("123456"));
        users.setDepartmentId(departmentId);
        users.setUserPhone(userPhone);
        users.setIcon(avatar);
        users.setUserQq(qq);
        users.setUserCheck(1);                   //状态正常
        users.setCreationTime(new Date());

        try {
            // 获取登录客户端的IP地址
            Map map = userService.addUser(users, roleId);
            int i = DataUtil.getInt(map.get("code"));
            if (i == 1) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg(DataUtil.getString(map.get("msg")));
                resultJson.setState(DataUtil.getInt(map.get("state")));
                logService.addLog(new Log("增,增加用户" + userName));
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg(DataUtil.getString(map.get("msg")));
                resultJson.setState(DataUtil.getInt(map.get("state")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 修改用户  回显
     *
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("queryUserByUserId")
    @AuthLoginAnnotation(authorityCode = "/user/queryUserByUserId")
    public JsonResultPage<Object> queryUserByUserId(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int userId = DataUtil.getInt(req.getParameter("userId"));
        try {
            User user = userService.queryUserByUserId(userId);

            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getUserId());
            map.put("loginname", DataUtil.getString(user.getUserAccount()));
            map.put("telphone", DataUtil.getString(user.getUserPhone()));
            map.put("email", DataUtil.getString(user.getUserEmail()));
            map.put("qq", DataUtil.getString(user.getUserQq()));
            map.put("role", DataUtil.getString(user.getRoleName()));
            map.put("icon", DataUtil.getString(user.getIcon()));

            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("获取员工成功！");
            resultJson.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 用户 授权
     *
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("updateUserRole")
    @AuthLoginAnnotation(authorityCode = "/user/updateUserRole")
    public JsonResultPage<Object> updateUserRole(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int userId = DataUtil.getInt(req.getParameter("userId"));
        int roleId = DataUtil.getInt(req.getParameter("roleId"));                      //角色id
        try {
            //先删除 再新增
            userMapper.delUserRole(userId);
            int i = userMapper.addUserRole(userId, roleId);
            if (i == 1) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("已授权！");
                logService.addLog(new Log("改,授权用户新角色"));
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("授权出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * 重置密码
     *
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("updateUserPass")
    @AuthLoginAnnotation(authorityCode = "/user/updateUserPass")
    public JsonResultPage<Object> updateUserPass(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int userId = DataUtil.getInt(req.getParameter("userId"));
        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(Md5Util.getPassword("123456"));
        try {
            int i = userService.updateUserPassWord(user);
            if (i == 1) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("已重置！");
                logService.addLog(new Log("改,重置用户[ID:" + userId + "]密码"));
            } else {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("重置出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * 管理员修改ia员工
     *
     * @param req
     * @param resp
     * @return
     */
    @PostMapping("updateUserInfo")
    @AuthLoginAnnotation(authorityCode = "/user/updateUserInfo")
    public JsonResultPage<Object> updateUser(HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        String userAccount = DataUtil.getString(req.getParameter("username"));         //登录账户
        String userName = DataUtil.getString(req.getParameter("username"));            //昵称
        String userPhone = DataUtil.getString(req.getParameter("phone"));              //手机号
        String userEmail = DataUtil.getString(req.getParameter("email"));              //邮箱
        int departmentId = DataUtil.getInt(2);                                       //部门
        String avatar = DataUtil.getString(req.getParameter("avatar"));                //sql  头像地址
        int roleId = DataUtil.getInt(req.getParameter("roleId"));                      //角色id
        String qq = DataUtil.getString(req.getParameter("qq"));                        //qq
        User users = new User();
        users.setUserAccount(userAccount);
        users.setUserEmail(userEmail);
        users.setUserName(userName);
        //users.setUserPassword(Md5Util.getPassword("123456"));
        users.setDepartmentId(departmentId);
        users.setUserPhone(userPhone);
        users.setIcon(avatar);
        users.setUserQq(qq);
        users.setUserCheck(1);                   //状态正常
        users.setCreationTime(new Date());

        try {

            int i = userService.updateUserByUserId(users, roleId);
            if (i == 1) {
                resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
                resultJson.setMsg("修改员工成功！");
                logService.addLog(new Log("改,修改用户[" + userAccount + "]资料"));
            } else if (i == 2) {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("此手机号已被添加，请重新填写手机号！");
            } else if (i == 3) {
                resultJson.setCode(EnumUtil.Result.FAILURE.getValue());
                resultJson.setMsg("此用户名已存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }

    /**
     * 改变用户状态
     *
     * @param req
     * @param resp
     * @return
     */

    @PostMapping("updateUserByUserId")
    @AuthLoginAnnotation(authorityCode = "/user/updateUserByUserId")
    public JsonResultPage<Object> updateUserByUserId(@RequestParam(value = "userId[]") int[] userId, HttpServletRequest req, HttpServletResponse resp) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();
        int type = DataUtil.getInt(req.getParameter("type"));   //1:正常  2：  禁用
        try {
            for (int i = 0; i < userId.length; i++) {
                if (userId[i] == 1) {
                    continue;
                } else {
                    userService.updateUserType(userId[i], type);
                    logService.addLog(new Log("改,修改用户[ID:" + userId[i] + "]转态为:" + (type == 1 ? "启用" : "禁用")));
                }
            }
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());
            resultJson.setMsg("执行成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode(EnumUtil.Result.ERROR.getValue());
        }
        return resultJson;
    }


}
