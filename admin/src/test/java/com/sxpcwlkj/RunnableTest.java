package com.sxpcwlkj;

public class RunnableTest {
    public static void main(String[] args) {
        RunnableTest1 runnableTest1=new RunnableTest1();
        RunnableTest2 runnableTest2=new RunnableTest2();
        Thread thread1 = new Thread(runnableTest1);
        Thread thread2 = new Thread(runnableTest2);
        thread1.start();
        thread2.start();
    }
}


class RunnableTest1 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner1运行状态——————————" + i);
        }
    }
}

class RunnableTest2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner2运行状态——————————" + i);
        }
    }
}

