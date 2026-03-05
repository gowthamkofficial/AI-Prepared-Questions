# Angular - Complete Interview Questions (Part 1)

## **Angular Basics**

### 1. What is Angular?
**Answer:** TypeScript-based open-source framework for building single-page applications (SPAs). Developed and maintained by Google.

**Key Features:**
- Component-based architecture
- Two-way data binding
- Dependency injection
- RxJS for reactive programming
- TypeScript support
- CLI for scaffolding
- Built-in routing
- Form handling

### 2. Angular vs AngularJS?
**Answer:**

| Feature | AngularJS (1.x) | Angular (2+) |
|---------|-----------------|--------------|
| Language | JavaScript | TypeScript |
| Architecture | MVC | Component-based |
| Mobile Support | No | Yes |
| Performance | Slower | Faster |
| CLI | No | Yes |
| Dependency Injection | Limited | Advanced |

### 3. What are components?
**Answer:** Building blocks of Angular applications with template, styles, and logic.

```typescript
@Component({
  selector: 'app-user',
  template: `
    <h1>{{name}}</h1>
    <button (click)="greet()">Greet</button>
  `,
  styles: [`
    h1 { color: blue; }
  `]
})
export class UserComponent {
  name = 'John';
  
  greet() {
    console.log(`Hello, ${this.name}`);
  }
}
```

### 4. What are component lifecycle hooks?
**Answer:**

```typescript
export class MyComponent implements OnInit, OnDestroy {
  // 1. Constructor - DI happens here
  constructor(private service: MyService) {}
  
  // 2. ngOnChanges - when input properties change
  ngOnChanges(changes: SimpleChanges) {
    console.log('Input changed', changes);
  }
  
  // 3. ngOnInit - after first ngOnChanges
  ngOnInit() {
    console.log('Component initialized');
  }
  
  // 4. ngDoCheck - custom change detection
  ngDoCheck() {
    console.log('Change detection run');
  }
  
  // 5. ngAfterContentInit - after content projection
  ngAfterContentInit() {
    console.log('Content projected');
  }
  
  // 6. ngAfterContentChecked - after content checked
  ngAfterContentChecked() {
    console.log('Content checked');
  }
  
  // 7. ngAfterViewInit - after view initialization
  ngAfterViewInit() {
    console.log('View initialized');
  }
  
  // 8. ngAfterViewChecked - after view checked
  ngAfterViewChecked() {
    console.log('View checked');
  }
  
  // 9. ngOnDestroy - cleanup before destruction
  ngOnDestroy() {
    console.log('Component destroyed');
  }
}
```

### 5. What are the types of data binding?
**Answer:**

```typescript
@Component({
  template: `
    <!-- 1. Interpolation (one-way: component to view) -->
    <h1>{{title}}</h1>
    <p>{{1 + 1}}</p>
    
    <!-- 2. Property binding (one-way: component to view) -->
    <img [src]="imageUrl">
    <button [disabled]="isDisabled">Click</button>
    
    <!-- 3. Event binding (one-way: view to component) -->
    <button (click)="handleClick()">Click</button>
    <input (input)="onInput($event)">
    
    <!-- 4. Two-way binding -->
    <input [(ngModel)]="name">
  `
})
export class AppComponent {
  title = 'My App';
  imageUrl = 'logo.png';
  isDisabled = false;
  name = 'John';
  
  handleClick() {
    console.log('Clicked');
  }
  
  onInput(event: Event) {
    console.log((event.target as HTMLInputElement).value);
  }
}
```

### 6. What are directives?
**Answer:** Instructions to the DOM.

**Types:**
1. **Component Directives** - with template
2. **Structural Directives** - modify DOM structure
3. **Attribute Directives** - modify appearance/behavior

```typescript
// Structural directives
<div *ngIf="isVisible">Visible</div>
<div *ngFor="let item of items">{{item}}</div>
<div [ngSwitch]="value">
  <p *ngSwitchCase="1">One</p>
  <p *ngSwitchCase="2">Two</p>
  <p *ngSwitchDefault>Other</p>
</div>

// Attribute directives
<div [ngClass]="{'active': isActive, 'disabled': isDisabled}">
<div [ngStyle]="{'color': textColor, 'font-size': fontSize}">
<div [class.active]="isActive">
<div [style.color]="textColor">

// Custom directive
@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  constructor(private el: ElementRef) {
    el.nativeElement.style.backgroundColor = 'yellow';
  }
}
```

### 7. What is the difference between *ngIf and [hidden]?
**Answer:**

```typescript
// *ngIf - removes from DOM
<div *ngIf="isVisible">Content</div>

// [hidden] - CSS display: none
<div [hidden]="!isVisible">Content</div>
```

**Comparison:**
- `*ngIf`: Removes element from DOM, better for large content
- `[hidden]`: Keeps in DOM, better for frequently toggled content

### 8. What is dependency injection?
**Answer:** Design pattern where dependencies are provided to a class rather than created by it.

```typescript
// Service
@Injectable({ providedIn: 'root' })
export class UserService {
  getUsers() {
    return ['John', 'Jane'];
  }
}

// Component
@Component({
  selector: 'app-users'
})
export class UsersComponent {
  users: string[];
  
  // DI happens in constructor
  constructor(private userService: UserService) {
    this.users = userService.getUsers();
  }
}
```

### 9. What are services?
**Answer:** Singleton classes for business logic, shared data, and API calls.

```typescript
@Injectable({ providedIn: 'root' })
export class DataService {
  private data: any[] = [];
  
  getData() {
    return this.data;
  }
  
  addData(item: any) {
    this.data.push(item);
  }
}

// Usage in component
constructor(private dataService: DataService) {
  this.items = dataService.getData();
}
```

### 10. What is providedIn: 'root'?
**Answer:**

```typescript
// Tree-shakeable singleton
@Injectable({ providedIn: 'root' })
export class MyService {}

// vs providers array
@NgModule({
  providers: [MyService]  // Module-level instance
})

// vs component providers
@Component({
  providers: [MyService]  // Component-level instance
})
```

**providedIn: 'root':**
- Creates singleton
- Tree-shakeable (removed if unused)
- Available app-wide
- Recommended approach

---

## **Modules & Routing**

### 11. What are modules?
**Answer:** Containers for organizing related code.

```typescript
@NgModule({
  declarations: [
    AppComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
```

### 12. What is lazy loading?
**Answer:** Load modules on demand to reduce initial bundle size.

```typescript
// app-routing.module.ts
const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module')
      .then(m => m.AdminModule)
  },
  {
    path: 'users',
    loadChildren: () => import('./users/users.module')
      .then(m => m.UsersModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
```

### 13. What is routing in Angular?
**Answer:**

```typescript
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'user/:id', component: UserComponent },
  { path: '**', component: NotFoundComponent }
];

// Template
<nav>
  <a routerLink="/">Home</a>
  <a routerLink="/about">About</a>
  <a [routerLink]="['/user', userId]">User</a>
</nav>
<router-outlet></router-outlet>

// Programmatic navigation
constructor(private router: Router) {}

navigate() {
  this.router.navigate(['/about']);
  this.router.navigate(['/user', 123]);
}

// Get route params
constructor(private route: ActivatedRoute) {
  this.route.params.subscribe(params => {
    console.log(params['id']);
  });
}
```

### 14. What are route guards?
**Answer:**

```typescript
// CanActivate - can access route
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  canActivate(): boolean {
    return this.authService.isLoggedIn();
  }
}

// CanDeactivate - can leave route
export class UnsavedChangesGuard implements CanDeactivate<ComponentType> {
  canDeactivate(component: ComponentType): boolean {
    return component.hasUnsavedChanges() 
      ? confirm('Discard changes?') 
      : true;
  }
}

// CanLoad - can load lazy module
export class AdminGuard implements CanLoad {
  canLoad(): boolean {
    return this.authService.isAdmin();
  }
}

// Resolve - pre-fetch data
export class UserResolver implements Resolve<User> {
  resolve(route: ActivatedRouteSnapshot): Observable<User> {
    return this.userService.getUser(route.params['id']);
  }
}

// Usage
const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    canDeactivate: [UnsavedChangesGuard]
  },
  {
    path: 'lazy',
    loadChildren: () => import('./lazy/lazy.module'),
    canLoad: [AdminGuard]
  },
  {
    path: 'user/:id',
    component: UserComponent,
    resolve: { user: UserResolver }
  }
];
```

---

## **Pipes**

### 15. What are pipes?
**Answer:** Transform data in templates.

```typescript
// Built-in pipes
{{ date | date:'short' }}
{{ price | currency:'USD' }}
{{ name | uppercase }}
{{ name | lowercase }}
{{ text | titlecase }}
{{ data | json }}
{{ number | number:'1.2-2' }}
{{ value | percent }}
{{ items | slice:0:5 }}

// Chaining pipes
{{ date | date:'short' | uppercase }}

// Pipe with parameters
{{ birthday | date:'dd/MM/yyyy' }}
```

### 16. What is the difference between pure and impure pipes?
**Answer:**

```typescript
// Pure pipe (default) - runs on primitive/reference change
@Pipe({ name: 'pure' })
export class PurePipe implements PipeTransform {
  transform(value: any): any {
    console.log('Pure pipe executed');
    return value;
  }
}

// Impure pipe - runs on every change detection
@Pipe({ name: 'impure', pure: false })
export class ImpurePipe implements PipeTransform {
  transform(value: any): any {
    console.log('Impure pipe executed');
    return value;
  }
}
```

### 17. How to create custom pipe?
**Answer:**

```typescript
@Pipe({ name: 'reverse' })
export class ReversePipe implements PipeTransform {
  transform(value: string): string {
    return value.split('').reverse().join('');
  }
}

// With parameters
@Pipe({ name: 'truncate' })
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 10): string {
    return value.length > limit 
      ? value.substring(0, limit) + '...' 
      : value;
  }
}

// Usage
{{ 'hello' | reverse }}
{{ longText | truncate:20 }}
```

---

## **Forms**

### 18. What are the types of forms?
**Answer:**

**1. Template-driven forms:**
```typescript
// Component
export class AppComponent {
  user = { name: '', email: '' };
  
  onSubmit(form: NgForm) {
    console.log(form.value);
  }
}

// Template
<form #userForm="ngForm" (ngSubmit)="onSubmit(userForm)">
  <input name="name" [(ngModel)]="user.name" required>
  <input name="email" [(ngModel)]="user.email" email>
  <button [disabled]="!userForm.valid">Submit</button>
</form>
```

**2. Reactive forms:**
```typescript
// Component
export class AppComponent {
  userForm: FormGroup;
  
  constructor(private fb: FormBuilder) {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      age: ['', [Validators.min(18), Validators.max(100)]]
    });
  }
  
  onSubmit() {
    if (this.userForm.valid) {
      console.log(this.userForm.value);
    }
  }
}

// Template
<form [formGroup]="userForm" (ngSubmit)="onSubmit()">
  <input formControlName="name">
  <input formControlName="email">
  <input formControlName="age" type="number">
  <button [disabled]="!userForm.valid">Submit</button>
</form>
```

### 19. What is FormGroup and FormControl?
**Answer:**

```typescript
// FormControl - single input
const nameControl = new FormControl('John', Validators.required);

// FormGroup - group of controls
const userForm = new FormGroup({
  name: new FormControl('', Validators.required),
  email: new FormControl('', [Validators.required, Validators.email]),
  address: new FormGroup({
    street: new FormControl(''),
    city: new FormControl('')
  })
});

// FormBuilder (cleaner syntax)
constructor(private fb: FormBuilder) {
  this.userForm = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    address: this.fb.group({
      street: [''],
      city: ['']
    })
  });
}

// Access values
console.log(userForm.value);
console.log(userForm.get('name')?.value);
console.log(userForm.get('address.city')?.value);
```

### 20. What are form validators?
**Answer:**

```typescript
// Built-in validators
Validators.required
Validators.email
Validators.minLength(5)
Validators.maxLength(20)
Validators.min(18)
Validators.max(100)
Validators.pattern(/^[a-zA-Z]+$/)

// Custom validator
function ageValidator(control: AbstractControl): ValidationErrors | null {
  const age = control.value;
  if (age < 18) {
    return { underage: true };
  }
  return null;
}

// Async validator
function emailExistsValidator(http: HttpClient): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    return http.get(`/api/check-email/${control.value}`).pipe(
      map(exists => exists ? { emailExists: true } : null)
    );
  };
}

// Usage
this.userForm = this.fb.group({
  age: ['', [Validators.required, ageValidator]],
  email: ['', [Validators.required], [emailExistsValidator(this.http)]]
});

// Check validation
if (this.userForm.get('age')?.hasError('underage')) {
  console.log('User is underage');
}
```

---

**Continue to Part 2 for RxJS, Signals, and Advanced Topics...**
