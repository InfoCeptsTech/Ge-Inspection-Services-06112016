package com.ge.inspection.ir.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.InspectionDao;
import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.model.AssetModel;
import com.ge.inspection.ir.model.InspectionModel;
import com.ge.inspection.ir.model.IssueCount;
import com.ge.inspection.ir.model.MediaModel;
import com.ge.inspection.ir.util.JSONUtil;

@RestController
public class InspectionController {
 
	@Autowired
	private InspectionDao inspectionDao;
	
	@Autowired
	private IssueDao issueDao;
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/getInspection/inspectorId={inspectorId}", method = RequestMethod.GET)
	public String getAssets(@PathVariable String inspectorId){
		AssetModel[] assetModel=inspectionDao.getInspectionDtls(inspectorId);
		String inspectionJson=JSONUtil.toJson(assetModel);
		return inspectionJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/getAsset/inspectorId={inspectorId}", method = RequestMethod.GET)
	public String getAsset(@PathVariable String inspectorId){
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		String assetJson="";
		if(assetList.size()>0){
			String assetId=assetList.get(0);
			List<InspectionModel> inspectionList=inspectionDao.getMediaDate(inspectorId,assetId);
			
			Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionList.get(0).getInspectionId());
			AssetModel[] assetModel=createJson(inspectorId,assetList,inspectionList,mediaList,0,0);
			assetJson=JSONUtil.toJson(assetModel);	
		}
		
		return assetJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/getMediaDate/inspectorId={inspectorId}&assetId={assetId}", method = RequestMethod.GET)
	public String getMediaDate(@PathVariable String inspectorId,@PathVariable String assetId){
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		
		List<InspectionModel> inspectionList=inspectionDao.getMediaDate(inspectorId,assetId);
		int mediaIndex=0;
		for(InspectionModel inspectionModel:inspectionList){
			if(inspectionModel.getInspectionId().equals(inspectionList.get(0).getInspectionId())){
				break;
			}
			mediaIndex++;
		}
		
		Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionList.get(0).getInspectionId());
		AssetModel[] assetModel=createJson(inspectorId,assetList,inspectionList,mediaList,assetIndex,mediaIndex);
		String assetJson=JSONUtil.toJson(assetModel);
		
		return assetJson;
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/getMedia/inspectorId={inspectorId}&assetId={assetId}&inspectionId={inspectionId}", method = RequestMethod.GET)
	public String getMedia(@PathVariable String inspectorId,@PathVariable String assetId,@PathVariable String inspectionId){
		//Set<MediaModel> assetList=inspectionDao.getMedia(inspectorId,assetId,inspectionStart);
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		
		List<InspectionModel> inspectionList=inspectionDao.getMediaDate(inspectorId,assetId);
		int mediaIndex=0;
		for(InspectionModel inspectionModel:inspectionList){
			if(inspectionModel.getInspectionId().equals(inspectionId)){
				break;
			}
			mediaIndex++;
		}
		
		Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionId);
		AssetModel[] assetModel=createJson(inspectorId,assetList,inspectionList,mediaList,assetIndex,mediaIndex);
		String assetJson=JSONUtil.toJson(assetModel);
		
		return assetJson;
	}
	
	@CrossOrigin
	private AssetModel[] createJson(String inspectorId,List<String> assetList,List<InspectionModel> inspectionModelList,Set<MediaModel> mediaList,int assetIndex,int mediaIndex){
		
		AssetModel[] assetModelArray = new AssetModel[assetList.size()];
		int assetIndexLocal=0;
		List<Object[]>  issueCountObj=issueDao.getIssueCount(inspectorId);
		for(String asset:assetList){
			AssetModel assetModel=null;
			 int mediaIndexLocal=0;
			 for(InspectionModel inspectionModel:inspectionModelList){
				if(mediaIndexLocal==mediaIndex){
					
					/*
					if(startDurationList.get(0)!=null && endDurationList.get(endDurationList.size()-1)!=null){
						duration=String.valueOf(startDurationList.get(0).getDateTime()).split(" ")[1]+"-"+String.valueOf(endDurationList.get(endDurationList.size()-1).getDateTime()).split(" ")[1];	
					}*/
					inspectionModel.setMediaModel(mediaList);
				
			 }
				List<IssueCount> issueCountList=new ArrayList<IssueCount>();
				for(Object obj:issueCountObj){
					//System.out.println(obj);
					Object[] assetObj=(Object[]) obj;
					if(obj!=null && assetObj[0].equals(asset)){
						if(!assetObj[1].equals(null) && !assetObj[1].equals("null")){
							IssueCount issueCount=new IssueCount(String.valueOf(assetObj[1]),String.valueOf(assetObj[2]));
							issueCountList.add(issueCount);
						}
						
					}
				}
			if(assetIndexLocal==assetIndex){
				assetModel=new AssetModel(String.valueOf(mediaIndexLocal),asset,inspectionModelList,issueCountList);
			}else{
				assetModel=new AssetModel(String.valueOf(mediaIndexLocal),asset,null,issueCountList);
			}
			mediaIndexLocal++;
		  }
		assetModelArray[assetIndexLocal]=assetModel;
		assetIndexLocal++;
	
	}
		return assetModelArray;
}
}
