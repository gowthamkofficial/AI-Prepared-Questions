Here are the answers with **examples and bolded keywords**:

## **JPA and Hibernate Internals **

### **52. What is the N+1 select problem?**
- **Performance issue** in ORM where 1 query triggers N additional queries
- **Happens with lazy loading** when accessing relationships
- **Wastes database resources** and slows application

**Example Scenario:**
```java
@Entity
public class Department {
    @Id
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;  // LAZY loaded
}

@Entity 
public class Employee {
    @Id
    private Long id;
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
}
```

**Problem Code (Causes N+1):**
```java
// Query 1: Get all departments
List<Department> departments = departmentRepository.findAll();

for (Department dept : departments) {
    // Query N: Get employees for each department (lazy load)
    // Each department.getEmployees() triggers separate query!
    System.out.println("Dept: " + dept.getName() + 
                       ", Employees: " + dept.getEmployees().size());
}

// If there are 100 departments:
// Query 1: SELECT * FROM departments
// Query 2: SELECT * FROM employees WHERE dept_id = 1
// Query 3: SELECT * FROM employees WHERE dept_id = 2
// ...
// Query 101: SELECT * FROM employees WHERE dept_id = 100
// TOTAL: 1 + 100 = 101 queries (N+1 problem!)
```

**Theoretical Keywords:**  
**Performance issue**, **Lazy loading problem**, **Multiple queries**, **Database roundtrips**

---

### **53. How do you solve the N+1 problem?**
**Solution 1: Use `JOIN FETCH` in JPQL**
```java
@Query("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees")
List<Department> findAllWithEmployees();
// Single query with JOIN
```

**Solution 2: Use Entity Graphs**
```java
@EntityGraph(attributePaths = {"employees"})
@Query("SELECT d FROM Department d")
List<Department> findAllWithEmployeesGraph();

// Or define named entity graph
@NamedEntityGraph(
    name = "Department.withEmployees",
    attributeNodes = @NamedAttributeNode("employees")
)
@Entity
public class Department {
    // ...
}

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @EntityGraph("Department.withEmployees")
    List<Department> findAll();
}
```

**Solution 3: Use `@Query` with `LEFT JOIN FETCH`**
```java
@Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees e LEFT JOIN FETCH e.projects")
List<Department> findAllWithEmployeesAndProjects();
// Fetches multiple levels
```

**Solution 4: Use `FetchType.EAGER` (Not Recommended)**
```java
@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
private List<Employee> employees;
// Loads everything always - can cause performance issues
```

**Solution 5: Batch Fetching**
```java
@Entity
public class Department {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "department")
    @BatchSize(size = 10)  // Loads employees in batches of 10
    private List<Employee> employees;
}
// Instead of 100 queries, does 10 queries (100/10)
```

**Solution 6: Use DTO Projections**
```java
public interface DepartmentDTO {
    Long getId();
    String getName();
    @Value("#{target.employees.size()}")
    Integer getEmployeeCount();
}

@Query("SELECT d.id as id, d.name as name, " +
       "COUNT(e) as employeeCount " +
       "FROM Department d LEFT JOIN d.employees e " +
       "GROUP BY d.id, d.name")
List<DepartmentDTO> findAllWithEmployeeCount();
// Single optimized query
```

**Best Practices:**
1. **Always use `JOIN FETCH`** for known needed relationships
2. **Use Entity Graphs** for dynamic fetching strategies
3. **Consider DTO projections** when you don't need full entities
4. **Monitor query performance** with `spring.jpa.show-sql=true`

**Theoretical Keywords:**  
**JOIN FETCH**, **Entity Graphs**, **Batch fetching**, **DTO projections**, **Query optimization**

---

### **54. What is JPQL?**
- **Java Persistence Query Language** (JPQL)
- **Object-oriented query language** for JPA entities
- **Similar to SQL** but works with entities, not tables
- **Database independent** - converted to SQL by JPA provider
- **Type-safe** when used with JPA criteria API

**JPQL vs SQL:**
```sql
-- SQL (database tables)
SELECT * FROM employees WHERE salary > 50000 AND department_id = 1;

-- JPQL (entity objects)
SELECT e FROM Employee e WHERE e.salary > 50000 AND e.department.id = 1
```

**JPQL Features:**
```java
// Basic SELECT
@Query("SELECT e FROM Employee e WHERE e.active = true")
List<Employee> findActiveEmployees();

// JOIN operations
@Query("SELECT e FROM Employee e JOIN e.department d WHERE d.name = :deptName")
List<Employee> findByDepartmentName(@Param("deptName") String deptName);

// Aggregation
@Query("SELECT AVG(e.salary) FROM Employee e")
Double findAverageSalary();

// UPDATE operation
@Modifying
@Query("UPDATE Employee e SET e.salary = e.salary * 1.1 WHERE e.department.id = :deptId")
@Transactional
int giveRaise(@Param("deptId") Long deptId);

// DELETE operation
@Modifying
@Query("DELETE FROM Employee e WHERE e.lastLoginDate < :date")
@Transactional
int deleteInactiveEmployees(@Param("date") LocalDate date);

// Named parameters
@Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :min AND :max")
List<Employee> findBySalaryRange(@Param("min") Double min, @Param("max") Double max);
```

**Type-safe with Criteria API:**
```java
// JPQL: SELECT e FROM Employee e WHERE e.name LIKE '%John%'
// Equivalent Criteria API:
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
Root<Employee> root = query.from(Employee.class);
query.select(root)
     .where(cb.like(root.get("name"), "%John%"));
```

**Theoretical Keywords:**  
**Object-oriented query language**, **Entity-based queries**, **Database independent**, **Type-safe**

---

### **55. Difference between JPQL and native query**

| **Aspect** | **JPQL** | **Native Query** |
|------------|----------|------------------|
| **Language** | Object-oriented (entities) | Database-specific SQL |
| **Portability** | Database independent | Database specific |
| **Type Safety** | Compile-time checking | Runtime checking only |
| **Syntax** | Uses entity/field names | Uses table/column names |
| **Performance** | Optimized by JPA provider | Direct database execution |
| **Security** | Less prone to SQL injection | More risk of SQL injection |

**JPQL Example:**
```java
@Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
List<Employee> findByDepartment(@Param("deptName") String deptName);
// Portable across databases
// Type-safe
// Uses entity field names
```

**Native Query Example:**
```java
@Query(value = "SELECT * FROM employees e " +
               "JOIN departments d ON e.department_id = d.id " +
               "WHERE d.name = :deptName", 
       nativeQuery = true)
List<Employee> findByDepartmentNative(@Param("deptName") String deptName);
// Database-specific SQL
// Faster for complex queries
// Uses actual table/column names
```

**Native Query with Projection:**
```java
public interface EmployeeSummary {
    Long getId();
    String getName();
    String getDepartmentName();
}

@Query(value = "SELECT e.id as id, e.name as name, d.name as departmentName " +
               "FROM employees e JOIN departments d ON e.department_id = d.id",
       nativeQuery = true)
List<EmployeeSummary> findEmployeeSummaries();
```

**When to use Native Query:**
1. **Complex database-specific features** (window functions, CTEs)
2. **Stored procedure calls**
3. **Optimized queries** that JPA can't generate efficiently
4. **Database-specific functions** not supported in JPQL

**When to use JPQL:**
1. **Database portability** needed
2. **Type safety** important
3. **Simple to moderate queries**
4. **Object-oriented** data access

**Theoretical Keywords:**  
**Portable vs Database-specific**, **Type safety**, **SQL injection risk**, **Performance considerations**

---

### **56. What is DTO projection?**
- **Data Transfer Object projection** - selects only needed fields
- **Improves performance** by fetching less data
- **Reduces memory usage** compared to full entities
- **Prevents lazy loading issues** by fetching eagerly

**Problem with Entity Projection:**
```java
// Fetches ALL fields from Employee table
@Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
List<Employee> findByDepartment(Long deptId);
// Loads: id, name, salary, address, phone, email, etc.
// May trigger lazy loading for relationships
```

**Solution with DTO Projection:**
```java
// Create DTO class
public class EmployeeDTO {
    private Long id;
    private String name;
    private String departmentName;
    
    public EmployeeDTO(Long id, String name, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
    }
    // getters
}

// Query with constructor expression
@Query("SELECT NEW com.example.dto.EmployeeDTO(e.id, e.name, e.department.name) " +
       "FROM Employee e WHERE e.department.id = :deptId")
List<EmployeeDTO> findDTOByDepartment(@Param("deptId") Long deptId);
// Only fetches: id, name, department.name
```

**Benefits:**
1. **Better performance** - less data transferred
2. **No lazy loading issues** - all data eagerly fetched
3. **Custom data shape** - combine fields from multiple entities
4. **Immutable objects** - can make DTOs immutable

**Theoretical Keywords:**  
**Data Transfer Object**, **Partial data fetching**, **Performance optimization**, **Constructor expressions**

---

### **57. Interface-based vs class-based projections**

**Interface-based Projections (Spring Data):**
```java
// Define interface with getter methods
public interface EmployeeSummary {
    Long getId();
    String getName();
    
    // Nested projections
    DepartmentInfo getDepartment();
    
    interface DepartmentInfo {
        String getName();
        String getLocation();
    }
    
    // Dynamic values with @Value
    @Value("#{target.name + ' (' + target.department.name + ')'}")
    String getDisplayName();
}

// Repository method
@Query("SELECT e.id as id, e.name as name, " +
       "e.department.name as departmentName, " +
       "e.department.location as departmentLocation " +
       "FROM Employee e")
List<EmployeeSummary> findAllSummaries();

// Usage
List<EmployeeSummary> summaries = repository.findAllSummaries();
for (EmployeeSummary s : summaries) {
    System.out.println(s.getId() + ": " + s.getName() + 
                       " - " + s.getDepartment().getName());
}
```

**Class-based Projections:**
```java
// DTO class with constructor
public class EmployeeDTO {
    private final Long id;
    private final String name;
    private final String departmentName;
    
    // Constructor must match query SELECT order
    public EmployeeDTO(Long id, String name, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
    }
    // getters only (immutable)
}

// Repository method
@Query("SELECT NEW com.example.dto.EmployeeDTO(e.id, e.name, d.name) " +
       "FROM Employee e JOIN e.department d")
List<EmployeeDTO> findAllDTOs();
```

**Comparison:**

| **Aspect** | **Interface-based** | **Class-based** |
|------------|---------------------|-----------------|
| **Type** | Interface with getters | Concrete class |
| **Mutability** | Dynamic proxy (immutable) | Can be mutable/immutable |
| **Constructor** | Not needed | Required for JPQL |
| **Nested projections** | Supported | Manual handling |
| **Dynamic values** | `@Value` SpEL support | Manual in constructor |
| **Usage** | Simpler, no class needed | More control |

**When to use:**
- **Use Interface-based**: Simple projections, nested data, dynamic values
- **Use Class-based**: Complex logic in constructor, validation, business methods

**Theoretical Keywords:**  
**Interface projections**, **Class projections**, **Dynamic proxies**, **Constructor expressions**

---

### **58. What is pagination?**
- **Technique to retrieve data in chunks/pages**
- **Prevents loading all data** at once
- **Improves performance** and memory usage
- **Essential for large datasets**

**Spring Data Pagination:**
```java
// Repository method
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByActiveTrue(Pageable pageable);
    
    Slice<User> findByEmailContaining(String email, Pageable pageable);
    
    List<User> findByDepartmentId(Long deptId, Pageable pageable);
}

// Service layer
@Service
public class UserService {
    public Page<User> getActiveUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findByActiveTrue(pageable);
    }
}

// Controller
@GetMapping("/users")
public ResponseEntity<Page<User>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sort) {
    
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    Page<User> users = userService.getUsers(pageable);
    return ResponseEntity.ok(users);
}
```

**Page vs Slice:**
```java
// Page - knows total elements and pages
Page<User> page = repository.findByActiveTrue(pageable);
page.getContent();      // List of users on current page
page.getTotalElements(); // Total users matching criteria
page.getTotalPages();    // Total pages
page.hasNext();         // Has next page?
page.hasPrevious();     // Has previous page?

// Slice - only knows about current slice
Slice<User> slice = repository.findByEmailContaining("john", pageable);
slice.getContent();     // List of users
slice.hasNext();        // Has next slice? (no total count)
// No getTotalElements() or getTotalPages()
```

**Theoretical Keywords:**  
**Data chunking**, **Page request**, **Page vs Slice**, **Performance optimization**

---

### **59. How pagination works internally?**
**Database-level pagination:**

**1. MySQL/PostgreSQL (LIMIT/OFFSET):**
```sql
-- Page 1 (records 1-10)
SELECT * FROM users ORDER BY name LIMIT 10 OFFSET 0;

-- Page 2 (records 11-20)
SELECT * FROM users ORDER BY name LIMIT 10 OFFSET 10;

-- Total count query (for Page object)
SELECT COUNT(*) FROM users;
```

**2. SQL Server:**
```sql
-- Using OFFSET-FETCH
SELECT * FROM users 
ORDER BY name 
OFFSET 10 ROWS 
FETCH NEXT 10 ROWS ONLY;
```

**3. Oracle (12c+):**
```sql
SELECT * FROM users 
ORDER BY name 
OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY;
```

**Performance Issues with OFFSET:**
```sql
-- Problem: OFFSET 1000000 means skip 1 million records
SELECT * FROM users ORDER BY id LIMIT 10 OFFSET 1000000;
-- Database must scan 1,000,010 records!
```

**Keyset Pagination (Better Performance):**
```java
// Instead of OFFSET, use WHERE with last seen value
@Query("SELECT u FROM User u WHERE u.id > :lastId ORDER BY u.id LIMIT :size")
List<User> findNextPage(@Param("lastId") Long lastId, @Param("size") int size);

// Usage: Remember last ID from previous page
Long lastId = 100;  // Last ID from page 1
List<User> page2 = repository.findNextPage(lastId, 10);
```

**Spring Data Pagination Internals:**
```java
// When you call:
Page<User> page = repository.findAll(PageRequest.of(2, 10));

// Hibernate executes:
// 1. Data query (with LIMIT/OFFSET)
SELECT * FROM users LIMIT 10 OFFSET 20;

// 2. Count query (for total pages)
SELECT COUNT(*) FROM users;
```

**Custom Count Query:**
```java
@Query(value = "SELECT u FROM User u WHERE u.active = true",
       countQuery = "SELECT COUNT(u) FROM User u WHERE u.active = true")
Page<User> findActiveUsers(Pageable pageable);
// Optimized count query
```

**Theoretical Keywords:**  
**LIMIT/OFFSET**, **Keyset pagination**, **Count query**, **Database performance**

---

### **60. What is optimistic locking?**
- **Concurrency control** strategy
- **Assumes conflicts are rare** - no locks during read
- **Checks for conflicts** at update time
- **Uses version column** to detect changes
- **Throws `OptimisticLockException`** if conflict

**Implementation:**
```java
@Entity
public class Product {
    @Id
    private Long id;
    
    private String name;
    private Integer quantity;
    
    @Version  // Optimistic locking version column
    private Integer version;
    
    // getters, setters
}
```

**How it works:**
```java
// User 1 reads product
Product p1 = productRepository.findById(1L).get();
// version = 1, quantity = 10

// User 2 reads same product
Product p2 = productRepository.findById(1L).get();
// version = 1, quantity = 10

// User 1 updates
p1.setQuantity(8);
productRepository.save(p1);
// UPDATE products SET quantity=8, version=2 WHERE id=1 AND version=1
// version increments to 2

// User 2 tries to update (stale data)
p2.setQuantity(5);
try {
    productRepository.save(p2);
    // UPDATE products SET quantity=5, version=2 WHERE id=1 AND version=1
    // FAILS: version is now 2, not 1!
} catch (OptimisticLockException e) {
    // Handle conflict - refresh and retry
}
```

**Database Schema:**
```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    quantity INTEGER,
    version INTEGER  -- Version column for optimistic locking
);
```

**Benefits:**
- **Better performance** - no locks during read
- **Scalable** - works well with high concurrency
- **No deadlocks** - no locking mechanism

**Drawbacks:**
- **Stale updates** rejected (must retry)
- **Not suitable** for high-conflict scenarios

**Theoretical Keywords:**  
**Version column**, **Conflict detection**, `@Version` **annotation**, **Optimistic concurrency**

---

### **61. What is pessimistic locking?**
- **Concurrency control** strategy
- **Locks data** during transaction
- **Prevents conflicts** by blocking other transactions
- **Database-level locks** (row-level or table-level)
- **Guarantees consistency** but reduces concurrency

**Types of Pessimistic Locks:**

**1. PESSIMISTIC_READ (Shared Lock):**
```java
@Query("SELECT p FROM Product p WHERE p.id = :id")
@Lock(LockModeType.PESSIMISTIC_READ)
Product findByIdWithReadLock(@Param("id") Long id);
// Other transactions can read but not write
```

**2. PESSIMISTIC_WRITE (Exclusive Lock):**
```java
@Query("SELECT p FROM Product p WHERE p.id = :id")
@Lock(LockModeType.PESSIMISTIC_WRITE)
Product findByIdWithWriteLock(@Param("id") Long id);
// Other transactions cannot read or write
```

**3. PESSIMISTIC_FORCE_INCREMENT:**
```java
@Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
Product findByIdWithForceIncrement(@Param("id") Long id);
// Locks and increments version
```

**Usage Example:**
```java
@Service
public class InventoryService {
    
    @Transactional
    public void updateStock(Long productId, int quantityChange) {
        // Get product with pessimistic lock
        Product product = productRepository.findByIdWithWriteLock(productId);
        
        // Update quantity (safe from other updates)
        int newQuantity = product.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new InsufficientStockException();
        }
        
        product.setQuantity(newQuantity);
        // Other transactions wait until commit
    }
}
```

**Database Behavior:**
```sql
-- PESSIMISTIC_WRITE executes:
SELECT * FROM products WHERE id = 1 FOR UPDATE;
-- Locks the row until transaction ends
```

**When to use Pessimistic Locking:**
1. **Banking transactions** (money transfers)
2. **Inventory management** (stock updates)
3. **Seat reservation systems**
4. **High-conflict scenarios**

**Comparison with Optimistic Locking:**

| **Aspect** | **Optimistic Locking** | **Pessimistic Locking** |
|------------|-----------------------|-------------------------|
| **Approach** | Detect conflicts | Prevent conflicts |
| **Locking** | No locks during read | Locks during transaction |
| **Performance** | Better for low conflict | Better for high conflict |
| **Failure Handling** | Retry on conflict | Block/wait |
| **Use Case** | Read-heavy, low conflict | Write-heavy, high conflict |

**Theoretical Keywords:**  
**Exclusive locks**, `FOR UPDATE`, **Transaction blocking**, **Concurrency control**, **Database locks**