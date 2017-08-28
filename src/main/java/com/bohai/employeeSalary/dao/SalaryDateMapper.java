package com.bohai.employeeSalary.dao;

import java.util.Map;

import com.bohai.employeeSalary.entity.SalaryDate;

public interface SalaryDateMapper {
  
    int insert(SalaryDate record);

  
    int insertSelective(SalaryDate record);
    
    //计算某年某月某天到某天的的工作天数: 
    int countWorkDays(String year,String month,String startDay,String endDay);
   
}