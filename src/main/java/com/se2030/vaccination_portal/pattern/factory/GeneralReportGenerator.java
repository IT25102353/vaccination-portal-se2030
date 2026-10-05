package com.se2030.vaccination_portal.pattern.factory;

import com.se2030.vaccination_portal.model.DoseRecord;
import com.se2030.vaccination_portal.service.DoseRecordService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;

import java.time.LocalDate;
import java.util.List;

// Builds the summary for general reports
public class GeneralReportGenerator implements ReportGenerator {

    private final DoseRecordService doseRecordService;
    private final VaccinationCenterService vaccinationCenterService;

    public GeneralReportGenerator(DoseRecordService doseRecordService, VaccinationCenterService vaccinationCenterService) {
        this.doseRecordService = doseRecordService;
        this.vaccinationCenterService = vaccinationCenterService;
    }

    @Override
    public String generateSummary(LocalDate periodStart, LocalDate periodEnd) {
        List<DoseRecord> inPeriod = doseRecordService.getAllDoseRecords().stream()
                .filter(d -> d.getDateAdministered() != null
                        && !d.getDateAdministered().isBefore(periodStart)
                        && !d.getDateAdministered().isAfter(periodEnd))
                .toList();
        return "General: " + inPeriod.size() + " doses administered from " + periodStart + " to " + periodEnd
                + " across " + vaccinationCenterService.getAllCenters().size() + " registered centers.";
    }
}
