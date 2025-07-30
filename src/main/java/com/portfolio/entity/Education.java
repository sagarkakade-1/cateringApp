package com.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Education entity representing educational background in the portfolio.
 * Demonstrates JPA annotations, validation, and enum usage.
 */
@Entity
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Degree is required")
    @Size(min = 2, max = 200, message = "Degree must be between 2 and 200 characters")
    @Column(name = "degree", nullable = false, length = 200)
    private String degree;

    @NotBlank(message = "Institution name is required")
    @Size(min = 2, max = 200, message = "Institution name must be between 2 and 200 characters")
    @Column(name = "institution", nullable = false, length = 200)
    private String institution;

    @Column(name = "field_of_study", length = 150)
    private String fieldOfStudy;

    @Column(name = "location")
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "gpa")
    private Double gpa;

    @Column(name = "max_gpa")
    private Double maxGpa = 4.0; // Default to 4.0 scale

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate; // null means currently studying

    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree_type", nullable = false)
    private DegreeType degreeType = DegreeType.BACHELOR;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EducationStatus status = EducationStatus.COMPLETED;

    @Column(name = "honors")
    private String honors; // e.g., "Magna Cum Laude", "Dean's List"

    @Column(name = "relevant_coursework", length = 1000)
    private String relevantCoursework; // Comma-separated values

    @Column(name = "activities", length = 500)
    private String activities; // Extracurricular activities

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

    // Enums
    public enum DegreeType {
        HIGH_SCHOOL, ASSOCIATE, BACHELOR, MASTER, DOCTORATE, CERTIFICATE, DIPLOMA, OTHER
    }

    public enum EducationStatus {
        COMPLETED, IN_PROGRESS, DROPPED_OUT, TRANSFERRED, DEFERRED
    }

    // Constructors
    public Education() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Education(String degree, String institution, LocalDate startDate, Portfolio portfolio) {
        this();
        this.degree = degree;
        this.institution = institution;
        this.startDate = startDate;
        this.portfolio = portfolio;
    }

    // JPA Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        // Auto-set isCurrent based on endDate and status
        this.isCurrent = (this.endDate == null && this.status == EducationStatus.IN_PROGRESS);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        // Auto-set isCurrent based on endDate and status
        this.isCurrent = (this.endDate == null && this.status == EducationStatus.IN_PROGRESS);
    }

    // Business methods using Java 8 features
    public String getDateRangeString() {
        String startStr = startDate.getYear() + "";
        if (isCurrent || endDate == null) {
            return startStr + " - Present";
        } else {
            return startStr + " - " + endDate.getYear();
        }
    }

    public String getGpaString() {
        if (gpa == null) return null;
        return String.format("%.2f", gpa) + 
               (maxGpa != null && maxGpa != 4.0 ? "/" + String.format("%.1f", maxGpa) : "");
    }

    public boolean hasHighGpa() {
        if (gpa == null || maxGpa == null) return false;
        double percentage = (gpa / maxGpa) * 100;
        return percentage >= 85.0; // 85% or higher
    }

    public String getDegreeTypeDisplayName() {
        return degreeType.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    public String getStatusDisplayName() {
        return status.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    public String getFullDegreeTitle() {
        StringBuilder title = new StringBuilder();
        title.append(degree);
        if (fieldOfStudy != null && !fieldOfStudy.trim().isEmpty()) {
            title.append(" in ").append(fieldOfStudy);
        }
        return title.toString();
    }

    public boolean isRecent() {
        LocalDate cutoffDate = LocalDate.now().minusYears(5);
        LocalDate endDateToUse = endDate != null ? endDate : LocalDate.now();
        return endDateToUse.isAfter(cutoffDate);
    }

    public int getDurationInYears() {
        LocalDate endDateToUse = endDate != null ? endDate : LocalDate.now();
        return endDateToUse.getYear() - startDate.getYear();
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
        this.isCurrent = (endDate == null && this.status == EducationStatus.IN_PROGRESS);
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
        if (isCurrent) {
            this.endDate = null;
            this.status = EducationStatus.IN_PROGRESS;
        }
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    public EducationStatus getStatus() {
        return status;
    }

    public void setStatus(EducationStatus status) {
        this.status = status;
        this.isCurrent = (this.endDate == null && status == EducationStatus.IN_PROGRESS);
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
        Education education = (Education) o;
        return Objects.equals(id, education.id) && 
               Objects.equals(degree, education.degree) && 
               Objects.equals(institution, education.institution) && 
               Objects.equals(startDate, education.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, degree, institution, startDate);
    }

    @Override
    public String toString() {
        return "Education{" +
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
                ", createdAt=" + createdAt +
                '}';
    }
}

