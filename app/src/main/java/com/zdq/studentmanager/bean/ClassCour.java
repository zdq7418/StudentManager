package com.zdq.studentmanager.bean;

/**
 * ClassCour entity. @author MyEclipse Persistence Tools
 */

public class ClassCour implements java.io.Serializable {

	// Fields

	private Integer classId;
	private Integer classNo;
	private Integer courseId;

	// Constructors

	/** default constructor */
	public ClassCour() {
	}

	/** full constructor */
	public ClassCour(Integer classNo, Integer courseId) {
		this.classNo = classNo;
		this.courseId = courseId;
	}

	// Property accessors

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassNo() {
		return this.classNo;
	}

	public void setClassNo(Integer classNo) {
		this.classNo = classNo;
	}

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

}