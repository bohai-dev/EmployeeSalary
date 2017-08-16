package com.bohai.employeeSalary.service;

import java.util.List;


import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.vo.PaginationParamVO;

public interface StaffInfoService {

	
	/**
	 * 分页查询用户信息
	 * @return
	 */
	public List<StaffInfo> queryStaffInfoPagination(PaginationParamVO paramVO);
}
