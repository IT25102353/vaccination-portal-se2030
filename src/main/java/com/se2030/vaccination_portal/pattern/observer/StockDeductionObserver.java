package com.se2030.vaccination_portal.pattern.observer;

import com.se2030.vaccination_portal.model.DoseRecord;
import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.repository.VaccineStockRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

// When a dose is recorded, reduce the matching stock by one
@Component
public class StockDeductionObserver implements Observer<DoseRecord> {

    private final VaccineStockRepository vaccineStockRepository;

    public StockDeductionObserver(VaccineStockRepository vaccineStockRepository) {
        this.vaccineStockRepository = vaccineStockRepository;
    }

    @Override
    public void update(DoseRecord doseRecord) {
        vaccineStockRepository.findAll().stream()
                .filter(stock -> stock.getVaccineName() != null
                        && stock.getVaccineName().equalsIgnoreCase(doseRecord.getVaccineName()))
                .filter(stock -> stock.getQuantityAvailable() != null && stock.getQuantityAvailable() > 0)
                .filter(stock -> !"EXPIRED".equals(stock.getStatus()))
                .min(Comparator.comparing(VaccineStock::getExpiryDate,
                        Comparator.nullsLast(Comparator.<LocalDate>naturalOrder())))
                .ifPresent(stock -> {
                    stock.setQuantityAvailable(stock.getQuantityAvailable() - 1);
                    vaccineStockRepository.save(stock);
                });
    }
}
