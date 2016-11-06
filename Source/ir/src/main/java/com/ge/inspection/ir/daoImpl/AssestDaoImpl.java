package com.ge.inspection.ir.daoImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.inspection.ir.dao.AssestDao;
import com.ge.inspection.ir.util.FileUtil;

@Component
public class AssestDaoImpl implements AssestDao{
    private final Logger logger = LoggerFactory.getLogger(AssestDaoImpl.class);
	@Value("${assest.config.path}")
	private String assestFilePath;
	
	@Value("${assest.config.path}")
	private String issuePath;
	
	@Value("${assest.marker.path}")
	private String markerPath;
	
	@Override
	public String getAssets(String userId) {
		String assestJson=FileUtil.loadFile(assestFilePath);
		return assestJson;
	}

	public String getIssue(String userId) {
		String issueJson=FileUtil.loadFile(issuePath);
		return issueJson;
	}

	public String getMarker(String userId) {
		String markerJson=FileUtil.loadFile(markerPath);
		return markerJson;
	}

}
