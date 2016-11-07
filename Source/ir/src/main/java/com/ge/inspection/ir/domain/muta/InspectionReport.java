package com.ge.inspection.ir.domain.muta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inspection_report")
public class InspectionReport {
	@Id 
	@Column(name="user_id")
	private String userId;
	
	@Column(length = 4000) 
	private String comment; 
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
