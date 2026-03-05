# TYPESCRIPT BASICS ANSWERS

---

## 1. What is TypeScript?

### Answer:
- TypeScript is a **strongly typed, object-oriented** programming language
- Developed and maintained by **Microsoft** (created by Anders Hejlsberg in 2012)
- It is a **superset of JavaScript** - any valid JS is valid TS
- Adds **static typing**, **interfaces**, **generics**, and other features
- TypeScript code **compiles (transpiles) to plain JavaScript**
- Provides **compile-time type checking** to catch errors early
- Improves **code quality**, **maintainability**, and **developer productivity**

### Theoretical Keywords:
**Superset of JavaScript**, **Static typing**, **Compile-time checking**,  
**Type safety**, **Transpilation**, **Microsoft**, **Anders Hejlsberg**,  
**ECMAScript compatibility**, **IDE support**

---

## 2. Why do we need TypeScript when JavaScript already exists?

### Answer:
- **Type Safety**: Catches type-related errors at compile time, not runtime
- **Better IDE Support**: IntelliSense, auto-completion, refactoring tools
- **Code Maintainability**: Self-documenting code through type annotations
- **Early Error Detection**: Finds bugs before code runs in production
- **Large-Scale Applications**: Better suited for enterprise applications
- **Team Collaboration**: Clearer contracts between different parts of code
- **Modern Features**: Access to latest ECMAScript features with backward compatibility

### Theoretical Keywords:
**Type safety**, **Compile-time errors**, **IntelliSense**,  
**Code maintainability**, **Refactoring support**, **Team collaboration**,  
**Enterprise applications**, **Developer experience**

### Example:
```typescript
// JavaScript - Error at runtime
function add(a, b) {
    return a + b;
}
add("5", 10); // "510" - unexpected string concatenation

// TypeScript - Error at compile time
function addTS(a: number, b: number): number {
    return a + b;
}
addTS("5", 10); // ❌ Compile Error: Argument of type 'string' is not assignable
```

---

## 3. Is TypeScript compiled or interpreted?

### Answer:
- TypeScript is **compiled** (more precisely, **transpiled**)
- TypeScript compiler (**tsc**) converts TS code to JavaScript
- The resulting JavaScript is then interpreted by browser/Node.js
- TypeScript itself **never runs directly** - always converted to JS first
- Compilation catches type errors before code execution
- Can target different JavaScript versions (ES5, ES6, ESNext, etc.)

### Theoretical Keywords:
**Transpilation**, **tsc compiler**, **Source-to-source compilation**,  
**Target JavaScript version**, **Type erasure**, **Build step**,  
**Compile-time vs runtime**

### Compilation Flow:
```
TypeScript (.ts) → tsc compiler → JavaScript (.js) → Browser/Node.js
```

---

## 4. What is the difference between JavaScript and TypeScript?

### Answer:

| Feature | JavaScript | TypeScript |
|---------|------------|------------|
| **Typing** | Dynamic (runtime) | Static (compile-time) |
| **Type Annotations** | Not supported | Supported |
| **Compilation** | Interpreted directly | Needs compilation to JS |
| **Interfaces** | Not available | Fully supported |
| **Generics** | Not available | Fully supported |
| **Error Detection** | Runtime errors | Compile-time errors |
| **IDE Support** | Limited | Excellent (IntelliSense) |
| **Learning Curve** | Easier | Slightly steeper |
| **File Extension** | `.js` | `.ts` / `.tsx` |

### Theoretical Keywords:
**Static vs dynamic typing**, **Type annotations**, **Compilation step**,  
**Interfaces**, **Generics**, **Type inference**, **File extensions**,  
**Backward compatibility**

---

## 5. What are the benefits of using TypeScript?

### Answer:
- **Type Safety**: Prevents type-related bugs at compile time
- **Better Tooling**: Superior autocomplete, navigation, refactoring
- **Self-Documenting Code**: Types serve as inline documentation
- **Easier Refactoring**: Safe code changes with type checking
- **Better Collaboration**: Clear contracts between team members
- **Future JavaScript Features**: Use modern features with backward compatibility
- **Gradual Adoption**: Can be added incrementally to existing projects
- **Framework Support**: First-class support in Angular, React, Vue

### Theoretical Keywords:
**Type safety**, **Developer productivity**, **Code quality**,  
**Maintainability**, **Refactoring**, **Team collaboration**,  
**Framework integration**, **Gradual migration**

---

## 6. What is type safety?

### Answer:
- **Type safety** means the compiler prevents type-related errors
- Ensures operations are performed only on compatible types
- Variables can only hold values of their declared type
- Function parameters must match expected types
- Prevents common bugs like calling methods on `undefined`
- Provides **guarantees** about data types at compile time

### Theoretical Keywords:
**Compile-time checking**, **Type compatibility**, **Type guards**,  
**Null safety**, **Type coercion prevention**, **Data integrity**,  
**Contract enforcement**

### Example:
```typescript
// Type safe code
let username: string = "John";
username = 123; // ❌ Error: Type 'number' is not assignable to type 'string'

function greet(name: string): string {
    return `Hello, ${name.toUpperCase()}`; // Safe - name is definitely string
}

greet(null); // ❌ Error: Argument of type 'null' is not assignable
```

---

## 7. What is static typing?

### Answer:
- **Static typing** means types are checked at **compile time** (before running)
- Types are declared explicitly or inferred by the compiler
- Once a variable has a type, it cannot change
- Opposite of **dynamic typing** where types are checked at runtime
- Errors are caught during development, not in production
- Enables better IDE support and documentation

### Theoretical Keywords:
**Compile-time type checking**, **Type declaration**, **Type inference**,  
**Early error detection**, **Type persistence**, **IDE integration**,  
**Dynamic vs static typing**

### Example:
```typescript
// Static typing - types checked at compile time
let count: number = 10;
count = "ten"; // ❌ Compile Error

// Dynamic typing (JavaScript) - types checked at runtime
let jsCount = 10;
jsCount = "ten"; // ✅ No error until runtime issues occur
```

---

## 8. What are primitive types in TypeScript?

### Answer:
TypeScript has the following **primitive types**:
- **number**: All numbers (integers, floats, hex, binary, octal)
- **string**: Text values (single, double quotes, template literals)
- **boolean**: `true` or `false`
- **null**: Intentional absence of value
- **undefined**: Uninitialized variable
- **symbol**: Unique identifier (ES6)
- **bigint**: Large integers (ES2020)

### Theoretical Keywords:
**Primitive types**, **Value types**, **Immutable values**,  
**Type annotation**, **Literal types**, **Type inference**

### Example:
```typescript
let age: number = 25;
let price: number = 99.99;
let hex: number = 0xff;

let name: string = "John";
let greeting: string = `Hello, ${name}`;

let isActive: boolean = true;

let nothing: null = null;
let notDefined: undefined = undefined;

let id: symbol = Symbol("id");

let bigNumber: bigint = 9007199254740991n;
```

---

## 9. What is the `any` type?

### Answer:
- **any** is a special type that **disables type checking**
- Variables of type `any` can hold **any value** of any type
- Essentially **opts out of TypeScript's type system**
- Useful for migrating JavaScript code or working with dynamic data
- **Should be avoided** when possible - defeats purpose of TypeScript
- Can be source of **runtime errors** that TypeScript normally prevents

### Theoretical Keywords:
**Type escape hatch**, **Opt-out of type checking**, **Dynamic typing**,  
**Migration helper**, **Anti-pattern**, **noImplicitAny**,  
**Type safety bypass**

### Example:
```typescript
let data: any = 10;
data = "hello";    // ✅ No error
data = true;       // ✅ No error
data = { x: 1 };   // ✅ No error

// Danger of any
let value: any = "hello";
console.log(value.toFixed(2)); // ❌ Runtime Error! (no compile error)

// any allows anything - TypeScript won't help
function process(input: any) {
    return input.foo.bar.baz; // No type checking - risky!
}
```

---

## 10. What is `unknown` type?

### Answer:
- **unknown** is the **type-safe counterpart** to `any`
- Can hold any value, but requires **type checking before use**
- Must narrow the type before performing operations
- Introduced in TypeScript 3.0 as a safer alternative to `any`
- Forces developers to handle type verification explicitly
- Ideal for values from external sources (API responses, user input)

### Theoretical Keywords:
**Type-safe any**, **Type narrowing required**, **Type guards**,  
**Safe dynamic typing**, **Type checking enforcement**, **TypeScript 3.0**,  
**External data handling**

### Example:
```typescript
let value: unknown = "hello";

// Cannot use directly - must narrow type first
// value.toUpperCase(); // ❌ Error: Object is of type 'unknown'

// Type narrowing required
if (typeof value === "string") {
    console.log(value.toUpperCase()); // ✅ Works - type narrowed to string
}

// Comparison with any
let anyValue: any = "hello";
anyValue.toUpperCase(); // ✅ No error (but risky)

let unknownValue: unknown = "hello";
// unknownValue.toUpperCase(); // ❌ Error - must check type first
```

---
