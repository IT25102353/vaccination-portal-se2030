package com.se2030.vaccination_portal.pattern.strategy;

// Only letters, numbers and spaces are allowed
public class AlphanumericRule implements ValidationStrategy<String> {

    private final String fieldLabel;

    public AlphanumericRule(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    @Override
    public void validate(String value) {
        if (value == null || !value.matches("[A-Za-z0-9 ]+")) {
            throw new IllegalArgumentException(fieldLabel + " can only contain letters and numbers");
        }
    }
}
