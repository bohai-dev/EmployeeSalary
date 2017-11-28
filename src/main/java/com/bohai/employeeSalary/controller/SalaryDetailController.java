package com.bohai.employeeSalary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.dao.SalaryDetailMapper;
import com.bohai.employeeSalary.entity.SalaryDetail;

@Controller
public class SalaryDetailController {
	
	@Autowired
	private SalaryDetailMapper salaryDetailMapper;
	
	/**
	 * 根据checkMessageId查询
	 * */
	@RequestMapping(value="salaryDetail/selectByCheckMessageId/{checkMessageId}", method = RequestMethod.GET)
	@ResponseBody
	public List<SalaryDetail> queryByCheckMessageId(@PathVariable String checkMessageId ){
	      List<SalaryDetail> salaryDetails=salaryDetailMapper.selectByCheckmessageId(checkMessageId);
	      return salaryDetails;
	}
	/**
	 * 根据staffNumber查询
	 * */
	@RequestMapping(value="salaryDetail/queryByStaffNumber/{staffNumber}",method = RequestMethod.GET)
	@ResponseBody
	public List<SalaryDetail> queryByStaffNumber(@PathVariable String staffNumber ){
	      List<SalaryDetail> salaryDetails=salaryDetailMapper.selectByStaffNumber(staffNumber);
	      return salaryDetails;
	}
}
