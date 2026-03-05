Here are the answers with **examples and bolded keywords**:

## **Spring Boot Basics **

### **11. What is Spring Boot?**
- **Opinionated framework** built on top of Spring
- **Simplifies Spring application** setup and development  
  *Example: Zero XML configuration, sensible defaults*
- Provides **production-ready features** out of the box  
  *Example: Health checks, metrics, externalized configuration*
- **Auto-configuration** automatically configures beans based on dependencies
- **Standalone applications** with embedded servers  
  *Example: Runs as executable JAR with embedded Tomcat*
- **Microservices ready** with Spring Cloud integration
- **Spring Boot starters** for easy dependency management

**Theoretical Keywords:**  
**Opinionated framework**, **Auto-configuration**, **Embedded server**, **Standalone**, **Production-ready**, **Microservices**

---

### **12. Why Spring Boot is preferred over Spring?**
- **Rapid development** with minimal configuration  
  *Example: Create REST API in minutes vs hours with Spring MVC*
- **No XML configuration** required  
  *Example: Spring Boot uses Java config and annotations*
- **Embedded servers** eliminate deployment complexity  
  *Example: No need to deploy WAR to external Tomcat*
- **Production-ready features** built-in  
  *Example: Actuator endpoints for monitoring (`/health`, `/metrics`)*
- **Simplified dependency management** with starters  
  *Example: Add `spring-boot-starter-web` for web app*
- **Auto-configuration** reduces boilerplate  
  *Example: Database auto-configured if dependency present*
- **Easier testing** with test slices  
  *Example: `@WebMvcTest` for MVC layer, `@DataJpaTest` for JPA*
- **Microservices friendly** with Spring Cloud

**Theoretical Keywords:**  
**Rapid development**, **Zero XML**, **Embedded servers**, **Production-ready**, **Dependency management**, **Auto-configuration**

---

### **13. What are Spring Boot starters?**
- **Dependency descriptors** that bundle common dependencies  
  *Example: `spring-boot-starter-web` includes Tomcat, Spring MVC, Jackson*
- Provide **opinionated dependencies** for specific functionalities  
  *Example: `spring-boot-starter-data-jpa` includes Hibernate, Spring Data JPA*
- **Simplify Maven/Gradle configurations**  
  *Example: Instead of 10 individual dependencies, add one starter*
- **Ensure version compatibility** between dependencies
- **Custom starters** can be created for company-specific needs
- Common starters:
  - **`spring-boot-starter-web`** - Web applications
  - **`spring-boot-starter-data-jpa`** - Database access
  - **`spring-boot-starter-security`** - Security
  - **`spring-boot-starter-test`** - Testing
  - **`spring-boot-starter-actuator`** - Production monitoring

**Theoretical Keywords:**  
**Dependency descriptors**, **Opinionated dependencies**, **Version compatibility**, **Simplify configuration**, **Predefined bundles**

---

### **14. What is auto-configuration?**
- **Automatic bean configuration** based on classpath dependencies  
  *Example: If H2 is in classpath, auto-configures in-memory DB*
- **Conditional configuration** using `@Conditional` annotations  
  *Example: `@ConditionalOnClass(DataSource.class)`*
- **Reduces boilerplate configuration**  
  *Example: No need to manually configure `DataSource`, `EntityManagerFactory`*
- **Customizable through properties**  
  *Example: `spring.datasource.url=jdbc:mysql://localhost/db`*
- **Can be overridden** with explicit `@Bean` definitions
- **Auto-configuration report** available at debug level  
  *Example: Shows which auto-configuration classes were applied*

**Theoretical Keywords:**  
**Automatic configuration**, **Conditional beans**, **Classpath scanning**, **Property-driven**, **Overrideable**

---

### **15. What is `@SpringBootApplication`?**
- **Meta-annotation** combining three annotations:
  1. **`@SpringBootConfiguration`** - Marks as configuration class
  2. **`@EnableAutoConfiguration`** - Enables auto-configuration
  3. **`@ComponentScan`** - Scans for components in current package
- **Single annotation** for main application class  
  *Example: `@SpringBootApplication` on class with `main()` method*
- **Enables Spring Boot features** with minimal code
- **Customizable scanning** with parameters  
  *Example: `@SpringBootApplication(scanBasePackages = "com.example")`*
- **Entry point** for Spring Boot applications

**Theoretical Keywords:**  
**Meta-annotation**, **Configuration**, **Auto-configuration**, **Component scanning**, **Entry point**

---

### **16. What is embedded Tomcat?**
- **Tomcat server packaged** inside application JAR  
  *Example: `spring-boot-starter-web` includes Tomcat dependencies*
- **No external server installation** required  
  *Example: Run with `java -jar app.jar`, not deploy to external Tomcat*
- **Default embedded server** in Spring Boot web apps
- **Configurable through properties**  
  *Example: `server.port=8080`, `server.servlet.context-path=/api`*
- **Lightweight deployment model**  
  *Example: Single JAR contains app + server*
- **Supports servlet API** and standard WAR features

**Theoretical Keywords:**  
**Packaged server**, **No external deployment**, **Configurable**, **Lightweight**, **Servlet container**

---

### **17. Can we change embedded server in Spring Boot?**
- **Yes, easily switchable** by changing dependencies
- **Exclude Tomcat**, include other server starter  
  *Example Maven:*
  ```xml
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
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jetty</artifactId>
  </dependency>
  ```
- **Supported servers**: Tomcat (default), Jetty, Undertow
- **Different characteristics**:
  - **Tomcat** - Most popular, Servlet API compliant
  - **Jetty** - Lightweight, async support
  - **Undertow** - High performance, low memory
- **No code changes required** - Just dependency change

**Theoretical Keywords:**  
**Switchable servers**, **Dependency exclusion**, **Multiple options**, **No code changes**

---

### **18. What is `application.properties`?**
- **Default configuration file** in Spring Boot
- **Key-value pairs** for application configuration  
  *Example:*
  ```
  server.port=9090
  spring.datasource.url=jdbc:mysql://localhost/test
  spring.jpa.show-sql=true
  ```
- **Externalized configuration** - separate from code
- **Profile-specific files** supported  
  *Example: `application-dev.properties`, `application-prod.properties`*
- **Hierarchical property names** with dot notation
- **Located in** `src/main/resources/`
- **Multiple formats**: `.properties`, `.yml`, `.yaml`

**Theoretical Keywords:**  
**Configuration file**, **Key-value pairs**, **Externalized config**, **Profile-specific**, **Hierarchical properties**

---

### **19. Difference between `application.properties` and `application.yml`**

**`application.properties` Example:**
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
```

**`application.yml` Example:**
```yaml
server:
  port: 8080
  
spring:
  datasource:
    url: jdbc:mysql://localhost/test
    username: root
    password: secret
  jpa:
    hibernate:
      ddl-auto: update
```

**Key Differences:**

| **Aspect** | **`application.properties`** | **`application.yml`** |
|------------|-----------------------------|-----------------------|
| **Format** | Flat key-value pairs | Hierarchical YAML format |
| **Readability** | Less readable for complex config | More readable, structured |
| **Duplication** | More duplication in keys | Less duplication with hierarchy |
| **Lists/Arrays** | Comma-separated values | Native YAML list syntax |
| **Comments** | `#` for comments | `#` for comments |
| **Order** | Alphabetical sorting | Maintains defined order |
| **Popularity** | Traditional, widely used | Preferred for microservices |
| **Complexity** | Simple, flat structure | Better for complex configurations |

**When to use:**
- **Use `.properties`** for simple configurations
- **Use `.yml`** for complex, hierarchical configurations  
  *Example: Microservices with multiple nested configurations*
- **Can't use both** for same profile - one overrides other
- **Spring Boot supports both** formats seamlessly

**Theoretical Keywords:**  
**Properties format**, **YAML format**, **Hierarchical vs flat**, **Readability**, **Configuration complexity**