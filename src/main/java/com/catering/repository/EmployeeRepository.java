package com.catering.repository;

import com.catering.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entity
 * Provides data access methods for employee management
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by employee code
     * @param employeeCode the employee code to search for
     * @return Optional containing the employee if found
     */
    Optional<Employee> findByEmployeeCode(String employeeCode);

    /**
     * Find employees by type
     * @param employeeType the employee type to search for
     * @return List of employees with the specified type
     */
    List<Employee> findByEmployeeType(Employee.EmployeeType employeeType);

    /**
     * Find all active employees
     * @return List of active employees
     */
    List<Employee> findByActiveTrue();

    /**
     * Find employees by name containing (case insensitive)
     * @param name the name to search for
     * @return List of employees whose name contains the search term
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Find employees by phone number
     * @param phone the phone number to search for
     * @return List of employees with the specified phone number
     */
    List<Employee> findByPhone(String phone);

    /**
     * Find employees by employee type and active status
     * @param employeeType the employee type
     * @param active the active status
     * @return List of employees matching the criteria
     */
    List<Employee> findByEmployeeTypeAndActive(Employee.EmployeeType employeeType, Boolean active);

    /**
     * Find employees hired after a specific date
     * @param date the hire date threshold
     * @return List of employees hired after the specified date
     */
    @Query("SELECT e FROM Employee e WHERE e.hireDate > :date")
    List<Employee> findEmployeesHiredAfter(@Param("date") LocalDate date);

    /**
     * Find employees hired between two dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List of employees hired between the specified dates
     */
    @Query("SELECT e FROM Employee e WHERE e.hireDate BETWEEN :startDate AND :endDate")
    List<Employee> findEmployeesHiredBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Find top performing employees by total orders served
     * @return List of employees ordered by total orders served (descending)
     */
    @Query("SELECT e FROM Employee e ORDER BY e.totalOrdersServed DESC")
    List<Employee> findTopPerformingEmployees();

    /**
     * Find employees with orders served greater than specified count
     * @param orderCount the minimum order count
     * @return List of employees with more orders served than specified
     */
    @Query("SELECT e FROM Employee e WHERE e.totalOrdersServed > :orderCount")
    List<Employee> findEmployeesWithOrdersGreaterThan(@Param("orderCount") Integer orderCount);

    /**
     * Count employees by type
     * @param employeeType the employee type
     * @return Count of employees with the specified type
     */
    long countByEmployeeType(Employee.EmployeeType employeeType);

    /**
     * Count active employees by type
     * @param employeeType the employee type
     * @param active the active status
     * @return Count of active employees with the specified type
     */
    long countByEmployeeTypeAndActive(Employee.EmployeeType employeeType, Boolean active);

    /**
     * Check if employee code exists
     * @param employeeCode the employee code to check
     * @return true if employee code exists, false otherwise
     */
    boolean existsByEmployeeCode(String employeeCode);

    /**
     * Find available employees for a specific date (not assigned to any order on that date)
     * @param eventDate the event date to check availability
     * @param employeeType the employee type (optional)
     * @return List of available employees
     */
    @Query("SELECT e FROM Employee e WHERE e.active = true " +
           "AND (:employeeType IS NULL OR e.employeeType = :employeeType) " +
           "AND e.id NOT IN (" +
           "    SELECT DISTINCT oe.employee.id FROM OrderEmployee oe " +
           "    JOIN oe.order o WHERE o.eventDate = :eventDate " +
           "    AND o.orderStatus IN ('PENDING', 'IN_PROGRESS')" +
           ")")
    List<Employee> findAvailableEmployees(@Param("eventDate") LocalDate eventDate, 
                                        @Param("employeeType") Employee.EmployeeType employeeType);
}

