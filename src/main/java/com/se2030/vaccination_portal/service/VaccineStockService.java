package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.repository.VaccineStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineStockService {

    private final VaccineStockRepository vaccineStockRepository;

    public VaccineStockService(VaccineStockRepository vaccineStockRepository) {
        this.vaccineStockRepository = vaccineStockRepository;
    }

    public List<VaccineStock> getAllStocks() {
        return vaccineStockRepository.findAll();
    }

    public List<String> getDistinctVaccineNames() {
        return vaccineStockRepository.findAll().stream()
                .map(VaccineStock::getVaccineName)
                .distinct()
                .sorted()
                .toList();
    }

    public VaccineStock getStockById(Long id) {
        return vaccineStockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaccine stock not found with id: " + id));
    }

    public VaccineStock createStock(VaccineStock stock) {
        if (stock.getVaccineName() == null || stock.getVaccineName().isBlank()) {
            throw new IllegalArgumentException("Vaccine name is required");
        }
        if (stock.getQuantityAvailable() == null || stock.getQuantityAvailable() < 0) {
            throw new IllegalArgumentException("Quantity available must be zero or more");
        }
        return vaccineStockRepository.save(stock);
    }

    public VaccineStock updateStock(Long id, VaccineStock updatedStock) {
        VaccineStock existingStock = getStockById(id);
        if (updatedStock.getVaccineName() == null || updatedStock.getVaccineName().isBlank()) {
            throw new IllegalArgumentException("Vaccine name is required");
        }
        if (updatedStock.getQuantityAvailable() == null || updatedStock.getQuantityAvailable() < 0) {
            throw new IllegalArgumentException("Quantity available must be zero or more");
        }
        existingStock.setVaccineName(updatedStock.getVaccineName());
        existingStock.setBatchNumber(updatedStock.getBatchNumber());
        existingStock.setManufacturer(updatedStock.getManufacturer());
        existingStock.setQuantityAvailable(updatedStock.getQuantityAvailable());
        existingStock.setReceivedDate(updatedStock.getReceivedDate());
        existingStock.setExpiryDate(updatedStock.getExpiryDate());
        existingStock.setStorageLocation(updatedStock.getStorageLocation());
        return vaccineStockRepository.save(existingStock);
    }

    public void deleteStock(Long id) {
        VaccineStock stock = getStockById(id);
        vaccineStockRepository.delete(stock);
    }
}
