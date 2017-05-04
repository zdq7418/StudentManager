package com.zdq.studentmanager.bean;

import java.sql.Timestamp;

/**
 * PrizeStud entity. @author MyEclipse Persistence Tools
 */

public class PrizeStud implements java.io.Serializable {

	// Fields

	private Integer prizeId;
	private String prizeNo;
	private String prizeStu;
	private Timestamp prizeDat;

	// Constructors

	/** default constructor */
	public PrizeStud() {
	}

	/** full constructor */
	public PrizeStud(String prizeNo, String prizeStu, Timestamp prizeDat) {
		this.prizeNo = prizeNo;
		this.prizeStu = prizeStu;
		this.prizeDat = prizeDat;
	}

	// Property accessors

	public Integer getPrizeId() {
		return this.prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeNo() {
		return this.prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	public String getPrizeStu() {
		return this.prizeStu;
	}

	public void setPrizeStu(String prizeStu) {
		this.prizeStu = prizeStu;
	}

	public Timestamp getPrizeDat() {
		return this.prizeDat;
	}

	public void setPrizeDat(Timestamp prizeDat) {
		this.prizeDat = prizeDat;
	}

}