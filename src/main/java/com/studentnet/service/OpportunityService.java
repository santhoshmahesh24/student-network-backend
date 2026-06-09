package com.studentnet.service;

import com.studentnet.dto.OpportunityDto;
import com.studentnet.model.Opportunity;
import com.studentnet.model.User;
import com.studentnet.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpportunityService {

    @Autowired private OpportunityRepository opportunityRepository;
    @Autowired private UserService userService;

    @Transactional
    public OpportunityDto.OpportunityResponse postOpportunity(
            String email, OpportunityDto.CreateOpportunityRequest request) {

        User poster = userService.getCurrentUser(email);
        Opportunity opp = Opportunity.builder()
                .postedBy(poster)
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .company(request.getCompany())
                .location(request.getLocation())
                .isPaid(request.isPaid())
                .stipend(request.getStipend())
                .requiredSkills(request.getRequiredSkills())
                .applyUrl(request.getApplyUrl())
                .deadline(request.getDeadline())
                .build();
        return OpportunityDto.OpportunityResponse.from(opportunityRepository.save(opp));
    }

    public Page<OpportunityDto.OpportunityResponse> getActiveOpportunities(int page, int size) {
        return opportunityRepository
                .findByStatusOrderByPostedAtDesc(Opportunity.Status.ACTIVE, PageRequest.of(page, size))
                .map(OpportunityDto.OpportunityResponse::from);
    }

    public Page<OpportunityDto.OpportunityResponse> getByType(
            Opportunity.OpportunityType type, int page, int size) {
        return opportunityRepository
                .findByTypeAndStatusOrderByPostedAtDesc(type, Opportunity.Status.ACTIVE,
                                                         PageRequest.of(page, size))
                .map(OpportunityDto.OpportunityResponse::from);
    }

    public Page<OpportunityDto.OpportunityResponse> searchOpportunities(
            String query, int page, int size) {
        return opportunityRepository.searchOpportunities(query, PageRequest.of(page, size))
                .map(OpportunityDto.OpportunityResponse::from);
    }

    @Transactional
    public void closeOpportunity(String email, Long opportunityId) {
        User user = userService.getCurrentUser(email);
        Opportunity opp = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
        if (!opp.getPostedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: only the poster can close this");
        }
        opp.setStatus(Opportunity.Status.CLOSED);
        opportunityRepository.save(opp);
    }
}
