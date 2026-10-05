package com.se2030.vaccination_portal.pattern.factory;

import com.se2030.vaccination_portal.service.AdverseReactionService;
import com.se2030.vaccination_portal.service.AppointmentService;
import com.se2030.vaccination_portal.service.DoseRecordService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import com.se2030.vaccination_portal.service.VaccineStockService;
import org.springframework.stereotype.Component;

// Factory Method pattern: creates the right generator for the report type
@Component
public class ReportGeneratorFactory {

    private final VaccineStockService vaccineStockService;
    private final AppointmentService appointmentService;
    private final AdverseReactionService adverseReactionService;
    private final DoseRecordService doseRecordService;
    private final VaccinationCenterService vaccinationCenterService;

    public ReportGeneratorFactory(VaccineStockService vaccineStockService, AppointmentService appointmentService,
                                  AdverseReactionService adverseReactionService, DoseRecordService doseRecordService,
                                  VaccinationCenterService vaccinationCenterService) {
        this.vaccineStockService = vaccineStockService;
        this.appointmentService = appointmentService;
        this.adverseReactionService = adverseReactionService;
        this.doseRecordService = doseRecordService;
        this.vaccinationCenterService = vaccinationCenterService;
    }

    public ReportGenerator createGenerator(String reportType) {
        if (reportType == null) {
            throw new IllegalArgumentException("Report type is required");
        }
        return switch (reportType) {
            case "INVENTORY" -> new InventoryReportGenerator(vaccineStockService);
            case "APPOINTMENTS" -> new AppointmentReportGenerator(appointmentService);
            case "ADVERSE_REACTIONS" -> new AdverseReactionReportGenerator(adverseReactionService);
            case "GENERAL" -> new GeneralReportGenerator(doseRecordService, vaccinationCenterService);
            default -> throw new IllegalArgumentException("Unknown report type: " + reportType);
        };
    }
}
