package com.bohai.employeeSalary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;

public interface StaffSalaryMapper {
	
	int insert(StaffSalary record);

	   
    int insertSelective(StaffSalary record);
    

    Long selectByStaffNumAndDate(StaffSalary staffSalary); 
    
    int updateByStaffNumAndDate(StaffSalary staffSalary);
    
    List<StaffSalary> queryStaffSalaryByParams(QueryStaffSalaryParamVO vo);
   
    @Select("select STAFF_DEPARTMENT_ID from STAFF_SALARY  GROUP BY STAFF_DEPARTMENT_ID")
    List<String>  queryDepartIds();
    
    //更新补缴社保公积金
    @Update("update STAFF_SALARY SET BUCKLE_UP=BUCKLE_UP+#{2} WHERE STAFF_NUMBER=#{0} AND PAY_DATE=#{1}")
    int updateBuckleUp(String staffNum,String payDate,String buckleUp);
}