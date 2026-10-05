package com.se2030.vaccination_portal.pattern.factory;

import com.se2030.vaccination_portal.model.AdverseReaction;
import com.se2030.vaccination_portal.service.AdverseReactionService;

import java.time.LocalDate;
import java.util.List;

// Builds the summary for adverse reaction reports
public class AdverseReactionReportGenerator implements ReportGenerator {

    private final AdverseReactionService adverseReactionService;

    public AdverseReactionReportGenerator(AdverseReactionService adverseReactionService) {
        this.adverseReactionService = adverseReactionService;
    }

    @Override
    public String generateSummary(LocalDate periodStart, LocalDate periodEnd) {
        List<AdverseReaction> inPeriod = adverseReactionService.getAllReactions().stream()
                .filter(r -> r.getReportedDate() != null
                        && !r.getReportedDate().isBefore(periodStart)
                        && !r.getReportedDate().isAfter(periodEnd))
                .toList();
        long severe = inPeriod.stream().filter(r -> "SEVERE".equalsIgnoreCase(r.getSeverity())).count();
        return "Adverse reactions from " + periodStart + " to " + periodEnd + ": " + inPeriod.size()
                + " reported, " + severe + " severe.";
    }
}
