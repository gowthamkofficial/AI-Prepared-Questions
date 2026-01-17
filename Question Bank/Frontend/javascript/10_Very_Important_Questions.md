# VERY IMPORTANT QUESTIONS (SERVICE COMPANIES) ANSWERS

---

## 60. Explain JavaScript execution flow step by step

### Answer:
- **Step 1**: JavaScript engine receives code
- **Step 2**: **Parsing** - Code is parsed into AST (Abstract Syntax Tree)
- **Step 3**: **Creation Phase** - Global Execution Context created, variables hoisted
- **Step 4**: **Execution Phase** - Code executed line by line
- **Step 5**: Functions create new execution contexts when called
- **Step 6**: Event loop handles async operations after sync code completes

### Theoretical Keywords:
**Parsing**, **AST**, **Execution Context**, **Creation phase**,  
**Execution phase**, **Call stack**, **Hoisting**, **Event loop**,  
**Compilation**, **Interpretation**

### Example:
```javascript
console.log("Start");           // 1. Sync - executes first

var x = 10;                     // 2. Sync - x assigned

function greet(name) {          // 3. Function declaration hoisted
    console.log("Hello " + name);
}

setTimeout(() => {              // 4. Async - callback queued
    console.log("Timeout");
}, 0);

Promise.resolve().then(() => {  // 5. Microtask - queued
    console.log("Promise");
});

greet("John");                  // 6. Sync - function executed

console.log("End");             // 7. Sync - executes

// OUTPUT ORDER:
// "Start"      (sync)
// "Hello John" (sync - function call)
// "End"        (sync)
// "Promise"    (microtask - higher priority)
// "Timeout"    (macrotask - lower priority)

/*
EXECUTION FLOW:

1. PARSING PHASE:
   - Code is tokenized and parsed
   - AST (Abstract Syntax Tree) created

2. CREATION PHASE (Global Context):
   - x = undefined (var hoisted)
   - greet = [function] (fully hoisted)
   - Memory allocated

3. EXECUTION PHASE:
   - "Start" logged
   - x = 10 (assigned)
   - setTimeout registered (callback to macrotask queue)
   - Promise.then registered (callback to microtask queue)
   - greet("John") called:
     * New execution context created
     * "Hello John" logged
     * Context popped from stack
   - "End" logged

4. EVENT LOOP (call stack empty):
   - Check microtask queue → "Promise" logged
   - Check macrotask queue → "Timeout" logged
*/
```

---

## 61. Difference between synchronous and asynchronous code

### Answer:
- **Synchronous**: Executes **sequentially**, blocks until complete
- **Asynchronous**: Executes **independently**, doesn't block
- Sync code runs on **main thread** one at a time
- Async operations handled by **Web APIs/Node APIs**
- Async callbacks processed via **event loop**
- JavaScript is **single-threaded** but **non-blocking**

### Theoretical Keywords:
**Blocking vs non-blocking**, **Sequential execution**, **Concurrent execution**,  
**Main thread**, **Web APIs**, **Event loop**, **Callback queue**,  
**Single-threaded**, **Non-blocking I/O**

### Example:
```javascript
// SYNCHRONOUS - Blocking
console.log("1");
console.log("2");
console.log("3");
// Output: 1, 2, 3 (always in order)

// Heavy sync operation blocks everything
function heavyComputation() {
    for (let i = 0; i < 1000000000; i++) {}
    console.log("Done computing");
}
console.log("Before");
heavyComputation();  // Blocks here - UI freezes!
console.log("After");  // Waits for computation

// ASYNCHRONOUS - Non-blocking
console.log("1");
setTimeout(() => console.log("2"), 1000);  // Doesn't block
console.log("3");
// Output: 1, 3, 2 (3 doesn't wait for 2)

// Real-world async example
console.log("Fetching data...");
fetch('/api/users')
    .then(response => response.json())
    .then(data => console.log("Data received:", data));
console.log("Request sent, continuing...");  // Doesn't wait!

// Async with async/await (looks sync but isn't)
async function getData() {
    console.log("Start");
    const response = await fetch('/api/data');  // Pauses here but doesn't block
    const data = await response.json();
    console.log("Data:", data);
    return data;
}

// WHY ASYNC MATTERS:
// - Network requests take time
// - File operations are slow
// - User interactions need responsive UI
// - Single thread can't wait for everything
```

---

## 62. How does the event loop work?

### Answer:
- Event loop is the **mechanism** enabling async in single-threaded JS
- Continuously monitors **call stack** and **callback queues**
- Moves callbacks to stack only when **stack is empty**
- **Microtask queue** (Promises) has priority over **macrotask queue** (setTimeout)
- Each iteration of loop is called a **"tick"**
- Enables **non-blocking** behavior in JavaScript

### Theoretical Keywords:
**Call stack monitoring**, **Callback queue**, **Microtask queue**,  
**Macrotask queue**, **Task priority**, **Non-blocking**,  
**Single-threaded concurrency**, **Tick**

### Example:
```javascript
console.log("Script start");

setTimeout(() => {
    console.log("setTimeout 1");
}, 0);

Promise.resolve()
    .then(() => console.log("Promise 1"))
    .then(() => console.log("Promise 2"));

setTimeout(() => {
    console.log("setTimeout 2");
}, 0);

console.log("Script end");

/*
OUTPUT:
Script start
Script end
Promise 1
Promise 2
setTimeout 1
setTimeout 2

EVENT LOOP EXPLANATION:

1. CALL STACK executes sync code:
   - "Script start" logged
   - setTimeout1 callback → Macrotask queue
   - Promise callbacks → Microtask queue
   - setTimeout2 callback → Macrotask queue
   - "Script end" logged
   - Call stack EMPTY

2. EVENT LOOP checks:
   - Stack empty? YES
   - Microtask queue? YES → Process ALL
     * "Promise 1" logged
     * New microtask added (then)
     * "Promise 2" logged
   - Microtask queue? EMPTY now

3. EVENT LOOP continues:
   - Macrotask queue? YES → Process ONE
     * "setTimeout 1" logged
   - Check microtasks (none)
   - Macrotask queue? YES → Process ONE
     * "setTimeout 2" logged

PRIORITY ORDER:
1. Call Stack (sync code)
2. Microtask Queue (Promise, queueMicrotask, MutationObserver)
3. Macrotask Queue (setTimeout, setInterval, I/O, UI rendering)
*/

// Microtask vs Macrotask
queueMicrotask(() => console.log("Microtask"));  // Higher priority
setTimeout(() => console.log("Macrotask"), 0);   // Lower priority
// Output: Microtask, Macrotask
```

---

## 63. Explain closure with a real-time example

### Answer:
- Closure = **Function + its lexical environment**
- Inner function **remembers** outer function's variables
- Variables **persist** even after outer function returns
- Used for **data privacy**, **state management**, **callbacks**
- Created every time a function is defined inside another

### Theoretical Keywords:
**Lexical scope**, **Variable persistence**, **Data privacy**,  
**State management**, **Function factory**, **Module pattern**,  
**Memory retention**, **Encapsulation**

### Example:
```javascript
// REAL-TIME EXAMPLE 1: Counter Module
function createCounter() {
    let count = 0;  // Private variable - can't access directly
    
    return {
        increment: function() {
            count++;
            return count;
        },
        decrement: function() {
            count--;
            return count;
        },
        getCount: function() {
            return count;
        }
    };
}

const counter = createCounter();
console.log(counter.increment());  // 1
console.log(counter.increment());  // 2
console.log(counter.getCount());   // 2
console.log(counter.count);        // undefined (private!)

// REAL-TIME EXAMPLE 2: API Rate Limiter
function createRateLimiter(maxCalls, timeWindow) {
    let calls = [];  // Closure remembers call history
    
    return function(callback) {
        const now = Date.now();
        calls = calls.filter(time => now - time < timeWindow);
        
        if (calls.length < maxCalls) {
            calls.push(now);
            callback();
        } else {
            console.log("Rate limit exceeded!");
        }
    };
}

const limiter = createRateLimiter(3, 10000);  // 3 calls per 10 seconds

// REAL-TIME EXAMPLE 3: Debounce Function
function debounce(func, delay) {
    let timeoutId;  // Closure maintains timer reference
    
    return function(...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    };
}

const searchInput = debounce((query) => {
    console.log("Searching:", query);
}, 300);

// REAL-TIME EXAMPLE 4: Event Handler with Data
function setupButtons() {
    const buttons = document.querySelectorAll('.btn');
    
    buttons.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            // Closure remembers 'index' for each button
            console.log(`Button ${index} clicked`);
        });
    });
}
```

---

## 64. Difference between `var`, `let`, and `const` with example

### Answer:
- **Scope**: `var` function-scoped, `let`/`const` block-scoped
- **Hoisting**: `var` initialized as undefined, `let`/`const` in TDZ
- **Re-declaration**: `var` allows, `let`/`const` don't
- **Re-assignment**: `var`/`let` allow, `const` doesn't
- **Global object**: `var` attaches to window, `let`/`const` don't
- **Best practice**: `const` default, `let` when re-assignment needed

### Theoretical Keywords:
**Function scope**, **Block scope**, **Hoisting**, **Temporal Dead Zone**,  
**Re-declaration**, **Re-assignment**, **Global object pollution**,  
**ES6 features**, **Best practices**

### Example:
```javascript
// 1. SCOPE DIFFERENCE
function scopeExample() {
    if (true) {
        var varVariable = "var";
        let letVariable = "let";
        const constVariable = "const";
    }
    console.log(varVariable);   // "var" - accessible (function scope)
    // console.log(letVariable);  // Error - not accessible (block scope)
    // console.log(constVariable); // Error - not accessible (block scope)
}

// 2. HOISTING DIFFERENCE
console.log(a);  // undefined (var hoisted, initialized as undefined)
// console.log(b);  // ReferenceError (let in TDZ)
// console.log(c);  // ReferenceError (const in TDZ)

var a = 1;
let b = 2;
const c = 3;

// 3. RE-DECLARATION
var x = 1;
var x = 2;  // OK - var allows re-declaration

let y = 1;
// let y = 2;  // Error - let doesn't allow re-declaration

// 4. RE-ASSIGNMENT
var v = 1;
v = 2;  // OK

let l = 1;
l = 2;  // OK

const con = 1;
// con = 2;  // Error - const can't be re-assigned

// But const objects/arrays can be mutated!
const obj = { name: "John" };
obj.name = "Jane";  // OK - mutating, not re-assigning
// obj = {};  // Error - re-assigning

// 5. GLOBAL OBJECT
var globalVar = "I'm global";
let globalLet = "I'm also global";

console.log(window.globalVar);  // "I'm global"
console.log(window.globalLet);  // undefined (not on window)

// 6. LOOP BEHAVIOR (Classic Interview Question)
// var - shares same variable
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 1000);
}
// Output: 3, 3, 3 (all same!)

// let - new binding per iteration
for (let j = 0; j < 3; j++) {
    setTimeout(() => console.log(j), 1000);
}
// Output: 0, 1, 2 (as expected!)
```

---

## 65. Difference between `==` and `===` with example

### Answer:
- **`==` (Loose)**: Compares values after **type coercion**
- **`===` (Strict)**: Compares values AND types, **no coercion**
- `==` can have **unexpected results** due to coercion
- `===` is **predictable** and recommended
- Same applies to `!=` vs `!==`
- Always use **strict equality** in production code

### Theoretical Keywords:
**Type coercion**, **Abstract equality**, **Strict equality**,  
**Implicit conversion**, **Type comparison**, **Best practices**,  
**Predictable behavior**, **Bug prevention**

### Example:
```javascript
// LOOSE EQUALITY (==) - Type Coercion
console.log(5 == "5");       // true (string "5" → number 5)
console.log(0 == false);     // true (false → 0)
console.log("" == false);    // true (both convert to 0)
console.log(null == undefined); // true (special rule)
console.log("1" == true);    // true (both → 1)

// STRICT EQUALITY (===) - No Coercion
console.log(5 === "5");      // false (different types)
console.log(0 === false);    // false (number vs boolean)
console.log("" === false);   // false (string vs boolean)
console.log(null === undefined); // false (different types)
console.log("1" === true);   // false (string vs boolean)

// TRICKY CASES
console.log([] == false);    // true ([] → "" → 0, false → 0)
console.log([] == ![]);      // true ([] → 0, ![] → false → 0)
console.log({} == "[object Object]"); // true (object → string)

// WHY USE ===
let userInput = "5";

// Bug with ==
if (userInput == 5) {
    console.log("Matched!");  // Matches unexpectedly
}

// Safe with ===
if (userInput === 5) {
    console.log("Matched!");  // Won't match (correct behavior)
}

// Converting before comparing (explicit)
if (Number(userInput) === 5) {
    console.log("Matched after conversion!");
}

// SPECIAL CASES even with ===
console.log(NaN === NaN);  // false (NaN is never equal to itself)
console.log(Number.isNaN(NaN));  // Use this to check NaN

// Object comparison (reference, not value)
console.log({} === {});    // false (different references)
console.log([] === []);    // false (different references)

const obj = {};
const ref = obj;
console.log(obj === ref);  // true (same reference)
```

---

## 66. Explain promises and `async/await`

### Answer:
- **Promise**: Object representing future completion/failure of async operation
- **States**: pending → fulfilled (resolved) or rejected
- **async/await**: Syntactic sugar over Promises for cleaner code
- **async**: Makes function return a Promise
- **await**: Pauses execution until Promise resolves
- Both are used for handling asynchronous operations cleanly

### Theoretical Keywords:
**Asynchronous handling**, **Promise states**, **Syntactic sugar**,  
**then/catch/finally**, **try-catch**, **Sequential async**,  
**Error handling**, **Non-blocking**

### Example:
```javascript
// CREATING A PROMISE
function fetchUser(id) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (id > 0) {
                resolve({ id, name: "John" });
            } else {
                reject("Invalid ID");
            }
        }, 1000);
    });
}

// CONSUMING WITH .then()/.catch()
fetchUser(1)
    .then(user => {
        console.log("User:", user);
        return fetchOrders(user.id);  // Return promise for chaining
    })
    .then(orders => {
        console.log("Orders:", orders);
    })
    .catch(error => {
        console.error("Error:", error);
    })
    .finally(() => {
        console.log("Cleanup");
    });

// CONSUMING WITH async/await
async function getUserData(id) {
    try {
        const user = await fetchUser(id);    // Wait for promise
        console.log("User:", user);
        
        const orders = await fetchOrders(user.id);
        console.log("Orders:", orders);
        
        return { user, orders };
    } catch (error) {
        console.error("Error:", error);
        throw error;  // Re-throw if needed
    } finally {
        console.log("Cleanup");
    }
}

// PARALLEL EXECUTION
async function parallel() {
    // Sequential (slow)
    const user = await fetchUser(1);
    const product = await fetchProduct(1);
    // Total time: user + product
    
    // Parallel (fast)
    const [user2, product2] = await Promise.all([
        fetchUser(1),
        fetchProduct(1)
    ]);
    // Total time: max(user, product)
}

// PROMISE UTILITY METHODS
// Wait for all
const results = await Promise.all([p1, p2, p3]);

// First to complete
const fastest = await Promise.race([p1, p2, p3]);

// All settled (no short-circuit on error)
const all = await Promise.allSettled([p1, p2, p3]);

// First successful
const firstSuccess = await Promise.any([p1, p2, p3]);
```

---

## 67. How did you use JavaScript in your project?

### Answer:
- **DOM Manipulation**: Interactive UI elements, form validation
- **API Integration**: Fetch data from REST APIs
- **Event Handling**: User interactions (clicks, inputs, scrolls)
- **Form Validation**: Client-side validation before submission
- **State Management**: Managing application state
- **Asynchronous Operations**: Loading data, file uploads

### Theoretical Keywords:
**DOM manipulation**, **Event handling**, **AJAX/Fetch**,  
**Form validation**, **State management**, **Single Page Application**,  
**Error handling**, **Performance optimization**

### Example:
```javascript
// 1. DOM MANIPULATION
function updateUserProfile(user) {
    document.getElementById('username').textContent = user.name;
    document.getElementById('avatar').src = user.avatar;
    document.querySelector('.profile-card').classList.add('active');
}

// 2. API INTEGRATION
async function loadProducts() {
    try {
        const response = await fetch('/api/products');
        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        showError('Failed to load products');
    }
}

// 3. FORM VALIDATION
function validateForm(formData) {
    const errors = {};
    
    if (!formData.email.includes('@')) {
        errors.email = 'Invalid email format';
    }
    
    if (formData.password.length < 8) {
        errors.password = 'Password must be at least 8 characters';
    }
    
    return Object.keys(errors).length === 0 ? null : errors;
}

// 4. EVENT HANDLING
document.getElementById('searchInput').addEventListener('input', 
    debounce((e) => {
        searchProducts(e.target.value);
    }, 300)
);

// 5. STATE MANAGEMENT
const state = {
    cart: [],
    user: null,
    
    addToCart(product) {
        this.cart.push(product);
        this.updateCartUI();
    },
    
    updateCartUI() {
        document.getElementById('cart-count').textContent = this.cart.length;
    }
};

// 6. REAL PROJECT EXAMPLE: Todo App
const todoApp = {
    todos: [],
    
    async loadTodos() {
        const todos = await fetch('/api/todos').then(r => r.json());
        this.todos = todos;
        this.render();
    },
    
    async addTodo(text) {
        const todo = await fetch('/api/todos', {
            method: 'POST',
            body: JSON.stringify({ text }),
            headers: { 'Content-Type': 'application/json' }
        }).then(r => r.json());
        
        this.todos.push(todo);
        this.render();
    },
    
    render() {
        const list = document.getElementById('todo-list');
        list.innerHTML = this.todos.map(todo => `
            <li data-id="${todo.id}">
                ${todo.text}
                <button class="delete">Delete</button>
            </li>
        `).join('');
    }
};
```

---
