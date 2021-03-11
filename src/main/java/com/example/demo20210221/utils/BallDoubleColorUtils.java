package com.example.demo20210221.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现了有返回值的类
 */
public class BallDoubleColorUtils implements Callable<Set<String>> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable callable = new BallDoubleColorUtils();
        FutureTask task = new FutureTask(callable);
        new Thread(task).start();
        //task.get();
        System.out.println("线程返回的值："+task.get());
    }

    @Override
    public Set<String> call() throws Exception {
        final String[] hongQiu = new String[]
                {"1","2","3","4","5","6","7","8","9","10",
                        "11","12","13","14","15","16","17","18","19","20",
                        "21","22","23","24","25","26","27","28","29","30",
                        "31","32","33"};
        final String[] lanQiu = new String[]
                {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        int i = 1;
        Set<String> set = new HashSet<>();
        while (set.size() < 6){
            System.out.println("set集合长度："+set.size());
            if(i == 1){
                Thread.sleep(13000);
            }else if(1 > i && i < 5){
                Thread.sleep(11000);
            }else{
                Thread.sleep(12000);
            }
            Random r = new Random();
            String selectedQiu = hongQiu[r.nextInt(hongQiu.length)];
            System.out.println("随机数:"+selectedQiu);
            set.add(selectedQiu);
            i++;
        }
        return set;
    }
}