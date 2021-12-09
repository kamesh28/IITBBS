
package com.smart.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

@Table(name = "Module")
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	

	private String name;
	//private Date date;
	private String mainauthor;
	//private String persons;
	private String coauthor;
	private String image;

	@Column(length = 100)
	private String description;
	
	//private Date createdModuleDate = new Date(System.currentTimeMillis());
	
	
	/*
	 * @Temporal(TemporalType.TIMESTAMP) private Date modulecreateDate = new
	 * Date(System.currentTimeMillis());
	 */
	@ManyToOne

	@JsonIgnore
	private User user;
	
	@OneToMany(cascade =CascadeType.ALL, fetch = FetchType.EAGER, mappedBy =  "modules")	
	private List<SubModule> submodules ;
	
	public List<SubModule> getSubmodules() {
		return submodules;
	}

	public void setSubmodules(List<SubModule> submodules) {
		this.submodules = submodules;
	}

	public Module() {
		super();
	}

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

	/*
	 * public Date getDate() { return date; }
	 * 
	 * public void setDate(Date date) { this.date = date; }
	 */
	
	  public String getMainauthor() { return mainauthor; }
	  
	  public void setMainauthor(String mainauthor) { this.mainauthor = mainauthor;
	  }
	 
	/*
	 * public String getCoauthor() { return coauthor; }
	 * 
	 * public void setCoauthor(String coauthor) { this.coauthor = coauthor; }
	 */
	

	public String getImage() {
		return image;
	}

	/*
	 * public String getPersons() { return persons; }
	 * 
	 * public void setPersons(String persons) { this.persons = persons; }
	 */

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		return this.cId == ((Module) obj).getcId();
	}
	
}
