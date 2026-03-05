# MISCELLANEOUS JAVA TOPICS

---

## 109. What are the default values in an array?

###  Answer:

* Depends on the array element type:
* **Numeric types** (byte, short, int, long): `0`
* **Floating-point** (float, double): `0.0`
* **boolean:** `false`
* **char:** `'\u0000'` (null character)
* **Object references:** `null`


* Default initialization occurs when the array is created but not explicitly initialized
* **Example:** `int[] numbers = new int[5];` (all elements are 0)

### Theoretical Keywords:

**Default initialization**, **Type-specific defaults**, **Zero/null/false**, **Array creation**, **Memory allocation**

---

## 110. How do you loop around an array using enhanced for loop?

###  Answer:

* Use the **for-each loop** syntax introduced in Java 5
* **Format:** `for (elementType variable : array)`
* Provides **read-only iteration** (cannot modify the array structure/size)
* Cleaner syntax than traditional for loop
* **Example:**

```java
String[] names = {"Alice", "Bob", "Charlie"};
for (String name : names) {
    System.out.println(name);
}

```

* Use a traditional for loop if the index is needed for logic

### Theoretical Keywords:

**For-each loop**, **Enhanced for loop**, **Read-only iteration**, **Simplified syntax**, **No index access**

---

## 111. How do you print the content of an array?

###  Answer:

* **Arrays.toString():** For 1D arrays—returns a formatted string like `[1, 2, 3]`
* **Arrays.deepToString():** For multi-dimensional arrays (nested arrays)
* **Manual looping:** Using for/foreach loops to print elements individually
* **Java 8+ Streams:** `Arrays.stream(arr).forEach(System.out::println)`
* **Warning:** Direct `System.out.println(arr)` prints the **classname + @ + hashcode** (e.g., `[I@15db9742`)

### Theoretical Keywords:

**Arrays.toString()**, **Arrays.deepToString()**, **Manual iteration**, **Stream API**, **Hashcode pitfall**

---

## 112. How do you compare two arrays?

###  Answer:

* **Arrays.equals():** Compares 1D arrays element by element (checks length and values)
* **Arrays.deepEquals():** Compares multi-dimensional arrays deeply
* **Manual comparison:** Loop through and compare elements manually
* **== operator:** Only compares **references** (checks if they are the exact same object in memory)
* For object arrays, `Arrays.equals()` uses the `equals()` method of the elements

### Theoretical Keywords:

**Arrays.equals()**, **Arrays.deepEquals()**, **Reference vs content comparison**, **Element-wise comparison**, **Object.equals()**

---

## 113. What is an enum?

###  Answer:

* A special class representing a **group of constants** (fixed set of values)
* Introduced in Java 5 to replace integer constants (e.g., `public static final int MONDAY = 1`)
* **Type-safe:** The compiler prevents assigning invalid values
* Can have fields, methods, and constructors
* Implicitly extends `java.lang.Enum`

### Theoretical Keywords:

**Enumeration**, **Type-safe constants**, **Fixed set of values**, **Special class**, **Enum class**, **Java 5 feature**

---

## 114. Can you use a switch statement around an enum?

###  Answer:

* **Yes**, and it is highly recommended for readability
* All enum constants should ideally be handled or covered by a `default` case
* Inside the `case` labels, you do **not** need to qualify with the enum name
* **Example:**

```java
Day day = Day.MONDAY;
switch (day) {
    case MONDAY: // Correct: just the constant
        System.out.println("Start of week");
        break;
}

```

### Theoretical Keywords:

**Switch compatibility**, **Type safety**, **Case validation**, **Simplified syntax**, **Compile-time checking**

---

## 115. What are variable arguments or varargs?

###  Answer:

* Feature allowing methods to accept **zero or more** arguments of a specified type
* **Syntax:** `type... variableName` (ellipsis)
* The arguments are treated as an **array** inside the method
* **Restriction:** Must be the **last parameter** in the method signature
* **Example:** `public int sum(int... numbers) { ... }` can be called as `sum(1, 2)` or `sum()`

### Theoretical Keywords:

**Variable arguments**, **Ellipsis syntax**, **Zero or more arguments**, **Array parameter**, **Last parameter requirement**

---

## 116. What are asserts used for?

###  Answer:

* Used to verify **assumptions** in code during development
* **Syntax:** `assert condition;` or `assert condition : message;`
* Throws an `AssertionError` if the condition is false
* **Crucial:** They are **disabled by default** at runtime. Use the `-ea` (enable assertions) flag to turn them on

### Theoretical Keywords:

**Assertion checking**, **Development tool**, **AssertionError**, **Runtime disabling (-ea flag)**, **Internal invariants**

---

## 117. When should asserts be used?

###  Answer:

* **Internal invariants:** Checking assumptions that should logically always be true
* **Control flow invariants:** Marking code paths that should be "unreachable"
* **Development/Testing:** Catching logic errors before production
* **Never use for:** Validating user input or public API arguments (use Exceptions for those)

### Theoretical Keywords:

**Internal consistency**, **Programmer errors**, **Development phase**, **Not production validation**, **Contract verification**

---

## 118. What is garbage collection?

###  Answer:

* **Automatic memory management** process in Java
* The JVM reclaims memory from objects that are no longer **reachable** (no references point to them)
* Prevents manual memory management errors like memory leaks or dangling pointers
* **Algorithms:** Mark-and-Sweep, Generational, G1, ZGC, etc.

### Theoretical Keywords:

**Automatic memory management**, **Memory reclamation**, **Reachability analysis**, **Memory leak prevention**, **JVM feature**

---

## 119. Can you explain garbage collection with an example?

###  Answer:

```java
String s1 = new String("Hello"); // Object A created
s1 = null; // Object A is now eligible for GC (unreachable)

```

* Objects become eligible for GC when they are no longer accessible via any live thread
* `System.gc()` is a request/suggestion to the JVM, but the JVM chooses when to actually run it

### Theoretical Keywords:

**Reference eligibility**, **Null assignment**, **Unreachable objects**, **System.gc() suggestion**, **Finalization**

---

## 120. When is garbage collection run?

###  Answer:

* **Heap pressure:** When the heap is nearly full or memory is low
* **Generational triggers:** Minor GC runs when the "Young Generation" is full
* **Major triggers:** Full GC runs when the "Old Generation" is full
* **JVM Discretion:** The timing is implementation-specific and depends on the collector used (e.g., G1 vs Parallel)

### Theoretical Keywords:

**Heap pressure**, **JVM discretion**, **Generational collection**, **Minor/Major GC**, **Background threads**, **Implementation dependent**

---

## 121. What are best practices on garbage collection?

###  Answer:

* **Don't over-manage:** Avoid calling `System.gc()` manually
* **Scope management:** Keep object lifetimes short (local variables)
* **Avoid Finalizers:** Use `try-with-resources` or `Cleaners` (Java 9+) instead
* **Profiling:** Use tools like VisualVM or JConsole to monitor memory usage
* **Selection:** Choose the right collector (e.g., G1GC for general apps, ZGC for low latency)

### Theoretical Keywords:

**Minimal intervention**, **Scope management**, **Finalizer avoidance**, **Memory profiling**, **JVM tuning**, **Collector selection**

---

## 122. What are initialization blocks?

###  Answer:

* Blocks of code that run during class or object initialization
* **Two types:**
1. **Static:** Runs once when the class is loaded
2. **Instance:** Runs every time an object is created


* They provide a way to perform complex initialization that a simple assignment can't handle

### Theoretical Keywords:

**Initialization blocks**, **Static initializer**, **Instance initializer**, **Execution order**, **Complex initialization**

---

## 123. What is a static initializer?

###  Answer:

* A code block marked `static { ... }` that runs **once** when the class is loaded
* Used for initializing static variables or one-time setups (like loading native libraries)
* Executes **before** any constructors or instance blocks
* Cannot access non-static (instance) members

### Theoretical Keywords:

**Class loading time**, **One-time execution**, **Static variable initialization**, **Before constructors**, **No instance access**

---

## 124. What is an instance initializer block?

###  Answer:

* A code block `{ ... }` that runs **each time** an instance is created
* Executes **after** the superclass constructor (`super()`) but **before** the current class constructor body
* Useful for sharing code across multiple constructors
* Can access instance variables and methods

### Theoretical Keywords:

**Per-object execution**, **Constructor complement**, **Shared initialization**, **After super() before constructor**, **Instance member access**

---

## 125. What is tokenizing?

###  Answer:

* The process of breaking a String into smaller pieces (**tokens**) based on **delimiters** (e.g., space, comma)
* Essential for parsing data formats like CSV or log files
* **Methods:** `String.split()` (regex), `Scanner`, or the legacy `StringTokenizer`

### Theoretical Keywords:

**String splitting**, **Delimiters**, **Parsing**, **Tokens**, **String.split()**, **StringTokenizer**, **Scanner**

---

## 126. Can you give an example of tokenizing?

###  Answer:

```java
// String.split()
String data = "apple,banana,cherry";
String[] tokens = data.split(","); // ["apple", "banana", "cherry"]

// Scanner
Scanner s = new Scanner("10 20 30");
while(s.hasNextInt()) { int n = s.nextInt(); }

```

### Theoretical Keywords:

**split() method**, **Regular expression delimiter**, **StringTokenizer iteration**, **Scanner parsing**, **Multiple delimiters**

---

## 127. What is serialization?

###  Answer:

* The process of converting an object's state into a **byte stream**
* Used for **persistence** (saving to disk) or **transmission** (sending across a network)
* Deserialization is the reverse process (byte stream to object)
* Requires the class to implement the `Serializable` **marker interface**

### Theoretical Keywords:

**Object to byte stream**, **Persistence**, **Network transmission**, **Serializable interface**, **Marker interface**

---

## 128. How do you serialize an object using the Serializable interface?

###  Answer:

* Make the class implement `java.io.Serializable`
* Use `ObjectOutputStream` wrapped around a `FileOutputStream`
* Call `writeObject(yourObject)`
* All non-transient instance variables must also be serializable

### Theoretical Keywords:

**Serializable implementation**, **ObjectOutputStream**, **writeObject()**, **Try-with-resources**, **File storage**

---

## 129. How do you de-serialize in Java?

###  Answer:

* Use `ObjectInputStream` wrapped around a `FileInputStream`
* Call `readObject()` and **cast** it to the expected class type
* Requires the class definition to be present in the classpath
* Must handle `ClassNotFoundException` and `IOException`

### Theoretical Keywords:

**ObjectInputStream**, **readObject()**, **Type casting**, **ClassNotFoundException**, **Classpath requirement**

---

## 130. What do you do if only parts of the object have to be serialized?

###  Answer:

* Use the **`transient`** keyword: Fields marked `transient` are skipped during serialization
* **Customization:** Implement `writeObject()` and `readObject()` methods within your class
* **Externalizable:** Implement the `Externalizable` interface for complete manual control over the byte stream

### Theoretical Keywords:

**transient keyword**, **Custom serialization**, **writeObject/readObject**, **Externalizable**, **Serialization proxy**, **Default values**

---

## 131. How do you serialize a hierarchy of objects?

###  Answer:

* If a superclass is `Serializable`, all its subclasses are **automatically** serializable
* If the superclass is **not** serializable but the subclass is:
* The superclass must have a **no-arg constructor**
* The subclass is responsible for saving/restoring the superclass state if needed



### Theoretical Keywords:

**Inheritance requirement**, **No-arg constructor**, **Superclass non-serialization**, **Field inheritance**, **Serializable inheritance**

---

## 132. Are the constructors in an object invoked when it is de-serialized?

###  Answer:

* **No**, for the serializable class itself, the constructor is **not called**
* The object is reconstructed by reading its fields directly from the stream
* **Exception:** The no-arg constructor of the first **non-serializable** superclass in the hierarchy **is** called

### Theoretical Keywords:

**No constructor invocation**, **Direct field population**, **readObject() for initialization**, **defaultReadObject()**, **Non-serializable superclass**

---

## 133. Are the values of static variables stored when an object is serialized?

###  Answer:

* **No**, static variables are **not** serialized
* Serialization applies to the **state of an instance**
* Since `static` variables belong to the class and not a specific object, they are ignored
* Upon deserialization, the object will see the **current** value of the static variable in the JVM

### Theoretical Keywords:

**Class-level storage**, **Not per-object**, **Class loader scope**, **Current value retention**, **No serialization**

---
