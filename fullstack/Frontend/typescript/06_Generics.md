# GENERICS ANSWERS

---

## 38. What are generics?

### Answer:
- **Generics** allow creating **reusable components** that work with multiple types
- Use **type parameters** (placeholders) that are specified when used
- Provide **type safety** while maintaining **flexibility**
- Convention: Use single capital letters (`T`, `U`, `K`, `V`)
- Similar to generics in Java, C#, and other typed languages
- Enable writing **type-safe** yet **flexible** code

### Theoretical Keywords:
**Type parameters**, **Reusable components**, **Type placeholders**,  
**Type safety**, **Flexibility**, **Parameterized types**

### Example:
```typescript
// Without generics - loses type information
function identityAny(value: any): any {
    return value;
}
const result1 = identityAny("hello");  // type: any (lost string type)

// With generics - preserves type information
function identity<T>(value: T): T {
    return value;
}

const result2 = identity<string>("hello");  // type: string
const result3 = identity<number>(42);       // type: number
const result4 = identity("world");          // type: string (inferred)

// Generic class
class Container<T> {
    private value: T;
    
    constructor(value: T) {
        this.value = value;
    }
    
    getValue(): T {
        return this.value;
    }
    
    setValue(value: T): void {
        this.value = value;
    }
}

const stringContainer = new Container<string>("Hello");
const numberContainer = new Container<number>(100);

console.log(stringContainer.getValue());  // "Hello" (type: string)
console.log(numberContainer.getValue());  // 100 (type: number)
```

---

## 39. Why are generics used?

### Answer:
- **Type Safety**: Catch type errors at compile time
- **Code Reusability**: Single implementation works with multiple types
- **Avoid Duplication**: No need to write same logic for different types
- **Better IntelliSense**: IDE knows exact types being used
- **Flexibility**: Work with any type while preserving type information
- **Avoid `any`**: Type-safe alternative to using `any`

### Theoretical Keywords:
**Code reusability**, **Type safety**, **DRY principle**,  
**Type preservation**, **Compile-time checking**, **Avoid any**

### Example:
```typescript
// Problem: Duplicate code for different types
function getFirstNumber(arr: number[]): number | undefined {
    return arr[0];
}

function getFirstString(arr: string[]): string | undefined {
    return arr[0];
}

// Solution: Generic function
function getFirst<T>(arr: T[]): T | undefined {
    return arr[0];
}

// Works with any type
getFirst<number>([1, 2, 3]);      // type: number | undefined
getFirst<string>(["a", "b"]);     // type: string | undefined
getFirst([true, false]);          // type: boolean | undefined (inferred)

// Problem: any loses type information
function wrapInArrayAny(value: any): any[] {
    return [value];
}
const wrapped1 = wrapInArrayAny("hello");  // type: any[]

// Solution: Generic preserves type
function wrapInArray<T>(value: T): T[] {
    return [value];
}
const wrapped2 = wrapInArray("hello");  // type: string[]

// Real-world: API response handling
interface ApiResponse<T> {
    data: T;
    status: number;
    message: string;
}

interface User {
    id: number;
    name: string;
}

interface Product {
    id: number;
    price: number;
}

// Same interface, different data types
const userResponse: ApiResponse<User> = {
    data: { id: 1, name: "John" },
    status: 200,
    message: "Success"
};

const productResponse: ApiResponse<Product> = {
    data: { id: 1, price: 99.99 },
    status: 200,
    message: "Success"
};
```

---

## 40. Generic functions

### Answer:
- Functions with **type parameters** that work with multiple types
- Type parameter declared in **angle brackets** before parameters
- Can have **multiple type parameters**
- Type can be **explicitly specified** or **inferred**
- Can apply **constraints** to limit allowed types

### Theoretical Keywords:
**Type parameters**, **Type inference**, **Multiple parameters**,  
**Explicit typing**, **Generic constraints**, **Function signature**

### Example:
```typescript
// Single type parameter
function identity<T>(value: T): T {
    return value;
}

// Multiple type parameters
function pair<T, U>(first: T, second: U): [T, U] {
    return [first, second];
}

const p1 = pair<string, number>("age", 25);  // [string, number]
const p2 = pair("name", "John");             // [string, string] (inferred)

// Generic function with array
function getLength<T>(arr: T[]): number {
    return arr.length;
}

// Generic function returning specific type
function createArray<T>(length: number, value: T): T[] {
    return Array(length).fill(value);
}

const numArray = createArray<number>(3, 0);    // [0, 0, 0] type: number[]
const strArray = createArray(3, "x");          // ["x", "x", "x"] type: string[]

// Generic arrow function
const reverse = <T>(arr: T[]): T[] => {
    return [...arr].reverse();
};

// Generic function with callback
function map<T, U>(arr: T[], fn: (item: T) => U): U[] {
    return arr.map(fn);
}

const numbers = [1, 2, 3];
const strings = map(numbers, n => n.toString());  // type: string[]
const doubled = map(numbers, n => n * 2);         // type: number[]

// Generic function type alias
type Transformer<T, U> = (input: T) => U;

const stringToNumber: Transformer<string, number> = (s) => parseInt(s);
```

---

## 41. Generic interfaces

### Answer:
- Interfaces with **type parameters** for flexible type definitions
- Enable creating **reusable contracts** for different types
- Type parameter specified when **implementing or using** interface
- Common in **API responses**, **repositories**, and **collections**

### Theoretical Keywords:
**Parameterized interfaces**, **Reusable contracts**, **Type flexibility**,  
**Implementation typing**, **Generic collections**

### Example:
```typescript
// Basic generic interface
interface Container<T> {
    value: T;
    getValue(): T;
    setValue(value: T): void;
}

// Implementing generic interface
class Box<T> implements Container<T> {
    constructor(public value: T) {}
    
    getValue(): T {
        return this.value;
    }
    
    setValue(value: T): void {
        this.value = value;
    }
}

const stringBox = new Box<string>("Hello");
const numberBox = new Box<number>(42);

// Generic interface for key-value pair
interface KeyValuePair<K, V> {
    key: K;
    value: V;
}

const pair1: KeyValuePair<string, number> = { key: "age", value: 25 };
const pair2: KeyValuePair<number, string> = { key: 1, value: "one" };

// Generic interface for API responses
interface ApiResponse<T> {
    data: T;
    status: number;
    timestamp: Date;
    error?: string;
}

interface User {
    id: number;
    name: string;
    email: string;
}

function fetchUser(): ApiResponse<User> {
    return {
        data: { id: 1, name: "John", email: "john@example.com" },
        status: 200,
        timestamp: new Date()
    };
}

// Generic repository interface
interface Repository<T> {
    findById(id: string): Promise<T | null>;
    findAll(): Promise<T[]>;
    create(item: T): Promise<T>;
    update(id: string, item: Partial<T>): Promise<T>;
    delete(id: string): Promise<boolean>;
}

// Implementation for specific entity
class UserRepository implements Repository<User> {
    async findById(id: string): Promise<User | null> {
        // Implementation
        return null;
    }
    async findAll(): Promise<User[]> { return []; }
    async create(user: User): Promise<User> { return user; }
    async update(id: string, user: Partial<User>): Promise<User> { 
        return { id: 1, name: "", email: "" }; 
    }
    async delete(id: string): Promise<boolean> { return true; }
}
```

---

## 42. Generic constraints

### Answer:
- **Constraints** limit the types that can be used with generics
- Use **`extends`** keyword to specify constraint
- Ensures type parameter has **certain properties or methods**
- Can constrain to **interfaces**, **types**, or **classes**
- Multiple constraints with **intersection (`&`)**

### Theoretical Keywords:
**Type constraints**, **extends keyword**, **Type bounds**,  
**Property requirements**, **Interface constraints**, **Multiple constraints**

### Example:
```typescript
// Without constraint - error
function getLength<T>(item: T): number {
    // return item.length;  // ❌ Error: Property 'length' does not exist
    return 0;
}

// With constraint - works
interface HasLength {
    length: number;
}

function getLengthConstrained<T extends HasLength>(item: T): number {
    return item.length;  // ✅ Works - T guaranteed to have length
}

getLengthConstrained("hello");      // ✅ string has length
getLengthConstrained([1, 2, 3]);    // ✅ array has length
getLengthConstrained({ length: 10 }); // ✅ object has length
// getLengthConstrained(123);       // ❌ number doesn't have length

// Constraint with keyof
function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

const person = { name: "John", age: 25 };
getProperty(person, "name");  // ✅ type: string
getProperty(person, "age");   // ✅ type: number
// getProperty(person, "email"); // ❌ Error: "email" not in keyof person

// Multiple constraints
interface Printable {
    print(): void;
}

interface Loggable {
    log(): void;
}

function process<T extends Printable & Loggable>(item: T): void {
    item.print();
    item.log();
}

// Constraint with class
class Animal {
    name: string = "";
}

class Dog extends Animal {
    bark(): void { console.log("Woof!"); }
}

function getAnimalName<T extends Animal>(animal: T): string {
    return animal.name;
}

// Constructor constraint
interface Constructor<T> {
    new (...args: any[]): T;
}

function createInstance<T>(ctor: Constructor<T>): T {
    return new ctor();
}
```

---

## 43. Real-time use cases of generics

### Answer:
Generics are used extensively in real-world TypeScript applications:
- **API Response Handling**: Type-safe response structures
- **State Management**: Redux/NgRx typed actions and state
- **Form Handling**: Type-safe form values
- **Data Repositories**: CRUD operations for any entity
- **Utility Functions**: Array operations, transformations
- **React/Angular Components**: Reusable typed components

### Theoretical Keywords:
**API responses**, **State management**, **Form handling**,  
**Repository pattern**, **Utility functions**, **Component props**

### Example:
```typescript
// 1. API Response Handler
interface ApiResponse<T> {
    data: T | null;
    error: string | null;
    loading: boolean;
}

async function fetchData<T>(url: string): Promise<ApiResponse<T>> {
    try {
        const response = await fetch(url);
        const data = await response.json();
        return { data, error: null, loading: false };
    } catch (error) {
        return { data: null, error: String(error), loading: false };
    }
}

interface User { id: number; name: string; }
const userResponse = await fetchData<User>("/api/user/1");
// userResponse.data is User | null

// 2. State Management (Redux-like)
interface Action<T = any> {
    type: string;
    payload: T;
}

function createAction<T>(type: string, payload: T): Action<T> {
    return { type, payload };
}

const addUser = createAction("ADD_USER", { id: 1, name: "John" });
const setCount = createAction("SET_COUNT", 42);

// 3. Form Handler
interface FormState<T> {
    values: T;
    errors: Partial<Record<keyof T, string>>;
    touched: Partial<Record<keyof T, boolean>>;
    isValid: boolean;
}

function useForm<T extends object>(initialValues: T): FormState<T> {
    return {
        values: initialValues,
        errors: {},
        touched: {},
        isValid: true
    };
}

interface LoginForm {
    email: string;
    password: string;
}

const loginForm = useForm<LoginForm>({ email: "", password: "" });

// 4. Generic Repository Pattern
interface Entity {
    id: string | number;
}

class BaseRepository<T extends Entity> {
    protected items: T[] = [];
    
    findById(id: T["id"]): T | undefined {
        return this.items.find(item => item.id === id);
    }
    
    create(item: T): T {
        this.items.push(item);
        return item;
    }
    
    update(id: T["id"], updates: Partial<T>): T | undefined {
        const index = this.items.findIndex(item => item.id === id);
        if (index !== -1) {
            this.items[index] = { ...this.items[index], ...updates };
            return this.items[index];
        }
        return undefined;
    }
    
    delete(id: T["id"]): boolean {
        const index = this.items.findIndex(item => item.id === id);
        if (index !== -1) {
            this.items.splice(index, 1);
            return true;
        }
        return false;
    }
}

// 5. Angular Service Example
interface HttpResponse<T> {
    body: T;
    status: number;
    headers: Map<string, string>;
}

class DataService {
    get<T>(url: string): Promise<HttpResponse<T>> {
        // Implementation
        return {} as Promise<HttpResponse<T>>;
    }
    
    post<T, R>(url: string, body: T): Promise<HttpResponse<R>> {
        // Implementation
        return {} as Promise<HttpResponse<R>>;
    }
}

// Usage
interface Product { id: number; name: string; price: number; }
const service = new DataService();
const products = await service.get<Product[]>("/api/products");
```

---
