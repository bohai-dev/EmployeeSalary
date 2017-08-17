package com.bohai.employeeSalary.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.service.StaffInfoService;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;
import com.bohai.employeeSalary.vo.TableJsonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;




@Controller
public class StaffInfoController {

	static Logger logger=Logger.getLogger(StaffInfoController.class);
	
	@Autowired
	private StaffInfoMapper staffInfoMapper;
	
	@Autowired
	private StaffInfoService staffInfoService;
	
	@RequestMapping("toStaffInfo")
	public String toStaffInfo(){
		return "home";
	}
	/**
	 * 保存用户信息
	 * */
	@RequestMapping(value="saveStaffInfo")
    @ResponseBody
	public void saveStaffInfo(@RequestBody(required = true) StaffInfo paramVO) throws BohaiException{
		//获取当前系统时间
		long date=Calendar.getInstance().getTimeInMillis();
		paramVO.setCreateTime(new Date(date));
		paramVO.setUpdateTime(new Date(date));
		paramVO.setIsLeave("0");
	    this.staffInfoMapper.insert(paramVO);
	}
	/**
	 * 分页查询所有用户信息
	 * */
	@RequestMapping(value="queryStaffInfos")
	@ResponseBody
	public List<StaffInfo> queryStaffInfoPagination(){
	
		List<StaffInfo> list = this.staffInfoService.queryStaffInfoPagination(null);
		
		return list;
	}
	
	@RequestMapping(value="queryByCondition")
	@ResponseBody
	public TableJsonResponse<StaffInfo> queryByCondition(@RequestBody(required = true) QueryStaffInfoParamVO paramVO){
		PageHelper.startPage(paramVO.getPageNumber(),paramVO.getPageSize());
		List<StaffInfo> list=this.staffInfoMapper.selectByCondition(paramVO);
		Page<StaffInfo> page=(Page)list;
		TableJsonResponse<StaffInfo> response=new TableJsonResponse<StaffInfo>();
		response.setTotal(page.getTotal());
		response.setRows(list);
		return response;
	}
	
	
}
