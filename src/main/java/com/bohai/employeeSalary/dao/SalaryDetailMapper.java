package com.bohai.employeeSalary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bohai.employeeSalary.entity.SalaryDetail;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;

public interface SalaryDetailMapper {
   
    int insert(SalaryDetail record);  
    
    int insertSelective(SalaryDetail record);   
    
    List<SalaryDetail> selectByCheckmessageId(String id);
    
    int updateByCheckMessageId(SalaryDetail salaryDetail);
    
    List<SalaryDetail> selectByStaffNumber(String number);
    
}