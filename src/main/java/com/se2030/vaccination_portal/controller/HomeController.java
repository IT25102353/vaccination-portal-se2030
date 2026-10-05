package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.service.AdverseReactionService;
import com.se2030.vaccination_portal.service.AppointmentService;
import com.se2030.vaccination_portal.service.DoseRecordService;
import com.se2030.vaccination_portal.service.ReportService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import com.se2030.vaccination_portal.service.VaccineStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final VaccineStockService vaccineStockService;
    private final AppointmentService appointmentService;
    private final DoseRecordService doseRecordService;
    private final VaccinationCenterService vaccinationCenterService;
    private final AdverseReactionService adverseReactionService;
    private final ReportService reportService;

    public HomeController(VaccineStockService vaccineStockService, AppointmentService appointmentService,
                          DoseRecordService doseRecordService, VaccinationCenterService vaccinationCenterService,
                          AdverseReactionService adverseReactionService, ReportService reportService) {
        this.vaccineStockService = vaccineStockService;
        this.appointmentService = appointmentService;
        this.doseRecordService = doseRecordService;
        this.vaccinationCenterService = vaccinationCenterService;
        this.adverseReactionService = adverseReactionService;
        this.reportService = reportService;
    }

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<VaccineStock> stocks = vaccineStockService.getAllStocks();

        int totalUnits = stocks.stream().mapToInt(s -> s.getQuantityAvailable() == null ? 0 : s.getQuantityAvailable()).sum();
        List<VaccineStock> needAttention = stocks.stream()
                .filter(s -> !"VALID".equals(s.getStatus()))
                .toList();

        model.addAttribute("totalUnits", totalUnits);
        model.addAttribute("batchCount", stocks.size());
        model.addAttribute("needAttention", needAttention);
        model.addAttribute("appointmentCount", appointmentService.getAllAppointments().size());
        model.addAttribute("doseCount", doseRecordService.getAllDoseRecords().size());
        model.addAttribute("centerCount", vaccinationCenterService.getAllCenters().size());
        model.addAttribute("reactionCount", adverseReactionService.getAllReactions().size());
        model.addAttribute("reportCount", reportService.getAllReports().size());
        return "dashboard";
    }
}
