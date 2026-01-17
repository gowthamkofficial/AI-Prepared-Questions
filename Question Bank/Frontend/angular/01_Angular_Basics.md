# ANGULAR BASICS ANSWERS

---

## 1. What is Angular?

### Answer:
- **Angular** is a **TypeScript-based open-source** front-end framework
- Developed and maintained by **Google**
- Used for building **Single Page Applications (SPAs)**
- Provides a complete solution with routing, forms, HTTP client, and more
- Uses **component-based architecture**
- Supports **two-way data binding**, **dependency injection**, and **modular development**
- Current versions are called **Angular** (2+), distinct from AngularJS (1.x)

### Theoretical Keywords:
**TypeScript**, **SPA**, **Component-based**, **Google**, **Two-way binding**,  
**Dependency Injection**, **Modular architecture**, **Cross-platform**

### Example:
```typescript
// app.component.ts - Basic Angular Component
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ title }}</h1>
    <p>Welcome to Angular!</p>
  `,
  styles: [`h1 { color: blue; }`]
})
export class AppComponent {
  title = 'My Angular App';
}
```

---

## 2. Difference between AngularJS and Angular

### Answer:

| Feature | AngularJS (1.x) | Angular (2+) |
|---------|-----------------|--------------|
| **Language** | JavaScript | TypeScript |
| **Architecture** | MVC-based | Component-based |
| **Mobile Support** | Not built for mobile | Mobile-friendly |
| **Data Binding** | Two-way with $scope | Two-way with [(ngModel)] |
| **Dependency Injection** | Basic | Hierarchical DI |
| **CLI** | None | Angular CLI |
| **Performance** | Slower (dirty checking) | Faster (change detection) |
| **Routing** | ngRoute | @angular/router |

### Theoretical Keywords:
**MVC vs Components**, **TypeScript vs JavaScript**, **Performance**,  
**Dirty checking vs Change detection**, **Mobile support**, **CLI**

---

## 3. What are components?

### Answer:
- **Components** are the **building blocks** of Angular applications
- Each component has its own **template**, **styles**, and **logic**
- Defined using `@Component` decorator
- Contains: **selector**, **template/templateUrl**, **styles/styleUrls**
- Components can be **nested** to create complex UIs
- Encapsulates view and behavior for a **specific UI element**

### Theoretical Keywords:
**Building blocks**, **@Component decorator**, **Encapsulation**,  
**Template**, **Selector**, **View + Logic**, **Reusable**

### Example:
```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent {
  userName: string = 'John Doe';
  userEmail: string = 'john@example.com';
  
  onEdit(): void {
    console.log('Editing user...');
  }
}
```

---

## 4. What is a module?

### Answer:
- **Module** is a **container** that groups related components, services, pipes, and directives
- Defined using `@NgModule` decorator
- Every Angular app has at least one module: **AppModule** (root module)
- Modules help organize code into **cohesive blocks** of functionality
- Can be **eagerly** or **lazily** loaded
- Types: Root module, Feature modules, Shared modules, Core modules

### Theoretical Keywords:
**@NgModule**, **Container**, **Organization**, **Lazy loading**,  
**Feature modules**, **Declarations**, **Imports**, **Exports**

### Example:
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { UserModule } from './user/user.module';

@NgModule({
  declarations: [AppComponent],  // Components, directives, pipes
  imports: [BrowserModule, UserModule],  // Other modules
  providers: [],  // Services
  bootstrap: [AppComponent]  // Root component
})
export class AppModule { }
```

---

## 5. What is a service?

### Answer:
- **Service** is a class that contains **business logic** and **data operations**
- Used for **code reusability** and **separation of concerns**
- Defined using `@Injectable` decorator
- Injected into components via **Dependency Injection**
- Typically used for: API calls, data sharing, utility functions
- Can be **singleton** (providedIn: 'root') or **scoped** to module/component

### Theoretical Keywords:
**@Injectable**, **Business logic**, **Dependency Injection**, **Singleton**,  
**Reusability**, **Separation of concerns**, **providedIn**

### Example:
```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'  // Singleton service
})
export class UserService {
  private apiUrl = '/api/users';
  
  constructor(private http: HttpClient) {}
  
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }
  
  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }
}
```

---

## 6. What is a directive?

### Answer:
- **Directives** are classes that **modify DOM elements** or component behavior
- Three types of directives:
  1. **Component directives**: Components with templates
  2. **Structural directives**: Change DOM layout (`*ngIf`, `*ngFor`)
  3. **Attribute directives**: Change appearance/behavior (`ngClass`, `ngStyle`)
- Defined using `@Directive` decorator
- Can create **custom directives** for specific functionality

### Theoretical Keywords:
**DOM manipulation**, **Structural directives**, **Attribute directives**,  
**@Directive decorator**, **Custom directives**, **Behavior modification**

### Example:
```typescript
// Custom attribute directive
import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  constructor(private el: ElementRef) {}
  
  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.el.nativeElement.style.backgroundColor = 'yellow';
  }
  
  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.el.nativeElement.style.backgroundColor = '';
  }
}

// Usage: <p appHighlight>Hover over me!</p>
```

---

## 7. What is data binding?

### Answer:
- **Data binding** is the **synchronization** between component and view
- Allows **automatic update** of view when data changes and vice versa
- Angular supports **four types** of data binding
- Eliminates need for manual DOM manipulation
- Makes code more **declarative** and **maintainable**

### Theoretical Keywords:
**Synchronization**, **Component-View connection**, **Automatic updates**,  
**Declarative UI**, **One-way**, **Two-way binding**

---

## 8. Types of data binding in Angular

### Answer:
Angular has **four types** of data binding:

| Type | Syntax | Direction | Description |
|------|--------|-----------|-------------|
| **Interpolation** | `{{ value }}` | Component → View | Display component data |
| **Property Binding** | `[property]="value"` | Component → View | Bind to element property |
| **Event Binding** | `(event)="handler()"` | View → Component | Listen to DOM events |
| **Two-way Binding** | `[(ngModel)]="value"` | Both ways | Sync data both directions |

### Theoretical Keywords:
**Interpolation**, **Property binding**, **Event binding**, **Two-way binding**,  
**ngModel**, **One-way vs Two-way**, **Data flow**

### Example:
```html
<!-- 1. Interpolation: Component → View -->
<h1>{{ title }}</h1>
<p>User: {{ user.name }}</p>

<!-- 2. Property Binding: Component → View -->
<img [src]="imageUrl">
<button [disabled]="isDisabled">Click</button>
<div [ngClass]="{'active': isActive}"></div>

<!-- 3. Event Binding: View → Component -->
<button (click)="onClick()">Click Me</button>
<input (input)="onInput($event)">
<form (submit)="onSubmit()">

<!-- 4. Two-way Binding: Both Directions -->
<input [(ngModel)]="username">
<!-- Equivalent to: -->
<input [ngModel]="username" (ngModelChange)="username = $event">
```

---

## 9. What is interpolation?

### Answer:
- **Interpolation** displays component data in the template
- Uses **double curly braces** syntax: `{{ expression }}`
- Evaluates the expression and converts to **string**
- Supports **expressions**, **method calls**, and **pipes**
- One-way binding: **Component → View**
- Cannot use: assignments, new, chaining, increment/decrement

### Theoretical Keywords:
**Double curly braces**, **Expression evaluation**, **String conversion**,  
**One-way binding**, **Template expressions**, **Pipes support**

### Example:
```typescript
@Component({
  selector: 'app-demo',
  template: `
    <!-- Simple interpolation -->
    <h1>{{ title }}</h1>
    
    <!-- Object property -->
    <p>Name: {{ user.name }}</p>
    
    <!-- Expression -->
    <p>Total: {{ price * quantity }}</p>
    
    <!-- Method call -->
    <p>Greeting: {{ getGreeting() }}</p>
    
    <!-- With pipe -->
    <p>Date: {{ today | date:'fullDate' }}</p>
    <p>Price: {{ price | currency:'USD' }}</p>
    
    <!-- Ternary operator -->
    <p>Status: {{ isActive ? 'Active' : 'Inactive' }}</p>
  `
})
export class DemoComponent {
  title = 'Welcome';
  user = { name: 'John', age: 25 };
  price = 99.99;
  quantity = 2;
  today = new Date();
  isActive = true;
  
  getGreeting(): string {
    return `Hello, ${this.user.name}!`;
  }
}
```

---

## 10. What are templates?

### Answer:
- **Templates** define the **view** of a component
- Written in **HTML** with Angular-specific syntax
- Can be **inline** (template) or **external** (templateUrl)
- Contains: HTML, directives, bindings, pipes
- Angular compiles templates into **JavaScript** (AOT/JIT)
- Templates are **type-checked** with TypeScript in strict mode

### Theoretical Keywords:
**View definition**, **HTML + Angular syntax**, **Inline vs external**,  
**Compilation**, **Type checking**, **Template syntax**

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

```html
<!-- external.component.html -->
<div class="container">
  <header>
    <h1>{{ title }}</h1>
  </header>
  
  <main>
    <ng-container *ngIf="isLoaded; else loading">
      <app-content [data]="contentData"></app-content>
    </ng-container>
    
    <ng-template #loading>
      <p>Loading...</p>
    </ng-template>
  </main>
</div>
```

---

## 11. What is the purpose of `@NgModule`?

### Answer:
- **@NgModule** is a decorator that defines an **Angular module**
- Configures the **compilation context** for components
- Has several important properties:
  - **declarations**: Components, directives, pipes in this module
  - **imports**: Other modules whose exports are needed
  - **exports**: Components/directives/pipes available to other modules
  - **providers**: Services available for injection
  - **bootstrap**: Root component (only in AppModule)

### Theoretical Keywords:
**Module decorator**, **Compilation context**, **declarations**,  
**imports**, **exports**, **providers**, **bootstrap**

### Example:
```typescript
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Components
import { UserListComponent } from './user-list/user-list.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UserFormComponent } from './user-form/user-form.component';

// Services
import { UserService } from './services/user.service';

// Pipes
import { UserFilterPipe } from './pipes/user-filter.pipe';

@NgModule({
  // What belongs to this module
  declarations: [
    UserListComponent,
    UserDetailComponent,
    UserFormComponent,
    UserFilterPipe
  ],
  
  // What this module needs from other modules
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  
  // What this module exposes to other modules
  exports: [
    UserListComponent,
    UserDetailComponent
  ],
  
  // Services scoped to this module
  providers: [
    UserService
  ]
})
export class UserModule { }
```

---
