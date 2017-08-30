package com.bohai.employeeSalary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;

public interface StaffSalaryMapper {
	
	int insert(StaffSalary record);

	   
    int insertSelective(StaffSalary record);
    

    Long selectByStaffNumAndDate(String staffNum,String date); 
    
    int updateByStaffNumAndDate(StaffSalary staffSalary);
    
    List<StaffSalary> queryStaffSalaryByParams(QueryStaffSalaryParamVO vo);
   
    @Select("select STAFF_DEPARTMENT_ID from STAFF_SALARY  GROUP BY STAFF_DEPARTMENT_ID")
    List<String>  queryDepartIds();
}