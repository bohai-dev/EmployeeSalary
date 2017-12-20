package com.bohai.employeeSalary.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.time.DateFormatUtils;

import com.bohai.employeeSalary.util.CommonUtils;


public class TestClass {
	
	
	
	 public static String filterNumber(String number)
	    {
	        number = number.replaceAll("[^(0-9)]", "");
	        return number;
	    }

	
	
	
	public static void main(String[] args) {
		/*String time="2017年05月";
		String str=time.replace("年", "-").replace("月", "");
		System.out.println(str);*/
		/*LocalDate localDate = LocalDate.now();
		System.out.println("localDate: " + localDate);*/		
		
	/*	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateString = dateTimeFormatter.format(LocalDateTime.now());
		System.out.println("日期转字符串: " + dateString);*/
		//计算个人所得税   ROUND(MAX(计税基数*5%*{0.6,2,4,5,6,7,9}-5*{0,21,111,201,551,1101,2701},0),2)		
		//  System.out.println(CommonUtils.getPath());
		
		
		
		//System.out.println(CommonUtils.getRound(3713.4));
	    BigDecimal[] nums=new BigDecimal[] {new BigDecimal(956),new BigDecimal(1269),new BigDecimal(895)};
	 
		System.out.println(CommonUtils.getMax(Arrays.asList(nums)));
		
		String datestr = "2017-9";
		
		System.out.println(datestr.substring(5));
		
		if(datestr.substring(5).length()<2){
		    datestr = new StringBuffer(datestr).insert(5, "0").toString();
		}
		System.out.println(datestr);
	}

}
