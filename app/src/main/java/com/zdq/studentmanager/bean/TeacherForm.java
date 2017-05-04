package com.zdq.studentmanager.bean;

/**
 * TeacherForm entity. @author MyEclipse Persistence Tools
 */

public class TeacherForm implements java.io.Serializable {

	// Fields

	private Integer teacherId;
	private String teacherName;
	private String teacherAccount;
	private String teacherTel;
	private String teacherUrlimage;

	// Constructors

	/** default constructor */
	public TeacherForm() {
	}

	/** full constructor */
	public TeacherForm(String teacherName, String teacherAccount,
			String teacherTel, String teacherUrlimage) {
		this.teacherName = teacherName;
		this.teacherAccount = teacherAccount;
		this.teacherTel = teacherTel;
		this.teacherUrlimage = teacherUrlimage;
	}

	// Property accessors

	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherAccount() {
		return this.teacherAccount;
	}

	public void setTeacherAccount(String teacherAccount) {
		this.teacherAccount = teacherAccount;
	}

	public String getTeacherTel() {
		return this.teacherTel;
	}

	public void setTeacherTel(String teacherTel) {
		this.teacherTel = teacherTel;
	}

	public String getTeacherUrlimage() {
		return this.teacherUrlimage;
	}

	public void setTeacherUrlimage(String teacherUrlimage) {
		this.teacherUrlimage = teacherUrlimage;
	}

	@Override
	public String toString() {
		return teacherName;
	}
}