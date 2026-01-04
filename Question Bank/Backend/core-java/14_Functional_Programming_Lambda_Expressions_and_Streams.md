# **FUNCTIONAL PROGRAMMING ANSWERS**

## **Functional Programming (Questions 208-220)**

### **208. What is functional programming?**
**Interviewer Answer:**
- **Paradigm**: Programming style that treats computation as evaluation of mathematical functions
- **Core Principles**:
  1. **Immutability**: Data doesn't change, new data is created
  2. **Pure Functions**: Same input → same output, no side effects
  3. **First-class Functions**: Functions can be passed as arguments, returned from functions
  4. **Higher-order Functions**: Functions that take/return other functions
  5. **Declarative Style**: Focus on **what** to do, not **how** to do it

**Java's Functional Programming Features** (since Java 8):
- Lambda Expressions
- Stream API
- Functional Interfaces
- Method References
- Optional class

**Contrast with Imperative Programming**:
```java
// Imperative (HOW)
List<Integer> squares = new ArrayList<>();
for (int num : numbers) {
    squares.add(num * num);
}

// Functional (WHAT)
List<Integer> squares = numbers.stream()
    .map(num -> num * num)
    .collect(Collectors.toList());
```

**Theoretical Keywords:**
Immutability, Pure functions, First-class functions, Higher-order functions, Declarative programming, Side-effect free

### **209. Can you give an example of functional programming?**
**Interviewer Answer:**
**Example 1: Filtering and Transforming Data**
```java
import java.util.*;
import java.util.stream.*;

public class FunctionalExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Imperative approach
        List<Integer> evenSquaresImperative = new ArrayList<>();
        for (Integer num : numbers) {
            if (num % 2 == 0) {
                evenSquaresImperative.add(num * num);
            }
        }
        
        // Functional approach
        List<Integer> evenSquaresFunctional = numbers.stream()
            .filter(num -> num % 2 == 0)     // Pure function
            .map(num -> num * num)           // Pure function  
            .collect(Collectors.toList());   // Terminal operation
        
        System.out.println("Imperative: " + evenSquaresImperative);
        System.out.println("Functional: " + evenSquaresFunctional);
    }
}
```

**Example 2: Processing Collection with Pure Functions**
```java
// Pure function - no side effects, same input → same output
public static int add(int a, int b) {
    return a + b;
}

// Impure function - has side effect (printing)
public static int addAndPrint(int a, int b) {
    int result = a + b;
    System.out.println(result);  // Side effect!
    return result;
}

// Higher-order function - takes function as parameter
public static int operate(int a, int b, BinaryOperator<Integer> operation) {
    return operation.apply(a, b);
}

// Usage
int sum = operate(5, 3, (x, y) -> x + y);      // 8
int product = operate(5, 3, (x, y) -> x * y);  // 15
```

**Example 3: Immutable Data Processing**
```java
// Immutable data class (Java 16+ records)
record Person(String name, int age) {}

List<Person> people = Arrays.asList(
    new Person("Alice", 30),
    new Person("Bob", 25),
    new Person("Charlie", 35)
);

// Functional pipeline - creates new data, doesn't modify original
List<String> adultNames = people.stream()
    .filter(p -> p.age() >= 30)          // Pure filter
    .map(Person::name)                   // Pure transformation
    .sorted()                            // Pure sorting
    .collect(Collectors.toList());       // New collection

System.out.println(adultNames);  // [Alice, Charlie]
// Original 'people' list unchanged (immutable processing)
```

**Theoretical Keywords:**
Pure functions, Stream pipeline, Filter-map-reduce, Immutable data, Higher-order functions, Declarative syntax

### **210. What is a stream?**
**Interviewer Answer:**
- **Stream**: Sequence of elements supporting sequential and parallel aggregate operations
- **Not a data structure**: Doesn't store elements, but conveys them from a source
- **Characteristics**:
  - **Lazy evaluation**: Operations are only executed when needed
  - **Can be infinite**: Can work with infinite data sources
  - **Consumable**: Can only be traversed once
  - **Parallelizable**: Can process elements in parallel easily

**Stream vs Collection**:
| **Collections** | **Streams** |
|----------------|-------------|
| Store elements | Convey elements |
| Eager evaluation | Lazy evaluation |
| Can be reused | Single-use |
| External iteration | Internal iteration |
| Memory stores all | Can process infinite |

**Stream Creation Examples**:
```java
// From Collection
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream1 = list.stream();

// From Array
String[] array = {"a", "b", "c"};
Stream<String> stream2 = Arrays.stream(array);

// Stream.of()
Stream<String> stream3 = Stream.of("a", "b", "c");

// Infinite streams
Stream<Integer> infinite = Stream.iterate(0, n -> n + 1);
Stream<Double> randoms = Stream.generate(Math::random);

// Primitive streams
IntStream intStream = IntStream.range(1, 10);
```

**Stream Pipeline**:
```java
List<Integer> result = list.stream()          // Source
    .filter(s -> s.length() > 3)              // Intermediate operation (lazy)
    .map(String::length)                      // Intermediate operation (lazy)
    .sorted()                                 // Intermediate operation (lazy)
    .collect(Collectors.toList());            // Terminal operation (eager)
```

**Theoretical Keywords:**
Sequence of elements, Lazy evaluation, Single traversal, Internal iteration, Parallel processing, Pipeline operations

### **211. Explain streams with an example.**
**Interviewer Answer:**
**Complete Stream Example**:
```java
import java.util.*;
import java.util.stream.*;

public class StreamExample {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Sales", 60000),
            new Employee("Charlie", "Engineering", 85000),
            new Employee("Diana", "Marketing", 55000),
            new Employee("Eve", "Engineering", 90000),
            new Employee("Frank", "Sales", 65000)
        );
        
        // Example 1: Filter engineers and get their names
        List<String> engineerNames = employees.stream()
            .filter(emp -> "Engineering".equals(emp.getDepartment()))
            .map(Employee::getName)
            .collect(Collectors.toList());
        
        System.out.println("Engineers: " + engineerNames);
        
        // Example 2: Average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        
        System.out.println("Average salaries: " + avgSalaryByDept);
        
        // Example 3: Highest paid employee
        Employee highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary))
            .orElse(null);
        
        System.out.println("Highest paid: " + highestPaid);
        
        // Example 4: Total salary expense
        double totalSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .sum();
        
        System.out.println("Total salary expense: " + totalSalary);
        
        // Example 5: Parallel stream for expensive operations
        List<Employee> highEarners = employees.parallelStream()
            .filter(emp -> emp.getSalary() > 80000)
            .sorted(Comparator.comparing(Employee::getName))
            .collect(Collectors.toList());
        
        System.out.println("High earners: " + highEarners);
    }
}

class Employee {
    private String name;
    private String department;
    private double salary;
    
    // Constructor, getters, toString
    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    
    @Override
    public String toString() {
        return name + " (" + department + "): $" + salary;
    }
}
```

**Output**:
```
Engineers: [Alice, Charlie, Eve]
Average salaries: {Sales=62500.0, Engineering=83333.33333333333, Marketing=55000.0}
Highest paid: Eve (Engineering): $90000.0
Total salary expense: 430000.0
High earners: [Charlie (Engineering): $85000.0, Eve (Engineering): $90000.0]
```

**Stream Operations Breakdown**:
1. **Source**: `employees.stream()`
2. **Intermediate operations**: `filter()`, `map()`, `sorted()` (lazy)
3. **Terminal operations**: `collect()`, `max()`, `sum()` (eager, triggers processing)

**Theoretical Keywords:**
Stream pipeline, Filter-map-collect pattern, Grouping collectors, Parallel streams, Terminal operations, Lazy evaluation

### **212. What are terminal operations in streams?**
**Interviewer Answer:**
- **Terminal operations**: Produce a result or side effect, ending the stream pipeline
- **Trigger execution**: All intermediate operations are executed when terminal operation is called
- **Stream is consumed**: After terminal operation, stream cannot be reused

**Categories of Terminal Operations**:

**1. Reduction Operations** (produce single value):
```java
// Count
long count = stream.count();

// Min/Max
Optional<Integer> min = stream.min(Comparator.naturalOrder());
Optional<Integer> max = stream.max(Comparator.naturalOrder());

// Sum (for primitive streams)
int sum = intStream.sum();

// Average (for primitive streams)
OptionalDouble avg = intStream.average();

// Reduce (custom reduction)
int product = stream.reduce(1, (a, b) -> a * b);

// Find operations
Optional<Integer> first = stream.findFirst();
Optional<Integer> any = stream.findAny();

// Match operations
boolean allMatch = stream.allMatch(x -> x > 0);
boolean anyMatch = stream.anyMatch(x -> x > 0);
boolean noneMatch = stream.noneMatch(x -> x > 0);
```

**2. Collection Operations** (produce collections):
```java
// Collect to List
List<Integer> list = stream.collect(Collectors.toList());

// Collect to Set
Set<Integer> set = stream.collect(Collectors.toSet());

// Collect to Map
Map<String, Integer> map = stream.collect(
    Collectors.toMap(keyMapper, valueMapper)
);

// Grouping
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));

// Partitioning
Map<Boolean, List<Employee>> partition = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.getSalary() > 70000));
```

**3. Iteration Operations** (produce side effects):
```java
// forEach
stream.forEach(System.out::println);

// forEachOrdered (for parallel streams)
stream.forEachOrdered(System.out::println);
```

**4. Array Conversion**:
```java
Object[] array = stream.toArray();
Integer[] intArray = stream.toArray(Integer[]::new);
```

**Complete Example**:
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

// Terminal operation examples
long count = numbers.stream().count();                    // 6
Optional<Integer> max = numbers.stream().max(Integer::compare); // 6
int sum = numbers.stream().mapToInt(i -> i).sum();       // 21
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());                       // [2, 4, 6]
boolean hasNegative = numbers.stream()
    .anyMatch(n -> n < 0);                               // false

// Terminal operation consumes stream - this won't work:
Stream<Integer> stream = numbers.stream();
stream.forEach(System.out::println);  // OK
stream.count();  // ERROR: stream has already been operated upon or closed
```

**Theoretical Keywords:**
Pipeline termination, Result production, Stream consumption, Reduction operations, Collection operations, Eager execution

### **213. What are method references?**
**Interviewer Answer:**
- **Method Reference**: Shorthand syntax for lambda expressions calling existing methods
- **Syntax**: `ClassName::methodName` or `instance::methodName`
- **Types of Method References**:

1. **Static method reference**: `ClassName::staticMethod`
   ```java
   // Lambda
   Function<String, Integer> parser1 = s -> Integer.parseInt(s);
   // Method reference
   Function<String, Integer> parser2 = Integer::parseInt;
   ```

2. **Instance method reference of particular object**: `instance::instanceMethod`
   ```java
   String prefix = "Hello, ";
   // Lambda
   Function<String, String> greeter1 = name -> prefix.concat(name);
   // Method reference
   Function<String, String> greeter2 = prefix::concat;
   ```

3. **Instance method reference of arbitrary object**: `ClassName::instanceMethod`
   ```java
   // Lambda
   Function<String, String> upper1 = s -> s.toUpperCase();
   // Method reference
   Function<String, String> upper2 = String::toUpperCase;
   
   // Lambda with two parameters
   BiFunction<String, String, Boolean> checker1 = (s1, s2) -> s1.equals(s2);
   // Method reference
   BiFunction<String, String, Boolean> checker2 = String::equals;
   ```

4. **Constructor reference**: `ClassName::new`
   ```java
   // Lambda
   Supplier<List<String>> supplier1 = () -> new ArrayList<>();
   // Constructor reference
   Supplier<List<String>> supplier2 = ArrayList::new;
   
   // With parameters
   Function<Integer, String[]> arrayCreator1 = size -> new String[size];
   Function<Integer, String[]> arrayCreator2 = String[]::new;
   ```

**Examples**:
```java
import java.util.*;
import java.util.function.*;

public class MethodReferenceExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 1. Static method reference
        names.forEach(System.out::println);  // Equivalent to: s -> System.out.println(s)
        
        // 2. Instance method of particular object
        String prefix = "Ms. ";
        names.stream()
            .map(prefix::concat)  // Equivalent to: name -> prefix.concat(name)
            .forEach(System.out::println);
        
        // 3. Instance method of arbitrary object
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)  // Equivalent to: s -> s.toUpperCase()
            .collect(Collectors.toList());
        
        // 4. Constructor reference
        List<String> copy = names.stream()
            .map(String::new)  // Creates new String from each
            .collect(Collectors.toList());
        
        // Complex example
        Map<String, Integer> nameLengths = names.stream()
            .collect(Collectors.toMap(
                Function.identity(),  // key: name itself
                String::length        // value: name length
            ));
        
        System.out.println(nameLengths);
    }
    
    // Custom example
    static class Person {
        private String name;
        
        public Person(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public static int compareByName(Person a, Person b) {
            return a.name.compareTo(b.name);
        }
    }
    
    public static void personExample() {
        List<Person> people = Arrays.asList(
            new Person("Alice"),
            new Person("Bob"),
            new Person("Charlie")
        );
        
        // Static method reference
        people.sort(Person::compareByName);
        
        // Instance method reference
        List<String> names = people.stream()
            .map(Person::getName)  // Instance method of arbitrary object
            .collect(Collectors.toList());
    }
}
```

**When to Use Method References**:
- **Use**: When lambda just calls existing method
- **Avoid**: When lambda has additional logic
- **Readability**: Method references are often more readable

**Theoretical Keywords:**
Lambda shorthand, Double colon syntax, Static/instance/constructor references, Readability improvement, Code conciseness

### **214. What are lambda expressions?**
**Interviewer Answer:**
- **Lambda Expression**: Anonymous function (function without name) that can be passed as argument
- **Syntax**: `(parameters) -> expression` or `(parameters) -> { statements; }`
- **Introduced**: Java 8 to enable functional programming
- **Purpose**: Concise way to implement functional interfaces

**Basic Syntax**:
```java
// Zero parameters
() -> System.out.println("Hello");

// One parameter (parentheses optional)
s -> System.out.println(s);
(s) -> System.out.println(s);

// Multiple parameters
(a, b) -> a + b;
(a, b) -> { return a + b; };

// With explicit types
(int a, int b) -> a + b;
(String s) -> s.length();
```

**Characteristics**:
1. **Anonymous**: No explicit name
2. **Concise**: Less boilerplate than anonymous classes
3. **Type Inference**: Compiler infers types from context
4. **Lexical Scoping**: Can access final/effectively final variables from enclosing scope

**Lambda vs Anonymous Class**:
```java
// Anonymous class (pre-Java 8)
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};

// Lambda expression (Java 8+)
Runnable r2 = () -> System.out.println("Running");

// Both are equivalent
```

**Variable Access Rules**:
```java
int count = 0;  // Effectively final (not modified)
String message = "Hello";  // Effectively final

Runnable r = () -> {
    System.out.println(message);  // OK - can read
    System.out.println(count);    // OK - can read
    // count++;  // ERROR - cannot modify
    // message = "Hi";  // ERROR - cannot modify
};
```

**Theoretical Keywords:**
Anonymous functions, Arrow operator, Type inference, Functional interface implementation, Concise syntax, Lexical scoping

### **215. Can you give an example of a lambda expression?**
**Interviewer Answer:**
**Example 1: Basic Lambda Usage**
```java
import java.util.*;
import java.util.function.*;

public class LambdaExamples {
    public static void main(String[] args) {
        // Example 1: Runnable
        Runnable runnable = () -> System.out.println("Running in thread");
        new Thread(runnable).start();
        
        // Example 2: Comparator
        List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
        
        // Lambda for comparator
        names.sort((s1, s2) -> s1.compareTo(s2));
        // Or even shorter
        names.sort(String::compareTo);
        
        System.out.println("Sorted: " + names);
        
        // Example 3: Event listener pattern
        Button button = new Button();
        button.addClickListener(() -> System.out.println("Button clicked!"));
        
        // Example 4: Custom functional interface
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;
        
        System.out.println("5 + 3 = " + add.calculate(5, 3));
        System.out.println("5 * 3 = " + multiply.calculate(5, 3));
    }
    
    // Functional interface for calculator
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }
    
    // Mock button class
    static class Button {
        private List<Runnable> clickListeners = new ArrayList<>();
        
        public void addClickListener(Runnable listener) {
            clickListeners.add(listener);
        }
        
        public void click() {
            clickListeners.forEach(Runnable::run);
        }
    }
}
```

**Example 2: Lambda with Collections**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// 1. Filtering with lambda
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)  // Lambda for Predicate
    .collect(Collectors.toList());

// 2. Mapping with lambda
List<Integer> squares = numbers.stream()
    .map(n -> n * n)  // Lambda for Function
    .collect(Collectors.toList());

// 3. Reducing with lambda
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);  // Lambda for BinaryOperator

// 4. ForEach with lambda
numbers.forEach(n -> System.out.print(n + " "));

// 5. Complex lambda with multiple statements
List<String> result = numbers.stream()
    .filter(n -> {
        boolean isPrime = n > 1;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    })
    .map(n -> "Prime: " + n)
    .collect(Collectors.toList());
```

**Example 3: Real-world Use Case - Processing Orders**
```java
class Order {
    private String id;
    private double amount;
    private String status;
    
    // Constructor, getters
    public Order(String id, double amount, String status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }
    
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}

public class OrderProcessor {
    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
            new Order("001", 150.0, "PROCESSING"),
            new Order("002", 75.5, "COMPLETED"),
            new Order("003", 200.0, "PROCESSING"),
            new Order("004", 50.0, "CANCELLED"),
            new Order("005", 300.0, "PROCESSING")
        );
        
        // Process all PROCESSING orders with amount > 100
        List<Order> largeProcessingOrders = orders.stream()
            .filter(order -> "PROCESSING".equals(order.getStatus()))
            .filter(order -> order.getAmount() > 100.0)
            .collect(Collectors.toList());
        
        // Calculate total amount of COMPLETED orders
        double totalCompleted = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .mapToDouble(Order::getAmount)
            .sum();
        
        // Format orders for display
        List<String> orderSummaries = orders.stream()
            .map(order -> 
                String.format("Order %s: $%.2f (%s)", 
                    order.getId(), order.getAmount(), order.getStatus()))
            .collect(Collectors.toList());
        
        // Process each order (side effect)
        orders.stream()
            .filter(order -> "PROCESSING".equals(order.getStatus()))
            .forEach(order -> processOrder(order));
    }
    
    private static void processOrder(Order order) {
        System.out.println("Processing order: " + order.getId());
        // Actual processing logic
    }
}
```

**Theoretical Keywords:**
Lambda syntax examples, Functional interfaces implementation, Collection processing, Real-world use cases, Multi-statement lambdas

### **216. Can you explain the relationship between lambda expressions and functional interfaces?**
**Interviewer Answer:**
- **Functional Interface**: Interface with exactly one abstract method (SAM - Single Abstract Method)
- **Lambda Expression**: Provides concise implementation of that single abstract method
- **Relationship**: Lambda expression can be used wherever a functional interface is expected

**Key Connection**:
```java
// Functional interface
@FunctionalInterface
interface MyFunction {
    int apply(int x, int y);  // Single abstract method
}

// Lambda provides implementation
MyFunction add = (a, b) -> a + b;      // Implements apply()
MyFunction multiply = (a, b) -> a * b; // Implements apply()

// Usage
int result1 = add.apply(5, 3);      // 8
int result2 = multiply.apply(5, 3); // 15
```

**How Compiler Matches Lambda to Interface**:
```java
// Step 1: Functional interface declaration
interface StringProcessor {
    String process(String input);  // Abstract method signature: String -> String
}

// Step 2: Lambda must match method signature
StringProcessor upper = s -> s.toUpperCase();  // OK: String -> String
StringProcessor repeat = s -> s + s;           // OK: String -> String
// StringProcessor invalid = s -> s.length();  // ERROR: returns int, not String

// Step 3: Compiler infers types
List<String> names = Arrays.asList("Alice", "Bob");
names.replaceAll(name -> name.toUpperCase());  // Lambda for UnaryOperator<String>
```

**Built-in Functional Interfaces Examples**:
```java
// 1. Predicate<T> - boolean test(T t)
Predicate<String> isLong = s -> s.length() > 10;
boolean result = isLong.test("Hello");  // false

// 2. Function<T, R> - R apply(T t)
Function<String, Integer> lengthFunc = s -> s.length();
int len = lengthFunc.apply("Hello");  // 5

// 3. Consumer<T> - void accept(T t)
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello");  // Prints "Hello"

// 4. Supplier<T> - T get()
Supplier<Double> randomSupplier = () -> Math.random();
double rand = randomSupplier.get();

// 5. UnaryOperator<T> - T apply(T t) (Function<T, T>)
UnaryOperator<String> upperOp = s -> s.toUpperCase();

// 6. BinaryOperator<T> - T apply(T t1, T t2)
BinaryOperator<Integer> adder = (a, b) -> a + b;
```

**Lambda as Method Parameter**:
```java
public class LambdaAsParameter {
    // Method expecting functional interface
    public static void processNumbers(List<Integer> numbers, 
                                     Predicate<Integer> filter,
                                     Function<Integer, Integer> mapper) {
        numbers.stream()
            .filter(filter)
            .map(mapper)
            .forEach(System.out::println);
    }
    
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        // Pass lambda expressions as arguments
        processNumbers(
            nums,
            n -> n % 2 == 0,      // Predicate: filter evens
            n -> n * n            // Function: square them
        );
        // Output: 4, 16, 36
    }
}
```

**@FunctionalInterface Annotation**:
- Optional annotation for compile-time checking
- Ensures interface has exactly one abstract method
- Provides documentation clarity

```java
@FunctionalInterface  // Compiler verifies single abstract method
interface ValidFunctional {
    void doWork();  // Single abstract method OK
    
    default void helper() {  // Default methods OK
        System.out.println("Helping");
    }
    
    static void utility() {  // Static methods OK
        System.out.println("Utility");
    }
}

// @FunctionalInterface  // This would cause compile error
interface InvalidFunctional {
    void method1();
    void method2();  // Second abstract method - not functional
}
```

**Theoretical Keywords:**
SAM (Single Abstract Method), Type inference, Method signature matching, Built-in functional interfaces, Lambda as argument, @FunctionalInterface annotation

### **217. What is a predicate?**
**Interviewer Answer:**
- **Predicate**: Functional interface that represents a boolean-valued function of one argument
- **Package**: `java.util.function.Predicate`
- **Method**: `boolean test(T t)`
- **Common use**: Filtering operations in streams

**Basic Usage**:
```java
import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        // Creating predicates
        Predicate<String> isLong = s -> s.length() > 5;
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<String> startsWithA = s -> s.startsWith("A");
        
        // Testing predicates
        System.out.println(isLong.test("Hello"));      // false
        System.out.println(isLong.test("Hello World")); // true
        System.out.println(isEven.test(10));           // true
        System.out.println(startsWithA.test("Alice")); // true
    }
}
```

**Predicate Composition (AND, OR, NOT)**:
```java
Predicate<Integer> isPositive = n -> n > 0;
Predicate<Integer> isEven = n -> n % 2 == 0;
Predicate<Integer> isLarge = n -> n > 100;

// AND: both conditions must be true
Predicate<Integer> isPositiveEven = isPositive.and(isEven);
System.out.println(isPositiveEven.test(10));  // true (10 > 0 AND even)
System.out.println(isPositiveEven.test(-5));  // false

// OR: either condition true
Predicate<Integer> isEvenOrLarge = isEven.or(isLarge);
System.out.println(isEvenOrLarge.test(5));    // false
System.out.println(isEvenOrLarge.test(150));  // true (large)
System.out.println(isEvenOrLarge.test(8));    // true (even)

// NOT: negate the predicate
Predicate<Integer> isOdd = isEven.negate();
System.out.println(isOdd.test(7));  // true

// Complex composition
Predicate<Integer> complex = isPositive.and(isEven).or(isLarge);
System.out.println(complex.test(50));   // true (positive & even)
System.out.println(complex.test(-200)); // false (not positive)
System.out.println(complex.test(200));  // true (large)
```

**Using Predicate with Collections**:
```java
import java.util.*;
import java.util.function.Predicate;

public class PredicateWithCollections {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Filter using predicate
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> greaterThan5 = n -> n > 5;
        
        List<Integer> filtered = numbers.stream()
            .filter(isEven.and(greaterThan5))
            .collect(Collectors.toList());
        
        System.out.println("Even numbers > 5: " + filtered); // [6, 8, 10]
        
        // Remove if predicate
        List<String> names = new ArrayList<>(Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Eve"
        ));
        
        Predicate<String> startsWithC = s -> s.startsWith("C");
        names.removeIf(startsWithC.negate()); // Remove if doesn't start with C
        
        System.out.println("Names starting with C: " + names); // [Charlie]
    }
    
    // Custom method using Predicate
    public static <T> List<T> filterList(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
}
```

**Real-world Example - User Validation**:
```java
class User {
    private String username;
    private String email;
    private int age;
    private boolean active;
    
    // Constructor, getters
    public User(String username, String email, int age, boolean active) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.active = active;
    }
    
    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public boolean isActive() { return active; }
}

public class UserValidation {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
            new User("alice", "alice@email.com", 25, true),
            new User("bob", "bob@email", 17, true),  // Invalid email
            new User("charlie", "charlie@email.com", 30, false), // Inactive
            new User("diana", "diana@email.com", 16, true)  // Underage
        );
        
        // Validation predicates
        Predicate<User> isValidEmail = user -> 
            user.getEmail().contains("@") && user.getEmail().contains(".");
        
        Predicate<User> isAdult = user -> user.getAge() >= 18;
        Predicate<User> isActive = User::isActive;
        
        // Combined validation
        Predicate<User> isValidUser = isValidEmail
            .and(isAdult)
            .and(isActive);
        
        // Filter valid users
        List<User> validUsers = users.stream()
            .filter(isValidUser)
            .collect(Collectors.toList());
        
        System.out.println("Valid users: " + validUsers.size()); // 1 (alice)
        
        // Find invalid users
        List<User> invalidUsers = users.stream()
            .filter(isValidUser.negate())
            .collect(Collectors.toList());
        
        System.out.println("Invalid users: " + invalidUsers.size()); // 3
    }
}
```

**Specialized Predicates**:
```java
// Primitive specialized predicates (avoid auto-boxing)
IntPredicate isPositiveInt = n -> n > 0;
LongPredicate isEvenLong = n -> n % 2 == 0;
DoublePredicate isPercentage = n -> n >= 0.0 && n <= 100.0;

// BiPredicate - two arguments
BiPredicate<String, String> stringsEqual = (s1, s2) -> s1.equals(s2);
BiPredicate<Integer, Integer> sumGreaterThan = (a, b) -> (a + b) > 10;
```

**Theoretical Keywords:**
Boolean-valued function, Filtering operations, Predicate composition (and/or/negate), removeIf(), Specialized predicates (IntPredicate, etc.)

### **218. What is the functional interface `Function`?**
**Interviewer Answer:**
- **Function**: Functional interface that accepts one argument and produces a result
- **Package**: `java.util.function.Function<T, R>`
- **Method**: `R apply(T t)`
- **Common use**: Transformation/mapping operations

**Basic Usage**:
```java
import java.util.function.Function;

public class FunctionExample {
    public static void main(String[] args) {
        // String to Integer transformation
        Function<String, Integer> lengthFunction = s -> s.length();
        int length = lengthFunction.apply("Hello");  // 5
        
        // Integer to String transformation
        Function<Integer, String> stringify = n -> "Number: " + n;
        String result = stringify.apply(42);  // "Number: 42"
        
        // Double to Double transformation
        Function<Double, Double> squareRoot = Math::sqrt;
        double root = squareRoot.apply(16.0);  // 4.0
    }
}
```

**Function Composition**:
```java
Function<Integer, Integer> multiplyBy2 = x -> x * 2;
Function<Integer, Integer> add3 = x -> x + 3;

// compose(): g.compose(f) = g(f(x)) - applies first, then second
Function<Integer, Integer> multiplyThenAdd = add3.compose(multiplyBy2);
int result1 = multiplyThenAdd.apply(5);  // multiplyBy2(5)=10, then add3(10)=13

// andThen(): f.andThen(g) = g(f(x)) - applies first, then second (more common)
Function<Integer, Integer> addThenMultiply = multiplyBy2.andThen(add3);
int result2 = addThenMultiply.apply(5);  // add3(5)=8, then multiplyBy2(8)=16

// Identity function: returns input unchanged
Function<String, String> identity = Function.identity();
String same = identity.apply("test");  // "test"
```

**Using Function with Streams**:
```java
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionWithStreams {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Map names to their lengths
        Function<String, Integer> nameToLength = String::length;
        List<Integer> lengths = names.stream()
            .map(nameToLength)
            .collect(Collectors.toList());
        System.out.println("Name lengths: " + lengths); // [5, 3, 7]
        
        // Convert to uppercase
        Function<String, String> toUpperCase = String::toUpperCase;
        List<String> upperNames = names.stream()
            .map(toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase: " + upperNames);
        
        // Chain functions
        Function<String, String> addPrefix = s -> "Ms. " + s;
        Function<String, String> formatName = addPrefix.andThen(toUpperCase);
        
        List<String> formatted = names.stream()
            .map(formatName)
            .collect(Collectors.toList());
        System.out.println("Formatted: " + formatted); // [MS. ALICE, ...]
    }
}
```

**Specialized Functions**:
```java
// Primitive specialized functions (avoid auto-boxing)
IntFunction<String> intToString = n -> "Value: " + n;
String str = intToString.apply(42);

ToIntFunction<String> stringToLength = String::length;
int len = stringToLength.applyAsInt("Hello");

IntToDoubleFunction celsiusToFahrenheit = c -> (c * 9/5.0) + 32;
double fahr = celsiusToFahrenheit.applyAsDouble(25); // 77.0

// BiFunction - two arguments, one result
BiFunction<Integer, Integer, Integer> adder = (a, b) -> a + b;
int sum = adder.apply(5, 3); // 8

BiFunction<String, Integer, String> repeatString = (s, n) -> s.repeat(n);
String repeated = repeatString.apply("Hi", 3); // "HiHiHi"
```

**Real-world Example - Data Transformation Pipeline**:
```java
class Product {
    private String name;
    private double price;
    private String category;
    
    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}

public class ProductTransformer {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 999.99, "Electronics"),
            new Product("Coffee", 4.99, "Food"),
            new Product("Book", 14.99, "Education"),
            new Product("Phone", 699.99, "Electronics")
        );
        
        // Transformation functions
        Function<Product, String> getName = Product::getName;
        Function<Product, Double> getPrice = Product::getPrice;
        Function<Product, String> getCategory = Product::getCategory;
        
        // Apply tax to price
        Function<Product, Double> applyTax = product -> 
            product.getPrice() * 1.08; // 8% tax
        
        // Format product for display
        Function<Product, String> formatProduct = product ->
            String.format("%s: $%.2f (%s)", 
                product.getName(), 
                product.getPrice(), 
                product.getCategory());
        
        // Chain: apply tax, then format
        Function<Product, String> taxAndFormat = applyTax.andThen(
            price -> String.format("Price with tax: $%.2f", price)
        );
        
        // Apply transformations
        List<String> productNames = products.stream()
            .map(getName)
            .collect(Collectors.toList());
        
        List<Double> pricesWithTax = products.stream()
            .map(applyTax)
            .collect(Collectors.toList());
        
        List<String> formattedProducts = products.stream()
            .map(formatProduct)
            .collect(Collectors.toList());
        
        System.out.println("Names: " + productNames);
        System.out.println("Prices with tax: " + pricesWithTax);
        System.out.println("Formatted: " + formattedProducts);
    }
}
```

**Theoretical Keywords:**
Transformation function, Type conversion, Function composition (andThen/compose), Identity function, Specialized functions (IntFunction, etc.), Stream mapping

### **219. What is a `Consumer`?**
**Interviewer Answer:**
- **Consumer**: Functional interface that accepts a single input and returns no result
- **Package**: `java.util.function.Consumer<T>`
- **Method**: `void accept(T t)`
- **Common use**: Performing side effects, like printing or modifying objects

**Basic Usage**:
```java
import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {
        // Simple consumer that prints
        Consumer<String> printer = s -> System.out.println(s);
        printer.accept("Hello World");  // Prints: Hello World
        
        // Consumer that modifies object
        StringBuilder builder = new StringBuilder();
        Consumer<String> appender = s -> builder.append(s).append(" ");
        appender.accept("Hello");
        appender.accept("World");
        System.out.println(builder.toString());  // "Hello World "
        
        // Consumer for logging
        Consumer<String> logger = message -> 
            System.out.println("[LOG] " + new java.util.Date() + ": " + message);
        logger.accept("Application started");
    }
}
```

**Consumer Composition**:
```java
Consumer<String> print = s -> System.out.print(s);
Consumer<String> printUpperCase = s -> System.out.print(s.toUpperCase());

// andThen(): executes consumers in sequence
Consumer<String> printBoth = print.andThen(printUpperCase);
printBoth.accept("Hello ");  
// Output: Hello HELLO (prints original, then uppercase)
```

**Using Consumer with Collections**:
```java
import java.util.*;
import java.util.function.Consumer;

public class ConsumerWithCollections {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // forEach expects a Consumer
        names.forEach(name -> System.out.println("Hello, " + name));
        
        // Or with method reference
        names.forEach(System.out::println);
        
        // Modify collection elements
        List<StringBuilder> builders = Arrays.asList(
            new StringBuilder("A"),
            new StringBuilder("B"),
            new StringBuilder("C")
        );
        
        Consumer<StringBuilder> appendX = sb -> sb.append("X");
        builders.forEach(appendX);
        
        builders.forEach(System.out::println);  // AX, BX, CX
    }
}
```

**BiConsumer - Two Arguments**:
```java
import java.util.function.BiConsumer;
import java.util.Map;

public class BiConsumerExample {
    public static void main(String[] args) {
        // BiConsumer for two arguments
        BiConsumer<String, Integer> printKeyValue = (key, value) -> 
            System.out.println(key + " = " + value);
        
        printKeyValue.accept("Age", 25);  // Age = 25
        
        // Using with Map
        Map<String, Integer> ages = new HashMap<>();
        ages.put("Alice", 30);
        ages.put("Bob", 25);
        ages.put("Charlie", 35);
        
        // Map.forEach expects BiConsumer
        ages.forEach(printKeyValue);
        // Or inline:
        ages.forEach((name, age) -> 
            System.out.println(name + " is " + age + " years old"));
    }
}
```

**Real-world Example - Processing System**:
```java
class Order {
    private String id;
    private double amount;
    private String status;
    
    public Order(String id, double amount, String status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Order[" + id + ", $" + amount + ", " + status + "]";
    }
}

public class OrderProcessor {
    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
            new Order("001", 150.0, "PENDING"),
            new Order("002", 75.5, "PENDING"),
            new Order("003", 200.0, "PROCESSING")
        );
        
        // Consumer to process an order
        Consumer<Order> processOrder = order -> {
            System.out.println("Processing: " + order.getId());
            // Simulate processing
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            order.setStatus("PROCESSED");
            System.out.println("Completed: " + order.getId());
        };
        
        // Consumer to log order details
        Consumer<Order> logOrder = order -> 
            System.out.println("[LOG] " + new java.util.Date() + 
                             " - Order " + order.getId() + 
                             ": $" + order.getAmount());
        
        // Chain: log first, then process
        Consumer<Order> processWithLog = logOrder.andThen(processOrder);
        
        // Process all pending orders
        orders.stream()
            .filter(order -> "PENDING".equals(order.getStatus()))
            .forEach(processWithLog);
        
        // Verify processing
        System.out.println("\nFinal order status:");
        orders.forEach(System.out::println);
    }
}
```

**Primitive Specialized Consumers**:
```java
// Avoid auto-boxing for primitives
IntConsumer printInt = n -> System.out.println("Integer: " + n);
printInt.accept(42);

LongConsumer printLong = n -> System.out.println("Long: " + n);
printLong.accept(10000000000L);

DoubleConsumer printDouble = n -> System.out.println("Double: " + n);
printDouble.accept(3.14159);

// ObjIntConsumer, ObjLongConsumer, ObjDoubleConsumer
ObjIntConsumer<String> printStringWithInt = (s, n) -> 
    System.out.println(s + ": " + n);
printStringWithInt.accept("Count", 10);
```

**Theoretical Keywords:**
Side-effect operations, Void return type, Consumer composition (andThen), forEach() method, BiConsumer, Primitive specialized consumers

### **220. Can you give examples of functional interfaces with multiple arguments?**
**Interviewer Answer:**
**1. BiFunction<T, U, R>** - Two arguments, returns result
```java
import java.util.function.BiFunction;

public class BiFunctionExample {
    public static void main(String[] args) {
        // Concatenate strings
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + " " + s2;
        String result = concat.apply("Hello", "World");  // "Hello World"
        
        // Calculate power
        BiFunction<Double, Double, Double> power = Math::pow;
        double squared = power.apply(5.0, 2.0);  // 25.0
        
        // Create objects
        BiFunction<String, Integer, Person> createPerson = Person::new;
        Person person = createPerson.apply("Alice", 30);
        
        // Real example: Calculate discounted price
        BiFunction<Double, Double, Double> calculateDiscount = (price, discount) -> 
            price - (price * discount / 100);
        double finalPrice = calculateDiscount.apply(100.0, 20.0);  // 80.0
    }
    
    static class Person {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
```

**2. BiConsumer<T, U>** - Two arguments, no result
```java
import java.util.function.BiConsumer;
import java.util.Map;

public class BiConsumerExample {
    public static void main(String[] args) {
        // Print key-value pair
        BiConsumer<String, Integer> printEntry = (key, value) -> 
            System.out.println(key + ": " + value);
        printEntry.accept("Age", 25);  // Age: 25
        
        // Add to map
        Map<String, Integer> map = new java.util.HashMap<>();
        BiConsumer<String, Integer> addToMap = map::put;
        addToMap.accept("Alice", 30);
        addToMap.accept("Bob", 25);
        
        // Process two related items
        BiConsumer<String, String> validateStrings = (s1, s2) -> {
            if (s1 == null || s2 == null) {
                throw new IllegalArgumentException("Strings cannot be null");
            }
            if (s1.length() != s2.length()) {
                System.out.println("Strings have different lengths");
            }
        };
    }
}
```

**3. BiPredicate<T, U>** - Two arguments, returns boolean
```java
import java.util.function.BiPredicate;

public class BiPredicateExample {
    public static void main(String[] args) {
        // Compare strings
        BiPredicate<String, String> areEqual = String::equals;
        boolean result1 = areEqual.test("Hello", "Hello");  // true
        boolean result2 = areEqual.test("Hello", "World");  // false
        
        // Check if sum exceeds limit
        BiPredicate<Integer, Integer> sumExceeds = (a, b) -> (a + b) > 100;
        boolean exceeds = sumExceeds.test(60, 50);  // true (110 > 100)
        
        // Validate username and password
        BiPredicate<String, String> isValidLogin = (user, pass) -> 
            user != null && pass != null && 
            user.length() >= 3 && pass.length() >= 8;
        
        boolean valid = isValidLogin.test("alice", "password123");  // true
    }
}
```

**4. BinaryOperator<T>** - Special case of BiFunction where all types are same
```java
import java.util.function.BinaryOperator;

public class BinaryOperatorExample {
    public static void main(String[] args) {
        // Mathematical operations
        BinaryOperator<Integer> add = Integer::sum;
        BinaryOperator<Integer> multiply = (a, b) -> a * b;
        BinaryOperator<Double> min = Math::min;
        BinaryOperator<String> concat = String::concat;
        
        int sum = add.apply(5, 3);           // 8
        int product = multiply.apply(5, 3);  // 15
        double smaller = min.apply(5.5, 3.2); // 3.2
        String combined = concat.apply("Hello ", "World"); // "Hello World"
        
        // Use in reduction
        java.util.List<Integer> numbers = java.util.Arrays.asList(1, 2, 3, 4, 5);
        int total = numbers.stream().reduce(0, add);  // 15
    }
}
```

**5. Custom Functional Interface with Multiple Parameters**
```java
// Three-parameter functional interface
@FunctionalInterface
interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}

public class CustomMultiArgExample {
    public static void main(String[] args) {
        // Calculate volume
        TriFunction<Double, Double, Double, Double> volume = 
            (length, width, height) -> length * width * height;
        
        double boxVolume = volume.apply(2.0, 3.0, 4.0);  // 24.0
        
        // Format string with three values
        TriFunction<String, Integer, Double, String> format = 
            (name, age, score) -> 
                String.format("%s (Age: %d, Score: %.2f)", name, age, score);
        
        String formatted = format.apply("Alice", 30, 95.5);
        // "Alice (Age: 30, Score: 95.50)"
    }
}
```

**6. Real-world Example - Calculator Service**
```java
import java.util.function.*;

public class CalculatorService {
    
    // Operation as BiFunction
    private static final BiFunction<Double, Double, Double> ADD = Double::sum;
    private static final BiFunction<Double, Double, Double> SUBTRACT = (a, b) -> a - b;
    private static final BiFunction<Double, Double, Double> MULTIPLY = (a, b) -> a * b;
    private static final BiFunction<Double, Double, Double> DIVIDE = (a, b) -> a / b;
    
    // Validator as BiPredicate
    private static final BiPredicate<Double, Double> isValidDivision = 
        (a, b) -> b != 0;
    
    // Logger as BiConsumer
    private static final BiConsumer<String, Double> logOperation = 
        (op, result) -> System.out.println(op + " result: " + result);
    
    public static double calculate(double a, double b, 
                                  BiFunction<Double, Double, Double> operation,
                                  String operationName) {
        // Validate if needed (e.g., division by zero)
        if ("DIVIDE".equals(operationName) && !isValidDivision.test(a, b)) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        
        // Perform calculation
        double result = operation.apply(a, b);
        
        // Log the operation
        logOperation.accept(operationName, result);
        
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("5 + 3 = " + calculate(5, 3, ADD, "ADD"));
        System.out.println("10 - 4 = " + calculate(10, 4, SUBTRACT, "SUBTRACT"));
        System.out.println("6 * 7 = " + calculate(6, 7, MULTIPLY, "MULTIPLY"));
        System.out.println("20 / 5 = " + calculate(20, 5, DIVIDE, "DIVIDE"));
        
        try {
            calculate(10, 0, DIVIDE, "DIVIDE");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Theoretical Keywords:**
BiFunction, BiConsumer, BiPredicate, BinaryOperator, Custom multi-arg interfaces, Real-world applications, Parameterized operations

---

**Excellent! You've now mastered Java Functional Programming concepts including lambdas, streams, and functional interfaces. These are essential for modern Java development and frequently tested in interviews. You're well-prepared to write concise, expressive, and functional Java code!** 🚀🎯