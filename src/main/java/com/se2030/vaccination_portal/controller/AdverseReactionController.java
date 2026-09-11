package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.AdverseReaction;
import com.se2030.vaccination_portal.service.AdverseReactionService;
import com.se2030.vaccination_portal.service.AppointmentService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import com.se2030.vaccination_portal.service.VaccineStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adverse-reactions")
public class AdverseReactionController {

    private final AdverseReactionService adverseReactionService;
    private final VaccineStockService vaccineStockService;
    private final VaccinationCenterService vaccinationCenterService;
    private final AppointmentService appointmentService;

    public AdverseReactionController(AdverseReactionService adverseReactionService,
                                      VaccineStockService vaccineStockService,
                                      VaccinationCenterService vaccinationCenterService,
                                      AppointmentService appointmentService) {
        this.adverseReactionService = adverseReactionService;
        this.vaccineStockService = vaccineStockService;
        this.vaccinationCenterService = vaccinationCenterService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String listReactions(Model model) {
        model.addAttribute("adverseReactions", adverseReactionService.getAllReactions());
        return "adversereaction/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("adverseReaction", new AdverseReaction());
        model.addAttribute("vaccineNames", vaccineStockService.getDistinctVaccineNames());
        model.addAttribute("centerNames", vaccinationCenterService.getDistinctCenterNames());
        model.addAttribute("patients", appointmentService.getDistinctPatients());
        return "adversereaction/create";
    }

    @PostMapping("/save")
    public String saveReaction(@ModelAttribute AdverseReaction adverseReaction, RedirectAttributes redirectAttributes) {
        try {
            adverseReactionService.createReaction(adverseReaction);
            redirectAttributes.addFlashAttribute("successMessage", "Adverse reaction reported successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adverse-reactions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("adverseReaction", adverseReactionService.getReactionById(id));
        model.addAttribute("vaccineNames", vaccineStockService.getDistinctVaccineNames());
        model.addAttribute("centerNames", vaccinationCenterService.getDistinctCenterNames());
        model.addAttribute("patients", appointmentService.getDistinctPatients());
        return "adversereaction/edit";
    }

    @PostMapping("/update/{id}")
    public String updateReaction(@PathVariable Long id, @ModelAttribute AdverseReaction adverseReaction,
                                  RedirectAttributes redirectAttributes) {
        try {
            adverseReactionService.updateReaction(id, adverseReaction);
            redirectAttributes.addFlashAttribute("successMessage", "Adverse reaction updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adverse-reactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteReaction(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adverseReactionService.deleteReaction(id);
            redirectAttributes.addFlashAttribute("successMessage", "Adverse reaction deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adverse-reactions";
    }
}
