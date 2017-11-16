package com.bohai.employeeSalary.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohai.employeeSalary.dao.CheckMessageMapper;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.SysUser;
import com.bohai.employeeSalary.service.CheckMessageService;
import com.bohai.employeeSalary.vo.PaginationParamVO;

@Service("checkMessageService")
public class CheckMessageServiceImpl implements CheckMessageService{

	@Autowired
	private CheckMessageMapper checkMessageMapper;
	
	@Autowired
	private StaffInfoMapper staffInfoMapper;

	@Override
	public List<Map> queryCheckMessages(PaginationParamVO paramVO) {
		// TODO Auto-generated method stub
		List<Map> checkMessageList=this.checkMessageMapper.queryCheckMessages(null);
		return checkMessageList;
	}
	
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
		
		//throw new RuntimeException();
	}
	
	@Override
	public void agreeStaffInfoList(List<CheckMessage> checkMessageList) {
		for(int i=0;i<checkMessageList.size();i++) {
			CheckMessage checkmessage=this.checkMessageMapper.selectByPrimaryKey(checkMessageList.get(i).getId());
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
			staffInfo.setSubmitStatus("1");
			
			if(checkmessage.getSubmitType().equals("0")){
			try {
				this.staffInfoMapper.insert(staffInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else{
				this.staffInfoMapper.updateByPrimaryKey(staffInfo);
			}
		}
		
	}

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
		staff.setSubmitStatus("1");
		 this.staffInfoMapper.updateByPrimaryKey(staff);
	}

}
