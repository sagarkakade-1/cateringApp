package com.catering.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee entity representing different types of catering staff
 * Supports Cook, Bai, Waiter, Driver, Display Table Boy, Service Boy
 */
@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee code is required")
    @Size(max = 20, message = "Employee code cannot exceed 20 characters")
    @Column(name = "employee_code", unique = true, nullable = false, length = 20)
    private String employeeCode;

    @NotBlank(message = "Employee name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    @Column(length = 20)
    private String phone;

    @Email(message = "Please provide a valid email address")
    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false, length = 30)
    private EmployeeType employeeType;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "salary_per_order", precision = 8, scale = 2)
    private BigDecimal salaryPerOrder = BigDecimal.ZERO;

    @Column(name = "base_salary", precision = 10, scale = 2)
    private BigDecimal baseSalary = BigDecimal.ZERO;

    @Column(name = "total_orders_served")
    private Integer totalOrdersServed = 0;

    @Column(name = "total_earnings", precision = 12, scale = 2)
    private BigDecimal totalEarnings = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean active = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEmployee> orderAssignments = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> assignedTasks = new ArrayList<>();

    // Constructors
    public Employee() {}

    public Employee(String employeeCode, String name, String phone, EmployeeType employeeType) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.phone = phone;
        this.employeeType = employeeType;
        this.hireDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalaryPerOrder() {
        return salaryPerOrder;
    }

    public void setSalaryPerOrder(BigDecimal salaryPerOrder) {
        this.salaryPerOrder = salaryPerOrder;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Integer getTotalOrdersServed() {
        return totalOrdersServed;
    }

    public void setTotalOrdersServed(Integer totalOrdersServed) {
        this.totalOrdersServed = totalOrdersServed;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderEmployee> getOrderAssignments() {
        return orderAssignments;
    }

    public void setOrderAssignments(List<OrderEmployee> orderAssignments) {
        this.orderAssignments = orderAssignments;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    // Employee Type Enum
    public enum EmployeeType {
        COOK("Cook"),
        BAI("Bai"),
        WAITER("Waiter"),
        DRIVER("Driver"),
        DISPLAY_TABLE_BOY("Display Table Boy"),
        SERVICE_BOY("Service Boy");

        private final String displayName;

        EmployeeType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Helper methods
    public void addOrderAssignment(OrderEmployee orderEmployee) {
        orderAssignments.add(orderEmployee);
        orderEmployee.setEmployee(this);
    }

    public void removeOrderAssignment(OrderEmployee orderEmployee) {
        orderAssignments.remove(orderEmployee);
        orderEmployee.setEmployee(null);
    }

    public void addTask(Task task) {
        assignedTasks.add(task);
        task.setAssignedTo(this);
    }

    public void removeTask(Task task) {
        assignedTasks.remove(task);
        task.setAssignedTo(null);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeCode='" + employeeCode + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", employeeType=" + employeeType +
                ", totalOrdersServed=" + totalOrdersServed +
                ", totalEarnings=" + totalEarnings +
                ", active=" + active +
                '}';
    }
}

