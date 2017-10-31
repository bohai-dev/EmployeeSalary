package com.bohai.employeeSalary.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.dao.CheckMessageMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.service.CheckMessageService;
import com.bohai.employeeSalary.vo.CommonResponseHead;
import com.bohai.employeeSalary.vo.QueryCheckMessageParamVO;

@Controller
public class CheckMessageController {

	@RequestMapping(value="toCheck")
    public String toCheck(){
        return "check";
    }
	
	@Autowired
	private CheckMessageService checkMessageService;
		
	@Autowired
	private CheckMessageMapper checkMessageMapper;
	/**
	 * 分页查询所有审核信息
	 * */
	@RequestMapping(value="queryCheckMessages")
	@ResponseBody
	public List<Map> queryCheckMessagesPagination(){
		List<Map> list = this.checkMessageService.queryCheckMessages(null);	
		return list;
	}
	
	/**
	 * 通过审核信息
	 * */
	@RequestMapping(value="agreeStaffInfo")
	@ResponseBody
	public CommonResponseHead agreeStaffInfo(@RequestBody(required=true) CheckMessage paramVO){
	    
	    CommonResponseHead commonResponseHead = new CommonResponseHead();
		try {
            checkMessageService.agreeStaffInfo(paramVO);
        } catch (Exception e) {
            commonResponseHead.setResponseCode("00001");
            commonResponseHead.setMessage(e.getMessage());
        }
		return commonResponseHead;
	}
	
	/**
	 *批量通过审核信息
	 * */
	@RequestMapping(value="agreeStaffInfoList")
	@ResponseBody
	public void agreeStaffInfoList(@RequestBody(required=true)List<CheckMessage> paramVO){
		checkMessageService.agreeStaffInfoList(paramVO);
	}
	
	/**
	 * 拒绝通过审核
	 * */
	@RequestMapping("refuseStaffInfo")
	@ResponseBody
	public void refuseStaffInfo(@RequestBody(required=true) CheckMessage paramVO){
		checkMessageService.refuseStaffInfo(paramVO);
	}
	
	/**
	 * 按条件查询
	 * */
	@RequestMapping("queryByCheckCondition")
	@ResponseBody
	public List<Map> queryByCheckCondition(@RequestBody(required=true) QueryCheckMessageParamVO paramVO){
		List<Map> list=this.checkMessageMapper.selectByCheckCondition(paramVO);
		return list;
	}
}
