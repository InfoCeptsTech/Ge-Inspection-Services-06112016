package com.ge.inspection.ir.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class InspectionModel {
    private Date date;
    private String duration;
    private String inspectionId;
    @JsonProperty("phase")
    private Set<MediaModel> mediaModel;
    private String url="my-media";
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
	public Set<MediaModel> getMediaModel() {
		return mediaModel;
	}
	public void setMediaModel(Set<MediaModel> mediaModel) {
		this.mediaModel = mediaModel;
	}
	
	public InspectionModel(Date date, String duration, String inspectionId,
			Set<MediaModel> mediaModel, String url) {
		super();
		this.date = date;
		this.duration = duration;
		this.inspectionId = inspectionId;
		this.mediaModel = mediaModel;
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	
  
}
