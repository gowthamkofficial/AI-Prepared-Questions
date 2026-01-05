# EXCEPTION HANDLING ANSWERS

---

## 91. Why is exception handling important?

###  Answer:

* **Prevents program termination** when unexpected situations occur
* **Separates error-handling code** from business logic (cleaner code)
* Provides **meaningful error messages** to users and developers
* Allows **graceful recovery** or cleanup before exiting
* Enables better debugging with **stack traces**
* Maintains application stability and user experience

### Theoretical Keywords:

**Program stability**, **Error recovery**, **Code separation**, **User experience**, **Graceful degradation**, **Debugging aid**

---

## 92. What design pattern is used to implement exception handling features in most languages?

###  Answer:

* **Chain of Responsibility** pattern
* Exceptions **propagate up the call stack** until caught by an appropriate handler
* Each method in the call chain can either handle the exception or pass it up
* Similar to how requests pass through a chain of handlers
* Java's exception hierarchy enables this pattern naturally

### Theoretical Keywords:

**Chain of Responsibility pattern**, **Call stack propagation**, **Handler chain**, **Exception bubbling**, **Pattern implementation**

---

## 93. What is the need for a finally block?

###  Answer:

* **Guarantees execution** of cleanup code regardless of whether an exception occurs
* Used for **resource cleanup** (closing files, database connections, network sockets)
* Executes even if the try block has `return`, `continue`, or `break` statements
* Ensures the program doesn't **leak resources**
* **Example:** Always close a file in the `finally` block

### Theoretical Keywords:

**Cleanup guarantee**, **Resource management**, **Deterministic execution**, **No resource leaks**, **Return statement override**

---

## 94. In what scenarios is code in finally not executed?

###  Answer:

* **JVM exits:** `System.exit(0)` in try or catch block
* **Infinite loop/blocking:** Thread stuck in try/catch
* **System crash:** Power failure, OS crash
* **Daemon thread death:** When only daemon threads remain
* **halt() method:** `Runtime.getRuntime().halt()`
* **Killing the process:** From task manager/terminal

### Theoretical Keywords:

**System.exit()**, **JVM termination**, **Process kill**, **Fatal errors**, **Daemon threads**, **Unrecoverable states**

---

## 95. Will finally be executed in the program below?

*(Assuming: `try { return; } finally { System.out.println("finally"); }`)*

###  Answer:

* **Yes**, `finally` executes before the method returns
* The `finally` block executes even when the `try` block has a `return` statement
* The return value is calculated and stored, then `finally` executes, then the method returns
* If `finally` also has a `return`, it **overrides** the try's return

### Theoretical Keywords:

**Return statement interaction**, **Execution guarantee**, **Value calculation timing**, **Override possibility**

---

## 96. Is try without a catch allowed?

###  Answer:

* **Yes**, but only with a **finally** block
* **Syntax:** `try { ... } finally { ... }`
* Used when you need cleanup but don't want to handle the exception locally
* Exception **propagates to caller** after `finally` executes
* Common for resource cleanup where the exception should be handled by the caller

### Theoretical Keywords:

**Try-finally only**, **Cleanup without handling**, **Exception propagation**, **Resource management pattern**

---

## 97. Is try without catch and finally allowed?

###  Answer:

* **No**, a `try` block must be followed by either `catch`, `finally`, or both
* `try { }` alone is a **compile-time error**
* **Minimum:** `try-catch` or `try-finally` or `try-catch-finally`
* **Java 7+:** `try-with-resources` is also valid

### Theoretical Keywords:

**Syntax requirement**, **Compile-time error**, **Minimum block requirement**, **Language specification**

---

## 98. Can you explain the hierarchy of exception handling classes?

###  Answer:

* **Throwable (root)**
* **Error (unchecked):** `VirtualMachineError`, `OutOfMemoryError`, `StackOverflowError`
* **Exception**
* **RuntimeException (unchecked):** `NullPointerException`, `ArithmeticException`
* **Checked Exceptions:** `IOException`, `SQLException`, `ClassNotFoundException`





### Theoretical Keywords:

**Throwable hierarchy**, **Error vs Exception**, **Checked vs unchecked**, **RuntimeException**, **Inheritance tree**

---

## 99. What is the difference between Error and Exception?

###  Answer:

* **Errors:** Serious system-level problems that applications shouldn't try to catch (e.g., `OutOfMemoryError`). Usually **unrecoverable**.
* **Exceptions:** Conditions that applications should catch and handle (e.g., `IOException`). Can often be **recovered from**.
* Errors are always unchecked; Exceptions can be checked or unchecked.

### Theoretical Keywords:

**System-level vs application-level**, **Recoverability**, **JVM state**, **Checked nature**, **Recovery strategy**

---

## 100. What is the difference between checked and unchecked exceptions?

###  Answer:

* **Checked Exceptions:** Compiler enforces handling; must be caught or declared with `throws`. Represent **recoverable** conditions (e.g., `FileNotFoundException`).
* **Unchecked Exceptions:** Subclasses of `RuntimeException` or `Error`. Usually represent **programming errors** or bugs (e.g., `NullPointerException`). No compiler enforcement.

### Theoretical Keywords:

**Compiler enforcement**, **Declaration requirement**, **Recoverable conditions**, **Programming errors**, **RuntimeException**

---

## 101. How do you throw an exception from a method?

###  Answer:

* Use the **throw** keyword with an exception instance
* For checked exceptions, you must declare them in the method signature using **throws**
* **Example:**

```java
public void checkAge(int age) {
    if (age < 18) throw new IllegalArgumentException("Too young");
}

```

### Theoretical Keywords:

**throw keyword**, **throws declaration**, **Exception instantiation**, **Checked exception requirement**, **Exception chaining**

---

## 102. What happens when you throw a checked exception from a method?

###  Answer:

* You **must declare it** in the method signature with a `throws` clause
* The **caller** must then either catch it or declare it in their own signature
* The compiler enforces this at compile time to force explicit error handling design

### Theoretical Keywords:

**Compiler enforcement**, **throws declaration**, **Caller responsibility**, **Explicit error handling**, **Design forcing**

---

## 103. What are the options you have to eliminate compilation errors when handling checked exceptions?

###  Answer:

1. **Catch the exception:** Handle it locally with a `try-catch` block
2. **Declare in throws:** Add the exception to the method signature to pass responsibility to the caller
3. **Wrap in RuntimeException:** Convert the checked exception into an unchecked one (use with caution)

### Theoretical Keywords:

**Try-catch block**, **throws declaration**, **Exception wrapping**, **Responsibility propagation**, **AutoCloseable**

---

## 104. How do you create a custom exception?

###  Answer:

* **Extend Exception** (for checked) or **RuntimeException** (for unchecked)
* Provide constructors that call `super()` to pass the message and cause
* **Example:**

```java
public class MyException extends Exception {
    public MyException(String msg) { super(msg); }
}

```

### Theoretical Keywords:

**Extend Exception/RuntimeException**, **Constructor overloading**, **Naming convention**, **Exception chaining**, **Custom messaging**

---

## 105. How do you handle multiple exception types with the same exception handling block?

###  Answer:

* Use the **Multi-catch** feature (Java 7+) using the pipe (`|`) operator
* **Syntax:** `catch (IOException | SQLException e) { ... }`
* Note: The exceptions in the multi-catch block cannot have a parent-child inheritance relationship

### Theoretical Keywords:

**Multi-catch syntax**, **Java 7 feature**, **Disjoint exceptions**, **Same handling logic**, **Pipe (|) operator**

---

## 106. Can you explain try-with-resources?

###  Answer:

* A Java 7+ feature for **automatic resource management**
* Resources that implement the **AutoCloseable** interface are automatically closed at the end of the block
* Eliminates the need for explicit `finally` blocks for closing files or streams
* **Example:** `try (BufferedReader br = new BufferedReader(...)) { ... }`

### Theoretical Keywords:

**Java 7 feature**, **AutoCloseable interface**, **Automatic resource management**, **No explicit finally**, **Clean code**

---

## 107. How does try-with-resources work?

###  Answer:

* Resources are closed in the **reverse order** of their declaration
* The `close()` method is called automatically even if an exception occurs
* If exceptions occur in both the `try` block and the `close()` method, the `try` exception is primary, and the `close()` exception is **suppressed**

### Theoretical Keywords:

**AutoCloseable.close()**, **Reverse closing order**, **Exception suppression**, **getSuppressed() method**, **Compiler transformation**

---

## 108. Can you explain a few exception handling best practices?

###  Answer:

* **Catch specific exceptions first**, general ones last
* **Don't swallow exceptions** (never leave a catch block empty)
* **Throw early, catch late** (handle at the appropriate abstraction level)
* Use **try-with-resources** for resource management
* Include the **original cause** when wrapping exceptions to preserve the stack trace

### Theoretical Keywords:

**Specific before general**, **No silent swallowing**, **Resource cleanup**, **Appropriate level handling**, **Exception chaining**, **Performance consideration**, **Documentation**, **Domain exceptions**, **Cleanup guarantee**

---
