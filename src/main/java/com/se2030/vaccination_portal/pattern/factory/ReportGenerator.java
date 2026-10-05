package com.se2030.vaccination_portal.pattern.factory;

import java.time.LocalDate;

// Factory Method pattern: every report generator implements this interface
public interface ReportGenerator {

    String generateSummary(LocalDate periodStart, LocalDate periodEnd);
}
