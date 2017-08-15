package com.bohai.employeeSalary.entity;

import java.util.Date;

public class StaffInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.STAFF_NUMBER
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String staffNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.PROBATION_DATE_START
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private Date probationDateStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.FORMAL_DATE_START
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private Date formalDateStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.LEAVE_DATE
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private Date leaveDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.NAME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.ID_NUMBER
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String idNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.POSITION_SALARY
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String positionSalary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.WORK_YEARS
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String workYears;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.EMAIL
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.REMARK
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.DEPARTMENT_ID
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String departmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.IS_LEAVE
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String isLeave;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.CREATE_TIME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.UPDATE_TIME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STAFF_INFO.IS_PROBATION
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    private String isProbation;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.STAFF_NUMBER
     *
     * @return the value of STAFF_INFO.STAFF_NUMBER
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getStaffNumber() {
        return staffNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.STAFF_NUMBER
     *
     * @param staffNumber the value for STAFF_INFO.STAFF_NUMBER
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber == null ? null : staffNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.PROBATION_DATE_START
     *
     * @return the value of STAFF_INFO.PROBATION_DATE_START
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public Date getProbationDateStart() {
        return probationDateStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.PROBATION_DATE_START
     *
     * @param probationDateStart the value for STAFF_INFO.PROBATION_DATE_START
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setProbationDateStart(Date probationDateStart) {
        this.probationDateStart = probationDateStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.FORMAL_DATE_START
     *
     * @return the value of STAFF_INFO.FORMAL_DATE_START
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public Date getFormalDateStart() {
        return formalDateStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.FORMAL_DATE_START
     *
     * @param formalDateStart the value for STAFF_INFO.FORMAL_DATE_START
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setFormalDateStart(Date formalDateStart) {
        this.formalDateStart = formalDateStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.LEAVE_DATE
     *
     * @return the value of STAFF_INFO.LEAVE_DATE
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.LEAVE_DATE
     *
     * @param leaveDate the value for STAFF_INFO.LEAVE_DATE
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.NAME
     *
     * @return the value of STAFF_INFO.NAME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.NAME
     *
     * @param name the value for STAFF_INFO.NAME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.ID_NUMBER
     *
     * @return the value of STAFF_INFO.ID_NUMBER
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.ID_NUMBER
     *
     * @param idNumber the value for STAFF_INFO.ID_NUMBER
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.POSITION_SALARY
     *
     * @return the value of STAFF_INFO.POSITION_SALARY
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getPositionSalary() {
        return positionSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.POSITION_SALARY
     *
     * @param positionSalary the value for STAFF_INFO.POSITION_SALARY
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setPositionSalary(String positionSalary) {
        this.positionSalary = positionSalary == null ? null : positionSalary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.WORK_YEARS
     *
     * @return the value of STAFF_INFO.WORK_YEARS
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getWorkYears() {
        return workYears;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.WORK_YEARS
     *
     * @param workYears the value for STAFF_INFO.WORK_YEARS
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setWorkYears(String workYears) {
        this.workYears = workYears == null ? null : workYears.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.EMAIL
     *
     * @return the value of STAFF_INFO.EMAIL
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.EMAIL
     *
     * @param email the value for STAFF_INFO.EMAIL
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.REMARK
     *
     * @return the value of STAFF_INFO.REMARK
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.REMARK
     *
     * @param remark the value for STAFF_INFO.REMARK
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.DEPARTMENT_ID
     *
     * @return the value of STAFF_INFO.DEPARTMENT_ID
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.DEPARTMENT_ID
     *
     * @param departmentId the value for STAFF_INFO.DEPARTMENT_ID
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.IS_LEAVE
     *
     * @return the value of STAFF_INFO.IS_LEAVE
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getIsLeave() {
        return isLeave;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.IS_LEAVE
     *
     * @param isLeave the value for STAFF_INFO.IS_LEAVE
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setIsLeave(String isLeave) {
        this.isLeave = isLeave == null ? null : isLeave.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.CREATE_TIME
     *
     * @return the value of STAFF_INFO.CREATE_TIME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.CREATE_TIME
     *
     * @param createTime the value for STAFF_INFO.CREATE_TIME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.UPDATE_TIME
     *
     * @return the value of STAFF_INFO.UPDATE_TIME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.UPDATE_TIME
     *
     * @param updateTime the value for STAFF_INFO.UPDATE_TIME
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STAFF_INFO.IS_PROBATION
     *
     * @return the value of STAFF_INFO.IS_PROBATION
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public String getIsProbation() {
        return isProbation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STAFF_INFO.IS_PROBATION
     *
     * @param isProbation the value for STAFF_INFO.IS_PROBATION
     *
     * @mbggenerated Tue Aug 15 13:38:03 CST 2017
     */
    public void setIsProbation(String isProbation) {
        this.isProbation = isProbation == null ? null : isProbation.trim();
    }
}