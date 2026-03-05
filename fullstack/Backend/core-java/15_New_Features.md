# **COMPREHENSIVE JAVA VERSION FEATURES (5 TO CURRENT)**

## **Java 5 (JDK 1.5) - September 2004**
** Answer:**
1. **Generics** - Type-safe collections
2. **Enhanced for-loop** - For-each syntax
3. **Autoboxing/Unboxing** - Automatic primitive-wrapper conversion
4. **Typesafe Enums** - Full-featured enum types
5. **Varargs** - Variable-length arguments
6. **Static Import** - Import static members
7. **Annotations (Metadata)** - @Override, @Deprecated, etc.
8. **java.util.concurrent** - Concurrency utilities
9. **Scanner Class** - For parsing input
10. **Formatted Output** - printf() method

**Key Examples**:
```java
// Generics
List<String> list = new ArrayList<String>();

// Enhanced for-loop
for (String s : list) { System.out.println(s); }

// Autoboxing
Integer i = 10;  // Autoboxing
int j = i;       // Auto-unboxing

// Enums with methods
enum Day { MONDAY, TUESDAY; 
    public boolean isWeekday() { return true; } }

// Varargs
void printAll(String... strings) { }
printAll("a", "b", "c");

// Static import
import static java.lang.Math.*;
double r = cos(PI * theta);
```

**Theoretical Keywords**: Generics, Enhanced for-loop, Autoboxing, Enums, Varargs, Annotations, Concurrency API

---

## **Java 6 (JDK 1.6) - December 2006**
** Answer:**
1. **Scripting Support** - JSR-223 (JavaScript, Ruby, etc.)
2. **Compiler API** - JSR-199 (javax.tools)
3. **Pluggable Annotation Processing** - JSR-269
4. **JDBC 4.0** - Automatic driver loading, SQLException improvements
5. **Java DB (Derby)** - Embedded database
6. **Navigable Collections** - NavigableSet, NavigableMap
7. **Deque Interface** - Double-ended queue
8. **Web Services** - JAX-WS 2.0, REST support
9. **Improved Monitoring** - JMX, JConsole
10. **Desktop Enhancements** - SwingWorker, System Tray

**No new language syntax features**

**Example - Scripting**:
```java
ScriptEngineManager manager = new ScriptEngineManager();
ScriptEngine engine = manager.getEngineByName("JavaScript");
engine.eval("print('Hello')");
```

**Theoretical Keywords**: Scripting engine, Compiler API, Navigable collections, JDBC 4.0, JMX

---

## **Java 7 (JDK 1.7) - July 2011**
** Answer:**
1. **Diamond Operator** - Type inference for generics
   ```java
   Map<String, List<String>> map = new HashMap<>();
   ```
2. **Strings in switch**
   ```java
   switch (day) {
       case "MONDAY": break;
   }
   ```
3. **Try-with-resources** - Automatic resource management
   ```java
   try (BufferedReader br = new BufferedReader(file)) {
       // auto-closed
   }
   ```
4. **Multi-catch** - Multiple exceptions in one catch
   ```java
   catch (IOException | SQLException e) { }
   ```
5. **Binary Literals & Underscores**
   ```java
   int binary = 0b1010;
   long creditCard = 1234_5678_9012_3456L;
   ```
6. **Fork/Join Framework** - Parallel processing
7. **NIO.2** - New I/O API (Path, Files, Paths)
8. **Watch Service** - File system monitoring
9. **Concurrency Updates** - Phaser, TransferQueue
10. **G1 Garbage Collector** (experimental)

**Theoretical Keywords**: Diamond operator, Try-with-resources, NIO.2, Multi-catch, Fork/Join

---

## **Java 8 (JDK 1.8) - March 2014**
** Answer:**
1. **Lambda Expressions**
   ```java
   (a, b) -> a + b
   ```
2. **Stream API**
   ```java
   list.stream().filter(x -> x > 0).collect(Collectors.toList())
   ```
3. **Functional Interfaces** - @FunctionalInterface
4. **Default Methods** in interfaces
   ```java
   interface Vehicle {
       default void start() { System.out.println("Starting"); }
   }
   ```
5. **Method References**
   ```java
   list.forEach(System.out::println)
   ```
6. **Optional** - Avoid NullPointerException
7. **New Date/Time API** (java.time)
   ```java
   LocalDate today = LocalDate.now();
   ```
8. **Nashorn JavaScript Engine** - Replace Rhino
9. **Type Annotations** & Repeatable Annotations
10. **Base64 Support** - java.util.Base64
11. **CompletableFuture** - Improved async programming
12. **Parallel Array Sorting** - Arrays.parallelSort()

**Theoretical Keywords**: Lambda, Streams, Optional, Date/Time API, Functional programming

---

## **Java 9 (JDK 9) - September 2017**
** Answer:**
1. **Module System (Project Jigsaw)**
   ```java
   module com.example {
       requires java.base;
       exports com.example.api;
   }
   ```
2. **JShell** - REPL (Read-Eval-Print Loop)
3. **Factory Methods for Collections**
   ```java
   List<String> list = List.of("a", "b", "c");
   Set<String> set = Set.of("a", "b");
   Map<String, Integer> map = Map.of("a", 1, "b", 2);
   ```
4. **Private Methods in Interfaces**
5. **HTTP/2 Client** (incubator)
6. **Process API Improvements**
7. **Reactive Streams** - Flow API
8. **Multi-Release JAR Files**
9. **Enhanced Deprecation** - @Deprecated(forRemoval=true)
10. **Diamond Operator for Anonymous Classes**
    ```java
    List<String> list = new ArrayList<>() { };
    ```

**Theoretical Keywords**: Modules, JShell, Immutable collections, HTTP/2 client, Jigsaw

---

## **Java 10 (JDK 10) - March 2018**
** Answer:**
1. **Local Variable Type Inference (var)**
   ```java
   var list = new ArrayList<String>();
   var stream = list.stream();
   ```
2. **Consolidated JDK Forest** - Single repository
3. **Application Class-Data Sharing** - Improve startup
4. **Parallel Full GC for G1** - Improve worst-case latency
5. **Experimental Java-Based JIT Compiler** (Graal)
6. **Root Certificates** - OpenJDK gets CA certs
7. **Time-Based Release Versioning** - 6-month release cycle

**Note**: var can only be used for local variables with initializers

**Theoretical Keywords**: Local variable type inference, var keyword, Graal compiler, CDS

---

## **Java 11 (LTS) - September 2018**
** Answer:**
1. **Local Variable Syntax for Lambda Parameters**
   ```java
   (var x, var y) -> x.process(y)
   ```
2. **HTTP Client (Standard)** - java.net.http
   ```java
   HttpClient client = HttpClient.newHttpClient();
   ```
3. **Epsilon GC** - No-op garbage collector
4. **Launch Single-File Source Programs**
   ```bash
   java HelloWorld.java
   ```
5. **String Methods**
   ```java
   "  hello  ".strip();          // "hello"
   "hello".repeat(3);            // "hellohellohello"
   "".isBlank();                 // true
   ```
6. **Files.readString()/writeString()**
7. **Predicate.not()** method
   ```java
   list.stream().filter(Predicate.not(String::isEmpty))
   ```
8. **Nest-Based Access Control** - JVM access control
9. **Flight Recorder** - Low-overhead data collection
10. **TLS 1.3 Support**
11. **Remove Java EE & CORBA Modules**

**Theoretical Keywords**: HTTP Client, Single-file execution, String methods, TLS 1.3, LTS

---

## **Java 12 (JDK 12) - March 2019**
** Answer:**
1. **Switch Expressions (Preview)**
   ```java
   int numLetters = switch (day) {
       case MONDAY, FRIDAY, SUNDAY -> 6;
       case TUESDAY -> 7;
       default -> {
           int k = day.toString().length();
           yield k;
       }
   };
   ```
2. **Shenandoah GC** - Low-pause-time GC
3. **Microbenchmark Suite** - JMH-based
4. **JVM Constants API** - Describe constants
5. **One AArch64 Port** - Remove 32-bit ARM
6. **Default CDS Archives** - Improve startup

**Theoretical Keywords**: Switch expressions (preview), Shenandoah GC, JMH benchmarks

---

## **Java 13 (JDK 13) - September 2019**
** Answer:**
1. **Text Blocks (Preview)**
   ```java
   String html = """
       <html>
           <body>
               <p>Hello</p>
           </body>
       </html>
       """;
   ```
2. **Switch Expressions (Second Preview)**
3. **Reimplement Legacy Socket API** - NIO-based
4. **ZGC: Uncommit Unused Memory**
5. **Dynamic CDS Archives** - AppCDS

**Theoretical Keywords**: Text blocks (preview), ZGC improvements, Socket API rewrite

---

## **Java 14 (JDK 14) - March 2020**
** Answer:**
1. **Switch Expressions (Standard)**
2. **Pattern Matching for instanceof (Preview)**
   ```java
   if (obj instanceof String s) {
       // can use s here
       System.out.println(s.length());
   }
   ```
3. **Records (Preview)**
   ```java
   record Point(int x, int y) { }
   Point p = new Point(10, 20);
   ```
4. **Helpful NullPointerExceptions** - Better error messages
5. **Text Blocks (Second Preview)**
6. **Packaging Tool (Incubator)** - jpackage
7. **Foreign-Memory Access API (Incubator)** - Panama
8. **ZGC on Windows/macOS**

**Theoretical Keywords**: Records (preview), Pattern matching, Better NPEs, jpackage

---

## **Java 15 (JDK 15) - September 2020**
** Answer:**
1. **Sealed Classes (Preview)**
   ```java
   public sealed class Shape 
       permits Circle, Square, Rectangle { }
   ```
2. **Text Blocks (Standard)**
3. **Records (Second Preview)**
4. **Hidden Classes** - Framework classes that can't be used directly
5. **ZGC & Shenandoah Production Ready**
6. **Pattern Matching instanceof (Second Preview)**
7. **Foreign-Memory Access API (Second Incubator)**
8. **Remove Nashorn JavaScript Engine**

**Theoretical Keywords**: Sealed classes (preview), Text blocks standard, Hidden classes

---

## **Java 16 (JDK 16) - March 2021**
** Answer:**
1. **Records (Standard)**
2. **Pattern Matching for instanceof (Standard)**
3. **Sealed Classes (Second Preview)**
4. **Vector API (Incubator)** - SIMD operations
   ```java
   var a = IntVector.fromArray(SPECIES_256, data, 0);
   var b = IntVector.fromArray(SPECIES_256, data, 8);
   var c = a.add(b);
   ```
5. **Foreign Linker API (Incubator)** - Panama
6. **ZGC Concurrent Thread Stack Processing**
7. **Unix-Domain Socket Channels**
8. **Packaging Tool Updates** - jpackage
9. **Strongly Encapsulate JDK Internals**

**Theoretical Keywords**: Records standard, Pattern matching standard, Vector API

---

## **Java 17 (LTS) - September 2021**
** Answer:**
1. **Sealed Classes (Standard)**
   ```java
   public sealed interface Expr 
       permits ConstantExpr, PlusExpr, TimesExpr { }
   ```
2. **Pattern Matching for switch (Preview)**
   ```java
   return switch (obj) {
       case Integer i -> i;
       case String s -> Integer.parseInt(s);
       default -> 0;
   };
   ```
3. **Foreign Function & Memory API (Incubator)**
4. **Enhanced Pseudo-Random Number Generators**
5. **New macOS Rendering Pipeline**
6. **Deprecate Applet API for Removal**
7. **Strongly Encapsulate JDK Internals**
8. **Switch Pattern Matching (Preview)**
9. **Remove RMI Activation**
10. **Deprecate Security Manager for Removal**

**Theoretical Keywords**: Sealed classes standard, Pattern matching switch, LTS, Panama API

---

## **Java 18 (JDK 18) - March 2022**
** Answer:**
1. **UTF-8 by Default** - Standard charset
2. **Simple Web Server** - jwebserver command
3. **Code Snippets in JavaDoc** - @snippet tag
4. **Internet-Address Resolution SPI** - Custom resolvers
5. **Vector API (Third Incubator)**
6. **Foreign Function & Memory API (Second Incubator)**
7. **Pattern Matching for switch (Second Preview)**
8. **Deprecate Finalization for Removal**

**Theoretical Keywords**: UTF-8 default, Web server, JavaDoc snippets, Vector API

---

## **Java 19 (JDK 19) - September 2022**
** Answer:**
1. **Virtual Threads (Preview)** - Project Loom
   ```java
   Thread.startVirtualThread(() -> {
       System.out.println("Virtual thread");
   });
   ```
2. **Pattern Matching for switch (Third Preview)**
3. **Record Patterns (Preview)**
   ```java
   if (obj instanceof Point(int x, int y)) {
       System.out.println(x + ", " + y);
   }
   ```
4. **Foreign Function & Memory API (Preview)**
5. **Vector API (Fourth Incubator)**
6. **Structured Concurrency (Incubator)**
   ```java
   try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
       Future<String> user = scope.fork(() -> findUser());
       Future<Integer> order = scope.fork(() -> fetchOrder());
       scope.join();
       return new Response(user.resultNow(), order.resultNow());
   }
   ```
 
**Theoretical Keywords**: Virtual threads, Record patterns, Structured concurrency, Project Loom

---

## **Java 20 (JDK 20) - March 2023**
** Answer:**
1. **Virtual Threads (Second Preview)**
2. **Scoped Values (Preview)** - Replace ThreadLocal
3. **Record Patterns (Second Preview)**
4. **Pattern Matching for switch (Fourth Preview)**
5. **Foreign Function & Memory API (Second Preview)**
6. **Vector API (Fifth Incubator)**
7. **Structured Concurrency (Second Incubator)**

**Theoretical Keywords**: Virtual threads preview, Scoped values, Pattern matching

---

## **Java 21 (LTS) - September 2023**
** Answer:**
1. **Virtual Threads (Standard)** - Production ready
2. **Sequenced Collections** - New interfaces
   ```java
   interface SequencedCollection<E> extends Collection<E> {
       void addFirst(E);
       void addLast(E);
       E getFirst();
       E getLast();
   }
   ```
3. **Record Patterns (Standard)**
4. **Pattern Matching for switch (Standard)**
5. **String Templates (Preview)**
   ```java
   String name = "Joan";
   String info = STR."My name is \{name}";
   ```
6. **Scoped Values (Preview)**
7. **Structured Concurrency (Preview)**
8. **Foreign Function & Memory API (Third Preview)**
9. **Key Encapsulation Mechanism API**
10. **Deprecate Windows 32-bit x86 Port**

**Key Features Summary**:
- **Virtual Threads**: Lightweight threads for high-throughput apps
- **Pattern Matching**: Cleaner instanceof and switch code
- **Records**: Immutable data carriers
- **String Templates**: Safer string interpolation

**Theoretical Keywords**: Virtual threads standard, Sequenced collections, String templates, LTS

---

## **Java 22 (JDK 22) - March 2024**
** Answer:**
1. **Statements before super(...) (Preview)**
   ```java
   public class Sub extends Super {
       public Sub(int param) {
           if (param <= 0) throw new IllegalArgumentException();
           super(param);
       }
   }
   ```
2. **String Templates (Second Preview)**
3. **Implicitly Declared Classes (Preview)** - Simplified main
   ```java
   void main() {  // No class declaration needed
       System.out.println("Hello");
   }
   ```
4. **Foreign Function & Memory API (Second Preview)**
5. **Class-File API (Preview)**
6. **Stream Gatherers (Preview)** - Custom intermediate ops
7. **Scoped Values (Second Preview)**
8. **Structured Concurrency (Second Preview)**

**Theoretical Keywords**: Statements before super, Simplified main, Stream gatherers

---

## **Quick Reference Cheat Sheet**

| **Version** | **Year** | **Key Features** |
|------------|----------|------------------|
| **Java 5** | 2004 | Generics, For-each, Autoboxing, Enums, Varargs |
| **Java 6** | 2006 | Scripting, Compiler API, JDBC 4.0 |
| **Java 7** | 2011 | Diamond operator, Try-with-resources, NIO.2 |
| **Java 8** | 2014 | **Lambda, Streams, Optional**, Date/Time API |
| **Java 9** | 2017 | Module system, JShell, Factory collections |
| **Java 10** | 2018 | **var** keyword |
| **Java 11** | 2018 | **HTTP Client**, Single-file execution, **LTS** |
| **Java 14** | 2020 | **Records** (preview), Pattern matching instanceof |
| **Java 16** | 2021 | **Records** (standard), Pattern matching (standard) |
| **Java 17** | 2021 | **Sealed classes**, Pattern matching switch, **LTS** |
| **Java 21** | 2023 | **Virtual threads**, String templates, **LTS** |

** Tips**:
1. **Know Java 8, 11, 17, 21 thoroughly** - These are LTS versions
2. **Be able to compare features** across versions
3. **Understand the evolution** - Why features were added
4. **Practice coding examples** for each major feature
5. **Know which features are preview/incubator vs standard**

**You now have comprehensive knowledge of Java 5 through current versions!** This is invaluable for , especially when discussing modern Java development and migration strategies. 🚀