package com.example.demo20210221.utils;

import java.util.concurrent.*;

public class ThreadUtils {
    public static void main(String[] args) {
        //getFixedNumberThreadPool();
        //getCacheableThreadPool();
        //getFixedLengthThreadPool();
        getSingleThreadPool();
    }

    /**
     * 创建一个固定线程数的线程池，并给线程分配任务
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     */
    public static void getFixedNumberThreadPool(){
        ExecutorService fixThreadPool = Executors.newFixedThreadPool(5);
        for(int i=1;i<=5;i++){
            final int task=i;
            fixThreadPool.execute(new Runnable(){
                @Override
                public void run(){
                    for(int j=1;j<=5;j++){
                        System.out.println(Thread.currentThread().getName()+"  "+"task:"+task+"times:"+j);
                    }
                }
            });
        }
        fixThreadPool.shutdown();
    }
    /**
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
     */
    public static void getCacheableThreadPool(){
        ExecutorService cashedThreadPool=Executors.newCachedThreadPool();
        for(int i=1;i<=100;i++){
            final int task=i;
            cashedThreadPool.execute(new Runnable(){
                @Override
                public void run(){
                    for(int j=1;j<=5;j++){
                        System.out.println(Thread.currentThread().getName()+"  "+"task:"+task+"times:"+j);
                    }
                }
            });
        }
        cashedThreadPool.shutdown();
    }
    /**
     * newScheduledThreadPool创建一个定长线程池，支持定时及周期性任务执行。
     */
    public static void getFixedLengthThreadPool(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {
            public void run() {
                System.out.println("delay 10 seconds");
            }
        }, 10, TimeUnit.SECONDS);
        scheduledThreadPool.shutdown();
    }
    /**
     * newSingleThreadExecutor创建一个单线程化的线程池，
     * 它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     */
    public static void getSingleThreadPool(){
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+" "+"task"+index);
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        singleThreadExecutor.shutdown();
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("执行子线程...");
    }
}

class MyCallable implements Callable {
    int i = 0;
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"  i的值："+ i);
        return i++; //call方法可以有返回值
    }
}

class MyThread extends Thread {
    int i;
    public MyThread(int i){
        super();
        this.i = i;
    }
    @Override
    public void run(){
        System.out.println(i);
    }
}

class Test1 {
    public static void main(String[] args) {
        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("主线程运行结束!");
    }
}

class Test2 {
    public static void main(String[] args) {
        Callable callable = new MyCallable();
        for (int i = 0; i < 10; i++) {
            FutureTask task = new FutureTask(callable);
            new Thread(task,"子线程"+ i).start();
            try {
                //获取子线程的返回值
                System.out.println("子线程返回值："+task.get() + "\n");
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Test3 {
    public static void main(String[] args) {
        Thread thread1 = new MyThread(1);
        Thread thread2 = new MyThread(2);
        Thread thread3 = new MyThread(3);
        Thread thread4 = new MyThread(4);
        Thread thread5 = new MyThread(5);
        Thread thread6 = new MyThread(6);
        Thread thread7 = new MyThread(7);
        Thread thread8 = new MyThread(8);
        Thread thread9 = new MyThread(9);
        Thread thread10 = new MyThread(10);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
    }
}
