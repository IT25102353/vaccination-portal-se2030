package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.Appointment;
import com.se2030.vaccination_portal.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getDistinctPatients() {
        Map<String, Appointment> patientsByNic = new LinkedHashMap<>();
        for (Appointment appointment : appointmentRepository.findAll()) {
            patientsByNic.put(appointment.getPatientNic(), appointment);
        }
        return new ArrayList<>(patientsByNic.values());
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
    }

    public Appointment createAppointment(Appointment appointment) {
        if (appointment.getPatientName() == null || appointment.getPatientName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (appointment.getAppointmentDate() == null) {
            throw new IllegalArgumentException("Appointment date is required");
        }
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointmentById(id);
        if (updatedAppointment.getPatientName() == null || updatedAppointment.getPatientName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (updatedAppointment.getAppointmentDate() == null) {
            throw new IllegalArgumentException("Appointment date is required");
        }
        existingAppointment.setPatientName(updatedAppointment.getPatientName());
        existingAppointment.setPatientNic(updatedAppointment.getPatientNic());
        existingAppointment.setContactNumber(updatedAppointment.getContactNumber());
        existingAppointment.setVaccineName(updatedAppointment.getVaccineName());
        existingAppointment.setCenterName(updatedAppointment.getCenterName());
        existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
        existingAppointment.setStatus(updatedAppointment.getStatus());
        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.delete(appointment);
    }
}
