# Angular - Complete Interview Questions (Part 2)

## **RxJS & Observables**

### 21. What is RxJS?
**Answer:** Reactive Extensions for JavaScript - library for reactive programming using Observables.

```typescript
import { Observable } from 'rxjs';

// Create observable
const observable = new Observable(subscriber => {
  subscriber.next(1);
  subscriber.next(2);
  subscriber.next(3);
  subscriber.complete();
});

// Subscribe
observable.subscribe({
  next: value => console.log(value),
  error: err => console.error(err),
  complete: () => console.log('Done')
});
```

### 22. What is the difference between Observable and Promise?
**Answer:**

| Feature | Observable | Promise |
|---------|------------|---------|
| Execution | Lazy | Eager |
| Values | Multiple | Single |
| Cancellable | Yes | No |
| Operators | Many | Limited |
| Error Handling | Can retry | Cannot retry |

```typescript
// Promise
const promise = new Promise(resolve => {
  setTimeout(() => resolve('Done'), 1000);
});
promise.then(value => console.log(value));

// Observable
const observable = new Observable(subscriber => {
  setTimeout(() => subscriber.next('Done'), 1000);
});
const subscription = observable.subscribe(value => console.log(value));
subscription.unsubscribe();  // Can cancel
```

### 23. What are common RxJS operators?
**Answer:**

```typescript
import { map, filter, switchMap, mergeMap, catchError, tap, 
         debounceTime, distinctUntilChanged, take, takeUntil } from 'rxjs/operators';

// map - transform values
of(1, 2, 3).pipe(
  map(x => x * 2)
).subscribe(console.log);  // 2, 4, 6

// filter - select values
of(1, 2, 3, 4).pipe(
  filter(x => x > 2)
).subscribe(console.log);  // 3, 4

// switchMap - cancel previous, switch to new
searchInput.pipe(
  debounceTime(300),
  switchMap(term => this.http.get(`/api/search?q=${term}`))
).subscribe(results => console.log(results));

// mergeMap - keep all concurrent
clicks.pipe(
  mergeMap(() => this.http.get('/api/data'))
).subscribe(data => console.log(data));

// catchError - handle errors
this.http.get('/api/data').pipe(
  catchError(error => {
    console.error(error);
    return of([]);  // Return fallback
  })
).subscribe(data => console.log(data));

// tap - side effects
this.http.get('/api/data').pipe(
  tap(data => console.log('Data:', data))
).subscribe();

// debounceTime - wait for pause
searchInput.pipe(
  debounceTime(300)
).subscribe(value => console.log(value));

// distinctUntilChanged - skip duplicates
input.pipe(
  distinctUntilChanged()
).subscribe(value => console.log(value));

// take - take n values
observable.pipe(
  take(3)
).subscribe(value => console.log(value));

// takeUntil - unsubscribe on event
private destroy$ = new Subject();

ngOnInit() {
  observable.pipe(
    takeUntil(this.destroy$)
  ).subscribe(value => console.log(value));
}

ngOnDestroy() {
  this.destroy$.next();
  this.destroy$.complete();
}
```

### 24. What is the difference between switchMap and mergeMap?
**Answer:**

```typescript
// switchMap - cancels previous request
searchInput.pipe(
  switchMap(term => this.http.get(`/api/search?q=${term}`))
).subscribe();
// If user types "abc" quickly, only searches for "abc"

// mergeMap - keeps all requests
clicks.pipe(
  mergeMap(() => this.http.get('/api/data'))
).subscribe();
// All requests complete, even if new ones start
```

### 25. What are Subject and BehaviorSubject?
**Answer:**

```typescript
// Subject - no initial value
const subject = new Subject<number>();
subject.subscribe(value => console.log('A:', value));
subject.next(1);  // A: 1
subject.subscribe(value => console.log('B:', value));
subject.next(2);  // A: 2, B: 2

// BehaviorSubject - has initial value, emits last value to new subscribers
const behaviorSubject = new BehaviorSubject<number>(0);
behaviorSubject.subscribe(value => console.log('A:', value));  // A: 0
behaviorSubject.next(1);  // A: 1
behaviorSubject.subscribe(value => console.log('B:', value));  // B: 1
behaviorSubject.next(2);  // A: 2, B: 2

// ReplaySubject - replays n values to new subscribers
const replaySubject = new ReplaySubject<number>(2);
replaySubject.next(1);
replaySubject.next(2);
replaySubject.next(3);
replaySubject.subscribe(value => console.log(value));  // 2, 3

// AsyncSubject - emits last value on complete
const asyncSubject = new AsyncSubject<number>();
asyncSubject.next(1);
asyncSubject.next(2);
asyncSubject.complete();
asyncSubject.subscribe(value => console.log(value));  // 2
```

---

## **Signals (Angular 16+)**

### 26. What are Signals?
**Answer:** New reactive primitive for state management with automatic dependency tracking.

```typescript
import { signal, computed, effect } from '@angular/core';

@Component({
  selector: 'app-counter',
  template: `
    <p>Count: {{count()}}</p>
    <p>Double: {{doubleCount()}}</p>
    <button (click)="increment()">+</button>
  `
})
export class CounterComponent {
  // Create signal
  count = signal(0);
  
  // Computed signal (derived state)
  doubleCount = computed(() => this.count() * 2);
  
  // Effect (side effects)
  constructor() {
    effect(() => {
      console.log('Count changed:', this.count());
    });
  }
  
  // Update signal
  increment() {
    this.count.update(value => value + 1);
    // or
    this.count.set(this.count() + 1);
  }
}
```

### 27. What are Signal methods?
**Answer:**

```typescript
// Create signal
const count = signal(0);

// Read value
console.log(count());  // 0

// Set value
count.set(5);

// Update based on current value
count.update(value => value + 1);

// Mutate objects/arrays (for performance)
const user = signal({ name: 'John', age: 30 });
user.mutate(u => u.age = 31);

// With arrays
const items = signal([1, 2, 3]);
items.mutate(arr => arr.push(4));
```

### 28. What is computed() in Signals?
**Answer:** Creates derived state that automatically updates.

```typescript
const firstName = signal('John');
const lastName = signal('Doe');

// Computed signal
const fullName = computed(() => `${firstName()} ${lastName()}`);

console.log(fullName());  // "John Doe"

firstName.set('Jane');
console.log(fullName());  // "Jane Doe"

// Complex computed
const items = signal([1, 2, 3, 4, 5]);
const evenItems = computed(() => items().filter(x => x % 2 === 0));
const sum = computed(() => items().reduce((a, b) => a + b, 0));
```

### 29. What is effect() in Signals?
**Answer:** Runs side effects when signals change.

```typescript
const count = signal(0);

// Effect runs when count changes
effect(() => {
  console.log('Count:', count());
  localStorage.setItem('count', count().toString());
});

// Effect with cleanup
effect((onCleanup) => {
  const timer = setInterval(() => {
    console.log('Count:', count());
  }, 1000);
  
  onCleanup(() => clearInterval(timer));
});

// Conditional effects
effect(() => {
  if (count() > 10) {
    console.log('Count exceeded 10');
  }
});
```

### 30. What is the difference between Signals and Observables?
**Answer:**

| Feature | Signals | Observables |
|---------|---------|-------------|
| Type | Synchronous | Asynchronous |
| Complexity | Simple | Complex |
| Performance | Better | Good |
| Operators | Limited | Many |
| Use Case | State management | Async operations |
| Learning Curve | Easy | Steep |

```typescript
// Signal
const count = signal(0);
count.set(1);
console.log(count());  // Synchronous

// Observable
const count$ = new BehaviorSubject(0);
count$.next(1);
count$.subscribe(value => console.log(value));  // Asynchronous
```

### 31. How to convert between Signals and Observables?
**Answer:**

```typescript
import { toSignal, toObservable } from '@angular/core/rxjs-interop';

// Observable to Signal
const data$ = this.http.get<User[]>('/api/users');
const users = toSignal(data$, { initialValue: [] });

// Signal to Observable
const count = signal(0);
const count$ = toObservable(count);

count$.subscribe(value => console.log(value));
```

### 32. What are writable vs readonly signals?
**Answer:**

```typescript
// Writable signal
const count = signal(0);
count.set(5);
count.update(v => v + 1);

// Readonly signal (from computed)
const doubleCount = computed(() => count() * 2);
// doubleCount.set(10);  // Error: readonly

// Make signal readonly
const readonlyCount = count.asReadonly();
// readonlyCount.set(5);  // Error
```

### 33. How to use Signals with objects?
**Answer:**

```typescript
// Object signal
const user = signal({ name: 'John', age: 30 });

// Update entire object
user.set({ name: 'Jane', age: 25 });

// Update with spread
user.update(u => ({ ...u, age: 31 }));

// Mutate (for performance)
user.mutate(u => {
  u.age = 31;
  u.name = 'Jane';
});

// Computed from object
const userName = computed(() => user().name);
const isAdult = computed(() => user().age >= 18);
```

---

## **Change Detection**

### 34. What are change detection strategies?
**Answer:**

```typescript
// Default - checks entire component tree
@Component({
  changeDetection: ChangeDetectionStrategy.Default
})

// OnPush - checks only when:
// 1. Input reference changes
// 2. Event fires
// 3. Async pipe emits
// 4. Manual trigger
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MyComponent {
  @Input() data: any;
  
  constructor(private cdr: ChangeDetectorRef) {}
  
  // Manual trigger
  updateData() {
    this.data.value = 'new';
    this.cdr.markForCheck();  // Trigger change detection
  }
}
```

### 35. What is ViewChild and ContentChild?
**Answer:**

```typescript
// ViewChild - query component's view
@Component({
  template: `
    <input #nameInput>
    <app-child #childComp></app-child>
  `
})
export class ParentComponent {
  @ViewChild('nameInput') nameInput!: ElementRef;
  @ViewChild('childComp') childComp!: ChildComponent;
  
  ngAfterViewInit() {
    console.log(this.nameInput.nativeElement.value);
    this.childComp.someMethod();
  }
}

// ContentChild - query projected content
@Component({
  selector: 'app-card',
  template: `
    <div class="card">
      <ng-content></ng-content>
    </div>
  `
})
export class CardComponent {
  @ContentChild('header') header!: ElementRef;
  
  ngAfterContentInit() {
    console.log(this.header);
  }
}

// Usage
<app-card>
  <h1 #header>Title</h1>
</app-card>
```

### 36. What is content projection?
**Answer:**

```typescript
// Single slot
@Component({
  selector: 'app-card',
  template: `
    <div class="card">
      <ng-content></ng-content>
    </div>
  `
})

// Usage
<app-card>
  <p>Projected content</p>
</app-card>

// Multiple slots
@Component({
  selector: 'app-card',
  template: `
    <div class="card">
      <div class="header">
        <ng-content select="[header]"></ng-content>
      </div>
      <div class="body">
        <ng-content select="[body]"></ng-content>
      </div>
      <div class="footer">
        <ng-content select="[footer]"></ng-content>
      </div>
    </div>
  `
})

// Usage
<app-card>
  <h1 header>Title</h1>
  <p body>Content</p>
  <button footer>Action</button>
</app-card>
```

---

## **HTTP & Interceptors**

### 37. How to use HttpClient?
**Answer:**

```typescript
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}
  
  // GET
  getUsers() {
    return this.http.get<User[]>('/api/users');
  }
  
  // GET with params
  searchUsers(term: string) {
    const params = new HttpParams().set('q', term);
    return this.http.get<User[]>('/api/users', { params });
  }
  
  // POST
  createUser(user: User) {
    return this.http.post<User>('/api/users', user);
  }
  
  // PUT
  updateUser(id: number, user: User) {
    return this.http.put<User>(`/api/users/${id}`, user);
  }
  
  // DELETE
  deleteUser(id: number) {
    return this.http.delete(`/api/users/${id}`);
  }
  
  // With headers
  getData() {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer token',
      'Content-Type': 'application/json'
    });
    return this.http.get('/api/data', { headers });
  }
}
```

### 38. What are HTTP interceptors?
**Answer:**

```typescript
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Clone and modify request
    const authReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer token')
    });
    
    return next.handle(authReq).pipe(
      catchError(error => {
        if (error.status === 401) {
          // Handle unauthorized
        }
        return throwError(() => error);
      })
    );
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

### 39. How to handle HTTP errors?
**Answer:**

```typescript
this.http.get('/api/data').pipe(
  catchError(error => {
    if (error.status === 404) {
      console.error('Not found');
    } else if (error.status === 500) {
      console.error('Server error');
    }
    return throwError(() => error);
  }),
  retry(3),  // Retry 3 times
  finalize(() => console.log('Request complete'))
).subscribe({
  next: data => console.log(data),
  error: err => console.error(err)
});
```

---

## **Advanced Topics**

### 40. What is AOT vs JIT compilation?
**Answer:**

| Feature | AOT | JIT |
|---------|-----|-----|
| Compilation | Build time | Runtime |
| Bundle Size | Smaller | Larger |
| Performance | Faster | Slower |
| Error Detection | Build time | Runtime |
| Production | Recommended | Not recommended |

```bash
# AOT (default in production)
ng build --aot

# JIT (development)
ng serve
```

### 41. What is Ivy?
**Answer:** Angular's rendering engine (v9+).

**Benefits:**
- Smaller bundle sizes
- Faster compilation
- Better debugging
- Improved type checking
- Tree-shaking

### 42. What are standalone components (v14+)?
**Answer:**

```typescript
@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `<h1>{{name}}</h1>`
})
export class UserComponent {
  name = 'John';
}

// No need for NgModule
// Bootstrap directly
bootstrapApplication(AppComponent);
```

### 43. What is TrackBy in *ngFor?
**Answer:**

```typescript
@Component({
  template: `
    <div *ngFor="let item of items; trackBy: trackById">
      {{item.name}}
    </div>
  `
})
export class ListComponent {
  items = [
    { id: 1, name: 'Item 1' },
    { id: 2, name: 'Item 2' }
  ];
  
  trackById(index: number, item: any) {
    return item.id;  // Track by id instead of reference
  }
}
```

**Benefits:**
- Improves performance
- Prevents unnecessary DOM updates
- Maintains component state

### 44. How to prevent memory leaks?
**Answer:**

```typescript
export class MyComponent implements OnDestroy {
  private destroy$ = new Subject<void>();
  
  ngOnInit() {
    // Method 1: takeUntil
    this.dataService.getData().pipe(
      takeUntil(this.destroy$)
    ).subscribe(data => console.log(data));
    
    // Method 2: Store subscription
    this.subscription = this.dataService.getData()
      .subscribe(data => console.log(data));
  }
  
  ngOnDestroy() {
    // Method 1: Complete subject
    this.destroy$.next();
    this.destroy$.complete();
    
    // Method 2: Unsubscribe
    this.subscription?.unsubscribe();
  }
}

// Method 3: Async pipe (auto unsubscribes)
@Component({
  template: `<div>{{data$ | async}}</div>`
})
export class MyComponent {
  data$ = this.dataService.getData();
}
```

---

**Total: 44+ comprehensive Angular questions including Signals and advanced topics**
