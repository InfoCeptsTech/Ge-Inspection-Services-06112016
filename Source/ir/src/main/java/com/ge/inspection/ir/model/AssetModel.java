package com.ge.inspection.ir.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AssetModel {
	private String id;
    private String title;
    private String url="my-assets";
    private List<IssueCount> issueCount;
    private List<InspectionModel> inspection;
    
	public AssetModel(String id, String title,List<InspectionModel> inspection,List<IssueCount> issueCount) {
		this.id=id;
		this.title = title;
		this.inspection = inspection;
		this.issueCount=issueCount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<InspectionModel> getInspection() {
		return inspection;
	}
	public void setInspection(List<InspectionModel> inspection) {
		this.inspection = inspection;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<IssueCount> getIssueCount() {
		return issueCount;
	}
	public void setIssueCount(List<IssueCount> issueCount) {
		this.issueCount = issueCount;
	}
  
}
