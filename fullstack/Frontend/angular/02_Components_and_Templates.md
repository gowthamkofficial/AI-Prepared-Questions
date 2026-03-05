# COMPONENTS AND TEMPLATES ANSWERS

---

## 12. What is a component in Angular?

### Answer:
- A **component** is a **TypeScript class** decorated with `@Component`
- Controls a **patch of screen** called a view
- Consists of three parts:
  1. **Class** (.ts): Logic and data
  2. **Template** (.html): View/UI
  3. **Styles** (.css/.scss): Component-specific styles
- Components are **reusable** and **composable**
- Has its own **lifecycle** managed by Angular

### Theoretical Keywords:
**@Component decorator**, **View controller**, **Class + Template + Styles**,  
**Encapsulation**, **Reusability**, **Lifecycle hooks**

### Example:
```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-card',  // HTML tag name
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss'],
  // Or inline:
  // template: `<div>{{ product.name }}</div>`,
  // styles: [`.card { padding: 10px; }`]
})
export class ProductCardComponent implements OnInit {
  // Properties (data)
  product: Product = {
    id: 1,
    name: 'Laptop',
    price: 999.99
  };
  
  isExpanded = false;
  
  // Lifecycle hook
  ngOnInit(): void {
    console.log('Component initialized');
  }
  
  // Methods (behavior)
  toggleExpand(): void {
    this.isExpanded = !this.isExpanded;
  }
  
  addToCart(): void {
    console.log('Added to cart:', this.product);
  }
}
```

---

## 13. Difference between component and directive

### Answer:

| Feature | Component | Directive |
|---------|-----------|-----------|
| **Decorator** | `@Component` | `@Directive` |
| **Template** | Required (has view) | No template |
| **Purpose** | Build UI blocks | Modify existing elements |
| **Selector** | Element selector | Attribute/class selector |
| **Usage** | `<app-header>` | `<div appHighlight>` |
| **Shadow DOM** | Optional encapsulation | No encapsulation |

### Theoretical Keywords:
**Template presence**, **UI building vs DOM modification**,  
**Element vs Attribute selector**, **View encapsulation**

### Example:
```typescript
// COMPONENT - Has its own view
@Component({
  selector: 'app-button',
  template: `
    <button [class]="buttonClass" (click)="onClick()">
      <ng-content></ng-content>
    </button>
  `,
  styles: [`button { padding: 10px 20px; }`]
})
export class ButtonComponent {
  @Input() buttonClass = 'primary';
  @Output() buttonClick = new EventEmitter<void>();
  
  onClick(): void {
    this.buttonClick.emit();
  }
}

// DIRECTIVE - Modifies existing elements
@Directive({
  selector: '[appTooltip]'
})
export class TooltipDirective {
  @Input() appTooltip = '';
  
  constructor(private el: ElementRef) {}
  
  @HostListener('mouseenter')
  showTooltip(): void {
    // Add tooltip to existing element
  }
  
  @HostListener('mouseleave')
  hideTooltip(): void {
    // Remove tooltip
  }
}

// Usage:
// <app-button>Click Me</app-button>  <!-- Component -->
// <span appTooltip="Help text">Hover me</span>  <!-- Directive -->
```

---

## 14. How to pass data from parent to child component?

### Answer:
- Use **`@Input()` decorator** in child component
- Parent passes data through **property binding**
- Data flows **one-way**: Parent → Child
- Can use **setter** or **ngOnChanges** to react to input changes
- Supports any data type: primitives, objects, arrays

### Theoretical Keywords:
**@Input decorator**, **Property binding**, **One-way data flow**,  
**Parent-child communication**, **Input setter**, **ngOnChanges**

### Example:
```typescript
// child.component.ts
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-child',
  template: `
    <div class="child">
      <h3>{{ title }}</h3>
      <p>User: {{ user?.name }}</p>
      <ul>
        <li *ngFor="let item of items">{{ item }}</li>
      </ul>
    </div>
  `
})
export class ChildComponent implements OnChanges {
  // Simple input
  @Input() title: string = '';
  
  // Object input
  @Input() user: User | null = null;
  
  // Array input
  @Input() items: string[] = [];
  
  // Input with setter (react to changes)
  private _count = 0;
  @Input()
  set count(value: number) {
    this._count = value;
    console.log('Count changed to:', value);
  }
  get count(): number {
    return this._count;
  }
  
  // React to changes using lifecycle hook
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['user']) {
      console.log('User changed:', changes['user'].currentValue);
    }
  }
}

// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <app-child
      [title]="pageTitle"
      [user]="currentUser"
      [items]="menuItems"
      [count]="counter">
    </app-child>
    
    <button (click)="counter = counter + 1">Increment</button>
  `
})
export class ParentComponent {
  pageTitle = 'Welcome Page';
  currentUser = { id: 1, name: 'John' };
  menuItems = ['Home', 'About', 'Contact'];
  counter = 0;
}
```

---

## 15. How to pass data from child to parent component?

### Answer:
- Use **`@Output()` decorator** with **EventEmitter**
- Child **emits events** that parent listens to
- Parent uses **event binding** to receive data
- Data flows: **Child → Parent**
- Can pass any data type with the event

### Theoretical Keywords:
**@Output decorator**, **EventEmitter**, **Event binding**,  
**Child-parent communication**, **Custom events**, **emit()**

### Example:
```typescript
// child.component.ts
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-child',
  template: `
    <div class="child">
      <input [(ngModel)]="inputValue" placeholder="Enter text">
      <button (click)="sendData()">Send to Parent</button>
      <button (click)="notifyParent()">Notify</button>
      
      <div class="item-list">
        <div *ngFor="let item of items" (click)="selectItem(item)">
          {{ item.name }}
        </div>
      </div>
    </div>
  `
})
export class ChildComponent {
  inputValue = '';
  items = [
    { id: 1, name: 'Item 1' },
    { id: 2, name: 'Item 2' }
  ];
  
  // Output with data
  @Output() dataSubmit = new EventEmitter<string>();
  
  // Output without data (just notification)
  @Output() notify = new EventEmitter<void>();
  
  // Output with object
  @Output() itemSelected = new EventEmitter<{ id: number; name: string }>();
  
  sendData(): void {
    this.dataSubmit.emit(this.inputValue);
  }
  
  notifyParent(): void {
    this.notify.emit();
  }
  
  selectItem(item: { id: number; name: string }): void {
    this.itemSelected.emit(item);
  }
}

// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <h2>Parent Component</h2>
    <p>Received: {{ receivedData }}</p>
    <p>Selected: {{ selectedItem?.name }}</p>
    
    <app-child
      (dataSubmit)="onDataReceived($event)"
      (notify)="onNotification()"
      (itemSelected)="onItemSelected($event)">
    </app-child>
  `
})
export class ParentComponent {
  receivedData = '';
  selectedItem: { id: number; name: string } | null = null;
  
  onDataReceived(data: string): void {
    this.receivedData = data;
    console.log('Data received from child:', data);
  }
  
  onNotification(): void {
    console.log('Notification received from child');
  }
  
  onItemSelected(item: { id: number; name: string }): void {
    this.selectedItem = item;
    console.log('Item selected:', item);
  }
}
```

---

## 16. What are input and output decorators?

### Answer:
- **@Input()**: Allows parent to pass data **into** child component
- **@Output()**: Allows child to send data **out** to parent component
- Together they enable **component communication**
- @Input uses **property binding** `[property]="value"`
- @Output uses **event binding** `(event)="handler($event)"`

### Theoretical Keywords:
**Property binding**, **Event binding**, **Data flow**,  
**Component communication**, **Decorators**, **EventEmitter**

### Example:
```typescript
import { Component, Input, Output, EventEmitter } from '@angular/core';

// Reusable Counter Component
@Component({
  selector: 'app-counter',
  template: `
    <div class="counter">
      <button (click)="decrement()">-</button>
      <span>{{ count }}</span>
      <button (click)="increment()">+</button>
    </div>
  `
})
export class CounterComponent {
  // INPUT: Receive initial value from parent
  @Input() count: number = 0;
  @Input() step: number = 1;
  @Input() min: number = 0;
  @Input() max: number = 100;
  
  // OUTPUT: Notify parent of changes
  @Output() countChange = new EventEmitter<number>();
  @Output() limitReached = new EventEmitter<'min' | 'max'>();
  
  increment(): void {
    if (this.count < this.max) {
      this.count += this.step;
      this.countChange.emit(this.count);
    } else {
      this.limitReached.emit('max');
    }
  }
  
  decrement(): void {
    if (this.count > this.min) {
      this.count -= this.step;
      this.countChange.emit(this.count);
    } else {
      this.limitReached.emit('min');
    }
  }
}

// Parent using the counter
@Component({
  selector: 'app-product',
  template: `
    <h3>{{ product.name }}</h3>
    <p>Quantity:</p>
    <app-counter
      [count]="quantity"
      [step]="1"
      [min]="1"
      [max]="product.stock"
      (countChange)="onQuantityChange($event)"
      (limitReached)="onLimitReached($event)">
    </app-counter>
    <p>Total: {{ product.price * quantity | currency }}</p>
  `
})
export class ProductComponent {
  product = { name: 'Laptop', price: 999, stock: 10 };
  quantity = 1;
  
  onQuantityChange(newQuantity: number): void {
    this.quantity = newQuantity;
  }
  
  onLimitReached(limit: 'min' | 'max'): void {
    if (limit === 'max') {
      alert('Maximum stock reached!');
    }
  }
}
```

---

## 17. What is `@ViewChild` and `@ContentChild`?

### Answer:
- **@ViewChild**: Access child component/element defined in **component's own template**
- **@ContentChild**: Access content **projected** into component via `<ng-content>`
- Both provide **reference** to child component, directive, or DOM element
- Available after `ngAfterViewInit` and `ngAfterContentInit` respectively
- Use `{ static: true }` if needed in `ngOnInit`

### Theoretical Keywords:
**Template query**, **Content projection**, **Component reference**,  
**DOM access**, **AfterViewInit**, **AfterContentInit**, **ng-content**

### Example:
```typescript
// ========================================
// @ViewChild - Access elements in own template
// ========================================

@Component({
  selector: 'app-parent',
  template: `
    <!-- Access child component -->
    <app-child #childRef></app-child>
    
    <!-- Access DOM element -->
    <input #inputRef type="text">
    
    <button (click)="focusInput()">Focus Input</button>
    <button (click)="callChildMethod()">Call Child Method</button>
  `
})
export class ParentComponent implements AfterViewInit {
  // Access child component
  @ViewChild('childRef') childComponent!: ChildComponent;
  
  // Access DOM element
  @ViewChild('inputRef') inputElement!: ElementRef<HTMLInputElement>;
  
  // Access by component type
  @ViewChild(ChildComponent) child!: ChildComponent;
  
  ngAfterViewInit(): void {
    // ViewChild is available here
    console.log('Child component:', this.childComponent);
  }
  
  focusInput(): void {
    this.inputElement.nativeElement.focus();
  }
  
  callChildMethod(): void {
    this.childComponent.doSomething();
  }
}

// ========================================
// @ContentChild - Access projected content
// ========================================

@Component({
  selector: 'app-card',
  template: `
    <div class="card">
      <div class="card-header">
        <ng-content select="[card-header]"></ng-content>
      </div>
      <div class="card-body">
        <ng-content></ng-content>
      </div>
    </div>
  `
})
export class CardComponent implements AfterContentInit {
  // Access projected content
  @ContentChild('headerContent') headerContent!: ElementRef;
  @ContentChild(HeaderComponent) headerComponent!: HeaderComponent;
  
  ngAfterContentInit(): void {
    // ContentChild is available here
    console.log('Header content:', this.headerContent);
  }
}

// Usage of CardComponent with content projection
@Component({
  selector: 'app-page',
  template: `
    <app-card>
      <!-- This is projected content -->
      <h2 card-header #headerContent>Card Title</h2>
      <p>Card body content goes here...</p>
      <app-header></app-header>
    </app-card>
  `
})
export class PageComponent { }
```

### ViewChildren and ContentChildren:
```typescript
@Component({
  selector: 'app-list',
  template: `
    <div *ngFor="let item of items">
      <app-list-item #itemRef [data]="item"></app-list-item>
    </div>
  `
})
export class ListComponent implements AfterViewInit {
  items = ['Item 1', 'Item 2', 'Item 3'];
  
  // Access multiple children
  @ViewChildren('itemRef') listItems!: QueryList<ListItemComponent>;
  
  ngAfterViewInit(): void {
    // Array of all child components
    this.listItems.forEach(item => console.log(item));
    
    // Listen for changes
    this.listItems.changes.subscribe(() => {
      console.log('List items changed');
    });
  }
}
```

---
