package com.ge.inspection.ir.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@Value("${media.location}")
 	private String mediaLocation;
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/inspection/getMedia/mediaId={imageName}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getPhotoBytes(@PathVariable String imageName) throws IOException {
	    String imagePath=mediaLocation+"/Polymer/images/"+imageName+".JPG";
		File imageFile=new File(imagePath);
		BufferedImage  image =null;
		try{
			image= ImageIO.read(imageFile);
		}catch(Exception e){
			image=ImageIO.read(new File(mediaLocation+"/Polymer/images/"+imageName+".jpg"));
		}
	   
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write( image, "jpg", baos );
	    baos.flush();
	    byte[] imageInByte = baos.toByteArray();
	    //return imageInByte;
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setAccessControlAllowCredentials(true);
	    responseHeaders.setAccessControlAllowOrigin("*");
	    return new ResponseEntity<byte[]>(imageInByte, responseHeaders, HttpStatus.CREATED);
	}
	 
	
	/*
	@CrossOrigin
	@RequestMapping(value = "/inspection/getInspection/inspectorId={inspectorId}", method = RequestMethod.GET)
	public String getAssets(@PathVariable String inspectorId){
		AssetModel[] assetModel=inspectionDao.getInspectionDtls(inspectorId);
		String inspectionJson=JSONUtil.toJson(assetModel);
		return inspectionJson;
	}*/
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/getAsset/inspectorId={inspectorId}&userId={userId}", method = RequestMethod.GET)
	public String getAsset(@PathVariable String inspectorId,@PathVariable String userId){
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		String assetJson="";
		if(assetList.size()>0){
			String assetId=assetList.get(0);
			List<String> phaseList=inspectionDao.getPhase(inspectorId, assetId);
			if(phaseList!=null && phaseList.size()>0){
				List<InspectionModel> inspectionList=inspectionDao.getMediaDate(inspectorId,assetId);
				if(inspectionList.size()>0){
					Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionList.get(0).getInspectionId(),phaseList.get(0));
					AssetModel[] assetModel=createJson(userId,inspectorId,assetList,inspectionList,mediaList,0,0);
					assetJson=JSONUtil.toJson(assetModel);	
				}
			}
		}
		
		return assetJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/getMediaDate/inspectorId={inspectorId}&assetId={assetId}&userId={userId}&phaseId={inspectionPhaseId}", method = RequestMethod.GET)
	public String getMediaDate(@PathVariable String inspectorId,@PathVariable String assetId,@PathVariable String userId,@PathVariable String inspectionPhaseId){
		String assetJson="";
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		
		if(inspectionPhaseId==null||inspectionPhaseId.equals("null")){
			List<String> phaseList=inspectionDao.getPhase(inspectorId, assetId);
			inspectionPhaseId=phaseList.get(0);
		}
		
		List<InspectionModel> inspectionList=inspectionDao.getMediaDate(inspectorId,assetId,inspectionPhaseId);
		int mediaIndex=0;
		for(InspectionModel inspectionModel:inspectionList){
			if(inspectionModel.getInspectionId().equals(inspectionList.get(0).getInspectionId())){
				break;
			}
			mediaIndex++;
		}
		
		if(inspectionList.size()>0){
			Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionList.get(0).getInspectionId(),inspectionPhaseId);
			AssetModel[] assetModel=createJson(userId,inspectorId,assetList,inspectionList,mediaList,assetIndex,mediaIndex);
			assetJson=JSONUtil.toJson(assetModel);
		}
			
		return assetJson;
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/getMedia/inspectorId={inspectorId}&assetId={assetId}&inspectionId={inspectionId}&pId={phaseId}&userId={userId}", method = RequestMethod.GET)
	public String getMedia(@PathVariable String inspectorId,@PathVariable String assetId,@PathVariable String inspectionId,@PathVariable String phaseId,@PathVariable String userId){
		//Set<MediaModel> assetList=inspectionDao.getMedia(inspectorId,assetId,inspectionStart);
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		
		List<InspectionModel> inspectionList=inspectionDao.getMediaDate(inspectorId,assetId,phaseId);
		int mediaIndex=0;
		for(InspectionModel inspectionModel:inspectionList){
			if(inspectionModel.getInspectionId().equals(inspectionId)){
				break;
			}
			mediaIndex++;
		}
		
		Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionId,phaseId);
		AssetModel[] assetModel=createJson(userId,inspectorId,assetList,inspectionList,mediaList,assetIndex,mediaIndex);
		String assetJson=JSONUtil.toJson(assetModel);
		
		return assetJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/getMedia/inspectorId={inspectorId}&assetId={assetId}&phaseId={inspectionPhaseId}", method = RequestMethod.GET)
	public String getPhaseDtls(@PathVariable String inspectorId,@PathVariable String assetId,@PathVariable String inspectionPhaseId){
		
		return null;
	}
	
	private AssetModel[] createJson(String userId,String inspectorId,List<String> assetList,List<InspectionModel> inspectionModelList,Set<MediaModel> mediaList,int assetIndex,int mediaIndex){
		
		AssetModel[] assetModelArray = new AssetModel[assetList.size()];
		int assetIndexLocal=0;
		List<Object[]>  issueCountObj=issueDao.getIssueCount(inspectorId,userId);
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
