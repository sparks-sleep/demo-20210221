package com.example.demo20210221.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * 查询某个目录下的所有文件
     * @param dir
     * @return
     */
    public static List<File> searchAllFile(File dir) {
        List<File> collector = new ArrayList();
        searchFiles(dir,collector);
        return collector;
    }

    /**
     * 递归获取某个目录下的所有文件
     * @param dir
     * @param collector
     */
    private static void searchFiles(File dir, List<File> collector) {
        if(dir.isDirectory()){
            File[] subFiles = dir.listFiles();
            for(int i = 0; i < subFiles.length; i++){
                searchFiles(subFiles[i],collector);
            }
        }else{
            collector.add(dir);
        }
    }

    public static File mkdir(String dir, String file) {
        if(dir == null){
            throw new IllegalArgumentException("dir must be not null");
        }
        File result = new File(dir,file);
        if(result.getParentFile() != null){
            result.getParentFile().mkdirs();
        }
        return result;
    }
}
