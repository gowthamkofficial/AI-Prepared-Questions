# TypeScript - Complete Interview Questions & Answers

## **TypeScript Basics**

### 1. What is TypeScript?
**Answer:** TypeScript is a superset of JavaScript that adds static typing. It compiles to plain JavaScript.

**Key Features:**
- Static type checking
- Interfaces and types
- Generics
- Enums
- Decorators
- Better IDE support
- Catches errors at compile-time

### 2. TypeScript vs JavaScript?
**Answer:**

| Feature | JavaScript | TypeScript |
|---------|------------|------------|
| Typing | Dynamic | Static |
| Compilation | Not required | Required |
| Error Detection | Runtime | Compile-time |
| IDE Support | Basic | Advanced |
| Learning Curve | Easier | Steeper |
| File Extension | .js | .ts |

### 3. What are basic types in TypeScript?
**Answer:**

```typescript
// Primitive types
let name: string = "John";
let age: number = 30;
let isActive: boolean = true;
let nothing: null = null;
let notDefined: undefined = undefined;

// Array
let numbers: number[] = [1, 2, 3];
let names: Array<string> = ["John", "Jane"];

// Tuple
let tuple: [string, number] = ["John", 30];

// Enum
enum Color { Red, Green, Blue }
let color: Color = Color.Red;

// Any (avoid when possible)
let anything: any = "hello";
anything = 42;

// Unknown (safer than any)
let value: unknown = "hello";
if (typeof value === "string") {
  console.log(value.toUpperCase());
}

// Void (no return value)
function log(): void {
  console.log("Hello");
}

// Never (never returns)
function error(): never {
  throw new Error("Error");
}

// Object
let obj: object = { name: "John" };
```

### 4. What is the difference between any and unknown?
**Answer:**

```typescript
// any - disables type checking
let value1: any = "hello";
value1.toUpperCase();  // No error
value1.nonExistent();  // No error (runtime error)

// unknown - requires type checking
let value2: unknown = "hello";
// value2.toUpperCase();  // Error

// Must check type first
if (typeof value2 === "string") {
  value2.toUpperCase();  // OK
}

// Best practice: Use unknown instead of any
```

### 5. What is the never type?
**Answer:** Represents values that never occur.

```typescript
// Function that never returns
function throwError(message: string): never {
  throw new Error(message);
}

// Infinite loop
function infiniteLoop(): never {
  while (true) { }
}

// Exhaustive type checking
type Shape = Circle | Square;

function getArea(shape: Shape): number {
  switch (shape.kind) {
    case "circle":
      return Math.PI * shape.radius ** 2;
    case "square":
      return shape.size ** 2;
    default:
      const _exhaustive: never = shape;  // Ensures all cases handled
      return _exhaustive;
  }
}
```

---

## **Interfaces & Types**

### 6. What is an interface?
**Answer:** Defines structure of an object.

```typescript
interface User {
  name: string;
  age: number;
  email?: string;  // Optional
  readonly id: number;  // Readonly
}

const user: User = {
  id: 1,
  name: "John",
  age: 30
};

// user.id = 2;  // Error: readonly

// Interface with methods
interface Person {
  name: string;
  greet(): void;
  greet(message: string): void;  // Overload
}

// Extending interfaces
interface Employee extends Person {
  role: string;
}
```

### 7. What is the difference between interface and type?
**Answer:**

```typescript
// Interface
interface User {
  name: string;
}

// Type alias
type User = {
  name: string;
};

// Key differences:

// 1. Extending
interface Animal { name: string; }
interface Dog extends Animal { breed: string; }

type Animal = { name: string; };
type Dog = Animal & { breed: string; };

// 2. Declaration merging (only interfaces)
interface User { name: string; }
interface User { age: number; }
// Merged: { name: string; age: number; }

// 3. Union types (only type)
type ID = string | number;

// 4. Primitives (only type)
type Name = string;

// 5. Tuples (both, but type is cleaner)
type Point = [number, number];

// Best practice: Use interface for objects, type for unions/primitives
```

### 8. What are union and intersection types?
**Answer:**

```typescript
// Union (OR) - can be one of multiple types
type ID = string | number;
let userId: ID = "abc123";
userId = 123;

type Status = "pending" | "approved" | "rejected";
let status: Status = "pending";

// Intersection (AND) - combines multiple types
type Person = { name: string; };
type Employee = { role: string; };
type Staff = Person & Employee;

const staff: Staff = {
  name: "John",
  role: "Developer"
};

// Practical example
type Success = { success: true; data: any; };
type Failure = { success: false; error: string; };
type Response = Success | Failure;

function handleResponse(response: Response) {
  if (response.success) {
    console.log(response.data);
  } else {
    console.error(response.error);
  }
}
```

### 9. What is type assertion?
**Answer:** Tell compiler the type of a value.

```typescript
// Using 'as'
let value: any = "hello";
let length: number = (value as string).length;

// Using angle brackets (not in JSX)
let length2: number = (<string>value).length;

// Non-null assertion
let name: string | null = getName();
let upperName = name!.toUpperCase();  // Assert not null

// Const assertion
let colors = ["red", "green"] as const;
// Type: readonly ["red", "green"]

// Double assertion (avoid)
let value2 = "hello" as unknown as number;  // Dangerous
```

---

## **Functions**

### 10. How to type functions?
**Answer:**

```typescript
// Function declaration
function add(a: number, b: number): number {
  return a + b;
}

// Function expression
const multiply = (a: number, b: number): number => {
  return a * b;
};

// Optional parameters
function greet(name: string, greeting?: string): string {
  return `${greeting || "Hello"}, ${name}`;
}

// Default parameters
function createUser(name: string, role: string = "user"): User {
  return { name, role };
}

// Rest parameters
function sum(...numbers: number[]): number {
  return numbers.reduce((a, b) => a + b, 0);
}

// Function type
type MathOperation = (a: number, b: number) => number;
const divide: MathOperation = (a, b) => a / b;

// Void return
function log(message: string): void {
  console.log(message);
}
```

### 11. What is function overloading?
**Answer:**

```typescript
// Overload signatures
function getValue(id: number): string;
function getValue(name: string): number;

// Implementation
function getValue(value: number | string): string | number {
  if (typeof value === "number") {
    return "User" + value;
  } else {
    return value.length;
  }
}

const result1 = getValue(1);      // string
const result2 = getValue("John"); // number
```

---

## **Generics**

### 12. What are generics?
**Answer:** Create reusable components that work with multiple types.

```typescript
// Generic function
function identity<T>(value: T): T {
  return value;
}

identity<number>(5);
identity<string>("hello");
identity(true);  // Type inferred

// Generic interface
interface Box<T> {
  value: T;
}

const numberBox: Box<number> = { value: 5 };
const stringBox: Box<string> = { value: "hello" };

// Generic class
class Container<T> {
  private value: T;
  
  constructor(value: T) {
    this.value = value;
  }
  
  getValue(): T {
    return this.value;
  }
}

const numContainer = new Container<number>(5);

// Multiple type parameters
function pair<T, U>(first: T, second: U): [T, U] {
  return [first, second];
}

pair<string, number>("age", 30);

// Generic constraints
interface HasLength {
  length: number;
}

function logLength<T extends HasLength>(value: T): void {
  console.log(value.length);
}

logLength("hello");  // OK
logLength([1, 2, 3]);  // OK
// logLength(5);  // Error
```

### 13. What are generic constraints?
**Answer:**

```typescript
// Extends constraint
function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
  return obj[key];
}

const user = { name: "John", age: 30 };
getProperty(user, "name");  // OK
// getProperty(user, "email");  // Error

// Multiple constraints
interface HasId {
  id: number;
}

interface HasName {
  name: string;
}

function merge<T extends HasId, U extends HasName>(obj1: T, obj2: U): T & U {
  return { ...obj1, ...obj2 };
}
```

---

## **Utility Types**

### 14. What is Partial<T>?
**Answer:** Makes all properties optional.

```typescript
interface User {
  name: string;
  age: number;
  email: string;
}

type PartialUser = Partial<User>;
// { name?: string; age?: number; email?: string; }

function updateUser(user: User, updates: Partial<User>): User {
  return { ...user, ...updates };
}

updateUser(user, { age: 31 });  // Only update age
```

### 15. What is Required<T>?
**Answer:** Makes all properties required.

```typescript
interface User {
  name?: string;
  age?: number;
}

type RequiredUser = Required<User>;
// { name: string; age: number; }
```

### 16. What is Readonly<T>?
**Answer:** Makes all properties readonly.

```typescript
interface User {
  name: string;
  age: number;
}

type ReadonlyUser = Readonly<User>;
// { readonly name: string; readonly age: number; }

const user: ReadonlyUser = { name: "John", age: 30 };
// user.name = "Jane";  // Error
```

### 17. What is Pick<T, K>?
**Answer:** Select specific properties.

```typescript
interface User {
  name: string;
  age: number;
  email: string;
  password: string;
}

type UserPreview = Pick<User, "name" | "email">;
// { name: string; email: string; }
```

### 18. What is Omit<T, K>?
**Answer:** Exclude specific properties.

```typescript
interface User {
  name: string;
  age: number;
  password: string;
}

type SafeUser = Omit<User, "password">;
// { name: string; age: number; }
```

### 19. What is Record<K, T>?
**Answer:** Create object type with specific keys and values.

```typescript
type Roles = Record<string, boolean>;
const roles: Roles = {
  admin: true,
  user: false,
  guest: true
};

type PageInfo = Record<"home" | "about" | "contact", { title: string }>;
const pages: PageInfo = {
  home: { title: "Home" },
  about: { title: "About" },
  contact: { title: "Contact" }
};
```

### 20. What is Exclude<T, U> and Extract<T, U>?
**Answer:**

```typescript
// Exclude - remove types
type T1 = Exclude<"a" | "b" | "c", "a">;  // "b" | "c"
type T2 = Exclude<string | number | boolean, string>;  // number | boolean

// Extract - keep types
type T3 = Extract<"a" | "b" | "c", "a" | "b">;  // "a" | "b"
type T4 = Extract<string | number | boolean, string>;  // string
```

### 21. What is ReturnType<T>?
**Answer:** Get return type of function.

```typescript
function getUser() {
  return { name: "John", age: 30 };
}

type User = ReturnType<typeof getUser>;
// { name: string; age: number; }

type T1 = ReturnType<() => string>;  // string
type T2 = ReturnType<(x: number) => number>;  // number
```

---

## **Advanced Types**

### 22. What are mapped types?
**Answer:**

```typescript
// Make all properties optional
type Optional<T> = {
  [P in keyof T]?: T[P];
};

// Make all properties readonly
type Readonly<T> = {
  readonly [P in keyof T]: T[P];
};

// Make all properties nullable
type Nullable<T> = {
  [P in keyof T]: T[P] | null;
};

interface User {
  name: string;
  age: number;
}

type NullableUser = Nullable<User>;
// { name: string | null; age: number | null; }
```

### 23. What are conditional types?
**Answer:**

```typescript
// Syntax: T extends U ? X : Y

type IsString<T> = T extends string ? true : false;

type T1 = IsString<string>;  // true
type T2 = IsString<number>;  // false

// Practical example
type NonNullable<T> = T extends null | undefined ? never : T;

type T3 = NonNullable<string | null>;  // string
type T4 = NonNullable<number | undefined>;  // number

// With infer
type ReturnType<T> = T extends (...args: any[]) => infer R ? R : never;
```

### 24. What is the keyof operator?
**Answer:**

```typescript
interface User {
  name: string;
  age: number;
  email: string;
}

type UserKeys = keyof User;  // "name" | "age" | "email"

function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
  return obj[key];
}

const user: User = { name: "John", age: 30, email: "john@example.com" };
getProperty(user, "name");  // OK
// getProperty(user, "invalid");  // Error
```

### 25. What is the typeof operator?
**Answer:**

```typescript
const user = {
  name: "John",
  age: 30,
  address: {
    city: "NYC"
  }
};

type User = typeof user;
// { name: string; age: number; address: { city: string; } }

const config = {
  apiUrl: "https://api.com",
  timeout: 5000
} as const;

type Config = typeof config;
// { readonly apiUrl: "https://api.com"; readonly timeout: 5000; }
```

### 26. What are index signatures?
**Answer:**

```typescript
// String index
interface StringMap {
  [key: string]: string;
}

const map: StringMap = {
  name: "John",
  city: "NYC"
};

// Number index
interface NumberArray {
  [index: number]: string;
}

const arr: NumberArray = ["a", "b", "c"];

// Mixed
interface MixedMap {
  [key: string]: string | number;
  length: number;  // Must match index signature
}
```

### 27. What are literal types?
**Answer:**

```typescript
// String literal
type Direction = "up" | "down" | "left" | "right";
let dir: Direction = "up";

// Number literal
type DiceRoll = 1 | 2 | 3 | 4 | 5 | 6;
let roll: DiceRoll = 3;

// Boolean literal
type Success = true;
let result: Success = true;

// Template literal types
type EventName = "click" | "focus" | "blur";
type Event = `on${Capitalize<EventName>}`;
// "onClick" | "onFocus" | "onBlur"
```

### 28. What are template literal types?
**Answer:**

```typescript
type Color = "red" | "green" | "blue";
type Shade = "light" | "dark";

type ColorShade = `${Shade}-${Color}`;
// "light-red" | "light-green" | "light-blue" | "dark-red" | ...

// With utility types
type Getter = `get${Capitalize<string>}`;
type Setter = `set${Capitalize<string>}`;

// Practical example
type HTTPMethod = "GET" | "POST" | "PUT" | "DELETE";
type Endpoint = `/api/${string}`;
type APICall = `${HTTPMethod} ${Endpoint}`;
```

---

## **Classes & OOP**

### 29. How to type classes?
**Answer:**

```typescript
class User {
  // Properties
  name: string;
  private age: number;
  protected role: string;
  readonly id: number;
  
  // Constructor
  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
    this.role = "user";
    this.id = Date.now();
  }
  
  // Method
  greet(): string {
    return `Hi, I'm ${this.name}`;
  }
  
  // Getter
  get info(): string {
    return `${this.name} (${this.age})`;
  }
  
  // Setter
  set userAge(value: number) {
    if (value < 0) throw new Error("Invalid age");
    this.age = value;
  }
  
  // Static
  static create(name: string): User {
    return new User(name, 0);
  }
}

// Shorthand constructor
class Person {
  constructor(
    public name: string,
    private age: number
  ) {}
}
```

### 30. What are access modifiers?
**Answer:**

```typescript
class User {
  public name: string;      // Accessible everywhere (default)
  private age: number;      // Only in class
  protected role: string;   // Class + subclasses
  
  constructor(name: string, age: number, role: string) {
    this.name = name;
    this.age = age;
    this.role = role;
  }
}

class Admin extends User {
  showRole() {
    console.log(this.role);  // OK (protected)
    // console.log(this.age);  // Error (private)
  }
}

const user = new User("John", 30, "admin");
console.log(user.name);  // OK
// console.log(user.age);  // Error
```

### 31. What are abstract classes?
**Answer:**

```typescript
abstract class Animal {
  constructor(public name: string) {}
  
  // Abstract method (must be implemented)
  abstract makeSound(): void;
  
  // Concrete method
  move(): void {
    console.log(`${this.name} is moving`);
  }
}

class Dog extends Animal {
  makeSound(): void {
    console.log("Woof!");
  }
}

// const animal = new Animal("Animal");  // Error
const dog = new Dog("Buddy");
dog.makeSound();  // "Woof!"
```

---

## **TypeScript Configuration**

### 32. What are important tsconfig.json options?
**Answer:**

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "commonjs",
    "lib": ["ES2020", "DOM"],
    "outDir": "./dist",
    "rootDir": "./src",
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true,
    "resolveJsonModule": true,
    "declaration": true,
    "sourceMap": true,
    "noImplicitAny": true,
    "strictNullChecks": true,
    "strictFunctionTypes": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true
  },
  "include": ["src/**/*"],
  "exclude": ["node_modules", "dist"]
}
```

### 33. What is strict mode?
**Answer:** Enables all strict type checking options.

```typescript
// strict: true enables:
// - noImplicitAny
// - strictNullChecks
// - strictFunctionTypes
// - strictBindCallApply
// - strictPropertyInitialization
// - noImplicitThis
// - alwaysStrict
```

---

**Total: 33+ comprehensive TypeScript questions with detailed answers**
