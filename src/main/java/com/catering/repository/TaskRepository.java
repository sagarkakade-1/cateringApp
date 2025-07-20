package com.catering.repository;

import com.catering.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Task entity
 * Provides data access methods for task management
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find tasks by order ID
     * @param orderId the order ID
     * @return List of tasks for the specified order
     */
    List<Task> findByOrderId(Long orderId);

    /**
     * Find tasks by assigned employee ID
     * @param employeeId the employee ID
     * @return List of tasks assigned to the specified employee
     */
    List<Task> findByAssignedToId(Long employeeId);

    /**
     * Find tasks by status
     * @param taskStatus the task status to search for
     * @return List of tasks with the specified status
     */
    List<Task> findByTaskStatus(Task.TaskStatus taskStatus);

    /**
     * Find tasks by priority
     * @param priority the task priority to search for
     * @return List of tasks with the specified priority
     */
    List<Task> findByPriority(Task.TaskPriority priority);

    /**
     * Find tasks by due date
     * @param dueDate the due date
     * @return List of tasks due on the specified date
     */
    List<Task> findByDueDate(LocalDate dueDate);

    /**
     * Find tasks by title containing (case insensitive)
     * @param title the title to search for
     * @return List of tasks whose title contains the search term
     */
    @Query("SELECT t FROM Task t WHERE LOWER(t.taskTitle) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Task> findByTaskTitleContainingIgnoreCase(@Param("title") String title);

    /**
     * Find overdue tasks (due date is before today and status is not DONE or DELETED)
     * @return List of overdue tasks
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_DATE AND t.taskStatus NOT IN ('DONE', 'DELETED')")
    List<Task> findOverdueTasks();

    /**
     * Find tasks due today
     * @return List of tasks due today
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate = CURRENT_DATE AND t.taskStatus NOT IN ('DONE', 'DELETED')")
    List<Task> findTasksDueToday();

    /**
     * Find tasks due within specified number of days
     * @param days the number of days from today
     * @return List of tasks due within the specified days
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN CURRENT_DATE AND :endDate AND t.taskStatus NOT IN ('DONE', 'DELETED')")
    List<Task> findTasksDueWithinDays(@Param("endDate") LocalDate endDate);

    /**
     * Find pending tasks (status is TODO or IN_PROGRESS)
     * @return List of pending tasks
     */
    @Query("SELECT t FROM Task t WHERE t.taskStatus IN ('TODO', 'IN_PROGRESS')")
    List<Task> findPendingTasks();

    /**
     * Find completed tasks (status is DONE)
     * @return List of completed tasks
     */
    List<Task> findByTaskStatus_Done();

    /**
     * Find tasks by employee and status
     * @param employeeId the employee ID
     * @param taskStatus the task status
     * @return List of tasks matching the criteria
     */
    List<Task> findByAssignedToIdAndTaskStatus(Long employeeId, Task.TaskStatus taskStatus);

    /**
     * Find tasks by order and status
     * @param orderId the order ID
     * @param taskStatus the task status
     * @return List of tasks matching the criteria
     */
    List<Task> findByOrderIdAndTaskStatus(Long orderId, Task.TaskStatus taskStatus);

    /**
     * Find high priority tasks
     * @return List of high priority tasks
     */
    @Query("SELECT t FROM Task t WHERE t.priority = 'HIGH' AND t.taskStatus NOT IN ('DONE', 'DELETED')")
    List<Task> findHighPriorityTasks();

    /**
     * Find tasks created by a specific user
     * @param userId the user ID
     * @return List of tasks created by the specified user
     */
    List<Task> findByCreatedById(Long userId);

    /**
     * Find tasks between two dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List of tasks with due dates between the specified dates
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findTasksBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Count tasks by status
     * @param taskStatus the task status
     * @return Count of tasks with the specified status
     */
    long countByTaskStatus(Task.TaskStatus taskStatus);

    /**
     * Count tasks by priority
     * @param priority the task priority
     * @return Count of tasks with the specified priority
     */
    long countByPriority(Task.TaskPriority priority);

    /**
     * Count tasks assigned to a specific employee
     * @param employeeId the employee ID
     * @return Count of tasks assigned to the specified employee
     */
    long countByAssignedToId(Long employeeId);

    /**
     * Count pending tasks for a specific employee
     * @param employeeId the employee ID
     * @return Count of pending tasks for the specified employee
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :employeeId AND t.taskStatus IN ('TODO', 'IN_PROGRESS')")
    long countPendingTasksByEmployee(@Param("employeeId") Long employeeId);

    /**
     * Count completed tasks for a specific employee
     * @param employeeId the employee ID
     * @return Count of completed tasks for the specified employee
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :employeeId AND t.taskStatus = 'DONE'")
    long countCompletedTasksByEmployee(@Param("employeeId") Long employeeId);

    /**
     * Find tasks for a specific order and employee
     * @param orderId the order ID
     * @param employeeId the employee ID
     * @return List of tasks for the specified order and employee
     */
    List<Task> findByOrderIdAndAssignedToId(Long orderId, Long employeeId);

    /**
     * Find unassigned tasks (no employee assigned)
     * @return List of tasks with no assigned employee
     */
    @Query("SELECT t FROM Task t WHERE t.assignedTo IS NULL AND t.taskStatus NOT IN ('DONE', 'DELETED')")
    List<Task> findUnassignedTasks();
}

