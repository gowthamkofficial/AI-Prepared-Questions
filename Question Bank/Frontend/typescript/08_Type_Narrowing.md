# TYPE NARROWING ANSWERS

---

## 51. What is type narrowing?

### Answer:
- **Type narrowing** is the process of **refining a type** to a more specific type
- TypeScript analyzes code flow to **automatically narrow types**
- Occurs within **conditional blocks** after type checks
- Enables safe access to **type-specific properties and methods**
- Uses various techniques: `typeof`, `instanceof`, `in`, custom guards

### Theoretical Keywords:
**Type refinement**, **Control flow analysis**, **Type guards**,  
**Discriminated unions**, **Narrowing techniques**, **Type safety**

### Example:
```typescript
// Type narrowing in action
function processValue(value: string | number | boolean) {
    // value is string | number | boolean here
    
    if (typeof value === "string") {
        // value is narrowed to string
        console.log(value.toUpperCase());
        console.log(value.length);
    } else if (typeof value === "number") {
        // value is narrowed to number
        console.log(value.toFixed(2));
        console.log(value * 2);
    } else {
        // value is narrowed to boolean
        console.log(value ? "Yes" : "No");
    }
}

// Narrowing with truthiness
function printName(name: string | null | undefined) {
    if (name) {
        // name is narrowed to string (truthy check)
        console.log(name.toUpperCase());
    } else {
        console.log("No name provided");
    }
}

// Narrowing with equality
function compare(a: string | number, b: string | boolean) {
    if (a === b) {
        // Both narrowed to string (only common type)
        console.log(a.toUpperCase());
        console.log(b.toUpperCase());
    }
}

// Narrowing with in operator
interface Bird {
    fly(): void;
    layEggs(): void;
}

interface Fish {
    swim(): void;
    layEggs(): void;
}

function move(animal: Bird | Fish) {
    if ("fly" in animal) {
        // animal is narrowed to Bird
        animal.fly();
    } else {
        // animal is narrowed to Fish
        animal.swim();
    }
}
```

---

## 52. `typeof` guards

### Answer:
- **typeof guards** use JavaScript's `typeof` operator for type narrowing
- Works with **primitive types**: string, number, boolean, symbol, bigint, undefined, function
- **Does not work** with `null` (returns "object") or custom types
- Most common and simplest form of type narrowing
- TypeScript understands the check and narrows accordingly

### Theoretical Keywords:
**Primitive type guards**, **typeof operator**, **Runtime type check**,  
**Control flow narrowing**, **Type safety**, **JavaScript typeof**

### Example:
```typescript
// typeof for primitive narrowing
function formatValue(value: string | number | boolean): string {
    if (typeof value === "string") {
        return value.trim().toUpperCase();
    }
    
    if (typeof value === "number") {
        return value.toFixed(2);
    }
    
    if (typeof value === "boolean") {
        return value ? "TRUE" : "FALSE";
    }
    
    // TypeScript knows this is unreachable
    return value;  // type: never
}

// typeof with functions
function callIfFunction(value: string | (() => void)) {
    if (typeof value === "function") {
        value();  // Narrowed to () => void
    } else {
        console.log(value);  // Narrowed to string
    }
}

// typeof with undefined
function greet(name?: string) {
    if (typeof name === "undefined") {
        return "Hello, Guest!";
    }
    return `Hello, ${name}!`;  // Narrowed to string
}

// typeof guard in ternary
function double(value: string | number): string | number {
    return typeof value === "string" 
        ? value + value 
        : value * 2;
}

// Combining typeof checks
function process(input: string | number | null | undefined) {
    if (typeof input === "string") {
        return input.length;
    }
    if (typeof input === "number") {
        return input * 2;
    }
    // input is null | undefined here
    return 0;
}

// Note: typeof null === "object" (JavaScript quirk)
function handleNull(value: string | null) {
    if (typeof value === "string") {
        console.log(value.toUpperCase());
    } else {
        // value is null here (not checked by typeof)
        console.log("Value is null");
    }
}
```

---

## 53. `instanceof` guards

### Answer:
- **instanceof guards** check if an object is an instance of a **class**
- Works with **classes** and **constructor functions**
- Cannot be used with **interfaces** (they don't exist at runtime)
- Checks the **prototype chain** of the object
- Useful for narrowing **class hierarchies** and **error types**

### Theoretical Keywords:
**Class instance check**, **Prototype chain**, **Constructor function**,  
**Runtime type check**, **Class hierarchy**, **Object inheritance**

### Example:
```typescript
// Basic instanceof guard
class Dog {
    bark() { console.log("Woof!"); }
}

class Cat {
    meow() { console.log("Meow!"); }
}

function makeSound(animal: Dog | Cat) {
    if (animal instanceof Dog) {
        animal.bark();  // Narrowed to Dog
    } else {
        animal.meow();  // Narrowed to Cat
    }
}

// instanceof with class hierarchy
class Animal {
    name: string;
    constructor(name: string) {
        this.name = name;
    }
}

class Bird extends Animal {
    fly() { console.log(`${this.name} is flying`); }
}

class Fish extends Animal {
    swim() { console.log(`${this.name} is swimming`); }
}

function move(animal: Animal) {
    if (animal instanceof Bird) {
        animal.fly();  // Narrowed to Bird
    } else if (animal instanceof Fish) {
        animal.swim();  // Narrowed to Fish
    }
}

// instanceof with Error types
function handleError(error: Error | string) {
    if (error instanceof TypeError) {
        console.log("Type Error:", error.message);
    } else if (error instanceof RangeError) {
        console.log("Range Error:", error.message);
    } else if (error instanceof Error) {
        console.log("Generic Error:", error.message);
    } else {
        console.log("String Error:", error);
    }
}

// instanceof with Date
function formatDate(value: string | Date): string {
    if (value instanceof Date) {
        return value.toISOString();  // Narrowed to Date
    }
    return new Date(value).toISOString();  // value is string
}

// instanceof with custom classes
class ApiResponse<T> {
    constructor(public data: T, public status: number) {}
}

class ApiError {
    constructor(public message: string, public code: number) {}
}

function handleApiResult(result: ApiResponse<any> | ApiError) {
    if (result instanceof ApiResponse) {
        console.log("Success:", result.data);
    } else {
        console.log("Error:", result.message);
    }
}

// Note: instanceof doesn't work with interfaces
interface User {
    name: string;
}
// if (obj instanceof User) {} // ❌ Error: 'User' only refers to a type
```

---

## 54. Custom type guards

### Answer:
- **Custom type guards** are functions that return a **type predicate**
- Syntax: `parameter is Type` as return type
- Allows creating **custom narrowing logic**
- Useful for **interfaces**, **complex types**, and **validation**
- TypeScript trusts the guard and narrows types accordingly

### Theoretical Keywords:
**Type predicate**, **User-defined guards**, **is keyword**,  
**Custom validation**, **Interface narrowing**, **Type assertion function**

### Example:
```typescript
// Basic custom type guard
interface Bird {
    fly(): void;
    feathers: number;
}

interface Fish {
    swim(): void;
    fins: number;
}

// Type guard function
function isBird(animal: Bird | Fish): animal is Bird {
    return (animal as Bird).feathers !== undefined;
}

function move(animal: Bird | Fish) {
    if (isBird(animal)) {
        animal.fly();  // Narrowed to Bird
    } else {
        animal.swim();  // Narrowed to Fish
    }
}

// Type guard with property check
interface User {
    type: "user";
    name: string;
    email: string;
}

interface Admin {
    type: "admin";
    name: string;
    permissions: string[];
}

function isAdmin(person: User | Admin): person is Admin {
    return person.type === "admin";
}

function showPermissions(person: User | Admin) {
    if (isAdmin(person)) {
        console.log(person.permissions);  // Narrowed to Admin
    } else {
        console.log("Regular user, no special permissions");
    }
}

// Type guard for null/undefined
function isDefined<T>(value: T | null | undefined): value is T {
    return value !== null && value !== undefined;
}

function processItems(items: (string | null | undefined)[]) {
    const validItems = items.filter(isDefined);
    // validItems is string[]
    validItems.forEach(item => console.log(item.toUpperCase()));
}

// Type guard for arrays
function isStringArray(value: unknown): value is string[] {
    return Array.isArray(value) && value.every(item => typeof item === "string");
}

function processData(data: unknown) {
    if (isStringArray(data)) {
        data.forEach(str => console.log(str.toUpperCase()));
    }
}

// Type guard for API responses
interface SuccessResponse<T> {
    success: true;
    data: T;
}

interface ErrorResponse {
    success: false;
    error: string;
}

type ApiResponse<T> = SuccessResponse<T> | ErrorResponse;

function isSuccess<T>(response: ApiResponse<T>): response is SuccessResponse<T> {
    return response.success === true;
}

async function fetchUser(): Promise<ApiResponse<{ name: string }>> {
    // API call
    return { success: true, data: { name: "John" } };
}

async function handleUser() {
    const response = await fetchUser();
    
    if (isSuccess(response)) {
        console.log(response.data.name);  // Safe access
    } else {
        console.log(response.error);  // Error handling
    }
}

// Assertion functions (TypeScript 3.7+)
function assertIsString(value: unknown): asserts value is string {
    if (typeof value !== "string") {
        throw new Error("Value is not a string");
    }
}

function processString(value: unknown) {
    assertIsString(value);
    // value is string after assertion
    console.log(value.toUpperCase());
}
```

---
