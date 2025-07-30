package com.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.entity.Experience;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Experience entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperienceDTO {

    private Long id;

    @NotBlank(message = "Job title is required")
    @Size(min = 2, max = 150, message = "Job title must be between 2 and 150 characters")
    private String jobTitle;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 150, message = "Company name must be between 2 and 150 characters")
    private String companyName;

    private String companyUrl;
    private String location;
    private String description;
    private String responsibilities;
    private String achievements;
    private String technologiesUsed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean isCurrent;
    private Experience.EmploymentType employmentType;
    private Boolean isFeatured;
    private Boolean isActive;
    private Integer displayOrder;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Computed fields
    private String durationString;
    private Long totalMonths;
    private String dateRangeString;
    private String employmentTypeDisplayName;
    private Boolean isLongTerm;
    private Boolean isRecent;

    // Portfolio reference (minimal)
    private Long portfolioId;
    private String portfolioOwnerName;

    // Constructors
    public ExperienceDTO() {}

    public ExperienceDTO(String jobTitle, String companyName, LocalDate startDate) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.startDate = startDate;
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
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Experience.EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Experience.EmploymentType employmentType) {
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

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public Long getTotalMonths() {
        return totalMonths;
    }

    public void setTotalMonths(Long totalMonths) {
        this.totalMonths = totalMonths;
    }

    public String getDateRangeString() {
        return dateRangeString;
    }

    public void setDateRangeString(String dateRangeString) {
        this.dateRangeString = dateRangeString;
    }

    public String getEmploymentTypeDisplayName() {
        return employmentTypeDisplayName;
    }

    public void setEmploymentTypeDisplayName(String employmentTypeDisplayName) {
        this.employmentTypeDisplayName = employmentTypeDisplayName;
    }

    public Boolean getIsLongTerm() {
        return isLongTerm;
    }

    public void setIsLongTerm(Boolean isLongTerm) {
        this.isLongTerm = isLongTerm;
    }

    public Boolean getIsRecent() {
        return isRecent;
    }

    public void setIsRecent(Boolean isRecent) {
        this.isRecent = isRecent;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioOwnerName() {
        return portfolioOwnerName;
    }

    public void setPortfolioOwnerName(String portfolioOwnerName) {
        this.portfolioOwnerName = portfolioOwnerName;
    }

    @Override
    public String toString() {
        return "ExperienceDTO{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isCurrent=" + isCurrent +
                ", employmentType=" + employmentType +
                ", isActive=" + isActive +
                '}';
    }
}

