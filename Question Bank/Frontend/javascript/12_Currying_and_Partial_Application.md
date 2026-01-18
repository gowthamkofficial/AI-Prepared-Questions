# CURRYING AND PARTIAL APPLICATION ANSWERS

---

## What is Currying?

### Answer:
- **Currying** is a technique of transforming a function that takes **multiple arguments** into a sequence of functions that take **one argument each**
- Named after mathematician **Haskell Curry**
- Returns a new function that accepts remaining arguments
- Creates **partial functions** - functions with some arguments pre-filled
- Enables **functional programming** patterns
- Improves **code reusability** and **composition**
- Different from **partial application** (though related)

### Theoretical Keywords:
**Function composition**, **Partial application**, **Higher-order function**,  
**Single-responsibility principle**, **Functional programming**, **Closure**,  
**Unary function**, **Function factory**, **Immutability**

### Example:
```javascript
// Regular function (multiple arguments)
function add(a, b, c) {
    return a + b + c;
}
console.log(add(1, 2, 3));  // 6

// Curried version (returns function)
function curriedAdd(a) {
    return function(b) {
        return function(c) {
            return a + b + c;
        };
    };
}
console.log(curriedAdd(1)(2)(3));  // 6

// Arrow function currying (cleaner)
const curriedAddArrow = a => b => c => a + b + c;
console.log(curriedAddArrow(1)(2)(3));  // 6

// Using curried function for partial application
const add1 = curriedAdd(1);        // Returns function
const add1and2 = add1(2);          // Returns function
const result = add1and2(3);        // Returns 6
console.log(result);  // 6

// Real-world: API call with base URL
function request(baseUrl) {
    return function(endpoint) {
        return function(params) {
            return fetch(`${baseUrl}${endpoint}`, params);
        };
    };
}

const apiRequest = request('https://api.example.com');
const getUsersRequest = apiRequest('/users');
const fetchUser1 = getUsersRequest({ userId: 1 });

// Simplified
const getUser = request('https://api.example.com')('/users')({ userId: 1 });

// Generic curry function (reusable)
function curry(fn) {
    const arity = fn.length;  // Number of parameters
    
    return function $curry(...args) {
        if (args.length >= arity) {
            return fn.apply(null, args);
        }
        return (...nextArgs) => $curry(...args, ...nextArgs);
    };
}

// Usage
const originalAdd = (a, b, c) => a + b + c;
const curriedAddGeneric = curry(originalAdd);
console.log(curriedAddGeneric(1)(2)(3));  // 6
console.log(curriedAddGeneric(1, 2)(3));  // 6 (partial)
console.log(curriedAddGeneric(1)(2, 3));  // 6 (partial)

// Data transformation pipeline
const map = curry((fn, array) => array.map(fn));
const filter = curry((predicate, array) => array.filter(predicate));

const double = map(x => x * 2);
const even = filter(x => x % 2 === 0);

const numbers = [1, 2, 3, 4, 5];
console.log(double(numbers));           // [2, 4, 6, 8, 10]
console.log(even(numbers));             // [2, 4]

// Composition with currying
const compose = (f, g) => x => f(g(x));
const isEven = n => n % 2 === 0;
const negate = p => x => !p(x);
const isOdd = negate(isEven);
console.log(isOdd(3));  // true
```

---

## Difference between Currying and Partial Application

### Answer:
- **Currying**: Transforms function into sequence of **single-argument functions**
- **Partial Application**: Creates new function by **fixing some arguments** of existing function
- **Currying**: Always returns a **unary function** (takes 1 argument)
- **Partial Application**: Can return function taking **multiple arguments**
- **Currying**: More formal/structured approach
- **Partial Application**: More flexible/practical approach
- **Both** use **closures** to preserve fixed arguments

### Theoretical Keywords:
**Function transformation**, **Closure**, **Higher-order function**,  
**Fixed arguments**, **Function arity**, **Unary function**,  
**Binary function**, **Functional programming**

### Example:
```javascript
// Original function
function multiply(a, b, c) {
    return a * b * c;
}

// CURRYING: converts to single-argument functions
function curriedMultiply(a) {
    return (b) => (c) => a * b * c;
}
console.log(curriedMultiply(2)(3)(4));  // 24

// PARTIAL APPLICATION: fixes some arguments
function partialMultiply(a) {
    return (b, c) => a * b * c;
}
console.log(partialMultiply(2)(3, 4));  // 24

// Real example: API requests
// With currying
const curriedPost = (url) => (headers) => (body) => {
    return fetch(url, { method: 'POST', headers, body });
};

// With partial application
const partialPost = (url) => (headers, body) => {
    return fetch(url, { method: 'POST', headers, body });
};

// Using currying (one arg at a time)
const postToUsers = curriedPost('https://api.example.com/users');
const withAuth = postToUsers({ 'Authorization': 'Bearer token' });
const response = withAuth(JSON.stringify({ name: 'John' }));

// Using partial application (multiple args at once)
const createUser = partialPost('https://api.example.com/users');
const response2 = createUser({ 'Authorization': 'Bearer token' }, JSON.stringify({ name: 'John' }));
```

---

## Use Cases of Currying in Real Projects

### Answer:
- **API Configuration**: Pre-configure base URLs and headers
- **Event Handlers**: Create specialized handlers with context
- **Validation**: Build validators for specific rules
- **Logging**: Create loggers with specific contexts/levels
- **Data Transformation**: Build reusable data pipelines
- **Testing**: Create mock functions with preset arguments
- **Configuration Management**: Store app configuration

### Example:
```javascript
// 1. Logger with context
function logger(level) {
    return function(module) {
        return function(message) {
            console.log(`[${level}] [${module}] ${message}`);
        };
    };
}

const infoLogger = logger('INFO');
const appLogger = infoLogger('App');
appLogger('Application started');  // [INFO] [App] Application started

// 2. Validator factory
const validateEmail = (pattern) => (value) => pattern.test(value);
const isValidEmail = validateEmail(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
console.log(isValidEmail('test@example.com'));  // true

// 3. Database query builder
const query = (table) => (condition) => (select) => {
    return `SELECT ${select} FROM ${table} WHERE ${condition}`;
};

const userQuery = query('users');
const activeUsers = userQuery('status = "active"');
const names = activeUsers('name, email');
console.log(names);  // SELECT name, email FROM users WHERE status = "active"

// 4. Discount calculator
const applyDiscount = (discountRate) => (quantity) => (price) => {
    const subtotal = quantity * price;
    const discount = subtotal * (discountRate / 100);
    return subtotal - discount;
};

const summerSale = applyDiscount(20);  // 20% discount
const bulkBuy = summerSale(100);       // 100 items
const totalPrice = bulkBuy(50);         // $50 per item
console.log(totalPrice);  // $4000

// 5. Function composition helper
const pipe = (...fns) => (value) =>
    fns.reduce((acc, fn) => fn(acc), value);

const addTax = (rate) => (amount) => amount * (1 + rate);
const applyTax = addTax(0.18);  // 18% tax

const withDiscount = pipe(
    (price) => price * 0.8,    // 20% off
    applyTax,                   // Add tax
    (price) => price.toFixed(2) // Format to 2 decimals
);

console.log(withDiscount(100));  // 77.76
```

---

## Interview Tips for Currying Questions

### Common Interview Questions:
1. **What is currying?** → Transform multi-argument function to single-argument functions
2. **When would you use currying?** → API configuration, validation, event handlers
3. **How does currying relate to closures?** → Closures preserve intermediate arguments
4. **Difference from partial application?** → Currying is strict (one arg), partial is flexible (multi-arg)
5. **Write a curry function** → See the generic curry() example above
6. **Real-world example?** → Database query builder or API logger

### Key Points to Mention:
- ✅ **Reusability**: Create specialized versions of generic functions
- ✅ **Composition**: Build complex operations from simple ones
- ✅ **Encapsulation**: Hide internal state with closures
- ✅ **Functional programming**: Core concept in functional paradigm
- ✅ **Performance consideration**: Each call creates new function (slight overhead)
- ✅ **Readability**: Can make code more readable or harder (context-dependent)

### Common Mistakes to Avoid:
- ❌ Confusing currying with partial application
- ❌ Overusing currying (not every function needs it)
- ❌ Creating deeply nested functions that hurt readability
- ❌ Not understanding closure implications
- ❌ Missing the difference between `curry(fn)(a)(b)` vs `fn(a, b)`

---

## Practical Interview Example

### Question: Implement a Generic Curry Function

```javascript
// Solution
function curry(fn) {
    const arity = fn.length;  // Get number of parameters
    
    return function $curry(...args) {
        // If we have enough arguments, call the original function
        if (args.length >= arity) {
            return fn.apply(null, args);
        }
        // Otherwise, return a function waiting for more arguments
        return (...nextArgs) => $curry(...args, ...nextArgs);
    };
}

// Test it
const add = (a, b, c) => a + b + c;
const curriedAdd = curry(add);

// All these work:
console.log(curriedAdd(1)(2)(3));      // 6
console.log(curriedAdd(1, 2)(3));      // 6
console.log(curriedAdd(1)(2, 3));      // 6
console.log(curriedAdd(1, 2, 3));      // 6

// Partial application
const add1 = curriedAdd(1);
const add1and2 = add1(2);
console.log(add1and2(3));              // 6
```

### Question: Implement Partial Application Function

```javascript
// Solution
function partial(fn, ...fixedArgs) {
    return function(...remainingArgs) {
        return fn(...fixedArgs, ...remainingArgs);
    };
}

// Test it
const multiply = (a, b, c) => a * b * c;
const multiplyBy2 = partial(multiply, 2);
const multiplyBy2and3 = partial(multiplyBy2, 3);

console.log(multiplyBy2and3(4));  // 2 * 3 * 4 = 24
```

---
