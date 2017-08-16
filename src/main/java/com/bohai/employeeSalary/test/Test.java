package com.bohai.employeeSalary.test;

public class Test {
	
	
	
	public static void main(String[] args) {
		String time="2017年05月";
		String str=time.replace("年", "-").replace("月", "");
		System.out.println(str);
	}

}
