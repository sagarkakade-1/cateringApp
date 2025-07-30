package com.portfolio.repository;

import com.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Skill entity.
 * Demonstrates Spring Data JPA with complex queries and aggregations.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    /**
     * Find all active skills
     */
    List<Skill> findByIsActiveTrue();

    /**
     * Find all featured skills
     */
    List<Skill> findByIsFeaturedTrueAndIsActiveTrue();

    /**
     * Find skills by portfolio ID
     */
    List<Skill> findByPortfolioIdAndIsActiveTrue(Long portfolioId);

    /**
     * Find featured skills by portfolio ID
     */
    List<Skill> findByPortfolioIdAndIsFeaturedTrueAndIsActiveTrue(Long portfolioId);

    /**
     * Find skills by category
     */
    List<Skill> findByCategoryAndIsActiveTrue(Skill.SkillCategory category);

    /**
     * Find skills by skill type
     */
    List<Skill> findBySkillTypeAndIsActiveTrue(Skill.SkillType skillType);

    /**
     * Find skills by portfolio ID and category
     */
    List<Skill> findByPortfolioIdAndCategoryAndIsActiveTrue(Long portfolioId, Skill.SkillCategory category);

    /**
     * Find skills by portfolio ID and skill type
     */
    List<Skill> findByPortfolioIdAndSkillTypeAndIsActiveTrue(Long portfolioId, Skill.SkillType skillType);

    /**
     * Find skills by name containing (case insensitive)
     */
    List<Skill> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    /**
     * Find skills with minimum proficiency level
     */
    List<Skill> findByProficiencyLevelGreaterThanEqualAndIsActiveTrue(Integer minProficiency);

    /**
     * Find skills by portfolio ID with minimum proficiency level
     */
    List<Skill> findByPortfolioIdAndProficiencyLevelGreaterThanEqualAndIsActiveTrue(
            Long portfolioId, Integer minProficiency);

    /**
     * Find skills ordered by proficiency level (highest first)
     */
    List<Skill> findByPortfolioIdAndIsActiveTrueOrderByProficiencyLevelDesc(Long portfolioId);

    /**
     * Find skills ordered by display order
     */
    List<Skill> findByPortfolioIdAndIsActiveTrueOrderByDisplayOrderAsc(Long portfolioId);

    /**
     * Find skills by category ordered by proficiency level
     */
    List<Skill> findByPortfolioIdAndCategoryAndIsActiveTrueOrderByProficiencyLevelDesc(
            Long portfolioId, Skill.SkillCategory category);

    /**
     * Custom query to find high proficiency skills (7+ rating)
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.proficiencyLevel >= 7 " +
           "AND s.isActive = true " +
           "ORDER BY s.proficiencyLevel DESC, s.name ASC")
    List<Skill> findHighProficiencySkills(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to find skills by multiple categories
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.category IN :categories " +
           "AND s.isActive = true " +
           "ORDER BY s.category ASC, s.proficiencyLevel DESC")
    List<Skill> findByPortfolioAndCategories(@Param("portfolioId") Long portfolioId, 
                                           @Param("categories") List<Skill.SkillCategory> categories);

    /**
     * Custom query to get skill statistics by portfolio
     */
    @Query("SELECT " +
           "COUNT(s) as totalSkills, " +
           "AVG(s.proficiencyLevel) as averageProficiency, " +
           "MAX(s.proficiencyLevel) as maxProficiency, " +
           "SUM(CASE WHEN s.proficiencyLevel >= 7 THEN 1 ELSE 0 END) as expertSkills, " +
           "SUM(CASE WHEN s.isFeatured = true THEN 1 ELSE 0 END) as featuredSkills " +
           "FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId AND s.isActive = true")
    Optional<Object[]> getSkillStatisticsByPortfolio(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to get skill count by category for a portfolio
     */
    @Query("SELECT s.category, COUNT(s) " +
           "FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.isActive = true " +
           "GROUP BY s.category " +
           "ORDER BY COUNT(s) DESC")
    List<Object[]> getSkillCountByCategory(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to get average proficiency by category for a portfolio
     */
    @Query("SELECT s.category, AVG(s.proficiencyLevel) " +
           "FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.isActive = true " +
           "GROUP BY s.category " +
           "ORDER BY AVG(s.proficiencyLevel) DESC")
    List<Object[]> getAverageProficiencyByCategory(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to find skills with years of experience
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.yearsOfExperience IS NOT NULL " +
           "AND s.yearsOfExperience >= :minYears " +
           "AND s.isActive = true " +
           "ORDER BY s.yearsOfExperience DESC, s.proficiencyLevel DESC")
    List<Skill> findSkillsWithExperience(@Param("portfolioId") Long portfolioId, 
                                       @Param("minYears") Integer minYears);

    /**
     * Custom query to search skills by name or description
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.isActive = true " +
           "AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Skill> searchSkills(@Param("portfolioId") Long portfolioId, @Param("searchTerm") String searchTerm);

    /**
     * Custom query to find complementary skills (same category, different skill type)
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.category = :category " +
           "AND s.skillType != :excludeSkillType " +
           "AND s.isActive = true " +
           "ORDER BY s.proficiencyLevel DESC")
    List<Skill> findComplementarySkills(@Param("portfolioId") Long portfolioId, 
                                      @Param("category") Skill.SkillCategory category,
                                      @Param("excludeSkillType") Skill.SkillType excludeSkillType);

    /**
     * Custom query to find top skills by proficiency for display
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.isActive = true " +
           "ORDER BY s.isFeatured DESC, s.proficiencyLevel DESC, s.displayOrder ASC")
    List<Skill> findTopSkillsForDisplay(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to find skills that need improvement (low proficiency but high years of experience)
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.proficiencyLevel < 5 " +
           "AND s.yearsOfExperience >= 2 " +
           "AND s.isActive = true " +
           "ORDER BY s.yearsOfExperience DESC")
    List<Skill> findSkillsNeedingImprovement(@Param("portfolioId") Long portfolioId);

    /**
     * Custom query to find recently added skills
     */
    @Query("SELECT s FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.isActive = true " +
           "ORDER BY s.createdAt DESC")
    List<Skill> findRecentlyAddedSkills(@Param("portfolioId") Long portfolioId);

    /**
     * Count skills by portfolio ID
     */
    long countByPortfolioIdAndIsActiveTrue(Long portfolioId);

    /**
     * Count featured skills by portfolio ID
     */
    long countByPortfolioIdAndIsFeaturedTrueAndIsActiveTrue(Long portfolioId);

    /**
     * Count skills by category and portfolio ID
     */
    long countByPortfolioIdAndCategoryAndIsActiveTrue(Long portfolioId, Skill.SkillCategory category);

    /**
     * Count high proficiency skills (7+) by portfolio ID
     */
    @Query("SELECT COUNT(s) FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND s.proficiencyLevel >= 7 " +
           "AND s.isActive = true")
    long countHighProficiencySkills(@Param("portfolioId") Long portfolioId);

    /**
     * Check if skill name exists for a portfolio (for validation)
     */
    boolean existsByPortfolioIdAndNameIgnoreCaseAndIsActiveTrue(Long portfolioId, String name);

    /**
     * Check if skill name exists for a portfolio excluding specific skill ID (for updates)
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
           "FROM Skill s " +
           "WHERE s.portfolio.id = :portfolioId " +
           "AND LOWER(s.name) = LOWER(:name) " +
           "AND s.id != :skillId " +
           "AND s.isActive = true")
    boolean existsByPortfolioIdAndNameIgnoreCaseAndIdNotAndIsActiveTrue(
            @Param("portfolioId") Long portfolioId, 
            @Param("name") String name, 
            @Param("skillId") Long skillId);
}

