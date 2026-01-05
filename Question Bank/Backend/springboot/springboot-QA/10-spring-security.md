Here are the answers with **examples and bolded keywords**:

## **Spring Security **

### **72. What is Spring Security?**
- **Security framework** for Spring applications
- **Provides authentication and authorization**
- **Protects against common attacks** (CSRF, XSS, session fixation)
- **Highly customizable** security configurations
- **Integration** with various authentication mechanisms

**Key Features:**
1. **Authentication** - Who are you?
2. **Authorization** - What are you allowed to do?
3. **Protection against attacks** - CSRF, XSS, clickjacking
4. **Servlet API integration** - Filters, Servlets
5. **Integration options** - LDAP, OAuth2, SAML, JWT

**Basic Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            );
        
        return http.build();
    }
}
```

**Maven Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Theoretical Keywords:**  
**Security framework**, **Authentication**, **Authorization**, **Attack protection**, **Servlet filters**

---

### **73. Authentication vs Authorization**

| **Aspect** | **Authentication** | **Authorization** |
|------------|-------------------|-------------------|
| **Purpose** | Verifies identity | Verifies permissions |
| **Question** | "Who are you?" | "What can you do?" |
| **Example** | Login with username/password | Access admin dashboard |
| **Timing** | Comes first | Comes after authentication |
| **Methods** | Password, OAuth, JWT | Roles, Permissions, Scopes |

**Authentication Examples:**
```java
// User login - verifying identity
public class AuthenticationRequest {
    private String username;
    private String password;
}

// In service
public Authentication authenticate(String username, String password) {
    UserDetails user = userDetailsService.loadUserByUsername(username);
    
    // Check password
    if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new BadCredentialsException("Invalid password");
    }
    
    return new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
}
```

**Authorization Examples:**
```java
// Controller with authorization
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")  // Authorization
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")  // Must be authenticated
    public User getProfile(Principal principal) {
        return userService.findByUsername(principal.getName());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'User', 'READ')")  // Custom permission
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}

// Method security
@Service
public class OrderService {
    
    @PreAuthorize("#userId == authentication.principal.id")
    public Order getUserOrder(Long userId, Long orderId) {
        // User can only access their own orders
        return orderRepository.findByUserIdAndId(userId, orderId);
    }
}
```

**Security Configuration with Both:**
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Enables @PreAuthorize, @PostAuthorize
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Authentication configuration
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            
            // Authorization configuration
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()  // No auth needed
                .requestMatchers("/user/**").hasRole("USER")  // Auth + Role check
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()  // Auth needed
            );
        
        return http.build();
    }
}
```

**Theoretical Keywords:**  
**Identity verification**, **Permission checking**, **Credentials**, **Roles**, **Permissions**

---

### **74. What is JWT?**
- **JSON Web Token** - compact, URL-safe token format
- **Self-contained** - contains claims about user
- **Stateless** - server doesn't store session
- **Signed** - can be verified for integrity
- **Three parts**: Header.Payload.Signature

**JWT Structure:**

**1. Header** (Algorithm & token type):
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**2. Payload** (Claims - user data):
```json
{
  "sub": "1234567890",        // Subject (user ID)
  "name": "John Doe",
  "email": "john@example.com",
  "roles": ["USER", "ADMIN"],
  "iat": 1516239022,          // Issued at
  "exp": 1516242622           // Expiration time
}
```

**3. Signature** (Verification):
```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret-key
)
```

**Final JWT Token:**
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.        // Header
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.  // Payload
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c   // Signature
```

**Maven Dependency:**
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

**Theoretical Keywords:**  
**JSON Web Token**, **Self-contained**, **Stateless authentication**, **Claims**, **Digital signature**

---

### **75. Explain JWT Flow**

**Complete JWT Authentication Flow:**

```
┌─────────┐    1. Login Request      ┌─────────┐
│         │ ───────────────────────> │         │
│  Client │                          │  Server │
│         │ <─────────────────────── │         │
└─────────┘    2. JWT Token         └─────────┘
        │                                │
        │  3. Request with JWT           │
        │ ───────────────────────────────>│
        │                                │
        │  4. Verified Response          │
        │ <───────────────────────────────│
```

**Step-by-Step Implementation:**

**1. User Login (Authentication):**
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 1. Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
            )
        );
        
        // 2. Generate JWT
        String token = jwtTokenUtil.generateToken(authentication);
        
        // 3. Return token
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

public class LoginRequest {
    private String username;
    private String password;
}

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    
    public AuthResponse(String token) {
        this.token = token;
    }
}
```

**2. JWT Utility Class:**
```java
@Component
public class JwtTokenUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("roles", userDetails.getAuthorities()
                   .stream()
                   .map(GrantedAuthority::getAuthority)
                   .collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

**3. JWT Filter (Processes each request):**
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Get token from header
        String token = getTokenFromRequest(request);
        
        if (token != null && jwtTokenUtil.validateToken(token)) {
            // 2. Extract username
            String username = jwtTokenUtil.getUsernameFromToken(token);
            
            // 3. Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // 4. Create authentication object
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            
            // 5. Set security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

**4. Security Configuration with JWT:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for stateless JWT
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No sessions
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, 
                           UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**5. Client Usage:**
```javascript
// Login
fetch('/api/auth/login', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({
        username: 'john',
        password: 'password123'
    })
})
.then(response => response.json())
.then(data => {
    const token = data.token;
    localStorage.setItem('jwtToken', token);  // Store token
});

// Subsequent requests
fetch('/api/protected', {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
    }
});
```

**Theoretical Keywords:**  
**Authentication flow**, **Token generation**, **Request filtering**, **Stateless sessions**, **Bearer token**

---

### **76. Where is JWT stored on client side?**

**Storage Options:**

**1. LocalStorage (Common for SPAs):**
```javascript
// Store token
localStorage.setItem('jwtToken', token);

// Retrieve token
const token = localStorage.getItem('jwtToken');

// Remove token
localStorage.removeItem('jwtToken');
```
**Pros:** Persists across browser sessions  
**Cons:** Vulnerable to XSS attacks

**2. SessionStorage:**
```javascript
// Store token (cleared when tab closes)
sessionStorage.setItem('jwtToken', token);
```
**Pros:** Cleared when tab closes, slightly more secure  
**Cons:** Doesn't persist across tabs

**3. Cookies (HTTP-only, Secure):**
```java
// Server sets cookie
@PostMapping("/login")
public ResponseEntity<?> login(HttpServletResponse response, 
                               @RequestBody LoginRequest request) {
    // ... authentication logic
    
    // Create cookie
    Cookie cookie = new Cookie("jwtToken", token);
    cookie.setHttpOnly(true);      // Not accessible via JavaScript
    cookie.setSecure(true);        // Only over HTTPS
    cookie.setPath("/");
    cookie.setMaxAge(7 * 24 * 60 * 60);  // 7 days
    
    response.addCookie(cookie);
    return ResponseEntity.ok().build();
}
```
**Pros:** Protected from XSS, automatic sending with requests  
**Cons:** Vulnerable to CSRF (mitigate with SameSite attribute)

**4. Cookies with SameSite and Secure flags:**
```java
// Modern cookie settings
ResponseCookie cookie = ResponseCookie.from("jwtToken", token)
    .httpOnly(true)
    .secure(true)
    .sameSite("Strict")  // or "Lax"
    .path("/")
    .maxAge(Duration.ofDays(7))
    .build();

response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
```

**5. React Context/State Management:**
```javascript
// React Context for token
const AuthContext = createContext();

function AuthProvider({ children }) {
    const [token, setToken] = useState(localStorage.getItem('token'));
    
    const login = (newToken) => {
        setToken(newToken);
        localStorage.setItem('token', newToken);
    };
    
    const logout = () => {
        setToken(null);
        localStorage.removeItem('token');
    };
    
    return (
        <AuthContext.Provider value={{ token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

// Using axios interceptor
axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
```

**Best Practices:**

1. **For SPA + REST API**: Use `localStorage` or `sessionStorage`
2. **Add XSS protection**: Content Security Policy (CSP)
3. **Short expiration**: 15-60 minutes for access tokens
4. **Use refresh tokens** for longer sessions
5. **Implement token rotation**

**Security Considerations:**

```yaml
# application.yml for production
server:
  ssl:
    enabled: true  # Always use HTTPS
    
spring:
  security:
    require-ssl: true
```

**Theoretical Keywords:**  
**Token storage**, **LocalStorage**, **HTTP-only cookies**, **XSS vulnerability**, **Security considerations**

---

### **77. What is `SecurityFilterChain`?**
- **Core component** in Spring Security
- **Chain of filters** that process requests
- **Each filter** handles specific security concern
- **Customizable** through configuration
- **Replacement** for deprecated `WebSecurityConfigurerAdapter`

**Default Filters in Chain:**
1. `ChannelProcessingFilter` - Redirects to HTTPS
2. `SecurityContextPersistenceFilter` - Stores security context
3. `LogoutFilter` - Handles logout
4. `UsernamePasswordAuthenticationFilter` - Processes login
5. `BasicAuthenticationFilter` - Basic auth
6. `RememberMeAuthenticationFilter` - Remember me
7. `AnonymousAuthenticationFilter` - Anonymous user
8. `ExceptionTranslationFilter` - Handles security exceptions
9. `FilterSecurityInterceptor` - Authorization decisions

**Custom SecurityFilterChain:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless API
            .csrf(csrf -> csrf.disable())
            
            // Configure session management
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configure authorization
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            
            // Configure form login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/api/auth/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true")
                .permitAll()
            )
            
            // Configure logout
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/login?logout=true")
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Configure exception handling
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
            )
            
            // Add custom filters
            .addFilterBefore(jwtAuthenticationFilter, 
                           UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(customFilter, 
                          BasicAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public FilterRegistrationBean<CustomFilter> customFilterRegistration() {
        FilterRegistrationBean<CustomFilter> registration = 
            new FilterRegistrationBean<>();
        registration.setFilter(new CustomFilter());
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }
}
```

**Multiple SecurityFilterChains:**
```java
@Configuration
@EnableWebSecurity
public class MultiSecurityConfig {
    
    // API security (stateless, JWT)
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, 
                           UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    // Web security (stateful, sessions)
    @Bean  
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/web/**")
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll())
            .logout(logout -> logout
                .permitAll());
        
        return http.build();
    }
    
    // Public endpoints
    @Bean
    @Order(3)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/public/**")
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll());
        
        return http.build();
    }
}
```

**Theoretical Keywords:**  
**Filter chain**, **Request processing**, **Security filters**, **HttpSecurity configuration**, **Filter ordering**

---

### **78. What is `PasswordEncoder`?**
- **Interface for encoding passwords**
- **One-way encryption** (hash, not encryption)
- **Prevents password storage** in plain text
- **Multiple implementations** available
- **Spring Security requires** password encoding

**Available Implementations:**

**1. BCryptPasswordEncoder (Recommended):**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();  // Default strength: 10
}

// With custom strength
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);  // Higher = more secure but slower
}

// Usage
PasswordEncoder encoder = new BCryptPasswordEncoder();
String rawPassword = "password123";
String encodedPassword = encoder.encode(rawPassword);
// Result: "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"

// Verification
boolean matches = encoder.matches(rawPassword, encodedPassword);  // true
```

**2. Argon2PasswordEncoder (Most secure):**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new Argon2PasswordEncoder(16, 32, 1, 65536, 10);
}
```

**3. Pbkdf2PasswordEncoder:**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
}
```

**4. SCryptPasswordEncoder:**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new SCryptPasswordEncoder();
}
```

**5. DelegatingPasswordEncoder (Multiple algorithms):**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    String encodingId = "bcrypt";
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put(encodingId, new BCryptPasswordEncoder());
    encoders.put("sha256", new StandardPasswordEncoder());
    
    return new DelegatingPasswordEncoder(encodingId, encoders);
}
// Automatically upgrades old hashes
```

**Usage in UserService:**
```java
@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(UserCreateRequest request) {
        // Encode password before saving
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);  // Store encoded password
        user.setEmail(request.getEmail());
        
        return userRepository.save(user);
    }
    
    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        // Compare raw password with encoded password
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
```

**Spring Security Auto-configuration:**
```java
@Configuration
public class SecurityConfig {
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            
            return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())  // Already encoded
                .roles(user.getRoles().toArray(new String[0]))
                .build();
        };
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        
        return new ProviderManager(authProvider);
    }
}
```

**Password Migration (Upgrading):**
```java
@Component
public class PasswordMigrationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public void migratePasswords() {
        List<User> users = userRepository.findAll();
        
        for (User user : users) {
            // Check if password needs migration (plain text or old hash)
            if (needsMigration(user.getPassword())) {
                // Re-encode with new algorithm
                String newPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(newPassword);
                userRepository.save(user);
            }
        }
    }
    
    private boolean needsMigration(String password) {
        // Check if password is plain text or uses old encoding
        return !password.startsWith("$2a$") && !password.startsWith("$2b$");
    }
}
```

**Theoretical Keywords:**  
**Password hashing**, **One-way encryption**, **BCrypt**, **Password verification**, **Security best practice**

---

### **79. How role-based authorization works?**

**1. Define Roles in Database:**
```java
@Entity
public class User {
    @Id
    private Long id;
    
    private String username;
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

@Entity
public class Role {
    @Id
    private Long id;
    
    private String name;  // ROLE_ADMIN, ROLE_USER, ROLE_MANAGER
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
```

**2. UserDetailsService Implementation:**
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsernameWithRoles(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Convert database roles to Spring Security GrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }
}
```

**3. Security Configuration with Roles:**
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Enable method security
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/public/**").permitAll()
                
                // Role-based URL security
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/api/user/**").hasRole("USER")
                
                // Hierarchical roles (ADMIN can access USER endpoints)
                .requestMatchers("/api/**").hasRole("USER")
                
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .logout(withDefaults());
        
        return http.build();
    }
}
```

**4. Method-level Security with `@PreAuthorize`:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // Role-based authorization
    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Admin access only");
    }
    
    @GetMapping("/manager-or-admin")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<String> managerOrAdmin() {
        return ResponseEntity.ok("Manager or Admin access");
    }
    
    // Multiple conditions
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == authentication.principal.id)")
    public ResponseEntity<User> updateUser(@PathVariable Long id, 
                                          @RequestBody UserUpdateRequest request) {
        // Admin can update anyone, users can only update themselves
        User updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }
}

@Service
public class OrderService {
    
    @PreAuthorize("hasRole('ADMIN') or hasPermission(#orderId, 'Order', 'VIEW')")
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    
    @PostAuthorize("hasRole('ADMIN') or returnObject.userId == authentication.principal.id")
    public Order getOrderWithPostAuth(Long orderId) {
        // Check after method execution
        return orderRepository.findById(orderId).orElse(null);
    }
}
```

**5. Custom Permission Evaluator:**
```java
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public boolean hasPermission(Authentication authentication, 
                                Object targetDomainObject, 
                                Object permission) {
        // Implement custom permission logic
        User currentUser = userRepository.findByUsername(authentication.getName());
        
        if (targetDomainObject instanceof Order) {
            Order order = (Order) targetDomainObject;
            return hasOrderPermission(currentUser, order, permission.toString());
        }
        
        return false;
    }
    
    @Override
    public boolean hasPermission(Authentication authentication, 
                                Serializable targetId, 
                                String targetType, 
                                Object permission) {
        // Implement custom permission logic by ID
        if ("Order".equals(targetType)) {
            Order order = orderRepository.findById((Long) targetId).orElse(null);
            return hasPermission(authentication, order, permission);
        }
        
        return false;
    }
    
    private boolean hasOrderPermission(User user, Order order, String permission) {
        if ("VIEW".equals(permission)) {
            return user.getId().equals(order.getUserId()) || 
                   user.hasRole("ADMIN");
        }
        if ("EDIT".equals(permission)) {
            return user.hasRole("ADMIN") || 
                   (user.hasRole("MANAGER") && order.getDepartment().equals(user.getDepartment()));
        }
        return false;
    }
}

// Register permission evaluator
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    
    @Autowired
    private CustomPermissionEvaluator permissionEvaluator;
    
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = 
            new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }
}
```

**6. Role Hierarchy:**
```java
@Bean
public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = 
        "ROLE_ADMIN > ROLE_MANAGER \n" +
        "ROLE_MANAGER > ROLE_USER \n" +
        "ROLE_USER > ROLE_GUEST";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
}

@Bean
public SecurityExpressionHandler<FilterInvocation> webSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler expressionHandler = 
        new DefaultWebSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy());
    return expressionHandler;
}
```

**7. Testing Role-based Authorization:**
```java
@SpringBootTest
@AutoConfigureMockMvc
public class RoleAuthorizationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithMockUser(roles = "USER")
    public void testUserAccess() throws Exception {
        mockMvc.perform(get("/api/user/profile"))
               .andExpect(status().isOk());
        
        mockMvc.perform(get("/api/admin/dashboard"))
               .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccess() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
               .andExpect(status().isOk());
        
        mockMvc.perform(get("/api/user/profile"))
               .andExpect(status().isOk());  // Admin can access user endpoints
    }
}
```

**Theoretical Keywords:**  
**Role-based access control**, **GrantedAuthority**, `@PreAuthorize`, **Permission evaluator**, **Role hierarchy**

---

### **80. What is CSRF?**
- **Cross-Site Request Forgery** attack
- **Tricks user's browser** into making unauthorized requests
- **Uses authenticated session** to perform actions
- **Spring Security provides** CSRF protection by default

**CSRF Attack Example:**
```html
<!-- Malicious website -->
<img src="https://bank.com/transfer?amount=1000&to=hacker" />

<!-- If user is logged into bank.com, browser sends cookies -->
<!-- Bank processes transfer thinking it's legitimate request -->
```

**Spring Security CSRF Protection:**

**1. Default CSRF Protection (Stateful apps):**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF enabled by default for stateful apps
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults());
        
        return http.build();
    }
}
```

**2. CSRF Token Generation:**
```html
<!-- Thymeleaf automatically includes CSRF token -->
<form th:action="@{/transfer}" method="post">
    <input type="hidden" 
           th:name="${_csrf.parameterName}" 
           th:value="${_csrf.token}" />
    <input type="text" name="amount" />
    <input type="text" name="toAccount" />
    <button type="submit">Transfer</button>
</form>

<!-- Or in JavaScript -->
<script>
    const csrfToken = document.querySelector("meta[name='_csrf']").content;
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    
    fetch('/api/transfer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ amount: 100, to: 'account123' })
    });
</script>
```

**3. Disable CSRF for Stateless APIs (JWT):**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless REST API
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
```

**4. Custom CSRF Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Custom CSRF configuration
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/public/**", "/webhook/**")
                .requireCsrfProtectionMatcher(request -> {
                    // Custom logic for when CSRF is required
                    String method = request.getMethod();
                    return !method.equalsIgnoreCase("GET") && 
                           !method.equalsIgnoreCase("HEAD") && 
                           !method.equalsIgnoreCase("OPTIONS") && 
                           !method.equalsIgnoreCase("TRACE");
                })
            )
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
```

**5. CSRF with Angular/React (Cookie-based):**
```java
@Bean
public CsrfTokenRepository csrfTokenRepository() {
    CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
    repository.setCookiePath("/");
    repository.setCookieName("XSRF-TOKEN");  // Angular expects this name
    return repository;
}

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
            .csrfTokenRepository(csrfTokenRepository())
        )
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        );
    
    return http.build();
}
```

**6. CSRF Filter Customization:**
```java
@Component
public class CustomCsrfFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        // Custom CSRF logic
        String csrfToken = request.getHeader("X-CSRF-TOKEN");
        String sessionToken = (String) request.getSession().getAttribute("csrfToken");
        
        if ("POST".equalsIgnoreCase(request.getMethod()) ||
            "PUT".equalsIgnoreCase(request.getMethod()) ||
            "DELETE".equalsIgnoreCase(request.getMethod()) ||
            "PATCH".equalsIgnoreCase(request.getMethod())) {
            
            if (csrfToken == null || !csrfToken.equals(sessionToken)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

**7. SameSite Cookie Protection:**
```java
@Bean
public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieSameSiteCustomizer() {
    return factory -> factory.addContextCustomizers(context -> {
        CookieProcessor cookieProcessor = new StandardCookieProcessor();
        cookieProcessor.setSameSiteCookies("strict");  // Lax, Strict, or None
        context.setCookieProcessor(cookieProcessor);
    });
}
```

**When to disable CSRF:**
- **Stateless APIs** (JWT-based)
- **Public APIs** consumed by third parties
- **Mobile app backends**

**Best Practices:**
1. **Enable CSRF** for stateful web applications
2. **Disable CSRF** for stateless REST APIs
3. **Use SameSite cookies** as additional protection
4. **Implement CORS** properly
5. **Use secure, HttpOnly cookies**

**Theoretical Keywords:**  
**Cross-Site Request Forgery**, **CSRF token**, **SameSite cookies**, **Stateless vs Stateful**, **Security protection**

---

### **81. How do you secure REST APIs?**

**1. Authentication (JWT):**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable for stateless API
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, 
                           UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

**2. HTTPS Enforcement:**
```java
@Configuration
public class HttpsConfig {
    
    @Bean
    public SecurityFilterChain httpsFilterChain(HttpSecurity http) throws Exception {
        http
            .requiresChannel(channel -> channel
                .anyRequest().requiresSecure()  // Force HTTPS
            );
        
        return http.build();
    }
}

// Or in application.yml
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: changeit
    key-store-type: PKCS12
    key-alias: tomcat
    
spring:
  security:
    require-ssl: true
```

**3. CORS Configuration:**
```java
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://trusted-domain.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}

// Or with WebMvcConfigurer
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://frontend.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

**4. Rate Limiting:**
```java
@Component
public class RateLimitFilter extends OncePerRequestFilter {
    
    private final Map<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        String clientIp = getClientIp(request);
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;
        
        RateLimitInfo info = rateLimitMap.computeIfAbsent(key, 
            k -> new RateLimitInfo(100, Duration.ofHours(1)));  // 100 requests/hour
        
        if (!info.isAllowed()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}

// Or using Bucket4j
@Configuration
public class RateLimitConfig {
    
    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
        FilterRegistrationBean<RateLimitFilter> registration = 
            new FilterRegistrationBean<>();
        registration.setFilter(new RateLimitFilter());
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        return registration;
    }
}
```

**5. Input Validation and Sanitization:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody @Sanitized UserCreateRequest request) {
        // @Valid for validation
        // @Sanitized for XSS protection (custom annotation)
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}

@Component
public class XssSanitizer {
    
    private static final HtmlSanitizer SANITIZER = Sanitizers.FORMATTING
        .and(Sanitizers.LINKS)
        .and(Sanitizers.BLOCKS);
    
    public String sanitize(String input) {
        if (input == null) return null;
        return SANITIZER.sanitize(input);
    }
}

// Custom annotation
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SafeHtmlValidator.class)
public @interface Sanitized {
    String message() default "Contains unsafe content";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

**6. API Versioning:**
```java
@RestController
@RequestMapping("/api/v1/users")  // Version in URL
public class UserControllerV1 { ... }

@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 { ... }

// Or using headers
@GetMapping(value = "/users", headers = "API-Version=1")
public ResponseEntity<?> getUsersV1() { ... }

@GetMapping(value = "/users", headers = "API-Version=2")
public ResponseEntity<?> getUsersV2() { ... }
```

**7. Logging and Monitoring:**
```java
@Component
public class ApiLoggingFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingFilter.class);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        long startTime = System.currentTimeMillis();
        
        // Wrap response to capture body
        ContentCachingResponseWrapper responseWrapper = 
            new ContentCachingResponseWrapper(response);
        
        try {
            filterChain.doFilter(request, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            
            // Log request details (sanitize sensitive data)
            logRequest(request, responseWrapper, duration);
            
            // Copy body to original response
            responseWrapper.copyBodyToResponse();
        }
    }
    
    private void logRequest(HttpServletRequest request, 
                           HttpServletResponse response,
                           long duration) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();
        String user = request.getRemoteUser();
        
        logger.info("API Call: {} {} | Status: {} | User: {} | Duration: {}ms",
                   method, uri, status, user, duration);
        
        // Log failed requests
        if (status >= 400) {
            logger.warn("Failed API call: {} {} - Status: {}", method, uri, status);
        }
    }
}
```

**8. Security Headers:**
```java
@Configuration
public class SecurityHeadersConfig {
    
    @Bean
    public SecurityFilterChain securityHeadersFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'"))
                .frameOptions(frame -> frame.sameOrigin())
                .xssProtection(xss -> xss
                    .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000))
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
            );
        
        return http.build();
    }
}
```

**9. API Keys for Third-party Access:**
```java
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    
    @Autowired
    private ApiKeyService apiKeyService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        String apiKey = request.getHeader("X-API-Key");
        
        if (apiKey != null) {
            if (apiKeyService.isValidApiKey(apiKey)) {
                // Set security context for API key user
                Authentication auth = new ApiKeyAuthentication(apiKey);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API key");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}

public class ApiKeyAuthentication extends AbstractAuthenticationToken {
    
    private final String apiKey;
    
    public ApiKeyAuthentication(String apiKey) {
        super(Collections.emptyList());
        this.apiKey = apiKey;
        setAuthenticated(true);
    }
    
    @Override
    public Object getCredentials() {
        return apiKey;
    }
    
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
```

**10. Complete Security Configuration:**
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApiSecurityConfig {
    
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // Security for /api/**
            .securityMatcher("/api/**")
            
            // Disable CSRF for stateless API
            .csrf(csrf -> csrf.disable())
            
            // Stateless session management
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // HTTPS enforcement
            .requiresChannel(channel -> channel
                .anyRequest().requiresSecure()
            )
            
            // Security headers
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'"))
                .frameOptions(frame -> frame.deny())
            )
            
            // CORS configuration
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Authorization
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            
            // Authentication
            .authenticationManager(authenticationManager())
            
            // Exception handling
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
            )
            
            // Add custom filters
            .addFilterBefore(jwtAuthenticationFilter, 
                           UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(rateLimitFilter, 
                           JwtAuthenticationFilter.class)
            .addFilterAfter(apiLoggingFilter, 
                           JwtAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://trusted-frontend.com"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
```

**Best Practices Checklist:**
1. ✅ Use HTTPS everywhere
2. ✅ Implement proper authentication (JWT/OAuth2)
3. ✅ Add authorization (roles/permissions)
4. ✅ Configure CORS properly
5. ✅ Add rate limiting
6. ✅ Validate and sanitize all inputs
7. ✅ Add security headers
8. ✅ Implement logging and monitoring
9. ✅ Use API versioning
10. ✅ Regular security audits

**Theoretical Keywords:**  
**REST API security**, **JWT authentication**, **CORS configuration**, **Rate limiting**, **Security headers**, **Input validation**