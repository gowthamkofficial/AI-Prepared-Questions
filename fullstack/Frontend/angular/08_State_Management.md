# STATE MANAGEMENT (NgRx) ANSWERS

---

## 60. What is NgRx?

### Answer:
- **NgRx** is a **state management library** for Angular
- Based on **Redux pattern** and **RxJS**
- Provides **single source of truth** for application state
- Uses **unidirectional data flow**
- Includes: Store, Actions, Reducers, Effects, Selectors

### Theoretical Keywords:
**Redux pattern**, **RxJS**, **Single source of truth**, **Unidirectional flow**,  
**Predictable state**, **Store**, **Immutable**

### Example:
```typescript
// Installation
// npm install @ngrx/store @ngrx/effects @ngrx/store-devtools

// Basic flow
// Action → Reducer → Store → Selector → Component
```

---

## 61. Why use state management?

### Answer:
- **Centralized state**: Single source of truth
- **Predictability**: State changes are explicit
- **Debugging**: Time-travel debugging with DevTools
- **Testability**: Pure functions are easy to test
- **Scalability**: Manages complex state interactions
- **Component decoupling**: Components don't need to know data source

### Theoretical Keywords:
**Centralized**, **Predictable**, **Debuggable**, **Testable**,  
**Scalable**, **Decoupled**, **Maintainable**

---

## 62. What is the Redux pattern?

### Answer:
- **Store**: Single state container
- **Actions**: Describe what happened
- **Reducers**: Pure functions that update state
- **One-way data flow**: Action → Reducer → New State

### Example:
```
┌─────────────┐
│  Component  │
│  (View)     │
└──────┬──────┘
       │ dispatches
       ▼
┌─────────────┐
│   Action    │
└──────┬──────┘
       │ sent to
       ▼
┌─────────────┐
│   Reducer   │ + old state = new state
└──────┬──────┘
       │ updates
       ▼
┌─────────────┐
│    Store    │
└──────┬──────┘
       │ notifies (via selector)
       ▼
┌─────────────┐
│  Component  │
└─────────────┘
```

---

## 63. What are Actions?

### Answer:
- **Actions** are events that describe **what happened**
- Have a **type** (unique identifier) and optional **payload**
- Created using `createAction()`
- Dispatched to trigger state changes

### Example:
```typescript
// actions/user.actions.ts
import { createAction, props } from '@ngrx/store';

// Simple action
export const loadUsers = createAction('[User List] Load Users');

// Action with payload
export const loadUsersSuccess = createAction(
  '[User API] Load Users Success',
  props<{ users: User[] }>()
);

export const loadUsersFailure = createAction(
  '[User API] Load Users Failure',
  props<{ error: string }>()
);

export const addUser = createAction(
  '[User Form] Add User',
  props<{ user: User }>()
);

export const updateUser = createAction(
  '[User Form] Update User',
  props<{ id: number; changes: Partial<User> }>()
);

export const deleteUser = createAction(
  '[User List] Delete User',
  props<{ id: number }>()
);

// Dispatching actions in component
@Component({...})
export class UserListComponent {
  constructor(private store: Store) { }
  
  loadUsers(): void {
    this.store.dispatch(loadUsers());
  }
  
  addUser(user: User): void {
    this.store.dispatch(addUser({ user }));
  }
  
  deleteUser(id: number): void {
    this.store.dispatch(deleteUser({ id }));
  }
}
```

---

## 64. What are Reducers?

### Answer:
- **Reducers** are **pure functions** that handle state transitions
- Take current state and action, return new state
- Must be **immutable** - never modify original state
- Created using `createReducer()`

### Example:
```typescript
// reducers/user.reducer.ts
import { createReducer, on } from '@ngrx/store';
import * as UserActions from '../actions/user.actions';

export interface UserState {
  users: User[];
  loading: boolean;
  error: string | null;
  selectedUserId: number | null;
}

export const initialState: UserState = {
  users: [],
  loading: false,
  error: null,
  selectedUserId: null
};

export const userReducer = createReducer(
  initialState,
  
  // Load users
  on(UserActions.loadUsers, (state) => ({
    ...state,
    loading: true,
    error: null
  })),
  
  on(UserActions.loadUsersSuccess, (state, { users }) => ({
    ...state,
    users,
    loading: false
  })),
  
  on(UserActions.loadUsersFailure, (state, { error }) => ({
    ...state,
    error,
    loading: false
  })),
  
  // Add user
  on(UserActions.addUser, (state, { user }) => ({
    ...state,
    users: [...state.users, user]
  })),
  
  // Update user
  on(UserActions.updateUser, (state, { id, changes }) => ({
    ...state,
    users: state.users.map(user =>
      user.id === id ? { ...user, ...changes } : user
    )
  })),
  
  // Delete user
  on(UserActions.deleteUser, (state, { id }) => ({
    ...state,
    users: state.users.filter(user => user.id !== id)
  })),
  
  // Select user
  on(UserActions.selectUser, (state, { id }) => ({
    ...state,
    selectedUserId: id
  }))
);
```

---

## 65. What are Selectors?

### Answer:
- **Selectors** are **pure functions** to extract data from store
- Provide **memoization** for performance
- Compose smaller selectors into complex ones
- Decouple components from store structure

### Example:
```typescript
// selectors/user.selectors.ts
import { createFeatureSelector, createSelector } from '@ngrx/store';
import { UserState } from '../reducers/user.reducer';

// Feature selector
export const selectUserState = createFeatureSelector<UserState>('users');

// Basic selectors
export const selectAllUsers = createSelector(
  selectUserState,
  (state) => state.users
);

export const selectUsersLoading = createSelector(
  selectUserState,
  (state) => state.loading
);

export const selectUsersError = createSelector(
  selectUserState,
  (state) => state.error
);

export const selectSelectedUserId = createSelector(
  selectUserState,
  (state) => state.selectedUserId
);

// Composed selectors
export const selectSelectedUser = createSelector(
  selectAllUsers,
  selectSelectedUserId,
  (users, selectedId) => users.find(user => user.id === selectedId)
);

export const selectActiveUsers = createSelector(
  selectAllUsers,
  (users) => users.filter(user => user.isActive)
);

export const selectUserCount = createSelector(
  selectAllUsers,
  (users) => users.length
);

// Parameterized selector
export const selectUserById = (id: number) => createSelector(
  selectAllUsers,
  (users) => users.find(user => user.id === id)
);

// Using selectors in component
@Component({...})
export class UserListComponent {
  users$ = this.store.select(selectAllUsers);
  loading$ = this.store.select(selectUsersLoading);
  error$ = this.store.select(selectUsersError);
  activeUsers$ = this.store.select(selectActiveUsers);
  
  constructor(private store: Store) { }
  
  // With parameter
  getUserById(id: number): Observable<User | undefined> {
    return this.store.select(selectUserById(id));
  }
}
```

---

## 66. What are Effects?

### Answer:
- **Effects** handle **side effects** (API calls, navigation, etc.)
- Listen for actions, perform async operations
- Dispatch new actions based on results
- Keep reducers pure and synchronous

### Example:
```typescript
// effects/user.effects.ts
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, exhaustMap, catchError, tap } from 'rxjs/operators';
import * as UserActions from '../actions/user.actions';
import { UserService } from '../services/user.service';

@Injectable()
export class UserEffects {
  constructor(
    private actions$: Actions,
    private userService: UserService,
    private router: Router
  ) { }
  
  // Load users effect
  loadUsers$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.loadUsers),
      exhaustMap(() =>
        this.userService.getUsers().pipe(
          map(users => UserActions.loadUsersSuccess({ users })),
          catchError(error => of(UserActions.loadUsersFailure({ 
            error: error.message 
          })))
        )
      )
    )
  );
  
  // Add user effect
  addUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.addUser),
      exhaustMap(({ user }) =>
        this.userService.createUser(user).pipe(
          map(newUser => UserActions.addUserSuccess({ user: newUser })),
          catchError(error => of(UserActions.addUserFailure({ 
            error: error.message 
          })))
        )
      )
    )
  );
  
  // Delete user effect
  deleteUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.deleteUser),
      exhaustMap(({ id }) =>
        this.userService.deleteUser(id).pipe(
          map(() => UserActions.deleteUserSuccess({ id })),
          catchError(error => of(UserActions.deleteUserFailure({ 
            error: error.message 
          })))
        )
      )
    )
  );
  
  // Navigation effect (no dispatch)
  addUserSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.addUserSuccess),
      tap(() => this.router.navigate(['/users']))
    ),
    { dispatch: false }
  );
  
  // Show notification effect
  showError$ = createEffect(() =>
    this.actions$.pipe(
      ofType(
        UserActions.loadUsersFailure,
        UserActions.addUserFailure,
        UserActions.deleteUserFailure
      ),
      tap(({ error }) => {
        this.snackBar.open(error, 'Close', { duration: 5000 });
      })
    ),
    { dispatch: false }
  );
}
```

---

## 67. What is Store?

### Answer:
- **Store** is the **single source of truth**
- Holds the entire application state
- State is **read-only**, changes via dispatching actions
- Components **select** slices of state

### Example:
```typescript
// app.module.ts - Store setup
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';

@NgModule({
  imports: [
    StoreModule.forRoot({
      users: userReducer,
      products: productReducer
    }),
    EffectsModule.forRoot([
      UserEffects,
      ProductEffects
    ]),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: environment.production
    })
  ]
})
export class AppModule { }

// Feature module
@NgModule({
  imports: [
    StoreModule.forFeature('orders', orderReducer),
    EffectsModule.forFeature([OrderEffects])
  ]
})
export class OrderModule { }

// Using Store in component
@Component({
  selector: 'app-user-dashboard',
  template: `
    <div *ngIf="loading$ | async">Loading...</div>
    <div *ngIf="error$ | async as error" class="error">{{ error }}</div>
    
    <ul>
      <li *ngFor="let user of users$ | async">
        {{ user.name }}
      </li>
    </ul>
    
    <button (click)="loadUsers()">Refresh</button>
  `
})
export class UserDashboardComponent implements OnInit {
  users$ = this.store.select(selectAllUsers);
  loading$ = this.store.select(selectUsersLoading);
  error$ = this.store.select(selectUsersError);
  
  constructor(private store: Store) { }
  
  ngOnInit(): void {
    this.loadUsers();
  }
  
  loadUsers(): void {
    this.store.dispatch(loadUsers());
  }
}
```

---

## 68. When should you use NgRx?

### Answer:
**Use NgRx when:**
- Large application with complex state
- Multiple components need same data
- State changes need to be tracked/debugged
- Offline support required
- Team needs consistent patterns

**Don't use NgRx when:**
- Simple application
- State is mostly local to components
- Team is unfamiliar with Redux
- Overhead not justified

### Example Decision Matrix:
```
Application Size    | State Complexity | Use NgRx?
--------------------|------------------|----------
Small               | Simple           | No
Small               | Complex          | Maybe (signals)
Medium              | Simple           | No
Medium              | Complex          | Yes
Large               | Any              | Yes
```

---

## 69. Alternatives to NgRx?

### Answer:
| Library | Use Case |
|---------|----------|
| **Angular Signals** | Built-in, simple reactivity |
| **Services + BehaviorSubject** | Simple shared state |
| **NGXS** | Less boilerplate than NgRx |
| **Akita** | Entity-based state management |
| **Elf** | Lightweight, modern |

### Example:
```typescript
// Simple service-based state management
@Injectable({ providedIn: 'root' })
export class UserStore {
  private usersSubject = new BehaviorSubject<User[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  
  users$ = this.usersSubject.asObservable();
  loading$ = this.loadingSubject.asObservable();
  
  constructor(private http: HttpClient) { }
  
  loadUsers(): void {
    this.loadingSubject.next(true);
    this.http.get<User[]>('/api/users').subscribe({
      next: (users) => {
        this.usersSubject.next(users);
        this.loadingSubject.next(false);
      },
      error: () => this.loadingSubject.next(false)
    });
  }
  
  addUser(user: User): void {
    const current = this.usersSubject.value;
    this.usersSubject.next([...current, user]);
  }
}

// Angular Signals (Angular 16+)
@Injectable({ providedIn: 'root' })
export class UserStore {
  users = signal<User[]>([]);
  loading = signal(false);
  
  // Computed
  activeUsers = computed(() => 
    this.users().filter(u => u.isActive)
  );
  
  loadUsers(): void {
    this.loading.set(true);
    // ...
    this.users.set(fetchedUsers);
    this.loading.set(false);
  }
}
```

---
