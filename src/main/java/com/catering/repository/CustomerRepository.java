package com.catering.repository;

import com.catering.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for Customer entity
 * Provides data access methods for customer management
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find customers by type
     * @param customerType the customer type to search for
     * @return List of customers with the specified type
     */
    List<Customer> findByCustomerType(Customer.CustomerType customerType);

    /**
     * Find customers by business type
     * @param businessType the business type to search for
     * @return List of customers with the specified business type
     */
    List<Customer> findByBusinessType(Customer.BusinessType businessType);

    /**
     * Find all active customers
     * @return List of active customers
     */
    List<Customer> findByActiveTrue();

    /**
     * Find customers by name containing (case insensitive)
     * @param name the name to search for
     * @return List of customers whose name contains the search term
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Find customers by contact person containing (case insensitive)
     * @param contactPerson the contact person name to search for
     * @return List of customers whose contact person contains the search term
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.contactPerson) LIKE LOWER(CONCAT('%', :contactPerson, '%'))")
    List<Customer> findByContactPersonContainingIgnoreCase(@Param("contactPerson") String contactPerson);

    /**
     * Find customers by phone number
     * @param phone the phone number to search for
     * @return List of customers with the specified phone number
     */
    List<Customer> findByPhone(String phone);

    /**
     * Find customers with outstanding amount greater than zero
     * @return List of customers with outstanding payments
     */
    @Query("SELECT c FROM Customer c WHERE c.outstandingAmount > 0")
    List<Customer> findCustomersWithOutstandingPayments();

    /**
     * Find customers with outstanding amount greater than specified amount
     * @param amount the minimum outstanding amount
     * @return List of customers with outstanding amount greater than specified
     */
    @Query("SELECT c FROM Customer c WHERE c.outstandingAmount > :amount")
    List<Customer> findCustomersWithOutstandingAmountGreaterThan(@Param("amount") BigDecimal amount);

    /**
     * Find top customers by total amount (descending order)
     * @param limit the maximum number of customers to return
     * @return List of top customers by total amount
     */
    @Query("SELECT c FROM Customer c ORDER BY c.totalAmount DESC")
    List<Customer> findTopCustomersByTotalAmount();

    /**
     * Find customers by customer type and active status
     * @param customerType the customer type
     * @param active the active status
     * @return List of customers matching the criteria
     */
    List<Customer> findByCustomerTypeAndActive(Customer.CustomerType customerType, Boolean active);

    /**
     * Count customers by type
     * @param customerType the customer type
     * @return Count of customers with the specified type
     */
    long countByCustomerType(Customer.CustomerType customerType);

    /**
     * Find customers with total orders greater than specified count
     * @param orderCount the minimum order count
     * @return List of customers with more orders than specified
     */
    @Query("SELECT c FROM Customer c WHERE c.totalOrders > :orderCount")
    List<Customer> findCustomersWithOrdersGreaterThan(@Param("orderCount") Integer orderCount);
}

