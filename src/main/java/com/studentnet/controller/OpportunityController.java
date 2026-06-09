package com.studentnet.controller;

import com.studentnet.dto.OpportunityDto;
import com.studentnet.model.Opportunity;
import com.studentnet.service.OpportunityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    @Autowired private OpportunityService opportunityService;

    /** Post a new opportunity */
    @PostMapping
    public ResponseEntity<OpportunityDto.OpportunityResponse> postOpportunity(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody OpportunityDto.CreateOpportunityRequest request) {
        return ResponseEntity.ok(
                opportunityService.postOpportunity(userDetails.getUsername(), request));
    }

    /** Browse all active opportunities */
    @GetMapping
    public ResponseEntity<Page<OpportunityDto.OpportunityResponse>> getOpportunities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(opportunityService.getActiveOpportunities(page, size));
    }

    /** Filter by type: INTERNSHIP, PROJECT, RESEARCH, JOB, HACKATHON, SCHOLARSHIP */
    @GetMapping("/type/{type}")
    public ResponseEntity<Page<OpportunityDto.OpportunityResponse>> getByType(
            @PathVariable Opportunity.OpportunityType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(opportunityService.getByType(type, page, size));
    }

    /** Search opportunities */
    @GetMapping("/search")
    public ResponseEntity<Page<OpportunityDto.OpportunityResponse>> searchOpportunities(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(opportunityService.searchOpportunities(q, page, size));
    }

    /** Close your opportunity */
    @PutMapping("/{id}/close")
    public ResponseEntity<String> closeOpportunity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        try {
            opportunityService.closeOpportunity(userDetails.getUsername(), id);
            return ResponseEntity.ok("Opportunity closed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
