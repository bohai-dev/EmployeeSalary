package com.bohai.employeeSalary.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

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
	 
	 
	 /**
	  * 得到一个BigDecimal类型数组中的最大值
	  * @param list
	  * @return
	  */
	 public static BigDecimal getMax(List<BigDecimal> list) {
		 BigDecimal max=list.get(0);
		 for(int i=1;i<list.size();i++) {
			 max=max.max(list.get(i));
		 }
		 
		 return max;
		 
	 }
	 /**
	  * 对一个BigDecimal数字进行四舍五入，并保留两位小数
	  * @return
	  */
	 public static BigDecimal getRound(BigDecimal money) {
			 
		//	BigDecimal  b=new BigDecimal(d);  
		    money=money.setScale(2,RoundingMode.HALF_UP);  
		    return money;
	 }
	 
	 /**
	  * 返回Date类型的年月，形如yyyy-MM 2017-08
	  * @param date
	  * @return
	  */
      public static String getYearMonth(Date date) {
    	  DateFormat format = new SimpleDateFormat("yyyy-MM");
      	  String formalDate=format.format(date); 
      	  return formalDate;
    	  
      } 
      
      /**
 	  * 返回Date类型的年月，形如yyyy-MM 2017-08-01
 	  * @param date
 	  * @return
 	  */
       public static String getYearMonthDay(Date date) {
     	  DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       	  String formalDate=format.format(date); 
       	  return formalDate;
     	  
       } 
       
     
}
