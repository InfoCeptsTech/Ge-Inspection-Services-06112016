package com.ge.inspection.ir.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IssueDtlModel {
  private String id;	
  private String defectType;
  private String miniPath;
  private String megaPath;
  @JsonProperty("issues-markers")
  private String issuesMarker;
  private String statusType;
  private String toolTip;
  private String issueImage;
  
  
public IssueDtlModel(String id,String defectType, String miniPath, String megaPath,
		String issuesMarker, String statusType, String toolTip,String issueImage) {
	super();
	this.id=id;
	this.defectType = defectType;
	this.miniPath = miniPath;
	this.megaPath = megaPath;
	this.issuesMarker = issuesMarker;
	this.statusType = statusType;
	this.toolTip = toolTip;
	this.issueImage= issueImage;
}
public String getDefectType() {
	return defectType;
}
public void setDefectType(String defectType) {
	this.defectType = defectType;
}
public String getMiniPath() {
	return miniPath;
}
public void setMiniPath(String miniPath) {
	this.miniPath = miniPath;
}
public String getMegaPath() {
	return megaPath;
}
public void setMegaPath(String megaPath) {
	this.megaPath = megaPath;
}
public String getIssuesMarker() {
	return issuesMarker;
}
public void setIssuesMarker(String issuesMarker) {
	this.issuesMarker = issuesMarker;
}
public String getStatusType() {
	return statusType;
}
public void setStatusType(String statusType) {
	this.statusType = statusType;
}
public String getToolTip() {
	return toolTip;
}
public void setToolTip(String toolTip) {
	this.toolTip = toolTip;
}
public String getIssueImage() {
	return issueImage;
}
public void setIssueImage(String issueImage) {
	this.issueImage = issueImage;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
  
  
}
