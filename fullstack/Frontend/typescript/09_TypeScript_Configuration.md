# TYPESCRIPT CONFIGURATION ANSWERS

---

## 55. What is `tsconfig.json`?

### Answer:
- **tsconfig.json** is the **configuration file** for TypeScript compiler
- Defines **compiler options**, **file inclusion/exclusion**, and project settings
- Located at **project root** directory
- Automatically detected by **tsc** and IDE (VS Code)
- Enables **consistent compilation** settings across team
- Can **extend** other configuration files

### Theoretical Keywords:
**Compiler configuration**, **Project settings**, **tsc options**,  
**File inclusion**, **Extends inheritance**, **Root directory**

### Example:
```json
{
  "compilerOptions": {
    // Target JavaScript version
    "target": "ES2020",
    
    // Module system
    "module": "commonjs",
    
    // Output directory
    "outDir": "./dist",
    
    // Root directory of source files
    "rootDir": "./src",
    
    // Strict type checking
    "strict": true,
    
    // Allow importing JSON files
    "resolveJsonModule": true,
    
    // Emit declaration files
    "declaration": true,
    
    // Skip type checking of declaration files
    "skipLibCheck": true,
    
    // Enable all strict type-checking options
    "strictNullChecks": true,
    "strictFunctionTypes": true,
    "noImplicitAny": true
  },
  
  // Files to include
  "include": [
    "src/**/*"
  ],
  
  // Files to exclude
  "exclude": [
    "node_modules",
    "dist",
    "**/*.test.ts"
  ],
  
  // Extend another config
  "extends": "./tsconfig.base.json"
}
```

---

## 56. Important compiler options

### Answer:
Key compiler options in **tsconfig.json**:

### Theoretical Keywords:
**target**, **module**, **strict**, **outDir**, **rootDir**,  
**esModuleInterop**, **declaration**, **sourceMap**

### Example:
```json
{
  "compilerOptions": {
    // ========================================
    // Basic Options
    // ========================================
    
    "target": "ES2020",        // ECMAScript target version
    "module": "commonjs",       // Module code generation
    "lib": ["ES2020", "DOM"],  // Library files to include
    
    // ========================================
    // Output Options
    // ========================================
    
    "outDir": "./dist",        // Output directory
    "rootDir": "./src",        // Root source directory
    "declaration": true,        // Generate .d.ts files
    "declarationMap": true,     // Generate sourcemaps for .d.ts
    "sourceMap": true,          // Generate .map files for debugging
    "removeComments": true,     // Remove comments in output
    
    // ========================================
    // Strict Type-Checking
    // ========================================
    
    "strict": true,             // Enable all strict options
    "noImplicitAny": true,      // Error on implicit any
    "strictNullChecks": true,   // Strict null/undefined checks
    "strictFunctionTypes": true, // Strict function type checking
    "strictBindCallApply": true, // Check bind, call, apply
    "strictPropertyInitialization": true, // Check class properties
    "noImplicitThis": true,     // Error on implicit this
    "alwaysStrict": true,       // Parse in strict mode
    
    // ========================================
    // Module Resolution
    // ========================================
    
    "moduleResolution": "node", // How modules are resolved
    "baseUrl": "./",            // Base for non-relative imports
    "paths": {                  // Path mapping
      "@/*": ["src/*"],
      "@components/*": ["src/components/*"]
    },
    "esModuleInterop": true,    // ES module interoperability
    "allowSyntheticDefaultImports": true,
    "resolveJsonModule": true,  // Allow importing JSON
    
    // ========================================
    // Additional Checks
    // ========================================
    
    "noUnusedLocals": true,     // Error on unused locals
    "noUnusedParameters": true, // Error on unused params
    "noImplicitReturns": true,  // Error on missing returns
    "noFallthroughCasesInSwitch": true,
    "noUncheckedIndexedAccess": true,
    
    // ========================================
    // Experimental
    // ========================================
    
    "experimentalDecorators": true,  // Enable decorators
    "emitDecoratorMetadata": true    // Emit decorator metadata
  }
}
```

---

## 57. `strict` mode in TypeScript

### Answer:
- **strict** is a **meta flag** that enables all strict type-checking options
- Setting `"strict": true` enables **7+ strict checks** at once
- Provides the **strongest type safety** guarantees
- Recommended for **new projects**
- Individual options can still be **disabled** if needed

### Theoretical Keywords:
**Meta flag**, **Type safety**, **Strict checking**, **Best practices**,  
**New projects**, **Error prevention**

### Enabled Options:
```json
{
  "compilerOptions": {
    // "strict": true is equivalent to:
    "strict": true,
    
    // Or individually:
    "noImplicitAny": true,
    "noImplicitThis": true,
    "strictNullChecks": true,
    "strictFunctionTypes": true,
    "strictBindCallApply": true,
    "strictPropertyInitialization": true,
    "alwaysStrict": true,
    "useUnknownInCatchVariables": true  // TS 4.4+
  }
}
```

### Example:
```typescript
// Without strict mode - these would be allowed:

// 1. Implicit any
function add(a, b) {  // ❌ Parameters have implicit 'any'
    return a + b;
}

// 2. Null/undefined issues
function greet(name: string) {
    console.log(name.toUpperCase());  // ❌ name could be null
}
greet(null);

// 3. Uninitialized properties
class User {
    name: string;  // ❌ Property not initialized
}

// With strict mode - all caught at compile time:

// 1. Must specify types
function addStrict(a: number, b: number): number {
    return a + b;
}

// 2. Must handle null
function greetStrict(name: string | null) {
    if (name) {
        console.log(name.toUpperCase());
    }
}

// 3. Must initialize or use definite assignment
class UserStrict {
    name: string;
    
    constructor(name: string) {
        this.name = name;  // ✅ Initialized
    }
}

// Or use definite assignment assertion
class UserWithAssertion {
    name!: string;  // ! asserts it will be assigned
}
```

---

## 58. What is `noImplicitAny`?

### Answer:
- **noImplicitAny** prevents variables from having **implicit `any` type**
- When enabled, must **explicitly type** parameters and variables
- Catches cases where TypeScript cannot infer a type
- Part of **strict** mode
- Forces **explicit type declarations**

### Theoretical Keywords:
**Implicit any**, **Type annotation required**, **Strict checking**,  
**Type safety**, **Explicit typing**, **Compiler error**

### Example:
```typescript
// ❌ With noImplicitAny: true - These cause errors:

// Error: Parameter 'x' implicitly has an 'any' type
function double(x) {
    return x * 2;
}

// Error: Parameter 'callback' implicitly has an 'any' type
function process(callback) {
    callback();
}

// Error: Variable 'data' implicitly has an 'any' type
let data;
data = "hello";
data = 42;

// ✅ Fixed versions:

function doubleFixed(x: number): number {
    return x * 2;
}

function processFixed(callback: () => void): void {
    callback();
}

let dataFixed: string | number;
dataFixed = "hello";
dataFixed = 42;

// Cases where any is acceptable (explicit):
function handleUnknownData(data: any): void {
    // Explicitly typed as any - acceptable
    console.log(data);
}

// JSON.parse returns any - known case
const parsed: any = JSON.parse('{"name": "John"}');

// Better approach with unknown
function handleSafe(data: unknown): void {
    if (typeof data === "string") {
        console.log(data.toUpperCase());
    }
}

// Array callback inference (works)
const numbers = [1, 2, 3];
numbers.map(n => n * 2);  // ✅ n is inferred as number
```

---

## 59. What is `strictNullChecks`?

### Answer:
- **strictNullChecks** makes `null` and `undefined` **distinct types**
- Variables cannot be null/undefined unless explicitly allowed
- Must handle null/undefined before using values
- Prevents **"Cannot read property of null/undefined"** errors
- Part of **strict** mode

### Theoretical Keywords:
**Null safety**, **Undefined handling**, **Explicit null**,  
**Type narrowing**, **Optional chaining**, **Null coalescing**

### Example:
```typescript
// ❌ Without strictNullChecks: false

function getLength(str: string) {
    return str.length;  // Could fail if str is null
}
getLength(null);  // No error - runtime crash!

// ✅ With strictNullChecks: true

function getLengthSafe(str: string | null): number {
    // Must handle null case
    if (str === null) {
        return 0;
    }
    return str.length;  // str is narrowed to string
}

// Or use optional chaining
function getLengthOptional(str: string | null | undefined): number {
    return str?.length ?? 0;
}

// Explicit null in types
let username: string | null = null;
username = "John";
username = null;  // ✅ Allowed

let requiredName: string = "John";
// requiredName = null;  // ❌ Error: null not assignable to string

// Function return types
function findUser(id: number): User | null {
    // May or may not find user
    if (id === 0) return null;
    return { id, name: "User" };
}

const user = findUser(1);
// user.name;  // ❌ Error: Object is possibly 'null'

if (user) {
    user.name;  // ✅ user narrowed to User
}

// Non-null assertion (use carefully)
const user2 = findUser(1);
console.log(user2!.name);  // ! asserts non-null (risky)

// Definite assignment assertion
class Example {
    name!: string;  // Will be assigned later
    
    initialize() {
        this.name = "Initialized";
    }
}

// Optional parameters
function greet(name?: string) {
    // name is string | undefined
    console.log(`Hello, ${name ?? "Guest"}`);
}
```

---

## 60. Difference between `target` and `module`

### Answer:
- **target**: The **ECMAScript version** for output JavaScript
- **module**: The **module system** for generated code
- They are **independent** but related settings
- **target** affects syntax (let, const, async/await, etc.)
- **module** affects how imports/exports are handled

### Theoretical Keywords:
**ECMAScript version**, **Module system**, **Output format**,  
**Transpilation**, **Compatibility**, **Code generation**

### Example:
```json
{
  "compilerOptions": {
    // target: Output JavaScript version
    "target": "ES2020",  // ES5, ES6, ES2015, ES2020, ESNext, etc.
    
    // module: Module code generation
    "module": "commonjs"  // commonjs, ES2015, ES2020, ESNext, AMD, etc.
  }
}
```

### Target Examples:
```typescript
// Source TypeScript
async function getData(): Promise<string> {
    const result = await fetch("/api");
    return result.text();
}

// Target: ES5 - transpiles async/await to callbacks
// Target: ES2017+ - keeps async/await as-is

// Source
const arr = [1, 2, 3];
const doubled = arr.map(n => n * 2);

// Target: ES5 - arrow function becomes regular function
// Target: ES6+ - keeps arrow function
```

### Module Examples:
```typescript
// Source TypeScript
import { User } from './user';
export const name = "John";

// module: "commonjs" output
const user_1 = require('./user');
exports.name = "John";

// module: "ES2015" output
import { User } from './user';
export const name = "John";

// module: "AMD" output
define(["require", "exports", "./user"], function(require, exports, user_1) {
    exports.name = "John";
});
```

### Common Combinations:
```json
// Node.js (CommonJS)
{
  "target": "ES2020",
  "module": "commonjs"
}

// Browser (ES Modules)
{
  "target": "ES2020",
  "module": "ES2020"
}

// Library (multiple formats)
{
  "target": "ES2015",
  "module": "ESNext"
}

// Angular
{
  "target": "ES2020",
  "module": "ES2020"
}

// React (with bundler)
{
  "target": "ES2020",
  "module": "ESNext"
}
```

---
