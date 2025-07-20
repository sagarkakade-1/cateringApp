package com.catering.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * OrderEmployee entity for many-to-many relationship between Orders and Employees
 * Tracks employee assignments to orders with payment details
 */
@Entity
@Table(name = "order_employees")
@EntityListeners(AuditingEntityListener.class)
public class OrderEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Size(max = 50, message = "Role in order cannot exceed 50 characters")
    @Column(name = "role_in_order", length = 50)
    private String roleInOrder;

    @CreatedDate
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @Column(name = "payment_amount", precision = 8, scale = 2)
    private BigDecimal paymentAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    // Constructors
    public OrderEmployee() {}

    public OrderEmployee(Order order, Employee employee, String roleInOrder, BigDecimal paymentAmount) {
        this.order = order;
        this.employee = employee;
        this.roleInOrder = roleInOrder;
        this.paymentAmount = paymentAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getRoleInOrder() {
        return roleInOrder;
    }

    public void setRoleInOrder(String roleInOrder) {
        this.roleInOrder = roleInOrder;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Payment Status Enum
    public enum PaymentStatus {
        PENDING("Pending"),
        PAID("Paid");

        private final String displayName;

        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Override
    public String toString() {
        return "OrderEmployee{" +
                "id=" + id +
                ", order=" + (order != null ? order.getOrderNumber() : "null") +
                ", employee=" + (employee != null ? employee.getName() : "null") +
                ", roleInOrder='" + roleInOrder + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}

