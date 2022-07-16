package com.sxpcwlkj.service.impl;

import com.sxpcwlkj.common.Page;
import com.sxpcwlkj.controller.admin.UserController;
import com.sxpcwlkj.entity.Log;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.mapper.LogMapper;
import com.sxpcwlkj.mapper.UserMapper;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addLog(Log log) {
//        User user=userMapper.queryUserByUserId(UserController.userId);
//        String str="["+user.getUserId()+"]"+user.getUserAccount();
//        str=str+log.getLogDesc();
        logMapper.addLog(log);
    }

    /**
     * 日志分页
     * @param page
     * @return
     */
    public Page queryLogPage(Page page) throws Exception{
        StringBuffer countSql = new StringBuffer();
        countSql.append("SELECT count(0) FROM p_log l INNER JOIN p_user u ON l.user_id = u.user_id ");
        StringBuffer selectSql = new StringBuffer();
        selectSql.append("SELECT l.*,u.user_name AS userName FROM p_log l INNER JOIN p_user u ON l.user_id = u.user_id ");
		/*String keyword = DataUtil.getString(page.getCondition().get("keyword"));
		if(""!=keyword) {
			countSql.append(" and (u.user_name like '%"+keyword+"%' or u.user_phone like '%"+keyword+"%' or u.user_email like '%"+keyword+"%')");
			selectSql.append(" and (u.user_name like '%"+keyword+"%' or u.user_phone like '%"+keyword+"%' or u.user_email like '%"+keyword+"%')");
		}*/
        String selectTime = DataUtil.getString(page.getCondition().get("selectTime"));
        if(""!=selectTime){
            countSql.append(" and l.`log_time` like  CONVERT('"+selectTime+"%', CHAR(20)) ");
            selectSql.append(" and l.`log_time` like CONVERT('"+selectTime+"%', CHAR(20)) ");
        }
        int iscoll = DataUtil.getInt(page.getCondition().get("iscoll"));
        if(iscoll == 0) {
            countSql.append(" where is_collect =0 ");
            selectSql.append(" where is_collect =0  ");
        }else {
            countSql.append(" where is_collect = 1");
            selectSql.append(" where is_collect = 1");
        }
        selectSql.append(" ORDER BY `log_time` DESC ");
        page = Page.PageUtil(page, selectSql.toString(), countSql.toString());						//调用分页工具类
        page.setItem(logMapper.queryLogPage(page.getSelectCountSql()));		//查询信息
        page.setTotalRow(logMapper.queryLogCount(page.getSelectCountSql()));	//查询总条数

        return Page.PageUtil(page);
    }

}
