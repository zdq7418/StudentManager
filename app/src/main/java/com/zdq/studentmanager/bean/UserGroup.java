package com.zdq.studentmanager.bean;

import java.sql.Timestamp;

/**
 * UserGroup entity. @author MyEclipse Persistence Tools
 */

public class UserGroup implements java.io.Serializable {

	// Fields

	private Integer grpSeq;
	private String grpName;
	private String grpStatus;
	private String operUer;
	private Timestamp operTime;
	private String remark;

	// Constructors

	/** default constructor */
	public UserGroup() {
	}

	/** full constructor */
	public UserGroup(String grpName, String grpStatus, String operUer,
			Timestamp operTime, String remark) {
		this.grpName = grpName;
		this.grpStatus = grpStatus;
		this.operUer = operUer;
		this.operTime = operTime;
		this.remark = remark;
	}

	// Property accessors

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

	public String getGrpStatus() {
		return this.grpStatus;
	}

	public void setGrpStatus(String grpStatus) {
		this.grpStatus = grpStatus;
	}

	public String getOperUer() {
		return this.operUer;
	}

	public void setOperUer(String operUer) {
		this.operUer = operUer;
	}

	public Timestamp getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}