# ADVANCED TOPICS ANSWERS

---

# PERFORMANCE & OPTIMIZATION

---

## 113. How to optimize Angular application performance?

### Answer:
1. **OnPush Change Detection**
2. **Lazy Loading**
3. **trackBy in ngFor**
4. **Pure Pipes**
5. **Bundle optimization**
6. **Web Workers**

### Example:
```typescript
// 1. OnPush Change Detection
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OptimizedComponent { }

// 2. Lazy Loading
const routes: Routes = [
  {
    path: 'feature',
    loadChildren: () => import('./feature/feature.module')
      .then(m => m.FeatureModule)
  }
];

// 3. trackBy
<div *ngFor="let item of items; trackBy: trackById">
  {{ item.name }}
</div>

trackById(index: number, item: Item): number {
  return item.id;
}

// 4. Pure Pipe instead of method in template
// BAD: <p>{{ getFullName() }}</p>
// GOOD: <p>{{ user | fullName }}</p>

// 5. Bundle Analysis
// ng build --stats-json
// npx webpack-bundle-analyzer dist/stats.json

// 6. Virtual Scrolling
import { ScrollingModule } from '@angular/cdk/scrolling';

@Component({
  template: `
    <cdk-virtual-scroll-viewport itemSize="50" class="viewport">
      <div *cdkVirtualFor="let item of items">{{ item.name }}</div>
    </cdk-virtual-scroll-viewport>
  `
})
export class VirtualScrollComponent { }
```

---

## 114. What is tree shaking?

### Answer:
- **Tree shaking** removes **unused code** from bundle
- Works with **ES modules** (import/export)
- Requires **production build**
- Libraries must be tree-shakable

### Example:
```typescript
// Tree-shakable (ES modules)
import { map } from 'rxjs/operators';  // Only imports 'map'

// Not tree-shakable
import * as _ from 'lodash';  // Imports entire library

// Better: Import specific function
import { debounce } from 'lodash-es';

// Service tree-shaking
@Injectable({
  providedIn: 'root'  // Tree-shakable if not injected
})
export class MyService { }
```

---

## 115. How to measure Angular application performance?

### Answer:
- **Lighthouse**: Overall performance audit
- **Chrome DevTools**: Performance tab, Memory
- **Angular DevTools**: Component profiler
- **Bundle Analyzer**: Bundle size analysis

### Example:
```typescript
// Enable Angular profiling
import { enableDebugTools } from '@angular/platform-browser';

platformBrowserDynamic().bootstrapModule(AppModule)
  .then(moduleRef => {
    const appRef = moduleRef.injector.get(ApplicationRef);
    const componentRef = appRef.components[0];
    enableDebugTools(componentRef);
  });

// In console: ng.profiler.timeChangeDetection()

// Custom performance logging
export class PerformanceService {
  mark(name: string): void {
    performance.mark(name);
  }
  
  measure(name: string, startMark: string, endMark: string): number {
    performance.measure(name, startMark, endMark);
    const measure = performance.getEntriesByName(name)[0];
    console.log(`${name}: ${measure.duration}ms`);
    return measure.duration;
  }
}
```

---

## 116. What is preloading strategy?

### Answer:
- **Preloading** loads lazy modules **after initial load**
- Improves **perceived performance**
- Built-in strategies: NoPreloading, PreloadAllModules

### Example:
```typescript
// Preload all modules
@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: PreloadAllModules
    })
  ]
})
export class AppRoutingModule { }

// Custom preloading strategy
@Injectable({ providedIn: 'root' })
export class SelectivePreloadingStrategy implements PreloadingStrategy {
  preload(route: Route, load: () => Observable<any>): Observable<any> {
    // Only preload routes marked with data.preload
    if (route.data?.['preload']) {
      return load();
    }
    return of(null);
  }
}

// Route configuration
const routes: Routes = [
  {
    path: 'important',
    loadChildren: () => import('./important/important.module'),
    data: { preload: true }  // Will be preloaded
  },
  {
    path: 'rarely-used',
    loadChildren: () => import('./rarely/rarely.module')
    // Won't be preloaded
  }
];
```

---

## 117. How to use Web Workers in Angular?

### Answer:
```bash
# Generate Web Worker
ng generate web-worker app
```

```typescript
// app.worker.ts
addEventListener('message', ({ data }) => {
  const result = heavyComputation(data);
  postMessage(result);
});

function heavyComputation(data: number[]): number {
  return data.reduce((sum, n) => sum + n, 0);
}

// Component using Web Worker
@Component({...})
export class HeavyTaskComponent {
  result = 0;
  private worker: Worker;
  
  constructor() {
    if (typeof Worker !== 'undefined') {
      this.worker = new Worker(new URL('./app.worker', import.meta.url));
      this.worker.onmessage = ({ data }) => {
        this.result = data;
      };
    }
  }
  
  calculate(data: number[]): void {
    this.worker.postMessage(data);
  }
  
  ngOnDestroy(): void {
    this.worker?.terminate();
  }
}
```

---

# ADVANCED RxJS

---

## 118. What is shareReplay?

### Answer:
- **shareReplay** multicasts and caches emitted values
- New subscribers get **cached values**
- Useful for **HTTP caching**

### Example:
```typescript
@Injectable({ providedIn: 'root' })
export class DataService {
  private config$?: Observable<Config>;
  
  getConfig(): Observable<Config> {
    if (!this.config$) {
      this.config$ = this.http.get<Config>('/api/config').pipe(
        shareReplay(1)  // Cache 1 value
      );
    }
    return this.config$;
  }
  
  // Multiple components call this - only one HTTP request
}
```

---

## 119. What is the difference between share and shareReplay?

### Answer:

| Operator | Caching | Use Case |
|----------|---------|----------|
| **share** | No caching | Live streams |
| **shareReplay** | Caches N values | HTTP, static data |

### Example:
```typescript
// share: No caching
const stream$ = source$.pipe(share());
stream$.subscribe();  // Gets live values only
// Later...
stream$.subscribe();  // Misses earlier values

// shareReplay: Caches values
const cached$ = source$.pipe(shareReplay(1));
cached$.subscribe();  // Gets live values
// Later...
cached$.subscribe();  // Gets last cached value + new ones
```

---

## 120. What are custom RxJS operators?

### Answer:
```typescript
// Custom operator: Log values
function log<T>(tag: string): MonoTypeOperatorFunction<T> {
  return (source$) => source$.pipe(
    tap(value => console.log(`[${tag}]`, value))
  );
}

// Custom operator: Retry with exponential backoff
function retryWithBackoff<T>(
  maxRetries: number,
  delayMs: number
): MonoTypeOperatorFunction<T> {
  return (source$) => source$.pipe(
    retryWhen(errors$ =>
      errors$.pipe(
        scan((retryCount, error) => {
          if (retryCount >= maxRetries) throw error;
          return retryCount + 1;
        }, 0),
        delayWhen(retryCount => 
          timer(delayMs * Math.pow(2, retryCount))
        )
      )
    )
  );
}

// Usage
this.http.get('/api/data').pipe(
  log('API Response'),
  retryWithBackoff(3, 1000)
).subscribe();
```

---

## 121. What is the difference between combineLatest and forkJoin?

### Answer:

| Operator | Behavior | Use Case |
|----------|----------|----------|
| **combineLatest** | Emits on any source emission | Live streams |
| **forkJoin** | Emits once when all complete | Parallel HTTP |

### Example:
```typescript
// combineLatest: Emits every time any source emits
combineLatest([
  this.form.valueChanges,
  this.permissions$
]).subscribe(([formValue, permissions]) => {
  // Runs on every form change
});

// forkJoin: Waits for all to complete
forkJoin({
  users: this.http.get<User[]>('/api/users'),
  products: this.http.get<Product[]>('/api/products')
}).subscribe(({ users, products }) => {
  // Runs once when both complete
});
```

---

# TESTING

---

## 122. How to unit test Angular components?

### Answer:
```typescript
describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let userService: jasmine.SpyObj<UserService>;
  
  beforeEach(async () => {
    const spy = jasmine.createSpyObj('UserService', ['getUser']);
    
    await TestBed.configureTestingModule({
      declarations: [UserComponent],
      providers: [
        { provide: UserService, useValue: spy }
      ]
    }).compileComponents();
    
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should display user name', () => {
    const user = { id: 1, name: 'John' };
    userService.getUser.and.returnValue(of(user));
    
    fixture.detectChanges();
    
    const element = fixture.nativeElement.querySelector('.user-name');
    expect(element.textContent).toContain('John');
  });
  
  it('should call service on init', () => {
    userService.getUser.and.returnValue(of({ id: 1, name: 'John' }));
    
    fixture.detectChanges();
    
    expect(userService.getUser).toHaveBeenCalled();
  });
});
```

---

## 123. How to test services?

### Answer:
```typescript
describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();  // Verify no outstanding requests
  });
  
  it('should fetch users', () => {
    const mockUsers = [{ id: 1, name: 'John' }];
    
    service.getUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
    });
    
    const req = httpMock.expectOne('/api/users');
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });
  
  it('should handle error', () => {
    service.getUsers().subscribe({
      error: (error) => {
        expect(error.status).toBe(500);
      }
    });
    
    const req = httpMock.expectOne('/api/users');
    req.flush('Error', { status: 500, statusText: 'Server Error' });
  });
});
```

---

## 124. How to test async code?

### Answer:
```typescript
describe('AsyncComponent', () => {
  // Using fakeAsync and tick
  it('should work with setTimeout', fakeAsync(() => {
    let value = false;
    
    setTimeout(() => { value = true; }, 1000);
    
    tick(1000);  // Advance time
    
    expect(value).toBe(true);
  }));
  
  // Using async/await with fixture
  it('should work with observables', async () => {
    const fixture = TestBed.createComponent(AsyncComponent);
    fixture.detectChanges();
    
    await fixture.whenStable();
    
    expect(fixture.nativeElement.textContent).toContain('loaded');
  });
  
  // Testing observables with marbles
  it('should debounce search', () => {
    const scheduler = new TestScheduler((actual, expected) => {
      expect(actual).toEqual(expected);
    });
    
    scheduler.run(({ cold, expectObservable }) => {
      const input = cold('a-b-c|');
      const expected = '---c|';
      
      const result = input.pipe(debounceTime(2, scheduler));
      
      expectObservable(result).toBe(expected);
    });
  });
});
```

---

## 125. What is TestBed?

### Answer:
- **TestBed** configures and creates testing module
- Sets up **dependency injection** for tests
- Creates **component fixtures** for testing

### Example:
```typescript
beforeEach(async () => {
  await TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
      RouterTestingModule,
      SharedModule
    ],
    declarations: [
      MyComponent,
      MockChildComponent
    ],
    providers: [
      { provide: DataService, useClass: MockDataService },
      { provide: API_URL, useValue: 'http://test.com' }
    ],
    schemas: [NO_ERRORS_SCHEMA]  // Ignore unknown elements
  }).compileComponents();
});
```

---

# SECURITY

---

## 126. What are common security vulnerabilities in Angular?

### Answer:
1. **XSS** (Cross-Site Scripting)
2. **CSRF** (Cross-Site Request Forgery)
3. **Injection attacks**
4. **Insecure dependencies**

---

## 127. How does Angular prevent XSS?

### Answer:
- Angular **sanitizes** values by default
- Automatically escapes dangerous HTML
- Use `DomSanitizer` for trusted content

### Example:
```typescript
// Automatic sanitization
@Component({
  template: `
    <!-- Safe: Angular escapes this -->
    <div>{{ userInput }}</div>
    
    <!-- Angular sanitizes innerHTML -->
    <div [innerHTML]="htmlContent"></div>
  `
})
export class SafeComponent {
  userInput = '<script>alert("XSS")</script>';  // Escaped
  htmlContent = '<b>Bold</b><script>alert("XSS")</script>';  // Script removed
}

// Bypass sanitization (use carefully!)
@Component({...})
export class TrustedComponent {
  constructor(private sanitizer: DomSanitizer) { }
  
  getTrustedHtml(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }
  
  getTrustedUrl(url: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }
}
```

---

## 128. How to implement CSRF protection?

### Answer:
```typescript
// HttpClient automatically reads XSRF token
// Backend sets cookie: XSRF-TOKEN
// Angular sends header: X-XSRF-TOKEN

// Custom configuration if needed
@NgModule({
  imports: [
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'My-Xsrf-Cookie',
      headerName: 'My-Xsrf-Header'
    })
  ]
})
export class AppModule { }
```

---

## 129. Security Best Practices

### Answer:
```typescript
// 1. Use HttpOnly cookies for tokens
// Set by backend, not accessible via JavaScript

// 2. Validate and sanitize inputs
// 3. Use Content Security Policy headers
// 4. Keep dependencies updated
// npm audit fix

// 5. Avoid eval() and innerHTML when possible

// 6. Implement proper authentication
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Add token to requests
    // Handle 401 responses
  }
}

// 7. Use HTTPS only
// 8. Implement rate limiting (backend)
```

---

# CLI & TOOLING

---

## 130. Useful Angular CLI commands

### Answer:
```bash
# Generate
ng generate component user
ng generate service data
ng generate module feature --routing
ng generate guard auth
ng generate pipe transform
ng generate directive highlight

# Build
ng build --configuration=production
ng build --stats-json  # For bundle analysis

# Serve
ng serve --port 4300 --open
ng serve --proxy-config proxy.conf.json

# Test
ng test --code-coverage
ng e2e

# Update
ng update @angular/cli @angular/core

# Analyze
ng analytics
```

---

## 131-132. CLI Configuration & Schematics

### Answer:
```json
// angular.json customization
{
  "schematics": {
    "@schematics/angular:component": {
      "style": "scss",
      "changeDetection": "OnPush",
      "standalone": true
    }
  }
}
```

---

# i18n (INTERNATIONALIZATION)

---

## 133-134. Angular Internationalization

### Answer:
```typescript
// 1. Mark text for translation
<h1 i18n="@@welcomeTitle">Welcome</h1>

// 2. Extract messages
// ng extract-i18n --output-path locale

// 3. Translate messages.xlf to messages.fr.xlf

// 4. Build with locale
// ng build --localize

// angular.json configuration
{
  "i18n": {
    "sourceLocale": "en-US",
    "locales": {
      "fr": "locale/messages.fr.xlf"
    }
  }
}
```

---

# ACCESSIBILITY

---

## 135-137. Angular Accessibility

### Answer:
```typescript
@Component({
  template: `
    <!-- Proper ARIA labels -->
    <button aria-label="Close dialog" (click)="close()">×</button>
    
    <!-- Focus management -->
    <input #searchInput aria-describedby="search-hint">
    <span id="search-hint">Enter search term</span>
    
    <!-- Live regions -->
    <div aria-live="polite">{{ statusMessage }}</div>
    
    <!-- Keyboard navigation -->
    <div (keydown.enter)="select()" (keydown.space)="select()" tabindex="0">
      Selectable item
    </div>
  `
})
export class AccessibleComponent implements AfterViewInit {
  @ViewChild('searchInput') searchInput!: ElementRef;
  
  ngAfterViewInit(): void {
    this.searchInput.nativeElement.focus();
  }
}

// CDK A11y module
import { A11yModule, LiveAnnouncer } from '@angular/cdk/a11y';

constructor(private liveAnnouncer: LiveAnnouncer) { }

announce(message: string): void {
  this.liveAnnouncer.announce(message, 'polite');
}
```

---

# SSR / ANGULAR UNIVERSAL

---

## 138-140. Server-Side Rendering

### Answer:
```bash
# Add SSR to project
ng add @angular/ssr
```

```typescript
// Server-side specific code
import { isPlatformBrowser, isPlatformServer } from '@angular/common';
import { PLATFORM_ID, inject } from '@angular/core';

@Component({...})
export class UniversalComponent {
  private platformId = inject(PLATFORM_ID);
  
  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      // Browser-only code (localStorage, window, etc.)
      localStorage.setItem('key', 'value');
    }
    
    if (isPlatformServer(this.platformId)) {
      // Server-only code
    }
  }
}

// Benefits of SSR:
// 1. Better SEO
// 2. Faster First Contentful Paint
// 3. Works without JavaScript enabled
```

---

# STATE MANAGEMENT PATTERNS

---

## 141-144. State Management Alternatives

### Answer:
```typescript
// 1. BehaviorSubject Store Pattern
@Injectable({ providedIn: 'root' })
export class Store<T> {
  private state$: BehaviorSubject<T>;
  
  constructor(initialState: T) {
    this.state$ = new BehaviorSubject<T>(initialState);
  }
  
  get state(): T {
    return this.state$.value;
  }
  
  select<K>(selector: (state: T) => K): Observable<K> {
    return this.state$.pipe(
      map(selector),
      distinctUntilChanged()
    );
  }
  
  setState(newState: Partial<T>): void {
    this.state$.next({ ...this.state, ...newState });
  }
}

// 2. Angular Signals (Angular 16+)
@Injectable({ providedIn: 'root' })
export class UserStore {
  // State
  users = signal<User[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  
  // Computed
  activeUsers = computed(() => 
    this.users().filter(u => u.isActive)
  );
  
  // Actions
  loadUsers(): void {
    this.loading.set(true);
    // ...
  }
}

// Component with signals
@Component({
  template: `
    <div *ngIf="store.loading()">Loading...</div>
    <div *ngFor="let user of store.users()">{{ user.name }}</div>
  `
})
export class UserListComponent {
  store = inject(UserStore);
}
```

---
