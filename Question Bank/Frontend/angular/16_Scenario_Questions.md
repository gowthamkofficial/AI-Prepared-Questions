# SCENARIO-BASED QUESTIONS ANSWERS

---

## Scenario 1: Component Communication in Complex Hierarchy

### Question:
You have a deeply nested component structure (Parent → Child → GrandChild → GreatGrandChild). How would you communicate data from GreatGrandChild to Parent without prop drilling?

### Answer:
```typescript
// Solution 1: Shared Service with BehaviorSubject
@Injectable({ providedIn: 'root' })
export class CommunicationService {
  private dataSubject = new BehaviorSubject<any>(null);
  data$ = this.dataSubject.asObservable();
  
  sendData(data: any): void {
    this.dataSubject.next(data);
  }
}

// GreatGrandChild (sender)
@Component({
  selector: 'app-great-grandchild'
})
export class GreatGrandChildComponent {
  constructor(private commService: CommunicationService) { }
  
  sendToParent(data: any): void {
    this.commService.sendData(data);
  }
}

// Parent (receiver)
@Component({
  selector: 'app-parent',
  template: `<div>{{ receivedData | json }}</div>`
})
export class ParentComponent implements OnInit, OnDestroy {
  receivedData: any;
  private destroy$ = new Subject<void>();
  
  constructor(private commService: CommunicationService) { }
  
  ngOnInit(): void {
    this.commService.data$
      .pipe(takeUntil(this.destroy$))
      .subscribe(data => this.receivedData = data);
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

// Solution 2: Using Event Emitters chain (not recommended for deep nesting)
// Solution 3: NgRx for complex state management
```

---

## Scenario 2: Large Data Table Performance

### Question:
Your application has a table with 10,000 rows. The page is very slow. How would you optimize it?

### Answer:
```typescript
// Solution: Virtual Scrolling + OnPush + trackBy
import { ScrollingModule } from '@angular/cdk/scrolling';

@Component({
  selector: 'app-large-table',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <cdk-virtual-scroll-viewport itemSize="48" class="viewport">
      <table>
        <thead>
          <tr>
            <th *ngFor="let col of columns">{{ col.label }}</th>
          </tr>
        </thead>
        <tbody>
          <tr *cdkVirtualFor="let row of data; trackBy: trackById">
            <td *ngFor="let col of columns">{{ row[col.field] }}</td>
          </tr>
        </tbody>
      </table>
    </cdk-virtual-scroll-viewport>
  `,
  styles: [`
    .viewport {
      height: 500px;
      width: 100%;
    }
  `]
})
export class LargeTableComponent {
  @Input() data: any[] = [];
  @Input() columns: Column[] = [];
  
  trackById(index: number, row: any): any {
    return row.id;
  }
}

// Additional optimizations:
// 1. Pagination instead of loading all data
// 2. Server-side filtering/sorting
// 3. Memoize expensive computations with pure pipes
// 4. Use OnPush change detection
// 5. Avoid complex template expressions
```

---

## Scenario 3: Handle Multiple HTTP Requests

### Question:
You need to fetch user details from one API and their orders from another API. Both should complete before displaying the page. How would you handle this?

### Answer:
```typescript
// Solution 1: forkJoin for parallel requests
@Component({
  selector: 'app-user-dashboard'
})
export class UserDashboardComponent implements OnInit {
  user: User | null = null;
  orders: Order[] = [];
  loading = true;
  error: string | null = null;
  
  constructor(
    private userService: UserService,
    private orderService: OrderService,
    private route: ActivatedRoute
  ) { }
  
  ngOnInit(): void {
    const userId = this.route.snapshot.params['id'];
    
    forkJoin({
      user: this.userService.getUser(userId),
      orders: this.orderService.getOrders(userId)
    }).pipe(
      finalize(() => this.loading = false)
    ).subscribe({
      next: ({ user, orders }) => {
        this.user = user;
        this.orders = orders;
      },
      error: (err) => {
        this.error = 'Failed to load data';
        console.error(err);
      }
    });
  }
}

// Solution 2: Using Resolver
export const userDashboardResolver: ResolveFn<{ user: User; orders: Order[] }> = 
  (route) => {
    const userService = inject(UserService);
    const orderService = inject(OrderService);
    const userId = route.params['id'];
    
    return forkJoin({
      user: userService.getUser(userId),
      orders: orderService.getOrders(userId)
    });
  };

// Route config
const routes: Routes = [
  {
    path: 'dashboard/:id',
    component: UserDashboardComponent,
    resolve: {
      data: userDashboardResolver
    }
  }
];

// Component using resolver data
export class UserDashboardComponent implements OnInit {
  data$ = this.route.data.pipe(
    map(data => data['data'])
  );
  
  constructor(private route: ActivatedRoute) { }
}
```

---

## Scenario 4: Search with Debounce

### Question:
Implement a search input that calls the API only after the user stops typing for 300ms and doesn't call for the same query twice.

### Answer:
```typescript
@Component({
  selector: 'app-search',
  template: `
    <input 
      type="text" 
      [formControl]="searchControl"
      placeholder="Search..."
    >
    
    <div *ngIf="loading">Searching...</div>
    
    <ul>
      <li *ngFor="let result of results$ | async">
        {{ result.name }}
      </li>
    </ul>
  `
})
export class SearchComponent implements OnInit {
  searchControl = new FormControl('');
  results$!: Observable<SearchResult[]>;
  loading = false;
  
  constructor(private searchService: SearchService) { }
  
  ngOnInit(): void {
    this.results$ = this.searchControl.valueChanges.pipe(
      debounceTime(300),              // Wait 300ms after typing stops
      distinctUntilChanged(),          // Don't search if same query
      filter(term => term.length >= 2), // Minimum 2 characters
      tap(() => this.loading = true),
      switchMap(term => 
        this.searchService.search(term).pipe(
          catchError(() => of([]))     // Return empty on error
        )
      ),
      tap(() => this.loading = false)
    );
  }
}

// Service
@Injectable({ providedIn: 'root' })
export class SearchService {
  private cache = new Map<string, SearchResult[]>();
  
  constructor(private http: HttpClient) { }
  
  search(term: string): Observable<SearchResult[]> {
    // Check cache first
    if (this.cache.has(term)) {
      return of(this.cache.get(term)!);
    }
    
    return this.http.get<SearchResult[]>(`/api/search?q=${term}`).pipe(
      tap(results => this.cache.set(term, results))
    );
  }
}
```

---

## Scenario 5: Prevent Unsaved Changes

### Question:
How would you warn users when they try to leave a page with unsaved form changes?

### Answer:
```typescript
// Interface for components with unsaved changes
export interface CanComponentDeactivate {
  canDeactivate(): boolean | Observable<boolean>;
}

// Guard
export const unsavedChangesGuard: CanDeactivateFn<CanComponentDeactivate> = 
  (component) => {
    if (component.canDeactivate()) {
      return true;
    }
    return confirm('You have unsaved changes. Do you really want to leave?');
  };

// Route configuration
const routes: Routes = [
  {
    path: 'edit/:id',
    component: EditComponent,
    canDeactivate: [unsavedChangesGuard]
  }
];

// Component implementation
@Component({
  selector: 'app-edit'
})
export class EditComponent implements CanComponentDeactivate {
  form: FormGroup;
  private initialValue: any;
  
  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
    
    // Store initial value
    this.initialValue = this.form.value;
  }
  
  canDeactivate(): boolean {
    // Compare current value with initial
    return JSON.stringify(this.form.value) === JSON.stringify(this.initialValue);
  }
  
  onSave(): void {
    if (this.form.valid) {
      // Save logic...
      this.initialValue = this.form.value;  // Update initial value
    }
  }
}

// Also handle browser back/refresh
@HostListener('window:beforeunload', ['$event'])
onBeforeUnload(event: BeforeUnloadEvent): void {
  if (!this.canDeactivate()) {
    event.returnValue = 'You have unsaved changes';
  }
}
```

---

## Scenario 6: Authentication Flow

### Question:
Implement a complete authentication flow with protected routes, auto-logout on token expiry, and token refresh.

### Answer:
```typescript
// Auth Service
@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();
  
  private tokenExpirationTimer: any;
  
  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.autoLogin();
  }
  
  login(credentials: { email: string; password: string }): Observable<User> {
    return this.http.post<AuthResponse>('/api/login', credentials).pipe(
      map(response => {
        this.handleAuthentication(response);
        return response.user;
      })
    );
  }
  
  private handleAuthentication(response: AuthResponse): void {
    const expirationDate = new Date(new Date().getTime() + response.expiresIn * 1000);
    
    localStorage.setItem('authData', JSON.stringify({
      user: response.user,
      token: response.token,
      expirationDate: expirationDate.toISOString()
    }));
    
    this.currentUserSubject.next(response.user);
    this.setAutoLogout(response.expiresIn * 1000);
  }
  
  autoLogin(): void {
    const authDataString = localStorage.getItem('authData');
    if (!authDataString) return;
    
    const authData = JSON.parse(authDataString);
    const expirationDate = new Date(authData.expirationDate);
    
    if (expirationDate <= new Date()) {
      this.logout();
      return;
    }
    
    this.currentUserSubject.next(authData.user);
    const expiresIn = expirationDate.getTime() - new Date().getTime();
    this.setAutoLogout(expiresIn);
  }
  
  private setAutoLogout(duration: number): void {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, duration);
  }
  
  logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('authData');
    
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    
    this.router.navigate(['/login']);
  }
  
  getToken(): string | null {
    const authData = localStorage.getItem('authData');
    return authData ? JSON.parse(authData).token : null;
  }
}

// Auth Guard
export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  return authService.currentUser$.pipe(
    take(1),
    map(user => {
      if (user) return true;
      return router.createUrlTree(['/login']);
    })
  );
};

// Auth Interceptor
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    
    if (token) {
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` }
      });
    }
    
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.logout();
        }
        return throwError(() => error);
      })
    );
  }
}
```

---

## Scenario 7: Dynamic Form Generation

### Question:
Build a form that generates fields dynamically based on JSON configuration from an API.

### Answer:
```typescript
// Field configuration interface
interface FormFieldConfig {
  type: 'text' | 'number' | 'email' | 'select' | 'checkbox' | 'textarea';
  name: string;
  label: string;
  required?: boolean;
  options?: { value: any; label: string }[];
  validators?: {
    min?: number;
    max?: number;
    minLength?: number;
    maxLength?: number;
    pattern?: string;
  };
}

@Component({
  selector: 'app-dynamic-form',
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <div *ngFor="let field of fields" class="form-field">
        <label [for]="field.name">{{ field.label }}</label>
        
        <ng-container [ngSwitch]="field.type">
          <input 
            *ngSwitchCase="'text'"
            type="text"
            [id]="field.name"
            [formControlName]="field.name"
          >
          
          <input 
            *ngSwitchCase="'number'"
            type="number"
            [id]="field.name"
            [formControlName]="field.name"
          >
          
          <input 
            *ngSwitchCase="'email'"
            type="email"
            [id]="field.name"
            [formControlName]="field.name"
          >
          
          <select 
            *ngSwitchCase="'select'"
            [id]="field.name"
            [formControlName]="field.name"
          >
            <option *ngFor="let opt of field.options" [value]="opt.value">
              {{ opt.label }}
            </option>
          </select>
          
          <textarea 
            *ngSwitchCase="'textarea'"
            [id]="field.name"
            [formControlName]="field.name"
          ></textarea>
          
          <input 
            *ngSwitchCase="'checkbox'"
            type="checkbox"
            [id]="field.name"
            [formControlName]="field.name"
          >
        </ng-container>
        
        <div *ngIf="form.get(field.name)?.invalid && form.get(field.name)?.touched" 
             class="error">
          {{ getErrorMessage(field.name) }}
        </div>
      </div>
      
      <button type="submit" [disabled]="form.invalid">Submit</button>
    </form>
  `
})
export class DynamicFormComponent implements OnInit {
  @Input() fields: FormFieldConfig[] = [];
  @Output() formSubmit = new EventEmitter<any>();
  
  form!: FormGroup;
  
  ngOnInit(): void {
    this.buildForm();
  }
  
  private buildForm(): void {
    const group: { [key: string]: FormControl } = {};
    
    this.fields.forEach(field => {
      const validators = this.getValidators(field);
      group[field.name] = new FormControl('', validators);
    });
    
    this.form = new FormGroup(group);
  }
  
  private getValidators(field: FormFieldConfig): ValidatorFn[] {
    const validators: ValidatorFn[] = [];
    
    if (field.required) validators.push(Validators.required);
    if (field.type === 'email') validators.push(Validators.email);
    
    if (field.validators) {
      if (field.validators.min !== undefined) 
        validators.push(Validators.min(field.validators.min));
      if (field.validators.max !== undefined) 
        validators.push(Validators.max(field.validators.max));
      if (field.validators.minLength !== undefined) 
        validators.push(Validators.minLength(field.validators.minLength));
      if (field.validators.maxLength !== undefined) 
        validators.push(Validators.maxLength(field.validators.maxLength));
      if (field.validators.pattern) 
        validators.push(Validators.pattern(field.validators.pattern));
    }
    
    return validators;
  }
  
  getErrorMessage(fieldName: string): string {
    const control = this.form.get(fieldName);
    if (!control?.errors) return '';
    
    if (control.errors['required']) return 'This field is required';
    if (control.errors['email']) return 'Invalid email format';
    if (control.errors['min']) return `Minimum value is ${control.errors['min'].min}`;
    if (control.errors['max']) return `Maximum value is ${control.errors['max'].max}`;
    
    return 'Invalid value';
  }
  
  onSubmit(): void {
    if (this.form.valid) {
      this.formSubmit.emit(this.form.value);
    }
  }
}
```

---

## Scenario 8: Real-time Updates with WebSocket

### Question:
Implement real-time notifications using WebSocket that displays toast messages.

### Answer:
```typescript
// WebSocket Service
@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private socket$?: WebSocketSubject<any>;
  private messages$ = new Subject<any>();
  
  connect(url: string): Observable<any> {
    if (!this.socket$) {
      this.socket$ = webSocket({
        url,
        openObserver: {
          next: () => console.log('WebSocket connected')
        },
        closeObserver: {
          next: () => {
            console.log('WebSocket disconnected');
            this.socket$ = undefined;
          }
        }
      });
      
      this.socket$.pipe(
        retryWhen(errors => 
          errors.pipe(
            tap(err => console.log('WebSocket error:', err)),
            delay(5000)  // Retry after 5 seconds
          )
        )
      ).subscribe(message => {
        this.messages$.next(message);
      });
    }
    
    return this.messages$.asObservable();
  }
  
  send(message: any): void {
    this.socket$?.next(message);
  }
  
  disconnect(): void {
    this.socket$?.complete();
    this.socket$ = undefined;
  }
}

// Notification Service
@Injectable({ providedIn: 'root' })
export class NotificationService {
  private notifications$ = new Subject<Notification>();
  
  constructor(private wsService: WebSocketService) {
    this.wsService.connect('wss://api.example.com/notifications')
      .subscribe(message => {
        this.notifications$.next({
          id: Date.now(),
          type: message.type,
          message: message.text,
          read: false
        });
      });
  }
  
  getNotifications(): Observable<Notification> {
    return this.notifications$.asObservable();
  }
}

// Toast Component
@Component({
  selector: 'app-toast-container',
  template: `
    <div class="toast-container">
      <div 
        *ngFor="let toast of toasts; trackBy: trackById"
        class="toast"
        [class]="toast.type"
        [@fadeInOut]
      >
        {{ toast.message }}
        <button (click)="remove(toast.id)">×</button>
      </div>
    </div>
  `,
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-20px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ opacity: 0, transform: 'translateX(100%)' }))
      ])
    ])
  ]
})
export class ToastContainerComponent implements OnInit, OnDestroy {
  toasts: Toast[] = [];
  private destroy$ = new Subject<void>();
  
  constructor(private notificationService: NotificationService) { }
  
  ngOnInit(): void {
    this.notificationService.getNotifications()
      .pipe(takeUntil(this.destroy$))
      .subscribe(notification => {
        this.addToast(notification);
      });
  }
  
  addToast(notification: Notification): void {
    const toast: Toast = {
      id: notification.id,
      message: notification.message,
      type: notification.type
    };
    
    this.toasts.push(toast);
    
    // Auto-remove after 5 seconds
    setTimeout(() => this.remove(toast.id), 5000);
  }
  
  remove(id: number): void {
    this.toasts = this.toasts.filter(t => t.id !== id);
  }
  
  trackById(index: number, toast: Toast): number {
    return toast.id;
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

---

## Scenario 9: Error Handling Strategy

### Question:
Implement a global error handling strategy that catches HTTP errors, shows user-friendly messages, and logs errors for debugging.

### Answer:
```typescript
// Global Error Handler
@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  constructor(
    private injector: Injector,
    private zone: NgZone
  ) { }
  
  handleError(error: any): void {
    const notificationService = this.injector.get(NotificationService);
    const loggingService = this.injector.get(LoggingService);
    
    let message = 'An unexpected error occurred';
    let stack = '';
    
    if (error instanceof HttpErrorResponse) {
      message = this.getHttpErrorMessage(error);
    } else if (error instanceof Error) {
      message = error.message;
      stack = error.stack || '';
    }
    
    // Log error
    loggingService.logError({
      message,
      stack,
      timestamp: new Date(),
      url: window.location.href
    });
    
    // Show notification in Angular zone
    this.zone.run(() => {
      notificationService.showError(message);
    });
    
    console.error('Global error:', error);
  }
  
  private getHttpErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400: return 'Bad request. Please check your input.';
      case 401: return 'Please log in to continue.';
      case 403: return 'You don\'t have permission to access this resource.';
      case 404: return 'Resource not found.';
      case 500: return 'Server error. Please try again later.';
      default: return 'An error occurred. Please try again.';
    }
  }
}

// Provide in AppModule
@NgModule({
  providers: [
    { provide: ErrorHandler, useClass: GlobalErrorHandler }
  ]
})
export class AppModule { }

// HTTP Error Interceptor
@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      retry({
        count: 2,
        delay: (error, retryCount) => {
          if (error.status === 0 || error.status >= 500) {
            return timer(1000 * retryCount);  // Retry server errors
          }
          throw error;  // Don't retry client errors
        }
      }),
      catchError((error: HttpErrorResponse) => {
        // Transform error for better handling
        return throwError(() => ({
          status: error.status,
          message: error.message,
          originalError: error
        }));
      })
    );
  }
}
```

---

## Scenario 10: Implementing Infinite Scroll

### Question:
Implement infinite scroll for a list that loads more data when the user scrolls near the bottom.

### Answer:
```typescript
@Component({
  selector: 'app-infinite-list',
  template: `
    <div class="list-container" #scrollContainer>
      <div *ngFor="let item of items; trackBy: trackById" class="item">
        {{ item.name }}
      </div>
      
      <div *ngIf="loading" class="loading">
        Loading more...
      </div>
      
      <div *ngIf="!hasMore && items.length > 0" class="end">
        No more items
      </div>
    </div>
  `
})
export class InfiniteListComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('scrollContainer') scrollContainer!: ElementRef;
  
  items: Item[] = [];
  loading = false;
  hasMore = true;
  private page = 1;
  private pageSize = 20;
  private destroy$ = new Subject<void>();
  
  constructor(private itemService: ItemService) { }
  
  ngOnInit(): void {
    this.loadItems();
  }
  
  ngAfterViewInit(): void {
    // Create scroll observable
    fromEvent(this.scrollContainer.nativeElement, 'scroll')
      .pipe(
        takeUntil(this.destroy$),
        debounceTime(200),
        filter(() => !this.loading && this.hasMore),
        filter(() => this.isNearBottom())
      )
      .subscribe(() => {
        this.loadItems();
      });
  }
  
  private isNearBottom(): boolean {
    const container = this.scrollContainer.nativeElement;
    const threshold = 100;  // pixels from bottom
    return container.scrollHeight - container.scrollTop - container.clientHeight < threshold;
  }
  
  private loadItems(): void {
    this.loading = true;
    
    this.itemService.getItems(this.page, this.pageSize)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (response) => {
          this.items = [...this.items, ...response.items];
          this.hasMore = response.hasMore;
          this.page++;
        },
        error: (err) => {
          console.error('Failed to load items:', err);
        }
      });
  }
  
  trackById(index: number, item: Item): number {
    return item.id;
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

// Alternative using Intersection Observer (better performance)
@Directive({
  selector: '[appInfiniteScroll]'
})
export class InfiniteScrollDirective implements OnInit, OnDestroy {
  @Output() scrolled = new EventEmitter<void>();
  
  private observer!: IntersectionObserver;
  
  constructor(private el: ElementRef) { }
  
  ngOnInit(): void {
    this.observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          this.scrolled.emit();
        }
      },
      { threshold: 0.1 }
    );
    
    this.observer.observe(this.el.nativeElement);
  }
  
  ngOnDestroy(): void {
    this.observer.disconnect();
  }
}

// Usage
// <div class="sentinel" appInfiniteScroll (scrolled)="loadMore()"></div>
```

---
