# Spring Security Interview Questions – Answers



### 1. What is Spring Security?
* A **powerful and customizable authentication and authorization framework** for Java applications, specifically designed for Spring-based applications.
* It is the **de facto standard** for securing Spring applications.
* Provides comprehensive security services for enterprise applications, including authentication, authorization, and protection against common vulnerabilities.

### 2. Why is Spring Security used?
* **To protect application resources** from unauthorized access.
* **To separate security concerns** from business logic.
* **To provide a comprehensive security solution** that handles:
  * Authentication (verifying who you are)
  * Authorization (verifying what you can do)
  * Protection against attacks (CSRF, session fixation, clickjacking)
  * Integration with various authentication providers
* **To comply with security standards** and best practices in enterprise applications.

### 3. What are the main features of Spring Security?
* **Authentication** - Support for various authentication mechanisms (form-based, HTTP Basic, OAuth2, LDAP, etc.)
* **Authorization** - Role-based and permission-based access control
* **Session Management** - Secure session handling
* **CSRF Protection** - Built-in Cross-Site Request Forgery protection
* **CORS Support** - Cross-Origin Resource Sharing configuration
* **Security Headers** - Automatic security headers (HSTS, X-Frame-Options, etc.)
* **Method Security** - Annotation-based method-level security
* **Integration** - Seamless integration with Spring ecosystem (Spring MVC, Spring Boot, Spring Data)
* **Extensibility** - Highly customizable through interfaces and extension points

### 4. What is authentication?
* The process of **verifying the identity** of a user or system.
* Answers the question: **"Who are you?"**
* **Common methods:**
  * Username/password
  * Token-based (JWT)
  * Certificate-based
  * Biometric
  * Social login (OAuth2)
* In Spring Security, successful authentication results in an `Authentication` object stored in the `SecurityContext`.

### 5. What is authorization?
* The process of **determining what an authenticated user is allowed to do**.
* Answers the question: **"What are you allowed to do?"**
* **Types:**
  * **Role-based:** Based on user roles (e.g., ADMIN, USER)
  * **Permission-based:** Based on specific permissions (e.g., READ, WRITE, DELETE)
  * **Attribute-based:** Dynamic authorization based on attributes
* Implemented through access control rules in configurations or annotations.

### 6. Difference between authentication and authorization.
| Aspect | Authentication | Authorization |
| :--- | :--- | :--- |
| **Purpose** | Verifies **identity** (who you are) | Verifies **permissions** (what you can do) |
| **Timing** | Comes **first** | Comes **after** authentication |
| **Methods** | Credentials, tokens, certificates | Roles, permissions, access control lists |
| **Question** | "Who are you?" | "Are you allowed to do this?" |
| **Example** | Login with username/password | Checking if user has ADMIN role |

### 7. What is `SecurityContext`?
* An **interface** that holds the security information for the current thread of execution.
* Contains the **`Authentication` object** of the currently authenticated user (or anonymous user if not authenticated).
* **Thread-local storage:** Each thread has its own `SecurityContext`.
* Can be accessed via `SecurityContextHolder.getContext()`.

### 8. What is `SecurityContextHolder`?
* The **central class** that provides access to the `SecurityContext`.
* **Strategies for storing `SecurityContext`:**
  * `MODE_THREADLOCAL` - Stores context in ThreadLocal (default)
  * `MODE_INHERITABLETHREADLOCAL` - Inherits context in child threads
  * `MODE_GLOBAL` - Single context for entire application (rarely used)
* **Usage:** `SecurityContextHolder.getContext().getAuthentication()`

### 9. What is `Authentication` object?
* The **core interface** representing an authentication request or authenticated principal.
* **Key properties:**
  * `principal` - The authenticated user (usually `UserDetails`)
  * `credentials` - Usually the password (cleared after authentication)
  * `authorities` - Collection of `GrantedAuthority` objects (roles/permissions)
  * `authenticated` - Boolean indicating if authentication was successful
* **Types:** `UsernamePasswordAuthenticationToken`, `JwtAuthenticationToken`, etc.

### 10. What is `Principal`?
* Represents the **authenticated entity** (user, system, or device).
* Usually an instance of `UserDetails` in Spring Security.
* Can be a username, user ID, or custom object containing user information.
* Accessed via: `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`

### 11. What is `GrantedAuthority`?
* An **interface** representing an authority granted to an `Authentication` object.
* Usually represents **roles** (e.g., "ROLE_ADMIN") or **permissions** (e.g., "READ_PRIVILEGE").
* Stored in the `Authentication.getAuthorities()` method.
* **Common implementations:** `SimpleGrantedAuthority`

### 12. What is `UserDetails`?
* A **core interface** that represents user information required by Spring Security.
* **Methods:**
  * `getUsername()` - Returns username
  * `getPassword()` - Returns encoded password
  * `getAuthorities()` - Returns collection of `GrantedAuthority`
  * `isAccountNonExpired()`, `isAccountNonLocked()`, `isCredentialsNonExpired()`, `isEnabled()`
* Typically implemented by application-specific user classes.

### 13. What is `UserDetailsService`?
* A **core interface** for loading user-specific data.
* **Single method:** `loadUserByUsername(String username)` returns `UserDetails`
* **Purpose:** Bridge between Spring Security and application's user data source (database, LDAP, etc.)
* **Implementations provided:** `InMemoryUserDetailsManager`, `JdbcUserDetailsManager`
* **Custom implementation:** Usually required for database authentication.

### 14. What is password encoding?
* The process of **transforming a plain text password into a secure, irreversible format**.
* **Purpose:** Prevent password exposure even if database is compromised.
* **Requirements:** One-way hashing, salting to prevent rainbow table attacks.
* **Never store passwords in plain text!**

### 15. What is `PasswordEncoder`?
* A **Spring Security interface** for encoding and validating passwords.
* **Key methods:**
  * `encode(CharSequence rawPassword)` - Encodes raw password
  * `matches(CharSequence rawPassword, String encodedPassword)` - Verifies password
* **Common implementations:** `BCryptPasswordEncoder`, `Pbkdf2PasswordEncoder`, `SCryptPasswordEncoder`
* Required for secure password storage.

### 16. What is `BCryptPasswordEncoder`?
* The **most recommended `PasswordEncoder` implementation** in Spring Security.
* **Features:**
  * Uses bcrypt hashing algorithm
  * **Automatic salting** (salt is part of the hash)
  * **Adaptive** - Can increase strength over time (work factor parameter)
  * **Slow by design** - Resistant to brute force attacks
* **Usage:** `new BCryptPasswordEncoder(12)` where 12 is the strength (4-31)

### 17. What is `@EnableWebSecurity`?
* A **marker annotation** that enables Spring Security's web security support.
* **Must be used** on a `@Configuration` class.
* **Triggers:** Import of `WebSecurityConfiguration`, `SpringWebMvcImportSelector`, etc.
* **Replaces:** Older `@EnableWebMvcSecurity` (deprecated)
* **Usage:** Typically applied to a class extending `WebSecurityConfigurerAdapter` (pre-5.7) or declaring `SecurityFilterChain` bean.

### 18. What is `HttpSecurity`?
* The **main configuration class** for HTTP security in Spring Security.
* Allows configuring:
  * URL-based authorization rules
  * Authentication mechanisms (form, HTTP Basic, etc.)
  * CSRF protection
  * Session management
  * Headers (CORS, cache control, etc.)
  * Logout configuration
* **Usage:** Configured in `configure(HttpSecurity http)` method (pre-5.7) or via `SecurityFilterChain` bean.

### 19. What is `AuthenticationManager`?
* The **core interface** for processing authentication requests.
* **Single method:** `authenticate(Authentication authentication)`
* **Responsibilities:**
  * Delegates to configured `AuthenticationProvider`s
  * Returns a fully populated `Authentication` object on success
  * Throws `AuthenticationException` on failure
* **Common implementation:** `ProviderManager` (delegates to list of `AuthenticationProvider`s)

### 20. What is a security filter chain?
* A **series of servlet filters** that process security-related concerns before requests reach the application.
* **Order matters:** Filters execute in a specific order.
* **Key filters in Spring Security:**
  * `SecurityContextPersistenceFilter` - Restores SecurityContext from Session
  * `UsernamePasswordAuthenticationFilter` - Processes form login
  * `BasicAuthenticationFilter` - Processes HTTP Basic authentication
  * `FilterSecurityInterceptor` - Makes access control decisions
  * `ExceptionTranslationFilter` - Handles security exceptions
* Custom filters can be added to the chain.



### 21. How does Spring Security work internally?
* **Request Flow:**
  1. Request enters Spring Security filter chain
  2. `SecurityContextPersistenceFilter` restores SecurityContext from HTTP session
  3. Authentication filters attempt to authenticate request
  4. `FilterSecurityInterceptor` checks authorization rules
  5. If authorized, request proceeds to application
  6. If authentication/authorization fails, appropriate exception is thrown
  7. `ExceptionTranslationFilter` translates exceptions to HTTP responses (redirect to login, 403, etc.)
* **Core Components:**
  * **Filters:** Process security concerns
  * **AuthenticationManager:** Manages authentication
  * **AccessDecisionManager:** Makes authorization decisions
  * **SecurityContext:** Holds authentication information

### 22. What is the filter chain order?
* **Typical order (important ones):**
  1. `ChannelProcessingFilter`
  2. `ConcurrentSessionFilter`
  3. `SecurityContextPersistenceFilter`
  4. `LogoutFilter`
  5. `UsernamePasswordAuthenticationFilter`
  6. `DefaultLoginPageGeneratingFilter`
  7. `BasicAuthenticationFilter`
  8. `RequestCacheAwareFilter`
  9. `SecurityContextHolderAwareRequestFilter`
  10. `AnonymousAuthenticationFilter`
  11. `SessionManagementFilter`
  12. `ExceptionTranslationFilter`
  13. `FilterSecurityInterceptor`
* **Custom filters** can be inserted at specific positions using `addFilterBefore()`, `addFilterAfter()`, `addFilterAt()`.

### 23. What is `OncePerRequestFilter`?
* A **base filter class** that guarantees a single execution per request dispatch.
* **Purpose:** Prevents multiple executions of the same filter in a single request cycle (important for forward/include dispatches).
* **Commonly extended** for custom security filters.
* **Key method:** `doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)`
* **Usage:** Override `doFilterInternal()` instead of `doFilter()`.

### 24. How do you configure in-memory authentication?
* **Using `WebSecurityConfigurerAdapter` (pre-5.7):**
  ```java
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
          .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
          .and()
          .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
  }
  ```
* **Using `SecurityFilterChain` (Spring Security 5.7+):**
  ```java
  @Bean
  public UserDetailsService userDetailsService() {
      UserDetails user = User.withUsername("user")
          .password(passwordEncoder().encode("password"))
          .roles("USER")
          .build();
      return new InMemoryUserDetailsManager(user);
  }
  ```
* **Use case:** Testing, simple applications, never for production!

### 25. How do you configure database authentication?
* **Steps:**
  1. Create `UserDetailsService` implementation that queries database
  2. Configure `PasswordEncoder`
  3. Configure `AuthenticationManagerBuilder` or `SecurityFilterChain`
* **Example implementation:**
  ```java
  @Service
  public class CustomUserDetailsService implements UserDetailsService {
      
      @Autowired
      private UserRepository userRepository;
      
      @Override
      public UserDetails loadUserByUsername(String username) {
          User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
          
          return org.springframework.security.core.userdetails.User
              .withUsername(user.getUsername())
              .password(user.getPassword())
              .authorities(user.getRoles())
              .build();
      }
  }
  ```

### 26. What is form-based authentication?
* **Traditional web application authentication** using HTML form.
* **Default Spring Security behavior:**
  * Provides default login page (`/login`)
  * Processes POST to `/login` with `username` and `password` parameters
  * Redirects to originally requested page after successful login
  * Supports remember-me functionality
* **Configuration:**
  ```java
  http.formLogin()
      .loginPage("/custom-login")  // Custom login page
      .loginProcessingUrl("/perform-login")
      .defaultSuccessUrl("/home", true)
      .failureUrl("/login?error=true");
  ```

### 27. What is HTTP Basic authentication?
* **Simple authentication scheme** where credentials are sent in request header.
* **Format:** `Authorization: Basic base64(username:password)`
* **Characteristics:**
  * **Stateless** - No session created
  * **Browser popup** - Browser shows native login dialog
  * **Credentials sent with every request**
  * **Not secure over HTTP** - Use HTTPS always
* **Configuration:**
  ```java
  http.httpBasic();
  ```
* **Use case:** REST APIs, simple automation scripts

### 28. What is JWT authentication?
* **JSON Web Token-based stateless authentication**.
* **Flow:**
  1. Client sends credentials to authentication endpoint
  2. Server validates and returns signed JWT
  3. Client includes JWT in `Authorization: Bearer <token>` header
  4. Server validates JWT signature and extracts user information
* **JWT Structure:** Header.Payload.Signature
* **Benefits:** Stateless, scalable, self-contained
* **Spring Security implementation:** Using Spring Security OAuth2 Resource Server or custom `OncePerRequestFilter`

### 29. How do you implement role-based authorization?
* **URL-based configuration:**
  ```java
  http.authorizeRequests()
      .antMatchers("/admin/**").hasRole("ADMIN")
      .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
      .antMatchers("/public/**").permitAll()
      .anyRequest().authenticated();
  ```
* **Method-based configuration:**
  ```java
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(Long userId) {
      // admin-only operation
  }
  ```
* **Role prefix:** By default, Spring Security adds "ROLE_" prefix. Use `hasRole("ADMIN")` not `hasRole("ROLE_ADMIN")`.

### 30. Difference between `hasRole` and `hasAuthority`.
| Aspect | `hasRole()` | `hasAuthority()` |
| :--- | :--- | :--- |
| **Prefix** | Automatically adds **"ROLE_"** prefix | **No prefix** added |
| **Usage** | For role-based checks | For general authority/permission checks |
| **Example** | `hasRole("ADMIN")` checks for "ROLE_ADMIN" | `hasAuthority("READ_PRIVILEGE")` checks for "READ_PRIVILEGE" |
| **Flexibility** | Less flexible, role-specific | More flexible, any authority |
| **When to use** | Traditional role-based access control | Fine-grained permission-based control |

### 31. What is method-level security?
* **Annotation-based security** applied directly to methods.
* **Enables:** Fine-grained access control at business layer.
* **Activation:** `@EnableGlobalMethodSecurity(prePostEnabled = true)` (pre-6.0) or `@EnableMethodSecurity()` (6.0+)
* **Annotations:**
  * `@PreAuthorize` - Check before method execution
  * `@PostAuthorize` - Check after method execution
  * `@PreFilter` - Filter collections before method
  * `@PostFilter` - Filter collections after method
  * `@Secured` - Simple role-based check (Spring-specific)

### 32. What is `@PreAuthorize`?
* **Annotation** that checks authorization **before** method execution.
* **Supports:** SpEL (Spring Expression Language) expressions.
* **Examples:**
  ```java
  @PreAuthorize("hasRole('ADMIN')")
  @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
  @PreAuthorize("#userId == authentication.principal.id")
  @PreAuthorize("@securityService.canAccessUser(#userId)")
  ```
* **Activation:** Requires `@EnableMethodSecurity()` or `@EnableGlobalMethodSecurity(prePostEnabled = true)`.

### 33. What is `@PostAuthorize`?
* **Annotation** that checks authorization **after** method execution.
* **Use case:** When authorization decision depends on method's return value.
* **Examples:**
  ```java
  @PostAuthorize("returnObject.owner == authentication.name")
  public Document getDocument(Long id) {
      // returns Document with owner property
  }
  
  @PostAuthorize("hasPermission(returnObject, 'READ')")
  public Salary getSalary(Long employeeId) {
      // check if user can read this salary
  }
  ```

### 34. What is CSRF?
* **Cross-Site Request Forgery** - Attack where authenticated user is tricked into submitting malicious request.
* **Scenario:** User logged into bank site, visits malicious site that submits form to bank.
* **Prevention:** CSRF tokens - unique token per session included in forms/requests.
* **Spring Security:** Enabled by default for state-changing requests (POST, PUT, DELETE, PATCH).

### 35. How does Spring Security handle CSRF?
* **Default behavior:** Enabled for state-changing HTTP methods.
* **Mechanism:**
  * Generates CSRF token, stores in session
  * Includes token in forms (hidden `_csrf` field)
  * Validates token on state-changing requests
* **Configuration:**
  ```java
  http.csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // For APIs
      .ignoringAntMatchers("/api/public/**"); // Exclude某些 endpoints
  ```
* **For REST APIs:** Often disabled (`http.csrf().disable()`) when using stateless authentication (JWT).

### 36. What is CORS?
* **Cross-Origin Resource Sharing** - Mechanism to allow web pages from one origin to access resources from another origin.
* **Same-origin policy:** Browser restricts cross-origin requests.
* **CORS headers:** `Access-Control-Allow-Origin`, `Access-Control-Allow-Methods`, `Access-Control-Allow-Headers`
* **Pre-flight requests:** OPTIONS request before actual request for complex scenarios.

### 37. How do you configure CORS in Spring Security?
* **Global configuration:**
  ```java
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(Arrays.asList("https://domain.com"));
      config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
      config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
      config.setAllowCredentials(true);
      
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return source;
  }
  ```
* **In Security configuration:**
  ```java
  http.cors().configurationSource(corsConfigurationSource());
  ```

### 38. What is session management?
* **Control of HTTP sessions** for authenticated users.
* **Spring Security features:**
  * **Session fixation protection** - Changes session ID after login
  * **Concurrent session control** - Limit simultaneous logins
  * **Session timeout** - Invalidate idle sessions
  * **Session creation policy** - Control when sessions are created
* **Configuration:**
  ```java
  http.sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
      .maximumSessions(1)
      .expiredUrl("/login?expired");
  ```

### 39. What is stateless authentication?
* **Authentication without server-side session storage**.
* **Mechanism:** Token-based (JWT) where all user information is in token.
* **Benefits:** Scalable, works well with REST APIs, no session replication needed.
* **Configuration:**
  ```java
  http.sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  ```
* **Use with:** JWT, OAuth2 tokens.

### 40. How do you handle logout?
* **Default logout:** POST to `/logout` invalidates session, clears SecurityContext, redirects to login page.
* **Custom configuration:**
  ```java
  http.logout()
      .logoutUrl("/custom-logout")  // Default is /logout
      .logoutSuccessUrl("/login?logout")
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID")
      .clearAuthentication(true)
      .addLogoutHandler(new CustomLogoutHandler())
      .logoutSuccessHandler(new CustomLogoutSuccessHandler());
  ```
* **For JWT:** Client discards token (server cannot invalidate JWT unless using token blacklist).

##  Senior / Architect Level

### 41. Explain Spring Security architecture in detail.
* **Core Components:**
  * **SecurityContextHolder** - Storage for SecurityContext
  * **SecurityContext** - Holds Authentication object
  * **Authentication** - Represents principal, credentials, authorities
  * **GrantedAuthority** - Represents roles/permissions
  * **UserDetails** - Core user information
  * **UserDetailsService** - Loads user-specific data
  
* **Filter Chain Architecture:**
  * **DelegatingFilterProxy** - Delegates to Spring-managed filter chain
  * **FilterChainProxy** - Manages SecurityFilterChain list
  * **SecurityFilterChain** - Ordered list of security filters
  
* **Authentication Flow:**
  1. Authentication filters extract credentials
  2. `AuthenticationManager` processes authentication
  3. `AuthenticationProvider` validates credentials
  4. Successful authentication creates `Authentication` object
  5. Object stored in `SecurityContext`
  
* **Authorization Flow:**
  1. `FilterSecurityInterceptor` checks authorization
  2. `AccessDecisionManager` makes decision
  3. `AccessDecisionVoter` votes on access
  4. Decision based on configuration/annotations

### 42. How do you secure REST APIs?
* **Authentication strategies:**
  * **JWT** - Most common for stateless APIs
  * **OAuth2** - For delegated authorization
  * **API Keys** - For server-to-server communication
  
* **Best practices:**
  * Use HTTPS only
  * Implement rate limiting
  * Validate and sanitize all inputs
  * Use proper HTTP status codes
  * Implement comprehensive logging
  * Use API versioning
  
* **Spring Security configuration for REST:**
  ```java
  http
      .csrf().disable()  // Stateless, no CSRF needed
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
          .antMatchers("/api/public/**").permitAll()
          .antMatchers("/api/**").authenticated()
      .and()
      .oauth2ResourceServer(oauth2 -> oauth2.jwt());  // For JWT
  ```

### 43. How do you implement OAuth2?
* **Using Spring Security OAuth2:**
  * **Authorization Server** - Issues tokens (Spring Authorization Server)
  * **Resource Server** - Validates tokens, serves resources
  * **Client** - Applications requesting access
  
* **Grant types:**
  * **Authorization Code** - For web apps with server-side components
  * **Implicit** - Deprecated, not recommended
  * **Resource Owner Password Credentials** - For trusted apps (limited use)
  * **Client Credentials** - For machine-to-machine
  
* **Configuration example (Resource Server):**
  ```java
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
      @Override
      public void configure(HttpSecurity http) throws Exception {
          http.authorizeRequests()
              .antMatchers("/api/**").authenticated();
      }
  }
  ```

### 44. What is OpenID Connect?
* **Identity layer on top of OAuth2** for authentication.
* **Key differences from OAuth2:**
  * OAuth2: **Authorization** (what you can do)
  * OpenID Connect: **Authentication** (who you are)
  
* **Components:**
  * **ID Token** (JWT) - Contains user identity information
  * **UserInfo Endpoint** - Returns user claims
  * **Standard Claims** - Standardized user attributes
  
* **Spring Security support:** Through Spring Security OAuth2 Client

### 45. How do you integrate Spring Security with external IdPs?
* **Identity Providers:** Auth0, Okta, Azure AD, Keycloak, Cognito
* **Integration methods:**
  * **SAML 2.0** - Enterprise SSO
  * **OpenID Connect** - Modern web/mobile apps
  * **LDAP** - Corporate directories
  
* **OpenID Connect configuration:**
  ```yaml
  spring:
    security:
      oauth2:
        client:
          registration:
            okta:
              client-id: ${OKTA_CLIENT_ID}
              client-secret: ${OKTA_CLIENT_SECRET}
              scope: openid, profile, email
          provider:
            okta:
              issuer-uri: https://dev-123456.okta.com/oauth2/default
  ```

### 46. How do you secure microservices?
* **API Gateway pattern:** Centralized security entry point
* **Service-to-service authentication:**
  * **mTLS** (mutual TLS) - Certificate-based
  * **JWT with shared secret/private key**
  * **OAuth2 Client Credentials grant**
  
* **Distributed security challenges:**
  * Token propagation across services
  * Centralized vs decentralized authorization
  * Security context propagation
  
* **Spring Cloud Security:** Provides solutions for:
  * OAuth2 propagation
  * Resource server configuration
  * Security context propagation

### 47. How do you handle token validation and refresh?
* **Token validation:**
  * **Signature verification** for JWT
  * **Expiration check**
  * **Issuer/audience validation**
  * **Custom claims validation**
  
* **Refresh tokens:**
  * Long-lived token used to obtain new access tokens
  * Stored securely (HTTP-only cookie, secure storage)
  * Rotated on use (refresh token rotation)
  
* **Spring Security OAuth2:**
  ```java
  @Bean
  public JwtDecoder jwtDecoder() {
      return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
  }
  ```

### 48. How do you design fine-grained access control?
* **Attribute-Based Access Control (ABAC):**
  * Policies based on attributes (user, resource, environment, action)
  * **Example:** "User can access document if department matches"
  
* **Implementation strategies:**
  * **Custom `AccessDecisionVoter`**
  * **Spring Security ACL** (Access Control Lists)
  * **Custom permission evaluator**
  * **External policy engine** (OPA - Open Policy Agent)
  
* **Example with custom permission evaluator:**
  ```java
  @PreAuthorize("@permissionEvaluator.hasPermission(#documentId, 'Document', 'READ')")
  public Document getDocument(String documentId) {
      // ...
  }
  ```

### 49. How do you implement multi-tenant security?
* **Tenant isolation strategies:**
  * **Separate database per tenant** - Highest isolation
  * **Separate schema per tenant** - Medium isolation
  * **Shared database with tenant discriminator** - Lowest isolation
  
* **Authentication/Authorization:**
  * Tenant-aware `UserDetailsService`
  * Tenant context in security context
  * Tenant-based URL patterns
  * Tenant-specific roles/permissions
  
* **Spring Security Multi-tenancy:**
  * Custom `AuthenticationProvider`
  * Tenant-aware filter
  * Database per tenant with separate `DataSource`

### 50. How do you prevent security vulnerabilities?
* **OWASP Top 10 mitigation:**
  1. **Injection** - Use prepared statements, parameterized queries
  2. **Broken Authentication** - Strong password policies, MFA, secure session management
  3. **Sensitive Data Exposure** - Encryption at rest and transit, proper key management
  4. **XXE** - Disable XML external entity processing
  5. **Broken Access Control** - Principle of least privilege, server-side validation
  6. **Security Misconfiguration** - Regular updates, minimal configurations
  7. **XSS** - Output encoding, Content Security Policy
  8. **Insecure Deserialization** - Validate serialized objects, use safe formats
  9. **Using Components with Known Vulnerabilities** - Dependency scanning, regular updates
  10. **Insufficient Logging & Monitoring** - Comprehensive logging, real-time monitoring

### 51. How do you implement rate limiting?
* **Purpose:** Prevent abuse, DDoS protection, fair usage
* **Implementation strategies:**
  * **API Gateway level** - Kong, AWS API Gateway
  * **Application level** - Spring Boot Starter for Resilience4j
  * **Cache-based** - Redis with atomic operations
  
* **Resilience4j example:**
  ```java
  @RateLimiter(name = "serviceA", fallbackMethod = "rateLimiterFallback")
  public ResponseEntity<String> apiMethod() {
      return ResponseEntity.ok("Success");
  }
  ```

### 52. How do you handle brute-force attacks?
* **Detection and prevention:**
  * **Account lockout** after failed attempts
  * **Progressive delays** between attempts
  * **CAPTCHA** after threshold
  * **IP-based blocking**
  
* **Spring Security implementation:**
  ```java
  @Component
  public class AuthenticationFailureListener implements 
          ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
      
      @Autowired
      private LoginAttemptService loginAttemptService;
      
      @Override
      public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
          String username = event.getAuthentication().getName();
          loginAttemptService.loginFailed(username);
      }
  }
  ```

### 53. How do you implement API Gateway security?
* **API Gateway responsibilities:**
  * Authentication/authorization
  * Rate limiting
  * Request/response transformation
  * Caching
  * Load balancing
  * Circuit breaking
  
* **Security patterns:**
  * **Token validation** at gateway
  * **Security context propagation** to services
  * **Centralized CORS/CSRF** handling
  * **IP whitelisting/blacklisting**
  
* **Spring Cloud Gateway:**
  ```yaml
  spring:
    cloud:
      gateway:
        routes:
        - id: secure-service
          uri: http://service:8080
          predicates:
          - Path=/api/**
          filters:
          - TokenRelay=
          - RateLimit=
  ```

### 54. How do you manage secrets securely?
* **Never store in:**
  * Version control
  * Configuration files in plaintext
  * Environment variables (for sensitive production secrets)
  
* **Secret management solutions:**
  * **HashiCorp Vault** - Industry standard
  * **AWS Secrets Manager** / **Azure Key Vault** - Cloud provider solutions
  * **Spring Cloud Config Server** with encryption
  * **Kubernetes Secrets** (base64 encoded, not encrypted by default)
  
* **Spring Vault integration:**
  ```java
  @Configuration
  @VaultPropertySource("secret/database")
  public class VaultConfig extends AbstractVaultConfiguration {
      // Vault template for dynamic secrets
  }
  ```

### 55. How do you handle auditing and logging?
* **Security auditing requirements:**
  * **Who** did **what**, **when**, from **where**
  * Authentication successes/failures
  * Authorization failures
  * Sensitive data access
  
* **Spring Security auditing:**
  ```java
  @Component
  public class SecurityAuditAspect {
      
      @AfterReturning(
          pointcut = "execution(* com.example.service.*.*(..))",
          returning = "result")
      public void auditMethodAccess(JoinPoint joinPoint, Object result) {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          // Log: user, method, timestamp, result
      }
  }
  ```
  
* **Structured logging:** JSON format for log aggregation systems

### 56. What are common Spring Security anti-patterns?
* **Hardcoding credentials** in configuration
* **Using weak password encoders** (MD5, SHA-1, plaintext)
* **Ignoring CSRF protection** for state-changing operations
* **Overly permissive CORS** configurations
* **Missing HTTPS** in production
* **Insecure session management** (no timeout, no fixation protection)
* **Logging sensitive data** (passwords, tokens, PII)
* **Role-based checks without context** (no resource-level authorization)
* **No rate limiting** on authentication endpoints
* **Not validating JWT signatures** properly

### 57. How do you test Spring Security configurations?
* **Testing strategies:**
  * **Unit tests** for security utilities, validators
  * **Integration tests** with `@SpringBootTest`
  * **MockMvc tests** with security context
  * **Test slices** (`@WebMvcTest`, `@DataJpaTest`)
  
* **Spring Security Test support:**
  ```java
  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testAdminEndpoint() throws Exception {
      mockMvc.perform(get("/admin"))
             .andExpect(status().isOk());
  }
  
  @Test
  @WithAnonymousUser
  public void testUnauthenticatedAccess() throws Exception {
      mockMvc.perform(get("/secure"))
             .andExpect(status().isUnauthorized());
  }
  ```

### 58. How do you handle backward compatibility?
* **API versioning:** Include security changes in versioned APIs
* **Graceful deprecation:** 
  * Support old authentication methods during transition
  * Communicate deprecation timeline
  * Provide migration guides
  
* **Token migration strategy:**
  1. Issue new format tokens
  2. Accept both old and new tokens during transition
  3. Gradually phase out old tokens
  4. Update clients systematically

### 59. How do you manage zero-downtime security changes?
* **Blue-green deployment:** Deploy new security configuration to inactive environment
* **Feature flags:** Enable new security features gradually
* **Certificate rotation:** Overlap old and new certificates
* **Secrets rotation:** Automated rotation with no service interruption
* **Configuration refresh:** External configuration with refresh capabilities

* **Spring Cloud Config with refresh:**
  ```java
  @RefreshScope
  @Configuration
  public class SecurityConfig {
      // Configuration refreshed without restart
  }
  ```

### 60. When should you NOT use Spring Security?
* **Simple applications** with basic security needs (consider simpler alternatives)
* **Extremely high-performance requirements** where overhead is unacceptable
* **Non-Spring applications** (use framework-native security)
* **When team lacks Spring Security expertise** and timeline is tight
* **Legacy systems** with custom security already implemented
* **Microservices using service mesh** (Istio, Linkerd) for network-level security
* **When using alternative JVM frameworks** (Micronaut, Quarkus have built-in security)
* **Serverless functions** where platform provides security (AWS Lambda, Azure Functions)

* **Alternatives to consider:**
  * **Apache Shiro** - Simpler, less opinionated
  * **Java EE Security** - For Java EE/Jakarta EE applications
  * **Custom security** - For unique requirements not met by frameworks
  * **Service mesh security** - For infrastructure-level security in microservices