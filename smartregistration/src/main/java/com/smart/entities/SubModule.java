package com.smart.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "submod")
public class SubModule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sId;
	private String workitem;
	private String sRemarks;
	private Date startdate;
	private Date enddate;
	
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	@ManyToOne
	@JoinColumn(name="cId")
	private Module modules;
	
	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	
	
	

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
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

	public Module getModules() {
		return modules;
	}

	public void setModules(Module module) {
		this.modules = module;
	}

	@Override
	public String toString() {
		return "SubModule [sId=" + sId + ", workitem=" + workitem + ", sRemarks=" + sRemarks + ", startdate="
				+ startdate + ", module=" + modules + "]";
	}
	
	
	

}
