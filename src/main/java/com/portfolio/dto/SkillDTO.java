package com.portfolio.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.entity.Skill;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Skill entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillDTO {

    private Long id;

    @NotBlank(message = "Skill name is required")
    @Size(min = 1, max = 100, message = "Skill name must be between 1 and 100 characters")
    private String name;

    private String description;

    @Min(value = 1, message = "Proficiency level must be at least 1")
    @Max(value = 10, message = "Proficiency level must not exceed 10")
    private Integer proficiencyLevel;

    private Skill.SkillCategory category;
    private Skill.SkillType skillType;
    private Integer yearsOfExperience;
    private Boolean isFeatured;
    private Boolean isActive;
    private Integer displayOrder;
    private String iconClass;
    private String colorCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Computed fields
    private String proficiencyDescription;
    private Double proficiencyPercentage;
    private Boolean isHighProficiency;
    private String categoryDisplayName;
    private String skillTypeDisplayName;

    // Portfolio reference (minimal)
    private Long portfolioId;
    private String portfolioOwnerName;

    // Constructors
    public SkillDTO() {}

    public SkillDTO(String name, Integer proficiencyLevel, Skill.SkillCategory category) {
        this.name = name;
        this.proficiencyLevel = proficiencyLevel;
        this.category = category;
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

    public Integer getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(Integer proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public Skill.SkillCategory getCategory() {
        return category;
    }

    public void setCategory(Skill.SkillCategory category) {
        this.category = category;
    }

    public Skill.SkillType getSkillType() {
        return skillType;
    }

    public void setSkillType(Skill.SkillType skillType) {
        this.skillType = skillType;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
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

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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

    public String getProficiencyDescription() {
        return proficiencyDescription;
    }

    public void setProficiencyDescription(String proficiencyDescription) {
        this.proficiencyDescription = proficiencyDescription;
    }

    public Double getProficiencyPercentage() {
        return proficiencyPercentage;
    }

    public void setProficiencyPercentage(Double proficiencyPercentage) {
        this.proficiencyPercentage = proficiencyPercentage;
    }

    public Boolean getIsHighProficiency() {
        return isHighProficiency;
    }

    public void setIsHighProficiency(Boolean isHighProficiency) {
        this.isHighProficiency = isHighProficiency;
    }

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public String getSkillTypeDisplayName() {
        return skillTypeDisplayName;
    }

    public void setSkillTypeDisplayName(String skillTypeDisplayName) {
        this.skillTypeDisplayName = skillTypeDisplayName;
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
        return "SkillDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", proficiencyLevel=" + proficiencyLevel +
                ", category=" + category +
                ", skillType=" + skillType +
                ", isFeatured=" + isFeatured +
                ", isActive=" + isActive +
                '}';
    }
}

