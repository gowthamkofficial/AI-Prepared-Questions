# LIFECYCLE HOOKS ANSWERS

---

## 70. What are lifecycle hooks?

### Answer:
- **Lifecycle hooks** are methods that Angular calls at specific moments
- Allow you to **tap into** component/directive lifecycle
- From **creation** to **destruction**
- Execute code at precise moments

### Theoretical Keywords:
**Lifecycle events**, **Component creation**, **Initialization**, **Change detection**,  
**Destruction**, **Hook methods**, **Order of execution**

### Example:
```typescript
@Component({
  selector: 'app-lifecycle-demo',
  template: '<p>{{ data }}</p>'
})
export class LifecycleDemoComponent implements 
  OnChanges, OnInit, DoCheck, 
  AfterContentInit, AfterContentChecked,
  AfterViewInit, AfterViewChecked, OnDestroy {
  
  @Input() data!: string;
  
  constructor() {
    console.log('1. Constructor');
    // Don't use for initialization logic
    // DI is available, but inputs are NOT set yet
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    console.log('2. ngOnChanges', changes);
    // Called when @Input properties change
    // First call: before ngOnInit
  }
  
  ngOnInit(): void {
    console.log('3. ngOnInit');
    // Initialize component
    // @Input values are available
    // Good place for initial data fetch
  }
  
  ngDoCheck(): void {
    console.log('4. ngDoCheck');
    // Custom change detection
    // Called on EVERY change detection cycle
    // Expensive - use carefully
  }
  
  ngAfterContentInit(): void {
    console.log('5. ngAfterContentInit');
    // After <ng-content> is projected
    // @ContentChild/@ContentChildren available
  }
  
  ngAfterContentChecked(): void {
    console.log('6. ngAfterContentChecked');
    // After projected content is checked
  }
  
  ngAfterViewInit(): void {
    console.log('7. ngAfterViewInit');
    // After view (and child views) initialized
    // @ViewChild/@ViewChildren available
  }
  
  ngAfterViewChecked(): void {
    console.log('8. ngAfterViewChecked');
    // After view checked
  }
  
  ngOnDestroy(): void {
    console.log('9. ngOnDestroy');
    // Cleanup before component is destroyed
    // Unsubscribe, clear timers, etc.
  }
}
```

---

## 71. What is `ngOnInit` and why use it?

### Answer:
- **ngOnInit** is called once after first `ngOnChanges`
- Used for **component initialization**
- **@Input** values are available (unlike constructor)
- Perfect for **API calls**, **data setup**

### Theoretical Keywords:
**Initialization**, **Input available**, **Single execution**,  
**Data fetching**, **Setup logic**, **After constructor**

### Example:
```typescript
@Component({
  selector: 'app-user-profile'
})
export class UserProfileComponent implements OnInit {
  @Input() userId!: number;
  user: User | null = null;
  
  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) {
    // Constructor: Only for DI
    // this.userId is undefined here!
    console.log('Constructor - userId:', this.userId); // undefined
  }
  
  ngOnInit(): void {
    // ngOnInit: @Input is available
    console.log('ngOnInit - userId:', this.userId); // actual value
    
    // Fetch data
    this.loadUser();
    
    // Subscribe to route params
    this.route.params.subscribe(params => {
      this.loadUser(params['id']);
    });
  }
  
  private loadUser(id?: number): void {
    const userId = id || this.userId;
    this.userService.getUser(userId).subscribe(user => {
      this.user = user;
    });
  }
}
```

---

## 72. What is the difference between `ngOnInit` and `constructor`?

### Answer:

| Feature | Constructor | ngOnInit |
|---------|-------------|----------|
| **Purpose** | Dependency Injection | Initialization logic |
| **@Input** | Not available | Available |
| **Called by** | JavaScript engine | Angular |
| **Timing** | Object creation | After first ngOnChanges |
| **Use for** | DI only | Setup, API calls |

### Example:
```typescript
@Component({
  selector: 'app-comparison'
})
export class ComparisonComponent implements OnInit {
  @Input() config!: Config;
  
  // WRONG: Don't do initialization in constructor
  constructor(private service: DataService) {
    // ❌ this.config is undefined
    // ❌ this.service.loadData(this.config); // Error!
  }
  
  // CORRECT: Do initialization in ngOnInit
  ngOnInit(): void {
    // ✅ this.config is available
    this.service.loadData(this.config);
  }
}

// Best practice pattern
@Component({
  selector: 'app-best-practice'
})
export class BestPracticeComponent implements OnInit, OnDestroy {
  @Input() userId!: number;
  
  private destroy$ = new Subject<void>();
  user$!: Observable<User>;
  
  constructor(
    private userService: UserService,  // Only DI
    private router: Router
  ) { }
  
  ngOnInit(): void {
    // All initialization here
    this.user$ = this.userService.getUser(this.userId);
    
    // Subscriptions with cleanup
    this.userService.notifications$
      .pipe(takeUntil(this.destroy$))
      .subscribe(/* ... */);
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

---

## 73. What is `ngOnDestroy` and why is it important?

### Answer:
- **ngOnDestroy** is called just before component is destroyed
- Used for **cleanup** to prevent memory leaks
- Unsubscribe from observables, clear timers, detach event listeners

### Theoretical Keywords:
**Cleanup**, **Memory leaks**, **Unsubscribe**, **Clear timers**,  
**Event listeners**, **Resource release**, **Garbage collection**

### Example:
```typescript
@Component({
  selector: 'app-cleanup-demo'
})
export class CleanupDemoComponent implements OnInit, OnDestroy {
  private subscription!: Subscription;
  private intervalId!: any;
  private destroy$ = new Subject<void>();
  
  constructor(
    private dataService: DataService,
    private elementRef: ElementRef
  ) { }
  
  ngOnInit(): void {
    // Manual subscription (needs cleanup)
    this.subscription = this.dataService.getData().subscribe();
    
    // Interval (needs cleanup)
    this.intervalId = setInterval(() => {
      console.log('Tick');
    }, 1000);
    
    // Using takeUntil pattern (automatic cleanup)
    this.dataService.stream$
      .pipe(takeUntil(this.destroy$))
      .subscribe(data => console.log(data));
    
    // Event listener (needs cleanup)
    this.elementRef.nativeElement.addEventListener('click', this.handleClick);
  }
  
  handleClick = () => {
    console.log('Clicked');
  };
  
  ngOnDestroy(): void {
    // 1. Unsubscribe manual subscriptions
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    
    // 2. Clear intervals/timeouts
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    
    // 3. Complete subject for takeUntil pattern
    this.destroy$.next();
    this.destroy$.complete();
    
    // 4. Remove event listeners
    this.elementRef.nativeElement.removeEventListener('click', this.handleClick);
    
    console.log('Component destroyed, resources cleaned up');
  }
}

// Using DestroyRef (Angular 16+)
@Component({
  selector: 'app-modern-cleanup'
})
export class ModernCleanupComponent {
  constructor(private destroyRef: DestroyRef) {
    // Automatic cleanup on destroy
    this.someObservable$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe();
  }
}

// Or with inject
@Component({})
export class InjectCleanupComponent {
  private destroyRef = inject(DestroyRef);
  
  ngOnInit(): void {
    someObservable$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe();
  }
}
```

---

## Lifecycle Hooks Order

### Answer:
```
1. constructor
2. ngOnChanges (if inputs exist)
3. ngOnInit
4. ngDoCheck
5. ngAfterContentInit
6. ngAfterContentChecked
7. ngAfterViewInit
8. ngAfterViewChecked

On subsequent changes:
- ngOnChanges (if inputs change)
- ngDoCheck
- ngAfterContentChecked
- ngAfterViewChecked

On destroy:
- ngOnDestroy
```

### Visual Timeline:
```
CREATION
   │
   ▼
constructor ──────────────────────────────────► DI only
   │
   ▼
ngOnChanges ──────────────────────────────────► @Input changed
   │
   ▼
ngOnInit ─────────────────────────────────────► Initialize
   │
   ▼
ngDoCheck ────────────────────────────────────► Custom change detection
   │
   ▼
ngAfterContentInit ───────────────────────────► <ng-content> ready
   │
   ▼
ngAfterContentChecked ────────────────────────► Content checked
   │
   ▼
ngAfterViewInit ──────────────────────────────► View ready
   │
   ▼
ngAfterViewChecked ───────────────────────────► View checked
   │
   ▼
(change detection cycles repeat above checks)
   │
   ▼
ngOnDestroy ──────────────────────────────────► Cleanup
```

---

## ngOnChanges Deep Dive

### Answer:
- Called whenever **@Input** property changes
- Receives **SimpleChanges** object with old/new values
- First call happens **before** ngOnInit

### Example:
```typescript
@Component({
  selector: 'app-user-card',
  template: `<div>{{ user?.name }}</div>`
})
export class UserCardComponent implements OnChanges, OnInit {
  @Input() user!: User;
  @Input() highlight = false;
  
  ngOnChanges(changes: SimpleChanges): void {
    console.log('Changes:', changes);
    
    // Check specific property
    if (changes['user']) {
      const userChange = changes['user'];
      console.log('Previous user:', userChange.previousValue);
      console.log('Current user:', userChange.currentValue);
      console.log('First change:', userChange.firstChange);
      
      if (!userChange.firstChange) {
        // React to user change (not initial)
        this.onUserChanged(userChange.currentValue);
      }
    }
    
    if (changes['highlight']) {
      this.updateHighlightStyle();
    }
  }
  
  ngOnInit(): void {
    // Called AFTER first ngOnChanges
    // user is already available here
  }
  
  private onUserChanged(newUser: User): void {
    // Handle user change
  }
  
  private updateHighlightStyle(): void {
    // Update styles
  }
}

// SimpleChanges structure
interface SimpleChanges {
  [propName: string]: SimpleChange;
}

interface SimpleChange {
  previousValue: any;
  currentValue: any;
  firstChange: boolean;
  isFirstChange(): boolean;
}
```

---

## AfterViewInit vs AfterContentInit

### Answer:

| Hook | When | Access |
|------|------|--------|
| **AfterContentInit** | After projected content | @ContentChild, @ContentChildren |
| **AfterViewInit** | After own view + children | @ViewChild, @ViewChildren |

### Example:
```typescript
@Component({
  selector: 'app-tabs',
  template: `
    <!-- Projected content -->
    <ng-content></ng-content>
    
    <!-- Own view -->
    <div #tabContainer>
      <button *ngFor="let tab of tabs">{{ tab.label }}</button>
    </div>
  `
})
export class TabsComponent implements AfterContentInit, AfterViewInit {
  // From projected content (<ng-content>)
  @ContentChildren(TabComponent) projectedTabs!: QueryList<TabComponent>;
  
  // From own template
  @ViewChild('tabContainer') container!: ElementRef;
  
  tabs: TabComponent[] = [];
  
  ngAfterContentInit(): void {
    // Projected content is ready
    // projectedTabs QueryList is populated
    console.log('Projected tabs:', this.projectedTabs.length);
    this.tabs = this.projectedTabs.toArray();
    
    // Listen for changes
    this.projectedTabs.changes.subscribe(() => {
      this.tabs = this.projectedTabs.toArray();
    });
  }
  
  ngAfterViewInit(): void {
    // Own view is ready
    // ViewChild references are available
    console.log('Container:', this.container.nativeElement);
    
    // Safe to manipulate DOM here
    this.container.nativeElement.classList.add('initialized');
  }
}

// Usage
@Component({
  template: `
    <app-tabs>
      <app-tab label="Tab 1">Content 1</app-tab>
      <app-tab label="Tab 2">Content 2</app-tab>
    </app-tabs>
  `
})
export class AppComponent { }
```

---
