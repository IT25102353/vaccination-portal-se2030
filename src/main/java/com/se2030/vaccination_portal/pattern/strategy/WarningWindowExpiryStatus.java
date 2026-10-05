package com.se2030.vaccination_portal.pattern.strategy;

import java.time.LocalDate;

// Gives EXPIRED, EXPIRING SOON or VALID using a warning window in days
public class WarningWindowExpiryStatus implements ExpiryStatusStrategy {

    private final int warningDays;

    public WarningWindowExpiryStatus(int warningDays) {
        this.warningDays = warningDays;
    }

    @Override
    public String calculateStatus(LocalDate expiryDate, LocalDate today) {
        if (expiryDate == null) {
            return "UNKNOWN";
        }
        if (expiryDate.isBefore(today)) {
            return "EXPIRED";
        }
        if (!expiryDate.isAfter(today.plusDays(warningDays))) {
            return "EXPIRING SOON";
        }
        return "VALID";
    }
}
