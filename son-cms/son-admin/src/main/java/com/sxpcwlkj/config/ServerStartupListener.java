package com.sxpcwlkj.config;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.config
 * @date 2020/12/4
 */
import com.sxpcwlkj.utils.DateUtil;
import com.sxpcwlkj.utils.SessionRequsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.Date;

/**
 * ServletContext可以初始化String类型的参数,spring框架启动之前执行
 * 配置需要在web.xml配置监听
 * <listener>
 * <listener-class>com.atguigu.atcrowdfunding.web.ServerStartupListener</listener-class>
 * </listener>
 */
@Configuration
public class ServerStartupListener implements ServletContextListener {
    private static final String KEY = "log4j.configurationFile";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 将web应用名称（路径）保存到application范围中
        ServletContext application = sce.getServletContext();
        String path = application.getContextPath();
        application.setAttribute("ctx", path);
        application.setAttribute("url", "https://www.jfgapp.com/");
        application.setAttribute("urlsx", "jfgapp.com");
        application.setAttribute("name", "集福购");
        application.setAttribute("sxpcwl", "陕西品创网络");
        application.setAttribute("qq", "1802221702");
        application.setAttribute("phone", "029-85578345");

        Date startTime= DateUtil.getStrToDate("2018-02-30");
        long[] d = DateUtil.getDistanceTime(new Date(), startTime);  //{天, 时, 分, 秒}
        long day=d[0];
        int yer=(int)day/365;
        application.setAttribute("day", yer);
        int moth=(int)day%365;
        if(moth>30){
            moth=moth/30;
        }
        application.setAttribute("moth", moth);
    }

    private String logMy(){
        //获取项目根路径下的upload绝对路径  ===> ps: /upload

        String objectPath = SessionRequsetUtil.getRequest().getSession().getServletContext().getRealPath("/") + "logs";
        objectPath= objectPath.replaceAll( "\\\\","/");
        File fileUpload = new File(objectPath);
        if (!fileUpload.exists()) {
            // 经过此方法后,项目根路径下的upload文件夹一定是存在的
            fileUpload.mkdir();
        }
        return objectPath;
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
