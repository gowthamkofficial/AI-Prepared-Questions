Here are the answers with **examples and bolded keywords**:

## **Spring Data JPA **

### **36. What is ORM?**
- **Object-Relational Mapping** technique
- **Maps Java objects** to database tables  
  *Example: `User` class → `users` table*
- **Automates persistence** - no manual SQL needed
- **Solves impedance mismatch** between OOP and RDBMS
- **Provides abstraction layer** over database
- **Handles CRUD operations** automatically

**How ORM Works:**
```java
// Java Object
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
}

// Maps to Database Table
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);
```

**Benefits:**
- **Productivity** - less boilerplate SQL code
- **Database independence** - switch DBs easily
- **Type safety** - compile-time checking
- **Caching** - improves performance

**Theoretical Keywords:**  
**Object-Relational Mapping**, **Impedance mismatch**, **Abstraction layer**, **Auto-persistence**, **CRUD operations**

---

### **37. What is Hibernate?**
- **Most popular ORM framework** for Java
- **Implements JPA specification** (Java Persistence API)
- **Open source** - part of Red Hat JBoss
- **Handles object-relational mapping** automatically
- **Provides caching** (first and second level)
- **Supports HQL** (Hibernate Query Language)
- **Database independent** - works with MySQL, Oracle, PostgreSQL, etc.

**Key Features:**
- **Lazy loading** - loads data on demand
- **Dirty checking** - auto-detects changes
- **Transaction management**
- **Connection pooling**
- **Automatic schema generation**

**Basic Hibernate Example:**
```java
// Hibernate Configuration (hibernate.cfg.xml)
SessionFactory sessionFactory = new Configuration()
    .configure("hibernate.cfg.xml")
    .addAnnotatedClass(User.class)
    .buildSessionFactory();

// Using Hibernate Session
try (Session session = sessionFactory.openSession()) {
    Transaction tx = session.beginTransaction();
    
    User user = new User("John", "john@email.com");
    session.save(user);  // Saves to database
    
    tx.commit();
}
```

**Spring Boot Integration:**
```yaml
# application.yml
spring:
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        show_sql: true
```

**Theoretical Keywords:**  
**JPA implementation**, **ORM framework**, **Session management**, **HQL**, **Caching**

---

### **38. What is Spring Data JPA?**
- **Part of Spring Data family** - abstraction over JPA
- **Reduces boilerplate code** for data access
- **Repository abstraction** with common CRUD operations
- **Automatic query generation** from method names  
  *Example: `findByEmail(String email)` auto-generates query*
- **Paging and sorting** support
- **Integration with Spring ecosystem** (transactions, security)

**Key Features:**
- **Repository interfaces** - no implementation needed
- **Query methods** - declarative query creation
- **Custom queries** with `@Query` annotation
- **Auditing support** (`@CreatedDate`, `@LastModifiedDate`)
- **Specification interface** for dynamic queries

**Example:**
```java
// Repository interface (NO implementation needed)
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Auto-generated query from method name
    List<User> findByLastName(String lastName);
    
    // Custom query with @Query
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmailAddress(String email);
    
    // Pagination
    Page<User> findByActiveTrue(Pageable pageable);
}

// Usage in Service
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAddress(email);
    }
}
```

**Theoretical Keywords:**  
**Repository abstraction**, **Auto-query generation**, **Spring integration**, **CRUD operations**, **Pagination**

---

### **39. Difference between JPA and Hibernate**

| **Aspect** | **JPA** | **Hibernate** |
|------------|---------|---------------|
| **Type** | Specification/Interface | Implementation |
| **Purpose** | Defines standards | Implements standards |
| **Annotations** | `@Entity`, `@Id`, `@Table` | JPA annotations + Hibernate-specific |
| **Query Language** | JPQL (Java Persistence Query Language) | HQL (Hibernate Query Language) |
| **Vendor Lock-in** | No (can switch implementations) | Yes (Hibernate-specific features) |
| **Extra Features** | Standard features only | Additional features (caching, filters) |

**JPA (Interface/Specification):**
- **Java Persistence API** - standard specification
- **Defined in javax.persistence package**  
  *Example: `javax.persistence.Entity`*
- **Portable** - can switch implementations
- **Annotations-based** configuration
- **EntityManager** interface for operations

**Hibernate (Implementation):**
- **Implements JPA specification**
- **Additional features beyond JPA**  
  *Example: Extra annotations, caching strategies*
- **Session interface** (Hibernate-specific)
- **More configuration options**
- **Better performance tuning**

**Code Comparison:**

**Pure JPA:**
```java
// Using EntityManager (JPA standard)
@PersistenceContext
private EntityManager entityManager;

public User findById(Long id) {
    return entityManager.find(User.class, id);
}
```

**Hibernate-specific:**
```java
// Using Session (Hibernate-specific)
@Autowired
private SessionFactory sessionFactory;

public User findById(Long id) {
    Session session = sessionFactory.getCurrentSession();
    return session.get(User.class, id);
}
```

**Spring Boot Default:**
- **Spring Boot uses JPA interface** with Hibernate implementation
- **You code to JPA interfaces** but get Hibernate benefits
- **Best of both worlds**

**Theoretical Keywords:**  
**Specification vs Implementation**, **Portability**, **Standard vs Extended features**, **EntityManager vs Session**

---

### **40. Difference between `CrudRepository` and `JpaRepository`**

| **Interface** | **Package** | **Methods** | **Features** | **When to Use** |
|---------------|-------------|-------------|--------------|-----------------|
| **`CrudRepository`** | `org.springframework.data.repository` | Basic CRUD | Save, find, delete, count | Simple CRUD needs |
| **`JpaRepository`** | `org.springframework.data.jpa.repository` | All CRUD + JPA-specific | Flush, batch delete, pagination | Full JPA features needed |

**`CrudRepository` (Basic CRUD):**
```java
public interface CrudRepository<T, ID> {
    <S extends T> S save(S entity);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    long count();
    void delete(T entity);
    void deleteAll();
    // NO pagination, NO flush methods
}

// Usage for simple needs
public interface UserRepository extends CrudRepository<User, Long> {
    // Only basic CRUD operations available
}
```

**`JpaRepository` (Extended Features):**
```java
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
    List<T> findAll();
    List<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
    
    <S extends T> List<S> saveAll(Iterable<S> entities);
    
    void flush();  // Force sync with database
    <S extends T> S saveAndFlush(S entity);
    
    void deleteInBatch(Iterable<T> entities);
    void deleteAllInBatch();
    
    T getOne(ID id);  // Returns proxy (lazy)
}
```

**Example Usage:**

**Simple CRUD - Use `CrudRepository`:**
```java
public interface ProductRepository extends CrudRepository<Product, Long> {
    // Basic operations only
    // save(), findById(), delete(), count()
}

@Service
public class ProductService {
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
```

**Advanced Features - Use `JpaRepository`:**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Pagination support
    Page<User> findByActiveTrue(Pageable pageable);
    
    // Batch operations
    @Transactional
    public void deactivateUsers(List<Long> userIds) {
        List<User> users = findAllById(userIds);
        users.forEach(u -> u.setActive(false));
        saveAll(users);  // Batch save
        flush();  // Force immediate write
    }
}

@Service
public class UserService {
    public Page<User> getActiveUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return userRepository.findByActiveTrue(pageable);
    }
}
```

**Hierarchy:**
```
Repository (marker interface)
    ↑
CrudRepository (basic CRUD)
    ↑
PagingAndSortingRepository (adds pagination)
    ↑
JpaRepository (adds JPA-specific methods)
```

**Choose based on needs:**
- **`CrudRepository`** - Simple CRUD, no pagination needed
- **`JpaRepository`** - Need pagination, batch operations, flush control

**Theoretical Keywords:**  
**Basic vs Extended CRUD**, **Pagination support**, **Batch operations**, **Flush control**

---

### **41. What is an entity?**
- **Java class mapped to database table**
- **Annotated with `@Entity`**
- **Represents persistent data** in application
- **Each instance** = row in table
- **Properties** = columns in table

**Entity Requirements:**
1. **`@Entity` annotation**
2. **No-arg constructor** (public or protected)
3. **`@Id` field** - primary key
4. **Not final** class or methods
5. **Implement Serializable** (optional, for caching)

**Entity Example:**
```java
@Entity  // Marks as JPA entity
@Table(name = "users")  // Custom table name
public class User implements Serializable {
    
    @Id  // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", nullable = false, length = 100)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private Integer age;
    
    @Temporal(TemporalType.DATE)  // Date only (no time)
    private Date birthDate;
    
    @Enumerated(EnumType.STRING)  // Store as string ("ACTIVE", "INACTIVE")
    private Status status;
    
    @Lob  // Large object (CLOB/BLOB)
    private String biography;
    
    @Transient  // Not persisted
    private String temporaryData;
    
    @CreatedDate  // Auto-set on creation
    private LocalDateTime createdAt;
    
    @LastModifiedDate  // Auto-updated
    private LocalDateTime updatedAt;
    
    // Required: No-arg constructor
    public User() {}
    
    // All-args constructor
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and setters
    // hashCode(), equals(), toString()
}
```

**Entity Relationships:**
```java
@Entity
public class Order {
    @Id
    private Long id;
    
    // Many-to-One relationship
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // One-to-Many relationship
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
}

@Entity
public class OrderItem {
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
```

**Theoretical Keywords:**  
**Persistent class**, **Table mapping**, **@Entity annotation**, **Database representation**

---

### **42. What is `@Id` and `@GeneratedValue`?**
- **`@Id`** - Marks primary key field
- **`@GeneratedValue`** - How primary key is generated

**`@Id` (Primary Key):**
```java
@Entity
public class User {
    @Id  // Marks as primary key
    private Long id;
}
```

**`@GeneratedValue` Strategies:**

1. **`GenerationType.IDENTITY`** - Database auto-increment
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;  // DB: AUTO_INCREMENT
   ```

2. **`GenerationType.SEQUENCE`** - Database sequence
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                   generator = "user_seq")
   @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ")
   private Long id;  // Uses USER_SEQ sequence
   ```

3. **`GenerationType.TABLE`** - Separate table for IDs
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, 
                   generator = "user_table")
   @TableGenerator(name = "user_table", table = "ID_GEN")
   private Long id;  // Uses ID_GEN table
   ```

4. **`GenerationType.AUTO`** - Provider chooses (default)
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)  // Hibernate chooses
   private Long id;
   ```

**Custom Generator:**
```java
@Id
@GeneratedValue(generator = "uuid")
@GenericGenerator(name = "uuid", strategy = "uuid2")
private String id;  // UUID as primary key
```

**Composite Primary Key:**
```java
@Entity
@IdClass(UserRoleId.class)  // Composite key class
public class UserRole {
    @Id
    private Long userId;
    
    @Id
    private Long roleId;
}

// Composite key class
public class UserRoleId implements Serializable {
    private Long userId;
    private Long roleId;
}
```

**Theoretical Keywords:**  
**Primary key annotation**, **Key generation strategies**, **Auto-increment**, **Sequence**, **UUID**

---

### **43. What is `@Transactional`?**
- **Annotation for declarative transaction management**
- **Wraps method** in database transaction
- **Automatically handles** begin, commit, rollback
- **Spring-managed** - no manual transaction code

**Key Attributes:**
- **`propagation`** - How transactions interact
- **`isolation`** - Transaction isolation level
- **`readOnly`** - Optimization for read operations
- **`timeout`** - Transaction timeout (seconds)
- **`rollbackFor`** - Exceptions triggering rollback
- **`noRollbackFor`** - Exceptions NOT triggering rollback

**Usage Examples:**

**Basic Transaction:**
```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional  // Entire method in transaction
    public User createUser(User user) {
        // If any exception occurs, all DB changes rolled back
        User savedUser = userRepository.save(user);
        
        // More DB operations
        auditRepository.logCreation(savedUser.getId());
        
        return savedUser;  // Transaction commits here
    }
}
```

**With Propagation:**
```java
@Transactional(propagation = Propagation.REQUIRED)  // Default
public void methodA() {
    // Uses existing transaction or creates new
}

@Transactional(propagation = Propagation.REQUIRES_NEW)
public void methodB() {
    // Always creates new transaction
    // Suspends existing transaction if any
}

@Transactional(propagation = Propagation.NESTED)
public void methodC() {
    // Nested transaction within existing
    // Can rollback independently
}
```

**With Isolation Level:**
```java
@Transactional(isolation = Isolation.READ_COMMITTED)  // Default
public User updateUser(User user) {
    // Prevents dirty reads
    return userRepository.save(user);
}

@Transactional(isolation = Isolation.SERIALIZABLE)
public void criticalOperation() {
    // Highest isolation, prevents phantom reads
}
```

**Read-Only Optimization:**
```java
@Transactional(readOnly = true)  // Optimized for reads
public List<User> getAllUsers() {
    return userRepository.findAll();
    // No flush at end, better performance
}
```

**Rollback Configuration:**
```java
@Transactional(rollbackFor = {ValidationException.class, 
                              DataAccessException.class})
public void processOrder(Order order) {
    // Rolls back on these specific exceptions
    
    if (order.getAmount() <= 0) {
        throw new ValidationException("Invalid amount");  // ROLLBACK
    }
}

@Transactional(noRollbackFor = {NotificationException.class})
public void sendNotification(User user) {
    // Commit even if notification fails
    notificationService.send(user);
}
```

**Service Layer Best Practice:**
```java
@Service
@Transactional  // Class-level - all public methods transactional
public class OrderService {
    
    public Order createOrder(Order order) {
        // Transactional by default
        return orderRepository.save(order);
    }
    
    @Transactional(readOnly = true)
    public Order getOrder(Long id) {
        // Read-only transaction
        return orderRepository.findById(id).orElse(null);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void auditOrder(Order order) {
        // Separate transaction for audit
        auditRepository.log("Order created", order.getId());
    }
}
```

**Theoretical Keywords:**  
**Declarative transactions**, **ACID properties**, **Propagation behavior**, **Isolation levels**, **Rollback rules**

---

### **44. Difference between `save()` and `saveAndFlush()`**

| **Method** | **Immediate Write** | **Returns** | **Use Case** | **Performance** |
|------------|---------------------|-------------|--------------|-----------------|
| **`save()`** | No (delayed until commit) | Saved entity | Normal saves | Better (batched) |
| **`saveAndFlush()`** | Yes (immediate) | Saved entity | Need immediate ID/validation | Slower |

**`save()` (Default - Delayed):**
```java
@Transactional
public void createUsers(List<User> users) {
    for (User user : users) {
        userRepository.save(user);  // Only in memory, not in DB yet
        // user.getId() might be null if using identity generation
    }
    // All saves flushed to DB at transaction commit
}

// Benefits:
// - Batch optimization
// - Single transaction
// - Better performance
```

**`saveAndFlush()` (Immediate):**
```java
@Transactional
public User createUserWithValidation(User user) {
    // Save immediately
    User savedUser = userRepository.saveAndFlush(user);
    
    // Now we have the generated ID
    Long userId = savedUser.getId();
    
    // Do something that needs the ID
    String userCode = "USER-" + userId;
    savedUser.setCode(userCode);
    
    // Update with the code
    return userRepository.save(savedUser);
}

// Another example - need immediate constraint checking
@Transactional
public void processOrder(Order order) {
    orderRepository.saveAndFlush(order);  // Immediate FK constraint check
    
    // Now safe to create order items
    for (OrderItem item : order.getItems()) {
        item.setOrder(order);  // Needs order ID
        itemRepository.save(item);
    }
}
```

**When to use each:**

**Use `save()` when:**
- Batch operations
- Performance critical
- Don't need immediate ID
- Normal CRUD operations

**Use `saveAndFlush()` when:**
- Need generated ID immediately
- Constraint validation needed
- Following operations depend on saved data
- Debugging/troubleshooting

**Technical Difference:**

```java
// Under the hood:
public <S extends T> S save(S entity) {
    if (entityInformation.isNew(entity)) {
        em.persist(entity);  // Makes persistent
        return entity;
    } else {
        return em.merge(entity);  // Updates
    }
    // NO em.flush() called
}

public <S extends T> S saveAndFlush(S entity) {
    S result = save(entity);
    em.flush();  // Forces synchronization with database
    return result;
}
```

**Example Scenario:**
```java
@Service
public class RegistrationService {
    
    @Transactional
    public User registerUser(UserRegistrationRequest request) {
        // Validate
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }
        
        // Create user
        User user = new User(request.getName(), request.getEmail());
        
        // Need ID for confirmation token
        userRepository.saveAndFlush(user);  // Get ID now
        
        // Create confirmation token with user ID
        String token = generateToken(user.getId());
        tokenRepository.save(new ConfirmationToken(user.getId(), token));
        
        // Send email with token
        emailService.sendConfirmation(user.getEmail(), token);
        
        return user;
    }
}
```

**Theoretical Keywords:**  
**Immediate vs Delayed persistence**, **Flush operation**, **Transaction boundary**, **Performance optimization**