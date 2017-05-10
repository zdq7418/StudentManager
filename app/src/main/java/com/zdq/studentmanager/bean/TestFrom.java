package com.zdq.studentmanager.bean;

import java.sql.Timestamp;

/**
 * TestFrom entity. @author MyEclipse Persistence Tools
 */

public class TestFrom implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer courseId;
	private Timestamp testTime;
	private String testQihao;
	private String testName;

	// Constructors

	/** default constructor */
	public TestFrom() {
	}

	/** full constructor */
	public TestFrom(Integer courseId, Timestamp testTime, String testQihao,
			String testName) {
		this.courseId = courseId;
		this.testTime = testTime;
		this.testQihao = testQihao;
		this.testName = testName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Timestamp getTestTime() {
		return this.testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public String getTestQihao() {
		return this.testQihao;
	}

	public void setTestQihao(String testQihao) {
		this.testQihao = testQihao;
	}

	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}