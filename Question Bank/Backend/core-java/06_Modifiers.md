

# MODIFIERS & CONTROL FLOW ANSWERS

---

## 64. What is default (package) class modifier?

###  Answer:

* When no access modifier is specified for a class, it gets **package-private/default access**
* Class is accessible **only within its own package**
* Cannot be accessed from classes in other packages
* Used for package-level encapsulation
* **Example:** `class PackageClass { }` (no public/private/protected)

### Theoretical Keywords:

**Package-private**, **No explicit modifier**, **Package-level access**, **Package encapsulation**, **Implicit access**

---

## 65. What is private access modifier?

###  Answer:

* **Most restrictive** access level
* Accessible **only within the declared class**
* Not accessible from subclasses or other classes (even in same package)
* Used for complete encapsulation of implementation details
* Commonly applied to fields with public getters/setters

### Theoretical Keywords:

**Most restrictive**, **Class-only access**, **Encapsulation**, **Implementation hiding**, **Getter/Setter pattern**

---

## 66. What is default or package access modifier?

###  Answer:

* Applied when **no access modifier is specified**
* Accessible **only within same package**
* Not accessible from different packages (even subclasses in different packages)
* Middle ground between `private` and `protected`
* Used for package-internal implementation

### Theoretical Keywords:

**Package-private**, **No modifier specified**, **Package boundary**, **Internal implementation**

---

## 67. What is protected access modifier?

###  Answer:

* Accessible within **same package AND by subclasses** (even in different packages)
* Broader than default, but more restrictive than public
* Subclasses can access protected members through inheritance
* Used when you want to expose to subclasses but not to entire world
* **Example:** Framework classes often use protected for extension points

### Theoretical Keywords:

**Package + subclass access**, **Inheritance access**, **Framework extension**, **Subclass-only exposure**

---

## 68. What is public access modifier?

###  Answer:

* **Least restrictive** access level
* Accessible from anywhere: same class, same package, different package, subclasses
* Used for API that needs to be exposed to all consumers
* Classes/interfaces that form public API should be public
* Should be used judiciously to maintain encapsulation

### Theoretical Keywords:

**Least restrictive**, **Global access**, **Public API**, **Interface exposure**, **Judicious use**

---

## 69. What access types of variables can be accessed from a class in the same package?

###  Answer:

* **public:** Yes
* **protected:** Yes
* **default (package-private):** Yes
* **private:** No
* All except private members are accessible within same package

### Theoretical Keywords:

**Package access scope**, **Private exclusion**, **Same-package visibility**

---

## 70. What access types of variables can be accessed from a class in a different package?

###  Answer:

* **public:** Yes
* **protected:** No (unless through inheritance)
* **default (package-private):** No
* **private:** No
* Only public members are accessible from different packages (without inheritance)

### Theoretical Keywords:

**Cross-package access**, **Public-only (non-inheritance)**, **Package boundary**

---

## 71. What access types of variables can be accessed from a subclass in the same package?

###  Answer:

* **public:** Yes
* **protected:** Yes
* **default (package-private):** Yes
* **private:** No
* Same as regular class in same package - private is still restricted

### Theoretical Keywords:

**Subclass + same package**, **Private exclusion**, **Inheritance + package access**

---

## 72. What access types of variables can be accessed from a subclass in a different package?

###  Answer:

* **public:** Yes
* **protected:** Yes (through inheritance only)
* **default (package-private):** No
* **private:** No
* Protected members are accessible to subclasses across packages, but default are not

### Theoretical Keywords:

**Cross-package subclass**, **Protected inheritance access**, **Default restriction**

---

## 73. What is the use of a final modifier on a class?

###  Answer:

* Prevents the class from being **extended** (cannot have subclasses)
* Makes the class immutable in terms of inheritance
* All methods are implicitly final (cannot be overridden)
* Used for security, immutability, or design intent
* **Example:** `String`, `Integer`, `Math` classes are final

### Theoretical Keywords:

**Non-extendable**, **Inheritance prevention**, **Implicit method finality**, **Security**, **Immutability**

---

## 74. What is the use of a final modifier on a method?

###  Answer:

* Prevents the method from being **overridden** in subclasses
* Used when method implementation should not be changed
* Improves performance (allows compiler optimizations)
* Used in template method pattern (make algorithm skeleton, keep key steps final)
* Private methods are implicitly final

### Theoretical Keywords:

**Non-overridable**, **Implementation locking**, **Performance optimization**, **Template method pattern**

---

## 75. What is a final variable?

###  Answer:

* A variable whose value **cannot be changed** once initialized
* Must be initialized either at declaration or in constructor
* **For primitives:** value cannot change
* **For objects:** reference cannot change, but object state can change (unless immutable)
* Convention: `UPPER_CASE` naming for constants
* **Example:** `final int MAX_SIZE = 100;`

### Theoretical Keywords:

**Constant value**, **Reference immutability**, **Mandatory initialization**, **State mutability**, **Naming convention**

---

## 76. What is a final argument?

###  Answer:

* A method parameter declared as **final**
* Cannot be reassigned within the method
* Prevents accidental modification of parameter reference
* Used for clarity and to prevent bugs
* **Example:**

```java
void process(final List<String> items) {
    // items = new ArrayList<>(); // COMPILE ERROR
    items.add("OK"); // Allowed - modifying object, not reference
}

```

### Theoretical Keywords:

**Parameter immutability**, **Reference reassignment prevention**, **Code clarity**, **Bug prevention**

---

## 77. What happens when a variable is marked as volatile?

###  Answer:

* Ensures variable reads/writes go **directly to main memory** (not CPU cache)
* Guarantees **visibility** of changes across threads
* Prevents compiler reordering optimizations around volatile access
* Does **NOT** provide atomicity (for that, use `AtomicInteger`, `synchronized`)
* Used for flags, status variables in multithreading
* **Example:** `private volatile boolean running = true;`

### Theoretical Keywords:

**Memory visibility**, **Main memory access**, **No thread caching**, **Happens-before guarantee**, **Not atomic**

---

## 78. What is a state variable?

###  Answer:

* A variable that represents the **state/condition** of an object or system
* Typically an instance variable that affects object behavior
* Changes to state variables should be controlled (often through methods)
* In multithreading, state variables need proper synchronization
* **Example:** `isRunning`, `count`, `connectionStatus`

### Theoretical Keywords:

**Object state**, **Instance variable**, **Behavioral influence**, **Controlled modification**, **Thread safety**

---
