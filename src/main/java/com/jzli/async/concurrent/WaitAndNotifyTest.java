package com.jzli.async.concurrent;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/4/8
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class WaitAndNotifyTest {
    // 在多线程间共享的对象上使用wait
    private String[] shareObj = {"true"};

    public static void main(String[] args) {
        WaitAndNotifyTest test = new WaitAndNotifyTest();
        ThreadWait threadWait1 = test.new ThreadWait("wait thread1");
        threadWait1.setPriority(2);
        ThreadWait threadWait2 = test.new ThreadWait("wait thread2");
        threadWait2.setPriority(3);
        ThreadWait threadWait3 = test.new ThreadWait("wait thread3");
        threadWait3.setPriority(4);

        ThreadNotify threadNotify = test.new ThreadNotify("notify thread");

        threadNotify.start();
        threadWait1.start();
        threadWait2.start();
        threadWait3.start();
    }


    class ThreadWait extends Thread {

        public ThreadWait(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (shareObj) {
                while ("true".equals(shareObj[0])) {
                    System.out.println(Thread.currentThread().getName() + "开始等待");
                    long startTime = System.currentTimeMillis();
                    try {
                        shareObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName()
                            + "等待时间为：" + (endTime - startTime));
                }
            }
            System.out.println(Thread.currentThread().getName() + "等待结束");
        }
    }

    class ThreadNotify extends Thread {

        public ThreadNotify(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                // 给等待线程等待时间
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (shareObj) {
                System.out.println(Thread.currentThread().getName() + "开始准备通知");
                shareObj[0] = "false";
                shareObj.notifyAll();
                System.out.println(Thread.currentThread().getName() + "通知结束");
            }
            System.out.println(Thread.currentThread().getName() + "运行结束");
        }
    }
}

