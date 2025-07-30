package com.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Skill entity representing technical and soft skills in the portfolio.
 * Demonstrates JPA annotations, validation, and enum usage.
 */
@Entity
@Table(name = "skills", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"portfolio_id", "name"})
})
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Size(min = 1, max = 100, message = "Skill name must be between 1 and 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Min(value = 1, message = "Proficiency level must be at least 1")
    @Max(value = 10, message = "Proficiency level must not exceed 10")
    @Column(name = "proficiency_level", nullable = false)
    private Integer proficiencyLevel = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private SkillCategory category = SkillCategory.TECHNICAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_type", nullable = false)
    private SkillType skillType = SkillType.PROGRAMMING_LANGUAGE;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "icon_class")
    private String iconClass; // CSS class for skill icon

    @Column(name = "color_code")
    private String colorCode; // Hex color code for skill representation

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
    public enum SkillCategory {
        TECHNICAL, SOFT_SKILL, LANGUAGE, CERTIFICATION, TOOL, FRAMEWORK
    }

    public enum SkillType {
        PROGRAMMING_LANGUAGE, FRAMEWORK, DATABASE, CLOUD_PLATFORM, 
        DEVELOPMENT_TOOL, METHODOLOGY, SOFT_SKILL, CERTIFICATION,
        OPERATING_SYSTEM, VERSION_CONTROL, TESTING, DESIGN
    }

    // Constructors
    public Skill() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Skill(String name, Integer proficiencyLevel, SkillCategory category, Portfolio portfolio) {
        this();
        this.name = name;
        this.proficiencyLevel = proficiencyLevel;
        this.category = category;
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
    public String getProficiencyDescription() {
        return switch (proficiencyLevel) {
            case 1, 2 -> "Beginner";
            case 3, 4 -> "Basic";
            case 5, 6 -> "Intermediate";
            case 7, 8 -> "Advanced";
            case 9, 10 -> "Expert";
            default -> "Unknown";
        };
    }

    public double getProficiencyPercentage() {
        return (proficiencyLevel / 10.0) * 100;
    }

    public boolean isHighProficiency() {
        return proficiencyLevel >= 7;
    }

    public String getCategoryDisplayName() {
        return category.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    public String getSkillTypeDisplayName() {
        return skillType.name().replace("_", " ").toLowerCase()
            .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }

    public String getDefaultColorCode() {
        return switch (category) {
            case TECHNICAL -> "#007bff";
            case FRAMEWORK -> "#28a745";
            case DATABASE -> "#ffc107";
            case CLOUD_PLATFORM -> "#17a2b8";
            case SOFT_SKILL -> "#6f42c1";
            case CERTIFICATION -> "#fd7e14";
            default -> "#6c757d";
        };
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

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillType skillType) {
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
        return colorCode != null ? colorCode : getDefaultColorCode();
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
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) && 
               Objects.equals(name, skill.name) && 
               Objects.equals(portfolio, skill.portfolio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, portfolio);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", proficiencyLevel=" + proficiencyLevel +
                ", category=" + category +
                ", skillType=" + skillType +
                ", isFeatured=" + isFeatured +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}

