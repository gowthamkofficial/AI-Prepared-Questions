# Java Interview Questions & Answers Guide
## Topic: EXCEPTION HANDLING (Questions 91-108)
### For 2-Year Experienced Java Backend Developers

---

### 91. Why is Exception Handling Important?

**Expected Answer (2-Year Level):**
Exception handling lets programs gracefully handle unexpected conditions, maintain control flow, and provide useful error information. It separates normal logic from error-handling logic and avoids program crashes.

**Key Theoretical Concepts:**
- Try-catch-finally
- Checked vs unchecked exceptions
- Propagation and stack traces

**Interviewer Expectation:**
Should explain practical benefits: robustness, readability, centralized handlers.

**Red Flags:**
- Relying solely on return codes instead of exceptions

**Depth Expected:** Intermediate

---

### 92. What design pattern is used to implement Exception handling Features in most languages?

**Expected Answer (2-Year Level):**
The Chain of Responsibility pattern describes exception propagation through call stack handlers where an exception can be handled at multiple levels.

**Key Theoretical Concepts:**
- Propagation up the call stack
- Handlers at different abstraction levels

**Interviewer Expectation:**
Should recognize conceptual similarity; not expecting deep pattern mapping.

**Depth Expected:** Intermediate

---

### 93. What is the need for finally block?

**Expected Answer (2-Year Level):**
`finally` executes regardless of whether an exception is thrown, allowing cleanup (close streams, release resources). It's guaranteed except in rare termination scenarios (see Q94).

**Key Theoretical Concepts:**
- Resource cleanup
- Try-with-resources preferred for AutoCloseable

**Interviewer Expectation:**
Should cite resource release examples.

**Red Flags:**
- Using finally for main logic instead of cleanup

**Depth Expected:** Intermediate

---

### 94. In what scenarios is code in finally not executed?

**Expected Answer (2-Year Level):**
`finally` may not run if:
- JVM exits via `System.exit()` before finally
- Thread is killed abruptly
- JVM crashes/Power failure
Also if `finally` itself throws an exception, that can mask previous exceptions.

**Key Theoretical Concepts:**
- Unusual termination and exception masking

**Interviewer Expectation:**
Should list common cases and explain that normally finally runs.

**Depth Expected:** Intermediate

---

### 95. Will finally be executed in the program below?

(Depends on snippet; typical question checks return in try/catch vs finally behavior.)

**Expected Answer (2-Year Level):**
Yes — even if `try` or `catch` has a `return`, `finally` will still execute before returning. But if `System.exit()` is called in try/catch, finally won't run.

**Key Theoretical Concepts:**
- Return and finally interplay

**Interviewer Expectation:**
Should show understanding that finally executes before method return.

**Depth Expected:** Intermediate

---

### 96. Is try without a catch is allowed?

**Expected Answer (2-Year Level):**
Yes, `try` without `catch` is allowed only if it has a `finally` block. Example: `try { /* ... */ } finally { /* cleanup */ }`.

**Key Theoretical Concepts:**
- Try-finally pattern

**Interviewer Expectation:**
Should know syntax rules.

**Depth Expected:** Beginner

---

### 97. Is try without catch and finally allowed?

**Expected Answer (2-Year Level):**
No — `try` must be followed by either `catch` or `finally` (or both). A bare `try` is illegal.

**Key Theoretical Concepts:**
- Syntax constraints

**Interviewer Expectation:**
Should know correct blocks.

**Depth Expected:** Beginner

---

### 98. Can you explain the hierarchy of Exception Handling Classes?

**Expected Answer (2-Year Level):**
`java.lang.Throwable` is the root. It has two main subclasses: `Error` (serious problems JVM throws) and `Exception`. `Exception` is divided into checked exceptions (subclasses other than `RuntimeException`) and unchecked exceptions (`RuntimeException` and its subclasses).

**Key Theoretical Concepts:**
- Throwable → Error / Exception → RuntimeException (unchecked)
- Checked exceptions require `throws` or handling

**Interviewer Expectation:**
Should draw or describe hierarchy and implications for handling.

**Red Flags:**
- Thinking `Error` should be caught routinely

**Depth Expected:** Intermediate

---

### 99. What is the difference between Error and Exception?

**Expected Answer (2-Year Level):**
`Error` represents serious problems beyond application control (OutOfMemoryError, StackOverflowError) and usually shouldn't be caught. `Exception` represents conditions an application might handle; divided into checked and unchecked.

**Key Theoretical Concepts:**
- Recoverable vs non-recoverable conditions

**Interviewer Expectation:**
Should know examples and best practices.

**Depth Expected:** Intermediate

---

### 100. What is the difference between Checked Exceptions and Unchecked Exceptions?

**Expected Answer (2-Year Level):**
Checked exceptions (subclasses of `Exception` excluding `RuntimeException`) must be declared or handled at compile time. Unchecked exceptions (`RuntimeException` and subclasses) do not need to be declared; they indicate programming errors (NPE, IAE).

**Key Theoretical Concepts:**
- Compile-time enforcement
- API design trade-offs

**Interviewer Expectation:**
Should list examples and explain when to use each.

**Red Flags:**
- Misclassifying common exceptions

**Depth Expected:** Intermediate

---

### 101. How do you throw an exception from a method?

**Expected Answer (2-Year Level):**
Use the `throw` keyword with an exception object: `throw new IOException("msg");`. For checked exceptions, declare with `throws` on method signature or handle within method.

**Key Theoretical Concepts:**
- `throw` vs `throws`

**Interviewer Expectation:**
Should show example and mention checked/unchecked differences.

**Depth Expected:** Beginner

---

### 102. What happens when you throw a Checked Exception from a method?

**Expected Answer (2-Year Level):**
The method must either handle it in a `try-catch` block or declare it with `throws` so callers are aware. Otherwise code won't compile.

**Key Theoretical Concepts:**
- Propagation and compile-time enforcement

**Interviewer Expectation:**
Should explain compile-time requirement.

**Depth Expected:** Beginner

---

### 103. WHAT ARE THE OPTIONS YOU HAVE TO ELIMINATE COMPILATION ERRORS WHEN HANDLING CHECKED EXCEPTIONS?

**Expected Answer (2-Year Level):**
Options:
- Catch the exception and handle it (`try-catch`)
- Declare the exception in the method signature with `throws` and let caller handle
- Wrap in an unchecked exception and throw that (use carefully)

**Key Theoretical Concepts:**
- API design and exception translation

**Interviewer Expectation:**
Should discuss trade-offs and when each is appropriate.

**Depth Expected:** Intermediate

---

### 104. HOW DO YOU CREATE A CUSTOM EXCEPTION?

**Expected Answer (2-Year Level):**
Extend `Exception` (checked) or `RuntimeException` (unchecked) and provide constructors:
```java
public class MyException extends Exception {
    public MyException(String msg) { super(msg); }
}
```

**Key Theoretical Concepts:**
- Checked vs unchecked custom exceptions
- Providing constructors and serialVersionUID

**Interviewer Expectation:**
Should show example and explain when to use custom exceptions for domain clarity.

**Depth Expected:** Intermediate

---

### 105. HOW DO YOU HANDLE MULTIPLE EXCEPTION TYPES WITH SAME EXCEPTION HANDLING BLOCK?

**Expected Answer (2-Year Level):**
Use multi-catch (Java 7+):
```java
try {
    // code
} catch (IOException | SQLException ex) {
    // handle both
}
```
Or catch a common superclass. Avoid catching `Exception` broadly unless necessary.

**Key Theoretical Concepts:**
- Multi-catch syntax
- Exception hierarchy

**Interviewer Expectation:**
Should show multi-catch and mention limitations (effectively final exception var).

**Depth Expected:** Intermediate

---

### 106. CAN YOU EXPLAIN ABOUT TRY WITH RESOURCES?

**Expected Answer (2-Year Level):**
Try-with-resources (Java 7+) automatically closes resources implementing `AutoCloseable` at the end of the block. Syntax:
```java
try (BufferedReader br = new BufferedReader(...)) {
    // use br
}
```
This reduces boilerplate and makes resource handling safer.

**Key Theoretical Concepts:**
- AutoCloseable interface
- Automatic suppression of exceptions (suppressed exceptions accessible via `getSuppressed()`)

**Interviewer Expectation:**
Should know syntax, benefits, and suppressed-exception behavior.

**Depth Expected:** Intermediate

---

### 107. HOW DOES TRY WITH RESOURCES WORK?

**Expected Answer (2-Year Level):**
Resources declared in the try parentheses are implicitly closed in reverse order when the block ends. If exceptions are thrown both in block and in close, the closing exceptions are added as suppressed exceptions to the primary exception.

**Key Theoretical Concepts:**
- Deterministic resource cleanup
- Suppressed exceptions and `Throwable.addSuppressed()`

**Interviewer Expectation:**
Should explain suppression and the order of closing.

**Depth Expected:** Intermediate

---

### 108. CAN YOU EXPLAIN A FEW EXCEPTION HANDLING BEST PRACTICES?

**Expected Answer (2-Year Level):**
Best practices:
- Catch the most specific exception possible
- Don’t swallow exceptions silently (avoid empty catch)
- Use checked exceptions for recoverable conditions; unchecked for programming errors
- Use try-with-resources for AutoCloseable resources
- Add helpful context to exception messages
- Avoid using exceptions for normal control flow
- Clean up resources in finally or try-with-resources

**Key Theoretical Concepts:**
- Maintainable error handling and observability

**Interviewer Expectation:**
Should be aware of readable and maintainable error handling patterns used in backend systems.

**Red Flags:**
- Blanket catch of `Exception` and ignoring it
- Overusing exceptions for flow control

**Depth Expected:** Intermediate to practical

---

End of EXCEPTION HANDLING (Questions 91-108)
