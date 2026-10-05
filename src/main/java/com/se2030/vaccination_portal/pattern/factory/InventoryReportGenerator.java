package com.se2030.vaccination_portal.pattern.factory;

import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.service.VaccineStockService;

import java.time.LocalDate;
import java.util.List;

// Builds the summary for inventory reports
public class InventoryReportGenerator implements ReportGenerator {

    private final VaccineStockService vaccineStockService;

    public InventoryReportGenerator(VaccineStockService vaccineStockService) {
        this.vaccineStockService = vaccineStockService;
    }

    @Override
    public String generateSummary(LocalDate periodStart, LocalDate periodEnd) {
        List<VaccineStock> stocks = vaccineStockService.getAllStocks();
        int totalDoses = stocks.stream().mapToInt(s -> s.getQuantityAvailable() == null ? 0 : s.getQuantityAvailable()).sum();
        long expired = stocks.stream().filter(s -> "EXPIRED".equals(s.getStatus())).count();
        long expiringSoon = stocks.stream().filter(s -> "EXPIRING SOON".equals(s.getStatus())).count();
        return "Inventory: " + stocks.size() + " batches, " + totalDoses + " doses in stock. "
                + expired + " expired, " + expiringSoon + " expiring soon.";
    }
}
