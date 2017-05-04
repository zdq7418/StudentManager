package com.zdq.studentmanager.bean;

/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */

public class UserRole implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String roleName;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(String roleName) {
		this.roleName = roleName;
	}

	// Property accessors

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}