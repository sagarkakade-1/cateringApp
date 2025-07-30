package com.portfolio.repository;

import com.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Portfolio entity.
 * Demonstrates Spring Data JPA features and custom query methods.
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    /**
     * Find portfolio by email address (unique constraint)
     */
    Optional<Portfolio> findByEmail(String email);

    /**
     * Find portfolio by email address (case insensitive)
     */
    Optional<Portfolio> findByEmailIgnoreCase(String email);

    /**
     * Find all active portfolios
     */
    List<Portfolio> findByIsActiveTrue();

    /**
     * Find portfolios by full name containing (case insensitive)
     */
    List<Portfolio> findByFullNameContainingIgnoreCase(String name);

    /**
     * Find portfolios by title containing (case insensitive)
     */
    List<Portfolio> findByTitleContainingIgnoreCase(String title);

    /**
     * Find portfolios by location containing (case insensitive)
     */
    List<Portfolio> findByLocationContainingIgnoreCase(String location);

    /**
     * Find portfolios with minimum years of experience
     */
    List<Portfolio> findByYearsOfExperienceGreaterThanEqual(Integer minYears);

    /**
     * Find portfolios created after a specific date
     */
    List<Portfolio> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find portfolios updated after a specific date
     */
    List<Portfolio> findByUpdatedAtAfter(LocalDateTime date);

    /**
     * Custom query to find portfolios with featured projects
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "JOIN p.projects proj " +
           "WHERE proj.isFeatured = true AND proj.isActive = true AND p.isActive = true")
    List<Portfolio> findPortfoliosWithFeaturedProjects();

    /**
     * Custom query to find portfolios with specific skill
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "JOIN p.skills s " +
           "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :skillName, '%')) " +
           "AND s.isActive = true AND p.isActive = true")
    List<Portfolio> findPortfoliosBySkillName(@Param("skillName") String skillName);

    /**
     * Custom query to find portfolios with high proficiency in specific skill category
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "JOIN p.skills s " +
           "WHERE s.category = :category " +
           "AND s.proficiencyLevel >= :minProficiency " +
           "AND s.isActive = true AND p.isActive = true")
    List<Portfolio> findPortfoliosBySkillCategoryAndProficiency(
            @Param("category") com.portfolio.entity.Skill.SkillCategory category,
            @Param("minProficiency") Integer minProficiency);

    /**
     * Custom query to find portfolios with recent experience at specific company
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "JOIN p.experiences e " +
           "WHERE LOWER(e.companyName) LIKE LOWER(CONCAT('%', :companyName, '%')) " +
           "AND e.isActive = true AND p.isActive = true " +
           "ORDER BY e.startDate DESC")
    List<Portfolio> findPortfoliosByCompanyName(@Param("companyName") String companyName);

    /**
     * Custom query to find portfolios with education from specific institution
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "JOIN p.educations edu " +
           "WHERE LOWER(edu.institution) LIKE LOWER(CONCAT('%', :institutionName, '%')) " +
           "AND edu.isActive = true AND p.isActive = true")
    List<Portfolio> findPortfoliosByEducationInstitution(@Param("institutionName") String institutionName);

    /**
     * Custom query to count active projects for a portfolio
     */
    @Query("SELECT COUNT(proj) FROM Project proj " +
           "WHERE proj.portfolio.id = :portfolioId " +
           "AND proj.isActive = true")
    Long countActiveProjectsByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to count featured projects for a portfolio
     */
    @Query("SELECT COUNT(proj) FROM Project proj " +
           "WHERE proj.portfolio.id = :portfolioId " +
           "AND proj.isFeatured = true AND proj.isActive = true")
    Long countFeaturedProjectsByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to get portfolio statistics
     */
    @Query("SELECT p, " +
           "(SELECT COUNT(proj) FROM Project proj WHERE proj.portfolio = p AND proj.isActive = true) as projectCount, " +
           "(SELECT COUNT(s) FROM Skill s WHERE s.portfolio = p AND s.isActive = true) as skillCount, " +
           "(SELECT COUNT(e) FROM Experience e WHERE e.portfolio = p AND e.isActive = true) as experienceCount " +
           "FROM Portfolio p " +
           "WHERE p.id = :portfolioId AND p.isActive = true")
    Optional<Object[]> getPortfolioStatistics(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to find portfolios with projects using specific technology
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "JOIN p.projects proj " +
           "WHERE LOWER(proj.technologies) LIKE LOWER(CONCAT('%', :technology, '%')) " +
           "AND proj.isActive = true AND p.isActive = true")
    List<Portfolio> findPortfoliosByProjectTechnology(@Param("technology") String technology);

    /**
     * Custom query to find recently updated portfolios
     */
    @Query("SELECT p FROM Portfolio p " +
           "WHERE p.isActive = true " +
           "ORDER BY p.updatedAt DESC")
    List<Portfolio> findRecentlyUpdatedPortfolios();

    /**
     * Custom query to find portfolios with complete profiles
     * (having at least one project, skill, and experience)
     */
    @Query("SELECT DISTINCT p FROM Portfolio p " +
           "WHERE p.isActive = true " +
           "AND EXISTS (SELECT 1 FROM Project proj WHERE proj.portfolio = p AND proj.isActive = true) " +
           "AND EXISTS (SELECT 1 FROM Skill s WHERE s.portfolio = p AND s.isActive = true) " +
           "AND EXISTS (SELECT 1 FROM Experience e WHERE e.portfolio = p AND e.isActive = true)")
    List<Portfolio> findCompleteProfiles();

    /**
     * Check if email exists (for validation)
     */
    boolean existsByEmail(String email);

    /**
     * Check if email exists excluding specific portfolio ID (for updates)
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM Portfolio p " +
           "WHERE p.email = :email AND p.id != :portfolioId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("portfolioId") Long portfolioId);
}

