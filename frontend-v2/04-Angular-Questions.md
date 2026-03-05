# Angular - Most Important Interview Questions (Including Signals)

## **Top 50 Angular Questions**

### 1. What is Angular?
**Answer:** TypeScript-based framework for building SPAs. Developed by Google.

### 2. Angular vs AngularJS?
**Answer:** Angular (2+) uses TypeScript, component-based, better performance. AngularJS (1.x) uses JavaScript, MVC, slower.

### 3. What are components?
**Answer:** Building blocks with template, styles, and logic:
```typescript
@Component({
  selector: 'app-user',
  template: '<h1>{{name}}</h1>',
  styles: ['h1 { color: blue; }']
})
export class UserComponent { name = 'John'; }
```

### 4. Component lifecycle hooks?
**Answer:**
- `ngOnInit`: After component initialization
- `ngOnChanges`: When input properties change
- `ngDoCheck`: Custom change detection
- `ngAfterViewInit`: After view initialization
- `ngAfterContentInit`: After content projection
- `ngOnDestroy`: Before component destruction

### 5. Data binding types?
**Answer:**
- Interpolation: `{{value}}`
- Property: `[property]="value"`
- Event: `(event)="handler()"`
- Two-way: `[(ngModel)]="value"`

### 6. What are directives?
**Answer:** Instructions to DOM. Types: Component, Structural (`*ngIf`, `*ngFor`), Attribute (`ngClass`, `ngStyle`).

### 7. `*ngIf` vs `[hidden]`?
**Answer:** `*ngIf` removes from DOM, `[hidden]` uses CSS `display: none`.

### 8. What is dependency injection?
**Answer:** Design pattern where dependencies are provided to class:
```typescript
constructor(private userService: UserService) {}
```

### 9. Services in Angular?
**Answer:** Singleton classes for business logic, shared across components:
```typescript
@Injectable({ providedIn: 'root' })
export class UserService { }
```

### 10. `providedIn: 'root'` vs providers array?
**Answer:** `providedIn: 'root'` creates singleton, tree-shakeable. Providers array creates instance per module/component.

### 11. What are modules?
**Answer:** Containers for components, directives, pipes, services:
```typescript
@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

### 12. Lazy loading?
**Answer:** Load modules on demand:
```typescript
const routes: Routes = [
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
];
```

### 13. Routing in Angular?
**Answer:**
```typescript
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'user/:id', component: UserComponent },
  { path: '**', component: NotFoundComponent }
];
```

### 14. Route guards?
**Answer:**
- `CanActivate`: Can access route
- `CanDeactivate`: Can leave route
- `CanLoad`: Can load lazy module
- `Resolve`: Pre-fetch data

### 15. What are pipes?
**Answer:** Transform data in templates:
```typescript
{{ date | date:'short' }}
{{ price | currency:'USD' }}
{{ name | uppercase }}
```

### 16. Pure vs impure pipes?
**Answer:** Pure pipes (default) run on primitive/reference change. Impure pipes run on every change detection.

### 17. Custom pipe?
**Answer:**
```typescript
@Pipe({ name: 'reverse' })
export class ReversePipe implements PipeTransform {
  transform(value: string): string {
    return value.split('').reverse().join('');
  }
}
```

### 18. Reactive forms vs Template-driven forms?
**Answer:**
- Reactive: Defined in component, more control, testable
- Template-driven: Defined in template, simpler, less control

### 19. FormGroup vs FormControl?
**Answer:**
```typescript
this.form = new FormGroup({
  name: new FormControl('', Validators.required),
  email: new FormControl('', [Validators.required, Validators.email])
});
```

### 20. Form validation?
**Answer:** Built-in: `Validators.required`, `Validators.email`, `Validators.minLength`. Custom validators for complex logic.

### 21. What is RxJS?
**Answer:** Reactive Extensions for JavaScript. Handles async operations with Observables.

### 22. Observable vs Promise?
**Answer:**
- Observable: Lazy, cancellable, multiple values, operators
- Promise: Eager, not cancellable, single value

### 23. Common RxJS operators?
**Answer:** `map`, `filter`, `switchMap`, `mergeMap`, `catchError`, `tap`, `debounceTime`, `distinctUntilChanged`.

### 24. `switchMap` vs `mergeMap`?
**Answer:** `switchMap` cancels previous, `mergeMap` keeps all concurrent.

### 25. Subject vs BehaviorSubject?
**Answer:** BehaviorSubject has initial value and emits last value to new subscribers.

### 26. Change detection strategies?
**Answer:**
- `Default`: Checks entire tree
- `OnPush`: Checks only when input changes or events

### 27. ViewChild vs ContentChild?
**Answer:** `ViewChild` queries component's view, `ContentChild` queries projected content.

### 28. Content projection?
**Answer:**
```typescript
// parent.component.html
<app-card><p>Projected content</p></app-card>

// card.component.html
<div class="card"><ng-content></ng-content></div>
```

### 29. AOT vs JIT compilation?
**Answer:**
- AOT (Ahead-of-Time): Compiles at build time, faster, smaller bundle
- JIT (Just-in-Time): Compiles at runtime, slower, larger bundle

### 30. What is Ivy?
**Answer:** Angular's rendering engine (v9+). Smaller bundles, faster compilation, better debugging.

### 31. Standalone components (v14+)?
**Answer:**
```typescript
@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule],
  template: '<h1>User</h1>'
})
export class UserComponent { }
```

### 32. **What are Signals (v16+)?**
**Answer:** New reactive primitive for state management:
```typescript
import { signal, computed, effect } from '@angular/core';

export class CounterComponent {
  count = signal(0);
  doubleCount = computed(() => this.count() * 2);
  
  constructor() {
    effect(() => console.log('Count:', this.count()));
  }
  
  increment() {
    this.count.update(value => value + 1);
  }
}
```

### 33. **Signal vs Observable?**
**Answer:**
- Signals: Synchronous, simpler, better performance, automatic dependency tracking
- Observables: Asynchronous, more operators, complex scenarios

### 34. **What is `computed()` in Signals?**
**Answer:** Derived state that automatically updates:
```typescript
firstName = signal('John');
lastName = signal('Doe');
fullName = computed(() => `${this.firstName()} ${this.lastName()}`);
```

### 35. **What is `effect()` in Signals?**
**Answer:** Side effects that run when signals change:
```typescript
count = signal(0);

constructor() {
  effect(() => {
    console.log('Count changed:', this.count());
  });
}
```

### 36. **Signal methods?**
**Answer:**
- `signal(value)`: Create signal
- `signal()`: Read value
- `set(value)`: Set new value
- `update(fn)`: Update based on current value
- `mutate(fn)`: Mutate objects/arrays

### 37. **`toSignal()` and `toObservable()`?**
**Answer:**
```typescript
// Observable to Signal
data$ = this.http.get('/api/data');
data = toSignal(this.data$);

// Signal to Observable
count = signal(0);
count$ = toObservable(this.count);
```

### 38. HttpClient?
**Answer:**
```typescript
constructor(private http: HttpClient) {}

getUsers() {
  return this.http.get<User[]>('/api/users');
}

createUser(user: User) {
  return this.http.post('/api/users', user);
}
```

### 39. Interceptors?
**Answer:** Intercept HTTP requests/responses:
```typescript
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer token')
    });
    return next.handle(authReq);
  }
}
```

### 40. Error handling in HTTP?
**Answer:**
```typescript
this.http.get('/api/data').pipe(
  catchError(error => {
    console.error('Error:', error);
    return throwError(() => error);
  })
).subscribe();
```

### 41. State management options?
**Answer:** NgRx, Akita, NGXS, Services with BehaviorSubject, Signals (v16+).

### 42. NgRx basics?
**Answer:** Redux pattern: Actions → Reducers → Store → Selectors.

### 43. Smart vs Presentational components?
**Answer:**
- Smart: Handle logic, services, state
- Presentational: Display data, emit events

### 44. TrackBy in `*ngFor`?
**Answer:** Improves performance by tracking items:
```typescript
<div *ngFor="let item of items; trackBy: trackById">{{item.name}}</div>

trackById(index: number, item: any) {
  return item.id;
}
```

### 45. Angular animations?
**Answer:**
```typescript
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 }))
      ])
    ])
  ]
})
```

### 46. Testing in Angular?
**Answer:** Jasmine + Karma for unit tests, Protractor/Cypress for e2e.

### 47. TestBed?
**Answer:** Configures testing module:
```typescript
TestBed.configureTestingModule({
  declarations: [UserComponent],
  providers: [UserService]
});
const fixture = TestBed.createComponent(UserComponent);
```

### 48. Async pipe?
**Answer:** Subscribes and unsubscribes automatically:
```typescript
users$ = this.userService.getUsers();
// Template: <div *ngFor="let user of users$ | async">{{user.name}}</div>
```

### 49. Memory leaks prevention?
**Answer:** Unsubscribe in `ngOnDestroy`, use `async` pipe, use `takeUntil` operator.

### 50. Angular Universal (SSR)?
**Answer:** Server-side rendering for better SEO and initial load performance.

## **Advanced Signal Concepts**

### 51. **Signal equality checking?**
**Answer:**
```typescript
count = signal(0, { equal: (a, b) => a === b });
```

### 52. **Writable vs Readonly signals?**
**Answer:**
```typescript
// Writable
count = signal(0);
count.set(5);

// Readonly (from computed)
doubleCount = computed(() => this.count() * 2);
// doubleCount.set(10); // Error
```

### 53. **Signal with objects?**
**Answer:**
```typescript
user = signal({ name: 'John', age: 30 });

// Update
user.update(u => ({ ...u, age: 31 }));

// Mutate (for performance)
user.mutate(u => u.age = 31);
```

### 54. **Untracked reads?**
**Answer:**
```typescript
effect(() => {
  console.log(this.count());
  untracked(() => {
    console.log(this.otherSignal()); // Won't trigger effect
  });
});
```

### 55. **Signal-based components?**
**Answer:**
```typescript
@Component({
  selector: 'app-counter',
  standalone: true,
  template: `
    <p>Count: {{count()}}</p>
    <button (click)="increment()">+</button>
  `
})
export class CounterComponent {
  count = signal(0);
  increment() { this.count.update(v => v + 1); }
}
```
