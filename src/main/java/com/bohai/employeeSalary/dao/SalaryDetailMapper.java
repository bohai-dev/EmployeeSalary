package com.bohai.employeeSalary.dao;

import java.util.List;

import java.util.Map;

import com.bohai.employeeSalary.entity.SalaryDetail;


public interface SalaryDetailMapper {
   
    int insert(SalaryDetail record);  
    
    int insertSelective(SalaryDetail record);   
    
    List<SalaryDetail> selectByCheckmessageId(String id);
    
    int updateByCheckMessageId(SalaryDetail salaryDetail);
    
    List<SalaryDetail> selectByStaffNumber(String number);
    

    int updateStateByStaffNumber(String staffNumber);

    String calcSalary(Map<String,String> map);

    
}