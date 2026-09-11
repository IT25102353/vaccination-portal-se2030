package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.Report;
import com.se2030.vaccination_portal.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String listReports(Model model) {
        model.addAttribute("reports", reportService.getAllReports());
        return "report/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("report", new Report());
        return "report/create";
    }

    @PostMapping("/save")
    public String saveReport(@ModelAttribute Report report, RedirectAttributes redirectAttributes) {
        try {
            reportService.createReport(report);
            redirectAttributes.addFlashAttribute("successMessage", "Report added successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("report", reportService.getReportById(id));
        return "report/edit";
    }

    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable Long id, @ModelAttribute Report report,
                                RedirectAttributes redirectAttributes) {
        try {
            reportService.updateReport(id, report);
            redirectAttributes.addFlashAttribute("successMessage", "Report updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reports";
    }

    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reportService.deleteReport(id);
            redirectAttributes.addFlashAttribute("successMessage", "Report deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reports";
    }
}
