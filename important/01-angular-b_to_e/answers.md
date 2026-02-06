# Angular Interview Questions & Expected Answers

## **Beginner / Junior Level**

### 1. **What is Angular?**
- Angular is a platform and framework for building single-page client applications using HTML and TypeScript
- It's developed and maintained by Google
- Uses component-based architecture for building scalable web applications
- Provides tools and libraries for routing, forms management, client-server communication, and more

### 2. **Angular vs AngularJS – differences.**
- **Architecture**: AngularJS uses MVC, Angular uses component-based architecture
- **Language**: AngularJS uses JavaScript, Angular uses TypeScript
- **Performance**: Angular has better performance with Ivy renderer and ahead-of-time compilation
- **Mobile Support**: Angular has better mobile support
- **Dependency Injection**: Both have DI but Angular's implementation is more sophisticated
- **Learning Curve**: Angular has steeper learning curve due to TypeScript and RxJS

### 3. **What are the main features of Angular?**
- Two-way data binding
- Dependency injection
- Component-based architecture
- Directives and templates
- TypeScript support
- RxJS for reactive programming
- Powerful CLI for project management
- Cross-platform development (web, mobile, desktop)

### 4. **What is TypeScript and why is it used in Angular?**
- TypeScript is a superset of JavaScript that adds static typing
- **Benefits in Angular**:
  - Early error detection during development
  - Better tooling and autocomplete support
  - Enhanced code readability and maintainability
  - Support for modern JavaScript features
  - Interfaces and type safety for Angular services and components

### 5. **What is a component?**
- Basic building block of Angular applications
- Consists of:
  - TypeScript class with properties and methods
  - HTML template for rendering
  - CSS/SCSS for styling
  - Decorated with `@Component()` metadata
- Encapsulates view logic and data
- Can communicate with other components via inputs and outputs

### 6. **What is a module (`NgModule`)?**
- Container for organizing related components, directives, pipes, and services
- Decorated with `@NgModule()` metadata
- **Key properties**:
  - `declarations`: Components, directives, pipes belonging to this module
  - `imports`: Other modules whose exported classes are needed
  - `providers`: Services available for dependency injection
  - `exports`: Classes to be used by other modules
- Every Angular app has at least one root module (AppModule)

### 7. **What is a template?**
- HTML view that defines what renders on the page
- Enhanced with Angular template syntax
- Can include:
  - Interpolation (`{{data}}`)
  - Property binding (`[property]="value"`)
  - Event binding (`(event)="handler()"`)
  - Structural directives (`*ngIf`, `*ngFor`)
  - Attribute directives (`ngClass`, `ngStyle`)

### 8. **What is data binding?**
- Communication mechanism between component class and template
- Keeps component data and view in sync automatically
- Four types of binding in Angular

### 9. **Types of data binding in Angular.**
1. **Interpolation**: `{{value}}` - Displays component property in view
2. **Property Binding**: `[property]="value"` - Binds property to DOM element
3. **Event Binding**: `(event)="handler()"` - Listens to DOM events
4. **Two-way Binding**: `[(ngModel)]="property"` - Combines property and event binding

### 10. **What is interpolation?**
- Technique to embed expressions in marked-up text
- Uses double curly braces: `{{expression}}`
- Evaluates expression and converts result to string
- Example: `<h1>Welcome {{userName}}!</h1>`

### 11. **What is property binding?**
- Sets element property to component property value
- Syntax: `[property]="expression"`
- Example: `<img [src]="imageUrl">`
- One-way binding from component to view

### 12. **What is event binding?**
- Listens for DOM events and executes component methods
- Syntax: `(event)="expression"`
- Example: `<button (click)="onSave()">Save</button>`
- One-way binding from view to component

### 13. **What is two-way binding?**
- Combination of property and event binding
- Keeps component property and view element value in sync
- Requires `FormsModule` for `[(ngModel)]`
- Example: `<input [(ngModel)]="username">`
- Changes in input update component property and vice versa

### 14. **What is `ngModel`?**
- Directive that enables two-way data binding for form elements
- Part of `FormsModule`
- Syntax: `[(ngModel)]="property"`
- Updates both view and component property simultaneously
- Can be used with validation and error handling

### 15. **What is a directive?**
- Classes that add behavior to DOM elements
- Three types of directives:
  1. **Components** - Directives with templates
  2. **Structural** - Change DOM layout by adding/removing elements
  3. **Attribute** - Change appearance/behavior of elements

### 16. **Types of directives.**
1. **Component Directives**: Have templates, define views
2. **Structural Directives**: Alter layout (`*ngIf`, `*ngFor`, `*ngSwitch`)
3. **Attribute Directives**: Change element behavior/appearance (`ngClass`, `ngStyle`, `ngModel`)

### 17. **What is `*ngIf`?**
- Structural directive that conditionally includes/excludes DOM elements
- Removes element from DOM when condition is false
- Syntax: `<div *ngIf="condition">Content</div>`
- Can be used with `else` and `then` clauses

### 18. **What is `*ngFor`?**
- Structural directive for repeating elements
- Iterates over collections/arrays
- Syntax: `<li *ngFor="let item of items; index as i">{{item.name}}</li>`
- Can track items by index or unique identifier using `trackBy`

### 19. **What is Angular CLI?**
- Command Line Interface for Angular development
- Automates common development tasks
- **Key commands**:
  - `ng new`: Create new project
  - `ng generate`: Generate components, services, modules
  - `ng serve`: Build and serve application
  - `ng build`: Build project for production
  - `ng test`: Run unit tests
  - `ng e2e`: Run end-to-end tests

### 20. **What is SPA (Single Page Application)?**
- Web application that loads single HTML page
- Updates content dynamically without page reloads
- **Angular SPA characteristics**:
  - Faster navigation between views
  - Better user experience
  - Reduced server load
  - Client-side routing
  - Can work offline with proper implementation

---

## **Mid-Level (2–5 Years Experience)**

### 21. **What is dependency injection?**
- Design pattern where classes receive dependencies from external sources
- **Angular DI system features**:
  - Hierarchical injector tree
  - Token-based lookup
  - Provider configuration at multiple levels
  - Supports singleton and instance-based services
- Reduces coupling between classes

### 22. **How does Angular DI work?**
- Uses hierarchical injector system
- **Components**:
  1. **Injector**: Container that holds providers
  2. **Provider**: Recipe for creating dependencies
  3. **Token**: Key used to locate dependency
- Providers can be configured at module or component level
- Child injectors can override parent providers

### 23. **What is a service?**
- Singleton class with specific purpose
- Decorated with `@Injectable()`
- Used for:
  - Data sharing between components
  - Business logic encapsulation
  - HTTP communication
  - Authentication/authorization
  - Logging and error handling
- Injected via constructor using DI

### 24. **What is `HttpClient`?**
- Built-in service for making HTTP requests
- Part of `HttpClientModule`
- **Features**:
  - Type-safe response handling
  - Request/response interception
  - Error handling with RxJS
  - Support for various data formats (JSON, text, blob)
  - Progress events for upload/download

### 25. **How do you call REST APIs in Angular?**
1. Import `HttpClientModule` in app module
2. Inject `HttpClient` in service/component
3. Use HTTP methods:
   ```typescript
   // GET request
   this.http.get<T[]>(url)
   // POST request
   this.http.post<T>(url, data)
   // PUT request
   this.http.put<T>(url, data)
   // DELETE request
   this.http.delete(url)
   ```
4. Handle responses using RxJS operators

### 26. **What is Observable?**
- Part of RxJS library
- Represents stream of values over time
- **Characteristics**:
  - Lazy evaluation (only executes when subscribed)
  - Can emit multiple values
  - Supports operators for transformation
  - Cancellable via subscription
- Used extensively in Angular for async operations

### 27. **Observable vs Promise.**
- **Emission**: Observable multiple values, Promise single value
- **Execution**: Observable lazy, Promise eager
- **Cancellation**: Observable cancellable, Promise not
- **Operators**: Observable has rich operators, Promise limited
- **Chaining**: Observable uses operators, Promise uses `.then()`
- **Error Handling**: Both have mechanisms but Observable more flexible

### 28. **What is RxJS?**
- Reactive Extensions library for JavaScript
- Implementation of Observable pattern
- **Key concepts**:
  - Observables (data streams)
  - Observers (consumers)
  - Subscriptions (connections)
  - Operators (transformation functions)
  - Subjects (special observables)
- Essential for handling async operations in Angular

### 29. **What are common RxJS operators?**
- **Creation**: `of`, `from`, `interval`, `timer`
- **Transformation**: `map`, `switchMap`, `mergeMap`, `concatMap`
- **Filtering**: `filter`, `take`, `skip`, `debounceTime`
- **Combination**: `combineLatest`, `forkJoin`, `merge`
- **Error Handling**: `catchError`, `retry`, `retryWhen`
- **Utility**: `tap`, `delay`, `finalize`

### 30. **What is subscription?**
- Connection between Observable and Observer
- Created when `subscribe()` is called
- **Responsibilities**:
  - Receives notifications from Observable
  - Can be cancelled to prevent memory leaks
  - Receives completion/error signals
- Should be unsubscribed when component is destroyed

### 31. **How do you unsubscribe from Observables?**
1. **Manual unsubscribe**:
   ```typescript
   private subscription: Subscription;
   
   ngOnInit() {
     this.subscription = observable.subscribe();
   }
   
   ngOnDestroy() {
     this.subscription.unsubscribe();
   }
   ```
2. **Async pipe** (automatic cleanup)
3. **takeUntil pattern**:
   ```typescript
   private destroy$ = new Subject<void>();
   
   ngOnInit() {
     observable.pipe(takeUntil(this.destroy$)).subscribe();
   }
   
   ngOnDestroy() {
     this.destroy$.next();
     this.destroy$.complete();
   }
   ```
4. **First operator** (`take(1)`, `first()`) for single emission

### 32. **What is routing in Angular?**
- Mechanism for navigating between views/components
- **Key concepts**:
  - Routes configuration mapping URLs to components
  - Router outlet where components render
  - Router links for navigation
  - Route parameters for dynamic segments
  - Route guards for protection
- Enables SPA navigation without page reloads

### 33. **What is `RouterModule`?**
- Angular module for routing functionality
- **Provides**:
  - `Router` service for programmatic navigation
  - `RouterOutlet` directive for component rendering
  - `RouterLink` directive for template navigation
  - Route configuration via `forRoot()` and `forChild()`
- Must be imported with route configuration

### 34. **What is lazy loading?**
- Technique to load feature modules on-demand
- **Benefits**:
  - Faster initial load time
  - Smaller initial bundle size
  - Better performance for large applications
- **Implementation**:
  ```typescript
  const routes: Routes = [
    { 
      path: 'feature', 
      loadChildren: () => import('./feature/feature.module')
        .then(m => m.FeatureModule) 
    }
  ];
  ```

### 35. **What is route guard?**
- Interface for controlling route navigation
- **Common use cases**:
  - Authentication/authorization
  - Data pre-fetching
  - Confirmation before leaving
  - Role-based access control
- Returns boolean, Observable, or Promise to allow/deny navigation

### 36. **Types of route guards.**
1. **CanActivate**: Controls access to route
2. **CanActivateChild**: Controls access to child routes
3. **CanDeactivate**: Controls leaving route
4. **CanLoad**: Controls lazy-loaded module loading
5. **Resolve**: Pre-fetches data before activation

### 37. **What is form handling in Angular?**
- Two approaches for managing forms:
  1. **Template-driven**: Forms defined in template, Angular infers FormControl objects
  2. **Reactive**: Forms defined in component, explicit FormControl creation
- **Features**:
  - Data binding and validation
  - State management and tracking
  - Error handling
  - Dynamic form controls

### 38. **Template-driven vs Reactive forms.**
- **Template-driven**:
  - Simpler, less code
  - Suitable for simple forms
  - Async validation challenging
  - Unit testing more difficult
  - Two-way binding with `[(ngModel)]`

- **Reactive forms**:
  - More control and flexibility
  - Better for complex forms
  - Synchronous programming model
  - Easier unit testing
  - Explicit form control creation

### 39. **What is form validation?**
- Process of ensuring user input meets requirements
- **Built-in validators**:
  - `required`, `minLength`, `maxLength`
  - `pattern`, `email`, `min`, `max`
- **Custom validators**: Create functions returning validation errors
- **Async validators**: For server-side validation
- **Validation states**: `valid`, `invalid`, `pending`, `pristine`, `dirty`, `touched`, `untouched`

### 40. **What is `FormBuilder`?**
- Service that simplifies reactive form creation
- **Methods**:
  - `group()`: Creates FormGroup
  - `control()`: Creates FormControl
  - `array()`: Creates FormArray
- **Example**:
  ```typescript
  this.form = this.fb.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]]
  });
  ```

---

## **Senior / Architect Level**

### 41. **Explain Angular architecture in detail.**
- **Modules**: Organization units (AppModule, FeatureModules)
- **Components**: UI building blocks with templates
- **Services**: Business logic and data services
- **Templates**: Views with Angular syntax
- **Metadata**: Decorators for configuration
- **Data Binding**: Component-template communication
- **Directives**: DOM behavior modifiers
- **Dependency Injection**: Service management
- **Routing**: Navigation between views

### 42. **What is change detection?**
- Mechanism to detect and propagate changes
- **Process**:
  1. Monitors component properties
  2. Checks for changes in bindings
  3. Updates DOM when changes detected
- Runs after async events (clicks, timers, HTTP)
- Can be configured with different strategies

### 43. **How does Angular change detection work?**
- **Default strategy**:
  1. Zone.js monkey patches async operations
  2. Triggers change detection after async events
  3. Checks all components from root to leaves
  4. Updates bindings if values changed
- **OnPush strategy**:
  1. Only runs when `@Input()` references change
  2. Or event occurs in component
  3. Or async pipe receives new value
  4. Or manually triggered via `ChangeDetectorRef`

### 44. **What is `OnPush` change detection strategy?**
- Performance optimization strategy
- **Triggers detection when**:
  - Input reference changes
  - Component emits event
  - Observable emits via async pipe
  - Manually via `ChangeDetectorRef`
- **Benefits**:
  - Redundant checks eliminated
  - Better performance for large apps
  - Explicit change management

### 45. **How do you improve Angular performance?**
1. **ChangeDetectionStrategy.OnPush**
2. **Lazy loading** modules
3. **TrackBy** with `*ngFor`
4. **Pure pipes** instead of methods
5. **AOT compilation** for production
6. **Tree shaking** with build optimization
7. **Virtual scrolling** for large lists
8. **Web Workers** for CPU-intensive tasks
9. **Optimize bundle size** (code splitting)
10. **Server-side rendering** (Angular Universal)

### 46. **What is Ahead-of-Time (AOT) compilation?**
- Compiles Angular templates during build phase
- **Benefits**:
  - Faster rendering (no compilation in browser)
  - Smaller bundle size (no compiler in bundle)
  - Template error detection at build time
  - Better security (no eval)
- **vs JIT**:
  - JIT compiles in browser
  - AOT compiles during build
  - AOT recommended for production

### 47. **AOT vs JIT compilation.**
- **AOT (Ahead-of-Time)**:
  - Build time compilation
  - Smaller bundle
  - Faster startup
  - Template errors caught early
  - Required for server-side rendering

- **JIT (Just-in-Time)**:
  - Runtime compilation in browser
  - Larger bundle (includes compiler)
  - Slower startup
  - Useful for development
  - Template errors at runtime

### 48. **What is Angular lifecycle hooks?**
- Interface methods called at specific component/directive phases
- **Order of execution**:
  1. `ngOnChanges()` - Input properties change
  2. `ngOnInit()` - After first `ngOnChanges()`
  3. `ngDoCheck()` - Custom change detection
  4. `ngAfterContentInit()` - After content projected
  5. `ngAfterContentChecked()` - After content checked
  6. `ngAfterViewInit()` - After view initialized
  7. `ngAfterViewChecked()` - After view checked
  8. `ngOnDestroy()` - Before destruction

### 49. **Explain commonly used lifecycle hooks.**
- **ngOnInit()**: Initialization logic after input binding
- **ngOnChanges()**: React to input property changes
- **ngOnDestroy()**: Cleanup (unsubscribe, remove listeners)
- **ngAfterViewInit()**: Access view children after initialization
- **ngDoCheck()**: Implement custom change detection

### 50. **What is state management?**
- Pattern for managing application state
- **Problems solved**:
  - Shared state across components
  - Predictable state changes
  - Debuggable state transitions
  - Performance optimization
- **Solutions**: Services, RxJS BehaviorSubject, NgRx, NGXS, Akita

### 51. **What is NgRx?**
- Redux pattern implementation for Angular
- **Core concepts**:
  - **Store**: Single source of truth
  - **Actions**: Describe events
  - **Reducers**: Pure functions handling state changes
  - **Selectors**: Memoized functions for state derivation
  - **Effects**: Side effect management
- Uses RxJS for reactive programming

### 52. **When should you use NgRx?**
- Large applications with complex state
- Multiple components sharing state
- Need for time-travel debugging
- Strict unidirectional data flow required
- Complex side effects management
- Team consistency in state management
- **Not recommended for**: Small apps, simple state, quick prototypes

### 53. **How do you handle large Angular applications?**
1. **Modular architecture** (feature modules)
2. **Lazy loading** with preloading strategy
3. **Shared module** for common components
4. **Core module** for singleton services
5. **State management** (NgRx for complex state)
6. **Micro-frontends** for very large apps
7. **Monorepo structure** with Nx workspaces
8. **Strict TypeScript configuration**
9. **Custom ESLint rules** for code consistency
10. **Automated testing** strategy

### 54. **How do you handle error handling globally?**
1. **HTTP Interceptor** for API errors
2. **Global Error Handler**:
   ```typescript
   @Injectable()
   export class GlobalErrorHandler implements ErrorHandler {
     handleError(error: any) {
       // Log to service, show user-friendly message
     }
   }
   ```
3. **Router error handling** with error route
4. **Loading/Error state management** in components
5. **Retry logic** for transient errors
6. **User notifications** via toast/snackbar service

### 55. **What is interceptor?**
- Service that intercepts HTTP requests/responses
- **Use cases**:
  - Add authentication headers
  - Log HTTP traffic
  - Handle errors globally
  - Transform request/response
  - Implement caching
  - Add loading indicators
- Multiple interceptors form processing chain

### 56. **How do HTTP interceptors work?**
1. Implement `HttpInterceptor` interface
2. Provide in root or feature module
3. Process request before sending
4. Process response before returning
5. Can modify, clone, or return new requests/responses
6. Can handle errors and retry logic
7. Multiple interceptors executed in order

### 57. **How do you secure Angular applications?**
1. **Authentication**: JWT, OAuth, OpenID Connect
2. **Authorization**: Route guards, role-based access
3. **Input sanitization**: DomSanitizer for safe HTML
4. **XSS prevention**: Built-in template sanitization
5. **CSRF protection**: WithCookieXSRFStrategy
6. **HTTPS enforcement**: For all communications
7. **Content Security Policy**: HTTP header configuration
8. **Security headers**: HSTS, X-Frame-Options
9. **Dependency auditing**: npm audit, Snyk
10. **Code review**: Security-focused reviews

### 58. **How do you handle role-based access?**
1. **Authentication service**: Manage user roles/permissions
2. **Route guards**: `CanActivate` with role checking
3. **Directive**: Structural directive for UI elements
   ```typescript
   @Directive({ selector: '[appHasRole]' })
   export class HasRoleDirective {
     @Input() appHasRole: string[];
     
     constructor(private templateRef: TemplateRef<any>,
                 private viewContainer: ViewContainerRef,
                 private authService: AuthService) {}
     
     ngOnInit() {
       if (this.authService.hasRole(this.appHasRole)) {
         this.viewContainer.createEmbeddedView(this.templateRef);
       } else {
         this.viewContainer.clear();
       }
     }
   }
   ```
4. **Service method**: Check permissions in components
5. **Backend validation**: Always verify on server

### 59. **What are Angular anti-patterns?**
1. **Subscribing in components** without cleanup
2. **Heavy computation in templates**
3. **Mutating data in pipes**
4. **Overusing two-way binding**
5. **Large components** (violating single responsibility)
6. **Business logic in components**
7. **Direct DOM manipulation** (bypassing Angular)
8. **Not using trackBy with *ngFor**
9. **Ignoring change detection optimization**
10. **Sync HTTP calls** (blocking UI)

### 60. **When should you NOT use Angular?**
- **Simple static websites**: Overkill, use plain HTML/CSS
- **SEO-critical marketing pages**: Without Angular Universal
- **Mobile-only apps**: Consider React Native/Flutter
- **Simple prototypes**: Quick iteration with lighter frameworks
- **Team lacking TypeScript experience**: Significant learning curve
- **Micro-interactions heavy sites**: Better with lighter libraries
- **Legacy browser support**: Limited in newer Angular versions
- **When React/Vue fit requirements better**: Evaluate team skills

---

## **Bonus – Practical / Scenario-Based Questions**

### 61. **How do you optimize Angular app load time?**
1. **Lazy loading** with preloading strategy
2. **AOT compilation** for production
3. **Bundle optimization**:
   - Tree shaking with Terser
   - Code splitting
   - Vendor chunk separation
4. **Assets optimization**:
   - Image compression
   - Font subsetting
   - Gzip/Brotli compression
5. **CDN usage** for static assets
6. **Service Workers** for caching
7. **Server-side rendering** (Angular Universal)
8. **Critical CSS inlining**

### 62. **How do you handle memory leaks in Angular?**
1. **Unsubscribe from Observables**:
   - Async pipe (automatic)
   - `takeUntil` pattern
   - `take(1)` for single emission
2. **Remove event listeners** in `ngOnDestroy`
3. **Clear intervals/timeouts**
4. **Detach change detection** for off-screen components
5. **Avoid closures** capturing component references
6. **Use Chrome DevTools** Memory profiler
7. **Weak references** where appropriate
8. **Component store cleanup** in state management

### 63. **How do you share data between components?**
1. **Parent to Child**: `@Input()` binding
2. **Child to Parent**: `@Output()` events
3. **Sibling Components**: Through parent
4. **Unrelated Components**:
   - Shared service with BehaviorSubject
   - State management (NgRx)
   - Router parameters/query params
   - LocalStorage/SessionStorage (limited)
5. **ViewChild/ContentChild**: Direct component reference

### 64. **How do you handle pagination and filtering?**
1. **Server-side** (recommended for large datasets):
   ```typescript
   getUsers(page: number, size: number, filters: any): Observable<UserPage> {
     const params = new HttpParams()
       .set('page', page.toString())
       .set('size', size.toString())
       .set('filters', JSON.stringify(filters));
     
     return this.http.get<UserPage>('/api/users', { params });
   }
   ```
2. **Client-side** (small datasets):
   - Array slice for pagination
   - Array filter for filtering
   - Computed properties with memoization
3. **Virtual scrolling** for UI performance
4. **Caching** with RxJS `shareReplay()`

### 65. **How do you implement authentication in Angular?**
1. **Auth Service** for login/logout/token management
2. **JToken storage**: HttpOnly cookies or secure storage
3. **HTTP Interceptor** to add auth headers
4. **Route Guards** for protected routes
5. **Token refresh** mechanism
6. **User state management** (BehaviorSubject/store)
7. **Silent refresh** for seamless experience
8. **Logout cleanup** (clear tokens, redirect)

### 66. **How do you handle file upload/download?**
**Upload**:
```typescript
uploadFile(file: File): Observable<ProgressEvent> {
  const formData = new FormData();
  formData.append('file', file);
  
  return this.http.post('/api/upload', formData, {
    reportProgress: true,
    observe: 'events'
  });
}
```

**Download**:
```typescript
downloadFile(id: string): void {
  this.http.get(`/api/files/${id}`, {
    responseType: 'blob',
    observe: 'response'
  }).subscribe(response => {
    const blob = new Blob([response.body], {type: response.headers.get('Content-Type')});
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = response.headers.get('File-Name') || 'download';
    a.click();
    window.URL.revokeObjectURL(url);
  });
}
```

### 67. **How do you manage environment configurations?**
1. **Environment files** (`environment.ts`, `environment.prod.ts`)
2. **Build configuration** in `angular.json`
3. **Runtime configuration** (external JSON file loaded at startup)
4. **Docker/K8s** environment variables
5. **Feature flags** service for toggle management
6. **Secure storage** for sensitive data (backend-managed)
7. **Configuration service** with caching

### 68. **How do you test Angular components?**
1. **Unit Tests** (Jasmine/Karma):
   ```typescript
   describe('Component', () => {
     let component: TestComponent;
     let fixture: ComponentFixture<TestComponent>;
     
     beforeEach(async () => {
       await TestBed.configureTestingModule({
         declarations: [TestComponent]
       }).compileComponents();
     });
     
     beforeEach(() => {
       fixture = TestBed.createComponent(TestComponent);
       component = fixture.componentInstance;
       fixture.detectChanges();
     });
     
     it('should create', () => {
       expect(component).toBeTruthy();
     });
   });
   ```
2. **Integration Tests**: Multiple components
3. **E2E Tests**: Cypress/Protractor
4. **Test Doubles**: Spies, mocks, stubs
5. **Test Coverage**: Istanbul reporting

### 69. **What is Jasmine and Karma?**
- **Jasmine**: Behavior-driven testing framework
  - Test structure: `describe()`, `it()`, `expect()`
  - Matchers: `toBe()`, `toEqual()`, `toContain()`
  - Spies: `spyOn()`, `callFake()`
  
- **Karma**: Test runner for JavaScript
  - Runs tests in real browsers
  - Watches file changes
  - Generates coverage reports
  - Integrates with CI/CD

### 70. **Angular vs React – when and why?**
**Choose Angular when**:
- Enterprise-scale applications
- Team prefers structured framework
- Need built-in solutions (routing, forms, HTTP)
- TypeScript adoption desired
- Large team requiring consistency
- Full-featured framework preferred

**Choose React when**:
- Highly interactive UI components
- Need flexibility in architecture
- Smaller bundle size critical
- Integration with other libraries needed
- Team has strong JavaScript skills
- Prefer incremental adoption

**Decision factors**:
- Team expertise and preference
- Project scale and complexity
- Performance requirements
- Ecosystem and library needs
- Long-term maintenance considerations