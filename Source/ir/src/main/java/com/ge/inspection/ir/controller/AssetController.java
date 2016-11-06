package com.ge.inspection.ir.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.AssestDao;
//Dummy service for static json

@RestController
public class AssetController {

	@Autowired
	private AssestDao assestDaoImple;
	
	@Autowired
	private ServletContext servletContext; 
	
	@Value("C:\\images\\")
	private String compMediaLocation;
	 
	@RequestMapping(value = "/inspection/getAssets/{userId}", method = RequestMethod.GET)
	public String getAssets(@PathVariable String userId){
		String assests=assestDaoImple.getAssets(userId);
		return assests;
	}
	@RequestMapping(value = "/inspection/getIssue/{userId}", method = RequestMethod.GET)
	public String getIssue(@PathVariable String userId){
		String assests=assestDaoImple.getIssue(userId);
		return assests;
	}
	@RequestMapping(value = "/inspection/getMarker/{userId}", method = RequestMethod.GET)
	public String getIssueMarker(@PathVariable String userId){
		String assests=assestDaoImple.getMarker(userId);
		return assests;
	}
	@ResponseBody
	@RequestMapping(value = "/inspection/media/mediaId={imageName}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotoBytes(@PathVariable String imageName) throws IOException {
	    String imagePath=compMediaLocation+imageName+".jpg";
		File imageFile=new File(imagePath);
	    BufferedImage  image = ImageIO.read(imageFile);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write( image, "jpg", baos );
	    baos.flush();
	    byte[] imageInByte = baos.toByteArray();
	    return imageInByte;
	}
	
	@RequestMapping(value = "/inspection/media/imagePath/imageIds={imageId}", method = RequestMethod.GET)
	public String getJson(@PathVariable String imageId)  {
		String [] imageIds=imageId.split(","); 
		String jsonPart="";
		int index=0;
		for(String id:imageIds){
			if(index==imageIds.length-1){
				jsonPart=jsonPart+"\"path-"+index+"\":"+"\"http://10.10.34.48:8080/ir-0.0.1/inspection/media/mediaId="+id+"\"";
			}else{
				jsonPart=jsonPart+"\"path-"+index+"\":"+"\"http://10.10.34.48:8080/ir-0.0.1/inspection/media/mediaId="+id+"\",";
			}
			index++;
		}
	
		String json="{\"path\":{"+jsonPart+ "},\"id\":\"1\"}";
        return json;
	}
}
