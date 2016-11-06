package com.ge.inspection.ir.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class IssueInspection {
  private String url;
  private Date date;
  private String duration;
  private String inspectionId;
  @JsonProperty("issues")
  private Set<IssueDtlModel> issueDtlModel;
  
  
public IssueInspection(String url, Date date, String duration,
		Set<IssueDtlModel> issueDtlModel,String inspectionId) {
	super();
	this.url = url;
	this.date = date;
	this.duration = duration;
	this.issueDtlModel = issueDtlModel;
	this.inspectionId=inspectionId;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public Set<IssueDtlModel> getIssueDtlModel() {
	return issueDtlModel;
}
public void setIssueDtlModel(Set<IssueDtlModel> issueDtlModel) {
	this.issueDtlModel = issueDtlModel;
}
public String getInspectionId() {
	return inspectionId;
}
public void setInspectionId(String inspectionId) {
	this.inspectionId = inspectionId;
}
  
  
}
