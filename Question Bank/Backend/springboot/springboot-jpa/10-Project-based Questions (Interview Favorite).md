Here are comprehensive, **project-focused answers** that demonstrate real-world experience and architectural thinking - perfect for :

## **Project-based Questions **

### **91. Explain database schema of your project**

** Answer:**
In our **E-commerce Platform**, the database schema follows a **modular, domain-driven design** with clear separation of concerns. The core schema comprises **15 main tables** organized into logical domains:

**Core Domains:**

1. **Identity Domain** (`users`, `roles`, `permissions`):
   - Handles authentication, authorization, and user profiles
   - Supports OAuth2, JWT, and role-based access control

2. **Product Domain** (`products`, `categories`, `inventory`):
   - Catalog management with categorization
   - Inventory tracking with real-time stock updates
   - Support for variants and attributes

3. **Order Domain** (`orders`, `order_items`, `payments`, `shipments`):
   - Complete order lifecycle management
   - Payment processing integration
   - Shipment tracking and logistics

4. **Customer Domain** (`customers`, `addresses`, `customer_preferences`):
   - Customer relationship management
   - Address book with validation
   - Personalized preferences

**Schema Design Principles:**
- **Normalization**: Up to 3NF for data integrity
- **Performance**: Strategic denormalization for read-heavy operations
- **Scalability**: Partition-ready design for future growth
- **Auditability**: Comprehensive audit trails on critical tables

**Example Table Structure:**
```sql
-- orders table example
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) UNIQUE,
    customer_id BIGINT NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 1,
    
    INDEX idx_customer (customer_id),
    INDEX idx_status_created (status, created_at DESC),
    INDEX idx_order_number (order_number),
    
    FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED;
```

**Theoretical Keywords:**  
**Domain-driven design**, **Modular schema architecture**, **Normalization strategy**, **Performance-conscious design**, **Scalability considerations**

---

### **92. Explain entity relationships used**

** Answer:**
We implemented **strategic relationship mappings** based on access patterns and business requirements, with a mix of unidirectional and bidirectional associations:

**Key Relationship Patterns:**

1. **Customer → Orders (One-to-Many):**
```java
@Entity
public class Customer {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();
}

@Entity
public class Order {
    @Id
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}
```

2. **Order → OrderItems (One-to-Many with Composite Key):**
```java
@Entity
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Integer quantity;
    private BigDecimal unitPrice;
}

@Embeddable
public class OrderItemId implements Serializable {
    private Long orderId;
    private Long productId;
}
```

3. **Product → Category (Many-to-Many with Extra Attributes):**
```java
@Entity
public class Product {
    @Id
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @BatchSize(size = 20)
    private Set<Category> categories = new HashSet<>();
}

@Entity
public class ProductCategory {
    @EmbeddedId
    private ProductCategoryId id;
    
    @ManyToOne
    @MapsId("productId")
    private Product product;
    
    @ManyToOne
    @MapsId("categoryId")
    private Category category;
    
    private Integer displayOrder;  // Extra attribute
    private Boolean isPrimary;
}
```

4. **User → Roles (Many-to-Many for Authorization):**
```java
@Entity
public class User {
    @Id
    private Long id;
    
    @ManyToMany(fetch = FetchType.EAGER)  // EAGER for security checks
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
```

**Design Decisions:**
- **Bidirectional where needed**: For navigation from both sides
- **Unidirectional where sufficient**: Simpler, less overhead
- **LAZY by default**: Except for security-related relationships
- **Cascade carefully**: Only where business rules allow

**Theoretical Keywords:**  
**Strategic relationship mapping**, **Access pattern-based design**, **Bidirectional vs unidirectional choice**, **Cascade strategy**, **Composite key implementation**

---

### **93. How many tables and why?**

** Answer:**
Our **E-commerce Platform** has **28 tables** in total, categorized by domain and purpose:

**Table Breakdown by Domain:**

1. **Identity & Security (5 tables)**:
   - `users`, `roles`, `permissions`, `user_roles`, `user_sessions`
   - **Why**: Separation of concerns, RBAC implementation, session management

2. **Product Catalog (6 tables)**:
   - `products`, `categories`, `product_categories`, `product_attributes`, `product_variants`, `inventory`
   - **Why**: Flexible catalog structure, attribute management, inventory tracking

3. **Order Management (8 tables)**:
   - `orders`, `order_items`, `payments`, `refunds`, `shipments`, `shipping_methods`, `taxes`, `invoices`
   - **Why**: Complete order lifecycle, financial tracking, compliance

4. **Customer Management (4 tables)**:
   - `customers`, `addresses`, `customer_preferences`, `wishlists`
   - **Why**: CRM functionality, personalization, address management

5. **Audit & Logging (3 tables)**:
   - `audit_logs`, `system_logs`, `change_history`
   - **Why**: Compliance, debugging, change tracking

6. **Configuration (2 tables)**:
   - `system_config`, `country_states`
   - **Why**: Dynamic configuration, reference data

**Design Rationale:**

**Why Not Fewer Tables?**
- **Data Integrity**: Each entity with distinct lifecycle and validation
- **Performance**: Smaller, focused tables for better indexing
- **Maintainability**: Clear separation of concerns
- **Scalability**: Independent scaling of different domains

**Why Not More Tables?**
- **Complexity Balance**: Avoid over-normalization
- **Join Performance**: Too many joins impact query performance
- **Development Speed**: Reasonable number for team to manage

**Example of Table Consolidation Decision:**

**Separate Tables Chosen:**
```sql
-- Instead of combining in one table
CREATE TABLE product_data (
    product_id BIGINT,
    attribute_name VARCHAR(100),
    attribute_value VARCHAR(500),
    attribute_type VARCHAR(50)
);

-- We used:
CREATE TABLE products ( ... );
CREATE TABLE product_attributes ( ... );  -- EAV pattern for flexibility
CREATE TABLE product_variants ( ... );    -- For SKU management
```

**Future Scalability Considerations:**
- **Partitioning Ready**: Tables designed for future partitioning
- **Sharding Potential**: Customer/order data shardable by region
- **Read Replicas**: Read-heavy tables separated from write-heavy

**Theoretical Keywords:**  
**Domain-based table organization**, **Balanced normalization**, **Scalability-driven design**, **Performance vs complexity trade-off**, **Future growth considerations**

---

### **94. How did you handle transactions in the project?**

** Answer:**
We implemented a **multi-layered transaction strategy** combining declarative and programmatic approaches based on business requirements:

**Transaction Strategy Layers:**

1. **Service Layer Transactions (Primary):**
```java
@Service
@Transactional(readOnly = true)  // Class-level default
public class OrderService {
    
    @Transactional  // Override for write operations
    public Order createOrder(OrderRequest request) {
        // Complex business logic with multiple repository calls
        // All or nothing - full rollback on any failure
    }
    
    public Order getOrder(Long id) {
        // Read-only transaction (optimized)
        return orderRepository.findById(id).orElseThrow();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void auditOrderCreation(Order order) {
        // Separate transaction for audit
        // Failure doesn't affect main order creation
    }
}
```

2. **Repository Layer Transactions:**
```java
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    @Modifying
    @Query("UPDATE Payment p SET p.status = :status WHERE p.id = :id")
    @Transactional
    int updateStatus(@Param("id") Long id, @Param("status") PaymentStatus status);
}
```

3. **Programmatic Transactions for Complex Logic:**
```java
@Service
public class InventoryService {
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    public void batchUpdateInventory(List<InventoryUpdate> updates) {
        transactionTemplate.execute(status -> {
            for (InventoryUpdate update : updates) {
                // Custom rollback logic
                if (update.getQuantity() < 0) {
                    status.setRollbackOnly();
                    throw new InsufficientStockException();
                }
                inventoryRepository.updateStock(update);
            }
            return null;
        });
    }
}
```

**Transaction Configuration:**
```yaml
# application.yml
spring:
  jpa:
    properties:
      javax:
        persistence:
          query:
            timeout: 30000  # 30 second query timeout
      hibernate:
        connection:
          autocommit: false  # Disable auto-commit
        jdbc:
          batch_size: 50
          fetch_size: 100
```

**Isolation Level Strategy:**
```java
// Critical financial operations
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void processPayment(PaymentRequest request) {
    // Prevent non-repeatable reads for balance checks
}

// Normal operations
@Transactional(isolation = Isolation.READ_COMMITTED)  // Default
public void updateProduct(ProductUpdate update) {
    // Standard isolation for catalog updates
}
```

**Distributed Transaction Handling:**
```java
// For cross-service operations (eventual consistency)
@Transactional
public void placeOrderWithSaga(Order order) {
    // 1. Local transaction - save order
    orderRepository.save(order);
    
    // 2. Publish domain event
    eventPublisher.publish(new OrderCreatedEvent(order));
    
    // Other services handle their part asynchronously
    // inventory-service: Reserve stock
    // payment-service: Process payment
    // notification-service: Send confirmation
}
```

**Transaction Monitoring:**
```java
@Component
public class TransactionMonitoringAspect {
    
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object monitorTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            if (duration > 5000) {  // Alert on >5s transactions
                log.warn("Long transaction detected: {}ms for {}", 
                         duration, joinPoint.getSignature());
                metricsService.recordLongTransaction(duration);
            }
        }
    }
}
```

**Challenges & Solutions:**
1. **Long Transactions**: Implemented chunk processing for batch operations
2. **Deadlocks**: Used retry logic with exponential backoff
3. **Connection Pool Exhaustion**: Proper pool sizing and timeout configuration
4. **Distributed Consistency**: Event-driven architecture with compensation

**Theoretical Keywords:**  
**Multi-layered transaction strategy**, **Declarative vs programmatic control**, **Isolation level optimization**, **Distributed transaction patterns**, **Performance monitoring integration**

---

### **95. How did you handle validations vs DB constraints?**

** Answer:**
We implemented a **defense-in-depth validation strategy** with clear separation between application validation (user experience) and database constraints (data integrity):

**Validation Layer Architecture:**

**1. Request Validation (API Layer):**
```java
public class UserRegistrationRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Valid  // Nested object validation
    private AddressRequest address;
}

@RestController
public class UserController {
    
    @PostMapping("/users")
    public ResponseEntity<User> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        // Spring validates automatically, returns 400 on failure
        return ResponseEntity.ok(userService.register(request));
    }
}
```

**2. Business Rule Validation (Service Layer):**
```java
@Service
@Validated  // Enables method parameter validation
public class UserService {
    
    public User register(@Valid UserRegistrationRequest request) {
        // Business rule validation
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessValidationException("Username already taken");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessValidationException("Email already registered");
        }
        
        // Age validation (business rule)
        if (request.getAge() != null && request.getAge() < 18) {
            throw new BusinessValidationException("Must be 18 or older");
        }
        
        return userRepository.save(request.toEntity());
    }
}
```

**3. Database Constraints (Ultimate Protection):**
```java
@Entity
@Table(name = "users", 
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
           @UniqueConstraint(name = "uk_users_username", columnNames = "username")
       })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

**4. Custom Constraint Validators:**
```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {
    String message() default "Password must be at least 8 characters with uppercase, lowercase, and number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

@Component
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        
        return password.length() >= 8 && hasUpper && hasLower && hasDigit;
    }
}
```

**5. Validation Error Handling:**
```java
@ControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        List<FieldErrorResponse> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new FieldErrorResponse(
                error.getField(),
                error.getDefaultMessage(),
                error.getRejectedValue()
            ))
            .collect(Collectors.toList());
        
        ValidationErrorResponse error = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Invalid request parameters")
            .fieldErrors(fieldErrors)
            .build();
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        // Handle database constraint violations
        // Convert to user-friendly messages
    }
}
```

**6. Race Condition Protection:**
```java
@Service
public class RegistrationService {
    
    @Transactional
    public User registerWithRaceProtection(UserRegistrationRequest request) {
        // Application validation first
        validateRegistrationRequest(request);
        
        try {
            // Database attempt (constraints catch races)
            return userRepository.save(request.toEntity());
            
        } catch (DataIntegrityViolationException e) {
            // Convert database error to business error
            if (isUniqueConstraintViolation(e)) {
                throw new BusinessValidationException("Username or email already taken");
            }
            throw e;
        }
    }
}
```

**Validation Strategy Matrix:**
| **Validation Type** | **Purpose** | **Layer** | **Error Response** |
|---------------------|-------------|-----------|-------------------|
| **Form Validation** | User experience | Controller | 400 with field errors |
| **Business Rules** | Domain logic | Service | 400/409 with business message |
| **Database Constraints** | Data integrity | Database | 500 converted to 409 |
| **Security Validation** | Security rules | Multiple | 400/403 |

**Benefits of This Approach:**
1. **Early Failure**: Catch errors before hitting database
2. **User Experience**: Clear, actionable error messages
3. **Data Integrity**: Database as final safety net
4. **Performance**: Avoid unnecessary database operations
5. **Security**: Defense in depth

**Theoretical Keywords:**  
**Defense-in-depth validation strategy**, **Layer-specific validation responsibilities**, **Race condition protection**, **Constraint violation handling**, **User experience vs data integrity balance**

---

### **96. How did you optimize JPA queries?**

** Answer:**
We implemented a **comprehensive JPA query optimization strategy** focused on reducing database load, minimizing network trips, and leveraging database capabilities:

**Optimization Techniques Applied:**

**1. N+1 Problem Elimination:**
```java
// Before: N+1 queries
List<Order> orders = orderRepository.findAll();
orders.forEach(o -> o.getItems().size());  // Each triggers query

// After: Single query with JOIN FETCH
@Query("SELECT DISTINCT o FROM Order o " +
       "JOIN FETCH o.items " +
       "JOIN FETCH o.customer " +
       "WHERE o.createdDate >= :startDate")
List<Order> findRecentOrdersWithDetails(@Param("startDate") LocalDate startDate);
```

**2. Entity Graphs for Dynamic Fetching:**
```java
@Entity
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "Order.withItems",
        attributeNodes = @NamedAttributeNode("items")
    ),
    @NamedEntityGraph(
        name = "Order.withItemsAndCustomer",
        attributeNodes = {
            @NamedAttributeNode("items"),
            @NamedEntityGraph(
                name = "items.withProduct",
                attributeNodes = @NamedAttributeNode("product")
            ),
            @NamedAttributeNode("customer")
        }
    )
})
public class Order { ... }

// Repository usage
@EntityGraph(value = "Order.withItemsAndCustomer")
List<Order> findByStatus(OrderStatus status);
```

**3. DTO Projections for Read-heavy Endpoints:**
```java
public interface OrderSummary {
    Long getId();
    String getOrderNumber();
    BigDecimal getTotalAmount();
    LocalDateTime getCreatedDate();
    
    @Value("#{target.customer.name}")
    String getCustomerName();
}

@Query("""
    SELECT o.id as id, o.orderNumber as orderNumber, 
           o.totalAmount as totalAmount, o.createdDate as createdDate,
           c.name as customerName
    FROM Order o 
    JOIN o.customer c
    WHERE o.status = :status
    ORDER BY o.createdDate DESC
    """)
Page<OrderSummary> findOrderSummariesByStatus(@Param("status") OrderStatus status, 
                                              Pageable pageable);
```

**4. Batch Fetching Configuration:**
```java
@Entity
public class Customer {
    @OneToMany(mappedBy = "customer")
    @BatchSize(size = 50)  // Load orders in batches of 50
    private List<Order> orders;
}

// In application.yml
spring:
  jpa:
    properties:
      hibernate:
        default_batch_fetch_size: 20
```

**5. Query-specific Optimizations:**
```java
// Use EXISTS instead of COUNT for existence checks
@Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END " +
       "FROM Order o WHERE o.customer.id = :customerId AND o.status = 'PENDING'")
boolean hasPendingOrders(@Param("customerId") Long customerId);

// Better: Use EXISTS (more efficient)
@Query("SELECT CASE WHEN EXISTS (" +
       "  SELECT 1 FROM Order o " +
       "  WHERE o.customer.id = :customerId AND o.status = 'PENDING'" +
       ") THEN TRUE ELSE FALSE END")
boolean hasPendingOrdersOptimized(@Param("customerId") Long customerId);
```

**6. Pagination with Keyset/Seek Method:**
```java
// Instead of OFFSET (slow for deep pages)
@Query("SELECT o FROM Order o WHERE o.id > :lastId ORDER BY o.id")
List<Order> findNextPage(@Param("lastId") Long lastId, Pageable pageable);

// For multi-column ordering
@Query("""
    SELECT o FROM Order o 
    WHERE (o.createdDate, o.id) > (:lastDate, :lastId)
    ORDER BY o.createdDate, o.id
    """)
List<Order> seekOrders(@Param("lastDate") LocalDateTime lastDate,
                       @Param("lastId") Long lastId,
                       Pageable pageable);
```

**7. Second-level Caching:**
```java
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "products")
@NaturalIdCache
public class Product {
    @NaturalId
    @Column(unique = true)
    private String sku;
    
    // Frequently read, rarely changed
}

// Query caching
@QueryHints({
    @QueryHint(name = "org.hibernate.cacheable", value = "true"),
    @QueryHint(name = "org.hibernate.cacheRegion", value = "productQueries")
})
@Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
List<Product> findByCategory(@Param("categoryId") Long categoryId);
```

**8. Native Query for Complex Operations:**
```java
// For complex analytics that JPQL can't optimize well
@Query(value = """
    WITH order_stats AS (
        SELECT customer_id, 
               COUNT(*) as order_count,
               SUM(total_amount) as total_spent
        FROM orders 
        WHERE created_date >= :startDate
        GROUP BY customer_id
    )
    SELECT c.name, os.order_count, os.total_spent
    FROM customers c
    JOIN order_stats os ON c.id = os.customer_id
    ORDER BY os.total_spent DESC
    LIMIT :limit
    """, nativeQuery = true)
List<Object[]> getTopCustomers(@Param("startDate") LocalDate startDate,
                               @Param("limit") int limit);
```

**9. Monitoring and Profiling:**
```yaml
# Enable query statistics
spring:
  jpa:
    properties:
      hibernate:
        generate_statistics: true
        session.events.log.LOG_QUERIES_SLOWER_THAN_MS: 1000  # Log slow queries
```

**Performance Metrics Tracked:**
1. **Query Count**: Reduce unnecessary queries
2. **Query Time**: Identify slow queries
3. **Fetch Size**: Optimize batch sizes
4. **Cache Hit Ratio**: Monitor cache effectiveness
5. **Connection Wait Time**: Identify pool issues

**Optimization Results:**
- **Query Reduction**: 80% reduction in queries for order listing
- **Response Time**: 3x improvement for complex reports
- **Database Load**: 60% reduction in database CPU usage
- **Memory Usage**: 40% reduction through proper fetching

**Theoretical Keywords:**  
**Comprehensive query optimization strategy**, **N+1 problem elimination**, **Efficient data fetching patterns**, **Performance monitoring integration**, **Database load reduction techniques**

---

### **97. How did you handle pagination and sorting?**

** Answer:**
We implemented a **multi-strategy pagination approach** tailored to different use cases, with careful consideration of performance implications and user experience:

**Pagination Strategy Implementation:**

**1. Standard Spring Data Pagination (for UI navigation):**
```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @GetMapping
    public ResponseEntity<Page<OrderSummary>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdDate,desc") String[] sort) {
        
        // Parse sort parameters
        Sort ordersSort = Sort.by(parseSortOrders(sort));
        
        Pageable pageable = PageRequest.of(page, size, ordersSort);
        Page<OrderSummary> orders = orderService.getOrders(pageable);
        
        // Add custom headers for UI
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(orders.getTotalElements()))
            .header("X-Total-Pages", String.valueOf(orders.getTotalPages()))
            .body(orders);
    }
    
    private List<Sort.Order> parseSortOrders(String[] sort) {
        return Arrays.stream(sort)
            .map(s -> {
                String[] parts = s.split(",");
                return new Sort.Order(
                    parts[1].equalsIgnoreCase("desc") ? 
                        Sort.Direction.DESC : Sort.Direction.ASC,
                    parts[0]
                );
            })
            .collect(Collectors.toList());
    }
}
```

**2. Keyset Pagination (for Infinite Scroll/API consumers):**
```java
@Service
public class OrderService {
    
    public List<OrderSummary> getOrdersAfterCursor(CursorPageRequest request) {
        if (request.getLastId() == null) {
            // First page
            return orderRepository.findFirstPage(
                request.getSize(), 
                parseSort(request.getSort())
            );
        } else {
            // Subsequent pages using keyset
            return orderRepository.findNextPage(
                request.getLastId(),
                request.getLastValue(), // For multi-column sort
                request.getSize(),
                parseSort(request.getSort())
            );
        }
    }
}

// Repository implementation
@Query("""
    SELECT o FROM Order o 
    WHERE (:lastId IS NULL OR 
          (o.createdDate < :lastDate) OR 
          (o.createdDate = :lastDate AND o.id < :lastId))
    ORDER BY o.createdDate DESC, o.id DESC
    """)
List<Order> findOrdersAfterCursor(@Param("lastDate") LocalDateTime lastDate,
                                  @Param("lastId") Long lastId,
                                  Pageable pageable);
```

**3. Slice-based Pagination (for real-time feeds):**
```java
// When total count is expensive or unnecessary
public Slice<OrderActivity> getRecentActivities(Pageable pageable) {
    return orderRepository.findRecentActivities(pageable);
    // No count query executed, just checks if hasNext
}
```

**4. Database-specific Optimizations:**
```java
// PostgreSQL optimized pagination
@Query(value = """
    SELECT * FROM orders 
    WHERE customer_id = :customerId
    ORDER BY created_date DESC, id DESC
    LIMIT :limit
    OFFSET :offset
    """, nativeQuery = true)
List<Order> findCustomerOrdersNative(@Param("customerId") Long customerId,
                                     @Param("limit") int limit,
                                     @Param("offset") int offset);

// With keyset optimization for deep pagination
@Query(value = """
    SELECT * FROM orders 
    WHERE customer_id = :customerId
    AND (created_date, id) < (:lastDate, :lastId)
    ORDER BY created_date DESC, id DESC
    LIMIT :limit
    """, nativeQuery = true)
List<Order> findCustomerOrdersAfterCursor(@Param("customerId") Long customerId,
                                          @Param("lastDate") LocalDateTime lastDate,
                                          @Param("lastId") Long lastId,
                                          @Param("limit") int limit);
```

**5. Sorting Strategy:**
```java
@Component
public class SortStrategyFactory {
    
    public Sort createSort(String sortBy, String direction) {
        // Validate sort field against allowed fields
        validateSortField(sortBy);
        
        // Handle composite sorts
        if (sortBy.contains(",")) {
            return Sort.by(Arrays.stream(sortBy.split(","))
                .map(field -> new Sort.Order(
                    direction.equalsIgnoreCase("desc") ? 
                        Sort.Direction.DESC : Sort.Direction.ASC,
                    field.trim()
                ))
                .collect(Collectors.toList()));
        }
        
        return Sort.by(
            direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC,
            sortBy
        );
    }
    
    private void validateSortField(String field) {
        Set<String> allowedFields = Set.of(
            "createdDate", "updatedDate", "totalAmount", "status"
        );
        
        if (!allowedFields.contains(field)) {
            throw new InvalidSortFieldException(
                "Field '" + field + "' is not sortable");
        }
    }
}
```

**6. Pagination Metadata Enrichment:**
```java
public class PaginatedResponse<T> {
    private List<T> content;
    private PaginationMetadata metadata;
    
    public static <T> PaginatedResponse<T> of(Page<T> page) {
        return new PaginatedResponse<>(
            page.getContent(),
            PaginationMetadata.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build()
        );
    }
}

// Controller response
@GetMapping
public PaginatedResponse<OrderSummary> getOrders(Pageable pageable) {
    Page<OrderSummary> page = orderService.getOrders(pageable);
    return PaginatedResponse.of(page);
}
```

**7. Performance Considerations:**

**Index Design for Pagination:**
```sql
-- Composite index for common pagination sorts
CREATE INDEX idx_orders_created_id ON orders(created_date DESC, id DESC);

-- Covering index for frequent queries
CREATE INDEX idx_orders_status_created 
ON orders(status, created_date DESC) 
INCLUDE (customer_id, total_amount);
```

**Pagination Strategy Selection Matrix:**
| **Use Case** | **Strategy** | **Why** |
|--------------|--------------|---------|
| **Admin UI** | Standard Page | Need total counts, page numbers |
| **Mobile App** | Keyset/Slice | Infinite scroll, fast next page |
| **Export** | Batch Streaming | Large datasets, memory efficient |
| **Real-time Feed** | Slice | No count needed, hasNext sufficient |

**Monitoring and Optimization:**
```java
@Aspect
@Component
public class PaginationMonitor {
    
    @Around("execution(* *..*Repository.*(..)) && args(pageable,..)")
    public Object monitorPagination(ProceedingJoinPoint joinPoint, 
                                   Pageable pageable) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            
            // Alert on inefficient pagination
            if (pageable.getOffset() > 10000 && duration > 1000) {
                log.warn("Deep pagination detected: offset={}, duration={}ms",
                         pageable.getOffset(), duration);
                metrics.recordDeepPagination(pageable.getOffset(), duration);
            }
        }
    }
}
```

**Results Achieved:**
- **Page Load Time**: < 100ms for first 1000 pages
- **Memory Usage**: Constant regardless of dataset size
- **Database Load**: 70% reduction in deep pagination queries
- **User Experience**: Smooth infinite scrolling even with millions of records

**Theoretical Keywords:**  
**Multi-strategy pagination implementation**, **Performance-optimized data retrieval**, **Cursor-based navigation**, **Sorting strategy management**, **Scalable data access patterns**

---

### **98. How did you handle database migrations?**

** Answer:**
We implemented a **comprehensive database migration strategy** using Flyway with strict version control, automated testing, and rollback capabilities:

**Migration Strategy Components:**

**1. Flyway Configuration:**
```yaml
# application.yml
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
    baseline-version: 1
    locations: classpath:db/migration
    table: schema_version
    validate-on-migrate: true
    clean-disabled: true  # Prevent accidental clean in production
    out-of-order: false   # Strict version order
    placeholders:
      tablePrefix: app_
    sql-migration-prefix: V
    sql-migration-separator: __
    sql-migration-suffixes: .sql
```

**2. Migration File Structure:**
```
src/main/resources/db/migration/
├── V1__Initial_schema.sql
├── V2__Add_user_roles.sql
├── V3__Create_product_tables.sql
├── V4__Add_order_status_index.sql
├── V5__Denormalize_customer_name.sql
├── V6__Partition_orders_table.sql
└── R__Rollback_procedures.sql
```

**3. Migration File Examples:**

**V1__Initial_schema.sql:**
```sql
-- Flyway migration (forward-only)
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_created ON users(created_at);
```

**V4__Add_order_status_index.sql:**
```sql
-- Add index for frequent query pattern
CREATE INDEX idx_orders_status_created 
ON orders(status, created_at DESC)
ALGORITHM=INPLACE, LOCK=NONE;  -- Online DDL for MySQL

-- Add comment for documentation
ALTER TABLE orders 
COMMENT 'Orders table - partitioned by month';
```

**V6__Partition_orders_table.sql:**
```sql
-- Major schema change with careful planning
-- 1. Create new partitioned table
CREATE TABLE orders_new (
    -- same structure as orders
) PARTITION BY RANGE (YEAR(created_at) * 100 + MONTH(created_at)) (
    PARTITION p202301 VALUES LESS THAN (202302),
    PARTITION p202302 VALUES LESS THAN (202303),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- 2. Copy data in batches (for large tables)
INSERT INTO orders_new 
SELECT * FROM orders 
WHERE created_at >= '2023-01-01' 
ORDER BY id 
LIMIT 10000;

-- Repeat in application code with progress tracking

-- 3. Atomic switch
RENAME TABLE orders TO orders_old, orders_new TO orders;

-- 4. Cleanup (in separate migration)
DROP TABLE orders_old;
```

**4. Data Migrations with Java Callbacks:**
```java
@Component
public class DataMigrationService {
    
    @Transactional
    @FlywayMigration
    public void migrateCustomerData() {
        // Complex data transformations
        List<Customer> customers = customerRepository.findAll();
        
        customers.forEach(customer -> {
            // Business logic for data migration
            customer.setFullName(
                customer.getFirstName() + " " + customer.getLastName());
            customer.setNormalizedEmail(
                normalizeEmail(customer.getEmail()));
        });
        
        customerRepository.saveAll(customers);
    }
}
```

**5. Rollback Strategy:**
```sql
-- R__Rollback_procedures.sql
-- Stored procedures for emergency rollbacks

CREATE PROCEDURE rollback_to_version(IN target_version VARCHAR(10))
BEGIN
    -- Manual rollback logic
    -- Used only in emergencies
    -- Typically involves:
    -- 1. Backup current data
    -- 2. Revert schema changes
    -- 3. Restore data
END;
```

**6. Migration Testing Strategy:**
```java
@SpringBootTest
@TestPropertySource(properties = {
    "spring.flyway.locations=classpath:db/migration,classpath:db/test"
})
public class MigrationTests {
    
    @Test
    public void testAllMigrationsApplyCleanly() {
        // Flyway auto-applies migrations
        // Test verifies application starts successfully
    }
    
    @Test
    public void testDataMigration() {
        // Test specific data migrations
        dataMigrationService.migrateCustomerData();
        
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers)
            .allMatch(c -> c.getFullName() != null)
            .allMatch(c -> c.getNormalizedEmail() != null);
    }
}
```

**7. Production Migration Process:**

**Pre-migration Checklist:**
1. ✅ Backup database
2. ✅ Run in staging environment
3. ✅ Performance impact assessment
4. ✅ Rollback plan documented
5. ✅ Maintenance window scheduled

**Migration Script:**
```bash
#!/bin/bash
# migrate.sh - Production migration script

echo "Starting database migration..."

# 1. Pre-migration backup
mysqldump -h $DB_HOST -u $DB_USER -p$DB_PASS $DB_NAME > backup_$(date +%Y%m%d_%H%M%S).sql

# 2. Set maintenance mode
curl -X POST https://api.example.com/maintenance/start

# 3. Run Flyway migration
java -jar flyway-commandline.jar migrate

# 4. Verify migration
java -jar flyway-commandline.jar info

# 5. Run data migrations
curl -X POST https://api.example.com/migrate/data

# 6. End maintenance mode
curl -X POST https://api.example.com/maintenance/end

echo "Migration completed successfully"
```

**8. Monitoring and Alerting:**
```yaml
# Prometheus metrics for migrations
management:
  metrics:
    export:
      prometheus:
        enabled: true
  endpoints:
    web:
      exposure:
        include: flyway,prometheus

# Custom metrics
@Component
public class MigrationMetrics {
    
    @Timed(value = "db.migration.duration", description = "Migration duration")
    @EventListener
    public void handleMigrationEvent(FlywayMigrationEvent event) {
        metrics.counter("db.migration.count").increment();
        metrics.gauge("db.migration.version", 
                     event.getMigrationInfo().getVersion().getVersion());
    }
}
```

**Challenges Overcome:**
1. **Zero-downtime migrations**: Using online DDL and careful planning
2. **Large data migrations**: Batch processing with progress tracking
3. **Rollback capabilities**: While Flyway is forward-only, we implemented application-level rollbacks
4. **Team coordination**: Clear migration ownership and communication

**Best Practices Established:**
1. **Small, focused migrations**: Each migration does one thing
2. **Version control**: All migrations in Git
3. **Automated testing**: Every migration tested in CI/CD
4. **Documentation**: Each migration includes business reason
5. **Monitoring**: Track migration performance and issues

**Theoretical Keywords:**  
**Comprehensive migration strategy**, **Version-controlled schema evolution**, **Zero-downtime deployment**, **Automated migration testing**, **Production-safe rollout process**

---

### **99. What challenges did you face with JPA?**

** Answer:**
We faced **several significant challenges with JPA/Hibernate** that required careful design decisions and custom solutions:

**Key Challenges and Solutions:**

**1. N+1 Query Problem:**
**Challenge**: Performance degradation with complex object graphs
**Solution**: 
```java
// Implemented EntityGraph and JOIN FETCH strategy
@EntityGraph(attributePaths = {"items.product", "customer.addresses"})
@Query("SELECT o FROM Order o WHERE o.status = :status")
List<Order> findByStatusWithGraph(@Param("status") OrderStatus status);
```

**2. Lazy Loading in Transactions:**
**Challenge**: `LazyInitializationException` when accessing relationships outside transaction
**Solution**:
```java
// Implemented Open Session/EntityManager in View pattern
@Configuration
public class JpaConfig {
    
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }
}

// And DTO projections for API responses
public interface OrderSummary {
    Long getId();
    String getOrderNumber();
    
    @Value("#{target.customer.name}")
    String getCustomerName();
}
```

**3. Performance with Large Datasets:**
**Challenge**: Memory issues with `findAll()` on large tables
**Solution**:
```java
// Implemented streaming and pagination
@Transactional(readOnly = true)
public void processAllOrders() {
    try (Stream<Order> orderStream = orderRepository.streamAll()) {
        orderStream
            .forEach(this::processOrder);
    }
}

// With batch processing
@Transactional
public void batchUpdateOrders(List<Order> orders) {
    int batchSize = 50;
    for (int i = 0; i < orders.size(); i += batchSize) {
        List<Order> batch = orders.subList(i, Math.min(i + batchSize, orders.size()));
        orderRepository.saveAll(batch);
        entityManager.flush();
        entityManager.clear();  // Clear persistence context
    }
}
```

**4. Complex Query Generation:**
**Challenge**: JPQL limitations for complex analytics
**Solution**:
```java
// Mixed JPQL with native queries
@Query(value = """
    WITH monthly_sales AS (
        SELECT 
            DATE_FORMAT(created_at, '%Y-%m') as month,
            SUM(total_amount) as revenue
        FROM orders
        GROUP BY DATE_FORMAT(created_at, '%Y-%m')
    )
    SELECT * FROM monthly_sales
    ORDER BY month DESC
    """, nativeQuery = true)
List<Object[]> getMonthlyRevenue();
```

**5. Inheritance Mapping:**
**Challenge**: Performance issues with `JOINED` inheritance
**Solution**:
```java
// Switched to SINGLE_TABLE with discriminator
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type")
public abstract class Payment { ... }

@Entity
@DiscriminatorValue("CREDIT_CARD")
public class CreditCardPayment extends Payment { ... }

@Entity  
@DiscriminatorValue("PAYPAL")
public class PayPalPayment extends Payment { ... }
```

**6. Cache Management:**
**Challenge**: Stale cache data in distributed environment
**Solution**:
```java
// Implemented cache invalidation strategy
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, 
       region = "products")
@org.hibernate.annotations.Cache(region = "products")
public class Product {
    // Cache configuration with TTL
}

// Cache invalidation on updates
@Service
public class ProductService {
    
    @CacheEvict(value = "products", key = "#product.id")
    @Transactional
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
```

**7. Versioning and Optimistic Locking:**
**Challenge**: Frequent `OptimisticLockException` in high-concurrency scenarios
**Solution**:
```java
// Implemented retry mechanism
@Retryable(value = OptimisticLockException.class, 
           maxAttempts = 3,
           backoff = @Backoff(delay = 100))
@Transactional
public Order updateOrderWithRetry(Long orderId, OrderUpdate update) {
    Order order = orderRepository.findById(orderId).orElseThrow();
    order.applyUpdate(update);
    return order;
}

// For extreme concurrency, switched to database-level locking
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT o FROM Order o WHERE o.id = :id")
Order findByIdForUpdate(@Param("id") Long id);
```

**8. Schema Evolution:**
**Challenge**: Database changes breaking existing queries
**Solution**:
```java
// Used @Column annotations for explicit mapping
@Entity
@Table(name = "users")
public class User {
    @Column(name = "user_email", length = 150)  // Explicit mapping
    private String email;
    
    // Legacy column support
    @Column(name = "legacy_user_code", insertable = false, updatable = false)
    private String legacyCode;
}
```

**9. Transaction Management:**
**Challenge**: Long-running transactions causing connection pool exhaustion
**Solution**:
```java
// Implemented chunk processing
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void processLargeDataset(List<Data> dataset) {
    int chunkSize = 1000;
    List<List<Data>> chunks = partition(dataset, chunkSize);
    
    for (List<Data> chunk : chunks) {
        processChunk(chunk);
        entityManager.flush();
        entityManager.clear();  // Release memory
    }
}
```

**10. Testing Difficulties:**
**Challenge**: Slow integration tests with database
**Solution**:
```java
// Used Testcontainers for realistic testing
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class OrderRepositoryTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
    
    @Test
    public void testSaveOrder() {
        // Real database tests
    }
}
```

**Lessons Learned:**
1. **JPA is powerful but requires discipline**: Proper configuration and patterns are essential
2. **Performance testing is crucial**: Always test with production-like data volumes
3. **Monitoring and metrics**: Essential for identifying issues early
4. **Team training**: JPA concepts need to be well understood by the team
5. **Balance abstraction with control**: Sometimes native SQL is better than complex JPQL

**Theoretical Keywords:**  
**JPA implementation challenges**, **Performance optimization learnings**, **Production issue resolutions**, **Best practice development**, **Team capability building**

---

### **100. What improvements would you make in DB design?**

** Answer:**
Based on our experience and evolving requirements, I would implement **several strategic improvements** to the database design for better performance, scalability, and maintainability:

**Proposed Improvements:**

**1. Strategic Denormalization:**
```sql
-- Current: Normalized design requiring joins
SELECT o.*, c.name, c.email 
FROM orders o 
JOIN customers c ON o.customer_id = c.id;

-- Proposed: Add frequently accessed customer data to orders
ALTER TABLE orders 
ADD COLUMN customer_name VARCHAR(100),
ADD COLUMN customer_email VARCHAR(150);

-- Update via trigger or application logic
CREATE TRIGGER update_order_customer_info 
AFTER UPDATE ON customers
FOR EACH ROW
BEGIN
    UPDATE orders 
    SET customer_name = NEW.name,
        customer_email = NEW.email
    WHERE customer_id = NEW.id;
END;
```

**2. Partitioning Strategy:**
```sql
-- Partition orders by month for performance
ALTER TABLE orders 
PARTITION BY RANGE (YEAR(created_at) * 100 + MONTH(created_at)) (
    PARTITION p202401 VALUES LESS THAN (202402),
    PARTITION p202402 VALUES LESS THAN (202403),
    PARTITION p202403 VALUES LESS THAN (202404),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Partition customers by region for sharding readiness
ALTER TABLE customers 
PARTITION BY LIST (region_id) (
    PARTITION p_north VALUES IN (1, 2, 3),
    PARTITION p_south VALUES IN (4, 5, 6),
    PARTITION p_east VALUES IN (7, 8, 9),
    PARTITION p_west VALUES IN (10, 11, 12)
);
```

**3. JSON Column Optimization:**
```java
// Store flexible attributes in JSON columns
@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    
    @Column(columnDefinition = "JSON")
    @Convert(converter = AttributesConverter.class)
    private Map<String, Object> attributes;  // Flexible schema
    
    @Column(columnDefinition = "JSON")
    private String specifications;  // Structured JSON
    
    // Generated columns for indexed JSON fields
    @Column(columnDefinition = "VARCHAR(100) GENERATED ALWAYS AS (JSON_UNQUOTE(specifications->'$.brand'))")
    private String brand;
}
```

**4. Materialized Views for Analytics:**
```sql
-- Create materialized view for frequent aggregations
CREATE MATERIALIZED VIEW daily_sales_mv AS
SELECT 
    DATE(created_at) as sale_date,
    COUNT(*) as order_count,
    SUM(total_amount) as total_revenue,
    AVG(total_amount) as avg_order_value
FROM orders
WHERE status = 'DELIVERED'
GROUP BY DATE(created_at);

-- Refresh schedule
CREATE EVENT refresh_daily_sales
ON SCHEDULE EVERY 1 HOUR
DO
    REFRESH MATERIALIZED VIEW daily_sales_mv;
```

**5. Advanced Indexing Strategy:**
```sql
-- Covering indexes for common queries
CREATE INDEX idx_orders_covering ON orders 
(status, created_at DESC, customer_id) 
INCLUDE (total_amount, payment_status);

-- Functional indexes
CREATE INDEX idx_users_lower_email ON users ((LOWER(email)));

-- Partial indexes
CREATE INDEX idx_active_products ON products (category_id, price) 
WHERE active = true AND stock > 0;

-- Composite indexes with column order optimized for queries
CREATE INDEX idx_search_products ON products 
(category_id, brand, price, created_at DESC);
```

**6. Read/Write Separation:**
```java
// Implement read replicas for scaling reads
@Configuration
public class DataSourceConfig {
    
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    public RoutingDataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("write", writeDataSource());
        targetDataSources.put("read", readDataSource());
        
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());
        
        return routingDataSource;
    }
}

// Use @Transactional(readOnly = true) for read operations
@Transactional(readOnly = true)
public List<Order> findCustomerOrders(Long customerId) {
    // Automatically uses read replica
    return orderRepository.findByCustomerId(customerId);
}
```

**7. Event Sourcing for Critical Entities:**
```java
// Instead of updating in-place, store events
@Entity
@Table(name = "order_events")
public class OrderEvent {
    @Id
    private String eventId;
    
    private Long orderId;
    private String eventType;  // CREATED, UPDATED, CANCELLED
    private String eventData;  // JSON payload
    private LocalDateTime timestamp;
    private String userId;
}

// Current state is derived from events
public Order getOrderCurrentState(Long orderId) {
    List<OrderEvent> events = orderEventRepository.findByOrderIdOrderByTimestamp(orderId);
    return Order.replayEvents(events);  // Rebuild from events
}
```

**8. Database Sharding Design:**
```sql
-- Prepare for horizontal scaling
-- Customer-based sharding
CREATE TABLE customers_00 (CHECK (shard_id = 00)) INHERITS (customers);
CREATE TABLE customers_01 (CHECK (shard_id = 01)) INHERITS (customers);

-- Sharding router table
CREATE TABLE shard_mapping (
    entity_type VARCHAR(50),
    entity_id BIGINT,
    shard_id INTEGER,
    PRIMARY KEY (entity_type, entity_id)
);

-- Application-level sharding logic
@Service
public class ShardingService {
    
    public DataSource getShardDataSource(Long customerId) {
        int shardId = customerId % SHARD_COUNT;
        return shardDataSources.get(shardId);
    }
}
```

**9. Advanced Data Types and Features:**
```sql
-- Use database-specific features
-- PostgreSQL arrays for tags
ALTER TABLE products 
ADD COLUMN tags TEXT[];

-- Query: Find products with any of these tags
SELECT * FROM products 
WHERE tags && ARRAY['electronics', 'gadgets'];

-- Full-text search integration
ALTER TABLE products 
ADD COLUMN search_vector tsvector;

CREATE INDEX idx_products_search ON products USING GIN(search_vector);

UPDATE products 
SET search_vector = 
    setweight(to_tsvector('english', name), 'A') ||
    setweight(to_tsvector('english', description), 'B');
```

**10. Monitoring and Observability:**
```sql
-- Add monitoring columns
ALTER TABLE orders 
ADD COLUMN query_time_ms INTEGER,
ADD COLUMN indexed_used BOOLEAN DEFAULT TRUE,
ADD COLUMN execution_plan JSON;

-- Create performance monitoring table
CREATE TABLE query_performance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    query_hash VARCHAR(64),
    table_name VARCHAR(100),
    execution_time_ms INTEGER,
    rows_affected INTEGER,
    indexed_used BOOLEAN,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_query_performance_timestamp (timestamp)
);
```

**Implementation Roadmap:**

**Phase 1 (Immediate - 1 month):**
1. Implement covering indexes
2. Add strategic denormalization
3. Set up query performance monitoring

**Phase 2 (Short-term - 3 months):**
1. Implement read/write separation
2. Add JSON columns for flexible attributes
3. Create materialized views for analytics

**Phase 3 (Medium-term - 6 months):**
1. Implement partitioning
2. Prepare for sharding
3. Add advanced data types

**Phase 4 (Long-term - 12 months):**
1. Full sharding implementation
2. Event sourcing for critical entities
3. Advanced replication strategies

**Expected Benefits:**
- **Performance**: 5-10x improvement for analytical queries
- **Scalability**: Support 10x current user load
- **Maintainability**: Easier schema evolution
- **Flexibility**: Support for new features without schema changes
- **Observability**: Better monitoring and troubleshooting

**Theoretical Keywords:**  
**Strategic database evolution plan**, **Performance and scalability improvements**, **Modern database feature adoption**, **Maintainability enhancements**, **Future-proof architecture design**