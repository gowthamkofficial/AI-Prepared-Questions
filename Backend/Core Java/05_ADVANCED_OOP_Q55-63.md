
## Topic: ADVANCED OBJECT ORIENTED CONCEPTS (Questions 55-63)


---

### 55. WHAT IS POLYMORPHISM?

**Expected Answer :**
Polymorphism allows objects to be treated as instances of their parent type and the correct overridden method is invoked at runtime. It means many forms — for example, a `Shape` reference can point to `Circle` or `Rectangle` and calling `draw()` will execute the specific implementation.

**Key Theoretical Concepts:**
- Runtime polymorphism (method overriding)
- Compile-time polymorphism (method overloading)
- Liskov Substitution Principle

**Interviewer Expectation:**
Should give a clear example and explain runtime dispatch.

**Red Flags:**
- Confusing overloading and overriding

**Depth Expected:** Intermediate

---

### 56. WHAT IS THE USE OF INSTANCEOF OPERATOR IN JAVA?

**Expected Answer :**
`instanceof` checks if an object is an instance of a given class or interface (including subclasses). Useful before downcasting to avoid `ClassCastException`.

**Example:**
```java
if (obj instanceof String) {
    String s = (String) obj;
}
```

**Key Theoretical Concepts:**
- Type checks at runtime
- Safer downcasting

**Interviewer Expectation:**
Should know syntax and typical usage.

**Red Flags:**
- Overusing `instanceof` as design crutch

**Depth Expected:** Beginner to Intermediate

---

### 57. WHAT IS COUPLING?

**Expected Answer :**
Coupling describes how interdependent modules/components are. Low coupling is desirable — components interact through well-defined interfaces.

**Key Theoretical Concepts:**
- Tight vs loose coupling
- Dependency inversion and modularity

**Interviewer Expectation:**
Should explain why low coupling improves maintainability and give small examples.

**Red Flags:**
- Not appreciating importance of decoupling in design

**Depth Expected:** Intermediate

---

### 58. WHAT IS COHESION?

**Expected Answer :**
Cohesion describes how closely related responsibilities within a single module/class are. High cohesion (a class doing one thing) is preferred.

**Key Theoretical Concepts:**
- Single Responsibility Principle
- Modularity and separation of concerns

**Interviewer Expectation:**
Should explain benefits of high cohesion.

**Red Flags:**
- Confusing coupling and cohesion

**Depth Expected:** Intermediate

---

### 59. WHAT IS ENCAPSULATION?

**Expected Answer :**
Encapsulation hides internal details and exposes behavior via well-defined APIs. Achieved via access modifiers and getters/setters to protect internal state.

**Key Theoretical Concepts:**
- Information hiding
- Access modifiers, immutability

**Interviewer Expectation:**
Should provide example using `private` fields and public accessors.

**Red Flags:**
- Using public fields liberally

**Depth Expected:** Beginner to Intermediate

---

### 60. WHAT IS AN INNER CLASS?

**Expected Answer :**
An inner class is a class defined within another class. Types include member inner classes, static nested classes, local classes (inside methods), and anonymous classes.

**Key Theoretical Concepts:**
- Access to outer class members
- Scoping and encapsulation

**Interviewer Expectation:**
Should identify types and provide short examples for each.

**Red Flags:**
- Misunderstanding static nested vs member inner classes

**Depth Expected:** Intermediate

---

### 61. WHAT IS A STATIC INNER CLASS?

**Expected Answer :**
A static nested class is declared static and does not have a reference to the outer instance. It behaves like a top-level class nested for packaging reasons and can access only static members of the outer class.

**Key Theoretical Concepts:**
- No implicit outer `this`
- Use for grouping related classes

**Interviewer Expectation:**
Should show example and explain memory/visibility implications.

**Red Flags:**
- Expecting static nested class to access instance members without an instance

**Depth Expected:** Intermediate

---

### 62. CAN YOU CREATE AN INNER CLASS INSIDE A METHOD?

**Expected Answer :**
Yes — local classes can be declared inside methods. They can access final or effectively final local variables (Java 8+ allows effectively final). They are useful for short-lived helper logic.

**Key Theoretical Concepts:**
- Local class scoping
- Access rules for local variables

**Interviewer Expectation:**
Should show an example and mention variable capture rules.

**Red Flags:**
- Not mentioning final/effectively final restriction

**Depth Expected:** Intermediate

---

### 63. WHAT IS AN ANONYMOUS CLASS?

**Expected Answer :**
An anonymous class is an inline class without a name often used to provide a quick implementation of an interface or extend a class. Example commonly used for event listeners or comparators before lambdas.

**Example:**
```java
Runnable r = new Runnable() { public void run() { System.out.println("run"); }};
```

**Key Theoretical Concepts:**
- Syntax and scoping
- Replaced by lambdas for functional interfaces

**Interviewer Expectation:**
Should be able to write a short example and note lambda replacement.

**Red Flags:**
- Confusing anonymous class with lambda expression

**Depth Expected:** Intermediate

---

End of ADVANCED OBJECT ORIENTED CONCEPTS (Questions 55-63)
