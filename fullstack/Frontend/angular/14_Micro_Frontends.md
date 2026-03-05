# MICRO FRONTENDS ANSWERS

---

## 103. What is Micro Frontend Architecture?

### Answer:
- **Micro Frontends** extend microservices to the frontend
- Break large frontend into **smaller, independent applications**
- Each team owns end-to-end feature (including UI)
- Can use **different frameworks**
- Independent **deployment and development**

### Theoretical Keywords:
**Microservices for frontend**, **Independent deployment**, **Team autonomy**,  
**Technology agnostic**, **Scalable architecture**, **Module Federation**

### Example:
```
Traditional Monolith:
┌─────────────────────────────────────────┐
│           Single Angular App            │
│  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐       │
│  │Users│ │Cart │ │Prod │ │Auth │       │
│  └─────┘ └─────┘ └─────┘ └─────┘       │
└─────────────────────────────────────────┘

Micro Frontend:
┌─────────────────────────────────────────┐
│              Shell/Container            │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐   │
│  │MFE: User│ │MFE: Cart│ │MFE: Prod│   │
│  │(Angular)│ │ (React) │ │ (Vue)   │   │
│  │Team A   │ │ Team B  │ │ Team C  │   │
│  └─────────┘ └─────────┘ └─────────┘   │
└─────────────────────────────────────────┘
```

---

## 104. What is Module Federation?

### Answer:
- **Module Federation** is a Webpack 5 feature
- Allows **sharing code** between applications at runtime
- Load **remote modules** dynamically
- Share **dependencies** to avoid duplication

### Example:
```javascript
// webpack.config.js - Remote App (products)
const ModuleFederationPlugin = require('webpack/lib/container/ModuleFederationPlugin');

module.exports = {
  plugins: [
    new ModuleFederationPlugin({
      name: 'products',
      filename: 'remoteEntry.js',
      exposes: {
        './ProductList': './src/app/product-list/product-list.component.ts',
        './ProductModule': './src/app/products/products.module.ts'
      },
      shared: {
        '@angular/core': { singleton: true, strictVersion: true },
        '@angular/common': { singleton: true, strictVersion: true },
        '@angular/router': { singleton: true, strictVersion: true },
        'rxjs': { singleton: true, strictVersion: true }
      }
    })
  ]
};

// webpack.config.js - Host App (shell)
module.exports = {
  plugins: [
    new ModuleFederationPlugin({
      name: 'shell',
      remotes: {
        products: 'products@http://localhost:4201/remoteEntry.js',
        cart: 'cart@http://localhost:4202/remoteEntry.js'
      },
      shared: {
        '@angular/core': { singleton: true, strictVersion: true },
        '@angular/common': { singleton: true, strictVersion: true },
        '@angular/router': { singleton: true, strictVersion: true }
      }
    })
  ]
};
```

---

## 105. How to implement Micro Frontends in Angular?

### Answer:
Using **@angular-architects/module-federation**:

```bash
# Add Module Federation to Angular project
ng add @angular-architects/module-federation --project shell --port 4200 --type host
ng add @angular-architects/module-federation --project mfe1 --port 4201 --type remote
```

```typescript
// Shell app - app-routing.module.ts
const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'products',
    loadChildren: () => loadRemoteModule({
      type: 'module',
      remoteEntry: 'http://localhost:4201/remoteEntry.js',
      exposedModule: './ProductsModule'
    }).then(m => m.ProductsModule)
  },
  {
    path: 'cart',
    loadChildren: () => loadRemoteModule({
      type: 'module',
      remoteEntry: 'http://localhost:4202/remoteEntry.js',
      exposedModule: './CartModule'
    }).then(m => m.CartModule)
  }
];

// Remote app - webpack.config.js (auto-generated)
module.exports = {
  output: {
    uniqueName: "mfeProducts",
    publicPath: "auto"
  },
  plugins: [
    new ModuleFederationPlugin({
      name: "mfeProducts",
      filename: "remoteEntry.js",
      exposes: {
        './ProductsModule': './src/app/products/products.module.ts',
      },
      shared: share({
        "@angular/core": { singleton: true, strictVersion: true },
        "@angular/common": { singleton: true, strictVersion: true },
        "@angular/router": { singleton: true, strictVersion: true },
        "rxjs": { singleton: true, strictVersion: true }
      })
    })
  ]
};
```

---

## 106. What are the challenges of Micro Frontends?

### Answer:

| Challenge | Solution |
|-----------|----------|
| **Shared state** | Event bus, shared service |
| **Consistent UI** | Design system, shared components |
| **Routing** | Shell manages routes |
| **Authentication** | Shared auth service |
| **Versioning** | Semantic versioning, compatible ranges |
| **Performance** | Lazy loading, shared dependencies |
| **Debugging** | Source maps, logging |

### Example - Shared State:
```typescript
// Custom Events for cross-MFE communication
// In MFE1
window.dispatchEvent(new CustomEvent('user:selected', { 
  detail: { userId: 123 } 
}));

// In MFE2
window.addEventListener('user:selected', (event: CustomEvent) => {
  const userId = event.detail.userId;
  this.loadUserDetails(userId);
});

// Or using shared service via window
// shared-state.service.ts
@Injectable()
export class SharedStateService {
  private state = new BehaviorSubject<any>({});
  
  constructor() {
    // Expose to window for cross-MFE access
    (window as any).sharedState = this;
  }
  
  setState(key: string, value: any): void {
    const current = this.state.value;
    this.state.next({ ...current, [key]: value });
  }
  
  getState(key: string): Observable<any> {
    return this.state.pipe(map(state => state[key]));
  }
}
```

---

## 107. How to handle routing in Micro Frontends?

### Answer:
```typescript
// Shell App Routes
const routes: Routes = [
  { path: '', component: ShellHomeComponent },
  {
    path: 'products',
    loadChildren: () => loadRemoteModule({
      remoteEntry: 'http://localhost:4201/remoteEntry.js',
      exposedModule: './ProductsModule'
    }).then(m => m.ProductsModule),
    canActivate: [AuthGuard]
  }
];

// Remote MFE Routes (products)
const routes: Routes = [
  { path: '', component: ProductListComponent },
  { path: ':id', component: ProductDetailComponent }
];

// Full URLs would be:
// /products -> ProductListComponent (from MFE)
// /products/123 -> ProductDetailComponent (from MFE)
```

---

## 108. How to share dependencies between Micro Frontends?

### Answer:
```javascript
// webpack.config.js
shared: share({
  "@angular/core": { 
    singleton: true,        // Only one instance
    strictVersion: true,    // Must match version
    requiredVersion: '^17.0.0'
  },
  "@angular/common": { singleton: true, strictVersion: true },
  "@angular/router": { singleton: true, strictVersion: true },
  "rxjs": { 
    singleton: true, 
    strictVersion: false    // Allow compatible versions
  },
  // Shared UI library
  "@my-org/ui-components": { 
    singleton: true,
    import: '@my-org/ui-components',
    requiredVersion: '^2.0.0'
  }
})
```

---

## 109. What is Web Components approach?

### Answer:
- Create **Custom Elements** from Angular components
- Framework-agnostic integration
- Uses **Angular Elements** package

### Example:
```typescript
// Create Angular Element
import { createCustomElement } from '@angular/elements';

@Component({
  selector: 'app-widget',
  template: `<div>{{ title }}</div>`
})
export class WidgetComponent {
  @Input() title = '';
}

@NgModule({
  declarations: [WidgetComponent],
  imports: [BrowserModule],
  entryComponents: [WidgetComponent]
})
export class AppModule {
  constructor(private injector: Injector) {
    const widgetElement = createCustomElement(WidgetComponent, { injector });
    customElements.define('my-widget', widgetElement);
  }
  
  ngDoBootstrap() { }
}

// Usage in any HTML (React, Vue, plain HTML)
// <my-widget title="Hello World"></my-widget>
```

---

## 110. What is Single-SPA?

### Answer:
- **Single-SPA** is a framework for bringing together multiple JS frameworks
- Manages **lifecycle** of micro frontends
- Handles **mounting/unmounting** applications

### Example:
```javascript
// root-config.js (orchestrator)
import { registerApplication, start } from 'single-spa';

registerApplication({
  name: '@my-org/angular-app',
  app: () => System.import('@my-org/angular-app'),
  activeWhen: ['/angular']
});

registerApplication({
  name: '@my-org/react-app',
  app: () => System.import('@my-org/react-app'),
  activeWhen: ['/react']
});

start();

// Angular MFE setup
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { singleSpaAngular } from 'single-spa-angular';

const lifecycles = singleSpaAngular({
  bootstrapFunction: () => platformBrowserDynamic().bootstrapModule(AppModule),
  template: '<app-root />',
  Router,
  NgZone
});

export const bootstrap = lifecycles.bootstrap;
export const mount = lifecycles.mount;
export const unmount = lifecycles.unmount;
```

---

## 111. How to handle authentication in Micro Frontends?

### Answer:
```typescript
// Shared Auth Service (in shell or shared library)
@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenKey = 'auth_token';
  
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }
  
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    // Notify all MFEs
    window.dispatchEvent(new CustomEvent('auth:tokenChanged', {
      detail: { token }
    }));
  }
  
  isAuthenticated(): boolean {
    return !!this.getToken();
  }
  
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    window.dispatchEvent(new CustomEvent('auth:logout'));
  }
}

// Each MFE listens for auth events
window.addEventListener('auth:logout', () => {
  // Clear local state, redirect to login
});

// Shared HTTP Interceptor
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('auth_token');
    
    if (token) {
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` }
      });
    }
    
    return next.handle(req);
  }
}
```

---

## 112. Micro Frontend Best Practices

### Answer:
1. **Design System**: Shared UI components library
2. **API Gateway**: Single entry point for backends
3. **Event-driven communication**: Loose coupling
4. **Independent deployments**: CI/CD per MFE
5. **Monitoring**: Distributed tracing
6. **Versioning strategy**: Semantic versioning

### Example Architecture:
```
┌──────────────────────────────────────────────────────────┐
│                        CDN                                │
├──────────────────────────────────────────────────────────┤
│                    Shell App (Host)                       │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────────────┐ │
│  │ Header  │ │ Nav     │ │ Router  │ │ Auth Service    │ │
│  └─────────┘ └─────────┘ └─────────┘ └─────────────────┘ │
├──────────────────────────────────────────────────────────┤
│                   Micro Frontends                         │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐           │
│  │ Products   │ │ Cart       │ │ Checkout   │           │
│  │ (Angular)  │ │ (React)    │ │ (Angular)  │           │
│  │ Team Alpha │ │ Team Beta  │ │ Team Gamma │           │
│  └────────────┘ └────────────┘ └────────────┘           │
├──────────────────────────────────────────────────────────┤
│               Shared Libraries                            │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────────┐  │
│  │ UI Components│ │ Utils        │ │ Event Bus        │  │
│  └──────────────┘ └──────────────┘ └──────────────────┘  │
├──────────────────────────────────────────────────────────┤
│                   API Gateway                             │
├──────────────────────────────────────────────────────────┤
│  ┌────────────┐ ┌────────────┐ ┌────────────┐           │
│  │ Products   │ │ Cart       │ │ Orders     │           │
│  │ Service    │ │ Service    │ │ Service    │           │
│  └────────────┘ └────────────┘ └────────────┘           │
└──────────────────────────────────────────────────────────┘
```

---
