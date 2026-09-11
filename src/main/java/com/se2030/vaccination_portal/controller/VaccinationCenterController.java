package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.VaccinationCenter;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/centers")
public class VaccinationCenterController {

    private final VaccinationCenterService vaccinationCenterService;

    public VaccinationCenterController(VaccinationCenterService vaccinationCenterService) {
        this.vaccinationCenterService = vaccinationCenterService;
    }

    @GetMapping
    public String listCenters(Model model) {
        model.addAttribute("centers", vaccinationCenterService.getAllCenters());
        return "center/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("center", new VaccinationCenter());
        return "center/create";
    }

    @PostMapping("/save")
    public String saveCenter(@ModelAttribute VaccinationCenter center, RedirectAttributes redirectAttributes) {
        try {
            vaccinationCenterService.createCenter(center);
            redirectAttributes.addFlashAttribute("successMessage", "Vaccination center added successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/centers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("center", vaccinationCenterService.getCenterById(id));
        return "center/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCenter(@PathVariable Long id, @ModelAttribute VaccinationCenter center,
                                RedirectAttributes redirectAttributes) {
        try {
            vaccinationCenterService.updateCenter(id, center);
            redirectAttributes.addFlashAttribute("successMessage", "Vaccination center updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/centers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCenter(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vaccinationCenterService.deleteCenter(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vaccination center deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/centers";
    }
}
