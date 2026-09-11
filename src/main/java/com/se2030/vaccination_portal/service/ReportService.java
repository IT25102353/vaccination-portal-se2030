package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.Report;
import com.se2030.vaccination_portal.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + id));
    }

    public Report createReport(Report report) {
        if (report.getReportTitle() == null || report.getReportTitle().isBlank()) {
            throw new IllegalArgumentException("Report title is required");
        }
        if (report.getGeneratedDate() == null) {
            throw new IllegalArgumentException("Generated date is required");
        }
        return reportRepository.save(report);
    }

    public Report updateReport(Long id, Report updatedReport) {
        Report existingReport = getReportById(id);
        if (updatedReport.getReportTitle() == null || updatedReport.getReportTitle().isBlank()) {
            throw new IllegalArgumentException("Report title is required");
        }
        if (updatedReport.getGeneratedDate() == null) {
            throw new IllegalArgumentException("Generated date is required");
        }
        existingReport.setReportTitle(updatedReport.getReportTitle());
        existingReport.setReportType(updatedReport.getReportType());
        existingReport.setPeriodStart(updatedReport.getPeriodStart());
        existingReport.setPeriodEnd(updatedReport.getPeriodEnd());
        existingReport.setGeneratedBy(updatedReport.getGeneratedBy());
        existingReport.setGeneratedDate(updatedReport.getGeneratedDate());
        existingReport.setSummary(updatedReport.getSummary());
        return reportRepository.save(existingReport);
    }

    public void deleteReport(Long id) {
        Report report = getReportById(id);
        reportRepository.delete(report);
    }
}
