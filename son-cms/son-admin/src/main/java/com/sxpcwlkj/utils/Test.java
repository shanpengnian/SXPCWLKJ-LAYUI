package com.sxpcwlkj.utils;

import java.util.concurrent.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * 有返回值的线程
 */
@SuppressWarnings("unchecked")
public class Test {
    public static void main(String[] args) throws ExecutionException,
            InterruptedException {

        //1：创建几个任务
        int taskSize = 2;
        //2：创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        //3：创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            //4：创建任务 可以传递参数
            Callable c = new MyCallable(i + " ");
            //5：pool.submit(c) 执行任务
            //6：并获取call方法返回的 Future对象
            Future f = pool.submit(c);
           //7：添加到集合
            list.add(f);
        }
        //8：关闭线程池
        pool.shutdown();

        //9：变量返回的任务对象 用 f.get() 获取
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }

    }
}

class MyCallable implements Callable<Object> {
    private String taskNum;

    MyCallable(String taskNum) {
        this.taskNum = taskNum;
    }

    public Object call() throws Exception {
        System.out.println(">>>" + taskNum + "任务启动");
        Date dateTmp1 = new Date();
        //Thread.sleep(1000);
        Date dateTmp2 = new Date();
        long time = dateTmp2.getTime() - dateTmp1.getTime();
        System.out.println(">>>" + taskNum + "任务终止");
        return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}