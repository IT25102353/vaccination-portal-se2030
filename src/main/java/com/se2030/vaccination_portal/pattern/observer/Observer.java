package com.se2030.vaccination_portal.pattern.observer;

// Observer pattern: observers get told when something happens
public interface Observer<T> {

    void update(T event);
}
