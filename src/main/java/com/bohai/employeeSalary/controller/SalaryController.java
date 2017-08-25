package com.bohai.employeeSalary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bohai.employeeSalary.dao.StaffSalaryMapper;
import com.bohai.employeeSalary.entity.Department;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.StaffSalary;
import com.bohai.employeeSalary.service.StaffSalaryService;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;
import com.bohai.employeeSalary.vo.QueryStaffSalaryParamVO;
import com.bohai.employeeSalary.vo.TableJsonResponse;
import com.bohai.employeeSalary.vo.TreeView;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class SalaryController {
	
	@Autowired
	private StaffSalaryMapper StaffSalaryMapper;
	@Autowired
	private StaffSalaryService staffSalaryService;
	
	@Autowired
	private StaffSalaryService salaryService;
	
	@RequestMapping(value="toSalary")
    public String toSalary(){
        return "salary";
    }
	
	
	@RequestMapping(value="querySalaryByParams")
	@ResponseBody
	public TableJsonResponse<StaffSalary> querySalaryByParams(@RequestBody(required = true) QueryStaffSalaryParamVO paramVO){
		
		PageHelper.startPage(paramVO.getPageNumber(), paramVO.getPageSize());
		List<StaffSalary> list=StaffSalaryMapper.queryStaffSalaryByParams(paramVO);
		Page<StaffSalary> page = (Page)list;
		TableJsonResponse<StaffSalary> response = new TableJsonResponse<StaffSalary>();
		
		
		
		response.setTotal(page.getTotal());
		response.setRows(list);		
		return response;
	}
	
	/**
	 * 计算工资
	 * @param staffSalary
	 * @return
	 */
	@RequestMapping(value="calculateSalary")
	@ResponseBody
	public int calculateSalary(@RequestBody(required = true) String depNum,String countDate){
		int count=salaryService.updateSalary(depNum, countDate);
		
		return count;

	}

	
//	@RequestMapping(value="queryDepStaffInfos")
//	@ResponseBody
//	public List<TreeView<StaffInfo>> queryDepStaffInfos(@RequestBody Department Dep){
//		
//		return this.staffSalaryService.queryDepStaffInfos(Dep.getDepName(),null);
//		
//	}
}
