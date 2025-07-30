package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Project entity.
 * Demonstrates Spring Data JPA features with custom queries and method naming conventions.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find all active projects
     */
    List<Project> findByIsActiveTrue();

    /**
     * Find all featured projects
     */
    List<Project> findByIsFeaturedTrueAndIsActiveTrue();

    /**
     * Find projects by portfolio ID
     */
    List<Project> findByPortfolioIdAndIsActiveTrue(Long portfolioId);

    /**
     * Find featured projects by portfolio ID
     */
    List<Project> findByPortfolioIdAndIsFeaturedTrueAndIsActiveTrue(Long portfolioId);

    /**
     * Find projects by name containing (case insensitive)
     */
    List<Project> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    /**
     * Find projects by status
     */
    List<Project> findByStatusAndIsActiveTrue(Project.ProjectStatus status);

    /**
     * Find projects by category
     */
    List<Project> findByCategoryAndIsActiveTrue(Project.ProjectCategory category);

    /**
     * Find projects by portfolio ID and status
     */
    List<Project> findByPortfolioIdAndStatusAndIsActiveTrue(Long portfolioId, Project.ProjectStatus status);

    /**
     * Find projects by portfolio ID and category
     */
    List<Project> findByPortfolioIdAndCategoryAndIsActiveTrue(Long portfolioId, Project.ProjectCategory category);

    /**
     * Find projects started after a specific date
     */
    List<Project> findByStartDateAfterAndIsActiveTrue(LocalDate date);

    /**
     * Find projects completed within a date range
     */
    List<Project> findByEndDateBetweenAndIsActiveTrue(LocalDate startDate, LocalDate endDate);

    /**
     * Find projects ordered by display order
     */
    List<Project> findByPortfolioIdAndIsActiveTrueOrderByDisplayOrderAsc(Long portfolioId);

    /**
     * Find projects ordered by creation date (newest first)
     */
    List<Project> findByPortfolioIdAndIsActiveTrueOrderByCreatedAtDesc(Long portfolioId);

    /**
     * Custom query to find projects by technology
     */
    @Query("SELECT p FROM Project p " +
           "WHERE LOWER(p.technologies) LIKE LOWER(CONCAT('%', :technology, '%')) " +
           "AND p.isActive = true")
    List<Project> findByTechnology(@Param("technology") String technology);

    /**
     * Custom query to find projects with multiple technologies
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.isActive = true " +
           "AND (" +
           "LOWER(p.technologies) LIKE LOWER(CONCAT('%', :tech1, '%')) " +
           "OR LOWER(p.technologies) LIKE LOWER(CONCAT('%', :tech2, '%'))" +
           ")")
    List<Project> findByTechnologies(@Param("tech1") String tech1, @Param("tech2") String tech2);

    /**
     * Custom query to find projects with all required technologies
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.isActive = true " +
           "AND LOWER(p.technologies) LIKE LOWER(CONCAT('%', :tech1, '%')) " +
           "AND LOWER(p.technologies) LIKE LOWER(CONCAT('%', :tech2, '%'))")
    List<Project> findByAllTechnologies(@Param("tech1") String tech1, @Param("tech2") String tech2);

    /**
     * Custom query to find recent projects (within last N months)
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.isActive = true " +
           "AND (p.endDate IS NULL OR p.endDate >= :cutoffDate) " +
           "ORDER BY p.createdAt DESC")
    List<Project> findRecentProjects(@Param("cutoffDate") LocalDate cutoffDate);

    /**
     * Custom query to find long-running projects (duration > N days)
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.isActive = true " +
           "AND p.startDate IS NOT NULL " +
           "AND (p.endDate IS NULL OR " +
           "     FUNCTION('DATEDIFF', COALESCE(p.endDate, CURRENT_DATE), p.startDate) >= :minDays)")
    List<Project> findLongRunningProjects(@Param("minDays") int minDays);

    /**
     * Custom query to find projects with external links
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.isActive = true " +
           "AND (p.projectUrl IS NOT NULL OR p.githubUrl IS NOT NULL OR p.demoUrl IS NOT NULL)")
    List<Project> findProjectsWithLinks();

    /**
     * Custom query to find projects by portfolio and technology
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.portfolio.id = :portfolioId " +
           "AND LOWER(p.technologies) LIKE LOWER(CONCAT('%', :technology, '%')) " +
           "AND p.isActive = true " +
           "ORDER BY p.displayOrder ASC, p.createdAt DESC")
    List<Project> findByPortfolioAndTechnology(@Param("portfolioId") Long portfolioId, 
                                               @Param("technology") String technology);

    /**
     * Custom query to get project statistics by portfolio
     */
    @Query("SELECT " +
           "COUNT(p) as totalProjects, " +
           "SUM(CASE WHEN p.isFeatured = true THEN 1 ELSE 0 END) as featuredProjects, " +
           "SUM(CASE WHEN p.status = 'COMPLETED' THEN 1 ELSE 0 END) as completedProjects, " +
           "SUM(CASE WHEN p.status = 'IN_PROGRESS' THEN 1 ELSE 0 END) as inProgressProjects " +
           "FROM Project p " +
           "WHERE p.portfolio.id = :portfolioId AND p.isActive = true")
    Optional<Object[]> getProjectStatisticsByPortfolio(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to find projects by category with pagination-like limit
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.category = :category " +
           "AND p.isActive = true " +
           "ORDER BY p.isFeatured DESC, p.displayOrder ASC, p.createdAt DESC")
    List<Project> findTopProjectsByCategory(@Param("category") Project.ProjectCategory category);

    /**
     * Custom query to find projects with images
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.imageUrl IS NOT NULL " +
           "AND p.imageUrl != '' " +
           "AND p.isActive = true " +
           "ORDER BY p.isFeatured DESC, p.createdAt DESC")
    List<Project> findProjectsWithImages();

    /**
     * Custom query to search projects by name or description
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.isActive = true " +
           "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Project> searchProjects(@Param("searchTerm") String searchTerm);

    /**
     * Custom query to find projects by status and portfolio
     */
    @Query("SELECT p FROM Project p " +
           "WHERE p.portfolio.id = :portfolioId " +
           "AND p.status IN :statuses " +
           "AND p.isActive = true " +
           "ORDER BY p.displayOrder ASC, p.updatedAt DESC")
    List<Project> findByPortfolioAndStatuses(@Param("portfolioId") Long portfolioId, 
                                           @Param("statuses") List<Project.ProjectStatus> statuses);

    /**
     * Count projects by portfolio ID
     */
    long countByPortfolioIdAndIsActiveTrue(Long portfolioId);

    /**
     * Count featured projects by portfolio ID
     */
    long countByPortfolioIdAndIsFeaturedTrueAndIsActiveTrue(Long portfolioId);

    /**
     * Count projects by status and portfolio ID
     */
    long countByPortfolioIdAndStatusAndIsActiveTrue(Long portfolioId, Project.ProjectStatus status);

    /**
     * Check if project name exists for a portfolio (for validation)
     */
    boolean existsByPortfolioIdAndNameIgnoreCaseAndIsActiveTrue(Long portfolioId, String name);

    /**
     * Check if project name exists for a portfolio excluding specific project ID (for updates)
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM Project p " +
           "WHERE p.portfolio.id = :portfolioId " +
           "AND LOWER(p.name) = LOWER(:name) " +
           "AND p.id != :projectId " +
           "AND p.isActive = true")
    boolean existsByPortfolioIdAndNameIgnoreCaseAndIdNotAndIsActiveTrue(
            @Param("portfolioId") Long portfolioId, 
            @Param("name") String name, 
            @Param("projectId") Long projectId);
}

