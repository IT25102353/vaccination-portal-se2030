package com.se2030.vaccination_portal.pattern.strategy;

// Contact number must be exactly 10 digits
public class ContactNumberRule implements ValidationStrategy<String> {

    @Override
    public void validate(String value) {
        if (value == null || !value.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must have exactly 10 digits");
        }
    }
}
