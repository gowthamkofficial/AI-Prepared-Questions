# CLOSURES AND `this` KEYWORD ANSWERS

---

## 49. What is a closure?

### Answer:
- A closure is a function that **remembers** its outer scope even after outer function returns
- Inner function has access to **outer function's variables**
- Variables are kept alive in memory (not garbage collected)
- Closures "close over" the **lexical environment**
- Created every time a function is created
- Foundation for many JavaScript patterns (modules, currying, etc.)

### Theoretical Keywords:
**Lexical scope**, **Variable persistence**, **Function scope**,  
**Memory retention**, **Encapsulation**, **Data privacy**,  
**Function factory**, **Stateful functions**

### Example:
```javascript
// Basic closure
function outer() {
    let count = 0;  // Outer variable
    
    function inner() {
        count++;    // Accessing outer variable
        return count;
    }
    
    return inner;   // Return the inner function
}

const counter = outer();  // outer() returns inner function
console.log(counter());   // 1
console.log(counter());   // 2
console.log(counter());   // 3
// 'count' persists between calls!

// Each call creates new closure
const counter2 = outer();
console.log(counter2());  // 1 (separate count variable)

// Closure for data privacy
function createBankAccount(initialBalance) {
    let balance = initialBalance;  // Private variable
    
    return {
        deposit: function(amount) {
            balance += amount;
            return balance;
        },
        withdraw: function(amount) {
            if (amount <= balance) {
                balance -= amount;
                return balance;
            }
            return "Insufficient funds";
        },
        getBalance: function() {
            return balance;
        }
    };
}

const account = createBankAccount(100);
console.log(account.getBalance());  // 100
console.log(account.deposit(50));   // 150
console.log(account.balance);       // undefined (private!)
```

---

## 50. Real-time use cases of closures

### Answer:
- **Data privacy/Encapsulation**: Hide internal state from outside access
- **Function factories**: Create specialized functions with preset values
- **Callbacks with state**: Maintain state in event handlers
- **Module pattern**: Create private/public interface
- **Currying and partial application**: Transform multi-argument functions
- **Memoization**: Cache expensive computation results

### Theoretical Keywords:
**Encapsulation**, **Factory functions**, **Module pattern**,  
**Event handlers**, **Currying**, **Memoization**,  
**State management**, **Private variables**

### Example:
```javascript
// 1. Function Factory
function createMultiplier(multiplier) {
    return function(number) {
        return number * multiplier;
    };
}
const double = createMultiplier(2);
const triple = createMultiplier(3);
console.log(double(5));   // 10
console.log(triple(5));   // 15

// 2. Event Handler with State
function setupButton(buttonId) {
    let clickCount = 0;
    
    document.getElementById(buttonId).addEventListener('click', function() {
        clickCount++;
        console.log(`Clicked ${clickCount} times`);
    });
}

// 3. Module Pattern
const calculator = (function() {
    let result = 0;  // Private
    
    return {
        add: (n) => result += n,
        subtract: (n) => result -= n,
        getResult: () => result,
        reset: () => result = 0
    };
})();

// 4. Memoization
function memoize(fn) {
    const cache = {};  // Closure maintains cache
    
    return function(...args) {
        const key = JSON.stringify(args);
        if (cache[key]) {
            console.log("From cache");
            return cache[key];
        }
        const result = fn.apply(this, args);
        cache[key] = result;
        return result;
    };
}

const expensiveFunc = memoize((n) => {
    console.log("Computing...");
    return n * 2;
});

// 5. setTimeout in loops (classic closure problem)
// Wrong - all log 5
for (var i = 0; i < 5; i++) {
    setTimeout(() => console.log(i), 1000);
}

// Fixed with closure (IIFE)
for (var i = 0; i < 5; i++) {
    (function(j) {
        setTimeout(() => console.log(j), 1000);
    })(i);
}

// Or simply use let (block-scoped)
for (let i = 0; i < 5; i++) {
    setTimeout(() => console.log(i), 1000);
}
```

---

## 51. What is the `this` keyword?

### Answer:
- `this` refers to the **object that is executing the current function**
- Value of `this` is determined at **runtime**, not definition time
- In global scope: `this` = window (browser) or global (Node.js)
- In object method: `this` = the object calling the method
- In event handler: `this` = the element that triggered event
- Can be explicitly set using **call, apply, bind**

### Theoretical Keywords:
**Execution context**, **Runtime binding**, **Dynamic binding**,  
**Object reference**, **Method invocation**, **Global object**,  
**call/apply/bind**, **Arrow function this**

### Example:
```javascript
// Global context
console.log(this);  // window (browser) or global (Node.js)

// Object method
const person = {
    name: "John",
    greet: function() {
        console.log(this.name);  // this = person
    }
};
person.greet();  // "John"

// Function call
function showThis() {
    console.log(this);
}
showThis();  // window (non-strict) or undefined (strict mode)

// Constructor function
function Person(name) {
    this.name = name;  // this = new object being created
}
const john = new Person("John");
console.log(john.name);  // "John"

// Event handler
button.addEventListener('click', function() {
    console.log(this);  // this = button element
});

// Explicit binding
const obj = { name: "Object" };
function sayName() {
    console.log(this.name);
}
sayName.call(obj);   // "Object" - call with this = obj
sayName.apply(obj);  // "Object" - apply with this = obj
const bound = sayName.bind(obj);
bound();             // "Object" - permanently bound
```

---

## 52. How does `this` behave in different scenarios?

### Answer:
- **Global context**: `this` = window/global object
- **Object method**: `this` = object owning the method
- **Simple function**: `this` = window (non-strict) or undefined (strict)
- **Constructor (new)**: `this` = newly created object
- **Event handler**: `this` = element that fired event
- **Arrow function**: `this` = inherited from enclosing scope
- **call/apply/bind**: `this` = explicitly provided object

### Theoretical Keywords:
**Implicit binding**, **Explicit binding**, **new binding**,  
**Default binding**, **Lexical binding**, **Strict mode**,  
**Method borrowing**, **Lost this**

### Example:
```javascript
// 1. Global Context
function globalThis() {
    console.log(this);
}
globalThis();  // window (non-strict) or undefined (strict)

// 2. Object Method
const obj = {
    name: "Object",
    method: function() {
        console.log(this.name);  // "Object"
    }
};
obj.method();

// 3. Method borrowed/extracted - LOSES this
const extracted = obj.method;
extracted();  // undefined (this is now window/undefined)

// 4. Callback loses this
const user = {
    name: "John",
    greet: function() {
        setTimeout(function() {
            console.log(this.name);  // undefined! (this = window)
        }, 1000);
    }
};

// 5. Fix with arrow function (lexical this)
const userFixed = {
    name: "John",
    greet: function() {
        setTimeout(() => {
            console.log(this.name);  // "John" (arrow inherits this)
        }, 1000);
    }
};

// 6. Constructor function
function Car(model) {
    this.model = model;  // this = new Car instance
}
const myCar = new Car("Tesla");

// 7. Explicit binding
function greet() {
    console.log(`Hello, ${this.name}`);
}
greet.call({ name: "John" });   // "Hello, John"
greet.apply({ name: "Jane" });  // "Hello, Jane"

// 8. Permanent binding with bind
const boundGreet = greet.bind({ name: "Bob" });
boundGreet();  // "Hello, Bob"
boundGreet.call({ name: "Alice" });  // Still "Hello, Bob" (bind is permanent)
```

---

## 53. `this` in arrow functions vs normal functions

### Answer:
- **Arrow function**: Does NOT have its own `this`
- **Arrow function**: Inherits `this` from **lexical scope** (enclosing context)
- **Normal function**: Has its own `this`, determined by how it's called
- Arrow functions **cannot** have `this` changed by call/apply/bind
- Arrow functions **cannot** be used as constructors
- Arrow functions ideal for **callbacks** to preserve `this`

### Theoretical Keywords:
**Lexical this**, **Dynamic this**, **Enclosing scope**,  
**call/apply/bind ineffective**, **No own this**,  
**Callback pattern**, **Method definition**

### Example:
```javascript
// Normal function - dynamic this
const obj = {
    name: "Object",
    normalMethod: function() {
        console.log(this.name);
    }
};
obj.normalMethod();  // "Object"

const extracted = obj.normalMethod;
extracted();  // undefined (this changed!)

// Arrow function - lexical this
const obj2 = {
    name: "Object",
    arrowMethod: () => {
        console.log(this.name);  // this = outer scope (window)
    }
};
obj2.arrowMethod();  // undefined (not the object!)

// Arrow functions shine in callbacks
const person = {
    name: "John",
    friends: ["Alice", "Bob"],
    
    // PROBLEM with normal function
    listFriendsBad: function() {
        this.friends.forEach(function(friend) {
            console.log(`${this.name} knows ${friend}`);  // this.name undefined!
        });
    },
    
    // SOLUTION with arrow function
    listFriendsGood: function() {
        this.friends.forEach((friend) => {
            console.log(`${this.name} knows ${friend}`);  // "John knows ..."
        });
    }
};

// call/apply/bind don't work on arrow functions
const arrowFn = () => console.log(this);
arrowFn.call({ name: "Custom" });  // Still window (call ignored!)

// Arrow as object method (anti-pattern)
const bad = {
    name: "Bad Example",
    getName: () => this.name  // Don't do this! this = window
};

// Arrow in class methods (good pattern)
class Component {
    constructor() {
        this.name = "Component";
    }
    
    // Arrow preserves 'this' when passed as callback
    handleClick = () => {
        console.log(this.name);  // Always "Component"
    }
}
```

---
