# JavaScript Interview Questions - Professional Answers

---

## BEGINNER LEVEL (0–2 Years)

### JavaScript Basics

---

### 1. What is JavaScript?

**Expected Answer:**
JavaScript is a high-level, dynamic, interpreted programming language primarily used for adding interactivity to web pages. It runs in browsers (client-side) and on servers via Node.js (server-side). JavaScript is single-threaded, event-driven, and supports multiple programming paradigms including object-oriented, functional, and imperative programming. It's one of the core technologies of web development alongside HTML and CSS.

**Key Theoretical Concepts:**
- High-level, dynamically typed language
- Interpreted/JIT-compiled by JavaScript engines (V8, SpiderMonkey)
- Single-threaded with event loop for async operations
- Prototype-based object-oriented programming
- First-class functions (functions as values)
- Event-driven, non-blocking I/O
- Cross-platform (browsers, Node.js, mobile apps)

**Example:**
```javascript
// Client-side JavaScript in browser
document.getElementById('button').addEventListener('click', function() {
    console.log('Button clicked!');
    document.getElementById('output').textContent = 'Hello, World!';
});

// Server-side JavaScript with Node.js
const http = require('http');
const server = http.createServer((req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello from Node.js!');
});
server.listen(3000);

// Modern JavaScript features
const greet = (name) => `Hello, ${name}!`;
const users = ['Alice', 'Bob', 'Charlie'];
const greetings = users.map(user => greet(user));
console.log(greetings); // ['Hello, Alice!', 'Hello, Bob!', 'Hello, Charlie!']
```

---

### 2. Is JavaScript interpreted or compiled?

**Expected Answer:**
JavaScript is traditionally interpreted, but modern JavaScript engines use Just-In-Time (JIT) compilation. The engine parses source code into an Abstract Syntax Tree (AST), converts it to bytecode, and JIT-compiles hot (frequently executed) code paths to optimized machine code at runtime. This hybrid approach provides the flexibility of interpretation with the performance benefits of compilation.

**Key Theoretical Concepts:**
- Just-In-Time (JIT) compilation for performance
- Abstract Syntax Tree (AST) parsing
- Bytecode generation and execution
- Hot code detection and optimization
- Runtime optimization and deoptimization
- Garbage collection for memory management
- Engine implementations (V8, SpiderMonkey, JavaScriptCore)

**Example:**
```javascript
// This code goes through multiple stages in the JS engine
function calculateSum(arr) {
    let sum = 0;
    for (let i = 0; i < arr.length; i++) {
        sum += arr[i];
    }
    return sum;
}

// First call - interpreted or baseline compiled
calculateSum([1, 2, 3, 4, 5]);

// After many calls - optimizing compiler kicks in
for (let i = 0; i < 10000; i++) {
    calculateSum([1, 2, 3, 4, 5]); // This gets optimized to machine code
}

// Engine stages:
// 1. Parsing: Source code → AST
// 2. Bytecode: AST → Bytecode
// 3. Interpretation: Bytecode execution (first runs)
// 4. Profiling: Detect hot functions
// 5. Optimization: Hot code → Optimized machine code
// 6. Deoptimization: If assumptions fail, fall back to bytecode
```

---

### 3. Difference between var, let, and const

**Expected Answer:**
**`var`**:
- Function-scoped (visible throughout the function)
- Hoisted to top of function with `undefined` initialization
- Can be re-declared in same scope
- No block scope - leaks outside blocks

**`let`**:
- Block-scoped (only visible within `{}` block)
- Hoisted but in Temporal Dead Zone until declaration
- Cannot be re-declared in same scope
- Preferred for variables that change

**`const`**:
- Block-scoped like `let`
- Must be initialized at declaration
- Cannot be reassigned (binding is immutable)
- Object/array contents can still be modified
- Preferred for values that shouldn't be reassigned

**Key Theoretical Concepts:**
- Function scope vs block scope
- Hoisting behavior differences
- Temporal Dead Zone (TDZ) for let/const
- Immutable binding vs immutable value
- Best practices: prefer const, use let when needed, avoid var

**Example:**
```javascript
// VAR - Function scoped, hoisted
function varExample() {
    console.log(x); // undefined (hoisted)
    var x = 10;
    
    if (true) {
        var x = 20; // Same variable, overwrites outer x
        console.log(x); // 20
    }
    console.log(x); // 20 (var leaks out of block)
}

// LET - Block scoped, TDZ
function letExample() {
    // console.log(y); // ReferenceError: Cannot access before initialization (TDZ)
    let y = 10;
    
    if (true) {
        let y = 20; // Different variable, block scoped
        console.log(y); // 20
    }
    console.log(y); // 10 (outer y unchanged)
    
    // let y = 30; // SyntaxError: Identifier 'y' has already been declared
}

// CONST - Block scoped, immutable binding
function constExample() {
    const z = 10;
    // z = 20; // TypeError: Assignment to constant variable
    
    const user = { name: 'Alice', age: 25 };
    user.age = 26; // OK - modifying object contents
    console.log(user); // { name: 'Alice', age: 26 }
    
    // user = {}; // TypeError: Assignment to constant variable
    
    const arr = [1, 2, 3];
    arr.push(4); // OK - modifying array contents
    console.log(arr); // [1, 2, 3, 4]
    
    // arr = []; // TypeError: Assignment to constant variable
}

// Best Practice
const PI = 3.14159; // Values that never change
let counter = 0; // Values that will change
// Avoid var in modern JavaScript
```

---

### Q: What is a closure?
**Expected Answer:**
A closure is a function that captures variables from its lexical scope, allowing access after the outer function has returned.

**Key Concepts:**
- Lexical scoping, memory retention, common patterns (factories, private state).

**Interviewer Expectation:**
Explain real use-cases and memory implications.

**Red Flags:**
- Not understanding captured variable lifetime.

**Depth Expected:**
Intermediate with a short code sample.

---

### Q: Event loop and async model (brief)
**Expected Answer:**
JS uses an event loop with a task queue and microtask queue; Promises’ reactions run in microtasks, callbacks in tasks; this enables non-blocking async behavior on a single thread.

**Key Concepts:**
- Call stack, task queue, microtasks, promise microtask timing.

**Interviewer Expectation:**
Describe order of execution for `setTimeout`, Promises, and I/O callbacks.

**Red Flags:**
- Saying setTimeout guarantees timing precision or confusing microtasks with tasks.

**Depth Expected:**
Intermediate (explain ordering with examples).

---

### 7. What are Promises and async/await?

**Expected Answer:**
**Promises**: Objects representing the eventual completion or failure of an asynchronous operation. Has three states: pending, fulfilled, or rejected. Provides `.then()`, `.catch()`, `.finally()` for handling results.

**async/await**: Syntactic sugar over Promises making async code look synchronous. `async` functions always return a Promise. `await` pauses execution until the Promise resolves, making code more readable than `.then()` chains.

**Key Theoretical Concepts:**
- Promise states: pending, fulfilled, rejected
- Promise chaining with .then()
- Error handling with .catch() and try/catch
- Promise.all(), Promise.race(), Promise.allSettled()
- async functions return Promises automatically
- await pauses execution, yields to event loop
- Microtask queue for Promise callbacks

**Example:**
```javascript
// Promise basics
const fetchData = new Promise((resolve, reject) => {
    setTimeout(() => resolve('Data loaded'), 1000);
});

fetchData
    .then(data => console.log(data))
    .catch(error => console.error(error));

// async/await - Cleaner syntax
async function getUserData(id) {
    try {
        const response = await fetch(`/api/users/${id}`);
        const user = await response.json();
        return user;
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}

// Promise.all - Parallel execution
const [users, posts] = await Promise.all([
    fetch('/api/users'),
    fetch('/api/posts')
]);
```

---

### 8. Array methods: map, filter, reduce

**Expected Answer:**
**map()**: Transforms each element, returns new array of same length.
**filter()**: Selects elements passing a test, returns new array (can be shorter).
**reduce()**: Reduces array to single value using accumulator.
**forEach()**: Executes callback for each element, returns undefined (for side effects).

All methods are immutable - don't modify the original array.

**Key Theoretical Concepts:**
- Higher-order functions
- Immutability principles
- Functional programming paradigms
- Method chaining
- Callback function signatures
- Accumulator pattern in reduce

**Example:**
```javascript
const numbers = [1, 2, 3, 4, 5];

// MAP - Transform
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8, 10]

// FILTER - Select
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4]

// REDUCE - Accumulate
const sum = numbers.reduce((acc, n) => acc + n, 0);
// 15

// CHAINING
const result = numbers
    .filter(n => n > 2)
    .map(n => n * 2)
    .reduce((acc, n) => acc + n, 0);
// 24
```

---

### 9. Difference between == and ===

**Expected Answer:**
**`==` (Loose Equality)**: Performs type coercion before comparison. Converts operands to same type, then compares. Can lead to unexpected results.

**`===` (Strict Equality)**: Checks both type and value without coercion. Returns true only if both type and value match. Recommended for most comparisons.

**Key Theoretical Concepts:**
- Type coercion with ==
- Type safety with ===
- Falsy values behavior
- Best practices: always use ===
- Common pitfalls with ==

**Example:**
```javascript
// Loose equality (==) - Type coercion
console.log(5 == '5');      // true (string '5' converted to number)
console.log(0 == false);    // true (false converted to 0)
console.log(null == undefined); // true (special case)
console.log('' == 0);       // true (empty string converted to 0)

// Strict equality (===) - No coercion
console.log(5 === '5');     // false (different types)
console.log(0 === false);   // false (different types)
console.log(null === undefined); // false
console.log('' === 0);      // false

// Best practice: Use ===
const userInput = '10';
if (userInput === 10) {     // false - type check prevents bugs
    console.log('Number 10');
}
```

---

### 10. What is the `this` keyword?

**Expected Answer:**
`this` refers to the context in which a function is executed. Its value depends on how the function is called:
- **Global context**: `window` (browser) or `global` (Node.js)
- **Object method**: the object
- **Constructor**: the new instance
- **Arrow function**: lexically inherited from enclosing scope
- **Event handler**: the element that fired the event

**Key Theoretical Concepts:**
- Dynamic binding based on call-site
- Lexical this in arrow functions
- call(), apply(), bind() for explicit binding
- Implicit vs explicit binding
- Lost context in callbacks

**Example:**
```javascript
// Object method
const user = {
    name: 'Alice',
    greet() {
        console.log(`Hello, ${this.name}`);
    }
};
user.greet(); // "Hello, Alice"

// Lost context
const greet = user.greet;
greet(); // "Hello, undefined" - this is window/global

// Arrow function - lexical this
const user2 = {
    name: 'Bob',
    greet: () => {
        console.log(this.name); // undefined - this from outer scope
    }
};

// Fix with bind
const boundGreet = user.greet.bind(user);
boundGreet(); // "Hello, Alice"

// Constructor
function Person(name) {
    this.name = name; // this refers to new instance
}
const person = new Person('Charlie');
```

### Execution Context & Event Loop

Q: What is the event loop?
**Expected Answer:** Single-threaded model with call stack, task queue, microtask queue; microtasks (Promises) run before next task.

### Asynchronous JavaScript

Q: Promises, async/await
**Expected Answer:** Promises represent future values; `async/await` simplifies promise usage; handle errors with `try/catch` or `.catch()`.

### Closures and `this`

Q: What is a closure?
**Expected Answer:** Function that retains access to lexical scope after outer function returns.

Q: How `this` behaves
**Expected Answer:** `this` depends on call site; arrow functions do not bind `this` and inherit it from surrounding scope.

### Browser & Web Concepts

Q: What is DOM and BOM?
**Expected Answer:** DOM represents HTML document tree; BOM are browser-provided objects like `window` and `navigator`.

Q: Storage: `localStorage`, `sessionStorage`, cookies
**Expected Answer:** `localStorage` persistent per domain; `sessionStorage` per tab; cookies sent to server (beware size/security).

Q: Event bubbling vs capturing and delegation
**Expected Answer:** Two phases of event propagation; delegation attaches handler higher up to capture child events.

### Advanced

Q: Execution flow, performance, and memory
**Expected Answer:** Understand call stack, memory heap, garbage collection and performance bottlenecks.

Q: Closures memory implications and use-cases
**Expected Answer:** Use for encapsulation; manage memory leaks by avoiding long-lived closures holding large objects.

---

I can now expand each of these into the full interviewer block (Expected Answer, Key Concepts, Interviewer Expectation, Red Flags, Depth) for every original bullet; confirm to proceed and I will generate the completed file.
