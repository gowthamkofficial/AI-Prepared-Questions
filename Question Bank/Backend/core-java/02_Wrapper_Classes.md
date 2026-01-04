# WRAPPER CLASSES ANSWERS

---

## 7. What are wrapper classes?

### Interviewer Answer:
- Classes that wrap primitive data types into objects
- Eight wrapper classes: **Byte, Short, Integer, Long, Float, Double, Character, Boolean**
- Located in **java.lang** package, automatically imported
- Provide object representation of primitives for use in collections and OOP contexts

### Theoretical Keywords:
**Byte**, **Short**, **Integer**, **Long**, **Float**, **Double**,  
**Character**, **Boolean**, **java.lang**, **Object representation**, **Primitive wrapper**

---

## 8. Why do we need wrapper classes in Java?

### Interviewer Answer:
- Collections (**ArrayList, HashMap**) only work with objects, not primitives
- Allow primitives to participate in object-oriented features (**polymorphism**)
- Provide utility methods for conversion and manipulation (**parseInt, toString**)
- Enable null values for numeric types (primitive int cannot be null, Integer can)
- Required for frameworks that use reflection and generics

### Theoretical Keywords:
**Collections framework**, **Object-only collections**,  
**Utility methods (parseInt)**, **Nullability**, **OOP principles**, **Framework compatibility**

---

## 9. What are the different ways of creating wrapper class instances?

### Interviewer Answer:
- Constructor (deprecated since Java 9): `Integer i = new Integer(10);`
- **valueOf()** method (recommended): `Integer i = Integer.valueOf(10);`
- **Autoboxing**: `Integer i = 10;` (compiler automatically converts)
- Factory methods: `Integer i = Integer.valueOf("10");`

### Theoretical Keywords:
**Constructor initialization**, **valueOf() method**, **Autoboxing**,  
**Factory pattern**, **Deprecated constructors**, **Caching mechanism**

---

## 10. What are differences in the two ways of creating wrapper classes?

### Interviewer Answer:
- Constructor: Always creates new object, even for cached values
- valueOf(): May return cached object for certain ranges (**-128 to 127** for Integer)
- Performance: valueOf() is more efficient due to caching
- Memory: Constructor wastes memory for frequently used values
- Modern practice: Use valueOf() or autoboxing, avoid constructors (deprecated)

### Theoretical Keywords:
**Object creation**, **Caching**, **Flyweight pattern**,  
**Memory efficiency**, **Integer cache (-128 to 127)**, **Deprecation**

---

## 11. What is autoboxing?

### Interviewer Answer:
- Automatic conversion of primitive to corresponding wrapper object
- Example: `Integer i = 10;` instead of `Integer i = Integer.valueOf(10);`
- Also works in reverse: `int x = i;` (**unboxing**)
- Handled by compiler at compile time
- Simplifies code and reduces verbosity

### Theoretical Keywords:
**Automatic conversion**, **Compiler feature**, **Primitive to object**,  
**Unboxing**, **Code simplification**, **Compile-time transformation**

---

## 12. What are the advantages of autoboxing?

### Interviewer Answer:
- Reduces code verbosity and improves readability
- Eliminates manual conversion between primitives and wrappers
- Makes working with collections more intuitive
- Reduces programming errors from manual conversions
- Integrates primitive types seamlessly into object-oriented code

### Theoretical Keywords:
**Code readability**, **Reduced verbosity**, **Error prevention**,  
**Seamless integration**, **Developer productivity**, **Type system unification**

---

## 13. What is casting?

### Interviewer Answer:
- Process of converting one data type to another
- Two types: implicit (**widening**) and explicit (**narrowing**)
- Primitive casting: converting between numeric types
- Reference casting: converting between object types in inheritance hierarchy
- May cause data loss or **ClassCastException** if not done properly

### Theoretical Keywords:
**Type conversion**, **Widening vs narrowing**, **Primitive casting**,  
**Reference casting**, **ClassCastException**, **Type safety**

---

## 14. What is implicit casting?

### Interviewer Answer:
- Automatic type conversion by compiler
- Occurs when converting from smaller to larger type (**widening**)
- No data loss guaranteed
- Example: `int i = 10; double d = i;`
- Also called widening conversion

### Theoretical Keywords:
**Automatic conversion**, **Widening conversion**, **Type promotion**,  
**No data loss**, **Compiler-managed**, **Safe conversion**

---

## 15. What is explicit casting?

### Interviewer Answer:
- Manual type conversion specified by programmer
- Required when converting from larger to smaller type (**narrowing**)
- Potential data loss may occur
- Syntax: `(targetType) value`
- Example: `double d = 10.5; int i = (int) d;` (loses decimal)
- For objects: requires **instanceof** check to avoid ClassCastException

### Theoretical Keywords:
**Manual conversion**, **Narrowing conversion**, **Type casting syntax**,  
**Potential data loss**, **ClassCastException**, **instanceof check**

---
