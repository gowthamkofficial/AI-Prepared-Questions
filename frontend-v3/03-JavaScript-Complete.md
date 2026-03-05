# JavaScript - Complete Interview Questions & Answers

## **JavaScript Fundamentals**

### 1. What is JavaScript?
**Answer:** JavaScript is a high-level, interpreted programming language used to create interactive web pages. It runs in browsers and on servers (Node.js).

**Key Features:**
- Interpreted (not compiled)
- Dynamic typing
- Prototype-based
- First-class functions
- Event-driven
- Single-threaded with async capabilities

### 2. What are JavaScript data types?
**Answer:**

**Primitive Types (7):**
```javascript
// String
let name = "John";

// Number
let age = 30;
let price = 99.99;

// Boolean
let isActive = true;

// Undefined
let x;  // undefined

// Null
let y = null;

// Symbol (ES6)
let id = Symbol('id');

// BigInt (ES2020)
let bigNum = 9007199254740991n;
```

**Reference Types:**
```javascript
// Object
let person = { name: "John", age: 30 };

// Array
let numbers = [1, 2, 3];

// Function
function greet() { }

// Date
let date = new Date();

// RegExp
let pattern = /abc/;
```

### 3. What is the difference between var, let, and const?
**Answer:**

| Feature | var | let | const |
|---------|-----|-----|-------|
| Scope | Function | Block | Block |
| Hoisting | Yes (undefined) | Yes (TDZ) | Yes (TDZ) |
| Redeclaration | Allowed | Not allowed | Not allowed |
| Reassignment | Allowed | Allowed | Not allowed |
| Temporal Dead Zone | No | Yes | Yes |

```javascript
// var - function scoped
function test() {
  var x = 1;
  if (true) {
    var x = 2;  // Same variable
    console.log(x);  // 2
  }
  console.log(x);  // 2
}

// let - block scoped
function test() {
  let x = 1;
  if (true) {
    let x = 2;  // Different variable
    console.log(x);  // 2
  }
  console.log(x);  // 1
}

// const - block scoped, immutable binding
const PI = 3.14;
// PI = 3.15;  // Error

const obj = { name: "John" };
obj.name = "Jane";  // Allowed (object is mutable)
// obj = {};  // Error (can't reassign)
```

### 4. What is hoisting?
**Answer:** JavaScript moves declarations to the top of their scope during compilation.

```javascript
// Variable hoisting
console.log(x);  // undefined (not error)
var x = 5;

// Equivalent to:
var x;
console.log(x);
x = 5;

// let/const hoisting (Temporal Dead Zone)
console.log(y);  // ReferenceError
let y = 5;

// Function hoisting
greet();  // Works
function greet() {
  console.log("Hello");
}

// Function expression (not hoisted)
sayHi();  // Error
var sayHi = function() {
  console.log("Hi");
};
```

### 5. What is the difference between == and ===?
**Answer:**

```javascript
// == (loose equality) - type coercion
5 == "5"      // true
0 == false    // true
null == undefined  // true

// === (strict equality) - no type coercion
5 === "5"     // false
0 === false   // false
null === undefined  // false

// Best practice: Always use ===
```

### 6. What is the difference between null and undefined?
**Answer:**

```javascript
// undefined - variable declared but not assigned
let x;
console.log(x);  // undefined
console.log(typeof x);  // "undefined"

// null - intentional absence of value
let y = null;
console.log(y);  // null
console.log(typeof y);  // "object" (JavaScript bug)

// Comparison
null == undefined   // true
null === undefined  // false
```

### 7. What are truthy and falsy values?
**Answer:**

**Falsy values (8):**
```javascript
false
0
-0
0n (BigInt zero)
"" (empty string)
null
undefined
NaN
```

**Everything else is truthy:**
```javascript
true
1, -1, 3.14
"0", "false" (non-empty strings)
[], {} (empty arrays/objects)
function() {}
```

```javascript
if ("0") {
  console.log("Truthy");  // Executes
}

if ([]) {
  console.log("Truthy");  // Executes
}
```

### 8. What are JavaScript operators?
**Answer:**

```javascript
// Arithmetic
+ - * / % **

// Assignment
= += -= *= /= %=

// Comparison
== === != !== > < >= <=

// Logical
&& || !

// Bitwise
& | ^ ~ << >> >>>

// Ternary
condition ? true : false

// Nullish coalescing
let value = input ?? "default";  // Only null/undefined

// Optional chaining
let name = user?.profile?.name;

// Spread
let arr = [...arr1, ...arr2];

// Rest
function sum(...numbers) { }
```

### 9. What are arrow functions?
**Answer:**

```javascript
// Regular function
function add(a, b) {
  return a + b;
}

// Arrow function
const add = (a, b) => a + b;

// With single parameter
const square = x => x * x;

// With no parameters
const greet = () => console.log("Hello");

// With block body
const multiply = (a, b) => {
  const result = a * b;
  return result;
};

// Differences from regular functions:
// 1. No 'this' binding
// 2. No 'arguments' object
// 3. Cannot be used as constructor
// 4. No prototype property
```

### 10. What is 'this' keyword?
**Answer:**

```javascript
// Global context
console.log(this);  // window (browser) or global (Node.js)

// Object method
const obj = {
  name: "John",
  greet: function() {
    console.log(this.name);  // "John"
  }
};

// Arrow function (lexical this)
const obj2 = {
  name: "Jane",
  greet: () => {
    console.log(this.name);  // undefined (inherits from outer scope)
  }
};

// Constructor
function Person(name) {
  this.name = name;
}
const person = new Person("John");

// Event handler
button.addEventListener('click', function() {
  console.log(this);  // button element
});

// Explicit binding
const obj3 = { name: "Bob" };
function greet() {
  console.log(this.name);
}
greet.call(obj3);   // "Bob"
greet.apply(obj3);  // "Bob"
const boundGreet = greet.bind(obj3);
boundGreet();       // "Bob"
```

---

## **Functions & Scope**

### 11. What is a closure?
**Answer:** Function that has access to outer function's variables even after outer function returns.

```javascript
function outer() {
  let count = 0;
  
  return function inner() {
    count++;
    console.log(count);
  };
}

const counter = outer();
counter();  // 1
counter();  // 2
counter();  // 3

// Real-world example: Private variables
function createCounter() {
  let count = 0;
  
  return {
    increment: () => ++count,
    decrement: () => --count,
    getCount: () => count
  };
}

const counter = createCounter();
console.log(counter.increment());  // 1
console.log(counter.getCount());   // 1
// count is private, can't access directly
```

### 12. What is the difference between call, apply, and bind?
**Answer:**

```javascript
const person = {
  name: "John",
  greet: function(greeting, punctuation) {
    console.log(`${greeting}, ${this.name}${punctuation}`);
  }
};

const anotherPerson = { name: "Jane" };

// call - invoke immediately with arguments
person.greet.call(anotherPerson, "Hello", "!");
// "Hello, Jane!"

// apply - invoke immediately with array
person.greet.apply(anotherPerson, ["Hi", "."]);
// "Hi, Jane."

// bind - returns new function
const greetJane = person.greet.bind(anotherPerson, "Hey");
greetJane("?");
// "Hey, Jane?"
```

### 13. What are higher-order functions?
**Answer:** Functions that take functions as arguments or return functions.

```javascript
// Takes function as argument
function map(arr, fn) {
  const result = [];
  for (let item of arr) {
    result.push(fn(item));
  }
  return result;
}

const numbers = [1, 2, 3];
const doubled = map(numbers, x => x * 2);  // [2, 4, 6]

// Returns function
function multiplier(factor) {
  return function(number) {
    return number * factor;
  };
}

const double = multiplier(2);
console.log(double(5));  // 10
```

### 14. What is currying?
**Answer:** Transforming function with multiple arguments into sequence of functions with single argument.

```javascript
// Regular function
function add(a, b, c) {
  return a + b + c;
}

// Curried function
function curriedAdd(a) {
  return function(b) {
    return function(c) {
      return a + b + c;
    };
  };
}

console.log(curriedAdd(1)(2)(3));  // 6

// Arrow function version
const curriedAdd = a => b => c => a + b + c;

// Practical use
const add5 = curriedAdd(5);
console.log(add5(3)(2));  // 10
```

---

## **Arrays**

### 15. What are array methods?
**Answer:**

```javascript
const arr = [1, 2, 3, 4, 5];

// map - transform elements
arr.map(x => x * 2);  // [2, 4, 6, 8, 10]

// filter - select elements
arr.filter(x => x > 2);  // [3, 4, 5]

// reduce - accumulate
arr.reduce((sum, x) => sum + x, 0);  // 15

// forEach - iterate
arr.forEach(x => console.log(x));

// find - first match
arr.find(x => x > 2);  // 3

// findIndex - index of first match
arr.findIndex(x => x > 2);  // 2

// some - check if any match
arr.some(x => x > 4);  // true

// every - check if all match
arr.every(x => x > 0);  // true

// includes - check if contains
arr.includes(3);  // true

// slice - extract portion
arr.slice(1, 3);  // [2, 3]

// splice - modify array
arr.splice(1, 2, 10, 20);  // removes 2 elements, adds 10, 20

// concat - merge arrays
arr.concat([6, 7]);  // [1, 2, 3, 4, 5, 6, 7]

// sort - sort array
arr.sort((a, b) => a - b);

// reverse - reverse array
arr.reverse();
```

### 16. What is the difference between map and forEach?
**Answer:**

```javascript
const arr = [1, 2, 3];

// map - returns new array
const doubled = arr.map(x => x * 2);
console.log(doubled);  // [2, 4, 6]

// forEach - returns undefined, used for side effects
const result = arr.forEach(x => console.log(x));
console.log(result);  // undefined

// map is chainable
arr.map(x => x * 2).filter(x => x > 2);

// forEach is not chainable
```

---

## **Objects**

### 17. How to create objects?
**Answer:**

```javascript
// Object literal
const obj1 = { name: "John", age: 30 };

// Constructor function
function Person(name, age) {
  this.name = name;
  this.age = age;
}
const obj2 = new Person("Jane", 25);

// Object.create
const proto = { greet() { console.log("Hi"); } };
const obj3 = Object.create(proto);

// Class (ES6)
class User {
  constructor(name) {
    this.name = name;
  }
}
const obj4 = new User("Bob");

// Factory function
function createPerson(name) {
  return {
    name,
    greet() { console.log(`Hi, ${this.name}`); }
  };
}
const obj5 = createPerson("Alice");
```

### 18. What is destructuring?
**Answer:**

```javascript
// Array destructuring
const [a, b, c] = [1, 2, 3];
const [first, , third] = [1, 2, 3];  // Skip elements
const [x, ...rest] = [1, 2, 3, 4];  // x=1, rest=[2,3,4]

// Object destructuring
const { name, age } = { name: "John", age: 30 };
const { name: userName } = { name: "John" };  // Rename
const { city = "NYC" } = {};  // Default value

// Nested destructuring
const { address: { street } } = {
  address: { street: "Main St" }
};

// Function parameters
function greet({ name, age }) {
  console.log(`${name} is ${age}`);
}
greet({ name: "John", age: 30 });
```

### 19. What is the spread operator?
**Answer:**

```javascript
// Array spread
const arr1 = [1, 2, 3];
const arr2 = [...arr1, 4, 5];  // [1, 2, 3, 4, 5]

// Merge arrays
const merged = [...arr1, ...arr2];

// Copy array
const copy = [...arr1];

// Object spread
const obj1 = { a: 1, b: 2 };
const obj2 = { ...obj1, c: 3 };  // { a: 1, b: 2, c: 3 }

// Merge objects
const merged = { ...obj1, ...obj2 };

// Override properties
const updated = { ...obj1, a: 10 };  // { a: 10, b: 2 }

// Function arguments
function sum(...numbers) {
  return numbers.reduce((a, b) => a + b, 0);
}
sum(1, 2, 3, 4);  // 10
```

### 20. What is the rest parameter?
**Answer:**

```javascript
// Rest in functions
function sum(...numbers) {
  return numbers.reduce((a, b) => a + b, 0);
}
sum(1, 2, 3);  // 6

// Rest with other parameters
function greet(greeting, ...names) {
  return `${greeting} ${names.join(", ")}`;
}
greet("Hello", "John", "Jane", "Bob");

// Rest in destructuring
const [first, ...rest] = [1, 2, 3, 4];
// first = 1, rest = [2, 3, 4]

const { a, ...others } = { a: 1, b: 2, c: 3 };
// a = 1, others = { b: 2, c: 3 }
```

---

## **Asynchronous JavaScript**

### 21. What is the event loop?
**Answer:** Mechanism that handles async operations in single-threaded JavaScript.

**Components:**
- Call Stack: Executes synchronous code
- Web APIs: Handle async operations (setTimeout, fetch)
- Callback Queue: Stores callbacks
- Microtask Queue: Stores promises (higher priority)
- Event Loop: Moves tasks from queues to call stack

```javascript
console.log("1");

setTimeout(() => console.log("2"), 0);

Promise.resolve().then(() => console.log("3"));

console.log("4");

// Output: 1, 4, 3, 2
// Microtasks (promises) execute before macrotasks (setTimeout)
```

### 22. What are Promises?
**Answer:** Objects representing eventual completion or failure of async operation.

```javascript
// Creating promise
const promise = new Promise((resolve, reject) => {
  setTimeout(() => {
    const success = true;
    if (success) {
      resolve("Success!");
    } else {
      reject("Error!");
    }
  }, 1000);
});

// Consuming promise
promise
  .then(result => console.log(result))
  .catch(error => console.error(error))
  .finally(() => console.log("Done"));

// Promise states: pending, fulfilled, rejected

// Promise chaining
fetch('/api/user')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error(error));

// Promise.all - wait for all
Promise.all([promise1, promise2])
  .then(([result1, result2]) => console.log(result1, result2));

// Promise.race - first to complete
Promise.race([promise1, promise2])
  .then(result => console.log(result));

// Promise.allSettled - wait for all (doesn't fail)
Promise.allSettled([promise1, promise2])
  .then(results => console.log(results));
```

### 23. What is async/await?
**Answer:** Syntactic sugar for promises, makes async code look synchronous.

```javascript
// Without async/await
function getUser() {
  return fetch('/api/user')
    .then(response => response.json())
    .then(data => data)
    .catch(error => console.error(error));
}

// With async/await
async function getUser() {
  try {
    const response = await fetch('/api/user');
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
  }
}

// Multiple awaits
async function getData() {
  const user = await getUser();
  const posts = await getPosts(user.id);
  return { user, posts };
}

// Parallel execution
async function getAll() {
  const [users, posts] = await Promise.all([
    getUsers(),
    getPosts()
  ]);
  return { users, posts };
}
```

### 24. What is callback hell?
**Answer:** Nested callbacks making code hard to read.

```javascript
// Callback hell
getData(function(a) {
  getMoreData(a, function(b) {
    getMoreData(b, function(c) {
      getMoreData(c, function(d) {
        console.log(d);
      });
    });
  });
});

// Solution: Promises
getData()
  .then(a => getMoreData(a))
  .then(b => getMoreData(b))
  .then(c => getMoreData(c))
  .then(d => console.log(d));

// Solution: async/await
async function fetchData() {
  const a = await getData();
  const b = await getMoreData(a);
  const c = await getMoreData(b);
  const d = await getMoreData(c);
  console.log(d);
}
```

---

## **ES6+ Features**

### 25. What are template literals?
**Answer:**

```javascript
const name = "John";
const age = 30;

// Template literal
const message = `Hello, ${name}! You are ${age} years old.`;

// Multi-line
const html = `
  <div>
    <h1>${name}</h1>
    <p>Age: ${age}</p>
  </div>
`;

// Expression evaluation
const result = `2 + 2 = ${2 + 2}`;

// Tagged templates
function highlight(strings, ...values) {
  return strings.reduce((result, str, i) => {
    return `${result}${str}<strong>${values[i] || ''}</strong>`;
  }, '');
}

const output = highlight`Name: ${name}, Age: ${age}`;
```

### 26. What are default parameters?
**Answer:**

```javascript
// Default parameters
function greet(name = "Guest", greeting = "Hello") {
  return `${greeting}, ${name}!`;
}

greet();  // "Hello, Guest!"
greet("John");  // "Hello, John!"
greet("Jane", "Hi");  // "Hi, Jane!"

// With expressions
function createUser(name, role = "user", id = Date.now()) {
  return { name, role, id };
}
```

### 27. What are modules (import/export)?
**Answer:**

```javascript
// Named exports
export const PI = 3.14;
export function add(a, b) {
  return a + b;
}

// Import named exports
import { PI, add } from './math.js';

// Import all
import * as math from './math.js';
console.log(math.PI);

// Default export
export default function multiply(a, b) {
  return a * b;
}

// Import default
import multiply from './math.js';

// Mixed
export default multiply;
export { PI, add };

import multiply, { PI, add } from './math.js';
```

### 28. What are classes?
**Answer:**

```javascript
class Person {
  // Constructor
  constructor(name, age) {
    this.name = name;
    this.age = age;
  }
  
  // Method
  greet() {
    return `Hi, I'm ${this.name}`;
  }
  
  // Getter
  get info() {
    return `${this.name} (${this.age})`;
  }
  
  // Setter
  set age(value) {
    if (value < 0) throw new Error("Invalid age");
    this._age = value;
  }
  
  // Static method
  static create(name) {
    return new Person(name, 0);
  }
}

// Inheritance
class Employee extends Person {
  constructor(name, age, role) {
    super(name, age);
    this.role = role;
  }
  
  greet() {
    return `${super.greet()}, I'm a ${this.role}`;
  }
}

const emp = new Employee("John", 30, "Developer");
```

---

**Total: 28+ comprehensive JavaScript questions with detailed answers**
