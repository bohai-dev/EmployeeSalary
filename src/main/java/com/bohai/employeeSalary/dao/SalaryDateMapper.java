package com.bohai.employeeSalary.dao;

import com.bohai.employeeSalary.entity.SalaryDate;

public interface SalaryDateMapper {
  
    int insert(SalaryDate record);

  
    int insertSelective(SalaryDate record);
    int countWorkDays(String year,String month,String startDay);
}