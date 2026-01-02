# Java Interview Questions & Answers Guide
## Topic: CONDITIONS & LOOPS (Questions 79-90)
### For 2-Year Experienced Java Backend Developers

---

### 79. Why should you always use blocks around an if statement?

**Expected Answer (2-Year Level):**
Blocks (`{}`) avoid ambiguity and bugs when adding/removing lines. Without braces only the next statement is controlled by the `if`, which often leads to maintenance errors.

**Example:**
```java
if (x > 5)
    System.out.println("Hello");
    System.out.println("World"); // This always executes regardless of if
```
Correct with braces:
```java
if (x > 5) {
    System.out.println("Hello");
    System.out.println("World");
}
```

**Key Theoretical Concepts:**
- Statement grouping, readability, and avoiding the "dangling else"/maintenance bugs

**Interviewer Expectation:**
Should understand why braces are safer and common style guidelines recommend always using them.

**Red Flags:**
- Not understanding the scope of `if` without braces

**Depth Expected:** Practical

---

### 80. Guess the output

Code:
```java
int x = 5;
if (x = 5) {
    System.out.println("A");
} else {
    System.out.println("B");
}
```

**Expected Answer (2-Year Level):**
This code will not compile. In Java `if` requires a boolean expression. `x = 5` is an assignment (int), not boolean. Correct would be `if (x == 5)`.

**Key Theoretical Concepts:**
- Type checking in Java's `if`

**Interviewer Expectation:**
Should spot the compile-time error and explain correct operator (`==`).

**Red Flags:**
- Thinking assignment in if is allowed (C-style) in Java

**Depth Expected:** Beginner

---

### 81. Guess the output

Code:
```java
boolean flag = false;
if (flag = true) {
    System.out.println("YES");
} else {
    System.out.println("NO");
}
```

**Expected Answer (2-Year Level):**
This compiles and prints `YES`. `flag = true` assigns `true` to `flag` and evaluates to `true`. However, this is almost always a bug; use `==` for comparison.

**Key Theoretical Concepts:**
- Assignment expression returns value for boolean
- Avoid accidental assignment in condition

**Interviewer Expectation:**
Should point out the assignment and recommend `if (flag)` or `if (flag == true)`.

**Red Flags:**
- Not noticing the assignment

**Depth Expected:** Beginner

---

### 82. Guess the output of this switch block

Code:
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

**Expected Answer (2-Year Level):**
Output:
```
Two
Three
Default
```
Because there are no `break` statements; execution falls through from `case 2`.

**Key Theoretical Concepts:**
- Fall-through behavior in switch
- Importance of `break`

**Interviewer Expectation:**
Should know fall-through and demonstrate consequences.

**Red Flags:**
- Assuming no fall-through

**Depth Expected:** Beginner to Intermediate

---

### 83. Guess the output of this switch block

Code:
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

**Expected Answer (2-Year Level):**
Output:
```
Monday
```
Because Java supports String in switch (since Java 7), and `case "MON"` matches.

**Key Theoretical Concepts:**
- Switch support for strings (Java 7+)
- Use of `break` to prevent fall-through

**Interviewer Expectation:**
Should confirm understanding of String switch and matching.

**Red Flags:**
- Thinking String switch is not allowed

**Depth Expected:** Beginner

---

### 84. Should default be the last case in a switch statement?

**Expected Answer (2-Year Level):**
No, `default` can appear anywhere. However, putting it last is conventional and clearer. If `default` is not last, remember fall-through rules and `break`s.

**Key Theoretical Concepts:**
- Switch order doesn't matter for matching but affects fall-through.

**Interviewer Expectation:**
Should explain convention vs language rules.

**Red Flags:**
- Insisting default must be last

**Depth Expected:** Beginner

---

### 85. Can a Switch statement be used around a String?

**Expected Answer (2-Year Level):**
Yes. Java supports switching on `String` since Java 7. The switch uses `String.equals()` semantics after computing hash for efficient dispatch.

**Key Theoretical Concepts:**
- Java 7 language feature
- Internals: uses hashing and equality checks

**Interviewer Expectation:**
Should know it's allowed and practical.

**Depth Expected:** Beginner to Intermediate

---

### 86. Guess the output of this for loop

Code:
```java
for (int i = 0; i < 5; i++) {
    System.out.print(i + " ");
}
```

**Expected Answer (2-Year Level):**
Output:
```
0 1 2 3 4 
```

**Interviewer Expectation:**
Simple loop comprehension.

**Depth Expected:** Beginner

---

### 87. What is an Enhanced For Loop?

**Expected Answer (2-Year Level):**
Also known as the "for-each" loop, introduced in Java 5 to iterate over arrays and `Iterable` types easily:
```java
for (String s : list) {
    System.out.println(s);
}
```

**Key Theoretical Concepts:**
- Read-only iteration: cannot change loop variable to modify collection elements
- Simpler syntax

**Interviewer Expectation:**
Should know use-cases and limitations (no index, can't remove elements from collection while iterating)

**Red Flags:**
- Assuming it's always equivalent to indexed loop for mutation

**Depth Expected:** Beginner

---

### 88. What is the output of the for loop below?

Code:
```java
int[] nums = {10, 20, 30};
for (int x : nums) {
    System.out.print(x + " ");
}
```

**Expected Answer (2-Year Level):**
Output:
```
10 20 30 
```

**Interviewer Expectation:**
Should show understanding of enhanced for loop on arrays.

**Depth Expected:** Beginner

---

### 89. What is the output of the program below?

Code:
```java
int[] arr = {1, 2, 3};
for (int x : arr) {
    x = x * 10;
}
System.out.println(arr[0]);
```

**Expected Answer (2-Year Level):**
Output: `1`.
Because `x` is a copy of the array element (primitive), modifying `x` does not change the array contents.

**Key Theoretical Concepts:**
- Primitive vs reference semantics
- Enhanced for loop copies primitive values

**Interviewer Expectation:**
Should explain why the array is unchanged.

**Red Flags:**
- Expecting `10` as output

**Depth Expected:** Intermediate

---

### 90. What is the output of the program below?

Code:
```java
int i = 0;
for (System.out.println("Start"); i < 3; i++) {
    System.out.println(i);
}
```

**Expected Answer (2-Year Level):**
Output:
```
Start
0
1
2
```
The `for` loop's initialization section can contain statements including a `System.out.println`. The loop prints Start once, then 0,1,2.

**Key Theoretical Concepts:**
- Structure of for loop: `for (init; condition; update)`
- init can be expressions with side effects

**Interviewer Expectation:**
Should understand for-loop structure and ordering.

**Red Flags:**
- Not knowing init is evaluated once before loop starts

**Depth Expected:** Intermediate

---

End of CONDITIONS & LOOPS (Questions 79-90)
