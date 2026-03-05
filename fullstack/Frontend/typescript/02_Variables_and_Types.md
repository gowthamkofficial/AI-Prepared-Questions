# VARIABLES AND TYPES ANSWERS

---

## 11. How do you declare variables in TypeScript?

### Answer:
- Use **`let`**, **`const`**, or **`var`** keywords (same as JavaScript)
- Add **type annotation** after variable name with colon (`:`)
- TypeScript can also **infer types** automatically
- **const** for values that won't change, **let** for variables
- **Avoid var** due to function scoping issues

### Theoretical Keywords:
**Type annotation**, **Type inference**, **Variable declaration**,  
**const vs let**, **Block scoping**, **Initialization**

### Example:
```typescript
// Explicit type annotation
let name: string = "John";
let age: number = 25;
let isActive: boolean = true;

// Type inference (TypeScript infers the type)
let city = "New York";      // inferred as string
let count = 100;            // inferred as number
let flag = false;           // inferred as boolean

// const - must be initialized, cannot be reassigned
const PI: number = 3.14159;
const CONFIG = { apiUrl: "http://api.com" }; // inferred type

// Multiple declarations
let x: number, y: number, z: number;
let a = 1, b = 2, c = 3; // inferred types
```

---

## 12. Difference between `any` and `unknown`

### Answer:

| Feature | `any` | `unknown` |
|---------|-------|-----------|
| **Type Safety** | No type checking | Type-safe |
| **Assignment** | Can assign any value | Can assign any value |
| **Usage** | Can use directly without checking | Must narrow type before use |
| **Method Calls** | Allowed without checks | Not allowed without narrowing |
| **Property Access** | Allowed without checks | Not allowed without narrowing |
| **Recommended** | Avoid when possible | Preferred over `any` |

### Theoretical Keywords:
**Type safety**, **Type narrowing**, **Type guards**,  
**Safe alternative**, **Type checking enforcement**, **Best practices**

### Example:
```typescript
// any - no restrictions (dangerous)
let anyValue: any = "hello";
anyValue.foo();           // ✅ No compile error (runtime error!)
anyValue.bar.baz;         // ✅ No compile error (runtime error!)
anyValue.toUpperCase();   // ✅ Works

// unknown - requires type checking (safe)
let unknownValue: unknown = "hello";
// unknownValue.toUpperCase(); // ❌ Error: Object is of type 'unknown'

// Must narrow the type first
if (typeof unknownValue === "string") {
    unknownValue.toUpperCase(); // ✅ Works after type guard
}

// Real-world scenario: API response
function handleResponse(data: unknown) {
    // Must validate before using
    if (typeof data === "object" && data !== null && "name" in data) {
        console.log((data as { name: string }).name);
    }
}
```

---

## 13. What is type inference?

### Answer:
- **Type inference** is TypeScript's ability to **automatically determine types**
- Compiler analyzes the value to infer the type
- No need to explicitly annotate types in many cases
- Makes code **cleaner** while maintaining type safety
- Works with variables, function return types, and more
- Best practice: Let TypeScript infer when type is obvious

### Theoretical Keywords:
**Automatic type detection**, **Implicit typing**, **Contextual typing**,  
**Best common type**, **Type deduction**, **Clean code**

### Example:
```typescript
// Type inference in action
let message = "Hello";     // inferred as string
let count = 42;            // inferred as number
let isValid = true;        // inferred as boolean

// Array type inference
let numbers = [1, 2, 3];           // inferred as number[]
let mixed = [1, "two", true];      // inferred as (string | number | boolean)[]

// Function return type inference
function add(a: number, b: number) {
    return a + b;  // return type inferred as number
}

// Object type inference
let user = {
    name: "John",
    age: 25
};  // inferred as { name: string; age: number; }

// Contextual typing
window.onclick = function(event) {
    // event is inferred as MouseEvent
    console.log(event.button);
};
```

---

## 14. What is union type?

### Answer:
- **Union type** allows a variable to hold **one of several types**
- Defined using the **pipe (`|`)** operator between types
- Variable can be **any one of the specified types** at a time
- Must handle all possible types when using the value
- Useful for parameters that accept multiple types

### Theoretical Keywords:
**Multiple types**, **Pipe operator**, **Type alternatives**,  
**Type narrowing**, **Discriminated unions**, **Flexible typing**

### Example:
```typescript
// Basic union type
let id: string | number;
id = "abc123";  // ✅ Valid
id = 12345;     // ✅ Valid
// id = true;   // ❌ Error: boolean not in union

// Union with null (nullable type)
let username: string | null = null;
username = "John";

// Function parameter with union
function printId(id: string | number): void {
    // Must narrow type to use type-specific methods
    if (typeof id === "string") {
        console.log(id.toUpperCase());
    } else {
        console.log(id.toFixed(2));
    }
}

// Union with literal types
type Status = "pending" | "approved" | "rejected";
let orderStatus: Status = "pending";

// Array with union
let data: (string | number)[] = [1, "two", 3, "four"];
```

---

## 15. What is intersection type?

### Answer:
- **Intersection type** combines **multiple types into one**
- Defined using the **ampersand (`&`)** operator
- Resulting type has **all properties** from all combined types
- Object must satisfy **all** intersected types
- Commonly used to **merge interfaces** or types

### Theoretical Keywords:
**Type combination**, **Ampersand operator**, **Type merging**,  
**Interface composition**, **All properties required**, **Mixins**

### Example:
```typescript
// Basic intersection
interface Person {
    name: string;
    age: number;
}

interface Employee {
    employeeId: string;
    department: string;
}

// Intersection combines both
type Staff = Person & Employee;

const staff: Staff = {
    name: "John",
    age: 30,
    employeeId: "E123",
    department: "Engineering"
};  // Must have ALL properties

// Intersection with type aliases
type Printable = {
    print(): void;
};

type Loggable = {
    log(): void;
};

type PrintableLoggable = Printable & Loggable;

const obj: PrintableLoggable = {
    print() { console.log("Printing..."); },
    log() { console.log("Logging..."); }
};

// Practical use case: extending types
type WithTimestamp<T> = T & { createdAt: Date; updatedAt: Date };

type User = { name: string; email: string };
type TimestampedUser = WithTimestamp<User>;
```

---

## 16. What is literal type?

### Answer:
- **Literal types** are types that represent **exact specific values**
- Variable can only hold that **exact value**, nothing else
- Works with **strings**, **numbers**, and **booleans**
- Often combined with **union types** for allowed values
- Provides **stronger type safety** than general types

### Theoretical Keywords:
**Exact value types**, **String literals**, **Number literals**,  
**Boolean literals**, **Const assertions**, **Narrowing**

### Example:
```typescript
// String literal types
let direction: "north" | "south" | "east" | "west";
direction = "north";  // ✅ Valid
// direction = "up";  // ❌ Error: not in literal union

// Number literal types
let diceRoll: 1 | 2 | 3 | 4 | 5 | 6;
diceRoll = 3;  // ✅ Valid
// diceRoll = 7; // ❌ Error

// Boolean literal type
let trueOnly: true = true;
// trueOnly = false; // ❌ Error

// Practical example: API status codes
type HttpStatus = 200 | 201 | 400 | 404 | 500;

function handleStatus(status: HttpStatus) {
    switch (status) {
        case 200: return "OK";
        case 404: return "Not Found";
        // TypeScript knows all possible values
    }
}

// const assertion for literal inference
let config = {
    endpoint: "/api",
    method: "GET"
} as const;
// config.method is type "GET", not string
```

---

## 17. What is `enum`?

### Answer:
- **Enum** (enumeration) defines a set of **named constants**
- Provides **readable names** for numeric or string values
- **Numeric enums**: Auto-increment from 0 by default
- **String enums**: Each member must be explicitly initialized
- Enums exist at **runtime** as JavaScript objects
- Useful for representing fixed sets of related values

### Theoretical Keywords:
**Named constants**, **Numeric enum**, **String enum**,  
**Auto-increment**, **Reverse mapping**, **Const enum**,  
**Runtime existence**

### Example:
```typescript
// Numeric enum (auto-increments from 0)
enum Direction {
    Up,      // 0
    Down,    // 1
    Left,    // 2
    Right    // 3
}
let move: Direction = Direction.Up;
console.log(move);  // 0

// Custom starting value
enum Status {
    Pending = 1,
    Active = 2,
    Inactive = 3
}

// String enum (must initialize each)
enum Color {
    Red = "RED",
    Green = "GREEN",
    Blue = "BLUE"
}
let color: Color = Color.Red;
console.log(color);  // "RED"

// Reverse mapping (numeric enums only)
enum Days {
    Sunday,
    Monday,
    Tuesday
}
console.log(Days[0]);      // "Sunday"
console.log(Days.Sunday);  // 0

// Const enum (inlined at compile time, no runtime object)
const enum Size {
    Small = "S",
    Medium = "M",
    Large = "L"
}
let size = Size.Small; // Compiles to: let size = "S";
```

---

## 18. Difference between `enum` and union types

### Answer:

| Feature | Enum | Union Types |
|---------|------|-------------|
| **Runtime** | Exists as JS object | Types only, erased at compile |
| **Performance** | Slight overhead | Zero overhead |
| **Reverse Mapping** | Available (numeric) | Not available |
| **Iteration** | Can iterate values | Cannot iterate |
| **Bundle Size** | Adds code | No extra code |
| **Flexibility** | Fixed structure | More flexible |

### Theoretical Keywords:
**Runtime vs compile-time**, **Bundle size**, **Reverse mapping**,  
**Type erasure**, **Performance**, **Const enum**

### Example:
```typescript
// Enum approach
enum StatusEnum {
    Pending = "PENDING",
    Active = "ACTIVE",
    Inactive = "INACTIVE"
}
let status1: StatusEnum = StatusEnum.Pending;

// Union type approach (preferred in many cases)
type StatusUnion = "PENDING" | "ACTIVE" | "INACTIVE";
let status2: StatusUnion = "PENDING";

// Compiled JavaScript:
// Enum generates:
// var StatusEnum;
// (function (StatusEnum) {
//     StatusEnum["Pending"] = "PENDING";
//     ...
// })(StatusEnum || (StatusEnum = {}));

// Union type generates: NOTHING (erased)

// When to use enum:
// - Need runtime access to values
// - Need to iterate over all values
// - Reverse mapping needed

// When to use union:
// - Smaller bundle size needed
// - No runtime features needed
// - Simple fixed set of values
```

---
