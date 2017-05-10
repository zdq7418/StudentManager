package com.zdq.studentmanager.bean;

import java.sql.Timestamp;

/**
 * StudentForm entity. @author MyEclipse Persistence Tools
 */

public class StudentForm implements java.io.Serializable {

	// Fields

	private Integer studentId;
	private String studentNo;
	private String studentName;
	private String studentSex;
	private Timestamp studentBir;
	private int studentCla;
	private String studentTel;
	private Timestamp studentEsd;
	private String studentAdd;
	private String studentRem;
	private String studentAccout;
	private String studentUrlimage;

	// Constructors

	/** default constructor */
	public StudentForm() {
	}

	/** full constructor */
	public StudentForm(String studentNo, String studentName, String studentSex,
			Timestamp studentBir, int studentCla, String studentTel,
			Timestamp studentEsd, String studentAdd, String studentRem,
			String studentAccout, String studentUrlimage) {
		this.studentNo = studentNo;
		this.studentName = studentName;
		this.studentSex = studentSex;
		this.studentBir = studentBir;
		this.studentCla = studentCla;
		this.studentTel = studentTel;
		this.studentEsd = studentEsd;
		this.studentAdd = studentAdd;
		this.studentRem = studentRem;
		this.studentAccout = studentAccout;
		this.studentUrlimage = studentUrlimage;
	}

	// Property accessors

	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSex() {
		return this.studentSex;
	}

	public void setStudentSex(String studentSex) {
		this.studentSex = studentSex;
	}

	public Timestamp getStudentBir() {
		return this.studentBir;
	}

	public void setStudentBir(Timestamp studentBir) {
		this.studentBir = studentBir;
	}

	public int getStudentCla() {
		return this.studentCla;
	}

	public void setStudentCla(int studentCla) {
		this.studentCla = studentCla;
	}

	public String getStudentTel() {
		return this.studentTel;
	}

	public void setStudentTel(String studentTel) {
		this.studentTel = studentTel;
	}

	public Timestamp getStudentEsd() {
		return this.studentEsd;
	}

	public void setStudentEsd(Timestamp studentEsd) {
		this.studentEsd = studentEsd;
	}

	public String getStudentAdd() {
		return this.studentAdd;
	}

	public void setStudentAdd(String studentAdd) {
		this.studentAdd = studentAdd;
	}

	public String getStudentRem() {
		return this.studentRem;
	}

	public void setStudentRem(String studentRem) {
		this.studentRem = studentRem;
	}

	public String getStudentAccout() {
		return this.studentAccout;
	}

	public void setStudentAccout(String studentAccout) {
		this.studentAccout = studentAccout;
	}

	public String getStudentUrlimage() {
		return this.studentUrlimage;
	}

	public void setStudentUrlimage(String studentUrlimage) {
		this.studentUrlimage = studentUrlimage;
	}

	@Override
	public String toString() {
		return studentName;
	}
}