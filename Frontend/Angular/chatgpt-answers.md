# Angular Interview Questions - Professional Answers

---

## BEGINNER LEVEL (0–2 Years)

### Angular Basics

---

### 1. What is Angular?

**Expected Answer:**
Angular is a TypeScript-based, open-source framework developed by Google for building dynamic single-page applications (SPAs). It provides a complete solution with built-in features like two-way data binding, dependency injection, routing, forms, HTTP client, and testing utilities. Angular follows a component-based architecture where the UI is broken down into reusable components. It uses RxJS for reactive programming and provides powerful CLI tools for scaffolding and development.

**Key Theoretical Concepts:**
- Component-based architecture
- TypeScript as the primary language
- Modules (@NgModule) for code organization
- Dependency injection for loose coupling
- Templates with data binding
- RxJS for reactive programming
- Angular CLI for project management
- Built-in router, forms, HTTP client

**Example:**
```typescript
// Component
@Component({
    selector: 'app-user',
    template: `
        <h2>{{ user.name }}</h2>
        <p>Email: {{ user.email }}</p>
        <button (click)="updateUser()">Update</button>
    `,
    styles: [`h2 { color: blue; }`]
})
export class UserComponent {
    user = { name: 'Alice', email: 'alice@example.com' };
    
    updateUser() {
        this.user.name = 'Alice Smith';
    }
}

// Module
@NgModule({
    declarations: [UserComponent],
    imports: [BrowserModule, FormsModule],
    providers: [UserService],
    bootstrap: [AppComponent]
})
export class AppModule { }

// Service with Dependency Injection
@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient) {}
    
    getUsers(): Observable<User[]> {
        return this.http.get<User[]>('/api/users');
    }
}
```

---

### 2. Difference between AngularJS and Angular

**Expected Answer:**
**AngularJS (1.x)**:
- JavaScript-based MVC framework
- Uses controllers and $scope
- Two-way data binding with dirty checking
- Slower performance for complex apps

**Angular (2+)**:
- Complete rewrite in TypeScript
- Component-based architecture (no controllers)
- Improved change detection with zones
- Better performance with AOT compilation
- Enhanced mobile support
- Better tooling and CLI

Angular 2+ is not an update but a complete redesign with better architecture and performance.

**Key Theoretical Concepts:**
- MVC vs Component-based architecture
- Dirty checking vs Zone.js change detection
- JavaScript vs TypeScript
- Controllers/$scope vs Components
- Improved performance and mobile support
- Better modularity and tree-shaking

**Example:**
```javascript
// AngularJS (1.x) - Controller approach
angular.module('app', [])
    .controller('UserController', function($scope, $http) {
        $scope.users = [];
        
        $http.get('/api/users').then(function(response) {
            $scope.users = response.data;
        });
        
        $scope.addUser = function(user) {
            $scope.users.push(user);
        };
    });
```

```typescript
// Angular (2+) - Component approach
@Component({
    selector: 'app-users',
    template: `
        <div *ngFor="let user of users">{{ user.name }}</div>
    `
})
export class UsersComponent implements OnInit {
    users: User[] = [];
    
    constructor(private userService: UserService) {}
    
    ngOnInit() {
        this.userService.getUsers().subscribe(
            users => this.users = users
        );
    }
    
    addUser(user: User) {
        this.users.push(user);
    }
}
```

---

### 3. What is a component?

**Expected Answer:**
A component is the basic building block of an Angular application that controls a portion of the screen (called a view). It consists of a TypeScript class decorated with @Component, an HTML template for the view, and optional CSS styles. Components have a lifecycle managed by Angular, can communicate with other components via inputs/outputs, and can inject services for business logic. Components are reusable and composable, allowing you to build complex UIs from simple pieces.

**Key Theoretical Concepts:**
- @Component decorator configuration
- Template (HTML) for view
- Styles (CSS) for presentation
- TypeScript class for logic
- Input properties (@Input) for data flow from parent
- Output events (@EventEmitter) for data flow to parent
- Lifecycle hooks (ngOnInit, ngOnDestroy, etc.)
- Component tree and hierarchy

**Example:**
```typescript
// Parent Component
@Component({
    selector: 'app-parent',
    template: `
        <h1>Parent Component</h1>
        <app-child 
            [message]="parentMessage"
            (notify)="onChildNotify($event)">
        </app-child>
    `
})
export class ParentComponent {
    parentMessage = 'Hello from Parent';
    
    onChildNotify(data: string) {
        console.log('Child says:', data);
    }
}

// Child Component
@Component({
    selector: 'app-child',
    template: `
        <div>
            <p>{{ message }}</p>
            <button (click)="sendNotification()">Notify Parent</button>
        </div>
    `,
    styles: [`
        div { padding: 20px; border: 1px solid #ccc; }
    `]
})
export class ChildComponent implements OnInit, OnDestroy {
    @Input() message: string = '';
    @Output() notify = new EventEmitter<string>();
    
    ngOnInit() {
        console.log('Component initialized');
    }
    
    sendNotification() {
        this.notify.emit('Data from child');
    }
    
    ngOnDestroy() {
        console.log('Component destroyed');
    }
}
```

---

### 4. What is a directive?

**Expected Answer:**
Directives are classes that add behavior to elements in Angular applications. There are three types:
1. **Component directives**: Directives with templates (components are special directives)
2. **Structural directives**: Change DOM structure (*ngIf, *ngFor, *ngSwitch)
3. **Attribute directives**: Change appearance or behavior (ngClass, ngStyle, custom directives)

Directives extend HTML functionality and allow you to create reusable behaviors.

**Key Theoretical Concepts:**
- Directive types (component, structural, attribute)
- Asterisk (*) syntax for structural directives
- DOM manipulation capabilities
- Renderer2 for safe DOM access
- HostListener and HostBinding decorators
- Custom directive creation

**Example:**
```typescript
// Structural Directive Usage
<div *ngIf="isVisible">Content shown conditionally</div>
<li *ngFor="let item of items">{{ item }}</li>

// Attribute Directive Usage
<div [ngClass]="{'active': isActive, 'disabled': isDisabled}"></div>
<p [ngStyle]="{'color': textColor, 'font-size': fontSize}">Styled text</p>

// Custom Attribute Directive
@Directive({
    selector: '[appHighlight]'
})
export class HighlightDirective {
    constructor(private el: ElementRef, private renderer: Renderer2) {}
    
    @Input() appHighlight: string = 'yellow';
    
    @HostListener('mouseenter') onMouseEnter() {
        this.renderer.setStyle(
            this.el.nativeElement,
            'backgroundColor',
            this.appHighlight
        );
    }
    
    @HostListener('mouseleave') onMouseLeave() {
        this.renderer.removeStyle(
            this.el.nativeElement,
            'backgroundColor'
        );
    }
}

// Usage
<p appHighlight="lightblue">Hover over me!</p>

// Custom Structural Directive
@Directive({
    selector: '[appUnless]'
})
export class UnlessDirective {
    constructor(
        private templateRef: TemplateRef<any>,
        private viewContainer: ViewContainerRef
    ) {}
    
    @Input() set appUnless(condition: boolean) {
        if (!condition) {
            this.viewContainer.createEmbeddedView(this.templateRef);
        } else {
            this.viewContainer.clear();
        }
    }
}

// Usage
<div *appUnless="isLoggedIn">Please log in</div>
```

---

### 5. What is data binding?

**Expected Answer:**
Data binding is the automatic synchronization between the model (component data) and view (template). Angular supports four types:
1. **Interpolation**: `{{ value }}` - One-way from component to view
2. **Property binding**: `[property]="value"` - One-way to DOM property
3. **Event binding**: `(event)="handler()"` - One-way from view to component
4. **Two-way binding**: `[(ngModel)]="value"` - Combines property and event binding

Data binding reduces boilerplate code and keeps the view synchronized with data.

**Key Theoretical Concepts:**
- One-way vs two-way data binding
- Interpolation for displaying data
- Property binding for DOM properties
- Event binding for user actions
- Banana in a box syntax [()] for two-way binding
- Change detection mechanism

**Example:**
```typescript
@Component({
    selector: 'app-data-binding',
    template: `
        <!-- 1. Interpolation -->
        <h1>{{ title }}</h1>
        <p>Count: {{ count }}</p>
        
        <!-- 2. Property Binding -->
        <img [src]="imageUrl" [alt]="imageAlt">
        <button [disabled]="isDisabled">Click</button>
        <div [class.active]="isActive"></div>
        <p [style.color]="textColor">Colored text</p>
        
        <!-- 3. Event Binding -->
        <button (click)="increment()">Increment</button>
        <input (input)="onInput($event)" (keyup.enter)="onEnter()">
        
        <!-- 4. Two-way Binding -->
        <input [(ngModel)]="username">
        <p>Hello, {{ username }}!</p>
    `
})
export class DataBindingComponent {
    // Data
    title = 'Data Binding Example';
    count = 0;
    imageUrl = '/assets/logo.png';
    imageAlt = 'Logo';
    isDisabled = false;
    isActive = true;
    textColor = 'blue';
    username = '';
    
    // Methods
    increment() {
        this.count++;
    }
    
    onInput(event: Event) {
        const value = (event.target as HTMLInputElement).value;
        console.log('Input:', value);
    }
    
    onEnter() {
        console.log('Enter pressed');
    }
}
```**
Know examples: `ngIf`/`ngFor` (structural) vs attribute directives.

**Red Flags:**
- Not distinguishing template-driven components from simple directives.

**Depth Expected:**
Intermediate: cite examples and usage.

---

### Q: Dependency Injection (brief)
**Expected Answer:**
Angular uses DI to provide and inject services; `providedIn: 'root'` registers a singleton application-wide by default.

**Key Concepts:**
- Providers, injector hierarchy, tree-shakable providers.

**Interviewer Expectation:**
Explain provider scope and how to control service lifetime.

**Red Flags:**
- Saying services are singletons without explaining provider scope.

**Depth Expected:**
Practical understanding for 2-year level.

---

### Q: Change detection and performance (brief)
**Expected Answer:**
Change detection runs to update the view; `OnPush` optimizes checks for immutable inputs; `zone.js` triggers detection on async events.

**Key Concepts:**
- Default vs `OnPush`, immutability, manual change detection (`ChangeDetectorRef`).

**Interviewer Expectation:**
Mention optimization strategies and when to use `OnPush`.

**Red Flags:**
- Recommending `OnPush` without understanding input immutability requirements.

**Depth Expected:**
Intermediate with example strategies.

---

If you want the full per-question structured answers for every bullet in the Angular `chatgpt.md`, tell me whether to expand "all questions" or specific sections.

---

Full expanded answers for all bullets (Beginner → Advanced):

### Angular Basics

Q: What is Angular?
**Expected Answer:** Angular is a TypeScript-based framework for building scalable single-page applications with a component-based architecture and strong CLI tooling.
**Key Concepts:** components, modules, DI, templates, RxJS.
**Interviewer Expectation:** Explain component lifecycle and modular architecture.
**Red Flags:** Confusing Angular with AngularJS.

Q: Difference between AngularJS and Angular
**Expected Answer:** AngularJS (1.x) is an MVC-style framework using `$scope` and controllers; Angular (2+) is rewritten with TypeScript, components, and improved performance.

Q: What are components, modules, services, directives?
**Expected Answer:** Components render views; modules group features; services provide shared logic via DI; directives modify DOM behavior (`structural` vs `attribute`).

### Project Config Files

Q: `angular.json`, `package.json`, `tsconfig.json`
**Expected Answer:** `angular.json` configures build/serve; `package.json` lists dependencies and scripts; `tsconfig.json` configures TypeScript compiler options.

### Directives, Pipes, DI, Routing, Forms

Q: Structural vs attribute directives; pipes
**Expected Answer:** Structural change DOM shape (`*ngIf`), attribute change appearance; pipes transform data in templates.

Q: DI and providedIn
**Expected Answer:** `providedIn: 'root'` registers singleton; DI hierarchy controls provider scope.

Q: Router features
**Expected Answer:** Configure `Routes`, lazy loading modules, guards for auth, passing params.

Q: Forms
**Expected Answer:** Template-driven for simple forms; reactive forms (`FormGroup`, `FormControl`) for complex validation and dynamic forms.

### Advanced

Q: Lifecycle hooks and change detection
**Expected Answer:** Use `ngOnInit`, `ngOnChanges`, etc.; `OnPush` improves perf when inputs are immutable.

Q: RxJS and Observables
**Expected Answer:** Observables represent streams; use operators (`switchMap`, `mergeMap`) and manage subscriptions with `async` pipe or `takeUntil`.

Q: State management
**Expected Answer:** NgRx provides Redux-like store; know store, actions, reducers, selectors, effects.

---

Confirm and I will expand each bullet into the full Q/A block with interviewer expectations and red flags for every entry in the original file.
