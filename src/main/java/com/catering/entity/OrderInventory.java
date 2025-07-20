package com.catering.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * OrderInventory entity for many-to-many relationship between Orders and Inventory
 * Tracks inventory usage in orders with cost details
 */
@Entity
@Table(name = "order_inventory")
@EntityListeners(AuditingEntityListener.class)
public class OrderInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @NotNull(message = "Quantity used is required")
    @Column(name = "quantity_used", nullable = false)
    private Integer quantityUsed;

    @Column(name = "unit_cost", precision = 8, scale = 2)
    private BigDecimal unitCost = BigDecimal.ZERO;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @CreatedDate
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    // Constructors
    public OrderInventory() {}

    public OrderInventory(Order order, Inventory inventory, Integer quantityUsed, BigDecimal unitCost) {
        this.order = order;
        this.inventory = inventory;
        this.quantityUsed = quantityUsed;
        this.unitCost = unitCost;
        calculateTotalCost();
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
        calculateTotalCost();
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
        calculateTotalCost();
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    // Helper methods
    private void calculateTotalCost() {
        if (quantityUsed != null && unitCost != null) {
            this.totalCost = unitCost.multiply(BigDecimal.valueOf(quantityUsed));
        }
    }

    @Override
    public String toString() {
        return "OrderInventory{" +
                "id=" + id +
                ", order=" + (order != null ? order.getOrderNumber() : "null") +
                ", inventory=" + (inventory != null ? inventory.getItemName() : "null") +
                ", quantityUsed=" + quantityUsed +
                ", unitCost=" + unitCost +
                ", totalCost=" + totalCost +
                '}';
    }
}

