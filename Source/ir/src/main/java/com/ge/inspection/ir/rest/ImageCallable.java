package com.ge.inspection.ir.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.ge.inspection.ir.util.ImageUtil;
import com.ge.inspection.ir.util.JSONUtil;

public class ImageCallable implements Callable<Map<String,String>>{

    private String authenticateUrl;
    private String imageUrl;
    private String username;
    private String password;
    private String imgPath;
    	
    private String mediaLocation;

		public ImageCallable(String imgPath, String authenticateUrl,
				String imageUrl, String username, String password,String mediaLocation) {
			this.imgPath=imgPath;
			this.authenticateUrl = authenticateUrl;
			this.imageUrl = imageUrl;
			this.username = username;
			this.password = password;
			this.mediaLocation=mediaLocation;
		}


		@Override
		public Map<String,String> call() throws Exception {
			
			 RestTemplate restTemplate = new RestTemplate();
		     
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_JSON_UTF8);  
			 
			 String requestJson="{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
			// System.out.println("authenticateUrl :: "+authenticateUrl);
			 HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			 String response = restTemplate.postForObject(authenticateUrl, entity,String.class); 
		     Map<String,String> respMap=(Map<String, String>) JSONUtil.toObject(response, Map.class);
		     String token=respMap.get("token");
		     //System.out.println(" token : "+token);
		     HttpHeaders imageHeaders = new HttpHeaders();
		   
			 imageHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8); 
			 imageHeaders.add("Authorization", "Bearer "+token);
			 imageHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    
		     File imgFile=new File(imgPath);
			 HttpEntity<String> imageEntity = new HttpEntity<String>("parameters", imageHeaders);
			 restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			 
		//	 ResponseEntity<byte[]> responseEntity=restTemplate.exchange("http://localhost:8080/ir-0.0.1/inspection/media/mediaId="+imgFile.getName().split("\\.")[0], HttpMethod.GET, imageEntity, byte[].class);
		//	 System.out.println("url :: "+imageUrl.concat(imgPath)+" , with token : "+token);
			 ResponseEntity<byte[]> responseEntity=restTemplate.exchange(imageUrl.concat(imgPath), HttpMethod.GET, imageEntity, byte[].class);
		//	 System.out.println("responseEntity::: "+responseEntity);
			 Map<String,String> imgMap=new HashMap<String, String>();
			 if(responseEntity!=null){
				 
				 byte[] imgByteArray=responseEntity.getBody();
				 //System.out.println("imgByteArray::: "+imgByteArray);
				// String base64EncodedImg = DatatypeConverter.printBase64Binary(imgByteArray);
				// imgMap.put(imgFile.getName().split("\\.")[0], base64EncodedImg);
				
				 
				 FileOutputStream stream = new FileOutputStream(mediaLocation+"/Polymer/images/"+imgFile.getName());
				 try {
				     stream.write(imgByteArray);
				     InputStream in = new ByteArrayInputStream(imgByteArray);
				     BufferedImage bImageFromConvert = ImageIO.read(in);
				     ImageUtil.compress(mediaLocation+"/Polymer/images/"+imgFile.getName(),bImageFromConvert,mediaLocation+"/Polymer/compressed/");
				  //   System.out.println("after file write , location : "+mediaLocation+"/Polymer/images/"+imgFile.getName());
				     bImageFromConvert.flush();
				     in.close();
				     bImageFromConvert=null;
				     in=null;
				 } catch(Exception e){
					e.printStackTrace();
				 } finally {
				     stream.close();
				 }
				
			 }
			 return imgMap;
		}
    	
    }
