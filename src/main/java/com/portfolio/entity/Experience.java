package com.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

/**
 * Experience entity representing work experience in the portfolio.
 * Demonstrates JPA annotations, validation, and date handling with Java 8+ features.
 */
@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Job title is required")
    @Size(min = 2, max = 150, message = "Job title must be between 2 and 150 characters")
    @Column(name = "job_title", nullable = false, length = 150)
    private String jobTitle;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 150, message = "Company name must be between 2 and 150 characters")
    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "company_url")
    private String companyUrl;

    @Column(name = "location")
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String responsibilities; // Newline or comma-separated

    @Column(name = "achievements", columnDefinition = "TEXT")
    private String achievements; // Newline or comma-separated

    @Column(name = "technologies_used", length = 1000)
    private String technologiesUsed; // Comma-separated values

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate; // null means current job

    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private EmploymentType employmentType = EmploymentType.FULL_TIME;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Many-to-One relationship with Portfolio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonBackReference
    private Portfolio portfolio;

    // Enum for employment types
    public enum EmploymentType {
        FULL_TIME, PART_TIME, CONTRACT, FREELANCE, INTERNSHIP, VOLUNTEER
    }

    // Constructors
    public Experience() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Experience(String jobTitle, String companyName, LocalDate startDate, Portfolio portfolio) {
        this();
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.startDate = startDate;
        this.portfolio = portfolio;
    }

    // JPA Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        // Auto-set isCurrent based on endDate
        this.isCurrent = (this.endDate == null);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        // Auto-set isCurrent based on endDate
        this.isCurrent = (this.endDate == null);
    }

    // Business methods using Java 8 features
    public String getDurationString() {
        LocalDate endDateToUse = endDate != null ? endDate : LocalDate.now();
        Period period = Period.between(startDate, endDateToUse);
        
        int years = period.getYears();
        int months = period.getMonths();
        
        if (years == 0 && months == 0) {
            return "Less than a month";
        } else if (years == 0) {
            return months == 1 ? "1 month" : months + " months";
        } else if (months == 0) {
            return years == 1 ? "1 year" : years + " years";
        } else {
            String yearStr = years == 1 ? "1 year" : years + " years";
            String monthStr = months == 1 ? "1 month" : months + " months";
            return yearStr + " " + monthStr;
        }
    }

    public long getTotalMonths() {
        LocalDate endDateToUse = endDate != null ? endDate : LocalDate.now();
        Period period = Period.between(startDate, endDateToUse);
        return period.toTotalMonths();
    }

    public String getDateRangeString() {
        String startStr = startDate.getMonth().name().substring(0, 3) + " " + startDate.getYear();
        if (isCurrent || endDate == null) {
            return startStr + " - Present";
        } else {
            String endStr = endDate.getMonth().name().substring(0, 3) + " " + endDate.getYear();
            return startStr + " - " + endStr;
        }
    }

    public String getEmploymentTypeDisplayName() {
        return employmentType.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    public boolean isLongTerm() {
        return getTotalMonths() >= 12; // 1 year or more
    }

    public boolean isRecent() {
        LocalDate cutoffDate = LocalDate.now().minusYears(2);
        LocalDate endDateToUse = endDate != null ? endDate : LocalDate.now();
        return endDateToUse.isAfter(cutoffDate);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getTechnologiesUsed() {
        return technologiesUsed;
    }

    public void setTechnologiesUsed(String technologiesUsed) {
        this.technologiesUsed = technologiesUsed;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        this.isCurrent = (endDate == null);
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
        if (isCurrent) {
            this.endDate = null;
        }
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(jobTitle, that.jobTitle) && 
               Objects.equals(companyName, that.companyName) && 
               Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobTitle, companyName, startDate);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isCurrent=" + isCurrent +
                ", employmentType=" + employmentType +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}

