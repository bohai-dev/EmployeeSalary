package com.bohai.employeeSalary.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public  class CommonUtils {
	
	/**
	 * 返回当前时间的字符串格式 
	 * 形如：yyyy-MM-dd HH:mm:ss
	 *  
	 */
	public static String getTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateString = dateTimeFormatter.format(LocalDateTime.now());
		return dateString;
		
	}
	
	/**
	 * 返回字符串中的数字
	 * @param number 要提取数字的字符串
	 * @return 数字
	 */
	 public static String filterNumber(String number)
	    {
	        number = number.replaceAll("[^(0-9)]", "");
	        return number;
	    }

}
