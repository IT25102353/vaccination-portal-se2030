package com.se2030.vaccination_portal.pattern;

import com.se2030.vaccination_portal.model.DoseRecord;
import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.pattern.observer.Observer;
import com.se2030.vaccination_portal.pattern.observer.StockDeductionObserver;
import com.se2030.vaccination_portal.repository.DoseRecordRepository;
import com.se2030.vaccination_portal.repository.VaccineStockRepository;
import com.se2030.vaccination_portal.service.DoseRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverPatternTest {

    private DoseRecord dose(String vaccine) {
        DoseRecord d = new DoseRecord();
        d.setPatientName("Nimal");
        d.setVaccineName(vaccine);
        d.setDateAdministered(LocalDate.now());
        return d;
    }

    @Test
    void serviceNotifiesEveryObserverWhenDoseIsRecorded() {
        DoseRecordRepository repo = Mockito.mock(DoseRecordRepository.class);
        Mockito.when(repo.save(Mockito.any(DoseRecord.class))).thenAnswer(i -> i.getArgument(0));

        List<DoseRecord> seenByFirst = new ArrayList<>();
        List<DoseRecord> seenBySecond = new ArrayList<>();
        Observer<DoseRecord> first = seenByFirst::add;
        Observer<DoseRecord> second = seenBySecond::add;

        new DoseRecordService(repo, List.of(first, second)).createDoseRecord(dose("Pfizer"));

        assertEquals(1, seenByFirst.size());
        assertEquals(1, seenBySecond.size());
    }

    @Test
    void stockObserverTakesOneDoseFromEarliestExpiringBatch() {
        VaccineStock later = new VaccineStock("Pfizer", "B2", "P", 10, LocalDate.now().minusDays(5), LocalDate.now().plusDays(300), "R1");
        VaccineStock sooner = new VaccineStock("Pfizer", "B1", "P", 10, LocalDate.now().minusDays(5), LocalDate.now().plusDays(100), "R1");
        VaccineStock other = new VaccineStock("Moderna", "B3", "M", 10, LocalDate.now().minusDays(5), LocalDate.now().plusDays(50), "R1");
        VaccineStockRepository repo = Mockito.mock(VaccineStockRepository.class);
        Mockito.when(repo.findAll()).thenReturn(List.of(later, sooner, other));

        new StockDeductionObserver(repo).update(dose("pfizer"));

        assertEquals(9, sooner.getQuantityAvailable());
        assertEquals(10, later.getQuantityAvailable());
        assertEquals(10, other.getQuantityAvailable());
    }
}
