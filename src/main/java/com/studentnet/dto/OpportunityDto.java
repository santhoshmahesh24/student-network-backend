package com.studentnet.dto;

import com.studentnet.model.Opportunity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OpportunityDto {

    public static class CreateOpportunityRequest {
        @NotBlank(message = "Title is required")
        private String title;
        @NotBlank(message = "Description is required")
        private String description;
        @NotNull(message = "Type is required")
        private Opportunity.OpportunityType type;
        private String company;
        private String location;
        private boolean isPaid;
        private String stipend;
        private String requiredSkills;
        private String applyUrl;
        private LocalDate deadline;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Opportunity.OpportunityType getType() { return type; }
        public String getCompany() { return company; }
        public String getLocation() { return location; }
        public boolean isPaid() { return isPaid; }
        public String getStipend() { return stipend; }
        public String getRequiredSkills() { return requiredSkills; }
        public String getApplyUrl() { return applyUrl; }
        public LocalDate getDeadline() { return deadline; }
        public void setTitle(String v) { title = v; }
        public void setDescription(String v) { description = v; }
        public void setType(Opportunity.OpportunityType v) { type = v; }
        public void setCompany(String v) { company = v; }
        public void setLocation(String v) { location = v; }
        public void setPaid(boolean v) { isPaid = v; }
        public void setStipend(String v) { stipend = v; }
        public void setRequiredSkills(String v) { requiredSkills = v; }
        public void setApplyUrl(String v) { applyUrl = v; }
        public void setDeadline(LocalDate v) { deadline = v; }
    }

    public static class OpportunityResponse {
        private Long id;
        private UserDto.UserSummary postedBy;
        private String title;
        private String description;
        private Opportunity.OpportunityType type;
        private String company;
        private String location;
        private boolean isPaid;
        private String stipend;
        private String requiredSkills;
        private String applyUrl;
        private LocalDate deadline;
        private Opportunity.Status status;
        private LocalDateTime postedAt;

        public static OpportunityResponse from(Opportunity o) {
            OpportunityResponse r = new OpportunityResponse();
            r.id = o.getId();
            r.postedBy = UserDto.UserSummary.from(o.getPostedBy());
            r.title = o.getTitle();
            r.description = o.getDescription();
            r.type = o.getType();
            r.company = o.getCompany();
            r.location = o.getLocation();
            r.isPaid = o.isPaid();
            r.stipend = o.getStipend();
            r.requiredSkills = o.getRequiredSkills();
            r.applyUrl = o.getApplyUrl();
            r.deadline = o.getDeadline();
            r.status = o.getStatus();
            r.postedAt = o.getPostedAt();
            return r;
        }

        public Long getId() { return id; }
        public UserDto.UserSummary getPostedBy() { return postedBy; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Opportunity.OpportunityType getType() { return type; }
        public String getCompany() { return company; }
        public String getLocation() { return location; }
        public boolean isPaid() { return isPaid; }
        public String getStipend() { return stipend; }
        public String getRequiredSkills() { return requiredSkills; }
        public String getApplyUrl() { return applyUrl; }
        public LocalDate getDeadline() { return deadline; }
        public Opportunity.Status getStatus() { return status; }
        public LocalDateTime getPostedAt() { return postedAt; }
    }
}
