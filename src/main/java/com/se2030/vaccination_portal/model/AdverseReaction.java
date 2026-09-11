package com.se2030.vaccination_portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class AdverseReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String patientNic;
    private String vaccineName;
    private String centerName;
    private String reactionDescription;
    private String severity;
    private LocalDate reportedDate;
    private String actionTaken;

    public AdverseReaction() {
    }

    public AdverseReaction(String patientName, String patientNic, String vaccineName, String centerName,
                            String reactionDescription, String severity, LocalDate reportedDate, String actionTaken) {
        this.patientName = patientName;
        this.patientNic = patientNic;
        this.vaccineName = vaccineName;
        this.centerName = centerName;
        this.reactionDescription = reactionDescription;
        this.severity = severity;
        this.reportedDate = reportedDate;
        this.actionTaken = actionTaken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientNic() {
        return patientNic;
    }

    public void setPatientNic(String patientNic) {
        this.patientNic = patientNic;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getReactionDescription() {
        return reactionDescription;
    }

    public void setReactionDescription(String reactionDescription) {
        this.reactionDescription = reactionDescription;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDate getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(LocalDate reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }
}
