package com.smart.entities;

import java.util.Date;

public class ViewEntireData {
	
	private int cId;
	private String name;
	private String mainauthor;
	private String coauthor;
	private String description; 
	private int sId;
	private String workitem;
	private String sRemarks;
	private Date startdate;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainauthor() {
		return mainauthor;
	}
	public void setMainauthor(String mainauthor) {
		this.mainauthor = mainauthor;
	}
	public String getCoauthor() {
		return coauthor;
	}
	public void setCoauthor(String coauthor) {
		this.coauthor = coauthor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	public String getWorkitem() {
		return workitem;
	}
	public void setWorkitem(String workitem) {
		this.workitem = workitem;
	}
	public String getsRemarks() {
		return sRemarks;
	}
	public void setsRemarks(String sRemarks) {
		this.sRemarks = sRemarks;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

}
