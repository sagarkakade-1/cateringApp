package com.portfolio.controller;

import com.portfolio.dto.PortfolioDTO;
import com.portfolio.service.PortfolioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Portfolio operations.
 * Demonstrates Spring Boot REST API best practices, validation, and proper HTTP status codes.
 */
@RestController
@RequestMapping("/api/portfolios")
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /**
     * Get all active portfolios
     * GET /api/portfolios
     */
    @GetMapping
    public ResponseEntity<List<PortfolioDTO>> getAllPortfolios() {
        List<PortfolioDTO> portfolios = portfolioService.getAllActivePortfolios();
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Get portfolio by ID
     * GET /api/portfolios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDTO> getPortfolioById(@PathVariable @Min(1) Long id) {
        Optional<PortfolioDTO> portfolio = portfolioService.getPortfolioById(id);
        return portfolio
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get portfolio by email
     * GET /api/portfolios/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<PortfolioDTO> getPortfolioByEmail(@PathVariable @Email String email) {
        Optional<PortfolioDTO> portfolio = portfolioService.getPortfolioByEmail(email);
        return portfolio
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Search portfolios by term
     * GET /api/portfolios/search?q={searchTerm}
     */
    @GetMapping("/search")
    public ResponseEntity<List<PortfolioDTO>> searchPortfolios(
            @RequestParam(value = "q", required = false) String searchTerm) {
        List<PortfolioDTO> portfolios = portfolioService.searchPortfolios(searchTerm);
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Get portfolios with featured projects
     * GET /api/portfolios/featured-projects
     */
    @GetMapping("/featured-projects")
    public ResponseEntity<List<PortfolioDTO>> getPortfoliosWithFeaturedProjects() {
        List<PortfolioDTO> portfolios = portfolioService.getPortfoliosWithFeaturedProjects();
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Get portfolios by skill
     * GET /api/portfolios/skill/{skillName}?minProficiency={level}
     */
    @GetMapping("/skill/{skillName}")
    public ResponseEntity<List<PortfolioDTO>> getPortfoliosBySkill(
            @PathVariable String skillName,
            @RequestParam(value = "minProficiency", required = false) 
            @Min(value = 1, message = "Minimum proficiency must be at least 1") Integer minProficiency) {
        List<PortfolioDTO> portfolios = portfolioService.getPortfoliosBySkill(skillName, minProficiency);
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Get complete profiles
     * GET /api/portfolios/complete
     */
    @GetMapping("/complete")
    public ResponseEntity<List<PortfolioDTO>> getCompleteProfiles() {
        List<PortfolioDTO> portfolios = portfolioService.getCompleteProfiles();
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Get recently updated portfolios
     * GET /api/portfolios/recent?days={days}
     */
    @GetMapping("/recent")
    public ResponseEntity<List<PortfolioDTO>> getRecentlyUpdatedPortfolios(
            @RequestParam(value = "days", defaultValue = "30") 
            @Min(value = 1, message = "Days must be at least 1") Integer days) {
        List<PortfolioDTO> portfolios = portfolioService.getRecentlyUpdatedPortfolios(days);
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Get portfolio statistics
     * GET /api/portfolios/{id}/statistics
     */
    @GetMapping("/{id}/statistics")
    public ResponseEntity<PortfolioDTO> getPortfolioStatistics(@PathVariable @Min(1) Long id) {
        Optional<PortfolioDTO> portfolio = portfolioService.getPortfolioStatistics(id);
        return portfolio
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create new portfolio
     * POST /api/portfolios
     */
    @PostMapping
    public ResponseEntity<PortfolioDTO> createPortfolio(@Valid @RequestBody PortfolioDTO portfolioDTO) {
        try {
            PortfolioDTO createdPortfolio = portfolioService.createPortfolio(portfolioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update existing portfolio
     * PUT /api/portfolios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDTO> updatePortfolio(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody PortfolioDTO portfolioDTO) {
        try {
            Optional<PortfolioDTO> updatedPortfolio = portfolioService.updatePortfolio(id, portfolioDTO);
            return updatedPortfolio
                    .map(p -> ResponseEntity.ok(p))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete portfolio (soft delete)
     * DELETE /api/portfolios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable @Min(1) Long id) {
        boolean deleted = portfolioService.deletePortfolio(id);
        return deleted ? 
                ResponseEntity.noContent().build() : 
                ResponseEntity.notFound().build();
    }

    /**
     * Health check endpoint
     * GET /api/portfolios/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Portfolio service is running");
    }
}

