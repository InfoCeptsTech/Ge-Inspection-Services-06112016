package com.ge.inspection.ir.daoImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.inspection.ir.dao.InspectionDao;
import com.ge.inspection.ir.domain.immuta.InspectionDtls;
import com.ge.inspection.ir.model.DurationModel;
import com.ge.inspection.ir.model.ImageModel;
import com.ge.inspection.ir.model.InspectionModel;
import com.ge.inspection.ir.model.MediaModel;
import com.ge.inspection.ir.model.Metadata;
import com.ge.inspection.ir.repository.immuta.InspectionDtlRepository;
import com.ge.inspection.ir.rest.ImageCallable;
import com.ge.inspection.ir.util.ImageUtil;

@Component("inspectionDao")
public class InspectionDaoImpl implements InspectionDao {

    @Autowired
    private InspectionDtlRepository inspectionDtlRepository;
    
    @Value("${media.location}")
   	private String mediaLocation;
    
    @Value("${immuta.authenticate.url}")
    private String authenticateUrl;
    @Value("${immuta.image.url}")
    private String imageUrl;
    @Value("${immuta.authenticate.username}")
    private String username;
    @Value("${immuta.authenticate.password}")
    private String password;
    
    

	@Override
	public List<String> getAsset(String inspectorId) {
		List<String> assetList=inspectionDtlRepository.getAsset(inspectorId);
		return assetList;
	}

	@Override
	public List<InspectionModel> getMediaDate(String inspectorId, String assetId,String inspectionPhaseId) {
		List<InspectionDtls> dateList=inspectionDtlRepository.getMediaDate(inspectorId, assetId,inspectionPhaseId);
		List<InspectionModel> inspectionModelList=getMediaDatebyInspection(dateList);
		return inspectionModelList;
	}
	
	@Override
	public List<InspectionModel> getMediaDate(String inspectorId, String assetId) {
		List<InspectionDtls> dateList=inspectionDtlRepository.getMediaDate(inspectorId, assetId);
		List<InspectionModel> inspectionModelList=getMediaDatebyInspection(dateList);
		return inspectionModelList;
	}
	
	
	private List<InspectionModel> getMediaDatebyInspection(List<InspectionDtls> inspectionList){
		Set<String> inspections=new HashSet<String>();
		
		for(InspectionDtls inspectionDtls:inspectionList){
			inspections.add(inspectionDtls.getInspectionId());
		}
		List<InspectionModel> inspectionModelList=new ArrayList<InspectionModel>();
		
		for(String inspectionId:inspections){
			List<DurationModel> startDuration=new ArrayList<DurationModel>();
			List<DurationModel> endDuration=new ArrayList<DurationModel>();
			InspectionModel inspectionModel=null;
			
			String duration="";
			for(InspectionDtls inspectionDtls:inspectionList){
				if(inspectionDtls.getInspectionId().equals(inspectionId)){
					startDuration.add(new DurationModel(inspectionDtls.getInspectionStart()));
					endDuration.add(new DurationModel(inspectionDtls.getInspectionStop()));
				}
			}
			if(startDuration.get(0)!=null && endDuration.get(endDuration.size()-1)!=null){
				String start=String.valueOf(startDuration.get(0).getDateTime()).split(" ")[1];
			    String end=String.valueOf(endDuration.get(endDuration.size()-1).getDateTime()).split(" ")[1];
			    
			    String[] startTime=start.split(":");
			    String[] endTime=end.split(":");
			    
				duration=String.valueOf(startTime[0]+":"+startTime[1]+"-"+endTime[0]+":"+endTime[1]);	
			}
			 inspectionModel=new InspectionModel(inspectionList.get(0).getInspectionStart(),duration,inspectionId,null,"my-media");
			inspectionModelList.add(inspectionModel);
			
		}
		return inspectionModelList;
	}
	

	@Override
	public Set<MediaModel> getMedia(String inspectorId, String assetId,
			String inspectionId,String inspectionPhaseId) {
		Set<MediaModel> mediaModel=null;
		List<InspectionDtls> inspectionDtlsList=null;
			//Date inspectionDate = formatter.parse(inspectionStart);
			inspectionDtlsList=inspectionDtlRepository.getMedia(inspectorId, assetId, inspectionId);
	 mediaModel=getMedia(inspectionDtlsList,inspectionPhaseId);
			
		return mediaModel;
	}


	
	private Set<MediaModel> getMedia(List<InspectionDtls> inspectionDtlsList,String inspectionPhaseId){
		Set<MediaModel> phaseSet=new HashSet<MediaModel>();
		ExecutorService executor = Executors.newFixedThreadPool(10);
    	List<Future<Map<String,String>>> list = new ArrayList<Future<Map<String,String>>>();
    	//Map<String,String> allImgMap=new HashMap<String, String>();
		
		for(InspectionDtls inspectionDtls:inspectionDtlsList){
			phaseSet.add(new MediaModel(inspectionDtls.getInspectionPhaseId()));
		}
		
		for(MediaModel phase:phaseSet){
					List<ImageModel> imageModelList=new ArrayList<ImageModel>();
					//int index=0;
					for(InspectionDtls inspectionDtls:inspectionDtlsList){
						if(inspectionDtls.getInspectionPhaseId().equalsIgnoreCase(phase.getTitle()) && (inspectionDtls.getInspectionPhaseId().equals(inspectionPhaseId))){
							
							//String compPath=ImageUtil.storeAndCompressedFile(mediaLocation+inspectionDtls.getBlobId(), compMediaLocation);
							File file=new File(inspectionDtls.getBlobId());
							String id=file.getName();
							//System.out.println("file name : "+id);
							List<Metadata> metadataList=new ArrayList<Metadata>();
							
							Metadata metadata=new Metadata("Altitude",String.valueOf(inspectionDtls.getLocation_globalPosition_altitude()));
							metadataList.add(metadata);
							
							if(!ImageUtil.isFilePresent(mediaLocation+"/Polymer/images/"+file.getName()) ||
									!ImageUtil.isFilePresent(mediaLocation+"/Polymer/compressed/"+file.getName())){
							
								String imgPath=inspectionDtls.getBlobId();
								String decodedPassword = new String(DatatypeConverter.parseBase64Binary(password));
					    		ImageCallable callable =new ImageCallable(imgPath,authenticateUrl,imageUrl,username,decodedPassword,mediaLocation);
					    		Future<Map<String,String>> future = executor.submit(callable);
					    		list.add(future);
							}
				    		 
							//byte[] imgByte=ImageUtil.getImageBinary(mediaLocation+inspectionDtls.getBlobId());
							
							imageModelList.add(new ImageModel(id.split("\\.")[0],"/Polymer/compressed/"+id, "/Polymer/images/"+id,inspectionDtls.getInspectionStop(),inspectionDtls.getInspectionStart(),metadataList,""));
							//index++;
						}
					}
					phase.setImageModel(imageModelList);
		}
		for(Future<Map<String,String>> future : list){
   		 try {
   			 
   			future.get();
   			/*
   			 Map<String,String> imgMap=future.get();
   			 allImgMap.putAll(imgMap);
   			 */
			} catch (Exception e) {
				e.printStackTrace();
			}
   		 
   	 }
	//	System.out.println("---------------before executor shutdown------------------");
   	 executor.shutdown();
   
		return phaseSet;
	}
  
	@Override
	public List<String> getPhase(String inspectorId, String assetId) {
		 List<String> phaseList=inspectionDtlRepository.getPhase(inspectorId, assetId);
		 return phaseList;
	}

	

}

