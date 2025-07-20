package com.catering.repository;

import com.catering.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Inventory entity
 * Provides data access methods for inventory management
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Find inventory item by item code
     * @param itemCode the item code to search for
     * @return Optional containing the inventory item if found
     */
    Optional<Inventory> findByItemCode(String itemCode);

    /**
     * Find inventory items by category
     * @param category the item category to search for
     * @return List of inventory items with the specified category
     */
    List<Inventory> findByCategory(Inventory.ItemCategory category);

    /**
     * Find all active inventory items
     * @return List of active inventory items
     */
    List<Inventory> findByActiveTrue();

    /**
     * Find inventory items by name containing (case insensitive)
     * @param itemName the item name to search for
     * @return List of inventory items whose name contains the search term
     */
    @Query("SELECT i FROM Inventory i WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))")
    List<Inventory> findByItemNameContainingIgnoreCase(@Param("itemName") String itemName);

    /**
     * Find inventory items by supplier
     * @param supplier the supplier name
     * @return List of inventory items from the specified supplier
     */
    List<Inventory> findBySupplier(String supplier);

    /**
     * Find inventory items by location
     * @param location the storage location
     * @return List of inventory items in the specified location
     */
    List<Inventory> findByLocation(String location);

    /**
     * Find low stock items (current stock <= minimum stock)
     * @return List of inventory items with low stock
     */
    @Query("SELECT i FROM Inventory i WHERE i.currentStock <= i.minimumStock AND i.active = true")
    List<Inventory> findLowStockItems();

    /**
     * Find out of stock items (current stock = 0)
     * @return List of inventory items that are out of stock
     */
    @Query("SELECT i FROM Inventory i WHERE i.currentStock = 0 AND i.active = true")
    List<Inventory> findOutOfStockItems();

    /**
     * Find inventory items with stock greater than specified quantity
     * @param quantity the minimum stock quantity
     * @return List of inventory items with stock greater than specified
     */
    @Query("SELECT i FROM Inventory i WHERE i.currentStock > :quantity")
    List<Inventory> findItemsWithStockGreaterThan(@Param("quantity") Integer quantity);

    /**
     * Find inventory items by category and active status
     * @param category the item category
     * @param active the active status
     * @return List of inventory items matching the criteria
     */
    List<Inventory> findByCategoryAndActive(Inventory.ItemCategory category, Boolean active);

    /**
     * Find inventory items with total value greater than specified amount
     * @param value the minimum total value
     * @return List of inventory items with total value greater than specified
     */
    @Query("SELECT i FROM Inventory i WHERE i.totalValue > :value")
    List<Inventory> findItemsWithValueGreaterThan(@Param("value") BigDecimal value);

    /**
     * Count inventory items by category
     * @param category the item category
     * @return Count of inventory items with the specified category
     */
    long countByCategory(Inventory.ItemCategory category);

    /**
     * Count active inventory items by category
     * @param category the item category
     * @param active the active status
     * @return Count of active inventory items with the specified category
     */
    long countByCategoryAndActive(Inventory.ItemCategory category, Boolean active);

    /**
     * Calculate total inventory value
     * @return Total value of all active inventory items
     */
    @Query("SELECT COALESCE(SUM(i.totalValue), 0) FROM Inventory i WHERE i.active = true")
    BigDecimal calculateTotalInventoryValue();

    /**
     * Calculate total inventory value by category
     * @param category the item category
     * @return Total value of inventory items in the specified category
     */
    @Query("SELECT COALESCE(SUM(i.totalValue), 0) FROM Inventory i WHERE i.category = :category AND i.active = true")
    BigDecimal calculateTotalValueByCategory(@Param("category") Inventory.ItemCategory category);

    /**
     * Check if item code exists
     * @param itemCode the item code to check
     * @return true if item code exists, false otherwise
     */
    boolean existsByItemCode(String itemCode);

    /**
     * Find inventory items that need reordering (low stock items)
     * @return List of inventory items that need reordering
     */
    @Query("SELECT i FROM Inventory i WHERE i.currentStock <= i.minimumStock AND i.active = true ORDER BY i.category, i.itemName")
    List<Inventory> findItemsNeedingReorder();

    /**
     * Find inventory items by unit type
     * @param unit the unit type (e.g., PIECES, KG, LITERS)
     * @return List of inventory items with the specified unit
     */
    List<Inventory> findByUnit(String unit);

    /**
     * Find most valuable inventory items (ordered by total value descending)
     * @return List of inventory items ordered by total value (highest first)
     */
    @Query("SELECT i FROM Inventory i WHERE i.active = true ORDER BY i.totalValue DESC")
    List<Inventory> findMostValuableItems();
}

