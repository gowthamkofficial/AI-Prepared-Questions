# EXECUTION CONTEXT AND MEMORY ANSWERS

---

## ⚡ QUICK 30-SECOND EXPLANATION: Execution Context

**Execution Context is the environment in which JavaScript code runs.**

- When JavaScript starts, it creates a **Global Execution Context**
- For every function call, a new **Function Execution Context** is created and pushed to the **Call Stack**

**Two phases of Execution Context:**

1. **Creation phase** – memory is allocated, variables are hoisted, functions are stored, and `this` is set
2. **Execution phase** – code is executed line by line and values are assigned

**Example:**
```javascript
var a = 10;

function test() {
  var b = 20;
  console.log(a + b);  // Output: 30
}

test();
```

---

## 36. What is execution context?

### Answer:
- Execution context is the **environment** where JavaScript code is evaluated and executed
- Contains **Variable Environment**, **Scope Chain**, and **this binding**
- **Three types**: Global, Function, and Eval execution context
- Created when function is **called** (not defined)
- Pushed onto **call stack** when created
- JavaScript starts with **Global Execution Context**

### Theoretical Keywords:
**Execution environment**, **Variable Environment**, **Lexical Environment**,  
**Scope chain**, **this binding**, **Call stack**, **Global context**,  
**Function context**, **Context creation**

### Example:
```javascript
// Global Execution Context
var globalVar = "global";

function outer() {
    // Function Execution Context for outer()
    var outerVar = "outer";
    
    function inner() {
        // Function Execution Context for inner()
        var innerVar = "inner";
        console.log(globalVar, outerVar, innerVar);
    }
    
    inner();  // New execution context created
}

outer();  // New execution context created

// Each call creates a new execution context
// Even for same function
function greet(name) {
    console.log(`Hello, ${name}`);
}

greet("John");  // Execution Context 1
greet("Jane");  // Execution Context 2 (separate)
```

---

## 37. What are the phases of execution context?

### Answer:
- **Creation Phase**: Memory allocated for variables and functions
- **Execution Phase**: Code is executed line by line
- During creation: Variables set to `undefined`, functions stored entirely
- During creation: `this` binding is determined
- During creation: Scope chain is created
- This explains **hoisting** behavior in JavaScript

### Theoretical Keywords:
**Creation phase**, **Execution phase**, **Memory allocation**,  
**Variable hoisting**, **Function hoisting**, **this determination**,  
**Scope chain creation**, **Two-phase process**

### Example:
```javascript
// Example code
var x = 10;
function greet(name) {
    var message = "Hello";
    console.log(message + " " + name);
}
greet("John");

/*
CREATION PHASE (Global Context):
- x = undefined (hoisted)
- greet = function definition (fully hoisted)
- this = window/global

EXECUTION PHASE (Global Context):
- x = 10 (assigned)
- greet("John") is called

CREATION PHASE (greet Function Context):
- name = "John" (parameter)
- message = undefined (hoisted)
- this = window/global

EXECUTION PHASE (greet Function Context):
- message = "Hello" (assigned)
- console.log executed
*/

// This is why:
console.log(a);  // undefined (creation phase set it)
var a = 5;

sayHi();  // Works! (function fully hoisted in creation)
function sayHi() {
    console.log("Hi");
}
```

---

## 38. What is call stack?

### Answer:
- Call stack is a **data structure** that tracks function execution
- Works on **LIFO** principle (Last In, First Out)
- Stores **execution contexts** of called functions
- When function called, its context is **pushed** onto stack
- When function returns, its context is **popped** off stack
- **Stack overflow** occurs when stack limit exceeded

### Theoretical Keywords:
**LIFO (Last In First Out)**, **Stack frame**, **Push and pop**,  
**Execution context**, **Function invocation**, **Stack overflow**,  
**Recursive calls**, **Call stack trace**

### Example:
```javascript
function first() {
    console.log("First start");
    second();
    console.log("First end");
}

function second() {
    console.log("Second start");
    third();
    console.log("Second end");
}

function third() {
    console.log("Third");
}

first();

/*
CALL STACK VISUALIZATION:

1. [Global] - initial
2. [first, Global] - first() called
3. [second, first, Global] - second() called
4. [third, second, first, Global] - third() called
5. [second, first, Global] - third() returns
6. [first, Global] - second() returns
7. [Global] - first() returns

Output:
"First start"
"Second start"
"Third"
"Second end"
"First end"
*/

// Stack Overflow (recursive without base case)
function infinite() {
    infinite();  // Keeps pushing to stack
}
// infinite();  // RangeError: Maximum call stack size exceeded
```

---

## 39. What is memory heap?

### Answer:
- Memory heap is **unstructured memory pool** for storing objects
- Where **reference types** (objects, arrays, functions) are stored
- Memory allocated **dynamically** as needed
- Size is not fixed, can grow and shrink
- Managed by **garbage collector** (automatic cleanup)
- **Primitives** stored in stack, **references** point to heap

### Theoretical Keywords:
**Heap memory**, **Dynamic allocation**, **Reference types**,  
**Garbage collection**, **Memory management**, **Object storage**,  
**Memory leak**, **Stack vs heap**

### Example:
```javascript
// Stack vs Heap storage

// Primitives - stored in stack
let num = 42;          // Stack: num -> 42
let str = "hello";     // Stack: str -> "hello"

// Objects - reference in stack, data in heap
let obj = { name: "John" };  // Stack: obj -> [heap address]
                              // Heap: { name: "John" }

// Copying primitives (value copy)
let a = 10;
let b = a;    // b gets copy of value
b = 20;
console.log(a);  // 10 (unchanged)

// Copying objects (reference copy)
let person1 = { name: "John" };
let person2 = person1;  // Both point to same heap location
person2.name = "Jane";
console.log(person1.name);  // "Jane" (both affected!)

// Memory heap and garbage collection
function createObjects() {
    let temp = { data: new Array(1000000) };
    // temp is eligible for garbage collection after function ends
}
createObjects();  // Object can be cleaned up
```

---

## 40. What is the event loop?

### Answer:
- Event loop is the mechanism that handles **asynchronous operations**
- Continuously checks if **call stack is empty**
- Moves callbacks from **task queues** to call stack
- Enables **non-blocking** behavior in single-threaded JavaScript
- Has **microtask queue** (Promises) and **macrotask queue** (setTimeout)
- Microtasks have **higher priority** than macrotasks

### Theoretical Keywords:
**Asynchronous execution**, **Call stack**, **Callback queue**,  
**Microtask queue**, **Macrotask queue**, **Non-blocking I/O**,  
**Single-threaded**, **Concurrency model**

### Example:
```javascript
console.log("1");

setTimeout(() => {
    console.log("2");
}, 0);

Promise.resolve().then(() => {
    console.log("3");
});

console.log("4");

// Output: 1, 4, 3, 2

/*
EVENT LOOP EXPLANATION:

1. console.log("1") - Sync, executes immediately → Output: "1"
2. setTimeout - Moves callback to macrotask queue
3. Promise.then - Moves callback to microtask queue
4. console.log("4") - Sync, executes immediately → Output: "4"
5. Call stack empty, check microtask queue first
6. Execute Promise callback → Output: "3"
7. Microtask queue empty, check macrotask queue
8. Execute setTimeout callback → Output: "2"

PRIORITY: Call Stack > Microtasks > Macrotasks
*/

// More complex example
console.log("Start");

setTimeout(() => console.log("Timeout 1"), 0);
setTimeout(() => console.log("Timeout 2"), 0);

Promise.resolve()
    .then(() => console.log("Promise 1"))
    .then(() => console.log("Promise 2"));

console.log("End");

// Output: Start, End, Promise 1, Promise 2, Timeout 1, Timeout 2
```

---

## 41. How is JavaScript single-threaded?

### Answer:
- JavaScript has **one call stack** (one task at a time)
- **Single main thread** for executing code
- Only one piece of code can run at any moment
- **Async operations** don't block the main thread
- Browser provides **Web APIs** for async (setTimeout, fetch)
- Node.js uses **libuv** for async operations
- Event loop enables **concurrency** without multiple threads

### Theoretical Keywords:
**Single call stack**, **Main thread**, **Web APIs**,  
**Non-blocking**, **Concurrency vs parallelism**, **Event loop**,  
**Callback queue**, **Thread safety**

### Example:
```javascript
// JavaScript is single-threaded but non-blocking

// Synchronous (blocking)
console.log("Step 1");
// Heavy computation would block everything
for (let i = 0; i < 1000000000; i++) { } // Blocks!
console.log("Step 2");

// Asynchronous (non-blocking)
console.log("Step 1");
setTimeout(() => {
    console.log("Step 3");  // Handled by Web API, not main thread
}, 1000);
console.log("Step 2");  // Doesn't wait for timeout

// How async works:
// 1. Main thread encounters setTimeout
// 2. Browser's Web API handles the timer
// 3. Main thread continues to next line
// 4. When timer completes, callback goes to queue
// 5. Event loop moves callback to stack when empty

// Web Workers for true parallelism (separate threads)
const worker = new Worker('worker.js');
worker.postMessage({ data: 'heavy task' });
worker.onmessage = (e) => console.log(e.data);

// But workers can't access DOM or share memory directly
// Main JS code remains single-threaded
```

---
