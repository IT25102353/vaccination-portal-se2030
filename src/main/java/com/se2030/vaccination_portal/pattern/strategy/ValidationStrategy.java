package com.se2030.vaccination_portal.pattern.strategy;

// Strategy pattern: every validation rule implements this interface
public interface ValidationStrategy<T> {

    void validate(T value);
}
