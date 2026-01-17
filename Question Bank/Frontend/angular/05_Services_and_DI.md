# SERVICES AND DEPENDENCY INJECTION ANSWERS

---

## 45. What is a Service in Angular?

### Answer:
- A **Service** is a class that encapsulates **reusable business logic**
- Used for **data sharing**, **API calls**, **state management**
- Follows **Single Responsibility Principle**
- Provided via **Dependency Injection**
- Not tied to any component's lifecycle

### Theoretical Keywords:
**Reusable logic**, **Separation of concerns**, **Singleton**,  
**API calls**, **State management**, **Injectable**

### Example:
```typescript
// User service
@Injectable({
  providedIn: 'root'  // Singleton across the app
})
export class UserService {
  private apiUrl = 'https://api.example.com/users';
  
  constructor(private http: HttpClient) { }
  
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }
  
  getUserById(id: number): Observable<User> {
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

// Usage in component
@Component({
  selector: 'app-user-list',
  template: `
    <div *ngFor="let user of users">{{ user.name }}</div>
  `
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  
  constructor(private userService: UserService) { }
  
  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }
}
```

---

## 46. What is Dependency Injection (DI)?

### Answer:
- **Dependency Injection** is a design pattern where dependencies are **provided externally**
- Angular's DI system **creates and manages** service instances
- Classes **declare** what they need, Angular **provides** it
- Promotes **loose coupling** and **testability**

### Theoretical Keywords:
**Design pattern**, **Inversion of Control**, **Loose coupling**,  
**Testability**, **Injector**, **Provider**

### Example:
```typescript
// Without DI (tight coupling)
class UserComponent {
  private userService: UserService;
  
  constructor() {
    this.userService = new UserService(new HttpClient());  // Hard to test
  }
}

// With DI (loose coupling)
@Component({
  selector: 'app-user'
})
export class UserComponent {
  constructor(private userService: UserService) { }  // Injected
}

// Benefits demonstrated in testing
describe('UserComponent', () => {
  let component: UserComponent;
  let mockUserService: jasmine.SpyObj<UserService>;
  
  beforeEach(() => {
    mockUserService = jasmine.createSpyObj('UserService', ['getUsers']);
    
    TestBed.configureTestingModule({
      providers: [
        { provide: UserService, useValue: mockUserService }
      ]
    });
    
    component = TestBed.createComponent(UserComponent).componentInstance;
  });
});
```

---

## 47. How does the Angular Injector work?

### Answer:
- **Injector** is a service locator that stores providers
- Creates **hierarchical tree** of injectors
- **Root injector** for app-wide singletons
- **Module injectors** for lazy-loaded modules
- **Element injectors** for components

### Theoretical Keywords:
**Hierarchical DI**, **Root injector**, **Module injector**,  
**Element injector**, **Provider resolution**, **Token**

### Example:
```typescript
// Provider registration levels

// 1. Root level (app-wide singleton)
@Injectable({
  providedIn: 'root'
})
export class AppService { }

// 2. Module level
@NgModule({
  providers: [ModuleService]  // Available to module
})
export class FeatureModule { }

// 3. Component level (new instance per component)
@Component({
  providers: [ComponentService]
})
export class MyComponent { }

// Injector hierarchy example
@Injectable()
export class LoggerService {
  log(message: string): void {
    console.log(message);
  }
}

// Custom injector usage
const injector = Injector.create({
  providers: [
    { provide: LoggerService, useClass: LoggerService }
  ]
});

const logger = injector.get(LoggerService);
logger.log('Hello');
```

---

## 48. What is `providedIn: 'root'`?

### Answer:
- **providedIn: 'root'** makes service a **singleton** at root level
- Service is **tree-shakable** if not used
- No need to add to module's providers array
- Recommended approach for most services

### Theoretical Keywords:
**Root singleton**, **Tree-shakable**, **Automatic registration**,  
**Application-wide**, **Lazy loading friendly**

### Example:
```typescript
// providedIn options
@Injectable({
  providedIn: 'root'  // App-wide singleton, tree-shakable
})
export class GlobalService { }

@Injectable({
  providedIn: 'any'  // New instance per lazy module
})
export class AnyService { }

@Injectable({
  providedIn: 'platform'  // Shared across apps (micro frontends)
})
export class PlatformService { }

@Injectable()  // Must be added to providers manually
export class ManualService { }

// Tree-shaking benefit
// If GlobalService is never injected anywhere,
// it won't be included in the final bundle
```

---

## 49. How to inject services into components?

### Answer:
- Use **constructor injection**
- Angular's DI resolves and provides instance
- Use **access modifiers** for automatic property creation
- Can use `inject()` function as alternative

### Example:
```typescript
// Method 1: Constructor injection (traditional)
@Component({
  selector: 'app-user'
})
export class UserComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private http: HttpClient
  ) { }
  
  loadUsers(): void {
    this.userService.getUsers().subscribe(/*...*/);
  }
}

// Method 2: inject() function (modern, Angular 14+)
@Component({
  selector: 'app-user'
})
export class UserComponent {
  private userService = inject(UserService);
  private router = inject(Router);
  
  loadUsers(): void {
    this.userService.getUsers().subscribe(/*...*/);
  }
}

// Method 3: @Inject decorator (for tokens)
@Component({
  selector: 'app-config'
})
export class ConfigComponent {
  constructor(
    @Inject(API_URL) private apiUrl: string,
    @Inject(DOCUMENT) private document: Document
  ) { }
}

// Method 4: Optional injection
@Component({
  selector: 'app-optional'
})
export class OptionalComponent {
  constructor(
    @Optional() private optionalService?: OptionalService
  ) {
    if (this.optionalService) {
      // Service was provided
    }
  }
}
```

---

## Provider Types

### Answer:
Different ways to configure providers:

| Provider Type | Use Case |
|---------------|----------|
| **useClass** | Provide different implementation |
| **useValue** | Provide static value |
| **useFactory** | Dynamic creation logic |
| **useExisting** | Alias to another provider |

### Example:
```typescript
// useClass: Different implementation
@Injectable()
export class MockUserService {
  getUsers(): Observable<User[]> {
    return of([{ id: 1, name: 'Mock User' }]);
  }
}

providers: [
  { provide: UserService, useClass: MockUserService }
]

// useValue: Static value or configuration
const API_CONFIG = {
  baseUrl: 'https://api.example.com',
  timeout: 5000
};

export const API_TOKEN = new InjectionToken<typeof API_CONFIG>('api.config');

providers: [
  { provide: API_TOKEN, useValue: API_CONFIG }
]

// useFactory: Dynamic creation
export function loggerFactory(http: HttpClient, config: Config) {
  return config.production 
    ? new ProductionLogger(http)
    : new DevelopmentLogger();
}

providers: [
  {
    provide: LoggerService,
    useFactory: loggerFactory,
    deps: [HttpClient, Config]
  }
]

// useExisting: Alias
providers: [
  NewLogger,
  { provide: OldLogger, useExisting: NewLogger }
]
```

---

## InjectionToken

### Answer:
- **InjectionToken** creates unique tokens for non-class dependencies
- Used for **configuration objects**, **strings**, **functions**
- Prevents naming conflicts

### Example:
```typescript
// Creating injection tokens
export const API_URL = new InjectionToken<string>('api.url');
export const APP_CONFIG = new InjectionToken<AppConfig>('app.config');
export const FEATURE_FLAGS = new InjectionToken<FeatureFlags>('feature.flags');

// Providing values
@NgModule({
  providers: [
    { provide: API_URL, useValue: 'https://api.example.com' },
    { 
      provide: APP_CONFIG, 
      useValue: { 
        appName: 'My App',
        version: '1.0.0' 
      } 
    },
    {
      provide: FEATURE_FLAGS,
      useFactory: () => ({
        darkMode: true,
        newDashboard: false
      })
    }
  ]
})
export class AppModule { }

// Injecting tokens
@Component({
  selector: 'app-root'
})
export class AppComponent {
  constructor(
    @Inject(API_URL) private apiUrl: string,
    @Inject(APP_CONFIG) private config: AppConfig,
    @Inject(FEATURE_FLAGS) private features: FeatureFlags
  ) {
    console.log(this.apiUrl);  // 'https://api.example.com'
  }
  
  // Or using inject() function
  private api = inject(API_URL);
}
```

---
