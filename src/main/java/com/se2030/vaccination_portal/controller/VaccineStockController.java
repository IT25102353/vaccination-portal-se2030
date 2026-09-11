package com.se2030.vaccination_portal.controller;

import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.service.VaccineStockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventory")
public class VaccineStockController {

    private final VaccineStockService vaccineStockService;

    public VaccineStockController(VaccineStockService vaccineStockService) {
        this.vaccineStockService = vaccineStockService;
    }

    @GetMapping
    public String listStocks(Model model) {
        model.addAttribute("stocks", vaccineStockService.getAllStocks());
        return "inventory/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("stock", new VaccineStock());
        return "inventory/create";
    }

    @PostMapping("/save")
    public String saveStock(@ModelAttribute VaccineStock stock, RedirectAttributes redirectAttributes) {
        try {
            vaccineStockService.createStock(stock);
            redirectAttributes.addFlashAttribute("successMessage", "Vaccine stock added successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/inventory";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("stock", vaccineStockService.getStockById(id));
        return "inventory/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStock(@PathVariable Long id, @ModelAttribute VaccineStock stock,
                               RedirectAttributes redirectAttributes) {
        try {
            vaccineStockService.updateStock(id, stock);
            redirectAttributes.addFlashAttribute("successMessage", "Vaccine stock updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteStock(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vaccineStockService.deleteStock(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vaccine stock deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/inventory";
    }
}
