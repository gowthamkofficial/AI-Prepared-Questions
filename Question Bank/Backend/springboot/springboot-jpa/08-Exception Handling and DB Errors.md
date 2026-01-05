Here are the answers focusing on **theory and internal concepts** as an  would expect:

## **Exception Handling and DB Errors **

### **71. How do you handle database exceptions in Spring Boot?**

** Answer:**
Database exceptions in Spring Boot are handled through a **layered exception translation mechanism** that converts vendor-specific SQL exceptions into Spring's unified `DataAccessException` hierarchy, making them consistent and easier to handle.

**Exception Translation Layers:**

1. **Database Layer**: Vendor-specific SQL exceptions (MySQL, PostgreSQL, etc.)
2. **JDBC Layer**: `SQLException` (checked exceptions)
3. **Spring Translation**: `DataAccessException` hierarchy (runtime exceptions)
4. **Application Handling**: Custom exception handlers

**Key Components:**

**1. `PersistenceExceptionTranslator`**:
- Translates persistence exceptions to Spring's exception hierarchy
- Implemented by `HibernateJpaDialect`, `JpaTransactionManager`

**2. `DataAccessException` Hierarchy**:
- **`DataIntegrityViolationException`**: Constraint violations
- **`OptimisticLockingFailureException`**: Version conflicts
- **`DeadlockLoserDataAccessException`**: Database deadlocks
- **`DataRetrievalFailureException`**: Data retrieval issues

**Handling Strategies:**

**1. Global Exception Handler:**
```java
@ControllerAdvice
public class DatabaseExceptionHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation() {
        // Handle constraint violations
    }
}
```

**2. Service Layer Handling:**
```java
@Service
public class UserService {
    
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("User already exists", e);
        }
    }
}
```

**3. Transaction Rollback Configuration:**
```java
@Transactional(rollbackFor = DataAccessException.class)
public void updateData() {
    // Auto-rollback on database exceptions
}
```

**Theoretical Keywords:**  
**Exception translation mechanism**, `DataAccessException` **hierarchy**, **Vendor exception unification**, **Layered exception handling**, **SQL exception conversion**

---

### **72. What is `DataIntegrityViolationException`?**

** Answer:**
`DataIntegrityViolationException` is a **Spring exception that wraps database constraint violation errors**, indicating that an operation would violate database integrity constraints like unique keys, foreign keys, or NOT NULL constraints.

**Common Causes:**

1. **Unique Constraint Violation**: Duplicate value in unique column
2. **Foreign Key Violation**: Referenced parent doesn't exist
3. **NOT NULL Violation**: Inserting NULL in NOT NULL column
4. **Check Constraint Violation**: Value violates CHECK constraint
5. **Data Type Mismatch**: Incorrect data type insertion

**Exception Hierarchy:**
```
DataAccessException (Spring)
    └── DataIntegrityViolationException
        ├── DuplicateKeyException
        └── ConstraintViolationException
```

**Database-specific Wrapping:**
- **MySQL**: `SQLIntegrityConstraintViolationException`
- **PostgreSQL**: `PSQLException` with constraint violation
- **Oracle**: `SQLException` with ORA-00001, ORA-02291, etc.

**Example Scenarios:**

**1. Unique Constraint:**
```java
@Entity
public class User {
    @Column(unique = true)
    private String email;  // Duplicate email causes exception
}
```

**2. Foreign Key Constraint:**
```sql
-- Insert order with non-existent user_id
INSERT INTO orders (user_id) VALUES (999);
-- Foreign key violation if user 999 doesn't exist
```

**Handling Example:**
```java
try {
    userRepository.save(user);
} catch (DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
        throw new DuplicateEmailException("Email already exists");
    }
    throw e;
}
```

**Theoretical Keywords:**  
**Database constraint violation wrapper**, **Integrity rule enforcement exception**, **Constraint failure indication**, **Spring's unified constraint exception**, **Database integrity protection**

---

### **73. What happens when a unique constraint fails?**

** Answer:**
When a unique constraint fails, the **database rejects the operation and throws a constraint violation exception**, which propagates through the JDBC driver to Spring's exception translation mechanism.

**Failure Sequence:**

1. **Database Level**:
   - Database engine detects duplicate value
   - Aborts the current statement/transaction
   - Returns error code/message through JDBC driver

2. **JDBC Driver Level**:
   - Converts database error to `SQLException`
   - Includes SQL state and vendor error codes

3. **Spring Level**:
   - `PersistenceExceptionTranslator` converts `SQLException`
   - Creates `DataIntegrityViolationException` (or `DuplicateKeyException`)

4. **Application Level**:
   - Exception propagates through call stack
   - Transaction marked for rollback
   - Application code can catch and handle

**Database Error Examples:**
- **MySQL**: `ERROR 1062 (23000): Duplicate entry 'value' for key 'key_name'`
- **PostgreSQL**: `ERROR: duplicate key value violates unique constraint "constraint_name"`
- **Oracle**: `ORA-00001: unique constraint (SCHEMA.CONSTRAINT_NAME) violated`

**Transaction Impact:**
- **Statement-level**: Failed statement rolled back
- **Transaction-level**: Entire transaction may be rolled back
- **Depends on**: Database configuration and error handling

**Constraint Types Causing Failure:**
1. **Primary Key Constraint**: Duplicate primary key
2. **Unique Constraint**: Duplicate in unique column
3. **Unique Index**: Functionally same as unique constraint

**Prevention Strategies:**
1. **Application Validation**: Check uniqueness before insert
2. **Optimistic Locking**: Version-based conflict detection
3. **UPSERT Operations**: `INSERT ... ON DUPLICATE KEY UPDATE`

**Theoretical Keywords:**  
**Constraint violation propagation**, **Database error chain**, **Transaction abort sequence**, **Uniqueness enforcement mechanism**, **Error code translation**

---

### **74. How to return a proper error when DB fails?**

** Answer:**
Returning proper errors for database failures involves **structured error responses with appropriate HTTP status codes, meaningful messages, and logging** while maintaining security and user experience.

**Error Response Components:**

1. **HTTP Status Codes**:
   - `400 Bad Request`: Constraint violations, validation errors
   - `409 Conflict`: Duplicate data, concurrent modification
   - `500 Internal Server Error`: Unexpected database errors
   - `503 Service Unavailable`: Database unavailable

2. **Response Body Structure**:
   ```json
   {
     "timestamp": "2024-01-15T10:30:00Z",
     "status": 409,
     "error": "Conflict",
     "message": "Email already registered",
     "path": "/api/users",
     "errorCode": "DUPLICATE_EMAIL",
     "details": ["email: john@example.com already exists"]
   }
   ```

3. **Error Classification**:
   - **User Errors**: Constraint violations, validation (4xx)
   - **System Errors**: Connection failures, timeouts (5xx)
   - **Business Errors**: Domain rule violations (custom)

**Implementation Strategies:**

**1. Global Exception Handler:**
```java
@ControllerAdvice
public class DatabaseExceptionHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, 
            WebRequest request) {
        
        String message = extractUserFriendlyMessage(ex);
        
        ErrorResponse error = ErrorResponse.builder()
            .status(HttpStatus.CONFLICT.value())
            .error("Data Conflict")
            .message(message)
            .errorCode("DATA_CONFLICT")
            .timestamp(LocalDateTime.now())
            .path(getRequestPath(request))
            .build();
        
        log.error("Database constraint violation", ex);
        
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    private String extractUserFriendlyMessage(DataIntegrityViolationException ex) {
        // Parse exception to find which constraint failed
        if (ex.getMessage().contains("unique constraint") || 
            ex.getMessage().contains("Duplicate entry")) {
            return "The provided data conflicts with existing records";
        }
        if (ex.getMessage().contains("foreign key constraint")) {
            return "Referenced data does not exist";
        }
        return "Data validation failed";
    }
}
```

**2. Security Considerations:**
- **Don't expose**: Database schemas, table names, SQL
- **Sanitize messages**: Remove technical details
- **Log details**: Full exception in logs, simple message to user

**3. Internationalization:**
```properties
# messages.properties
error.data.duplicate=The {0} '{1}' already exists
error.data.reference=Referenced {0} not found
```

**Best Practices:**
1. **Consistent Format**: Same error structure across all endpoints
2. **Appropriate Status Codes**: Follow HTTP semantics
3. **User-Friendly Messages**: Technical details in logs only
4. **Error Logging**: Log with context for debugging
5. **Monitoring Integration**: Connect to monitoring systems

**Theoretical Keywords:**  
**Structured error response design**, **HTTP status code semantics**, **User vs system error differentiation**, **Security-aware error messaging**, **Error response consistency**

---

### **75. How to handle constraint violations globally?**

** Answer:**
Handling constraint violations globally involves **centralized exception handling with constraint-specific error mapping** that provides consistent responses across the application.

**Global Handling Strategy:**

**1. Central Exception Handler:**
```java
@ControllerAdvice
public class ConstraintViolationHandler {
    
    private final ConstraintParser constraintParser;
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            DataIntegrityViolationException ex) {
        
        ConstraintViolationInfo violation = constraintParser.parse(ex);
        
        ErrorResponse error = ErrorResponse.builder()
            .status(determineHttpStatus(violation))
            .error(determineErrorType(violation))
            .message(violation.getUserMessage())
            .errorCode(violation.getErrorCode())
            .field(violation.getFieldName())
            .rejectedValue(violation.getRejectedValue())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
```

**2. Constraint Parser:**
```java
@Component
public class ConstraintParser {
    
    public ConstraintViolationInfo parse(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        
        if (message.contains("unique constraint") || message.contains("Duplicate entry")) {
            return parseUniqueConstraint(message);
        }
        if (message.contains("foreign key constraint")) {
            return parseForeignKeyConstraint(message);
        }
        if (message.contains("NOT NULL")) {
            return parseNotNullConstraint(message);
        }
        
        return new ConstraintViolationInfo("DATA_INTEGRITY_VIOLATION", 
                                          "Data validation failed");
    }
    
    private ConstraintViolationInfo parseUniqueConstraint(String message) {
        // Extract field name from message
        Pattern pattern = Pattern.compile("Duplicate entry '(.+?)' for key '(.+?)'");
        Matcher matcher = pattern.matcher(message);
        
        if (matcher.find()) {
            String value = matcher.group(1);
            String key = matcher.group(2);
            String field = extractFieldFromKey(key);
            
            return new ConstraintViolationInfo(
                "DUPLICATE_VALUE",
                String.format("%s '%s' already exists", field, value),
                field,
                value
            );
        }
        
        return new ConstraintViolationInfo("DUPLICATE_VALUE", "Duplicate value");
    }
}
```

**3. Constraint Mapping Configuration:**
```yaml
# application.yml
constraints:
  mappings:
    - pattern: ".*unique constraint.*users_email_key.*"
      errorCode: "DUPLICATE_EMAIL"
      message: "Email already registered"
      field: "email"
      status: 409
    
    - pattern: ".*foreign key constraint.*user_id.*"
      errorCode: "INVALID_USER"
      message: "Referenced user does not exist"
      field: "userId"
      status: 400
    
    - pattern: ".*NOT NULL.*email.*"
      errorCode: "MISSING_EMAIL"
      message: "Email is required"
      field: "email"
      status: 400
```

**4. Database-specific Handlers:**
```java
@Component
public class MySqlConstraintParser implements ConstraintParser {
    // MySQL specific parsing
}

@Component  
public class PostgresConstraintParser implements ConstraintParser {
    // PostgreSQL specific parsing
}

@Component
@Profile("mysql")
public class MySqlConstraintConfig {
    @Bean
    public ConstraintParser constraintParser() {
        return new MySqlConstraintParser();
    }
}
```

**Benefits:**
- **Consistency**: Same handling across all endpoints
- **Maintainability**: Centralized constraint mapping
- **Extensibility**: Easy to add new constraint types
- **Database Agnostic**: Works with different databases

**Theoretical Keywords:**  
**Centralized constraint handling**, **Exception parsing and mapping**, **Database-agnostic error processing**, **Constraint pattern matching**, **Global error response unification**

---

### **76. How do validation errors differ from DB errors?**

** Answer:**
Validation errors and database errors occur at **different layers of the application with different purposes, timing, and characteristics**, though both ensure data integrity.

**Key Differences:**

| **Aspect** | **Validation Errors** | **Database Errors** |
|------------|----------------------|-------------------|
| **Layer** | Application/Business Logic | Database/Storage |
| **Timing** | Before database operations | During database operations |
| **Purpose** | Business rule enforcement | Data integrity enforcement |
| **Control** | Application-controlled | Database-controlled |
| **Cost** | Cheap (in-memory) | Expensive (I/O, transactions) |
| **Type** | `ConstraintViolationException` | `DataIntegrityViolationException` |
| **Handling** | Can be prevented | Must be handled after occurrence |

**Validation Errors (Application Level):**
1. **Bean Validation**: `@NotNull`, `@Size`, `@Pattern`
2. **Business Logic**: Domain rules, business constraints
3. **Custom Validators**: Application-specific rules
4. **Timing**: Before any database operation

**Database Errors (Storage Level):**
1. **Constraint Violations**: Unique, foreign key, check constraints
2. **Data Type Issues**: Type mismatches, overflow
3. **Concurrency Issues**: Deadlocks, optimistic lock failures
4. **Resource Issues**: Connection failures, timeouts

**Example Flow:**
```
User Request → Bean Validation (FAIL: Validation Error) → Return 400
               ↓
User Request → Bean Validation (PASS) → Business Logic (PASS) → 
               Database Insert (FAIL: Unique Constraint) → Return 409
```

**Error Prevention Strategy:**
- **Validation First**: Catch errors early, cheaply
- **Database as Safety Net**: Final integrity guarantee
- **Defense in Depth**: Multiple layers of validation

**Practical Implication:**
Always **validate in application layer first** to avoid expensive database operations, but **never rely solely** on application validation - database constraints are the ultimate truth.

**Theoretical Keywords:**  
**Application vs storage layer errors**, **Prevention vs detection timing**, **Business rule vs data integrity enforcement**, **In-memory vs I/O operation costs**, **Defense-in-depth validation strategy**

---

### **77. How do you log SQL queries?**

** Answer:**
SQL query logging in Spring Boot is configured through **property-based settings that control what SQL information is logged and at what detail level**, primarily for debugging and performance analysis.

**Logging Configuration Levels:**

**1. Basic SQL Logging:**
```yaml
spring:
  jpa:
    show-sql: true  # Logs SQL to console
    properties:
      hibernate:
        format_sql: true  # Pretty-print SQL
```

**2. Detailed Hibernate Logging:**
```yaml
logging:
  level:
    org.hibernate.SQL: DEBUG  # Logs SQL statements
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE  # Logs parameter values
    org.hibernate.stat: DEBUG  # Logs statistics
```

**3. Logback Configuration (Structured Logging):**
```xml
<logger name="org.hibernate.SQL" level="DEBUG">
    <appender-ref ref="SQL_APPENDER"/>
</logger>

<appender name="SQL_APPENDER" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
        <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
</appender>
```

**4. Custom SQL Interceptor:**
```java
@Component
public class SqlLoggerInterceptor implements EmptyInterceptor {
    
    @Override
    public String onPrepareStatement(String sql) {
        log.debug("Executing SQL: {}", sql);
        // Log timing, parameters, etc.
        return sql;
    }
}
```

**5. DataSource Proxy for Advanced Logging:**
```java
@Bean
public DataSource dataSource() {
    return ProxyDataSourceBuilder
        .create(actualDataSource())
        .logQueryBySlf4j(SLF4JLogLevel.DEBUG)
        .multiline()
        .build();
}
```

**What Gets Logged:**
1. **SQL Statements**: INSERT, UPDATE, DELETE, SELECT
2. **Parameters**: Bound parameter values (with TRACE level)
3. **Timing**: Query execution time (with statistics enabled)
4. **Connection Info**: Connection acquisition/release
5. **Transaction Boundaries**: Begin/commit/rollback

**Performance Considerations:**
- **Production**: Minimal logging (warnings/errors only)
- **Development/Staging**: DEBUG level for troubleshooting
- **Never in Production**: Don't log sensitive data (passwords, PII)

**Best Practices:**
1. **Use Profiles**: Different logging per environment
2. **Mask Sensitive Data**: Don't log passwords, tokens
3. **Structured Logging**: Use JSON format for analysis
4. **Sampling**: Log subset of queries in production
5. **Monitoring Integration**: Connect to APM tools

**Theoretical Keywords:**  
**SQL statement logging configuration**, **Hibernate logging levels**, **Performance vs debug logging trade-off**, **Query parameter visibility control**, **Environment-specific logging strategy**

---

### **78. How to debug slow database queries?**

** Answer:**
Debugging slow database queries involves a **systematic approach identifying bottlenecks through monitoring, analysis, and optimization techniques** at multiple levels.

**Debugging Methodology:**

**1. Identification:**
- **Application Logs**: Long-running queries in logs
- **Database Monitoring**: Slow query logs
- **APM Tools**: Application performance monitoring
- **User Reports**: User complaints about slowness

**2. Analysis Tools:**

**Application Level:**
- **Hibernate Statistics**: Enable `spring.jpa.properties.hibernate.generate_statistics: true`
- **Spring Boot Actuator**: `/actuator/metrics/hibernate.*`
- **Logging**: SQL with timing information

**Database Level:**
- **EXPLAIN/EXPLAIN ANALYZE**: Query execution plans
- **Slow Query Log**: Database-specific slow query logging
- **Performance Schema**: MySQL performance schema
- **pg_stat_statements**: PostgreSQL query statistics

**3. Common Causes & Solutions:**

**A. Missing Indexes:**
```sql
-- Identify missing indexes
EXPLAIN SELECT * FROM users WHERE email = 'test@example.com';
-- If type = ALL (full scan), need index

-- Create index
CREATE INDEX idx_users_email ON users(email);
```

**B. N+1 Query Problem:**
```java
// Problem: Separate queries for each parent
List<Department> depts = departmentRepository.findAll();
depts.forEach(d -> d.getEmployees().size());  // N+1

// Solution: Use JOIN FETCH
@Query("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees")
List<Department> findAllWithEmployees();
```

**C. Large Result Sets:**
```java
// Problem: Loading all records
List<User> users = userRepository.findAll();  // Millions of records

// Solution: Use pagination
Page<User> page = userRepository.findAll(PageRequest.of(0, 100));
```

**D. Inefficient Joins:**
```sql
-- Check join order and types
EXPLAIN 
SELECT * FROM orders o 
JOIN users u ON o.user_id = u.id 
JOIN products p ON o.product_id = p.id;
```

**4. Optimization Techniques:**

**Query Optimization:**
- **Use Indexes**: Proper indexing strategy
- **Limit Results**: `LIMIT` clause, pagination
- **Select Only Needed Columns**: Avoid `SELECT *`
- **Batch Operations**: Use batching for multiple operations

**Application Optimization:**
- **Caching**: Second-level cache, query cache
- **Connection Pooling**: Proper pool configuration
- **Batch Processing**: Process in chunks
- **Async Processing**: Non-blocking operations

**Database Optimization:**
- **Table Partitioning**: For large tables
- **Materialized Views**: For complex aggregations
- **Query Plan Hints**: Guide optimizer (use cautiously)
- **Regular Maintenance**: Vacuum, analyze, reindex

**5. Monitoring & Alerting:**
- **Set Baselines**: Normal performance metrics
- **Alert Thresholds**: Alert when queries exceed thresholds
- **Regular Reviews**: Periodic query performance reviews
- **Capacity Planning**: Monitor growth trends

**Theoretical Keywords:**  
**Query performance analysis methodology**, **Bottleneck identification techniques**, **Execution plan analysis**, **Index optimization strategy**, **Multi-layer performance debugging**

---

### **79. What happens if DB connection is lost?**

** Answer:**
When a database connection is lost, Spring Boot's **connection pool and transaction management systems detect the failure and respond according to configured policies**, with different behaviors during idle vs active operations.

**Failure Detection Mechanisms:**

**1. Connection Pool Detection:**
- **Validation Queries**: Periodic `SELECT 1` to check connection health
- **Timeout Settings**: Connection, socket, query timeouts
- **Keep-alive**: TCP keep-alive packets

**2. Failure Scenarios:**

**A. During Idle Connection:**
```java
// Connection in pool goes stale
// Next borrow attempt fails
// Pool creates new connection (if possible)
// Application continues with minimal disruption
```

**B. During Active Transaction:**
```java
@Transactional
public void processOrder() {
    // Connection lost mid-transaction
    // JDBC throws SQLException
    // Spring marks transaction for rollback
    // Exception propagates to caller
    // Connection returned to pool (marked bad)
}
```

**C. During Connection Acquisition:**
```java
// Application needs connection, pool is empty
// Pool tries to create new connection
// Database unavailable - connection fails
// Pool throws CannotGetJdbcConnectionException
// Application must handle or retry
```

**3. Spring Boot Default Behavior:**

**With HikariCP (Default Pool):**
```yaml
spring:
  datasource:
    hikari:
      connection-test-query: SELECT 1  # MySQL
      # or
      connection-init-sql: SELECT 1  # Others
      validation-timeout: 5000
      leak-detection-threshold: 60000
```

**4. Exception Hierarchy:**
```
DataAccessException (Spring)
    ├── DataAccessResourceFailureException
    │   └── CannotGetJdbcConnectionException
    ├── DataRetrievalFailureException  
    └── QueryTimeoutException
```

**5. Application Impact:**

**Immediate Effects:**
- **Active Operations Fail**: Current database operations fail
- **Exceptions Thrown**: `CannotGetJdbcConnectionException`
- **Transaction Rollback**: Active transactions rolled back
- **Pool Cleanup**: Bad connections removed from pool

**Recovery Behavior:**
- **Automatic Reconnection**: Pool creates new connections when needed
- **Retry Logic**: With proper configuration (Spring Retry)
- **Circuit Breaker**: With resilience patterns (Resilience4j)

**6. Best Practices for Handling:**

**Configuration:**
```yaml
spring:
  datasource:
    hikari:
      connection-timeout: 30000  # Wait 30s for connection
      max-lifetime: 1800000      # 30 minutes max connection age
      idle-timeout: 600000       # 10 minutes idle timeout
```

**Application Logic:**
```java
@Service
public class OrderService {
    
    @Retryable(value = CannotGetJdbcConnectionException.class, 
               maxAttempts = 3, 
               backoff = @Backoff(delay = 1000))
    @Transactional
    public Order processWithRetry(Order order) {
        return orderRepository.save(order);
    }
    
    @Recover
    public Order processFallback(CannotGetJdbcConnectionException e, Order order) {
        // Fallback logic (queue for later processing)
        return queueOrderForLater(order);
    }
}
```

**Theoretical Keywords:**  
**Connection failure detection**, **Pool recovery mechanisms**, **Transaction failure handling**, **Automatic reconnection strategies**, **Connection health monitoring**

---

### **80. How does Spring Boot retry DB connection?**

** Answer:**
Spring Boot provides **multiple strategies for database connection retry** through configuration properties, connection pool settings, and integration with retry libraries, operating at different layers of the connection stack.

**Retry Mechanisms at Different Layers:**

**1. Connection Pool Level (HikariCP Default):**
```yaml
spring:
  datasource:
    hikari:
      initialization-fail-timeout: 1          # Fail fast on startup
      connection-timeout: 30000               # Wait for connection
      # Note: HikariCP doesn't retry failed connections by default
      # It throws exception immediately on failure
```

**2. Spring Boot Auto-retry (for Startup):**
```yaml
spring:
  datasource:
    # Spring Boot 2.3+ automatically retries startup connection
    hikari:
      connection-init-sql: SELECT 1           # Validation query
  sql:
    init:
      mode: always                            # Initialize schema
```

**3. Spring Retry Library (Application Level):**
```xml
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
</dependency>
```

**Implementation Strategies:**

**A. Method-level Retry:**
```java
@Service
public class DatabaseService {
    
    @Retryable(
        value = {CannotGetJdbcConnectionException.class, DataAccessException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    @Recover
    public User recoverSaveUser(Exception e, User user) {
        // Fallback: queue for later processing
        log.error("Failed to save user after retries, queuing: {}", user.getId(), e);
        return queueService.queueForLater(user);
    }
}
```

**B. Template-based Retry:**
```java
@Configuration
@EnableRetry
public class RetryConfig {
    
    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
            .maxAttempts(3)
            .exponentialBackoff(1000, 2, 10000)
            .retryOn(CannotGetJdbcConnectionException.class)
            .traversingCauses()
            .build();
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource, RetryTemplate retryTemplate) {
        return new JdbcTemplate(dataSource) {
            @Override
            public <T> T execute(ConnectionCallback<T> action) {
                return retryTemplate.execute(context -> 
                    super.execute(action));
            }
        };
    }
}
```

**C. Circuit Breaker Pattern (Resilience4j):**
```java
@CircuitBreaker(name = "databaseService", fallbackMethod = "fallback")
@RateLimiter(name = "databaseService")
@Bulkhead(name = "databaseService")
@Retry(name = "databaseService")
public User saveUserWithResilience(User user) {
    return userRepository.save(user);
}
```

**4. Database-specific Connection Testing:**

**MySQL:**
```yaml
spring:
  datasource:
    hikari:
      connection-test-query: SELECT 1
      keepalive-time: 30000
```

**PostgreSQL:**
```yaml
spring:
  datasource:
    hikari:
      connection-init-sql: SELECT 1
```

**5. Health Check Integration:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health
  health:
    db:
      enabled: true
    diskspace:
      enabled: true
```

**6. Retry Logic Flow:**
```
1. Database operation fails
2. Exception caught by retry interceptor
3. Check retry policy (max attempts, exceptions)
4. Wait (backoff strategy)
5. Retry operation
6. Success or fallback after max attempts
```

**Best Practices:**
1. **Different Strategies**: Different retry for different failures
2. **Exponential Backoff**: Prevent overwhelming database
3. **Circuit Breaker**: Prevent cascading failures
4. **Monitoring**: Track retry rates and failures
5. **Fallback Strategies**: Graceful degradation

**Theoretical Keywords:**  
**Multi-layer retry strategies**, **Exponential backoff implementation**, **Circuit breaker integration**, **Connection pool recovery**, **Graceful degradation patterns**