package com.sxpcwlkj.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sxpcwlkj.annotation.AuthLoginAnnotation;
import com.sxpcwlkj.entity.Log;
import com.sxpcwlkj.entity.Tasks;
import com.sxpcwlkj.mapper.TasksMapper;
import com.sxpcwlkj.service.LogService;
import com.sxpcwlkj.utils.DataUtil;
import com.sxpcwlkj.utils.EnumUtil;
import com.sxpcwlkj.utils.JsonResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/task")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {
    @Autowired
    private TasksMapper tasksMapper;
    @Autowired
    private LogService logService;
    /**
     * 定时任务
     *
     * @return
     */

    @GetMapping("/listPage")
    @AuthLoginAnnotation(authorityCode = "/task/listPage")
    public JsonResultPage<Object> selectTaskList(int currpage) {
        JsonResultPage<Object> resultJson = new JsonResultPage<Object>();

        IPage<Tasks> tasksPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Tasks>(currpage, DataUtil.getInt(EnumUtil.PageSize.PAGE_NUMBER_TEN.getValue()));//参数一是当前页，参数二是每页个数
        tasksPage = tasksMapper.selectPage(tasksPage, null);
        //List<Tasks> list = tasksPage.getRecords();
        //list.forEach(System.out::println);
        resultJson.setCount(DataUtil.getInt(tasksPage.getTotal()));                             //总条数
        resultJson.setCurrpage(currpage);                                                       //当前页
        resultJson.setShowSize(DataUtil.getInt(EnumUtil.PageSize.PAGE_NUMBER_TEN.getValue()));  //每页显示条数
        resultJson.setData(tasksPage.getRecords());                                             //结果list
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());                                  //一级状态
        resultJson.setState(1);                                                                 //二级状态
        resultJson.setMsg("查询成功");
        return resultJson;

    }

    /**
     * 添加定时器
     * @param tasks
     * @return
     */
    @GetMapping("/addtask")
    @AuthLoginAnnotation(authorityCode = "/task/addtask")
    public JsonResultPage<Object> addTask(HttpServletRequest req, HttpServletResponse resp, Tasks tasks){
        JsonResultPage<Object> resultJson=new JsonResultPage<>();
        tasksMapper.insert(tasks);
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());                                  //一级状态
        resultJson.setState(1);                                                                 //二级状态
        resultJson.setMsg("添加成功");
        logService.addLog(new Log("增,增加["+tasks.getTaskName()+"]定时器"));
        return resultJson;
    }

    /**
     * 定时器回显
     * @param req
     * @param resp
     * @param taskId
     * @return
     */
    @GetMapping("/selectTaskOne")
    @AuthLoginAnnotation(authorityCode = "/task/selectTaskOne")
    public JsonResultPage<Object> selectTaskOne(HttpServletRequest req, HttpServletResponse resp, int taskId){
        JsonResultPage<Object> resultJson=new JsonResultPage<>();
        Tasks tasks=tasksMapper.selectById(taskId);
        if(tasks!=null){
            resultJson.setData(tasks);
            resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());                                  //一级状态
            resultJson.setState(1);                                                                 //二级状态
            resultJson.setMsg("查询成功");
        }else {
            resultJson.setCode(EnumUtil.Result.FAILURE.getValue());                                  //一级状态
            resultJson.setState(-1);                                                                 //二级状态
            resultJson.setMsg("查询失败");
        }
        return resultJson;
    }

    /**
     * 更新定时器
     * @param req
     * @param resp
     * @param tasks
     * @return
     */

    @GetMapping("/updateTask")
    @AuthLoginAnnotation(authorityCode = "/task/updateTask")
    public JsonResultPage<Object> updateTask(HttpServletRequest req, HttpServletResponse resp, Tasks tasks){
        JsonResultPage<Object> resultJson=new JsonResultPage<>();
        Tasks Istasks=tasksMapper.selectById(tasks.getTaskId());
        if(Istasks==null){
            resultJson.setCode(EnumUtil.Result.FAILURE.getValue());                                  //一级状态
            resultJson.setState(-1);                                                                 //二级状态
            resultJson.setMsg("更新失败");
            return resultJson;
        }
        logService.addLog(new Log("改,修改["+Istasks.getTaskName()+"]定时器"));
        tasksMapper.updateById(tasks);
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());                                  //一级状态
        resultJson.setState(1);                                                                 //二级状态
        resultJson.setMsg("更新成功");
        return resultJson;
    }


    @GetMapping("/deleteTask")
    @AuthLoginAnnotation(authorityCode = "/task/deleteTask")
    public JsonResultPage<Object> deleteTask(HttpServletRequest req, HttpServletResponse resp, int taskId){
        JsonResultPage<Object> resultJson=new JsonResultPage<>();
        Tasks Istasks=tasksMapper.selectById(taskId);
        if(Istasks==null){
            resultJson.setCode(EnumUtil.Result.FAILURE.getValue());                                  //一级状态
            resultJson.setState(-1);                                                                 //二级状态
            resultJson.setMsg("删除失败");
            return resultJson;
        }
        logService.addLog(new Log("删,删除["+Istasks.getTaskName()+"]定时器"));
        tasksMapper.deleteById(taskId);
        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());                                  //一级状态
        resultJson.setState(1);                                                                 //二级状态
        resultJson.setMsg("删除成功");
        return resultJson;
    }

    /**
     * 更新定时器状态
     * @param req
     * @param resp
     * @param state
     * @param taskId
     * @return
     */

    @GetMapping("/updateTaskState")
    @AuthLoginAnnotation(authorityCode = "/task/updateTaskState")
    public JsonResultPage<Object> updateTaskState(HttpServletRequest req, HttpServletResponse resp, int state, @RequestParam(value = "taskId[]") int[] taskId ){
        JsonResultPage<Object> resultJson=new JsonResultPage<>();

        for(int i=0;i<taskId.length;i++){
            Tasks Istasks=tasksMapper.selectById(taskId[i]);
            if(Istasks!=null){
                Istasks.setTaskState(state);
                tasksMapper.updateById(Istasks);
                logService.addLog(new Log("改,修改["+Istasks.getTaskName()+"]转态为:"+(state==1?"启用":"禁用")));
            }
        }

        resultJson.setCode(EnumUtil.Result.SUCCESS.getValue());                                  //一级状态
        resultJson.setState(1);                                                                 //二级状态
        resultJson.setMsg("设置成功");
        return resultJson;
    }

}
