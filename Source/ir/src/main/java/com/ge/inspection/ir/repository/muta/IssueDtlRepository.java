package com.ge.inspection.ir.repository.muta;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ge.inspection.ir.domain.muta.InspectionMedia;

public interface IssueDtlRepository extends
		JpaRepository<InspectionMedia, String> {

	@Query("SELECT u FROM InspectionMedia u WHERE u.defectType!=null and LOWER(u.userId) =LOWER(:userId) AND LOWER(u.inspectorId) = LOWER(:inspectorId) and LOWER(u.assetId) = LOWER(:assetId) order by u.inspectionId")
	List<InspectionMedia> getIssueDate(
			@Param("inspectorId") String inspectorId,
			@Param("assetId") String assetId,
			@Param("userId") String userId);

	@Query("SELECT u FROM InspectionMedia u WHERE u.defectType!=null and LOWER(u.userId) =LOWER(:userId) AND LOWER(u.inspectorId) = LOWER(:inspectorId) and LOWER(u.assetId) = LOWER(:assetId) order by u.inspectionId")
	List<InspectionMedia> findIssue(@Param("inspectorId") String inspectorId,
			@Param("assetId") String assetId,
			@Param("userId") String userId);

	// @Query("SELECT u FROM InspectionMedia u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and u.issueDate=:issueDate")
	// List<InspectionMedia> findByInspectorId(@Param("inspectorId") String
	// inspectorId,@Param("issueDate") Date issueDate);

	@Query("SELECT u FROM InspectionMedia u WHERE u.defectType!=null and LOWER(u.userId) =LOWER(:userId) AND LOWER(u.inspectorId) = LOWER(:inspectorId) order by u.inspectionId")
	List<InspectionMedia> findIssueMarker(
			@Param("inspectorId") String inspectorId,
			@Param("userId") String userId);

	@Transactional
	@Modifying
	@Query("UPDATE InspectionMedia set statusType=:statusType where blobId=:blobId")
	void updateIssue(@Param("blobId") String blobId,
			@Param("statusType") String statusType);

	@Transactional
	@Modifying
	@Query("UPDATE InspectionMedia set statusType=:statusType where blobId=:blobId")
	void addUpdateIssue(@Param("blobId") String blobId,
			@Param("statusType") String statusType);

	@Transactional
	@Modifying
	@Query("UPDATE InspectionMedia set issueImage=:issueImage,commentJson=:commentJson,statusType=:statusType,description=:description,annotedComments=:annotedComments,annotatedMetadata=:annotatedMetadata,comment=:comment,defectType=:defectType,inspectionDate=:inspectionDate,inspectionRequirement=:inspectionRequirement where blobId=:blobId and userId=:userId")
	void updateIssue(@Param("blobId") String blobId,
			@Param("statusType") String statusType,
			@Param("defectType") String defectType,
			@Param("comment") String comment,
			@Param("description") String description,
			@Param("annotedComments") String annotedComments,
			@Param("annotatedMetadata") String annotatedMetadata,
			@Param("issueImage") byte[] issueImage,
			@Param("commentJson") String commentJson,
			@Param("inspectionDate") Date inspectionDate,
			@Param("userId") String userId,
			@Param("inspectionRequirement") String inspectionRequirement);
	
	@Transactional
	@Modifying
	@Query("UPDATE InspectionMedia set comment=:comment,inspectionId=:inspectionId,inspectionDate=:inspectionDate where blobId=:blobId and userId=:userId")
	void updateComments(@Param("blobId") String blobId,
			@Param("comment") String comment,
			@Param("userId") String userId,
			@Param("inspectionDate") Date inspectionDate);
	
	@Transactional
	@Modifying
	@Query("UPDATE InspectionMedia set statusType=:statusType,defectType=:defectType,inspectionDate=:inspectionDate where blobId=:blobId and userId=:userId")
	void updateDefectType(@Param("blobId") String blobId,
			@Param("statusType") String statusType,
			@Param("userId") String userId,
			@Param("defectType") String defectType,
			@Param("inspectionDate") Date inspectionDate);
	
	@Query("select u.assetId,u.defectType as asset,count(u) as total from InspectionMedia as u where inspectorId=:inspectorId and lower(u.userId)=lower(:userId) group by u.assetId,u.defectType order by u.assetId asc")
	List<Object[]> getIssueCount(@Param("inspectorId") String inspectorId,@Param("userId") String userId);
	
	@Query(" select count(u) from InspectionMedia as u where lower(u.userId)=lower(:userId) and lower(u.blobId)=lower(:blobId)")
	int getCount(@Param("userId") String userId,@Param("blobId") String blobId);

}
