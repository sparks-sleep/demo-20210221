package com.example.demo20210221;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args){
//        try {
//            toCharset();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        //System.out.println(HJ3());
        HJ3_2();
    }

    public static void toCharset() throws UnsupportedEncodingException {
        //获取系统默认编码
        System.out.println("系统默认编码：" + System.getProperty("file.encoding")); //查询结果GBK
        //系统默认字符编码
        System.out.println("系统默认字符编码：" + Charset.defaultCharset()); //查询结果GBK
        //操作系统用户使用的语言
        System.out.println("系统默认语言：" + System.getProperty("user.language")); //查询结果zh

        //定义字符串包含数字和中文
        String t = "hello, 大家好！";
        //通过getBytes方法获取默认的编码
        System.out.println("默认编码格式：");
        byte[] b = t.getBytes(); //ASCII，GBK，UTF-8对数字和英文字母的编码相同，unicode的编码跟前面三项都不同
        //打印默认编码
        for(byte a : b)
            System.out.print(a + ",\t");
        System.out.println();
        System.out.println(new String(b));
        //打印GBK编码
        System.out.println("GBK编码格式");
        b = t.getBytes("GBK");
        for(byte a : b)
            System.out.print(a + ",\t");
        System.out.println();
        System.out.println(new String(b));
        //打印UTF-8编码
        System.out.println("UTF-8编码格式");
        b = t.getBytes("UTF-8");
        for(byte a : b)
            System.out.print(a + ",\t");
        System.out.println();
        System.out.println(new String(b));
        //打印ASCII编码
        System.out.println("ASCII编码格式");
        b = t.getBytes("ASCII");
        for(byte a : b)
            System.out.print(a + ",\t");
        System.out.println();
        System.out.println(new String(b));
        //打印UNICODE编码
        System.out.println("UNICODE编码格式");
        b = t.getBytes("UNICODE");
        for(byte a : b)
            System.out.print(a + ",\t");
        System.out.println();
        System.out.println(new String(b));
        System.out.println();
    }

    /**
     * 华为机试3
     * @return
     */
    public static TreeSet<Integer> HJ3() {
        BufferedReader bufferedReader =new BufferedReader(
                new InputStreamReader(System.in));
        Random random = new Random();
        TreeSet<Integer> set = new TreeSet<>();
        try {
            System.out.print("请输入需要生成的学号数量：");
            String text = bufferedReader.readLine();
            while(set.size() < Integer.valueOf(text)){
                int num = random.nextInt(1000)+1;
                set.add(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }
    /**
     * 华为机试3_2
     */
    public static void HJ3_2() {
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        String str;
        while(true) {
            try {
                if (!((str = bf.readLine()) != null)) break;
                boolean[] stu = new boolean[1001];
                StringBuilder sb = new StringBuilder();
                int n = Integer.parseInt(str);
                for (int i = 0; i < n; i++)
                    stu[Integer.parseInt(bf.readLine())] = true;
                for (int i = 0; i < 1001; i++)
                    if (stu[i])
                        sb.append(i).append("\n");
                sb.deleteCharAt(sb.length() - 1);
                System.out.println(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
