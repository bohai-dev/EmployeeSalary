package com.bohai.employeeSalary.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.vo.PaginationParamVO;

public interface StaffInfoService {

	
	/**
	 * 分页查询用户信息
	 * @return
	 */
	public List<StaffInfo> queryStaffInfoPagination(PaginationParamVO paramVO);
	
	/**
	 * 根据员工姓名查询员工信息
	 * @param name
	 * @return
	 */
	public List<StaffInfo> queryStaffInfoByName(String name);
	
	/**
	 * 提交审核信息
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	public Map<String ,String> submitStaffInfo(CheckMessage paramVO) throws BohaiException;
}
