package com.sxpcwlkj.config;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;


import com.sun.management.OperatingSystemMXBean;
import com.sxpcwlkj.entity.SystemInfo;
import com.sxpcwlkj.utils.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysInfoConfig {


    public SystemInfo getAll(SystemInfo systemInfo){
        MemoryMXBean mxb = ManagementFactory.getMemoryMXBean();
        Properties props=System.getProperties(); //系统属性
        try {
            systemInfo.setSysName(EnumUtil.SysIifo.SYSNAME.getValue());                      //后台系统名称
            systemInfo.setSysVersion(EnumUtil.SysIifo.SYSVERSION.getValue());                //后台系统版本
            systemInfo.setSysXiTong(System.getProperty("os.name"));                           //系统架构
            systemInfo.setGetSysXiTongVersion(System.getProperty("os.version"));              //系统版本
            systemInfo.setSysCpu(DataUtil.getString(Runtime.getRuntime().availableProcessors())+"核");      //CPU核数
            systemInfo.setSysCupFuzai(getSystemCpuLoad());                                    //获取系统cpu负载
            systemInfo.setSysJvmlv(getProcessCpuLoad());                                      //jvm线程负载
            systemInfo.setSysNeicun(getTotalMemorySize());                                    //系统总内存
            systemInfo.setSysShenyuNeicun(getUsedMemory());                                   //系统已使用内存
            systemInfo.setSysNeicunlv(BigDecimalUtil.div(getUsedMemory()+"",getTotalMemorySize()+""));//内存使用率
            systemInfo.setSysYanyan(EnumUtil.SysIifo.LANGUNGER.getValue());                   //系统语言
            systemInfo.setSysIp(IpUtils.getHostIp()+":"+IpUtils.getHttpPort());               //系统IP+端口
            systemInfo.setSysJvmMaxneicun(mxb.getHeapMemoryUsage().getMax() / 1024 / 1024 + "MB");//JVM内存
            systemInfo.setSysJvm(props.getProperty("java.vm.specification.name"));          //JVM信息
            systemInfo.setSysJvmVersion(props.getProperty("java.vm.specification.version"));//JVM版本
            int j = (int)Runtime.getRuntime().freeMemory()/1024;                            //Java 虚拟机中的空闲内存量
            systemInfo.setSysJvmneicunKonxian(j+"");
            int i = (int)Runtime.getRuntime().totalMemory()/1024;                           //Java 虚拟机中的内存总量,以字节为单位
            systemInfo.setSysJvmMaxneicun(i+"");
            systemInfo.setSysShiqu(String.valueOf(TimeZone.getDefault().getID()));          //时区
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return systemInfo;
    }

    /**
     * 获得Linux cpu使用率
     * @return float efficiency
     * @throws IOException
     * @throws InterruptedException
     */
    public static float getCpuInfo() throws IOException, InterruptedException {

        try {
            File file = new File("/proc/stat");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            StringTokenizer token = new StringTokenizer(br.readLine());
            token.nextToken();
            long user1 = Long.parseLong(token.nextToken() + "");
            long nice1 = Long.parseLong(token.nextToken() + "");
            long sys1 = Long.parseLong(token.nextToken() + "");
            long idle1 = Long.parseLong(token.nextToken() + "");


            Thread.sleep(1000);


            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
            token = new StringTokenizer(br.readLine());
            token.nextToken();
            long user2 = Long.parseLong(token.nextToken());
            long nice2 = Long.parseLong(token.nextToken());
            long sys2 = Long.parseLong(token.nextToken());
            long idle2 = Long.parseLong(token.nextToken());


            return (float) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) * 100
                    / (float) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1
                    + sys1 + idle1));
        }catch (Exception e){

        }
       return 0;
    }

    /**
     * 获取系统cpu负载
     * @return
     */
    public static double getSystemCpuLoad(){
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double SystemCpuLoad = osmxb.getSystemCpuLoad();
        return SystemCpuLoad;
    }

    /**
     * 获取jvm线程负载
     * @return
     */
    public static double getProcessCpuLoad(){
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double ProcessCpuLoad = osmxb.getProcessCpuLoad();
        return ProcessCpuLoad;
    }

    /**
     * 获取总的物理内存
     * @return
     */
    public static long getTotalMemorySize(){
        int kb = 1024*1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        return totalMemorySize;
    }

    /**
     * 获取剩余的物理内存
     * @return
     */
    public static long getFreePhysicalMemorySize(){
        int kb = 1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
        return freePhysicalMemorySize;
    }

    /**
     * 获取已使用的物理内存
     * @return
     */
    public static long getUsedMemory(){
        int kb = 1024*1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        //已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
        return usedMemory;
    }


    public static void main(String[] res){

        try {
            log.info(getUsedMemory()+"");
            log.info(getTotalMemorySize()+"");
            log.info(BigDecimalUtil.div(getUsedMemory()+"",getTotalMemorySize()+"")+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

