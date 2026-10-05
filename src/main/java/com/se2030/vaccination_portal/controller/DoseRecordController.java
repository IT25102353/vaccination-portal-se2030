package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.DoseRecord;
import com.se2030.vaccination_portal.service.AppointmentService;
import com.se2030.vaccination_portal.service.DoseRecordService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import com.se2030.vaccination_portal.service.VaccineStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dose-records")
public class DoseRecordController {

    private final DoseRecordService doseRecordService;
    private final VaccineStockService vaccineStockService;
    private final VaccinationCenterService vaccinationCenterService;
    private final AppointmentService appointmentService;

    public DoseRecordController(DoseRecordService doseRecordService, VaccineStockService vaccineStockService,
                                 VaccinationCenterService vaccinationCenterService, AppointmentService appointmentService) {
        this.doseRecordService = doseRecordService;
        this.vaccineStockService = vaccineStockService;
        this.vaccinationCenterService = vaccinationCenterService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String listDoseRecords(Model model) {
        model.addAttribute("doseRecords", doseRecordService.getAllDoseRecords());
        return "doserecord/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doseRecord", new DoseRecord());
        model.addAttribute("vaccineNames", vaccineStockService.getDistinctVaccineNames());
        model.addAttribute("centerNames", vaccinationCenterService.getDistinctCenterNames());
        model.addAttribute("patients", appointmentService.getDistinctPatients());
        return "doserecord/create";
    }

    @PostMapping("/save")
    public String saveDoseRecord(@ModelAttribute DoseRecord doseRecord, RedirectAttributes redirectAttributes) {
        try {
            doseRecordService.createDoseRecord(doseRecord);
            redirectAttributes.addFlashAttribute("successMessage", "Dose record added successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/dose-records";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("doseRecord", doseRecordService.getDoseRecordById(id));
        model.addAttribute("vaccineNames", vaccineStockService.getDistinctVaccineNames());
        model.addAttribute("centerNames", vaccinationCenterService.getDistinctCenterNames());
        model.addAttribute("patients", appointmentService.getDistinctPatients());
        return "doserecord/edit";
    }

    @PostMapping("/update/{id}")
    public String updateDoseRecord(@PathVariable Long id, @ModelAttribute DoseRecord doseRecord,
                                    RedirectAttributes redirectAttributes) {
        try {
            doseRecordService.updateDoseRecord(id, doseRecord);
            redirectAttributes.addFlashAttribute("successMessage", "Dose record updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/dose-records";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoseRecord(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            doseRecordService.deleteDoseRecord(id);
            redirectAttributes.addFlashAttribute("successMessage", "Dose record deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/dose-records";
    }
}
