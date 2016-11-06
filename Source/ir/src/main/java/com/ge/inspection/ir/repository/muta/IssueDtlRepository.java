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

	@Query("SELECT u FROM InspectionMedia u WHERE u.defectType!=null and LOWER(u.inspectorId) = LOWER(:inspectorId) and LOWER(u.assetId) = LOWER(:assetId) order by u.inspectionId")
	List<InspectionMedia> getIssueDate(
			@Param("inspectorId") String inspectorId,
			@Param("assetId") String assetId);

	@Query("SELECT u FROM InspectionMedia u WHERE u.defectType!=null and LOWER(u.inspectorId) = LOWER(:inspectorId) and LOWER(u.assetId) = LOWER(:assetId) and LOWER(u.inspectionId) = LOWER(:inspectionId) order by u.inspectionId")
	List<InspectionMedia> findIssue(@Param("inspectorId") String inspectorId,
			@Param("assetId") String assetId,
			@Param("inspectionId") String inspectionId);

	// @Query("SELECT u FROM InspectionMedia u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and u.issueDate=:issueDate")
	// List<InspectionMedia> findByInspectorId(@Param("inspectorId") String
	// inspectorId,@Param("issueDate") Date issueDate);

	@Query("SELECT u FROM InspectionMedia u WHERE u.defectType!=null and LOWER(u.inspectorId) = LOWER(:inspectorId) order by u.inspectionId")
	List<InspectionMedia> findIssueMarker(
			@Param("inspectorId") String inspectorId);

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
	@Query("UPDATE InspectionMedia set statusType=:statusType,inspectorId=:inspectorId,defectType=:defectType,inspectionId=:inspectionId,assetId=:assetId,inspectionDate=:inspectionDate where blobId=:blobId")
	void updateIssueType(@Param("blobId") String blobId,
			@Param("statusType") String statusType,
			@Param("inspectorId") String inspectorId,
			@Param("defectType") String defectType,
			@Param("inspectionId") String inspectionId,
			@Param("assetId") String assetId,
			@Param("inspectionDate") Date inspectionDate);
	
	@Transactional
	@Modifying
	@Query("UPDATE InspectionMedia set comment=:comment,inspectorId=:inspectorId,inspectionId=:inspectionId,assetId=:assetId,inspectionDate=:inspectionDate where blobId=:blobId")
	void updateComments(@Param("blobId") String blobId,
			@Param("inspectorId") String inspectorId,
			@Param("comment") String comment,
			@Param("inspectionId") String inspectionId,
			@Param("assetId") String assetId,
			@Param("inspectionDate") Date inspectionDate);
	
	@Query("select u.assetId,u.defectType as asset,count(u) as total from InspectionMedia as u where inspectorId=:inspectorId group by u.assetId,u.defectType order by u.assetId asc")
	List<Object[]> getIssueCount(@Param("inspectorId") String inspectorId);

}
