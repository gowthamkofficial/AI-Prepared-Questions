# FREQUENTLY ASKED AND SCENARIO QUESTIONS ANSWERS

---

## 91. What is AOT vs JIT compilation?

### Answer:

| Feature | AOT (Ahead-of-Time) | JIT (Just-in-Time) |
|---------|---------------------|-------------------|
| **When** | Build time | Runtime (browser) |
| **Bundle size** | Smaller | Larger (includes compiler) |
| **Startup** | Faster | Slower |
| **Error detection** | Build time | Runtime |
| **Default** | Production | Development |

### Example:
```bash
# JIT compilation (development)
ng serve

# AOT compilation (production - default)
ng build --configuration=production

# AOT in development
ng serve --aot
```

```typescript
// AOT catches template errors at build time
@Component({
  template: `{{ user.nmae }}`  // Typo: 'nmae' instead of 'name'
})
// AOT: Build error - Property 'nmae' does not exist
// JIT: Runtime error in browser
```

---

## 92. What are HTTP Interceptors?

### Answer:
- **Interceptors** intercept HTTP requests/responses
- Used for: Auth tokens, logging, error handling, caching
- Implement `HttpInterceptor` interface

### Example:
```typescript
// Auth Interceptor
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    
    if (token) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(authReq);
    }
    
    return next.handle(req);
  }
}

// Error Interceptor
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router) { }
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.router.navigate(['/login']);
        }
        if (error.status === 403) {
          this.router.navigate(['/forbidden']);
        }
        return throwError(() => error);
      })
    );
  }
}

// Loading Interceptor
@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  constructor(private loadingService: LoadingService) { }
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.loadingService.show();
    
    return next.handle(req).pipe(
      finalize(() => this.loadingService.hide())
    );
  }
}

// Register interceptors
@NgModule({
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true }
  ]
})
export class CoreModule { }

// Functional interceptor (Angular 15+)
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(AuthService).getToken();
  
  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
  
  return next(req);
};

// Provide functional interceptor
bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor]))
  ]
});
```

---

## 93. What is View Encapsulation?

### Answer:
- Controls how styles are **scoped** to component
- Three modes: **Emulated**, **None**, **ShadowDom**

| Mode | Behavior |
|------|----------|
| **Emulated** | Scoped via attributes (default) |
| **None** | Global styles |
| **ShadowDom** | Native Shadow DOM |

### Example:
```typescript
// Emulated (default) - Styles scoped to component
@Component({
  selector: 'app-card',
  encapsulation: ViewEncapsulation.Emulated,
  styles: [`
    .title { color: blue; }  /* Only affects this component */
  `]
})
export class CardComponent { }

// None - Styles are global
@Component({
  selector: 'app-global',
  encapsulation: ViewEncapsulation.None,
  styles: [`
    .title { color: red; }  /* Affects entire application */
  `]
})
export class GlobalComponent { }

// ShadowDom - Native shadow DOM encapsulation
@Component({
  selector: 'app-shadow',
  encapsulation: ViewEncapsulation.ShadowDom,
  styles: [`
    .title { color: green; }  /* True isolation */
  `]
})
export class ShadowComponent { }

// Penetrating encapsulation with ::ng-deep
@Component({
  styles: [`
    :host ::ng-deep .child-style {
      color: purple;  /* Affects child components */
    }
  `]
})
export class ParentComponent { }
```

---

## 94. What is Content Projection?

### Answer:
- Pass content from parent to child component
- Uses `<ng-content>` tag
- Supports **single slot**, **multi-slot**, **conditional**

### Example:
```typescript
// Single slot projection
@Component({
  selector: 'app-card',
  template: `
    <div class="card">
      <ng-content></ng-content>
    </div>
  `
})
export class CardComponent { }

// Usage
// <app-card>
//   <h1>Title</h1>
//   <p>Content goes here</p>
// </app-card>

// Multi-slot projection
@Component({
  selector: 'app-modal',
  template: `
    <div class="modal">
      <header>
        <ng-content select="[modal-header]"></ng-content>
      </header>
      <main>
        <ng-content select="[modal-body]"></ng-content>
      </main>
      <footer>
        <ng-content select="[modal-footer]"></ng-content>
      </footer>
    </div>
  `
})
export class ModalComponent { }

// Usage
// <app-modal>
//   <h2 modal-header>Title</h2>
//   <div modal-body>Content</div>
//   <button modal-footer>Close</button>
// </app-modal>

// Conditional projection with ngProjectAs
// <app-card>
//   <ng-container ngProjectAs="[modal-header]">
//     <h2 *ngIf="showHeader">Dynamic Header</h2>
//   </ng-container>
// </app-card>
```

---

## 95. What is ng-template?

### Answer:
- **ng-template** defines template that isn't rendered directly
- Used with **structural directives**, **ngTemplateOutlet**
- For **conditional rendering**, **reusable templates**

### Example:
```typescript
@Component({
  template: `
    <!-- Basic ng-template -->
    <ng-template #loading>
      <div class="spinner">Loading...</div>
    </ng-template>
    
    <!-- Usage with *ngIf -->
    <div *ngIf="data; else loading">
      {{ data | json }}
    </div>
    
    <!-- ngTemplateOutlet for reuse -->
    <ng-container *ngTemplateOutlet="loading"></ng-container>
    
    <!-- Template with context -->
    <ng-template #userCard let-user let-index="index">
      <div class="card">
        {{ index + 1 }}. {{ user.name }}
      </div>
    </ng-template>
    
    <ng-container 
      *ngTemplateOutlet="userCard; context: { $implicit: currentUser, index: 0 }">
    </ng-container>
    
    <!-- Passing template to child -->
    <app-list [itemTemplate]="userCard" [items]="users"></app-list>
  `
})
export class TemplateExampleComponent {
  data: any;
  currentUser = { name: 'John' };
  users = [{ name: 'John' }, { name: 'Jane' }];
}

// Child component receiving template
@Component({
  selector: 'app-list',
  template: `
    <div *ngFor="let item of items; let i = index">
      <ng-container *ngTemplateOutlet="itemTemplate; context: { $implicit: item, index: i }">
      </ng-container>
    </div>
  `
})
export class ListComponent {
  @Input() items: any[] = [];
  @Input() itemTemplate!: TemplateRef<any>;
}
```

---

## 96. How to communicate between components?

### Answer:

| Method | Use Case |
|--------|----------|
| **@Input/@Output** | Parent ↔ Child |
| **Service** | Any components |
| **ViewChild** | Parent → Child |
| **ContentChild** | Projected content |
| **Router** | Navigation data |

### Example:
```typescript
// 1. @Input/@Output (Parent ↔ Child)
@Component({
  selector: 'app-child',
  template: `<button (click)="sendMessage()">Send</button>`
})
export class ChildComponent {
  @Input() data!: string;
  @Output() message = new EventEmitter<string>();
  
  sendMessage(): void {
    this.message.emit('Hello from child');
  }
}

// Parent
// <app-child [data]="parentData" (message)="onMessage($event)"></app-child>

// 2. Service (Any components)
@Injectable({ providedIn: 'root' })
export class CommunicationService {
  private messageSubject = new Subject<string>();
  message$ = this.messageSubject.asObservable();
  
  sendMessage(message: string): void {
    this.messageSubject.next(message);
  }
}

// Component A (sender)
this.communicationService.sendMessage('Hello');

// Component B (receiver)
this.communicationService.message$.subscribe(msg => console.log(msg));

// 3. @ViewChild (Parent → Child)
@Component({
  template: `<app-child #child></app-child>`
})
export class ParentComponent implements AfterViewInit {
  @ViewChild('child') childComponent!: ChildComponent;
  
  ngAfterViewInit(): void {
    this.childComponent.someMethod();
  }
}
```

---

## 97. What is the async pipe?

### Answer:
- **Async pipe** subscribes to Observable/Promise
- **Automatically unsubscribes** on destroy
- Returns **latest value**
- Best practice for displaying async data

### Example:
```typescript
@Component({
  template: `
    <!-- Basic usage -->
    <div *ngIf="user$ | async as user">
      Welcome, {{ user.name }}!
    </div>
    
    <!-- With loading state -->
    <ng-container *ngIf="data$ | async as data; else loading">
      <div *ngFor="let item of data">{{ item.name }}</div>
    </ng-container>
    <ng-template #loading>Loading...</ng-template>
    
    <!-- Multiple subscriptions (avoid!) -->
    <div>{{ (user$ | async)?.name }}</div>
    <div>{{ (user$ | async)?.email }}</div>
    
    <!-- Better: Single subscription -->
    <ng-container *ngIf="user$ | async as user">
      <div>{{ user.name }}</div>
      <div>{{ user.email }}</div>
    </ng-container>
    
    <!-- With error handling -->
    <ng-container *ngIf="{
      data: data$ | async,
      error: error$ | async,
      loading: loading$ | async
    } as vm">
      <div *ngIf="vm.loading">Loading...</div>
      <div *ngIf="vm.error" class="error">{{ vm.error }}</div>
      <div *ngIf="vm.data">{{ vm.data | json }}</div>
    </ng-container>
  `
})
export class AsyncPipeComponent {
  user$ = this.userService.getCurrentUser();
  data$ = this.dataService.getData();
  error$ = this.errorService.error$;
  loading$ = this.loadingService.loading$;
  
  constructor(
    private userService: UserService,
    private dataService: DataService,
    private errorService: ErrorService,
    private loadingService: LoadingService
  ) { }
}
```

---

## 98. What is trackBy in *ngFor?

### Answer:
- **trackBy** tells Angular how to **identify items**
- Prevents **unnecessary DOM re-renders**
- Improves **performance** for large lists

### Example:
```typescript
@Component({
  template: `
    <!-- Without trackBy - entire list re-renders -->
    <div *ngFor="let item of items">{{ item.name }}</div>
    
    <!-- With trackBy - only changed items re-render -->
    <div *ngFor="let item of items; trackBy: trackById">
      {{ item.name }}
    </div>
  `
})
export class ListComponent {
  items: Item[] = [];
  
  trackById(index: number, item: Item): number {
    return item.id;
  }
  
  // Alternative: track by index (less ideal)
  trackByIndex(index: number): number {
    return index;
  }
  
  // When items change
  refreshData(): void {
    this.items = this.items.map(item => ({ ...item }));
    // Without trackBy: All DOM elements recreated
    // With trackBy: Only changed elements updated
  }
}
```

---

## 99. Scenario: Parent-child data flow

### Question:
How would you pass data from parent to child and receive events back?

### Answer:
```typescript
// Parent Component
@Component({
  selector: 'app-parent',
  template: `
    <h1>Parent Component</h1>
    <p>Selected: {{ selectedUser?.name }}</p>
    
    <app-user-list 
      [users]="users"
      [selectedId]="selectedUser?.id"
      (userSelected)="onUserSelected($event)"
      (userDeleted)="onUserDeleted($event)">
    </app-user-list>
  `
})
export class ParentComponent implements OnInit {
  users: User[] = [];
  selectedUser: User | null = null;
  
  constructor(private userService: UserService) { }
  
  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }
  
  onUserSelected(user: User): void {
    this.selectedUser = user;
  }
  
  onUserDeleted(userId: number): void {
    this.users = this.users.filter(u => u.id !== userId);
    if (this.selectedUser?.id === userId) {
      this.selectedUser = null;
    }
  }
}

// Child Component
@Component({
  selector: 'app-user-list',
  template: `
    <ul>
      <li *ngFor="let user of users; trackBy: trackById"
          [class.selected]="user.id === selectedId"
          (click)="selectUser(user)">
        {{ user.name }}
        <button (click)="deleteUser(user.id, $event)">Delete</button>
      </li>
    </ul>
  `
})
export class UserListComponent {
  @Input() users: User[] = [];
  @Input() selectedId: number | null = null;
  
  @Output() userSelected = new EventEmitter<User>();
  @Output() userDeleted = new EventEmitter<number>();
  
  trackById(index: number, user: User): number {
    return user.id;
  }
  
  selectUser(user: User): void {
    this.userSelected.emit(user);
  }
  
  deleteUser(userId: number, event: Event): void {
    event.stopPropagation();
    this.userDeleted.emit(userId);
  }
}
```

---

## 100. Scenario: Service-based communication

### Question:
How would you share data between unrelated components?

### Answer:
```typescript
// Shared Service
@Injectable({ providedIn: 'root' })
export class NotificationService {
  private notificationSubject = new BehaviorSubject<Notification | null>(null);
  notification$ = this.notificationSubject.asObservable();
  
  show(message: string, type: 'success' | 'error' | 'info'): void {
    this.notificationSubject.next({ message, type, id: Date.now() });
    
    // Auto-hide after 5 seconds
    setTimeout(() => this.hide(), 5000);
  }
  
  hide(): void {
    this.notificationSubject.next(null);
  }
}

// Component A (sender - anywhere in app)
@Component({...})
export class FormComponent {
  constructor(private notificationService: NotificationService) { }
  
  onSubmit(): void {
    this.saveData().subscribe({
      next: () => this.notificationService.show('Saved!', 'success'),
      error: () => this.notificationService.show('Error saving', 'error')
    });
  }
}

// Component B (receiver - in app.component)
@Component({
  selector: 'app-notification',
  template: `
    <div *ngIf="notification$ | async as notification" 
         [class]="notification.type">
      {{ notification.message }}
      <button (click)="close()">×</button>
    </div>
  `
})
export class NotificationComponent {
  notification$ = this.notificationService.notification$;
  
  constructor(private notificationService: NotificationService) { }
  
  close(): void {
    this.notificationService.hide();
  }
}
```

---

## 101. Scenario: Dynamic component loading

### Question:
How would you load components dynamically at runtime?

### Answer:
```typescript
// Dynamic component loader
@Component({
  selector: 'app-dynamic-host',
  template: `<ng-container #container></ng-container>`
})
export class DynamicHostComponent implements OnDestroy {
  @ViewChild('container', { read: ViewContainerRef }) 
  container!: ViewContainerRef;
  
  private componentRef: ComponentRef<any> | null = null;
  
  loadComponent<T>(component: Type<T>, inputs?: Partial<T>): ComponentRef<T> {
    this.container.clear();
    this.componentRef = this.container.createComponent(component);
    
    if (inputs) {
      Object.assign(this.componentRef.instance, inputs);
    }
    
    return this.componentRef;
  }
  
  ngOnDestroy(): void {
    this.componentRef?.destroy();
  }
}

// Usage
@Component({
  template: `
    <app-dynamic-host></app-dynamic-host>
    <button (click)="loadChart()">Load Chart</button>
    <button (click)="loadTable()">Load Table</button>
  `
})
export class DashboardComponent {
  @ViewChild(DynamicHostComponent) host!: DynamicHostComponent;
  
  loadChart(): void {
    this.host.loadComponent(ChartComponent, { 
      data: this.chartData 
    });
  }
  
  loadTable(): void {
    this.host.loadComponent(TableComponent, { 
      data: this.tableData,
      columns: ['name', 'age', 'email']
    });
  }
}
```

---

## 102. Scenario: Form with dynamic fields

### Question:
How would you create a form with dynamic fields?

### Answer:
```typescript
@Component({
  selector: 'app-dynamic-form',
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <div formArrayName="fields">
        <div *ngFor="let field of fields.controls; let i = index" [formGroupName]="i">
          <input formControlName="label" placeholder="Field Label">
          <select formControlName="type">
            <option value="text">Text</option>
            <option value="number">Number</option>
            <option value="email">Email</option>
          </select>
          <input type="checkbox" formControlName="required"> Required
          <button type="button" (click)="removeField(i)">Remove</button>
        </div>
      </div>
      
      <button type="button" (click)="addField()">Add Field</button>
      <button type="submit" [disabled]="form.invalid">Submit</button>
    </form>
    
    <!-- Preview generated form -->
    <h3>Preview:</h3>
    <form [formGroup]="generatedForm">
      <div *ngFor="let field of fields.value">
        <label>{{ field.label }}</label>
        <input [type]="field.type" [formControlName]="field.label">
      </div>
    </form>
  `
})
export class DynamicFormComponent {
  form = new FormGroup({
    fields: new FormArray<FormGroup>([])
  });
  
  generatedForm = new FormGroup({});
  
  get fields(): FormArray<FormGroup> {
    return this.form.get('fields') as FormArray<FormGroup>;
  }
  
  addField(): void {
    const fieldGroup = new FormGroup({
      label: new FormControl('', Validators.required),
      type: new FormControl('text'),
      required: new FormControl(false)
    });
    this.fields.push(fieldGroup);
    this.updateGeneratedForm();
  }
  
  removeField(index: number): void {
    this.fields.removeAt(index);
    this.updateGeneratedForm();
  }
  
  updateGeneratedForm(): void {
    const newForm = new FormGroup({});
    this.fields.value.forEach(field => {
      const validators = field.required ? [Validators.required] : [];
      newForm.addControl(field.label, new FormControl('', validators));
    });
    this.generatedForm = newForm;
  }
  
  onSubmit(): void {
    console.log('Field definitions:', this.form.value);
    console.log('Generated form values:', this.generatedForm.value);
  }
}
```

---
