package com.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.entity.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Project entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {

    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 200, message = "Project name must be between 2 and 200 characters")
    private String name;

    private String description;
    private String shortDescription;
    private String technologies;
    private List<String> technologyList;
    private String projectUrl;
    private String githubUrl;
    private String demoUrl;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean isFeatured;
    private Boolean isActive;
    private Integer displayOrder;
    private Project.ProjectStatus status;
    private Project.ProjectCategory category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Computed fields
    private String statusDisplayName;
    private String categoryDisplayName;
    private String durationString;
    private Long durationInDays;
    private Boolean hasValidUrls;
    private Boolean isCurrentlyActive;

    // Portfolio reference (minimal)
    private Long portfolioId;
    private String portfolioOwnerName;

    // Constructors
    public ProjectDTO() {}

    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<String> getTechnologyList() {
        return technologyList;
    }

    public void setTechnologyList(List<String> technologyList) {
        this.technologyList = technologyList;
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

    public Project.ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(Project.ProjectStatus status) {
        this.status = status;
    }

    public Project.ProjectCategory getCategory() {
        return category;
    }

    public void setCategory(Project.ProjectCategory category) {
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

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
    }

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public Long getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Long durationInDays) {
        this.durationInDays = durationInDays;
    }

    public Boolean getHasValidUrls() {
        return hasValidUrls;
    }

    public void setHasValidUrls(Boolean hasValidUrls) {
        this.hasValidUrls = hasValidUrls;
    }

    public Boolean getIsCurrentlyActive() {
        return isCurrentlyActive;
    }

    public void setIsCurrentlyActive(Boolean isCurrentlyActive) {
        this.isCurrentlyActive = isCurrentlyActive;
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
        return "ProjectDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", category=" + category +
                ", isFeatured=" + isFeatured +
                ", isActive=" + isActive +
                '}';
    }
}

