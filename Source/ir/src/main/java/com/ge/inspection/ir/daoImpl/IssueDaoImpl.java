package com.ge.inspection.ir.daoImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.extensions;
import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueInspection;
import com.ge.inspection.ir.model.IssueMarkerModel;
import com.ge.inspection.ir.repository.muta.IssueDtlRepository;
import com.ge.inspection.ir.util.JSONUtil;

@Component("issueDaoImpl")
public class IssueDaoImpl implements IssueDao{

	@Autowired
	private IssueDtlRepository issueDtlRepository;
	@Autowired
	private InspectionDaoImpl inspectionDaoImpl;
	
	@PersistenceContext
	private EntityManager mutaEntityManager;
	
	@Modifying
	@Transactional(value="mutaTransactionManager")
	public void addIssue(List<InspectionMedia> inspectionMediaList){
		
		for(InspectionMedia inspectionMedia:inspectionMediaList){
			if(issueDtlRepository.getCount(inspectionMedia.getUserId(),inspectionMedia.getBlobId())==0){
				issueDtlRepository.saveAndFlush(inspectionMedia);
			}else{
				issueDtlRepository.updateIssue(inspectionMedia.getBlobId(), inspectionMedia.getStatusType(), inspectionMedia.getDefectType(), inspectionMedia.getComment(), inspectionMedia.getDescription(), inspectionMedia.getAnnotedComments(), inspectionMedia.getAnnotatedMetadata(), inspectionMedia.getIssueImage(), inspectionMedia.getCommentJson(), inspectionMedia.getInspectionDate(), inspectionMedia.getUserId());
			}
		}
	
	}
    
    @Override
	public List<IssueInspection> getIssueDate(String inspectorId,String assetId,String userId) {
		List<InspectionMedia> issueDateList=issueDtlRepository.getIssueDate(inspectorId,assetId,userId);
		List<IssueInspection> issueInspectionList =getIssueInspection(issueDateList,inspectorId,assetId,userId);
		return issueInspectionList;
	}

    
    private List<IssueInspection> getIssueInspection(List<InspectionMedia> issueDateList,String inspectorId,String assetId,String userId){
    	Set<String> inspectionIds =new HashSet<String>();
    	
    	//List<InspectionModel> inspectionModelList=inspectionDaoImpl.getMediaDate(inspectorId,assetId);
    	
		/*
		if(issueDate.size()>0){
			String issueDateStr= df.format(issueDate.get(0));
			List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueDateStr,assetId);
			IssueModel[] issueModelArray=getIssueJson(assetList,issueDate,inspectionMediaList,assetIndex);
			issueDateJson=JSONUtil.toJson(issueModelArray);
		}*/
		for(InspectionMedia inspectionMedia:issueDateList){
			inspectionIds.add(inspectionMedia.getInspectionId());
		}
		
		List<IssueInspection> issueInspectionList=new ArrayList<IssueInspection>();
		for(String inspectionId:inspectionIds){
			if(inspectionId.equals(issueDateList.get(0).getInspectionId())){
				IssueInspection issueInspection=new IssueInspection("my-issues",issueDateList.get(0).getInspectionDate() , "", null, inspectionId);
				issueInspectionList.add(issueInspection);
			}
		}
		
		
		return issueInspectionList;
    }
    
	@Override
	public List<InspectionMedia>  getIssueDtls(String inspectorId,String inspectionId,String assetId,String userId) {
		List<InspectionMedia> inspectionDtlList=null;
		inspectionDtlList=issueDtlRepository.findIssue(inspectorId,assetId,inspectionId,userId);
		return inspectionDtlList;
	}

	@Override
	public List<IssueMarkerModel> getIssueMarker(String inspectorId,String userId) {
		List<IssueMarkerModel> issueMarkerModelList=null;
		List<InspectionMedia> inspectionDtlList=issueDtlRepository.findIssueMarker(inspectorId,userId);
		issueMarkerModelList=getIssueMarkerModel(inspectionDtlList);
		return issueMarkerModelList;
	}

	
	
	private List<IssueMarkerModel> getIssueMarkerModel(List<InspectionMedia> inspectionDtlList) {
		List<IssueMarkerModel> issueMarkerList=new ArrayList<IssueMarkerModel>();
		for(InspectionMedia inspectionMedia:inspectionDtlList){
			Object annotatedObject=JSONUtil.toObject(inspectionMedia.getAnnotatedMetadata(), Object.class);
			List<Object> commentList=new ArrayList<Object>();
			List<Object> descList=new ArrayList<Object>();
			if(inspectionMedia.getCommentJson()!=null && inspectionMedia.getCommentJson().trim().length()>0){
				commentList=(List<Object>) JSONUtil.toObject(inspectionMedia.getCommentJson(),List.class);
			}
			if(inspectionMedia.getDescription()!=null && inspectionMedia.getDescription().trim().length()>0){
				 descList=(List<Object>) JSONUtil.toObject(inspectionMedia.getDescription(),List.class);
			}
			
			File file=new File(inspectionMedia.getBlobId());
			String id=file.getName().split("\\.")[0];
			IssueMarkerModel issueModel=new IssueMarkerModel(id, "/images/marker.png", "issue-marker", null,annotatedObject, commentList, inspectionMedia.getDefectType(), inspectionMedia.getStatusType(),descList);
		
			issueMarkerList.add(issueModel);
		}
        return issueMarkerList;	
	}

	@Override
	public void updateIssue(InspectionMedia inspectionMedia) {
		issueDtlRepository.updateIssue(inspectionMedia.getBlobId(),inspectionMedia.getStatusType());
	}
 
	@Override
	public void addUpdateIssue(List<InspectionMedia> inspectionMediaList) {
		for(InspectionMedia inspectionMedia:inspectionMediaList){
			if(issueDtlRepository.getCount(inspectionMedia.getUserId(),inspectionMedia.getBlobId())!=0){
				if(inspectionMedia.getDefectType()!=null && inspectionMedia.getDefectType().trim().length()>0){
					issueDtlRepository.updateDefectType(inspectionMedia.getBlobId(), inspectionMedia.getStatusType(), inspectionMedia.getDefectType(),inspectionMedia.getUserId(), new Date());		
				}else{
					issueDtlRepository.updateComments(inspectionMedia.getBlobId(), inspectionMedia.getCommentJson(), inspectionMedia.getUserId(), new Date());		
				}
			}else{
				System.out.println("Not an issue not updating");
			}
		}
		
	}
	
	public List<Object[]> getIssueCount(String inspectorId,String userId){
		return issueDtlRepository.getIssueCount(inspectorId,userId);
	}


}
