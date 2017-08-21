package com.bohai.employeeSalary.dao;



import java.util.List;

import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;

public interface StaffSalaryMapper {
   
    int insert(StaffSalary record);

   
    int insertSelective(StaffSalary record);
    

    Long selectByStaffNumAndDate(String staffNum,String date); 
    
    int updateByStaffNumAndDate(StaffSalary staffSalary);
    
    List<StaffSalary> queryStaffSalaryByParams(QueryStaffSalaryParamVO vo);
   
}