package com.ge.inspection.ir.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	
	public static String loadFile(String filePath){
		String content="";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
		{
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				content+=sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
