package com.zdq.studentmanager.bean;

/**
 * UserRome entity. @author MyEclipse Persistence Tools
 */

public class UserRome implements java.io.Serializable {

	// Fields

	private Integer roMeSeq;
	private Integer roleSeq;
	private Integer menuSeq;
	private String remark;

	// Constructors

	/** default constructor */
	public UserRome() {
	}

	/** full constructor */
	public UserRome(Integer roleSeq, Integer menuSeq, String remark) {
		this.roleSeq = roleSeq;
		this.menuSeq = menuSeq;
		this.remark = remark;
	}

	// Property accessors

	public Integer getRoMeSeq() {
		return this.roMeSeq;
	}

	public void setRoMeSeq(Integer roMeSeq) {
		this.roMeSeq = roMeSeq;
	}

	public Integer getRoleSeq() {
		return this.roleSeq;
	}

	public void setRoleSeq(Integer roleSeq) {
		this.roleSeq = roleSeq;
	}

	public Integer getMenuSeq() {
		return this.menuSeq;
	}

	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}