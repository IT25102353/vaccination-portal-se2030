package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.Appointment;
import com.se2030.vaccination_portal.model.VaccinationCenter;
import com.se2030.vaccination_portal.repository.AppointmentRepository;
import com.se2030.vaccination_portal.repository.VaccinationCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContactNumberValidationTest {

    private VaccinationCenterService centerService;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        centerService = new VaccinationCenterService(Mockito.mock(VaccinationCenterRepository.class));
        appointmentService = new AppointmentService(Mockito.mock(AppointmentRepository.class));
    }

    private VaccinationCenter center(String contact) {
        return new VaccinationCenter("Central", "Main St", "Colombo", contact, 100, "8-4", "ACTIVE");
    }

    private Appointment appointment(String contact) {
        return new Appointment("Nimal", "123456789V", contact, "Pfizer", "Central",
                LocalDate.now().plusDays(1), LocalTime.of(9, 0), "SCHEDULED");
    }

    @Test
    void centerAcceptsTenDigits() {
        assertDoesNotThrow(() -> centerService.createCenter(center("0771234567")));
    }

    @Test
    void centerRejectsWrongContactNumbers() {
        for (String bad : new String[]{"077123456", "07712345678", "07712345ab", "077-123456", "", null}) {
            assertThrows(IllegalArgumentException.class, () -> centerService.createCenter(center(bad)), "value: " + bad);
        }
    }

    @Test
    void appointmentAcceptsTenDigits() {
        assertDoesNotThrow(() -> appointmentService.createAppointment(appointment("0771234567")));
    }

    @Test
    void appointmentRejectsWrongContactNumbers() {
        for (String bad : new String[]{"12345", "12345678901", "abcdefghij", "+947712345", null}) {
            assertThrows(IllegalArgumentException.class, () -> appointmentService.createAppointment(appointment(bad)), "value: " + bad);
        }
    }
}
