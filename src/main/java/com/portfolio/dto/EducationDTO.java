package com.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.entity.Education;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Education entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EducationDTO {

    private Long id;

    @NotBlank(message = "Degree is required")
    @Size(min = 2, max = 200, message = "Degree must be between 2 and 200 characters")
    private String degree;

    @NotBlank(message = "Institution name is required")
    @Size(min = 2, max = 200, message = "Institution name must be between 2 and 200 characters")
    private String institution;

    private String fieldOfStudy;
    private String location;
    private String description;
    private Double gpa;
    private Double maxGpa;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean isCurrent;
    private Education.DegreeType degreeType;
    private Education.EducationStatus status;
    private String honors;
    private String relevantCoursework;
    private String activities;
    private Boolean isFeatured;
    private Boolean isActive;
    private Integer displayOrder;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Computed fields
    private String dateRangeString;
    private String gpaString;
    private Boolean hasHighGpa;
    private String degreeTypeDisplayName;
    private String statusDisplayName;
    private String fullDegreeTitle;
    private Boolean isRecent;
    private Integer durationInYears;

    // Portfolio reference (minimal)
    private Long portfolioId;
    private String portfolioOwnerName;

    // Constructors
    public EducationDTO() {}

    public EducationDTO(String degree, String institution, LocalDate startDate) {
        this.degree = degree;
        this.institution = institution;
        this.startDate = startDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Double getMaxGpa() {
        return maxGpa;
    }

    public void setMaxGpa(Double maxGpa) {
        this.maxGpa = maxGpa;
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

    public Education.DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(Education.DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    public Education.EducationStatus getStatus() {
        return status;
    }

    public void setStatus(Education.EducationStatus status) {
        this.status = status;
    }

    public String getHonors() {
        return honors;
    }

    public void setHonors(String honors) {
        this.honors = honors;
    }

    public String getRelevantCoursework() {
        return relevantCoursework;
    }

    public void setRelevantCoursework(String relevantCoursework) {
        this.relevantCoursework = relevantCoursework;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
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

    public String getDateRangeString() {
        return dateRangeString;
    }

    public void setDateRangeString(String dateRangeString) {
        this.dateRangeString = dateRangeString;
    }

    public String getGpaString() {
        return gpaString;
    }

    public void setGpaString(String gpaString) {
        this.gpaString = gpaString;
    }

    public Boolean getHasHighGpa() {
        return hasHighGpa;
    }

    public void setHasHighGpa(Boolean hasHighGpa) {
        this.hasHighGpa = hasHighGpa;
    }

    public String getDegreeTypeDisplayName() {
        return degreeTypeDisplayName;
    }

    public void setDegreeTypeDisplayName(String degreeTypeDisplayName) {
        this.degreeTypeDisplayName = degreeTypeDisplayName;
    }

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
    }

    public String getFullDegreeTitle() {
        return fullDegreeTitle;
    }

    public void setFullDegreeTitle(String fullDegreeTitle) {
        this.fullDegreeTitle = fullDegreeTitle;
    }

    public Boolean getIsRecent() {
        return isRecent;
    }

    public void setIsRecent(Boolean isRecent) {
        this.isRecent = isRecent;
    }

    public Integer getDurationInYears() {
        return durationInYears;
    }

    public void setDurationInYears(Integer durationInYears) {
        this.durationInYears = durationInYears;
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
        return "EducationDTO{" +
                "id=" + id +
                ", degree='" + degree + '\'' +
                ", institution='" + institution + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", degreeType=" + degreeType +
                ", status=" + status +
                ", isCurrent=" + isCurrent +
                ", isActive=" + isActive +
                '}';
    }
}

