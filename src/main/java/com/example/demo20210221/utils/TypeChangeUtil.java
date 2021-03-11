package com.example.demo20210221.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeChangeUtil {

	final static String dateStart = "2020-10-01";
	static java.util.Date utilDate = null;
	/**
	 * 返回java.sql.Date格式的 
	 * @param strDate 
	 * @return
	 */
    public static Date strToStartDate(String strDate) {
    	Date sqlDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			utilDate = format.parse(dateStart);
			if(StringUtils.isNotBlank(strDate)){
	    		utilDate = format.parse(strDate);
	    	}

			sqlDate = new Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;  
    }
    /**
     * 返回java.sql.Date格式的
     * @param strDate
     * @return
     */
    public static Date strToEndDate(String strDate) {
    	Date sqlDate = new Date(new java.util.Date().getTime());
		if(StringUtils.isNotBlank(strDate)){
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				utilDate = format.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		sqlDate = new Date(utilDate.getTime());
    	}
		return sqlDate;  
    }
    /**
     * 返回java.sql.Time格式的 
     * @param strDate
     * @return
     */
    public static Time strToTime(String strDate) {  
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");   
        try {
        	new Time(format.parse(strDate).getTime());
			return Time.valueOf(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;  
    }
}
