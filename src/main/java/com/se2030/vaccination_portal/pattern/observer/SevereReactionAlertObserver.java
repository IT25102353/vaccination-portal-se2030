package com.se2030.vaccination_portal.pattern.observer;

import com.se2030.vaccination_portal.model.AdverseReaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// When a SEVERE reaction is reported, log a warning
@Component
public class SevereReactionAlertObserver implements Observer<AdverseReaction> {

    private static final Logger log = LoggerFactory.getLogger(SevereReactionAlertObserver.class);

    @Override
    public void update(AdverseReaction reaction) {
        if ("SEVERE".equalsIgnoreCase(reaction.getSeverity())) {
            log.warn("SEVERE adverse reaction reported for {} after {} at {}", reaction.getPatientName(),
                    reaction.getVaccineName(), reaction.getCenterName());
        }
    }
}
