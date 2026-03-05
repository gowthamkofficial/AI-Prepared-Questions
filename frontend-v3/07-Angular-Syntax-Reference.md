# Angular Syntax Reference Guide

## **Component Syntax**

### Basic Component
```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  name = 'John';
  age = 30;
}
```

### Inline Template & Styles
```typescript
@Component({
  selector: 'app-inline',
  template: `
    <h1>{{title}}</h1>
    <p>{{description}}</p>
  `,
  styles: [`
    h1 { color: blue; }
    p { font-size: 14px; }
  `]
})
```

### Standalone Component (Angular 14+)
```typescript
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-standalone',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `<h1>Standalone Component</h1>`
})
export class StandaloneComponent {}
```

---

## **Data Binding Syntax**

### Interpolation
```html
<!-- String interpolation -->
<h1>{{title}}</h1>
<p>{{firstName + ' ' + lastName}}</p>
<p>{{2 + 2}}</p>
<p>{{getFullName()}}</p>
```

### Property Binding
```html
<!-- Element properties -->
<img [src]="imageUrl">
<button [disabled]="isDisabled">Click</button>
<input [value]="username">
<div [innerHTML]="htmlContent"></div>

<!-- Class binding -->
<div [class.active]="isActive"></div>
<div [class]="'btn btn-primary'"></div>

<!-- Style binding -->
<div [style.color]="textColor"></div>
<div [style.font-size.px]="fontSize"></div>
```

### Event Binding
```html
<!-- Click events -->
<button (click)="handleClick()">Click</button>
<button (click)="handleClick($event)">Click with event</button>

<!-- Input events -->
<input (input)="onInput($event)">
<input (change)="onChange($event)">
<input (keyup)="onKeyUp($event)">
<input (keyup.enter)="onEnter()">

<!-- Mouse events -->
<div (mouseenter)="onMouseEnter()"></div>
<div (mouseleave)="onMouseLeave()"></div>

<!-- Form events -->
<form (submit)="onSubmit($event)">
<form (ngSubmit)="onSubmit()">
```

### Two-Way Binding
```html
<!-- NgModel -->
<input [(ngModel)]="username">

<!-- Equivalent to -->
<input [ngModel]="username" (ngModelChange)="username = $event">

<!-- Custom two-way binding -->
<app-counter [(count)]="value"></app-counter>
```

---

## **Directives Syntax**

### Structural Directives

#### *ngIf
```html
<!-- Basic -->
<div *ngIf="isVisible">Content</div>

<!-- With else -->
<div *ngIf="isLoggedIn; else loginTemplate">
  Welcome back!
</div>
<ng-template #loginTemplate>
  Please login
</ng-template>

<!-- With then/else -->
<div *ngIf="isLoading; then loading else content"></div>
<ng-template #loading>Loading...</ng-template>
<ng-template #content>Content loaded</ng-template>

<!-- Angular 17+ new syntax -->
@if (isVisible) {
  <p>Visible</p>
} @else if (isHidden) {
  <p>Hidden</p>
} @else {
  <p>Default</p>
}
```

#### *ngFor
```html
<!-- Basic -->
<div *ngFor="let item of items">{{item}}</div>

<!-- With index -->
<div *ngFor="let item of items; let i = index">
  {{i}}: {{item}}
</div>

<!-- With trackBy -->
<div *ngFor="let item of items; trackBy: trackById">
  {{item.name}}
</div>

<!-- All variables -->
<div *ngFor="let item of items; 
             let i = index;
             let first = first;
             let last = last;
             let even = even;
             let odd = odd">
  {{i}}: {{item}}
</div>

<!-- Angular 17+ new syntax -->
@for (item of items; track item.id) {
  <div>{{item.name}}</div>
} @empty {
  <p>No items</p>
}
```

#### *ngSwitch
```html
<div [ngSwitch]="value">
  <p *ngSwitchCase="1">One</p>
  <p *ngSwitchCase="2">Two</p>
  <p *ngSwitchCase="3">Three</p>
  <p *ngSwitchDefault>Other</p>
</div>

<!-- Angular 17+ new syntax -->
@switch (value) {
  @case (1) { <p>One</p> }
  @case (2) { <p>Two</p> }
  @default { <p>Other</p> }
}
```

### Attribute Directives

#### ngClass
```html
<!-- Object syntax -->
<div [ngClass]="{'active': isActive, 'disabled': isDisabled}"></div>

<!-- Array syntax -->
<div [ngClass]="['btn', 'btn-primary']"></div>

<!-- String syntax -->
<div [ngClass]="'btn btn-primary'"></div>

<!-- Method -->
<div [ngClass]="getClasses()"></div>
```

#### ngStyle
```html
<!-- Object syntax -->
<div [ngStyle]="{'color': textColor, 'font-size': fontSize + 'px'}"></div>

<!-- Method -->
<div [ngStyle]="getStyles()"></div>
```

### Custom Directive
```typescript
import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input() appHighlight = 'yellow';
  
  constructor(private el: ElementRef) {}
  
  @HostListener('mouseenter') onMouseEnter() {
    this.highlight(this.appHighlight);
  }
  
  @HostListener('mouseleave') onMouseLeave() {
    this.highlight('');
  }
  
  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}

// Usage
<p appHighlight>Hover me</p>
<p [appHighlight]="'lightblue'">Hover me</p>
```

---

## **Pipes Syntax**

### Built-in Pipes
```html
<!-- Date pipe -->
{{ date | date }}
{{ date | date:'short' }}
{{ date | date:'dd/MM/yyyy' }}
{{ date | date:'fullDate' }}

<!-- Currency pipe -->
{{ price | currency }}
{{ price | currency:'USD' }}
{{ price | currency:'EUR':'symbol':'1.2-2' }}

<!-- Number pipe -->
{{ number | number }}
{{ number | number:'1.2-2' }}

<!-- Percent pipe -->
{{ value | percent }}
{{ value | percent:'1.2-2' }}

<!-- Text pipes -->
{{ text | uppercase }}
{{ text | lowercase }}
{{ text | titlecase }}

<!-- JSON pipe -->
{{ object | json }}

<!-- Slice pipe -->
{{ array | slice:0:5 }}
{{ text | slice:0:10 }}

<!-- Async pipe -->
{{ observable$ | async }}
{{ promise | async }}

<!-- Chaining pipes -->
{{ date | date:'short' | uppercase }}
```

### Custom Pipe
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'reverse'
})
export class ReversePipe implements PipeTransform {
  transform(value: string): string {
    return value.split('').reverse().join('');
  }
}

// With parameters
@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 10, trail: string = '...'): string {
    return value.length > limit 
      ? value.substring(0, limit) + trail 
      : value;
  }
}

// Usage
{{ 'hello' | reverse }}
{{ longText | truncate:20:'...' }}
```

### Pure vs Impure Pipe
```typescript
// Pure pipe (default)
@Pipe({
  name: 'pure',
  pure: true
})

// Impure pipe
@Pipe({
  name: 'impure',
  pure: false
})
```

---

## **Forms Syntax**

### Template-Driven Forms
```html
<form #userForm="ngForm" (ngSubmit)="onSubmit(userForm)">
  <!-- Basic input -->
  <input name="username" 
         [(ngModel)]="user.username" 
         required 
         minlength="3">
  
  <!-- Email validation -->
  <input name="email" 
         [(ngModel)]="user.email" 
         required 
         email>
  
  <!-- Pattern validation -->
  <input name="phone" 
         [(ngModel)]="user.phone" 
         pattern="[0-9]{10}">
  
  <!-- Validation messages -->
  <div *ngIf="userForm.controls['username']?.invalid && 
              userForm.controls['username']?.touched">
    <p *ngIf="userForm.controls['username']?.errors?.['required']">
      Username is required
    </p>
    <p *ngIf="userForm.controls['username']?.errors?.['minlength']">
      Minimum 3 characters
    </p>
  </div>
  
  <button [disabled]="!userForm.valid">Submit</button>
</form>
```

### Reactive Forms
```typescript
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

export class UserComponent {
  userForm: FormGroup;
  
  constructor(private fb: FormBuilder) {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      age: ['', [Validators.min(18), Validators.max(100)]],
      address: this.fb.group({
        street: [''],
        city: [''],
        zip: ['']
      })
    });
  }
  
  onSubmit() {
    if (this.userForm.valid) {
      console.log(this.userForm.value);
    }
  }
}
```

```html
<form [formGroup]="userForm" (ngSubmit)="onSubmit()">
  <input formControlName="username">
  
  <div *ngIf="userForm.get('username')?.invalid && 
              userForm.get('username')?.touched">
    <p *ngIf="userForm.get('username')?.errors?.['required']">Required</p>
    <p *ngIf="userForm.get('username')?.errors?.['minlength']">Min 3 chars</p>
  </div>
  
  <!-- Nested form group -->
  <div formGroupName="address">
    <input formControlName="street">
    <input formControlName="city">
    <input formControlName="zip">
  </div>
  
  <button [disabled]="!userForm.valid">Submit</button>
</form>
```

---

## **Route Guards Syntax**

### CanActivate
```typescript
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}
  
  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}

// Functional guard (Angular 15+)
export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  if (authService.isLoggedIn()) {
    return true;
  }
  return router.createUrlTree(['/login']);
};
```

### CanDeactivate
```typescript
export interface CanComponentDeactivate {
  canDeactivate: () => boolean | Observable<boolean>;
}

@Injectable({ providedIn: 'root' })
export class UnsavedChangesGuard implements CanDeactivate<CanComponentDeactivate> {
  canDeactivate(component: CanComponentDeactivate): boolean {
    return component.canDeactivate ? 
      component.canDeactivate() : 
      true;
  }
}
```

### CanLoad
```typescript
@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanLoad {
  canLoad(): boolean {
    return this.authService.isAdmin();
  }
}
```

### Resolve
```typescript
@Injectable({ providedIn: 'root' })
export class UserResolver implements Resolve<User> {
  constructor(private userService: UserService) {}
  
  resolve(route: ActivatedRouteSnapshot): Observable<User> {
    return this.userService.getUser(route.params['id']);
  }
}

// Usage in routes
const routes: Routes = [
  {
    path: 'user/:id',
    component: UserComponent,
    resolve: { user: UserResolver }
  }
];

// Access resolved data
constructor(private route: ActivatedRoute) {
  this.route.data.subscribe(data => {
    this.user = data['user'];
  });
}
```

---

## **HTTP Interceptor Syntax**

```typescript
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Clone and modify request
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${this.getToken()}`)
    });
    
    return next.handle(authReq).pipe(
      catchError(error => {
        if (error.status === 401) {
          // Handle unauthorized
        }
        return throwError(() => error);
      }),
      finalize(() => {
        // Cleanup
      })
    );
  }
  
  private getToken(): string {
    return localStorage.getItem('token') || '';
  }
}

// Register interceptor
@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
})
```

---

## **Signals Syntax (Angular 16+)**

```typescript
import { signal, computed, effect } from '@angular/core';

export class CounterComponent {
  // Create signal
  count = signal(0);
  
  // Computed signal
  doubleCount = computed(() => this.count() * 2);
  
  // Effect
  constructor() {
    effect(() => {
      console.log('Count:', this.count());
    });
  }
  
  // Update methods
  increment() {
    this.count.set(this.count() + 1);
    // or
    this.count.update(value => value + 1);
  }
  
  // Mutate (for objects/arrays)
  user = signal({ name: 'John', age: 30 });
  
  updateAge() {
    this.user.mutate(u => u.age = 31);
  }
}

// Template
<p>Count: {{count()}}</p>
<p>Double: {{doubleCount()}}</p>
<button (click)="increment()">+</button>
```

---

## **Routing Syntax**

```typescript
// Define routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'user/:id', component: UserComponent },
  { 
    path: 'admin', 
    component: AdminComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'users', component: UsersComponent },
      { path: 'settings', component: SettingsComponent }
    ]
  },
  { 
    path: 'lazy',
    loadChildren: () => import('./lazy/lazy.module').then(m => m.LazyModule)
  },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
```

```html
<!-- Template -->
<nav>
  <a routerLink="/">Home</a>
  <a routerLink="/about">About</a>
  <a [routerLink]="['/user', userId]">User</a>
  <a routerLink="/admin" routerLinkActive="active">Admin</a>
</nav>

<router-outlet></router-outlet>
```

```typescript
// Programmatic navigation
constructor(
  private router: Router,
  private route: ActivatedRoute
) {}

navigate() {
  this.router.navigate(['/about']);
  this.router.navigate(['/user', 123]);
  this.router.navigate(['../sibling'], { relativeTo: this.route });
}

// Get params
this.route.params.subscribe(params => {
  console.log(params['id']);
});

// Get query params
this.route.queryParams.subscribe(params => {
  console.log(params['search']);
});
```

---

## **Quick Reference Table**

| Feature | Syntax | Example |
|---------|--------|---------|
| Interpolation | `{{}}` | `{{name}}` |
| Property Binding | `[property]` | `[src]="url"` |
| Event Binding | `(event)` | `(click)="fn()"` |
| Two-way Binding | `[(ngModel)]` | `[(ngModel)]="name"` |
| Structural Directive | `*directive` | `*ngIf="show"` |
| Attribute Directive | `[directive]` | `[ngClass]="classes"` |
| Pipe | `\|` | `{{date \| date}}` |
| Template Variable | `#var` | `<input #name>` |
| Safe Navigation | `?.` | `{{user?.name}}` |
| Non-null Assertion | `!` | `{{user!.name}}` |

---

**Total: Complete syntax reference for all Angular features**
