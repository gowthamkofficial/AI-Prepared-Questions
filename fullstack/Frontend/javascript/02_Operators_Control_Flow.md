# OPERATORS AND CONTROL FLOW ANSWERS

---

## 11. Types of operators in JavaScript

### Answer:
- **Arithmetic**: `+`, `-`, `*`, `/`, `%`, `**`, `++`, `--`
- **Assignment**: `=`, `+=`, `-=`, `*=`, `/=`, `%=`
- **Comparison**: `==`, `===`, `!=`, `!==`, `>`, `<`, `>=`, `<=`
- **Logical**: `&&`, `||`, `!`, `??` (nullish coalescing)
- **Bitwise**: `&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`
- **Ternary**: `condition ? value1 : value2`
- **Type**: `typeof`, `instanceof`
- **Spread/Rest**: `...`

### Theoretical Keywords:
**Arithmetic operators**, **Comparison operators**, **Logical operators**,  
**Assignment operators**, **Bitwise operators**, **Ternary operator**,  
**Operator precedence**, **Short-circuit evaluation**

---

## 12. Difference between `==` and `===`

### Answer:
- **`==` (Loose Equality)**: Compares values after **type coercion**
- **`===` (Strict Equality)**: Compares values **AND** types (no coercion)
- `==` converts operands to same type before comparison
- `===` returns false if types are different
- **Best Practice**: Always use `===` to avoid unexpected results
- Same applies to `!=` (loose) vs `!==` (strict)

### Theoretical Keywords:
**Type coercion**, **Abstract equality**, **Strict equality**,  
**Implicit conversion**, **Type comparison**, **Falsy values**,  
**Best practices**, **Bug prevention**

### Example:
```javascript
// Loose equality (==) - type coercion
5 == "5"        // true (string converted to number)
0 == false      // true (false converted to 0)
null == undefined // true (special rule)
"" == false     // true

// Strict equality (===) - no coercion
5 === "5"       // false (different types)
0 === false     // false (number vs boolean)
null === undefined // false (different types)
"" === false    // false

// Always prefer ===
let value = "10";
if (value === 10) {  // false - correct behavior
    console.log("Number");
}
```

---

## 13. What are conditional statements?

### Answer:
- Conditional statements execute different code blocks based on conditions
- **if statement**: Executes code if condition is true
- **if-else**: Provides alternative when condition is false
- **else if**: Chains multiple conditions
- **switch**: Tests a value against multiple cases
- **Ternary operator**: Shorthand for simple if-else

### Theoretical Keywords:
**Control flow**, **Boolean evaluation**, **Truthy and falsy values**,  
**Code branching**, **Conditional execution**, **Decision making**,  
**Nested conditions**, **Guard clauses**

### Example:
```javascript
// if-else
let age = 18;
if (age >= 18) {
    console.log("Adult");
} else if (age >= 13) {
    console.log("Teenager");
} else {
    console.log("Child");
}

// Ternary operator
let status = age >= 18 ? "Adult" : "Minor";

// Falsy values: false, 0, "", null, undefined, NaN
if (!"") {  // true (empty string is falsy)
    console.log("Falsy!");
}
```

---

## 14. Difference between `if-else` and `switch`

### Answer:
- **if-else**: Evaluates **boolean expressions**, more flexible
- **switch**: Compares **single value** against multiple cases
- **if-else**: Better for complex conditions and ranges
- **switch**: Better for multiple exact value matches
- **switch** uses **strict equality (===)** for comparison
- **switch** needs **break** to prevent fall-through

### Theoretical Keywords:
**Boolean expression**, **Case matching**, **Fall-through behavior**,  
**Break statement**, **Default case**, **Performance comparison**,  
**Readability**, **Use cases**

### Example:
```javascript
// if-else - complex conditions
let score = 85;
if (score >= 90) {
    console.log("A");
} else if (score >= 80) {
    console.log("B");
} else {
    console.log("C");
}

// switch - exact value matches
let day = "Monday";
switch (day) {
    case "Monday":
    case "Tuesday":
        console.log("Weekday");
        break;
    case "Saturday":
    case "Sunday":
        console.log("Weekend");
        break;
    default:
        console.log("Invalid day");
}
```

---

## 15. Difference between `for`, `while`, and `do-while` loops

### Answer:
- **for loop**: Best when iterations are **known in advance**
- **while loop**: Best when iterations depend on a **condition**
- **do-while loop**: Executes **at least once**, then checks condition
- **for**: Initialization, condition, increment in one line
- **while**: Only condition check at start
- **do-while**: Condition checked at **end** of each iteration

### Theoretical Keywords:
**Entry-controlled loop**, **Exit-controlled loop**, **Loop counter**,  
**Iteration**, **Loop condition**, **Infinite loop**, **Loop optimization**,  
**for...of**, **for...in**

### Example:
```javascript
// for loop - known iterations
for (let i = 0; i < 5; i++) {
    console.log(i);  // 0, 1, 2, 3, 4
}

// while loop - condition-based
let count = 0;
while (count < 5) {
    console.log(count);
    count++;
}

// do-while - executes at least once
let num = 10;
do {
    console.log(num);  // 10 (runs once even though condition is false)
} while (num < 5);

// for...of - iterate over iterable values
for (let char of "Hello") {
    console.log(char);
}

// for...in - iterate over object keys
for (let key in { a: 1, b: 2 }) {
    console.log(key);  // "a", "b"
}
```

---

## 16. What is `break` and `continue`?

### Answer:
- **break**: Immediately **exits** the entire loop/switch
- **continue**: **Skips** current iteration and moves to next
- Both work with `for`, `while`, `do-while` loops
- **break** also used in `switch` statements to prevent fall-through
- Can use **labeled statements** for nested loops
- Overuse can make code harder to read

### Theoretical Keywords:
**Loop control**, **Exit loop**, **Skip iteration**, **Labeled statements**,  
**Control flow**, **Switch fall-through**, **Nested loops**,  
**Code readability**

### Example:
```javascript
// break - exit loop
for (let i = 0; i < 10; i++) {
    if (i === 5) break;  // stops at 5
    console.log(i);      // 0, 1, 2, 3, 4
}

// continue - skip iteration
for (let i = 0; i < 5; i++) {
    if (i === 2) continue;  // skips 2
    console.log(i);         // 0, 1, 3, 4
}

// Labeled break for nested loops
outer: for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
        if (i === 1 && j === 1) break outer;
        console.log(i, j);
    }
}
```

---
