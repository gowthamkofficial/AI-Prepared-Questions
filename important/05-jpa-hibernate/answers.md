# Spring Data JPA / Hibernate Interview Questions – Answers



### 1. What is JPA?
* **Java Persistence API (JPA)** is a Java **specification** for object-relational mapping (ORM) that standardizes how Java objects are persisted to relational databases.
* Provides a set of **interfaces, annotations, and patterns** for managing relational data in Java applications.
* It's **not an implementation** - it's the standard that implementations like Hibernate, EclipseLink, and OpenJPA follow.

### 2. What is Hibernate?
* Hibernate is the most popular **implementation** of the JPA specification.
* It's an **open-source ORM framework** that maps Java objects to database tables and vice versa.
* Provides additional features beyond JPA (like proprietary annotations, caching strategies, and performance tuning options).

### 3. Difference between JPA and Hibernate.
| Aspect | JPA | Hibernate |
| :--- | :--- | :--- |
| **Nature** | **Specification/API** (set of rules and standards) | **Implementation** of JPA specification |
| **Relationship** | Defines the **what** (what features should exist) | Defines the **how** (how to implement those features) |
| **Vendor Lock-in** | No vendor lock-in - can switch implementations | Using Hibernate-specific features creates vendor lock-in |
| **Features** | Standard features only | JPA features + additional Hibernate-specific features |

### 4. What is ORM?
* **Object-Relational Mapping** is a programming technique that **converts data between incompatible type systems** - objects in Java to tables in relational databases.
* **Key Benefits:**
    * Eliminates boilerplate JDBC code
    * Provides database independence
    * Handles object relationships automatically
    * Supports inheritance mapping
* **Drawbacks:** Performance overhead if not configured properly, learning curve.

### 5. What is an Entity?
* An **Entity** is a **plain Java object (POJO)** that is mapped to a database table.
* Must follow specific rules:
    * Must be annotated with `@Entity`
    * Must have a no-argument constructor
    * Must have at least one field annotated with `@Id` (primary key)
    * Should implement `equals()` and `hashCode()` methods
    * Should not be `final`

### 6. What is `@Entity` annotation?
* Marks a Java class as a **JPA entity**, meaning its instances can be stored in and retrieved from a database table.
* **Requirements:**
    * Class must have a public or protected no-argument constructor
    * Class must not be declared `final`
    * Methods or persistent instance variables must not be declared `final`
* Example: `@Entity public class Employee { ... }`

### 7. What is a Primary Key?
* A **unique identifier** for each row in a database table.
* Ensures that each record can be uniquely identified.
* In JPA, primary key fields are annotated with `@Id`.
* Can be **simple** (single field) or **composite** (multiple fields) using `@IdClass` or `@EmbeddedId`.

### 8. What is `@Id`?
* Annotation used to mark a **field or property** as the primary key of an entity.
* Can be applied to a single field (simple primary key) or multiple fields (composite primary key).
* **Example:**
    ```java
    @Id
    private Long id;
    ```

### 9. What is `@GeneratedValue`?
* Annotation used with `@Id` to specify how the primary key should be **generated automatically**.
* **Strategies:**
    * `GenerationType.IDENTITY`: Database auto-increment column (MySQL, SQL Server)
    * `GenerationType.SEQUENCE`: Database sequence (Oracle, PostgreSQL)
    * `GenerationType.TABLE`: Uses a database table to generate keys
    * `GenerationType.AUTO`: Lets persistence provider choose strategy

### 10. What is `@Table`?
* Annotation used to specify the **database table details** for an entity.
* **Optional** - if omitted, entity name is used as table name.
* **Attributes:**
    * `name`: Specifies table name
    * `schema`: Database schema name
    * `catalog`: Database catalog name
* **Example:** `@Table(name = "employees", schema = "hr")`

### 11. What is `@Column`?
* Annotation used to specify **column details** for a field/property.
* **Optional** - if omitted, field name is used as column name.
* **Common Attributes:**
    * `name`: Column name
    * `nullable`: Whether column can be null
    * `unique`: Whether column has unique constraint
    * `length`: Maximum length for String columns
    * `precision`, `scale`: For decimal columns

### 12. What is a Repository?
* A **Data Access Object (DAO)** that provides abstraction for data access operations.
* In Spring Data JPA, repositories are **interfaces** that extend `JpaRepository`, `CrudRepository`, etc.
* **Responsibilities:**
    * Encapsulate data access logic
    * Reduce boilerplate code
    * Provide standard CRUD operations
    * Support query methods

### 13. What is `JpaRepository`?
* A **Spring Data JPA interface** that provides JPA-specific extensions to `CrudRepository`.
* **Extends:** `PagingAndSortingRepository` and `QueryByExampleExecutor`
* **Key Features:**
    * CRUD operations
    * Pagination and sorting
    * Batch operations
    * Flush management
    * JPA-specific methods like `findAll()`, `findById()`, `save()`, `delete()`

### 14. Difference between `CrudRepository` and `JpaRepository`.
| Feature | `CrudRepository` | `JpaRepository` |
| :--- | :--- | :--- |
| **Interface Hierarchy** | Base interface | Extends `PagingAndSortingRepository` which extends `CrudRepository` |
| **Methods** | Basic CRUD operations only | All `CrudRepository` methods + JPA-specific methods |
| **Pagination/Sorting** | No | Yes (inherited from `PagingAndSortingRepository`) |
| **Batch Operations** | No | Yes (e.g., `saveAll()`, `deleteAllInBatch()`) |
| **Flush Management** | No | Yes (e.g., `flush()`, `saveAndFlush()`) |
| **Use Case** | Basic CRUD needs | Full JPA functionality with pagination and batch operations |

### 15. What is persistence context?
* A **set of managed entity instances** where for any persistent entity identity, there is a unique entity instance.
* **Characteristics:**
    * Maintains a cache of entities (first-level cache)
    * Tracks entity state changes (dirty checking)
    * Ensures transaction isolation
* **Types in JPA:**
    * **Transaction-scoped:** Created per transaction (most common)
    * **Extended:** Spans multiple transactions

### 16. What is `EntityManager`?
* The **primary interface** for interacting with the persistence context.
* **Responsibilities:**
    * Creating and removing entity instances
    * Finding entities by primary key
    * Managing entity lifecycle
    * Executing queries
    * Controlling transactions
* **Obtained from:** `EntityManagerFactory` or injected via `@PersistenceContext`

### 17. What is `@Transactional`?
* Annotation that **defines the scope of a database transaction**.
* **Applied at:** Method or class level
* **Key Behaviors:**
    * **Atomicity:** All operations succeed or all fail
    * **Consistency:** Maintains data integrity
    * **Isolation:** Controls concurrent access
    * **Durability:** Committed changes persist
* **Best Practice:** Apply at service layer, not repository layer

### 18. What is lazy loading?
* A **fetching strategy** where related entities are **loaded only when accessed**.
* **Default for:** `@OneToMany` and `@ManyToMany` relationships
* **Benefits:** Better performance, less memory usage
* **Drawback:** Can cause `LazyInitializationException` if accessed outside session
* **Configured with:** `fetch = FetchType.LAZY`

### 19. What is eager loading?
* A **fetching strategy** where related entities are **loaded immediately** with the parent entity.
* **Default for:** `@ManyToOne` and `@OneToOne` relationships
* **Benefits:** No lazy initialization exceptions
* **Drawback:** Performance overhead, potential for loading unnecessary data
* **Configured with:** `fetch = FetchType.EAGER`

### 20. What is mapping in JPA?
* The process of defining **how Java objects relate to database tables**.
* **Types of Mappings:**
    * **Basic Mapping:** Simple fields to columns (`@Basic`)
    * **Relationship Mapping:** Between entities (`@OneToOne`, `@OneToMany`, etc.)
    * **Inheritance Mapping:** Object inheritance to tables (`@Inheritance`)
    * **Embedded Mapping:** Value objects (`@Embeddable`, `@Embedded`)
    * **Collection Mapping:** Collections of basic/embedded types



### 21. Difference between `save()` and `saveAndFlush()`.
| Aspect | `save()` | `saveAndFlush()` |
| :--- | :--- | :--- |
| **Operation** | Persists entity, may not write to DB immediately | Persists entity and **immediately writes to database** |
| **Flush Behavior** | Waits for transaction commit or flush | Forces immediate synchronization with database |
| **Transaction Boundary** | Changes visible only after commit | Changes visible immediately to other transactions |
| **Use Case** | Normal save operations | When you need immediate ID generation or want to catch constraint violations early |
| **Performance** | Better (batched with other operations) | Slightly slower (immediate DB write) |

### 22. What is first-level cache?
* **Persistence context cache** that exists within a single `EntityManager`/`Session`.
* **Characteristics:**
    * Automatic and always enabled
    * Scoped to current session/transaction
    * Prevents duplicate database queries for same entity
    * Cleared when session closes
* **Benefits:** Reduces database hits, improves performance

### 23. What is second-level cache?
* **Shared cache** across multiple `EntityManager` instances (across sessions).
* **Characteristics:**
    * Optional, needs explicit configuration
    * Shared by entire application
    * Requires cache provider (EhCache, Infinispan, Hazelcast)
    * Can be configured per entity or collection
* **Use Case:** Read-heavy data that rarely changes

### 24. Difference between `getOne()` and `findById()`.
| Aspect | `getOne()` | `findById()` |
| :--- | :--- | :--- |
| **Return Type** | Returns a **proxy/reference** (lazy) | Returns `Optional<T>` (eager) |
| **Database Hit** | **No immediate database call** | **Immediate database call** |
| **Exception** | Throws `EntityNotFoundException` on access if not found | Returns `Optional.empty()` if not found |
| **Use Case** | When you only need reference (e.g., setting relationships) | When you need actual data immediately |
| **Performance** | Better for reference-only scenarios | Better when data is needed immediately |

### 25. What is JPQL?
* **Java Persistence Query Language** - an object-oriented query language for JPA entities.
* **Characteristics:**
    * Works with entities and their fields, not database tables/columns
    * Database independent
    * Supports polymorphism
    * Case-insensitive for keywords, case-sensitive for entity/field names
* **Example:** `SELECT e FROM Employee e WHERE e.department.name = :deptName`

### 26. Difference between JPQL and SQL.
| Aspect | JPQL | SQL |
| :--- | :--- | :--- |
| **Abstraction Level** | Object-oriented (works with entities) | Database-oriented (works with tables) |
| **Portability** | Database independent | Database specific (dialects) |
| **Syntax** | Similar to SQL but uses entity/field names | Uses table/column names |
| **Polymorphism** | Supports inheritance/polymorphism | No direct support |
| **Case Sensitivity** | Keywords case-insensitive, entities case-sensitive | Typically case-insensitive |

### 27. What are native queries?
* **SQL queries** executed directly against the database.
* **Characteristics:**
    * Database specific
    * Bypasses JPA abstraction
    * Can use database-specific features
    * Less portable
* **Use Cases:**
    * Complex queries not supported by JPQL
    * Database-specific features
    * Stored procedure calls
* **Annotation:** `@Query(nativeQuery = true, value = "...")`

### 28. What is pagination in JPA?
* Technique to retrieve data in **chunks/pages** rather than all at once.
* **Implemented using:**
    * `Pageable` parameter in repository methods
    * `PageRequest` to create pageable objects
    * `Page` or `Slice` as return types
* **Benefits:**
    * Better performance for large datasets
    * Reduced memory consumption
    * Better user experience for UIs
* **Example:** `Page<Employee> findAll(Pageable pageable);`

### 29. What is sorting in JPA?
* Arranging query results in **specific order**.
* **Implemented using:**
    * `Sort` parameter in repository methods
    * `Sort.by()` to create sort criteria
    * Can be combined with pagination
* **Sort Directions:** `ASC` (ascending), `DESC` (descending)
* **Example:** `List<Employee> findAll(Sort sort);`

### 30. What is `@Query` annotation?
* Annotation used to define **custom queries** in repository methods.
* **Supports:** JPQL and native SQL queries
* **Features:**
    * Named parameters (`:paramName`)
    * Positional parameters (`?1`)
    * Pagination support
    * Native query support
* **Example:**
    ```java
    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<Employee> findHighEarners(@Param("minSalary") BigDecimal salary);
    ```

### 31. What are derived query methods?
* **Query methods automatically derived** from method names in repository interfaces.
* **Pattern:** `findBy[Property][Comparator][And/Or]...`
* **Supported Keywords:** `And`, `Or`, `Between`, `LessThan`, `GreaterThan`, `Like`, `In`, `Null`, `NotNull`, `OrderBy`
* **Example:** `List<Employee> findByDepartmentNameAndSalaryGreaterThan(String deptName, BigDecimal salary);`
* **Limitation:** Complex queries may be hard to express

### 32. What is `@OneToOne` mapping?
* Represents a **one-to-one relationship** between two entities.
* **Example:** Employee ↔ EmployeeProfile (one employee has one profile)
* **Implementation:**
    ```java
    @Entity
    public class Employee {
        @OneToOne
        @JoinColumn(name = "profile_id")
        private EmployeeProfile profile;
    }
    ```
* **Owning Side:** Has the foreign key column
* **Inverse Side:** Mapped by the owning side

### 33. What is `@OneToMany` mapping?
* Represents a **one-to-many relationship** (parent to children).
* **Example:** Department ↔ Employees (one department has many employees)
* **Default Fetch:** LAZY
* **Implementation:**
    ```java
    @Entity
    public class Department {
        @OneToMany(mappedBy = "department")
        private List<Employee> employees;
    }
    ```

### 34. What is `@ManyToOne` mapping?
* Represents a **many-to-one relationship** (child to parent).
* **Example:** Employee ↔ Department (many employees belong to one department)
* **Default Fetch:** EAGER
* **Owning Side:** Always the many side (has foreign key)
* **Implementation:**
    ```java
    @Entity
    public class Employee {
        @ManyToOne
        @JoinColumn(name = "department_id")
        private Department department;
    }
    ```

### 35. What is `@ManyToMany` mapping?
* Represents a **many-to-many relationship** between two entities.
* **Requires:** Join table
* **Example:** Student ↔ Course (many students take many courses)
* **Implementation:**
    ```java
    @Entity
    public class Student {
        @ManyToMany
        @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
        )
        private List<Course> courses;
    }
    ```

### 36. What is cascade?
* Defines how **operations on parent entity propagate to related entities**.
* **Cascade Types:**
    * `ALL`: All operations cascade
    * `PERSIST`: Only persist operation cascades
    * `MERGE`: Only merge operation cascades
    * `REMOVE`: Only remove operation cascades
    * `REFRESH`: Only refresh operation cascades
    * `DETACH`: Only detach operation cascades
* **Example:** `@OneToMany(cascade = CascadeType.ALL)`

### 37. What is orphan removal?
* Automatically **removes child entities** when they're removed from the parent's collection.
* **Similar to:** `CascadeType.REMOVE` but more specific
* **Difference:** Orphan removal works when child is removed from collection; cascade remove works when parent is removed
* **Example:** `@OneToMany(orphanRemoval = true)`
* **Use Case:** When child cannot exist without parent

### 38. What is `@Embeddable` and `@Embedded`?
* **Value Object pattern** for composing entities from reusable components.
* **`@Embeddable`:** Marks a class as embeddable (value object)
* **`@Embedded`:** Marks a field as containing an embedded object
* **Example:**
    ```java
    @Embeddable
    public class Address {
        private String street;
        private String city;
    }
    
    @Entity
    public class Employee {
        @Embedded
        private Address address;
    }
    ```
* **Benefits:** Code reuse, better encapsulation

### 39. What is optimistic locking?
* **Concurrency control strategy** that assumes conflicts are rare.
* **Mechanism:** Version/timestamp column
* **Annotation:** `@Version` on a field
* **Behavior:**
    * Each update increments version
    * If versions don't match, `OptimisticLockException` is thrown
    * Requires retry logic
* **Use Case:** Read-heavy applications, low contention scenarios

### 40. What is pessimistic locking?
* **Concurrency control strategy** that assumes conflicts are common.
* **Mechanism:** Database locks (row-level or table-level)
* **Lock Modes:**
    * `PESSIMISTIC_READ`: Shared lock
    * `PESSIMISTIC_WRITE`: Exclusive lock
* **Behavior:**
    * Locks acquired when reading
    * Other transactions blocked until lock released
* **Use Case:** Write-heavy applications, high contention scenarios
* **Drawback:** Can cause deadlocks

##  Senior / Architect Level

### 41. Explain Hibernate architecture.
* **Layered Architecture:**
    1. **Application Layer:** Uses Hibernate APIs
    2. **Hibernate Core:** Core ORM functionality
    3. **Database Layer:** JDBC/JTA
* **Key Components:**
    * **SessionFactory:** Thread-safe, immutable factory for creating Sessions
    * **Session:** Single-threaded unit of work, wraps JDBC connection
    * **Transaction:** Optional, manages atomic units of work
    * **ConnectionProvider:** Manages database connections
    * **TransactionFactory:** Manages transactions
* **Supporting Services:**
    * **Query Services:** HQL, Criteria, Native SQL
    * **Caching:** First-level, second-level, query cache
    * **Interceptor/Event System:** For custom behavior

### 42. How does persistence context work internally?
* **Internal Structure:**
    * **Entity Map:** Stores entity instances keyed by entity type and ID
    * **Entity Entry:** Tracks entity state (managed, detached, removed)
    * **Snapshot:** Original state of entity for dirty checking
* **Lifecycle States:**
    * **Transient:** Just created, not associated with PC
    * **Managed:** Associated with PC, tracked for changes
    * **Detached:** Previously managed, no longer associated
    * **Removed:** Scheduled for deletion
* **Dirty Checking Mechanism:**
    * Compares current state with snapshot
    * Automatic at flush time
    * Updates only changed fields

### 43. How do you optimize JPA performance?
* **Query Optimization:**
    * Use appropriate fetch strategies
    * Implement pagination for large results
    * Use projections/DTOs instead of entities
    * Avoid N+1 queries
* **Caching Strategy:**
    * Enable second-level cache for read-heavy data
    * Use query cache for frequent identical queries
* **Batch Operations:**
    * Use `@BatchSize` for collections
    * Implement JDBC batching
* **Connection Management:**
    * Proper connection pool sizing
    * Statement caching
* **Monitoring:** Use Hibernate statistics, slow query logs

### 44. What is N+1 problem?
* **Performance issue** where one query for parent entities triggers N additional queries for child entities.
* **Scenario:** Loading 100 employees, then making 100 separate queries for their departments
* **Causes:** Lazy loading accessed in loop, improper fetch strategy
* **Detection:** Monitoring query count, Hibernate statistics
* **Impact:** Severe performance degradation, especially with large datasets

### 45. How do you solve N+1 issue?
* **Join Fetch:** Use `JOIN FETCH` in JPQL
    * `SELECT e FROM Employee e JOIN FETCH e.department`
* **Entity Graph:** Use `@EntityGraph` annotation
* **Batch Size:** Use `@BatchSize` annotation on collections
* **Fetch Subselect:** Use `@Fetch(FetchMode.SUBSELECT)`
* **DTO Projections:** Select only needed fields
* **Eager Loading:** For frequently accessed relationships (use carefully)

### 46. What is `fetch join`?
* **JPQL/HQL construct** to eagerly load associations in a single query.
* **Syntax:** `JOIN FETCH associationPath`
* **Benefits:**
    * Solves N+1 problem
    * Single database roundtrip
    * Better performance
* **Limitations:**
    * Can cause cartesian product (duplicate rows)
    * Pagination issues with collection fetches
* **Example:** `SELECT e FROM Employee e JOIN FETCH e.department JOIN FETCH e.projects`

### 47. What is `@EntityGraph`?
* **Annotation-based approach** to define fetch plans dynamically.
* **Types:**
    * **Named Entity Graph:** Defined on entity class
    * **Ad-hoc Entity Graph:** Defined on repository method
* **Benefits:**
    * Declarative fetch strategy
    * Can be reused
    * Dynamic at runtime
* **Example:**
    ```java
    @EntityGraph(attributePaths = {"department", "projects"})
    Employee findWithDepartmentAndProjectsById(Long id);
    ```

### 48. How do you design efficient entity relationships?
* **Principles:**
    * **Bidirectional vs Unidirectional:** Prefer unidirectional when possible
    * **Owning Side:** Properly define owning side for relationships
    * **Lazy by Default:** Use lazy loading, fetch eagerly only when needed
    * **Avoid Circular References:** Can cause serialization issues
    * **Use Value Objects:** For reusable components
* **Relationship Design:**
    * `@ManyToOne` for foreign key (most efficient)
    * Consider denormalization for performance
    * Use indexes on foreign key columns

### 49. How do you handle large data volumes?
* **Pagination:** Always paginate large result sets
* **Streaming:** Use result streaming for processing
* **Batch Processing:** JDBC batch updates
* **Stateless Session:** For bulk operations
* **Partitioning:** Database-level partitioning
* **Archiving:** Move historical data to archive tables
* **Read Replicas:** Offload read operations
* **CQRS Pattern:** Separate read/write models

### 50. What is batch processing in Hibernate?
* **Processing multiple entities** in batches to reduce database roundtrips.
* **Configuration:**
    ```properties
    hibernate.jdbc.batch_size=50
    hibernate.order_inserts=true
    hibernate.order_updates=true
    ```
* **StatelessSession:** For bulk operations without persistence context overhead
* **JDBC Batch:** More efficient than individual statements
* **Considerations:** Identity generation conflicts, transaction size

### 51. How do you handle transactions across multiple databases?
* **Distributed Transactions:**
    * **JTA (Java Transaction API):** For XA-compliant resources
    * **Two-Phase Commit (2PC):** For atomicity across databases
* **Patterns:**
    * **Saga Pattern:** Compensating transactions
    * **Eventual Consistency:** Accept temporary inconsistency
    * **Transactional Outbox:** Store events in local transaction
* **Challenges:**
    * Performance overhead
    * Increased complexity
    * Partial failure handling

### 52. What is multi-tenancy in Hibernate?
* **Single application instance** serving multiple tenants (customers/organizations).
* **Strategies:**
    * **Separate Database:** Each tenant has own database
    * **Separate Schema:** Each tenant has own schema in same database
    * **Discriminator Column:** All tenants share tables, distinguished by tenant_id
* **Implementation:** `MultiTenantConnectionProvider`, `CurrentTenantIdentifierResolver`
* **Considerations:** Data isolation, performance, backup/restore

### 53. How do you handle schema evolution?
* **Migration Tools:**
    * **Flyway:** Version-based migrations
    * **Liquibase:** Change-log based migrations
* **Strategies:**
    * **Backward Compatible Changes:** Add columns, not remove
    * **Versioned Releases:** Schema changes with application releases
    * **Zero-Downtime Deployments:** Blue-green, canary releases
* **JPA Support:** `schema-generation` properties for auto-DDL

### 54. How do you manage database migrations?
* **Principles:**
    * **Version Control:** Migrations in version control
    * **Idempotent:** Migrations can run multiple times safely
    * **Rollback Support:** Plan for rollback scenarios
    * **Testing:** Test migrations in staging
* **Process:**
    1. Development: Create migration scripts
    2. Code Review: Review migration scripts
    3. Testing: Apply in test environments
    4. Production: Apply during maintenance window/rolling update

### 55. How do you implement auditing?
* **JPA Callbacks:** `@PrePersist`, `@PreUpdate`, `@PostLoad`, etc.
* **`@EntityListeners`:** For reusable auditing logic
* **Spring Data JPA Auditing:**
    * `@CreatedDate`, `@LastModifiedDate`
    * `@CreatedBy`, `@LastModifiedBy`
    * Enable with `@EnableJpaAuditing`
* **Hibernate Envers:** For full audit history with versioning
* **Custom Solutions:** Audit tables with triggers

### 56. How do you handle soft deletes?
* **Mark as deleted** instead of physically deleting.
* **Implementation:**
    * Add `deleted` boolean flag or `deleted_at` timestamp
    * Filter deleted records in queries
    * Use `@Where` annotation: `@Where(clause = "deleted = false")`
    * Use `@SQLDelete` for custom delete SQL
* **Considerations:**
    * Index performance
    * Foreign key constraints
    * Query complexity
    * Storage growth

### 57. What are common JPA anti-patterns?
* **Entity as DTO:** Exposing entities directly to presentation layer
* **Over-fetching:** Loading unnecessary relationships/columns
* **N+1 Queries:** Due to lazy loading in loops
* **Large Transactions:** Holding transactions open too long
* **Ignoring Caching:** Not using appropriate cache strategies
* **Poor Indexing:** Missing indexes on foreign keys/frequently queried columns
* **Inheritance Misuse:** Using table-per-class for deep hierarchies
* **Bidirectional Overuse:** Creating circular references unnecessarily

### 58. How do you ensure data consistency?
* **ACID Properties:**
    * **Atomicity:** Proper transaction boundaries
    * **Consistency:** Database constraints, validation
    * **Isolation:** Appropriate isolation levels
    * **Durability:** Proper commit/rollback handling
* **Database Constraints:**
    * Primary keys, foreign keys, unique constraints
    * Check constraints, not null constraints
* **Application Level:**
    * Validation (Bean Validation API)
    * Business logic validation
    * Optimistic/pessimistic locking

### 59. How do you secure database access?
* **Principle of Least Privilege:** Minimal database permissions
* **Connection Security:**
    * Encrypted connections (TLS/SSL)
    * Credential management (vaults, not plaintext)
* **SQL Injection Prevention:**
    * Use parameterized queries (JPQL, Criteria API)
    * Never concatenate SQL
* **Row-Level Security:** Database-level security policies
* **Audit Logging:** Track data access/modification
* **Encryption:** Encrypt sensitive data at rest

### 60. When should you NOT use JPA / Hibernate?
* **Scenarios:**
    * **Simple CRUD:** When JDBC or simpler ORM suffices
    * **Complex Reporting:** When complex analytical queries are needed
    * **High Performance Requirements:** When every microsecond counts
    * **Stored Procedure Heavy:** When business logic is in database
    * **Legacy Databases:** With non-standard schemas
    * **Micro-batch Processing:** When working with data streams
    * **Limited Memory:** Embedded/edge computing scenarios
* **Alternatives:**
    * **JDBC Template:** More control, less magic
    * **MyBatis:** SQL-centric approach
    * **JOOQ:** Type-safe SQL building
    * **NoSQL Clients:** For non-relational data