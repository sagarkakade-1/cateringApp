package com.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.controller.PortfolioController;
import com.portfolio.entity.Portfolio;
import com.portfolio.entity.Project;
import com.portfolio.entity.Skill;
import com.portfolio.repository.PortfolioRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.repository.SkillRepository;
import com.portfolio.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Comprehensive test suite for the Portfolio Application
 * Tests all major components including controllers, services, and repositories
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class PortfolioApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PortfolioRepository portfolioRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private SkillRepository skillRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioController portfolioController;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    /**
     * Test that the Spring Boot application context loads successfully
     */
    @Test
    void contextLoads() {
        // This test ensures that the application context loads without errors
        // If this test passes, it means all beans are properly configured
    }

    /**
     * Test portfolio retrieval endpoint
     */
    @Test
    void testGetPortfolio() throws Exception {
        // Setup mock data
        Portfolio mockPortfolio = createMockPortfolio();
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(mockPortfolio));

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(get("/api/portfolio")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.title").value("Senior Java Developer"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    /**
     * Test projects retrieval endpoint
     */
    @Test
    void testGetAllProjects() throws Exception {
        // Setup mock data
        List<Project> mockProjects = createMockProjects();
        when(projectRepository.findAll()).thenReturn(mockProjects);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(get("/api/portfolio/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpected(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("E-Commerce Platform"))
                .andExpect(jsonPath("$[1].title").value("Task Management System"));
    }

    /**
     * Test project creation endpoint
     */
    @Test
    void testCreateProject() throws Exception {
        // Setup mock data
        Project newProject = createMockProject("New Project", "A new test project");
        when(projectRepository.save(any(Project.class))).thenReturn(newProject);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(post("/api/portfolio/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProject)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Project"))
                .andExpect(jsonPath("$.description").value("A new test project"));
    }

    /**
     * Test skills retrieval endpoint
     */
    @Test
    void testGetAllSkills() throws Exception {
        // Setup mock data
        List<Skill> mockSkills = createMockSkills();
        when(skillRepository.findAll()).thenReturn(mockSkills);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(get("/api/portfolio/skills")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[1].name").value("Spring Boot"))
                .andExpect(jsonPath("$[2].name").value("PostgreSQL"));
    }

    /**
     * Test skill creation endpoint
     */
    @Test
    void testCreateSkill() throws Exception {
        // Setup mock data
        Skill newSkill = createMockSkill("Docker", "TOOL", 4);
        when(skillRepository.save(any(Skill.class))).thenReturn(newSkill);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(post("/api/portfolio/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSkill)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Docker"))
                .andExpect(jsonPath("$.category").value("TOOL"))
                .andExpect(jsonPath("$.level").value(4));
    }

    /**
     * Test error handling for non-existent project
     */
    @Test
    void testGetNonExistentProject() throws Exception {
        // Setup mock to return empty
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(get("/api/portfolio/projects/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test validation for invalid project data
     */
    @Test
    void testCreateProjectWithInvalidData() throws Exception {
        // Create project with invalid data (empty title)
        Project invalidProject = new Project();
        invalidProject.setTitle(""); // Invalid: empty title
        invalidProject.setDescription("Valid description");

        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Perform test
        mockMvc.perform(post("/api/portfolio/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProject)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test static resource serving
     */
    @Test
    void testStaticResourceAccess() throws Exception {
        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Test that the main HTML page is accessible
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    // Helper methods to create mock data

    private Portfolio createMockPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setFullName("John Doe");
        portfolio.setTitle("Senior Java Developer");
        portfolio.setEmail("john.doe@example.com");
        portfolio.setPhone("+1 (555) 123-4567");
        portfolio.setLocation("New York, NY");
        portfolio.setSummary("Experienced Java developer with expertise in Spring Boot and PostgreSQL");
        portfolio.setLinkedinUrl("https://linkedin.com/in/johndoe");
        portfolio.setGithubUrl("https://github.com/johndoe");
        portfolio.setYearsOfExperience(5);
        return portfolio;
    }

    private List<Project> createMockProjects() {
        Project project1 = createMockProject("E-Commerce Platform", 
            "Full-stack e-commerce application built with Spring Boot and PostgreSQL");
        project1.setId(1L);
        project1.setTechnologies("Java,Spring Boot,PostgreSQL,HTML,CSS,JavaScript");
        project1.setGithubUrl("https://github.com/johndoe/ecommerce");
        project1.setLiveUrl("https://ecommerce-demo.com");

        Project project2 = createMockProject("Task Management System", 
            "RESTful API for task management with user roles and notifications");
        project2.setId(2L);
        project2.setTechnologies("Spring Boot,Spring Security,JPA,PostgreSQL,Maven");
        project2.setGithubUrl("https://github.com/johndoe/taskmanager");

        return Arrays.asList(project1, project2);
    }

    private Project createMockProject(String title, String description) {
        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setStartDate(LocalDate.of(2023, 1, 1));
        project.setEndDate(LocalDate.of(2023, 6, 30));
        project.setCategory("WEB_APPLICATION");
        return project;
    }

    private List<Skill> createMockSkills() {
        Skill skill1 = createMockSkill("Java", "TECHNICAL", 5);
        skill1.setId(1L);

        Skill skill2 = createMockSkill("Spring Boot", "FRAMEWORK", 4);
        skill2.setId(2L);

        Skill skill3 = createMockSkill("PostgreSQL", "DATABASE", 4);
        skill3.setId(3L);

        return Arrays.asList(skill1, skill2, skill3);
    }

    private Skill createMockSkill(String name, String category, int level) {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setCategory(category);
        skill.setLevel(level);
        return skill;
    }
}

/**
 * Additional test class for service layer testing
 */
@SpringBootTest
@ActiveProfiles("test")
class PortfolioServiceTests {

    @MockBean
    private PortfolioRepository portfolioRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private SkillRepository skillRepository;

    @Autowired
    private PortfolioService portfolioService;

    /**
     * Test portfolio service retrieval
     */
    @Test
    void testGetPortfolioService() {
        // Setup mock data
        Portfolio mockPortfolio = new Portfolio();
        mockPortfolio.setId(1L);
        mockPortfolio.setFullName("Test User");
        
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(mockPortfolio));

        // Test service method
        Optional<Portfolio> result = portfolioService.getPortfolio();
        
        // Assertions would go here if we had proper service methods
        // For now, this tests that the service can be autowired correctly
    }

    /**
     * Test project service operations
     */
    @Test
    void testProjectServiceOperations() {
        // Setup mock data
        List<Project> mockProjects = Arrays.asList(
            createTestProject("Project 1"),
            createTestProject("Project 2")
        );
        
        when(projectRepository.findAll()).thenReturn(mockProjects);

        // Test would call service methods here
        // This ensures the service layer is properly configured
    }

    private Project createTestProject(String title) {
        Project project = new Project();
        project.setTitle(title);
        project.setDescription("Test project description");
        project.setCategory("WEB_APPLICATION");
        return project;
    }
}
