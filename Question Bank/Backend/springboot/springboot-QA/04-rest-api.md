Here are the answers with **examples and bolded keywords**:

## **REST API **

### **26. What is REST?**
- **REpresentational State Transfer** - architectural style for web services
- **Resource-based** architecture  
  *Example: Users, Orders, Products as resources*
- **Stateless** communication - each request contains all information needed
- **Client-server** architecture - separation of concerns
- **Uniform interface** with standard HTTP methods
- **Cacheable** responses to improve performance
- **Layered system** - intermediaries can be inserted
- **Code on demand** (optional) - servers can send executable code

**Key Principles:**
- **Resources** identified by URIs  
  *Example: `/api/users/123`*
- **Representations** (JSON, XML) of resource state
- **Stateless** - no server-side session
- **Hypermedia** as engine of application state (HATEOAS)

**Theoretical Keywords:**  
**Architectural style**, **Resource-based**, **Stateless**, **HTTP methods**, **Uniform interface**, **Cacheable**

---

### **27. What is RESTful web service?**
- **Web service implementing REST principles**
- **Uses HTTP as application protocol** (not just transport)
- **Standard HTTP methods** for CRUD operations:
  - `GET` - Retrieve resource
  - `POST` - Create resource
  - `PUT` - Update/replace resource
  - `DELETE` - Delete resource
- **Returns structured data** (JSON/XML)  
  *Example: JSON response for user data*
- **Resource-oriented URLs**  
  *Example: `/api/users` not `/api/getAllUsers`*
- **Stateless** - each request independent

**Example RESTful Endpoints:**
```
GET    /api/users           → Get all users
POST   /api/users           → Create user
GET    /api/users/{id}      → Get user by ID
PUT    /api/users/{id}      → Update user
DELETE /api/users/{id}      → Delete user
```

**Theoretical Keywords:**  
**HTTP-based**, **CRUD operations**, **Resource URIs**, **JSON/XML responses**, **Stateless**

---

### **28. Difference between `@Controller` and `@RestController`**

| **Aspect** | **`@Controller`** | **`@RestController`** |
|------------|-------------------|----------------------|
| **Purpose** | Traditional Spring MVC controller | REST API controller |
| **Response** | Returns view name | Returns data directly |
| **Annotations** | Needs `@ResponseBody` on methods | Auto `@ResponseBody` on all methods |
| **View Resolver** | Uses ViewResolver for rendering | No view resolution |
| **Content Type** | HTML views | JSON/XML data |

**Code Examples:**

**`@Controller` (Spring MVC):**
```java
@Controller  // Returns view names
public class UserController {
    
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users-page";  // Returns view name
    }
    
    @GetMapping("/api/users")
    @ResponseBody  // Needed to return JSON
    public List<User> getUsersJson() {
        return userService.findAll();
    }
}
```

**`@RestController` (REST API):**
```java
@RestController  // = @Controller + @ResponseBody on all methods
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();  // Auto-converted to JSON
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);  // Auto-converted to JSON
    }
}
```

**Theoretical Keywords:**  
**View-based vs Data-based**, **@ResponseBody**, **JSON response**, **View resolution**

---

### **29. Difference between `@RequestParam` and `@PathVariable`**

| **Aspect** | **`@RequestParam`** | **`@PathVariable`** |
|------------|---------------------|---------------------|
| **Usage** | Query parameters | URL path segments |
| **URL Format** | `?key=value` | `/path/{value}` |
| **Optional** | Can be optional | Usually required |
| **Multiple** | Multiple params possible | Multiple path variables |
| **Type** | Usually for filtering | Identifying resources |

**Code Examples:**

**`@RequestParam` (Query Parameters):**
```java
@GetMapping("/api/users")
// URL: /api/users?page=1&size=20&sort=name,asc
public List<User> getUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String sort) {
    
    // Used for filtering, pagination, sorting
    return userService.findWithPagination(page, size, sort);
}

@GetMapping("/search")
// URL: /api/users/search?name=john&email=example.com
public List<User> searchUsers(
    @RequestParam String name,
    @RequestParam String email) {
    return userService.search(name, email);
}
```

**`@PathVariable` (URL Path Variables):**
```java
@GetMapping("/api/users/{userId}")
// URL: /api/users/123
public User getUserById(@PathVariable Long userId) {
    return userService.findById(userId);  // userId = 123
}

@GetMapping("/api/users/{userId}/orders/{orderId}")
// URL: /api/users/123/orders/456
public Order getUserOrder(
    @PathVariable Long userId,
    @PathVariable Long orderId) {
    
    return orderService.findUserOrder(userId, orderId);
}
```

**When to use:**
- **Use `@PathVariable`** for resource identification  
  *Example: `/users/{id}`, `/products/{category}/{id}`*
- **Use `@RequestParam`** for optional parameters  
  *Example: `/users?active=true&role=admin`*

**Theoretical Keywords:**  
**Query parameters**, **URL path variables**, **Resource identification**, **Filtering**

---

### **30. What is `@RequestBody`?**
- **Annotation to bind HTTP request body** to method parameter
- **Converts JSON/XML** to Java object automatically  
  *Example: JSON → User object*
- **Used with `POST`/`PUT`** requests
- **Requires `Content-Type` header**  
  *Example: `Content-Type: application/json`*
- **Jackson library** handles JSON conversion
- **Validation** can be added with `@Valid`

**Code Examples:**

```java
@PostMapping("/api/users")
public User createUser(@RequestBody User user) {
    // JSON request body automatically converted to User object
    return userService.save(user);
}

@PostMapping("/api/users")
public ResponseEntity<User> createUser(
        @Valid @RequestBody UserCreateRequest request) {
    // @Valid triggers validation on request DTO
    User user = userService.createUser(request);
    return ResponseEntity.created(URI.create("/users/" + user.getId()))
                         .body(user);
}
```

**JSON Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}
```

**Theoretical Keywords:**  
**Request body binding**, **JSON conversion**, **POST/PUT methods**, **Jackson mapping**

---

### **31. What is `ResponseEntity`?**
- **Wrapper for HTTP response** in Spring
- **Full control** over response: status, headers, body
- **Generic class** `ResponseEntity<T>` where T is response body type
- **Builder pattern** for easy construction
- **Alternative to** returning just the object

**Code Examples:**

**Simple Usage:**
```java
@GetMapping("/api/users/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    User user = userService.findById(id);
    if (user == null) {
        return ResponseEntity.notFound().build();  // 404
    }
    return ResponseEntity.ok(user);  // 200 OK with user body
}
```

**Advanced Usage:**
```java
@PostMapping("/api/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    User savedUser = userService.save(user);
    
    return ResponseEntity
        .created(URI.create("/api/users/" + savedUser.getId()))  // 201
        .header("X-Custom-Header", "value")
        .body(savedUser);
}

@GetMapping("/api/users")
public ResponseEntity<List<User>> getUsers() {
    List<User> users = userService.findAll();
    
    return ResponseEntity
        .status(HttpStatus.OK)
        .header("X-Total-Count", String.valueOf(users.size()))
        .body(users);
}

// With custom status
@DeleteMapping("/api/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    boolean deleted = userService.delete(id);
    if (!deleted) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404
    }
    return ResponseEntity.noContent().build();  // 204 No Content
}
```

**Theoretical Keywords:**  
**HTTP response wrapper**, **Status codes**, **Response headers**, **Builder pattern**

---

### **32. What are HTTP methods?**
- **Verbs defining operation** on resources
- **Standard CRUD mapping**:

| **Method** | **Idempotent** | **Safe** | **Purpose** | **Example** |
|------------|----------------|----------|-------------|-------------|
| **GET** | Yes | Yes | Retrieve resource | `GET /api/users/1` |
| **POST** | No | No | Create resource | `POST /api/users` |
| **PUT** | Yes | No | Replace resource | `PUT /api/users/1` |
| **PATCH** | No | No | Partial update | `PATCH /api/users/1` |
| **DELETE** | Yes | No | Delete resource | `DELETE /api/users/1` |
| **HEAD** | Yes | Yes | Headers only | `HEAD /api/users` |
| **OPTIONS** | Yes | Yes | Allowed methods | `OPTIONS /api/users` |

**Key Concepts:**
- **Idempotent** - Same request multiple times = same effect
- **Safe** - No modification of resource (read-only)
- **RESTful usage**:
  - **GET** - Read (no side effects)
  - **POST** - Create (new resource)
  - **PUT** - Update (full replacement)
  - **PATCH** - Update (partial)
  - **DELETE** - Delete

**Theoretical Keywords:**  
**HTTP verbs**, **CRUD operations**, **Idempotent**, **Safe**, **Resource operations**

---

### **33. What are HTTP status codes?**
- **3-digit codes** indicating request outcome
- **5 categories** (first digit):

| **Category** | **Range** | **Meaning** | **Common Codes** |
|--------------|-----------|-------------|------------------|
| **1xx** | 100-199 | Informational | 100 Continue |
| **2xx** | 200-299 | Success | 200 OK, 201 Created, 204 No Content |
| **3xx** | 300-399 | Redirection | 301 Moved, 304 Not Modified |
| **4xx** | 400-499 | Client Error | 400 Bad Request, 401 Unauthorized, 404 Not Found |
| **5xx** | 500-599 | Server Error | 500 Internal Error, 503 Service Unavailable |

**Common Status Codes:**

**Success (2xx):**
- **200 OK** - Standard success response
- **201 Created** - Resource created (with Location header)
- **204 No Content** - Success but no body (DELETE)

**Client Error (4xx):**
- **400 Bad Request** - Invalid request syntax
- **401 Unauthorized** - Authentication needed
- **403 Forbidden** - Authenticated but not authorized
- **404 Not Found** - Resource doesn't exist
- **409 Conflict** - Resource conflict (duplicate)

**Server Error (5xx):**
- **500 Internal Server Error** - Generic server error
- **503 Service Unavailable** - Temporarily unavailable

**Spring Usage Example:**
```java
@PostMapping("/api/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
        User saved = userService.save(user);
        return ResponseEntity
            .created(URI.create("/users/" + saved.getId()))  // 201
            .body(saved);
    } catch (DuplicateException e) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)  // 409
            .body(null);
    }
}
```

**Theoretical Keywords:**  
**Status codes**, **Response categories**, **Success/error codes**, **RESTful status**

---

### **34. Difference between PUT and PATCH**

| **Aspect** | **PUT** | **PATCH** |
|------------|---------|-----------|
| **Purpose** | Replace entire resource | Update part of resource |
| **Idempotent** | Yes | No (depends on implementation) |
| **Request Body** | Complete resource | Only fields to update |
| **Semantics** | "Replace with this" | "Apply these changes" |
| **Safety** | Potentially destructive | Safer for partial updates |

**Code Examples:**

**PUT (Full Update):**
```java
@PutMapping("/api/users/{id}")
public User updateUser(@PathVariable Long id, 
                       @RequestBody User user) {
    // User object must contain ALL fields
    user.setId(id);  // Set ID from path
    return userService.update(user);
}
```
**PUT Request:**
```json
{
  "id": 123,
  "name": "Updated Name",
  "email": "updated@email.com",
  "age": 35,
  "address": "New Address"
  // ALL fields required
}
```

**PATCH (Partial Update):**
```java
@PatchMapping("/api/users/{id}")
public User partialUpdateUser(@PathVariable Long id,
                              @RequestBody Map<String, Object> updates) {
    // Only update provided fields
    return userService.partialUpdate(id, updates);
}
```
**PATCH Request:**
```json
{
  "email": "newemail@example.com",
  "age": 36
  // Only fields to update
}
```

**JSON Merge Patch (Standard):**
```java
@PatchMapping(path = "/api/users/{id}", 
              consumes = "application/merge-patch+json")
public User mergePatchUser(@PathVariable Long id,
                           @RequestBody User partialUser) {
    return userService.mergeUpdate(id, partialUser);
}
```

**When to use:**
- **Use PUT** when client sends complete resource
- **Use PATCH** when client sends only changed fields
- **Use PATCH** for large resources to reduce bandwidth

**Theoretical Keywords:**  
**Full vs Partial update**, **Idempotent**, **Request body size**, **Update semantics**

---

### **35. How do you design REST APIs?**
- **Resource-oriented design**  
  *Example: Nouns (users, orders) not verbs (getUsers)*
- **Use proper HTTP methods**  
  *Example: `GET /users`, `POST /users`, not `GET /getUsers`*
- **Consistent URL patterns**  
  *Example: `/api/{resource}/{id}/{sub-resource}`*
- **Proper status codes**  
  *Example: 201 for created, 404 for not found*
- **Versioning** in URL or headers  
  *Example: `/api/v1/users` or `Accept: application/vnd.api.v1+json`*
- **Pagination, filtering, sorting**  
  *Example: `/users?page=1&size=20&sort=name,desc`*
- **Error handling** with consistent format  
  *Example: `{"error": "message", "code": "ERROR_CODE"}`*
- **HATEOAS** for discoverability (optional)
- **Security** - authentication/authorization
- **Documentation** with OpenAPI/Swagger

**Design Principles:**

1. **Resource Naming:**
   ```
   Good: GET /api/users/123/orders
   Bad:  GET /api/getUserOrders?userId=123
   ```

2. **HTTP Methods Mapping:**
   ```
   POST    /api/users     → Create user
   GET     /api/users     → List users
   GET     /api/users/123 → Get user 123
   PUT     /api/users/123 → Update user 123
   DELETE  /api/users/123 → Delete user 123
   ```

3. **Response Design:**
   ```json
   {
     "data": { /* resource */ },
     "meta": {
       "page": 1,
       "total": 100
     },
     "links": {
       "self": "/api/users/123",
       "orders": "/api/users/123/orders"
     }
   }
   ```

4. **Error Response:**
   ```json
   {
     "timestamp": "2024-01-15T10:30:00Z",
     "status": 404,
     "error": "Not Found",
     "message": "User with id 999 not found",
     "path": "/api/users/999"
   }
   ```

5. **Versioning Strategies:**
   - **URL versioning**: `/api/v1/users`
   - **Header versioning**: `Accept: application/vnd.api.v1+json`
   - **Query param**: `/api/users?version=1`

**Best Practices Checklist:**
- ✅ Use nouns for resources
- ✅ Use HTTP methods correctly
- ✅ Return appropriate status codes
- ✅ Version your API
- ✅ Support pagination for collections
- ✅ Consistent error responses
- ✅ Secure with HTTPS
- ✅ Document with OpenAPI/Swagger
- ✅ Use proper content negotiation

**Theoretical Keywords:**  
**Resource design**, **HTTP semantics**, **Versioning**, **Pagination**, **Error handling**, **API documentation**