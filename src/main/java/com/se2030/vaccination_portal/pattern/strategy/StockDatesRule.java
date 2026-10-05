package com.se2030.vaccination_portal.pattern.strategy;

import com.se2030.vaccination_portal.model.VaccineStock;

// Received date must be before the expiry date
public class StockDatesRule implements ValidationStrategy<VaccineStock> {

    @Override
    public void validate(VaccineStock stock) {
        if (stock.getReceivedDate() != null && stock.getExpiryDate() != null
                && !stock.getReceivedDate().isBefore(stock.getExpiryDate())) {
            throw new IllegalArgumentException("Received date must be before the expiry date");
        }
    }
}
