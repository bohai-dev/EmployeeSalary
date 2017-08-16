package com.bohai.employeeSalary.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.employeeSalary.dao.StaffInfoMapper;
import com.bohai.employeeSalary.entity.StaffInfo;
import com.bohai.employeeSalary.service.StaffInfoService;
import com.bohai.employeeSalary.vo.PaginationParamVO;

@Service("staffInfoService")
public class StaffInfoServiceImpl implements StaffInfoService{

	@Autowired
	private StaffInfoMapper staffInfoMapper;
	
	@Override
	public List<StaffInfo> queryStaffInfoPagination(PaginationParamVO paramVO) {
		// TODO Auto-generated method stub
	 List<StaffInfo> staffInfoList=this.staffInfoMapper.queryStaffInfos(null);
		return staffInfoList;
	}

	
}
