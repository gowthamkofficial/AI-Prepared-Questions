Here are the answers with **examples and bolded keywords**:

## **Validation **

### **68. What is `@Valid`?**
- **Annotation from JSR-380** (Bean Validation 2.0)
- **Triggers validation** on method parameters or object fields
- **Used on controller methods** to validate request bodies
- **Recursively validates** nested objects
- **Throws `MethodArgumentNotValidException`** if validation fails

**Usage Examples:**

**Controller-level Validation:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody UserCreateRequest request) {
        // If validation fails, Spring throws MethodArgumentNotValidException
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        // Validates update request
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
}
```

**DTO with Validation Annotations:**
```java
public class UserCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be 2-50 characters")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private Integer age;
    
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
             message = "Password must have 8+ chars with digit, lowercase, uppercase")
    private String password;
    
    // Nested object validation
    @Valid
    private Address address;
    
    // Collection validation
    @NotEmpty(message = "At least one role required")
    private List<@NotBlank String> roles;
    
    // getters, setters
}

public class Address {
    @NotBlank
    private String street;
    
    @NotBlank
    private String city;
    
    @NotBlank
    @Size(min = 5, max = 10)
    private String zipCode;
}
```

**Path Variable Validation:**
```java
@GetMapping("/{id}")
public ResponseEntity<User> getUser(
        @PathVariable @Min(1) Long id) {  // Validates path variable
    User user = userService.findById(id);
    return ResponseEntity.ok(user);
}
```

**Query Parameter Validation:**
```java
@GetMapping("/search")
public ResponseEntity<List<User>> searchUsers(
        @RequestParam @NotBlank String name,
        @RequestParam @Email String email) {
    // Validates query parameters
    List<User> users = userService.search(name, email);
    return ResponseEntity.ok(users);
}
```

**Theoretical Keywords:**  
**JSR-380**, **Bean validation**, **Method parameter validation**, **Recursive validation**, `MethodArgumentNotValidException`

---

### **69. What is `@Validated`?**
- **Spring's annotation** for method-level validation
- **Enables validation** on service layer methods
- **Supports validation groups** (different rules for different scenarios)
- **Throws `ConstraintViolationException`** if validation fails
- **Can be used at class level** to validate all methods

**Usage Examples:**

**Service Layer Validation:**
```java
@Service
@Validated  // Enables method parameter validation
public class UserService {
    
    public User createUser(@Valid UserCreateRequest request) {
        // Validates request before processing
        User user = convertToEntity(request);
        return userRepository.save(user);
    }
    
    public User findById(@Min(1) Long id) {
        // Validates method parameter
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    public List<User> findByAgeRange(
            @Min(18) Integer minAge,
            @Max(100) Integer maxAge) {
        // Validates multiple parameters
        return userRepository.findByAgeBetween(minAge, maxAge);
    }
}
```

**Validation Groups:**
```java
// Define validation groups
public interface CreateValidation {}
public interface UpdateValidation {}

public class UserRequest {
    @NotNull(groups = UpdateValidation.class)  // Only for updates
    private Long id;
    
    @NotBlank(groups = CreateValidation.class)  // Only for creation
    @Size(min = 2, max = 50, groups = {CreateValidation.class, UpdateValidation.class})
    private String name;
    
    @Email(groups = {CreateValidation.class, UpdateValidation.class})
    private String email;
}

// Controller using groups
@RestController
public class UserController {
    
    @PostMapping
    public ResponseEntity<User> createUser(
            @Validated(CreateValidation.class) @RequestBody UserRequest request) {
        // Only validates CreateValidation group
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Validated(UpdateValidation.class) @RequestBody UserRequest request) {
        // Only validates UpdateValidation group
        request.setId(id);
        User user = userService.updateUser(request);
        return ResponseEntity.ok(user);
    }
}
```

**Class-level `@Validated`:**
```java
@RestController
@Validated  // Validates all method parameters in this controller
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable @Min(1) Long id) {
        // Auto-validated by @Validated at class level
        return userService.findById(id);
    }
    
    @GetMapping("/age/{age}")
    public List<User> getUsersByAge(@PathVariable @Min(18) @Max(100) Integer age) {
        // Auto-validated
        return userService.findByAge(age);
    }
}
```

**Custom Validator with `@Validated`:**
```java
@Component
@Validated
public class PaymentService {
    
    public void processPayment(
            @Valid PaymentRequest request,
            @CreditCardNumber String creditCardNumber) {  // Custom validator
        // Both parameters validated
        paymentGateway.charge(creditCardNumber, request.getAmount());
    }
}
```

**Difference from `@Valid`:**
- **`@Valid`** - JSR standard, mainly for controller parameters
- **`@Validated`** - Spring extension, works on any Spring bean methods
- **`@Validated`** supports validation groups
- **`@Validated`** throws `ConstraintViolationException`

**Theoretical Keywords:**  
**Method validation**, **Validation groups**, **Service layer validation**, `ConstraintViolationException`

---

### **70. Difference between `@NotNull`, `@NotEmpty`, and `@NotBlank`**

| **Annotation** | **Checks For** | **Applicable To** | **Example** |
|----------------|----------------|-------------------|-------------|
| **`@NotNull`** | Null reference | Any object | `null` → ❌, `""` → ✅, `" "` → ✅ |
| **`@NotEmpty`** | Null or empty | String, Collection, Map, Array | `null` → ❌, `""` → ❌, `" "` → ✅ |
| **`@NotBlank`** | Null, empty, or whitespace | String only | `null` → ❌, `""` → ❌, `" "` → ❌ |

**Code Examples:**

**`@NotNull` (Null check only):**
```java
public class UserRequest {
    @NotNull(message = "ID cannot be null")
    private Long id;  // null → ❌, 123 → ✅
    
    @NotNull
    private String name;  // null → ❌, "" → ✅, "John" → ✅
    
    @NotNull
    private List<String> roles;  // null → ❌, [] → ✅
}
```

**`@NotEmpty` (Null or empty check):**
```java
public class UserRequest {
    @NotEmpty(message = "Name cannot be empty")
    private String name;  // null → ❌, "" → ❌, " " → ✅, "John" → ✅
    
    @NotEmpty
    private List<String> roles;  // null → ❌, [] → ❌, ["ADMIN"] → ✅
    
    @NotEmpty
    private Map<String, String> metadata;  // null → ❌, {} → ❌
    
    @NotEmpty
    private String[] tags;  // null → ❌, [] → ❌
}
```

**`@NotBlank` (Null, empty, or whitespace check):**
```java
public class UserRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;  // null → ❌, "" → ❌, " " → ❌, "John" → ✅
    
    @NotBlank
    private String email;  // Only for String fields
    
    // @NotBlank cannot be used on:
    // private List<String> list;  // ❌ Compilation error
    // private Integer number;     // ❌ Compilation error
}
```

**Combined Usage:**
```java
public class RegistrationRequest {
    // For required database ID (update scenarios)
    @NotNull(groups = UpdateValidation.class)
    private Long id;
    
    // For required non-blank user input
    @NotBlank(message = "Username is required")
    private String username;
    
    // For collections that must have elements
    @NotEmpty(message = "At least one role required")
    private List<@NotBlank String> roles;
    
    // For optional but non-null collection
    @NotNull
    private List<String> preferences = new ArrayList<>();  // Can be empty but not null
}
```

**Validation Examples:**
```java
// Test cases
RegistrationRequest request = new RegistrationRequest();

// @NotNull test
request.setId(null);        // ❌ Validation fails
request.setId(123L);        // ✅ Validation passes

// @NotBlank test  
request.setUsername(null);  // ❌ Validation fails
request.setUsername("");    // ❌ Validation fails  
request.setUsername("  ");  // ❌ Validation fails
request.setUsername("John");// ✅ Validation passes

// @NotEmpty test
request.setRoles(null);     // ❌ Validation fails
request.setRoles(new ArrayList<>()); // ❌ Validation fails
request.setRoles(List.of(""));       // ✅ Validation passes (checks collection, not elements)
request.setRoles(List.of("ADMIN"));  // ✅ Validation passes
```

**When to use which:**
- **Use `@NotNull`** when field shouldn't be null, but empty/blank is OK
- **Use `@NotEmpty`** when collection/map/array must have elements
- **Use `@NotBlank`** when String must have non-whitespace content

**Theoretical Keywords:**  
**Null validation**, **Empty validation**, **Blank validation**, **String validation**, **Collection validation**

---

### **71. How do you handle validation globally?**

**1. Global Exception Handler for Validation:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        // Extract field errors
        List<FieldErrorResponse> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new FieldErrorResponse(
                error.getField(),
                error.getDefaultMessage(),
                error.getRejectedValue()
            ))
            .collect(Collectors.toList());
        
        // Extract global errors
        List<String> globalErrors = ex.getBindingResult()
            .getGlobalErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Invalid request parameters")
            .errorCode("VALIDATION_ERROR")
            .fieldErrors(fieldErrors)
            .globalErrors(globalErrors)
            .build();
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        
        List<String> errors = ex.getConstraintViolations()
            .stream()
            .map(violation -> 
                violation.getPropertyPath() + ": " + violation.getMessage())
            .collect(Collectors.toList());
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Constraint Violation")
            .message("Method parameter validation failed")
            .errorCode("CONSTRAINT_VIOLATION")
            .details(errors)
            .build();
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

// Field error DTO
public class FieldErrorResponse {
    private String field;
    private String message;
    private Object rejectedValue;
    
    // constructor, getters, setters
}

// Error response DTO
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String errorCode;
    private List<FieldErrorResponse> fieldErrors;
    private List<String> globalErrors;
    private List<String> details;
    
    // builder, getters, setters
}
```

**2. Custom Validator with Global Configuration:**
```java
// Custom validator
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        
        // Custom validation logic
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*].*");
        
        if (!hasUpper) {
            addConstraintViolation(context, "Password must contain uppercase letter");
            return false;
        }
        if (!hasLower) {
            addConstraintViolation(context, "Password must contain lowercase letter");
            return false;
        }
        // ... more checks
        
        return password.length() >= 8;
    }
    
    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}
```

**3. Global Message Source for Internationalization:**
```java
@Configuration
public class ValidationConfig {
    
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }
}

// messages.properties
NotBlank.userCreateRequest.name=Name is required
Size.userCreateRequest.name=Name must be between {min} and {max} characters
Email.userCreateRequest.email=Please provide a valid email address
```

**4. Global Validation Rules in Properties:**
```yaml
# application.yml
app:
  validation:
    password:
      min-length: 8
      require-upper: true
      require-lower: true
      require-digit: true
      require-special: true
```

**5. Request/Response Logging with Validation:**
```java
@Component
public class ValidationLoggingAspect {
    
    @Before("@within(org.springframework.web.bind.annotation.RestController) && args(.., @org.springframework.validation.annotation.Validated *)")
    public void logValidation(Object request) {
        log.info("Validating request: {}", request);
    }
    
    @AfterThrowing(pointcut = "@within(org.springframework.web.bind.annotation.RestController)", 
                   throwing = "ex")
    public void logValidationError(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getBindingResult().getAllErrors());
    }
}
```

**6. Global Validation Response Format:**
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errorCode": "VALIDATION_ERROR",
  "fieldErrors": [
    {
      "field": "email",
      "message": "Invalid email format",
      "rejectedValue": "invalid-email"
    },
    {
      "field": "age",
      "message": "Age must be at least 18",
      "rejectedValue": 16
    }
  ],
  "globalErrors": [
    "Password and confirm password must match"
  ]
}
```

**7. Enable Global Validation in Spring Boot:**
```java
@SpringBootApplication
public class Application implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ValidationInterceptor());
    }
}

@Component
public class ValidationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                            HttpServletResponse response, 
                            Object handler) {
        // Global pre-validation logic
        return true;
    }
}
```

**8. Testing Global Validation:**
```java
@SpringBootTest
@AutoConfigureMockMvc
public class GlobalValidationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testValidationErrorResponse() throws Exception {
        String invalidRequest = """
            {
              "name": "",
              "email": "invalid-email",
              "age": 15
            }
            """;
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors[0].field").exists());
    }
}
```

**Best Practices:**
1. **Centralize validation handling** in `@ControllerAdvice`
2. **Use consistent error response format**
3. **Include field-level error details**
4. **Internationalize validation messages**
5. **Log validation failures** for debugging
6. **Test validation scenarios** thoroughly

**Theoretical Keywords:**  
**Global exception handler**, **Consistent error format**, **Field error details**, **Internationalization**, **Centralized validation**