package com.sxpcwlkj.utils;

public class SysouUtil {
    public static void sysou(Object o){
        if(true){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
           /* for(StackTraceElement s : stackTrace){
                System.out.println("控制台打印：" + s.getClassName() + " -" + s.getFileName() + "-" + s.getMethodName() + "-" + s.getLineNumber() + ":"+o.toString());
            }*/

            System.out.println("控制台打印：" +stackTrace[2].getFileName() + "-" + stackTrace[2].getMethodName() + "-" + stackTrace[2].getLineNumber() + "=> "+o.toString());


        }

    }
}
