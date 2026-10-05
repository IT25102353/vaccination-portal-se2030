package com.se2030.vaccination_portal.pattern.factory;

import com.se2030.vaccination_portal.model.Appointment;
import com.se2030.vaccination_portal.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;

// Builds the summary for appointment reports
public class AppointmentReportGenerator implements ReportGenerator {

    private final AppointmentService appointmentService;

    public AppointmentReportGenerator(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public String generateSummary(LocalDate periodStart, LocalDate periodEnd) {
        List<Appointment> inPeriod = appointmentService.getAllAppointments().stream()
                .filter(a -> a.getAppointmentDate() != null
                        && !a.getAppointmentDate().isBefore(periodStart)
                        && !a.getAppointmentDate().isAfter(periodEnd))
                .toList();
        long completed = inPeriod.stream().filter(a -> "COMPLETED".equals(a.getStatus())).count();
        long cancelled = inPeriod.stream().filter(a -> "CANCELLED".equals(a.getStatus())).count();
        return "Appointments from " + periodStart + " to " + periodEnd + ": " + inPeriod.size()
                + " in total, " + completed + " completed, " + cancelled + " cancelled.";
    }
}
