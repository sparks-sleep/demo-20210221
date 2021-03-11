package com.example.demo20210221.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
    public static Map<Object, Object> customMap = new HashMap<>();
    static {
        File dir = new File("src/main/resources");
        List<File> files = FileUtils.searchAllFile(new File(dir.getAbsolutePath()));
        for(File file : files){
            if(file.getName().endsWith(".properties")){
                Properties prop = new Properties();
                try {
                    prop.load(new FileInputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                customMap.putAll(prop);
            }

        }
    }

    /**
     * 根据文件"src/main/resources/datasource.properties"key值，获取value值
     * @param file
     * @param key
     * @return
     */
    public static String getProperty(File file ,String key){
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
