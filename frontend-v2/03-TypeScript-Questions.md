# TypeScript - Most Important Interview Questions

## **Top 40 TypeScript Questions**

### 1. What is TypeScript?
**Answer:** Superset of JavaScript with static typing. Compiles to JavaScript.

### 2. TypeScript vs JavaScript?
**Answer:** TypeScript has static typing, interfaces, enums, generics. Catches errors at compile-time.

### 3. Basic types in TypeScript?
**Answer:** `string`, `number`, `boolean`, `array`, `tuple`, `enum`, `any`, `void`, `null`, `undefined`, `never`, `unknown`.

### 4. `any` vs `unknown`?
**Answer:** `any` disables type checking, `unknown` requires type checking before use.

### 5. What is `never` type?
**Answer:** Represents values that never occur (functions that throw errors or infinite loops).

### 6. Interface vs Type?
**Answer:**
```typescript
interface User { name: string; }  // Can extend, merge
type User = { name: string; }     // Can use unions, intersections
```
Both similar, interfaces better for objects.

### 7. What are Generics?
**Answer:** Reusable components with type variables:
```typescript
function identity<T>(arg: T): T { return arg; }
identity<number>(5);
```

### 8. Union vs Intersection types?
**Answer:**
```typescript
type A = string | number;        // Union (OR)
type B = User & Admin;           // Intersection (AND)
```

### 9. Type assertion?
**Answer:** Tell compiler the type:
```typescript
let value: any = "hello";
let length = (value as string).length;
let length2 = (<string>value).length;
```

### 10. Optional vs Required properties?
**Answer:**
```typescript
interface User {
  name: string;      // Required
  age?: number;      // Optional
}
```

### 11. Readonly modifier?
**Answer:**
```typescript
interface User {
  readonly id: number;
}
const user: User = { id: 1 };
// user.id = 2; // Error
```

### 12. Tuple type?
**Answer:** Fixed-length array with specific types:
```typescript
let tuple: [string, number] = ["hello", 42];
```

### 13. Enum type?
**Answer:**
```typescript
enum Color { Red, Green, Blue }
let c: Color = Color.Green;

enum Status { Active = 1, Inactive = 0 }
```

### 14. Type guards?
**Answer:**
```typescript
function isString(value: any): value is string {
  return typeof value === "string";
}
if (isString(value)) { /* value is string */ }
```

### 15. `typeof` vs `instanceof`?
**Answer:** `typeof` for primitives, `instanceof` for objects/classes.

### 16. Access modifiers?
**Answer:**
```typescript
class User {
  public name: string;      // Accessible everywhere
  private age: number;      // Only in class
  protected role: string;   // Class + subclasses
}
```

### 17. Abstract class?
**Answer:**
```typescript
abstract class Animal {
  abstract makeSound(): void;
  move(): void { console.log("moving"); }
}
```

### 18. Interface vs Abstract class?
**Answer:** Interface is contract (no implementation), abstract class can have implementation.

### 19. Function overloading?
**Answer:**
```typescript
function add(a: string, b: string): string;
function add(a: number, b: number): number;
function add(a: any, b: any): any { return a + b; }
```

### 20. Optional chaining?
**Answer:**
```typescript
const name = user?.profile?.name;
```

### 21. Nullish coalescing?
**Answer:**
```typescript
const value = input ?? "default";  // Only null/undefined
const value2 = input || "default"; // Any falsy
```

### 22. Utility types?
**Answer:**
```typescript
Partial<T>      // All properties optional
Required<T>     // All properties required
Readonly<T>     // All properties readonly
Pick<T, K>      // Select properties
Omit<T, K>      // Exclude properties
Record<K, T>    // Object with keys K and values T
```

### 23. `Partial<T>` example?
**Answer:**
```typescript
interface User { name: string; age: number; }
type PartialUser = Partial<User>;
// { name?: string; age?: number; }
```

### 24. `Pick<T, K>` example?
**Answer:**
```typescript
interface User { name: string; age: number; email: string; }
type UserPreview = Pick<User, "name" | "email">;
// { name: string; email: string; }
```

### 25. `Omit<T, K>` example?
**Answer:**
```typescript
interface User { name: string; age: number; password: string; }
type SafeUser = Omit<User, "password">;
// { name: string; age: number; }
```

### 26. `Record<K, T>` example?
**Answer:**
```typescript
type Roles = Record<string, boolean>;
const roles: Roles = { admin: true, user: false };
```

### 27. Mapped types?
**Answer:**
```typescript
type Readonly<T> = {
  readonly [P in keyof T]: T[P];
};
```

### 28. Conditional types?
**Answer:**
```typescript
type IsString<T> = T extends string ? true : false;
```

### 29. `keyof` operator?
**Answer:**
```typescript
interface User { name: string; age: number; }
type UserKeys = keyof User;  // "name" | "age"
```

### 30. `typeof` operator?
**Answer:**
```typescript
const user = { name: "John", age: 30 };
type User = typeof user;  // { name: string; age: number; }
```

### 31. Index signatures?
**Answer:**
```typescript
interface StringMap {
  [key: string]: string;
}
const map: StringMap = { a: "1", b: "2" };
```

### 32. Literal types?
**Answer:**
```typescript
type Direction = "up" | "down" | "left" | "right";
let dir: Direction = "up";
```

### 33. Template literal types?
**Answer:**
```typescript
type EventName = "click" | "focus";
type Event = `on${Capitalize<EventName>}`;  // "onClick" | "onFocus"
```

### 34. Decorators?
**Answer:**
```typescript
function Log(target: any, key: string) {
  console.log(`${key} was called`);
}
class User {
  @Log
  getName() { }
}
```

### 35. Namespace vs Module?
**Answer:** Modules use ES6 import/export, namespaces use internal TypeScript organization.

### 36. `declare` keyword?
**Answer:** Declares types for external libraries:
```typescript
declare const jQuery: any;
```

### 37. `.d.ts` files?
**Answer:** Type declaration files for JavaScript libraries.

### 38. `tsconfig.json` important options?
**Answer:**
```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "commonjs",
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "outDir": "./dist",
    "rootDir": "./src"
  }
}
```

### 39. `strict` mode?
**Answer:** Enables all strict type checking: `strictNullChecks`, `strictFunctionTypes`, `noImplicitAny`, etc.

### 40. Type inference?
**Answer:** TypeScript automatically infers types:
```typescript
let x = 3;  // inferred as number
let arr = [1, 2, 3];  // inferred as number[]
```

## **Advanced Concepts**

### 41. Discriminated unions?
**Answer:**
```typescript
interface Circle { kind: "circle"; radius: number; }
interface Square { kind: "square"; size: number; }
type Shape = Circle | Square;

function area(shape: Shape) {
  switch (shape.kind) {
    case "circle": return Math.PI * shape.radius ** 2;
    case "square": return shape.size ** 2;
  }
}
```

### 42. Type narrowing?
**Answer:**
```typescript
function print(value: string | number) {
  if (typeof value === "string") {
    console.log(value.toUpperCase());  // TypeScript knows it's string
  }
}
```

### 43. `as const` assertion?
**Answer:**
```typescript
const colors = ["red", "green"] as const;
// readonly ["red", "green"]
```

### 44. `satisfies` operator (TS 4.9+)?
**Answer:**
```typescript
const config = { url: "https://api.com" } satisfies Config;
// Validates type but preserves literal types
```

### 45. Variance in TypeScript?
**Answer:** Covariance (return types), contravariance (parameter types), bivariance (methods).
