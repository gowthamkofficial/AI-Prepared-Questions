# Complete Frontend Interview Question Bank

## 📚 **Table of Contents**

### 1. [HTML Questions](./01-HTML-Questions.md) - 25 Questions
- HTML5 features
- Semantic tags
- Forms and validation
- Meta tags and SEO
- Storage APIs
- Multimedia elements

### 2. [CSS Questions](./02-CSS-Questions.md) - 30 Questions
- Box Model
- Flexbox & Grid
- Positioning
- Responsive design
- Animations & Transitions
- CSS variables

### 3. [TypeScript Questions](./03-TypeScript-Questions.md) - 45 Questions
- Basic types
- Interfaces & Types
- Generics
- Utility types
- Advanced types
- Decorators

### 4. [Angular Questions](./04-Angular-Questions.md) - 55 Questions
- Components & Modules
- Dependency Injection
- Routing & Guards
- RxJS & Observables
- Forms (Reactive & Template-driven)
- **Signals (v16+)** ⭐
- Change Detection
- Testing

### 5. [General Topics](./05-General-Topics.md) - 47 Questions
- **Webpack** (Entry, Output, Loaders, Plugins)
- **JIT vs AOT** Compilation
- **package.json Versioning** (^, ~, *)
- Build tools (Vite, esbuild)
- Performance optimization
- Module systems

### 6. [Coding Problems](./06-Coding-Problems.md) - 50 Problems
- **Array problems** (15 problems)
- **String problems** (15 problems)
- **Object problems** (15 problems)
- **Combined problems** (5 problems)

---

## 🎯 **Quick Reference Guide**

### **Most Important Topics for Interviews**

#### HTML (Top 10)
1. HTML5 new features
2. Semantic tags
3. `<script>` async vs defer
4. localStorage vs sessionStorage
5. Form validation
6. Meta tags
7. GET vs POST
8. Data attributes
9. Accessibility
10. SEO basics

#### CSS (Top 10)
1. Box Model & box-sizing
2. Flexbox
3. Grid
4. Position values
5. Specificity
6. Media queries
7. `em` vs `rem`
8. Pseudo-classes vs pseudo-elements
9. Transitions & animations
10. CSS variables

#### TypeScript (Top 10)
1. Basic types
2. Interface vs Type
3. Generics
4. Union & Intersection types
5. Utility types (Partial, Pick, Omit)
6. Type guards
7. Optional chaining
8. Readonly modifier
9. Enums
10. `any` vs `unknown`

#### Angular (Top 15)
1. Components & lifecycle hooks
2. Data binding (4 types)
3. Dependency Injection
4. Services
5. Routing & Guards
6. Directives (`*ngIf`, `*ngFor`)
7. Pipes
8. Reactive vs Template-driven forms
9. RxJS Observables
10. **Signals** (signal, computed, effect) ⭐
11. Change Detection (Default vs OnPush)
12. AOT vs JIT
13. Lazy loading
14. HttpClient & Interceptors
15. Testing (TestBed)

#### General Topics (Top 10)
1. **Webpack** (Loaders vs Plugins)
2. **JIT vs AOT** compilation
3. **^** vs **~** in package.json
4. Semantic versioning
5. Code splitting
6. Tree shaking
7. Lazy loading
8. Bundle optimization
9. Environment variables
10. npm vs yarn vs pnpm

---

## 🔥 **Angular Signals (v16+) - Deep Dive**

### What are Signals?
New reactive primitive for state management with automatic dependency tracking.

### Core Concepts
```typescript
import { signal, computed, effect } from '@angular/core';

// 1. Create signal
count = signal(0);

// 2. Computed (derived state)
doubleCount = computed(() => this.count() * 2);

// 3. Effect (side effects)
constructor() {
  effect(() => console.log('Count:', this.count()));
}

// 4. Update signal
increment() {
  this.count.update(v => v + 1);  // Based on current
  this.count.set(5);              // Set directly
}
```

### Signal Methods
- `signal(value)` - Create
- `signal()` - Read
- `set(value)` - Set new value
- `update(fn)` - Update based on current
- `mutate(fn)` - Mutate objects/arrays

### Signals vs Observables
| Feature | Signals | Observables |
|---------|---------|-------------|
| Type | Synchronous | Asynchronous |
| Complexity | Simple | Complex |
| Performance | Better | Good |
| Use Case | State | Async operations |

### Interop
```typescript
// Observable → Signal
data = toSignal(this.http.get('/api'));

// Signal → Observable
count$ = toObservable(this.count);
```

---

## 📦 **package.json Versioning**

### Semantic Versioning (SemVer)
`MAJOR.MINOR.PATCH` (e.g., `1.2.3`)

### Version Symbols
```json
{
  "dependencies": {
    "package1": "1.2.3",      // Exact
    "package2": "^1.2.3",     // ^: 1.x.x (recommended)
    "package3": "~1.2.3",     // ~: 1.2.x (conservative)
    "package4": "*"           // Any (avoid)
  }
}
```

### Comparison
| Symbol | Updates | Example | Range |
|--------|---------|---------|-------|
| `^` | Minor + Patch | `^1.2.3` | `1.2.3` to `1.x.x` |
| `~` | Patch only | `~1.2.3` | `1.2.3` to `1.2.x` |
| None | Exact | `1.2.3` | `1.2.3` only |

---

## ⚙️ **Webpack Essentials**

### Core Concepts
1. **Entry**: Starting point
2. **Output**: Bundle destination
3. **Loaders**: Transform files
4. **Plugins**: Broader tasks
5. **Mode**: development/production

### Loaders vs Plugins
- **Loaders**: File transformations (ts-loader, css-loader)
- **Plugins**: Bundle-level tasks (HtmlWebpackPlugin, minification)

### Common Loaders
- `babel-loader` - ES6+ transpilation
- `ts-loader` - TypeScript
- `css-loader` - Import CSS
- `sass-loader` - Compile SCSS

### Common Plugins
- `HtmlWebpackPlugin` - Generate HTML
- `MiniCssExtractPlugin` - Extract CSS
- `TerserPlugin` - Minify JS

---

## 🚀 **JIT vs AOT Compilation**

### JIT (Just-in-Time)
- Compiles in browser at runtime
- Faster development builds
- Larger bundle (includes compiler)
- Use in development

### AOT (Ahead-of-Time)
- Compiles during build
- Faster rendering
- Smaller bundle
- Better security
- Use in production

### Comparison
| Feature | JIT | AOT |
|---------|-----|-----|
| Compilation | Runtime | Build time |
| Bundle Size | Larger | Smaller |
| Performance | Slower | Faster |
| Errors | Runtime | Build time |

---

## 💡 **Coding Problem Patterns**

### Array Patterns
- Two pointers
- Sliding window
- Hash map for frequency
- Set for uniqueness
- Reduce for accumulation

### String Patterns
- Split/Join for manipulation
- RegEx for validation
- Character frequency map
- Two pointers for palindrome

### Object Patterns
- Object.entries() for iteration
- Reduce for transformation
- Spread operator for merging
- Destructuring for extraction

---

## 📝 **Interview Tips**

### How to Answer
1. **Understand** - Clarify the question
2. **Explain** - Define the concept
3. **Example** - Show code
4. **Compare** - Alternatives
5. **Best Practice** - Recommendations

### Common Follow-ups
- "Can you show an example?"
- "When would you use this?"
- "What are the alternatives?"
- "How did you use this in your project?"

### What Interviewers Look For
✅ Clear explanations
✅ Practical examples
✅ Understanding of trade-offs
✅ Best practices
✅ Real-world experience

---

## 🎓 **Study Plan**

### Week 1: Fundamentals
- HTML (25 questions)
- CSS (30 questions)
- Basic coding problems

### Week 2: TypeScript & Angular Basics
- TypeScript (45 questions)
- Angular components, directives, pipes
- Intermediate coding problems

### Week 3: Angular Advanced
- Signals, RxJS, Forms
- Routing, Guards, Interceptors
- Change Detection, Testing

### Week 4: General Topics & Practice
- Webpack, JIT/AOT, Versioning
- Advanced coding problems
- Mock interviews

---

## 📊 **Difficulty Levels**

### Beginner (0-2 years)
- HTML, CSS basics
- TypeScript fundamentals
- Angular components, directives
- Basic array/string problems

### Intermediate (2-4 years)
- CSS Flexbox/Grid
- TypeScript generics, utility types
- Angular services, routing, forms
- RxJS basics
- Intermediate coding problems

### Advanced (4+ years)
- Angular Signals
- Advanced RxJS
- Change Detection optimization
- Webpack configuration
- Complex coding problems
- Architecture patterns

---

## 🔗 **Quick Links**

- [HTML Questions](./01-HTML-Questions.md)
- [CSS Questions](./02-CSS-Questions.md)
- [TypeScript Questions](./03-TypeScript-Questions.md)
- [Angular Questions](./04-Angular-Questions.md)
- [General Topics](./05-General-Topics.md)
- [Coding Problems](./06-Coding-Problems.md)

---

## ✅ **Last-Minute Checklist**

### Day Before Interview
- [ ] Review HTML5 features
- [ ] Practice Flexbox/Grid
- [ ] Understand Signals
- [ ] Know ^ vs ~ in package.json
- [ ] Practice 5 array problems
- [ ] Practice 5 string problems
- [ ] Review Angular lifecycle hooks
- [ ] Understand JIT vs AOT

### 1 Hour Before
- [ ] Review top 10 from each section
- [ ] Practice explaining concepts aloud
- [ ] Prepare project examples
- [ ] Relax and stay confident

---

**Total Questions: 200+**
**Total Coding Problems: 50**

Good luck with your interview! 🚀
