package com.ge.inspection.ir.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class IssueModel {
    private String id;
    private String title;
    private String url;
    @JsonProperty("inspection")
    private List<IssueInspection> issueInspection;
  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<IssueInspection> getIssueInspection() {
		return issueInspection;
	}
	public void setIssueInspection(List<IssueInspection> issueInspection) {
		this.issueInspection = issueInspection;
	}
	public IssueModel(String id, String title, String url,
			List<IssueInspection> issueInspection) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.issueInspection = issueInspection;
	}
	
  
}
