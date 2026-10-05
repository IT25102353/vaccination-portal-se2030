package com.se2030.vaccination_portal.pattern.strategy;

import java.time.LocalDate;

// Strategy pattern: different ways to work out the expiry status
public interface ExpiryStatusStrategy {

    String calculateStatus(LocalDate expiryDate, LocalDate today);
}
