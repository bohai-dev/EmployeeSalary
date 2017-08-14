package com.bohai.employeeSalary.dao;

import com.bohai.employeeSalary.entity.SysUser;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USERS
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USERS
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USERS
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USERS
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    SysUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USERS
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_USERS
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    int updateByPrimaryKey(SysUser record);

	SysUser queryUserByUsername(String username);
}