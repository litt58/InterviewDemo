package com.jzli.async.concurrent;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/4/8
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：join()方法会使当前线程等待调用join()方法的线程结束后才能继续执行
 * ========================================================
 */
public class JoinTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new JoinTask());
        thread.start();

        for (int i = 0; i < 20; i++) {
            System.out.println("主线程第" + i + "次执行！");
            if (i >= 2) {
                try {
                    // t1线程合并到主线程中，主线程停止执行过程，转而执行t1线程，直到t1执行完毕后继续。
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class JoinTask implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("线程1第" + i + "次执行！");
        }
    }
}