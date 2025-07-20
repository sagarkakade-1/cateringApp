package com.catering.repository;

import com.catering.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Order entity
 * Provides data access methods for order management
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find order by order number
     * @param orderNumber the order number to search for
     * @return Optional containing the order if found
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Find orders by customer ID
     * @param customerId the customer ID
     * @return List of orders for the specified customer
     */
    List<Order> findByCustomerId(Long customerId);

    /**
     * Find orders by order type
     * @param orderType the order type to search for
     * @return List of orders with the specified type
     */
    List<Order> findByOrderType(Order.OrderType orderType);

    /**
     * Find orders by status
     * @param orderStatus the order status to search for
     * @return List of orders with the specified status
     */
    List<Order> findByOrderStatus(Order.OrderStatus orderStatus);

    /**
     * Find orders by payment status
     * @param paymentStatus the payment status to search for
     * @return List of orders with the specified payment status
     */
    List<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus);

    /**
     * Find orders by event date
     * @param eventDate the event date
     * @return List of orders for the specified event date
     */
    List<Order> findByEventDate(LocalDate eventDate);

    /**
     * Find orders between two dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List of orders between the specified dates
     */
    @Query("SELECT o FROM Order o WHERE o.eventDate BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Find orders by event name containing (case insensitive)
     * @param eventName the event name to search for
     * @return List of orders whose event name contains the search term
     */
    @Query("SELECT o FROM Order o WHERE LOWER(o.eventName) LIKE LOWER(CONCAT('%', :eventName, '%'))")
    List<Order> findByEventNameContainingIgnoreCase(@Param("eventName") String eventName);

    /**
     * Find orders by venue address containing (case insensitive)
     * @param venueAddress the venue address to search for
     * @return List of orders whose venue address contains the search term
     */
    @Query("SELECT o FROM Order o WHERE LOWER(o.venueAddress) LIKE LOWER(CONCAT('%', :venueAddress, '%'))")
    List<Order> findByVenueAddressContainingIgnoreCase(@Param("venueAddress") String venueAddress);

    /**
     * Find orders with total amount greater than specified amount
     * @param amount the minimum total amount
     * @return List of orders with total amount greater than specified
     */
    @Query("SELECT o FROM Order o WHERE o.totalAmount > :amount")
    List<Order> findOrdersWithAmountGreaterThan(@Param("amount") BigDecimal amount);

    /**
     * Find orders with outstanding payments
     * @return List of orders with remaining amount greater than zero
     */
    @Query("SELECT o FROM Order o WHERE o.remainingAmount > 0")
    List<Order> findOrdersWithOutstandingPayments();

    /**
     * Find upcoming orders (event date is today or in the future)
     * @return List of upcoming orders
     */
    @Query("SELECT o FROM Order o WHERE o.eventDate >= CURRENT_DATE ORDER BY o.eventDate ASC")
    List<Order> findUpcomingOrders();

    /**
     * Find orders for today
     * @return List of orders scheduled for today
     */
    @Query("SELECT o FROM Order o WHERE o.eventDate = CURRENT_DATE")
    List<Order> findTodaysOrders();

    /**
     * Find orders by assigned cook
     * @param cookId the cook's employee ID
     * @return List of orders assigned to the specified cook
     */
    @Query("SELECT o FROM Order o WHERE o.assignedCook.id = :cookId")
    List<Order> findOrdersByAssignedCook(@Param("cookId") Long cookId);

    /**
     * Find orders by assigned driver
     * @param driverId the driver's employee ID
     * @return List of orders assigned to the specified driver
     */
    @Query("SELECT o FROM Order o WHERE o.assignedDriver.id = :driverId")
    List<Order> findOrdersByAssignedDriver(@Param("driverId") Long driverId);

    /**
     * Count orders by status
     * @param orderStatus the order status
     * @return Count of orders with the specified status
     */
    long countByOrderStatus(Order.OrderStatus orderStatus);

    /**
     * Count orders by payment status
     * @param paymentStatus the payment status
     * @return Count of orders with the specified payment status
     */
    long countByPaymentStatus(Order.PaymentStatus paymentStatus);

    /**
     * Count orders by order type
     * @param orderType the order type
     * @return Count of orders with the specified type
     */
    long countByOrderType(Order.OrderType orderType);

    /**
     * Find orders created by a specific user
     * @param userId the user ID
     * @return List of orders created by the specified user
     */
    List<Order> findByCreatedById(Long userId);

    /**
     * Find orders for a specific month and year
     * @param year the year
     * @param month the month (1-12)
     * @return List of orders for the specified month and year
     */
    @Query("SELECT o FROM Order o WHERE YEAR(o.eventDate) = :year AND MONTH(o.eventDate) = :month")
    List<Order> findOrdersByMonthAndYear(@Param("year") int year, @Param("month") int month);

    /**
     * Calculate total revenue for completed orders
     * @return Total revenue from completed orders
     */
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.orderStatus = 'COMPLETED'")
    BigDecimal calculateTotalRevenue();

    /**
     * Calculate total outstanding amount
     * @return Total outstanding amount from all orders
     */
    @Query("SELECT COALESCE(SUM(o.remainingAmount), 0) FROM Order o WHERE o.remainingAmount > 0")
    BigDecimal calculateTotalOutstandingAmount();

    /**
     * Check if order number exists
     * @param orderNumber the order number to check
     * @return true if order number exists, false otherwise
     */
    boolean existsByOrderNumber(String orderNumber);
}

