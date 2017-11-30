package com.bohai.employeeSalary.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.bohai.employeeSalary.dao.CheckMessageMapper;
import com.bohai.employeeSalary.dao.SalaryDetailMapper;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.SalaryDetail;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.SysUser;
import com.bohai.employeeSalary.service.CheckMessageService;
import com.bohai.employeeSalary.vo.PaginationParamVO;

@Service("checkMessageService")
public class CheckMessageServiceImpl implements CheckMessageService{

    static Logger logger = Logger.getLogger(CheckMessageServiceImpl.class);
            
	@Autowired
	private CheckMessageMapper checkMessageMapper;
	
	@Autowired
	private StaffInfoMapper staffInfoMapper;
	@Autowired
	private SalaryDetailMapper slaryDetailMapper;

	@Override
	public List<Map> queryCheckMessages(PaginationParamVO paramVO) {
		// TODO Auto-generated method stub
		List<Map> checkMessageList=this.checkMessageMapper.queryCheckMessages(null);
		return checkMessageList;
	}
	
	//审核通过单条数据
	@Transactional
	@Override
	public void agreeStaffInfo(CheckMessage paramVO) {
		// TODO Auto-generated method stub
		CheckMessage checkmessage=this.checkMessageMapper.selectByPrimaryKey(paramVO.getId());
		checkmessage.setTage("1");
		//获取当前登录用户姓名
				Subject currentUser = SecurityUtils.getSubject();
			    String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
			    checkmessage.setChecker(userName);
		//获取当前系统时间
				long date=Calendar.getInstance().getTimeInMillis();
				checkmessage.setCheckTime(new Date(date));
		this.checkMessageMapper.updateByPrimaryKeySelective(checkmessage);
		StaffInfo staffInfo=new StaffInfo();
		staffInfo.setProbationDateStart(checkmessage.getProbationDateStart());
		staffInfo.setFormalDateStart(checkmessage.getFormalDateStart());
		staffInfo.setLeaveDate(checkmessage.getLeaveDate());
		staffInfo.setName(checkmessage.getName());
		staffInfo.setIdNumber(checkmessage.getIdNumber());
		staffInfo.setStaffNumber(checkmessage.getStaffNumber());
		staffInfo.setPositionSalary(checkmessage.getPositionSalary());
		staffInfo.setWorkYears(checkmessage.getWorkYears());
		staffInfo.setEmail(checkmessage.getEmail());
		staffInfo.setRemark(checkmessage.getRemark());
		staffInfo.setDepartmentId(checkmessage.getDepartmentId());
		staffInfo.setIsLeave(checkmessage.getIsLeave());
		staffInfo.setCreateTime(new Date(date));
		staffInfo.setUpdateTime(checkmessage.getUpdateTime());
		staffInfo.setIsProbation(checkmessage.getIsProbation());
		staffInfo.setCoefficeient(checkmessage.getCoefficeient());
		staffInfo.setSkillSalary(checkmessage.getSkillSalary());
		staffInfo.setProbationSalary(checkmessage.getProbationSalary());
		staffInfo.setSubmitStatus("1");
		
		
		if(checkmessage.getSubmitType().equals("0")){
		this.staffInfoMapper.insert(staffInfo);
		}else{
			this.staffInfoMapper.updateByPrimaryKey(staffInfo);
		}
		//更新工资变动
		//先将该员工原来的可用状态的工资变动重置为不可用
		this.slaryDetailMapper.updateStateByStaffNumber(paramVO.getStaffNumber());
		//再重新插入
        SalaryDetail salaryDetail=new SalaryDetail();
        salaryDetail.setCheckMessageId(paramVO.getId());
        salaryDetail.setCheckState("1"); //1、可用
		slaryDetailMapper.updateByCheckMessageId(salaryDetail);
		
	}
	
	//批量审核通过
	@Override
	public void agreeStaffInfoList(List<CheckMessage> checkMessageList) {
		for(int i=0;i<checkMessageList.size();i++) {
			CheckMessage checkmessage=this.checkMessageMapper.selectByPrimaryKey(checkMessageList.get(i).getId());
			checkmessage.setTage("1");   //1审核通过
			//获取当前登录用户姓名
			Subject currentUser = SecurityUtils.getSubject();
		    String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
		    checkmessage.setChecker(userName);
			//获取当前系统时间					
			checkmessage.setCheckTime(new Date());
			this.checkMessageMapper.updateByPrimaryKeySelective(checkmessage);  //更新checkMessage表
			//更新工资变动表
			//先将该员工原来的可用状态的工资变动重置为不可用
			 this.slaryDetailMapper.updateStateByStaffNumber(checkmessage.getStaffNumber());
			
			
		    //再重新插入
	        SalaryDetail salaryDetail=new SalaryDetail();
		    salaryDetail.setCheckMessageId(checkmessage.getId());
	        salaryDetail.setCheckState("1"); //1、可用
			slaryDetailMapper.updateByCheckMessageId(salaryDetail);
	
			 
			
			StaffInfo staffInfo=new StaffInfo();
			staffInfo.setProbationDateStart(checkmessage.getProbationDateStart());
			staffInfo.setFormalDateStart(checkmessage.getFormalDateStart());
			staffInfo.setLeaveDate(checkmessage.getLeaveDate());
			staffInfo.setName(checkmessage.getName());
			staffInfo.setIdNumber(checkmessage.getIdNumber());
			staffInfo.setStaffNumber(checkmessage.getStaffNumber());
			staffInfo.setPositionSalary(checkmessage.getPositionSalary());
			staffInfo.setProbationSalary(checkmessage.getProbationSalary());     //设置试用期工资
			staffInfo.setWorkYears(checkmessage.getWorkYears());
			staffInfo.setEmail(checkmessage.getEmail());
			staffInfo.setRemark(checkmessage.getRemark());
			staffInfo.setDepartmentId(checkmessage.getDepartmentId());
			staffInfo.setIsLeave(checkmessage.getIsLeave());
			staffInfo.setCreateTime(new Date());
			staffInfo.setUpdateTime(checkmessage.getUpdateTime());
			staffInfo.setIsProbation(checkmessage.getIsProbation());
			staffInfo.setCoefficeient(checkmessage.getCoefficeient());
			staffInfo.setSkillSalary(checkmessage.getSkillSalary());
			staffInfo.setSubmitStatus("1");
			
			if(checkmessage.getSubmitType().equals("0")){
			try {
				this.staffInfoMapper.insert(staffInfo);   //插入员工信息表
			} catch (Exception e) {
			    logger.error("保存员工信息失败",e);
			}
			}else{
				this.staffInfoMapper.updateByPrimaryKey(staffInfo);  //更新员工信息表
			}
		}
		
	}

	@Transactional
	@Override
	public void refuseStaffInfo(CheckMessage paramVO) {
		// TODO Auto-generated method stub
		//获取当前登录用户姓名
				Subject currentUser = SecurityUtils.getSubject();
			    String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
		paramVO.setChecker(userName);
		//获取当前系统时间
		long date=Calendar.getInstance().getTimeInMillis();
		paramVO.setCheckTime(new Date(date));
		paramVO.setTage("2");
		this.checkMessageMapper.updateByPrimaryKeySelective(paramVO);
		StaffInfo staff=this.staffInfoMapper.selectByPrimaryKey(paramVO.getStaffNumber());
		if(staff != null) {
			staff.setSubmitStatus("1");
			this.staffInfoMapper.updateByPrimaryKey(staff);
		}
	}
	
	//修改信息提交审核
	@Transactional
	@Override
	public Map<String,String> submitUpdateStaffInfo( CheckMessage paramVO){
		
        Map<String,String> map=new HashMap<String,String>();		
		List<CheckMessage> cmList=this.checkMessageMapper.selectByStaffNumber(paramVO.getStaffNumber());
		if (cmList!=null&&cmList.size()>0) {
			
			     map.put("status", "false");
			     return map;
			
		     }
		
		else {
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
				paramVO.setSubmitType("1");  //修改员工信息
			}else if(paramVO.getIsLeave().equals("1")){
				paramVO.setSubmitType("2");  //离职员工信息
			}
			 paramVO.setId(checkMessageMapper.generateCheckMessageId());
			 this.checkMessageMapper.insert(paramVO);
			 //插入工资变动情况
			 List<SalaryDetail> salaryDetails=paramVO.getSalaryDetails();
			 if(salaryDetails!=null) {
				 for(int i=0;i<salaryDetails.size();i++) {
					 SalaryDetail salaryDetail=salaryDetails.get(i);
					 salaryDetail.setCheckMessageId(paramVO.getId());
					 salaryDetail.setStaffNumber(paramVO.getStaffNumber());
					 salaryDetail.setCheckState("0");  //状态不可用					 
					 //插入工资变动信息
					 this.slaryDetailMapper.insertSelective(salaryDetail);
				 }
			 }
			 
			 StaffInfo staff=this.staffInfoMapper.selectByPrimaryKey(paramVO.getStaffNumber());
			 staff.setSubmitStatus("0");
			 this.staffInfoMapper.updateByPrimaryKey(staff);
			 
			 map.put("status", "success");
			 return map;
	     }
 
    }
}
