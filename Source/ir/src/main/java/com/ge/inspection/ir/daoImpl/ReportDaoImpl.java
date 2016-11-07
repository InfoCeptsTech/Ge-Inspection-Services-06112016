package com.ge.inspection.ir.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.inspection.ir.dao.ReportDao;
import com.ge.inspection.ir.domain.muta.InspectionReport;
import com.ge.inspection.ir.repository.muta.ReportDtlRepository;

@Component("reportDao")
public class ReportDaoImpl implements ReportDao{

	@Autowired
	private ReportDtlRepository reportDtlRepository;
	@Override
	public void addReport(InspectionReport inspectionReport) {
		reportDtlRepository.saveAndFlush(inspectionReport);
	}

}
