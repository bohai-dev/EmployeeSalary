package com.bohai.employeeSalary.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
		String s="长春营业部2017年07月营销人员提成表";
		String num=filterNumber(s);
		System.out.println(num);

	}

}
