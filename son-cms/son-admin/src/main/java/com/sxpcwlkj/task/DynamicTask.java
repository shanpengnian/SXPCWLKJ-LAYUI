package com.sxpcwlkj.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxpcwlkj.entity.Tasks;
import com.sxpcwlkj.mapper.TasksMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 动态定时器
 * 此方案的只能动态改变已经启动定时器的  执行周期
 * 不能添加新的任务和启动/暂停/停止任务
 */
@Slf4j
//1.主要用于标记配置类
//@Configuration
// 2.开启定时任务
//@EnableScheduling
public class DynamicTask implements SchedulingConfigurer {



    /**
     * @fileName：Schedule
     * @createTime：2019/5/14 17:16
     * @author：
     * @version：
     * @description：基于注解(@Scheduled)的简单定时器demo cron表达式语法:[秒] [分] [小时] [日] [月] [周] [年]
     * @Scheduled(fixedDelay = 5000) //上一次执行完毕时间点之后5秒再执行
     * @Scheduled(fixedDelayString = "5000") //上一次执行完毕时间点之后5秒再执行
     * @Scheduled(fixedRate = 5000) //上一次开始执行时间点之后5秒再执行
     * @Scheduled(initialDelay=1000, fixedRate=5000) //第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
     */

    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void testTasks() {

        System.err.println("基于注解(@Scheduled)的简单定时器demo: " + LocalDateTime.now());
    }

    @Autowired
    private TasksMapper tasksMapper;

    /**
     *        QueryWrapper<Tasks> wrapper = new QueryWrapper<Tasks>()
     *                 .eq("task_state", 1)                   //状态启用
     *                 .gt("task_num", 0)                     //次数大于0
     *                 .le("task_start_time", new Date())         //当前时间>= 开始时间
     *                 .ge("task_end_time", new Date())           //当前时间<= 结束时间
     *                 .orderByDesc("task_sort");              //排序
     *         List<Tasks> taskSList = tasksMapper.selectList(wrapper);
     */


    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        QueryWrapper<Tasks> wrapper = new QueryWrapper<Tasks>()
                //项目启动时候 现有的定时器全部注入加载
                //.eq("task_state", 1)                   //状态启用
                //.gt("task_num", 0)                     //次数大于0
                //.le("task_start_time", new Date())         //当前时间>= 开始时间
                //.ge("task_end_time", new Date())           //当前时间<= 结束时间
                .orderByDesc("task_sort");              //排序
        List<Tasks> taskSList = tasksMapper.selectList(wrapper);

        for (int i = 0; i < taskSList.size(); i++) {
            Tasks tasks = taskSList.get(i);
            taskRegistrar.addTriggerTask(new Runnable() {
                @Override
                public void run() {
                    // 任务逻辑
                    if (isexecute(tasks)) {
                        tasks.setTaskNum(tasks.getTaskNum()-1);
                        tasks.setTaskUpdateTime(new Date());
                        tasksMapper.updateById(tasks);
                        executeTask(tasks.getTaskFunction());
                    }
                }
            }, new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    //任务触发，可修改任务的执行周期
                    CronTrigger trigger = new CronTrigger(tasksMapper.selectById(tasks.getTaskId()).getTaskCode());
                    Date nextExec = trigger.nextExecutionTime(triggerContext);
                    return nextExec;
                }
            });
        }

    }

    /**
     * 检查是否有效
     *
     * @param tasks
     * @return
     */
    private boolean isexecute(Tasks tasks) {
        QueryWrapper<Tasks> wrapper = new QueryWrapper<Tasks>()
                .eq("task_state", 1)                   //状态启用
                .gt("task_num", 0)                     //次数大于0
                .le("task_start_time", new Date())         //当前时间>= 开始时间
                .ge("task_end_time", new Date())           //当前时间<= 结束时间
                .eq("task_id", tasks.getTaskId());
        Tasks task = tasksMapper.selectOne(wrapper);
        if (task != null) {
            return true;
        }
        return false;
    }

    /**
     * 任务指派执行
     * @param function
     */
    private void executeTask(String function) {
        System.out.println(" 时间：【" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()) + "】" + "执行方法" + function);

    }

}