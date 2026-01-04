

## 55. What is polymorphism?

### Interviewer Answer:

* Ability of an object to **take many forms** ("poly" = many, "morph" = forms)
* Two types:
1. **Compile-time polymorphism:** Method overloading
2. **Runtime polymorphism:** Method overriding (superclass reference, subclass object)


* Enables writing **flexible and extensible** code

### Theoretical Keywords:

**Many forms**, **Method overloading**, **Method overriding**, **Dynamic method dispatch**, **Flexible design**

---

## 56. What is the use of the instanceof operator in Java?

### Interviewer Answer:

* Checks if an object is an **instance of a specific class** or interface
* Returns **boolean**: true if object is instance, false otherwise
* Used before casting to avoid **ClassCastException**
* Checks inheritance hierarchy (returns true for superclasses too)

### Theoretical Keywords:

**Type checking**, **Safe casting**, **ClassCastException prevention**, **Inheritance checking**, **Boolean operator**

---

## 57. What is coupling?

### Interviewer Answer:

* **Degree of interdependence** between software modules
* **Low coupling is desirable:** modules communicate through well-defined interfaces
* **High coupling is problematic:** changes in one module require changes in others
* Goal: **Minimize coupling** to improve maintainability and testability

### Theoretical Keywords:

**Module interdependence**, **Design quality metric**, **Low coupling goal**, **Maintainability**, **Interface-based communication**

---

## 58. What is cohesion?

### Interviewer Answer:

* **Degree to which elements** within a module belong together
* **High cohesion is desirable:** module has single, well-focused responsibility
* **Low cohesion is problematic:** module does unrelated things
* Related to **Single Responsibility Principle**

### Theoretical Keywords:

**Module focus**, **Single responsibility**, **Functional relatedness**, **Readability**, **Maintainability**

---

## 59. What is encapsulation?

### Interviewer Answer:

* **Bundling data (fields)** and methods that operate on that data within a single unit (class)
* **Hiding internal state** and requiring interaction through public methods
* Achieved using **access modifiers**: private, protected, public
* Protects **object integrity** by preventing unauthorized access

### Theoretical Keywords:

**Data hiding**, **Access modifiers (private/public/protected)**, **Getter/Setter methods**, **Object integrity**, **Information hiding**

---

## 60. What is an inner class?

### Interviewer Answer:

* A class defined **within another class** (outer class)
* Has access to **all members** of outer class, including private members
* Used for logical grouping and encapsulation
* Four types: Non-static, Static, Local, and Anonymous

### Theoretical Keywords:

**Nested class**, **Outer class access**, **Logical grouping**, **Encapsulation**, **Four types**

---

## 61. What is a static inner class?

### Interviewer Answer:

* Nested class declared as **static**
* Doesn't have access to instance members of outer class (only static members)
* Can be **instantiated without** outer class instance
* Commonly used for grouping related classes

### Theoretical Keywords:

**static keyword**, **No outer instance needed**, **Static members only**, **Independent instantiation**

---

## 62. Can you create an inner class inside a method?

### Interviewer Answer:

* **Yes**, called **local inner class**
* Defined within a method body
* Can only be instantiated **within that method**
* Can access local variables only if they are **final or effectively final** (Java 8+)

### Theoretical Keywords:

**Local inner class**, **Method scope**, **Final/effectively final variables**, **Limited instantiation**

---

## 63. What is an anonymous class?

### Interviewer Answer:

* A class **without a name**, defined and instantiated in a single expression
* Used to **override methods** of a class or interface on the fly
* Commonly used for **event listeners and callbacks**
* Syntax: `new ClassOrInterface() { // implementations }`

### Theoretical Keywords:

**Nameless class**, **Inline implementation**, **Single expression**, **Event listeners**, **Override methods**

Would you like me to generate a summary table comparing **Overloading vs Overriding** or provide a deeper dive into **Solid Principles**?


