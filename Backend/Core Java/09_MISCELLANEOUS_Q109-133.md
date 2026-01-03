
## Topic: MISCELLANEOUS (Questions 109-133)


---

### 109. WHAT ARE THE DEFAULT VALUES IN AN ARRAY?

**Expected Answer :**
Default values depend on element type: numeric primitives → 0 (or 0.0), `char` → '\u0000', `boolean` → `false`, object references → `null`.

**Key Theoretical Concepts:**
- JVM zero-initialization of arrays
- Distinguish between local variables (must be initialized) and array elements (auto-initialized)

**Interviewer Expectation:**
Should list defaults for common types and know that arrays are objects on the heap.

**Red Flags:**
- Saying primitives are uninitialized or random

**Depth Expected:** Beginner

---

### 110. HOW DO YOU LOOP AROUND AN ARRAY USING ENHANCED FOR LOOP?

**Expected Answer :**
Use the for-each syntax:
```java
int[] arr = {1,2,3};
for (int x : arr) {
    System.out.println(x);
}
```

**Key Theoretical Concepts:**
- Read-only iteration variable for primitives
- Works for arrays and Iterable types

**Interviewer Expectation:**
Should demonstrate simple usage and limitations (no index access).

**Depth Expected:** Beginner

---

### 111. HOW DO YOU PRINT THE CONTENT OF AN ARRAY?

**Expected Answer :**
Use `Arrays.toString(array)` for one-dimensional arrays and `Arrays.deepToString(array)` for nested arrays. Or iterate and print elements.

**Example:**
```java
int[] arr = {1,2,3};
System.out.println(Arrays.toString(arr)); // [1, 2, 3]
```

**Key Theoretical Concepts:**
- Utility methods in `java.util.Arrays`

**Interviewer Expectation:**
Should mention Arrays utilities, not `System.out.println(arr)` which prints reference.

**Depth Expected:** Beginner

---

### 112. HOW DO YOU COMPARE TWO ARRAYS?

**Expected Answer :**
Use `Arrays.equals(a, b)` for one-dimensional arrays and `Arrays.deepEquals(a, b)` for nested arrays. For sorting-independent comparison, sort or convert to collections if order doesn't matter.

**Key Theoretical Concepts:**
- Element-wise equality
- Deep equality for nested arrays

**Interviewer Expectation:**
Should mention `Arrays.equals` and `deepEquals` and order sensitivity.

**Depth Expected:** Intermediate

---

### 113. WHAT IS AN ENUM?

**Expected Answer :**
`enum` is a special Java type representing a fixed set of constants. Enums are full-featured classes that can have fields, methods, and implement interfaces.

**Example:**
```java
public enum Day { MON, TUE, WED }
```

**Key Theoretical Concepts:**
- Type safety vs constants
- Enum methods: `values()`, `valueOf()`
- Can add constructors and methods

**Interviewer Expectation:**
Should show understanding of using enums for fixed sets and advantages over `int` constants.

**Red Flags:**
- Treating enums as plain integers

**Depth Expected:** Intermediate

---

### 114. CAN YOU USE A SWITCH STATEMENT AROUND AN ENUM?

**Expected Answer :**
Yes. You can switch on enums using `case ENUM_CONSTANT:`.

**Example:**
```java
Day d = Day.MON;
switch (d) {
  case MON: System.out.println("Mon"); break;
}
```

**Key Theoretical Concepts:**
- Compile-time safety with enums

**Interviewer Expectation:**
Should know syntax and benefits.

**Depth Expected:** Beginner

---

### 115. WHAT ARE VARIABLE ARGUMENTS OR VARARGS?

**Expected Answer :**
Varargs allow a method to accept a variable number of arguments of the same type, using `...` syntax.

**Example:**
```java
void log(String... msgs) {
    for (String m : msgs) System.out.println(m);
}
```

**Key Theoretical Concepts:**
- Varargs compile to arrays
- Must be last parameter

**Interviewer Expectation:**
Should know syntax and that varargs are sugar for arrays.

**Depth Expected:** Beginner

---

### 116. WHAT ARE ASSERTS USED FOR?

**Expected Answer :**
`assert` is used for internal sanity checks during development to assert expected conditions. They are typically disabled at runtime and should not replace proper exception handling.

**Key Theoretical Concepts:**
- Assertions enabled via JVM flags (`-ea`)
- Not for argument validation in public APIs

**Interviewer Expectation:**
Should know how to enable and use asserts and limitations.

**Red Flags:**
- Using asserts for input validation in production

**Depth Expected:** Intermediate

---

### 117. WHEN SHOULD ASSERTS BE USED?

**Expected Answer :**
Use asserts for internal consistency checks, invariants, and unreachable code. Do not use for user input validation or error handling.

**Key Theoretical Concepts:**
- Assertions are for developer-time checks

**Interviewer Expectation:**
Should give appropriate examples.

**Depth Expected:** Intermediate

---

### 118. WHAT IS GARBAGE COLLECTION?

**Expected Answer :**
Garbage collection (GC) is automatic memory management where the JVM reclaims memory occupied by objects no longer reachable by the application.

**Key Theoretical Concepts:**
- Reachability analysis (strong/soft/weak/phantom refs)
- GC roots
- Generational collectors (young/old), mark-and-sweep, copying

**Interviewer Expectation:**
Should understand concept and practical effects (pause times, memory leaks via references).

**Red Flags:**
- Thinking GC runs deterministically at fixed times

**Depth Expected:** Intermediate

---

### 119. CAN YOU EXPLAIN GARBAGE COLLECTION WITH AN EXAMPLE?

**Expected Answer :**
Example: If you set `obj = null` and there are no other references to the object, it's eligible for GC. In a long-running server, keeping references in static collections prevents GC and leads to memory leaks.

**Key Theoretical Concepts:**
- Eligibility vs guaranteed collection
- Memory leak patterns: unintentionally retained references

**Interviewer Expectation:**
Should be able to explain reachability and a real-world leak scenario.

**Depth Expected:** Intermediate

---

### 120. WHEN IS GARBAGE COLLECTION RUN?

**Expected Answer :**
GC runs when the JVM decides it's necessary (e.g., low free heap), or can be triggered with `System.gc()` (hint only). It's nondeterministic and implementation-specific.

**Key Theoretical Concepts:**
- GC heuristics, triggers, and `System.gc()` as suggestion

**Interviewer Expectation:**
Should stress nondeterminism and avoidance of relying on GC timing.

**Depth Expected:** Intermediate

---

### 121. WHAT ARE BEST PRACTICES ON GARBAGE COLLECTION?

**Expected Answer :**
Best practices:
- Avoid unnecessary object creation (reuse buffers, StringBuilder)
- Null out long-lived references when no longer needed
- Use weak/soft references for caches
- Tune GC only when needed using metrics; prefer defaults for general apps
- Monitor with tools (jvisualvm, jcmd, GC logs)

**Key Theoretical Concepts:**
- Profiling and measuring before tuning

**Interviewer Expectation:**
Should know practical techniques and tools, not deep GC internals.

**Depth Expected:** Intermediate

---

### 122. WHAT ARE INITIALIZATION BLOCKS?

**Expected Answer :**
Initialization blocks are code blocks in a class executed when an instance is created. They run before the constructor body and after super() call. There are static and instance initializer blocks.

**Example:**
```java
{ // instance initializer
  // runs for each new instance
}
static { // static initializer
  // runs when class is loaded
}
```

**Key Theoretical Concepts:**
- Order: static blocks -> instance initializer -> constructor

**Interviewer Expectation:**
Should know when to use and ordering rules.

**Depth Expected:** Intermediate

---

### 123. WHAT IS A STATIC INITIALIZER?

**Expected Answer :**
A static initializer (`static { ... }`) executes once when the class is loaded. Used to initialize static fields or perform one-time setup.

**Key Theoretical Concepts:**
- Class loading and initialization

**Interviewer Expectation:**
Should understand single-time initialization and exception behavior (ExceptionInInitializerError if fails).

**Depth Expected:** Intermediate

---

### 124. WHAT IS AN INSTANCE INITIALIZER BLOCK?

**Expected Answer :**
An instance initializer (`{ ... }`) runs for each object creation, after `super()` call and before constructor body. Useful when multiple constructors share initialization code.

**Key Theoretical Concepts:**
- Execution order and usage alternatives (private init method)

**Interviewer Expectation:**
Should know use-cases and ordering.

**Depth Expected:** Intermediate

---

### 125. WHAT IS TOKENIZING?

**Expected Answer :**
Tokenizing is splitting a string into tokens based on delimiters. Historically `StringTokenizer` was used, but `String.split()` and `Scanner` are preferred.

**Key Theoretical Concepts:**
- Parsing input, regex-based splitting

**Interviewer Expectation:**
Should know modern methods and that `StringTokenizer` is legacy.

**Depth Expected:** Beginner

---

### 126. CAN YOU GIVE AN EXAMPLE OF TOKENIZING?

**Expected Answer :**
Using `String.split`:
```java
String s = "a,b,c";
String[] parts = s.split(",");
```
Or `Scanner` for tokenizing input by whitespace.

**Key Theoretical Concepts:**
- Regex splitting and edge cases (empty tokens)

**Interviewer Expectation:**
Should show a robust example and mention trimming and empty token handling.

**Depth Expected:** Beginner to Intermediate

---

### 127. WHAT IS SERIALIZATION?

**Expected Answer :**
Serialization is converting an object into a byte stream for storage or transmission; deserialization reconstructs the object from the stream.

**Key Theoretical Concepts:**
- `java.io.Serializable` marker interface
- `serialVersionUID`
- Security and compatibility concerns

**Interviewer Expectation:**
Should know primary purpose and dangers (e.g., exposing internals, incompatible changes).

**Depth Expected:** Intermediate

---

### 128. HOW DO YOU SERIALIZE AN OBJECT USING SERIALIZABLE INTERFACE?

**Expected Answer :**
Make class implement `Serializable`, optionally define `serialVersionUID`, and use `ObjectOutputStream`:
```java
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("o.bin"))) {
    oos.writeObject(obj);
}
```

**Key Theoretical Concepts:**
- Marker interface, default serialization behavior
- Custom serialization via `writeObject`/`readObject`

**Interviewer Expectation:**
Should show code and mention caveats (transient, security).

**Depth Expected:** Intermediate

---

### 129. HOW DO YOU DE-SERIALIZE IN JAVA?

**Expected Answer :**
Use `ObjectInputStream`:
```java
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("o.bin"))) {
    MyClass obj = (MyClass) ois.readObject();
}
```

**Key Theoretical Concepts:**
- Class compatibility, `ClassNotFoundException`
- Security: don't deserialize untrusted data

**Interviewer Expectation:**
Should demonstrate basic code and mention exceptions to handle.

**Depth Expected:** Intermediate

---

### 130. WHAT DO YOU DO IF ONLY PARTS OF THE OBJECT HAVE TO BE SERIALIZED?

**Expected Answer :**
Mark non-serializable or sensitive fields as `transient`, implement custom `writeObject`/`readObject` to control serialization, or use DTOs to serialize only required data.

**Key Theoretical Concepts:**
- `transient` keyword
- Custom serialization methods

**Interviewer Expectation:**
Should suggest secure and maintainable approaches.

**Depth Expected:** Intermediate

---

### 131. HOW DO YOU SERIALIZE A HIERARCHY OF OBJECTS?

**Expected Answer :**
If all classes in the graph implement `Serializable`, Java serializes the object graph recursively. If a superclass is not serializable, you must provide no-arg constructor for that superclass so deserialization can construct it.

**Key Theoretical Concepts:**
- Object graph traversal, references preserved
- Non-serializable parents handling

**Interviewer Expectation:**
Should understand graph semantics and references (shared references preserved).

**Depth Expected:** Intermediate

---

### 132. ARE THE CONSTRUCTORS IN AN OBJECT INVOKED WHEN IT IS DE-SERIALIZED?

**Expected Answer :**
During deserialization, constructors are not run for serializable classes. For non-serializable superclasses, the first non-serializable superclass constructor is invoked.

**Key Theoretical Concepts:**
- Deserialization bypasses normal construction

**Interviewer Expectation:**
Should know this subtle behavior and potential side-effects.

**Depth Expected:** Intermediate

---

### 133. ARE THE VALUES OF STATIC VARIABLES STORED WHEN AN OBJECT IS SERIALIZED?

**Expected Answer :**
No. Static variables belong to the class, not the instance, and are not serialized. You must handle static state separately.

**Key Theoretical Concepts:**
- Instance vs class-level state

**Interviewer Expectation:**
Should be able to explain how to persist shared/static state separately.

**Depth Expected:** Beginner to Intermediate

---

End of MISCELLANEOUS (Questions 109-133)
