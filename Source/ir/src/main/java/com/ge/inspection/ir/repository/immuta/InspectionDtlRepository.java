package com.ge.inspection.ir.repository.immuta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ge.inspection.ir.domain.immuta.InspectionDtls;

@Repository
public interface InspectionDtlRepository extends JpaRepository<InspectionDtls, String>{
	
	// List<InspectionDtls> findByInspectorId(@Param("inspectorId") String inspectorId);
	 
	// @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) order by u.inspectionId")
	// List<InspectionDtls> findIssueByInspectorId(@Param("inspectorId") String inspectorId);
	
	 @Query("SELECT distinct  (u.assetId) FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) ")
	 List<String> getAsset(@Param("inspectorId") String inspectorId); 
	 
	 @Query("SELECT distinct  (u.inspectionPhaseId) FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and lower(u.assetId)=lower(:assetId) order by u.inspectionPhaseId")
	 List<String> getPhase(@Param("inspectorId") String inspectorId,@Param("assetId")String assetId);
	 
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.assetId)=LOWER(:assetId) and lower(u.inspectionPhaseId)=lower(:inspectionPhaseId) and LOWER(u.inspectorId) = LOWER(:inspectorId) order by u.inspectionPhaseId")
	 List<InspectionDtls> getMediaDate(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId,@Param("inspectionPhaseId")String inspectionPhaseId);
	 
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.assetId)=LOWER(:assetId) and LOWER(u.inspectorId) = LOWER(:inspectorId) order by u.inspectionPhaseId")
	 List<InspectionDtls> getMediaDate(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId);
	
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId)  and LOWER(u.assetId)=LOWER(:assetId) and lower(u.inspectionPhaseId)=lower(:inspectionPhaseId) and u.inspectionId=:inspectionId order by u.inspectionPhaseId")
	 List<InspectionDtls> getMedia(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId,@Param("inspectionId") String inspectionId,@Param("inspectionPhaseId")String inspectionPhaseId);
	 
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId)  and LOWER(u.assetId)=LOWER(:assetId)  and u.inspectionId=:inspectionId order by u.inspectionPhaseId")
	 List<InspectionDtls> getMedia(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId,@Param("inspectionId") String inspectionId);
	
	// @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId)  and LOWER(u.assetId)=LOWER(:assetId) and u.inspectionPhaseId=:inspectionPhaseId")
	// List<InspectionDtls> getMediaDateByPhase(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId,@Param("inspectionPhaseId") String inspectionPhaseId);

}
