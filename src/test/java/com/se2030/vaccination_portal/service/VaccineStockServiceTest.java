package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.VaccineStock;
import com.se2030.vaccination_portal.repository.VaccineStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class VaccineStockServiceTest {

    private VaccineStockRepository repository;
    private VaccineStockService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(VaccineStockRepository.class);
        service = new VaccineStockService(repository);
    }

    private VaccineStock validStock() {
        return new VaccineStock("Pfizer BioNTech", "AB123", "Pfizer Inc", 50,
                LocalDate.of(2026, 1, 1), LocalDate.of(2027, 1, 1), "Cold Room 1");
    }

    @Test
    void validStockIsSaved() {
        assertDoesNotThrow(() -> service.createStock(validStock()));
        verify(repository).save(any(VaccineStock.class));
    }

    @Test
    void symbolInVaccineNameIsRejected() {
        VaccineStock stock = validStock();
        stock.setVaccineName("Pfizer%");
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
        verify(repository, never()).save(any());
    }

    @Test
    void symbolInBatchNumberIsRejected() {
        VaccineStock stock = validStock();
        stock.setBatchNumber("AB-12&");
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
    }

    @Test
    void symbolInManufacturerIsRejected() {
        VaccineStock stock = validStock();
        stock.setManufacturer("Pfizer*");
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
    }

    @Test
    void symbolInStorageLocationIsRejected() {
        VaccineStock stock = validStock();
        stock.setStorageLocation("Room #1");
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
    }

    @Test
    void negativeQuantityIsRejected() {
        VaccineStock stock = validStock();
        stock.setQuantityAvailable(-1);
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
    }

    @Test
    void receivedDateAfterExpiryDateIsRejected() {
        VaccineStock stock = validStock();
        stock.setReceivedDate(LocalDate.of(2028, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
    }

    @Test
    void receivedDateEqualToExpiryDateIsRejected() {
        VaccineStock stock = validStock();
        stock.setReceivedDate(stock.getExpiryDate());
        assertThrows(IllegalArgumentException.class, () -> service.createStock(stock));
    }

    @Test
    void updateAlsoChecksSymbolsAndDates() {
        VaccineStock existing = validStock();
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of(existing));

        VaccineStock badName = validStock();
        badName.setVaccineName("Bad$Name");
        assertThrows(IllegalArgumentException.class, () -> service.updateStock(1L, badName));

        VaccineStock badDates = validStock();
        badDates.setReceivedDate(LocalDate.of(2030, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> service.updateStock(1L, badDates));
    }
}
