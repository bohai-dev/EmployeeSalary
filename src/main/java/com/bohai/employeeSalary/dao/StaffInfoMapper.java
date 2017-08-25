package com.bohai.employeeSalary.dao;

import java.util.List;
import java.util.Map;

import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;


public interface StaffInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_INFO
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    int deleteByPrimaryKey(String staffNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_INFO
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    int insert(StaffInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_INFO
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    int insertSelective(StaffInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_INFO
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    StaffInfo selectByPrimaryKey(String staffNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_INFO
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    int updateByPrimaryKeySelective(StaffInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STAFF_INFO
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    int updateByPrimaryKey(StaffInfo record);

    /**
     * 查询所有信息
     * */
    List<StaffInfo> queryStaffInfos(Map<String,Object> map);

    /**
     * 按条件查询
     * */
	List<StaffInfo> selectByCondition(QueryStaffInfoParamVO paramVO);

	/**
	 * 按部门查询
	 * */
	List<StaffInfo> selectByDepartment(String depNumber);


}