package com.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Project entity representing portfolio projects.
 * Demonstrates JPA relationships, validation, and Java 8+ features.
 */
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 200, message = "Project name must be between 2 and 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "technologies", length = 1000)
    private String technologies; // Comma-separated values

    @Column(name = "project_url")
    private String projectUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "demo_url")
    private String demoUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status = ProjectStatus.COMPLETED;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ProjectCategory category = ProjectCategory.WEB_APPLICATION;

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
    public enum ProjectStatus {
        PLANNING, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED
    }

    public enum ProjectCategory {
        WEB_APPLICATION, MOBILE_APP, DESKTOP_APP, API, LIBRARY, 
        DATA_ANALYSIS, MACHINE_LEARNING, DEVOPS, OTHER
    }

    // Constructors
    public Project() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Project(String name, String description, Portfolio portfolio) {
        this();
        this.name = name;
        this.description = description;
        this.portfolio = portfolio;
    }

    // JPA Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Business methods using Java 8 features
    public List<String> getTechnologyList() {
        return technologies != null ? 
            Arrays.stream(technologies.split(","))
                .map(String::trim)
                .filter(tech -> !tech.isEmpty())
                .collect(Collectors.toList()) : 
            new ArrayList<>();
    }

    public void setTechnologyList(List<String> techList) {
        this.technologies = techList.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(tech -> !tech.isEmpty())
            .collect(Collectors.joining(", "));
    }

    public boolean isCurrentlyActive() {
        return status == ProjectStatus.IN_PROGRESS || 
               (status == ProjectStatus.COMPLETED && endDate == null);
    }

    public long getDurationInDays() {
        if (startDate == null) return 0;
        LocalDate endDateToUse = endDate != null ? endDate : LocalDate.now();
        return startDate.until(endDateToUse).getDays();
    }

    public boolean hasValidUrls() {
        return (projectUrl != null && !projectUrl.trim().isEmpty()) ||
               (githubUrl != null && !githubUrl.trim().isEmpty()) ||
               (demoUrl != null && !demoUrl.trim().isEmpty());
    }

    public String getStatusDisplayName() {
        return status.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    public String getCategoryDisplayName() {
        return category.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getDemoUrl() {
        return demoUrl;
    }

    public void setDemoUrl(String demoUrl) {
        this.demoUrl = demoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public ProjectCategory getCategory() {
        return category;
    }

    public void setCategory(ProjectCategory category) {
        this.category = category;
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
        Project project = (Project) o;
        return Objects.equals(id, project.id) && 
               Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", category=" + category +
                ", isFeatured=" + isFeatured +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}

