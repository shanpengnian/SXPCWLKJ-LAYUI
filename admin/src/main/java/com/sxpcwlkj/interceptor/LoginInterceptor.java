package com.sxpcwlkj.interceptor;

import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.annotation.KeyToken;
import com.sxpcwlkj.entity.Function;
import com.sxpcwlkj.entity.Role;
import com.sxpcwlkj.entity.User;
import com.sxpcwlkj.mapper.FunctionMapper;
import com.sxpcwlkj.mapper.RoleMapper;
import com.sxpcwlkj.service.LoginService;
import com.sxpcwlkj.service.ObjectEntityService;
import com.sxpcwlkj.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ObjectEntityService objectEntityService;
    @Autowired
    private FunctionMapper functionMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 注解的使用
     *
     * Shiro 不进行拦截只是不进行 登录拦截
     * 所有方法都经过 HandlerInterceptor 拦截器
     * @PassToken 注解则不进行Shiro验证, 前提是 方法上面有该注解且 注解的值为  true
     * @LoginToken 没有@PassToken 但是有@LoginToken,意思就是进行登录验证当前状态是否是登录状态,且Token是否正确
     *             如果没有登录sessionkeyid 是系统直接进入login方法
     *             如果有sessionkeyid  当时账号密码 或Token不正确  则被拦截
     */


    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        //response.setHeader("Access-Control-Allow-Origin", "*");

//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
//
//        response.setHeader("Access-Control-Max-Age", "3600");
//
//        response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查是否进行Key校验
        if (method.isAnnotationPresent(KeyToken.class)) {
            /*验证appkey*/
            String tokenKet = DataUtil.getString(request.getHeader("token"));
            Map<String, Object> data = TokenUtil.getpareJwt(tokenKet);
            if (data == null) {
                throw new RuntimeException("非法请求");
            }
            request.setAttribute("objectId", data.get("objectId"));
            return objectEntityService.isToken(data);
        }

        //检查是否有 @PassToken 注释,进行逻辑验证,没有跳过
        if (method.isAnnotationPresent(AuthLoginAnnotation.class)) {
            AuthLoginAnnotation authLoginAnnotation = method.getAnnotation(AuthLoginAnnotation.class);
            if (!authLoginAnnotation.login()) {
                // 值为:false时 不进行登录验证 放行
                return true;
            } else {

                //进行登录验证
                String token = request.getHeader("token");// 从 http 请求头中取出 token
                if (token == null || "".equals(token)) {
                    token = DataUtil.getString(request.getParameter("token"));

                }else {
                // return true;
                }

                if (token == null) {
                    response.setStatus(404);
                    log.error("缺少token,未登录");
                    return false;
                } else {
                    Map<String, Object> getMap = TokenUtil.getpareJwt(token);
                    if (getMap == null) {
                        response.setStatus(404);
                        log.error("token认证失败");
                        return false;
                    } else {
                        //登录成功
                        //默认USER 对象
                        int id = DataUtil.getInt(getMap.get("id"));
                        //redisUtil.del("user_redis_id_"+id);
                        User isRedisUser = redisUtil.getUserKey(id + "");
                        if (isRedisUser == null) {
                            User user = loginService.getUserById(id);
                            if (user == null) {
                                throw new RuntimeException("用户不存在，请重新登录");
                            }
                            user.setUserPassword("");
                            List<Role> list = roleMapper.lestUserRoleOne(user.getUserId());
                            user.setStrRoles(JsonUtil.entityToJsonString(list));
                            List<Function> funlist = functionMapper.listFunctionForUserId(user.getUserId());
                            user.setStrfunction(JsonUtil.entityToJsonString(funlist));
                            redisUtil.setUserKey(user);
                            if (authLoginAnnotation.authority()) {
                                boolean state = false;
                                for (Function f : funlist) {
                                    if (authLoginAnnotation.authorityCode().equals(f.getFunCode())){
                                        state = true;
                                        break;
                                    }
                                }
                                if(state){
                                    return state;
                                }else {
                                    response.setStatus(403);
                                    log.error("无权限");
                                    return false;
                                }
                            }
                        } else {
                            if (authLoginAnnotation.authority()) {
                                List<Function> functionList = isRedisUser.getFunctionList();
                                boolean state = false;
                                for (Function f : functionList) {
                                    //SysouUtil.sysou(authLoginAnnotation.authorityCode());
                                    //SysouUtil.sysou(f.getFunCode());
                                    //SysouUtil.sysou(authLoginAnnotation.authorityCode().equals(f.getFunCode()));
                                    if (authLoginAnnotation.authorityCode().equals(f.getFunCode())){
                                        state = true;
                                        break;
                                    }
                                }
                                if(state){
                                    return state;
                                }else {
                                    response.setStatus(403);
                                    log.error("无权限");
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }


}
