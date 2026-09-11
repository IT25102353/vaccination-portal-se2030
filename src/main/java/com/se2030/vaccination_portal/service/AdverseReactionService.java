package com.se2030.vaccination_portal.service;

import com.se2030.vaccination_portal.model.AdverseReaction;
import com.se2030.vaccination_portal.repository.AdverseReactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdverseReactionService {

    private final AdverseReactionRepository adverseReactionRepository;

    public AdverseReactionService(AdverseReactionRepository adverseReactionRepository) {
        this.adverseReactionRepository = adverseReactionRepository;
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
        return adverseReactionRepository.save(reaction);
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
