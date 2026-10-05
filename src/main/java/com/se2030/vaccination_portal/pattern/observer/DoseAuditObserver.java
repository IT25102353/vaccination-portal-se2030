package com.se2030.vaccination_portal.pattern.observer;

import com.se2030.vaccination_portal.model.DoseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// When a dose is recorded, write a log line
@Component
public class DoseAuditObserver implements Observer<DoseRecord> {

    private static final Logger log = LoggerFactory.getLogger(DoseAuditObserver.class);

    @Override
    public void update(DoseRecord doseRecord) {
        log.info("Dose recorded: {} (dose {}) for {} at {}", doseRecord.getVaccineName(),
                doseRecord.getDoseNumber(), doseRecord.getPatientName(), doseRecord.getCenterName());
    }
}
