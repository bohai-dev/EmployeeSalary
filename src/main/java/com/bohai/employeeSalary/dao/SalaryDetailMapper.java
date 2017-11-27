package com.bohai.employeeSalary.dao;

import com.bohai.employeeSalary.entity.SalaryDetail;

public interface SalaryDetailMapper {
   
    int insert(SalaryDetail record);  
    int insertSelective(SalaryDetail record);
    
}