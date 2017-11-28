package com.bohai.employeeSalary.entity;

import java.util.Date;

public class SalaryDetail {

    private String checkMessageId;

    private String staffNumber;

    private String salary;

    private String startTime;

    private String endTime;

    private String remark;

    private String checkState;

    private String commitPerson;

    private String checkPerson;

    private Date createTime;

    private Date updateTime;
    
    private String rownum;
    

    public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getCheckMessageId() {
        return checkMessageId;
    }

    public void setCheckMessageId(String checkMessageId) {
        this.checkMessageId = checkMessageId == null ? null : checkMessageId.trim();
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber == null ? null : staffNumber.trim();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState == null ? null : checkState.trim();
    }

    public String getCommitPerson() {
        return commitPerson;
    }

    public void setCommitPerson(String commitPerson) {
        this.commitPerson = commitPerson == null ? null : commitPerson.trim();
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson == null ? null : checkPerson.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}