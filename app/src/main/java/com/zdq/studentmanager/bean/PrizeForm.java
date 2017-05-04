package com.zdq.studentmanager.bean;

/**
 * PrizeForm entity. @author MyEclipse Persistence Tools
 */

public class PrizeForm implements java.io.Serializable {

	// Fields

	private Integer prizeId;
	private String prizeName;
	private String prizeRem;

	// Constructors

	/** default constructor */
	public PrizeForm() {
	}

	/** full constructor */
	public PrizeForm(String prizeName, String prizeRem) {
		this.prizeName = prizeName;
		this.prizeRem = prizeRem;
	}

	// Property accessors

	public Integer getPrizeId() {
		return this.prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return this.prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getPrizeRem() {
		return this.prizeRem;
	}

	public void setPrizeRem(String prizeRem) {
		this.prizeRem = prizeRem;
	}

}