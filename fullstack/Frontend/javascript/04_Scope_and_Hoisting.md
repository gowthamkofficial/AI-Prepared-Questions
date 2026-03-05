# SCOPE AND HOISTING ANSWERS

---

## 23. What is scope?

### Answer:
- Scope determines the **accessibility** and **visibility** of variables
- Defines where variables can be **accessed** or **referenced**
- JavaScript has **three types** of scope: Global, Function, and Block
- Variables are looked up through **scope chain** (inner to outer)
- Scope helps prevent **naming conflicts** and manage memory
- Created at **compile time** (lexical/static scoping)

### Theoretical Keywords:
**Variable accessibility**, **Lexical scope**, **Scope chain**,  
**Variable lookup**, **Namespace**, **Encapsulation**,  
**Static scoping**, **Variable lifetime**

### Example:
```javascript
// Global scope
let globalVar = "I'm global";

function outer() {
    // Function scope
    let outerVar = "I'm in outer";
    
    function inner() {
        // Inner function scope
        let innerVar = "I'm in inner";
        
        console.log(globalVar);  // Accessible (global)
        console.log(outerVar);   // Accessible (outer scope)
        console.log(innerVar);   // Accessible (current scope)
    }
    
    inner();
    // console.log(innerVar);  // Error! Not accessible
}

outer();
// console.log(outerVar);  // Error! Not accessible
```

---

## 24. Global scope vs function scope vs block scope

### Answer:
- **Global Scope**: Variables declared outside any function/block, accessible everywhere
- **Function Scope**: Variables declared inside function, only accessible within function
- **Block Scope**: Variables declared inside `{}` (if, for, while), accessible only in that block
- `var` is **function-scoped**, `let` and `const` are **block-scoped**
- Global variables are attached to **window object** (in browsers)

### Theoretical Keywords:
**Global scope**, **Function scope**, **Block scope**, **Lexical scope**,  
**var vs let vs const**, **Window object**, **Module scope**,  
**Scope isolation**, **Variable shadowing**

### Example:
```javascript
// Global scope
var globalVar = "global";
let globalLet = "global let";

function testScope() {
    // Function scope
    var functionVar = "function";
    let functionLet = "function let";
    
    if (true) {
        // Block scope
        var blockVar = "var in block";    // Function scoped! (var ignores block)
        let blockLet = "let in block";    // Block scoped
        const blockConst = "const in block"; // Block scoped
        
        console.log(blockLet);    // Accessible
    }
    
    console.log(blockVar);        // Accessible (var is function-scoped)
    // console.log(blockLet);     // Error! Block scoped
}

testScope();

// Global var attaches to window
console.log(window.globalVar);    // "global"
console.log(window.globalLet);    // undefined (let doesn't attach)
```

---

## 25. What is hoisting?

### Answer:
- Hoisting is JavaScript's behavior of moving **declarations** to the top of scope
- Happens during **compilation phase** before execution
- **Only declarations** are hoisted, not **initializations**
- `var` is hoisted and initialized with **undefined**
- `let` and `const` are hoisted but in **Temporal Dead Zone (TDZ)**
- **Function declarations** are fully hoisted (body included)

### Theoretical Keywords:
**Declaration hoisting**, **Compilation phase**, **Execution phase**,  
**Variable initialization**, **Temporal Dead Zone**, **Function hoisting**,  
**Memory allocation**, **Two-phase execution**

### Example:
```javascript
// What we write
console.log(x);  // undefined (not ReferenceError!)
var x = 5;

// How JavaScript interprets it (conceptually)
var x;           // Declaration hoisted
console.log(x);  // undefined
x = 5;           // Initialization stays

// Function hoisting
sayHello();      // Works! "Hello"

function sayHello() {
    console.log("Hello");
}

// Function expression NOT hoisted
// greet();  // Error: Cannot access before initialization
const greet = function() {
    console.log("Hi");
};
```

---

## 26. Hoisting behavior of `var`, `let`, and `const`

### Answer:
- **var**: Hoisted and initialized with `undefined`, accessible before declaration
- **let**: Hoisted but NOT initialized, throws ReferenceError if accessed before declaration
- **const**: Same as `let`, plus MUST be initialized at declaration
- **Function declarations**: Fully hoisted with body
- **Function expressions**: Only variable hoisted, not the function body
- **Class declarations**: Hoisted but in TDZ (like let/const)

### Theoretical Keywords:
**var hoisting**, **let hoisting**, **const hoisting**,  
**Temporal Dead Zone**, **ReferenceError**, **Initialization**,  
**Function vs variable hoisting**, **Best practices**

### Example:
```javascript
// var - hoisted with undefined
console.log(a);  // undefined
var a = 10;
console.log(a);  // 10

// let - hoisted but in TDZ
// console.log(b);  // ReferenceError: Cannot access 'b' before initialization
let b = 20;
console.log(b);  // 20

// const - same as let, must initialize
// console.log(c);  // ReferenceError
const c = 30;
// const d;  // SyntaxError: Missing initializer

// Function declaration - fully hoisted
greet();  // "Hello!" (works)
function greet() {
    console.log("Hello!");
}

// Function expression - only var hoisted
// sayHi();  // TypeError: sayHi is not a function
var sayHi = function() {
    console.log("Hi!");
};
```

---

## 27. What is the temporal dead zone?

### Answer:
- TDZ is the time between entering scope and variable **declaration being processed**
- Variables exist in TDZ from **start of block** until declaration line
- Accessing variable in TDZ throws **ReferenceError**
- Applies to `let`, `const`, and **class declarations**
- **var** does NOT have TDZ (initialized with undefined)
- TDZ helps catch **use before declaration** bugs

### Theoretical Keywords:
**Temporal Dead Zone**, **ReferenceError**, **Variable initialization**,  
**Hoisting vs accessibility**, **let and const**, **Scope entry**,  
**Dead zone**, **Bug prevention**

### Example:
```javascript
// TDZ visualization
{
    // TDZ for `x` starts here (block entry)
    // console.log(x);  // ReferenceError: Cannot access 'x' before initialization
    
    // Still in TDZ
    const fn = () => x;  // Reference to x is fine (not accessing yet)
    // fn();  // But calling would error
    
    let x = 10;  // TDZ ends here
    
    console.log(x);  // 10 (safe now)
    console.log(fn());  // 10 (safe to call now)
}

// TDZ in function parameters
function example(a = b, b = 2) {  // 'b' is in TDZ when 'a' is evaluated
    return a + b;
}
// example();  // ReferenceError

// Why TDZ exists - catches bugs
function buggyCode() {
    // With var, this silently uses undefined
    console.log(name);  // undefined (if var)
    // console.log(name);  // ReferenceError (if let - better!)
    let name = "John";
}
```

---
