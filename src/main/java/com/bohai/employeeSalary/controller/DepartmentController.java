package com.bohai.employeeSalary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.dao.DepartmentMapper;
import com.bohai.employeeSalary.entity.Department;



@Controller
public class DepartmentController {

	@Autowired
	private DepartmentMapper departmentMapper;
	
	@RequestMapping(value="queryDepartment")
	@ResponseBody
	public List<Department> queryDepartment(){
		
		return this.departmentMapper.queryDepartments();
	}
	
}
