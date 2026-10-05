package com.se2030.vaccination_portal.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VaccineStockStatusTest {

    private VaccineStock stockExpiringOn(LocalDate expiry) {
        return new VaccineStock("Pfizer", "B1", "Pfizer", 10, LocalDate.now().minusYears(1), expiry, "Fridge1");
    }

    @Test
    void pastExpiryDateIsExpired() {
        assertEquals("EXPIRED", stockExpiringOn(LocalDate.now().minusDays(1)).getStatus());
    }

    @Test
    void expiryWithin30DaysIsExpiringSoon() {
        assertEquals("EXPIRING SOON", stockExpiringOn(LocalDate.now().plusDays(10)).getStatus());
    }

    @Test
    void farFutureExpiryIsValid() {
        assertEquals("VALID", stockExpiringOn(LocalDate.now().plusDays(200)).getStatus());
    }

    @Test
    void missingExpiryIsUnknown() {
        assertEquals("UNKNOWN", stockExpiringOn(null).getStatus());
    }
}
