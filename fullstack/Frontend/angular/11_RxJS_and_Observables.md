# RxJS AND OBSERVABLES ANSWERS

---

## 78. What is RxJS?

### Answer:
- **RxJS** (Reactive Extensions for JavaScript) is a library for **reactive programming**
- Uses **Observables** to work with async data streams
- Provides **operators** to transform, filter, combine streams
- Core part of Angular's architecture

### Theoretical Keywords:
**Reactive programming**, **Observables**, **Data streams**,  
**Operators**, **Async handling**, **Functional programming**

### Example:
```typescript
import { Observable, of, from, interval } from 'rxjs';
import { map, filter, take } from 'rxjs/operators';

// Creating observables
const simple$ = of(1, 2, 3, 4, 5);
const fromArray$ = from([1, 2, 3, 4, 5]);
const interval$ = interval(1000);  // Emits 0, 1, 2... every second

// Using operators
simple$.pipe(
  filter(n => n % 2 === 0),
  map(n => n * 10)
).subscribe(value => console.log(value));  // 20, 40
```

---

## 79. What is an Observable?

### Answer:
- **Observable** is a stream of values over time
- Can emit **multiple values** (unlike Promise)
- **Lazy**: Doesn't execute until subscribed
- Can be **synchronous or asynchronous**
- Supports **cancellation** via unsubscribe

### Theoretical Keywords:
**Data stream**, **Lazy evaluation**, **Multiple values**,  
**Subscribe**, **Unsubscribe**, **Push-based**

### Example:
```typescript
// Creating Observable
const myObservable$ = new Observable<number>(subscriber => {
  subscriber.next(1);
  subscriber.next(2);
  subscriber.next(3);
  
  setTimeout(() => {
    subscriber.next(4);
    subscriber.complete();  // No more values
  }, 1000);
  
  // Cleanup function (called on unsubscribe)
  return () => {
    console.log('Cleanup');
  };
});

// Subscribing
const subscription = myObservable$.subscribe({
  next: (value) => console.log('Value:', value),
  error: (err) => console.error('Error:', err),
  complete: () => console.log('Complete')
});

// Output:
// Value: 1
// Value: 2
// Value: 3
// (after 1 second)
// Value: 4
// Complete
// Cleanup

// Unsubscribe to cancel
subscription.unsubscribe();
```

---

## 80. What is the difference between Observable and Promise?

### Answer:

| Feature | Observable | Promise |
|---------|------------|---------|
| **Values** | Multiple | Single |
| **Execution** | Lazy (on subscribe) | Eager (immediate) |
| **Cancellation** | Yes (unsubscribe) | No |
| **Operators** | Many (map, filter, etc.) | Limited (then, catch) |
| **Async** | Sync or async | Always async |

### Example:
```typescript
// Promise - Single value, eager
const promise = new Promise((resolve) => {
  console.log('Promise executing');  // Runs immediately
  resolve('done');
});

// Observable - Multiple values, lazy
const observable$ = new Observable(subscriber => {
  console.log('Observable executing');  // Only runs on subscribe
  subscriber.next('value 1');
  subscriber.next('value 2');
  subscriber.complete();
});

// Observable to Promise conversion
import { firstValueFrom, lastValueFrom } from 'rxjs';

const value = await firstValueFrom(observable$);
const last = await lastValueFrom(observable$);

// Promise to Observable conversion
import { from } from 'rxjs';
const obs$ = from(promise);
```

---

## 81. What are Subjects?

### Answer:
- **Subject** is both **Observable** and **Observer**
- Can **multicast** to multiple subscribers
- Types: Subject, BehaviorSubject, ReplaySubject, AsyncSubject

### Theoretical Keywords:
**Multicast**, **Hot observable**, **Observer pattern**,  
**Event emitter**, **State management**, **Shared stream**

### Example:
```typescript
// Regular Subject
const subject = new Subject<number>();

subject.subscribe(v => console.log('A:', v));
subject.subscribe(v => console.log('B:', v));

subject.next(1);  // A: 1, B: 1
subject.next(2);  // A: 2, B: 2

// BehaviorSubject - Has current value
const behavior$ = new BehaviorSubject<number>(0);  // Initial value

behavior$.subscribe(v => console.log('Value:', v));  // Immediately: Value: 0
behavior$.next(1);  // Value: 1
console.log(behavior$.value);  // 1 (synchronous access)

// ReplaySubject - Replays N values to new subscribers
const replay$ = new ReplaySubject<number>(2);  // Buffer last 2

replay$.next(1);
replay$.next(2);
replay$.next(3);

replay$.subscribe(v => console.log('Replay:', v));
// Output: Replay: 2, Replay: 3 (last 2 values)

// AsyncSubject - Only emits last value on complete
const async$ = new AsyncSubject<number>();

async$.subscribe(v => console.log('Async:', v));
async$.next(1);
async$.next(2);
async$.next(3);
async$.complete();  // Now emits: Async: 3
```

---

## 82. What is BehaviorSubject?

### Answer:
- **BehaviorSubject** is a Subject with a **current value**
- Requires an **initial value**
- New subscribers immediately receive **current value**
- Commonly used for **state management**

### Example:
```typescript
// State management with BehaviorSubject
@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  
  currentUser$ = this.currentUserSubject.asObservable();
  
  // Synchronous access to current value
  get currentUser(): User | null {
    return this.currentUserSubject.value;
  }
  
  login(credentials: Credentials): Observable<User> {
    return this.http.post<User>('/api/login', credentials).pipe(
      tap(user => this.currentUserSubject.next(user))
    );
  }
  
  logout(): void {
    this.currentUserSubject.next(null);
  }
  
  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null;
  }
}

// Component usage
@Component({
  template: `
    <div *ngIf="currentUser$ | async as user">
      Welcome, {{ user.name }}!
    </div>
  `
})
export class NavComponent {
  currentUser$ = this.authService.currentUser$;
  
  constructor(private authService: AuthService) { }
}
```

---

## 83. What are the most common RxJS operators?

### Answer:
Categories of operators:
- **Creation**: of, from, interval, timer
- **Transformation**: map, switchMap, mergeMap
- **Filtering**: filter, take, debounceTime
- **Combination**: combineLatest, merge, forkJoin
- **Error handling**: catchError, retry

### Example:
```typescript
// Transformation operators
of(1, 2, 3).pipe(
  map(x => x * 10)  // 10, 20, 30
);

// Filtering operators
of(1, 2, 3, 4, 5).pipe(
  filter(x => x % 2 === 0),  // 2, 4
  take(1)  // Only first: 2
);

// Higher-order mapping
// switchMap - Cancel previous, use latest
this.searchInput.valueChanges.pipe(
  debounceTime(300),
  switchMap(term => this.searchService.search(term))
);

// mergeMap - Run all in parallel
from(userIds).pipe(
  mergeMap(id => this.userService.getUser(id))
);

// concatMap - Run sequentially
from(tasks).pipe(
  concatMap(task => this.taskService.process(task))
);

// exhaustMap - Ignore while processing
this.submitButton$.pipe(
  exhaustMap(() => this.formService.submit(this.form.value))
);

// Combination operators
combineLatest([user$, permissions$]).pipe(
  map(([user, permissions]) => ({ user, permissions }))
);

forkJoin({
  users: this.userService.getUsers(),
  products: this.productService.getProducts()
}).subscribe(({ users, products }) => {
  // Both completed
});

// Error handling
this.http.get('/api/data').pipe(
  retry(3),
  catchError(error => {
    console.error(error);
    return of([]);  // Return fallback value
  })
);
```

---

## 84. What is the difference between switchMap, mergeMap, and concatMap?

### Answer:

| Operator | Behavior | Use Case |
|----------|----------|----------|
| **switchMap** | Cancel previous, only latest | Search, autocomplete |
| **mergeMap** | All run parallel | Parallel requests |
| **concatMap** | Run sequentially | Order matters |
| **exhaustMap** | Ignore until complete | Submit button |

### Example:
```typescript
// switchMap - Typeahead search
// Cancels previous request when new search term arrives
this.searchControl.valueChanges.pipe(
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(term => this.api.search(term))
).subscribe(results => {
  this.searchResults = results;
});

// mergeMap - Fetch all in parallel
// All requests run simultaneously
const userIds = [1, 2, 3, 4, 5];
from(userIds).pipe(
  mergeMap(id => this.userService.getUser(id), 3)  // Max 3 concurrent
).subscribe(user => {
  this.users.push(user);
});

// concatMap - Sequential operations
// Wait for each to complete before starting next
from(this.filesToUpload).pipe(
  concatMap(file => this.uploadService.upload(file))
).subscribe(result => {
  console.log('File uploaded:', result);
});

// exhaustMap - Prevent duplicate submissions
this.saveButton.pipe(
  exhaustMap(() => this.saveData())  // Ignore clicks while saving
).subscribe(result => {
  console.log('Saved');
});

// Visual comparison
// Input:    --1--2--3-->
// API call takes: =====>

// switchMap: --1==X2==X3====>  (cancels 1, 2)
// mergeMap:  --1=====>
//            ---2=====>
//            ----3=====>       (all parallel)
// concatMap: --1======2======3======>  (sequential)
// exhaustMap: --1=====>3=====>  (ignores 2)
```

---

## 85. How to handle errors in RxJS?

### Answer:
- **catchError**: Handle error and return fallback
- **retry**: Retry failed operation
- **retryWhen**: Conditional retry with delay
- **finalize**: Execute on complete or error

### Example:
```typescript
// Basic error handling
this.http.get<User[]>('/api/users').pipe(
  catchError(error => {
    console.error('Error fetching users:', error);
    return of([]);  // Return empty array as fallback
  })
).subscribe(users => {
  this.users = users;
});

// Retry with delay
this.http.get('/api/data').pipe(
  retry({
    count: 3,
    delay: 1000  // Wait 1 second between retries
  }),
  catchError(error => {
    return throwError(() => new Error('Failed after 3 retries'));
  })
);

// Exponential backoff retry
this.http.get('/api/data').pipe(
  retryWhen(errors => 
    errors.pipe(
      scan((retryCount, error) => {
        if (retryCount >= 3) throw error;
        return retryCount + 1;
      }, 0),
      delay(attempt => Math.pow(2, attempt) * 1000)  // 1s, 2s, 4s
    )
  )
);

// Using finalize for cleanup
this.loading = true;
this.http.get('/api/data').pipe(
  finalize(() => {
    this.loading = false;  // Always runs
  })
).subscribe({
  next: (data) => this.data = data,
  error: (error) => this.error = error.message
});

// Global error handling with interceptor
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Redirect to login
        }
        if (error.status === 500) {
          // Show error notification
        }
        return throwError(() => error);
      })
    );
  }
}
```

---

## Common RxJS Patterns in Angular

### Answer:
```typescript
// 1. Unsubscribe pattern
export class MyComponent implements OnDestroy {
  private destroy$ = new Subject<void>();
  
  ngOnInit(): void {
    this.dataService.getData()
      .pipe(takeUntil(this.destroy$))
      .subscribe();
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

// 2. Async pipe (auto unsubscribe)
@Component({
  template: `<div *ngFor="let item of items$ | async">{{ item.name }}</div>`
})
export class ListComponent {
  items$ = this.service.getItems();
}

// 3. Share single HTTP request
@Injectable()
export class DataService {
  private data$?: Observable<Data>;
  
  getData(): Observable<Data> {
    if (!this.data$) {
      this.data$ = this.http.get<Data>('/api/data').pipe(
        shareReplay(1)
      );
    }
    return this.data$;
  }
}

// 4. Polling
interval(5000).pipe(
  startWith(0),
  switchMap(() => this.http.get('/api/status')),
  takeUntil(this.destroy$)
).subscribe(status => {
  this.status = status;
});

// 5. Combine multiple sources
combineLatest([
  this.route.params,
  this.route.queryParams,
  this.authService.currentUser$
]).pipe(
  switchMap(([params, query, user]) => 
    this.dataService.getData(params['id'], query, user)
  )
).subscribe(data => {
  this.data = data;
});
```

---
