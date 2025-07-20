package com.catering.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory entity for tracking materials and supplies
 * Supports utensils, display tables, water cans, food items, equipment
 */
@Entity
@Table(name = "inventory")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item code is required")
    @Size(max = 30, message = "Item code cannot exceed 30 characters")
    @Column(name = "item_code", unique = true, nullable = false, length = 30)
    private String itemCode;

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name cannot exceed 100 characters")
    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ItemCategory category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 20, message = "Unit cannot exceed 20 characters")
    @Column(length = 20)
    private String unit;

    @Column(name = "current_stock")
    private Integer currentStock = 0;

    @Column(name = "minimum_stock")
    private Integer minimumStock = 0;

    @Column(name = "unit_cost", precision = 8, scale = 2)
    private BigDecimal unitCost = BigDecimal.ZERO;

    @Column(name = "total_value", precision = 10, scale = 2)
    private BigDecimal totalValue = BigDecimal.ZERO;

    @Size(max = 100, message = "Supplier name cannot exceed 100 characters")
    @Column(length = 100)
    private String supplier;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    @Column(length = 100)
    private String location;

    @Column(nullable = false)
    private Boolean active = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderInventory> orderUsages = new ArrayList<>();

    // Constructors
    public Inventory() {}

    public Inventory(String itemCode, String itemName, ItemCategory category, String unit) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.unit = unit;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
        updateTotalValue();
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
        updateTotalValue();
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<OrderInventory> getOrderUsages() {
        return orderUsages;
    }

    public void setOrderUsages(List<OrderInventory> orderUsages) {
        this.orderUsages = orderUsages;
    }

    // Item Category Enum
    public enum ItemCategory {
        UTENSILS("Utensils"),
        DISPLAY_TABLES("Display Tables"),
        WATER_CANS("Water Cans"),
        VEGETABLES("Vegetables"),
        GROCERY("Grocery"),
        EQUIPMENT("Equipment");

        private final String displayName;

        ItemCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Helper methods
    public void addOrderUsage(OrderInventory orderInventory) {
        orderUsages.add(orderInventory);
        orderInventory.setInventory(this);
    }

    public void removeOrderUsage(OrderInventory orderInventory) {
        orderUsages.remove(orderInventory);
        orderInventory.setInventory(null);
    }

    public boolean isLowStock() {
        return currentStock != null && minimumStock != null && currentStock <= minimumStock;
    }

    public void updateStock(Integer quantity) {
        if (currentStock != null) {
            this.currentStock += quantity;
            updateTotalValue();
        }
    }

    public void useStock(Integer quantity) {
        if (currentStock != null && currentStock >= quantity) {
            this.currentStock -= quantity;
            updateTotalValue();
        }
    }

    private void updateTotalValue() {
        if (currentStock != null && unitCost != null) {
            this.totalValue = unitCost.multiply(BigDecimal.valueOf(currentStock));
        }
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", category=" + category +
                ", currentStock=" + currentStock +
                ", minimumStock=" + minimumStock +
                ", unitCost=" + unitCost +
                ", totalValue=" + totalValue +
                ", active=" + active +
                '}';
    }
}

