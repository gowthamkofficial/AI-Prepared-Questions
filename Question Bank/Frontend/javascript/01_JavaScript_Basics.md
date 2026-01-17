# JAVASCRIPT BASICS ANSWERS

---

## 1. What is JavaScript?

### Answer:
- JavaScript is a **lightweight, interpreted, high-level** programming language
- Primarily used for making web pages **interactive and dynamic**
- Runs in browsers (client-side) and servers (**Node.js**)
- Supports **object-oriented**, **functional**, and **event-driven** programming paradigms
- Single-threaded with **asynchronous** capabilities through event loop
- Created by **Brendan Eich** in 1995 at Netscape

### Theoretical Keywords:
**Interpreted language**, **Dynamic typing**, **Single-threaded**,  
**Event-driven**, **Client-side scripting**, **ECMAScript specification**,  
**Cross-platform**, **Multi-paradigm**

---

## 2. Is JavaScript interpreted or compiled?

### Answer:
- Traditionally considered **interpreted** language (line-by-line execution)
- Modern JavaScript engines use **Just-In-Time (JIT) compilation**
- Code is parsed → compiled to bytecode → optimized during runtime
- **V8 engine** (Chrome/Node.js) compiles directly to machine code
- Technically now a **"JIT-compiled" or "hybrid"** language

### Theoretical Keywords:
**JIT compilation**, **V8 engine**, **SpiderMonkey (Firefox)**,  
**Bytecode**, **Runtime optimization**, **Abstract Syntax Tree (AST)**,  
**Hot code optimization**, **Interpreter vs compiler**

---

## 3. Difference between Java and JavaScript

### Answer:
- **Origin**: Java by Sun Microsystems, JavaScript by Netscape
- **Typing**: Java is statically typed, JavaScript is dynamically typed
- **Execution**: Java compiles to bytecode for JVM, JavaScript runs in browser/Node.js
- **Paradigm**: Java is class-based OOP, JavaScript is prototype-based
- **Syntax**: Java requires explicit types, JavaScript uses inference
- **Use Case**: Java for enterprise/backend, JavaScript for web/full-stack

### Theoretical Keywords:
**Static vs dynamic typing**, **Class-based vs prototype-based**,  
**Compiled vs interpreted**, **JVM vs JavaScript engine**,  
**Strong typing vs weak typing**, **Different ecosystems**

---

## 4. What are variables in JavaScript?

### Answer:
- Variables are **containers for storing data values**
- Declared using **`var`**, **`let`**, or **`const`** keywords
- JavaScript variables are **dynamically typed** (type determined at runtime)
- Variable names are **case-sensitive** and follow camelCase convention
- Must start with letter, underscore (_), or dollar sign ($)
- Cannot use reserved keywords as variable names

### Theoretical Keywords:
**Declaration**, **Initialization**, **Assignment**,  
**Dynamic typing**, **Identifier naming**, **Memory allocation**,  
**Reference vs value**, **Variable scope**

---

## 5. Difference between `var`, `let`, and `const`

### Answer:
- **Scope**: `var` is function-scoped, `let` and `const` are block-scoped
- **Hoisting**: `var` hoisted with undefined, `let`/`const` hoisted but in TDZ
- **Re-declaration**: `var` allows re-declaration, `let`/`const` do not
- **Re-assignment**: `var` and `let` allow re-assignment, `const` does not
- **Global Object**: `var` attaches to window object, `let`/`const` do not
- **Best Practice**: Use `const` by default, `let` when re-assignment needed

### Theoretical Keywords:
**Function scope vs block scope**, **Hoisting behavior**,  
**Temporal Dead Zone (TDZ)**, **Re-declaration**, **Re-assignment**,  
**Global object pollution**, **ES6 features**, **Immutability**

### Example:
```javascript
// var - function scoped, hoisted
function varExample() {
    console.log(x); // undefined (hoisted)
    var x = 10;
    var x = 20; // re-declaration allowed
}

// let - block scoped
{
    let y = 10;
    y = 20; // re-assignment allowed
    // let y = 30; // Error: Cannot re-declare
}

// const - block scoped, must initialize
const PI = 3.14;
// PI = 3.15; // Error: Cannot re-assign
const obj = { name: 'John' };
obj.name = 'Jane'; // Allowed (mutating, not re-assigning)
```

---

## 6. What are data types in JavaScript?

### Answer:
- **Primitive Types** (7): `string`, `number`, `boolean`, `undefined`, `null`, `symbol`, `bigint`
- **Non-Primitive Type**: `object` (includes arrays, functions, objects)
- JavaScript is **dynamically typed** (type can change at runtime)
- **typeof** operator used to check data type
- **Primitives** are immutable, **objects** are mutable
- ES6 added `Symbol`, ES2020 added `BigInt`

### Theoretical Keywords:
**Primitive types**, **Reference types**, **Type coercion**,  
**Dynamic typing**, **typeof operator**, **Symbol**, **BigInt**,  
**Immutability**, **Pass by value vs pass by reference**

---

## 7. Primitive vs non-primitive data types

### Answer:
- **Primitive**: Stored directly in **stack**, accessed by value
- **Non-Primitive**: Stored in **heap**, accessed by reference
- **Primitive**: Immutable, changes create new values
- **Non-Primitive**: Mutable, can be modified in place
- **Comparison**: Primitives compared by value, objects by reference
- **Memory**: Primitives have fixed size, objects have dynamic size

### Theoretical Keywords:
**Stack vs heap memory**, **Pass by value**, **Pass by reference**,  
**Immutable vs mutable**, **Memory allocation**, **Reference comparison**,  
**Deep copy vs shallow copy**, **Value types**

### Example:
```javascript
// Primitive - copied by value
let a = 10;
let b = a;
b = 20;
console.log(a); // 10 (unchanged)

// Non-primitive - copied by reference
let obj1 = { name: 'John' };
let obj2 = obj1;
obj2.name = 'Jane';
console.log(obj1.name); // 'Jane' (changed!)
```

---

## 8. What is `typeof`?

### Answer:
- **typeof** is a **unary operator** that returns the data type as a string
- Returns: `"string"`, `"number"`, `"boolean"`, `"undefined"`, `"object"`, `"function"`, `"symbol"`, `"bigint"`
- **typeof null** returns `"object"` (historical bug)
- **typeof array** returns `"object"` (use `Array.isArray()` instead)
- **typeof function** returns `"function"` (special case)

### Theoretical Keywords:
**Type checking**, **Unary operator**, **Runtime type detection**,  
**typeof null bug**, **Type coercion**, **Dynamic typing**,  
**instanceof vs typeof**

### Example:
```javascript
typeof "Hello"      // "string"
typeof 42           // "number"
typeof true         // "boolean"
typeof undefined    // "undefined"
typeof null         // "object" (bug!)
typeof {}           // "object"
typeof []           // "object"
typeof function(){} // "function"
typeof Symbol()     // "symbol"
typeof 10n          // "bigint"
```

---

## 9. What is `NaN`?

### Answer:
- **NaN** stands for **"Not-a-Number"**
- Represents result of invalid or undefined mathematical operation
- Type of NaN is **"number"** (typeof NaN === "number")
- **NaN !== NaN** (NaN is not equal to itself!)
- Use **isNaN()** or **Number.isNaN()** to check for NaN
- **Number.isNaN()** is more reliable (doesn't coerce types)

### Theoretical Keywords:
**Invalid number**, **IEEE 754 standard**, **Type coercion**,  
**isNaN vs Number.isNaN**, **Arithmetic errors**, **Infinity vs NaN**,  
**Self-inequality**

### Example:
```javascript
parseInt("hello")     // NaN
0 / 0                 // NaN
Math.sqrt(-1)         // NaN

NaN === NaN           // false
Number.isNaN(NaN)     // true
isNaN("hello")        // true (coerces string)
Number.isNaN("hello") // false (no coercion)
```

---

## 10. What is `undefined` vs `null`?

### Answer:
- **undefined**: Variable declared but not assigned a value
- **null**: Intentional absence of any object value
- **typeof undefined** returns `"undefined"`
- **typeof null** returns `"object"` (historical bug)
- **undefined == null** is `true` (loose equality)
- **undefined === null** is `false` (strict equality)

### Theoretical Keywords:
**Uninitialized variable**, **Intentional empty value**,  
**Type coercion**, **Loose vs strict equality**, **Falsy values**,  
**Default function parameters**, **Object property absence**

### Example:
```javascript
let x;
console.log(x);        // undefined

let y = null;
console.log(y);        // null

console.log(undefined == null);  // true
console.log(undefined === null); // false

typeof undefined  // "undefined"
typeof null       // "object"

// Use cases
function greet(name = "Guest") {  // undefined triggers default
    console.log(name);
}
let user = null;  // intentionally empty
```

---
