package com.ge.inspection.ir.domain.muta;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name="inspection_media")
public class InspectionMedia {
	@Column(length = 4000) 
	private String comment; 
	@Id 
	private String blobId; 
	private String inspectorId; 
	@Temporal(TemporalType.TIMESTAMP) 
	private Date inspectionDate; 
	private String assetId; 
	private String statusType; 
	private String defectType; 
	private String inspectionId; 
	@Column(length = 4000) 
	private String annotatedMetadata; 
	@Column(length = 4000) 
	private String description; 
	@Column(length = 4000)
	private String annotedComments;
	@Column(name = "issueimage")
	private byte[] issueImage;
	
	
	public InspectionMedia() {
		super();
	}

	public InspectionMedia(String comment, String blobId,
			String inspectorId, Date inspectionDate, String statusType,
			String defectType, String annotatedMetadata, String description) {
		this.comment=comment;
		this.blobId=blobId;
		this.inspectorId=inspectorId;
		this.inspectionDate=inspectionDate;
		this.statusType=statusType;
		this.defectType=defectType;
		this.annotatedMetadata=annotatedMetadata;
		this.description=description;
		
	}
	
	public InspectionMedia(String comment, String blobId,
			String inspectorId, Date inspectionDate, String statusType,
			String defectType, String annotatedMetadata, String description,
			String assetId, String inspectionId,byte[] issueImage,String annotedComments) {
		this.comment=comment;
		this.blobId=blobId;
		this.inspectorId=inspectorId;
		this.inspectionDate=inspectionDate;
		this.statusType=statusType;
		this.defectType=defectType;
		this.annotatedMetadata=annotatedMetadata;
		this.description=description;
		this.assetId=assetId;
		this.inspectionId=inspectionId;
		this.issueImage=issueImage;
		this.annotedComments=annotedComments;
	}
	public InspectionMedia(String blobId, Date inspectionDate, String statusType) {
		this.blobId=blobId;
		this.inspectionDate=inspectionDate;
		this.statusType=statusType;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBlobId() {
		return blobId;
	}
	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}
	public String getInspectorId() {
		return inspectorId;
	}
	public void setInspectorId(String inspectorId) {
		this.inspectorId = inspectorId;
	}
	public Date getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	public String getAnnotatedMetadata() {
		return annotatedMetadata;
	}
	public void setAnnotatedMetadata(String annotatedMetadata) {
		this.annotatedMetadata = annotatedMetadata;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnnotedComments() {
		return annotedComments;
	}
	public void setAnnotedComments(String annotedComments) {
		this.annotedComments = annotedComments;
	}

	public byte[] getIssueImage() {
		return issueImage;
	}

	public void setIssueImage(byte[] issueImage) {
		this.issueImage = issueImage;
	} 
	
}
