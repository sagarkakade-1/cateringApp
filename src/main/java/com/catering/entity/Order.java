package com.catering.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order entity representing both Full Catering and Half Catering orders
 * Central entity that connects customers, employees, inventory, and tasks
 */
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Order number is required")
    @Size(max = 30, message = "Order number cannot exceed 30 characters")
    @Column(name = "order_number", unique = true, nullable = false, length = 30)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false, length = 20)
    private OrderType orderType;

    @Size(max = 200, message = "Event name cannot exceed 200 characters")
    @Column(name = "event_name", length = 200)
    private String eventName;

    @NotNull(message = "Event date is required")
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "event_time")
    private LocalTime eventTime;

    @Column(name = "venue_address", columnDefinition = "TEXT")
    private String venueAddress;

    @Column(name = "guest_count")
    private Integer guestCount;

    @Column(name = "menu_details", columnDefinition = "TEXT")
    private String menuDetails;

    @Column(name = "special_requirements", columnDefinition = "TEXT")
    private String specialRequirements;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 20)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "advance_amount", precision = 12, scale = 2)
    private BigDecimal advanceAmount = BigDecimal.ZERO;

    @Column(name = "remaining_amount", precision = 12, scale = 2)
    private BigDecimal remainingAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_cook")
    private Employee assignedCook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_driver")
    private Employee assignedDriver;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEmployee> employeeAssignments = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderInventory> inventoryUsage = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    // Constructors
    public Order() {}

    public Order(String orderNumber, Customer customer, OrderType orderType, LocalDate eventDate) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.orderType = orderType;
        this.eventDate = eventDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getMenuDetails() {
        return menuDetails;
    }

    public void setMenuDetails(String menuDetails) {
        this.menuDetails = menuDetails;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        this.remainingAmount = totalAmount.subtract(advanceAmount != null ? advanceAmount : BigDecimal.ZERO);
    }

    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
        this.remainingAmount = (totalAmount != null ? totalAmount : BigDecimal.ZERO).subtract(advanceAmount != null ? advanceAmount : BigDecimal.ZERO);
        updatePaymentStatus();
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Employee getAssignedCook() {
        return assignedCook;
    }

    public void setAssignedCook(Employee assignedCook) {
        this.assignedCook = assignedCook;
    }

    public Employee getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(Employee assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public List<OrderEmployee> getEmployeeAssignments() {
        return employeeAssignments;
    }

    public void setEmployeeAssignments(List<OrderEmployee> employeeAssignments) {
        this.employeeAssignments = employeeAssignments;
    }

    public List<OrderInventory> getInventoryUsage() {
        return inventoryUsage;
    }

    public void setInventoryUsage(List<OrderInventory> inventoryUsage) {
        this.inventoryUsage = inventoryUsage;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Enums
    public enum OrderType {
        FULL_CATERING("Full Catering"),
        HALF_CATERING("Half Catering");

        private final String displayName;

        OrderType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum OrderStatus {
        PENDING("Pending"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PaymentStatus {
        PENDING("Pending"),
        ADVANCE_PAID("Advance Paid"),
        FULLY_PAID("Fully Paid");

        private final String displayName;

        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Helper methods
    public void addEmployeeAssignment(OrderEmployee orderEmployee) {
        employeeAssignments.add(orderEmployee);
        orderEmployee.setOrder(this);
    }

    public void removeEmployeeAssignment(OrderEmployee orderEmployee) {
        employeeAssignments.remove(orderEmployee);
        orderEmployee.setOrder(null);
    }

    public void addInventoryUsage(OrderInventory orderInventory) {
        inventoryUsage.add(orderInventory);
        orderInventory.setOrder(this);
    }

    public void removeInventoryUsage(OrderInventory orderInventory) {
        inventoryUsage.remove(orderInventory);
        orderInventory.setOrder(null);
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setOrder(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setOrder(null);
    }

    private void updatePaymentStatus() {
        if (remainingAmount == null || remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            this.paymentStatus = PaymentStatus.FULLY_PAID;
        } else if (advanceAmount != null && advanceAmount.compareTo(BigDecimal.ZERO) > 0) {
            this.paymentStatus = PaymentStatus.ADVANCE_PAID;
        } else {
            this.paymentStatus = PaymentStatus.PENDING;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderType=" + orderType +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", guestCount=" + guestCount +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}

