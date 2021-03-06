package com.bohai.employeeSalary.entity;

import java.util.Date;

public class SysUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.ID
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.USERNAME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.PASSWORD
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.SALT
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String salt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.LOCKED
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String locked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.CREATE_TIME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.UPDATE_TIME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.FULL_NAME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String fullName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_USERS.DEP_NO
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    private String depNo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.ID
     *
     * @return the value of SYS_USERS.ID
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.ID
     *
     * @param id the value for SYS_USERS.ID
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.USERNAME
     *
     * @return the value of SYS_USERS.USERNAME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.USERNAME
     *
     * @param username the value for SYS_USERS.USERNAME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.PASSWORD
     *
     * @return the value of SYS_USERS.PASSWORD
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.PASSWORD
     *
     * @param password the value for SYS_USERS.PASSWORD
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.SALT
     *
     * @return the value of SYS_USERS.SALT
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.SALT
     *
     * @param salt the value for SYS_USERS.SALT
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.LOCKED
     *
     * @return the value of SYS_USERS.LOCKED
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getLocked() {
        return locked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.LOCKED
     *
     * @param locked the value for SYS_USERS.LOCKED
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.CREATE_TIME
     *
     * @return the value of SYS_USERS.CREATE_TIME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.CREATE_TIME
     *
     * @param createTime the value for SYS_USERS.CREATE_TIME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.UPDATE_TIME
     *
     * @return the value of SYS_USERS.UPDATE_TIME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.UPDATE_TIME
     *
     * @param updateTime the value for SYS_USERS.UPDATE_TIME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.FULL_NAME
     *
     * @return the value of SYS_USERS.FULL_NAME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.FULL_NAME
     *
     * @param fullName the value for SYS_USERS.FULL_NAME
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_USERS.DEP_NO
     *
     * @return the value of SYS_USERS.DEP_NO
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public String getDepNo() {
        return depNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_USERS.DEP_NO
     *
     * @param depNo the value for SYS_USERS.DEP_NO
     *
     * @mbggenerated Mon Aug 14 11:32:53 CST 2017
     */
    public void setDepNo(String depNo) {
        this.depNo = depNo == null ? null : depNo.trim();
    }
}