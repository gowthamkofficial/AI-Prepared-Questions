# Additional Important Topics - Complete Answers

## **Important RxJS Operators**

### 1. What is the difference between map and switchMap?
**Answer:**

```typescript
// map - transforms values
of(1, 2, 3).pipe(
  map(x => x * 2)
).subscribe(console.log);  // 2, 4, 6

// switchMap - transforms to Observable, cancels previous
searchInput.pipe(
  switchMap(term => this.http.get(`/api/search?q=${term}`))
).subscribe(results => console.log(results));
```

### 2. What is the difference between concatMap and mergeMap?
**Answer:**

```typescript
// concatMap - waits for previous to complete (sequential)
clicks.pipe(
  concatMap(() => this.http.get('/api/data'))
).subscribe();
// Request 1 → completes → Request 2 → completes

// mergeMap - runs all concurrently (parallel)
clicks.pipe(
  mergeMap(() => this.http.get('/api/data'))
).subscribe();
// Request 1, Request 2, Request 3 all run together
```

### 3. What is exhaustMap?
**Answer:** Ignores new values while current Observable is active.

```typescript
// Prevents duplicate submissions
submitButton.pipe(
  exhaustMap(() => this.http.post('/api/submit', data))
).subscribe();
// Click 1 → processing... (clicks 2,3,4 ignored) → complete
```

### 4. What is forkJoin?
**Answer:** Waits for all Observables to complete, emits array of last values.

```typescript
forkJoin({
  users: this.http.get('/api/users'),
  posts: this.http.get('/api/posts'),
  comments: this.http.get('/api/comments')
}).subscribe(({ users, posts, comments }) => {
  console.log(users, posts, comments);
});
```

### 5. What is combineLatest?
**Answer:** Emits when any Observable emits, combines latest values.

```typescript
combineLatest([
  this.firstName$,
  this.lastName$
]).pipe(
  map(([first, last]) => `${first} ${last}`)
).subscribe(fullName => console.log(fullName));
```

### 6. What is debounceTime vs throttleTime?
**Answer:**

```typescript
// debounceTime - waits for pause
searchInput.pipe(
  debounceTime(300)  // Wait 300ms after last keystroke
).subscribe();

// throttleTime - emits at intervals
scrollEvent.pipe(
  throttleTime(1000)  // Emit once per second max
).subscribe();
```

### 7. What is distinctUntilChanged?
**Answer:** Skips duplicate consecutive values.

```typescript
input.pipe(
  distinctUntilChanged()
).subscribe(value => console.log(value));
// "a", "a", "b", "b", "a" → emits: "a", "b", "a"
```

### 8. What is takeUntil and why is it important?
**Answer:** Unsubscribes when notifier emits (prevents memory leaks).

```typescript
export class MyComponent implements OnDestroy {
  private destroy$ = new Subject<void>();
  
  ngOnInit() {
    this.dataService.getData().pipe(
      takeUntil(this.destroy$)
    ).subscribe(data => console.log(data));
  }
  
  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

### 9. What is catchError and retry?
**Answer:**

```typescript
this.http.get('/api/data').pipe(
  retry(3),  // Retry 3 times on error
  catchError(error => {
    console.error('Error:', error);
    return of([]);  // Return fallback value
  })
).subscribe(data => console.log(data));
```

### 10. What is tap operator?
**Answer:** Performs side effects without modifying stream.

```typescript
this.http.get('/api/data').pipe(
  tap(data => console.log('Data received:', data)),
  tap(() => this.loading = false),
  map(data => data.items)
).subscribe();
```

---

## **TrackBy in Detail**

### 11. What is TrackBy in *ngFor and why is it important?
**Answer:** Improves performance by tracking items by unique identifier instead of object reference.

**Without trackBy:**
```typescript
@Component({
  template: `
    <div *ngFor="let item of items">
      {{item.name}}
    </div>
  `
})
export class ListComponent {
  items = [
    { id: 1, name: 'Item 1' },
    { id: 2, name: 'Item 2' }
  ];
  
  refresh() {
    this.items = [
      { id: 1, name: 'Item 1' },
      { id: 2, name: 'Item 2' }
    ];
    // Angular recreates ALL DOM elements (slow)
  }
}
```

**With trackBy:**
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
  
  trackById(index: number, item: any): number {
    return item.id;  // Track by unique id
  }
  
  refresh() {
    this.items = [
      { id: 1, name: 'Item 1' },
      { id: 2, name: 'Item 2' }
    ];
    // Angular reuses existing DOM elements (fast)
  }
}
```

**Benefits:**
- Prevents unnecessary DOM updates
- Improves performance with large lists
- Maintains component state
- Reduces memory usage

### 12. What is OnPush change detection strategy?
**Answer:**

```typescript
@Component({
  selector: 'app-user',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `<p>{{user.name}}</p>`
})
export class UserComponent {
  @Input() user: User;
  
  // Change detection runs only when:
  // 1. Input reference changes
  // 2. Event fires in component
  // 3. Async pipe emits
  // 4. Manual trigger
}

// Parent component
updateUser() {
  // Won't trigger change detection (same reference)
  this.user.name = 'New Name';
  
  // Will trigger change detection (new reference)
  this.user = { ...this.user, name: 'New Name' };
}
```

### 13. How to manually trigger change detection?
**Answer:**

```typescript
import { ChangeDetectorRef } from '@angular/core';

export class MyComponent {
  constructor(private cdr: ChangeDetectorRef) {}
  
  updateData() {
    this.data.value = 'new';
    
    // Mark for check (OnPush)
    this.cdr.markForCheck();
    
    // Detect changes immediately
    this.cdr.detectChanges();
    
    // Detach from change detection
    this.cdr.detach();
    
    // Reattach to change detection
    this.cdr.reattach();
  }
}
```

---

## **NgRx State Management**

### 14. What is NgRx?
**Answer:** Redux-inspired state management library for Angular using RxJS.

**Core Concepts:**
- Store: Single source of truth
- Actions: Events that describe state changes
- Reducers: Pure functions that handle state changes
- Effects: Handle side effects (API calls)
- Selectors: Query state

### 15. What are Actions in NgRx?
**Answer:**

```typescript
import { createAction, props } from '@ngrx/store';

// Define actions
export const loadUsers = createAction('[User] Load Users');

export const loadUsersSuccess = createAction(
  '[User] Load Users Success',
  props<{ users: User[] }>()
);

export const loadUsersFailure = createAction(
  '[User] Load Users Failure',
  props<{ error: string }>()
);

// Dispatch action
this.store.dispatch(loadUsers());
```

### 16. What are Reducers in NgRx?
**Answer:**

```typescript
import { createReducer, on } from '@ngrx/store';

export interface UserState {
  users: User[];
  loading: boolean;
  error: string | null;
}

const initialState: UserState = {
  users: [],
  loading: false,
  error: null
};

export const userReducer = createReducer(
  initialState,
  on(loadUsers, state => ({
    ...state,
    loading: true
  })),
  on(loadUsersSuccess, (state, { users }) => ({
    ...state,
    users,
    loading: false,
    error: null
  })),
  on(loadUsersFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  }))
);
```

### 17. What are Effects in NgRx?
**Answer:**

```typescript
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, catchError, switchMap } from 'rxjs/operators';

@Injectable()
export class UserEffects {
  loadUsers$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadUsers),
      switchMap(() =>
        this.userService.getUsers().pipe(
          map(users => loadUsersSuccess({ users })),
          catchError(error => of(loadUsersFailure({ error: error.message })))
        )
      )
    )
  );
  
  constructor(
    private actions$: Actions,
    private userService: UserService
  ) {}
}
```

### 18. What are Selectors in NgRx?
**Answer:**

```typescript
import { createSelector, createFeatureSelector } from '@ngrx/store';

// Feature selector
export const selectUserState = createFeatureSelector<UserState>('users');

// Memoized selectors
export const selectAllUsers = createSelector(
  selectUserState,
  state => state.users
);

export const selectUserLoading = createSelector(
  selectUserState,
  state => state.loading
);

export const selectActiveUsers = createSelector(
  selectAllUsers,
  users => users.filter(u => u.active)
);

// Use in component
this.users$ = this.store.select(selectAllUsers);
```

### 19. What is the difference between NgRx Store and Services?
**Answer:**

| Feature | NgRx Store | Services |
|---------|-----------|----------|
| State | Centralized | Distributed |
| Immutability | Enforced | Not enforced |
| Time Travel | Yes | No |
| DevTools | Yes | No |
| Complexity | Higher | Lower |
| Use Case | Large apps | Small/medium apps |

### 20. What is the NgRx Store flow?
**Answer:**

```
Component → dispatch(Action) → Reducer → New State → Selector → Component
                    ↓
                 Effect → API → dispatch(Action)
```

### 21. What are Entity Adapters in NgRx?
**Answer:**

```typescript
import { createEntityAdapter, EntityState } from '@ngrx/entity';

export interface UserState extends EntityState<User> {
  selectedUserId: number | null;
}

export const userAdapter = createEntityAdapter<User>();

const initialState: UserState = userAdapter.getInitialState({
  selectedUserId: null
});

export const userReducer = createReducer(
  initialState,
  on(loadUsersSuccess, (state, { users }) =>
    userAdapter.setAll(users, state)
  ),
  on(addUser, (state, { user }) =>
    userAdapter.addOne(user, state)
  ),
  on(updateUser, (state, { user }) =>
    userAdapter.updateOne({ id: user.id, changes: user }, state)
  ),
  on(deleteUser, (state, { id }) =>
    userAdapter.removeOne(id, state)
  )
);

// Selectors
const { selectAll, selectEntities, selectIds } = userAdapter.getSelectors();
```

### 22. What is the difference between dispatch and select?
**Answer:**

```typescript
// dispatch - trigger actions (write)
this.store.dispatch(loadUsers());
this.store.dispatch(addUser({ user }));

// select - read state (read)
this.users$ = this.store.select(selectAllUsers);
this.loading$ = this.store.select(selectUserLoading);
```

### 23. What are NgRx best practices?
**Answer:**

1. **Use Entity Adapters** for collections
2. **Keep reducers pure** (no side effects)
3. **Use Effects** for async operations
4. **Memoize selectors** for performance
5. **Normalize state** (avoid nested data)
6. **Use action creators** (createAction)
7. **Handle errors** in effects
8. **Use OnPush** change detection
9. **Avoid dispatching in selectors**
10. **Use DevTools** for debugging

---

## **Performance & Optimization**

### 24. What is Virtual Scrolling (CDK)?
**Answer:**

```typescript
import { ScrollingModule } from '@angular/cdk/scrolling';

@Component({
  template: `
    <cdk-virtual-scroll-viewport itemSize="50" style="height: 400px">
      <div *cdkVirtualFor="let item of items">
        {{item.name}}
      </div>
    </cdk-virtual-scroll-viewport>
  `
})
export class ListComponent {
  items = Array.from({ length: 10000 }, (_, i) => ({ 
    id: i, 
    name: `Item ${i}` 
  }));
}
```

**Benefits:**
- Renders only visible items
- Handles large lists efficiently
- Smooth scrolling

### 25. What is Preloading Strategy?
**Answer:**

```typescript
// No preloading (default)
RouterModule.forRoot(routes)

// Preload all lazy modules
RouterModule.forRoot(routes, {
  preloadingStrategy: PreloadAllModules
})

// Custom preloading
@Injectable()
export class CustomPreloadStrategy implements PreloadingStrategy {
  preload(route: Route, load: () => Observable<any>): Observable<any> {
    return route.data?.['preload'] ? load() : of(null);
  }
}

const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module'),
    data: { preload: true }
  }
];
```

### 26. What is Bundle Size Optimization?
**Answer:**

```bash
# Analyze bundle
ng build --stats-json
npx webpack-bundle-analyzer dist/stats.json

# Optimization techniques:
# 1. Lazy loading
# 2. Tree shaking
# 3. AOT compilation
# 4. Production build
ng build --configuration production

# 5. Remove unused dependencies
# 6. Use smaller alternatives
# 7. Code splitting
```

### 27. What is Server-Side Rendering (SSR)?
**Answer:** Renders Angular app on server, sends HTML to browser.

**Benefits:**
- Faster initial load
- Better SEO
- Social media previews
- Improved performance

### 28. What is Angular Universal?
**Answer:**

```bash
# Add Universal
ng add @nguniversal/express-engine

# Build and serve
npm run build:ssr
npm run serve:ssr
```

### 29. What is Service Worker and PWA?
**Answer:**

```bash
# Add PWA
ng add @angular/pwa

# Features:
# - Offline support
# - Caching
# - Push notifications
# - Install prompt
```

### 30. What is Differential Loading?
**Answer:** Serves different bundles based on browser support.

```json
// Modern browsers (ES2015+)
main-es2015.js

// Legacy browsers (ES5)
main-es5.js
```

**Benefits:**
- Smaller bundles for modern browsers
- Backward compatibility
- Automatic by Angular CLI

---

**Total: 30 comprehensive answers for important topics**
