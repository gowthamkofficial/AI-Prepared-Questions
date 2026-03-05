Here are the answers with **examples and bolded keywords**:

## **Annotations (Very Important) **

### **20. What is `@Component`?**
- **Stereotype annotation** that marks a Java class as a Spring component
- **Auto-detected** during classpath scanning  
  *Example: `@Component` class in `@ComponentScan` package becomes Spring bean*
- **Base annotation** for other stereotype annotations  
  *Example: `@Service`, `@Repository`, `@Controller` are specialized `@Component`s*
- **Creates singleton bean** in Spring container  
  *Example: Only one instance of `@Component` class managed by Spring*
- **Bean name** derived from class name (camelCase)  
  *Example: `UserService` → bean name "userService"*
- **Can specify custom bean name**  
  *Example: `@Component("myUserService")`*

**Code Example:**
```java
@Component  // Spring will create bean of this class
public class EmailService {
    public void sendEmail(String to, String message) {
        // email sending logic
    }
}

// In another class
@Component
public class NotificationService {
    @Autowired
    private EmailService emailService;  // Injected by Spring
}
```

**Theoretical Keywords:**  
**Stereotype annotation**, **Auto-detection**, **Bean creation**, **Singleton**, **Classpath scanning**

---

### **21. Difference between `@Component`, `@Service`, `@Repository`**

| **Annotation** | **Purpose** | **Example** | **Special Features** |
|----------------|-------------|-------------|----------------------|
| **`@Component`** | Generic stereotype for any Spring-managed component | Utility classes, helpers | Base annotation, no special semantics |
| **`@Service`** | Marks business service layer | `UserService`, `PaymentService` | Indicates business logic, transaction boundary |
| **`@Repository`** | Marks DAO/Repository layer | `UserRepository`, `OrderRepository` | **Exception translation** (SQL → DataAccessException) |

**Detailed Differences:**

**1. `@Component`:**
- **Generic purpose** component
- **No special semantics**  
  *Example: `@Component` on utility class like `DateFormatter`*
- **Base annotation** that others inherit from
- **Bean creation only** - no additional functionality

**2. `@Service`:**
- **Business logic layer** marker  
  *Example:*
  ```java
  @Service
  public class UserService {
      @Autowired
      private UserRepository repository;
      
      @Transactional
      public User createUser(User user) {
          // Business logic + transaction
          return repository.save(user);
      }
  }
  ```
- **Semantic annotation** - indicates service layer
- **Transaction boundaries** typically here
- **Same as `@Component`** technically, but conveys intent

**3. `@Repository`:**
- **Data access layer** marker  
  *Example:*
  ```java
  @Repository
  public interface UserRepository extends JpaRepository<User, Long> {
      // Exception translation happens automatically
      User findByEmail(String email);
  }
  ```
- **Exception translation** feature  
  *Example: SQLException → DataAccessException*
- **Persistence exception hierarchy** support
- **Required for `@EnableJpaRepositories`** to work

**Important Note:**
- **All three are technically identical** at runtime
- **Choose based on layer/role** for clarity and tool support
- **`@Controller`** is fourth stereotype for web layer

**Theoretical Keywords:**  
**Stereotype annotations**, **Layer separation**, **Exception translation**, **Semantic meaning**, **Business logic vs Data access**

---

### **22. What is `@Autowired`?**
- **Annotation for dependency injection** in Spring
- **Automatically wires beans** by type  
  *Example: `@Autowired UserService` injects UserService bean*
- **Can be used on**: Constructors, setters, fields
- **Multiple wiring strategies**: byType, byName, constructor
- **`required` attribute** (default true)  
  *Example: `@Autowired(required = false)` - optional dependency*
- **`@Qualifier`** used with `@Autowired` for multiple beans of same type

**Code Examples:**

**Field Injection:**
```java
@Service
public class UserService {
    @Autowired  // Injects UserRepository bean
    private UserRepository userRepository;
}
```

**Constructor Injection (Recommended):**
```java
@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired  // Spring 4.3+: @Autowired optional on single constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**Setter Injection:**
```java
@Service
public class UserService {
    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**Multiple Beans with `@Qualifier`:**
```java
@Autowired
@Qualifier("pdfReport")
private ReportGenerator reportGenerator;
```

**Theoretical Keywords:**  
**Dependency injection**, **Autowiring**, **Bean wiring**, **Field/constructor/setter injection**, **Qualifier**

---

### **23. What is `@Configuration`?**
- **Marks a class as configuration source** for Spring container
- **Contains `@Bean` methods** that define Spring beans  
  *Example: Database configuration, external service configuration*
- **Alternative to XML configuration**  
  *Example: Java-based configuration vs `applicationContext.xml`*
- **`@Component` meta-annotation** - also a Spring component
- **Can import other configurations**  
  *Example: `@Import(DatabaseConfig.class)`*
- **Singleton beans** - `@Bean` methods called once by container

**Code Example:**
```java
@Configuration
public class AppConfig {
    
    @Bean  // Defines a Spring bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);  // Auto-wired parameter
    }
}
```

**Theoretical Keywords:**  
**Configuration class**, **Bean definitions**, **Java-based config**, **Bean factory methods**

---

### **24. What is `@Bean`?**
- **Annotation on methods** in `@Configuration` classes
- **Returns an object** to be registered as Spring bean  
  *Example: `@Bean` method returning `DataSource` object*
- **Method name** becomes bean name by default  
  *Example: `dataSource()` method → bean name "dataSource"*
- **Can specify custom name**  
  *Example: `@Bean(name = "myDataSource")`*
- **Singleton scope by default** - method called once
- **Method parameters auto-wired**  
  *Example: `@Bean` method with `DataSource` parameter gets injected*

**Code Example:**
```java
@Configuration
public class DatabaseConfig {
    
    @Bean  // Creates DataSource bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        return dataSource;
    }
    
    @Bean  // Creates EntityManagerFactory bean, auto-wires DataSource
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.entity");
        return em;
    }
}
```

**Theoretical Keywords:**  
**Bean definition method**, **Method-level annotation**, **Bean registration**, **Factory method**

---

### **25. Difference between `@Bean` and `@Component`**

| **Aspect** | **`@Component`** | **`@Bean`** |
|------------|------------------|-------------|
| **Level** | Class-level annotation | Method-level annotation |
| **Usage** | On your own classes | On methods (often in `@Configuration`) |
| **Control** | Limited control over instantiation | Full control over instantiation logic |
| **External Classes** | Cannot use on third-party classes | Can use with third-party classes |
| **Multiple Beans** | One bean per class | Multiple beans from same method possible |
| **Conditional** | With `@Conditional` on class | With `@Conditional` on method |

**Detailed Comparison:**

**1. `@Component`:**
- **Used on your own classes**  
  *Example: `@Component` on `UserService` class you wrote*
- **Auto-detected** by component scanning
- **Limited to one bean** per class
- **Simple instantiation** - Spring calls default constructor

**Code Example:**
```java
@Component  // Your class, auto-detected
public class EmailService {
    // Spring creates instance using default constructor
}
```

**2. `@Bean`:**
- **Used on methods** (typically in `@Configuration` classes)
- **Can instantiate third-party classes**  
  *Example:*
  ```java
  @Configuration
  public class Config {
      @Bean
      public ObjectMapper objectMapper() {
          // Creating and configuring third-party Jackson ObjectMapper
          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          return mapper;
      }
  }
  ```
- **Full control** over instantiation and configuration
- **Can create multiple beans** from same class  
  *Example:*
  ```java
  @Bean
  public DataSource primaryDataSource() { /* config 1 */ }
  
  @Bean  
  public DataSource secondaryDataSource() { /* config 2 */ }
  ```
- **Method-based conditional logic**  
  *Example:*
  ```java
  @Bean
  @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
  public CacheManager cacheManager() {
      return new RedisCacheManager();
  }
  ```

**When to use which:**
- **Use `@Component`** for your own service/component classes
- **Use `@Bean`** for:
  1. Third-party library classes
  2. Complex bean configuration
  3. Conditional bean creation
  4. Multiple instances of same type

**Theoretical Keywords:**  
**Class vs Method level**, **Own vs Third-party classes**, **Auto-detection vs Explicit definition**, **Single vs Multiple beans**