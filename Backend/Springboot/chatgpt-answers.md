# Spring Boot Interview Answers — Concise Theory

## BEGINNER LEVEL (0–2 Years)

1. What is Spring Framework?
    - Spring is a lightweight Java framework providing IoC/DI, AOP, transaction management and modular components to build enterprise applications.
2. Why do we use Spring?
    - To simplify infrastructure concerns (lifecycle, transactions, security), promote loose coupling, and increase testability via dependency injection.
3. What problems does Spring solve?
    - It solves tight coupling, boilerplate code, cross-cutting concerns, lifecycle management, and eases integration with other technologies.
4. What is IoC (Inversion of Control)?
    - IoC is a design principle where control of object creation and wiring is inverted to a container, which manages dependencies and lifecycles.
5. What is Dependency Injection?
    - DI is a pattern where an external entity provides an object's dependencies, enabling loose coupling and easier testing.
6. Types of Dependency Injection
    - Constructor injection, setter injection, and field injection; constructor injection is preferred for immutability and mandatory dependencies.
7. Difference between tight coupling and loose coupling
    - Tight coupling binds classes to concrete implementations; loose coupling relies on abstractions and external wiring, making code flexible and testable.
8. What is a Spring Bean?
    - A Spring Bean is an object managed by the Spring IoC container, created and configured from metadata (annotations, XML, or Java config).
9. What is ApplicationContext?
    - ApplicationContext is Spring's advanced IoC container that provides bean management plus enterprise features like events, i18n, and resource loading.
10. Difference between BeanFactory and ApplicationContext
    - BeanFactory is a minimal container with lazy bean creation; ApplicationContext adds enterprise services and typically eagerly initializes singletons.

---

## Spring Boot Basics

11. What is Spring Boot?
    - Spring Boot is a convention-over-configuration layer that simplifies Spring application setup with auto-configuration and starters.
12. Why Spring Boot is preferred over Spring?
    - It reduces manual configuration, provides embedded servers, opinionated defaults, and quick startup for production-ready apps.
13. What are Spring Boot starters?
    - Starters are dependency bundles that pull in common libraries for a feature (e.g., web, data, security) to simplify setup.
14. What is auto-configuration?
    - Auto-configuration automatically configures beans based on classpath settings and properties to reduce explicit config.
15. What is `@SpringBootApplication`?
    - A convenience annotation combining `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan` to bootstrap apps.
16. What is embedded Tomcat?
   - Embedded Tomcat is a servlet container packaged with the application so it can run standalone as a JAR without deploying to external servers.
17. Can we change embedded server in Spring Boot?
   - Yes; you can replace Tomcat with Jetty, Undertow, etc., by excluding one starter and including another.
18. What is `application.properties`?
   - It's a key-value configuration file used to externalize and override application settings.
19. Difference between `application.properties` and `application.yml`
   - Both are config formats; YAML supports hierarchical structures and is more readable for nested properties.

---

## Annotations (Very Important)

20. What is `@Component`?
   - `@Component` marks a class as a Spring-managed bean eligible for component scanning.
21. Difference between `@Component`, `@Service`, `@Repository`
   - All register beans; `@Service` is for business logic, `@Repository` for persistence (adds exception translation), `@Component` is generic.
22. What is `@Autowired`?
   - `@Autowired` injects dependencies by type (or qualifier) into constructors, setters, or fields.
23. What is `@Configuration`?
   - Marks a class that declares `@Bean` methods for Java-based configuration.
24. What is `@Bean`?
   - Declares a method that returns a Spring bean to be managed by the container.
25. Difference between `@Bean` and `@Component`
   - `@Bean` is for explicit method-based bean registration; `@Component` is classpath-scanned automatic registration.

---

## INTERMEDIATE LEVEL (2–4 Years)

### REST API

26. What is REST?
   - REST is an architectural style using stateless HTTP operations and resource-oriented URIs.
27. What is RESTful web service?
   - A web service that exposes resources and uses HTTP methods (GET, POST, PUT, DELETE) consistently.
28. Difference between `@Controller` and `@RestController`
   - `@RestController` is `@Controller` + `@ResponseBody`; it returns objects as HTTP responses (usually JSON).
29. Difference between `@RequestParam` and `@PathVariable`
   - `@RequestParam` binds query/form params; `@PathVariable` binds URI path segments.
30. What is `@RequestBody`?
   - Binds the HTTP request body to a method parameter and converts it using configured HttpMessageConverters.
31. What is `ResponseEntity`?
   - A wrapper to control HTTP status, headers, and body in responses.
32. What are HTTP methods?
   - Standard verbs like GET, POST, PUT, PATCH, DELETE, OPTIONS, used to express intent on resources.
33. What are HTTP status codes?
   - Numeric codes signaling request outcome (2xx success, 4xx client error, 5xx server error).
34. Difference between PUT and PATCH
   - PUT replaces a resource entirely; PATCH applies partial updates.
35. How do you design REST APIs?
   - Use resource-oriented URIs, proper HTTP methods, statelessness, clear versioning, and meaningful status codes.

### Spring Data JPA

36. What is ORM?
   - Object-Relational Mapping maps objects to database tables to abstract SQL and persistence operations.
37. What is Hibernate?
   - Hibernate is a popular Java ORM implementation providing JPA-compliant data access and caching.
38. What is Spring Data JPA?
   - A Spring project that simplifies JPA-based repositories with CRUD abstractions and query derivation.
39. Difference between JPA and Hibernate
   - JPA is a specification; Hibernate is a concrete implementation of JPA.
40. Difference between `CrudRepository` and `JpaRepository`
   - `JpaRepository` extends `CrudRepository` adding JPA-specific methods like pagination and flushing.
41. What is an Entity?
   - An Entity is a persistent domain object mapped to a database table using `@Entity`.
42. What is `@Id` and `@GeneratedValue`?
   - `@Id` marks primary key; `@GeneratedValue` configures automatic key generation strategies.
43. What is `@Transactional`?
   - Declares transactional boundaries so Spring manages commit/rollback automatically.
44. Difference between `save()` and `saveAndFlush()`
   - `save()` persists but may defer DB flush; `saveAndFlush()` forces immediate flush to the database.

### Relationships

45. Explain OneToOne mapping
   - A one-to-one relationship links two entities where one instance maps to exactly one of the other.
46. Explain OneToMany and ManyToOne
   - OneToMany: one parent maps to many children; ManyToOne: child references a single parent.
47. Explain ManyToMany mapping
   - ManyToMany associates multiple instances on both sides, often via a join table.
48. What is `@JoinColumn`?
   - Specifies the foreign key column used for joining two tables in a relationship.
49. What is cascade?
   - Cascade defines which operations (persist, merge, remove, refresh) propagate from parent to child.
50. Types of Cascade
   - Common types: ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH.
51. FetchType.LAZY vs FetchType.EAGER
   - LAZY defers loading until needed; EAGER loads related entities immediately.

---

## ADVANCED LEVEL (4+ Years)

### JPA & Hibernate Internals

52. What is the N+1 select problem?
   - Excessive queries caused by lazy loading where N related selects follow the initial query.
53. How do you solve N+1 problem?
   - Use eager fetch joins, `@EntityGraph`, batch fetching, or optimized queries to fetch relationships in fewer queries.
54. What is JPQL?
   - Java Persistence Query Language — a JPQL object-oriented query language over entities.
55. Difference between JPQL and Native Query
   - JPQL queries entities/fields; native queries execute raw SQL against the database.
56. What is DTO projection?
   - Mapping query results to Data Transfer Objects to fetch only needed fields and decouple persistence from API models.
57. Interface-based vs Class-based projections
   - Interface projections map getters to fields; class-based projections use constructor expressions for DTOs.
58. What is pagination?
   - Dividing query results into pages using limits/offsets to handle large result sets.
59. How pagination works internally?
   - Typically via SQL `LIMIT/OFFSET` or keyset pagination to fetch subsets and count total when needed.
60. What is optimistic locking?
   - A concurrency control using versioning (e.g., `@Version`) that prevents lost updates without DB locks.
61. What is pessimistic locking?
   - Locks DB rows during a transaction to prevent concurrent access, used when conflicts are likely.

### Exception Handling

62. How do you handle exceptions in Spring Boot?
   - Use `@ExceptionHandler` in controllers or a global `@ControllerAdvice` to map exceptions to responses.
63. What is `@ExceptionHandler`?
   - Method-level annotation to handle specific exceptions and produce custom responses.
64. What is `@ControllerAdvice`?
   - A global component that centralizes exception handling and advice across controllers.
65. Difference between `@ExceptionHandler` and `@ControllerAdvice`
   - `@ExceptionHandler` is per-controller (or method); `@ControllerAdvice` applies handlers globally.
66. How do you return a common error response?
   - Define a standard error DTO and map exceptions to it using `@ControllerAdvice` and `ResponseEntity`.
67. How do you handle validation errors?
   - Use `@Valid`/`@Validated` on request DTOs and handle `MethodArgumentNotValidException` in a global handler.

### Validation

68. What is `@Valid`?
   - Triggers JSR-303 bean validation on method parameters or request bodies.
69. What is `@Validated`?
   - Spring's variant enabling group-based validation on beans or method parameters.
70. Difference between `@NotNull`, `@NotEmpty`, `@NotBlank`
   - `@NotNull` prohibits null; `@NotEmpty` disallows null/empty collections or strings; `@NotBlank` disallows blank strings.
71. How do you handle validation globally?
   - Catch validation exceptions in `@ControllerAdvice` and return standardized error payloads.

---

## SPRING SECURITY (Frequently Asked)

72. What is Spring Security?
   - A framework for authentication, authorization, and security-related features for Spring applications.
73. Authentication vs Authorization
   - Authentication verifies identity; authorization checks permissions/roles to access resources.
74. What is JWT?
   - JSON Web Token — a compact, self-contained token for stateless authentication carrying claims.
75. Explain JWT flow
   - User authenticates → server issues JWT → client sends JWT in requests → server validates token and authorizes.
76. Where is JWT stored on client side?
   - Common options: Authorization header (recommended) or localStorage/sessionStorage (subject to XSS risks); cookies with proper flags are safer.
77. What is `SecurityFilterChain`?
   - A chain of security filters (configured in modern Spring Security) controlling authentication and authorization flow.
78. What is PasswordEncoder?
   - Interface for hashing passwords (e.g., BCryptPasswordEncoder) to store credentials securely.
79. How role-based authorization works?
   - Roles/authorities assigned to users are checked via method or URL security rules to grant/deny access.
80. What is CSRF?
   - Cross-Site Request Forgery — an attack mitigated by tokens or same-site cookie policies for state-changing requests.
81. How do you secure REST APIs?
   - Use stateless auth (JWT/OAuth2), HTTPS, input validation, rate limiting, and proper CORS and security headers.

---

## SPRING BOOT INTERNALS (Service Company Favorite)

82. How does Spring Boot application start?
   - `SpringApplication.run()` bootstraps the context, triggers auto-configuration, component scanning, and starts the embedded server.
83. Explain auto-configuration flow
   - Spring Boot evaluates conditional configuration classes based on classpath and properties to register auto-configured beans.
84. What happens internally when you hit a REST API?
   - Request passes through servlet/filter chain, security filters, DispatcherServlet, handler mapping, controller, and message conversion to response.
85. How dependency injection works internally?
   - The container builds bean definitions, resolves dependencies by type/qualifier, and injects them during bean creation phase.
86. How Spring identifies beans?
   - Via component scanning, `@Bean` methods, and explicit registrations using configuration metadata.
87. What is classpath scanning?
   - Scanning packages for annotated classes (`@Component`, `@Service`, etc.) to auto-register beans.
88. How `@ComponentScan` works?
   - It tells Spring which base packages to scan for candidate components to register as beans.

---

## PROFILES, CONFIG & DEPLOYMENT

89. What are Spring profiles?
   - Profiles are named groups of configuration properties/beans that can be activated per environment (dev, prod).
90. Why profiles are used?
   - To separate environment-specific configurations like DBs, logging, or feature toggles.
91. Difference between dev and prod configuration
   - Dev favors debugging, verbose logging and hot reload; prod emphasizes performance, security, and optimizations.
92. What is `@Profile`?
   - Annotates beans/configs to load only when a specific profile is active.
93. What is `@Value`?
   - Injects individual property values into fields or method parameters.
94. What is `@ConfigurationProperties`?
   - Binds a group of properties to a POJO for type-safe configuration.
95. How do you externalize configuration?
   - Use `application.properties`/`application.yml`, environment variables, command-line args, or config servers.

---

## TESTING (Basic Expectation)

96. What is unit testing?
   - Testing individual units (methods/classes) in isolation using mocks to assert behavior.
97. Difference between unit and integration testing
   - Unit tests isolate a component; integration tests verify interactions between components or with infrastructure (DB, web).
98. What is `@SpringBootTest`?
   - Loads the full application context for integration-style tests.
99. What is `@WebMvcTest`?
   - Slices the context to load web-layer components (controllers, filters) for focused MVC tests.
100. What is MockMvc?
   - A utility to test Spring MVC controllers by performing mock HTTP requests against the controller layer.
101. What is Mockito?
   - A mocking framework for creating test doubles and verifying interactions in unit tests.

---

## VERY IMPORTANT QUESTIONS (Infosys / Wipro / CTS / Capgemini)

102. Explain your Spring Boot project
   - Describe architecture, used modules (web, data, security), key design choices, and deployment model concisely.
103. What annotations did you use and why?
   - List and justify use of common annotations like `@RestController`, `@Service`, `@Repository`, `@Transactional`.
104. How did you handle exceptions?
   - Use centralized `@ControllerAdvice` to map exceptions to consistent error responses and appropriate status codes.
105. How did you design REST APIs?
   - Use resource-based URIs, proper HTTP methods, DTOs for input/output, validation, and versioning.
106. How did you connect Spring Boot with database?
   - Configure datasource properties, use Spring Data repositories or JPA EntityManager, and manage migrations via Flyway/Liquibase.
107. How did you handle validations?
   - Use JSR-303 annotations (`@Valid`) on DTOs and handle constraint violations in a global exception handler.
108. How did you secure APIs?
   - Implement authentication/authorization (JWT/OAuth2), secure endpoints with roles, and enforce HTTPS.
109. What challenges did you face in your project?
   - Mention typical issues: performance tuning, transaction boundaries, data modeling, and integrations.
110. How did you optimize performance?
   - Caching, query optimization, connection pooling, async processing, and reducing payloads.
111. What will you improve if you rewrite the project?
   - Modularization, better error handling, more tests, clearer API contracts, and observability.

---

## FINAL INTERVIEW TIP

112. Flow of request
   - Client → Network → Load balancer → Embedded server → Filters → DispatcherServlet → Controller → Service → Repository → DB → Response back through same chain.
113. Why you used each annotation
   - Explain purpose: scanning, configuration, DI, persistence, transaction, validation, and mapping.
114. How errors are handled
   - Use validation annotations plus `@ControllerAdvice`/`@ExceptionHandler` to map exceptions to structured responses.

```
# Spring Boot Interview Questions - Professional Answers

---

## BEGINNER LEVEL (0–2 Years)

### Spring Basics

---

### 1. What is Spring Framework?

**Expected Answer:**
Spring Framework is a comprehensive Java application development framework that provides infrastructure support for building enterprise applications. It simplifies Java development by providing features like dependency injection, aspect-oriented programming, and transaction management. Spring promotes loose coupling through Inversion of Control (IoC) and makes applications easier to test and maintain. The framework is modular, allowing developers to use only the components they need.

**Key Theoretical Concepts:**
- Inversion of Control (IoC) container for managing object lifecycles
- Dependency Injection for loose coupling
- Aspect-Oriented Programming (AOP) for cross-cutting concerns
- POJO-based development model
- Modular architecture (Spring MVC, Spring Data, Spring Security, etc.)
- Enterprise application infrastructure
- Transaction management abstraction
- Integration with various technologies

**Example:**
```java
// Without Spring - Tight Coupling
public class UserService {
    private UserRepository repository = new UserRepository();
    private EmailService emailService = new EmailService();
}

// With Spring - Loose Coupling via Dependency Injection
@Service
public class UserService {
    private final UserRepository repository;
    private final EmailService emailService;
    
    @Autowired
    public UserService(UserRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }
}
```

---

### 2. Why do we use Spring?

**Expected Answer:**
We use Spring to simplify enterprise application development by eliminating boilerplate code and managing complex infrastructure concerns. Spring promotes best practices like loose coupling through dependency injection, making applications more testable and maintainable. It provides declarative transaction management, security, and data access abstractions. Spring's extensive ecosystem offers solutions for common enterprise needs while allowing developers to focus on business logic rather than infrastructure code.

**Key Theoretical Concepts:**
- Reduces boilerplate code through abstractions and templates
- Promotes testability via dependency injection
- Declarative programming model (transactions, caching, security)
- Integration with multiple frameworks and technologies
- Enterprise design patterns implementation
- Configuration flexibility (XML, annotations, Java config)
- Production-ready features for monitoring and management

**Example:**
```java
// Without Spring - Manual transaction management
public void transferMoney(Account from, Account to, double amount) {
    Connection conn = null;
    try {
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        // Complex SQL operations
        conn.commit();
    } catch (Exception e) {
        conn.rollback();
    } finally {
        conn.close();
    }
}

// With Spring - Declarative transaction
@Service
public class BankService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Transactional
    public void transferMoney(Account from, Account to, double amount) {
        from.debit(amount);
        to.credit(amount);
        accountRepository.save(from);
        accountRepository.save(to);
        // Spring handles transaction, rollback, connection management
    }
}
```

---

### 3. What problems does Spring solve?

**Expected Answer:**
Spring solves several critical enterprise development problems:
- **Tight Coupling**: Uses dependency injection to create loosely coupled, testable components
- **Object Lifecycle Management**: IoC container manages bean creation, initialization, and destruction
- **Boilerplate Code**: Reduces repetitive code for database access, transaction management, exception handling
- **Testing Complexity**: Makes unit testing easier through dependency injection and mock support
- **Cross-cutting Concerns**: Handles logging, security, transactions through AOP without cluttering business logic
- **Configuration Management**: Provides externalized configuration and multiple configuration approaches
- **Integration Challenges**: Simplifies integration with databases, messaging systems, web services

**Key Theoretical Concepts:**
- Dependency management through IoC container
- Aspect-Oriented Programming for separation of concerns
- Template pattern implementation (JdbcTemplate, RestTemplate)
- Transaction abstraction layer
- Exception hierarchy and translation
- Resource management and automatic cleanup
- Consistent programming model across different technologies

**Example:**
```java
// Problem: Database access with boilerplate
public List<User> getUsers() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<User> users = new ArrayList<>();
    try {
        conn = dataSource.getConnection();
        ps = conn.prepareStatement("SELECT * FROM users");
        rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            users.add(user);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        // Close resources
    }
    return users;
}

// Solution: Spring Data JPA eliminates boilerplate
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // That's it! Spring provides implementation
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getUsers() {
        return userRepository.findAll(); // Clean, simple
    }
}
```

---

### 4. What is IoC (Inversion of Control)?

**Expected Answer:**
Inversion of Control is a design principle where the control of object creation and dependency management is inverted from the application code to a framework (Spring IoC container). Instead of objects creating their own dependencies using the `new` keyword, the container creates objects and injects dependencies. This "Hollywood Principle" ("Don't call us, we'll call you") promotes loose coupling because objects don't need to know how to create or locate their dependencies. The container manages the entire lifecycle of objects.

**Key Theoretical Concepts:**
- Container-managed object lifecycle
- Hollywood Principle: framework calls application code
- Dependency lookup vs dependency injection
- Separation of configuration from business logic
- Bean factory and application context
- Control flow inversion
- Decoupling object creation from object usage

**Example:**
```java
// Traditional Control Flow (No IoC) - Object controls dependencies
public class OrderService {
    private PaymentService paymentService;
    private InventoryService inventoryService;
    private EmailService emailService;
    
    public OrderService() {
        // You control creation and configuration
        this.paymentService = new PayPalPaymentService();
        this.inventoryService = new InventoryService();
        this.emailService = new EmailService();
    }
}

// Inversion of Control - Container controls creation
@Service
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final EmailService emailService;
    
    @Autowired
    public OrderService(PaymentService paymentService,
                       InventoryService inventoryService,
                       EmailService emailService) {
        // Spring creates and injects dependencies
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.emailService = emailService;
    }
}

// Spring configuration (Java-based)
@Configuration
public class AppConfig {
    @Bean
    public PaymentService paymentService() {
        return new PayPalPaymentService();
    }
    
    @Bean
    public OrderService orderService() {
        return new OrderService(paymentService(), 
                               inventoryService(), 
                               emailService());
    }
}
```

---

### 5. What is Dependency Injection?

**Expected Answer:**
Dependency Injection is a design pattern and the primary implementation of IoC where an object's dependencies are provided (injected) by an external entity rather than the object creating them. In Spring, the IoC container analyzes configuration metadata (annotations, XML, Java config) and injects required dependencies at runtime through constructors, setters, or fields. This makes code more modular, testable, and maintainable as objects are loosely coupled and can work with different implementations through interfaces.

**Key Theoretical Concepts:**
- Constructor injection (recommended for required dependencies)
- Setter injection (for optional dependencies)
- Field injection (simple but reduces testability)
- Interface-based programming
- Testability through mock injection
- Loose coupling between components
- Dependency resolution at runtime
- Circular dependency detection

**Example:**
```java
// Constructor Injection (Recommended - Immutable, explicit dependencies)
@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    
    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }
    
    public User createUser(String username, String password) {
        User user = new User(username, encoder.encode(password));
        return repository.save(user);
    }
}

// Setter Injection (For optional dependencies)
@Service
public class NotificationService {
    private SMSProvider smsProvider;
    
    @Autowired(required = false)
    public void setSmsProvider(SMSProvider smsProvider) {
        this.smsProvider = smsProvider;
    }
    
    public void notify(String message) {
        if (smsProvider != null) {
            smsProvider.send(message);
        }
    }
}

// Field Injection (Not recommended for production)
@Service
public class ReportService {
    @Autowired
    private DataService dataService; // Hard to test, breaks immutability
}

// Testing with constructor injection
@Test
public void testUserService() {
    UserRepository mockRepo = mock(UserRepository.class);
    PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
    UserService service = new UserService(mockRepo, mockEncoder);
    // Easy to test with mocks
}
```

---

### 6. Types of Dependency Injection

**Expected Answer:**
Spring supports three types of dependency injection:

1. **Constructor Injection**: Dependencies provided through class constructor. Best for required, immutable dependencies. Ensures all dependencies are available before object use. Supports final fields.

2. **Setter Injection**: Dependencies provided through setter methods. Best for optional dependencies that may change. Allows partial dependency injection.

3. **Field Injection**: Dependencies injected directly into fields using @Autowired. Simplest syntax but makes testing difficult, breaks encapsulation, and doesn't support immutability.

**Recommendation**: Use constructor injection for mandatory dependencies, setter injection for optional ones, avoid field injection in production.

**Key Theoretical Concepts:**
- Immutability through constructor injection with final fields
- Optional vs mandatory dependencies
- Circular dependency handling
- Testability considerations
- Dependency visibility and encapsulation
- Spring's autowiring modes (byType, byName, constructor)
- Required vs optional injection

**Example:**
```java
@Service
public class OrderProcessingService {
    
    // Constructor Injection - RECOMMENDED for required dependencies
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;
    private final InventoryService inventoryService;
    
    @Autowired
    public OrderProcessingService(OrderRepository orderRepository,
                                  PaymentGateway paymentGateway,
                                  InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
        this.inventoryService = inventoryService;
    }
    
    // Setter Injection - For optional dependencies
    private NotificationService notificationService;
    private AuditService auditService;
    
    @Autowired(required = false)
    public void setNotificationService(NotificationService service) {
        this.notificationService = service;
    }
    
    @Autowired(required = false)
    public void setAuditService(AuditService service) {
        this.auditService = service;
    }
    
    public Order processOrder(Order order) {
        // Use required dependencies
        inventoryService.reserve(order.getItems());
        paymentGateway.charge(order.getTotal());
        Order saved = orderRepository.save(order);
        
        // Use optional dependencies if available
        if (notificationService != null) {
            notificationService.sendConfirmation(order);
        }
        if (auditService != null) {
            auditService.logOrderCreated(order);
        }
        
        return saved;
    }
}

// Field Injection - AVOID in production code
@Service
public class BadExampleService {
    @Autowired
    private UserRepository repository; // Cannot be final, hard to test
    
    // Testing requires Spring context or reflection
}
```

---

### 7. Difference between tight coupling and loose coupling

**Expected Answer:**
**Tight Coupling**: Classes directly depend on concrete implementations, creating rigid code where changes in one class force changes in dependent classes. Objects create their own dependencies using `new`, making unit testing difficult as you cannot substitute mock implementations. Changes ripple through the codebase.

**Loose Coupling**: Classes depend on abstractions (interfaces or abstract classes), making code flexible and maintainable. Dependencies are injected from outside, allowing easy substitution of implementations and simplified testing with mocks. Changes are isolated to implementation classes.

Spring promotes loose coupling through dependency injection and interface-based programming.

**Key Theoretical Concepts:**
- Dependency on interfaces vs concrete classes
- Code maintainability and flexibility
- Testing implications (mocking, stubbing)
- Design to interfaces principle
- Modularity and reusability
- Impact on code changes and refactoring
- Single Responsibility Principle
- Open/Closed Principle compliance

**Example:**
```java
// TIGHT COUPLING - Rigid, hard to test, hard to change
public class OrderProcessor {
    private EmailService emailService = new EmailService(); // Hardcoded
    private SMSService smsService = new SMSService(); // Hardcoded
    
    public void processOrder(Order order) {
        // Process order
        emailService.send("Order confirmed"); // Cannot substitute mock
        smsService.send("Order confirmed");  // Forced to use real services
    }
}

// LOOSE COUPLING - Flexible, testable, maintainable
// 1. Define interface
public interface NotificationService {
    void send(String message);
}

// 2. Multiple implementations
@Service
public class EmailNotificationService implements NotificationService {
    public void send(String message) {
        // Send email
    }
}

@Service
public class SMSNotificationService implements NotificationService {
    public void send(String message) {
        // Send SMS
    }
}

@Service
public class MultiChannelNotificationService implements NotificationService {
    private final List<NotificationService> services;
    
    public MultiChannelNotificationService(List<NotificationService> services) {
        this.services = services;
    }
    
    public void send(String message) {
        services.forEach(service -> service.send(message));
    }
}

// 3. Depend on interface
@Service
public class OrderProcessor {
    private final NotificationService notificationService;
    
    @Autowired
    public OrderProcessor(@Qualifier("emailNotificationService") 
                         NotificationService notificationService) {
        this.notificationService = notificationService; // Any implementation
    }
    
    public void processOrder(Order order) {
        // Process order
        notificationService.send("Order confirmed"); // Flexible
    }
}

// 4. Easy testing with mocks
@Test
public void testOrderProcessor() {
    NotificationService mockService = mock(NotificationService.class);
    OrderProcessor processor = new OrderProcessor(mockService);
    processor.processOrder(new Order());
    verify(mockService).send(anyString());
}

// 5. Easy to switch implementations via configuration
@Configuration
public class AppConfig {
    @Bean
    @Primary
    public NotificationService notificationService() {
        // Can easily switch between email, SMS, or multi-channel
        return new EmailNotificationService();
    }
}
```

---

### 8. What is a Spring Bean?

**Expected Answer:**
A Spring Bean is an object that is instantiated, assembled, and managed by the Spring IoC container. Beans are created based on configuration metadata provided through annotations (@Component, @Service, @Repository), XML, or Java configuration (@Bean). The container controls the bean's lifecycle (creation, initialization, destruction), handles dependency injection, applies AOP proxies, and manages scope. By default, beans are singletons, but you can configure different scopes like prototype, request, or session.

**Key Theoretical Concepts:**
- Container-managed objects with full lifecycle control
- Bean scopes (singleton, prototype, request, session, application)
- Bean lifecycle (instantiation → dependency injection → initialization → usage → destruction)
- Bean definition and registration mechanisms
- Bean naming conventions and qualifiers
- Dependency resolution through autowiring
- Lazy vs eager initialization
- Custom initialization and destruction callbacks

**Example:**
```java
// Bean definition using stereotype annotations
@Component
public class EmailValidator {
    // Spring creates singleton instance by default
}

@Service // Specialized @Component for service layer
public class UserService {
    // Singleton bean managed by Spring
}

@Repository // Specialized @Component for persistence layer
public class UserRepository {
    // Adds persistence exception translation
}

// Bean definition using @Bean in @Configuration
@Configuration
public class AppConfig {
    
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        ds.setUsername("root");
        return ds;
    }
    
    @Bean(name = "customUserService", initMethod = "init", destroyMethod = "cleanup")
    @Scope("prototype") // New instance for each request
    @Lazy // Created only when first requested
    public UserService userService(UserRepository repository) {
        return new UserService(repository);
    }
    
    @Bean
    @Primary // Preferred when multiple beans of same type exist
    public PaymentService paymentService() {
        return new PayPalPaymentService();
    }
}

// Bean lifecycle callbacks
@Component
public class DatabaseConnectionPool {
    
    @PostConstruct
    public void initialize() {
        System.out.println("Initializing connection pool...");
    }
    
    @PreDestroy
    public void cleanup() {
        System.out.println("Closing connections...");
    }
}

// Retrieving beans from ApplicationContext
@Component
public class BeanExample {
    @Autowired
    private ApplicationContext context;
    
    public void demonstrateBeans() {
        // Get bean by type
        UserService service = context.getBean(UserService.class);
        
        // Get bean by name
        UserService service2 = context.getBean("customUserService", UserService.class);
        
        // Check if bean exists
        boolean exists = context.containsBean("userService");
        
        // Get all beans of a type
        Map<String, UserService> services = context.getBeansOfType(UserService.class);
    }
}
```

---

### 9. What is ApplicationContext?

**Expected Answer:**
ApplicationContext is the central interface in Spring that represents the IoC container and provides the application configuration. It's an advanced container that extends BeanFactory and provides comprehensive bean management plus enterprise features. ApplicationContext loads bean definitions from configuration sources, instantiates beans eagerly (by default), injects dependencies, and provides additional features like event propagation, internationalization, resource loading, and AOP support. It's the primary interface for interacting with the Spring container.

**Key Theoretical Concepts:**
- Bean factory functionality with lifecycle management
- Event publication and listening mechanism
- Internationalization (MessageSource) support
- Resource loading from various sources
- Environment abstraction for profiles and properties
- Eager singleton bean initialization
- Integration with Spring AOP for proxies
- Hierarchy support for parent-child contexts

**Example:**
```java
// Creating ApplicationContext in Spring Boot
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyApplication.class, args);
        
        // 1. Bean Management
        UserService service = context.getBean(UserService.class);
        UserService namedService = context.getBean("userService", UserService.class);
        boolean exists = context.containsBean("userService");
        Map<String, UserService> services = context.getBeansOfType(UserService.class);
        String[] beanNames = context.getBeanDefinitionNames();
        
        // 2. Environment and Properties
        Environment env = context.getEnvironment();
        String dbUrl = env.getProperty("spring.datasource.url");
        String[] activeProfiles = env.getActiveProfiles();
        
        // 3. Event Publishing
        context.publishEvent(new UserCreatedEvent(user));
    }
}

// Event Handling
@Component
public class UserEventListener {
    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        System.out.println("User created: " + event.getUser().getName());
    }
    
    @EventListener
    @Async
    public void handleUserCreatedAsync(UserCreatedEvent event) {
        // Asynchronous event handling
    }
}

// Custom Event
public class UserCreatedEvent extends ApplicationEvent {
    private User user;
    
    public UserCreatedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
    
    public User getUser() { return user; }
}

// Internationalization
@Service
public class MessageService {
    @Autowired
    private MessageSource messageSource;
    
    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }
}

// Resource Loading
@Component
public class ResourceLoader {
    @Autowired
    private ApplicationContext context;
    
    public void loadResources() throws IOException {
        Resource resource = context.getResource("classpath:config.properties");
        Resource urlResource = context.getResource("https://example.com/data.json");
        Resource fileResource = context.getResource("file:///data/users.csv");
        
        InputStream is = resource.getInputStream();
        // Use resource
    }
}
```

---

### 10. Difference between BeanFactory and ApplicationContext

**Expected Answer:**
**BeanFactory**:
- Basic IoC container providing fundamental dependency injection features
- Lazy bean initialization - creates beans only when first requested
- Lightweight, minimal memory footprint
- Suitable for resource-constrained environments
- Manual registration of BeanPostProcessors

**ApplicationContext**:
- Advanced container extending BeanFactory with enterprise features
- Eager bean initialization - creates singleton beans at startup
- Event propagation, i18n, AOP, resource loading built-in
- Automatic registration of BeanPostProcessors
- Preferred for all enterprise applications

**Recommendation**: Use ApplicationContext for all applications unless you have severe memory constraints.

**Key Theoretical Concepts:**
- Lazy vs eager bean initialization strategies
- Feature set comparison and enterprise capabilities
- Memory and startup performance implications
- Event handling and propagation mechanisms
- AOP proxy creation timing
- BeanPostProcessor and BeanFactoryPostProcessor registration
- MessageSource and ResourceLoader integration

**Example:**
```java
// BeanFactory - Basic container (rarely used in practice)
Resource resource = new ClassPathResource("beans.xml");
BeanFactory factory = new XmlBeanFactory(resource);
UserService service = factory.getBean(UserService.class); // Created on first call

// ApplicationContext - Full-featured container (RECOMMENDED)
ApplicationContext context = 
    new AnnotationConfigApplicationContext(AppConfig.class);
UserService service = context.getBean(UserService.class); // Already created at startup

// Demonstrate ApplicationContext additional features
@Component
public class ContextFeatures {
    @Autowired
    private ApplicationContext context;
    
    public void demonstrateFeatures() {
        // 1. Event Publishing (Not in BeanFactory)
        context.publishEvent(new CustomEvent(this, "data"));
        
        // 2. Internationalization (Not in BeanFactory)
        String msg = context.getMessage("welcome", null, Locale.US);
        
        // 3. Resource Loading (Not in BeanFactory)
        Resource res = context.getResource("classpath:config.properties");
        
        // 4. Environment Access (Not in BeanFactory)
        Environment env = context.getEnvironment();
        String profile = env.getActiveProfiles()[0];
        
        // 5. Parent Context (Not in BeanFactory)
        ApplicationContext parent = context.getParent();
    }
}

// Startup comparison
@Configuration
public class AppConfig {
    @Bean
    @Lazy // Can make ApplicationContext lazy too
    public ExpensiveService expensiveService() {
        return new ExpensiveService();
    }
}

// BeanFactory behavior
BeanFactory factory = new XmlBeanFactory(resource);
// No beans created yet
ExpensiveService service = factory.getBean(ExpensiveService.class);
// Bean created NOW on first request

// ApplicationContext behavior
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
// All singleton beans created immediately at startup
// (unless marked @Lazy)
```

---

## Spring Boot Basics

---

### 11. What is Spring Boot?

**Expected Answer:**
Spring Boot is an opinionated framework built on top of Spring Framework that simplifies application setup, configuration, and deployment. It provides auto-configuration to automatically configure beans based on classpath dependencies, embedded servlet containers (Tomcat, Jetty, Undertow) eliminating the need for WAR deployment, and starter dependencies that bundle related libraries. Spring Boot follows "convention over configuration", providing sensible defaults while allowing customization. It enables creating production-ready, standalone Spring applications quickly.

**Key Theoretical Concepts:**
- Auto-configuration based on classpath scanning
- Starter dependencies for simplified dependency management
- Embedded servlet containers for standalone execution
- Convention over configuration philosophy
- @SpringBootApplication meta-annotation
- Spring Boot Actuator for production monitoring
- Externalized configuration via application.properties/yml
- Opinionated defaults with full override capability

**Example:**
```java
// Minimal Spring Boot Application - No XML configuration needed
@SpringBootApplication // Combines @Configuration + @EnableAutoConfiguration + @ComponentScan
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
        // Starts embedded Tomcat, auto-configures beans, ready in seconds
    }
}

// application.properties - Simple configuration
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

// REST Controller - Just works, no additional configuration
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}

// Entity - Auto-configured with JPA
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    // getters, setters
}

// Repository - Implementation auto-generated
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}

// Package as executable JAR
// mvn clean package
// java -jar myapp.jar
// Application runs with embedded Tomcat on port 8080
```

---

### 12. Why Spring Boot is preferred over Spring?

**Expected Answer:**
Spring Boot is preferred because it significantly reduces development time and complexity:
- **Zero XML Configuration**: Uses Java-based configuration and annotations
- **Auto-Configuration**: Automatically configures beans based on dependencies on classpath
- **Embedded Servers**: No need for separate server installation or WAR deployment
- **Starter Dependencies**: Simplified dependency management with version compatibility
- **Production-Ready Features**: Built-in health checks, metrics, monitoring via Actuator
- **Rapid Development**: Create working applications in minutes
- **Microservices-Friendly**: Perfect for creating standalone, self-contained services
- **Sensible Defaults**: Works out-of-the-box with ability to customize everything

Spring Boot doesn't replace Spring - it's built on Spring and makes it much easier to use.

**Key Theoretical Concepts:**
- Convention over configuration reduces boilerplate
- Opinionated defaults speed up development
- Starter POMs eliminate dependency hell
- Auto-configuration mechanism using @Conditional
- DevTools for hot-reloading during development
- Spring Initializr for rapid project bootstrapping
- Actuator endpoints for production monitoring
- Simplified testing with @SpringBootTest

**Example:**
```java
// Traditional Spring (Pre-Boot) - Lots of XML and configuration
// web.xml
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>

// applicationContext.xml
<beans>
    <context:component-scan base-package="com.example"/>
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    </bean>
    <bean id="entityManagerFactory" class="..."/>
    <bean id="transactionManager" class="..."/>
</beans>

// Deploy WAR to Tomcat

// Spring Boot - Minimal code, maximum productivity
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: secret
  jpa:
    hibernate:
      ddl-auto: update

// That's it! Run as: java -jar app.jar

// Comparison of dependency management
// Spring - Manual version management
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.10</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.5</version>
</dependency>
// ... many more

// Spring Boot - Single starter
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
// All compatible versions included automatically
```

---

### 13. What are Spring Boot starters?

**Expected Answer:**
Spring Boot starters are pre-configured dependency descriptors that bundle all necessary libraries for a specific functionality with compatible versions. Instead of manually adding and managing multiple dependencies, you add one starter and get everything you need. Common starters include:
- `spring-boot-starter-web`: Spring MVC, REST, Jackson, embedded Tomcat
- `spring-boot-starter-data-jpa`: Spring Data JPA, Hibernate, JDBC
- `spring-boot-starter-security`: Spring Security (core, web, config)
- `spring-boot-starter-test`: JUnit, Mockito, AssertJ, Spring Test
- `spring-boot-starter-actuator`: Production monitoring and metrics

**Key Theoretical Concepts:**
- Dependency aggregation and curation
- Version compatibility management
- Opinionated dependency sets for common use cases
- Transitive dependency resolution
- Customization through exclusions
- Consistent versioning across Spring ecosystem
- Simplified Maven/Gradle configuration

**Example:**
```xml
<!-- pom.xml - Spring Boot Starters -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <!-- Web Starter: Includes Spring MVC, Tomcat, Jackson, Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- JPA Starter: Includes Spring Data JPA, Hibernate, JDBC, Transactions -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Security Starter: Includes Spring Security (core, web, config) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- Validation Starter: Includes Hibernate Validator -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Test Starter: Includes JUnit 5, Mockito, AssertJ, Hamcrest, Spring Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Actuator: Production monitoring, metrics, health checks -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <!-- Database driver - Not a starter but needed -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>

<!-- Excluding dependencies from starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- Add Jetty instead -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

```java
// All these work automatically with appropriate starters
@RestController
public class MyController {
    // spring-boot-starter-web enables this
}

@Entity
public class User {
    // spring-boot-starter-data-jpa enables this
}

public interface UserRepo extends JpaRepository<User, Long> {
    // spring-boot-starter-data-jpa provides implementation
}

@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder; // spring-boot-starter-security provides this
}
```

---

Full expanded answers for all bullets (Beginner → Advanced):

### Spring Basics

Q: What is Spring Framework?
**Expected Answer:** A modular Java framework offering IoC/DI, AOP, and integrations (MVC, Data, Security) to build maintainable enterprise apps.
**Key Concepts:** Inversion of Control, Dependency Injection, bean lifecycle.
**Interviewer Expectation:** Explain how DI improves testability and decoupling.
**Red Flags:** Confusing Spring Boot with core Spring container.

Q: Why use Spring?
**Expected Answer:** Promotes loose coupling, has rich ecosystem, powerful abstractions for transactions, security, data access.

### Spring Boot Basics

Q: What is Spring Boot and auto-configuration?
**Expected Answer:** Boot simplifies setup via starters and auto-configuration that configures beans based on classpath and properties. `@SpringBootApplication` bundles common annotations.

Q: Embedded servlet containers
**Expected Answer:** Boot packages embedded servers (Tomcat/Jetty) enabling runnable JARs.

### Annotations

Q: `@Component`, `@Service`, `@Repository`, `@Autowired`
**Expected Answer:** Component stereotypes categorize beans; `@Repository` adds persistence exception translation; `@Autowired` injects dependencies.

### REST API

Q: `@Controller` vs `@RestController`, `@RequestParam` vs `@PathVariable`
**Expected Answer:** `@RestController` = `@Controller` + `@ResponseBody`; `@RequestParam` reads query params; `@PathVariable` maps path segments.

Q: `@RequestBody`, `ResponseEntity`
**Expected Answer:** `@RequestBody` binds JSON to object; `ResponseEntity` controls status and headers.

### Spring Data JPA

Q: What is Spring Data JPA and `JpaRepository`?
**Expected Answer:** Provides repository interfaces for CRUD and pagination; `JpaRepository` adds JPA-specific operations.

Q: Transactions and `@Transactional`
**Expected Answer:** `@Transactional` declares transaction boundaries; explain propagation and rollback rules.

### Relationships and Fetching

Q: OneToMany/ManyToOne and FetchType
**Expected Answer:** Define relationships with `@JoinColumn`; default fetch strategies differ; lazy vs eager affects query behavior and N+1 issues.

### Advanced Internals

Q: N+1 problem and solutions
**Expected Answer:** Occurs with lazy relationships; solve with fetch joins, DTO projection, or batch fetching.

Q: Exception handling
**Expected Answer:** Use `@ControllerAdvice` and `@ExceptionHandler` for global handling.

Q: Validation
**Expected Answer:** Use `@Valid` and bean validation annotations; handle `MethodArgumentNotValidException`.

### Spring Security

Q: Authentication vs Authorization, JWT
**Expected Answer:** Authn verifies identity; authz checks roles/permissions. JWT is common for stateless APIs; use `PasswordEncoder` and configure `SecurityFilterChain`.

### Internals & Deployment

Q: Application startup and profiles
**Expected Answer:** Boot auto-configuration triggers bean creation; use profiles to separate env configs; `@ConfigurationProperties` binds typed properties.

### Testing

Q: Unit vs Integration testing, `@SpringBootTest`, `@WebMvcTest`
**Expected Answer:** Use slice tests (`@WebMvcTest`) for controllers and `@SpringBootTest` for full context; Mockito for mocking dependencies.

---

I'll expand every bullet into the full structured block now — confirm and I'll finalise the four files with the full per-question format for every original bullet.
