/*
 * package com.smart.entities;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.GeneratedValue; import javax.persistence.GenerationType;
 * import javax.persistence.Id; import javax.persistence.ManyToOne; import
 * javax.persistence.Table;
 * 
 * import com.fasterxml.jackson.annotation.JsonIgnore;
 * 
 * @Entity
 * 
 * @Table(name = "views") public class View {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO)
 * 
 * private int vId; private String task; private String timestamp; private
 * String description;
 * 
 * @ManyToOne
 * 
 * @JsonIgnore private Module module;
 * 
 * public int getvId() { return vId; }
 * 
 * public void setvId(int vId) { this.vId = vId; }
 * 
 * public String getTask() { return task; }
 * 
 * public void setTask(String task) { this.task = task; }
 * 
 * public String getTimestamp() { return timestamp; }
 * 
 * public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
 * 
 * public String getDescription() { return description; }
 * 
 * public void setDescription(String description) { this.description =
 * description; }
 * 
 * public Module getModule() { return module; }
 * 
 * public void setModule(Module module) { this.module = module; }
 * 
 * @Override public boolean equals(Object obj) { return this.vId == ((View)
 * obj).getvId(); }
 * 
 * }
 */