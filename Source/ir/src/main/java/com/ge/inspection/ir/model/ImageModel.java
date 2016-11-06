package com.ge.inspection.ir.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ImageModel {
  private String id;
  private String miniPath;
  private String megaPath;
  @JsonIgnore
  private Date inspectionEndDate;
  @JsonIgnore
  private Date inspectionStartDate;
  
  private String imgBinary;
  
  private List<Metadata> metadata;
  
public ImageModel(String id, String miniPath, String megaPath,Date inspectionEndDate,Date inspectionStartDate,List<Metadata> metadata,String imgBinary) {
	this.id = id;
	this.miniPath = miniPath;
	this.megaPath = megaPath;
	this.inspectionEndDate=inspectionEndDate;
	this.inspectionStartDate=inspectionStartDate;
	this.metadata=metadata;
	this.imgBinary=imgBinary;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
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
public Date getInspectionEndDate() {
	return inspectionEndDate;
}
public void setInspectionEndDate(Date inspectionEndDate) {
	this.inspectionEndDate = inspectionEndDate;
}
public Date getInspectionStartDate() {
	return inspectionStartDate;
}
public void setInspectionStartDate(Date inspectionStartDate) {
	this.inspectionStartDate = inspectionStartDate;
}
public List<Metadata> getMetadata() {
	return metadata;
}
public void setMetadata(List<Metadata> metadata) {
	this.metadata = metadata;
}
public String getImgBinary() {
	return imgBinary;
}
public void setImgBinary(String imgBinary) {
	this.imgBinary = imgBinary;
}
  
  
}
