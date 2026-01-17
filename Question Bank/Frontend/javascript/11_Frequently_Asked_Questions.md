# FREQUENTLY ASKED QUESTIONS (SERVICE COMPANIES) ANSWERS

---

## 68. Is JavaScript single-threaded or multi-threaded?

### Answer:
- JavaScript is **single-threaded** - one call stack, one task at a time
- Main thread executes one piece of code at a time
- **Event loop** enables concurrency without multiple threads
- Web Workers provide **true multi-threading** for heavy tasks
- Node.js uses **libuv** thread pool for I/O operations
- Single-threaded but **non-blocking** through async mechanisms

### Theoretical Keywords:
**Single-threaded**, **Call stack**, **Event loop**, **Concurrency**,  
**Web Workers**, **Thread pool**, **Non-blocking I/O**,  
**Main thread**, **Parallel execution**

---

## 69. How does JavaScript handle asynchronous operations if it is single-threaded?

### Answer:
- Uses **Event Loop** mechanism for concurrency
- **Browser/Node.js APIs** handle async operations (setTimeout, fetch)
- Callbacks queued in **task queues** when operations complete
- Event loop moves callbacks to stack when **stack is empty**
- **Microtasks** (Promises) have priority over **macrotasks** (timers)
- This enables **non-blocking** behavior without multiple threads

### Theoretical Keywords:
**Event loop**, **Web APIs**, **Callback queue**, **Microtask queue**,  
**Macrotask queue**, **Non-blocking**, **Async delegation**,  
**Single-threaded concurrency**

### Example:
```javascript
console.log("1");  // Sync - call stack

setTimeout(() => {
    console.log("2");  // Web API handles timer, callback to queue
}, 0);

Promise.resolve().then(() => {
    console.log("3");  // Microtask queue (higher priority)
});

console.log("4");  // Sync - call stack

// Output: 1, 4, 3, 2
// Stack executes sync first, then microtasks, then macrotasks
```

---

## 70. What happens when you declare a variable without `var`, `let`, or `const`?

### Answer:
- Creates a **global variable** (attaches to window object)
- This is called **implicit global** - very dangerous!
- In **strict mode**, throws **ReferenceError**
- Pollutes global namespace
- Can cause **hard-to-debug bugs**
- Always use `let`, `const`, or `var` to declare variables

### Theoretical Keywords:
**Implicit global**, **Global namespace pollution**, **Strict mode**,  
**ReferenceError**, **Window object**, **Best practices**,  
**Accidental globals**, **Use strict**

### Example:
```javascript
// Without strict mode
function test() {
    x = 10;  // Creates global variable!
}
test();
console.log(x);        // 10
console.log(window.x); // 10 (attached to global)

// With strict mode
"use strict";
function testStrict() {
    y = 10;  // ReferenceError: y is not defined
}

// Always declare variables
function proper() {
    let local = 10;  // Proper declaration
}
```

---

## 71. What is the difference between `undefined` and not defined?

### Answer:
- **undefined**: Variable declared but **not assigned** a value
- **not defined**: Variable **never declared** - ReferenceError
- `undefined` is a **type** and a **value** in JavaScript
- Accessing not defined variable throws **ReferenceError**
- `typeof undefined` returns "undefined"
- `typeof notDeclared` also returns "undefined" (safe check)

### Theoretical Keywords:
**Declared vs undeclared**, **undefined type**, **ReferenceError**,  
**typeof operator**, **Variable existence**, **Memory allocation**,  
**Initialization**, **Safe type checking**

### Example:
```javascript
// undefined - declared but not assigned
let x;
console.log(x);        // undefined
console.log(typeof x); // "undefined"

// not defined - never declared
// console.log(y);     // ReferenceError: y is not defined
console.log(typeof y); // "undefined" (typeof is safe!)

// Checking variable existence safely
if (typeof someVar !== 'undefined') {
    // Variable exists
}

// Function parameters
function greet(name) {
    console.log(name);  // undefined if no argument passed
}
greet();  // undefined

// Object properties
const obj = { name: "John" };
console.log(obj.age);  // undefined (property doesn't exist)
// console.log(notAnObj.prop);  // ReferenceError (notAnObj not defined)
```

---

## 72. What is the use of strict mode?

### Answer:
- Strict mode enables **stricter parsing** and error handling
- Activated with **`"use strict";`** at file or function start
- **Prevents** accidental globals, duplicate parameters
- **Throws errors** for silent failures
- **Disables** dangerous features (with, eval scope leaking)
- Makes code **safer and more optimizable**

### Theoretical Keywords:
**Strict parsing**, **Error throwing**, **Implicit globals**,  
**Silent failures**, **Optimization**, **Security**,  
**Deprecated features**, **ES5 feature**

### Example:
```javascript
"use strict";

// 1. Prevents accidental globals
// x = 10;  // ReferenceError

// 2. Prevents duplicate parameters
// function sum(a, a) {}  // SyntaxError

// 3. Prevents deleting variables
let y = 10;
// delete y;  // SyntaxError

// 4. Prevents octal literals
// let num = 010;  // SyntaxError

// 5. Reserved words can't be used
// let let = 1;  // SyntaxError
// let static = 1;  // SyntaxError

// 6. this in functions is undefined (not window)
function showThis() {
    console.log(this);  // undefined (not window)
}

// 7. Cannot write to read-only properties
const obj = {};
Object.defineProperty(obj, 'x', { value: 10, writable: false });
// obj.x = 20;  // TypeError in strict mode

// Function-level strict mode
function strictFunction() {
    "use strict";
    // Only this function is strict
}
```

---

## 73. What is closure and where is it stored in memory?

### Answer:
- Closure is **function + its lexical environment**
- Stored in **heap memory** (not stack)
- Variables in closure are kept alive (not garbage collected)
- Each closure gets its own **environment record**
- Accessible via function's internal **[[Environment]]** property
- Memory released when closure is no longer referenced

### Theoretical Keywords:
**Heap memory**, **Lexical environment**, **Environment record**,  
**Variable persistence**, **Garbage collection**, **[[Environment]]**,  
**Memory retention**, **Scope chain**

### Example:
```javascript
function createCounter() {
    let count = 0;  // Stored in heap with closure
    
    return function() {
        count++;
        return count;
    };
}

const counter = createCounter();

/*
MEMORY STRUCTURE:

HEAP:
┌─────────────────────────────────────┐
│ Environment Record (createCounter)  │
│ ┌─────────────────────────────────┐ │
│ │ count: 0 → 1 → 2 → ...          │ │
│ └─────────────────────────────────┘ │
└─────────────────────────────────────┘
         ▲
         │ [[Environment]]
┌────────┴───────┐
│ Inner Function │ ──→ Lives in heap
└────────────────┘
         ▲
         │ Reference
┌────────┴───────┐
│ counter (var)  │ ──→ Stack (holds reference)
└────────────────┘
*/

counter();  // 1
counter();  // 2 (count persists in heap)

// When counter is no longer referenced
// counter = null;  // Closure becomes eligible for garbage collection
```

---

## 74. What is the difference between deep copy and shallow copy?

### Answer:
- **Shallow copy**: Copies only **top-level** properties
- **Deep copy**: Copies **all nested levels** recursively
- Shallow: Nested objects share **same reference**
- Deep: Nested objects are **completely independent**
- Shallow methods: `Object.assign()`, spread operator
- Deep methods: `JSON.parse(JSON.stringify())`, `structuredClone()`

### Theoretical Keywords:
**Reference copy**, **Value copy**, **Nested objects**,  
**Object.assign**, **Spread operator**, **structuredClone**,  
**JSON serialization**, **Recursive copy**

### Example:
```javascript
const original = {
    name: "John",
    address: {
        city: "NYC",
        zip: "10001"
    },
    hobbies: ["reading", "coding"]
};

// SHALLOW COPY - nested objects share reference
const shallow1 = { ...original };
const shallow2 = Object.assign({}, original);

shallow1.name = "Jane";           // Independent
shallow1.address.city = "LA";     // Affects original!
shallow1.hobbies.push("gaming");  // Affects original!

console.log(original.address.city);  // "LA" (changed!)
console.log(original.hobbies);       // ["reading", "coding", "gaming"]

// DEEP COPY - completely independent
// Method 1: JSON (has limitations)
const deep1 = JSON.parse(JSON.stringify(original));
// Loses: functions, undefined, Date objects, RegExp, etc.

// Method 2: structuredClone (modern, recommended)
const deep2 = structuredClone(original);

// Method 3: Custom recursive function
function deepClone(obj) {
    if (obj === null || typeof obj !== 'object') return obj;
    if (Array.isArray(obj)) return obj.map(deepClone);
    
    const clone = {};
    for (let key in obj) {
        if (obj.hasOwnProperty(key)) {
            clone[key] = deepClone(obj[key]);
        }
    }
    return clone;
}

deep2.address.city = "Boston";
console.log(original.address.city);  // "LA" (unchanged!)
```

---

## 75. How do you clone an object in JavaScript?

### Answer:
- **Shallow clone**: `{ ...obj }`, `Object.assign({}, obj)`
- **Deep clone**: `structuredClone(obj)`, JSON method
- For arrays: `[...arr]`, `arr.slice()`, `Array.from(arr)`
- Libraries: Lodash `_.cloneDeep()`
- Consider what needs cloning (methods, symbols, dates)
- `structuredClone()` is the modern standard for deep cloning

### Theoretical Keywords:
**Object.assign**, **Spread operator**, **structuredClone**,  
**JSON serialization**, **Array methods**, **Lodash**,  
**Shallow vs deep**, **Cloning limitations**

### Example:
```javascript
const obj = { a: 1, b: { c: 2 } };

// SHALLOW CLONE METHODS
const clone1 = { ...obj };
const clone2 = Object.assign({}, obj);

// DEEP CLONE METHODS
const clone3 = JSON.parse(JSON.stringify(obj));  // Loses functions, dates
const clone4 = structuredClone(obj);             // Modern, recommended

// ARRAY CLONING
const arr = [1, 2, [3, 4]];
const arrClone1 = [...arr];           // Shallow
const arrClone2 = arr.slice();        // Shallow
const arrClone3 = Array.from(arr);    // Shallow
const arrClone4 = structuredClone(arr); // Deep

// HANDLING SPECIAL CASES
const complex = {
    date: new Date(),
    regex: /test/g,
    func: () => console.log("hi"),
    undef: undefined
};

// JSON method loses these
const jsonClone = JSON.parse(JSON.stringify(complex));
console.log(jsonClone.date);   // String, not Date!
console.log(jsonClone.func);   // undefined (lost)
console.log(jsonClone.undef);  // undefined (lost in JSON)

// structuredClone preserves most
const structuredCopy = structuredClone({
    date: new Date(),
    nested: { a: 1 }
});
console.log(structuredCopy.date instanceof Date);  // true
```

---

## 76. What is the difference between `null` and `undefined`?

### Answer:
- **undefined**: Variable declared but **not assigned**
- **null**: Intentional **absence of value** (assigned explicitly)
- `typeof undefined` → `"undefined"`
- `typeof null` → `"object"` (historical bug)
- `undefined == null` → `true` (loose equality)
- `undefined === null` → `false` (strict equality)

### Theoretical Keywords:
**Uninitialized**, **Intentional empty**, **Type differences**,  
**Loose equality**, **Strict equality**, **typeof bug**,  
**Default values**, **Explicit assignment**

### Example:
```javascript
// undefined - system assigned
let x;                    // undefined
function fn(param) {
    console.log(param);   // undefined if not passed
}
const obj = {};
console.log(obj.missing); // undefined

// null - developer assigned
let y = null;             // Intentionally empty
let user = null;          // Will be assigned later
const response = { data: null };  // Explicitly no data

// Type comparison
console.log(typeof undefined);  // "undefined"
console.log(typeof null);       // "object" (bug!)

// Equality comparison
console.log(undefined == null);   // true
console.log(undefined === null);  // false

// Practical usage
function getUser(id) {
    if (!id) return null;  // Explicitly return "no user"
    return { id, name: "John" };
}

let result = getUser();
if (result === null) {
    console.log("No user found");
}

// Default parameters
function greet(name = "Guest") {
    console.log(`Hello, ${name}`);
}
greet(undefined);  // "Hello, Guest" (default triggers)
greet(null);       // "Hello, null" (null doesn't trigger default!)
```

---

## 77. What is the difference between `call`, `apply`, and `bind`?

### Answer:
- All three set `this` context explicitly
- **call**: Invokes immediately, arguments passed **individually**
- **apply**: Invokes immediately, arguments passed as **array**
- **bind**: Returns **new function** with bound `this`, doesn't invoke
- Use `call` for known arguments, `apply` for arrays
- Use `bind` when you need function reference for later

### Theoretical Keywords:
**Explicit this binding**, **Function invocation**, **Arguments passing**,  
**Returned function**, **Immediate vs deferred**, **Method borrowing**,  
**Partial application**, **Context binding**

### Example:
```javascript
const person = { name: "John" };

function greet(greeting, punctuation) {
    console.log(`${greeting}, ${this.name}${punctuation}`);
}

// CALL - invokes immediately, individual arguments
greet.call(person, "Hello", "!");     // "Hello, John!"

// APPLY - invokes immediately, array of arguments
greet.apply(person, ["Hi", "?"]);     // "Hi, John?"

// BIND - returns new function, doesn't invoke
const boundGreet = greet.bind(person);
boundGreet("Hey", ".");               // "Hey, John."

// Partial application with bind
const sayHelloTo = greet.bind(person, "Hello");
sayHelloTo("!");                      // "Hello, John!"

// Practical use cases
// call - Method borrowing
const arrayLike = { 0: 'a', 1: 'b', length: 2 };
const realArray = Array.prototype.slice.call(arrayLike);

// apply - Math operations with arrays
const numbers = [5, 6, 2, 3, 7];
const max = Math.max.apply(null, numbers);  // 7
// Modern: Math.max(...numbers)

// bind - Event handlers
class Button {
    constructor(label) {
        this.label = label;
    }
    
    handleClick() {
        console.log(`${this.label} clicked`);
    }
}

const btn = new Button("Submit");
// Without bind, 'this' would be the button element
element.addEventListener('click', btn.handleClick.bind(btn));
```

---

## 78. What is event delegation and why is it used?

### Answer:
- Attaching **single handler to parent** instead of multiple children
- Uses **event bubbling** to catch events from descendants
- **Why**: Memory efficiency, handles dynamic elements
- Check `event.target` to identify actual clicked element
- Reduces number of event listeners
- Perfect for lists, tables, dynamic content

### Theoretical Keywords:
**Parent handler**, **Event bubbling**, **Dynamic elements**,  
**Memory efficiency**, **event.target**, **Single handler**,  
**Performance**, **Maintainability**

### Example:
```javascript
// Instead of adding handler to each item
const list = document.getElementById('todo-list');

list.addEventListener('click', (e) => {
    if (e.target.matches('.delete-btn')) {
        e.target.closest('li').remove();
    }
    if (e.target.matches('.edit-btn')) {
        // Edit logic
    }
});

// Benefits:
// 1. One handler vs many
// 2. Works for dynamically added items
// 3. Less memory usage
// 4. Easier to manage
```

---

## 79. What is the difference between `map`, `filter`, and `reduce`?

### Answer:
- **map**: Transforms each element, returns **new array of same length**
- **filter**: Keeps elements passing test, returns **filtered array**
- **reduce**: Combines all elements into **single value**
- All three don't modify original array
- Can be chained together
- `map`/`filter` more readable, `reduce` more powerful

### Theoretical Keywords:
**Transformation**, **Filtering**, **Aggregation**, **Immutability**,  
**Higher-order functions**, **Method chaining**, **Callback functions**,  
**Functional programming**

### Example:
```javascript
const numbers = [1, 2, 3, 4, 5];

// MAP - transform each element
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8, 10] - same length

// FILTER - keep matching elements
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4] - filtered

// REDUCE - combine into single value
const sum = numbers.reduce((acc, n) => acc + n, 0);
// 15 - single value

// Chaining
const result = numbers
    .filter(n => n > 2)    // [3, 4, 5]
    .map(n => n * 2)       // [6, 8, 10]
    .reduce((a, b) => a + b); // 24
```

---

## 80. What is hoisting and why does it happen?

### Answer:
- Hoisting moves **declarations** to top of scope during compilation
- Only **declarations** hoisted, not **initializations**
- `var` hoisted with `undefined`, `let`/`const` in TDZ
- **Functions** fully hoisted (can call before declaration)
- **Why**: JavaScript's two-phase execution (compile then execute)
- Helps with mutual recursion and flexible code organization

### Theoretical Keywords:
**Declaration hoisting**, **Compilation phase**, **Execution phase**,  
**var vs let/const**, **Function hoisting**, **TDZ**,  
**Memory allocation**, **Two-phase execution**

### Example:
```javascript
// var hoisting
console.log(x);  // undefined (not error!)
var x = 5;

// Function hoisting
greet();  // Works!
function greet() {
    console.log("Hello");
}

// let/const - TDZ
// console.log(y);  // ReferenceError
let y = 10;
```

---

## 81. What is temporal dead zone?

### Answer:
- TDZ is time between **scope entry** and **variable declaration**
- Variables exist but **cannot be accessed** in TDZ
- Applies to `let`, `const`, and class declarations
- Accessing throws **ReferenceError**
- Helps catch bugs from using variables before declaration
- `var` doesn't have TDZ (initialized with undefined)

### Theoretical Keywords:
**Temporal Dead Zone**, **ReferenceError**, **let/const**,  
**Scope entry**, **Declaration line**, **Bug prevention**,  
**Hoisting behavior**, **Variable initialization**

### Example:
```javascript
{
    // TDZ starts for 'x'
    // console.log(x);  // ReferenceError!
    let x = 10;  // TDZ ends
    console.log(x);  // 10
}
```

---

## 82. How does garbage collection work in JavaScript?

### Answer:
- **Automatic** memory management - no manual deallocation
- Uses **mark-and-sweep** algorithm
- Marks all **reachable** objects starting from roots
- **Sweeps** (frees) unmarked objects
- **Roots**: global object, call stack, closures
- Memory leaks happen when unneeded references persist

### Theoretical Keywords:
**Mark-and-sweep**, **Reachability**, **Memory management**,  
**Automatic cleanup**, **Roots**, **Memory leaks**,  
**Reference counting**, **Heap cleanup**

### Example:
```javascript
// Object eligible for GC when no references
let obj = { data: "large" };
obj = null;  // Now eligible for garbage collection

// Memory leak - circular reference (old browsers)
// Modern engines handle this

// Memory leak - forgotten closures
function createLeak() {
    const hugeData = new Array(1000000);
    return function() {
        console.log(hugeData.length);  // Holds reference!
    };
}
const leak = createLeak();  // hugeData stays in memory

// Fixing leaks
// 1. Set unused references to null
// 2. Remove event listeners when not needed
// 3. Clear intervals/timeouts
// 4. Be careful with closures
```

---
