package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.VaccinationCenter;
import com.se2030.vaccination_portal.pattern.strategy.ContactNumberRule;
import com.se2030.vaccination_portal.pattern.strategy.ValidationStrategy;
import com.se2030.vaccination_portal.repository.VaccinationCenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationCenterService {

    // Strategy pattern: the contact number rule is a separate strategy object
    private final ValidationStrategy<String> contactNumberRule = new ContactNumberRule();

    private final VaccinationCenterRepository vaccinationCenterRepository;

    public VaccinationCenterService(VaccinationCenterRepository vaccinationCenterRepository) {
        this.vaccinationCenterRepository = vaccinationCenterRepository;
    }

    public List<VaccinationCenter> getAllCenters() {
        return vaccinationCenterRepository.findAll();
    }

    public List<String> getDistinctCenterNames() {
        return vaccinationCenterRepository.findAll().stream()
                .map(VaccinationCenter::getCenterName)
                .distinct()
                .sorted()
                .toList();
    }

    public VaccinationCenter getCenterById(Long id) {
        return vaccinationCenterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaccination center not found with id: " + id));
    }

    public VaccinationCenter createCenter(VaccinationCenter center) {
        contactNumberRule.validate(center.getContactNumber());
        if (center.getCenterName() == null || center.getCenterName().isBlank()) {
            throw new IllegalArgumentException("Center name is required");
        }
        if (center.getCapacityPerDay() == null) {
            throw new IllegalArgumentException("Capacity per day is required");
        }
        return vaccinationCenterRepository.save(center);
    }

    public VaccinationCenter updateCenter(Long id, VaccinationCenter updatedCenter) {
        contactNumberRule.validate(updatedCenter.getContactNumber());
        VaccinationCenter existingCenter = getCenterById(id);
        if (updatedCenter.getCenterName() == null || updatedCenter.getCenterName().isBlank()) {
            throw new IllegalArgumentException("Center name is required");
        }
        if (updatedCenter.getCapacityPerDay() == null) {
            throw new IllegalArgumentException("Capacity per day is required");
        }
        existingCenter.setCenterName(updatedCenter.getCenterName());
        existingCenter.setAddress(updatedCenter.getAddress());
        existingCenter.setDistrict(updatedCenter.getDistrict());
        existingCenter.setContactNumber(updatedCenter.getContactNumber());
        existingCenter.setCapacityPerDay(updatedCenter.getCapacityPerDay());
        existingCenter.setOperatingHours(updatedCenter.getOperatingHours());
        existingCenter.setStatus(updatedCenter.getStatus());
        return vaccinationCenterRepository.save(existingCenter);
    }

    public void deleteCenter(Long id) {
        VaccinationCenter center = getCenterById(id);
        vaccinationCenterRepository.delete(center);
    }
}
