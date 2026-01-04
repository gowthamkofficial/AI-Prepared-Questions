# **GENERICS ANSWERS**

## **Generics (Questions 178-184)**

### **178. What are generics?**
**Interviewer Answer:**
- Feature introduced in Java 5 that enables types (classes/interfaces) to be parameters when defining classes, interfaces, and methods
- Provide type safety at compile time by allowing you to specify the type of objects a collection can contain
- Eliminate the need for explicit casting and reduce runtime errors
- Use angle brackets `< >` to specify type parameters
- Example: Instead of `List` (raw type), use `List<String>` to indicate a list that contains only strings

**Theoretical Keywords:**
Type parameters, Compile-time type safety, Java 5 feature, Angle brackets syntax, Generic classes/interfaces/methods

### **179. Why do we need generics? Can you give an example of how generics make a program more flexible?**
**Interviewer Answer:**
**Why we need generics:**
1. **Type Safety**: Catches type mismatches at compile time rather than runtime
2. **Eliminate Casts**: No need for explicit type casting
3. **Code Reusability**: Write code that works with different types
4. **Better Readability**: Clear intent about what types are used

**Example without generics (pre-Java 5):**
```java
List list = new ArrayList();  // Raw type
list.add("Hello");
list.add(123);  // Accidentally adding Integer - compiler doesn't complain
String str = (String) list.get(1);  // ClassCastException at runtime!
```

**Example with generics:**
```java
List<String> list = new ArrayList<>();  // Generic type
list.add("Hello");
list.add(123);  // COMPILE-TIME ERROR: incompatible types
String str = list.get(0);  // No casting needed - compiler knows it's String
```

**Flexibility example - Generic class:**
```java
// One Box class works for any type
Box<String> stringBox = new Box<>();
stringBox.setContent("Hello");

Box<Integer> integerBox = new Box<>();
integerBox.setContent(123);

// Without generics, we'd need separate classes:
// StringBox, IntegerBox, etc.
```

**Theoretical Keywords:**
Type safety, Cast elimination, Reusability, Readability, Runtime error prevention, Polymorphic code

### **180. How do you declare a generic class?**
**Interviewer Answer:**
- Declare type parameter(s) in angle brackets after class name
- Can have multiple type parameters separated by commas
- Type parameters can be used throughout class as actual types
- Convention: Use single uppercase letters (T for Type, E for Element, K for Key, V for Value)

**Example:**
```java
// Single type parameter
public class Box<T> {
    private T content;
    
    public void setContent(T content) {
        this.content = content;
    }
    
    public T getContent() {
        return content;
    }
}

// Multiple type parameters
public class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Usage
Box<String> stringBox = new Box<>();
stringBox.setContent("Hello");

Pair<String, Integer> pair = new Pair<>("Age", 25);
```

**Theoretical Keywords:**
Type parameter declaration, Multiple type parameters, Naming conventions (T, E, K, V), Generic class instantiation, Type inference (diamond operator <>)

### **181. What are the restrictions in using a generic type that is declared in a class declaration?**
**Interviewer Answer:**
1. **Cannot Instantiate Generic Types with Primitive Types**:
   ```java
   // Invalid
   List<int> numbers;  // ERROR
   // Valid
   List<Integer> numbers;  // OK
   ```

2. **Cannot Create Instances of Type Parameters**:
   ```java
   public class Box<T> {
       private T obj = new T();  // COMPILE ERROR
   }
   ```

3. **Cannot Declare Static Fields of Type Parameters**:
   ```java
   public class Box<T> {
       private static T staticField;  // COMPILE ERROR
   }
   ```

4. **Cannot Use Type Parameters in Static Methods (unless method itself is generic)**:
   ```java
   public class Box<T> {
       public static void print(T obj) { }  // ERROR
       
       public static <U> void print(U obj) { }  // OK - method generic
   }
   ```

5. **Cannot Use `instanceof` with Parameterized Types**:
   ```java
   if (obj instanceof List<String>) { }  // ERROR
   if (obj instanceof List) { }  // OK - raw type
   ```

6. **Cannot Create Arrays of Parameterized Types**:
   ```java
   List<String>[] array = new List<String>[10];  // ERROR
   List<String>[] array = new List[10];  // Warning - raw type
   ```

7. **Cannot Throw or Catch Instances of Generic Class**:
   ```java
   // class MyException<T> extends Exception { }  // ERROR
   ```

**Theoretical Keywords:**
Type erasure, Primitive type restriction, Static context limitations, Instanceof restriction, Array creation limitation, Exception type restriction

### **182. How can we restrict generics to a subclass of a particular class?**
**Interviewer Answer:**
- Use **upper bounded wildcard**: `<? extends T>`
- Restricts unknown type to be subtype of T (including T itself)
- Allows reading (covariant) but restricts writing (except null)

**Example:**
```java
// Method that works with any List of Number or its subclasses
public static double sum(List<? extends Number> numbers) {
    double total = 0.0;
    for (Number num : numbers) {
        total += num.doubleValue();  // Can read as Number
    }
    return total;
}

// Usage
List<Integer> integers = Arrays.asList(1, 2, 3);
List<Double> doubles = Arrays.asList(1.5, 2.5, 3.5);
double intSum = sum(integers);  // OK - Integer extends Number
double doubleSum = sum(doubles); // OK - Double extends Number

// Writing restriction example
public static void addNumber(List<? extends Number> numbers) {
    numbers.add(10);     // COMPILE ERROR - cannot add
    numbers.add(10.5);   // COMPILE ERROR - cannot add
    numbers.add(null);   // OK - null is allowed
}
```

**Class declaration with bounded type parameter:**
```java
public class Container<T extends Number> {
    private T value;
    
    public Container(T value) {
        this.value = value;
    }
    
    public double getDoubleValue() {
        return value.doubleValue();  // Can call Number methods
    }
}

// Usage
Container<Integer> intContainer = new Container<>(10);  // OK
Container<Double> doubleContainer = new Container<>(10.5);  // OK
Container<String> stringContainer = new Container<>("hello");  // ERROR
```

**Theoretical Keywords:**
Upper bounded wildcard, extends keyword, Covariant reading, Write restriction, Type parameter bounds, PECS (Producer Extends)

### **183. How can we restrict generics to a super class of a particular class?**
**Interviewer Answer:**
- Use **lower bounded wildcard**: `<? super T>`
- Restricts unknown type to be supertype of T (including T itself)
- Allows writing (contravariant) but restricts reading (only as Object)

**Example:**
```java
// Method that can add integers to any List that can hold integers
public static void addIntegers(List<? super Integer> list) {
    list.add(10);    // OK - can add Integer
    list.add(20);    // OK - can add Integer
    // list.add(10.5);  // ERROR - cannot add Double
    
    // Reading restriction
    Object obj = list.get(0);  // OK - can only read as Object
    // Integer num = list.get(0);  // ERROR - cannot read as Integer
}

// Usage
List<Integer> integerList = new ArrayList<>();
addIntegers(integerList);  // OK

List<Number> numberList = new ArrayList<>();
addIntegers(numberList);   // OK - Number is super of Integer

List<Object> objectList = new ArrayList<>();
addIntegers(objectList);   // OK - Object is super of Integer

List<Double> doubleList = new ArrayList<>();
addIntegers(doubleList);   // ERROR - Double is not super of Integer
```

**Class with lower bound (less common):**
```java
public class Sink<T> {
    public void fill(List<? super T> list, T item) {
        list.add(item);  // Can add T to any list that can hold T
    }
}

// Usage
Sink<Number> sink = new Sink<>();
List<Object> objects = new ArrayList<>();
sink.fill(objects, 10.5);  // OK - adds Number to Object list
```

**Theoretical Keywords:**
Lower bounded wildcard, super keyword, Contravariant writing, Read restriction, PECS (Consumer Super), Type hierarchy flexibility

### **184. Can you give an example of a generic method?**
**Interviewer Answer:**
- Generic method declares its own type parameters, independent of class
- Type parameter scope is limited to the method
- Declared before return type: `<T> returnType methodName(T param)`

**Examples:**

**1. Basic generic method:**
```java
public class Utility {
    // Generic method in non-generic class
    public static <T> T getFirstElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    // Usage
    List<String> strings = Arrays.asList("A", "B", "C");
    String firstString = Utility.getFirstElement(strings);  // Type inference
    
    List<Integer> numbers = Arrays.asList(1, 2, 3);
    Integer firstNumber = Utility.getFirstElement(numbers);
}
```

**2. Multiple type parameters:**
```java
public static <K, V> V getValue(Map<K, V> map, K key) {
    return map.get(key);
}

// Usage
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 95);
Integer score = getValue(scores, "Alice");
```

**3. Bounded generic method:**
```java
// Method to find maximum of any Comparable objects
public static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) > 0 ? a : b;
}

// Usage
String maxString = max("apple", "orange");  // "orange"
Integer maxNumber = max(10, 20);            // 20
```

**4. Generic method with wildcards:**
```java
// Copy from source (producer) to destination (consumer)
public static <T> void copy(List<? extends T> source, List<? super T> dest) {
    for (T item : source) {
        dest.add(item);
    }
}

// Usage
List<Integer> source = Arrays.asList(1, 2, 3);
List<Number> dest = new ArrayList<>();
copy(source, dest);  // OK - Integer extends Number
```

**5. Generic constructor (yes, constructors can be generic too!):**
```java
public class Box<T> {
    private T content;
    
    // Generic constructor
    public <U extends T> Box(U content) {
        this.content = content;
    }
}

// Usage
Box<Number> box = new Box<>(10);  // Integer (U) extends Number (T)
```

**Theoretical Keywords:**
Method type parameters, Type inference, Bounded method type parameters, Generic constructors, Scope limitation, Utility methods

---

**Excellent! You've now mastered Java Generics, a crucial topic for writing type-safe and reusable code. Generics are fundamental to modern Java development and frequently appear in interviews. You're well-prepared to discuss type parameters, wildcards, and bounded types!** 🎯📚