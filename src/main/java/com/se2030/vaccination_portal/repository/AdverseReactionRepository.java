package com.se2030.vaccination_portal.repository;

import com.se2030.vaccination_portal.model.AdverseReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdverseReactionRepository extends JpaRepository<AdverseReaction, Long> {
}
