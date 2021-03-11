package com.example.demo20210221.utils;

import org.apache.commons.lang3.StringUtils;

public class ChangValueUtils {

    /**
     * 去掉字符串指定的前缀
     * @param str 字符串名称
     * @param prefix 前缀数组
     * @return
     */
    public static String removePrefix(String str, String[] prefix) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            if (null != prefix) {
                String[] prefixArray = prefix;
                for(int i = 0; i < prefix.length; ++i) {
                    String pf = prefixArray[i];
                    if (str.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                        //截取前缀后面的字符串
                        return str.substring(pf.length());
                    }
                }
            }
            return str;
        }
    }

    /**
     * 清除字符串下划线
     * @param strKey
     * @return
     */
    public static String getNoUnderlineStr(String strKey){
        if(strKey.indexOf("_")!=-1){
            String[] keyArray = strKey.split("_");
            StringBuffer sb = new StringBuffer();
            boolean flag = false;
            for(String key:keyArray){
                if(flag){
                    //下划线后的首字母大写
                    sb.append(StringUtils.capitalize(key));
                }else{
                    flag=true;
                    sb.append(key);
                }
            }
            strKey = sb.toString();
        }
        return strKey;
    }

    /**
     * 根据表名获取模块名
     * @param tableName
     * @return
     */
    public static String byTableGetModule(String tableName){
        String moduleName = "";
        if(tableName.indexOf("_")!=-1){
            String[] keyArray = tableName.split("_");
            moduleName = keyArray[0].toLowerCase();
        }
        return moduleName;
    }

    /**
     * tableName转className,
     * 1.去掉字符串指定的前缀  2.清除字符串下划线,下划线后的首字母大写 3.首字母大写
     * @param tableName
     * @return
     */
    public static String firstStrUpperCase(String tableName){
        String prefix = (String) PropertiesUtils.customMap.get("tableRemovePrefixes");
        String[] prefixArray =prefix.split(",");
        //1.去掉字符串指定的前缀
        tableName = ChangValueUtils.removePrefix(tableName,prefixArray);
        //2.清除字符串下划线,下划线后的首字母大写
        String className = ChangValueUtils.getNoUnderlineStr(tableName);
        return className;
    }

    /**
     * 字段名解析
     * @param columnName
     * @return
     */
    public static String columnToJavaField(String columnName) {
        //按_分隔成数组
        String[] split = columnName.toLowerCase().split("_");//
        String fieldName = split[0];
        for (int i = 1; i < split.length ; i++) {
            // .substring(0,1) 截取数组的首字母   +split[i].substring(1); 再加上后面
            fieldName += split[i].substring(0, 1).toUpperCase()+split[i].substring(1);
        }
        return fieldName;
    }

    /**
     * 将数据库中带下划线的字段转换为Java常用的驼峰字段
     * @param field
     * @return
     */
    public static String changeToJavaField(String field){
        String[] fields = field.toLowerCase().split("_");
        StringBuilder sBuilder = new StringBuilder(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            char[] cs =fields[i].toCharArray();
            cs[0]-=32;
            sBuilder.append(String.valueOf(cs));
        }
        return sBuilder.toString();
    }

}
