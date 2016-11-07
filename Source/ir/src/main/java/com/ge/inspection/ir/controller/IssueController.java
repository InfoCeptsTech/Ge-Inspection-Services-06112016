package com.ge.inspection.ir.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.InspectionDao;
import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueDtlModel;
import com.ge.inspection.ir.model.IssueInspection;
import com.ge.inspection.ir.model.IssueMarkerModel;
import com.ge.inspection.ir.model.IssueModel;
import com.ge.inspection.ir.util.ImageUtil;
import com.ge.inspection.ir.util.JSONUtil;

@RestController
public class IssueController {
	@Autowired
	private IssueDao issueDaoImpl;
	
	@Autowired
	private InspectionDao inspectionDao;
	
	@Value("${media.location}")
 	private String mediaLocation;
	@Autowired
	private ImageUtil imageUtil;
	
	
	private String getValue(Object descObject,Object commentObj) {
		StringBuilder commentString=new StringBuilder();
		StringBuilder descString=new StringBuilder();
		String comment="";
		
		ArrayList<Object> descList= (ArrayList<Object>) descObject;
		ArrayList<Object> commentList=(ArrayList<Object>) commentObj;
		
		for(Object c:descList){
			Map<String,String> map=(Map<String, String>) c;
			commentString=commentString.append(map.get("date")+":"+map.get("message")+"\n");
		}
		for(Object c:commentList){
			Map<String,String> map=(Map<String, String>) c;
			descString=descString.append(map.get("date")+":"+map.get("message")+"\n");
		}
		
		comment=commentString.toString()+"\n  "+descString.toString();
		return comment;
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/addIssue", method = RequestMethod.POST)
	public String addIssue(@RequestBody String inspectionMedia){
		
		List<Object> reqObject=(List<Object>) JSONUtil.toObject(inspectionMedia, List.class);
		List<InspectionMedia> inspectionMediaList=new ArrayList<InspectionMedia>();
		for(Object object:reqObject){
			
			Map<String,Object> reqMap=(Map<String, Object>) object;
			String annotation=JSONUtil.toJson(reqMap.get("annotation"));
			String blobId=(String)reqMap.get("id");
			blobId=blobId.replace("images", "temp");
			String comment=JSONUtil.toJson(reqMap.get("comments"));
			String description=JSONUtil.toJson(reqMap.get("description"));
			//String comment=(String)reqMap.get("comments");
			//String description=(String)reqMap.get("description");
			String inspectorId=(String)reqMap.get("inspectorId");
			String defectType=String.valueOf(reqMap.get("defectType"));
			String statusType=String.valueOf(reqMap.get("statusType"));
			String inspectionId=(String)reqMap.get("inspectionId");
			String assetId=(String)reqMap.get("assetId");
			String userId=(String) reqMap.get("userId");
			String base64Img=(String)reqMap.get("dataURL");
			byte[] imgByte=null;
			try{
				 if(base64Img!=null){
					 imgByte = Base64.decodeBase64(base64Img);
					 imageUtil.storeImage(base64Img,blobId,mediaLocation); 
				 }
			}catch(Exception e){
				//e.printStackTrace();
			}finally{
				InspectionMedia media=new InspectionMedia(getValue(reqMap.get("comments"),reqMap.get("description")), blobId, inspectorId, new Date(), statusType, defectType,  annotation,description,assetId,inspectionId,imgByte,getAnnotatedComments(reqMap.get("annotation")),comment,userId);
				inspectionMediaList.add(media);
			}
			
		}
		issueDaoImpl.addIssue(inspectionMediaList);
		return "success";
	}
	private String getAnnotatedComments(Object annotatedMetadata){
		String annotatedComments=" ";
		if(annotatedMetadata!=null && String.valueOf(annotatedMetadata)!=null){
			List<Object> annoationList=(List<Object>) annotatedMetadata;
			
			int index=1;
			for(Object obj:annoationList ){
				Map<String,Object> objMap=(Map<String, Object>) obj;
				annotatedComments=annotatedComments.concat(index+". "+(String) objMap.get("text")+", ");
				index++;
			}
		}
		
		return annotatedComments.substring(0,annotatedComments.length()-1);
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/addUpdateIssue", method = RequestMethod.POST)
	public String addUpdateIssue(@RequestBody String inspectionMedia){
		List<Object> reqObject=(List<Object>) JSONUtil.toObject(inspectionMedia, List.class);
		List<InspectionMedia> inspectionMediaList=new ArrayList<InspectionMedia>();
		for(Object object:reqObject){
			Map<String,Object> reqMap= (Map<String, Object>) object;
			String blobId=(String)reqMap.get("id");
			String comment=JSONUtil.toJson(reqMap.get("comments"));
			List<Map> list=new ArrayList<Map>();
			Map<String,String> commentMap=new HashMap<String, String>();
			commentMap.put("date", String.valueOf(new Date()));
			commentMap.put("message", comment);
			list.add(commentMap);
			String inspectorId=(String)reqMap.get("inspectorId");
			String defectType=String.valueOf(reqMap.get("defectType"));
			String statusType=String.valueOf(reqMap.get("statusType"));
			String inspectionId=(String)reqMap.get("inspectionId");
			String assetId=(String)reqMap.get("assetId");
			String userId=(String)reqMap.get("userId");
			InspectionMedia media=new InspectionMedia(comment,blobId,inspectorId,new Date(),assetId,statusType,defectType,inspectionId,userId);
			inspectionMediaList.add(media);
			
			
		}
		issueDaoImpl.addUpdateIssue(inspectionMediaList);
	  return "success";	
	}
	
	@CrossOrigin
	@RequestMapping(value = "/inspection/updateIssue", method = RequestMethod.POST)
	public String updateIssue(@RequestBody String inspectionMedia){
		List<Object> reqObject=(List<Object>) JSONUtil.toObject(inspectionMedia, List.class);
		for(Object object:reqObject){
		
			Map<String,Object> reqMap= (Map<String, Object>) object;
			String blobId=(String)reqMap.get("id");
			String statusType=(String)reqMap.get("statusType");
			InspectionMedia media=new InspectionMedia(blobId,new Date(),statusType);
			issueDaoImpl.updateIssue(media);
		}
		return "success";
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/getIssueDate/inspectorId={inspectorId}&assetId={assetId}&userId={userId}", method = RequestMethod.GET)
	public String getIssueDate(@PathVariable String inspectorId,@PathVariable String assetId,@PathVariable String userId){
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		List<IssueInspection> issueInspectionList=issueDaoImpl.getIssueDate(inspectorId,assetId,userId);
		String issueDateJson="";
		
		if(issueInspectionList.size()>0){
			List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueInspectionList.get(0).getInspectionId(),assetId,userId);
			IssueModel[] issueModelArray=getIssueJson(assetList,issueInspectionList,inspectionMediaList,assetIndex);
			issueDateJson=JSONUtil.toJson(issueModelArray);
		}
		
		return issueDateJson;
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/getIssues/inspectorId={inspectorId}&inspectionId={inspectionId}&assetId={assetId}&userId={userId}", method = RequestMethod.GET)
	public String getIssue(@PathVariable String inspectorId,@PathVariable String inspectionId,@PathVariable String assetId,@PathVariable String userId){
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		List<IssueInspection> issueInspectionList=issueDaoImpl.getIssueDate(inspectorId,assetId,userId);
		String issueDateJson="";
		int inspectionIndex=0;
		
		for(IssueInspection issueInspection:issueInspectionList){
			if(issueInspection.getInspectionId().equalsIgnoreCase(inspectionId)){
				break;
			}
			inspectionIndex++;
		}
		
		if(issueInspectionList.size()>0){
			List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueInspectionList.get(inspectionIndex).getInspectionId(),assetId,userId);
			IssueModel[] issueModelArray=getIssueJson(assetList,issueInspectionList,inspectionMediaList,assetIndex);
			issueDateJson=JSONUtil.toJson(issueModelArray);
		}
		
		return issueDateJson;
	}
	@CrossOrigin
	@RequestMapping(value = "/inspection/getIssueMarker/inspectorId={inspectorId}&userId={userId}", method = RequestMethod.GET)
	public String getIssueMarker(@PathVariable String inspectorId,@PathVariable String userId){
		List<IssueMarkerModel> inspectionMediaList= issueDaoImpl.getIssueMarker(inspectorId,userId);
		String issueMarker=JSONUtil.toJson(inspectionMediaList);
		return issueMarker;
	}
	
	private IssueModel[] getIssueJson(List<String> assetList,List<IssueInspection> issueInspectionList,List<InspectionMedia> inspectionMediaList,int assetIndex){
		
		IssueModel[] issueInsectionArray=new IssueModel[assetList.size()];
		int index=0;
		for(String asset:assetList){
			
			for(IssueInspection issueInspection:issueInspectionList){
				Set<IssueDtlModel> set=new HashSet<IssueDtlModel>();
				for(InspectionMedia inspectionMedia:inspectionMediaList){
					String compFilePath="";
					/*
					File file=new File(mediaLocation+inspectionMedia.getBlobId());
					if(!ImageUtil.isCompressedFilePresent(compMediaLocation+file.getName())){
						compFilePath=ImageUtil.storeAndCompressedFile(mediaLocation+inspectionMedia.getBlobId(), compMediaLocation);
					}
					String base64EncodedImg =null;
					if(inspectionMedia.getIssueImage()!=null){
						 base64EncodedImg = DatatypeConverter.printBase64Binary(inspectionMedia.getIssueImage());
					}*/
					 
					 File file=new File(inspectionMedia.getBlobId());
					 String megaPath=inspectionMedia.getBlobId().replace("/Polymer/temp", "/Polymer/images");
					IssueDtlModel issueDtlModel=new IssueDtlModel(file.getName().split("\\.")[0],inspectionMedia.getDefectType(), "/Polymer/temp/"+file.getName(), megaPath, "/Polymer/images/marker2.png", inspectionMedia.getStatusType(), "tooltip","");
					set.add(issueDtlModel);
				}
				issueInspection.setIssueDtlModel(set);
			}
			
			IssueModel issueModel=null;
			if(index==assetIndex){
				issueModel=new IssueModel(String.valueOf(index),asset,"drone-one",issueInspectionList);
			}else{
				issueModel=new IssueModel(String.valueOf(index),asset,"drone-one",null);
			}
			issueInsectionArray[index]=issueModel;
			index++;
		}
		
		return issueInsectionArray;
	}
	
	
	
	
}
