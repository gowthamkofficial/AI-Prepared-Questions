
## 79. Why should you always use blocks around an if statement?

### Given Code:

```java
if (x > 5)
    System.out.println("Hello");
    System.out.println("World");

```

### Interviewer Answer:

* Without braces, **only the first statement** is part of the `if` block
* The second `println("World")` always executes regardless of condition
* This can lead to logical bugs that are hard to spot
* Always use braces for clarity, consistency, and to prevent errors
* Even single statements should have braces for maintainability
* **Output:** If : "Hello" "World"; If : "World"

### Theoretical Keywords:

**Code block scope**, **Braces necessity**, **Readability**, **Bug prevention**, **Maintenance**

---

## 80. Guess the output

### Given Code:

```java
int x = 5;
if (x = 5) {  // COMPILE ERROR
    System.out.println("A");
} else {
    System.out.println("B");
}

```

### Interviewer Answer:

* **Compile Error:** `if (x = 5)` tries to assign 5 to x
* In Java, `if` requires a **boolean expression**
* `x = 5` is an assignment that returns `int` (5), not boolean
* Should be `if (x == 5)` for comparison
* Common typo/bug for beginners

### Theoretical Keywords:

**Assignment vs comparison**, **Boolean expression required**, **Common typo**, **Compile-time error**

---

## 81. Guess the output

### Given Code:

```java
boolean flag = false;
if (flag = true) {
    System.out.println("YES");
} else {
    System.out.println("NO");
}

```

### Interviewer Answer:

* **Output:** "YES"
* `flag = true` assigns true to flag and returns true
* The if condition evaluates to true (assignment returns the assigned value)
* This is valid but likely a bug - probably meant `flag == true`
* Always use `if (flag)` or `if (!flag)` for booleans

### Theoretical Keywords:

**Boolean assignment**, **Assignment returns value**, **Valid but buggy**, **Boolean shorthand**

---

## 82. Guess the output of this switch block

### Given Code:

```java
int x = 2;
switch (x) {
    case 1:
        System.out.println("One");
    case 2:
        System.out.println("Two");
    case 3:
        System.out.println("Three");
    default:
        System.out.println("Default");
}

```

### Interviewer Answer:

* **Output:**

```text
Two
Three
Default

```

* Switch **falls through** to subsequent cases when no `break` is used
* Execution starts at `case 2:`, then continues through `case 3:` and `default:`
* Always use `break` (or `return`, `throw`) unless fall-through is intentional
* Java 14+ has enhanced switch with arrow syntax that doesn't fall through

### Theoretical Keywords:

**Switch fall-through**, **Break statement necessity**, **Case penetration**, **Enhanced switch (Java 14+)**

---

## 83. Guess the output of this switch block

### Given Code:

```java
String day = "MON";
switch (day) {
    case "MON":
        System.out.println("Monday");
        break;
    case "TUE":
        System.out.println("Tuesday");
        break;
    default:
        System.out.println("Unknown");
}

```

### Interviewer Answer:

* **Output:** "Monday"
* String switches are allowed (Java 7+)
* Case matches "MON" exactly
* `break` prevents fall-through to other cases
* **Case-sensitive** comparison

### Theoretical Keywords:

**String switch (Java 7+ )**, **Case sensitivity**, **Break prevents fall-through**, **Exact match**

---

## 84. Should default be the last case in a switch statement?

### Interviewer Answer:

* **No**, `default` can appear anywhere in switch statement
* However, convention is to put it last for readability
* Execution order depends on matching case, not position
* If placed in middle, it needs `break` to prevent fall-through
* **Example with default in middle:**

```java
switch (x) {
    default: System.out.println("Unknown"); break;
    case 1: System.out.println("One"); break;
}

```

### Theoretical Keywords:

**Default position flexibility**, **Convention vs requirement**, **Execution order**, **Readability**

---

## 85. Can a switch statement be used around a String?

### Interviewer Answer:

* **Yes, since Java 7**
* Uses `String.equals()` for comparison (case-sensitive)
* Null values cause `NullPointerException` (handle with null check)
* More readable than long if-else chain for string comparisons
* **Example:**

```java
switch (input) {
    case "YES": // OK
    case "NO":  // OK
    // null: would throw NPE
}

```

### Theoretical Keywords:

**Java 7 feature**, **String.equals() comparison**, **Case-sensitive**, **Null safety required**

---

## 86. Guess the output of this for loop

### Given Code:

```java
for (int i = 0; i < 5; i++) {
    System.out.print(i + " ");
}

```

### Interviewer Answer:

* **Output:** "0 1 2 3 4 "
* Loop executes 5 times with i values: 0, 1, 2, 3, 4
* When i becomes 5, condition  is false, loop ends
* Classic for loop pattern: **initialization; condition; increment**

### Theoretical Keywords:

**For loop execution**, **Loop counter**, **Condition evaluation**, **Increment operator**

---

## 87. What is an enhanced for loop?

### Interviewer Answer:

* Also called **for-each loop** (introduced in Java 5)
* Simplified syntax for iterating over arrays and Collections
* Cannot modify array/collection during iteration (except through iterator)
* No index variable available (use regular for loop if needed)
* **Syntax:** `for (Type element : collection)`
* **Example:**

```java
for (String name : namesList) {
    System.out.println(name);
}

```

### Theoretical Keywords:

**For-each loop**, **Java 5 feature**, **Iteration simplification**, **Read-only iteration**, **Collection/Array traversal**

---

## 88. What is the output of the for loop below?

### Given Code:

```java
int[] nums = {10, 20, 30};
for (int x : nums) {
    System.out.print(x + " ");
}

```

### Interviewer Answer:

* **Output:** "10 20 30 "
* Enhanced for loop iterates over array elements
* `x` takes values: 10, then 20, then 30
* Equivalent to traditional: `for (int i = 0; i < nums.length; i++)`

### Theoretical Keywords:

**Array iteration**, **Enhanced for loop**, **Element access**, **Sequential traversal**

---

## 89. What is the output of the program below?

### Given Code:

```java
int[] arr = {1, 2, 3};
for (int x : arr) {
    x = x * 10;  // Modifies local variable x, not array element
}
System.out.println(arr[0]);

```

### Interviewer Answer:

* **Output:** "1"
* In enhanced for loop, `x` is a **local copy** of array element
* Modifying `x` does **NOT** modify the original array
* Use traditional for loop or access via index to modify array
* If array contained objects, you could modify object state but not reference

### Theoretical Keywords:

**Value copy**, **Local variable modification**, **Array unaffected**, **Primitive vs object behavior**

---

## 90. What is the output of the program below?

### Given Code:

```java
int i = 0;
for (System.out.println("Start"); i < 3; i++) {
    System.out.println(i);
}

```

### Interviewer Answer:

* **Output:**

```text
Start
0
1
2

```

* For loop components are flexible
* **Initialization:** `System.out.println("Start")` executes once at start
* **Condition:**  checked before each iteration
* **Increment:** `i++` executes after each iteration
* Valid but unusual - initialization can be any statement

### Theoretical Keywords:

**For loop flexibility**, **Initialization statement**, **Statement execution order**, **Valid syntax**

---
