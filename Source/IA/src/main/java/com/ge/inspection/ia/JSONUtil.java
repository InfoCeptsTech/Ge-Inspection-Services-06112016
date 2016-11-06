package com.ge.inspection.ia;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
	private static ObjectMapper objectMapper=new ObjectMapper();
	private static DateFormat df = new SimpleDateFormat("MMM dd yyyy");
	
	
	public static Object toObject(String jsonString,Class<?> className){
		Object obj=null;
		try {
			objectMapper.setDateFormat(df);
			 obj=objectMapper.readValue(jsonString, className);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static String toJson(Object obj){
		objectMapper.setDateFormat(df);
		String json=null;
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}
