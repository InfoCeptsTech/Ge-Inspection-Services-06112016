package com.ge.inspection.ir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.ReportDao;
import com.ge.inspection.ir.domain.muta.InspectionReport;
import com.ge.inspection.ir.util.JSONUtil;

@RestController
public class ReportController {

	@Autowired
	private ReportDao reportDao;
	@CrossOrigin
	@RequestMapping(value = "/inspection/addReport", method = RequestMethod.POST)
	public String addReport(@RequestBody String reportDtls){
		InspectionReport inspectionReport=(InspectionReport) JSONUtil.toObject(reportDtls, InspectionReport.class);
		reportDao.addReport(inspectionReport);
		return "success";
	}
}
