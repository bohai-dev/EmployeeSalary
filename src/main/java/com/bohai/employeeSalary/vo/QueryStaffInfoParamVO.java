package com.bohai.employeeSalary.vo;


/**
 * 查询员工信息入参
 * @author chenyang
 * */
public class QueryStaffInfoParamVO extends PaginationParamVO{

	private String departmentId;
	
	private String staffNumber;
	
	private String name;
	
	private String isProbation;
	
	private String isLeave;
	
    private String leaveDate;
	

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsProbation() {
		return isProbation;
	}

	public void setIsProbation(String isProbation) {
		this.isProbation = isProbation;
	}

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	
	
}
