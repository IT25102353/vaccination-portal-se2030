package com.se2030.vaccination_portal.pattern;

import com.se2030.vaccination_portal.pattern.strategy.AlphanumericRule;
import com.se2030.vaccination_portal.pattern.strategy.ContactNumberRule;
import com.se2030.vaccination_portal.pattern.strategy.WarningWindowExpiryStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StrategyPatternTest {

    @Test
    void alphanumericRuleAcceptsLettersNumbersSpaces() {
        assertDoesNotThrow(() -> new AlphanumericRule("Name").validate("Pfizer 2"));
    }

    @Test
    void alphanumericRuleRejectsSymbolsWithFieldName() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new AlphanumericRule("Batch number").validate("A%1"));
        assertEquals("Batch number can only contain letters and numbers", e.getMessage());
    }

    @Test
    void contactNumberRuleNeedsTenDigits() {
        ContactNumberRule rule = new ContactNumberRule();
        assertDoesNotThrow(() -> rule.validate("0771234567"));
        assertThrows(IllegalArgumentException.class, () -> rule.validate("12345"));
        assertThrows(IllegalArgumentException.class, () -> rule.validate(null));
    }

    @Test
    void differentStrategiesGiveDifferentStatuses() {
        LocalDate today = LocalDate.of(2026, 1, 1);
        LocalDate expiry = today.plusDays(45);
        assertEquals("VALID", new WarningWindowExpiryStatus(30).calculateStatus(expiry, today));
        assertEquals("EXPIRING SOON", new WarningWindowExpiryStatus(60).calculateStatus(expiry, today));
    }
}
