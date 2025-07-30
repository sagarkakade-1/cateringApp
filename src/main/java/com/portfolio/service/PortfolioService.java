package com.portfolio.service;

import com.portfolio.dto.PortfolioDTO;
import com.portfolio.dto.ProjectDTO;
import com.portfolio.dto.SkillDTO;
import com.portfolio.dto.ExperienceDTO;
import com.portfolio.dto.EducationDTO;
import com.portfolio.entity.Portfolio;
import com.portfolio.entity.Project;
import com.portfolio.entity.Skill;
import com.portfolio.entity.Experience;
import com.portfolio.entity.Education;
import com.portfolio.repository.PortfolioRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Comparator;

/**
 * Service class for Portfolio operations.
 * Demonstrates extensive use of Java 8+ features including Streams, Optional, Lambda expressions,
 * Method references, and functional programming concepts.
 */
@Service
@Transactional
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository,
                           ProjectRepository projectRepository,
                           SkillRepository skillRepository) {
        this.portfolioRepository = portfolioRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    /**
     * Get all active portfolios with Java 8 Streams
     */
    @Transactional(readOnly = true)
    public List<PortfolioDTO> getAllActivePortfolios() {
        return portfolioRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(PortfolioDTO::getUpdatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Get portfolio by ID with Optional handling
     */
    @Transactional(readOnly = true)
    public Optional<PortfolioDTO> getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .filter(Portfolio::getIsActive)
                .map(this::convertToDetailedDTO);
    }

    /**
     * Get portfolio by email with Optional chaining
     */
    @Transactional(readOnly = true)
    public Optional<PortfolioDTO> getPortfolioByEmail(String email) {
        return portfolioRepository.findByEmailIgnoreCase(email)
                .filter(Portfolio::getIsActive)
                .map(this::convertToDetailedDTO);
    }

    /**
     * Search portfolios by various criteria using Streams
     */
    @Transactional(readOnly = true)
    public List<PortfolioDTO> searchPortfolios(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllActivePortfolios();
        }

        String term = searchTerm.toLowerCase().trim();
        
        return portfolioRepository.findByIsActiveTrue()
                .stream()
                .filter(portfolio -> matchesSearchCriteria(portfolio, term))
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(PortfolioDTO::getFullName))
                .collect(Collectors.toList());
    }

    /**
     * Get portfolios with featured projects using method references
     */
    @Transactional(readOnly = true)
    public List<PortfolioDTO> getPortfoliosWithFeaturedProjects() {
        return portfolioRepository.findPortfoliosWithFeaturedProjects()
                .stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(PortfolioDTO::getUpdatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Get portfolios by skill with Stream filtering
     */
    @Transactional(readOnly = true)
    public List<PortfolioDTO> getPortfoliosBySkill(String skillName, Integer minProficiency) {
        return portfolioRepository.findPortfoliosBySkillName(skillName)
                .stream()
                .filter(portfolio -> hasSkillWithMinProficiency(portfolio, skillName, minProficiency))
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(this::getMaxSkillProficiency).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Get portfolio statistics using Optional and Streams
     */
    @Transactional(readOnly = true)
    public Optional<PortfolioDTO> getPortfolioStatistics(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .filter(Portfolio::getIsActive)
                .map(this::enrichWithStatistics);
    }

    /**
     * Create new portfolio with validation
     */
    public PortfolioDTO createPortfolio(PortfolioDTO portfolioDTO) {
        validatePortfolioForCreation(portfolioDTO);
        
        Portfolio portfolio = convertToEntity(portfolioDTO);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        
        return convertToDetailedDTO(savedPortfolio);
    }

    /**
     * Update existing portfolio with Optional handling
     */
    public Optional<PortfolioDTO> updatePortfolio(Long id, PortfolioDTO portfolioDTO) {
        return portfolioRepository.findById(id)
                .filter(Portfolio::getIsActive)
                .map(existingPortfolio -> {
                    validatePortfolioForUpdate(portfolioDTO, id);
                    updatePortfolioFields(existingPortfolio, portfolioDTO);
                    Portfolio savedPortfolio = portfolioRepository.save(existingPortfolio);
                    return convertToDetailedDTO(savedPortfolio);
                });
    }

    /**
     * Soft delete portfolio
     */
    public boolean deletePortfolio(Long id) {
        return portfolioRepository.findById(id)
                .map(portfolio -> {
                    portfolio.setIsActive(false);
                    portfolio.setUpdatedAt(LocalDateTime.now());
                    portfolioRepository.save(portfolio);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Get complete profiles using Stream filtering
     */
    @Transactional(readOnly = true)
    public List<PortfolioDTO> getCompleteProfiles() {
        return portfolioRepository.findCompleteProfiles()
                .stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(PortfolioDTO::getUpdatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Get recently updated portfolios with time-based filtering
     */
    @Transactional(readOnly = true)
    public List<PortfolioDTO> getRecentlyUpdatedPortfolios(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        
        return portfolioRepository.findByUpdatedAtAfter(cutoffDate)
                .stream()
                .filter(Portfolio::getIsActive)
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(PortfolioDTO::getUpdatedAt).reversed())
                .collect(Collectors.toList());
    }

    // Private helper methods demonstrating Java 8 features

    /**
     * Convert Portfolio entity to DTO using method references and Optional
     */
    private PortfolioDTO convertToDTO(Portfolio portfolio) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getId());
        dto.setFullName(portfolio.getFullName());
        dto.setTitle(portfolio.getTitle());
        dto.setSummary(portfolio.getSummary());
        dto.setEmail(portfolio.getEmail());
        dto.setPhone(portfolio.getPhone());
        dto.setLocation(portfolio.getLocation());
        dto.setLinkedinUrl(portfolio.getLinkedinUrl());
        dto.setGithubUrl(portfolio.getGithubUrl());
        dto.setWebsiteUrl(portfolio.getWebsiteUrl());
        dto.setProfileImageUrl(portfolio.getProfileImageUrl().orElse(null));
        dto.setYearsOfExperience(portfolio.getYearsOfExperience());
        dto.setIsActive(portfolio.getIsActive());
        dto.setCreatedAt(portfolio.getCreatedAt());
        dto.setUpdatedAt(portfolio.getUpdatedAt());
        
        return dto;
    }

    /**
     * Convert Portfolio entity to detailed DTO with related entities
     */
    private PortfolioDTO convertToDetailedDTO(Portfolio portfolio) {
        PortfolioDTO dto = convertToDTO(portfolio);
        
        // Convert related entities using Streams
        dto.setProjects(portfolio.getProjects()
                .stream()
                .filter(Project::getIsActive)
                .map(this::convertProjectToDTO)
                .sorted(Comparator.comparing(ProjectDTO::getDisplayOrder)
                        .thenComparing(ProjectDTO::getCreatedAt).reversed())
                .collect(Collectors.toList()));
        
        dto.setSkills(portfolio.getSkills()
                .stream()
                .filter(Skill::getIsActive)
                .map(this::convertSkillToDTO)
                .sorted(Comparator.comparing(SkillDTO::getProficiencyLevel).reversed()
                        .thenComparing(SkillDTO::getName))
                .collect(Collectors.toList()));
        
        dto.setExperiences(portfolio.getExperiences()
                .stream()
                .filter(Experience::getIsActive)
                .map(this::convertExperienceToDTO)
                .sorted(Comparator.comparing(ExperienceDTO::getStartDate).reversed())
                .collect(Collectors.toList()));
        
        dto.setEducations(portfolio.getEducations()
                .stream()
                .filter(Education::getIsActive)
                .map(this::convertEducationToDTO)
                .sorted(Comparator.comparing(EducationDTO::getStartDate).reversed())
                .collect(Collectors.toList()));
        
        return enrichWithStatistics(portfolio, dto);
    }

    /**
     * Convert Project entity to DTO with computed fields
     */
    private ProjectDTO convertProjectToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setShortDescription(project.getShortDescription());
        dto.setTechnologies(project.getTechnologies());
        dto.setTechnologyList(project.getTechnologyList());
        dto.setProjectUrl(project.getProjectUrl());
        dto.setGithubUrl(project.getGithubUrl());
        dto.setDemoUrl(project.getDemoUrl());
        dto.setImageUrl(project.getImageUrl());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setIsFeatured(project.getIsFeatured());
        dto.setIsActive(project.getIsActive());
        dto.setDisplayOrder(project.getDisplayOrder());
        dto.setStatus(project.getStatus());
        dto.setCategory(project.getCategory());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        
        // Set computed fields
        dto.setStatusDisplayName(project.getStatusDisplayName());
        dto.setCategoryDisplayName(project.getCategoryDisplayName());
        dto.setDurationInDays(project.getDurationInDays());
        dto.setHasValidUrls(project.hasValidUrls());
        dto.setIsCurrentlyActive(project.isCurrentlyActive());
        dto.setPortfolioId(project.getPortfolio().getId());
        dto.setPortfolioOwnerName(project.getPortfolio().getFullName());
        
        return dto;
    }

    /**
     * Convert Skill entity to DTO with computed fields
     */
    private SkillDTO convertSkillToDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setDescription(skill.getDescription());
        dto.setProficiencyLevel(skill.getProficiencyLevel());
        dto.setCategory(skill.getCategory());
        dto.setSkillType(skill.getSkillType());
        dto.setYearsOfExperience(skill.getYearsOfExperience());
        dto.setIsFeatured(skill.getIsFeatured());
        dto.setIsActive(skill.getIsActive());
        dto.setDisplayOrder(skill.getDisplayOrder());
        dto.setIconClass(skill.getIconClass());
        dto.setColorCode(skill.getColorCode());
        dto.setCreatedAt(skill.getCreatedAt());
        dto.setUpdatedAt(skill.getUpdatedAt());
        
        // Set computed fields
        dto.setProficiencyDescription(skill.getProficiencyDescription());
        dto.setProficiencyPercentage(skill.getProficiencyPercentage());
        dto.setIsHighProficiency(skill.isHighProficiency());
        dto.setCategoryDisplayName(skill.getCategoryDisplayName());
        dto.setSkillTypeDisplayName(skill.getSkillTypeDisplayName());
        dto.setPortfolioId(skill.getPortfolio().getId());
        dto.setPortfolioOwnerName(skill.getPortfolio().getFullName());
        
        return dto;
    }

    /**
     * Convert Experience entity to DTO with computed fields
     */
    private ExperienceDTO convertExperienceToDTO(Experience experience) {
        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(experience.getId());
        dto.setJobTitle(experience.getJobTitle());
        dto.setCompanyName(experience.getCompanyName());
        dto.setCompanyUrl(experience.getCompanyUrl());
        dto.setLocation(experience.getLocation());
        dto.setDescription(experience.getDescription());
        dto.setResponsibilities(experience.getResponsibilities());
        dto.setAchievements(experience.getAchievements());
        dto.setTechnologiesUsed(experience.getTechnologiesUsed());
        dto.setStartDate(experience.getStartDate());
        dto.setEndDate(experience.getEndDate());
        dto.setIsCurrent(experience.getIsCurrent());
        dto.setEmploymentType(experience.getEmploymentType());
        dto.setIsFeatured(experience.getIsFeatured());
        dto.setIsActive(experience.getIsActive());
        dto.setDisplayOrder(experience.getDisplayOrder());
        dto.setCreatedAt(experience.getCreatedAt());
        dto.setUpdatedAt(experience.getUpdatedAt());
        
        // Set computed fields
        dto.setDurationString(experience.getDurationString());
        dto.setTotalMonths(experience.getTotalMonths());
        dto.setDateRangeString(experience.getDateRangeString());
        dto.setEmploymentTypeDisplayName(experience.getEmploymentTypeDisplayName());
        dto.setIsLongTerm(experience.isLongTerm());
        dto.setIsRecent(experience.isRecent());
        dto.setPortfolioId(experience.getPortfolio().getId());
        dto.setPortfolioOwnerName(experience.getPortfolio().getFullName());
        
        return dto;
    }

    /**
     * Convert Education entity to DTO with computed fields
     */
    private EducationDTO convertEducationToDTO(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setId(education.getId());
        dto.setDegree(education.getDegree());
        dto.setInstitution(education.getInstitution());
        dto.setFieldOfStudy(education.getFieldOfStudy());
        dto.setLocation(education.getLocation());
        dto.setDescription(education.getDescription());
        dto.setGpa(education.getGpa());
        dto.setMaxGpa(education.getMaxGpa());
        dto.setStartDate(education.getStartDate());
        dto.setEndDate(education.getEndDate());
        dto.setIsCurrent(education.getIsCurrent());
        dto.setDegreeType(education.getDegreeType());
        dto.setStatus(education.getStatus());
        dto.setHonors(education.getHonors());
        dto.setRelevantCoursework(education.getRelevantCoursework());
        dto.setActivities(education.getActivities());
        dto.setIsFeatured(education.getIsFeatured());
        dto.setIsActive(education.getIsActive());
        dto.setDisplayOrder(education.getDisplayOrder());
        dto.setCreatedAt(education.getCreatedAt());
        dto.setUpdatedAt(education.getUpdatedAt());
        
        // Set computed fields
        dto.setDateRangeString(education.getDateRangeString());
        dto.setGpaString(education.getGpaString());
        dto.setHasHighGpa(education.hasHighGpa());
        dto.setDegreeTypeDisplayName(education.getDegreeTypeDisplayName());
        dto.setStatusDisplayName(education.getStatusDisplayName());
        dto.setFullDegreeTitle(education.getFullDegreeTitle());
        dto.setIsRecent(education.isRecent());
        dto.setDurationInYears(education.getDurationInYears());
        dto.setPortfolioId(education.getPortfolio().getId());
        dto.setPortfolioOwnerName(education.getPortfolio().getFullName());
        
        return dto;
    }

    /**
     * Convert DTO to entity for creation
     */
    private Portfolio convertToEntity(PortfolioDTO dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setFullName(dto.getFullName());
        portfolio.setTitle(dto.getTitle());
        portfolio.setSummary(dto.getSummary());
        portfolio.setEmail(dto.getEmail());
        portfolio.setPhone(dto.getPhone());
        portfolio.setLocation(dto.getLocation());
        portfolio.setLinkedinUrl(dto.getLinkedinUrl());
        portfolio.setGithubUrl(dto.getGithubUrl());
        portfolio.setWebsiteUrl(dto.getWebsiteUrl());
        portfolio.setProfileImageUrl(dto.getProfileImageUrl());
        portfolio.setYearsOfExperience(dto.getYearsOfExperience());
        portfolio.setIsActive(Optional.ofNullable(dto.getIsActive()).orElse(true));
        
        return portfolio;
    }

    /**
     * Update portfolio fields from DTO
     */
    private void updatePortfolioFields(Portfolio portfolio, PortfolioDTO dto) {
        Optional.ofNullable(dto.getFullName()).ifPresent(portfolio::setFullName);
        Optional.ofNullable(dto.getTitle()).ifPresent(portfolio::setTitle);
        Optional.ofNullable(dto.getSummary()).ifPresent(portfolio::setSummary);
        Optional.ofNullable(dto.getEmail()).ifPresent(portfolio::setEmail);
        Optional.ofNullable(dto.getPhone()).ifPresent(portfolio::setPhone);
        Optional.ofNullable(dto.getLocation()).ifPresent(portfolio::setLocation);
        Optional.ofNullable(dto.getLinkedinUrl()).ifPresent(portfolio::setLinkedinUrl);
        Optional.ofNullable(dto.getGithubUrl()).ifPresent(portfolio::setGithubUrl);
        Optional.ofNullable(dto.getWebsiteUrl()).ifPresent(portfolio::setWebsiteUrl);
        Optional.ofNullable(dto.getProfileImageUrl()).ifPresent(portfolio::setProfileImageUrl);
        Optional.ofNullable(dto.getYearsOfExperience()).ifPresent(portfolio::setYearsOfExperience);
        Optional.ofNullable(dto.getIsActive()).ifPresent(portfolio::setIsActive);
    }

    /**
     * Enrich portfolio with statistics using Streams and aggregations
     */
    private PortfolioDTO enrichWithStatistics(Portfolio portfolio, PortfolioDTO dto) {
        // Project statistics
        List<Project> activeProjects = portfolio.getProjects()
                .stream()
                .filter(Project::getIsActive)
                .collect(Collectors.toList());
        
        dto.setTotalProjects((long) activeProjects.size());
        dto.setFeaturedProjects(activeProjects.stream()
                .filter(Project::getIsFeatured)
                .count());
        
        // Skill statistics
        List<Skill> activeSkills = portfolio.getSkills()
                .stream()
                .filter(Skill::getIsActive)
                .collect(Collectors.toList());
        
        dto.setTotalSkills((long) activeSkills.size());
        dto.setExpertSkills(activeSkills.stream()
                .filter(Skill::isHighProficiency)
                .count());
        
        dto.setAverageSkillProficiency(activeSkills.stream()
                .mapToInt(Skill::getProficiencyLevel)
                .average()
                .orElse(0.0));
        
        return dto;
    }

    /**
     * Enrich portfolio with statistics (overloaded method)
     */
    private PortfolioDTO enrichWithStatistics(Portfolio portfolio) {
        PortfolioDTO dto = convertToDTO(portfolio);
        return enrichWithStatistics(portfolio, dto);
    }

    /**
     * Check if portfolio matches search criteria using Streams
     */
    private boolean matchesSearchCriteria(Portfolio portfolio, String term) {
        return Stream.of(
                portfolio.getFullName(),
                portfolio.getTitle(),
                portfolio.getSummary(),
                portfolio.getLocation()
        )
        .filter(java.util.Objects::nonNull)
        .anyMatch(field -> field.toLowerCase().contains(term));
    }

    /**
     * Check if portfolio has skill with minimum proficiency
     */
    private boolean hasSkillWithMinProficiency(Portfolio portfolio, String skillName, Integer minProficiency) {
        if (minProficiency == null) return true;
        
        return portfolio.getSkills()
                .stream()
                .filter(Skill::getIsActive)
                .filter(skill -> skill.getName().toLowerCase().contains(skillName.toLowerCase()))
                .anyMatch(skill -> skill.getProficiencyLevel() >= minProficiency);
    }

    /**
     * Get maximum skill proficiency for sorting
     */
    private Integer getMaxSkillProficiency(Portfolio portfolio) {
        return portfolio.getSkills()
                .stream()
                .filter(Skill::getIsActive)
                .mapToInt(Skill::getProficiencyLevel)
                .max()
                .orElse(0);
    }

    /**
     * Validate portfolio for creation
     */
    private void validatePortfolioForCreation(PortfolioDTO dto) {
        if (portfolioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }
    }

    /**
     * Validate portfolio for update
     */
    private void validatePortfolioForUpdate(PortfolioDTO dto, Long portfolioId) {
        if (portfolioRepository.existsByEmailAndIdNot(dto.getEmail(), portfolioId)) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }
    }
}

