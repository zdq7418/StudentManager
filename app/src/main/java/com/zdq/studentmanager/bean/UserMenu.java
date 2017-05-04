package com.zdq.studentmanager.bean;

/**
 * UserMenu entity. @author MyEclipse Persistence Tools
 */

public class UserMenu implements java.io.Serializable {

	// Fields

	private Integer menuSeq;
	private String menuName;
	private Integer fmenuSeq;
	private Integer menuLayer;
	private String menuPath;

	// Constructors

	/** default constructor */
	public UserMenu() {
	}

	/** full constructor */
	public UserMenu(String menuName, Integer fmenuSeq, Integer menuLayer,
			String menuPath) {
		this.menuName = menuName;
		this.fmenuSeq = fmenuSeq;
		this.menuLayer = menuLayer;
		this.menuPath = menuPath;
	}

	// Property accessors

	public Integer getMenuSeq() {
		return this.menuSeq;
	}

	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getFmenuSeq() {
		return this.fmenuSeq;
	}

	public void setFmenuSeq(Integer fmenuSeq) {
		this.fmenuSeq = fmenuSeq;
	}

	public Integer getMenuLayer() {
		return this.menuLayer;
	}

	public void setMenuLayer(Integer menuLayer) {
		this.menuLayer = menuLayer;
	}

	public String getMenuPath() {
		return this.menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

}