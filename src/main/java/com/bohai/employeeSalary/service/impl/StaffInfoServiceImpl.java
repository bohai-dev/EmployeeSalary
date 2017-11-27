package com.bohai.employeeSalary.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohai.employeeSalary.controller.exception.BohaiException;
import com.bohai.employeeSalary.dao.CheckMessageMapper;
import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.CheckMessage;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.entity.SysUser;
import com.bohai.employeeSalary.service.SalaryDetailService;
import com.bohai.employeeSalary.service.StaffInfoService;
import com.bohai.employeeSalary.vo.PaginationParamVO;
import com.bohai.employeeSalary.vo.QueryStaffInfoParamVO;

@Service("staffInfoService")
public class StaffInfoServiceImpl implements StaffInfoService{
	
	static Logger logger = Logger.getLogger(StaffInfoServiceImpl.class);

	@Autowired
	private StaffInfoMapper staffInfoMapper;
	
	@Autowired
	private CheckMessageMapper checkMessageMapper;
	
	@Autowired
	private SalaryDetailService salaryDetailService;
	
	@Override
	public List<StaffInfo> queryStaffInfoPagination(PaginationParamVO paramVO) {
		// TODO Auto-generated method stub
	 List<StaffInfo> staffInfoList=this.staffInfoMapper.queryStaffInfos(null);
		return staffInfoList;
	}

    @Override
    public List<StaffInfo> queryStaffInfoByName(String name) {
        
        QueryStaffInfoParamVO paramVO = new QueryStaffInfoParamVO();
        paramVO.setName(name);
        return this.staffInfoMapper.selectByCondition(paramVO);
        
    }

    /**
	 * 提交审核信息
	 * @param paramVO
	 * @return
	 * @throws BohaiException
	 */
	@Override
	@Transactional
	public Map<String, String> submitStaffInfo(CheckMessage paramVO) throws BohaiException  {
		
		try {
			Map<String,String> map=new HashMap<String,String>();
			List<CheckMessage> cm=this.checkMessageMapper.selectByStaffNumber(paramVO.getStaffNumber());
			StaffInfo cm2=this.staffInfoMapper.selectByPrimaryKey(paramVO.getStaffNumber());
			if((cm!=null && cm.size()>0) || cm2!=null){
				map.put("message", "该员工信息已存在或正在审核中");
				return  map;
			}else{
				//获取当前系统时间
				long date=Calendar.getInstance().getTimeInMillis();
				paramVO.setCreateTime(new Date(date));
				paramVO.setUpdateTime(new Date(date));
				paramVO.setIsLeave("0");
				paramVO.setId(checkMessageMapper.generateCheckMessageId());
				//获取当前登录用户姓名
				Subject currentUser = SecurityUtils.getSubject();
			    String userName=((SysUser)currentUser.getSession().getAttribute("user")).getFullName();
				paramVO.setSubmitter(userName);
				//
				paramVO.setSubmitTime(new Date(date));
				paramVO.setTage("0");
				paramVO.setSubmitType("0");
				//插入checkMessae表
			    this.checkMessageMapper.insert(paramVO);   
			    //插入工资详情表
			    this.salaryDetailService.insertByCheckMessage(paramVO);
			    
			    map.put("message", "提交审核成功");
			    return map;
			}
		} catch (Exception e) {
			logger.error("提交审核信息异常",e);
			throw new BohaiException("", "提交审核信息异常");
		}
	}

	
}
