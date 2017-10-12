package com.bohai.employeeSalary.service;

import java.util.List;
import java.util.Map;

import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.vo.PaginationParamVO;

public interface CheckMessageService {

	
	/**
	 * 分页查询待审核信息
	 * */
	List<Map> queryCheckMessages(PaginationParamVO paramVO);

	void agreeStaffInfo(CheckMessage paramVO);
//
	void refuseStaffInfo(CheckMessage paramVO);

}
