# FUNCTIONS ANSWERS

---

## 17. What is a function?

### Answer:
- A function is a **reusable block of code** designed to perform a specific task
- Functions help achieve **DRY (Don't Repeat Yourself)** principle
- Can accept **parameters** and **return** values
- Creates its own **scope** for variables
- Functions are **first-class citizens** in JavaScript (can be assigned, passed, returned)
- Helps in **modularity** and **code organization**

### Theoretical Keywords:
**Reusability**, **Modularity**, **Parameters**, **Arguments**,  
**Return value**, **Function scope**, **First-class functions**,  
**Abstraction**, **Single responsibility**

### Example:
```javascript
// Function declaration
function greet(name) {
    return `Hello, ${name}!`;
}

console.log(greet("John"));  // "Hello, John!"

// Functions as first-class citizens
const sayHello = greet;  // assigned to variable
const result = [1, 2, 3].map(x => x * 2);  // passed as argument
```

---

## 18. Types of functions in JavaScript

### Answer:
- **Named Functions**: Have a name identifier
- **Anonymous Functions**: No name, often used as callbacks
- **Arrow Functions**: ES6 shorthand syntax with `=>`
- **IIFE**: Immediately Invoked Function Expression
- **Constructor Functions**: Create objects with `new`
- **Generator Functions**: Use `function*` and `yield`
- **Async Functions**: Use `async/await` for promises

### Theoretical Keywords:
**Named function**, **Anonymous function**, **Arrow function**,  
**IIFE**, **Constructor function**, **Generator function**,  
**Async function**, **Higher-order function**, **Pure function**

### Example:
```javascript
// Named function
function add(a, b) { return a + b; }

// Anonymous function
const multiply = function(a, b) { return a * b; };

// Arrow function
const divide = (a, b) => a / b;

// IIFE - Immediately Invoked Function Expression
(function() {
    console.log("Executed immediately!");
})();

// Generator function
function* numberGenerator() {
    yield 1;
    yield 2;
    yield 3;
}

// Async function
async function fetchData() {
    const response = await fetch('/api/data');
    return response.json();
}
```

---

## 19. Function declaration vs function expression

### Answer:
- **Declaration**: Uses `function` keyword, can be called **before** definition (hoisted)
- **Expression**: Assigned to variable, **NOT hoisted** (only variable is hoisted)
- **Declaration**: Creates named function
- **Expression**: Can be anonymous or named
- **Declaration**: Hoisted to top of scope with function body
- **Expression**: Variable hoisted but assigned `undefined`

### Theoretical Keywords:
**Hoisting**, **Function hoisting vs variable hoisting**,  
**Named vs anonymous**, **Temporal Dead Zone**, **Scope**,  
**Initialization**, **Best practices**

### Example:
```javascript
// Function Declaration - hoisted
console.log(greet("John"));  // Works! "Hello, John!"

function greet(name) {
    return `Hello, ${name}!`;
}

// Function Expression - NOT hoisted
console.log(sayHi("John"));  // Error! Cannot access before initialization

const sayHi = function(name) {
    return `Hi, ${name}!`;
};

// Named function expression (useful for recursion/debugging)
const factorial = function fact(n) {
    return n <= 1 ? 1 : n * fact(n - 1);
};
```

---

## 20. Arrow functions

### Answer:
- **ES6 feature** providing shorter syntax for functions
- **Implicit return** for single expressions (no curly braces)
- **Lexical `this`** binding (inherits `this` from enclosing scope)
- **Cannot** be used as constructors (`new` keyword)
- **No** `arguments` object (use rest parameters instead)
- **No** own `this`, `super`, or `new.target`

### Theoretical Keywords:
**ES6 syntax**, **Lexical this**, **Implicit return**,  
**Concise body**, **Block body**, **Rest parameters**,  
**No arguments object**, **Cannot be constructor**

### Example:
```javascript
// Traditional function
function add(a, b) {
    return a + b;
}

// Arrow function - block body
const addArrow = (a, b) => {
    return a + b;
};

// Arrow function - concise body (implicit return)
const addShort = (a, b) => a + b;

// Single parameter - no parentheses needed
const double = x => x * 2;

// No parameters - empty parentheses required
const getRandom = () => Math.random();

// Returning object literal - wrap in parentheses
const createPerson = (name) => ({ name: name, age: 0 });
```

---

## 21. Difference between arrow function and normal function

### Answer:
- **`this` binding**: Normal function has own `this`, arrow function inherits `this`
- **`arguments` object**: Normal function has it, arrow function doesn't
- **Constructor**: Normal function can be used with `new`, arrow function cannot
- **Methods**: Arrow functions not suitable for object methods
- **Hoisting**: Function declarations hoisted, arrow functions not
- **Syntax**: Arrow functions are more concise

### Theoretical Keywords:
**Lexical this**, **Dynamic this**, **arguments object**,  
**Constructor function**, **Method definition**, **Hoisting behavior**,  
**Use cases**, **Object methods**

### Example:
```javascript
// this behavior
const obj = {
    name: "John",
    
    // Normal function - own 'this'
    greet: function() {
        console.log(this.name);  // "John"
    },
    
    // Arrow function - inherits 'this' from surrounding scope
    greetArrow: () => {
        console.log(this.name);  // undefined (this = window/global)
    },
    
    // Use arrow in nested function to preserve 'this'
    delayedGreet: function() {
        setTimeout(() => {
            console.log(this.name);  // "John" (arrow inherits from delayedGreet)
        }, 1000);
    }
};

// arguments object
function normalFunc() {
    console.log(arguments);  // [1, 2, 3]
}
normalFunc(1, 2, 3);

const arrowFunc = (...args) => {
    console.log(args);  // [1, 2, 3] (use rest parameters)
};
arrowFunc(1, 2, 3);
```

---

## 22. What is a callback function?

### Answer:
- A **callback** is a function passed as an **argument** to another function
- Called/executed **after** the completion of an operation
- Enables **asynchronous programming** in JavaScript
- Common in event handlers, timers, and array methods
- Can lead to **callback hell** (deeply nested callbacks)
- Foundation for **Promises** and **async/await**

### Theoretical Keywords:
**Higher-order function**, **Asynchronous operation**, **Event handling**,  
**Callback hell**, **Inversion of control**, **Continuation passing**,  
**Non-blocking**, **Event loop**

### Example:
```javascript
// Callback in array method
const numbers = [1, 2, 3];
numbers.forEach(function(num) {  // callback function
    console.log(num);
});

// Callback in setTimeout
function greet(name) {
    console.log(`Hello, ${name}!`);
}
setTimeout(greet, 1000, "John");  // greet is callback

// Custom function with callback
function fetchData(callback) {
    setTimeout(() => {
        const data = { id: 1, name: "John" };
        callback(data);  // call the callback with data
    }, 1000);
}

fetchData(function(result) {
    console.log(result);  // { id: 1, name: "John" }
});

// Callback Hell (problem)
getData(function(a) {
    getMoreData(a, function(b) {
        getMoreData(b, function(c) {
            // deeply nested - hard to read and maintain
        });
    });
});
```

---
