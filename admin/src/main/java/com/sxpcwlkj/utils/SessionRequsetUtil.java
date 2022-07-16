package com.sxpcwlkj.utils;

import com.alibaba.druid.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ：shanpegnian - http://www.sxpcwlkj.com
 * @version ：1.0
 * @description ： com.sxpcwlkj.common.utils
 * @date ： 2019/3/10
 */
public class SessionRequsetUtil {
    //================================================session==============================================================

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession(false);
    }

    /**
     * 设置session的Attribute
     *
     * @param name
     * @param value
     */
    public static void setSessionAttribute(String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setAttribute(name, value);
        }
    }

    /**
     * 获取session中的Attribute
     *
     * @param name
     * @return
     */
    public static Object getSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession().getAttribute(name);
    }

    /**
     * 删除session中的Attribute
     *
     * @param name
     */
    public static void removeSessionAttribute(String name) {
        getRequest().getSession().removeAttribute(name);
    }

    //================================================request==============================================================

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 设置request的Attribute
     *
     * @param name
     * @param value
     */
    public static void setRequestAttribute(String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(name, value);
        }
    }

    /**
     * 获取request中的Attribute
     *
     * @param name
     * @return
     */
    public static Object getRequestAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getAttribute(name);
    }

    //======================================================= Token  ===========================================================

    /**
     * 生成Token  放入 session
     *
     * @return
     */
    public static String getTokenPutSession() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            getRequest().getSession().setAttribute("token", encoder.encode(md5));
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 移除token
     */
    public static void getTokenRemove() {
        getRequest().getSession().removeAttribute("token");
    }

    /**
     * 判断 客户端传入的token 与  session  的  token  是否一致
     *
     * @param reqToken
     * @return
     */
    public static boolean getTokenIsEqual(String reqToken) {
        String token_client = getRequest().getParameter(reqToken);
        if (StringUtils.isEmpty(token_client)) {
            return false;
        }
        String token_server = (String) getRequest().getSession().getAttribute("token");
        if (StringUtils.isEmpty(token_server)) {
            return false;
        }

        if (!token_server.equals(token_client)) {
            return false;
        }

        return true;
    }

    //======================================================= Outher ===========================================================

    /**
     * 获取绝对路径
     *
     * @return
     */
    public static String getRealRootPath() {
        return getRequest().getServletContext().getRealPath("/");
    }

    /**
     * 获取ip
     *
     * @return
     */
    public static String getIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return request.getRemoteAddr();
        }
        return null;
    }

    /**
     * 获取上下文path
     *
     * @return
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }


    /**
     * 获取头部
     *
     * @return
     */
    public static String getHeanderFrom() {
        HttpServletRequest req = getRequest();
        Map<String, String> mapHeader = new HashMap<String, String>();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            mapHeader.put(key, value);
        }
        String aa = mapHeader.get("from");
        return aa;
    }

    /**
     * 获取当前访问域名
     * @return
     */
    public static String getRequestUrl(){
        HttpServletRequest req = getRequest();
        StringBuffer url = req.getRequestURL();
        String tempContextUrl = url.delete(url.length() - req.getRequestURI().length(), url.length()).append("/").toString();
        URL urls = null;
        try {
            urls = new URL(tempContextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String host = urls.getHost();//获取主机名
        return host;
    }
}
