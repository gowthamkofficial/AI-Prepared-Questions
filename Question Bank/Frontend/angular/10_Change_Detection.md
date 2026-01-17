# CHANGE DETECTION ANSWERS

---

## 74. What is Change Detection?

### Answer:
- **Change Detection** is Angular's mechanism to **sync model and view**
- Detects when data changes and **updates the DOM**
- Runs from **root component down** the component tree
- Triggered by: events, HTTP responses, timers, Promises

### Theoretical Keywords:
**Model-View sync**, **DOM update**, **Component tree traversal**,  
**Zone.js**, **Trigger events**, **Performance**, **Dirty checking**

### Example:
```typescript
@Component({
  selector: 'app-counter',
  template: `
    <p>Count: {{ count }}</p>
    <button (click)="increment()">+1</button>
  `
})
export class CounterComponent {
  count = 0;
  
  increment(): void {
    this.count++;  // Change detection runs after this
    // Angular detects 'count' changed and updates DOM
  }
}

// What triggers change detection:
// 1. User events (click, input, submit)
// 2. HTTP requests completing
// 3. setTimeout/setInterval
// 4. Promise resolution
// 5. Any async operation
```

---

## 75. What are the Change Detection strategies?

### Answer:
Two strategies:
- **Default**: Check all components on every cycle
- **OnPush**: Check only when specific conditions met

### Theoretical Keywords:
**Default strategy**, **OnPush strategy**, **Performance optimization**,  
**Immutable data**, **Observable inputs**, **Reference check**

### Example:
```typescript
// Default Strategy (checks every cycle)
@Component({
  selector: 'app-default',
  changeDetection: ChangeDetectionStrategy.Default,
  template: `{{ data.name }}`
})
export class DefaultComponent {
  @Input() data!: { name: string };
}

// OnPush Strategy (checks only when needed)
@Component({
  selector: 'app-onpush',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `{{ data.name }}`
})
export class OnPushComponent {
  @Input() data!: { name: string };
}

// Parent component
@Component({
  template: `
    <app-default [data]="user"></app-default>
    <app-onpush [data]="user"></app-onpush>
    <button (click)="mutate()">Mutate</button>
    <button (click)="replace()">Replace</button>
  `
})
export class ParentComponent {
  user = { name: 'John' };
  
  mutate(): void {
    // Mutation - Default updates, OnPush does NOT
    this.user.name = 'Jane';
  }
  
  replace(): void {
    // New reference - Both update
    this.user = { name: 'Jane' };
  }
}
```

---

## 76. What is OnPush Change Detection?

### Answer:
**OnPush** only triggers change detection when:
1. **@Input reference** changes (not mutation)
2. **Event** originates from component or children
3. **Async pipe** receives new value
4. **Manually triggered** via ChangeDetectorRef

### Example:
```typescript
@Component({
  selector: 'app-user-card',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div>{{ user.name }}</div>
    <div>{{ data$ | async }}</div>
    <button (click)="onClick()">Click</button>
  `
})
export class UserCardComponent {
  @Input() user!: User;
  
  data$ = this.dataService.getData();
  
  constructor(
    private dataService: DataService,
    private cdr: ChangeDetectorRef
  ) { }
  
  onClick(): void {
    // Event from this component - triggers change detection
  }
  
  // Manual trigger when needed
  forceUpdate(): void {
    this.cdr.markForCheck();  // Mark this and ancestors
    // or
    this.cdr.detectChanges();  // Run immediately on this component
  }
}

// Best practices with OnPush
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <!-- Use async pipe for observables -->
    <div *ngFor="let item of items$ | async">{{ item.name }}</div>
    
    <!-- Pass new object references, don't mutate -->
    <app-child [data]="immutableData"></app-child>
  `
})
export class OnPushBestPractices {
  items$: Observable<Item[]>;
  
  // GOOD: Create new array
  addItem(item: Item): void {
    this.items = [...this.items, item];
  }
  
  // BAD: Mutation won't trigger update
  badAddItem(item: Item): void {
    this.items.push(item);  // Won't work with OnPush!
  }
}
```

---

## 77. How to manually trigger Change Detection?

### Answer:
Use **ChangeDetectorRef** methods:
- `markForCheck()`: Mark component and ancestors for check
- `detectChanges()`: Run change detection immediately
- `detach()`: Detach from change detection tree
- `reattach()`: Reattach to change detection tree

### Example:
```typescript
@Component({
  selector: 'app-manual-cd',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `<p>{{ value }}</p>`
})
export class ManualCDComponent implements OnInit {
  value = 'initial';
  
  constructor(
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) { }
  
  ngOnInit(): void {
    // External update (WebSocket, etc.)
    externalLibrary.onUpdate((newValue) => {
      this.value = newValue;
      this.cdr.markForCheck();  // Tell Angular to check
    });
  }
  
  // markForCheck vs detectChanges
  usingMarkForCheck(): void {
    this.value = 'new value';
    this.cdr.markForCheck();
    // Marks for next change detection cycle
    // Angular will check on next tick
  }
  
  usingDetectChanges(): void {
    this.value = 'new value';
    this.cdr.detectChanges();
    // Runs change detection immediately
    // Only on this component and children
  }
  
  // Detach/Reattach for performance
  heavyProcessing(): void {
    this.cdr.detach();  // Stop automatic checks
    
    for (let i = 0; i < 10000; i++) {
      this.processItem(i);
    }
    
    this.cdr.reattach();  // Resume checks
    this.cdr.detectChanges();  // Update view
  }
  
  // Running outside Angular zone
  outsideAngular(): void {
    this.zone.runOutsideAngular(() => {
      // This won't trigger change detection
      setInterval(() => {
        this.someInternalUpdate();
      }, 100);
    });
    
    // When you need to update view
    this.zone.run(() => {
      this.cdr.detectChanges();
    });
  }
}

// Real-world example: Real-time chart
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RealtimeChartComponent implements OnInit, OnDestroy {
  data: number[] = [];
  private subscription!: Subscription;
  
  constructor(
    private cdr: ChangeDetectorRef,
    private websocket: WebSocketService
  ) { }
  
  ngOnInit(): void {
    // Batch updates for performance
    this.subscription = this.websocket.dataStream$
      .pipe(
        bufferTime(100)  // Batch updates every 100ms
      )
      .subscribe(batch => {
        if (batch.length > 0) {
          this.data = [...this.data, ...batch].slice(-100);
          this.cdr.markForCheck();
        }
      });
  }
  
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
```

---

## Zone.js and Change Detection

### Answer:
- **Zone.js** patches async APIs to notify Angular
- Triggers change detection after async operations
- Can be bypassed for performance optimization

### Example:
```typescript
@Component({...})
export class ZoneExampleComponent {
  constructor(private zone: NgZone) { }
  
  // Inside Angular zone (triggers CD)
  insideZone(): void {
    setTimeout(() => {
      this.value = 'updated';  // CD runs automatically
    }, 1000);
  }
  
  // Outside Angular zone (no automatic CD)
  outsideZone(): void {
    this.zone.runOutsideAngular(() => {
      // Good for performance-critical operations
      setInterval(() => {
        this.internalCounter++;  // No CD triggered
        
        // Only update view when needed
        if (this.internalCounter % 100 === 0) {
          this.zone.run(() => {
            this.displayCounter = this.internalCounter;
          });
        }
      }, 10);
    });
  }
  
  // Check if inside Angular zone
  checkZone(): void {
    console.log('In Angular Zone:', NgZone.isInAngularZone());
  }
}

// Zoneless Angular (experimental in v18)
// bootstrapApplication(AppComponent, {
//   providers: [
//     provideExperimentalZonelessChangeDetection()
//   ]
// });
```

---

## Change Detection Performance Tips

### Answer:
1. Use **OnPush** strategy
2. Use **async pipe** with observables
3. **Immutable** data patterns
4. **trackBy** with ngFor
5. Avoid complex expressions in templates
6. Use **pure pipes** for transformations

### Example:
```typescript
// 1. OnPush + Immutable
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OptimizedListComponent {
  @Input() items!: Item[];
  
  // 2. trackBy for ngFor
  trackById(index: number, item: Item): number {
    return item.id;
  }
}

// Template
// <div *ngFor="let item of items; trackBy: trackById">
//   {{ item.name }}
// </div>

// 3. Avoid this in templates
// BAD: Called every CD cycle
// <div>{{ getFullName() }}</div>

// GOOD: Use computed property or pipe
// <div>{{ fullName }}</div>
// <div>{{ user | fullName }}</div>

// 4. Pure pipe for transformations
@Pipe({ name: 'fullName', pure: true })
export class FullNamePipe implements PipeTransform {
  transform(user: User): string {
    return `${user.firstName} ${user.lastName}`;
  }
}
```

---
