
# OBJECT ORIENTED PROGRAMMING ANSWERS

---

## 23. What is a class?

### Interviewer Answer:

* A class is a **blueprint or template** for creating objects
* Defines the **structure** (state/attributes as fields) and **behavior** (methods)
* Acts as a **user-defined data type** that encapsulates data and functions
* **Example:** `Car` class with fields like `color`, `model` and methods like `drive()`, `brake()`

### Theoretical Keywords:

**Blueprint**, **Template**, **User-defined type**, **Encapsulation**, **Fields (state)**, **Methods (behavior)**, **Object factory**

---

## 24. What is an object?

### Interviewer Answer:

* An object is a **specific instance** of a class created at runtime
* Has **actual values** for the fields defined in the class
* Occupies **memory** and has a **unique identity**
* **Example:** `myCar` object of `Car` class with `color="Red"`, `model="SUV"`

### Theoretical Keywords:

**Instance**, **Runtime entity**, **Memory allocation**, **State (field values)**, **Identity**, **Reference**

---

## 25. What is the state of an object?

### Interviewer Answer:

* The state represents the **current data values** stored in an object's fields
* Also called **attributes** or **properties**
* Can **change over time** through method calls (unless object is immutable)
* **Example:** A `BankAccount` object's state includes `accountNumber`, `balance`, `ownerName`

### Theoretical Keywords:

**Fields/Attributes**, **Instance variables**, **Data members**, **Object properties**, **Mutable state**

---

## 26. What is the behavior of an object?

### Interviewer Answer:

* The behavior defines **what an object can do**, represented by its **methods**
* Operations that can **read or modify** the object's state
* Defines how objects **interact** with each other
* **Example:** `BankAccount` behaviors: `deposit()`, `withdraw()`, `checkBalance()`

### Theoretical Keywords:

**Methods**, **Operations**, **Object capabilities**, **Public interface**, **Message passing**

---

## 27. What is the superclass of every class in Java?

### Interviewer Answer:

* The **Object class** (`java.lang.Object`) is the superclass of all Java classes
* Directly or indirectly, every class **extends Object**
* Provides default implementations of key methods: `toString()`, `equals()`, `hashCode()`, `getClass()`, `clone()`, `finalize()`
* Enables **polymorphic treatment** of all objects

### Theoretical Keywords:

**java.lang.Object**, **Root class**, **Default methods**, **Inheritance hierarchy**, **Polymorphism foundation**

---

## 28. Explain the toString() method.

### Interviewer Answer:

* Returns a **string representation** of an object
* Default implementation in `Object` class returns: `ClassName@HexadecimalHashCode`
* Should be **overridden** to provide meaningful information about object state
* Automatically called by `System.out.println()` and string concatenation
* Important for **debugging and logging**

### Theoretical Keywords:

**String representation**, **Debugging**, **Object state display**, **Override**, **Automatic invocation**

---

## 29. What is the use of the equals() method in Java?

### Interviewer Answer:

* Used to compare two objects for **logical equality** (value-based comparison)
* Default `Object.equals()` compares **references** (same as `==` operator)
* Should be **overridden** to compare actual content/state of objects
* Used by collections like `HashMap`, `HashSet` for operations
* Must follow contract: **reflexive, symmetric, transitive, consistent**

### Theoretical Keywords:

**Logical equality**, **Value comparison**, **Reference vs content**, **Collection operations**, **Contract**

---

## 30. What are the important things to consider when implementing equals()?

### Interviewer Answer:

* **Reflexive:** `x.equals(x)` must always return true
* **Symmetric:** If `x.equals(y)` is true, then `y.equals(x)` must be true
* **Transitive:** If `x.equals(y)` and `y.equals(z)` are true, then `x.equals(z)` must be true
* **Consistent:** Multiple invocations must return same result unless state changes
* **Null handling:** `x.equals(null)` must return false
* **Type checking:** Use `instanceof` to check object type
* **Field comparison:** Compare all significant fields
* **Override hashCode():** Always override `hashCode()` when overriding `equals()`

### Theoretical Keywords:

**equals contract**, **Reflexive**, **Symmetric**, **Transitive**, **Consistent**, **Null safety**, **Field comparison**, **hashCode consistency**

---

## 31. What is the hashCode() method used for in Java?

### Interviewer Answer:

* Returns an **integer hash code** value for an object
* Used by **hash-based collections** (`HashMap`, `HashSet`, `HashTable`) for efficient storage/retrieval
* Objects with same `equals()` must have same `hashCode()`
* Objects with different `hashCode()` must be `equals() = false`
* Default `Object.hashCode()` typically returns memory address

### Theoretical Keywords:

**Hash code**, **Hash-based collections**, **Bucket distribution**, **Performance optimization**, **equals-hashCode contract**

---

## 32. Explain inheritance with examples.

### Interviewer Answer:

* Mechanism where a **child class (subclass)** inherits properties and behaviors from **parent class (superclass)**
* Promotes **code reuse** and establishes **"is-a" relationship**
* Uses **extends** keyword
* **Example:**

```java
class Vehicle { void move() { } }
class Car extends Vehicle { } // Car IS-A Vehicle
class Bike extends Vehicle { } // Bike IS-A Vehicle

```

* Subclass can add new features or override inherited methods

### Theoretical Keywords:

**IS-A relationship**, **extends keyword**, **Code reuse**, **Hierarchy**, **Superclass/Subclass**, **Method overriding**

---

## 33. What is method overloading?

### Interviewer Answer:

* Having multiple methods with **same name but different parameters** in same class
* Also called **compile-time polymorphism**
* Differs by: **number of parameters, type of parameters, or order of parameters**
* Return type alone doesn't differentiate overloaded methods
* **Example:**

```java
void print(int a) { }
void print(String s) { }
void print(int a, String s) { }

```

### Theoretical Keywords:

**Compile-time polymorphism**, **Same method name**, **Different parameters**, **Static binding**, **Early binding**

---

## 34. What is method overriding?

### Interviewer Answer:

* **Redefining a method** in subclass that's already defined in superclass
* Also called **runtime polymorphism**
* Must have **same method signature** (name, parameters, return type)
* Access modifier cannot be more restrictive than superclass method
* Uses **@Override** annotation (recommended)
* **Example:**

```java
class Animal { void sound() { System.out.println("Animal sound"); } }
class Dog extends Animal { 
    @Override
    void sound() { System.out.println("Bark"); } 
}

```

### Theoretical Keywords:

**Runtime polymorphism**, **Dynamic binding**, **Late binding**, **@Override annotation**, **Signature matching**

---

## 35. Can a superclass reference variable hold an object of a subclass?

### Interviewer Answer:

* **Yes**, this is a fundamental aspect of polymorphism
* Known as **"upcasting"** - automatic and safe
* Can access only superclass methods (and overridden methods of subclass)
* Cannot access subclass-specific methods without explicit casting
* **Example:**

```java
Animal myAnimal = new Dog(); // Valid upcasting
myAnimal.sound(); // Calls Dog's overridden sound()
// myAnimal.fetch(); // ERROR: Animal doesn't have fetch()

```

### Theoretical Keywords:

**Upcasting**, **Polymorphism**, **Reference type vs object type**, **Dynamic method dispatch**

---

## 36. Is multiple inheritance allowed in Java?

### Interviewer Answer:

* **No**, Java doesn't support multiple inheritance of classes (to avoid **diamond problem**)
* A class can extend only one superclass
* However, multiple inheritance is supported through **interfaces**
* A class can implement multiple interfaces
* **Default methods** in interfaces (Java 8+) provide some behavior inheritance

### Theoretical Keywords:

**Diamond problem**, **Single inheritance**, **Multiple interface implementation**, **Default methods**

---

## 37. What is an interface?

### Interviewer Answer:

* A **contract** that defines a set of methods that implementing classes must provide
* Until Java 7: only abstract method declarations (no implementation)
* Java 8+: can have **default and static methods** with implementation
* Java 9+: can have **private methods**
* Represents **"can-do"** relationship rather than "is-a"
* All interface methods are implicitly `public abstract`
* All fields are implicitly `public static final`

### Theoretical Keywords:

**Contract**, **Abstract methods**, **Default methods**, **Static methods**, **Public API**, **Implementation requirement**

---

## 38. How do you define an interface?

### Interviewer Answer:

* Use **interface** keyword instead of class
* Can contain: abstract method declarations, default methods, static methods, constant fields
* **Example:**

```java
public interface Drawable {
    String COLOR = "Black"; // Constant
    void draw(); // Abstract method
    default void resize() { System.out.println("Resizing"); }
    static void printInfo() { System.out.println("Drawable Interface"); }
}

```

### Theoretical Keywords:

**interface keyword**, **Abstract methods**, **Default methods**, **Static methods**, **Constants**, **Java 8 features**

---

## 39. How do you implement an interface?

### Interviewer Answer:

* Use **implements** keyword in class definition
* Class must provide implementation for all **abstract methods** in interface
* Can implement **multiple interfaces** (comma-separated)
* **Example:**

```java
class Circle implements Drawable, Resizable {
    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

```

### Theoretical Keywords:

**implements keyword**, **Method implementation**, **Multiple interfaces**, **@Override**, **Contract fulfillment**

---

## 40. Can you explain a few tricky things about interfaces?

### Interviewer Answer:

* **Default method conflicts:** If class implements two interfaces with same default method, must override it
* **Diamond problem with interfaces:** Resolved by requiring explicit override in implementing class
* **Variables are constants:** All interface variables are implicitly `public static final`
* **No constructors:** Interfaces cannot have constructors
* **Multiple inheritance allowed:** Unlike classes, interfaces support multiple inheritance
* **Functional interfaces:** Single abstract method interfaces (SAM) for lambdas
* **Marker interfaces:** Empty interfaces (like `Serializable`) used for tagging

### Theoretical Keywords:

**Default method conflicts**, **Diamond problem resolution**, **Constants only**, **No constructors**, **Multiple inheritance**, **Functional interfaces**, **Marker interfaces**

---

## 41. Can you extend an interface?

### Interviewer Answer:

* **Yes**, interfaces can extend other interfaces using **extends** keyword
* Can extend **multiple interfaces** (unlike classes)
* Inherits all abstract and default methods from parent interfaces
* **Example:**

```java
interface A { void methodA(); }
interface B { void methodB(); }
interface C extends A, B { // Multiple inheritance allowed
    void methodC();
}

```

### Theoretical Keywords:

**Interface inheritance**, **extends keyword**, **Multiple inheritance**, **Method inheritance**

---

## 42. Can a class implement multiple interfaces?

### Interviewer Answer:

* **Yes**, a class can implement multiple interfaces
* Use comma-separated list after **implements** keyword
* Must provide implementations for all abstract methods from all interfaces
* **Example:**

```java
class SmartPhone implements Camera, Phone, MusicPlayer {
    // Must implement all methods from Camera, Phone, and MusicPlayer
}

```

### Theoretical Keywords:

**Multiple interface implementation**, **implements keyword**, **Contract fulfillment**, **Comma-separated**

---

## 43. What is an abstract class?

### Interviewer Answer:

* A class that **cannot be instantiated** (cannot create objects)
* Declared with **abstract** keyword
* Can contain both **abstract methods** (no body) and **concrete methods** (with body)
* May have **constructors** (called by subclass constructors)
* Used as base class for inheritance hierarchy
* **Example:**

```java
abstract class Animal {
    abstract void sound(); // Abstract method
    void breathe() { System.out.println("Breathing"); } // Concrete method
}

```

### Theoretical Keywords:

**abstract keyword**, **Cannot instantiate**, **Abstract methods**, **Concrete methods**, **Base class**, **Template pattern**

---

## 44. When do you use an abstract class?

### Interviewer Answer:

* **Share code among related classes:** When multiple classes share common code
* **Template Method pattern:** Define skeleton of algorithm in superclass
* **Partial implementation:** Provide some implementation, leave specifics to subclasses
* **Non-final common behavior:** When you want subclasses to extend/override behavior
* **State maintenance:** When base class needs to maintain state (interfaces can't have instance variables)
* **Access modifiers:** Need `protected` or package-private methods

### Theoretical Keywords:

**Code reuse**, **Template Method pattern**, **Partial implementation**, **State sharing**, **Access control**, **Inheritance hierarchy**

---

## 45. How do you define an abstract method?

### Interviewer Answer:

* Declare with **abstract** keyword
* **No method body** (end with semicolon)
* Can only exist in **abstract class** or interface
* Must be implemented by **first concrete subclass**
* **Example:**

```java
abstract class Shape {
    abstract double calculateArea(); // No { } body
}
class Circle extends Shape {
    @Override
    double calculateArea() { return Math.PI * r * r; }
}

```

### Theoretical Keywords:

**abstract keyword**, **No implementation**, **Semicolon termination**, **Mandatory override**, **Contract**

---

## 46. Compare abstract class vs interface.

### Interviewer Answer:

| Feature | Abstract Class | Interface |
| --- | --- | --- |
| **Methods** | Abstract & Concrete | Abstract (Java 8+: Default/Static) |
| **State** | Instance variables allowed | Constants only (`static final`) |
| **Inheritance** | Single inheritance (`extends`) | Multiple inheritance (`implements`) |
| **Constructors** | Can have constructors | No constructors |
| **Access** | Any modifier | Implicitly `public` |

### Theoretical Keywords:

**Inheritance vs Implementation**, **State maintenance**, **Multiple inheritance**, **Code reuse**, **Contract definition**, **Access modifiers**

---

## 47. What is a constructor?

### Interviewer Answer:

* Special method used to **initialize objects** when they're created
* Has **same name as class** and **no return type** (not even void)
* Called automatically when **new** keyword is used
* Can be **overloaded** (multiple constructors with different parameters)
* If no constructor defined, Java provides **default no-arg constructor**

### Theoretical Keywords:

**Object initialization**, **Same name as class**, **No return type**, **Automatic invocation**, **Overloading**, **Default constructor**

---

## 48. What is a default constructor?

### Interviewer Answer:

* A **no-argument constructor** provided by Java compiler if no constructor is explicitly defined
* Takes no parameters and has **empty body**
* Initializes instance variables to **default values** (0, null, false)
* Once any constructor is defined, default constructor is **not provided**

### Theoretical Keywords:

**No-argument constructor**, **Compiler-generated**, **Default initialization**, **Zero/null/false defaults**

---

## 49. Will this code compile?

Assuming: `class Test { Test(int x) {} }` ... `Test t = new Test();`

### Interviewer Answer:

* **No, it won't compile**
* Once a parameterized constructor is defined, Java **doesn't provide** default constructor
* Need to either: define a no-arg constructor, or call the existing constructor with argument
* **Fix:** `Test t = new Test(5);` or add `Test() { }` constructor

### Theoretical Keywords:

**Constructor overloading**, **Default constructor suppression**, **Compile-time error**, **Explicit definition required**

---

## 50. How do you call a superclass constructor from a constructor?

### Interviewer Answer:

* Use **super()** as **first statement** in subclass constructor
* Can pass arguments to call specific superclass constructor
* If not explicitly called, compiler inserts `super()` (calls no-arg super constructor)
* **Example:**

```java
class Child extends Parent {
    Child() { super(10); } // Must be first line
}

```

### Theoretical Keywords:

**super() keyword**, **Constructor chaining**, **First statement requirement**, **Implicit super()**

---

## 51. Will this code compile?

Assuming `Parent` has only `Parent(int x)` and `Child` has `Child() { }` without explicit `super`.

### Interviewer Answer:

* **No, it won't compile**
* Parent has no default constructor
* Compiler tries to insert `super()` but Parent has no no-arg constructor
* Must **explicitly call** `super(int)` in Child constructor

### Theoretical Keywords:

**Implicit super() failure**, **Constructor mismatch**, **Compile-time error**, **Explicit constructor call**

---

## 52. What is the use of this?

### Interviewer Answer:

* **Refer to current instance variables:** Distinguish between instance variables and parameters (`this.name = name`)
* **Call another constructor of same class:** Must be first statement (`this("Unknown")`)
* **Pass current object as parameter:** `obj.method(this)`
* **Return current object:** Return `this` for method chaining

### Theoretical Keywords:

**Current object reference**, **Variable disambiguation**, **Constructor chaining**, **Method chaining**

---

## 53. Can a constructor be called directly from a method?

### Interviewer Answer:

* **No**, constructors cannot be called directly from regular methods
* Constructors are only called during **object creation** with `new` keyword
* Or from other constructors using `this()` or `super()`
* You can create a new object inside a method, which calls the constructor indirectly

### Theoretical Keywords:

**Constructor invocation restrictions**, **new keyword**, **this()/super() calls**, **Object creation context**

---

## 54. Is a superclass constructor called even when there is no explicit call from a subclass constructor?

### Interviewer Answer:

* **Yes, always**
* If no explicit `super()` call, compiler automatically inserts `super()` as **first statement**
* This calls the no-argument constructor of immediate superclass
* Ensures **proper initialization** of inherited members
* Chain continues up to `Object` class constructor

### Theoretical Keywords:

**Implicit super()**, **Constructor chaining**, **Automatic insertion**, **Initialization guarantee**, **Object class root**

---
