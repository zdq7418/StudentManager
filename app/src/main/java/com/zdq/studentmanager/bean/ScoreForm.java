package com.zdq.studentmanager.bean;

/**
 * ScoreForm entity. @author MyEclipse Persistence Tools
 */

public class ScoreForm implements java.io.Serializable {

	// Fields

	private Integer scoreId;
	private String scoreNo;
	private String scorePer;
	private String scoreCls;
	private String scoreStu;
	private String scoreCou;
	private Double scoreSco;
	private String studentNo;

	// Constructors

	/** default constructor */
	public ScoreForm() {
	}

	/** full constructor */
	public ScoreForm(String scoreNo, String scorePer, String scoreCls,
			String scoreStu, String scoreCou, Double scoreSco, String studentNo) {
		this.scoreNo = scoreNo;
		this.scorePer = scorePer;
		this.scoreCls = scoreCls;
		this.scoreStu = scoreStu;
		this.scoreCou = scoreCou;
		this.scoreSco = scoreSco;
		this.studentNo = studentNo;
	}

	// Property accessors

	public Integer getScoreId() {
		return this.scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public String getScoreNo() {
		return this.scoreNo;
	}

	public void setScoreNo(String scoreNo) {
		this.scoreNo = scoreNo;
	}

	public String getScorePer() {
		return this.scorePer;
	}

	public void setScorePer(String scorePer) {
		this.scorePer = scorePer;
	}

	public String getScoreCls() {
		return this.scoreCls;
	}

	public void setScoreCls(String scoreCls) {
		this.scoreCls = scoreCls;
	}

	public String getScoreStu() {
		return this.scoreStu;
	}

	public void setScoreStu(String scoreStu) {
		this.scoreStu = scoreStu;
	}

	public String getScoreCou() {
		return this.scoreCou;
	}

	public void setScoreCou(String scoreCou) {
		this.scoreCou = scoreCou;
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

}