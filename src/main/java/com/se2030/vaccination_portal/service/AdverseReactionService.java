package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.AdverseReaction;
import com.se2030.vaccination_portal.pattern.observer.Observer;
import com.se2030.vaccination_portal.repository.AdverseReactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdverseReactionService {

    private final AdverseReactionRepository adverseReactionRepository;

    // Observer pattern: this service is the subject, the observers react when a reaction is reported
    private final List<Observer<AdverseReaction>> observers;

    public AdverseReactionService(AdverseReactionRepository adverseReactionRepository,
                                  List<Observer<AdverseReaction>> observers) {
        this.adverseReactionRepository = adverseReactionRepository;
        this.observers = observers;
    }

    public List<AdverseReaction> getAllReactions() {
        return adverseReactionRepository.findAll();
    }

    public AdverseReaction getReactionById(Long id) {
        return adverseReactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Adverse reaction not found with id: " + id));
    }

    public AdverseReaction createReaction(AdverseReaction reaction) {
        if (reaction.getPatientName() == null || reaction.getPatientName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (reaction.getReportedDate() == null) {
            throw new IllegalArgumentException("Reported date is required");
        }
        AdverseReaction saved = adverseReactionRepository.save(reaction);
        for (Observer<AdverseReaction> observer : observers) {
            observer.update(saved);
        }
        return saved;
    }

    public AdverseReaction updateReaction(Long id, AdverseReaction updatedReaction) {
        AdverseReaction existingReaction = getReactionById(id);
        if (updatedReaction.getPatientName() == null || updatedReaction.getPatientName().isBlank()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (updatedReaction.getReportedDate() == null) {
            throw new IllegalArgumentException("Reported date is required");
        }
        existingReaction.setPatientName(updatedReaction.getPatientName());
        existingReaction.setPatientNic(updatedReaction.getPatientNic());
        existingReaction.setVaccineName(updatedReaction.getVaccineName());
        existingReaction.setCenterName(updatedReaction.getCenterName());
        existingReaction.setReactionDescription(updatedReaction.getReactionDescription());
        existingReaction.setSeverity(updatedReaction.getSeverity());
        existingReaction.setReportedDate(updatedReaction.getReportedDate());
        existingReaction.setActionTaken(updatedReaction.getActionTaken());
        return adverseReactionRepository.save(existingReaction);
    }

    public void deleteReaction(Long id) {
        AdverseReaction reaction = getReactionById(id);
        adverseReactionRepository.delete(reaction);
    }
}
