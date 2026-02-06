# Angular Senior & Architect Level Interview Questions & Expected Answers

## **Architecture & Design**

### 1. **Explain Angular architecture in detail**
- **Component-based architecture**: UI built from tree of components
- **Modular structure**: Organized as NgModules (AppModule, FeatureModules, SharedModule, CoreModule)
- **Dependency Injection**: Hierarchical injector system for service management
- **Template system**: HTML enhanced with Angular directives and data binding
- **Change detection**: Zone.js monitoring async operations, OnPush/default strategies
- **Router**: Client-side navigation with lazy loading support
- **RxJS integration**: Reactive programming for async operations
- **Build system**: AOT/JIT compilation, tree-shaking, bundle optimization

### 2. **How do you design a large-scale Angular application?**
- **Start with monorepo**: Use Nx workspace for shared tooling and libraries
- **Domain-driven structure**: Organize by business domains rather than technical layers
- **Feature modules**: Each business capability in separate module with lazy loading
- **Shared infrastructure**: CoreModule (singletons), SharedModule (reusable components)
- **State management strategy**: Evaluate NgRx vs services based on complexity
- **Micro-frontend architecture**: For very large apps, use Module Federation
- **Configuration management**: Environment-specific builds, feature flags
- **Testing strategy**: Unit, integration, E2E with consistent patterns
- **CI/CD pipeline**: Automated builds, tests, deployments with quality gates

### 3. **How do you decide module boundaries in Angular?**
- **Business domain boundaries**: One module per business capability
- **Team structure**: Align with team ownership boundaries
- **Lazy loading requirements**: Separate modules for code splitting
- **Reusability**: SharedModule for cross-cutting components
- **Performance considerations**: Split based on bundle size targets
- **Security boundaries**: Separate modules for different access levels
- **Key indicators**:
  - Module should have clear, single responsibility
  - Should encapsulate related components, services, routing
  - Should define clear public API via exports
  - Should manage its own dependencies

### 4. **What is the difference between CoreModule and SharedModule?**
- **CoreModule**:
  - Contains singleton services (providedIn: 'root' preferred)
  - Contains app-wide singleton components (navbar, footer)
  - Contains interceptors, guards, global error handlers
  - Imported only once in AppModule
  - Should not contain declarations used elsewhere

- **SharedModule**:
  - Contains reusable components, directives, pipes
  - Exports CommonModule, FormsModule, etc.
  - No providers (services) should be declared here
  - Can be imported by multiple feature modules
  - Typically contains UI components, form controls, utilities

### 5. **How do you prevent multiple service instances in Angular?**
- **Use providedIn: 'root'**:
  ```typescript
  @Injectable({ providedIn: 'root' })
  export class MyService { }
  ```
- **Register in CoreModule**: Provide in CoreModule providers array
- **Avoid in SharedModule**: Never provide services in SharedModule
- **Component-level providers**: Only when deliberately needing separate instances
- **Module-level providers**: Use for module-specific services
- **Check provider hierarchy**: Understand injector tree (root > module > component)
- **Lazy-loaded modules**: Be aware they create child injectors

### 6. **Explain feature modules vs lazy-loaded modules**
- **Feature modules**: Logical grouping of related functionality
- **Lazy-loaded modules**: Feature modules loaded on-demand
- **Key differences**:
  - **Loading time**: Lazy modules load at runtime, features may be eager
  - **Bundle size**: Lazy modules split code into separate chunks
  - **Routing**: Lazy modules use `loadChildren` syntax
  - **Dependencies**: Lazy modules should be self-contained
- **Best practice**: Most feature modules should be lazy-loaded
- **Exception**: Small modules used immediately can be eager-loaded

### 7. **How do you structure Angular apps in micro-frontend architecture?**
- **Module Federation** (Webpack 5): Primary approach for Angular
- **Single-SPA**: Alternative framework for micro-frontends
- **Key considerations**:
  1. **Independent deployment**: Each micro-app deploys separately
  2. **Shared dependencies**: Avoid duplication of Angular, RxJS
  3. **Communication**: Custom events, shared services, state management
  4. **Routing**: Host app manages main routing, micro-apps handle sub-routes
  5. **Styling**: CSS isolation strategies (shadow DOM, scoped styles)
  6. **Build coordination**: Version compatibility between micro-apps
  7. **Host-container pattern**: Shell app loads micro-apps dynamically

### 8. **How does Single-SPA / Module Federation work with Angular?**
- **Single-SPA**:
  - Framework-agnostic micro-frontend solution
  - Angular wrapper (`single-spa-angular`) provides integration
  - Each Angular app registers as Single-SPA application
  - Root config orchestrates multiple apps
  - Uses SystemJS for module loading

- **Module Federation**:
  - Webpack 5 feature for sharing modules at runtime
  - **Host app**: Exposes components/routes to be consumed
  - **Remote app**: Consumes exposed modules from host
  - **Shared dependencies**: Angular, RxJS shared to prevent duplication
  - **Dynamic loading**: RemoteEntry points for runtime integration

### 9. **How do you handle cross-cutting concerns in Angular?**
- **Interceptors**: HTTP interceptors for auth, logging, error handling
- **Guards**: Route guards for authentication/authorization
- **Services**: Singleton services for shared business logic
- **Directives**: Attribute directives for reusable behaviors
- **Pipes**: For data transformation across components
- **Base components**: Abstract classes for common functionality
- **Decorators**: Method/class decorators for AOP concerns
- **Error handlers**: Global error handler for uncaught exceptions
- **Logging service**: Centralized logging with different levels

### 10. **How do you enforce scalable folder structure?**
- **Establish conventions early**: Document and socialize structure
- **Use Nx workspace**: Enforces consistent structure across projects
- **Domain-driven folders**:
  ```
  src/app/
  ├── features/          # Feature modules
  │   ├── orders/       # Order management
  │   ├── customers/    # Customer management
  │   └── products/     # Product catalog
  ├── core/             # Singleton services
  ├── shared/           # Reusable components
  ├── models/           # TypeScript interfaces
  ├── utils/            # Utility functions
  └── styles/           # Global styles
  ```
- **Linting rules**: ESLint/TSLint rules for import patterns
- **Code reviews**: Enforce structure during PR reviews
- **Generator tools**: Angular CLI schematics for consistent generation
- **Documentation**: README files explaining the structure

---

## **Change Detection & Performance (VERY IMPORTANT)**

### 11. **Explain Angular Change Detection in depth**
- **Purpose**: Synchronize component state with DOM
- **Mechanism**:
  1. Zone.js patches async APIs (setTimeout, promises, events)
  2. Async operation completion triggers change detection
  3. Angular walks component tree checking bindings
  4. Updates DOM where values have changed
- **Two strategies**:
  - **Default**: Checks all components after any async event
  - **OnPush**: Only checks when inputs change or events fire
- **ChangeDetectorRef API**: `detectChanges()`, `markForCheck()`, `detach()`
- **Lifecycle integration**: Runs after ngOnInit, ngAfterViewInit, etc.

### 12. **What is Zone.js and why does Angular use it?**
- **Zone.js**: Library that patches async JavaScript APIs
- **Monkey patching**: Overwrites setTimeout, Promise, addEventListener, etc.
- **Purpose**: Know when async operations complete to trigger change detection
- **Execution context**: Zones capture async operations for tracking
- **Angular's NgZone**: Wrapper around Zone.js for Angular-specific logic
- **RunOutsideAngular**: For performance-critical code avoiding CD
- **Performance impact**: Zone.js adds overhead but enables automatic CD

### 13. **Difference between Default and OnPush change detection**
- **Default strategy**:
  - Checks all components in tree
  - Runs after any async event in zone
  - Simpler but less performant
  - Good for small apps or prototypes

- **OnPush strategy**:
  - Only checks component when:
    1. Input reference changes (`@Input()`)
    2. Component emits event
    3. Async pipe receives new value
    4. Change detection triggered manually
  - Requires immutable data patterns
  - Significant performance improvement
  - Essential for large applications

### 14. **When does Angular run change detection?**
1. **User events**: Click, input, submit, etc.
2. **Async operations**: HTTP responses, setTimeout, setInterval
3. **Observable emissions**: When subscribed with async pipe
4. **Manual triggering**: `ChangeDetectorRef.detectChanges()`
5. **Component initialization**: After ngOnInit, ngAfterViewInit
6. **Zone.js triggers**: Any async operation within Angular zone
7. **Tick() in tests**: `fixture.detectChanges()` in unit tests

### 15. **How do you optimize Angular performance?**
1. **OnPush change detection**: For most components
2. **Lazy loading**: Split code into chunks
3. **TrackBy with *ngFor**: Prevent DOM re-creation
4. **Pure pipes**: Avoid method calls in templates
5. **Virtual scrolling**: For large lists (CDK Virtual Scroll)
6. **Memoization**: Cache expensive computations
7. **Unsubscribe properly**: Prevent memory leaks
8. **AOT compilation**: Production builds only
9. **Bundle optimization**: Code splitting, tree-shaking
10. **Server-side rendering**: Angular Universal for SEO/initial load

### 16. **What are pure vs impure pipes?**
- **Pure pipes** (default):
  - Stateless transformation
  - Only recalculates when input reference changes
  - Angular caches results for same inputs
  - Use for simple, deterministic transformations
  - Example: `currency`, `date`, `uppercase`

- **Impure pipes** (`pure: false`):
  - Recalculates every change detection cycle
  - Can have side effects
  - Performance expensive
  - Use only when necessary (stateful transformations)
  - Example: Custom pipes reacting to global state

### 17. **How do you avoid unnecessary re-renders?**
1. **Use OnPush strategy**: Limit change detection scope
2. **Immutable data**: Change object references instead of mutating
3. **Memoized selectors**: Cache derived state
4. **TrackBy function**: Identify items in lists
5. **Detach change detectors**: For static components
6. **RunOutsideAngular**: For performance-critical code
7. **Debounce rapid events**: Search inputs, scroll events
8. **Avoid method calls in templates**: Use pure pipes or computed properties
9. **Split components**: Isolate frequently changing parts

### 18. **What is trackBy and why is it important?**
- **Purpose**: Identify items in `*ngFor` to prevent DOM re-creation
- **Problem**: Without trackBy, Angular destroys and recreates DOM on array changes
- **Solution**: Provide unique identifier function
  ```html
  <div *ngFor="let item of items; trackBy: trackById">
  ```
  ```typescript
  trackById(index: number, item: any): number {
    return item.id;
  }
  ```
- **Benefits**:
  - Better performance for large lists
  - Preserves DOM state (form inputs, focus)
  - Smother animations during updates
  - Essential for virtual scrolling

### 19. **How do you debug performance issues in Angular?**
1. **Angular DevTools**:
   - Component explorer
   - Profiler for change detection
   - Dependency injection graph
2. **Chrome DevTools**:
   - Performance tab for recording
   - Memory tab for leaks
   - Network tab for bundle analysis
3. **Console profiling**:
   ```typescript
   window.ng.profiler.timeChangeDetection();
   ```
4. **Bundle analysis**:
   - `source-map-explorer`
   - Webpack Bundle Analyzer
5. **Lighthouse audits**: Performance metrics
6. **Custom metrics**: Timing critical operations
7. **A/B testing**: Compare different implementations

### 20. **How do you handle large lists (10k+ rows)?**
1. **Virtual scrolling** (Angular CDK):
   ```html
   <cdk-virtual-scroll-viewport itemSize="50">
     <div *cdkVirtualFor="let item of items">
       {{item.name}}
     </div>
   </cdk-virtual-scroll-viewport>
   ```
2. **Pagination**: Server-side or client-side
3. **Infinite scrolling**: Load more on scroll
4. **Windowing**: Render only visible items
5. **Performance optimizations**:
   - `trackBy` with unique IDs
   - Simplified templates
   - OnPush change detection
   - Debounced filtering/sorting
6. **Web Workers**: Offload filtering/sorting
7. **IndexedDB**: Client-side storage for large datasets

---

## **RxJS & Reactive Programming (MANDATORY)**

### 21. **Explain Observable vs Promise**
- **Observable**:
  - Stream of multiple values over time
  - Lazy execution (starts on subscribe)
  - Cancellable via unsubscribe
  - Rich operators (map, filter, merge, etc.)
  - Supports multiple subscribers
  - Better for complex async flows

- **Promise**:
  - Single future value
  - Eager execution (starts immediately)
  - Not cancellable
  - Limited chaining (.then, .catch)
  - Single subscriber resolution
  - Simpler API for basic async

### 22. **What are cold and hot observables?**
- **Cold observables**:
  - Start producing values on subscription
  - Each subscriber gets independent stream
  - Example: `HttpClient.get()`, `timer()`, `of()`
  - Like watching a movie on Netflix

- **Hot observables**:
  - Produce values regardless of subscribers
  - Subscribers get values from time of subscription
  - Example: `fromEvent()`, Subjects, shared multicast
  - Like watching live TV broadcast

### 23. **Difference between Subject, BehaviorSubject, ReplaySubject**
- **Subject**:
  - No initial value
  - Only emits values after subscription
  - Multicasts to all subscribers
  - Example: Event bus

- **BehaviorSubject**:
  - Requires initial value
  - Emits current value to new subscribers
  - Always has "current value"
  - Example: Current user, form state

- **ReplaySubject**:
  - Can replay specified number of previous values
  - New subscribers get replayed values
  - Configurable buffer size and window time
  - Example: Chat history, audit trail

### 24. **Explain mergeMap vs switchMap vs concatMap vs exhaustMap**
- **mergeMap** (flatMap):
  - Maps to observable, merges results
  - All inner observables run concurrently
  - Order not guaranteed
  - Use for independent parallel operations

- **switchMap**:
  - Maps to observable, cancels previous inner observable
  - Only latest inner observable runs
  - Use for typeahead search, cancel previous requests

- **concatMap**:
  - Maps to observable, runs sequentially
  - Waits for previous to complete
  - Maintains order
  - Use for sequential operations (save steps)

- **exhaustMap**:
  - Ignores new source emissions while inner observable runs
  - Drops emissions during active inner observable
  - Use for prevent duplicate submissions

### 25. **When would you use switchMap over mergeMap?**
- **Typeahead search**: Cancel previous search on new input
- **Auto-save**: Cancel previous save on new changes
- **Route navigation**: Cancel previous data load on new route
- **Button clicks**: Prevent multiple API calls from rapid clicks
- **Real-time filtering**: Cancel previous filter on new criteria
- **Key principle**: When you only care about latest result, not all intermediate results

### 26. **What are higher-order observables?**
- **Definition**: Observables that emit other observables
- **Higher-order mapping operators**: `mergeMap`, `switchMap`, `concatMap`, `exhaustMap`
- **Example**: HTTP calls inside observable streams
  ```typescript
  searchTerm$.pipe(
    debounceTime(300),
    switchMap(term => this.api.search(term))
  )
  ```
- **Flattening**: Process inner observables and flatten back to single stream
- **Common pattern**: Transform event streams into data streams

### 27. **How do you manage subscriptions properly?**
1. **Async pipe** (automatic cleanup):
   ```html
   {{data$ | async}}
   ```
2. **takeUntil pattern**:
   ```typescript
   private destroy$ = new Subject<void>();
   
   ngOnInit() {
     observable$
       .pipe(takeUntil(this.destroy$))
       .subscribe();
   }
   
   ngOnDestroy() {
     this.destroy$.next();
     this.destroy$.complete();
   }
   ```
3. **take/takeWhile operators**:
   ```typescript
   observable$.pipe(take(1)).subscribe(); // Single emission
   observable$.pipe(takeWhile(() => this.active)).subscribe();
   ```
4. **Manual unsubscribe**:
   ```typescript
   private subscription: Subscription;
   
   ngOnInit() {
     this.subscription = observable$.subscribe();
   }
   
   ngOnDestroy() {
     this.subscription.unsubscribe();
   }
   ```

### 28. **What is async pipe and why is it important?**
- **Purpose**: Automatically subscribes/unsubscribes in templates
- **Syntax**: `{{observable$ | async}}`
- **Benefits**:
  - Automatic subscription management
  - No memory leaks
  - Triggers change detection automatically
  - Cleaner component code
  - Works with OnPush change detection
- **Use cases**:
  - Displaying observable data
  - Combining with structural directives
  - Conditional rendering based on streams

### 29. **Explain multicasting in RxJS**
- **Problem**: Cold observables execute for each subscriber
- **Solution**: Share single execution among multiple subscribers
- **Operators**:
  - `share()`: Automatically refcounts subscribers
  - `shareReplay(bufferSize)`: Replays values to late subscribers
  - `publish()` + `connect()`: Manual control
  - `multicast()` with Subject
- **Use case**: Prevent duplicate HTTP calls
  ```typescript
  data$ = this.http.get('/api/data').pipe(
    shareReplay(1)
  );
  ```

### 30. **How do you handle error handling in RxJS streams?**
1. **catchError operator**:
   ```typescript
   observable$.pipe(
     catchError(error => {
       // Handle error
       return of(defaultValue); // Recovery observable
     })
   )
   ```
2. **retry operators**:
   ```typescript
   observable$.pipe(
     retry(3), // Retry 3 times
     retryWhen(errors => errors.pipe(delay(1000))) // Retry with delay
   )
   ```
3. **Error completion**: Uncaught errors complete the observable
4. **Global error handling**: HTTP interceptors for API errors
5. **User feedback**: Combine with UI notification streams
6. **Fallback strategies**: Switch to backup data source



## **Forms (Advanced Level)**

### 31. **Template-driven vs Reactive forms (deep comparison)**
- **Template-driven forms**:
  - Forms defined and managed in template
  - Uses `[(ngModel)]` for two-way binding
  - Asynchronous - Angular creates controls behind scenes
  - Simpler, less code for basic forms
  - Harder to unit test
  - Limited dynamic form capabilities
  - Good for simple forms, prototypes

- **Reactive forms**:
  - Forms defined and managed in component class
  - Synchronous - explicit control creation
  - More control and flexibility
  - Better performance for complex forms
  - Easier unit testing
  - Supports dynamic form controls
  - Better for complex validation scenarios
  - Preferred for enterprise applications

### 32. **How do Reactive Forms internally work?**
- **FormControl**: Tracks value, validation status of individual control
- **FormGroup**: Groups multiple controls, tracks group status
- **FormArray**: Manages array of controls
- **AbstractControl**: Base class with common properties
- **Value changes observable**: `valueChanges` emits on changes
- **Status changes observable**: `statusChanges` emits on validation changes
- **Validation**: Sync/async validators applied to controls
- **Update strategies**: `setValue()`, `patchValue()`, `reset()`
- **Dirty/pristine/touched**: Track user interaction state

### 33. **What is FormArray and when do you use it?**
- **Purpose**: Manage dynamic list of form controls
- **Use cases**:
  - Dynamic list of items (phone numbers, emails)
  - Repeating sections in forms
  - Array-based data structures
  - When number of controls is unknown at compile time
- **Example**:
  ```typescript
  this.form = this.fb.group({
    items: this.fb.array([
      this.fb.control(''),
      this.fb.control('')
    ])
  });
  
  get items(): FormArray {
    return this.form.get('items') as FormArray;
  }
  
  addItem() {
    this.items.push(this.fb.control(''));
  }
  ```

### 34. **How do you build dynamic forms?**
1. **FormArray approach**: For repeated controls
2. **Component factory**: Generate form components dynamically
3. **Configuration-driven forms**: JSON configuration defining form structure
4. **Dynamic control addition**:
   ```typescript
   addControl(controlName: string) {
     this.form.addControl(controlName, this.fb.control(''));
   }
   ```
5. **Template approach with ngComponentOutlet**: For complex dynamic components
6. **Metadata-driven**: Use decorators or configuration objects
7. **State management**: Track dynamic form state in service

### 35. **How do you implement custom validators?**
- **Sync validators** (return ValidationErrors | null):
  ```typescript
  export function customValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const valid = /* validation logic */;
      return valid ? null : { customError: true };
    };
  }
  ```
- **Async validators** (return Promise/Observable):
  ```typescript
  export function asyncValidator(api: ApiService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return api.checkValue(control.value).pipe(
        map(isValid => isValid ? null : { asyncError: true })
      );
    };
  }
  ```
- **Usage**:
  ```typescript
  this.form = this.fb.group({
    field: ['', [Validators.required, customValidator()]]
  });
  ```

### 36. **How do you implement async validators?**
1. **Create async validator function**:
   ```typescript
   export function uniqueEmailValidator(api: UserService): AsyncValidatorFn {
     return (control: AbstractControl): Observable<ValidationErrors | null> => {
       return api.checkEmailExists(control.value).pipe(
         map(exists => exists ? { emailExists: true } : null),
         catchError(() => of(null)) // Handle errors gracefully
       );
     };
   }
   ```
2. **Apply to form control**:
   ```typescript
   this.form = this.fb.group({
     email: ['', null, uniqueEmailValidator(this.userService)]
   });
   ```
3. **Considerations**:
   - Debounce user input to prevent excessive API calls
   - Cancel previous validation on new input
   - Show loading state during validation
   - Cache validation results when appropriate

### 37. **How do you handle cross-field validation?**
1. **FormGroup level validator**:
   ```typescript
   export function passwordMatchValidator(group: FormGroup): ValidationErrors | null {
     const password = group.get('password').value;
     const confirmPassword = group.get('confirmPassword').value;
     return password === confirmPassword ? null : { mismatch: true };
   }
   
   this.form = this.fb.group({
     password: [''],
     confirmPassword: ['']
   }, { validators: passwordMatchValidator });
   ```
2. **Custom validator accessing sibling controls**:
   ```typescript
   export function dateRangeValidator(): ValidatorFn {
     return (control: AbstractControl): ValidationErrors | null => {
       const group = control.parent;
       if (!group) return null;
       const start = group.get('startDate').value;
       const end = group.get('endDate').value;
       return start && end && start > end ? { invalidRange: true } : null;
     };
   }
   ```

### 38. **How do you manage large complex forms?**
1. **Break into sub-forms**: Use nested FormGroups
2. **Component decomposition**: Split into smaller form components
3. **Form state management**: Use services or state management (NgRx)
4. **Validation strategy**:
   - Lazy validation for non-critical fields
   - Async validation with debouncing
   - Cross-field validation at appropriate levels
5. **Performance optimizations**:
   - OnPush change detection for form components
   - Debounced valueChanges subscriptions
   - Virtual scrolling for large form arrays
6. **Persistence**: Auto-save with debounce, draft saving
7. **Stepper/wizard pattern**: Break into multiple steps/pages

### 39. **How do you improve form performance?**
1. **OnPush change detection**: For all form components
2. **Debounce valueChanges**:
   ```typescript
   this.form.valueChanges.pipe(
     debounceTime(300)
   ).subscribe(values => {
     // Process changes
   });
   ```
3. **Lazy validation**: Only validate on blur or submit for non-critical fields
4. **Optimize template bindings**: Avoid method calls in templates
5. **Virtual scrolling**: For FormArrays with many items
6. **Disable change detection**: For static form sections
7. **Memoize expensive computations**: Cache validation results
8. **Use trackBy**: For *ngFor with form arrays

### 40. **How do you reuse form logic?**
1. **Custom form controls**: Implement ControlValueAccessor
   ```typescript
   @Component({
     selector: 'app-custom-control',
     providers: [{
       provide: NG_VALUE_ACCESSOR,
       useExisting: CustomControlComponent,
       multi: true
     }]
   })
   export class CustomControlComponent implements ControlValueAccessor {
     // Implement writeValue, registerOnChange, registerOnTouched
   }
   ```
2. **Base form classes**: Common form functionality
3. **Form services**: Shared validation, transformation logic
4. **Directives**: Reusable validation directives
5. **Template reuse**: Shared form templates with ngTemplateOutlet
6. **Configuration objects**: Define form structure and validation rules
7. **Form builder utilities**: Helper functions for common form patterns

---

## **Authentication & Security**

### 41. **How do you implement authentication in Angular?**
1. **Auth Service**: Central authentication logic
   ```typescript
   @Injectable({ providedIn: 'root' })
   export class AuthService {
     private tokenSubject = new BehaviorSubject<string>(null);
     
     login(credentials): Observable<void> {
       return this.http.post('/api/login', credentials).pipe(
         tap(response => this.storeToken(response.token))
       );
     }
     
     logout() {
       this.removeToken();
     }
   }
   ```
2. **Token storage**: HttpOnly cookies (recommended) or secure localStorage
3. **HTTP Interceptor**: Add auth token to requests
4. **Route Guards**: Protect routes based on auth state
5. **Token refresh**: Handle expired tokens automatically
6. **Multi-tab sync**: Share auth state across tabs

### 42. **JWT vs Session-based authentication**
- **JWT (JSON Web Token)**:
  - Stateless - server doesn't store session
  - Self-contained (claims in token)
  - Good for distributed systems
  - Token size can be large
  - Hard to revoke (requires blacklist)
  - Stored client-side

- **Session-based**:
  - Stateful - server stores session
  - Session ID sent in cookie
  - Easy to revoke (delete session)
  - Smaller payload (just session ID)
  - Requires session storage
  - Better for traditional web apps
  - CSRF protection needed

### 43. **Where do you store tokens and why?**
1. **HttpOnly Cookies** (Recommended):
   - Secure, not accessible via JavaScript
   - Automatically sent with requests
   - Protected from XSS
   - Requires CSRF protection
   - Domain-specific

2. **localStorage**:
   - Accessible via JavaScript
   - Vulnerable to XSS attacks
   - Manual token management required
   - Persists across browser sessions
   - Can be stolen if XSS occurs

3. **sessionStorage**:
   - Similar to localStorage
   - Cleared on tab close
   - Better for sensitive temporary data

4. **Memory**:
   - Most secure (cleared on refresh)
   - No persistence
   - Requires re-authentication often

### 44. **How do HTTP interceptors work internally?**
- **Chain of responsibility pattern**: Multiple interceptors process requests
- **Request flow**:
  1. Interceptor receives HttpRequest
  2. Can clone and modify request
  3. Calls `next.handle(modifiedRequest)`
  4. Response flows back through chain
- **Response flow**: Reverse order of request chain
- **Implementation**:
  ```typescript
  @Injectable()
  export class AuthInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(cloned);
    }
  }
  ```

### 45. **How do you handle token refresh?**
1. **Interceptor approach**:
   ```typescript
   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
     return next.handle(this.addToken(req)).pipe(
       catchError(error => {
         if (error.status === 401 && !req.url.includes('/refresh')) {
           return this.handle401Error(req, next);
         }
         return throwError(error);
       })
     );
   }
   
   private handle401Error(req: HttpRequest<any>, next: HttpHandler) {
     if (!this.refreshTokenInProgress) {
       this.refreshTokenInProgress = true;
       
       return this.authService.refreshToken().pipe(
         switchMap((newToken: string) => {
           this.refreshTokenInProgress = false;
           return next.handle(this.addToken(req, newToken));
         }),
         catchError(() => {
           this.refreshTokenInProgress = false;
           this.authService.logout();
           return throwError(error);
         })
       );
     } else {
       // Wait for refresh to complete
       return this.refreshSubject.pipe(
         filter(token => token !== null),
         take(1),
         switchMap(token => next.handle(this.addToken(req, token)))
       );
     }
   }
   ```

### 46. **What are route guards and types of guards?**
1. **CanActivate**: Control access to route
   ```typescript
   @Injectable()
   export class AuthGuard implements CanActivate {
     canActivate(): boolean | Observable<boolean> {
       return this.authService.isAuthenticated();
     }
   }
   ```
2. **CanActivateChild**: Control access to child routes
3. **CanDeactivate**: Ask permission to leave route (unsaved changes)
4. **CanLoad**: Control loading of lazy-loaded modules
5. **Resolve**: Pre-fetch data before route activation
6. **Usage in routes**:
   ```typescript
   const routes: Routes = [{
     path: 'protected',
     component: ProtectedComponent,
     canActivate: [AuthGuard]
   }];
   ```

### 47. **How do you handle role-based access control?**
1. **Role-based guards**:
   ```typescript
   @Injectable()
   export class RoleGuard implements CanActivate {
     canActivate(route: ActivatedRouteSnapshot): boolean {
       const expectedRoles = route.data.roles;
       const userRole = this.authService.getUserRole();
       return expectedRoles.includes(userRole);
     }
   }
   ```
2. **Route configuration**:
   ```typescript
   const routes: Routes = [{
     path: 'admin',
     component: AdminComponent,
     canActivate: [RoleGuard],
     data: { roles: ['admin', 'superadmin'] }
   }];
   ```
3. **Directive for UI elements**:
   ```typescript
   @Directive({ selector: '[appHasRole]' })
   export class HasRoleDirective {
     @Input() appHasRole: string[];
     
     constructor(private templateRef: TemplateRef<any>,
                 private viewContainer: ViewContainerRef,
                 private authService: AuthService) {}
     
     ngOnInit() {
       const hasRole = this.authService.hasAnyRole(this.appHasRole);
       hasRole ? this.viewContainer.createEmbeddedView(this.templateRef)
               : this.viewContainer.clear();
     }
   }
   ```

### 48. **How do you protect Angular apps from XSS / CSRF?**
- **XSS Protection**:
  1. **Built-in Angular sanitization**: Automatically sanitizes bindings
  2. **DomSanitizer**: For trusted content
  3. **Content Security Policy (CSP)**: HTTP header
  4. **Input validation**: Validate and sanitize user input
  5. **Avoid innerHTML**: Use Angular templates instead
  6. **HttpOnly cookies**: Store tokens securely

- **CSRF Protection**:
  1. **SameSite cookies**: Set to 'Strict' or 'Lax'
  2. **CSRF tokens**: Server-generated tokens
  3. **Double-submit cookies**: Random token in cookie and request
  4. **Angular HttpClient**: Built-in CSRF protection with `HttpClientXsrfModule`

### 49. **How do you secure environment configurations?**
1. **Build-time configurations** (`environment.ts`):
   - Separate files for different environments
   - Values baked into bundle at build time
   - Not suitable for secrets

2. **Runtime configurations**:
   - Load config from external JSON file
   - Fetch from API endpoint
   - Use Docker/Kubernetes environment variables

3. **Secrets management**:
   - Never store secrets in frontend code
   - Use backend proxies for sensitive operations
   - Use environment variables in CI/CD
   - Hash/encrypt sensitive config values

4. **Best practices**:
   - Validate configurations at startup
   - Fallback to defaults
   - Cache configurations appropriately

### 50. **How do you handle logout across multiple tabs?**
1. **BroadcastChannel API** (modern browsers):
   ```typescript
   export class AuthService {
     private logoutChannel = new BroadcastChannel('logout');
     
     constructor() {
       this.logoutChannel.onmessage = () => this.clearLocalAuth();
     }
     
     logoutAllTabs() {
       this.logoutChannel.postMessage('logout');
       this.clearLocalAuth();
     }
   }
   ```
2. **localStorage events**:
   ```typescript
   @HostListener('window:storage', ['$event'])
   onStorageChange(event: StorageEvent) {
     if (event.key === 'logout' && event.newValue) {
       this.authService.logout();
     }
   }
   ```
3. **WebSockets**: Real-time logout notification
4. **Polling**: Check auth status periodically
5. **Token invalidation**: On server-side for immediate effect

---

## **Routing & Navigation**

### 51. **How does Angular Router work internally?**
- **Route configuration**: Tree structure defining URL to component mapping
- **Location strategy**: HashLocationStrategy vs PathLocationStrategy
- **Route matching**: Matches URL against route definitions
- **Component activation**: Instantiates and activates components
- **Navigation cycle**:
  1. Navigation triggered (routerLink or router.navigate)
  2. Guards executed (CanActivate, CanDeactivate)
  3. Resolvers fetch data
  4. Old components deactivated
  5. New components activated
  6. URL updated
- **Route reuse**: Strategy for caching components
- **Lazy loading**: Loads modules on-demand

### 52. **Lazy loading vs Preloading strategies**
- **Lazy loading**: Load module only when route is accessed
  ```typescript
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
  ```
- **Preloading strategies**:
  1. **NoPreloading**: Default, no preloading
  2. **PreloadAllModules**: Loads all lazy modules after initial load
  3. **QuicklinkStrategy**: Preloads modules for visible links
  4. **Custom strategy**: Business logic-based preloading
- **Custom preloader example**:
  ```typescript
  @Injectable()
  export class CustomPreloadingStrategy implements PreloadingStrategy {
    preload(route: Route, load: () => Observable<any>): Observable<any> {
      return route.data && route.data.preload ? load() : of(null);
    }
  }
  ```

### 53. **What is Resolve Guard?**
- **Purpose**: Pre-fetch data before component initialization
- **Implementation**:
  ```typescript
  @Injectable()
  export class UserResolver implements Resolve<User> {
    constructor(private userService: UserService) {}
    
    resolve(route: ActivatedRouteSnapshot): Observable<User> {
      const userId = route.params.id;
      return this.userService.getUser(userId);
    }
  }
  ```
- **Route configuration**:
  ```typescript
  { 
    path: 'user/:id', 
    component: UserComponent,
    resolve: { user: UserResolver }
  }
  ```
- **Component access**:
  ```typescript
  ngOnInit() {
    this.user = this.route.snapshot.data.user;
  }
  ```
- **Benefits**: Component initialized with data, no loading states needed

### 54. **How do you handle deep linking?**
- **Route parameters**: Capture IDs from URL
  ```typescript
  { path: 'product/:id', component: ProductComponent }
  ```
- **Query parameters**: Filtering, sorting, pagination
  ```typescript
  // Set query params
  this.router.navigate(['/products'], { queryParams: { page: 2 } });
  
  // Read query params
  this.route.queryParams.subscribe(params => {
    this.page = params.page;
  });
  ```
- **Fragment**: Navigate to page sections
- **State object**: Pass complex data via NavigationExtras
- **URL encoding**: Properly encode/decode parameters
- **Route resolvers**: Ensure data is loaded before component renders

### 55. **How do you pass data between routes?**
1. **Route parameters**: Simple IDs
   ```typescript
   this.router.navigate(['/user', userId]);
   ```
2. **Query parameters**: Filter/sort data
   ```typescript
   this.router.navigate(['/products'], { queryParams: { sort: 'name' } });
   ```
3. **Route data**: Static data in route configuration
4. **Resolve guard**: Pre-fetched data
5. **NavigationExtras state**:
   ```typescript
   this.router.navigate(['/details'], { 
     state: { product: this.selectedProduct }
   });
   
   // Receive in target component
   this.product = this.router.getCurrentNavigation().extras.state.product;
   ```
6. **Shared service**: Store data in service
7. **State management**: NgRx/Akita for global state

### 56. **Route reuse strategy – have you implemented it?**
- **Purpose**: Cache components to avoid re-creation
- **Default strategy**: Reuses components when navigating to same route
- **Custom implementation**:
  ```typescript
  @Injectable()
  export class CustomReuseStrategy implements RouteReuseStrategy {
    private storedRoutes = new Map<string, DetachedRouteHandle>();
    
    shouldDetach(route: ActivatedRouteSnapshot): boolean {
      return route.data.reuse || false;
    }
    
    store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle): void {
      this.storedRoutes.set(this.getRouteKey(route), handle);
    }
    
    shouldAttach(route: ActivatedRouteSnapshot): boolean {
      return this.storedRoutes.has(this.getRouteKey(route));
    }
    
    retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle {
      return this.storedRoutes.get(this.getRouteKey(route));
    }
    
    shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
      return future.routeConfig === curr.routeConfig;
    }
    
    private getRouteKey(route: ActivatedRouteSnapshot): string {
      return route.pathFromRoot.map(r => r.url.map(segment => segment.toString()).join('/')).join('/');
    }
  }
  ```

### 57. **How do you handle unauthorized navigation?**
1. **Auth guard redirect**:
   ```typescript
   canActivate(): boolean | UrlTree {
     if (!this.authService.isAuthenticated()) {
       return this.router.parseUrl('/login');
     }
     return true;
   }
   ```
2. **Redirect after login**: Store attempted URL
   ```typescript
   login(): void {
     this.authService.login(credentials).subscribe(() => {
       const redirectUrl = this.authService.redirectUrl || '/dashboard';
       this.router.navigate([redirectUrl]);
     });
   }
   ```
3. **Global error handler**: Redirect on 401 responses
4. **HTTP interceptor**: Catch 401 errors and redirect to login
5. **Route protection**: Guard all protected routes

### 58. **How do you optimize routing for large apps?**
1. **Lazy loading**: Split by feature modules
2. **Preloading strategy**: Balance between initial load and navigation speed
3. **Route guards optimization**: Cache guard results when possible
4. **Route configuration splitting**: Separate routing modules
5. **AOT compilation**: For faster route resolution
6. **Minimal resolvers**: Only use when absolutely necessary
7. **Route reuse**: For frequently visited routes
8. **Bundle analysis**: Monitor lazy chunk sizes
9. **Progressive loading**: Load essential routes first

---

## **State Management**

### 59. **When do you need state management?**
1. **Shared state**: Multiple components need same data
2. **Complex state transitions**: Business logic in state changes
3. **Time-travel debugging**: Need to replay actions
4. **Server-state synchronization**: Optimistic updates, conflict resolution
5. **Undo/redo functionality**: State history management
6. **Large application scale**: Team coordination, predictability
7. **Persistent state**: Across sessions/routes
8. **Derived state**: Computed values from multiple sources

**Red flags for state management**:
- Simple parent-child communication
- Small application size
- Simple data flow
- Single developer/team

### 60. **NgRx vs Akita vs BehaviorSubject – comparison**
- **NgRx** (Redux pattern):
  - Strict unidirectional data flow
  - Boilerplate-heavy but predictable
  - Excellent devtools, time-travel debugging
  - Learning curve steep
  - Best for large teams, complex state

- **Akita**:
  - Less boilerplate than NgRx
  - More flexible, OOP-oriented
  - Built-in entity management
  - Good TypeScript support
  - Simpler learning curve

- **BehaviorSubject** (DIY approach):
  - Minimal setup
  - Maximum flexibility
  - No enforced patterns
  - Can become unstructured
  - Good for small/medium apps

**Decision matrix**:
- Team size → Large: NgRx, Small: Akita/BehaviorSubject
- Complexity → High: NgRx, Medium: Akita, Low: BehaviorSubject
- TypeScript usage → Heavy: Akita, Any: NgRx/BehaviorSubject
- Learning time → Limited: BehaviorSubject, Available: Akita/NgRx

### 61. **Explain Redux pattern in Angular**
1. **Store**: Single source of truth (immutable state)
2. **Actions**: Describe events (type + payload)
3. **Reducers**: Pure functions updating state
4. **Selectors**: Memoized functions reading state
5. **Effects**: Handle side effects (API calls)
6. **Flow**:
   ```
   Component → Action → Reducer → Store → Selector → Component
               ↓
             Effect → API → Action
   ```
7. **NgRx implementation**:
   ```typescript
   // Action
   export const loadUsers = createAction('[Users] Load Users');
   export const loadUsersSuccess = createAction(
     '[Users] Load Users Success',
     props<{ users: User[] }>()
   );
   
   // Reducer
   const usersReducer = createReducer(
     initialState,
     on(loadUsersSuccess, (state, { users }) => ({ ...state, users }))
   );
   
   // Effect
   loadUsers$ = createEffect(() => this.actions$.pipe(
     ofType(loadUsers),
     mergeMap(() => this.userService.getUsers().pipe(
       map(users => loadUsersSuccess({ users })),
       catchError(() => of(loadUsersFailure()))
     ))
   ));
   ```

### 62. **What are Actions, Reducers, Effects, Selectors?**
- **Actions**:
  - Plain objects describing events
  - Have type and optional payload
  - Dispatched by components/services
  - Example: `{ type: '[Users] Load Users', page: 1 }`

- **Reducers**:
  - Pure functions taking (state, action) → new state
  - No side effects
  - Immutable updates
  - Example: Adding user to state array

- **Effects**:
  - Handle side effects (API calls, localStorage)
  - Listen for actions, perform async operations
  - Dispatch new actions when done
  - Example: Call API on loadUsers action

- **Selectors**:
  - Memoized functions reading from state
  - Compute derived data
  - Efficient recomputation
  - Example: `selectFilteredUsers`

### 63. **How do you handle side effects?**
1. **NgRx Effects** (recommended for NgRx):
   ```typescript
   @Injectable()
   export class UserEffects {
     loadUsers$ = createEffect(() => this.actions$.pipe(
       ofType(loadUsers),
       mergeMap(() => this.userService.getUsers().pipe(
         map(users => loadUsersSuccess({ users })),
         catchError(error => of(loadUsersFailure({ error })))
       ))
     ));
   }
   ```
2. **Service-based effects** (for non-NgRx):
   ```typescript
   export class UserService {
     private loadUsersAction = new Subject<void>();
     
     users$ = this.loadUsersAction.pipe(
       switchMap(() => this.http.get<User[]>('/api/users')),
       shareReplay(1)
     );
   }
   ```
3. **Component effects** (simple cases):
   ```typescript
   ngOnInit() {
     this.userService.getUsers().subscribe(users => {
       this.store.dispatch(loadUsersSuccess({ users }));
     });
   }
   ```

### 64. **How do you structure NgRx for large apps?**
1. **Feature-based organization**:
   ```
   app/
   ├── store/
   │   ├── index.ts
   │   └── features/
   │       ├── users/
   │       │   ├── actions/
   │       │   ├── reducers/
   │       │   ├── effects/
   │       │   ├── selectors/
   │       │   └── models/
   │       └── products/
   ```
2. **Shared state**: Common state in root store
3. **Entity adapters**: For normalized entity state
4. **Meta-reducers**: Global state transformations
5. **Selectors composition**: Reuse and compose selectors
6. **Lazy-loaded features**: Each feature manages its own state
7. **State persistence**: Sync with localStorage/IndexedDB
8. **DevTools integration**: For debugging

### 65. **How do you avoid overusing state management?**
1. **Component state first**: Start with local component state
2. **Lift state up**: Move to parent before going global
3. **Service-based sharing**: Use services with BehaviorSubject
4. **Context-based decisions**:
   - Local state: Component-specific, temporary
   - Shared state: Used by 2+ components
   - Global state: App-wide, persisted
5. **Question checklist**:
   - Is this state used by multiple unrelated components?
   - Does this state need to be persisted?
   - Is the state complex with business logic?
   - Do we need time-travel debugging?
6. **Refactor incrementally**: Start simple, evolve as needed

### 66. **How do you debug NgRx issues?**
1. **Redux DevTools**:
   - Time-travel debugging
   - Action log inspection
   - State diff viewing
2. **Console logging**:
   ```typescript
   const debug = (action: any) => {
     console.log('Action:', action);
     return action;
   };
   
   this.actions$.pipe(
     tap(debug),
     ofType(someAction)
   )
   ```
3. **Meta-reducers for logging**:
   ```typescript
   export function debugMetaReducer(reducer: ActionReducer<any>): ActionReducer<any> {
     return (state, action) => {
       console.log('State before:', state);
       console.log('Action:', action);
       const nextState = reducer(state, action);
       console.log('State after:', nextState);
       return nextState;
     };
   }
   ```
4. **Unit testing**: Test actions, reducers, effects, selectors
5. **Error handling in effects**: Proper catchError usage

---

## **Build, Deployment & Tooling**

### 67. **How does Angular CLI build process work?**
1. **Configuration loading**: Reads `angular.json`
2. **Webpack configuration**: Sets up loaders, plugins
3. **TypeScript compilation**: Transpiles .ts to .js
4. **Template compilation**: AOT or JIT compilation
5. **Bundle generation**: Creates main, polyfills, vendor chunks
6. **Asset processing**: Copies assets, processes styles
7. **Optimization**: Minification, tree-shaking, code splitting
8. **Output generation**: Creates `dist/` folder
9. **Source maps**: For debugging production builds

### 68. **What is AOT vs JIT?**
- **AOT (Ahead-of-Time)**:
  - Compiles during build phase
  - Smaller bundle (no compiler in bundle)
  - Faster rendering (no compilation in browser)
  - Template errors caught early
  - Better security (no eval)
  - Required for SSR
  - Production builds use AOT

- **JIT (Just-in-Time)**:
  - Compiles in browser at runtime
  - Larger bundle (includes compiler)
  - Slower initial load
  - Template errors at runtime
  - Easier debugging (no source map issues)
  - Default for development

**Production checklist**: Always use AOT for production

### 69. **What is Ivy and what problems does it solve?**
- **Next-gen Angular rendering engine** (Angular 9+)
- **Problems solved**:
  1. **Smaller bundle sizes**: Better tree-shaking
  2. **Faster compilation**: Incremental compilation
  3. **Better debugging**: Improved stack traces
  4. **Improved type checking**: Template type checking
  5. **Metadata removal**: No more .metadata.json files
  6. **Local compilation**: Components compiled independently
  7. **Backwards compatibility**: Works with existing code
- **Key features**:
  - Locality: Compile components independently
  - Tree-shakable: Remove unused code effectively
  - Type checking: Catch template errors at build time

### 70. **How do you reduce bundle size?**
1. **Lazy loading**: Split code by feature modules
2. **AOT compilation**: Remove compiler from bundle
3. **Tree-shaking**: Remove unused code
4. **Bundle analysis**: Use `source-map-explorer`
5. **Dependency optimization**:
   - Import only needed parts of libraries
   - Avoid large dependencies
   - Check alternative lighter libraries
6. **Compression**: Gzip/Brotli compression
7. **CDN for libraries**: Use CDN for common libraries
8. **Server-side rendering**: Reduce initial load time
9. **Differential loading**: Modern vs legacy bundles

### 71. **What is tree-shaking?**
- **Process**: Removing unused code from final bundle
- **How it works**:
  1. Static analysis of imports/exports
  2. Marks unused code as "dead"
  3. Removes dead code during bundling
- **Requirements**:
  - ES2015 modules (import/export)
  - No side effects in module initialization
  - Proper library design (side-effect-free)
- **Angular optimization**: Ivy compiler improves tree-shaking
- **Verification**: Use bundle analyzer to check unused code

### 72. **How do you handle environment-based builds?**
1. **Environment files**: `environment.ts`, `environment.prod.ts`
   ```typescript
   // environment.ts
   export const environment = {
     production: false,
     apiUrl: 'http://localhost:3000/api'
   };
   ```
2. **Build configurations** in `angular.json`:
   ```json
   "configurations": {
     "production": {
       "fileReplacements": [{
         "replace": "src/environments/environment.ts",
         "with": "src/environments/environment.prod.ts"
       }]
     },
     "staging": {
       "fileReplacements": [{
         "replace": "src/environments/environment.ts",
         "with": "src/environments/environment.staging.ts"
       }]
     }
   }
   ```
3. **Runtime configuration**: Load config from JSON file
4. **Docker/K8s**: Use environment variables

### 73. **SSR vs CSR vs SSG (Angular Universal)**
- **CSR (Client-Side Rendering)**:
  - Renders in browser
  - Fast navigation after load
  - Poor SEO, slow initial load
  - Simple deployment

- **SSR (Server-Side Rendering)**:
  - Renders on server, sends HTML
  - Better SEO, faster initial load
  - Higher server load
  - Complex deployment
  - Angular Universal

- **SSG (Static Site Generation)**:
  - Pre-renders at build time
  - Best performance, excellent SEO
  - No server needed
  - Not dynamic content
  - Angular Scully

**Choosing strategy**:
- Marketing site: SSG
- Dashboard app: CSR
- E-commerce: SSR/SSG hybrid

### 74. **How do you improve first load time?**
1. **Lazy loading**: Split main bundle
2. **Preloading strategy**: Load likely modules
3. **Server-side rendering**: Send rendered HTML
4. **Bundle optimization**:
   - Remove unused code
   - Compress bundles
   - Use modern formats (ES2015+)
5. **Asset optimization**:
   - Compress images
   - Use WebP format
   - Font subsetting
6. **CDN usage**: For static assets
7. **HTTP/2**: For parallel loading
8. **Service workers**: Cache assets
9. **Critical CSS**: Inline above-fold CSS

### 75. **How do you handle version upgrades?**
1. **Preparation**:
   - Read migration guide
   - Check breaking changes
   - Update dependencies gradually
2. **Process**:
   ```bash
   ng update @angular/cli @angular/core
   ```
3. **Testing strategy**:
   - Update in separate branch
   - Run all tests
   - Manual regression testing
   - Performance testing
4. **Rollback plan**: Have working version to revert to
5. **Staggered deployment**: Update percentage of users
6. **Monitoring**: Watch error rates, performance metrics
7. **Documentation**: Update team on changes

---


# Testing (Senior-Level Expectation)

## 76. **Unit testing strategy for Angular apps**
1. **Test pyramid approach**:
   - 70% unit tests (components, services, pipes)
   - 20% integration tests (component interactions)
   - 10% E2E tests (user workflows)

2. **Testing priorities**:
   - Business logic first
   - Critical user journeys
   - Edge cases and error handling
   - Performance-sensitive code

3. **Toolchain**:
   - **Karma + Jasmine**: Default Angular setup
   - **Jest**: Faster, snapshot testing
   - **Testing Library**: User-centric testing
   - **Cypress**: Component testing

4. **Code coverage targets**:
   - 80%+ for business logic
   - 60-70% for UI components
   - Focus on meaningful coverage, not just numbers

## 77. **How do you test components with dependencies?**
1. **TestBed configuration**:
   ```typescript
   beforeEach(async () => {
     await TestBed.configureTestingModule({
       declarations: [UserComponent],
       providers: [
         { provide: UserService, useClass: MockUserService },
         { provide: ActivatedRoute, useValue: { snapshot: { params: { id: '123' } } } }
       ],
       imports: [ReactiveFormsModule, RouterTestingModule]
     }).compileComponents();
   });
   ```

2. **Mocking strategies**:
   - **Class mocks**: Create mock implementations
   - **Jasmine spies**: `spyOn(service, 'method').and.returnValue(of(data))`
   - **Provider overrides**: Replace real services with test doubles

3. **Testing with inputs/outputs**:
   ```typescript
   it('should emit on button click', () => {
     const emitSpy = spyOn(component.userSelected, 'emit');
     component.selectUser(testUser);
     expect(emitSpy).toHaveBeenCalledWith(testUser);
   });
   ```

## 78. **Jasmine vs Jest – which do you prefer?**
- **Jasmine** (Angular default):
  - Built-in with Angular CLI
  - Familiar to Angular developers
  - Good integration with Karma
  - Slower test execution
  - Limited snapshot testing

- **Jest** (Modern alternative):
  - Much faster test execution
  - Built-in coverage, mocking, snapshot testing
  - Better watch mode
  - Can be configured with Angular
  - Requires additional setup

**Recommendation**:
- **New projects**: Consider Jest for better performance
- **Existing projects**: Stick with Jasmine unless performance is critical
- **Large codebases**: Jest parallel execution provides significant speedup

## 79. **What is TestBed?**
- **Purpose**: Configure and create Angular testing module
- **Key methods**:
  - `configureTestingModule()`: Set up module for testing
  - `createComponent()`: Create component instance
  - `inject()`: Get service instance from injector
  - `overrideModule/Provider/Component()`: Modify module configuration

- **Example setup**:
  ```typescript
  describe('Component Test', () => {
    let fixture: ComponentFixture<TestComponent>;
    let component: TestComponent;
    
    beforeEach(async () => {
      await TestBed.configureTestingModule({
        declarations: [TestComponent],
        imports: [FormsModule],
        providers: [TestService]
      }).compileComponents();
      
      fixture = TestBed.createComponent(TestComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
  ```

## 80. **How do you mock services and observables?**
1. **Service mocking**:
   ```typescript
   class MockUserService {
     getUsers = jasmine.createSpy('getUsers').and.returnValue(
       of([{ id: 1, name: 'Test User' }])
     );
   }
   ```

2. **Observable mocking**:
   ```typescript
   // Success case
   spyOn(service, 'getData').and.returnValue(of(testData));
   
   // Error case
   spyOn(service, 'getData').and.returnValue(throwError(() => new Error('Test error')));
   
   // Delayed response
   spyOn(service, 'getData').and.returnValue(
     of(testData).pipe(delay(100))
   );
   ```

3. **HTTP testing**:
   ```typescript
   it('should fetch users', () => {
     const testUsers = [{ id: 1, name: 'Test' }];
     httpClientSpy.get.and.returnValue(of(testUsers));
     
     service.getUsers().subscribe(users => {
       expect(users).toEqual(testUsers);
     });
   });
   ```

## 81. **How do you test RxJS streams?**
1. **Marble testing** (rxjs/testing):
   ```typescript
   import { TestScheduler } from 'rxjs/testing';
   
   describe('RxJS Stream', () => {
     let testScheduler: TestScheduler;
     
     beforeEach(() => {
       testScheduler = new TestScheduler((actual, expected) => {
         expect(actual).toEqual(expected);
       });
     });
     
     it('should map values correctly', () => {
       testScheduler.run(({ cold, expectObservable }) => {
         const source$ = cold('--a--b--c|', { a: 1, b: 2, c: 3 });
         const expected =    '--x--y--z|', { x: 2, y: 4, z: 6 };
         
         const result$ = source$.pipe(map(x => x * 2));
         expectObservable(result$).toBe(expected);
       });
     });
   });
   ```

2. **Subscription testing**:
   ```typescript
   it('should emit values', (done) => {
     const emittedValues: number[] = [];
     
     source$.subscribe({
       next: value => emittedValues.push(value),
       complete: () => {
         expect(emittedValues).toEqual([1, 2, 3]);
         done();
       }
     });
   });
   ```

## 82. **E2E testing approach (Cypress/Playwright)**
1. **Cypress for Angular**:
   ```typescript
   // cypress/integration/user.spec.ts
   describe('User Management', () => {
     it('should create a new user', () => {
       cy.visit('/users');
       cy.get('[data-testid="add-user"]').click();
       cy.get('[data-testid="user-name"]').type('John Doe');
       cy.get('[data-testid="save-user"]').click();
       cy.contains('User created successfully').should('be.visible');
     });
   });
   ```

2. **Playwright advantages**:
   - Multi-browser support
   - Faster execution
   - Better API for complex scenarios
   - Built-in video recording

3. **Best practices**:
   - Use data-testid attributes for selectors
   - Create reusable test commands
   - Mock API responses for consistency
   - Run tests in CI/CD pipeline
   - Test critical user journeys only

## 83. **How do you maintain test stability?**
1. **Avoid flaky tests**:
   - Use stable selectors (data-testid, not CSS classes)
   - Add proper waiting mechanisms
   - Mock external dependencies
   - Clear state between tests

2. **Test isolation**:
   ```typescript
   beforeEach(() => {
     localStorage.clear();
     sessionStorage.clear();
     TestBed.resetTestingModule();
   });
   ```

3. **Retry mechanisms**:
   ```typescript
   // Cypress example
   it('should load page', { retries: 2 }, () => {
     cy.visit('/');
     cy.get('.loaded-element').should('be.visible');
   });
   ```

4. **Parallel execution**: Run tests in parallel when possible
5. **Regular maintenance**: Remove obsolete tests, update selectors

## 84. **Code coverage vs meaningful tests**
**Code coverage pitfalls**:
- High coverage ≠ good tests
- Can encourage testing implementation details
- May miss important edge cases

**Meaningful test characteristics**:
1. **Test behavior, not implementation**:
   ```typescript
   // Bad: Tests implementation detail
   it('should call service method', () => {
     expect(serviceSpy.getUsers).toHaveBeenCalled();
   });
   
   // Good: Tests user-visible behavior
   it('should display users list', () => {
     expect(screen.getByText('John Doe')).toBeVisible();
   });
   ```

2. **Focus on business requirements**
3. **Test edge cases and error scenarios**
4. **Test integration between components**
5. **Maintain test readability and simplicity**

**Recommended approach**:
- Aim for 70-80% coverage as guideline
- Focus on critical paths and business logic
- Use mutation testing to validate test effectiveness
- Regularly review tests for value

---

# Real-World Scenario Questions (VERY COMMON)

## 85. **App is slow – how do you debug?**
1. **Initial assessment**:
   - Use Chrome DevTools Performance tab
   - Record performance profile
   - Identify long tasks (>50ms)

2. **Angular-specific checks**:
   ```typescript
   // Enable Angular profiler
   ng.profiler.timeChangeDetection({ record: true });
   ```
   - Check change detection cycles
   - Identify components causing re-renders

3. **Common issues and solutions**:
   - **Too many change detection cycles**: Implement OnPush strategy
   - **Heavy computation in templates**: Move to pure pipes or computed properties
   - **Memory leaks**: Check subscription cleanup
   - **Large bundle size**: Implement lazy loading, code splitting
   - **Inefficient *ngFor**: Add trackBy function

4. **Tools**:
   - Angular DevTools (Component tree, profiler)
   - Webpack Bundle Analyzer
   - Lighthouse for performance metrics

## 86. **Memory leak in Angular – how do you detect?**
1. **Detection methods**:
   ```typescript
   // Monitor subscription count
   private subscriptions = new Subscription();
   
   ngOnInit() {
     this.subscriptions.add(observable$.subscribe());
   }
   
   ngOnDestroy() {
     this.subscriptions.unsubscribe();
   }
   ```

2. **Chrome DevTools Memory tab**:
   - Take heap snapshot before/after navigation
   - Compare snapshots for retained memory
   - Look for detached DOM nodes

3. **Common leak sources**:
   - **Unsubscribed observables**: Use async pipe or takeUntil
   - **Event listeners**: Remove in ngOnDestroy
   - **Timers/intervals**: Clear in ngOnDestroy
   - **Third-party libraries**: Check cleanup requirements

4. **Automated detection**:
   ```typescript
   // Test helper
   describe('Memory Leak Test', () => {
     it('should not leak memory', () => {
       const before = performance.memory.usedJSHeapSize;
       // Create/destroy component multiple times
       const after = performance.memory.usedJSHeapSize;
       expect(after - before).toBeLessThan(threshold);
     });
   });
   ```

## 87. **Large form with 200+ controls – how do you design?**
1. **Architecture**:
   ```typescript
   // Break into sections
   this.form = this.fb.group({
     personalInfo: this.fb.group({ /* 20 controls */ }),
     addressInfo: this.fb.group({ /* 30 controls */ }),
     employmentInfo: this.fb.group({ /* 50 controls */ }),
     // ... more sections
   });
   ```

2. **Performance optimizations**:
   - **Lazy validation**: Validate on blur instead of on change
   - **Virtual scrolling**: Only render visible sections
   - **OnPush strategy**: For all form components
   - **Debounced saves**: Auto-save with 1-2 second delay
   - **Section-based loading**: Load data as sections become visible

3. **UX considerations**:
   - **Wizard/stepper pattern**: Break into multiple pages
   - **Progress indicators**: Show completion status
   - **Save draft**: Auto-save with recovery option
   - **Validation summary**: Show all errors at once

4. **Technical implementation**:
   - Use FormArray for repeating sections
   - Implement custom ControlValueAccessor for complex controls
   - Store form state in service for persistence
   - Use web workers for complex validation logic

## 88. **Handling concurrent API calls efficiently**
1. **RxJS operators strategy**:
   ```typescript
   // Sequential execution (one after another)
   data$ = ids$.pipe(
     concatMap(id => this.api.getData(id))
   );
   
   // Parallel execution with limit
   data$ = ids$.pipe(
     mergeMap(id => this.api.getData(id), 3) // 3 concurrent calls
   );
   
   // Cancel previous on new request (search)
   searchResults$ = searchTerm$.pipe(
     debounceTime(300),
     distinctUntilChanged(),
     switchMap(term => this.api.search(term))
   );
   ```

2. **Error handling for concurrent calls**:
   ```typescript
   results$ = forkJoin([
     api.call1().pipe(catchError(() => of(null))),
     api.call2().pipe(catchError(() => of(null))),
     api.call3().pipe(catchError(() => of(null)))
   ]);
   ```

3. **Progress tracking**:
   ```typescript
   const calls = urls.map(url => this.api.get(url));
   
   forkJoin(calls).pipe(
     scan((completed, result) => completed + 1, 0),
     map(completed => (completed / calls.length) * 100)
   ).subscribe(progress => {
     this.progress = progress;
   });
   ```

## 89. **How do you share data between unrelated components?**
1. **Service with BehaviorSubject**:
   ```typescript
   @Injectable({ providedIn: 'root' })
   export class DataSharingService {
     private dataSubject = new BehaviorSubject<any>(null);
     data$ = this.dataSubject.asObservable();
     
     updateData(data: any) {
       this.dataSubject.next(data);
     }
   }
   ```

2. **State management** (NgRx/Akita):
   - Use for complex, app-wide state
   - Provides predictable state updates
   - Enables time-travel debugging

3. **Observable data services**:
   ```typescript
   export class UserService {
     private usersSubject = new BehaviorSubject<User[]>([]);
     users$ = this.usersSubject.asObservable();
     
     loadUsers() {
       this.http.get<User[]>('/api/users').subscribe(users => {
         this.usersSubject.next(users);
       });
     }
   }
   ```

4. **Context-based approaches**:
   - **Parent component**: Lift state up to common ancestor
   - **Route parameters**: Pass data via URL
   - **LocalStorage**: For persistence across routes

## 90. **How do you handle offline scenarios?**
1. **Detection**:
   ```typescript
   @Injectable({ providedIn: 'root' })
   export class ConnectivityService {
     online$ = merge(
       fromEvent(window, 'online').pipe(map(() => true)),
       fromEvent(window, 'offline').pipe(map(() => false)),
       of(navigator.onLine)
     ).pipe(distinctUntilChanged());
   }
   ```

2. **Caching strategy**:
   ```typescript
   // Service with caching
   getData(): Observable<Data> {
     if (navigator.onLine) {
       return this.http.get<Data>('/api/data').pipe(
         tap(data => this.cacheService.set('data', data))
       );
     } else {
       return of(this.cacheService.get('data'));
     }
   }
   ```

3. **Queue for offline actions**:
   ```typescript
   export class OfflineQueueService {
     private queue: Action[] = [];
     
     addToQueue(action: Action) {
       this.queue.push(action);
       localStorage.setItem('offlineQueue', JSON.stringify(this.queue));
     }
     
     processQueue() {
       if (navigator.onLine) {
         this.queue.forEach(action => this.executeAction(action));
         this.queue = [];
         localStorage.removeItem('offlineQueue');
       }
     }
   }
   ```

4. **UI feedback**:
   - Show offline indicator
   - Disable online-only features
   - Show queued actions count
   - Sync status indicator

## 91. **How do you design reusable components?**
1. **API design principles**:
   ```typescript
   @Component({
     selector: 'app-data-table',
     template: `...`
   })
   export class DataTableComponent<T> {
     @Input() data: T[];
     @Input() columns: ColumnDefinition[];
     @Output() rowClick = new EventEmitter<T>();
     
     // Optional inputs with defaults
     @Input() pageSize = 10;
     @Input() showActions = true;
   }
   ```

2. **Content projection**:
   ```html
   <!-- Component template -->
   <div class="card">
     <div class="card-header">
       <ng-content select="[card-header]"></ng-content>
     </div>
     <div class="card-body">
       <ng-content></ng-content>
     </div>
   </div>
   
   <!-- Usage -->
   <app-card>
     <div card-header>Custom Header</div>
     <p>Card content</p>
   </app-card>
   ```

3. **Configuration over inheritance**:
   - Use @Input() for customization
   - Provide sensible defaults
   - Support dependency injection for services

4. **Documentation and examples**:
   - Storybook for component catalog
   - Usage examples in README
   - Prop tables with types and defaults

## 92. **How do you handle breaking API changes?**
1. **Versioning strategy**:
   ```typescript
   // API service with version support
   export class ApiService {
     private readonly baseUrl = '/api/v2'; // Configurable
     
     // Legacy support
     getLegacyData() {
       return this.http.get('/api/v1/data');
     }
   }
   ```

2. **Adapter pattern**:
   ```typescript
   export interface NewUserModel {
     firstName: string;
     lastName: string;
     email: string;
   }
   
   export class UserAdapter {
     static fromLegacy(legacyUser: any): NewUserModel {
       return {
         firstName: legacyUser.first_name,
         lastName: legacyUser.last_name,
         email: legacyUser.email_address
       };
     }
   }
   ```

3. **Migration approach**:
   - **Dual support**: Support old and new APIs temporarily
   - **Feature flags**: Gradually roll out new API usage
   - **Monitoring**: Track errors from both versions
   - **Deprecation warnings**: Log when using old API

4. **Communication**:
   - Coordinate with backend team
   - Schedule breaking changes
   - Provide migration guides
   - Support backward compatibility period

## 93. **Migration strategy from AngularJS / older Angular**
1. **Assessment phase**:
   - Inventory of components, services, directives
   - Identify third-party dependencies
   - Evaluate complexity and dependencies

2. **Migration approaches**:
   - **Big bang**: Complete rewrite (riskier)
   - **Strangler pattern**: Gradually replace parts
   - **Micro-frontends**: Run both apps side by side

3. **AngularJS to Angular migration**:
   ```typescript
   // Using ngUpgrade for hybrid app
   import { downgradeComponent } from '@angular/upgrade/static';
   
   // Angular component
   @Component({ selector: 'app-new-component', template: '...' })
   export class NewComponent {}
   
   // Downgrade for AngularJS
   angular.module('hybridApp')
     .directive('appNewComponent', 
       downgradeComponent({ component: NewComponent }));
   ```

4. **Version upgrade strategy**:
   - Update one major version at a time
   - Use `ng update` with `--force` if needed
   - Test thoroughly after each upgrade
   - Keep dependencies updated

## 94. **How do you mentor junior developers?**
1. **Structured onboarding**:
   - Codebase tour and architecture overview
   - Pair programming sessions
   - Code review guidelines
   - Testing standards

2. **Progressive learning path**:
   ```typescript
   // Week 1-2: Basics
   - Components, templates, data binding
   - Services and dependency injection
   
   // Week 3-4: Intermediate
   - RxJS and observables
   - Forms (template-driven and reactive)
   - Routing and navigation
   
   // Month 2: Advanced
   - State management
   - Performance optimization
   - Testing strategies
   ```

3. **Code review focus areas**:
   - Architecture and design patterns
   - Performance considerations
   - Error handling and edge cases
   - Testing coverage and quality

4. **Growth opportunities**:
   - Assign ownership of small features
   - Encourage conference/meetup attendance
   - Support open source contributions
   - Regular 1:1 feedback sessions

## 95. **How do you enforce coding standards?**
1. **Automated tools**:
   ```json
   // .eslintrc.json
   {
     "extends": ["eslint:recommended", "plugin:@angular-eslint/recommended"],
     "rules": {
       "@angular-eslint/component-selector": ["error", 
         { "type": "element", "prefix": "app", "style": "kebab-case" }
       ],
       "@angular-eslint/directive-selector": ["error", 
         { "type": "attribute", "prefix": "app", "style": "camelCase" }
       ]
     }
   }
   ```

2. **Pre-commit hooks** (Husky + lint-staged):
   ```json
   // package.json
   {
     "husky": {
       "hooks": {
         "pre-commit": "lint-staged",
         "pre-push": "npm run test:ci"
       }
     },
     "lint-staged": {
       "*.ts": ["eslint --fix", "prettier --write"]
     }
   }
   ```

3. **CI/CD pipeline checks**:
   - Linting errors fail the build
   - Test coverage minimum thresholds
   - TypeScript compilation checks
   - Bundle size limits

4. **Code review checklist**:
   - Follows project architecture
   - Proper error handling
   - Adequate testing
   - Performance considerations
   - Security best practices

---

# Leadership / Architect-Level

## 96. **How do you review Angular code?**
1. **Architecture review**:
   - Component responsibility and size
   - Module organization and boundaries
   - State management approach
   - Dependency injection usage

2. **Performance considerations**:
   ```typescript
   // Checklist
   - [ ] OnPush change detection where applicable
   - [ ] Proper subscription management
   - [ ] trackBy used with *ngFor
   - [ ] Memoization for expensive computations
   - [ ] Lazy loading implemented
   ```

3. **Testing review**:
   - Unit tests for business logic
   - Integration tests for component interactions
   - Meaningful test cases (not just coverage)
   - Test readability and maintainability

4. **Security review**:
   - Input sanitization
   - XSS prevention
   - Authentication/authorization checks
   - Sensitive data handling

## 97. **How do you define Angular best practices for a team?**
1. **Documentation**:
   - Style guide (naming conventions, file structure)
   - Architecture decisions record (ADR)
   - Common patterns and anti-patterns
   - Tooling setup guide

2. **Standardized tooling**:
   - Consistent ESLint/Prettier configuration
   - Shared IDE settings (.vscode/settings.json)
   - Common testing setup
   - Build and deployment scripts

3. **Regular knowledge sharing**:
   - Weekly tech talks
   - Code review sessions
   - Architecture review meetings
   - Learning resource sharing

4. **Enforcement mechanisms**:
   - Pre-commit hooks
   - CI/CD pipeline checks
   - Regular architecture reviews
   - Performance audits

## 98. **How do you handle technical debt?**
1. **Identification and tracking**:
   ```typescript
   // Use TODO comments with context
   // TODO: Refactor this when migrating to RxJS 7
   // Context: RxJS 7 has better typing for this pattern
   // Priority: Medium
   // Estimated effort: 2 days
   ```

2. **Prioritization framework**:
   - **Impact**: User experience, performance, maintainability
   - **Effort**: Time and complexity to fix
   - **Risk**: Potential for regression
   - **Dependencies**: Blocking other work

3. **Dedicated time**:
   - "Fix-it Fridays" for small debt
   - Quarterly tech debt sprints
   - Allocate 20% capacity for maintenance
   - Include in sprint planning

4. **Prevention strategies**:
   - Code review standards
   - Automated quality gates
   - Regular refactoring
   - Architecture reviews

## 99. **How do you choose libraries wisely?**
1. **Evaluation criteria**:
   ```typescript
   const libraryEvaluation = {
     // Technical
     maintenance: 'Active maintenance (>6 months)',
     community: 'GitHub stars, contributors, issues',
     documentation: 'Comprehensive, up-to-date',
     compatibility: 'Angular version support',
     
     // Project fit
     size: 'Bundle impact',
     learningCurve: 'Team ramp-up time',
     alternatives: 'Other options considered',
     necessity: 'Solve real problem vs custom solution'
   };
   ```

2. **Due diligence process**:
   - Proof of concept implementation
   - Security audit (check for vulnerabilities)
   - License compliance check
   - Performance impact assessment
   - Team skill assessment

3. **Adoption strategy**:
   - Start with limited scope
   - Monitor for issues
   - Have rollback plan
   - Document usage patterns

## 100. **How do you future-proof Angular applications?**
1. **Architecture decisions**:
   - Modular design with clear boundaries
   - Dependency injection for testability
   - Reactive patterns with RxJS
   - API abstraction layers

2. **Technology choices**:
   - Stick with Angular-supported libraries
   - Avoid experimental/unstable APIs
   - Use TypeScript strict mode
   - Implement comprehensive testing

3. **Maintainability practices**:
   - Regular dependency updates
   - Continuous refactoring
   - Documentation of complex logic
   - Knowledge sharing across team

4. **Adaptability strategies**:
   - Feature flags for experimentation
   - A/B testing infrastructure
   - Monitoring and observability
   - Performance benchmarking

5. **Team development**:
   - Cross-training on critical parts
   - Code ownership rotation
   - Regular architecture reviews
   - Investment in developer tooling

---

**Key Takeaways for Senior Angular Interviews:**

1. **Depth over breadth**: Understand "why" behind every decision
2. **Real-world experience**: Focus on practical problem-solving
3. **Architecture thinking**: Consider scalability, maintainability, team dynamics
4. **Communication skills**: Explain complex concepts clearly
5. **Continuous learning**: Show awareness of Angular ecosystem evolution
6. **Leadership mindset**: Consider team, process, and business impact

Remember: Senior roles evaluate not just technical skills but also design thinking, decision-making process, and ability to mentor others. Always provide context for your answers and demonstrate learning from past experiences.