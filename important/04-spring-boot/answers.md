# Spring Boot Interview Questions – Answers



### 1. What is Spring Boot?
* An open-source, opinionated framework built on top of the Spring Framework designed to **simplify the bootstrapping and development** of production-ready Spring applications.
* Provides **standalone, production-grade** applications with minimal configuration.
* Key features: Embedded servers, auto-configuration, and starter dependencies.

### 2. Difference between Spring and Spring Boot.
| Feature | Spring Framework | Spring Boot |
| :--- | :--- | :--- |
| **Purpose** | A comprehensive framework for enterprise Java development. Provides dependency injection, data access, web MVC, etc. | An extension of Spring designed to simplify and accelerate Spring application development. |
| **Configuration** | Requires extensive XML or Java-based configuration (e.g., setting up DispatcherServlet, view resolvers, database connections). | Provides **auto-configuration** based on the classpath, drastically reducing manual configuration. |
| **Deployment** | Requires an external application server (Tomcat, JBoss) to be set up and configured separately. | Comes with **embedded servers** (Tomcat, Jetty, Undertow), allowing applications to be packaged as standalone JARs. |
| **Bootstrapping** | Complex setup to create a basic project. | Provides **starters** and the **Spring Initializr** for rapid project generation. |
| **Production Ready** | Requires additional setup for metrics, health checks, etc. | Built-in features like **Actuator** provide production-ready endpoints out of the box. |

### 3. Why is Spring Boot used?
* **Rapid Application Development:** Significantly reduces development and configuration time.
* **Opinionated Defaults:** Provides sensible defaults, allowing developers to focus on business logic.
* **Standalone Applications:** Creates self-contained applications with embedded servers.
* **Microservices Ready:** Ideal for building lightweight, independently deployable microservices.
* **Production Features:** Built-in health checks, metrics, and externalized configuration.

### 4. What is auto-configuration?
* A core feature of Spring Boot that **automatically configures** your Spring application based on the **JAR dependencies** present on the classpath.
* For example, if `spring-boot-starter-data-jpa` is on the classpath, Spring Boot will automatically configure a `DataSource` and JPA-related beans.
* It uses `@Conditional` annotations (e.g., `@ConditionalOnClass`, `@ConditionalOnMissingBean`) to apply configurations only when certain conditions are met.

### 5. What is `@SpringBootApplication`?
* A convenience annotation that combines three other annotations:
    1.  **`@SpringBootConfiguration`:** Indicates that a class provides Spring Boot application configuration (a specialized form of `@Configuration`).
    2.  **`@EnableAutoConfiguration`:** Enables Spring Boot's auto-configuration mechanism.
    3.  **`@ComponentScan`:** Enables component scanning within the package of the annotated class and its sub-packages.
* It is typically placed on the **main application class**.

### 6. What is an embedded server?
* A web server (like Tomcat, Jetty, or Undertow) that is **bundled within the application's executable JAR file**.
* Eliminates the need to deploy a WAR file to an external server.
* The application can be run using `java -jar yourapp.jar`.

### 7. Difference between Tomcat and Jetty.
| Feature | Tomcat (Apache) | Jetty (Eclipse) |
| :--- | :--- | :--- |
| **Maturity & Community** | Older, larger, more established community. | Slightly newer, very active community. |
| **Design Philosophy** | Servlet container first, with added features. | Designed to be a full-featured, lightweight, embeddable server and servlet container. |
| **Footprint & Performance** | Slightly larger footprint. Generally considered very robust. | Extremely lightweight and optimized for low memory usage and fast startup. Often preferred for embedded use. |
| **Flexibility** | Highly configurable and extensible. | Very modular; you can include only the components you need. |
| **Spring Boot Default** | **Default** embedded server. | Can be used by excluding Tomcat and adding the Jetty starter. |

### 8. What is `application.properties` or `application.yml`?
* External configuration files used to configure Spring Boot application properties.
* Allow for **externalized configuration**, meaning you can change application behavior without recompiling code (e.g., database URLs, server ports, feature flags).
* **`application.properties`:** Uses a key-value pair format (e.g., `server.port=8081`).
* **`application.yml`:** Uses a hierarchical YAML format, which is more readable for complex configurations.
* Files are typically placed in the `src/main/resources` directory. Spring Boot automatically loads them.

### 9. How does Spring Boot start?
1.  The `main` method runs `SpringApplication.run(YourApplication.class, args);`.
2.  Spring Boot **loads all auto-configurations** defined in `spring.factories` files within the starter JARs.
3.  It starts the **embedded web server** (if a web application is detected).
4.  It performs **component scanning** (as defined by `@SpringBootApplication`) to find and register all Spring beans (`@Component`, `@Service`, `@Repository`, `@Controller`).
5.  The application context is refreshed, and all singleton beans are instantiated.

### 10. What is dependency injection?
* A core design pattern of the Spring Framework where **objects define their dependencies (other objects they work with) but do not create them**.
* Instead, an **IoC Container** (ApplicationContext) is responsible for **creating and injecting** those dependencies.
* **Benefits:** Promotes loose coupling, easier testing, and more maintainable code.

### 11. What is Inversion of Control (IoC)?
* A broader principle where the **control of object creation and lifecycle is transferred from the application code to a framework or container** (the Spring IoC Container).
* **Dependency Injection is a specific implementation of IoC.**
* In Spring, the `ApplicationContext` is the IoC container that manages beans.

### 12. What is `@Component`?
* A **stereotype annotation** that marks a Java class as a Spring-managed component.
* During component scanning, Spring automatically detects and registers classes annotated with `@Component` as beans in the `ApplicationContext`.
* It is the generic stereotype for any Spring-managed component. More specific stereotypes include `@Service`, `@Repository`, and `@Controller`.

### 13. Difference between `@Component`, `@Service`, and `@Repository`.
| Annotation | Purpose & Layer | Special Behavior |
| :--- | :--- | :--- |
| **`@Component`** | Generic stereotype for any Spring-managed bean. | Base annotation. No additional behavior beyond bean registration. |
| **`@Service`** | Stereotype specifically for the **service/business logic layer**. | Semantically indicates a business service facade. Functionally identical to `@Component`. |
| **`@Repository`** | Stereotype for the **data access layer** (DAO classes). | In addition to bean registration, it enables **automatic translation of persistence-specific exceptions** (e.g., `PersistenceException`) into Spring's unified `DataAccessException`. |

### 14. What is `@Autowired`?
* An annotation used for **automatic dependency injection**.
* It can be applied to fields, setter methods, and constructors to tell Spring to inject a matching bean from the `ApplicationContext`.
* **Constructor injection is now generally preferred** as it makes dependencies explicit and promotes immutability.

### 15. What is `@RestController`?
* A specialized version of the `@Controller` annotation used for building **RESTful web services**.
* It combines `@Controller` and `@ResponseBody`.
* This means all handler methods within a `@RestController` **automatically serialize their return values into the HTTP response body** (typically as JSON or XML), rather than resolving to a view template.

### 16. Difference between `@Controller` and `@RestController`.
| Feature | `@Controller` | `@RestController` |
| :--- | :--- | :--- |
| **Primary Use** | For building traditional **MVC web applications** with server-side rendered views (JSP, Thymeleaf). | For building **REST APIs** that return data (JSON/XML). |
| **Response** | Handler methods typically return a `String` (view name) or a `ModelAndView` object. | Handler methods return domain objects which are automatically written to the HTTP response body. |
| **Annotations** | Requires `@ResponseBody` on individual methods to return data. | Has `@ResponseBody` semantics built-in for all methods. |
| **Composition** | - | = `@Controller` + `@ResponseBody` |

### 17. What is `@RequestMapping`?
* A generic annotation used at the **class or method level** to map web requests to specific handler methods or classes.
* It can be configured with attributes like `path` (or `value`), `method`, `consumes`, and `produces`.
* **More specific, HTTP-method aligned annotations are now preferred:** `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`.

### 18. Difference between `@GetMapping` and `@PostMapping`.
| Annotation | HTTP Method | Typical Use Case |
| :--- | :--- | :--- |
| **`@GetMapping`** | `GET` | Used to **retrieve** resources. Should be idempotent and safe (no side effects). Parameters often in the URL (query params or path variables). |
| **`@PostMapping`** | `POST` | Used to **create** new resources. Often sends data in the request body (e.g., JSON). Not idempotent (multiple identical requests may create multiple resources). |

### 19. What is Actuator?
* A Spring Boot sub-project that provides **production-ready features** to help you monitor and manage your application.
* It exposes a set of **HTTP endpoints** (like `/actuator/health`, `/actuator/metrics`, `/actuator/info`) that provide insight into the application's state.
* Endpoints can be used for health checks, metrics gathering, auditing, and tracing.
* **Security Note:** These endpoints should be secured in a production environment.

### 20. What is Spring Boot DevTools?
* A module that provides **developer productivity tools**.
* **Key Features:**
    * **Automatic Restart:** Automatically restarts the application when classpath files change (much faster than a cold restart).
    * **Live Reload:** Can trigger a browser refresh when resources change (requires a browser plugin).
    * **Property Defaults:** Sets development-friendly defaults (e.g., disabled template caching).
    * **Remote Debugging Support.**



### 21. How does Spring Boot auto-configuration work internally?
* **Trigger:** The `@EnableAutoConfiguration` annotation (part of `@SpringBootApplication`) enables the mechanism.
* **Process:**
    1. Spring Boot scans the classpath for JAR files containing `META-INF/spring.factories` files.
    2. It loads all configuration classes listed under the `org.springframework.boot.autoconfigure.EnableAutoConfiguration` key.
    3. These configuration classes are annotated with `@Configuration` and use a series of **`@Conditional` annotations** (e.g., `@ConditionalOnClass`, `@ConditionalOnMissingBean`, `@ConditionalOnProperty`) to determine if they should be applied.
    4. If all conditions are met, the configuration is loaded, creating the necessary beans.
* **Example:** If `HibernateJpaAutoConfiguration` finds a `DataSource` bean and the `EntityManagerFactory` class on the classpath, and no existing `EntityManagerFactory` bean exists, it will auto-configure JPA.

### 22. What are starters?
* **Dependency descriptors** that simplify your Maven/Gradle configuration.
* **Purpose:** To bundle a set of related dependencies (e.g., `spring-boot-starter-web` includes Tomcat, Spring MVC, Jackson) so you don't have to manually specify each one.
* Follow a naming pattern: `spring-boot-starter-*` (e.g., `spring-boot-starter-data-jpa`, `spring-boot-starter-security`).
* They provide **curated dependencies** that are tested to work well together, reducing version conflicts.

### 23. What is Spring Data JPA?
* A part of the larger Spring Data family that **simplifies data access layers** and reduces boilerplate code for JPA-based repositories.
* **Key Features:**
    * **Repository Abstraction:** Allows you to create repository interfaces by extending `JpaRepository`, `CrudRepository`, or `PagingAndSortingRepository`.
    * **Query Derivation:** Automatically generates queries from method names (e.g., `findByLastNameAndFirstName`).
    * **Custom Queries:** Support for `@Query` annotation to define JPQL or native SQL queries.
    * **Pagination and Sorting:** Built-in support out of the box.

### 24. What is Hibernate?
* The **most popular JPA (Java Persistence API) implementation**.
* It is an **Object-Relational Mapping (ORM)** framework that maps Java objects (entities) to database tables and vice versa.
* Handles database operations, connection pooling, caching, and transaction management.
* **Spring Boot Starter:** `spring-boot-starter-data-jpa` includes Hibernate by default.

### 25. What is ORM?
* **Object-Relational Mapping** is a programming technique for converting data between incompatible type systems (objects in Java and tables in a relational database).
* **Benefits:**
    * Increases productivity by reducing boilerplate JDBC code.
    * Provides database independence (to some extent).
    * Manages relationships (one-to-many, many-to-many) between objects.
* **Drawbacks:** Can introduce performance overhead if not used carefully (e.g., N+1 query problem).

### 26. Difference between JPA and Hibernate.
| Concept | JPA | Hibernate |
| :--- | :--- | :--- |
| **Definition** | A **Java specification** (API) for ORM. Defines a set of interfaces and annotations. | An **implementation** of the JPA specification. |
| **Relationship** | The standard (the rulebook). | One of many implementations (Vendor). Others include EclipseLink, OpenJPA. |
| **Scope** | Provides a standard way to map objects to relational databases. | Provides all JPA features plus additional proprietary features (extra annotations, utilities). |
| **Usage** | You write code against the JPA interfaces (`EntityManager`, `Entity`). | In Spring Boot, you typically use it as the underlying JPA provider. |

### 27. What is `@Entity`?
* A JPA annotation that marks a Java class as a **persistent entity**, meaning its instances can be stored in and retrieved from a database table.
* Each instance of the class corresponds to a row in the table.
* The class must have a no-argument constructor and an `@Id` annotation marking its primary key field.

### 28. What is `@Repository`?
* A Spring **stereotype annotation** used at the class level on Data Access Objects (DAOs) or Repository classes.
* **Purposes:**
    1.  **Bean Registration:** Marks the class as a Spring-managed component (like `@Component`).
    2.  **Exception Translation:** Automatically catches persistence-specific exceptions (like those from Hibernate/JPA) and re-throws them as Spring's unified `DataAccessException`. This keeps your service layer clean of vendor-specific exceptions.
* Typically used on interfaces extending `JpaRepository`.

### 29. What is `@Transactional`?
* An annotation that **declaratively defines the scope of a database transaction**.
* Applied at the method or class level.
* **Key Behaviors:**
    * **Atomicity:** Ensures all database operations within the method either complete successfully or roll back entirely.
    * **Propagation:** Defines how transactions relate to each other (e.g., `REQUIRED` – uses existing transaction or creates a new one).
    * **Isolation Level:** Controls the visibility of changes between concurrent transactions.
    * **Rollback Rules:** Defines which exceptions trigger a rollback.
* **Best Practice:** Typically applied on service layer methods, not the repository layer.

### 30. What is lazy vs eager loading?
* Strategies for loading related entities in an ORM.
* **Eager Loading:**
    * The related entity/collection is **loaded immediately** along with the parent entity (via a JOIN query).
    * Defined using `fetch = FetchType.EAGER` on relationship mappings (`@OneToMany`, `@ManyToOne`).
    * Can lead to performance issues if loading large object graphs unnecessarily.
* **Lazy Loading:**
    * The related entity/collection is **loaded only when it is explicitly accessed** for the first time.
    * Defined using `fetch = FetchType.LAZY` (the default for `@OneToMany`, `@ManyToMany`).
    * More efficient but can cause `LazyInitializationException` if the entity is accessed outside the scope of an open persistence context/session.
    * Solved by using **fetch joins** in queries or `@EntityGraph`.

### 31. How do you handle exceptions globally?
* Using **`@ControllerAdvice`** (or `@RestControllerAdvice`) along with `@ExceptionHandler` methods.
* This creates a global, centralized exception handling component that catches exceptions thrown by any controller.
* Allows for consistent error response structures (JSON for APIs) and HTTP status codes across the entire application.

### 32. What is `@ControllerAdvice`?
* A specialization of the `@Component` annotation that allows a class to be a **global interceptor of exceptions** thrown by methods annotated with `@RequestMapping` (or `@GetMapping`, etc.).
* Methods within a `@ControllerAdvice` class can be annotated with `@ExceptionHandler`, `@InitBinder`, and `@ModelAttribute` and apply to all controllers.
* **`@RestControllerAdvice`** is the combination of `@ControllerAdvice` and `@ResponseBody`, used specifically for REST APIs to return error responses directly in the body.

### 33. What is validation in Spring Boot?
* The process of ensuring that incoming data (e.g., from an API request) meets certain constraints before processing.
* **Java Bean Validation API (JSR 380):** The standard, implemented by **Hibernate Validator**.
* Uses annotations like `@NotNull`, `@Size`, `@Email`, `@Min`, `@Max` on fields of request DTOs or entity classes.
* **Triggered** by annotating a controller method parameter with `@Valid` or `@Validated`.

### 34. What is `@Valid`?
* An annotation used on **method parameters** (or fields) to trigger validation of the annotated object.
* When placed before a `@RequestBody` parameter in a controller method, Spring automatically validates the object upon request binding.
* If validation fails, a `MethodArgumentNotValidException` is thrown, which can be caught by a `@ExceptionHandler` to return a structured error response (e.g., 400 Bad Request with error details).

### 35. How do you secure Spring Boot applications?
* Primarily using the **Spring Security** framework.
* **Common Steps:**
    1. Add the `spring-boot-starter-security` dependency.
    2. Configure security rules (URL access, authentication method) via a configuration class extending `WebSecurityConfigurerAdapter` (pre-Spring Security 5.7) or by registering a `SecurityFilterChain` bean.
    3. Choose an authentication mechanism: In-memory, JDBC, LDAP, OAuth2, or JWT.
    4. Use annotations like `@PreAuthorize`, `@Secured` for method-level security.

### 36. What is Spring Security?
* A powerful and highly customizable **authentication and access-control framework** for Java applications.
* It is the de facto standard for securing Spring-based applications.
* **Core Features:**
    * Authentication (who are you?)
    * Authorization (what are you allowed to do?)
    * Protection against common attacks (CSRF, session fixation, clickjacking).
    * Integration with various authentication providers.

### 37. What is JWT authentication?
* **JSON Web Token (JWT)** is a compact, URL-safe token standard used for **stateless authentication** in APIs.
* **Flow in Spring Boot:**
    1. Client sends credentials to a login endpoint.
    2. Server validates credentials and generates a signed JWT (containing user claims/roles) and returns it.
    3. Client includes the JWT in the `Authorization: Bearer <token>` header of subsequent requests.
    4. Server validates the JWT signature and extracts user identity/authorities for each request, eliminating the need for server-side sessions.
* Implemented using libraries like **jjwt** or with Spring Security OAuth2 Resource Server.

### 38. How do you handle CORS?
* **Cross-Origin Resource Sharing** is a mechanism that allows or restricts web pages from making requests to a domain different from the one that served the page.
* **In Spring Boot, configuration can be done:**
    1. **Globally:** By defining a `WebMvcConfigurer` bean and overriding the `addCorsMappings` method.
    2. **Per Controller/Method:** Using the `@CrossOrigin` annotation.
* Configuration includes allowed origins, HTTP methods, headers, and whether credentials are allowed.

### 39. How do you connect to external APIs?
* **Spring's `RestTemplate`** (synchronous, template-based) – being superseded.
* **Spring WebClient** (asynchronous, reactive, non-blocking) – recommended for Spring 5+.
* **Third-party clients** like Feign (from Spring Cloud OpenFeign) which provides a declarative, interface-based REST client.
* **Key Practices:** Externalize API URLs and credentials in configuration, use connection pooling, implement retry and circuit breaker patterns for resilience.

### 40. How do you write unit tests in Spring Boot?
* **Layer & Tools:**
    * **Unit Tests (Isolated):** Use **JUnit 5** and **Mockito** to test individual components (services, utilities) in isolation by mocking their dependencies.
    * **Slice Tests:** Use Spring Boot test annotations to test a specific layer:
        * `@WebMvcTest` – for testing the web layer (controllers) in isolation.
        * `@DataJpaTest` – for testing the JPA persistence layer with an embedded database.
        * `@JsonTest` – for testing JSON serialization/deserialization.
    * **Integration Tests:** Use `@SpringBootTest` to load the full application context and test the integration of multiple layers, often with a test database (e.g., H2).
* **Best Practice:** Aim for a high proportion of fast, isolated unit tests, complemented by a smaller set of integration tests.

##  Senior / Architect Level

### 41. Explain Spring Boot architecture.
* **Layered (n-tier) architecture** is the most common pattern:
    1.  **Presentation Layer:** Controllers (`@RestController`) handling HTTP requests/responses, validation, and serialization.
    2.  **Business/Service Layer:** Services (`@Service`) containing the core business logic, orchestrating calls to repositories and other services. Enforces transactions (`@Transactional`).
    3.  **Persistence/Data Access Layer:** Repositories (`@Repository`) interfacing with the database via JPA/Hibernate.
    4.  **Database Layer:** The actual RDBMS or NoSQL store.
* **Cross-Cutting Concerns:** Security (Spring Security), logging, monitoring (Actuator), and external configuration are woven across the layers.
* The **Spring IoC Container** (`ApplicationContext`) is the core, managing the lifecycle and dependencies of all beans across these layers.

### 42. How do you design scalable Spring Boot microservices?
* **Decouple and Define Boundaries:** Use Domain-Driven Design (DDD) to define bounded contexts. Each microservice owns its data and exposes a clear API.
* **Stateless Design:** Services should not maintain client session state, allowing any instance to handle any request. Store state externally (e.g., in a database or Redis).
* **Independent Deployment & Scaling:** Each service is packaged, deployed, and scaled independently (often as Docker containers orchestrated by Kubernetes).
* **Resilience:** Implement circuit breakers (Resilience4j), retries, fallbacks, and bulkheads to handle failures gracefully.
* **API-First Design:** Use OpenAPI/Swagger to define contracts before implementation.
* **Event-Driven Communication:** Use messaging (Kafka, RabbitMQ) for asynchronous, loosely-coupled integration.

### 43. How do you implement service-to-service communication?
* **Synchronous:**
    * **REST HTTP APIs:** Most common. Use WebClient or Feign with client-side load balancing (Spring Cloud LoadBalancer).
    * **gRPC:** For high-performance, low-latency, polyglot environments.
* **Asynchronous (Event-Based):**
    * **Message Brokers:** Apache Kafka (for event streaming, high throughput) or RabbitMQ (for traditional message queuing). Services publish and subscribe to events.
* **Considerations:** Timeouts, retries, idempotency (for retries), and implementing the Circuit Breaker pattern are critical for synchronous calls.

### 44. What is Spring Cloud?
* An umbrella project that provides tools for developers to quickly build common patterns in **distributed systems** (microservices).
* It builds on top of Spring Boot to provide features for:
    * **Configuration Management** (Spring Cloud Config)
    * **Service Discovery** (Spring Cloud Netflix Eureka, Consul integration)
    * **Client-Side Load Balancing** (Spring Cloud LoadBalancer)
    * **API Gateway** (Spring Cloud Gateway)
    * **Circuit Breakers** (Spring Cloud Circuit Breaker with Resilience4j or Sentinel)
    * **Distributed Tracing** (Spring Cloud Sleuth with Zipkin)

### 45. What is Config Server?
* A **centralized external configuration management** service (part of Spring Cloud Config).
* Microservices fetch their configuration (from `application.properties`/`yml`) from a central Config Server on startup, rather than having it bundled in their JAR.
* Configurations are typically stored in a version-controlled repository (like Git).
* **Benefits:** Allows changing configuration across all services without rebuilding/redeploying them, and provides a single source of truth.

### 46. What is Eureka / Service Discovery?
* **Service Discovery** is the pattern where microservices register themselves at startup and can find the network locations of other services they need to communicate with.
* **Netflix Eureka** (integrated with Spring Cloud Netflix) is a **Service Registry & Discovery Server**.
    * **Eureka Server:** The registry where services register themselves.
    * **Eureka Client:** Integrated into each microservice. It registers with the server and fetches the registry to discover other services.
* This enables **client-side load balancing** and makes the system resilient to IP/port changes.

### 47. What is API Gateway?
* A single entry point for all client requests in a microservice architecture.
* **Responsibilities (handled by Spring Cloud Gateway or Zuul):**
    * **Routing:** Directing requests to the appropriate backend service.
    * **Aggregation:** Composing results from multiple services.
    * **Cross-Cutting Concerns:** Centralized authentication/authorization, rate limiting, logging, monitoring, and CORS handling.
    * **Load Balancing.**
* It decouples clients from the internal microservice topology.

### 48. How do you implement circuit breaker?
* The Circuit Breaker pattern prevents a network or service failure from cascading to other services.
* **Implementation:**
    1. Use **Spring Cloud Circuit Breaker** with an implementation like **Resilience4j** (or formerly Hystrix).
    2. Wrap a call to an external service with a circuit breaker.
    3. **States:**
        * **CLOSED:** Requests pass through normally.
        * **OPEN:** After failures exceed a threshold, the circuit opens and calls fail immediately (fast failure) without attempting the remote call.
        * **HALF-OPEN:** After a timeout, a trial request is allowed. Success closes the circuit; failure re-opens it.
* Provides fallback methods to return a default response when the circuit is open.

### 49. What is Resilience4j?
* A **lightweight, modular fault tolerance library** designed for Java 8 and functional programming.
* It is the recommended Circuit Breaker implementation for Spring Boot/Spring Cloud (replacing Hystrix).
* **Core Modules:**
    * **CircuitBreaker:** To prevent cascading failures.
    * **RateLimiter:** To limit the number of requests in a time period.
    * **Bulkhead:** To limit concurrent calls to a service.
    * **Retry:** To automatically retry failed calls.
    * **TimeLimiter:** To limit the time spent waiting for a call.

### 50. How do you handle distributed transactions?
* The traditional **Two-Phase Commit (2PC / XA transactions)** is complex and often a performance bottleneck in microservices.
* **Modern Patterns:**
    * **Saga Pattern:** A sequence of local transactions, each updating data within a single service. If a step fails, compensating transactions (rollback actions) are executed to undo the previous steps. Can be choreographed (events) or orchestrated (central coordinator).
    * **Eventual Consistency:** Accept that data will be consistent across services eventually, not immediately. Use events to propagate changes asynchronously.
    * **Transactional Outbox Pattern:** Append events/messages to an "outbox" table as part of the local transaction. A separate process reads the outbox and publishes the events to the message broker, ensuring atomicity.
* The choice depends on the business requirements for consistency vs. availability.

### 51. How do you implement messaging (Kafka / RabbitMQ)?
* **Spring for Apache Kafka / Spring AMQP (for RabbitMQ)** provide abstractions to simplify integration.
* **Key Components:**
    * **Message Producer/Template:** `KafkaTemplate` or `RabbitTemplate` to send messages.
    * **Message Listeners/Consumers:** Use `@KafkaListener` or `@RabbitListener` to annotate methods that process incoming messages.
    * **Configuration:** Connection factories, serializers/deserializers, topic/queue definitions.
* **Considerations:** Idempotent message processing, retry with dead-letter queues (DLQ), message ordering, and consumer group management (Kafka).

### 52. How do you secure microservices?
* **API-Level Security:**
    * Use **OAuth 2.0 / OpenID Connect (OIDC)** with JWT tokens. An API Gateway or individual services validate the token.
    * **JWT tokens** should be signed (JWS) and can be encrypted (JWE) for sensitive data.
* **Service-to-Service Security:**
    * **Mutual TLS (mTLS)** for encrypting and authenticating all internal traffic.
    * API keys or client credentials grant for machine-to-machine communication.
* **Secrets Management:** Store sensitive configuration (DB passwords, API keys) in a dedicated vault (HashiCorp Vault, AWS Secrets Manager) instead of config files.

### 53. How do you handle performance tuning?
* **Application Level:**
    * **Database:** Optimize queries (use indexes, avoid N+1 selects), use connection pooling (HikariCP), implement caching (Spring Cache with Redis/Caffeine).
    * **JVM Tuning:** Set appropriate heap sizes (`-Xms`, `-Xmx`), choose a garbage collector (G1 for most web apps).
    * **Code:** Use async processing (`@Async`, WebFlux), minimize blocking I/O, avoid large object allocations in hot paths.
* **Infrastructure Level:** Horizontal scaling (more instances), proper resource allocation (CPU/memory), CDN for static content.

### 54. How do you manage application configuration securely?
* **Do not** commit secrets (passwords, API keys) to source control.
* **Externalize Configuration:** Use environment variables, OS environment, or cloud provider secret stores.
* **Use a Secrets Manager:** Integrate with **HashiCorp Vault**, **AWS Secrets Manager**, or **Azure Key Vault**. The application fetches secrets at runtime.
* **Spring Cloud Config Server** can integrate with Vault as a backend for secure property sources.

### 55. How do you implement logging and monitoring?
* **Logging:**
    * Use **SLF4J** with **Logback** (default) or **Log4j2**.
    * Structure logs as JSON for easy parsing by log aggregators.
    * Use **MDC (Mapped Diagnostic Context)** to add trace IDs to all logs from a single request.
* **Monitoring:**
    * **Spring Boot Actuator** exposes `/actuator/metrics`, `/actuator/health`.
    * **Micrometer:** The metrics facade that integrates Actuator metrics with external monitoring systems like **Prometheus** (for scraping) and **Grafana** (for visualization).
    * **Distributed Tracing:** Use **Spring Cloud Sleuth** (now part of Micrometer) with **Zipkin** or **Jaeger** to trace requests across service boundaries.

### 56. How do you deploy Spring Boot applications?
* **As a JAR:** The standard approach. `java -jar app.jar`. Run on a VM or inside a **Docker container**.
* **As a Docker Container:**
    * Create a minimal Docker image using a `jdk` or `jre` base image, or use **distroless** images for security.
    * Use a multi-stage Dockerfile to separate build and runtime layers.
* **Orchestration:** Deploy containers to **Kubernetes** (K8s) or a cloud PaaS (**AWS Elastic Beanstalk**, **Azure App Service**, **Google App Engine**).

### 57. How do you handle zero-downtime deployments?
* **Blue-Green Deployment:** Have two identical production environments (Blue and Green). Route live traffic to one (e.g., Blue). Deploy the new version to the idle environment (Green). Test it. Switch the router to send all traffic to Green. Roll back by switching back to Blue.
* **Canary Releases:** Roll out the new version to a small subset of users/servers first. Monitor for errors. Gradually increase the traffic to the new version.
* **In Kubernetes:** Use Deployment strategies with **readiness probes**. New pods are created and pass readiness checks before old pods are terminated (rolling update).
* **Prerequisites:** Stateless application design, backward-compatible database migrations, and session externalization.

### 58. What are common Spring Boot anti-patterns?
* **The Monolithic "Microservice":** Creating a service that is too large and does too much (a distributed monolith).
* **Tight Coupling:** Services communicating directly via databases or using shared libraries that create version lock-in.
* **Ignoring Circuit Breakers:** Not implementing resilience patterns for synchronous inter-service calls.
* **Logging Too Little (or Too Much):** Not having structured, queryable logs for debugging production issues.
* **Hardcoding Configuration:** Committing environment-specific config (like prod DB URL) to source control.
* **God Service Classes:** Not separating concerns within a service, leading to massive, unmaintainable `@Service` classes.
* **Transactional Service Layer Overuse:** Wrapping entire service methods in `@Transactional`, leading to long database connections and potential deadlocks.

### 59. How do you handle backward compatibility?
* **API Versioning:**
    * **URI Versioning:** `/api/v1/resource`, `/api/v2/resource`.
    * **Header Versioning:** `Accept: application/vnd.myapp.v1+json`.
* **Evolutionary Design:**
    * **Add, Don't Change:** For REST APIs, add new fields/endpoints but avoid changing or removing existing ones for active consumers.
    * **Deprecation Cycle:** Mark old APIs as `@Deprecated` and communicate a sunset date to consumers.
    * **Consumer-Driven Contracts:** Use tools like **Pact** to ensure providers don't break consumer expectations.
* **Database Migrations:** Use tools like **Flyway** or **Liquibase** to apply backward-compatible schema changes (add columns, new tables) without breaking existing code.

### 60. When should you NOT use Spring Boot?
* **Extremely Resource-Constrained Environments:** Where the memory/CPU overhead of the JVM and Spring container is prohibitive (e.g., some IoT edge devices). Consider Go or Rust.
* **Simple, Short-Lived Scripts or Utilities:** The startup time and configuration of Spring Boot is overkill. Use a scripting language.
* **Applications Requiring Ultra-Low Latency (<1ms):** The garbage collector and framework layers can introduce unpredictable pauses. Consider C++, Rust, or specialized Java (with GraalVM native image).
* **Team Expertise:** If the team has deep expertise in another framework (e.g., Micronaut, Quarkus) that better fits the project's specific needs (like native compilation).
* **When You Need Full Control Over Every Component:** Spring Boot's opinionated defaults and auto-configuration can be a barrier if you need to heavily customize low-level infrastructure.