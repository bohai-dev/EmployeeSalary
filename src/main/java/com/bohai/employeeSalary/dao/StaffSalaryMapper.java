package com.bohai.employeeSalary.dao;



import com.bohai.employeeSalary.entity.StaffSalary;

public interface StaffSalaryMapper {
   
    int insert(StaffSalary record);

   
    int insertSelective(StaffSalary record);
    

    Long selectByStaffNumAndDate(String staffNum,String date);    
    int updateByStaffNumAndDate(StaffSalary staffSalary);
   
}