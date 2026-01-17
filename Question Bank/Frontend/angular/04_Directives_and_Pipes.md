# DIRECTIVES AND PIPES ANSWERS

---

## 39. What are directives in Angular?

### Answer:
- **Directives** are classes that add behavior to elements in Angular templates
- Allow you to **manipulate the DOM** declaratively
- Three types: **Component**, **Structural**, **Attribute**

### Theoretical Keywords:
**DOM manipulation**, **Template behavior**, **Component directive**,  
**Structural directive**, **Attribute directive**, **Declarative**

### Example:
```typescript
// Built-in directives usage
@Component({
  template: `
    <!-- Structural Directives -->
    <div *ngIf="isVisible">Conditional content</div>
    <div *ngFor="let item of items">{{ item }}</div>
    <div [ngSwitch]="status">
      <span *ngSwitchCase="'active'">Active</span>
      <span *ngSwitchDefault>Unknown</span>
    </div>
    
    <!-- Attribute Directives -->
    <div [ngClass]="{'active': isActive, 'disabled': !isEnabled}">Styled</div>
    <div [ngStyle]="{'color': textColor, 'font-size': fontSize + 'px'}">Styled</div>
  `
})
export class AppComponent { }
```

---

## 40. What are the types of directives?

### Answer:

| Type | Purpose | Example |
|------|---------|---------|
| **Component** | Directive with template | `@Component()` |
| **Structural** | Change DOM layout | `*ngIf`, `*ngFor`, `*ngSwitch` |
| **Attribute** | Change appearance/behavior | `[ngClass]`, `[ngStyle]`, custom |

### Example:
```typescript
// 1. Component Directive (with template)
@Component({
  selector: 'app-card',
  template: '<div class="card"><ng-content></ng-content></div>'
})
export class CardComponent { }

// 2. Structural Directive (changes DOM structure)
@Directive({
  selector: '[appUnless]',
  standalone: true
})
export class UnlessDirective {
  private hasView = false;
  
  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) { }
  
  @Input() set appUnless(condition: boolean) {
    if (!condition && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (condition && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }
  }
}

// 3. Attribute Directive (changes appearance)
@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {
  @Input() appHighlight = 'yellow';
  
  constructor(private el: ElementRef) { }
  
  @HostListener('mouseenter') onMouseEnter() {
    this.el.nativeElement.style.backgroundColor = this.appHighlight;
  }
  
  @HostListener('mouseleave') onMouseLeave() {
    this.el.nativeElement.style.backgroundColor = '';
  }
}
```

---

## 41. Difference between `*ngIf` and `[hidden]`?

### Answer:

| Feature | *ngIf | [hidden] |
|---------|-------|----------|
| **DOM** | Removes element | Hides with CSS |
| **Initialization** | Creates on true | Always created |
| **Performance** | Better for heavy content | Better for toggles |
| **CSS Override** | Not affected | Can be overridden |
| **Lifecycle Hooks** | Triggers on each render | Only once |

### Example:
```typescript
@Component({
  template: `
    <!-- *ngIf: Element is removed/added to DOM -->
    <heavy-component *ngIf="isVisible"></heavy-component>
    
    <!-- [hidden]: Element stays in DOM, just hidden -->
    <div [hidden]="!isVisible">Quick toggle content</div>
    
    <!-- *ngIf with else -->
    <div *ngIf="user; else noUser">
      Welcome, {{ user.name }}!
    </div>
    <ng-template #noUser>
      <div>Please log in</div>
    </ng-template>
  `
})
export class ComparisonComponent {
  isVisible = true;
  user: User | null = null;
}
```

---

## 42. What are custom directives and how do you create them?

### Answer:
- Custom directives extend Angular's functionality
- Use `@Directive` decorator
- Can be **attribute** or **structural** directives

### Example:
```typescript
// Attribute Directive: Auto-focus
@Directive({
  selector: '[appAutoFocus]',
  standalone: true
})
export class AutoFocusDirective implements AfterViewInit {
  constructor(private el: ElementRef) { }
  
  ngAfterViewInit(): void {
    this.el.nativeElement.focus();
  }
}

// Attribute Directive: Tooltip
@Directive({
  selector: '[appTooltip]',
  standalone: true
})
export class TooltipDirective {
  @Input('appTooltip') tooltipText = '';
  private tooltipElement: HTMLElement | null = null;
  
  constructor(private el: ElementRef, private renderer: Renderer2) { }
  
  @HostListener('mouseenter') onMouseEnter() {
    this.showTooltip();
  }
  
  @HostListener('mouseleave') onMouseLeave() {
    this.hideTooltip();
  }
  
  private showTooltip(): void {
    this.tooltipElement = this.renderer.createElement('span');
    this.renderer.addClass(this.tooltipElement, 'tooltip');
    this.renderer.appendChild(
      this.tooltipElement, 
      this.renderer.createText(this.tooltipText)
    );
    this.renderer.appendChild(this.el.nativeElement, this.tooltipElement);
  }
  
  private hideTooltip(): void {
    if (this.tooltipElement) {
      this.renderer.removeChild(this.el.nativeElement, this.tooltipElement);
      this.tooltipElement = null;
    }
  }
}

// Structural Directive: Repeat
@Directive({
  selector: '[appRepeat]',
  standalone: true
})
export class RepeatDirective {
  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) { }
  
  @Input() set appRepeat(times: number) {
    this.viewContainer.clear();
    for (let i = 0; i < times; i++) {
      this.viewContainer.createEmbeddedView(this.templateRef, {
        $implicit: i,
        index: i
      });
    }
  }
}

// Usage
@Component({
  template: `
    <input appAutoFocus />
    <button [appTooltip]="'Click to submit'">Submit</button>
    <div *appRepeat="5; let i">Item {{ i + 1 }}</div>
  `
})
export class MyComponent { }
```

---

## 43. What are pipes?

### Answer:
- **Pipes** transform data in templates
- Take input value and return transformed output
- Use the **pipe operator** `|`
- Can be **chained** and accept **parameters**

### Theoretical Keywords:
**Data transformation**, **Template formatting**, **Pure pipes**,  
**Impure pipes**, **Pipe operator**, **Chaining**

### Example:
```typescript
@Component({
  template: `
    <!-- Built-in Pipes -->
    <p>{{ name | uppercase }}</p>
    <p>{{ name | lowercase }}</p>
    <p>{{ title | titlecase }}</p>
    <p>{{ price | currency:'USD':'symbol':'1.2-2' }}</p>
    <p>{{ percentage | percent:'1.0-2' }}</p>
    <p>{{ today | date:'fullDate' }}</p>
    <p>{{ today | date:'short' }}</p>
    <p>{{ data | json }}</p>
    <p>{{ items | slice:0:5 }}</p>
    <p>{{ observable$ | async }}</p>
    
    <!-- Chaining Pipes -->
    <p>{{ name | uppercase | slice:0:10 }}</p>
  `
})
export class PipeExampleComponent {
  name = 'john doe';
  title = 'hello world';
  price = 42.50;
  percentage = 0.854;
  today = new Date();
  data = { key: 'value' };
  items = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  observable$ = of('async value');
}
```

---

## 44. How to create custom pipes?

### Answer:
- Use `@Pipe` decorator
- Implement `PipeTransform` interface
- Define `transform()` method

### Example:
```typescript
// Simple transform pipe
@Pipe({
  name: 'truncate',
  standalone: true
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50, trail: string = '...'): string {
    if (!value) return '';
    if (value.length <= limit) return value;
    return value.substring(0, limit) + trail;
  }
}

// Filter pipe
@Pipe({
  name: 'filter',
  standalone: true
})
export class FilterPipe implements PipeTransform {
  transform<T>(items: T[], field: keyof T, value: any): T[] {
    if (!items || !field || value === undefined) return items;
    return items.filter(item => item[field] === value);
  }
}

// Time ago pipe
@Pipe({
  name: 'timeAgo',
  standalone: true
})
export class TimeAgoPipe implements PipeTransform {
  transform(value: Date | string): string {
    const date = new Date(value);
    const now = new Date();
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);
    
    if (seconds < 60) return 'just now';
    if (seconds < 3600) return `${Math.floor(seconds / 60)} minutes ago`;
    if (seconds < 86400) return `${Math.floor(seconds / 3600)} hours ago`;
    if (seconds < 2592000) return `${Math.floor(seconds / 86400)} days ago`;
    return `${Math.floor(seconds / 2592000)} months ago`;
  }
}

// Safe HTML pipe
@Pipe({
  name: 'safeHtml',
  standalone: true
})
export class SafeHtmlPipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) { }
  
  transform(value: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(value);
  }
}

// Usage
@Component({
  imports: [TruncatePipe, FilterPipe, TimeAgoPipe, SafeHtmlPipe],
  template: `
    <p>{{ longText | truncate:100:'...' }}</p>
    <div *ngFor="let user of users | filter:'role':'admin'">
      {{ user.name }}
    </div>
    <p>{{ createdAt | timeAgo }}</p>
    <div [innerHTML]="htmlContent | safeHtml"></div>
  `
})
export class CustomPipeComponent {
  longText = 'Lorem ipsum dolor sit amet...';
  users = [
    { name: 'John', role: 'admin' },
    { name: 'Jane', role: 'user' }
  ];
  createdAt = new Date('2024-01-01');
  htmlContent = '<strong>Bold text</strong>';
}
```

---

## Pure vs Impure Pipes

### Answer:
| Type | Execution | Use Case |
|------|-----------|----------|
| **Pure** | Only when input changes (reference) | Most transformations |
| **Impure** | Every change detection cycle | Arrays, objects that mutate |

### Example:
```typescript
// Pure Pipe (default)
@Pipe({
  name: 'purePipe',
  pure: true  // Default
})
export class PurePipe implements PipeTransform {
  transform(value: any): any {
    console.log('Pure pipe executed');
    return value;
  }
}

// Impure Pipe
@Pipe({
  name: 'impurePipe',
  pure: false  // Runs on every change detection
})
export class ImpurePipe implements PipeTransform {
  transform(items: any[], filterBy: string): any[] {
    console.log('Impure pipe executed');
    return items.filter(item => item.includes(filterBy));
  }
}

// Usage
@Component({
  template: `
    <!-- Pure: Won't update when array items change -->
    <div *ngFor="let item of items | purePipe">{{ item }}</div>
    
    <!-- Impure: Updates on any change -->
    <div *ngFor="let item of items | impurePipe:'a'">{{ item }}</div>
  `
})
export class PipeComponent {
  items = ['apple', 'banana', 'cherry'];
  
  addItem() {
    this.items.push('apricot');  // Pure pipe won't detect
    // To trigger pure pipe, create new array:
    // this.items = [...this.items, 'apricot'];
  }
}
```

---
