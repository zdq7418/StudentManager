package com.zdq.studentmanager.bean;

/**
 * CourseForm entity. @author MyEclipse Persistence Tools
 */

public class CourseForm implements java.io.Serializable {

	// Fields

	private Integer courseId;
	private String courseName;
	private String courseRem;

	// Constructors

	/** default constructor */
	public CourseForm() {
	}

	/** full constructor */
	public CourseForm(String courseName, String courseRem) {
		this.courseName = courseName;
		this.courseRem = courseRem;
	}

	// Property accessors

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseRem() {
		return this.courseRem;
	}

	public void setCourseRem(String courseRem) {
		this.courseRem = courseRem;
	}

}