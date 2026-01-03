
## Topic: OBJECT ORIENTED PROGRAMMING BASICS (Questions 23-54)


---

### 23. What is a Class?

**Expected Answer :**
A class is a blueprint or template that defines the structure (fields) and behavior (methods) of objects. It declares state and behaviors that objects created from the class will have.

**Key Theoretical Concepts:**
- Abstraction and modeling
- Fields (attributes) and methods (behaviors)
- Access modifiers and encapsulation

**Interviewer Expectation:**
Should be able to define a class and give a simple example.

**Red Flags:**
- Confusing class with object or package

**Depth Expected:** Beginner

---

### 24. What is an Object?

**Expected Answer :**
An object is an instance of a class containing actual values for the fields and allowing method invocation. Objects represent real-world entities.

**Key Theoretical Concepts:**
- Instance-level state
- Identity, state, and behavior

**Interviewer Expectation:**
Should give example of `new User()` and explain instance fields vs class members.

**Red Flags:**
- Not understanding instances vs. classes

**Depth Expected:** Beginner

---

### 25. What is state of an Object?

**Expected Answer :**
State refers to the values of an object's fields at a given time. For example, a `User` object might have `name`, `email`, and `age` representing its state.

**Key Theoretical Concepts:**
- Encapsulation of data
- Mutable vs immutable state

**Interviewer Expectation:**
Should explain state is stored in instance variables and may change over time.

**Red Flags:**
- Mixing state with behavior

**Depth Expected:** Beginner

---

### 26. What is behavior of an Object?

**Expected Answer :**
Behavior is what an object can do — the methods it exposes. For example, `User.login()`, `User.logout()` are behaviors.

**Key Theoretical Concepts:**
- Methods implement behavior
- Separation of concerns between state and behavior

**Interviewer Expectation:**
Should provide examples and show understanding of responsibilities.

**Red Flags:**
- Treating fields as behavior

**Depth Expected:** Beginner

---

### 27. What is the super class of every class in Java?

**Expected Answer :**
`java.lang.Object` is the superclass of every class in Java. All classes implicitly extend `Object` if they don't explicitly extend another class.

**Key Theoretical Concepts:**
- Inheritance hierarchy
- Methods from Object: `toString()`, `equals()`, `hashCode()`, `getClass()`, `notify()`/`wait()`

**Interviewer Expectation:**
Should know `Object` and core methods that are commonly overridden.

**Red Flags:**
- Not knowing `Object` is the root

**Depth Expected:** Beginner

---

### 28. Explain about toString method?

**Expected Answer :**
`toString()` is a method in `Object` that returns a string representation of the object. By default it returns `ClassName@hexHashCode`. It's common to override `toString()` to provide human-readable information about object state.

**Key Theoretical Concepts:**
- Overriding from `Object`
- Readability and logging

**Interviewer Expectation:**
Should know to override `toString()` for debugging/logging and give a quick example.

**Red Flags:**
- Relying on default `toString()` for logs in production

**Depth Expected:** Beginner

---

### 29. What is the use of equals method in Java?

**Expected Answer :**
`equals()` tests logical equality between objects. The default implementation in `Object` compares references (`==`). Many classes override `equals()` to compare meaningful fields.

**Key Theoretical Concepts:**
- Contract between `equals()` and `hashCode()`
- Symmetry, reflexivity, transitivity

**Interviewer Expectation:**
A 2-year developer should know to override `equals()` when logical equality is required and be aware of the `hashCode()` contract.

**Red Flags:**
- Overriding `equals()` without updating `hashCode()`
- Using `==` to compare strings

**Depth Expected:** Intermediate

---

### 30. What are the important things to consider when implementing equals method?

**Expected Answer :**
Follow the equals contract: reflexive, symmetric, transitive, consistent, and `x.equals(null)` must return false. Use `instanceof` or `getClass()` check, compare same set of significant fields, and override `hashCode()` accordingly.

**Key Theoretical Concepts:**
- equals/hashCode contract
- Null handling
- Performance considerations

**Interviewer Expectation:**
Should mention null checks, type checks, and consistent fields, and show simple example code.

**Red Flags:**
- Forgetting `hashCode()`
- Comparing floating-point numbers incorrectly without tolerance

**Depth Expected:** Intermediate

---

### 31. What is the hashCode method used for in Java?

**Expected Answer :**
`hashCode()` returns an integer hash for the object used in hash-based collections (HashMap, HashSet). If two objects are equal per `equals()`, they must have the same `hashCode()`. Unequal objects can share hash codes but it reduces performance.

**Key Theoretical Concepts:**
- hash-based data structures
- equals/hashCode contract
- Distribution and collisions

**Interviewer Expectation:**
Should understand the relationship with hash collections and why consistent implementation matters.

**Red Flags:**
- Implementing hashCode() poorly (always return constant)

**Depth Expected:** Intermediate

---

### 32. Explain inheritance with Examples.

**Expected Answer :**
Inheritance allows a class to reuse fields and methods of another class (`extends`). Example:
```java
class Animal { void eat() {} }
class Dog extends Animal { void bark() {} }
```
`Dog` inherits `eat()` and can add `bark()`.

**Key Theoretical Concepts:**
- Code reuse
- IS-A relationship
- Method overriding
- Single class inheritance in Java

**Interviewer Expectation:**
Should provide a small example and explain benefits and caveats (tight coupling, fragile base class problem).

**Red Flags:**
- Misusing inheritance where composition is better

**Depth Expected:** Intermediate

---

### 33. WHAT IS METHOD OVERLOADING?

**Expected Answer :**
Method overloading is defining methods in the same class with the same name but different parameter lists (type, number, or order). Return type alone cannot distinguish overloads.

**Key Theoretical Concepts:**
- Compile-time polymorphism
- Signature differences

**Interviewer Expectation:**
Should give an example and note it's resolved at compile time.

**Red Flags:**
- Thinking return type is enough to overload

**Depth Expected:** Beginner

---

### 34. WHAT IS METHOD OVERRIDING?

**Expected Answer :**
Method overriding is providing a new implementation in a subclass for a method declared in a superclass with the same signature and compatible return type. It's run-time polymorphism.

**Key Theoretical Concepts:**
- Dynamic binding
- Access modifier rules (cannot reduce visibility)
- `@Override` annotation

**Interviewer Expectation:**
Should show example of overriding and mention `super` to call parent implementation.

**Red Flags:**
- Overriding static methods (not possible)
- Changing method signature accidentally

**Depth Expected:** Intermediate

---

### 35. CAN SUPER CLASS REFERENCE VARIABLE CAN HOLD AN OBJECT OF SUB CLASS?

**Expected Answer :**
Yes. A superclass reference can point to a subclass instance (polymorphism). This enables calling overridden methods on the object at runtime.

**Key Theoretical Concepts:**
- Polymorphism and Liskov Substitution Principle
- Upcasting (implicit) and downcasting (explicit)

**Interviewer Expectation:**
Should demonstrate with code and explain limitations (can't call subclass-specific methods without casting).

**Red Flags:**
- Trying to call subclass-only methods without cast

**Depth Expected:** Intermediate

---

### 36. IS MULTIPLE INHERITANCE ALLOWED IN JAVA?

**Expected Answer :**
Java does not support multiple class inheritance to avoid the diamond problem. However, multiple inheritance of type is possible via interfaces (a class can implement multiple interfaces). Since Java 8, interfaces can have default methods which can reintroduce ambiguity handled by rules.

**Key Theoretical Concepts:**
- Diamond problem
- Interfaces vs classes
- Default method conflict resolution

**Interviewer Expectation:**
Should explain Java's approach and give example of implementing multiple interfaces.

**Red Flags:**
- Saying Java supports multiple class inheritance

**Depth Expected:** Intermediate

---

### 37. WHAT IS AN INTERFACE?

**Expected Answer :**
An interface declares a contract of methods a class can implement. Prior to Java 8 interfaces could only have abstract methods; since Java 8 they can have default and static methods. Interfaces are used to define capabilities and allow multiple inheritance of type.

**Key Theoretical Concepts:**
- Contract, loose coupling
- Default and static methods (Java 8+)
- Functional interfaces (single abstract method)

**Interviewer Expectation:**
Should know the role of interfaces and basic syntax.

**Red Flags:**
- Confusing interfaces with abstract classes

**Depth Expected:** Intermediate

---

### 38. HOW DO YOU DEFINE AN INTERFACE?

**Expected Answer :**
```java
public interface Printable {
    void print();
    default void log() { System.out.println("logging"); }
    static int getDefaultValue() { return 42; }
}
```

**Key Theoretical Concepts:**
- `interface` keyword
- Abstract methods implicitly public
- Default/static methods allowed since Java 8

**Interviewer Expectation:**
Should write a short example and explain access modifiers for interface methods/fields (fields are `public static final`).

**Red Flags:**
- Declaring non-final fields inside interface incorrectly

**Depth Expected:** Beginner to Intermediate

---

### 39. HOW DO YOU IMPLEMENT AN INTERFACE?

**Expected Answer :**
```java
public class Report implements Printable {
    @Override
    public void print() {
        System.out.println("Printing report");
    }
}
```

**Key Theoretical Concepts:**
- `implements` keyword
- Must implement all abstract methods or declare class abstract

**Interviewer Expectation:**
Should show code and mention `@Override`.

**Red Flags:**
- Not implementing all methods without making class abstract

**Depth Expected:** Beginner

---

### 40. CAN YOU EXPLAIN A FEW TRICKY THINGS ABOUT INTERFACES?

**Expected Answer :**
Tricky points:
- Interfaces can have default and static methods (Java 8+)
- Multiple interfaces can define default methods with same signature — class must resolve by overriding
- Fields are implicitly `public static final` (constants)
- Methods in interface are implicitly `public` (unless private in later Java versions)
- Since Java 9, interfaces can have `private` methods for default methods to reuse code

**Key Theoretical Concepts:**
- Default method conflict resolution
- Interface evolution

**Interviewer Expectation:**
A 2-year dev should know default methods and basic conflict resolution rules.

**Red Flags:**
- Saying interfaces can't have method bodies (in modern Java)

**Depth Expected:** Intermediate

---

### 41. CAN YOU EXTEND AN INTERFACE?

**Expected Answer :**
Yes. Interfaces can extend other interfaces using `extends` keyword and inherit abstract/default/static methods.

**Example:**
```java
interface A { void a(); }
interface B extends A { void b(); }
class C implements B { public void a(){} public void b(){} }
```

**Key Theoretical Concepts:**
- Interface inheritance
- Multiple inheritance of interfaces

**Interviewer Expectation:**
Should understand and give example.

**Red Flags:**
- Confusing with class extension

**Depth Expected:** Beginner

---

### 42. CAN A CLASS EXTEND MULTIPLE INTERFACES?

**Expected Answer :**
A class can implement multiple interfaces (use comma-separated `implements`). This is allowed and common.

**Example:**
```java
interface A { void a(); }
interface B { void b(); }
class C implements A, B { public void a(){} public void b(){} }
```

**Key Theoretical Concepts:**
- Multiple type inheritance via interfaces

**Interviewer Expectation:**
Should know syntax and why it's useful.

**Red Flags:**
- Saying Java classes can extend multiple classes

**Depth Expected:** Beginner

---

### 43. WHAT IS AN ABSTRACT CLASS?

**Expected Answer :**
An abstract class can define both abstract methods (without implementation) and concrete methods. It cannot be instantiated directly and is used when there is shared code plus some methods left for subclasses to implement.

**Key Theoretical Concepts:**
- `abstract` keyword
- Partial implementation and state

**Interviewer Expectation:**
Should know when to use abstract class vs interface and show example.

**Red Flags:**
- Using abstract class where interface is better or vice versa without reasoning

**Depth Expected:** Intermediate

---

### 44. WHEN DO YOU USE AN ABSTRACT CLASS?

**Expected Answer :**
Use abstract class when you want to provide common implementation and state (fields) to subclasses, but still force subclasses to implement some methods. When there's a clear is-a relationship and shared behavior/state exists.

**Key Theoretical Concepts:**
- Code reuse with shared state
- Template method pattern

**Interviewer Expectation:**
Should explain design rationale and trade-offs compared to interfaces.

**Red Flags:**
- Choosing abstract class without need for shared state

**Depth Expected:** Intermediate

---

### 45. HOW DO YOU DEFINE AN ABSTRACT METHOD?

**Expected Answer :**
In an abstract class, declare a method without a body and mark it `abstract`:
```java
public abstract class Shape {
    public abstract double area();
}
```

**Key Theoretical Concepts:**
- Subclasses must implement abstract methods unless they are abstract too

**Interviewer Expectation:**
Should write syntax and explain compilation rules.

**Red Flags:**
- Missing `abstract` modifier

**Depth Expected:** Beginner

---

### 46. COMPARE ABSTRACT CLASS VS INTERFACE?

**Expected Answer :**
- Abstract class: can have state (fields), constructors, concrete methods; single inheritance (class extends one abstract class).
- Interface: no instance state (only constants), methods were abstract historically but now can have `default`, `static`, and `private` methods; multiple interfaces can be implemented; used for types/capabilities.

When to pick:
- Use interface for defining capabilities across unrelated classes
- Use abstract class when providing base implementation and shared state

**Key Theoretical Concepts:**
- Multiple inheritance of type vs single class inheritance
- Evolution of interfaces (default/static)

**Interviewer Expectation:**
Should compare practical trade-offs and provide examples.

**Red Flags:**
- Not recognizing default methods change since Java 8

**Depth Expected:** Intermediate

---

### 47. WHAT IS A CONSTRUCTOR?

**Expected Answer :**
A constructor initializes new objects. It has the same name as the class, no return type, and can be overloaded. If no constructor is declared, a default no-arg constructor is provided.

**Key Theoretical Concepts:**
- Object initialization
- Overloaded constructors
- `this()` and `super()` calls

**Interviewer Expectation:**
Should explain purpose and show simple constructor examples.

**Red Flags:**
- Trying to return a value from constructor

**Depth Expected:** Beginner

---

### 48. WHAT IS A DEFAULT CONSTRUCTOR?

**Expected Answer :**
A default constructor is the no-argument constructor provided by the compiler when no constructors are explicitly declared. It calls the superclass no-arg constructor.

**Key Theoretical Concepts:**
- Compiler-generated code
- Behavior when other constructors exist (compiler won't generate default)

**Interviewer Expectation:**
Should know behavior and when it's created.

**Red Flags:**
- Expecting default constructor when a constructor with args is defined

**Depth Expected:** Beginner

---

### 49. WILL THIS CODE COMPILE?

(Question likely references a snippet; since snippet is not provided, explain general guidelines.)

**Expected Answer :**
Explain compile-time checks: types, method signatures, visibility, abstract class instantiation, and missing returns. Evaluate the code per Java rules.

**Key Theoretical Concepts:**
- Syntax and type checking

**Interviewer Expectation:**
Should reason about compilation errors and fix them.

**Red Flags:**
- Inability to read and reason about code

**Depth Expected:** Intermediate reasoning

---

### 50. HOW DO YOU CALL A SUPER CLASS CONSTRUCTOR FROM A CONSTRUCTOR?

**Expected Answer :**
Use `super(...)` as the first statement in the subclass constructor to call the superclass constructor with matching parameters.

**Example:**
```java
class A { A(int x) {} }
class B extends A { B() { super(5); } }
```

**Key Theoretical Concepts:**
- Constructor chaining
- Call to `super()` must be first statement

**Interviewer Expectation:**
Should know syntax and ordering rules.

**Red Flags:**
- Calling `super()` after other statements

**Depth Expected:** Beginner

---

### 51. WILL THIS CODE COMPILE?

(Again depends on snippet.)

**Expected Answer :**
Follow the same approach: identify violations like missing method implementations, unchecked conversions, visibility issues, or static context mistakes.

**Interviewer Expectation:**
Able to reason about code semantics.

**Depth Expected:** Intermediate

---

### 52. WHAT IS THE USE OF THIS(I)?

**Expected Answer :**
`this` refers to the current object instance. `this()` calls another constructor in the same class (constructor chaining). `this.field` differentiates instance field from local variables.

**Key Theoretical Concepts:**
- Reference to current object
- Constructor chaining with `this()` must be first statement

**Interviewer Expectation:**
Should show examples of `this()` and `this.field`.

**Red Flags:**
- Misusing `this` in static context

**Depth Expected:** Beginner

---

### 53. CAN A CONSTRUCTOR BE CALLED DIRECTLY FROM A METHOD?

**Expected Answer :**
No. You cannot call a constructor like a regular method. You can call another constructor using `this()` but only from a constructor. From a method, instantiate an object with `new ClassName(...)`.

**Key Theoretical Concepts:**
- `new` operator and object creation
- `this()` only inside constructors

**Interviewer Expectation:**
Should state the rule and show correct instantiation.

**Red Flags:**
- Trying to use `ClassName()` directly like a method

**Depth Expected:** Beginner

---

### 54. IS A SUPER CLASS CONSTRUCTOR CALLED EVEN WHEN THERE IS NO EXPLICIT CALL FROM A SUB CLASS CONSTRUCTOR?

**Expected Answer :**
Yes. If no explicit `super(...)` is present, the compiler inserts a no-arg `super()` call. If the superclass lacks a no-arg constructor, compilation fails unless subclass explicitly calls an existing superclass constructor.

**Key Theoretical Concepts:**
- Implicit `super()` injection
- Constructor chaining across hierarchy

**Interviewer Expectation:**
Should recognize this rule and explain compilation error scenario.

**Red Flags:**
- Not knowing implicit `super()` behavior

**Depth Expected:** Beginner to Intermediate

---

End of OBJECT ORIENTED PROGRAMMING BASICS (Questions 23-54)
