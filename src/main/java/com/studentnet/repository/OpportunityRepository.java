package com.studentnet.repository;

import com.studentnet.model.Opportunity;
import com.studentnet.model.Opportunity.OpportunityType;
import com.studentnet.model.Opportunity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    Page<Opportunity> findByStatusOrderByPostedAtDesc(Status status, Pageable pageable);

    Page<Opportunity> findByTypeAndStatusOrderByPostedAtDesc(OpportunityType type,
                                                              Status status, Pageable pageable);

    Page<Opportunity> findByPostedByIdOrderByPostedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT o FROM Opportunity o WHERE o.status = 'ACTIVE' AND (" +
           "LOWER(o.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(o.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(o.requiredSkills) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(o.company) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Opportunity> searchOpportunities(@Param("query") String query, Pageable pageable);
}
