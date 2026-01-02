# Spring Boot Interview Questions – Service Company Focus

This README contains **commonly asked Spring Boot interview questions** for **Infosys, Wipro, Cognizant, Capgemini (Service-Based Companies)**.

The questions are **experience-based**, **concept-driven**, and reflect **real interview patterns**.

---

## BEGINNER LEVEL (0–2 Years)

### Spring Basics

* What is Spring Framework?
* Why do we use Spring?
* What problems does Spring solve?
* What is IoC (Inversion of Control)?
* What is Dependency Injection?
* Types of Dependency Injection
* Difference between tight coupling and loose coupling
* What is a Spring Bean?
* What is ApplicationContext?
* Difference between BeanFactory and ApplicationContext

### Spring Boot Basics

* What is Spring Boot?
* Why Spring Boot is preferred over Spring?
* What are Spring Boot starters?
* What is auto-configuration?
* What is `@SpringBootApplication`?
* What is embedded Tomcat?
* Can we change embedded server in Spring Boot?
* What is `application.properties`?
* Difference between `application.properties` and `application.yml`

### Annotations (Very Important)

* What is `@Component`?
* Difference between `@Component`, `@Service`, `@Repository`
* What is `@Autowired`?
* What is `@Configuration`?
* What is `@Bean`?
* Difference between `@Bean` and `@Component`

---

## INTERMEDIATE LEVEL (2–4 Years)

### REST API

* What is REST?
* What is RESTful web service?
* Difference between `@Controller` and `@RestController`
* Difference between `@RequestParam` and `@PathVariable`
* What is `@RequestBody`?
* What is `ResponseEntity`?
* What are HTTP methods?
* What are HTTP status codes?
* Difference between PUT and PATCH
* How do you design REST APIs?

### Spring Data JPA

* What is ORM?
* What is Hibernate?
* What is Spring Data JPA?
* Difference between JPA and Hibernate
* Difference between `CrudRepository` and `JpaRepository`
* What is an Entity?
* What is `@Id` and `@GeneratedValue`?
* What is `@Transactional`?
* Difference between `save()` and `saveAndFlush()`

### Relationships

* Explain OneToOne mapping
* Explain OneToMany and ManyToOne
* Explain ManyToMany mapping
* What is `@JoinColumn`?
* What is cascade?
* Types of Cascade
* FetchType.LAZY vs FetchType.EAGER

---

## ADVANCED LEVEL (4+ Years)

### JPA & Hibernate Internals

* What is the N+1 select problem?
* How do you solve N+1 problem?
* What is JPQL?
* Difference between JPQL and Native Query
* What is DTO projection?
* Interface-based vs Class-based projections
* What is pagination?
* How pagination works internally?
* What is optimistic locking?
* What is pessimistic locking?

### Exception Handling

* How do you handle exceptions in Spring Boot?
* What is `@ExceptionHandler`?
* What is `@ControllerAdvice`?
* Difference between `@ExceptionHandler` and `@ControllerAdvice`
* How do you return a common error response?
* How do you handle validation errors?

### Validation

* What is `@Valid`?
* What is `@Validated`?
* Difference between `@NotNull`, `@NotEmpty`, `@NotBlank`
* How do you handle validation globally?

---

## SPRING SECURITY (Frequently Asked)

* What is Spring Security?
* Authentication vs Authorization
* What is JWT?
* Explain JWT flow
* Where is JWT stored on client side?
* What is `SecurityFilterChain`?
* What is PasswordEncoder?
* How role-based authorization works?
* What is CSRF?
* How do you secure REST APIs?

---

## SPRING BOOT INTERNALS (Service Company Favorite)

* How does Spring Boot application start?
* Explain auto-configuration flow
* What happens internally when you hit a REST API?
* How dependency injection works internally?
* How Spring identifies beans?
* What is classpath scanning?
* How `@ComponentScan` works?

---

##  PROFILES, CONFIG & DEPLOYMENT

* What are Spring profiles?
* Why profiles are used?
* Difference between dev and prod configuration
* What is `@Profile`?
* What is `@Value`?
* What is `@ConfigurationProperties`?
* How do you externalize configuration?

---

## 🟤 TESTING (Basic Expectation)

* What is unit testing?
* Difference between unit and integration testing
* What is `@SpringBootTest`?
* What is `@WebMvcTest`?
* What is MockMvc?
* What is Mockito?

---

## VERY IMPORTANT QUESTIONS (Infosys / Wipro / CTS / Capgemini)

* Explain your Spring Boot project
* What annotations did you use and why?
* How did you handle exceptions?
* How did you design REST APIs?
* How did you connect Spring Boot with database?
* How did you handle validations?
* How did you secure APIs?
* What challenges did you face in your project?
* How did you optimize performance?
* What will you improve if you rewrite the project?

---

## FINAL INTERVIEW TIP

> Service companies focus more on **clarity of concepts**, **project explanation**, and **real-time understanding** rather than deep framework internals.

If you can explain:

* Flow of request
* Why you used each annotation
* How errors are handled

