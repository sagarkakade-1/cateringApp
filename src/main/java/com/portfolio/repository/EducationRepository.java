package com.portfolio.repository;

import com.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Education entity operations.
 * Demonstrates Spring Data JPA query methods and custom queries.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    /**
     * Find education records by portfolio ID
     */
    List<Education> findByPortfolioId(Long portfolioId);

    /**
     * Find education records by portfolio ID ordered by display order
     */
    List<Education> findByPortfolioIdOrderByDisplayOrderAsc(Long portfolioId);

    /**
     * Find current education (where endDate is null or isCurrent is true)
     */
    @Query("SELECT e FROM Education e WHERE e.portfolio.id = :portfolioId AND (e.endDate IS NULL OR e.isCurrent = true)")
    List<Education> findCurrentEducationByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Find education by institution name (case insensitive)
     */
    List<Education> findByInstitutionContainingIgnoreCase(String institution);

    /**
     * Find education by degree (case insensitive)
     */
    List<Education> findByDegreeContainingIgnoreCase(String degree);

    /**
     * Find education by field of study (case insensitive)
     */
    List<Education> findByFieldOfStudyContainingIgnoreCase(String fieldOfStudy);

    /**
     * Find education by degree type
     */
    List<Education> findByDegreeType(Education.DegreeType degreeType);

    /**
     * Find education that started after a specific date
     */
    List<Education> findByStartDateAfter(LocalDate date);

    /**
     * Find education by location (case insensitive)
     */
    List<Education> findByLocationContainingIgnoreCase(String location);

    /**
     * Find education with high GPA (above 3.5)
     */
    @Query("SELECT e FROM Education e WHERE e.portfolio.id = :portfolioId AND e.gpa >= 3.5")
    List<Education> findHighGpaEducationByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Find completed education (not current)
     */
    @Query("SELECT e FROM Education e WHERE e.portfolio.id = :portfolioId AND e.endDate IS NOT NULL AND e.isCurrent = false")
    List<Education> findCompletedEducationByPortfolioId(@Param("portfolioId") Long portfolioId);

    /**
     * Count education records by portfolio ID
     */
    long countByPortfolioId(Long portfolioId);

    /**
     * Find education by GPA range
     */
    @Query("SELECT e FROM Education e WHERE e.portfolio.id = :portfolioId AND e.gpa BETWEEN :minGpa AND :maxGpa")
    List<Education> findByPortfolioIdAndGpaBetween(@Param("portfolioId") Long portfolioId, 
                                                  @Param("minGpa") Double minGpa, 
                                                  @Param("maxGpa") Double maxGpa);

    /**
     * Find highest degree by portfolio ID
     */
    @Query("SELECT e FROM Education e WHERE e.portfolio.id = :portfolioId " +
           "ORDER BY CASE e.degreeType " +
           "WHEN 'DOCTORATE' THEN 7 " +
           "WHEN 'MASTER' THEN 6 " +
           "WHEN 'BACHELOR' THEN 5 " +
           "WHEN 'ASSOCIATE' THEN 4 " +
           "WHEN 'DIPLOMA' THEN 3 " +
           "WHEN 'CERTIFICATE' THEN 2 " +
           "WHEN 'HIGH_SCHOOL' THEN 1 " +
           "ELSE 0 END DESC")
    List<Education> findHighestDegreeByPortfolioId(@Param("portfolioId") Long portfolioId);
}
