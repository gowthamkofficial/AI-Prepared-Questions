# MODULES AND ARCHITECTURE ANSWERS

---

## 86. What are Angular Modules?

### Answer:
- **NgModule** is a container for related code (components, services, etc.)
- Organizes application into **cohesive blocks**
- Controls **component visibility** and **dependencies**
- Enables **lazy loading**

### Theoretical Keywords:
**NgModule**, **Organization**, **Encapsulation**, **Dependencies**,  
**Feature modules**, **Shared modules**, **Lazy loading**

### Example:
```typescript
@NgModule({
  declarations: [    // Components, directives, pipes owned by this module
    UserListComponent,
    UserDetailComponent,
    UserFilterPipe
  ],
  imports: [         // Other modules needed
    CommonModule,
    FormsModule,
    SharedModule
  ],
  exports: [         // Make available to other modules
    UserListComponent
  ],
  providers: [       // Services (prefer providedIn: 'root')
    UserService
  ]
})
export class UserModule { }
```

---

## 87. Types of Angular modules?

### Answer:

| Type | Purpose | Example |
|------|---------|---------|
| **Root (App)** | Bootstrap application | AppModule |
| **Feature** | Encapsulate feature | UserModule, ProductModule |
| **Shared** | Reusable components | SharedModule |
| **Core** | Singleton services | CoreModule |
| **Routing** | Route configuration | AppRoutingModule |

### Example:
```typescript
// Root Module
@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    SharedModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

// Feature Module
@NgModule({
  declarations: [
    ProductListComponent,
    ProductDetailComponent
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    SharedModule
  ]
})
export class ProductModule { }

// Shared Module
@NgModule({
  declarations: [
    ButtonComponent,
    CardComponent,
    LoadingSpinnerComponent,
    TruncatePipe
  ],
  imports: [CommonModule],
  exports: [
    // Re-export commonly used modules
    CommonModule,
    FormsModule,
    // Export shared components
    ButtonComponent,
    CardComponent,
    LoadingSpinnerComponent,
    TruncatePipe
  ]
})
export class SharedModule { }

// Core Module (singleton services)
@NgModule({
  providers: [
    AuthService,
    LoggingService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ]
})
export class CoreModule {
  // Prevent re-import
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule already loaded. Import only in AppModule.');
    }
  }
}
```

---

## 88. What is the difference between declarations and imports?

### Answer:

| Property | Purpose | Contains |
|----------|---------|----------|
| **declarations** | Define what belongs to module | Components, Directives, Pipes |
| **imports** | What this module needs | Other NgModules |
| **exports** | What to share with other modules | Components, Modules |
| **providers** | Services available | Services |

### Example:
```typescript
@NgModule({
  // Components/Directives/Pipes that BELONG to this module
  declarations: [
    MyComponent,        // Component
    HighlightDirective, // Directive
    TruncatePipe        // Pipe
  ],
  
  // Modules this module NEEDS
  imports: [
    CommonModule,       // For *ngIf, *ngFor, etc.
    FormsModule,        // For ngModel
    HttpClientModule,   // For HttpClient
    FeatureModule       // Custom module
  ],
  
  // What to SHARE with modules that import this
  exports: [
    MyComponent,
    TruncatePipe,
    CommonModule  // Re-export
  ],
  
  // Services (prefer providedIn: 'root' instead)
  providers: [
    MyService
  ]
})
export class MyModule { }
```

---

## 89. What is a Shared Module?

### Answer:
- Contains **commonly used** components, directives, pipes
- **Imported by multiple** feature modules
- **Exports** items for use elsewhere
- Does NOT contain services (usually)

### Example:
```typescript
// shared/shared.module.ts
@NgModule({
  declarations: [
    // UI Components
    ButtonComponent,
    CardComponent,
    ModalComponent,
    AlertComponent,
    LoadingComponent,
    PaginationComponent,
    
    // Directives
    ClickOutsideDirective,
    AutoFocusDirective,
    
    // Pipes
    DateFormatPipe,
    CurrencyFormatPipe,
    TruncatePipe,
    SafeHtmlPipe
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
    // Re-export Angular modules
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    
    // Export all shared components
    ButtonComponent,
    CardComponent,
    ModalComponent,
    AlertComponent,
    LoadingComponent,
    PaginationComponent,
    
    // Export directives
    ClickOutsideDirective,
    AutoFocusDirective,
    
    // Export pipes
    DateFormatPipe,
    CurrencyFormatPipe,
    TruncatePipe,
    SafeHtmlPipe
  ]
})
export class SharedModule { }

// Usage in feature module
@NgModule({
  imports: [SharedModule]  // Gets all exported items
})
export class FeatureModule { }
```

---

## 90. What is the Core Module?

### Answer:
- Contains **singleton services** used across the app
- **Imported ONLY in AppModule**
- Contains **HTTP interceptors**, **guards**
- Prevents duplicate service instances

### Example:
```typescript
// core/core.module.ts
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    // Singleton services
    AuthService,
    LoggingService,
    NotificationService,
    
    // HTTP Interceptors
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true },
    
    // Guards
    AuthGuard,
    AdminGuard
  ]
})
export class CoreModule {
  // Ensure CoreModule is only imported once
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(
        'CoreModule is already loaded. Import it only in AppModule.'
      );
    }
  }
  
  // Alternative: Static forRoot pattern
  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        AuthService,
        LoggingService
      ]
    };
  }
}

// app.module.ts
@NgModule({
  imports: [
    BrowserModule,
    CoreModule,  // Import only here
    SharedModule,
    AppRoutingModule
  ]
})
export class AppModule { }
```

---

## Standalone Components

### Answer:
- **Angular 14+** feature
- Components without NgModule
- Declare dependencies directly
- Simpler, more tree-shakable

### Example:
```typescript
// Standalone component
@Component({
  selector: 'app-user-card',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    DatePipe
  ],
  template: `
    <div class="card">
      <h3>{{ user.name }}</h3>
      <p>{{ user.createdAt | date:'mediumDate' }}</p>
      <a [routerLink]="['/users', user.id]">View Details</a>
    </div>
  `
})
export class UserCardComponent {
  @Input() user!: User;
}

// Using in another standalone component
@Component({
  standalone: true,
  imports: [UserCardComponent],
  template: `
    <app-user-card [user]="user"></app-user-card>
  `
})
export class UserProfileComponent { }

// Bootstrapping with standalone
// main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations()
  ]
});

// Lazy loading standalone component
const routes: Routes = [
  {
    path: 'dashboard',
    loadComponent: () => import('./dashboard/dashboard.component')
      .then(c => c.DashboardComponent)
  }
];
```

---

## Module Organization Best Practices

### Answer:
```
src/
├── app/
│   ├── core/                    # Singleton services, guards
│   │   ├── services/
│   │   │   ├── auth.service.ts
│   │   │   └── api.service.ts
│   │   ├── guards/
│   │   ├── interceptors/
│   │   └── core.module.ts
│   │
│   ├── shared/                  # Reusable components
│   │   ├── components/
│   │   │   ├── button/
│   │   │   └── modal/
│   │   ├── directives/
│   │   ├── pipes/
│   │   └── shared.module.ts
│   │
│   ├── features/               # Feature modules
│   │   ├── users/
│   │   │   ├── components/
│   │   │   ├── services/
│   │   │   ├── users-routing.module.ts
│   │   │   └── users.module.ts
│   │   │
│   │   └── products/
│   │       ├── components/
│   │       ├── services/
│   │       └── products.module.ts
│   │
│   ├── layout/                 # Layout components
│   │   ├── header/
│   │   ├── footer/
│   │   └── sidebar/
│   │
│   ├── app-routing.module.ts
│   ├── app.component.ts
│   └── app.module.ts
```

---
