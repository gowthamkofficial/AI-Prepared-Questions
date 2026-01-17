# ADVANCED TYPES ANSWERS

---

## 44. What is tuple?

### Answer:
- **Tuple** is a **fixed-length array** with known types at each position
- Each element can have a **different type**
- Unlike arrays, tuples have **strict length** and **position-based types**
- Useful for **returning multiple values** from functions
- Supports **optional elements** and **rest elements**

### Theoretical Keywords:
**Fixed-length array**, **Position-based types**, **Heterogeneous types**,  
**Multiple return values**, **Destructuring**, **Type safety**

### Example:
```typescript
// Basic tuple
let person: [string, number] = ["John", 25];
// person[0] is string, person[1] is number

console.log(person[0].toUpperCase());  // "JOHN" - knows it's string
console.log(person[1].toFixed(2));     // "25.00" - knows it's number

// Tuple with labels (TypeScript 4.0+)
type Point = [x: number, y: number];
const point: Point = [10, 20];

// Optional tuple elements
type OptionalTuple = [string, number?];
const t1: OptionalTuple = ["hello"];
const t2: OptionalTuple = ["hello", 42];

// Tuple with rest elements
type StringNumberBooleans = [string, number, ...boolean[]];
const t3: StringNumberBooleans = ["hello", 1, true, false, true];

// Function returning tuple
function getNameAndAge(): [string, number] {
    return ["John", 25];
}

const [name, age] = getNameAndAge();  // Destructuring

// readonly tuple
const readonlyTuple: readonly [string, number] = ["hello", 42];
// readonlyTuple[0] = "world";  // ❌ Error: Cannot assign

// Real-world: React useState hook returns tuple
// const [state, setState] = useState<string>("");  // [string, function]

// Tuple vs Array
let tuple: [string, number] = ["a", 1];  // exactly 2 elements
let array: (string | number)[] = ["a", 1, "b", 2];  // any length
```

---

## 45. What is mapped type?

### Answer:
- **Mapped types** create new types by **transforming properties** of existing types
- Use **`in keyof`** syntax to iterate over properties
- Can **modify**, **add**, or **remove** property modifiers
- Foundation for utility types like `Partial`, `Required`, `Readonly`
- Powerful for creating **derived types** from existing ones

### Theoretical Keywords:
**Property transformation**, **in keyof**, **Type iteration**,  
**Modifier manipulation**, **Derived types**, **Utility types**

### Example:
```typescript
// Basic mapped type
type Keys = "name" | "age" | "email";

type Person = {
    [K in Keys]: string;
};
// { name: string; age: string; email: string; }

// Mapped type from existing type
interface User {
    id: number;
    name: string;
    email: string;
}

// Make all properties optional
type OptionalUser = {
    [K in keyof User]?: User[K];
};
// { id?: number; name?: string; email?: string; }

// Make all properties readonly
type ReadonlyUser = {
    readonly [K in keyof User]: User[K];
};

// Make all properties nullable
type NullableUser = {
    [K in keyof User]: User[K] | null;
};

// Transform property types
type StringifiedUser = {
    [K in keyof User]: string;
};
// { id: string; name: string; email: string; }

// Generic mapped type
type Nullable<T> = {
    [K in keyof T]: T[K] | null;
};

type NullableUser2 = Nullable<User>;

// Remove readonly modifier
type Mutable<T> = {
    -readonly [K in keyof T]: T[K];
};

// Remove optional modifier
type Required<T> = {
    [K in keyof T]-?: T[K];
};

// Key remapping (TypeScript 4.1+)
type Getters<T> = {
    [K in keyof T as `get${Capitalize<string & K>}`]: () => T[K];
};

type UserGetters = Getters<User>;
// { getId: () => number; getName: () => string; getEmail: () => string; }
```

---

## 46. What is conditional type?

### Answer:
- **Conditional types** select type based on a **condition**
- Syntax: `T extends U ? X : Y`
- If `T` is assignable to `U`, result is `X`, otherwise `Y`
- Can be **nested** for complex type logic
- Enable **type-level programming** and inference

### Theoretical Keywords:
**Type condition**, **extends keyword**, **Ternary type**,  
**Type inference**, **Distributive conditional**, **Type narrowing**

### Example:
```typescript
// Basic conditional type
type IsString<T> = T extends string ? true : false;

type A = IsString<string>;   // true
type B = IsString<number>;   // false
type C = IsString<"hello">;  // true

// Extracting types
type ExtractArrayType<T> = T extends (infer U)[] ? U : never;

type D = ExtractArrayType<string[]>;   // string
type E = ExtractArrayType<number[]>;   // number
type F = ExtractArrayType<string>;     // never

// Function return type extraction
type ReturnType<T> = T extends (...args: any[]) => infer R ? R : never;

function getUser() {
    return { id: 1, name: "John" };
}

type UserType = ReturnType<typeof getUser>;
// { id: number; name: string; }

// Nested conditional types
type TypeName<T> = 
    T extends string ? "string" :
    T extends number ? "number" :
    T extends boolean ? "boolean" :
    T extends undefined ? "undefined" :
    T extends Function ? "function" :
    "object";

type T1 = TypeName<string>;     // "string"
type T2 = TypeName<() => void>; // "function"
type T3 = TypeName<string[]>;   // "object"

// Distributive conditional types
type ToArray<T> = T extends any ? T[] : never;

type G = ToArray<string | number>;  // string[] | number[]

// Non-distributive (wrap in tuple)
type ToArrayNonDistributive<T> = [T] extends [any] ? T[] : never;

type H = ToArrayNonDistributive<string | number>;  // (string | number)[]

// Practical example: Excluding types
type Exclude<T, U> = T extends U ? never : T;

type I = Exclude<"a" | "b" | "c", "a">;  // "b" | "c"
```

---

## 47. What is `keyof` operator?

### Answer:
- **keyof** creates a **union type** of all property keys of a type
- Returns **string literal union** of property names
- Useful for creating **type-safe property access**
- Often combined with **generics** and **indexed access types**
- Essential for building **type-safe utilities**

### Theoretical Keywords:
**Property keys**, **Union of keys**, **Index type query**,  
**Type-safe access**, **Generic constraints**, **Lookup types**

### Example:
```typescript
// Basic keyof
interface User {
    id: number;
    name: string;
    email: string;
}

type UserKeys = keyof User;  // "id" | "name" | "email"

// Using keyof for type-safe property access
function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

const user: User = { id: 1, name: "John", email: "john@example.com" };

const name = getProperty(user, "name");   // type: string
const id = getProperty(user, "id");       // type: number
// getProperty(user, "age");              // ❌ Error: "age" not in keyof User

// keyof with index signatures
interface StringMap {
    [key: string]: number;
}

type StringMapKeys = keyof StringMap;  // string | number

// keyof with arrays
type ArrayKeys = keyof string[];  // number | "length" | "push" | "pop" | ...

// Practical: Pick implementation
type MyPick<T, K extends keyof T> = {
    [P in K]: T[P];
};

type UserNameEmail = MyPick<User, "name" | "email">;
// { name: string; email: string; }

// keyof with typeof
const config = {
    apiUrl: "https://api.example.com",
    timeout: 5000,
    retries: 3
};

type ConfigKeys = keyof typeof config;  // "apiUrl" | "timeout" | "retries"

// Creating type-safe event handlers
interface Events {
    click: MouseEvent;
    focus: FocusEvent;
    keydown: KeyboardEvent;
}

function addEventListener<K extends keyof Events>(
    eventType: K,
    handler: (event: Events[K]) => void
): void {
    // Implementation
}

addEventListener("click", (event) => {
    console.log(event.clientX);  // event is MouseEvent
});
```

---

## 48. What is `typeof` operator in TypeScript?

### Answer:
- **typeof** in TypeScript extracts the **type of a value/variable**
- Different from JavaScript's runtime `typeof`
- Used in **type context** to get compile-time type
- Useful for getting types from **values**, **functions**, **objects**
- Commonly used with **keyof** for type-safe operations

### Theoretical Keywords:
**Type extraction**, **Type context**, **Compile-time typeof**,  
**Value to type**, **Type inference**, **ReturnType**

### Example:
```typescript
// JavaScript typeof (runtime)
const value = "hello";
console.log(typeof value);  // "string" (runtime check)

// TypeScript typeof (type level)
let message = "Hello World";
type MessageType = typeof message;  // string

// typeof with objects
const user = {
    id: 1,
    name: "John",
    isActive: true
};

type UserType = typeof user;
// { id: number; name: string; isActive: boolean; }

// typeof with arrays
const numbers = [1, 2, 3];
type NumberArrayType = typeof numbers;  // number[]

// typeof with const assertion
const colors = ["red", "green", "blue"] as const;
type ColorsType = typeof colors;  // readonly ["red", "green", "blue"]
type Color = (typeof colors)[number];  // "red" | "green" | "blue"

// typeof with functions
function add(a: number, b: number): number {
    return a + b;
}

type AddFunction = typeof add;  // (a: number, b: number) => number
type AddReturn = ReturnType<typeof add>;  // number
type AddParams = Parameters<typeof add>;  // [a: number, b: number]

// typeof with keyof
const config = {
    apiUrl: "https://api.example.com",
    timeout: 5000
};

type ConfigKeys = keyof typeof config;  // "apiUrl" | "timeout"

function getConfig<K extends keyof typeof config>(key: K): (typeof config)[K] {
    return config[key];
}

const url = getConfig("apiUrl");     // type: string
const timeout = getConfig("timeout"); // type: number

// typeof with enum
enum Status {
    Pending,
    Active,
    Inactive
}

type StatusType = typeof Status;
// typeof Status (the enum object itself, not the values)

type StatusValue = Status;
// Status (enum member values: 0 | 1 | 2)
```

---

## 49. What are utility types?

### Answer:
- **Utility types** are **built-in generic types** provided by TypeScript
- Used to **transform** existing types into new types
- Save time by avoiding manual type creation
- Include types for making properties optional, required, readonly, etc.
- Defined in TypeScript's **lib.es5.d.ts**

### Theoretical Keywords:
**Built-in types**, **Type transformation**, **Generic utilities**,  
**Property modifiers**, **Type manipulation**, **Standard library**

### Example:
```typescript
interface User {
    id: number;
    name: string;
    email: string;
    age: number;
}

// Partial<T> - Makes all properties optional
type PartialUser = Partial<User>;
// { id?: number; name?: string; email?: string; age?: number; }

// Required<T> - Makes all properties required
interface OptionalConfig {
    apiUrl?: string;
    timeout?: number;
}
type RequiredConfig = Required<OptionalConfig>;
// { apiUrl: string; timeout: number; }

// Readonly<T> - Makes all properties readonly
type ReadonlyUser = Readonly<User>;
// All properties are readonly

// Pick<T, K> - Pick specific properties
type UserCredentials = Pick<User, "email" | "name">;
// { email: string; name: string; }

// Omit<T, K> - Omit specific properties
type UserWithoutId = Omit<User, "id">;
// { name: string; email: string; age: number; }

// Record<K, T> - Create object type with keys K and values T
type PageInfo = Record<"home" | "about" | "contact", { title: string }>;
// { home: { title: string }; about: { title: string }; contact: { title: string }; }

// Extract<T, U> - Extract types assignable to U
type StringOrNumber = string | number | boolean;
type OnlyStrings = Extract<StringOrNumber, string>;  // string

// Exclude<T, U> - Exclude types assignable to U
type NotStrings = Exclude<StringOrNumber, string>;  // number | boolean

// NonNullable<T> - Remove null and undefined
type MaybeString = string | null | undefined;
type DefiniteString = NonNullable<MaybeString>;  // string

// ReturnType<T> - Get function return type
function getUser() {
    return { id: 1, name: "John" };
}
type GetUserReturn = ReturnType<typeof getUser>;
// { id: number; name: string; }

// Parameters<T> - Get function parameters as tuple
type GetUserParams = Parameters<typeof getUser>;  // []

function createUser(name: string, age: number) {
    return { name, age };
}
type CreateUserParams = Parameters<typeof createUser>;  // [string, number]
```

---

## 50. Common utility types (`Partial`, `Required`, `Pick`, `Omit`, `Record`)

### Answer:
These are the **most commonly used** utility types in TypeScript:

### Theoretical Keywords:
**Partial**, **Required**, **Pick**, **Omit**, **Record**,  
**Type transformation**, **Property selection**, **Object mapping**

### Example:
```typescript
interface Product {
    id: number;
    name: string;
    price: number;
    description: string;
    inStock: boolean;
}

// ========================================
// Partial<T> - All properties become optional
// ========================================
// Use case: Update functions where not all fields are required

type ProductUpdate = Partial<Product>;

function updateProduct(id: number, updates: Partial<Product>): void {
    // Can pass any subset of properties
}

updateProduct(1, { price: 99.99 });  // ✅ Only update price
updateProduct(1, { name: "New Name", inStock: false });  // ✅ Multiple fields

// ========================================
// Required<T> - All properties become required
// ========================================
// Use case: Ensure all optional properties are provided

interface Config {
    apiUrl?: string;
    timeout?: number;
    retries?: number;
}

type StrictConfig = Required<Config>;
// All properties now required

function initApp(config: Required<Config>): void {
    // Guaranteed to have all values
}

// ========================================
// Pick<T, K> - Select specific properties
// ========================================
// Use case: Create subset types for specific purposes

type ProductPreview = Pick<Product, "id" | "name" | "price">;
// { id: number; name: string; price: number; }

function displayProductCard(product: ProductPreview): void {
    console.log(`${product.name}: $${product.price}`);
}

// ========================================
// Omit<T, K> - Remove specific properties
// ========================================
// Use case: Exclude certain properties (like id for creation)

type CreateProductDTO = Omit<Product, "id">;
// { name: string; price: number; description: string; inStock: boolean; }

function createProduct(data: Omit<Product, "id">): Product {
    return { ...data, id: Math.random() };
}

createProduct({
    name: "Laptop",
    price: 999,
    description: "A great laptop",
    inStock: true
});

// ========================================
// Record<K, T> - Create object with specific key-value types
// ========================================
// Use case: Type-safe dictionaries and maps

// Simple record
type StatusCodes = Record<number, string>;
const codes: StatusCodes = {
    200: "OK",
    404: "Not Found",
    500: "Server Error"
};

// Record with union keys
type PageNames = "home" | "about" | "contact";
type PageConfig = Record<PageNames, { title: string; url: string }>;

const pages: PageConfig = {
    home: { title: "Home", url: "/" },
    about: { title: "About Us", url: "/about" },
    contact: { title: "Contact", url: "/contact" }
};

// Record for entity lookup
type UserById = Record<string, Product>;
const productLookup: UserById = {
    "p1": { id: 1, name: "Product 1", price: 10, description: "", inStock: true },
    "p2": { id: 2, name: "Product 2", price: 20, description: "", inStock: false }
};

// Combining utility types
type CreateProductForm = Partial<Omit<Product, "id">>;
// All fields optional except id is removed
```

---
