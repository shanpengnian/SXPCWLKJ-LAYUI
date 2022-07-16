package com.sxpcwlkj;

public class ThreadTest extends Thread  {
    private int ticket = 5;
    @Override
    public void run() {
        //  没有加锁
        //        while (true) {
        //            System.out.println("Thread ticket = " + ticket--);
        //            if (ticket < 0) {
        //                break;
        //            }
        //        }

        //加锁后
        if (ticket > 0) {
            synchronized (this) {
                if (ticket > 0) {
                    while (true) {
                        System.out.println("Thread:" + Thread.currentThread().getName() + "--Thread ticket = " + ticket--);
                        if (ticket < 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public static  void main(String ars[]){

        ThreadTest t1 = new ThreadTest();
        new Thread(t1).start();
        new Thread(t1).start();
    }


}
