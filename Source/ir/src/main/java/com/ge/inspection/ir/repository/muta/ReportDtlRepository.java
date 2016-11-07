package com.ge.inspection.ir.repository.muta;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ge.inspection.ir.domain.muta.InspectionReport;

public interface ReportDtlRepository extends JpaRepository<InspectionReport, String>{

}
