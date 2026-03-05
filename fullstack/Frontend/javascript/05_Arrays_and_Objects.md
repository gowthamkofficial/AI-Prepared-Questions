# ARRAYS AND OBJECTS ANSWERS

---

## 28. What is an array?

### Answer:
- An array is an **ordered collection** of elements stored in a single variable
- Arrays are **zero-indexed** (first element at index 0)
- Can hold **mixed data types** (numbers, strings, objects, etc.)
- Arrays are **objects** in JavaScript (`typeof [] === "object"`)
- **Dynamic length** - can grow or shrink
- Have many built-in methods for manipulation

### Theoretical Keywords:
**Ordered collection**, **Zero-indexed**, **Dynamic length**,  
**Iterable**, **Reference type**, **Array methods**,  
**Sparse arrays**, **Array-like objects**

### Example:
```javascript
// Array creation
const fruits = ["apple", "banana", "orange"];
const mixed = [1, "hello", true, { name: "John" }, [1, 2]];
const empty = [];
const withLength = new Array(5);  // [empty × 5]

// Accessing elements
console.log(fruits[0]);      // "apple"
console.log(fruits.length);  // 3

// Modifying
fruits[1] = "mango";
fruits.push("grape");        // Add to end
fruits.unshift("kiwi");      // Add to start

// Check if array
Array.isArray(fruits);       // true
typeof fruits;               // "object" (not reliable)
```

---

## 29. Common array methods (`map`, `filter`, `reduce`, `forEach`)

### Answer:
- **map()**: Creates new array by transforming each element
- **filter()**: Creates new array with elements passing a test
- **reduce()**: Reduces array to single value (accumulator pattern)
- **forEach()**: Executes function for each element, returns undefined
- `map`, `filter`, `reduce` are **immutable** (don't modify original)
- These are **higher-order functions** (take callback as argument)

### Theoretical Keywords:
**Higher-order functions**, **Callback function**, **Immutability**,  
**Transformation**, **Filtering**, **Aggregation**, **Iteration**,  
**Functional programming**, **Method chaining**

### Example:
```javascript
const numbers = [1, 2, 3, 4, 5];

// map - transform each element
const doubled = numbers.map(num => num * 2);
console.log(doubled);  // [2, 4, 6, 8, 10]

// filter - keep elements passing test
const evens = numbers.filter(num => num % 2 === 0);
console.log(evens);    // [2, 4]

// reduce - accumulate to single value
const sum = numbers.reduce((acc, num) => acc + num, 0);
console.log(sum);      // 15

// forEach - side effects, no return
numbers.forEach(num => console.log(num));  // prints 1, 2, 3, 4, 5

// Chaining methods
const result = numbers
    .filter(n => n > 2)      // [3, 4, 5]
    .map(n => n * 2)         // [6, 8, 10]
    .reduce((a, b) => a + b); // 24
```

---

## 30. Difference between `map` and `forEach`

### Answer:
- **Return value**: `map` returns new array, `forEach` returns undefined
- **Immutability**: `map` doesn't modify original, creates new array
- **Use case**: `map` for transformation, `forEach` for side effects
- **Chainable**: `map` can be chained, `forEach` cannot
- **Performance**: Similar, but `forEach` slightly faster (no new array)
- **Break**: Neither can be stopped early (use `for...of` or `some`/`every`)

### Theoretical Keywords:
**Return value**, **Array transformation**, **Side effects**,  
**Method chaining**, **Immutability**, **Functional programming**,  
**Pure function**, **Use cases**

### Example:
```javascript
const numbers = [1, 2, 3];

// map - returns new array
const doubled = numbers.map(n => n * 2);
console.log(doubled);        // [2, 4, 6]
console.log(numbers);        // [1, 2, 3] (unchanged)

// forEach - returns undefined
const result = numbers.forEach(n => console.log(n));
console.log(result);         // undefined

// Practical difference
// Use map when you need the transformed array
const prices = [100, 200, 300];
const withTax = prices.map(p => p * 1.1);

// Use forEach for side effects (logging, DOM manipulation)
prices.forEach(p => console.log(`Price: $${p}`));

// Chaining
const chainResult = numbers
    .map(n => n * 2)    // [2, 4, 6]
    .filter(n => n > 3); // [4, 6] - chainable!

// Cannot chain forEach
// numbers.forEach(n => n * 2).filter(...)  // Error!
```

---

## 31. Difference between `filter` and `reduce`

### Answer:
- **filter**: Returns **array** of elements matching condition
- **reduce**: Returns **single value** (number, object, array, etc.)
- **filter**: Uses boolean callback, keeps true elements
- **reduce**: Uses accumulator callback, builds up result
- **filter**: Output length ≤ input length
- **reduce**: Can transform array into any data structure

### Theoretical Keywords:
**Filtering**, **Aggregation**, **Accumulator pattern**,  
**Boolean predicate**, **Single value output**, **Array transformation**,  
**Initial value**, **Functional programming**

### Example:
```javascript
const numbers = [1, 2, 3, 4, 5];

// filter - returns array subset
const evens = numbers.filter(n => n % 2 === 0);
console.log(evens);  // [2, 4]

// reduce - returns single value
const sum = numbers.reduce((acc, n) => acc + n, 0);
console.log(sum);    // 15

// reduce can do what filter does (but less readable)
const evensWithReduce = numbers.reduce((acc, n) => {
    if (n % 2 === 0) acc.push(n);
    return acc;
}, []);
console.log(evensWithReduce);  // [2, 4]

// Practical use cases
const products = [
    { name: 'Phone', price: 500 },
    { name: 'Laptop', price: 1000 },
    { name: 'Tablet', price: 300 }
];

// filter - get expensive products
const expensive = products.filter(p => p.price > 400);

// reduce - calculate total price
const total = products.reduce((sum, p) => sum + p.price, 0);

// reduce - group by property
const grouped = products.reduce((acc, p) => {
    const category = p.price > 500 ? 'expensive' : 'affordable';
    acc[category] = acc[category] || [];
    acc[category].push(p);
    return acc;
}, {});
```

---

## 32. What is an object?

### Answer:
- An object is a collection of **key-value pairs** (properties)
- Keys are **strings** (or Symbols), values can be any type
- Objects are **reference types** (stored in heap)
- Can contain **methods** (functions as properties)
- JavaScript is **object-oriented** (almost everything is an object)
- Created using **object literals**, **constructors**, or **Object.create()**

### Theoretical Keywords:
**Key-value pairs**, **Properties**, **Methods**, **Reference type**,  
**Object literal**, **Constructor function**, **Prototype**,  
**Mutable**, **Dynamic properties**

### Example:
```javascript
// Object literal
const person = {
    name: "John",
    age: 30,
    isEmployed: true,
    address: {
        city: "New York",
        zip: "10001"
    },
    greet: function() {
        return `Hello, I'm ${this.name}`;
    },
    // ES6 shorthand method
    sayAge() {
        return `I'm ${this.age} years old`;
    }
};

// Constructor function
function Person(name, age) {
    this.name = name;
    this.age = age;
}
const john = new Person("John", 30);

// ES6 Class
class User {
    constructor(name) {
        this.name = name;
    }
}
```

---

## 33. How to access object properties?

### Answer:
- **Dot notation**: `object.property` (simpler, common)
- **Bracket notation**: `object["property"]` (dynamic keys)
- **Destructuring**: `const { property } = object`
- Bracket notation required for **dynamic keys** and **special characters**
- Non-existent properties return **undefined**
- Use **optional chaining** (`?.`) for safe access

### Theoretical Keywords:
**Dot notation**, **Bracket notation**, **Destructuring**,  
**Dynamic property access**, **Optional chaining**, **Property lookup**,  
**undefined for missing**, **Computed property names**

### Example:
```javascript
const person = {
    name: "John",
    age: 30,
    "full-name": "John Doe",  // special character in key
    address: {
        city: "New York"
    }
};

// Dot notation
console.log(person.name);      // "John"
console.log(person.address.city);  // "New York"

// Bracket notation
console.log(person["age"]);    // 30
console.log(person["full-name"]);  // "John Doe" (can't use dot here)

// Dynamic key
const key = "name";
console.log(person[key]);      // "John"
console.log(person.key);       // undefined (looks for literal "key")

// Destructuring
const { name, age } = person;
const { address: { city } } = person;  // Nested destructuring

// Optional chaining (safe access)
console.log(person.address?.city);     // "New York"
console.log(person.contact?.phone);    // undefined (no error)
```

---

## 34. Difference between dot notation and bracket notation

### Answer:
- **Dot notation**: Uses literal property name, cleaner syntax
- **Bracket notation**: Uses string/expression, more flexible
- **Dynamic keys**: Only bracket notation works
- **Special characters**: Only bracket notation (spaces, hyphens)
- **Keywords**: Bracket notation safer for reserved words
- **Performance**: Dot notation slightly faster (direct lookup)

### Theoretical Keywords:
**Static vs dynamic access**, **Property name evaluation**,  
**Computed properties**, **Special characters**, **Reserved words**,  
**Syntax flexibility**, **Code readability**

### Example:
```javascript
const obj = {
    name: "John",
    "user-id": 123,
    "123": "number key",
    class: "A"  // reserved word as key
};

// Dot notation - simple, readable
obj.name;         // "John"

// Bracket notation - required for:
// 1. Dynamic keys
const key = "name";
obj[key];         // "John"

// 2. Special characters
obj["user-id"];   // 123
// obj.user-id;   // SyntaxError

// 3. Numeric keys
obj["123"];       // "number key"
// obj.123;       // SyntaxError

// 4. Reserved words (safer)
obj["class"];     // "A"
obj.class;        // "A" (works but confusing)

// 5. Computed property access
const prop = "na" + "me";
obj[prop];        // "John"

// Setting properties
obj.newProp = "value";     // dot
obj["dynamic-prop"] = "x"; // bracket
```

---

## 35. What is destructuring?

### Answer:
- Destructuring is a syntax for **extracting values** from arrays/objects
- Creates variables from object properties or array elements
- Supports **default values** for undefined properties
- Allows **renaming** variables during extraction
- Can be used in **function parameters**
- Works with **nested structures**

### Theoretical Keywords:
**Object destructuring**, **Array destructuring**, **Default values**,  
**Renaming**, **Rest pattern**, **Nested destructuring**,  
**Function parameters**, **ES6 feature**

### Example:
```javascript
// Object destructuring
const person = { name: "John", age: 30, city: "NYC" };
const { name, age } = person;
console.log(name, age);  // "John" 30

// With renaming
const { name: userName, age: userAge } = person;
console.log(userName);   // "John"

// With default values
const { name, country = "USA" } = person;
console.log(country);    // "USA" (default used)

// Array destructuring
const colors = ["red", "green", "blue"];
const [first, second] = colors;
console.log(first);      // "red"

// Skip elements
const [, , third] = colors;
console.log(third);      // "blue"

// Rest pattern
const [head, ...rest] = colors;
console.log(rest);       // ["green", "blue"]

// Nested destructuring
const user = { info: { name: "John", address: { city: "NYC" } } };
const { info: { name: n, address: { city } } } = user;

// Function parameters
function greet({ name, age = 18 }) {
    console.log(`${name} is ${age}`);
}
greet(person);

// Swapping variables
let a = 1, b = 2;
[a, b] = [b, a];  // a = 2, b = 1
```

---
