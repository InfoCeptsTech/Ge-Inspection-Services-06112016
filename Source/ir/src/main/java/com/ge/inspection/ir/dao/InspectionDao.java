package com.ge.inspection.ir.dao;

import java.util.List;
import java.util.Set;

import com.ge.inspection.ir.model.AssetModel;
import com.ge.inspection.ir.model.InspectionModel;
import com.ge.inspection.ir.model.MediaModel;

public interface InspectionDao {
	
	public AssetModel[]  getInspectionDtls(String inspectorId);
	
	public List<String>  getAsset(String inspectorId);
	
	public List<InspectionModel>  getMediaDate(String inspectorId,String assetId);
	
	public Set<MediaModel>  getMedia(String inspectorId,String assetId,String inspectionId);
 
}
