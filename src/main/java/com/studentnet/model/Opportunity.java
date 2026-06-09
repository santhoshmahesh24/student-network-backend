package com.studentnet.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunities")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_id", nullable = false)
    private User postedBy;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    private OpportunityType type;

    private String company;
    private String location;
    private boolean isPaid;
    private String stipend;
    private String requiredSkills;
    private String applyUrl;
    private LocalDate deadline;

    private LocalDateTime postedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum OpportunityType { INTERNSHIP, PROJECT, RESEARCH, JOB, HACKATHON, SCHOLARSHIP }
    public enum Status { ACTIVE, CLOSED, EXPIRED }

    public Opportunity() {}

    // Getters
    public Long getId() { return id; }
    public User getPostedBy() { return postedBy; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public OpportunityType getType() { return type; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public boolean isPaid() { return isPaid; }
    public String getStipend() { return stipend; }
    public String getRequiredSkills() { return requiredSkills; }
    public String getApplyUrl() { return applyUrl; }
    public LocalDate getDeadline() { return deadline; }
    public LocalDateTime getPostedAt() { return postedAt; }
    public Status getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setType(OpportunityType type) { this.type = type; }
    public void setCompany(String company) { this.company = company; }
    public void setLocation(String location) { this.location = location; }
    public void setPaid(boolean paid) { isPaid = paid; }
    public void setStipend(String stipend) { this.stipend = stipend; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }
    public void setApplyUrl(String applyUrl) { this.applyUrl = applyUrl; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }
    public void setStatus(Status status) { this.status = status; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Opportunity o = new Opportunity();
        public Builder postedBy(User v) { o.postedBy = v; return this; }
        public Builder title(String v) { o.title = v; return this; }
        public Builder description(String v) { o.description = v; return this; }
        public Builder type(OpportunityType v) { o.type = v; return this; }
        public Builder company(String v) { o.company = v; return this; }
        public Builder location(String v) { o.location = v; return this; }
        public Builder isPaid(boolean v) { o.isPaid = v; return this; }
        public Builder stipend(String v) { o.stipend = v; return this; }
        public Builder requiredSkills(String v) { o.requiredSkills = v; return this; }
        public Builder applyUrl(String v) { o.applyUrl = v; return this; }
        public Builder deadline(LocalDate v) { o.deadline = v; return this; }
        public Opportunity build() { return o; }
    }
}
