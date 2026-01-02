# Answers for TypeScript Interview Questions

Source: D:\Interview\AI Prepared Questions\Frontend\Typescript\chatgpt.md

---

### Q: What is TypeScript?
**Expected Answer (Beginner):**
TypeScript is a statically typed superset of JavaScript that adds optional types, interfaces, and modern language features; it compiles to plain JavaScript.

**Key Theoretical Concepts:**
- Static typing, transpilation to JS, gradual typing, tooling support (editor intellisense).

**Interviewer Expectation:**
Explain what TypeScript adds over JavaScript and why teams use it (safety, tooling).

**Red Flags:**
- Saying TypeScript runs in the browser directly or confusing it with a runtime.

**Depth Expected:**
Surface-to-intermediate for 0–2 year level.

---

### Q: Why use TypeScript when JavaScript exists?
**Expected Answer:**
TypeScript provides compile-time type checking, better IDE support, safer refactoring, and clearer APIs — important for large codebases.

**Key Concepts:**
- Type inference, type annotations, interfaces, generics, compiler options (`tsconfig.json`).

**Interviewer Expectation:**
Practical reasons (team scale, fewer runtime bugs, maintainability).

**Red Flags:**
- Saying "it’s just for syntax" or focusing only on syntax sugar.

**Depth Expected:**
Practical understanding with one or two examples.

---

### Q: Is TypeScript compiled or interpreted?
**Expected Answer:**
TypeScript is transpiled/compiled to JavaScript by `tsc` (or bundlers). The emitted JS runs in browsers/Node.js.

**Key Concepts:**
- Transpilation, source maps, declaration files (`.d.ts`).

**Interviewer Expectation:**
Know the build step and how TS integrates with toolchains.

**Red Flags:**
- Claiming TypeScript is executed directly by the runtime.

**Depth Expected:**
Basic build-process knowledge.

---

### Q: What is `any` vs `unknown`?
**Expected Answer:**
`any` disables type checking for a value; `unknown` is safer — you must narrow it before use.

**Key Concepts:**
- Type safety, type narrowing, `typeof`/`instanceof` guards.

**Interviewer Expectation:**
Explain when to use each and why `unknown` is preferred over `any`.

**Red Flags:**
- Using `any` indiscriminately without reason.

**Depth Expected:**
Practical examples of narrowing.

---

### Q: Interfaces vs `type` aliases
**Expected Answer:**
`interface` describes object shapes and supports declaration merging and extension; `type` aliases are more flexible (unions, intersections), but can't be merged.

**Key Concepts:**
- Structural typing, extension, unions/intersections, declaration merging.

**Interviewer Expectation:**
Know when to pick one over the other and common patterns in libraries.

**Red Flags:**
- Not understanding extension or saying they are identical in every case.

**Depth Expected:**
Intermediate: small code example recommended.

---

### Q: Generics (brief)
**Expected Answer:**
Generics allow writing reusable, type-safe functions and components, e.g., `function id<T>(v: T): T`.

**Key Concepts:**
- Generic constraints, default type params, generic interfaces and classes.

**Interviewer Expectation:**
Be able to explain a simple generic function and a use-case.

**Red Flags:**
- Confusing generics with dynamic typing.

**Depth Expected:**
Practical knowledge for 2-year level.

---

If you want these expanded into the full per-question format for every bullet in the original `chatgpt.md`, I can generate the remaining entries — tell me if you want "all questions" or a subset (which sections).

---

Full expanded answers for all bullets (Beginner → Advanced):

### TypeScript Basics

Q: What is TypeScript?
**Expected Answer:** TypeScript is a statically typed superset of JavaScript that adds optional types, interfaces, enums, and modern language features; it transpiles to plain JavaScript.
**Key Concepts:** static typing, transpilation, gradual typing, structural typing.
**Interviewer Expectation:** Explain benefits for large codebases and tooling improvements.
**Red Flags:** Confusing TypeScript with a runtime.
**Depth Expected:** Basic explanation and one example.

Q: Why do we need TypeScript when JavaScript already exists?
**Expected Answer:** For stronger type-safety, better IDE support, earlier error detection, and safer refactoring in larger teams.
**Key Concepts:** maintainability, types as contracts, compiler checks.
**Interviewer Expectation:** Practical reasons and adoption scenarios.
**Red Flags:** Saying only "because it’s trendy".
**Depth Expected:** Practical examples.

Q: Is TypeScript compiled or interpreted?
**Expected Answer:** TypeScript is compiled/transpiled to JavaScript before execution by `tsc` or build tools.
**Key Concepts:** emit JS, source maps, build step.
**Interviewer Expectation:** Know integration with bundlers and runtime.
**Red Flags:** Saying TS runs directly in browsers.
**Depth Expected:** Build pipeline awareness.

Q: What is the difference between JavaScript and TypeScript?
**Expected Answer:** TypeScript adds optional static typing, interfaces, and compile-time checks; JS is dynamic and untyped.
**Key Concepts:** compile-time vs runtime checks, type erasure.
**Interviewer Expectation:** Tradeoffs and migration strategies.
**Red Flags:** Overstating performance differences.
**Depth Expected:** Practical pros/cons.

Q: What are the benefits of using TypeScript?
**Expected Answer:** Better tooling, error detection, API clarity, safer refactors, and improved collaboration.
**Key Concepts:** type annotations, intellisense, interfaces, generics.
**Interviewer Expectation:** Real-world benefits for projects.
**Red Flags:** Vague or non-technical answers.
**Depth Expected:** Examples from projects.

Q: What is type safety / static typing?
**Expected Answer:** Static typing means types are checked at compile time, reducing runtime type errors.
**Key Concepts:** type inference, strict mode, null checks.
**Interviewer Expectation:** Explain `strict` flags and common errors prevented.
**Red Flags:** Not understanding `null`/`undefined` handling.
**Depth Expected:** Basic.

Q: What are primitive types in TypeScript?
**Expected Answer:** `string`, `number`, `boolean`, `bigint`, `symbol`, `null`, `undefined`.
**Key Concepts:** literal types, `void`, `never`.
**Interviewer Expectation:** Know common types and use-cases.
**Depth Expected:** Basic list and example.

Q: What is the `any` type? What is `unknown`?
**Expected Answer:** `any` opts out of checking; `unknown` requires narrowing before use and is safer.
**Key Concepts:** escape hatch vs safe alternative, narrowing.
**Interviewer Expectation:** Prefer `unknown` over `any` and show narrowing example.
**Depth Expected:** Practical.

### Variables and Types

Q: How do you declare variables in TypeScript?
**Expected Answer:** `let`, `const`, `var` with optional type annotations: `let x: number = 1;`.
**Key Concepts:** block vs function scope, `const` immutability of binding.
**Interviewer Expectation:** Use `const` by default.

Q: What is type inference?
**Expected Answer:** Compiler infers a variable's type from its initializer when no explicit annotation is provided.
**Key Concepts:** best practices, when to annotate.

Q: What are union and intersection types?
**Expected Answer:** Union `A | B` allows either type; intersection `A & B` requires both.
**Key Concepts:** discriminated unions, narrowing patterns.

Q: What is a literal type and enum?
**Expected Answer:** Literal types restrict to specific values; enums are named constants (numeric/string) available at runtime.
**Key Concepts:** use-cases and tradeoffs (enums runtime cost vs union literals compile-time).

### Functions

Q: How to define functions, optional/default/rest parameters, and overloads?
**Expected Answer:** Provide examples: `function f(a: number, b?: string): void {}`, default `b='x'`, rest `...args: any[]`, overloads via multiple signatures.
**Key Concepts:** parameter typing, signature order, implementation signature.

Q: What is function overloading in TypeScript?
**Expected Answer:** Multiple call signatures with a single implementation; used to represent different input shapes.

### Interfaces and Types

Q: What is an interface? Difference from `type`?
**Expected Answer:** Interface declares object shapes and can be extended/merged; `type` aliases are more general (unions/intersections) but no declaration merging.

Q: Readonly and optional properties?
**Expected Answer:** `readonly` prevents assignment; `?` marks optional property; discuss structural typing.

### Classes and OOP

Q: How classes work in TypeScript? Access modifiers, abstract classes?
**Expected Answer:** Support for `public`, `private`, `protected`, `readonly`, `abstract` classes and inheritance; constructor parameter properties shorthand exists.

### Generics

Q: What are generics and why use them?
**Expected Answer:** Generics make functions and classes reusable and type-safe, e.g., `function identity<T>(x: T): T`.

### Advanced Types & Narrowing

Q: Tuples, mapped types, conditional types, `keyof`, `typeof`?
**Expected Answer:** Tuples are fixed-length arrays; mapped/conditional types transform types; `keyof` gets property names, `typeof` captures type of values.

Q: Type narrowing strategies?
**Expected Answer:** Use `typeof`, `instanceof`, discriminated unions, and custom type guards.

### Configuration and Tooling

Q: What is `tsconfig.json` and important compiler options?
**Expected Answer:** `tsconfig.json` controls compiler options; `strict`, `noImplicitAny`, `strictNullChecks`, `target`, `module` are important.

Q: Declaration files (`.d.ts`)?
**Expected Answer:** Provide typings for JS libraries; used for interoperability.

---

If you want, I can also produce a printable cheat-sheet or expand specific questions into sample code answers.
