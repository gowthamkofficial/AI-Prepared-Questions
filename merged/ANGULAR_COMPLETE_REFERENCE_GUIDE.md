# Angular Complete Reference Guide: Old vs New Syntax

A comprehensive reference document covering Angular features across versions (Pre-Angular 12 vs Angular 12+), including modern patterns with Signals and standalone components.

---

## Table of Contents
1. [Bootstrapping (main.ts)](#bootstrapping-maints)
2. [Modules (NgModule)](#modules-ngmodule)
3. [Services](#services)
4. [Components](#components)
5. [Directives](#directives)
6. [Pipes](#pipes)
7. [ngClass and ngStyle](#ngclass-and-ngstyle)
8. [Auth Guards](#auth-guards)
9. [Interceptors](#interceptors)
10. [Observables and Subscriptions](#observables-and-subscriptions)
11. [Lifecycle Hooks](#lifecycle-hooks)
12. [RxJS Operators](#rxjs-operators)
13. [Forms (Template-Driven and Reactive)](#forms)
14. [Custom Validation](#custom-validation)
15. [Signals](#signals)

---

## Bootstrapping (main.ts)

### Description
The bootstrapping process initializes the Angular application and runs the root module/component.

### Older Syntax (Pre-Angular 12)
```typescript
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch(err => console.error(err));
```

### Current Syntax (Angular 12+)
```typescript
import { platformBrowser } from '@angular/platform-browser';
import { AppModule } from './app/app.module';

platformBrowser()
  .bootstrapModule(AppModule)
  .catch(err => console.error(err));
```

### Modern Syntax (Angular 14+ with Standalone Components)
```typescript
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
```

**app.config.ts** (Angular 14+ Standalone):
```typescript
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor]))
  ]
};
```

### Key Differences
- **Pre-Angular 12**: Used `platformBrowserDynamic()` for dynamic module compilation
- **Angular 12+**: `platformBrowser()` works for pre-compiled modules
- **Angular 14+**: `bootstrapApplication()` directly bootstraps standalone components without NgModule, simpler and more tree-shakeable
- **Modern approach**: No need for NgModule; use functional providers instead

---

## Modules (NgModule)

### Description
Modules organize related components, services, and directives. Each module declares what it contains and what it exports.

### Older Syntax (Pre-Angular 12)
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { UserComponent } from './components/user.component';
import { UserService } from './services/user.service';
import { HighlightDirective } from './directives/highlight.directive';
import { TitlePipe } from './pipes/title.pipe';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    HighlightDirective,
    TitlePipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

### Current Syntax (Angular 12+)
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { UserComponent } from './components/user.component';
import { UserService } from './services/user.service';
import { HighlightDirective } from './directives/highlight.directive';
import { TitlePipe } from './pipes/title.pipe';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    HighlightDirective,
    TitlePipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

### Modern Syntax (Angular 14+ Standalone Components - No NgModule)
```typescript
// No module file needed! Import standalone components directly.

// app.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { UserComponent } from './components/user.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    UserComponent
  ],
  template: `<router-outlet></router-outlet>`
})
export class AppComponent { }

// app.routes.ts
import { Routes } from '@angular/router';
import { UserComponent } from './components/user.component';

export const routes: Routes = [
  { path: 'users', component: UserComponent }
];
```

**Feature Module (Modern Standalone):**
```typescript
// user.routes.ts
import { Routes } from '@angular/router';
import { UserListComponent } from './user-list.component';
import { UserDetailComponent } from './user-detail.component';

export const USER_ROUTES: Routes = [
  { path: '', component: UserListComponent },
  { path: ':id', component: UserDetailComponent }
];

// In app.routes.ts
export const routes: Routes = [
  { path: 'users', loadChildren: () => import('./users/user.routes').then(m => m.USER_ROUTES) }
];
```

### Key Differences
- **Pre-Angular 12**: Required NgModule to declare and import everything
- **Angular 12+**: Same module pattern, but optimization flags introduced
- **Angular 14+**: Standalone components eliminate the need for NgModule entirely
- **Tree-shaking**: Standalone improves tree-shaking and bundle size
- **Lazy loading**: With standalone, use `loadChildren` with route-based code splitting

---

## Services

### Description
Services are singleton objects that provide business logic, data access, and reusable functionality across components.

### Older Syntax (Pre-Angular 12)
```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root' // Available application-wide
})
export class UserService {
  private apiUrl = 'https://api.example.com/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

### Current Syntax (Angular 12+)
```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = 'https://api.example.com/users';

  constructor(private readonly http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  updateUser(id: number, user: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

### Modern Syntax (Angular 16+ with Signals)
```typescript
import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { computed, effect } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = 'https://api.example.com/users';
  private readonly http = inject(HttpClient);

  // Signals for state management
  users = signal<User[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  // Computed value based on users signal
  userCount = computed(() => this.users().length);

  constructor() {
    // Effect to update loading state
    effect(() => {
      console.log(`Loaded ${this.userCount()} users`);
    });
  }

  getUsers(): Observable<User[]> {
    this.loading.set(true);
    return this.http.get<User[]>(this.apiUrl).pipe(
      catchError(err => {
        this.error.set(err.message);
        this.loading.set(false);
        return throwError(() => err);
      })
    );
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`).pipe(
      catchError(err => {
        this.error.set(err.message);
        return throwError(() => err);
      })
    );
  }

  setUsers(users: User[]): void {
    this.users.set(users);
    this.loading.set(false);
  }

  addUser(user: User): void {
    this.users.update(users => [...users, user]);
  }

  updateUserInSignal(id: number, updates: Partial<User>): void {
    this.users.update(users =>
      users.map(u => u.id === id ? { ...u, ...updates } : u)
    );
  }

  removeUser(id: number): void {
    this.users.update(users => users.filter(u => u.id !== id));
  }
}
```

### Key Differences
- **Pre-Angular 12**: Basic service with Observable pattern
- **Angular 12+**: `readonly` modifier for immutability, `Partial<T>` for type safety
- **Angular 16+**: Signals replace BehaviorSubject for state management, simpler API, automatic change detection
- **Modern approach**: Inject using `inject()` function instead of constructor injection, computed values track dependencies automatically

---

## Components

### Description
Components are the building blocks of Angular applications, combining a template, styles, and logic.

### Older Syntax (Pre-Angular 12)
```typescript
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { UserService } from '../services/user.service';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  selectedUser: User | null = null;

  @Output() userSelected = new EventEmitter<User>();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.userService.getUsers().subscribe(
      (users) => {
        this.users = users;
        this.loading = false;
      },
      (error) => {
        console.error('Error loading users:', error);
        this.loading = false;
      }
    );
  }

  selectUser(user: User): void {
    this.selectedUser = user;
    this.userSelected.emit(user);
  }
}
```

**Template (user-list.component.html):**
```html
<div *ngIf="loading">Loading...</div>
<div *ngIf="!loading && users.length > 0">
  <ul>
    <li *ngFor="let user of users" 
        (click)="selectUser(user)"
        [class.active]="selectedUser?.id === user.id">
      {{ user.name }} - {{ user.email }}
    </li>
  </ul>
</div>
<div *ngIf="!loading && users.length === 0">No users found</div>
```

### Current Syntax (Angular 12+)
```typescript
import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserListComponent implements OnInit {
  users$: Observable<User[]>;
  loading$: Observable<boolean>;
  selectedUser: User | null = null;

  @Output() userSelected = new EventEmitter<User>();

  private destroy$ = new Subject<void>();

  constructor(private readonly userService: UserService) {
    this.users$ = this.userService.getUsers();
    this.loading$ = this.userService.loading$;
  }

  ngOnInit(): void {
    this.userService.loadUsers()
      .pipe(takeUntil(this.destroy$))
      .subscribe();
  }

  selectUser(user: User): void {
    this.selectedUser = user;
    this.userSelected.emit(user);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

### Modern Syntax (Angular 14+ Standalone with Signals)
```typescript
import { Component, inject, signal, computed, output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { toSignal } from '@angular/core/rxjs-interop';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="loading()">Loading...</div>
    <div *ngIf="!loading() && users().length > 0">
      <ul>
        <li *ngFor="let user of users()" 
            (click)="selectUser(user)"
            [class.active]="selectedUser()?.id === user.id">
          {{ user.name }} - {{ user.email }}
        </li>
      </ul>
    </div>
    <div *ngIf="!loading() && users().length === 0">No users found</div>
  `,
  styles: [`
    .active { background-color: lightblue; }
  `]
})
export class UserListComponent implements OnInit {
  private userService = inject(UserService);

  // Signals
  users = signal<User[]>([]);
  loading = signal(false);
  selectedUser = signal<User | null>(null);

  // Computed property
  userCount = computed(() => this.users().length);

  // Output for parent communication
  userSelected = output<User>();

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading.set(true);
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users.set(users);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading users:', err);
        this.loading.set(false);
      }
    });
  }

  selectUser(user: User): void {
    this.selectedUser.set(user);
    this.userSelected.emit(user);
  }
}
```

**Advanced Modern Syntax (Angular 16+ with Signals, toSignal):**
```typescript
import { Component, inject, signal, computed, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="loading()">Loading...</div>
    <div *ngIf="!loading() && (users() || []).length > 0">
      <ul>
        <li *ngFor="let user of users() || []" 
            (click)="selectUser(user)"
            [class.active]="selectedUser()?.id === user.id">
          {{ user.name }} - {{ user.email }}
        </li>
      </ul>
    </div>
  `
})
export class UserListComponent {
  private userService = inject(UserService);

  // Convert Observable to Signal
  users = toSignal(this.userService.getUsers(), { initialValue: [] });
  loading = this.userService.loading;
  selectedUser = signal<User | null>(null);
  userCount = computed(() => this.users().length);
  userSelected = output<User>();

  selectUser(user: User): void {
    this.selectedUser.set(user);
    this.userSelected.emit(user);
  }
}
```

### Key Differences
- **Pre-Angular 12**: Manual subscription management, property-based state
- **Angular 12+**: OnPush change detection, RxJS operators (takeUntil), destroy$ pattern
- **Angular 14+**: Standalone components, signals for state, output() for event emission
- **Angular 16+**: toSignal() converts Observables to Signals, automatic cleanup
- **Modern approach**: No manual unsubscribe needed with Signals, cleaner reactivity

---

## Directives

### Description
Directives add behavior to DOM elements. Attribute directives modify element properties, while structural directives modify the DOM structure.

### Attribute Directive - Older Syntax (Pre-Angular 12)
```typescript
import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input() highlightColor: string = 'yellow';

  constructor(private el: ElementRef) {
    this.el.nativeElement.style.backgroundColor = this.highlightColor;
  }

  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.highlight(this.highlightColor);
  }

  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.highlight('');
  }

  private highlight(color: string): void {
    this.el.nativeElement.style.backgroundColor = color;
  }
}
```

**Usage:**
```html
<p appHighlight highlightColor="lightblue">Hover me!</p>
```

### Attribute Directive - Current Syntax (Angular 12+)
```typescript
import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input() appHighlight: string = 'yellow'; // Input name matches selector

  constructor(
    private el: ElementRef,
    private renderer: Renderer2
  ) { }

  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.highlight(this.appHighlight);
  }

  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.highlight('');
  }

  private highlight(color: string): void {
    this.renderer.setStyle(this.el.nativeElement, 'backgroundColor', color);
  }
}
```

**Usage:**
```html
<!-- Bind value directly to directive selector name -->
<p [appHighlight]="'lightblue'">Hover me!</p>
```

### Attribute Directive - Modern Syntax (Angular 14+ Standalone)
```typescript
import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {
  @Input() appHighlight: string = 'yellow';
  @Input() appHighlightHover: string = 'lightblue';

  constructor(
    private el: ElementRef,
    private renderer: Renderer2
  ) { }

  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.setBackgroundColor(this.appHighlightHover);
  }

  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.setBackgroundColor(this.appHighlight);
  }

  ngOnInit(): void {
    this.setBackgroundColor(this.appHighlight);
  }

  private setBackgroundColor(color: string): void {
    this.renderer.setStyle(this.el.nativeElement, 'backgroundColor', color);
  }
}
```

**Usage:**
```html
<p [appHighlight]="'yellow'" [appHighlightHover]="'lightblue'">Hover me!</p>
```

### Structural Directive - Older Syntax (Pre-Angular 12)
```typescript
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appUnless]'
})
export class UnlessDirective {
  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) { }

  @Input()
  set appUnless(condition: boolean) {
    if (!condition && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (condition && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }
  }
}
```

**Usage:**
```html
<p *appUnless="isHidden">Visible when isHidden is false</p>
```

### Structural Directive - Modern Syntax (Angular 14+ Standalone with Control Flow)
```typescript
// With Angular 17+, use new @if, @for, @switch instead of *ngIf, *ngFor, *ngSwitch
// Example of conditional rendering:

@Component({
  selector: 'app-example',
  template: `
    @if (isVisible()) {
      <p>This is visible</p>
    } @else {
      <p>This is hidden</p>
    }

    @for (item of items(); track item.id) {
      <div>{{ item.name }}</div>
    }

    @switch (status()) {
      @case ('loading') {
        <p>Loading...</p>
      }
      @case ('loaded') {
        <p>Loaded</p>
      }
      @default {
        <p>Unknown status</p>
      }
    }
  `
})
export class ExampleComponent {
  isVisible = signal(true);
  items = signal([{ id: 1, name: 'Item 1' }]);
  status = signal('loading');
}
```

### Key Differences
- **Pre-Angular 12**: Direct DOM manipulation with ElementRef, basic directive structure
- **Angular 12+**: Renderer2 for safer DOM manipulation, Input naming convention
- **Angular 14+**: Standalone directives, @HostListener binding improvements
- **Angular 17+**: New control flow syntax (@if, @for, @switch) replaces *ngIf, *ngFor, *ngSwitch for better performance and readability

---

## Pipes

### Description
Pipes format data for display in templates. Pure pipes only run when input changes, while impure pipes run on every change detection cycle.

### Pure Pipe - Older Syntax (Pre-Angular 12)
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'titleCase'
})
export class TitleCasePipe implements PipeTransform {
  transform(value: string, args?: any): string {
    if (!value) return value;
    return value
      .split(' ')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  }
}
```

**Usage:**
```html
<p>{{ 'hello world' | titleCase }}</p>
<!-- Output: Hello World -->
```

### Pure Pipe - Current Syntax (Angular 12+)
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'titleCase'
})
export class TitleCasePipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) return '';
    return value
      .split(' ')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  }
}
```

### Pure Pipe - Modern Syntax (Angular 14+ Standalone)
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'titleCase',
  standalone: true
})
export class TitleCasePipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    return value
      ?.split(' ')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ') ?? '';
  }
}
```

### Impure Pipe - Older Syntax (Pre-Angular 12)
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterBy',
  pure: false // Important: mark as impure
})
export class FilterByPipe implements PipeTransform {
  transform(items: any[], filterKey: string, filterValue: any): any[] {
    if (!items || !filterKey) return items;
    return items.filter(item => item[filterKey] === filterValue);
  }
}
```

**Usage:**
```html
<div *ngFor="let item of items | filterBy:'status':'active'">
  {{ item.name }}
</div>
```

### Impure Pipe - Modern Syntax (Angular 12+)
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterBy',
  pure: false
})
export class FilterByPipe implements PipeTransform {
  transform<T extends Record<string, any>>(
    items: T[] | null | undefined,
    filterKey: keyof T,
    filterValue: any
  ): T[] {
    if (!items || !filterKey) return items ?? [];
    return items.filter(item => item[filterKey] === filterValue);
  }
}
```

### Advanced Pipe - Angular 14+ with Signals
```typescript
import { Pipe, PipeTransform, inject } from '@angular/core';
import { signal } from '@angular/core';

@Pipe({
  name: 'async',
  standalone: true,
  pure: false
})
export class CustomAsyncPipe implements PipeTransform {
  private lastValue: any;

  transform<T>(promise: Promise<T> | null | undefined): T | null {
    if (!promise) return null;

    promise.then(value => {
      this.lastValue = value;
    });

    return this.lastValue ?? null;
  }
}
```

### Custom Date Pipe Example - Older vs Modern
```typescript
// Older Syntax (Pre-Angular 12)
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatDate'
})
export class FormatDatePipe implements PipeTransform {
  transform(value: Date, format: string): string {
    if (!value) return '';
    // Custom date formatting logic
    return value.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }
}

// Modern Syntax (Angular 14+)
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatDate',
  standalone: true
})
export class FormatDatePipe implements PipeTransform {
  transform(value: Date | string | null | undefined, format: string = 'long'): string {
    if (!value) return '';
    const date = typeof value === 'string' ? new Date(value) : value;
    
    const options: Intl.DateTimeFormatOptions = {
      year: 'numeric',
      month: format === 'short' ? '2-digit' : 'long',
      day: 'numeric'
    };
    
    return date.toLocaleDateString('en-US', options);
  }
}
```

### Key Differences
- **Pure Pipes**: Called only when input reference changes; more performant
- **Impure Pipes**: Called on every change detection cycle; use sparingly
- **Pre-Angular 12**: Basic implementation without nullability checks
- **Angular 12+**: Better TypeScript typing, null safety
- **Angular 14+**: Standalone pipes, better type generics
- **Modern approach**: Consider using computed signals instead of pipes for complex transformations

---

## ngClass and ngStyle

### Description
ngClass dynamically adds/removes CSS classes. ngStyle dynamically sets inline styles.

### Older Syntax (Pre-Angular 12)

**ngClass:**
```typescript
export class ClassComponent {
  isActive = true;
  hasError = false;
  userStatus = 'premium'; // 'free', 'premium', 'vip'
}
```

```html
<!-- Single class binding -->
<div [ngClass]="{ 'active': isActive }">Single class</div>

<!-- Multiple classes -->
<div [ngClass]="{ 'active': isActive, 'error': hasError }">Multiple classes</div>

<!-- Class expression -->
<div [ngClass]="isActive ? 'active' : 'inactive'">Ternary</div>

<!-- String of classes -->
<div [ngClass]="'active error highlight'">String</div>

<!-- Array of classes -->
<div [ngClass]="['active', 'highlight']">Array</div>
```

**ngStyle:**
```html
<!-- Object syntax -->
<div [ngStyle]="{ 'color': 'red', 'font-size.px': 14 }">Red text</div>

<!-- Ternary -->
<div [ngStyle]="{ 'backgroundColor': isActive ? 'green' : 'gray' }">Dynamic</div>

<!-- Multiple properties -->
<div [ngStyle]="{ 
  'color': textColor, 
  'backgroundColor': bgColor,
  'padding.px': padding 
}">
  Styled element
</div>
```

### Current Syntax (Angular 12+)
```typescript
export class ClassComponent {
  isActive = signal(true);
  hasError = signal(false);
  userStatus = signal('premium'); // 'free', 'premium', 'vip'
  
  // Computed styles
  dynamicClasses = computed(() => ({
    'active': this.isActive(),
    'error': this.hasError(),
    [this.userStatus()]: true
  }));

  dynamicStyles = computed(() => ({
    'color': this.hasError() ? 'red' : 'black',
    'backgroundColor': this.isActive() ? 'lightgreen' : 'lightgray'
  }));
}
```

```html
<!-- ngClass with computed -->
<div [ngClass]="dynamicClasses()">Dynamic classes</div>

<!-- ngStyle with computed -->
<div [ngStyle]="dynamicStyles()">Dynamic styles</div>

<!-- Direct binding -->
<div [class.active]="isActive()" [class.error]="hasError()">
  Direct class binding
</div>

<!-- Style binding -->
<div [style.color]="hasError() ? 'red' : 'black'"
     [style.backgroundColor]="isActive() ? 'lightgreen' : 'lightgray'">
  Direct style binding
</div>
```

### Modern Syntax (Angular 14+ with Class/Style Binding)
```typescript
import { Component, signal, computed } from '@angular/core';

export class ClassComponent {
  isActive = signal(true);
  hasError = signal(false);
  userStatus = signal<'free' | 'premium' | 'vip'>('premium');
  padding = signal(16);
  
  // Computed class object
  dynamicClasses = computed(() => ({
    'active': this.isActive(),
    'error': this.hasError(),
    'premium-user': this.userStatus() === 'premium',
    'vip-user': this.userStatus() === 'vip'
  }));

  // Computed style object
  dynamicStyles = computed(() => ({
    'color': this.hasError() ? 'rgb(220, 38, 38)' : 'rgb(0, 0, 0)',
    'backgroundColor': this.isActive() ? 'rgb(187, 247, 208)' : 'rgb(243, 244, 246)',
    'padding': `${this.padding()}px`,
    'borderRadius': '8px',
    'transition': 'all 0.3s ease'
  }));

  toggleActive(): void {
    this.isActive.update(val => !val);
  }

  setError(hasError: boolean): void {
    this.hasError.set(hasError);
  }
}
```

**Template with Modern Binding:**
```html
<!-- Using computed styles/classes -->
<div [ngClass]="dynamicClasses()" 
     [ngStyle]="dynamicStyles()">
  {{ userStatus() }} user
</div>

<!-- Direct property binding (Angular 15+) -->
<div class="base-class"
     [class.active]="isActive()"
     [class.error]="hasError()"
     [style.color]="hasError() ? 'red' : 'inherit'"
     [style.padding.px]="padding()">
  Direct binding example
</div>

<!-- Advanced: Combining with Array -->
<div [ngClass]="[
  'base-class',
  isActive() ? 'active' : 'inactive',
  userStatus() === 'premium' ? 'premium-badge' : ''
]">
  Array-based classes
</div>

<!-- Buttons with dynamic styles -->
<button (click)="toggleActive()"
        [ngClass]="{ 'btn-active': isActive(), 'btn-inactive': !isActive() }"
        [ngStyle]="{ 'backgroundColor': isActive() ? 'green' : 'gray' }">
  Toggle Active
</button>
```

### Key Differences
- **Pre-Angular 12**: Object/array/ternary approaches all valid
- **Angular 12+**: `readonly` properties, better typing
- **Angular 14+**: Signals with computed for reactivity, less boilerplate
- **Modern approach**: Direct property binding `[class.name]` often preferred over ngClass for single classes
- **Performance**: Computed signals cache results, reducing unnecessary re-evaluations

---

## Auth Guards

### Description
Guards control access to routes. CanActivate protects routes, CanDeactivate prevents unsaved changes, CanLoad lazy modules, and CanMatch matches specific route conditions.

### CanActivate Guard - Older Syntax (Pre-Angular 12)
```typescript
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    if (this.authService.isAuthenticated()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
```

**Usage in Routes:**
```typescript
const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent }
];
```

### CanActivate Guard - Current Syntax (Angular 12+)
```typescript
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authService.isAuthenticated$.pipe(
      map(isAuthenticated => {
        if (isAuthenticated) {
          return true;
        }
        return this.router.parseUrl('/login');
      })
    );
  }
}
```

### Modern Guard - Angular 14+ (Functional Approach)
```typescript
import { Injectable, inject } from '@angular/core';
import { Router, CanActivateFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

// Functional guard (new approach)
export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true;
  }

  // Store the attempted URL for redirecting
  authService.redirectUrl = state.url;
  return router.parseUrl('/login');
};

// If you still need class-based guards:
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  private authService = inject(AuthService);
  private router = inject(Router);

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    if (this.authService.isAuthenticated()) {
      return true;
    }
    return this.router.parseUrl('/login');
  }
}
```

**Usage with Functional Guards:**
```typescript
const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent }
];
```

### CanDeactivate Guard - Older Syntax (Pre-Angular 12)
```typescript
import { Injectable } from '@angular/core';
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

export interface CanComponentDeactivate {
  canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean;
}

@Injectable({
  providedIn: 'root'
})
export class CanDeactivateGuard implements CanDeactivate<CanComponentDeactivate> {
  canDeactivate(
    component: CanComponentDeactivate
  ): Observable<boolean> | Promise<boolean> | boolean {
    return component.canDeactivate ? component.canDeactivate() : true;
  }
}
```

**Component implementing CanDeactivate:**
```typescript
import { Component } from '@angular/core';
import { Observable } from 'rxjs';

export class EditComponent implements CanComponentDeactivate {
  hasUnsavedChanges = false;
  isDirty = false;

  canDeactivate(): Observable<boolean> | boolean {
    if (this.isDirty) {
      return window.confirm('You have unsaved changes. Leave anyway?');
    }
    return true;
  }
}
```

### CanDeactivate Guard - Modern Syntax (Angular 14+ Functional)
```typescript
import { CanDeactivateFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

export interface CanComponentDeactivate {
  canDeactivate: () => boolean;
}

export const canDeactivateGuard: CanDeactivateFn<CanComponentDeactivate> = (
  component: CanComponentDeactivate
) => {
  return component.canDeactivate ? component.canDeactivate() : true;
};
```

**Usage:**
```typescript
const routes: Routes = [
  {
    path: 'edit',
    component: EditComponent,
    canDeactivate: [canDeactivateGuard]
  }
];
```

### CanLoad Guard - Modern Syntax (Angular 14+)
```typescript
import { CanLoadFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Injectable, inject } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

export const canLoadAdminGuard: CanLoadFn = (route, segments) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.hasRole('admin')) {
    return true;
  }

  router.navigate(['/unauthorized']);
  return false;
};
```

**Usage:**
```typescript
const routes: Routes = [
  {
    path: 'admin',
    canLoad: [canLoadAdminGuard],
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  }
];
```

### CanMatch Guard - Angular 15+ (New Pattern Matching)
```typescript
import { CanMatchFn, Route } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const adminCanMatchGuard: CanMatchFn = (
  route: Route,
  segments: UrlSegment[]
) => {
  const authService = inject(AuthService);
  return authService.hasRole('admin');
};

// Usage with multiple routes for same path
const routes: Routes = [
  {
    path: 'admin',
    canMatch: [adminCanMatchGuard],
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  },
  {
    path: 'admin',
    component: UnauthorizedComponent // Fallback when guard fails
  }
];
```

### Role-Based Guard - Modern Example
```typescript
import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const roleGuard = (requiredRole: string): CanActivateFn => {
  return () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (authService.hasRole(requiredRole)) {
      return true;
    }

    router.navigate(['/unauthorized']);
    return false;
  };
};

// Usage
const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [roleGuard('admin')]
  },
  {
    path: 'editor',
    component: EditorComponent,
    canActivate: [roleGuard('editor')]
  }
];
```

### Key Differences
- **Pre-Angular 12**: Class-based guards only
- **Angular 12+**: Return `UrlTree` instead of navigating manually
- **Angular 14+**: Functional guards are preferred over class-based
- **Angular 15+**: CanMatch for pattern-based routing
- **Modern approach**: Use factory functions for guards with parameters, compose multiple guards easily

---

## Interceptors

### Description
Interceptors intercept HTTP requests and responses globally, useful for authentication, error handling, and logging.

### Older Syntax (Pre-Angular 12)
```typescript
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req);
  }
}
```

**Module Setup:**
```typescript
@NgModule({
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ]
})
export class AppModule { }
```

### Current Syntax (Angular 12+)
```typescript
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.logout();
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
```

### Modern Syntax (Angular 13+ Functional Interceptor)
```typescript
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Add authentication token
  const token = authService.getToken();
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req).pipe(
    catchError(error => {
      if (error.status === 401) {
        authService.logout();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};

// Logging interceptor
export const loggingInterceptor: HttpInterceptorFn = (req, next) => {
  console.log(`Starting request: ${req.method} ${req.url}`);
  
  return next(req).pipe(
    tap(event => {
      if (event instanceof HttpResponse) {
        console.log(`Completed request: ${req.method} ${req.url}`, event.status);
      }
    })
  );
};

// Error handling interceptor
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError(error => {
      let errorMessage = 'An error occurred';
      
      if (error.error instanceof ErrorEvent) {
        errorMessage = `Error: ${error.error.message}`;
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
      
      console.error(errorMessage);
      return throwError(() => error);
    })
  );
};
```

**Setup in app.config.ts (Angular 14+ Standalone):**
```typescript
import { ApplicationConfig } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor, loggingInterceptor, errorInterceptor } from './interceptors';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withInterceptors([
        authInterceptor,
        loggingInterceptor,
        errorInterceptor
      ])
    )
  ]
};
```

### Advanced Interceptor - Request/Response Modification
```typescript
import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators';

// Request transformation interceptor
export const requestTransformInterceptor: HttpInterceptorFn = (req, next) => {
  // Transform request URL (add API prefix)
  if (!req.url.startsWith('http')) {
    req = req.clone({
      url: `https://api.example.com${req.url}`
    });
  }

  // Add custom headers
  req = req.clone({
    setHeaders: {
      'X-Requested-With': 'XMLHttpRequest',
      'X-API-Version': '1.0'
    }
  });

  return next(req);
};

// Response transformation interceptor
export const responseTransformInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    tap(event => {
      if (event instanceof HttpResponse) {
        // Transform response data
        const data = event.body;
        const transformedResponse = event.clone({
          body: {
            ...data,
            timestamp: new Date()
          }
        });
        return transformedResponse;
      }
    })
  );
};

// Retry interceptor
export const retryInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    retry({
      count: 3,
      delay: 1000
    })
  );
};
```

### Key Differences
- **Pre-Angular 12**: Class-based interceptors with HttpInterceptor interface
- **Angular 12+**: Better error handling with typed HttpErrorResponse
- **Angular 13+**: Functional interceptors replace class-based approach
- **Modern setup**: Use `provideHttpClient` with `withInterceptors()` in standalone apps
- **Chaining**: Multiple functional interceptors can be composed easily

---

## Observables and Subscriptions

### Description
Observables are lazy, asynchronous data streams. Subscriptions activate them and receive data.

### Older Syntax (Pre-Angular 12)
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-list',
  template: `
    <div *ngIf="loading$ | async">Loading...</div>
    <div *ngIf="!(loading$ | async) && users$ | async as users">
      <ul>
        <li *ngFor="let user of users">{{ user.name }}</li>
      </ul>
    </div>
  `
})
export class UserListComponent implements OnInit, OnDestroy {
  users$: Observable<any[]>;
  loading$: Observable<boolean>;
  private subscription = new Subscription();
  private destroy$ = new Subject<void>();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.users$ = this.userService.getUsers();
    this.loading$ = this.userService.loading$;

    // Manual subscription
    this.subscription = this.users$.subscribe(
      users => console.log('Users:', users),
      error => console.error('Error:', error),
      () => console.log('Complete')
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
```

### Current Syntax (Angular 12+)
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-list',
  template: `
    <div *ngIf="loading$ | async">Loading...</div>
    <div *ngIf="!(loading$ | async) && users$ | async as users">
      <ul>
        <li *ngFor="let user of users">{{ user.name }}</li>
      </ul>
    </div>
  `
})
export class UserListComponent implements OnInit, OnDestroy {
  users$: Observable<any[]>;
  loading$: Observable<boolean>;
  private destroy$ = new Subject<void>();

  constructor(private readonly userService: UserService) { }

  ngOnInit(): void {
    this.users$ = this.userService.getUsers();
    this.loading$ = this.userService.loading$;

    // Using takeUntil pattern
    this.users$
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: users => console.log('Users:', users),
        error: error => console.error('Error:', error),
        complete: () => console.log('Complete')
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

### Modern Syntax (Angular 14+ with Signals and toSignal)
```typescript
import { Component, inject, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="loading()">Loading...</div>
    <div *ngIf="!loading() && users()">
      <ul>
        <li *ngFor="let user of users()">{{ user.name }}</li>
      </ul>
    </div>
  `
})
export class UserListComponent {
  private userService = inject(UserService);

  // Convert Observable to Signal
  users = toSignal(this.userService.getUsers(), { initialValue: [] });
  loading = this.userService.loading; // Already a signal

  constructor() {
    // Effect runs when users signal changes
    effect(() => {
      console.log(`Loaded ${this.users().length} users`);
    });
  }
}
```

### Subject and BehaviorSubject - Older vs Modern
```typescript
// Pre-Angular 12 - Subject
import { Subject } from 'rxjs';

export class EventService {
  private userCreated$ = new Subject<User>();
  
  onUserCreated$ = this.userCreated$.asObservable();

  notifyUserCreated(user: User): void {
    this.userCreated$.next(user);
  }
}

// Pre-Angular 12 - BehaviorSubject for state
import { BehaviorSubject } from 'rxjs';

export class StateService {
  private state$ = new BehaviorSubject<AppState>(initialState);
  
  state$: Observable<AppState> = this.state$.asObservable();

  updateState(state: Partial<AppState>): void {
    const current = this.state$.value;
    this.state$.next({ ...current, ...state });
  }
}

// Modern - Using Signals instead
import { signal, Signal } from '@angular/core';

export class EventService {
  private userCreatedInternal = signal<User | null>(null);
  userCreated: Signal<User | null> = this.userCreatedInternal;

  notifyUserCreated(user: User): void {
    this.userCreatedInternal.set(user);
  }
}

// Modern - State management with signals
export interface AppState {
  users: User[];
  selectedUser: User | null;
  loading: boolean;
}

export class StateService {
  private state = signal<AppState>({
    users: [],
    selectedUser: null,
    loading: false
  });

  appState = computed(() => this.state());
  users = computed(() => this.state().users);
  selectedUser = computed(() => this.state().selectedUser);
  loading = computed(() => this.state().loading);

  updateState(updates: Partial<AppState>): void {
    this.state.update(current => ({ ...current, ...updates }));
  }

  setUsers(users: User[]): void {
    this.state.update(s => ({ ...s, users }));
  }
}
```

### Key Differences
- **Pre-Angular 12**: Manual subscription management, must unsubscribe manually
- **Angular 12+**: `takeUntil` pattern simplifies cleanup, async pipe often used
- **Angular 14+**: Signals replace most Observable needs, less boilerplate
- **toSignal**: Converts Observables to Signals for seamless integration
- **Modern approach**: Prefer Signals for state, Observables for streams (HTTP, user input)

---

## Lifecycle Hooks

### Description
Lifecycle hooks allow you to tap into key moments in a component's life: creation, initialization, change detection, and destruction.

### Overview of All Lifecycle Hooks
```typescript
import { Component, OnInit, OnDestroy, OnChanges, DoCheck, 
         AfterContentInit, AfterContentChecked, AfterViewInit, 
         AfterViewChecked, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-lifecycle',
  template: `<p>{{ message }}</p>`
})
export class LifecycleComponent implements 
  OnChanges, OnInit, DoCheck, AfterContentInit, 
  AfterContentChecked, AfterViewInit, AfterViewChecked, OnDestroy {
  
  message = 'Lifecycle Demo';

  ngOnChanges(changes: SimpleChanges): void {
    console.log('ngOnChanges - Input properties changed', changes);
    // Called before ngOnInit and whenever input properties change
  }

  ngOnInit(): void {
    console.log('ngOnInit - Component initialized');
    // Called once after component is created and inputs are initialized
  }

  ngDoCheck(): void {
    console.log('ngDoCheck - Change detection cycle');
    // Called during every change detection cycle
    // Use carefully - it runs very frequently
  }

  ngAfterContentInit(): void {
    console.log('ngAfterContentInit - Content children initialized');
    // Called after content is projected into the component
  }

  ngAfterContentChecked(): void {
    console.log('ngAfterContentChecked - Content children checked');
    // Called after change detection checks projected content
  }

  ngAfterViewInit(): void {
    console.log('ngAfterViewInit - View and child views initialized');
    // Called after view is fully rendered
    // Good place for DOM manipulation
  }

  ngAfterViewChecked(): void {
    console.log('ngAfterViewChecked - View and child views checked');
    // Called after change detection checks the view
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy - Component destroyed');
    // Called just before component is destroyed
    // Use for cleanup (unsubscribe, clear timers, etc.)
  }
}
```

### Practical Examples

**Older Syntax (Pre-Angular 12) - OnInit and OnDestroy:**
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html'
})
export class UserDetailComponent implements OnInit, OnDestroy {
  user: any;
  private subscription: Subscription;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // Initialize data
    this.subscription = this.userService.currentUser$.subscribe(
      user => this.user = user
    );
  }

  ngOnDestroy(): void {
    // Cleanup: unsubscribe to prevent memory leaks
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
```

**Current Syntax (Angular 12+) - Better Cleanup:**
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html'
})
export class UserDetailComponent implements OnInit, OnDestroy {
  user: any;
  private destroy$ = new Subject<void>();

  constructor(private readonly userService: UserService) { }

  ngOnInit(): void {
    this.userService.currentUser$
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => this.user = user);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

**Modern Syntax (Angular 14+ with Signals):**
```typescript
import { Component, inject, signal, effect, computed } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-detail',
  standalone: true,
  template: `<div>{{ user()?.name }}</div>`
})
export class UserDetailComponent {
  private userService = inject(UserService);

  // No OnInit/OnDestroy needed - automatic cleanup
  user = toSignal(this.userService.currentUser$);

  // Effect for side effects
  constructor() {
    effect(() => {
      const currentUser = this.user();
      if (currentUser) {
        console.log('User loaded:', currentUser.name);
      }
    });
  }
}
```

**OnChanges Example - Older vs Modern:**
```typescript
// Pre-Angular 12
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-counter',
  template: `<p>Count: {{ count }}</p>`
})
export class CounterComponent implements OnChanges {
  @Input() initialValue: number = 0;
  count: number = 0;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['initialValue'] && !changes['initialValue'].firstChange) {
      console.log('initialValue changed from', changes['initialValue'].previousValue, 
                  'to', changes['initialValue'].currentValue);
      this.count = changes['initialValue'].currentValue;
    }
  }
}

// Modern (Angular 14+) - Using @Input signals instead
import { Component, input, signal, effect } from '@angular/core';

@Component({
  selector: 'app-counter',
  standalone: true,
  template: `<p>Count: {{ count() }}</p>`
})
export class CounterComponent {
  initialValue = input(0, { alias: 'initialValue' });
  count = signal(0);

  constructor() {
    effect(() => {
      // Automatically runs when initialValue changes
      this.count.set(this.initialValue());
    });
  }
}
```

**AfterViewInit Example:**
```typescript
// Older - View manipulation after view is ready
import { Component, ViewChild, AfterViewInit, ElementRef } from '@angular/core';

@Component({
  selector: 'app-search',
  template: `<input #searchInput type="text" placeholder="Search...">`
})
export class SearchComponent implements AfterViewInit {
  @ViewChild('searchInput') searchInput: ElementRef;

  ngAfterViewInit(): void {
    // Safe to manipulate DOM here
    this.searchInput.nativeElement.focus();
  }
}

// Modern - Same pattern works with standalone
import { Component, ViewChild, AfterViewInit, ElementRef } from '@angular/core';

@Component({
  selector: 'app-search',
  standalone: true,
  template: `<input #searchInput type="text" placeholder="Search...">`
})
export class SearchComponent implements AfterViewInit {
  @ViewChild('searchInput') searchInput!: ElementRef<HTMLInputElement>;

  ngAfterViewInit(): void {
    this.searchInput.nativeElement.focus();
  }
}
```

### Key Differences
- **All versions**: Core lifecycle methods remain the same
- **Pre-Angular 12**: Manual subscription cleanup in OnDestroy
- **Angular 12+**: `takeUntil` pattern preferred over manual unsubscribe
- **Angular 14+**: Signals eliminate need for lifecycle hooks in many cases
- **toSignal**: Automatic cleanup, no OnDestroy needed
- **effect()**: Side effects replace watch/DoCheck patterns

---

## RxJS Operators

### Description
Operators are functions that take an Observable as input and return a new Observable, enabling functional composition.

### Essential Operators

#### pipe, map
```typescript
// Pre-Angular 12 / Current
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

const users$ = this.http.get<User[]>('/api/users').pipe(
  tap(users => console.log('Raw data:', users)), // Side effect
  map(users => users.filter(u => u.active)) // Transform
);

// With map transformation
const userNames$ = users$.pipe(
  map(users => users.map(u => u.name))
);

// Nested transformation
const userInfo$ = this.userService.getUser(id).pipe(
  map(user => ({
    id: user.id,
    fullName: `${user.firstName} ${user.lastName}`,
    email: user.email
  }))
);
```

#### debounceTime, distinctUntilChanged
```typescript
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

// Search input with debounce
export class SearchComponent {
  searchTerm$ = new Subject<string>();

  searchResults$ = this.searchTerm$.pipe(
    debounceTime(300), // Wait 300ms after user stops typing
    distinctUntilChanged(), // Only if value actually changed
    switchMap(term => this.searchService.search(term))
  );

  onSearchInput(term: string): void {
    this.searchTerm$.next(term);
  }
}
```

#### mergeMap, concatMap, switchMap
```typescript
// mergeMap - Execute in parallel, order doesn't matter
const uploadFiles$ = files$.pipe(
  mergeMap(file => this.uploadService.upload(file))
);

// concatMap - Execute sequentially, order matters
const processQueue$ = items$.pipe(
  concatMap(item => this.processItem(item)) // Wait for each to complete
);

// switchMap - Cancel previous and switch to new (most common for routing)
const userProfile$ = userId$.pipe(
  switchMap(id => this.userService.getUser(id)) // Cancels previous request if id changes
);
```

#### forkJoin
```typescript
import { forkJoin } from 'rxjs';

// Wait for all requests to complete
const loadDashboard$ = forkJoin({
  users: this.userService.getUsers(),
  reports: this.reportService.getReports(),
  settings: this.settingsService.getSettings()
}).pipe(
  tap(({ users, reports, settings }) => {
    console.log('All data loaded');
  })
);

// Usage
loadDashboard$.subscribe(({ users, reports, settings }) => {
  this.users = users;
  this.reports = reports;
  this.settings = settings;
});
```

#### combineLatest
```typescript
import { combineLatest } from 'rxjs';

// Combine multiple sources, emit when any changes
const filteredUsers$ = combineLatest({
  users: this.userService.users$,
  filter: this.filterService.filter$,
  sort: this.sortService.sort$
}).pipe(
  map(({ users, filter, sort }) => {
    return users
      .filter(u => u.name.includes(filter))
      .sort((a, b) => sort === 'asc' ? a.name.localeCompare(b.name) : -1);
  })
);
```

#### takeUntil
```typescript
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

export class DataComponent implements OnDestroy {
  private destroy$ = new Subject<void>();
  
  ngOnInit(): void {
    this.userService.users$
      .pipe(takeUntil(this.destroy$))
      .subscribe(users => this.users = users);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

### Other Important Operators

```typescript
// filter - Conditional pass through
data$.pipe(
  filter(item => item.active)
)

// reduce - Accumulate values
values$.pipe(
  reduce((acc, val) => acc + val, 0) // Sum all values
)

// scan - Like reduce but emit intermediate results
clicks$.pipe(
  scan((count) => count + 1, 0) // Emit running count
)

// first / last - Get first/last emission
clicks$.pipe(first()) // Get first click
clicks$.pipe(last()) // Get last click

// skip / skipWhile - Skip emissions
data$.pipe(skip(2)) // Skip first 2 emissions
data$.pipe(skipWhile(x => x < 5)) // Skip while condition is true

// take / takeWhile - Take limited emissions
clicks$.pipe(take(5)) // Take first 5
clicks$.pipe(takeWhile(x => x < 5)) // Take while condition is true

// startWith - Emit value before source
data$.pipe(startWith(null)) // Emit null first, then data

// defaultIfEmpty - Emit if source completes without value
data$.pipe(defaultIfEmpty([])) // Emit [] if no data

// catchError - Handle errors
http.get(url).pipe(
  catchError(error => {
    console.error(error);
    return of(null); // Return fallback value
  })
)

// retry - Retry on error
http.get(url).pipe(
  retry(3) // Retry up to 3 times
)

// timeout - Emit error if no value within time
data$.pipe(
  timeout(5000) // Error if no value within 5s
)

// delay - Delay each emission
data$.pipe(
  delay(1000) // Delay by 1 second
)

// withLatestFrom - Combine with latest value from another observable
search$.pipe(
  withLatestFrom(filters$), // Get latest filters with each search
  map(([searchTerm, filters]) => ({ searchTerm, filters }))
)

// shareReplay - Share and replay last N values
const sharedData$ = expensiveOperation$.pipe(
  shareReplay(1) // All subscribers get last value
)
```

### Practical Examples

**Search with Debounce and Switch:**
```typescript
export class SearchComponent {
  private searchTerm$ = new Subject<string>();
  
  results$ = this.searchTerm$.pipe(
    debounceTime(300),
    distinctUntilChanged(),
    switchMap(term => 
      term ? this.searchService.search(term) : of([])
    ),
    catchError(error => {
      console.error('Search error:', error);
      return of([]);
    })
  );

  onSearch(term: string): void {
    this.searchTerm$.next(term);
  }
}
```

**Parallel Requests with forkJoin:**
```typescript
export class DashboardComponent implements OnInit {
  dashboardData$ = forkJoin({
    users: this.userService.getUsers(),
    analytics: this.analyticsService.getAnalytics(),
    notifications: this.notificationService.getNotifications()
  }).pipe(
    tap(data => console.log('Dashboard data loaded:', data)),
    catchError(error => {
      console.error('Error loading dashboard:', error);
      return of(null);
    })
  );

  ngOnInit(): void {
    this.dashboardData$.subscribe(data => {
      if (data) {
        this.displayDashboard(data);
      }
    });
  }
}
```

**Reactive Filter and Sort:**
```typescript
export class DataTableComponent {
  private filter$ = new Subject<string>();
  private sort$ = new Subject<'asc' | 'desc'>();
  data$ = new BehaviorSubject<Item[]>([]);

  filteredAndSorted$ = combineLatest({
    data: this.data$,
    filter: this.filter$.pipe(startWith('')),
    sort: this.sort$.pipe(startWith('asc'))
  }).pipe(
    map(({ data, filter, sort }) => {
      return data
        .filter(item => item.name.includes(filter))
        .sort((a, b) => 
          sort === 'asc' 
            ? a.name.localeCompare(b.name)
            : b.name.localeCompare(a.name)
        );
    }),
    debounceTime(200)
  );
}
```

### Key Differences
- **Core operators**: Remain consistent across Angular versions
- **Usage patterns**: `pipe()` syntax has remained standard
- **Modern approach**: Consider Signals for state, Observables for events/HTTP
- **Composition**: Functional operators enable powerful declarative chains
- **Performance**: Operators like `shareReplay()` prevent duplicate requests

---

## Forms

### Description
Angular provides two approaches for forms: Template-Driven (simple, less code) and Reactive (powerful, explicit, testable).

### Template-Driven Forms - Older Syntax (Pre-Angular 12)
```typescript
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  template: `
    <form #form="ngForm" (ngSubmit)="onSubmit(form)">
      <div>
        <label>Username:</label>
        <input 
          type="text" 
          name="username" 
          ngModel 
          required 
          minlength="3">
      </div>
      <div>
        <label>Password:</label>
        <input 
          type="password" 
          name="password" 
          ngModel 
          required>
      </div>
      <div>
        <label>Remember me:</label>
        <input type="checkbox" name="rememberMe" ngModel>
      </div>
      <button type="submit" [disabled]="!form.valid">Login</button>
    </form>
  `
})
export class LoginFormComponent {
  onSubmit(form: NgForm): void {
    if (form.valid) {
      console.log(form.value); // { username: '', password: '', rememberMe: false }
    }
  }
}
```

### Template-Driven Forms - Modern Syntax (Angular 14+)
```typescript
import { Component, signal } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <form #form="ngForm" (ngSubmit)="onSubmit(form)">
      <div>
        <label>Username:</label>
        <input 
          type="text" 
          name="username" 
          [(ngModel)]="username"
          #usernameField="ngModelControl"
          required 
          minlength="3">
        <span *ngIf="usernameField.errors?.['required']">Required</span>
        <span *ngIf="usernameField.errors?.['minlength']">Min 3 chars</span>
      </div>
      <div>
        <label>Password:</label>
        <input 
          type="password" 
          name="password" 
          [(ngModel)]="password"
          required>
      </div>
      <div>
        <label>Remember me:</label>
        <input type="checkbox" name="rememberMe" [(ngModel)]="rememberMe">
      </div>
      <button type="submit" [disabled]="!form.valid">Login</button>
    </form>
  `
})
export class LoginFormComponent {
  username = signal('');
  password = signal('');
  rememberMe = signal(false);

  onSubmit(form: NgForm): void {
    if (form.valid) {
      console.log({
        username: this.username(),
        password: this.password(),
        rememberMe: this.rememberMe()
      });
    }
  }
}
```

### Reactive Forms - Older Syntax (Pre-Angular 12)
```typescript
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-form',
  template: `
    <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
      <div>
        <label>First Name:</label>
        <input 
          type="text" 
          formControlName="firstName"
          [class.error]="isFieldInvalid('firstName')">
        <span *ngIf="getError('firstName', 'required')">Required</span>
      </div>
      <div>
        <label>Email:</label>
        <input 
          type="email" 
          formControlName="email"
          [class.error]="isFieldInvalid('email')">
        <span *ngIf="getError('email', 'required')">Required</span>
        <span *ngIf="getError('email', 'email')">Invalid email</span>
      </div>
      <div>
        <label>Password:</label>
        <input 
          type="password" 
          formControlName="password"
          [class.error]="isFieldInvalid('password')">
      </div>
      <button type="submit" [disabled]="!userForm.valid">Submit</button>
    </form>
  `
})
export class UserFormComponent implements OnInit {
  userForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.userForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  ngOnInit(): void {
    // Subscribe to form value changes
    this.userForm.valueChanges.subscribe(values => {
      console.log('Form values:', values);
    });
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      console.log('Form data:', this.userForm.value);
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.userForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  getError(fieldName: string, errorType: string): boolean {
    const field = this.userForm.get(fieldName);
    return !!(field && field.hasError(errorType) && (field.dirty || field.touched));
  }
}
```

### Reactive Forms - Current Syntax (Angular 12+)
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-user-form',
  template: `
    <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
      <div>
        <label>First Name:</label>
        <input 
          type="text" 
          formControlName="firstName"
          [class.error]="isFieldInvalid('firstName')">
        <app-error-message 
          [control]="userForm.get('firstName')">
        </app-error-message>
      </div>
      <div>
        <label>Email:</label>
        <input 
          type="email" 
          formControlName="email"
          [class.error]="isFieldInvalid('email')">
        <app-error-message 
          [control]="userForm.get('email')">
        </app-error-message>
      </div>
      <div formGroupName="address">
        <label>Street:</label>
        <input type="text" formControlName="street">
      </div>
      <button type="submit" [disabled]="!userForm.valid">Submit</button>
    </form>
  `
})
export class UserFormComponent implements OnInit, OnDestroy {
  userForm: FormGroup;
  private destroy$ = new Subject<void>();

  constructor(private readonly fb: FormBuilder) {
    this.userForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      address: this.fb.group({
        street: [''],
        city: ['']
      })
    });
  }

  ngOnInit(): void {
    this.userForm.statusChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe(status => console.log('Form status:', status));

    this.userForm.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe(values => console.log('Form values:', values));
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      console.log('Form data:', this.userForm.value);
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.userForm.get(fieldName);
    return !!(field?.invalid && (field?.dirty || field?.touched));
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

### Reactive Forms - Modern Syntax (Angular 14+ with Signals)
```typescript
import { Component, signal, computed, inject } from '@angular/core';
import { 
  FormBuilder, 
  FormGroup, 
  Validators, 
  ReactiveFormsModule,
  FormControl
} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
      <div>
        <label>First Name:</label>
        <input 
          type="text" 
          formControlName="firstName"
          [class.error]="isFieldInvalid('firstName')">
        <span *ngIf="getFieldError('firstName')">{{ getFieldError('firstName') }}</span>
      </div>
      <div>
        <label>Email:</label>
        <input 
          type="email" 
          formControlName="email"
          [class.error]="isFieldInvalid('email')">
        <span *ngIf="getFieldError('email')">{{ getFieldError('email') }}</span>
      </div>
      <div>
        <label>Subscribe to newsletter:</label>
        <input type="checkbox" formControlName="subscribe">
      </div>
      <button type="submit" [disabled]="!userForm.valid">Submit</button>
      <p *ngIf="submitSuccess()">Form submitted successfully!</p>
    </form>
  `
})
export class UserFormComponent {
  private fb = inject(FormBuilder);
  
  userForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.minLength(2)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    subscribe: [false]
  });

  submitSuccess = signal(false);
  isFormDirty = signal(false);

  // Computed validation state
  firstNameErrors = computed(() => {
    const control = this.userForm.get('firstName');
    if (!control || !control.touched) return '';
    if (control.errors?.['required']) return 'First name is required';
    if (control.errors?.['minlength']) return 'Minimum 2 characters';
    return '';
  });

  isFormValid = computed(() => this.userForm.valid);

  onSubmit(): void {
    if (this.userForm.valid) {
      const data = this.userForm.getRawValue();
      console.log('Submitting:', data);
      this.submitSuccess.set(true);
      setTimeout(() => this.submitSuccess.set(false), 3000);
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.userForm.get(fieldName);
    return !!(field?.invalid && (field?.dirty || field?.touched));
  }

  getFieldError(fieldName: string): string {
    const field = this.userForm.get(fieldName);
    if (!field || !field.touched) return '';
    if (field.errors?.['required']) return `${fieldName} is required`;
    if (field.errors?.['email']) return 'Invalid email format';
    if (field.errors?.['minlength']) {
      const minLength = field.errors['minlength'].requiredLength;
      return `Minimum ${minLength} characters required`;
    }
    return '';
  }
}
```

### Advanced: Dynamic Form Controls

```typescript
// Modern approach with FormArray
@Component({
  selector: 'app-dynamic-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <div formArrayName="emails">
        <div *ngFor="let email of emailControls(); let i = index" 
             [formGroupName]="i">
          <input 
            type="email" 
            formControlName="address"
            placeholder="Email {{ i + 1 }}">
          <button type="button" (click)="removeEmail(i)">Remove</button>
        </div>
      </div>
      <button type="button" (click)="addEmail()">Add Email</button>
      <button type="submit">Submit</button>
    </form>
  `
})
export class DynamicFormComponent {
  private fb = inject(FormBuilder);

  form = this.fb.group({
    emails: this.fb.array([
      this.fb.group({ address: ['', Validators.email] })
    ])
  });

  emailControls = computed(() => 
    (this.form.get('emails') as FormArray).controls
  );

  addEmail(): void {
    const emails = this.form.get('emails') as FormArray;
    emails.push(this.fb.group({ address: ['', Validators.email] }));
  }

  removeEmail(index: number): void {
    const emails = this.form.get('emails') as FormArray;
    emails.removeAt(index);
  }

  onSubmit(): void {
    console.log(this.form.value);
  }
}
```

### Key Differences
- **Template-Driven**: Simpler, less code, good for simple forms
- **Reactive**: More powerful, explicit, better for complex validation and dynamic forms
- **Pre-Angular 12**: Manual subscription management
- **Angular 12+**: FormArray and nested groups support
- **Angular 14+**: Can use Signals with forms for reactive state
- **Modern approach**: Reactive forms preferred, custom components for reusable form logic

---

## Custom Validation

### Description
Custom validators extend Angular's built-in validators for domain-specific validation rules.

### Sync Validator - Older Syntax (Pre-Angular 12)
```typescript
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

// Custom validator function
export function passwordStrengthValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null; // Don't validate empty values
    }

    const hasNumber = /[0-9]/.test(value);
    const hasUpper = /[A-Z]/.test(value);
    const hasSpecial = /[!@#$%^&*]/.test(value);

    const passwordValid = hasNumber && hasUpper && hasSpecial;

    return !passwordValid ? { 'passwordStrength': true } : null;
  };
}

// Usage in form
@Component({...})
export class MyComponent {
  form = new FormGroup({
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      passwordStrengthValidator()
    ])
  });
}
```

### Async Validator - Older Syntax (Pre-Angular 12)
```typescript
import { AbstractControl, ValidationErrors, AsyncValidatorFn } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, debounceTime } from 'rxjs/operators';

// Check if email already exists
export function emailExistsValidator(userService: UserService): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    if (!control.value) {
      return of(null);
    }

    return userService.checkEmailExists(control.value).pipe(
      debounceTime(300),
      map(exists => exists ? { 'emailExists': true } : null)
    );
  };
}

// Usage
@Component({...})
export class RegisterComponent implements OnInit {
  form: FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService) {
    this.form = this.fb.group({
      email: [
        '',
        [Validators.required, Validators.email],
        [emailExistsValidator(this.userService)] // Async validators as 3rd param
      ]
    });
  }
}
```

### Cross-Field Validator - Older Syntax (Pre-Angular 12)
```typescript
// Validate that password and confirmPassword match
export function passwordMatchValidator(): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;

    return password === confirmPassword 
      ? null 
      : { 'passwordMismatch': true };
  };
}

// Usage on FormGroup
@Component({...})
export class ResetPasswordComponent {
  form = new FormGroup(
    {
      password: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required)
    },
    passwordMatchValidator()
  );
}
```

### Modern Custom Validation (Angular 12+)
```typescript
import { AbstractControl, ValidationErrors, ValidatorFn, AsyncValidatorFn } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, debounceTime, catchError } from 'rxjs/operators';

// Sync validator with better typing
export function passwordStrengthValidator(): ValidatorFn {
  return (control: AbstractControl<string | null>): ValidationErrors | null => {
    const value = control.value;

    if (!value) return null;

    const requirements = {
      hasNumber: /[0-9]/.test(value),
      hasUpper: /[A-Z]/.test(value),
      hasSpecial: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(value),
      hasMinLength: value.length >= 8
    };

    const allMet = Object.values(requirements).every(val => val);

    return !allMet ? { passwordStrength: requirements } : null;
  };
}

// Async validator
export function emailExistsValidator(userService: UserService): AsyncValidatorFn {
  return (control: AbstractControl<string | null>): Observable<ValidationErrors | null> => {
    if (!control.value) {
      return of(null);
    }

    return userService.checkEmailExists(control.value).pipe(
      debounceTime(300),
      map(exists => exists ? { emailExists: true } : null),
      catchError(() => of(null)) // Handle service errors gracefully
    );
  };
}

// Cross-field validator
export function passwordMatchValidator(): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;

    if (!password || !confirmPassword) return null;

    return password === confirmPassword 
      ? null 
      : { passwordMismatch: { password, confirmPassword } };
  };
}

// Min value validator
export function minValueValidator(min: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    
    if (value === null || value === undefined || value === '') {
      return null;
    }

    return value >= min ? null : { minValue: { min, actual: value } };
  };
}

// Pattern with custom message
export function phoneNumberValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;

    const phoneRegex = /^(\+1)?[-.\s]?\(?[0-9]{3}\)?[-.\s]?[0-9]{3}[-.\s]?[0-9]{4}$/;
    return phoneRegex.test(control.value) 
      ? null 
      : { invalidPhone: { value: control.value } };
  };
}
```

### Usage in Angular 14+ with Reactive Forms
```typescript
import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <div>
        <label>Email:</label>
        <input type="email" formControlName="email">
        <span *ngIf="form.get('email')?.hasError('emailExists')">
          Email already registered
        </span>
      </div>

      <div>
        <label>Password:</label>
        <input type="password" formControlName="password">
        <div *ngIf="form.get('password')?.errors?.['passwordStrength'] as strengths">
          <span *ngIf="!strengths.hasNumber">Need number</span>
          <span *ngIf="!strengths.hasUpper">Need uppercase</span>
          <span *ngIf="!strengths.hasSpecial">Need special char</span>
          <span *ngIf="!strengths.hasMinLength">Min 8 chars</span>
        </div>
      </div>

      <div>
        <label>Confirm Password:</label>
        <input type="password" formControlName="confirmPassword">
        <span *ngIf="form.hasError('passwordMismatch') && form.get('confirmPassword')?.touched">
          Passwords do not match
        </span>
      </div>

      <div>
        <label>Phone:</label>
        <input type="tel" formControlName="phone">
        <span *ngIf="form.get('phone')?.hasError('invalidPhone')">
          Invalid phone format
        </span>
      </div>

      <button type="submit" [disabled]="form.invalid">Register</button>
    </form>
  `
})
export class RegistrationComponent {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);

  form = this.fb.group(
    {
      email: [
        '',
        [Validators.required, Validators.email],
        [emailExistsValidator(this.userService)]
      ],
      password: [
        '',
        [Validators.required, passwordStrengthValidator()]
      ],
      confirmPassword: ['', Validators.required],
      phone: ['', phoneNumberValidator()]
    },
    { validators: passwordMatchValidator() }
  );

  onSubmit(): void {
    if (this.form.valid) {
      console.log(this.form.getRawValue());
    }
  }
}
```

### Reusable Validator Decorators Pattern
```typescript
// For class-based validation
export class CustomValidators {
  static passwordStrength(): ValidatorFn {
    return passwordStrengthValidator();
  }

  static emailExists(userService: UserService): AsyncValidatorFn {
    return emailExistsValidator(userService);
  }

  static passwordMatch(): ValidatorFn {
    return passwordMatchValidator();
  }

  static minValue(min: number): ValidatorFn {
    return minValueValidator(min);
  }

  static phone(): ValidatorFn {
    return phoneNumberValidator();
  }

  static unique(service: any, fieldName: string): AsyncValidatorFn {
    return (control: AbstractControl) => {
      return service.checkUnique(fieldName, control.value).pipe(
        map(exists => exists ? { unique: true } : null),
        catchError(() => of(null))
      );
    };
  }
}

// Usage
form = this.fb.group({
  email: [
    '',
    [Validators.required, Validators.email],
    [CustomValidators.emailExists(this.userService)]
  ],
  password: ['', CustomValidators.passwordStrength()],
  phone: ['', CustomValidators.phone()]
});
```

### Key Differences
- **Pre-Angular 12**: Basic validator structure, manual error handling
- **Angular 12+**: Better TypeScript typing, improved error objects
- **Async validation**: Debounce and error handling patterns essential
- **Composition**: Chain multiple validators easily
- **Custom patterns**: Reusable validator classes for consistency
- **Modern approach**: Use custom validators with reactive forms for maximum control

---

## Signals

### Description
Signals (Angular 16+) provide a new reactivity primitive that's simpler and more performant than Observables for state management.

### Signal Basics

```typescript
import { signal, computed, effect } from '@angular/core';

// Create a signal
const count = signal(0);

// Read signal value - must call as function
console.log(count()); // 0

// Update signal
count.set(1);
count.update(val => val + 1); // Now 2

// Computed signal - automatically tracks dependencies
const doubled = computed(() => count() * 2);
console.log(doubled()); // 4

// Effect - runs whenever dependencies change
effect(() => {
  console.log(`Count is now ${count()}`);
}); // Logs when count changes
```

### Component with Signals
```typescript
import { Component, signal, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from './user.service';

interface User {
  id: number;
  name: string;
  email: string;
}

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div>
      <h2>Users ({{ userCount() }})</h2>
      
      <div *ngIf="loading()">Loading...</div>
      
      <ul *ngIf="!loading()">
        <li *ngFor="let user of users()">
          {{ user.name }} - {{ user.email }}
          <button (click)="deleteUser(user.id)">Delete</button>
        </li>
      </ul>

      <div *ngIf="error()">{{ error() }}</div>

      <input 
        type="text" 
        placeholder="New user name"
        [(ngModel)]="newUserName">
      <button (click)="addUser()">Add User</button>
    </div>
  `
})
export class UserListComponent {
  private userService = inject(UserService);

  // State signals
  users = signal<User[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  newUserName = signal('');

  // Computed signals
  userCount = computed(() => this.users().length);
  hasUsers = computed(() => this.userCount() > 0);
  isEmpty = computed(() => this.userCount() === 0);

  constructor() {
    // Side effect: auto-load users on component creation
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading.set(true);
    this.error.set(null);

    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users.set(users);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err.message);
        this.loading.set(false);
      }
    });
  }

  addUser(): void {
    const name = this.newUserName().trim();
    if (!name) return;

    const newUser: User = {
      id: Math.max(0, ...this.users().map(u => u.id)) + 1,
      name,
      email: `${name.toLowerCase()}@example.com`
    };

    this.users.update(users => [...users, newUser]);
    this.newUserName.set('');
  }

  deleteUser(id: number): void {
    this.users.update(users => users.filter(u => u.id !== id));
  }
}
```

### Signals vs Observables

```typescript
// OBSERVABLES (for async events, HTTP, streams)
export class EventService {
  private events$ = new Subject<Event>();
  events$: Observable<Event> = this.events$.asObservable();

  emit(event: Event): void {
    this.events$.next(event);
  }
}

// SIGNALS (for synchronous state)
export class CounterService {
  count = signal(0);
  
  increment(): void {
    this.count.update(c => c + 1);
  }
}

// HYBRID (Signals + Observables)
export class HybridService {
  // State as signal
  private selectedUser = signal<User | null>(null);

  // Observable from signal
  selectedUser$ = toObservable(this.selectedUser);

  // Observable to signal
  users = toSignal(this.http.get<User[]>('/api/users'));
}
```

### Input/Output with Signals

```typescript
import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-child',
  standalone: true,
  template: `
    <p>Name: {{ name() }}</p>
    <p>Age: {{ age() }}</p>
    <button (click)="onDelete()">Delete</button>
  `
})
export class ChildComponent {
  // Input signals (read-only by default)
  name = input<string>('');
  age = input<number>(0);

  // Output signal
  delete = output<void>();

  onDelete(): void {
    this.delete.emit();
  }
}

// Parent component
@Component({
  selector: 'app-parent',
  template: `
    <app-child 
      [name]="userName()"
      [age]="userAge()"
      (delete)="onUserDelete()">
    </app-child>
  `
})
export class ParentComponent {
  userName = signal('John');
  userAge = signal(30);

  onUserDelete(): void {
    console.log('User deleted');
  }
}
```

### Effect Hook

```typescript
import { Component, signal, effect } from '@angular/core';

@Component({
  selector: 'app-effect-demo',
  standalone: true,
  template: `
    <input [(ngModel)]="text">
    <p>{{ text() }}</p>
    <p>Length: {{ textLength() }}</p>
  `
})
export class EffectDemoComponent {
  text = signal('');
  textLength = signal(0);

  constructor() {
    // Effect 1: Auto-update length whenever text changes
    effect(() => {
      this.textLength.set(this.text().length);
    });

    // Effect 2: Log changes
    effect(() => {
      console.log(`Text changed to: "${this.text()}"`);
    });

    // Effect 3: Complex side effect
    effect(() => {
      const currentText = this.text();
      // Could trigger API calls, storage updates, etc.
      if (currentText.length > 10) {
        this.saveToLocalStorage(currentText);
      }
    });
  }

  saveToLocalStorage(text: string): void {
    localStorage.setItem('draft', text);
  }
}
```

### Signal Inputs with Required and Aliases

```typescript
import { Component, input } from '@angular/core';

@Component({
  selector: 'app-product',
  standalone: true,
  template: `
    <div>
      <h3>{{ productName() }}</h3>
      <p>Price: ${{ productPrice() }}</p>
      <p>In Stock: {{ inStock() }}</p>
    </div>
  `
})
export class ProductComponent {
  // Required input
  productName = input.required<string>();

  // Optional with default
  productPrice = input(0);

  // Alias for input name
  inStock = input(true, { alias: 'stockStatus' });
}

// Usage
@Component({
  template: `
    <app-product 
      [productName]="'Laptop'"
      [productPrice]="999"
      [stockStatus]="true">
    </app-product>
  `
})
export class ParentComponent { }
```

### Advanced: Signal-Based Forms

```typescript
import { Component, signal, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface FormValue {
  name: string;
  email: string;
  age: number;
}

@Component({
  selector: 'app-signal-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <form (ngSubmit)="onSubmit()">
      <div>
        <input 
          type="text" 
          placeholder="Name"
          [(ngModel)]="name"
          name="name">
      </div>
      <div>
        <input 
          type="email" 
          placeholder="Email"
          [(ngModel)]="email"
          name="email">
      </div>
      <div>
        <input 
          type="number" 
          placeholder="Age"
          [(ngModel)]="age"
          name="age">
      </div>
      
      <p>Is Valid: {{ isValid() }}</p>
      <p *ngIf="name() || email() || age() > 0">Form Data: {{ formValue() | json }}</p>
      
      <button type="submit" [disabled]="!isValid()">Submit</button>
    </form>
  `
})
export class SignalFormComponent {
  // Form state as signals
  name = signal('');
  email = signal('');
  age = signal(0);

  // Computed form value
  formValue = computed<FormValue>(() => ({
    name: this.name(),
    email: this.email(),
    age: this.age()
  }));

  // Computed validation
  isValid = computed(() => {
    const value = this.formValue();
    return value.name.length > 0 
      && value.email.includes('@') 
      && value.age > 0;
  });

  constructor() {
    // Log form changes
    effect(() => {
      console.log('Form changed:', this.formValue());
    });
  }

  onSubmit(): void {
    if (this.isValid()) {
      console.log('Submitting:', this.formValue());
    }
  }
}
```

### Key Differences
- **Signals**: Synchronous, simpler, better for state
- **Observables**: Asynchronous, more powerful for streams
- **Computed**: Automatically track dependencies, cached results
- **Effect**: Run side effects reactively
- **Performance**: Signals are more granular, better change detection
- **Modern approach**: Use Signals for state, Observables for HTTP/async events

---

## Summary & Quick Reference

| Feature | Pre-Angular 12 | Angular 12+ | Angular 14+ | Angular 16+ |
|---------|---|---|---|---|
| **Bootstrapping** | `platformBrowserDynamic()` | Same | `bootstrapApplication()` | Same |
| **Modules** | Required NgModule | Same | Optional (Standalone) | Deprecated |
| **Services** | Constructor injection | Same | `inject()` function | With Signals |
| **Components** | Module declaration | Same | `standalone: true` | With Signals |
| **Directives** | Module declaration | Same | `standalone: true` | With @Input signals |
| **Pipes** | Module declaration | Same | `standalone: true` | Can use Signals |
| **Guards** | Class-based | Return UrlTree | Functional (Recommended) | Built-in patterns |
| **Interceptors** | Class-based | Same | Functional (Recommended) | Functional preferred |
| **Forms** | Template & Reactive | Same | Reactive preferred | Signals optional |
| **State** | BehaviorSubject | Observable | Signals | **Signals preferred** |
| **Change Detection** | Manual | OnPush default | Signals better | Granular with Signals |

---

## Best Practices & Recommendations

### For Legacy/Old Codebases
- Maintain NgModule structure
- Use RxJS Observables with `takeUntil` pattern
- Class-based guards and interceptors
- Template-driven forms for simple cases

### For Modern Angular Projects
- Use standalone components and APIs
- Prefer Signals for state management
- Functional guards and interceptors
- Reactive forms with custom validation
- Effects for side effects instead of subscriptions

### Performance Tips
- Use `ChangeDetectionStrategy.OnPush` with Observables
- Signals handle change detection automatically
- Lazy load routes and features
- Use `trackBy` in `*ngFor` loops
- Implement virtual scrolling for large lists
- Unsubscribe properly (takeUntil or Signals with toSignal)

### When to Use Each Pattern
- **BehaviorSubject**: Legacy code, shared state across components
- **Signals**: New projects, simple state management
- **Observables**: HTTP requests, user input streams
- **toSignal()**: Integrate async sources into signal-based components
- **toObservable()**: Expose signals as observables for legacy code

