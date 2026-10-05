package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.DoseRecord;
import com.se2030.vaccination_portal.pattern.observer.Observer;
import com.se2030.vaccination_portal.repository.DoseRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoseRecordService {

    private final DoseRecordRepository doseRecordRepository;

    // Observer pattern: this service is the subject, the observers react when a dose is recorded
    private final List<Observer<DoseRecord>> observers;

    public DoseRecordService(DoseRecordRepository doseRecordRepository, List<Observer<DoseRecord>> observers) {
        this.doseRecordRepository = doseRecordRepository;
        this.observers = observers;
    }

    public List<DoseRecord> getAllDoseRecords() {
        return doseRecordRepository.findAll();
    }

    public DoseRecord getDoseRecordById(Long id) {
        return doseRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dose record not found with id: " + id));
    }

    public DoseRecord createDoseRecord(DoseRecord doseRecord) {
        if (doseRecord.getPatientName() == null || doseRecord.getPatientName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (doseRecord.getDateAdministered() == null) {
            throw new IllegalArgumentException("Date administered is required");
        }
        DoseRecord saved = doseRecordRepository.save(doseRecord);
        notifyObservers(saved);
        return saved;
    }

    public DoseRecord updateDoseRecord(Long id, DoseRecord updatedDoseRecord) {
        DoseRecord existingDoseRecord = getDoseRecordById(id);
        if (updatedDoseRecord.getPatientName() == null || updatedDoseRecord.getPatientName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (updatedDoseRecord.getDateAdministered() == null) {
            throw new IllegalArgumentException("Date administered is required");
        }
        existingDoseRecord.setPatientName(updatedDoseRecord.getPatientName());
        existingDoseRecord.setPatientNic(updatedDoseRecord.getPatientNic());
        existingDoseRecord.setVaccineName(updatedDoseRecord.getVaccineName());
        existingDoseRecord.setDoseNumber(updatedDoseRecord.getDoseNumber());
        existingDoseRecord.setDateAdministered(updatedDoseRecord.getDateAdministered());
        existingDoseRecord.setAdministeredBy(updatedDoseRecord.getAdministeredBy());
        existingDoseRecord.setCenterName(updatedDoseRecord.getCenterName());
        existingDoseRecord.setCertificateNumber(updatedDoseRecord.getCertificateNumber());
        return doseRecordRepository.save(existingDoseRecord);
    }

    public void deleteDoseRecord(Long id) {
        DoseRecord doseRecord = getDoseRecordById(id);
        doseRecordRepository.delete(doseRecord);
    }

    private void notifyObservers(DoseRecord doseRecord) {
        for (Observer<DoseRecord> observer : observers) {
            observer.update(doseRecord);
        }
    }
}
