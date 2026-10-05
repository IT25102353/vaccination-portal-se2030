package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.pattern.strategy.AlphanumericRule;
import com.se2030.vaccination_portal.pattern.strategy.StockDatesRule;
import com.se2030.vaccination_portal.pattern.strategy.ValidationStrategy;
import com.se2030.vaccination_portal.repository.VaccineStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineStockService {

    // Strategy pattern: each rule is a separate strategy object
    private final ValidationStrategy<String> vaccineNameRule = new AlphanumericRule("Vaccine name");
    private final ValidationStrategy<String> batchNumberRule = new AlphanumericRule("Batch number");
    private final ValidationStrategy<String> manufacturerRule = new AlphanumericRule("Manufacturer");
    private final ValidationStrategy<String> storageLocationRule = new AlphanumericRule("Storage location");
    private final ValidationStrategy<VaccineStock> datesRule = new StockDatesRule();

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
        validateStock(stock);
        return vaccineStockRepository.save(stock);
    }

    public VaccineStock updateStock(Long id, VaccineStock updatedStock) {
        VaccineStock existingStock = getStockById(id);
        validateStock(updatedStock);
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

    private void validateStock(VaccineStock stock) {
        if (stock.getVaccineName() == null || stock.getVaccineName().isBlank()) {
            throw new IllegalArgumentException("Vaccine name is required");
        }
        vaccineNameRule.validate(stock.getVaccineName());
        batchNumberRule.validate(stock.getBatchNumber());
        manufacturerRule.validate(stock.getManufacturer());
        storageLocationRule.validate(stock.getStorageLocation());
        if (stock.getQuantityAvailable() == null || stock.getQuantityAvailable() < 0) {
            throw new IllegalArgumentException("Quantity available must be zero or more");
        }
        datesRule.validate(stock);
    }
}
