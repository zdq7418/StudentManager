package com.zdq.studentmanager.bean;

/**
 * ScoreForm entity. @author MyEclipse Persistence Tools
 */

public class ScoreForm implements java.io.Serializable {

	// Fields

	private Integer scoreId;
	private Integer scoreCls;
	private Double scoreSco;
	private String studentNo;
	private Integer testId;
	private Integer studentId;

	// Constructors

	/** default constructor */
	public ScoreForm() {
	}

	/** full constructor */
	public ScoreForm(Integer scoreCls, Double scoreSco, String studentNo,
			Integer testId, Integer studentId) {
		this.scoreCls = scoreCls;
		this.scoreSco = scoreSco;
		this.studentNo = studentNo;
		this.testId = testId;
		this.studentId = studentId;
	}

	// Property accessors

	public Integer getScoreId() {
		return this.scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public Integer getScoreCls() {
		return this.scoreCls;
	}

	public void setScoreCls(Integer scoreCls) {
		this.scoreCls = scoreCls;
	}

	public Double getScoreSco() {
		return this.scoreSco;
	}

	public void setScoreSco(Double scoreSco) {
		this.scoreSco = scoreSco;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getTestId() {
		return this.testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

}