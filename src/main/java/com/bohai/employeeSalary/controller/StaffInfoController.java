package com.bohai.employeeSalary.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.CheckMessageMapper;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.SysUser;
import com.bohai.employeeSalary.service.StaffInfoService;
import com.bohai.employeeSalary.vo.QueryCheckMessageParamVO;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;




@Controller
public class StaffInfoController {

	static Logger logger=Logger.getLogger(StaffInfoController.class);
	
	@Autowired
	private StaffInfoMapper staffInfoMapper;
	
	@Autowired
	private StaffInfoService staffInfoService;
	
	@Autowired
	private CheckMessageMapper checkMessageMapper;
	
	
	@RequestMapping("tochangePw")
	public String tochangePW(){
		return "changePw";
	}
//	@RequestMapping("toStaffInfo")
//	public String toStaffInfo(){
//		return "home";
//	}
//	/**
//	 * 保存用户信息
//	 * */
//	@RequestMapping(value="saveStaffInfo")
//    @ResponseBody
//	public void saveStaffInfo(@RequestBody(required = true) StaffInfo paramVO) throws BohaiException{		
//		//获取当前系统时间
//		long date=Calendar.getInstance().getTimeInMillis();
//		paramVO.setCreateTime(new Date(date));
//		paramVO.setUpdateTime(new Date(date));
//		paramVO.setIsLeave("0");
//	    this.staffInfoMapper.insert(paramVO);
//	}
	/**
	 * 提交审核信息
	 * */
	@RequestMapping(value="submitStaffInfo")   
	 @ResponseBody
	public Map<String,String> submitStaffInfo(@RequestBody(required = true) CheckMessage paramVO) throws BohaiException{		
		Map<String,String> map=new HashMap<String,String>();
		List<CheckMessage> cm=this.checkMessageMapper.selectByStaffNumber(paramVO.getStaffNumber());
		StaffInfo cm2=this.staffInfoMapper.selectByPrimaryKey(paramVO.getStaffNumber());
		if(cm!=null || cm2!=null){
			map.put("status", "false");
			return  map;
		}
		else{
		//获取当前系统时间
		long date=Calendar.getInstance().getTimeInMillis();
		paramVO.setCreateTime(new Date(date));
		paramVO.setUpdateTime(new Date(date));
		paramVO.setIsLeave("0");
		//获取当前登录用户姓名
		Subject currentUser = SecurityUtils.getSubject();
        String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
		paramVO.setSubmitter(userName);
		//
		paramVO.setSubmitTime(new Date(date));
		paramVO.setTage("0");
		paramVO.setSubmitType("0");
	    this.checkMessageMapper.insert(paramVO);
	    map.put("status", "success");
	    return map;
		}
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
	
	/**
	 * 按条件查询
	 * */
	@RequestMapping(value="queryByCondition")
	@ResponseBody
	public List<StaffInfo> queryByCondition(@RequestBody(required = true) QueryStaffInfoParamVO paramVO){
		List<StaffInfo> list=this.staffInfoMapper.selectByCondition(paramVO);
		return list;
	}
	
	/**
	 * 提交更新审核用户信息
	 * */
	@RequestMapping(value="submitUpdateStaffInfo")
	@ResponseBody
	public void submitUpdateStaffInfo(@RequestBody(required=true) CheckMessage paramVO){
		//获取当前系统时间
				long date=Calendar.getInstance().getTimeInMillis();
				paramVO.setCreateTime(new Date(date));
				paramVO.setUpdateTime(new Date(date));
				//获取当前登录用户姓名
				Subject currentUser = SecurityUtils.getSubject();
		        String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
				paramVO.setSubmitter(userName);
				//
				paramVO.setSubmitTime(new Date(date));
				paramVO.setTage("0");
				if(paramVO.getIsLeave().equals("0")){
					paramVO.setSubmitType("1");
				}else if(paramVO.getIsLeave().equals("1")){
					paramVO.setSubmitType("2");
				}
				 this.checkMessageMapper.insert(paramVO);
				 StaffInfo staff=this.staffInfoMapper.selectByPrimaryKey(paramVO.getStaffNumber());
				 staff.setSubmitStatus("0");
				 this.staffInfoMapper.updateByPrimaryKey(staff);
	}
	
	@RequestMapping("queryCheckMessagesBySubmitter")
	@ResponseBody
	public List<CheckMessage> submittercheckMessageTable(){
		//获取当前登录用户姓名
		Subject currentUser = SecurityUtils.getSubject();
        String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
        return this.checkMessageMapper.selectBySubmitter(userName);
	}
	
	@RequestMapping("queryCheckMessageByCondition")
	@ResponseBody
	public List<CheckMessage> queryCheckMessageByCondition(@RequestBody(required=true) QueryCheckMessageParamVO paramVO){
		List<CheckMessage> list=this.checkMessageMapper.selectByCheckCondition(paramVO);
		return list;	
	}
}
