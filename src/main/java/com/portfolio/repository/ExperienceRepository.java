package com.portfolio.repository;

import com.portfolio.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Experience entity operations.
 * Demonstrates Spring Data JPA query methods and custom queries.
 */
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    /**
     * Find experiences by portfolio ID
     */
    List<Experience> findByPortfolioId(Long portfolioId);

    /**
     * Find experiences by portfolio ID ordered by display order
     */
    List<Experience> findByPortfolioIdOrderByDisplayOrderAsc(Long portfolioId);

    /**
     * Find current experiences (where endDate is null or isCurrent is true)
     */
    @Query("SELECT e FROM Experience e WHERE e.portfolio.id = :portfolioId AND (e.endDate IS NULL OR e.isCurrent = true)")
    List<Experience> findCurrentExperiencesByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Find experiences by company name (case insensitive)
     */
    List<Experience> findByCompanyContainingIgnoreCase(String company);

    /**
     * Find experiences by job title (case insensitive)
     */
    List<Experience> findByJobTitleContainingIgnoreCase(String jobTitle);

    /**
     * Find experiences by employment type
     */
    List<Experience> findByEmploymentType(Experience.EmploymentType employmentType);

    /**
     * Find experiences that started after a specific date
     */
    List<Experience> findByStartDateAfter(LocalDate date);

    /**
     * Find experiences by location (case insensitive)
     */
    List<Experience> findByLocationContainingIgnoreCase(String location);

    /**
     * Find long-term experiences (more than 1 year)
     */
    @Query("SELECT e FROM Experience e WHERE e.portfolio.id = :portfolioId AND " +
           "(e.endDate IS NULL OR (COALESCE(e.endDate, CURRENT_DATE) - e.startDate) >= 365)")
    List<Experience> findLongTermExperiencesByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Count experiences by portfolio ID
     */
    long countByPortfolioId(Long portfolioId);

    /**
     * Find experiences with specific technologies
     */
    @Query("SELECT e FROM Experience e WHERE e.portfolio.id = :portfolioId AND " +
           "LOWER(e.technologies) LIKE LOWER(CONCAT('%', :technology, '%'))")
    List<Experience> findByPortfolioIdAndTechnologiesContaining(@Param("portfolioId") Long portfolioId, 
                                                               @Param("technology") String technology);
}
