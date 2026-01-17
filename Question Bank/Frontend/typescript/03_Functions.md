# FUNCTIONS ANSWERS

---

## 19. How to define a function in TypeScript?

### Answer:
- Functions in TypeScript can have **typed parameters** and **return types**
- Use colon (`:`) after parameter for type annotation
- Use colon after parentheses for return type
- TypeScript can **infer return type** automatically
- Supports **arrow functions**, **function expressions**, and **declarations**

### Theoretical Keywords:
**Parameter types**, **Return type**, **Type annotation**,  
**Arrow functions**, **Function expression**, **Type inference**

### Example:
```typescript
// Function declaration with types
function add(a: number, b: number): number {
    return a + b;
}

// Arrow function with types
const multiply = (a: number, b: number): number => a * b;

// Function expression
const divide: (a: number, b: number) => number = function(a, b) {
    return a / b;
};

// Return type inference (TypeScript infers number)
function subtract(a: number, b: number) {
    return a - b;  // inferred return type: number
}

// void return type (no return value)
function logMessage(message: string): void {
    console.log(message);
}

// never return type (function never returns)
function throwError(message: string): never {
    throw new Error(message);
}

// Function type alias
type MathOperation = (x: number, y: number) => number;
const sum: MathOperation = (a, b) => a + b;
```

---

## 20. Optional parameters vs default parameters

### Answer:
- **Optional parameters**: May or may not be provided (use `?`)
- **Default parameters**: Have a fallback value if not provided
- Optional parameters must come **after required parameters**
- Default parameters can be anywhere (but usually at end)
- Optional parameter type includes `undefined` automatically

### Theoretical Keywords:
**Optional modifier**, **Default value**, **Parameter order**,  
**Undefined handling**, **Function overloading alternative**

### Example:
```typescript
// Optional parameter (use ?)
function greet(name: string, greeting?: string): string {
    if (greeting) {
        return `${greeting}, ${name}!`;
    }
    return `Hello, ${name}!`;
}
greet("John");           // "Hello, John!"
greet("John", "Hi");     // "Hi, John!"

// Default parameter (use =)
function greetWithDefault(name: string, greeting: string = "Hello"): string {
    return `${greeting}, ${name}!`;
}
greetWithDefault("John");         // "Hello, John!"
greetWithDefault("John", "Hi");   // "Hi, John!"

// Difference in type
// Optional: greeting?: string  → string | undefined
// Default: greeting: string = "Hello" → string (undefined triggers default)

// Multiple optional/default parameters
function createUser(
    name: string,
    age?: number,
    country: string = "USA",
    active: boolean = true
) {
    return { name, age, country, active };
}

createUser("John");                    // { name: "John", age: undefined, country: "USA", active: true }
createUser("Jane", 25);                // { name: "Jane", age: 25, country: "USA", active: true }
createUser("Bob", 30, "UK", false);    // { name: "Bob", age: 30, country: "UK", active: false }
```

---

## 21. What are rest parameters?

### Answer:
- **Rest parameters** collect multiple arguments into an **array**
- Defined using **spread operator (`...`)** before parameter name
- Must be the **last parameter** in function signature
- Allows functions to accept **variable number of arguments**
- Type annotation applies to the array type

### Theoretical Keywords:
**Variable arguments**, **Spread operator**, **Array collection**,  
**Variadic functions**, **Last parameter rule**, **Tuple rest**

### Example:
```typescript
// Basic rest parameter
function sum(...numbers: number[]): number {
    return numbers.reduce((total, num) => total + num, 0);
}
sum(1, 2, 3);        // 6
sum(1, 2, 3, 4, 5);  // 15

// Rest parameter with other parameters
function greetAll(greeting: string, ...names: string[]): string {
    return `${greeting}, ${names.join(", ")}!`;
}
greetAll("Hello", "John", "Jane", "Bob");  // "Hello, John, Jane, Bob!"

// Rest parameter with tuple type
function processData(id: number, ...data: [string, boolean]): void {
    const [name, active] = data;
    console.log(`ID: ${id}, Name: ${name}, Active: ${active}`);
}
processData(1, "John", true);

// Real-world example: Event handler
function logEvents(eventType: string, ...args: unknown[]): void {
    console.log(`[${eventType}]`, ...args);
}
logEvents("click", { x: 100, y: 200 }, "button");

// Using with array spread
const nums = [1, 2, 3, 4, 5];
console.log(sum(...nums));  // 15
```

---

## 22. Function return types

### Answer:
- **Return type** specifies what type a function returns
- Declared after parameter list with colon (`:`)
- **void**: Function doesn't return a value
- **never**: Function never completes (throws or infinite loop)
- TypeScript can **infer** return types automatically
- Explicit return types improve code documentation

### Theoretical Keywords:
**Return type annotation**, **void**, **never**, **Type inference**,  
**Explicit vs implicit**, **Promise return type**

### Example:
```typescript
// Explicit return types
function getString(): string {
    return "hello";
}

function getNumber(): number {
    return 42;
}

function getBoolean(): boolean {
    return true;
}

// void - no return value
function log(message: string): void {
    console.log(message);
    // return undefined; // implicit
}

// never - function never returns normally
function fail(message: string): never {
    throw new Error(message);
}

function infiniteLoop(): never {
    while (true) {
        // ...
    }
}

// Promise return type (async functions)
async function fetchData(): Promise<string> {
    const response = await fetch("/api/data");
    return response.text();
}

// Union return type
function getIdOrName(useId: boolean): string | number {
    return useId ? 123 : "abc";
}

// Object return type
function createUser(name: string): { name: string; id: number } {
    return { name, id: Math.random() };
}

// Tuple return type
function getCoordinates(): [number, number] {
    return [10, 20];
}
```

---

## 23. What is function overloading?

### Answer:
- **Function overloading** allows multiple function signatures for same function name
- Different **parameter types** or **counts** → different behaviors
- TypeScript uses **overload signatures** followed by **implementation signature**
- Implementation must handle **all** overload cases
- Useful when return type depends on parameter types

### Theoretical Keywords:
**Multiple signatures**, **Overload resolution**, **Implementation signature**,  
**Type narrowing**, **Polymorphism**, **Signature matching**

### Example:
```typescript
// Function overloading
function format(value: string): string;
function format(value: number): string;
function format(value: boolean): string;
function format(value: string | number | boolean): string {
    if (typeof value === "string") {
        return value.toUpperCase();
    } else if (typeof value === "number") {
        return value.toFixed(2);
    } else {
        return value ? "Yes" : "No";
    }
}

format("hello");  // "HELLO"
format(3.14159);  // "3.14"
format(true);     // "Yes"

// Overloading with different return types
function getValue(key: "name"): string;
function getValue(key: "age"): number;
function getValue(key: "active"): boolean;
function getValue(key: string): string | number | boolean {
    const data: Record<string, string | number | boolean> = {
        name: "John",
        age: 25,
        active: true
    };
    return data[key];
}

const name = getValue("name");    // type: string
const age = getValue("age");      // type: number
const active = getValue("active"); // type: boolean
```

---

## 24. How function overloading works in TypeScript?

### Answer:
- **Step 1**: Define overload signatures (just declarations, no body)
- **Step 2**: Define implementation signature (with body)
- **Step 3**: TypeScript matches call to **first matching overload**
- Implementation must be **compatible with all overloads**
- Order matters: put more specific overloads **first**
- Implementation signature is **not callable directly**

### Theoretical Keywords:
**Overload signatures**, **Implementation signature**, **Signature matching**,  
**Order precedence**, **Type widening**, **Compatibility**

### Example:
```typescript
// How TypeScript resolves overloads

// Overload signatures (what callers see)
function process(x: string): string;           // Signature 1
function process(x: number): number;           // Signature 2
function process(x: string[]): string[];       // Signature 3

// Implementation signature (handles all cases)
function process(x: string | number | string[]): string | number | string[] {
    if (typeof x === "string") {
        return x.toUpperCase();
    } else if (typeof x === "number") {
        return x * 2;
    } else {
        return x.map(s => s.toUpperCase());
    }
}

// TypeScript matches to appropriate overload
const result1 = process("hello");     // type: string (matches Signature 1)
const result2 = process(10);          // type: number (matches Signature 2)
const result3 = process(["a", "b"]);  // type: string[] (matches Signature 3)

// Order matters - more specific first
function createDate(timestamp: number): Date;
function createDate(year: number, month: number, day: number): Date;
function createDate(yearOrTimestamp: number, month?: number, day?: number): Date {
    if (month !== undefined && day !== undefined) {
        return new Date(yearOrTimestamp, month - 1, day);
    }
    return new Date(yearOrTimestamp);
}

createDate(1609459200000);    // From timestamp
createDate(2021, 1, 1);       // From year, month, day
// createDate(2021, 1);       // ❌ Error: No matching overload
```

---
