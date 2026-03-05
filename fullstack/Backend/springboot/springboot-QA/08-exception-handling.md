Here are the answers with **examples and bolded keywords**:

## **Exception Handling **

### **62. How do you handle exceptions in Spring Boot?**
- **Centralized exception handling** using `@ControllerAdvice`
- **Method-level handling** using `@ExceptionHandler`
- **ResponseEntity** for custom HTTP responses
- **Spring's built-in exceptions** automatically handled
- **Custom exception classes** for business errors

**Approaches:**

**1. Global Exception Handler (`@ControllerAdvice`):**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            "NOT_FOUND", 
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

**2. Controller-specific Handler (`@ExceptionHandler`):**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(ex.getMessage());
    }
}
```

**3. Response Status Exceptions:**
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

@Service
public class UserService {
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "User not found with id: " + id));
    }
}
```

**4. Custom Error Response Structure:**
```java
public class ErrorResponse {
    private String errorCode;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private List<String> details;
    
    // constructor, getters, setters
}
```

**Theoretical Keywords:**  
**Centralized handling**, `@ControllerAdvice`, `@ExceptionHandler`, **ResponseEntity**, **Custom exceptions**

---

### **63. What is `@ExceptionHandler`?**
- **Method-level annotation** to handle exceptions
- **Declares exception handling method** within controller
- **Scope limited** to controller where declared
- **Can handle multiple exceptions** with array
- **Returns custom response** for specific exceptions

**Usage Examples:**

**Basic Usage:**
```java
@RestController
public class UserController {
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
    }
    
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInput(InvalidInputException ex) {
        return new ErrorResponse("INVALID_INPUT", ex.getMessage());
    }
}
```

**Multiple Exceptions:**
```java
@ExceptionHandler({UserNotFoundException.class, 
                   ProfileNotFoundException.class})
public ResponseEntity<ErrorResponse> handleNotFoundExceptions(
        RuntimeException ex) {
    ErrorResponse error = new ErrorResponse(
        "NOT_FOUND",
        ex.getMessage(),
        HttpStatus.NOT_FOUND.value()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
```

**With Method Parameters:**
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGeneralException(
        Exception ex, 
        WebRequest request) {
    
    // Access exception, request, locale, etc.
    String path = ((ServletWebRequest) request).getRequest().getRequestURI();
    
    ErrorResponse error = new ErrorResponse(
        "INTERNAL_ERROR",
        ex.getMessage(),
        path,
        HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
}
```

**Order of Precedence:**
```java
@ExceptionHandler(SpecificException.class)  // Handled first
public ResponseEntity<?> handleSpecific() { ... }

@ExceptionHandler(GeneralException.class)   // Handled second  
public ResponseEntity<?> handleGeneral() { ... }

@ExceptionHandler(Exception.class)          // Catch-all last
public ResponseEntity<?> handleAll() { ... }
```

**Theoretical Keywords:**  
**Method-level handling**, **Exception-specific methods**, **Controller scope**, **Multiple exceptions**

---

### **64. What is `@ControllerAdvice`?**
- **Global exception handler** for multiple controllers
- **Centralizes exception handling** across application
- **Can handle exceptions** from all controllers
- **Combines with `@ExceptionHandler`** for global handling
- **Specialized variants**: `@RestControllerAdvice`

**Basic Implementation:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            "NOT_FOUND",
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());
        
        ErrorResponse error = new ErrorResponse(
            "VALIDATION_FAILED",
            "Validation failed",
            errors,
            HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
```

**`@RestControllerAdvice` (for REST APIs):**
```java
@RestControllerAdvice  // = @ControllerAdvice + @ResponseBody
public class RestExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            "INTERNAL_ERROR",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

**Scoped `@ControllerAdvice`:**
```java
// Handle exceptions only from specific package
@ControllerAdvice(basePackages = "com.example.api")
public class ApiExceptionHandler { ... }

// Handle exceptions only from specific annotations
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler { ... }

// Handle exceptions only from specific classes
@ControllerAdvice(assignableTypes = {UserController.class, 
                                      ProductController.class})
public class SpecificControllerHandler { ... }
```

**With `@ResponseBody` and `@ResponseStatus`:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody  // Return as response body (not view name)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // Set HTTP status
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse("NOT_FOUND", ex.getMessage());
    }
}
```

**Theoretical Keywords:**  
**Global handler**, **Centralized exception handling**, **Multiple controllers**, `@RestControllerAdvice`

---

### **65. Difference between `@ExceptionHandler` and `@ControllerAdvice`**

| **Aspect** | **`@ExceptionHandler`** | **`@ControllerAdvice`** |
|------------|-------------------------|-------------------------|
| **Scope** | Controller-level | Application-level (global) |
| **Location** | Inside controller class | Separate class |
| **Reusability** | Limited to one controller | Reusable across controllers |
| **Use Case** | Controller-specific exceptions | Common exceptions across app |
| **Combination** | Used inside controllers | Contains `@ExceptionHandler` methods |

**`@ExceptionHandler` Only (Controller-specific):**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // Only handles exceptions from UserController
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound() {
        // User-specific handling
    }
}

@RestController  
@RequestMapping("/api/products")
public class ProductController {
    // UserNotFoundException NOT handled here
    // Would need duplicate handler
}
```

**`@ControllerAdvice` (Global):**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Handles exceptions from ALL controllers
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound() {
        // Global handling for all resources
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidation() {
        // Global validation handling
    }
}
```

**Combined Approach (Recommended):**
```java
// Global common exceptions
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({ResourceNotFoundException.class,
                       ValidationException.class})
    public ResponseEntity<ErrorResponse> handleCommonExceptions(
            RuntimeException ex) {
        // Handle common exceptions globally
    }
}

// Controller-specific exceptions  
@RestController
public class PaymentController {
    
    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentFailed(
            PaymentFailedException ex) {
        // Payment-specific handling
        // Not needed in other controllers
    }
}
```

**Best Practice:**  
Use **`@ControllerAdvice` for common exceptions** and **`@ExceptionHandler` in controllers for specific exceptions**.

**Theoretical Keywords:**  
**Local vs Global scope**, **Controller-specific vs Application-wide**, **Reusability**

---

### **66. How do you return a common error response?**
**1. Standard Error Response Structure:**
```java
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String errorCode;
    private List<String> details;
    
    public ErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
    
    // Builder pattern for easier creation
    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }
    
    // getters
}
```

**2. Global Exception Handler with Consistent Response:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex, WebRequest request) {
        
        String path = ((ServletWebRequest) request)
                     .getRequest().getRequestURI();
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message("An unexpected error occurred")
            .path(path)
            .errorCode("INTERNAL_ERROR")
            .build();
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        
        String path = ((ServletWebRequest) request)
                     .getRequest().getRequestURI();
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Not Found")
            .message(ex.getMessage())
            .path(path)
            .errorCode("RESOURCE_NOT_FOUND")
            .build();
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

**3. Custom Exception with Error Code:**
```java
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    
    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(Long userId) {
        super("USER_NOT_FOUND", 
              "User not found with id: " + userId);
    }
}

// In GlobalExceptionHandler
@ExceptionHandler(BaseException.class)
public ResponseEntity<ErrorResponse> handleBaseException(
        BaseException ex, WebRequest request) {
    
    String path = getPath(request);
    
    ErrorResponse error = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error("Business Error")
        .message(ex.getMessage())
        .path(path)
        .errorCode(ex.getErrorCode())  // From custom exception
        .build();
    
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
}
```

**4. Spring's Default Error Handling:**
```yaml
# Customize in application.yml
server:
  error:
    include-message: always
    include-binding-errors: always
    include-stacktrace: never
    include-exception: false
```

**5. Consistent Response for Success and Error:**
```java
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;
    private LocalDateTime timestamp;
    
    // Success response
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.data = data;
        response.timestamp = LocalDateTime.now();
        return response;
    }
    
    // Error response  
    public static ApiResponse<?> error(String errorCode, String message) {
        ApiResponse<?> response = new ApiResponse<>();
        response.success = false;
        response.error = new ErrorResponse(errorCode, message);
        response.timestamp = LocalDateTime.now();
        return response;
    }
}

// In controller
@GetMapping("/{id}")
public ApiResponse<User> getUser(@PathVariable Long id) {
    try {
        User user = userService.findById(id);
        return ApiResponse.success(user);
    } catch (UserNotFoundException ex) {
        return ApiResponse.error("USER_NOT_FOUND", ex.getMessage());
    }
}
```

**Theoretical Keywords:**  
**Standard error format**, **Consistent response structure**, **Error codes**, **Timestamp**, **Path information**

---

### **67. How do you handle validation errors?**
**1. Bean Validation with `@Valid`:**
```java
public class UserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private Integer age;
    
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;
    
    @Future(message = "Birth date must be in the future")
    private LocalDate birthDate;
    
    // getters, setters
}

@RestController
public class UserController {
    
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest request) {
        // If validation fails, MethodArgumentNotValidException is thrown
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}
```

**2. Global Validation Exception Handler:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        // Extract validation errors
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        // Or get as list
        List<String> errorMessages = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Invalid input parameters")
            .errorCode("VALIDATION_ERROR")
            .details(errorMessages)
            .build();
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
```

**3. Custom Validator:**
```java
// Custom annotation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// Validator implementation
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) return true; // Use @NotNull for null checks
        
        // Custom validation logic
        return phone.startsWith("+") && phone.length() >= 10;
    }
}

// Usage in DTO
public class ContactRequest {
    @ValidPhoneNumber
    private String phone;
}
```

**4. Group Validation:**
```java
// Define validation groups
public interface CreateValidation {}
public interface UpdateValidation {}

public class UserRequest {
    @NotNull(groups = {CreateValidation.class, UpdateValidation.class})
    private Long id;
    
    @NotBlank(groups = CreateValidation.class)
    private String name;
    
    @Email(groups = {CreateValidation.class, UpdateValidation.class})
    private String email;
}

// In controller
@PostMapping("/users")
public ResponseEntity<?> createUser(
        @Validated(CreateValidation.class) @RequestBody UserRequest request) {
    // Only validates CreateValidation group
}

@PutMapping("/users/{id}")
public ResponseEntity<?> updateUser(
        @Validated(UpdateValidation.class) @RequestBody UserRequest request) {
    // Only validates UpdateValidation group
}
```

**5. Cross-field Validation:**
```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegistrationRequest> {
    
    @Override
    public boolean isValid(UserRegistrationRequest request, 
                          ConstraintValidatorContext context) {
        return request.getPassword().equals(request.getConfirmPassword());
    }
}

// Apply to class
@PasswordMatch
public class UserRegistrationRequest {
    private String password;
    private String confirmPassword;
}
```

**6. Manual Validation in Service Layer:**
```java
@Service
@Validated  // Enables method parameter validation
public class UserService {
    
    public User createUser(@Valid UserRequest request) {
        // Method parameter validation
    }
    
    public User findById(@Min(1) Long id) {
        // id must be >= 1
    }
}
```

**7. Response Format for Validation Errors:**
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errorCode": "VALIDATION_ERROR",
  "details": [
    "name: Name is required",
    "email: Invalid email format",
    "age: Age must be at least 18"
  ],
  "fieldErrors": [
    {
      "field": "name",
      "message": "Name is required"
    },
    {
      "field": "email", 
      "message": "Invalid email format"
    }
  ]
}
```

**8. Spring Boot Validation Configuration:**
```yaml
# application.yml
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  web:
    resources:
      add-mappings: false
```

**Theoretical Keywords:**  
**Bean validation**, `@Valid` **annotation**, `@Constraint`, **Custom validators**, **Validation groups**, **Method validation**