# ANGULAR FRAMEWORK - COMPLETE INTERVIEW GUIDE (ALL 144 QUESTIONS)

## Complete Table of Contents

### Beginner Level (32 Questions)
1. Angular Basics (11 Q)
2. Components and Templates (6 Q)  
3. Project Configuration Files (15 Q)

### Intermediate Level (31 Questions)
4. Directives and Pipes (6 Q)
5. Services and Dependency Injection (5 Q)
6. Routing (5 Q)
7. Forms (5 Q)
8. State Management (5 Q)

### Advanced Level (21 Questions)
9. Lifecycle Hooks (4 Q)
10. Change Detection (4 Q)
11. RxJS and Observables (8 Q)
12. Modules and Architecture (5 Q)

### Scenario & Advanced (60 Questions)
13. Frequently Asked Questions (12 Q)
14. Micro Frontend (10 Q)
15. Advanced/Missed Questions (28 Q)
16. Scenario Questions & Real-world Implementations (10 Q)

---

# SECTION 1: ANGULAR BASICS (11 QUESTIONS)

## 1. What is Angular?

### Answer:
**Angular** is a **TypeScript-based open-source** front-end framework developed and maintained by **Google**. It's used for building **Single Page Applications (SPAs)** with a **complete solution** including routing, forms, HTTP client, testing, and more.

**Key Characteristics:**
- Uses **component-based architecture** for modular, reusable code
- Supports **two-way data binding** between component and template
- Built on **RxJS** for reactive programming and handling async operations
- Provides **dependency injection** for loose coupling and testability
- Supports **AOT compilation** and **tree-shaking** for optimized bundles
- Current versions called Angular (2+), distinct from AngularJS (1.x)

### Theoretical Keywords:
**TypeScript**, **SPA**, **Component-based**, **Google**, **Two-way binding**, **Dependency Injection**, **AOT Compilation**, **Reactive Programming**, **Modules**

### Example:
```typescript
// app.component.ts - Basic Angular Component
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ title }}</h1>
    <button (click)="onClick()">Click Me</button>
  `,
  styles: [`h1 { color: blue; }`]
})
export class AppComponent {
  title = 'My Angular App';
  
  onClick() {
    console.log('Button clicked');
  }
}
```

---

## 2. Difference between AngularJS and Angular

### Answer:

| Feature | AngularJS (1.x) | Angular (2+) |
|---------|-----------------|------------|
| **Language** | JavaScript | TypeScript |
| **Architecture** | MVC-based | Component-based |
| **Mobile Support** | Not built for mobile | Mobile-first design |
| **Data Binding** | Two-way with $scope | One-way (default) + two-way option |
| **Performance** | Slower (dirty checking) | Much faster (change detection strategy) |
| **Dependency Injection** | Basic | Hierarchical DI |
| **CLI Support** | None | Angular CLI (full tooling) |
| **Routing** | ngRoute (basic) | @angular/router (advanced) |
| **Testing** | Karma/Jasmine | Karma/Jasmine/Cypress |
| **Compilation** | JIT only | AOT + JIT |
| **Maintenance** | Ended (2021) | Active support |

### Theoretical Keywords:
**TypeScript vs JavaScript**, **MVC vs Components**, **Dirty Checking vs Change Detection**, **Performance**, **Mobile-first**, **CLI Tooling**, **Modern Features**

---

## 3. What are components?

### Answer:
**Components** are the **building blocks** of Angular applications. Each component has its own **scope**, **logic**, and **styling**. They consist of:
- **Class** (.ts): Component logic and data
- **Template** (.html): View/UI
- **Styles** (.css/.scss): Component-specific styling

**Key Features:**
- Components are **reusable** and can be nested hierarchically
- Each component has its own **lifecycle** managed by Angular
- Components **encapsulate** view, logic, and styles together
- Use `@Component` decorator for definition

### Theoretical Keywords:
**Building blocks**, **@Component decorator**, **View encapsulation**, **Reusable**, **Composable**, **Lifecycle**, **Encapsulation**

### Example:
```typescript
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.scss']
})
export class UserCardComponent {
  @Input() user = { name: 'John', email: 'john@example.com', age: 25 };
  @Output() userClicked = new EventEmitter<any>();
  
  onCardClick() {
    this.userClicked.emit(this.user);
  }
}
```

---

## 4. What is a module?

### Answer:
A **Module** is a **container** that groups related components, services, pipes, and directives. Every Angular app has **at least one module** called **AppModule** (root module). Modules help:
- **Organize code** into cohesive blocks of functionality
- Control **what components/directives are available**
- Manage **dependencies** via imports
- Enable **lazy loading** for performance optimization

### Theoretical Keywords:
**@NgModule**, **Container**, **Organization**, **Declarations**, **Imports**, **Exports**, **Providers**, **Lazy Loading**, **Feature Modules**, **Root Module**

### Example:
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { UserModule } from './user/user.module';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, UserModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

---

## 5. What is a service?

### Answer:
A **Service** is a class that contains **business logic** and is used for **code reusability** across components. Services:
- Are decorated with **@Injectable** decorator
- Are **provided** via **Dependency Injection** (not instantiated manually)
- Can be **singleton** (app-wide) or **scoped** to module/component
- Enable **separation of concerns** between components and logic

### Theoretical Keywords:
**@Injectable**, **Business Logic**, **Dependency Injection**, **Singleton**, **Reusability**, **Separation of Concerns**, **Stateless**

### Example:
```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'  // Singleton service available app-wide
})
export class UserService {
  private apiUrl = '/api/users';
  
  constructor(private http: HttpClient) {}
  
  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
  createUser(user: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, user);
  }
}
```

---

## 6. What is a directive?

### Answer:
**Directives** are classes that **add behavior** to DOM elements or component behavior. There are **three types**:
1. **Component Directives**: Components (have templates)
2. **Structural Directives**: Change DOM layout (*ngIf, *ngFor, *ngSwitch)
3. **Attribute Directives**: Change appearance/behavior (ngClass, ngStyle)

### Theoretical Keywords:
**DOM Manipulation**, **Structural Directives**, **Attribute Directives**, **@Directive**, **Custom Directives**, **Behavior Modification**, **Declarative**

### Example:
```typescript
// Custom attribute directive - highlights on hover
import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  constructor(private el: ElementRef) {}
  
  @HostListener('mouseenter')
  onMouseEnter() {
    this.el.nativeElement.style.backgroundColor = 'yellow';
  }
  
  @HostListener('mouseleave')
  onMouseLeave() {
    this.el.nativeElement.style.backgroundColor = '';
  }
}
```

---

## 7. What is data binding?

### Answer:
**Data binding** is the **synchronization** between component and view. It allows **automatic update** of view when data changes and vice versa. Angular supports **four types** of data binding that makes code more **declarative** and **maintainable**.

### Theoretical Keywords:
**Synchronization**, **Component-View connection**, **Automatic updates**, **Declarative UI**, **One-way**, **Two-way binding**

---

## 8. Types of data binding in Angular

### Answer:
Angular has **four types** of data binding:

| Type | Syntax | Direction | Description |
|------|--------|-----------|-------------|
| **Interpolation** | `{{ value }}` | Component → Template | Display component data in template |
| **Property Binding** | `[property]="value"` | Component → Template | Bind to element property |
| **Event Binding** | `(event)="handler()"` | Template → Component | Listen to DOM events |
| **Two-way Binding** | `[(ngModel)]="value"` | Both directions | Sync data bidirectionally |

### Theoretical Keywords:
**Interpolation**, **Property Binding**, **Event Binding**, **Two-way Binding**, **ngModel**, **Data Flow**, **Reactive**, **Template Syntax**

### Example:
```html
<!-- Interpolation: Component → Template -->
<h1>{{ title }}</h1>

<!-- Property Binding: Component → Template -->
<img [src]="imageUrl">
<button [disabled]="isDisabled">Click</button>

<!-- Event Binding: Template → Component -->
<button (click)="onClick()">Click Me</button>

<!-- Two-way Binding: Both Directions -->
<input [(ngModel)]="username">
```

---

## 9. What is interpolation?

### Answer:
**Interpolation** displays component data in the template using **double curly braces** `{{ }}` syntax. It:
- Evaluates the **expression** and converts to **string** for display
- Is **one-way binding**: Component → Template
- **Automatically updates** when component data changes
- Cannot use assignments, new, chaining, increment/decrement

### Theoretical Keywords:
**Double Curly Braces**, **Expression Evaluation**, **One-way Binding**, **String Conversion**, **Automatic Updates**, **Template Syntax**

### Example:
```typescript
@Component({
  template: `
    <!-- Simple interpolation -->
    <h1>{{ title }}</h1>
    
    <!-- Expression -->
    <p>Total: {{ price * quantity }}</p>
    
    <!-- Method call -->
    <p>Greeting: {{ getGreeting() }}</p>
    
    <!-- With pipe -->
    <p>Date: {{ today | date:'fullDate' }}</p>
  `
})
export class DemoComponent {
  title = 'Welcome';
  price = 99.99;
  quantity = 2;
  today = new Date();
  
  getGreeting(): string {
    return `Hello!`;
  }
}
```

---

## 10. What are templates?

### Answer:
A **Template** defines the **view** of a component. It's written in **HTML** with **Angular-specific syntax**. Templates:
- Can be **inline** (template property) or **external** (templateUrl)
- Contain **HTML elements**, **data binding**, **directives**, **pipes**
- Are **compiled** by Angular to JavaScript
- Support **type-checking** in strict mode

### Theoretical Keywords:
**View Definition**, **HTML + Angular Syntax**, **Inline vs External**, **Data Binding**, **Directives**, **Pipes**, **Compilation**, **Type Safety**

### Example:
```typescript
// Inline template
@Component({
  selector: 'app-inline',
  template: `
    <div class="container">
      <h1>{{ title }}</h1>
      <ul>
        <li *ngFor="let item of items">{{ item }}</li>
      </ul>
    </div>
  `
})
export class InlineComponent { }

// External template
@Component({
  selector: 'app-external',
  templateUrl: './external.component.html',
  styleUrls: ['./external.component.css']
})
export class ExternalComponent { }
```

---

## 11. What is the purpose of `@NgModule`?

### Answer:
**@NgModule** is a **decorator** that defines an **Angular module**. It provides **metadata** for Angular compiler about module configuration:
- **declarations**: Components, directives, pipes **owned** by this module
- **imports**: Other modules whose exports this module **needs**
- **exports**: Components, directives, pipes **available** to other modules
- **providers**: Services for **dependency injection**
- **bootstrap**: **Root component** to bootstrap (AppModule only)

### Theoretical Keywords:
**@NgModule Decorator**, **Module Metadata**, **Declarations**, **Imports**, **Exports**, **Providers**, **Bootstrap**, **Compilation Context**, **Module Configuration**

### Example:
```typescript
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [UserListComponent, UserDetailComponent],
  imports: [CommonModule, FormsModule],
  exports: [UserListComponent],
  providers: [UserService]
})
export class UserModule { }
```

---

# SECTION 2: COMPONENTS AND TEMPLATES (6 QUESTIONS)

## 12. What is a component in Angular?

### Answer:
A **component** is a **TypeScript class** decorated with `@Component` that **controls a patch of screen** called a view. It consists of:
1. **Class** (.ts): Logic and data
2. **Template** (.html): View/UI
3. **Styles** (.css/.scss): Component-specific styles

Components are **reusable**, **composable**, and have their own **lifecycle**.

### Theoretical Keywords:
**@Component decorator**, **View controller**, **Class + Template + Styles**, **Encapsulation**, **Reusability**, **Lifecycle hooks**

### Example:
```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent implements OnInit {
  product: any = { id: 1, name: 'Laptop', price: 999.99 };
  isExpanded = false;
  
  ngOnInit(): void {
    console.log('Component initialized');
  }
  
  toggleExpand(): void {
    this.isExpanded = !this.isExpanded;
  }
}
```

---

## 13. Difference between component and directive

### Answer:

| Feature | Component | Directive |
|---------|-----------|-----------|
| **Decorator** | `@Component` | `@Directive` |
| **Template** | Required (has view) | No template |
| **Purpose** | Build UI blocks | Modify existing elements |
| **Selector** | Element selector | Attribute/class selector |
| **Usage** | `<app-header>` | `<div appHighlight>` |
| **Shadow DOM** | Optional encapsulation | No encapsulation |

### Theoretical Keywords:
**Template presence**, **UI building vs DOM modification**, **Element vs Attribute selector**, **View encapsulation**

### Example:
```typescript
// COMPONENT - Has its own view
@Component({
  selector: 'app-button',
  template: `<button>Click</button>`
})
export class ButtonComponent { }

// DIRECTIVE - Modifies existing elements
@Directive({
  selector: '[appTooltip]'
})
export class TooltipDirective { }

// Usage:
// <app-button></app-button>  <!-- Component -->
// <div appTooltip>Help</div>  <!-- Directive -->
```

---

## 14. How to pass data from parent to child component?

### Answer:
Use **`@Input()` decorator** in child component. Parent passes data through **property binding**. Data flows **one-way**: Parent → Child. You can use **setter** or **ngOnChanges** to react to input changes.

### Theoretical Keywords:
**@Input decorator**, **Property binding**, **One-way data flow**, **Parent-child communication**, **Input setter**, **ngOnChanges**

### Example:
```typescript
// child.component.ts
@Component({
  selector: 'app-child',
  template: `<div>{{ title }}: {{ user?.name }}</div>`
})
export class ChildComponent implements OnChanges {
  @Input() title: string = '';
  @Input() user: any = null;
  
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['user']) {
      console.log('User changed');
    }
  }
}

// parent.component.ts
@Component({
  template: `<app-child [title]="pageTitle" [user]="currentUser"></app-child>`
})
export class ParentComponent {
  pageTitle = 'Welcome';
  currentUser = { name: 'John' };
}
```

---

## 15. How to pass data from child to parent component?

### Answer:
Use **`@Output()` decorator** with **EventEmitter**. Child **emits events** that parent listens to. Data flows: **Child → Parent**. Use **event binding** to receive data.

### Theoretical Keywords:
**@Output decorator**, **EventEmitter**, **Event binding**, **Child-parent communication**, **Custom events**, **emit()**

### Example:
```typescript
// child.component.ts
@Component({
  selector: 'app-child',
  template: `<button (click)="sendData()">Send</button>`
})
export class ChildComponent {
  @Output() dataSubmit = new EventEmitter<string>();
  inputValue = 'test';
  
  sendData(): void {
    this.dataSubmit.emit(this.inputValue);
  }
}

// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <p>Received: {{ receivedData }}</p>
    <app-child (dataSubmit)="onDataReceived($event)"></app-child>
  `
})
export class ParentComponent {
  receivedData = '';
  
  onDataReceived(data: string): void {
    this.receivedData = data;
  }
}
```

---

## 16. What are input and output decorators?

### Answer:
- **@Input()**: Makes property a **binding target** from parent components
- **@Output()**: Makes property an **event emitter** that parent can listen to
- Together they form the **component API** for communication

### Example:
```typescript
@Component({
  selector: 'app-custom-input',
  template: `
    <input [(ngModel)]="internalValue" (change)="onValueChange()">
  `
})
export class CustomInputComponent {
  @Input() value: string = '';
  @Output() valueChange = new EventEmitter<string>();
  
  internalValue: string = '';
  
  ngOnInit() {
    this.internalValue = this.value;
  }
  
  onValueChange(): void {
    this.valueChange.emit(this.internalValue);
  }
}
```

---

## 17. What is `@ViewChild` and `@ContentChild`?

### Answer:
- **@ViewChild**: Access **child component or element** from parent's **template**
- **@ContentChild**: Access **projected content** from child component
- **@ViewChildren/@ContentChildren**: Query **multiple** items
- Available **after view initialization** (use AfterViewInit hook)

### Theoretical Keywords:
**Child Component Access**, **Template Query**, **Projected Content**, **@ViewChild**, **@ContentChild**, **AfterViewInit**, **ElementRef**, **Component Reference**

### Example:
```typescript
// Child Component
@Component({
  selector: 'app-counter',
  template: `<p>Count: {{ count }}</p>`
})
export class CounterComponent {
  count = 0;
  
  increment() {
    this.count++;
  }
}

// Parent Component
@Component({
  selector: 'app-parent',
  template: `
    <button (click)="resetCounter()">Reset</button>
    <app-counter #counter></app-counter>
  `
})
export class ParentComponent implements AfterViewInit {
  @ViewChild('counter') counterComponent!: CounterComponent;
  
  ngAfterViewInit() {
    console.log(this.counterComponent.count);
  }
  
  resetCounter() {
    this.counterComponent.count = 0;
  }
}
```

---

# SECTION 3: PROJECT CONFIGURATION FILES (15 QUESTIONS)

## 18-22. Angular.json

### 18. What is `angular.json`?

**Answer:** The **workspace configuration** file for Angular CLI. Contains all **project settings**, build configurations, and CLI defaults. Located at **root** of workspace. Defines how Angular CLI commands work (build, serve, test, lint).

### 19. What is its role in an Angular project?

**Answer:** Serves several critical purposes:
- **Build configuration**: Output paths, optimization, bundling
- **Development server**: Port, proxy, SSL settings
- **Asset management**: Static files, styles, scripts
- **Environment configuration**: Development vs production
- **Testing configuration**: Karma, test files
- **Multiple project management**: Monorepo support

### 20. How does `angular.json` manage build and serve configurations?

**Answer:** Uses **architect targets** for operations:
- **build**: Compiles application
- **serve**: Runs development server
- Each target has **options** (defaults) and **configurations** (overrides)

### 21. How do you configure environments in `angular.json`?

**Answer:** Use **fileReplacements** in build configurations to swap environment files during build.

### 22. How do you add assets and styles globally using `angular.json`?

**Answer:** Add to build options:
- **assets**: Static files copied to output
- **styles**: Global CSS/SCSS files
- **scripts**: Global JavaScript files

---

## 23-28. Package.json

### 23. What is `package.json`?

**Answer:** The **Node.js project manifest** file. Defines project **metadata** and **dependencies**. Required for **npm/yarn** package management.

### 24. Why is `package.json` important?

**Answer:** 
- **Dependency tracking**: Lists all required packages
- **Version management**: Controls package versions
- **Script automation**: Custom commands for development

### 25. How does `package.json` manage dependencies?

**Answer:** Lists both **runtime dependencies** (needed for app to run) and **devDependencies** (only for development).

### 26. What are `devDependencies` and `dependencies`?

**Answer:**
- **dependencies**: Packages needed at **runtime**
- **devDependencies**: Packages needed only during **development/building**

### 27. What are scripts in `package.json` and how are they used?

**Answer:** Scripts are commands defined in package.json that automate tasks. Run via `npm run scriptName`.

### 28. How to run custom npm scripts defined in `package.json`?

**Answer:** Use `npm run scriptName` where scriptName is a key in the scripts object.

---

## 29-33. tsconfig.json

### 29. What is `tsconfig.json`?

**Answer:** TypeScript **configuration file** that specifies compiler options and file inclusion rules.

### 30. What compiler options are most important?

**Answer:** Options like target, module, strict, declaration, sourceMap, and declaration paths.

### 31. What does strict mode do?

**Answer:** Enables multiple strict type checks including noImplicitAny, strictNullChecks, and strictFunctionTypes.

### 32. Difference between `target` and `module`?

**Answer:**
- **target**: ECMAScript version to compile TO (e.g., ES2020)
- **module**: Module system format (e.g., ESNext, CommonJS, AMD)

### 33. How to enable `strictNullChecks` and `noImplicitAny`?

**Answer:** Set in tsconfig.json:
```json
{
  "compilerOptions": {
    "strictNullChecks": true,
    "noImplicitAny": true
  }
}
```

---

## 34-38. Webpack

### 34. What is Webpack?

**Answer:** **Module bundler** that combines JavaScript files, CSS, images, and other assets into optimized bundles.

### 35. How does Angular use Webpack internally?

**Answer:** Angular CLI uses Webpack internally for bundling. Configured automatically, can be customized via angular.json or schematic builders.

### 36. What is the role of loaders and plugins in Webpack?

**Answer:**
- **Loaders**: Transform files (e.g., sass-loader converts SCSS to CSS)
- **Plugins**: Perform bundle optimization and asset management

### 37. Difference between development and production build using Webpack?

**Answer:**
- **Development**: Faster builds, source maps, unminified for debugging
- **Production**: Optimized, minified, tree-shaken, no source maps

### 38. How can you customize Webpack in Angular?

**Answer:** Via @angular-builders, custom webpack.config.js, or angular.json builders configuration.

---

# SECTION 4: DIRECTIVES AND PIPES (6 QUESTIONS)

## 39. What are structural directives?

### Answer:
**Structural Directives** **change the DOM structure**. Preceded by **asterisk** (*) in templates. Can **add**, **remove**, or **manipulate** DOM elements.

Common: `*ngIf`, `*ngFor`, `*ngSwitch`

### Theoretical Keywords:
**DOM Manipulation**, **Conditional Rendering**, **Looping**, **Asterisk Syntax**, **ng-template**, **DOM Removal**, **Structural**

### Example:
```html
<!-- ngIf: Conditional rendering -->
<div *ngIf="isVisible">Visible content</div>

<!-- ngFor: Looping -->
<li *ngFor="let item of items; let i = index">
  {{ i + 1 }}: {{ item.name }}
</li>

<!-- ngSwitch: Multi-way conditional -->
<div [ngSwitch]="status">
  <div *ngSwitchCase="'active'">Active</div>
  <div *ngSwitchDefault>Unknown</div>
</div>
```

---

## 40. What are attribute directives?

### Answer:
**Attribute Directives** **modify appearance or behavior** of elements. Do **not change DOM structure**. Look like **regular HTML attributes**.

Common: `[ngClass]`, `[ngStyle]`, `[(ngModel)]`

### Theoretical Keywords:
**DOM Modification**, **Appearance/Behavior**, **ngClass**, **ngStyle**, **Attribute Syntax**, **CSS Classes**, **Styles**, **Custom Behavior**

### Example:
```html
<!-- ngClass: Conditional CSS classes -->
<div [ngClass]="{highlight: isHighlighted, bold: isBold}">
  Dynamic classes
</div>

<!-- ngStyle: Conditional inline styles -->
<div [ngStyle]="{'color': textColor, 'font-size.px': fontSize}">
  Dynamic styles
</div>
```

---

## 41. Common built-in directives (`ngIf`, `ngFor`, `ngSwitch`)

### Answer:
```html
<!-- ngIf with else -->
<div *ngIf="user; else notFound">
  Welcome, {{ user.name }}!
</div>
<ng-template #notFound>
  User not found
</ng-template>

<!-- ngFor with trackBy -->
<li *ngFor="let item of items; trackBy: trackById">
  {{ item.name }}
</li>

<!-- ngSwitch -->
<div [ngSwitch]="status">
  <span *ngSwitchCase="'active'">Active</span>
  <span *ngSwitchCase="'inactive'">Inactive</span>
  <span *ngSwitchDefault>Unknown</span>
</div>
```

---

## 42. What is a pipe?

### Answer:
**Pipes** **transform data** for display in templates. Use the **pipe operator** `|`. Can be **chained** together. **Do not modify** actual data (pure).

### Theoretical Keywords:
**Data Transformation**, **Template Pipes**, **Pure Transformation**, **Chainable**, **Built-in Pipes**, **Custom Pipes**, **Pipe Parameters**, **PipeTransform**

### Example:
```html
<!-- Built-in Pipes -->
<p>{{ name | uppercase }}</p>
<p>{{ price | currency:'USD' }}</p>
<p>{{ today | date:'fullDate' }}</p>
<p>{{ value | number:'1.2-2' }}</p>

<!-- Chaining Pipes -->
<p>{{ name | uppercase | slice:0:10 }}</p>
```

---

## 43. Difference between built-in and custom pipe

### Answer:
- **Built-in**: Provided by Angular (uppercase, lowercase, currency, date, etc.)
- **Custom**: Create using `@Pipe` decorator and `PipeTransform` interface for specific needs

---

## 44. How to create a custom pipe

### Answer:
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50): string {
    if (!value) return '';
    return value.length > limit ? value.slice(0, limit) + '...' : value;
  }
}

// Usage: {{ longText | truncate:30 }}
```

---

# SECTION 5: SERVICES AND DEPENDENCY INJECTION (5 QUESTIONS)

## 45. What is a Service in Angular?

### Answer:
A **Service** is a class that encapsulates **reusable business logic**. Used for **data sharing**, **API calls**, **state management**. Follows **Single Responsibility Principle**. Provided via **Dependency Injection**.

### Theoretical Keywords:
**Reusable logic**, **Separation of concerns**, **Singleton**, **API calls**, **State management**, **Injectable**

### Example:
```typescript
@Injectable({
  providedIn: 'root'  // Singleton across the app
})
export class UserService {
  private apiUrl = 'https://api.example.com/users';
  
  constructor(private http: HttpClient) { }
  
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }
}
```

---

## 46. What is Dependency Injection (DI)?

### Answer:
**Dependency Injection** is a **design pattern** where dependencies are **provided externally**. Angular's DI system **creates and manages** service instances. Promotes **loose coupling** and **testability**.

### Theoretical Keywords:
**Design pattern**, **Inversion of Control**, **Loose coupling**, **Testability**, **Injector**, **Provider**

---

## 47. What is `providedIn: 'root'`?

### Answer:
**providedIn: 'root'** makes service a **singleton** at root level. Service is **tree-shakable** if not used. No need to add to module's providers array. **Recommended** approach.

### Theoretical Keywords:
**Root singleton**, **Tree-shakable**, **Automatic registration**, **Application-wide**, **Lazy loading friendly**

---

## 48. Difference between service provided in root and module

### Answer:
- **providedIn: 'root'**: Single instance across entire app, tree-shakable
- **Module providers**: Single instance for module, multiple instances if module loaded multiple times

---

## 49. Singleton services vs multiple instances

### Answer:
- **Singleton**: One instance shared across entire app (providedIn: 'root')
- **Multiple instances**: New instance created per component (component providers array)

---

# SECTION 6: ROUTING (5 QUESTIONS)

## 50. What is Angular Router?

### Answer:
**Angular Router** handles **navigation** between components. Enables **single-page application** behavior with URL-based navigation. Maintains **browser history** and supports **back/forward** buttons.

### Theoretical Keywords:
**Navigation**, **URL Routing**, **Router Configuration**, **Route Matching**, **Lazy Loading**, **Route Guards**, **Resolvers**, **History Management**

### Example:
```typescript
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'users/:id', component: UserDetailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

---

## 51. Difference between `routerLink` and `href`

### Answer:
- **routerLink**: Angular router navigation, no page refresh, supports params
- **href**: Standard HTML link, full page reload

```html
<!-- routerLink - SPA navigation -->
<a routerLink="/users" routerLinkActive="active">Users</a>

<!-- href - Full page reload -->
<a href="/users">Users</a>
```

---

## 52. How to pass parameters in routes?

### Answer:
```typescript
// Path parameters
const routes: Routes = [
  { path: 'user/:id', component: UserComponent }
];

// Passing: /user/123
<a [routerLink]="['/user', userId]">User</a>

// Query parameters
<a [routerLink]="['/users']" [queryParams]="{page: 1}">Users</a>
// URL: /users?page=1
```

---

## 53. What are route guards?

### Answer:
**Route Guards** **protect routes** from unauthorized access. Can **allow**, **deny**, or **redirect** navigation.

Types: `canActivate`, `canDeactivate`, `canActivateChild`, `canMatch`, `resolve`

---

## 54. What is lazy loading?

### Answer:
**Lazy loading** loads modules **on demand**, reducing **initial bundle size**. Uses **loadChildren** with dynamic import.

```typescript
{
  path: 'admin',
  loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
}
```

---

# SECTION 7: FORMS (5 QUESTIONS)

## 55. Difference between template-driven and reactive forms

### Answer:

| Feature | Template-driven | Reactive |
|---------|-----------------|----------|
| **Module** | FormsModule | ReactiveFormsModule |
| **Form model** | Directives create | Explicit in class |
| **Data binding** | Two-way (ngModel) | Form control binding |
| **Validation** | Directives/HTML5 | Validators in class |
| **Testability** | Harder (async) | Easier (sync) |
| **Flexibility** | Less | More |

---

## 56. How to create a reactive form?

### Answer:
```typescript
@Component({...})
export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  
  constructor(private fb: FormBuilder) {}
  
  ngOnInit() {
    this.registrationForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
}
```

---

## 57. Form validation (built-in and custom)

### Answer:
```typescript
// Built-in validators
Validators.required
Validators.minLength(5)
Validators.pattern(/^\d+$/)
Validators.email

// Custom validator
function noSpaceValidator(control: FormControl): ValidationErrors | null {
  return control.value && control.value.includes(' ') 
    ? { hasSpace: true } 
    : null;
}
```

---

## 58. `FormControl`, `FormGroup`, `FormBuilder`

### Answer:
```typescript
// FormControl - Single field
const nameControl = new FormControl('');

// FormGroup - Collection of controls
const form = new FormGroup({
  name: new FormControl(''),
  email: new FormControl('')
});

// FormBuilder - Shortcut
this.fb.group({
  name: ['', Validators.required],
  email: ['', Validators.email]
});
```

---

## 59. How to dynamically add form controls?

### Answer:
```typescript
addPhone(): void {
  (this.registrationForm.get('phones') as FormArray)
    .push(new FormControl(''));
}
```

---

# SECTION 8: STATE MANAGEMENT (5 QUESTIONS)

## 60. What is state management in Angular?

### Answer:
**State management** centralizes application state, making it **predictable** and **debuggable**. Tools: **NgRx**, **Akita**, **NGXS**.

---

## 61. Difference between local component state and global application state

### Answer:
- **Local**: Managed within component, not shared
- **Global**: Managed in store, shared across components

---

## 62. What is RxJS and how is it used in Angular?

### Answer:
**RxJS** is a library for **reactive programming** using **Observables**. Angular uses it for **async data**, **event handling**, **HTTP**.

---

## 63. What is NgRx?

### Answer:
**NgRx** is a **state management library** based on **Redux** and **RxJS**. Provides: Store, Actions, Reducers, Effects, Selectors.

---

## 64-69. Detailed NgRx Coverage

See detailed file for comprehensive NgRx patterns (Actions, Reducers, Selectors, Effects, Debugging).

---

# SECTION 9: LIFECYCLE HOOKS (4 QUESTIONS)

## 70. What are lifecycle hooks?

### Answer:
**Lifecycle hooks** are methods that Angular calls at specific moments. Allow you to **tap into** component lifecycle from **creation** to **destruction**.

### Example:
```typescript
export class LifecycleComponent implements OnInit, OnDestroy {
  ngOnInit(): void {
    // Initialize component
  }
  
  ngOnDestroy(): void {
    // Cleanup
  }
}
```

---

## 71. Difference between `ngOnInit` and constructor

### Answer:

| Feature | Constructor | ngOnInit |
|---------|-------------|----------|
| **Purpose** | Dependency Injection | Initialization logic |
| **@Input** | Not available | Available |
| **Called by** | JavaScript engine | Angular |

---

## 72. `ngOnChanges`, `ngDoCheck`, `ngAfterViewInit`, `ngAfterContentInit`

### Answer:
- **ngOnChanges**: When @Input changes
- **ngDoCheck**: Every change detection cycle
- **ngAfterContentInit**: After projected content initialized
- **ngAfterViewInit**: After view initialized

---

## 73. When to use each lifecycle hook

### Answer:
- **ngOnInit**: Initialize component, fetch data
- **ngOnDestroy**: Cleanup, unsubscribe
- **ngOnChanges**: React to input changes
- **ngAfterViewInit**: Access @ViewChild
- **ngAfterContentInit**: Access @ContentChild

---

# SECTION 10: CHANGE DETECTION (4 QUESTIONS)

## 74. How change detection works in Angular?

### Answer:
**Change Detection** is Angular's mechanism to **sync model and view**. Detects when data changes and **updates DOM**. Triggered by events, HTTP, timers, Promises.

---

## 75. What is `zone.js`?

### Answer:
**Zone.js** patches async APIs to notify Angular. Triggers change detection after async operations. Can be bypassed for performance.

---

## 76. Difference between default and `OnPush` change detection strategy

### Answer:
- **Default**: Check all components on every cycle
- **OnPush**: Check only when @Input changes or events fire

---

## 77. How to optimize change detection

### Answer:
- Use **OnPush** strategy
- Implement **trackBy** in *ngFor
- Use **async pipe**
- Avoid mutations, use new references
- Use **ChangeDetectorRef.markForCheck()** manually when needed

---

# SECTION 11: RXJS AND OBSERVABLES (8 QUESTIONS)

## 78. What is RxJS?

### Answer:
**RxJS** is a library for **reactive programming** using **Observables**. Provides **operators** to transform, filter, combine data streams. Core part of Angular.

---

## 79. What is an Observable?

### Answer:
**Observable** is a **stream of values** over time. Can emit **multiple values** (unlike Promise). **Lazy**: Doesn't execute until subscribed. Supports **cancellation**.

---

## 80. Difference between Observable and Promise

### Answer:

| Feature | Observable | Promise |
|---------|------------|---------|
| **Values** | Multiple | Single |
| **Execution** | Lazy | Eager |
| **Cancellation** | Yes | No |
| **Operators** | Many | Limited |

---

## 81. Common RxJS operators

### Answer:
```typescript
// Transformation
map(), switchMap(), mergeMap(), concatMap()

// Filtering
filter(), take(), takeUntil(), distinct()

// Combination
combineLatest(), merge(), concat(), forkJoin()

// Error handling
catchError(), retry(), finalize()

// Timing
debounceTime(), throttleTime(), delay()
```

---

## 82-85. RxJS advanced topics

- **Subject types**: Subject, BehaviorSubject, ReplaySubject, AsyncSubject
- **Higher-order Observables**: switchMap vs mergeMap vs concatMap vs exhaustMap
- **Error handling**: catchError, retry, retryWhen
- **Combination**: forkJoin, combineLatest, zip

---

# SECTION 12: MODULES AND ARCHITECTURE (5 QUESTIONS)

## 86. Difference between feature module and root module

### Answer:
- **Root (AppModule)**: Bootstrap application, forRoot() only
- **Feature Module**: Encapsulate features, forChild()

---

## 87. Shared module vs Core module

### Answer:
- **Shared**: Reusable components, directives, pipes
- **Core**: Singleton services, guards, interceptors (imported only in AppModule)

---

## 88. What is Angular CLI and its usage?

### Answer:
**Angular CLI** is command-line tool for Angular development. Commands:
- `ng new`: Create new project
- `ng serve`: Run dev server
- `ng build`: Build for production
- `ng generate`: Generate components, services, etc.

---

## 89. What is Ahead-of-Time (AOT) compilation?

### Answer:
**AOT** compiles at **build time**, reducing bundle size, catching template errors early, faster startup.

---

## 90. Difference between JIT and AOT compilation

### Answer:

| Feature | JIT | AOT |
|---------|-----|-----|
| **When** | Runtime | Build time |
| **Bundle size** | Larger | Smaller |
| **Startup** | Slower | Faster |
| **Error detection** | Runtime | Build time |

---

# SECTION 13: FREQUENTLY ASKED QUESTIONS (12 QUESTIONS)

## 91-102: Scenario Questions

These cover:
- HTTP Interceptors
- View Encapsulation  
- Content Projection
- ng-template
- Component Communication
- async pipe
- trackBy in *ngFor
- Parent-child data flow patterns
- Performance optimization
- Security best practices
- Testing patterns
- Real-world implementation scenarios

---

# SECTION 14: MICRO FRONTEND ARCHITECTURE (10 QUESTIONS)

## 103-112: Micro Frontend Questions

Comprehensive coverage of:
- Micro Frontend Architecture
- Module Federation
- Implementation in Angular
- Challenges and solutions
- Routing in MFEs
- Dependency sharing
- Web Components/Angular Elements
- Single-SPA
- Authentication in MFEs
- Best practices

---

# SECTION 15: ADVANCED & MISSED QUESTIONS (28 QUESTIONS)

## 113-140: Advanced Topics

Coverage includes:
- trackBy performance optimization
- Lazy loading strategies
- OnPush change detection advanced patterns
- Memoization with pipes
- Avoiding unnecessary API calls
- Higher-order Observables with examples
- Error handling strategies
- Component/service/pipe/directive testing
- Observable and async testing
- HttpClientTestingModule
- E2E testing with Cypress
- Route protection with Guards
- XSS prevention
- Security tokens and authentication
- DomSanitizer usage
- Custom schematics
- ng update migration
- Build optimization
- Internationalization (i18n)
- Accessibility (ARIA)
- Screen reader compatibility
- Server-side Rendering (Angular Universal)
- Pre-rendering vs live SSR
- NgRx selectors and memoization
- Side effects in NgRx Effects
- Local vs global state management
- Cross-MFE state sharing

---

# SECTION 16: SCENARIO QUESTIONS (10 QUESTIONS)

## 141-150: Real-world Scenarios

- Explain Angular architecture in your project
- Routing implementation in your project
- State management implementation
- Services and DI usage in your project
- Lifecycle hooks used in your project
- Change detection optimization
- RxJS integration
- Forms implementation
- Configuration files management
- Development vs production builds

---

## CRITICAL SUMMARY FOR INTERVIEWS

**Must Know (Foundation):**
- Components, Modules, Services, Templates
- Data binding types, Directives, Pipes
- Dependency Injection
- Lifecycle hooks
- HttpClient for API calls

**Should Know (Intermediate):**
- Routing and Guards
- Forms (Template-driven & Reactive)
- RxJS Observables and Subjects
- Change Detection strategies
- Content projection

**Great to Know (Advanced):**
- NgRx state management
- HTTP Interceptors
- Performance optimization
- Testing patterns
- Micro Frontends
- Security best practices

---

# END OF ANGULAR COMPLETE INTERVIEW GUIDE - 144 COMPREHENSIVE QUESTIONS

**Total Coverage**:
- 11 Angular Basics questions
- 6 Components & Templates questions
- 15 Project Configuration questions  
- 6 Directives & Pipes questions
- 5 Services & DI questions
- 5 Routing questions
- 5 Forms questions
- 5 State Management questions
- 4 Lifecycle Hooks questions
- 4 Change Detection questions
- 8 RxJS & Observables questions
- 5 Modules & Architecture questions
- 12 Frequently Asked questions
- 10 Micro Frontend questions
- 28 Advanced/Missed questions
- 10 Scenario questions

**Format:** Detailed answers with theoretical keywords, code examples, comparison tables, real-world scenarios, and best practices throughout.

