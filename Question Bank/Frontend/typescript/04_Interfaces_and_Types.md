# INTERFACES AND TYPES ANSWERS

---

## 25. What is an interface?

### Answer:
- **Interface** defines the **structure/shape** of an object
- Specifies **property names** and their **types**
- Acts as a **contract** that objects must follow
- Used for **type checking** at compile time
- Interfaces are **erased** at runtime (no JavaScript output)
- Supports **optional properties**, **readonly**, and **methods**

### Theoretical Keywords:
**Object shape**, **Contract**, **Structure definition**,  
**Compile-time only**, **Duck typing**, **Type checking**

### Example:
```typescript
// Basic interface
interface User {
    name: string;
    age: number;
    email: string;
}

// Object must match interface shape
const user: User = {
    name: "John",
    age: 25,
    email: "john@example.com"
};

// Interface with optional and readonly
interface Product {
    readonly id: number;
    name: string;
    price: number;
    description?: string;  // optional
}

const product: Product = {
    id: 1,
    name: "Laptop",
    price: 999
};
// product.id = 2; // ❌ Error: Cannot assign to 'id' because it is read-only

// Interface with methods
interface Calculator {
    add(a: number, b: number): number;
    subtract(a: number, b: number): number;
}

const calc: Calculator = {
    add: (a, b) => a + b,
    subtract: (a, b) => a - b
};
```

---

## 26. Difference between interface and type

### Answer:

| Feature | Interface | Type |
|---------|-----------|------|
| **Declaration** | `interface Name {}` | `type Name = {}` |
| **Extension** | `extends` keyword | `&` intersection |
| **Merging** | ✅ Declaration merging | ❌ Cannot merge |
| **Implements** | ✅ Classes can implement | ✅ Classes can implement |
| **Union/Intersection** | ❌ Cannot create unions | ✅ Can create unions |
| **Primitives** | ❌ Only objects | ✅ Any type |
| **Computed Props** | ❌ Not supported | ✅ Supported |

### Theoretical Keywords:
**Declaration merging**, **Extensibility**, **Union types**,  
**Intersection types**, **Primitives**, **Object types**

### Example:
```typescript
// Interface
interface UserInterface {
    name: string;
    age: number;
}

// Type alias
type UserType = {
    name: string;
    age: number;
};

// Declaration merging (only interface)
interface UserInterface {
    email: string;  // Merged with above
}
// UserInterface now has: name, age, email

// type UserType { email: string; } // ❌ Error: Duplicate identifier

// Union types (only type)
type StringOrNumber = string | number;
// interface StringOrNumber = string | number; // ❌ Not possible

// Primitive types (only type)
type ID = string;
type Callback = () => void;

// Extending
interface Admin extends UserInterface {
    role: string;
}

type AdminType = UserType & {
    role: string;
};

// Computed properties (only type)
type Keys = "name" | "age";
type Person = {
    [K in Keys]: string;
};
```

---

## 27. Can interfaces be extended?

### Answer:
- **Yes**, interfaces can be **extended** using the `extends` keyword
- Child interface **inherits** all properties from parent
- Can extend **multiple interfaces** (comma-separated)
- Can **add new properties** or **override** with compatible types
- Creates an **inheritance hierarchy** for object shapes

### Theoretical Keywords:
**Interface inheritance**, **extends keyword**, **Multiple inheritance**,  
**Property inheritance**, **Type hierarchy**, **Object composition**

### Example:
```typescript
// Base interface
interface Person {
    name: string;
    age: number;
}

// Extending single interface
interface Employee extends Person {
    employeeId: string;
    department: string;
}

const emp: Employee = {
    name: "John",
    age: 30,
    employeeId: "E123",
    department: "Engineering"
};

// Extending multiple interfaces
interface Printable {
    print(): void;
}

interface Loggable {
    log(): void;
}

interface Document extends Printable, Loggable {
    title: string;
    content: string;
}

const doc: Document = {
    title: "Report",
    content: "...",
    print() { console.log("Printing..."); },
    log() { console.log("Logging..."); }
};

// Overriding properties (must be compatible)
interface Animal {
    name: string;
    age: number | string;
}

interface Dog extends Animal {
    breed: string;
    age: number;  // Narrowed from number | string to number
}
```

---

## 28. Can types be extended?

### Answer:
- **Yes**, types can be extended using **intersection (`&`)**
- Creates a **new type** combining all properties
- Cannot use `extends` keyword like interfaces
- Intersection merges **all properties** from all types
- Conflicting properties create intersection (may become `never`)

### Theoretical Keywords:
**Intersection types**, **Type composition**, **Ampersand operator**,  
**Property merging**, **Type combination**

### Example:
```typescript
// Base type
type Person = {
    name: string;
    age: number;
};

// Extending type with intersection
type Employee = Person & {
    employeeId: string;
    department: string;
};

const emp: Employee = {
    name: "John",
    age: 30,
    employeeId: "E123",
    department: "Engineering"
};

// Combining multiple types
type Printable = {
    print(): void;
};

type Loggable = {
    log(): void;
};

type Document = Printable & Loggable & {
    title: string;
    content: string;
};

// Generic type extension
type WithId<T> = T & { id: number };

type User = {
    name: string;
    email: string;
};

type UserWithId = WithId<User>;
// { name: string; email: string; id: number; }

// ⚠️ Conflicting properties become intersection
type A = { value: string };
type B = { value: number };
type C = A & B;  // value: string & number → never
// const c: C = { value: ??? }; // Impossible to satisfy
```

---

## 29. When to use interface vs type?

### Answer:
**Use Interface when:**
- Defining **object shapes** for classes to implement
- Need **declaration merging** (extending third-party types)
- Building **public APIs** or libraries
- Prefer **OOP-style** syntax with `extends`

**Use Type when:**
- Need **union types** or **intersection types**
- Working with **primitives**, **tuples**, or **functions**
- Need **computed/mapped types**
- Want more **flexibility**

### Theoretical Keywords:
**Best practices**, **Declaration merging**, **Union types**,  
**Object shapes**, **API design**, **Flexibility vs structure**

### Example:
```typescript
// Use Interface - Object shapes, class implementation
interface Repository<T> {
    find(id: string): T | null;
    save(item: T): void;
    delete(id: string): boolean;
}

class UserRepository implements Repository<User> {
    find(id: string) { /* ... */ return null; }
    save(item: User) { /* ... */ }
    delete(id: string) { return true; }
}

// Use Type - Unions, primitives, complex types
type ID = string | number;
type Callback<T> = (data: T) => void;
type Status = "pending" | "success" | "error";

type Result<T> = 
    | { status: "success"; data: T }
    | { status: "error"; error: Error };

// Use Interface - Declaration merging
interface Window {
    myCustomProperty: string;
}

// Use Type - Computed properties
type Keys = "name" | "age" | "email";
type UserRecord = {
    [K in Keys]: string;
};

// Practical guideline:
// - Default to interface for objects
// - Use type when interface can't express it
```

---

## 30. What is `readonly` property?

### Answer:
- **readonly** modifier makes a property **immutable after initialization**
- Can only be assigned during **object creation** or **constructor**
- Compile-time check only (no runtime enforcement)
- Useful for **configuration objects**, **IDs**, and **constants**
- Can be used in interfaces, types, and classes

### Theoretical Keywords:
**Immutability**, **Compile-time only**, **Property modifier**,  
**Const-like behavior**, **Initialization only**, **Object integrity**

### Example:
```typescript
// readonly in interface
interface User {
    readonly id: number;
    name: string;
    email: string;
}

const user: User = {
    id: 1,
    name: "John",
    email: "john@example.com"
};

user.name = "Jane";     // ✅ Allowed
// user.id = 2;         // ❌ Error: Cannot assign to 'id' because it is read-only

// readonly in type
type Config = {
    readonly apiUrl: string;
    readonly apiKey: string;
};

// readonly in class
class Person {
    readonly birthDate: Date;
    
    constructor(birthDate: Date) {
        this.birthDate = birthDate;  // ✅ Allowed in constructor
    }
    
    changeBirthDate(date: Date) {
        // this.birthDate = date;  // ❌ Error
    }
}

// ReadonlyArray
const numbers: ReadonlyArray<number> = [1, 2, 3];
// numbers.push(4);  // ❌ Error
// numbers[0] = 10;  // ❌ Error

// Readonly utility type
type ReadonlyUser = Readonly<User>;
// All properties become readonly
```

---

## 31. Optional properties in interfaces

### Answer:
- **Optional properties** may or may not be present in object
- Marked with **question mark (`?`)** after property name
- Type automatically includes `undefined`
- Useful for **partial data** or **configuration options**
- Must handle `undefined` case when accessing

### Theoretical Keywords:
**Question mark modifier**, **Partial data**, **Undefined handling**,  
**Optional chaining**, **Configuration objects**, **Flexible contracts**

### Example:
```typescript
// Optional properties
interface UserProfile {
    name: string;           // required
    email: string;          // required
    age?: number;           // optional
    phone?: string;         // optional
    address?: {             // optional nested object
        street: string;
        city: string;
    };
}

// Valid objects
const user1: UserProfile = {
    name: "John",
    email: "john@example.com"
};

const user2: UserProfile = {
    name: "Jane",
    email: "jane@example.com",
    age: 25,
    phone: "123-456-7890"
};

// Accessing optional properties safely
function getAge(user: UserProfile): string {
    // Optional chaining
    return user.age?.toString() ?? "Unknown";
    
    // Or traditional check
    // if (user.age !== undefined) {
    //     return user.age.toString();
    // }
    // return "Unknown";
}

// Function with optional parameters object
interface RequestOptions {
    url: string;
    method?: "GET" | "POST" | "PUT" | "DELETE";
    headers?: Record<string, string>;
    body?: unknown;
    timeout?: number;
}

function makeRequest(options: RequestOptions) {
    const method = options.method ?? "GET";
    const timeout = options.timeout ?? 5000;
    // ...
}

makeRequest({ url: "/api/users" });  // method defaults to "GET"
```

---
