package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.Appointment;
import com.se2030.vaccination_portal.service.AppointmentService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import com.se2030.vaccination_portal.service.VaccineStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final VaccineStockService vaccineStockService;
    private final VaccinationCenterService vaccinationCenterService;

    public AppointmentController(AppointmentService appointmentService, VaccineStockService vaccineStockService,
                                  VaccinationCenterService vaccinationCenterService) {
        this.appointmentService = appointmentService;
        this.vaccineStockService = vaccineStockService;
        this.vaccinationCenterService = vaccinationCenterService;
    }

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointment/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("vaccineNames", vaccineStockService.getDistinctVaccineNames());
        model.addAttribute("centerNames", vaccinationCenterService.getDistinctCenterNames());
        return "appointment/create";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.createAppointment(appointment);
            redirectAttributes.addFlashAttribute("successMessage", "Appointment scheduled successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/appointments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.getAppointmentById(id));
        model.addAttribute("vaccineNames", vaccineStockService.getDistinctVaccineNames());
        model.addAttribute("centerNames", vaccinationCenterService.getDistinctCenterNames());
        return "appointment/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id, @ModelAttribute Appointment appointment,
                                     RedirectAttributes redirectAttributes) {
        try {
            appointmentService.updateAppointment(id, appointment);
            redirectAttributes.addFlashAttribute("successMessage", "Appointment updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.deleteAppointment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Appointment deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/appointments";
    }
}
