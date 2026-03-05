Here are the answers with **examples and bolded keywords**:

## **Spring Basics **

### **1. What is Spring Framework?**
- Comprehensive, **modular framework** for enterprise Java applications  
  *Example: You can use Spring Core for DI, Spring MVC for web, Spring Security for auth*
- Provides **infrastructure support** for application development  
  *Example: Transaction management, data access exception hierarchy*
- Core feature: **Dependency Injection (Inversion of Control container)**  
  *Example: `@Autowired` annotation to inject dependencies*
- Not just one framework but an **ecosystem** (Spring Boot, Security, Data, Cloud, etc.)
- Promotes **POJO-based programming model**  
  *Example: Plain Java classes with annotations, no need to extend framework classes*
- Offers **abstraction layers** for transaction management, data access, web MVC

**Theoretical Keywords:**  
**Enterprise framework**, **Modular**, **Dependency Injection**, **Ecosystem**, **Infrastructure support**, **POJO**

---

### **2. Why do we use Spring?**
- Simplifies **enterprise Java development**  
  *Example: Instead of manual JDBC connection management, use `JdbcTemplate`*
- Enables **loose coupling** through Dependency Injection  
  *Example: `@Service` class depends on `@Repository` interface, not concrete class*
- Provides **Aspect-Oriented Programming (AOP)** for cross-cutting concerns  
  *Example: Add logging to all service methods with one aspect class*
- Eliminates **boilerplate code** with template classes  
  *Example: `JdbcTemplate` handles connection, statement, exception handling*
- Offers comprehensive **transaction management**  
  *Example: `@Transactional` annotation for declarative transactions*
- Provides **MVC framework** for web applications  
  *Example: `@Controller`, `@RequestMapping` annotations*
- Easy **integration** with other technologies  
  *Example: `@Entity` JPA classes work with Spring Data JPA*
- Promotes **testable code** design  
  *Example: Use `Mockito` to mock dependencies in unit tests*

**Theoretical Keywords:**  
**Simplification**, **Loose coupling**, **AOP**, **Boilerplate reduction**, **Transaction management**, **Integration**, **Testability**

---

### **3. What problems does Spring solve?**
- **Tight coupling** between components through DI  
  *Example: Without Spring: `new UserServiceImpl(new UserRepositoryImpl())`*  
  *With Spring: `@Autowired UserService userService;`*
- **Boilerplate code** in JDBC, transactions, etc.  
  *Example: Manual try-catch-finally for connections vs `JdbcTemplate.update()`*
- **Complex configuration** of enterprise applications  
  *Example: XML configuration files replaced by Java Config or annotations*
- **Cross-cutting concerns** mixing with business logic  
  *Example: Logging code scattered vs centralized in `@Aspect` class*
- **Testing difficulties** by promoting POJO-based development  
  *Example: Can test service without starting full container using mocks*
- **Integration challenges** between different technologies  
  *Example: Spring Boot starters auto-configure Hibernate, Jackson, etc.*
- **Transaction management** complexity  
  *Example: Programmatic `beginTransaction()/commit()` vs `@Transactional`*
- **Resource management** issues  
  *Example: Connection pooling handled by Spring/Spring Boot*

**Theoretical Keywords:**  
**Tight coupling**, **Boilerplate code**, **Configuration complexity**, **Cross-cutting concerns**, **Testability**, **Integration**, **Transaction management**

---

### **4. What is IoC (Inversion of Control)?**
- **Design principle** where control of object creation and wiring is inverted
- Traditional approach: Objects create their own dependencies  
  *Example: `Car car = new Car(new Engine());` - Car controls Engine creation*
- IoC approach: **Container creates and manages objects**  
  *Example: Spring creates Car and Engine, injects Engine into Car*
- **Control shifts** from application code to framework/container
- Enables **loose coupling** between components
- Core principle behind **Spring framework**  
  *Example: Spring container manages beans, their lifecycle and dependencies*

**Theoretical Keywords:**  
**Design principle**, **Container management**, **Control inversion**, **Loose coupling**, **Framework responsibility**

---

### **5. What is Dependency Injection?**
- **Implementation of IoC principle**
- **Technique** where objects receive dependencies from external source
- **Dependencies are "injected"** rather than created internally  
  *Example: Without DI: `service = new Service(new Repository());`*  
  *With DI: `@Service class Service { @Autowired Repository repo; }`*
- Three types: **Constructor, Setter, Field injection**
- Promotes **separation of concerns**  
  *Example: Service doesn't need to know how Repository is created*
- Makes code more **modular and testable**  
  *Example: Can inject mock Repository for testing*

**Theoretical Keywords:**  
**IoC implementation**, **External dependency provision**, **Injection types**, **Separation of concerns**, **Testability**

---

### **6. Types of dependency injection**
- **Constructor Injection**: Dependencies provided via constructor  
  *Example: `public UserService(UserRepository repo) { this.repo = repo; }`*  
  - **Advantages**: Immutable objects, ensures dependency availability
  - **Spring's recommended** approach
  
- **Setter Injection**: Dependencies via setter methods  
  *Example: `public void setRepository(UserRepository repo) { this.repo = repo; }`*  
  - **Advantages**: Flexible, allows optional dependencies
  
- **Field Injection**: Direct field injection using @Autowired  
  *Example: `@Autowired private UserRepository repo;`*  
  - **Disadvantages**: Hard to test, breaks encapsulation
  
- **Interface Injection** (less common in Spring)

**Theoretical Keywords:**  
**Constructor Injection**, **Setter Injection**, **Field Injection**, **Immutable objects**, **Flexibility**, **Testability**

---

### **7. Difference between tight coupling and loose coupling**

**Tight Coupling Example:**
```java
// Tight Coupling
class PDFReport {
    private PDFGenerator generator = new PDFGenerator();
    // Direct dependency on concrete class
}

// Changes affect this class
class PDFGenerator {
    public void generate() { /* PDF specific */ }
}
```

**Loose Coupling Example:**
```java
// Loose Coupling
interface ReportGenerator {
    void generate();
}

class PDFReport {
    private ReportGenerator generator;
    // Depends on interface
    
    public PDFReport(ReportGenerator gen) {
        this.generator = gen;  // Can be PDF, Excel, etc.
    }
}
```

**Tight Coupling:**
- **Direct dependency** between classes
- **Changes in one class** affect dependent classes  
  *Example: Change PDFGenerator signature breaks PDFReport*
- **Hard to test** (cannot mock dependencies)
- **Low reusability**
- **Compile-time dependency**

**Loose Coupling:**
- **Indirect dependency** through interfaces/abstractions
- **Changes isolated**, minimal impact on dependent classes  
  *Example: Can change PDFGenerator implementation without changing PDFReport*
- **Easy to test** (can mock dependencies)  
  *Example: Use Mockito to mock ReportGenerator*
- **High reusability**  
  *Example: PDFReport can work with any ReportGenerator implementation*
- **Runtime dependency resolution**

**Theoretical Keywords:**  
**Direct dependency**, **Change impact**, **Testability**, **Reusability**, **Abstraction**, **Interfaces**

---

### **8. What is a Spring Bean?**
- **Object managed** by Spring IoC container  
  *Example: Classes annotated with `@Component`, `@Service`, `@Repository`*
- **Instantiated, assembled, and managed** by Spring framework
- **Created from configuration metadata**  
  *Example: `@Bean` method in `@Configuration` class, or XML `<bean>`*
- **Singleton by default** (one instance per container)  
  *Example: All `@Autowired UserService` get same instance*
- **Lifecycle managed** by container  
  *Example: `@PostConstruct` init method, `@PreDestroy` cleanup*
- Can have **different scopes**  
  *Example: `@Scope("prototype")` for new instance each time*
- Identified by **bean name/id**  
  *Example: Default name is class name with lowercase first letter*

**Theoretical Keywords:**  
**Container-managed object**, **Configuration metadata**, **Singleton scope**, **Lifecycle management**, **Spring IoC**

---

### **9. What is ApplicationContext?**
- **Central interface** in Spring framework
- **Advanced container** that extends BeanFactory  
  *Example: `ClassPathXmlApplicationContext`, `AnnotationConfigApplicationContext`*
- Provides **enterprise-specific functionality**:
  - **Internationalization** support  
    *Example: `context.getMessage("welcome.message", null, locale)`*
  - **Event publication** mechanism  
    *Example: `context.publishEvent(new CustomEvent(this))`*
  - **Resource loading** capabilities  
    *Example: `context.getResource("classpath:config.properties")`*
  - **AOP integration**  
    *Example: Automatically applies `@Transactional` aspects*
- **Automatically loads** bean definitions and dependencies  
  *Example: Scans `@ComponentScan` packages on startup*
- **Heavyweight container** with more features than BeanFactory

**Theoretical Keywords:**  
**Advanced container**, **Enterprise features**, **Internationalization**, **Event handling**, **Resource loading**, **AOP integration**

---

### **10. Difference between BeanFactory and ApplicationContext**

**BeanFactory Example:**
```java
// Basic container
Resource resource = new ClassPathResource("beans.xml");
BeanFactory factory = new XmlBeanFactory(resource);
UserService service = factory.getBean("userService");  // Lazy loading
```

**ApplicationContext Example:**
```java
// Advanced container
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
UserService service = context.getBean(UserService.class);  // Eager loading
String message = context.getMessage("error.notfound", null, Locale.US);  // i18n
```

**BeanFactory:**
- **Basic container** with core DI functionality
- **Lazy initialization** of beans  
  *Example: Bean created only when `getBean()` called*
- **Lightweight**, less memory consumption
- **Manual configuration** required  
  *Example: Need to register BeanPostProcessors manually*
- **Limited enterprise features**
- Suitable for **resource-constrained environments**

**ApplicationContext:**
- **Advanced container** (extends BeanFactory)
- **Eager initialization** of singleton beans  
  *Example: All singleton beans created at startup*
- **Heavyweight**, more features
- **Automatic registration** of bean post-processors
- **Enterprise features**: Internationalization, events, AOP  
  *Example: Built-in support for `@Transactional`, `@Scheduled`*
- Preferred in **most Spring applications**  
  *Example: Spring Boot uses `AnnotationConfigApplicationContext`*

**Theoretical Keywords:**  
**Basic vs Advanced container**, **Lazy vs Eager initialization**, **Lightweight vs Heavyweight**, **Manual vs Automatic configuration**, **Enterprise features**