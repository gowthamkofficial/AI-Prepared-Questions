Here are the answers focusing on **theory and internal concepts** as an  would expect:

## **Real-time Scenario Questions **

### **81. API is slow due to database – what will you check first?**

** Answer:**
When an API is slow due to database issues, follow a **systematic troubleshooting approach** starting with the most common and easily identifiable problems:

**Immediate Checks (First 5-10 minutes):**

1. **Database Metrics**:
   - CPU utilization (high CPU indicates query problems)
   - Memory usage (insufficient buffer pool)
   - Disk I/O (slow disk access)
   - Connection count (connection pool exhaustion)

2. **Application Logs**:
   - Slow query logs (`spring.jpa.properties.hibernate.generate_statistics: true`)
   - Long-running transactions
   - Connection pool wait times

3. **Specific Query Analysis**:
   - Identify the slow API endpoint
   - Check which database queries it executes
   - Look for N+1 query patterns
   - Check for full table scans

**Common Root Causes (In Order of Likelihood):**

1. **Missing Indexes** (Most Common):
   ```sql
   -- Check query execution plan
   EXPLAIN SELECT * FROM orders WHERE user_id = 123 AND status = 'PENDING';
   -- Look for "type: ALL" (full table scan)
   ```

2. **N+1 Query Problem**:
   ```java
   // Check for lazy loading in loops
   List<Order> orders = orderRepository.findAll();
   orders.forEach(o -> o.getItems().size()); // Each triggers query
   ```

3. **Large Result Sets**:
   ```java
   // Fetching all records without pagination
   List<User> users = userRepository.findAll(); // Millions of records
   ```

4. **Inefficient Joins**:
   ```sql
   -- Cartesian products from missing join conditions
   SELECT * FROM users, orders; -- Instead of JOIN
   ```

5. **Lock Contention**:
   - Table/row locks blocking queries
   - Deadlock situations

**Initial Diagnostic Commands:**

**Database Level:**
```sql
-- MySQL: Show running queries
SHOW PROCESSLIST;
SHOW ENGINE INNODB STATUS;

-- PostgreSQL: Active queries
SELECT * FROM pg_stat_activity WHERE state = 'active';

-- Check locks
SELECT * FROM information_schema.INNODB_LOCKS;
```

**Application Level:**
```yaml
# Enable detailed logging
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.stat: DEBUG
    org.springframework.jdbc.core: TRACE
```

**Quick Wins Checklist:**
1. ✅ Check for missing indexes on WHERE/JOIN/ORDER BY columns
2. ✅ Verify pagination is used for large datasets
3. ✅ Ensure proper connection pool sizing
4. ✅ Check for N+1 query patterns
5. ✅ Review transaction boundaries (not too long)

**Theoretical Keywords:**  
**Systematic performance troubleshooting**, **Root cause prioritization**, **Diagnostic methodology**, **Common performance antipatterns**, **Quick-win identification**

---

### **82. Large table with millions of records – how will you fetch data?**

** Answer:**
For large tables with millions of records, use **pagination strategies that minimize memory usage and database load**, avoiding full table scans and large result set transfers.

**Primary Strategies:**

**1. Offset-based Pagination (Basic but problematic):**
```java
Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
Page<User> users = userRepository.findAll(pageable);
```
**Issue**: `OFFSET` becomes slow for deep pages (database scans all skipped rows).

**2. Keyset Pagination (Recommended):**
```java
@Query("SELECT u FROM User u WHERE u.id > :lastId ORDER BY u.id")
List<User> findNextPage(@Param("lastId") Long lastId, Pageable pageable);
```
**Benefits**: Constant time regardless of page depth, uses index efficiently.

**3. Seek Method (Advanced keyset):**
```java
@Query("""
    SELECT u FROM User u 
    WHERE (u.createdAt, u.id) > (:lastCreatedAt, :lastId)
    ORDER BY u.createdAt, u.id
    """)
List<User> seek(@Param("lastCreatedAt") LocalDateTime lastCreatedAt, 
                @Param("lastId") Long lastId, 
                Pageable pageable);
```

**4. Time-based Partitioning:**
```java
@Query("SELECT u FROM User u WHERE u.createdAt >= :start AND u.createdAt < :end")
List<User> findByDateRange(@Param("start") LocalDateTime start, 
                          @Param("end") LocalDateTime end);
```

**5. Database-side Filtering:**
- **Partitioning**: Physical table partitioning by date/range
- **Materialized Views**: Pre-aggregated data
- **Read Replicas**: Offload read queries

**Implementation Considerations:**

**A. Index Design:**
```sql
-- Composite index for keyset pagination
CREATE INDEX idx_users_created_id ON users(created_at DESC, id ASC);
```

**B. Batch Processing (for background jobs):**
```java
@Transactional
public void processAllUsers() {
    Long lastId = 0L;
    List<User> batch;
    
    do {
        batch = userRepository.findNextBatch(lastId, 1000);
        processBatch(batch);
        lastId = batch.get(batch.size() - 1).getId();
        entityManager.flush();
        entityManager.clear(); // Clear persistence context
    } while (!batch.isEmpty());
}
```

**C. Streaming for Large Exports:**
```java
@Transactional(readOnly = true)
public void exportUsers(OutputStream output) {
    try (Stream<User> userStream = userRepository.streamAll()) {
        userStream.forEach(user -> {
            // Process and write to output
            // Keep batches small to avoid memory issues
        });
    }
}
```

**Performance Optimization Rules:**
1. **Never use `findAll()`** on large tables
2. **Always use WHERE clause** to limit results
3. **Use indexes** on filtering/sorting columns
4. **Consider read replicas** for heavy read loads
5. **Implement time-based archiving** for old data

**Theoretical Keywords:**  
**Scalable data retrieval strategies**, **Keyset vs offset pagination**, **Memory-efficient processing**, **Batch processing patterns**, **Large dataset optimization**

---

### **83. How do you prevent duplicate records?**

** Answer:**
Preventing duplicate records requires a **multi-layered defense strategy** combining application validation, database constraints, and business logic enforcement.

**Defense Layers:**

**1. Database Constraints (Ultimate Protection):**
```sql
-- Unique constraints (most reliable)
ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);
ALTER TABLE users ADD UNIQUE INDEX idx_unique_username (username);

-- Composite uniqueness
ALTER TABLE user_roles ADD UNIQUE (user_id, role_id);
```

**JPA Mapping:**
```java
@Entity
public class User {
    @Column(unique = true)  // Creates unique constraint
    private String email;
    
    @Column(unique = true)
    private String username;
}
```

**2. Application-level Validation (First Line):**
```java
@Service
public class UserService {
    
    @Transactional(readOnly = true)
    public User createUser(UserCreateRequest request) {
        // Check existence before insert
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUsernameException();
        }
        
        return userRepository.save(request.toEntity());
    }
}
```

**3. Upsert Operations (Atomic):**
```java
// Using native query for UPSERT
@Modifying
@Query(value = """
    INSERT INTO users (email, username) 
    VALUES (:email, :username)
    ON DUPLICATE KEY UPDATE 
        updated_at = CURRENT_TIMESTAMP
    """, nativeQuery = true)
int upsertUser(@Param("email") String email, 
               @Param("username") String username);
```

**4. Optimistic Locking for Updates:**
```java
@Entity
public class User {
    @Id
    private Long id;
    
    @Version
    private Integer version;  // Prevents concurrent duplicate updates
    
    private String email;
}
```

**5. Distributed Locking (for distributed systems):**
```java
@Service
public class UserService {
    
    @Autowired
    private RedissonClient redisson;
    
    public User createUserWithLock(UserCreateRequest request) {
        RLock lock = redisson.getLock("user:create:" + request.getEmail());
        
        try {
            lock.lock(10, TimeUnit.SECONDS);  // Acquire lock
            
            // Check and create within lock
            return createUser(request);
        } finally {
            lock.unlock();
        }
    }
}
```

**6. Race Condition Handling:**

**Problem:** Check-then-act race condition:
```java
if (!existsByEmail(email)) {  // Thread 1 checks
    // Thread 2 checks here and also passes
    save(user);  // Both threads save → duplicate
}
```

**Solutions:**
- **Database constraint**: Catches race conditions
- **Synchronized method**: For single JVM
- **Distributed lock**: For multiple JVMs
- **Database locking**: `SELECT ... FOR UPDATE`

**Best Practice Checklist:**
1. ✅ Database unique constraints (non-negotiable)
2. ✅ Application validation (user-friendly errors)
3. ✅ Proper error handling (catch `DataIntegrityViolationException`)
4. ✅ Concurrent access consideration (locks/transactions)
5. ✅ Monitoring (alert on constraint violations)

**Theoretical Keywords:**  
**Multi-layered duplicate prevention**, **Database constraint enforcement**, **Race condition mitigation**, **Concurrent access control**, **Data integrity strategy**

---

### **84. How do you ensure data consistency across multiple tables?**

** Answer:**
Ensuring data consistency across multiple tables requires **transaction management with ACID properties, proper relationship mapping, and business logic enforcement**.

**Consistency Strategies:**

**1. Database Transactions (ACID):**
```java
@Transactional  // All operations succeed or fail together
public void placeOrder(Order order, List<OrderItem> items) {
    // 1. Save order
    orderRepository.save(order);
    
    // 2. Save items (foreign key references order)
    items.forEach(item -> {
        item.setOrder(order);
        orderItemRepository.save(item);
    });
    
    // 3. Update inventory
    items.forEach(item -> {
        inventoryService.reduceStock(item.getProductId(), item.getQuantity());
    });
    
    // All succeed or all rollback
}
```

**2. Foreign Key Constraints:**
```sql
-- Ensures order_items references valid orders
ALTER TABLE order_items 
ADD CONSTRAINT fk_order_items_order 
FOREIGN KEY (order_id) REFERENCES orders(id) 
ON DELETE CASCADE;
```

**3. Cascade Operations:**
```java
@Entity
public class Order {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;  // Save order → save items automatically
}
```

**4. Business Logic in Transactions:**
```java
@Transactional
public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) {
    Account from = accountRepository.findById(fromAccountId)
        .orElseThrow(() -> new AccountNotFoundException(fromAccountId));
    
    Account to = accountRepository.findById(toAccountId)
        .orElseThrow(() -> new AccountNotFoundException(toAccountId));
    
    // Business rules within transaction
    if (from.getBalance().compareTo(amount) < 0) {
        throw new InsufficientFundsException();
    }
    
    from.debit(amount);
    to.credit(amount);
    
    // Both updates in same transaction
    accountRepository.save(from);
    accountRepository.save(to);
    
    // Audit log
    auditRepository.logTransfer(from, to, amount);
}
```

**5. Two-Phase Commit (Distributed Transactions):**
For multiple databases/services:
- **Phase 1**: Prepare (all services ready to commit)
- **Phase 2**: Commit (all commit) or Rollback (all rollback)
- **Use**: JTA, Spring Cloud with distributed transaction coordinator

**6. Eventual Consistency (for distributed systems):**
```java
// Using events with compensating transactions
public void placeOrder(Order order) {
    // 1. Create order (local transaction)
    orderRepository.save(order);
    
    // 2. Publish event for other services
    eventPublisher.publish(new OrderCreatedEvent(order));
    
    // Other services update eventually
    // inventory-service: Reduce stock
    // payment-service: Process payment
    // notification-service: Send confirmation
}
```

**7. Database Triggers (for complex rules):**
```sql
-- Ensure total matches sum of items
CREATE TRIGGER validate_order_total 
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
    DECLARE items_total DECIMAL(10,2);
    
    SELECT SUM(price * quantity) INTO items_total
    FROM order_items WHERE order_id = NEW.id;
    
    IF items_total != NEW.total_amount THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Order total does not match items total';
    END IF;
END;
```

**8. Validation at Multiple Levels:**

**Database Level:**
- Foreign keys
- Check constraints
- Triggers
- Stored procedures

**Application Level:**
- Service layer validation
- Domain model invariants
- Command validation

**Consistency Patterns:**
1. **Immediate**: ACID transactions (strong consistency)
2. **Eventual**: Message queues, sagas (high scalability)
3. **Compensating**: Reverse operations on failure
4. **Pessimistic**: Locks (high consistency, lower concurrency)
5. **Optimistic**: Versioning (higher concurrency, retry on conflict)

**Theoretical Keywords:**  
**Multi-table transaction management**, **Referential integrity enforcement**, **Business rule consistency**, **Distributed consistency patterns**, **ACID vs eventual consistency**

---

### **85. How do you handle concurrent updates?**

** Answer:**
Handling concurrent updates requires **concurrency control mechanisms** that prevent data corruption while maintaining system performance and user experience.

**Concurrency Control Strategies:**

**1. Optimistic Locking (Most Common):**
```java
@Entity
public class Product {
    @Id
    private Long id;
    
    private String name;
    private Integer quantity;
    
    @Version  // Optimistic lock version column
    private Integer version;
}
```

**How it works:**
```
User 1: Read product (version=1, quantity=10)
User 2: Read product (version=1, quantity=10)

User 1: Update product SET quantity=8, version=2 WHERE id=1 AND version=1 ✓
User 2: Update product SET quantity=5, version=2 WHERE id=1 AND version=1 ✗
         → Fails (version is now 2)
```

**2. Pessimistic Locking (for high contention):**
```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT p FROM Product p WHERE p.id = :id")
Product findByIdForUpdate(@Param("id") Long id);

// Usage
@Transactional
public void updateProduct(Long id, ProductUpdate update) {
    Product product = productRepository.findByIdForUpdate(id);
    // Other transactions wait here
    product.applyUpdate(update);
}
```

**3. Database-level Locking:**
```sql
-- SELECT FOR UPDATE (pessimistic)
BEGIN TRANSACTION;
SELECT * FROM products WHERE id = 1 FOR UPDATE;
-- Other transactions blocked until commit
UPDATE products SET quantity = quantity - 1 WHERE id = 1;
COMMIT;
```

**4. Application-level Locking:**
```java
@Service
public class InventoryService {
    
    private final Map<Long, Lock> productLocks = new ConcurrentHashMap<>();
    
    public void updateStock(Long productId, Integer quantityChange) {
        Lock lock = productLocks.computeIfAbsent(productId, 
                     k -> new ReentrantLock());
        
        lock.lock();
        try {
            Product product = productRepository.findById(productId).get();
            product.adjustStock(quantityChange);
            productRepository.save(product);
        } finally {
            lock.unlock();
        }
    }
}
```

**5. Queue-based Processing (for high volume):**
```java
@Component
public class UpdateQueue {
    
    private final BlockingQueue<UpdateRequest> queue = new LinkedBlockingQueue<>();
    
    @PostConstruct
    public void startProcessor() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                UpdateRequest request = queue.take();
                processUpdate(request);  // Sequential processing
            }
        });
    }
    
    public void submitUpdate(UpdateRequest request) {
        queue.offer(request);
    }
}
```

**6. Last Write Wins (with timestamp):**
```java
@Entity
public class Document {
    @Id
    private Long id;
    
    private String content;
    private LocalDateTime lastUpdated;
    
    public void update(String newContent, LocalDateTime updateTime) {
        if (updateTime.isAfter(this.lastUpdated)) {
            this.content = newContent;
            this.lastUpdated = updateTime;
        }
    }
}
```

**7. Conflict Resolution Strategies:**

**Manual Resolution:**
```java
@ExceptionHandler(OptimisticLockException.class)
public ResponseEntity<ConflictResponse> handleOptimisticLock(
        OptimisticLockException ex) {
    
    // Return both versions to client
    Product current = productRepository.findById(productId).get();
    Product attempted = extractFromException(ex);
    
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ConflictResponse(current, attempted));
}
```

**Automatic Retry:**
```java
@Retryable(value = OptimisticLockException.class, maxAttempts = 3)
@Transactional
public void updateWithRetry(ProductUpdate update) {
    Product product = productRepository.findById(update.getId()).get();
    product.applyUpdate(update);
}
```

**Choosing the Right Strategy:**

| **Scenario** | **Recommended Approach** |
|--------------|--------------------------|
| **Low contention, many reads** | Optimistic locking |
| **High contention (auctions)** | Pessimistic locking |
| **Financial transactions** | Pessimistic + retry |
| **Collaborative editing** | Operational transformation |
| **High throughput** | Queue-based processing |

**Theoretical Keywords:**  
**Concurrency control mechanisms**, **Optimistic vs pessimistic locking**, **Conflict detection and resolution**, **Transaction isolation management**, **Scalable update handling**

---

### **86. What happens if two users update same record?**

** Answer:**
When two users update the same record concurrently, the outcome depends on the **concurrency control mechanism** in place, with different behaviors for optimistic vs pessimistic approaches.

**Scenario Analysis:**

**1. No Concurrency Control (Dangerous):**
```
User A: Read product (quantity=10)
User B: Read product (quantity=10)

User A: Update quantity=8
User B: Update quantity=5

Result: quantity=5 (User B's update overwrites User A's)
Problem: Lost update! User A's change is lost.
```

**2. With Optimistic Locking:**
```
User A: Read product (version=1, quantity=10)
User B: Read product (version=1, quantity=10)

User A: UPDATE SET quantity=8, version=2 WHERE id=1 AND version=1 ✓
        (Version increments to 2)

User B: UPDATE SET quantity=5, version=2 WHERE id=1 AND version=1 ✗
        → OptimisticLockException (version mismatch)
        
Result: quantity=8 (User A wins), User B gets exception
```

**3. With Pessimistic Locking:**
```
User A: SELECT ... FOR UPDATE (acquires lock)
        Read product (quantity=10)

User B: SELECT ... FOR UPDATE (blocks, waits for lock)

User A: Update quantity=8
        COMMIT (releases lock)

User B: Gets lock, reads quantity=8
        Update quantity=5
        COMMIT

Result: quantity=5 (sequential execution)
```

**4. Database Isolation Levels Impact:**

**READ COMMITTED (Default):**
- Can see each other's committed changes
- Non-repeatable reads possible

**REPEATABLE READ:**
- Snapshot isolation
- Each sees consistent snapshot

**SERIALIZABLE:**
- Complete isolation
- One executes after another

**5. Real-world Example - Inventory System:**
```java
// Without proper handling - race condition!
public void purchaseProduct(Long productId, Integer quantity) {
    Product product = productRepository.findById(productId).get();
    
    if (product.getStock() >= quantity) {
        product.setStock(product.getStock() - quantity);  // Race condition here!
        productRepository.save(product);
    }
}

// Two simultaneous purchases of last item:
// Both check stock (1), both pass, both set stock to 0
// Result: Stock goes to -1 (impossible!)
```

**6. Solution Patterns:**

**A. Database-driven (Recommended):**
```sql
-- Atomic update
UPDATE products 
SET stock = stock - 1 
WHERE id = 1 AND stock >= 1;
-- Returns rows affected: 1 if successful, 0 if out of stock
```

**B. Optimistic Locking Solution:**
```java
@Transactional
public PurchaseResult purchaseWithOptimisticLock(Long productId, Integer quantity) {
    try {
        Product product = productRepository.findById(productId).get();
        
        if (product.getStock() < quantity) {
            return PurchaseResult.outOfStock();
        }
        
        product.reduceStock(quantity);
        productRepository.save(product);  // OptimisticLockException on conflict
        
        return PurchaseResult.success();
        
    } catch (OptimisticLockException e) {
        // Retry or inform user
        return PurchaseResult.conflict();
    }
}
```

**C. Pessimistic Locking Solution:**
```java
@Transactional
public PurchaseResult purchaseWithPessimisticLock(Long productId, Integer quantity) {
    Product product = productRepository.findByIdWithLock(productId);
    
    if (product.getStock() < quantity) {
        return PurchaseResult.outOfStock();
    }
    
    product.reduceStock(quantity);
    return PurchaseResult.success();
    // Lock released on commit
}
```

**User Experience Considerations:**
1. **Immediate Feedback**: Show error immediately on conflict
2. **Automatic Retry**: Retry operation for user
3. **Merge Changes**: Allow merging conflicting updates
4. **Queue System**: Put request in queue, process sequentially

**Theoretical Keywords:**  
**Concurrent update scenarios**, **Lost update problem**, **Optimistic conflict detection**, **Pessimistic lock sequencing**, **Race condition outcomes**

---

### **87. How does optimistic locking work with `@Version`?**

** Answer:**
Optimistic locking with `@Version` works through a **version number or timestamp mechanism** that detects concurrent modifications by tracking entity state changes across transactions.

**Mechanism:**

**1. Version Field Declaration:**
```java
@Entity
public class Product {
    @Id
    private Long id;
    
    private String name;
    private Integer stock;
    
    @Version  // Marks field as optimistic lock version
    private Integer version;  // Integer, Long, or Timestamp
}
```

**2. How It Works:**

**Initial State:**
```
Database: | id | name | stock | version |
          | 1  | Apple| 100   | 1       |
```

**Transaction A:**
```sql
-- Read
SELECT id, name, stock, version FROM product WHERE id = 1;
-- Returns: version=1

-- Update (increment version)
UPDATE product 
SET name='Apple', stock=90, version=2 
WHERE id=1 AND version=1;
-- Checks: current version (1) matches stored version (1)
-- Updates version to 2
```

**Transaction B (Concurrent):**
```sql
-- Read (same time as A)
SELECT id, name, stock, version FROM product WHERE id = 1;
-- Returns: version=1 (before A's update)

-- Update (later)
UPDATE product 
SET name='Apple', stock=80, version=2 
WHERE id=1 AND version=1;
-- FAILS: version is now 2, not 1
-- Throws OptimisticLockException
```

**3. Version Field Types:**

**Integer/Long:**
```java
@Version
private Long version;  // Incremented on each update
```

**Timestamp:**
```java
@Version
private LocalDateTime version;  // Updated to current time
```

**4. Automatic Version Management:**

**On Persist (INSERT):**
- Version set to initial value (0 for numbers, current time for timestamp)

**On Update:**
- JPA automatically increments version
- UPDATE includes version check in WHERE clause
- If version changed by another transaction → `OptimisticLockException`

**5. Exception Handling:**
```java
@Transactional
public void updateProduct(ProductUpdate update) {
    try {
        Product product = productRepository.findById(update.getId()).get();
        product.applyUpdate(update);
        // JPA automatically handles version increment
    } catch (OptimisticLockException e) {
        // Handle conflict
        throw new ConcurrentModificationException(
            "Product was modified by another user", e);
    }
}
```

**6. Manual Version Check:**
```java
// Sometimes needed for custom updates
@Modifying
@Query("UPDATE Product p SET p.stock = p.stock - :quantity " +
       "WHERE p.id = :id AND p.version = :version")
int updateStockWithVersion(@Param("id") Long id, 
                          @Param("quantity") Integer quantity,
                          @Param("version") Integer version);
```

**7. Benefits:**
- **No Locks**: Doesn't block other readers/writers
- **High Concurrency**: Multiple reads, writes only conflict on commit
- **Scalable**: Works well in distributed systems
- **Automatic**: Minimal application code

**8. Limitations:**
- **Stale Data**: Users can work with stale data
- **Conflict Handling**: Application must handle exceptions
- **Not for High Contention**: Too many conflicts degrade performance

**Use Cases:**
- **Web Applications**: Multiple users editing same entity
- **Shopping Carts**: Concurrent modifications
- **Collaborative Editing**: Document editing systems
- **CQRS Systems**: Optimistic concurrency for commands

**Theoretical Keywords:**  
**Version-based conflict detection**, **Optimistic concurrency control**, **Stale data prevention**, **Automatic version management**, **Non-blocking update mechanism**

---

### **88. Difference between optimistic and pessimistic locking**

** Answer:**
Optimistic and pessimistic locking are **fundamentally different concurrency control strategies** with opposite philosophies: optimistic assumes conflicts are rare, pessimistic assumes they're common.

**Core Philosophy Difference:**
- **Optimistic**: "Let's proceed and check for conflicts later"
- **Pessimistic**: "Let's prevent conflicts from happening"

**Detailed Comparison:**

| **Aspect** | **Optimistic Locking** | **Pessimistic Locking** |
|------------|------------------------|-------------------------|
| **Approach** | Detect conflicts | Prevent conflicts |
| **Mechanism** | Version/timestamp | Database locks |
| **Database Hit** | On commit only | On read and write |
| **Performance** | Better for low conflict | Better for high conflict |
| **Scalability** | High (no locks) | Limited (locks limit concurrency) |
| **Use Case** | Read-heavy, low conflict | Write-heavy, high conflict |
| **Implementation** | `@Version` field | `SELECT ... FOR UPDATE` |
| **Exception** | `OptimisticLockException` | Blocking/wait timeout |
| **Deadlocks** | No | Possible |
| **User Experience** | Retry on conflict | Wait or immediate failure |

**Implementation Examples:**

**Optimistic Locking:**
```java
@Entity
public class Account {
    @Id
    private Long id;
    
    @Version
    private Integer version;
    
    private BigDecimal balance;
}
// Conflict detected at commit time
```

**Pessimistic Locking:**
```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT a FROM Account a WHERE a.id = :id")
Account findByIdForUpdate(@Param("id") Long id);
// Lock acquired at read time
```

**Transaction Flow Comparison:**

**Optimistic Flow:**
```
1. Read data (no lock)
2. Modify data locally
3. Try to commit
4. If conflict → exception → retry or notify
```

**Pessimistic Flow:**
```
1. Read data with lock (others blocked)
2. Modify data
3. Commit (release lock)
```

**Performance Characteristics:**

**Throughput:**
- **Optimistic**: Higher when conflicts rare
- **Pessimistic**: Lower due to locking overhead

**Latency:**
- **Optimistic**: Lower (no wait for locks)
- **Pessimistic**: Higher (wait for locks)

**Resource Usage:**
- **Optimistic**: Less database resources
- **Pessimistic**: More (locks consume memory)

**Real-world Analogy:**
- **Optimistic**: Google Docs (multiple people edit, merge conflicts resolved later)
- **Pessimistic**: Bank vault (one person at a time, others wait)

**Choosing Between Them:**

**Use Optimistic When:**
- Conflicts are rare (< 5% of transactions)
- Read-to-write ratio is high
- System is distributed
- You want maximum scalability

**Use Pessimistic When:**
- Conflicts are common (> 20% of transactions)
- Data consistency is critical (banking)
- Operations are short
- System is single-database

**Hybrid Approach:**
```java
// Start optimistic, escalate to pessimistic on high conflict
public void updateWithAdaptiveLock(Entity entity) {
    int retries = 0;
    while (retries < MAX_RETRIES) {
        try {
            return updateOptimistically(entity);
        } catch (OptimisticLockException e) {
            retries++;
            if (retries >= MAX_RETRIES) {
                return updatePessimistically(entity);
            }
        }
    }
}
```

**Theoretical Keywords:**  
**Conflict detection vs prevention**, **Locking strategy comparison**, **Concurrency control philosophies**, **Performance trade-off analysis**, **Use case based strategy selection**

---

### **89. How do you design entities for performance?**

** Answer:**
Designing entities for performance requires **optimizing both database schema and object-relational mapping** to minimize query complexity, reduce data transfer, and maximize cache efficiency.

**Performance Optimization Strategies:**

**1. Proper Primitive Types:**
```java
// Use primitives for frequently accessed fields
private int quantity;        // Instead of Integer
private double price;        // Instead of Double
private boolean active;      // Instead of Boolean
```

**2. Lazy Loading by Default:**
```java
@OneToMany(fetch = FetchType.LAZY)  // Default, but be explicit
private List<OrderItem> items;

@ManyToOne(fetch = FetchType.LAZY)  // Override default EAGER
private Category category;
```

**3. Selective Field Loading:**
```java
@Entity
public class User {
    @Id
    private Long id;
    
    private String username;
    private String email;
    
    @Basic(fetch = FetchType.LAZY)  // Load on-demand
    @Lob
    private String biography;  // Large text, rarely needed
    
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;  // Large binary
}
```

**4. Denormalization for Read Performance:**
```java
@Entity
public class Order {
    @Id
    private Long id;
    
    // Denormalized fields for frequent queries
    private String customerName;  // Instead of JOIN every time
    private BigDecimal orderTotal; // Pre-calculated
    
    @Transient  // Not persisted, calculated
    public BigDecimal calculateTax() {
        return orderTotal.multiply(TAX_RATE);
    }
}
```

**5. Index Strategy:**
```java
@Entity
@Table(indexes = {
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_status", columnList = "status, createdDate"),
    @Index(name = "idx_user_name", columnList = "lastName, firstName")
})
public class User {
    // Fields used in WHERE, ORDER BY, JOIN
}
```

**6. Inheritance Strategy Selection:**
```java
// SINGLE_TABLE (best performance, worst normalization)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

// JOINED (good normalization, slower queries)
@Inheritance(strategy = InheritanceType.JOINED)

// TABLE_PER_CLASS (avoid for polymorphic queries)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
```

**7. Caching Strategy:**
```java
@Entity
@Cacheable  // Second-level cache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product {
    // Frequently read, rarely changed
}
```

**8. Batch Size Optimization:**
```java
@Entity
public class Department {
    @OneToMany(mappedBy = "department")
    @BatchSize(size = 50)  // Load employees in batches of 50
    private List<Employee> employees;
}
```

**9. Avoid Large Collections:**
```java
// Instead of:
@OneToMany
private List<Order> allOrders;  // Could be millions

// Use:
@OneToMany
@Where(clause = "status = 'ACTIVE'")  // Filter at database level
private List<Order> activeOrders;

// Or query with pagination
Page<Order> recentOrders = orderRepository.findRecent(PageRequest.of(0, 50));
```

**10. DTO Projection for Read Operations:**
```java
public interface UserSummary {
    Long getId();
    String getUsername();
    String getEmail();
    // Only needed fields, no entity overhead
}

@Query("SELECT u.id as id, u.username as username, u.email as email " +
       "FROM User u WHERE u.active = true")
List<UserSummary> findActiveUsersSummary();
```

**11. Database-specific Optimizations:**
```java
@Entity
@Table(name = "orders", 
       indexes = @Index(columnList = "createdDate DESC"),
       comment = "Partitioned by month for performance")
public class Order {
    // MySQL: Consider ENGINE=InnoDB ROW_FORMAT=COMPRESSED
    // PostgreSQL: Consider partitioning
}
```

**12. Audit Fields Optimization:**
```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // Index these for time-based queries
}
```

**Performance Anti-patterns to Avoid:**

1. **Eager Loading Everything**: Load only what's needed
2. **Bidirectional Relationships Everywhere**: Increases complexity
3. **Large Embedded Objects**: Bloat entity size
4. **Cascading All Operations**: Unnecessary overhead
5. **No Indexes**: Full table scans

**Design Checklist for Performance:**
1. ✅ Use LAZY loading for all relationships
2. ✅ Add indexes on query fields
3. ✅ Consider denormalization for frequent reads
4. ✅ Use DTO projections for read-heavy endpoints
5. ✅ Implement proper caching strategy
6. ✅ Batch size configuration for collections
7. ✅ Database-specific optimizations
8. ✅ Regular performance testing

**Theoretical Keywords:**  
**Entity performance optimization principles**, **ORM efficiency design**, **Database schema performance alignment**, **Query optimization through design**, **Memory and I/O efficiency considerations**

---

### **90. When will you use native query instead of JPA?**

** Answer:**
Native queries should be used when **database-specific features, complex operations, or performance optimizations** cannot be achieved through JPQL or the Criteria API, accepting the trade-off of database portability.

**Specific Use Cases for Native Queries:**

**1. Database-specific Functions:**
```java
// PostgreSQL window functions
@Query(value = """
    SELECT id, name, salary,
           RANK() OVER (ORDER BY salary DESC) as rank
    FROM employees
    WHERE department_id = :deptId
    """, nativeQuery = true)
List<Object[]> findEmployeesWithRank(@Param("deptId") Long deptId);
```

**2. Complex Analytics and Reporting:**
```java
// Multiple CTEs (Common Table Expressions)
@Query(value = """
    WITH sales_cte AS (
        SELECT product_id, SUM(quantity) as total_sold
        FROM order_items
        GROUP BY product_id
    ),
    inventory_cte AS (
        SELECT product_id, current_stock
        FROM inventory
    )
    SELECT p.name, s.total_sold, i.current_stock
    FROM products p
    JOIN sales_cte s ON p.id = s.product_id
    JOIN inventory_cte i ON p.id = i.product_id
    ORDER BY s.total_sold DESC
    """, nativeQuery = true)
List<Object[]> getSalesAnalytics();
```

**3. Full-text Search:**
```java
// MySQL full-text search
@Query(value = """
    SELECT * FROM products 
    WHERE MATCH(name, description) 
    AGAINST(:searchTerm IN NATURAL LANGUAGE MODE)
    """, nativeQuery = true)
List<Product> fullTextSearch(@Param("searchTerm") String searchTerm);
```

**4. Geospatial Queries:**
```java
// PostgreSQL PostGIS
@Query(value = """
    SELECT *, ST_Distance(location, ST_MakePoint(:lon, :lat)) as distance
    FROM stores
    WHERE ST_DWithin(location, ST_MakePoint(:lon, :lat), :radius)
    ORDER BY distance
    """, nativeQuery = true)
List<Store> findNearbyStores(@Param("lat") double lat, 
                            @Param("lon") double lon, 
                            @Param("radius") double radius);
```

**5. Bulk Operations:**
```java
// Efficient bulk update
@Modifying
@Query(value = """
    UPDATE products 
    SET price = price * 1.1 
    WHERE category_id = :categoryId
    AND last_updated < :cutoffDate
    """, nativeQuery = true)
@Transactional
int bulkUpdatePrices(@Param("categoryId") Long categoryId,
                    @Param("cutoffDate") LocalDate cutoffDate);
```

**6. Stored Procedures:**
```java
// Calling stored procedures
@Query(value = "CALL calculate_monthly_sales(:month, :year)", 
       nativeQuery = true)
@Procedure
List<SalesReport> generateSalesReport(@Param("month") int month,
                                     @Param("year") int year);
```

**7. Performance-critical Operations:**
```java
// When JPQL generates inefficient SQL
@Query(value = """
    SELECT /*+ INDEX(orders idx_orders_customer_date) */ *
    FROM orders 
    WHERE customer_id = :customerId
    AND order_date >= :startDate
    """, nativeQuery = true)
List<Order> findCustomerOrdersOptimized(@Param("customerId") Long customerId,
                                       @Param("startDate") LocalDate startDate);
```

**8. Database-specific JSON Operations:**
```java
// PostgreSQL JSONB operations
@Query(value = """
    SELECT * FROM users 
    WHERE metadata->>'preferences' LIKE '%dark_mode%'
    AND metadata @> '{"active": true}'::jsonb
    """, nativeQuery = true)
List<User> findUsersWithDarkMode();
```

**9. Legacy Database Integration:**
```java
// Complex legacy schema that doesn't map well to entities
@Query(value = """
    SELECT 
        cust.cust_no as customerNumber,
        ord.ord_ref as orderReference,
        SUM(item.qty * item.price) as totalAmount
    FROM legacy_customer cust
    JOIN legacy_orders ord ON cust.cust_id = ord.cust_id
    JOIN legacy_order_items item ON ord.ord_id = item.ord_id
    WHERE cust.status = 'ACTIVE'
    GROUP BY cust.cust_no, ord.ord_ref
    """, nativeQuery = true)
List<CustomerOrderSummary> getLegacyCustomerOrders();
```

**Decision Framework:**

**Use Native Query When:**
1. ✅ Need database-specific features/functions
2. ✅ Complex analytics/reporting queries
3. ✅ Performance optimization needed (query hints, specific indexes)
4. ✅ Working with legacy/complex schemas
5. ✅ Full-text or geospatial search
6. ✅ Bulk operations efficiency critical

**Use JPQL When:**
1. ✅ Database portability needed
2. ✅ Simple to moderate complexity queries
3. ✅ Type safety important
4. ✅ Object-oriented querying preferred
5. ✅ Maintainability and readability prioritized

**Best Practices for Native Queries:**

**1. Parameter Binding (Security):**
```java
// GOOD: Use named parameters
@Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
User findByEmail(@Param("email") String email);

// BAD: String concatenation (SQL injection risk)
@Query(value = "SELECT * FROM users WHERE email = '" + email + "'", nativeQuery = true)
```

**2. Result Mapping:**
```java
// Map to DTO
public interface UserSummary {
    Long getId();
    String getUsername();
    String getEmail();
}

@Query(value = """
    SELECT id, username, email 
    FROM users WHERE active = true
    """, nativeQuery = true)
List<UserSummary> findActiveUsers();
```

**3. Testing:**
```java
@Test
public void testNativeQuery() {
    // Test with in-memory database (H2)
    // May need different SQL for different databases
}
```

**4. Documentation:**
```java
/**
 * Native query for PostgreSQL-specific full-text search.
 * Uses PostgreSQL's tsvector/tsquery for performance.
 * Not portable to other databases.
 */
@Query(value = "...", nativeQuery = true)
```

**Performance Consideration:**
Native queries can be **20-30% faster** for complex operations but lose **portability and type safety**.

**Theoretical Keywords:**  
**Database-specific feature utilization**, **Performance vs portability trade-off**, **Complex query optimization**, **Legacy system integration**, **Analytics and reporting requirements**