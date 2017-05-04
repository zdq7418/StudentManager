package com.zdq.studentmanager.bean;

import java.sql.Timestamp;

/**
 * UserForm entity. @author MyEclipse Persistence Tools
 */

public class UserForm implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String userAcct;
	private String passwd;
	private String userName;
	private String depart;
	private String conPhone;
	private Integer grpSeq;
	private String grpName;
	private Integer roleSeq;
	private String roleName;
	private String userStatus;
	private Timestamp crtTime;
	private String remark;
	private String loginStatus;

	// Constructors

	/** default constructor */
	public UserForm() {
	}

	/** minimal constructor */
	public UserForm(String userAcct) {
		this.userAcct = userAcct;
	}

	/** full constructor */
	public UserForm(String userAcct, String passwd, String userName,
			String depart, String conPhone, Integer grpSeq, String grpName,
			Integer roleSeq, String roleName, String userStatus,
			Timestamp crtTime, String remark, String loginStatus) {
		this.userAcct = userAcct;
		this.passwd = passwd;
		this.userName = userName;
		this.depart = depart;
		this.conPhone = conPhone;
		this.grpSeq = grpSeq;
		this.grpName = grpName;
		this.roleSeq = roleSeq;
		this.roleName = roleName;
		this.userStatus = userStatus;
		this.crtTime = crtTime;
		this.remark = remark;
		this.loginStatus = loginStatus;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserAcct() {
		return this.userAcct;
	}

	public void setUserAcct(String userAcct) {
		this.userAcct = userAcct;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepart() {
		return this.depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getConPhone() {
		return this.conPhone;
	}

	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}

	public Integer getGrpSeq() {
		return this.grpSeq;
	}

	public void setGrpSeq(Integer grpSeq) {
		this.grpSeq = grpSeq;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public Integer getRoleSeq() {
		return this.roleSeq;
	}

	public void setRoleSeq(Integer roleSeq) {
		this.roleSeq = roleSeq;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Timestamp getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Timestamp crtTime) {
		this.crtTime = crtTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

}