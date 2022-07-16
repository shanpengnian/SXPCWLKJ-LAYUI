package com.sxpcwlkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.entity.*;
import com.sxpcwlkj.mapper.*;
import com.sxpcwlkj.service.UserService;
import com.sxpcwlkj.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.sxpcwlkj.utils.DataUtil.getDouble;


@Service
@CacheConfig(cacheNames = "userInfoCache") // redis缓存类名 主要是用于和其他类的冲突
/**
 * @Transactional(
 *     readOnly = false, //读写事务 true表明所注解的方法或类只是读取数据,false表明所注解的方法或类是增加，删除，修改数据。
 *     timeout = -1 ,     //事务的超时时间，-1为无限制
 *     noRollbackFor = ArithmeticException.class, //遇到指定的异常不回滚
 *     isolation = Isolation.DEFAULT, //事务的隔离级别，此处使用后端数据库的默认隔离级别
 *     propagation = Propagation.REQUIRED //事务的传播行为
 * )
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FunctionMapper functionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleFunMapper roleFunMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map loginUser(User user) {
        Map<String, Object> resMap = new HashMap<>();

        //第一步  验证账号和密码
        User sqlUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_account", user.getUserAccount()));
        if (sqlUser == null) {
            resMap.put("code", EnumUtil.Result.SUCCESS.getValue());
            resMap.put("msg", "用户名不存在");
            resMap.put("state", -1);
            return resMap;
        }
        try {

            User sqlUserPass = userMapper.selectOne(new QueryWrapper<User>()
                    .eq("user_account", user.getUserAccount())
                    .eq("user_password",user.getUserPassword()));

            if(sqlUserPass==null){
                resMap.put("code", EnumUtil.Result.SUCCESS.getValue());
                resMap.put("msg", "账号或密码错误！");
                resMap.put("state", -3);
                return resMap;
            }


            if (sqlUserPass.getUserCheck() == 2 || sqlUserPass.getUserCheck() == 3) {
                resMap.put("code", EnumUtil.Result.SUCCESS.getValue());
                resMap.put("msg", "账号存在违规已禁用！");
                resMap.put("state", -3);
                return resMap;
            }
            sqlUserPass.setUserPassword("");
            resMap.put("code", EnumUtil.Result.SUCCESS.getValue());
            resMap.put("msg", "登录成功");
            resMap.put("state", 1);
            resMap.put("userId",sqlUserPass.getUserId());
            resMap.put("user",sqlUserPass);
            Map map=new HashMap();
            map.put("phone",sqlUserPass.getUserPhone());
            map.put("userId",sqlUserPass.getUserId());
            String token = TokenUtil.getCreateJwt(sqlUserPass.getUserId()+"",map,DateUtil.getAddDate(new Date(),0,0,1,0,0,0,0));
            resMap.put("token", token);
            redisUtil.delUserKey(sqlUserPass.getUserId()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMap;
    }

    /**
     * 员工列表分页
     *
     * @param page
     * @return
     * @throws Exception
     */
    public Page queryUserPage(Page page) {
        StringBuffer countSql = new StringBuffer();
        countSql.append("SELECT count(0) FROM p_user u  LEFT JOIN  `p_user_money` m ON u.`user_id`=m.`user_id` LEFT JOIN `p_user_role` f ON f.`user_id` = u.user_id LEFT JOIN `p_role` re  ON  f.`role_id`=re.`role_id`  WHERE 1 = 1");
        StringBuffer selectSql = new StringBuffer();
        selectSql.append("SELECT  m.`available_money` AS balance ,re.`role_name` AS roleName,  u.* FROM p_user u  LEFT JOIN  `p_user_money` m ON u.`user_id`=m.`user_id` LEFT JOIN `p_user_role` f ON f.`user_id` = u.user_id LEFT JOIN `p_role` re  ON  f.`role_id`=re.`role_id`  WHERE 1 = 1");
        int roleType = DataUtil.getInt(page.getCondition().get("roleType"));
        String username = DataUtil.getString(page.getCondition().get("userName"));
        String userPhone = DataUtil.getString(page.getCondition().get("userPhone"));
        String userQq = DataUtil.getString(page.getCondition().get("userQq"));

        if (roleType != 0) {
            countSql.append(" AND re.`role_id`=" + roleType);
            selectSql.append(" AND re.`role_id`=" + roleType);
        }
        if (username != null && !username.equals("")) {
            countSql.append("   AND u.`user_account` LIKE CONVERT( '%" + username + "%', CHAR(50))");
            selectSql.append("  AND u.`user_account` LIKE CONVERT( '%" + username + "%', CHAR(50))");
        }
        if (userPhone != null && !userPhone.equals("")) {
            countSql.append("  AND u.`user_phone` LIKE CONVERT( '%" + userPhone + "%', CHAR(50))");
            selectSql.append("  AND u.`user_phone` LIKE CONVERT( '%" + userPhone + "%', CHAR(50))");
        }
        if (userQq != null && !userQq.equals("")) {
            countSql.append("  AND u.`user_qq` LIKE CONVERT( '%" + userQq + "%', CHAR(50))");
            selectSql.append("  AND u.`user_qq` LIKE CONVERT( '%" + userQq + "%', CHAR(50))");
        }
        selectSql.append(" ORDER BY user_id ASC ");
        page = Page.PageUtil(page, selectSql.toString(), countSql.toString());                        //调用分页工具类
        page.setItem(userMapper.queryUserPage(page.getSelectCountSql()));        //查询信息
        page.setTotalRow(userMapper.queryUserCount(page.getSelectCountSql()));    //查询总条数

        for (int i = 0; i < page.getItem().size(); i++) {
            User user = (User) page.getItem().get(i);
            List<Role> listRole = userMapper.squeryRole(user.getUserId());
            user.setRoles(listRole);
        }

        return Page.PageUtil(page);
    }

    /**
     * 角色列表分页
     *
     * @param page
     * @return
     * @throws Exception
     */
    public Page queryRolePage(Page page) {
        // 统计总条数的sql
        StringBuffer countSql = new StringBuffer();
        countSql.append(" SELECT count(*) FROM `p_role` r  LEFT JOIN `p_code` c ON r.`code_id`=c.`code_id`  WHERE  1=1");

        StringBuffer selectSql = new StringBuffer();
        selectSql.append("SELECT *  FROM `p_role` r  LEFT JOIN `p_code` c ON r.`code_id`=c.`code_id`  WHERE  1=1 ");

        String keyword = DataUtil.getString(page.getCondition().get("keyword"));
        if (!"".equals(keyword)) {
            countSql.append(" and r.role_name like CONVERT( '%" + keyword + "%', CHAR(50))");
            selectSql.append(" and r.role_name like CONVERT( '%" + keyword + "%', CHAR(50))");
        }
        int roletype = DataUtil.getInt(page.getCondition().get("roletype"));
        if (roletype > 0) {
            countSql.append(" AND r.`role_type`=" + roletype);
            selectSql.append(" AND r.`role_type`=" + roletype);
        }
        selectSql.append("  ORDER BY r.role_sort");
        page = Page.PageUtil(page, selectSql.toString(), countSql.toString());                        //调用分页工具类
        page.setItem(userMapper.queryRolePage(page.getSelectCountSql()));        //查询信息
        page.setTotalRow(userMapper.queryRoleCount(page.getSelectCountSql()));    //查询总条数

        return Page.PageUtil(page);
    }


    /**
     * 根绝角色编号查询角色信息
     *
     * @param roleId
     * @throws Exception
     */
    public Role queryRoleByRoleId(int roleId) {
        return userMapper.queryRoleByRoleIdAll(roleId);
    }

    /**
     * 修改角色
     *
     * @param role
     * @throws Exception
     */
    public void updateRoleByRoleId(Role role) {
        roleMapper.updateById(role);
        //userMapper.updateRoleByRoleId(role);
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @throws Exception
     */
    public void deleteRoleByRoleId(int roleId) {
        userMapper.deleteRoleFunRoleByRoleId(roleId);    //角色权限关系表
        userMapper.deletepUserRoleByRoleId(roleId);      //员工和角色关系表
        userMapper.deleteRoleByRoleId(roleId);           //角色表
    }

    /**
     * 编辑角色权限
     *
     * @param funIds
     * @param roleId
     * @return
     * @throws Exception
     */
    public int updateRoleFunction(int[] funIds, int roleId) {
        List<Function> list = new ArrayList<Function>();
        if (funIds.length <= 0) {
            return 1;
        }
        /*删除之前的*/
        userMapper.deleteRoleFunctionByRoleId(roleId);
        for (int i = 0; i < funIds.length; i++) {
            userMapper.addRoleFunction(roleId, DataUtil.getInt(funIds[i]));
        }
        return 1;
    }

    /**
     * 根据角色编号查询权限
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<Function> queryFunctionByRoleId(int roleId) {
        return userMapper.queryFunctionByRoleId(roleId);
    }


    @Override
    public List<Function> Allfunction() {
        return userMapper.Allfunction();
    }

    @Override
    public List<Code> selsetRoleCode(int type) {
        return userMapper.selsetRoleCode(type);
    }

    /**
     * 查询总权限
     *
     * @return
     * @throws Exception
     */
    public List<Function> queryFunctionAll() {
        return userMapper.queryFunctionAll();
    }

    /**
     * 删除权限
     *
     * @param functionId
     * @throws Exception
     */
    public int deleteFunctionByFunId(int functionId) {
        List<Function> funList = userMapper.queryFunctionByFatherIdGetSonFun(functionId);
        if (funList.size() > 0) {
            return 0;
        } else {
            //删除对应的授权里面的数据
            roleFunMapper.delete(new QueryWrapper<RoleFun>().eq("fun_id",functionId));
            functionMapper.deleteById(functionId);
            return 1;
        }
    }

    public int updateUserByUserInfo(User user) {
        //1：根据用户名查询账户
        User userName = userMapper.queryUserName(user.getUserAccount());
        if (userName == null) {
            //不存在 可以设置
            return userMapper.updateUserByUserInfo(user);
        } else if (userName.getUserId() == user.getUserId()) {
            //存在  看是自己，可以修改
            return userMapper.updateUserByUserInfo(user);
        } else {
            //存在  不是自己不能修改
            return -2;
        }
    }

    @Override
    public User selectUserForPhone(String userPhone) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_phone", userPhone));
    }


    /**
     * 员工修改密码
     *
     * @param user
     */
    public int forgetUserPassWord(User user) {
        return userMapper.forgetUserPassWord(user);
    }

    /**
     * 修改头像
     *
     * @param user
     */
    public int updateUserImgByUserId(User user) {
        return userMapper.updateUserImgByUserId(user);
    }

    /**
     * 新增员工列表
     *
     * @param user
     * @throws Exception
     */
    public Map addUser(User user, int roleId) {
        Map returenMap = new HashMap();
        try {
            User userPhone = userMapper.queryUserPhone(user.getUserPhone());
            User userAccount = userMapper.queryUserName(user.getUserAccount());
            if (userAccount != null) {
                returenMap.put("code", 1);
                returenMap.put("msg", "用户名已存在,请更换用户名再试");
                returenMap.put("userId", userAccount.getUserId());
                returenMap.put("state", -1);
                return returenMap;
            } else if (userPhone != null) {
                user.setUserId(userPhone.getUserId());
                returenMap.put("code", 1);
                returenMap.put("msg", "手机号已存在,请更换手机号再试");
                returenMap.put("userId", userPhone.getUserId());
                returenMap.put("state", -1);
                return returenMap;
            } else {
                //添加用户步骤
                //1.添加用户
                userMapper.insert(user);

                //添加授权
                //删除授权
                userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", user.getUserId()));
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);

                //添加会员资金表
                UserMoney userMoney = new UserMoney();
                userMoney.setAddTime(new Date());
                userMoney.setUserId(user.getUserId());
                userMapper.addUserMoney(userMoney);
                returenMap.put("code", 1);
                returenMap.put("msg", "添加成功");
                returenMap.put("state", 1);
                return returenMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            returenMap.put("code", 0);
            returenMap.put("msg", "出错了");
            returenMap.put("state", -1);
        }

        return returenMap;
    }

    /**
     * 根据员工编号查询员工信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public User queryUserByUserId(int userId) {
        return userMapper.queryUserByUserId(userId);
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUserPassWord(User user) {
        return userMapper.updateUserPassWord(user);
    }


    /**
     * 修改员工
     *
     * @param user
     * @throws Exception
     */
    public int updateUserByUserId(User user, int roleId) {
        User uuu = userMapper.queryUserPhone(user.getUserPhone());
        user.setUserId(uuu.getUserId());
        User user1 = userMapper.queryUserName(user.getUserAccount());
        User user2 = userMapper.queryUserPhone(user.getUserPhone());
        if (user1 != null && !user.getUserAccount().equals(user1.getUserAccount())) {
            return 3;
        }
        if (user2 != null && !user.getUserPhone().equals(user2.getUserPhone())) {
            return 2;
        }
        userMapper.updateById(user);
        // 更新角色
        userMapper.updateUserRole(user.getUserId(), roleId);
        return 1;
    }

    /**
     * 修改状态
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public int updateUserType(int userId, int type){
        return userMapper.updateUserType(userId, type);
    }

    @Override
    public int updateUserMony(int userId, BigDecimal pice, String desc){
        //查询是否有该会员的资金记录，有更新  没有  新增
        UserMoney userMoney = userMapper.getuserMony(userId);
        int i = 0;
        if (userMoney != null) {
            //如果余额小于1元, 且是扣款操作
            if (DataUtil.getStrDoubleToInt(userMoney.getAvailableMoney()) < 1 && DataUtil.getDouble(pice) < 0) {
                return 0;
            }
            //更新
            i = userMapper.upadetUserMony(userId, pice);
        } else {
            //是扣款操作
            if (DataUtil.getDouble(pice) < 0) {
                return 0;
            }
            //新增
            UserMoney userMoneyTwo = new UserMoney();
            userMoneyTwo.setAddTime(new Date());
            userMoneyTwo.setAvailableMoney(pice);
            userMoneyTwo.setTotalMoney(pice);
            userMoneyTwo.setUserId(userId);
            i = userMapper.addUserMony(userMoneyTwo);
        }
        //记录会员流水
        if (i == 1) {
            UserWaterMoney userWaterMoney = new UserWaterMoney();
            userWaterMoney.setUserId(userId);
            userWaterMoney.setUserWaterTime(new Date());
            userWaterMoney.setUserWaterIp(DataUtil.getIp());
            userWaterMoney.setUserWaterDesc(desc);
            if (DataUtil.getDouble(pice) > 0) {
                userWaterMoney.setUserWaterMoney(DataUtil.getBigDecimal(pice));
                userWaterMoney.setUserWaterType(2);
                i = userMapper.addUserMonyWater(userWaterMoney);
            } else if (DataUtil.getDouble(pice) < 0) {
                Double d = getDouble(pice) * -1;
                userWaterMoney.setUserWaterMoney(DataUtil.getBigDecimal(d));
                userWaterMoney.setUserWaterType(1);
                i = userMapper.addUserMonyWater(userWaterMoney);
            }
            return i;
        } else {
            i = 0;
        }
        return i;
    }
}
