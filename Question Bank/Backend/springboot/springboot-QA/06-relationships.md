Here are the answers with **simple examples and bolded keywords**:

## **Relationships **

### **45. Explain `OneToOne` mapping**
- **One-to-one relationship** between two entities
- **Each instance** of A is associated with exactly one instance of B
- **Bi-directional or uni-directional**
- **Shared primary key** or foreign key approach

**Common Examples:**
- User ↔ UserProfile (one user, one profile)
- Employee ↔ EmployeeDetails
- Student ↔ StudentIdCard

**Code Example: Shared Primary Key Approach:**
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn  // Shares primary key
    private UserProfile profile;  // One user has one profile
    
    // constructor, getters, setters
}

@Entity
public class UserProfile {
    @Id
    private Long id;  // Same as User id
    
    private String bio;
    private String avatarUrl;
    
    @OneToOne
    @MapsId  // Maps User id to this id
    @JoinColumn(name = "user_id")
    private User user;  // One profile belongs to one user
    
    // constructor, getters, setters
}
```

**Code Example: Foreign Key Approach:**
```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    private String username;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")  // Foreign key in User table
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    @GeneratedValue
    private Long id;
    
    private String bio;
    
    @OneToOne(mappedBy = "profile")  // Mapped by User.profile field
    private User user;
}
```

**Usage:**
```java
// Create User with Profile
User user = new User("john_doe");
UserProfile profile = new UserProfile("Software Developer", "avatar.jpg");
user.setProfile(profile);
profile.setUser(user);

userRepository.save(user);  // Saves both due to cascade
```

**Theoretical Keywords:**  
**One-to-one relationship**, **Shared primary key**, **Foreign key**, **Bi-directional mapping**

---

### **46. Explain `OneToMany` and `ManyToOne`**
- **Most common relationship** in database design
- **`@OneToMany`** - One parent has many children
- **`@ManyToOne`** - Many children belong to one parent
- **Usually used together** for bi-directional relationship

**Real-world Examples:**
- Department ↔ Employees (one department, many employees)
- Order ↔ OrderItems (one order, many items)
- Author ↔ Books (one author, many books)

**Code Example: Department (One) ↔ Employees (Many):**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
    
    // Add helper method
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
}

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")  // Foreign key column
    private Department department;
    
    // constructor, getters, setters
}
```

**Code Example: Order (One) ↔ OrderItems (Many):**
```java
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    
    private LocalDateTime orderDate;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    
    // Helper method
    public void addItem(Product product, int quantity) {
        OrderItem item = new OrderItem(this, product, quantity);
        items.add(item);
    }
}

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    
    private int quantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    // constructor, getters, setters
}
```

**Usage:**
```java
// Create Order with Items
Order order = new Order();
order.addItem(product1, 2);
order.addItem(product2, 1);

orderRepository.save(order);  // Saves order and all items
```

**Uni-directional `@OneToMany` (without `@ManyToOne`):**
```java
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")  // Foreign key in Comment table
    private List<Comment> comments = new ArrayList<>();
}

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    
    private String content;
    // No reference back to Post
}
```

**Key Points:**
- **`@ManyToOne` side is owning side** (has foreign key)
- **`mappedBy`** used on inverse side (`@OneToMany`)
- **Always initialize collections** to avoid NullPointerException

**Theoretical Keywords:**  
**Parent-child relationship**, **Owning side**, **Inverse side**, **Foreign key**, **Bi-directional**

---

### **47. Explain `ManyToMany` mapping**
- **Many-to-many relationship** between two entities
- **Requires join table** (third table for mapping)
- **Each instance** of A can be associated with many B, and vice versa
- **Bi-directional or uni-directional**

**Common Examples:**
- Student ↔ Course (many students, many courses)
- Book ↔ Author (many books, many authors)
- User ↔ Role (many users, many roles)

**Code Example: Student ↔ Course:**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @ManyToMany
    @JoinTable(
        name = "student_course",  // Join table name
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
    
    // Helper method
    public void enrollInCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }
}

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
```

**Join Table Structure:**
```
student_course table:
| student_id | course_id |
|------------|-----------|
| 1          | 101       |
| 1          | 102       |
| 2          | 101       |
```

**With Additional Columns in Join Table (Extra Attributes):**
```java
@Entity
public class StudentCourse {
    @EmbeddedId
    private StudentCourseId id;
    
    @ManyToOne
    @MapsId("studentId")
    private Student student;
    
    @ManyToOne
    @MapsId("courseId")
    private Course course;
    
    private LocalDate enrolledDate;  // Extra attribute
    private String grade;            // Extra attribute
}

@Embeddable
public class StudentCourseId implements Serializable {
    private Long studentId;
    private Long courseId;
}
```

**Usage:**
```java
// Enroll students in courses
Student alice = studentRepository.findById(1L).get();
Student bob = studentRepository.findById(2L).get();

Course math = courseRepository.findById(101L).get();
Course physics = courseRepository.findById(102L).get();

alice.enrollInCourse(math);
alice.enrollInCourse(physics);
bob.enrollInCourse(math);

studentRepository.save(alice);  // Updates join table
```

**Best Practices:**
- **Use `Set` instead of `List`** to avoid duplicates
- **Override `equals()` and `hashCode()`** for entities
- **Consider extra attributes** - may need separate entity

**Theoretical Keywords:**  
**Join table**, **Many-to-many relationship**, **Bi-directional mapping**, **Composite key**

---

### **48. What is `@JoinColumn`?**
- **Specifies foreign key column** for relationships
- **Used on owning side** of relationship
- **Defines column name** in database

**Usage Examples:**

**`@ManyToOne` with `@JoinColumn`:**
```java
@Entity
public class Employee {
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "dept_id")  // Foreign key column name
    private Department department;
}

// Creates: employee.dept_id references department.id
```

**`@OneToOne` with `@JoinColumn`:**
```java
@Entity
public class User {
    @Id
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "profile_id", unique = true)
    private UserProfile profile;
}
```

**Multiple `@JoinColumn`s for composite keys:**
```java
@Entity
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;
    
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
}
```

**Attributes of `@JoinColumn`:**
- **`name`** - column name (default: fieldname + "_" + referenced column)
- **`referencedColumnName`** - referenced column in target table
- **`nullable`** - can be null? (default: true)
- **`unique`** - unique constraint? (default: false)
- **`insertable`** - include in INSERT? (default: true)
- **`updatable`** - include in UPDATE? (default: true)

**Theoretical Keywords:**  
**Foreign key specification**, **Column mapping**, **Owning side annotation**

---

### **49. What is cascade?**
- **Propagates operations** from parent to child entities
- **Automatically perform** same operation on related entities
- **Reduces boilerplate code** for saving related entities

**Without Cascade (Manual):**
```java
@Transactional
public void createOrderWithItems(Order order) {
    // Save order first
    orderRepository.save(order);
    
    // Save each item manually
    for (OrderItem item : order.getItems()) {
        item.setOrder(order);  // Set relationship
        itemRepository.save(item);
    }
}
```

**With Cascade (Automatic):**
```java
@Entity
public class Order {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}

@Transactional
public void createOrderWithItems(Order order) {
    // Just save order - items saved automatically
    orderRepository.save(order);
}
```

**Theoretical Keywords:**  
**Operation propagation**, **Parent-child synchronization**, **Automatic persistence**

---

### **50. Types of cascade**
**`CascadeType` Enum Values:**

1. **`CascadeType.ALL`** - All operations cascade
   ```java
   @OneToMany(cascade = CascadeType.ALL)
   // Save, delete, refresh, etc. all cascade
   ```

2. **`CascadeType.PERSIST`** - Save operation cascades
   ```java
   @OneToMany(cascade = CascadeType.PERSIST)
   // When parent saved, children also saved
   ```

3. **`CascadeType.MERGE`** - Update operation cascades
   ```java
   @OneToMany(cascade = CascadeType.MERGE)
   // When parent updated, children also updated
   ```

4. **`CascadeType.REMOVE`** - Delete operation cascades
   ```java
   @OneToMany(cascade = CascadeType.REMOVE)
   // When parent deleted, children also deleted
   ```

5. **`CascadeType.REFRESH`** - Refresh operation cascades
   ```java
   @OneToMany(cascade = CascadeType.REFRESH)
   // When parent refreshed from DB, children too
   ```

6. **`CascadeType.DETACH`** - Detach operation cascades
   ```java
   @OneToMany(cascade = CascadeType.DETACH)
   // When parent detached, children too
   ```

**Common Combinations:**
```java
// Save and delete cascade (most common)
@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})

// Save, update, delete
@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
```

**Orphan Removal (Special Cascade):**
```java
@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
// When child removed from collection, it's deleted from DB
```

**Example:**
```java
@Entity
public class Order {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "order", 
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}

// Usage
Order order = orderRepository.findById(1L).get();

// Add new item - automatically saved
order.getItems().add(new OrderItem("Product A", 1));

// Remove item - automatically deleted from DB
order.getItems().remove(0);

orderRepository.save(order);  // All changes cascaded
```

**Theoretical Keywords:**  
**Cascade operations**, **Propagation types**, **Orphan removal**, **Parent-child persistence**

---

### **51. `FetchType.LAZY` vs `FetchType.EAGER`**

| **Aspect** | **`FetchType.LAZY`** | **`FetchType.EAGER`** |
|------------|----------------------|----------------------|
| **When loaded** | On demand (when accessed) | Immediately with parent |
| **Performance** | Better (loads only what's needed) | Worse (loads everything) |
| **N+1 Problem** | Can cause if not careful | Less likely but loads more data |
| **Default for** | `@ManyToOne`, `@OneToOne` | `@OneToMany`, `@ManyToMany` |
| **Memory Usage** | Lower | Higher |

**`FetchType.LAZY` (Load on Demand):**
```java
@Entity
public class Order {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> items;  // NOT loaded with order
    
    public List<OrderItem> getItems() {
        // Loaded here when first accessed
        if (items == null) {
            // Lazy loading happens
        }
        return items;
    }
}

// Usage
Order order = orderRepository.findById(1L).get();
// order loaded, but items NOT loaded yet

System.out.println(order.getId());  // OK

// Access items - triggers query to load items
List<OrderItem> items = order.getItems();  // QUERY EXECUTED HERE
System.out.println(items.size());
```

**`FetchType.EAGER` (Load Immediately):**
```java
@Entity
public class Order {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> items;  // Loaded with order in JOIN
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;  // Loaded with order
}

// Usage
Order order = orderRepository.findById(1L).get();
// order + items + customer ALL loaded in single query

System.out.println(order.getItems().size());  // Already loaded
System.out.println(order.getCustomer().getName());  // Already loaded
```

**N+1 Problem with LAZY:**
```java
// Problem: N+1 queries
List<Order> orders = orderRepository.findAll();

for (Order order : orders) {
    // Each access triggers new query!
    System.out.println(order.getItems().size());  // QUERY for each order
}
// Total: 1 (findAll) + N (getItems) queries = N+1 problem

// Solution: Use JOIN FETCH in query
@Query("SELECT o FROM Order o JOIN FETCH o.items")
List<Order> findAllWithItems();
```

**Best Practices:**

1. **Default to `LAZY`** for all relationships
   ```java
   @ManyToOne(fetch = FetchType.LAZY)  // Default for @ManyToOne
   @OneToMany(fetch = FetchType.LAZY)  // Must specify (default is EAGER)
   ```

2. **Use `EAGER` only when:**
   - Always need related data
   - Small dataset
   - Performance not critical

3. **Use `JOIN FETCH` for queries when needed:**
   ```java
   @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id")
   Order findByIdWithItems(@Param("id") Long id);
   ```

4. **Use Entity Graphs:**
   ```java
   @EntityGraph(attributePaths = {"items", "customer"})
   @Query("SELECT o FROM Order o WHERE o.id = :id")
   Order findByIdWithDetails(@Param("id") Long id);
   ```

**Performance Comparison:**

**LAZY (Better for large data):**
```
Query 1: SELECT * FROM orders WHERE id = 1
[Later...] 
Query 2: SELECT * FROM order_items WHERE order_id = 1
```

**EAGER (Single query but more data):**
```
Query 1: SELECT o.*, i.* FROM orders o 
         LEFT JOIN order_items i ON o.id = i.order_id 
         WHERE o.id = 1
```

**Recommendation:**  
**Always use `LAZY`** and explicitly fetch when needed using JOIN FETCH or Entity Graphs.

**Theoretical Keywords:**  
**Loading strategies**, **Lazy loading**, **Eager loading**, **N+1 problem**, **Performance optimization**