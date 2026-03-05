
# STRINGS ANSWERS

---

## 16. Are all strings immutable?

###  Answer:
- Yes, String objects in Java are immutable
- Once created, their value cannot be changed
- Operations like `concat()`, `toUpperCase()` return **NEW** String objects
- Immutability enables security, thread-safety, and string pooling
- StringBuilder and StringBuffer are mutable alternatives

### Theoretical Keywords:
**Immutable objects**, **String pool**, **Thread safety**,  
**Security**, **Flyweight pattern**, **Hashcode caching**

---

## 17. Where are string values stored in memory?

###  Answer:
- String Literals: Stored in **String Pool** (special area in Heap)
- String Objects created with **new**: Stored in regular Heap memory
- String Pool helps memory optimization by reusing identical literals
- **intern()** method can move heap strings to string pool
- Java 7+ moved String Pool from **PermGen** to regular Heap

### Theoretical Keywords:
**String Pool**, **Heap memory**, **String literal**,  
**intern() method**, **PermGen vs Heap**, **Memory optimization**

---

## 18. Why should you be careful about string concatenation (+) in loops?

###  Answer:
- Strings are immutable, so each concatenation creates a new String object
- This leads to excessive object creation and garbage collection overhead
- Memory and performance inefficient for large loops
- Time complexity becomes **O(n²)** instead of **O(n)**
- Example: Loop with 1000 iterations creates ~1000 temporary String objects

### Theoretical Keywords:
**Immutability overhead**, **Object creation**, **Garbage collection**,  
**Performance degradation**, **O(n²) complexity**, **Memory inefficiency**

---

## 19. How do you solve the above problem?

###  Answer:
- Use **StringBuilder** for single-threaded scenarios
- Use **StringBuffer** for multi-threaded scenarios
- Pre-allocate capacity if final size is known
- Avoid concatenation in loops - use `append()`
- Use `join()` or **StringJoiner**

### Theoretical Keywords:
**StringBuilder**, **StringBuffer**, **append() method**,  
**Capacity pre-allocation**, **Thread safety**, **StringJoiner**

---

## 20. What are differences between String and StringBuffer?

###  Answer:
- Mutability: String immutable, StringBuffer mutable
- Performance: StringBuffer faster for multiple modifications
- Thread Safety: StringBuffer is synchronized (thread-safe)
- Memory: String creates many objects, StringBuffer modifies in-place
- Use Case: String for constants, StringBuffer for multi-threaded use

### Theoretical Keywords:
**Immutable vs mutable**, **Synchronized vs non-synchronized**,  
**Thread safety**, **In-place modification**, **Performance comparison**

---

## 21. What are differences between StringBuilder and StringBuffer?

###  Answer:
- Thread Safety: StringBuffer synchronized, StringBuilder not synchronized
- Performance: StringBuilder faster in single-threaded environments
- API: Both have same methods
- Use Case: StringBuilder single-threaded, StringBuffer multi-threaded
- Version: StringBuilder added in Java 5

### Theoretical Keywords:
**Synchronization**, **Thread safety**, **Performance optimization**,  
**Single-threaded vs multi-threaded**, **Java 5 feature**, **API compatibility**

---

## 22. Can you give examples of different utility methods in the String class?

###  Answer:
- Length/Check: `length()`, `isEmpty()`, `isBlank()` (Java 11+)
- Search: `indexOf()`, `lastIndexOf()`, `contains()`, `startsWith()`, `endsWith()`
- Substring: `substring()`, `split()`, `join()` (Java 8+)
- Modification: `toUpperCase()`, `toLowerCase()`, `trim()`, `strip()` (Java 11+)
- Comparison: `equals()`, `equalsIgnoreCase()`, `compareTo()`
- Conversion: `valueOf()`, `toCharArray()`, `format()`

### Theoretical Keywords:
**String manipulation**, **Search methods**, **Substring extraction**,  
**Case conversion**, **Trimming whitespace**, **Comparison methods**,  
**Conversion utilities**







