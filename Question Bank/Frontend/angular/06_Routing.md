# ROUTING ANSWERS

---

## 50. What is Angular Routing?

### Answer:
- **Angular Router** enables **navigation** between views/components
- Maps **URL paths** to **components**
- Supports **deep linking**, **guards**, **lazy loading**
- Single Page Application (SPA) navigation without page refresh

### Theoretical Keywords:
**Navigation**, **URL mapping**, **SPA**, **Deep linking**,  
**Route configuration**, **RouterModule**, **Guards**

### Example:
```typescript
// Route configuration
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'users', component: UserListComponent },
  { path: 'users/:id', component: UserDetailComponent },
  { path: '**', component: NotFoundComponent }
];

// App setup
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// Template navigation
@Component({
  template: `
    <nav>
      <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Home</a>
      <a routerLink="/about" routerLinkActive="active">About</a>
      <a routerLink="/users" routerLinkActive="active">Users</a>
    </nav>
    
    <router-outlet></router-outlet>
  `
})
export class AppComponent { }
```

---

## 51. How does lazy loading work?

### Answer:
- **Lazy loading** loads modules **on demand**
- Reduces **initial bundle size**
- Uses **loadChildren** with dynamic import
- Creates **separate chunk** for each lazy module

### Theoretical Keywords:
**Code splitting**, **On-demand loading**, **loadChildren**,  
**Dynamic import**, **Chunk files**, **Performance**

### Example:
```typescript
// App routes with lazy loading
const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module')
      .then(m => m.AdminModule)
  },
  {
    path: 'products',
    loadChildren: () => import('./products/products.module')
      .then(m => m.ProductsModule)
  },
  // Standalone component lazy loading (Angular 14+)
  {
    path: 'settings',
    loadComponent: () => import('./settings/settings.component')
      .then(c => c.SettingsComponent)
  }
];

// Admin module with its own routes
@NgModule({
  imports: [
    RouterModule.forChild([
      { path: '', component: AdminDashboardComponent },
      { path: 'users', component: AdminUsersComponent },
      { path: 'settings', component: AdminSettingsComponent }
    ])
  ]
})
export class AdminModule { }

// Preloading strategies
@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: PreloadAllModules  // Preload after initial load
    })
  ]
})
export class AppRoutingModule { }

// Custom preloading strategy
@Injectable({ providedIn: 'root' })
export class SelectivePreloadingStrategy implements PreloadingStrategy {
  preload(route: Route, load: () => Observable<any>): Observable<any> {
    return route.data?.['preload'] ? load() : of(null);
  }
}

// Usage
const routes: Routes = [
  {
    path: 'important',
    loadChildren: () => import('./important/important.module')
      .then(m => m.ImportantModule),
    data: { preload: true }  // Will be preloaded
  }
];
```

---

## 52. What are Route Guards?

### Answer:
- **Route Guards** control **access to routes**
- Execute **before navigation** or **before leaving**
- Can **allow**, **deny**, or **redirect**
- Types: CanActivate, CanDeactivate, CanLoad, Resolve, CanMatch

### Theoretical Keywords:
**Access control**, **Navigation guard**, **Authentication**,  
**Authorization**, **Route protection**, **Functional guards**

### Example:
```typescript
// Functional guards (Angular 15+)
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  if (authService.isAuthenticated()) {
    return true;
  }
  
  return router.createUrlTree(['/login'], {
    queryParams: { returnUrl: state.url }
  });
};

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const requiredRole = route.data['role'];
  
  return authService.hasRole(requiredRole);
};

// Class-based guard
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    if (this.authService.isAuthenticated()) {
      return true;
    }
    return this.router.createUrlTree(['/login']);
  }
}

// CanDeactivate guard (unsaved changes)
export const unsavedChangesGuard: CanDeactivateFn<ComponentWithUnsavedChanges> = 
  (component) => {
    if (component.hasUnsavedChanges()) {
      return confirm('You have unsaved changes. Leave anyway?');
    }
    return true;
  };

// Route configuration
const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard]
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'admin' }
  },
  {
    path: 'editor',
    component: EditorComponent,
    canDeactivate: [unsavedChangesGuard]
  }
];
```

---

## 53. What is the difference between `RouterModule.forRoot()` and `forChild()`?

### Answer:

| Method | Usage | Router Service | Where |
|--------|-------|----------------|-------|
| **forRoot()** | Main app routes | Creates Router | AppModule only |
| **forChild()** | Feature routes | Reuses Router | Feature modules |

### Example:
```typescript
// AppRoutingModule - use forRoot()
@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      enableTracing: false,  // Debug routing
      useHash: false,        // Use hash routing
      scrollPositionRestoration: 'enabled',
      paramsInheritanceStrategy: 'always'
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// Feature module - use forChild()
@NgModule({
  imports: [RouterModule.forChild(featureRoutes)],
  exports: [RouterModule]
})
export class FeatureRoutingModule { }

// Why this matters:
// forRoot() creates the Router service instance
// forChild() uses the existing Router instance
// Using forRoot() in feature modules would create multiple router instances
```

---

## 54. How to pass route parameters?

### Answer:
Multiple ways to pass data through routes:
- **Path parameters**: `/users/:id`
- **Query parameters**: `/users?page=1&sort=name`
- **Route data**: Static data in route config
- **State**: Navigation extras

### Example:
```typescript
// Route configuration
const routes: Routes = [
  { path: 'user/:id', component: UserComponent },
  { 
    path: 'products', 
    component: ProductsComponent,
    data: { title: 'Products Page', breadcrumb: 'Products' }
  }
];

// Passing parameters
@Component({
  template: `
    <!-- Path parameter -->
    <a [routerLink]="['/user', userId]">User Details</a>
    
    <!-- Query parameters -->
    <a [routerLink]="['/products']" [queryParams]="{page: 1, sort: 'name'}">
      Products
    </a>
    
    <!-- Preserve query params -->
    <a [routerLink]="['/next']" queryParamsHandling="preserve">Next</a>
  `
})
export class NavComponent {
  userId = 123;
  
  constructor(private router: Router) { }
  
  navigateProgrammatically(): void {
    // With path param
    this.router.navigate(['/user', this.userId]);
    
    // With query params
    this.router.navigate(['/products'], {
      queryParams: { page: 1, sort: 'name' }
    });
    
    // With state
    this.router.navigate(['/checkout'], {
      state: { items: this.cartItems }
    });
  }
}

// Reading parameters
@Component({
  selector: 'app-user'
})
export class UserComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) { }
  
  ngOnInit(): void {
    // Path parameter (snapshot - one time)
    const id = this.route.snapshot.paramMap.get('id');
    
    // Path parameter (observable - reactive)
    this.route.paramMap.subscribe(params => {
      const userId = params.get('id');
      this.loadUser(userId);
    });
    
    // Query parameters
    this.route.queryParamMap.subscribe(params => {
      const page = params.get('page');
      const sort = params.get('sort');
    });
    
    // Route data
    const title = this.route.snapshot.data['title'];
    
    // Navigation state
    const state = this.router.getCurrentNavigation()?.extras.state;
    // Or
    const historyState = history.state;
  }
}
```

---

## Resolver

### Answer:
- **Resolver** fetches data **before route activation**
- Ensures data is available when component loads
- Prevents empty state flicker

### Example:
```typescript
// Functional resolver (Angular 15+)
export const userResolver: ResolveFn<User> = (route) => {
  const userService = inject(UserService);
  const id = route.paramMap.get('id')!;
  return userService.getUserById(+id);
};

// Class-based resolver
@Injectable({ providedIn: 'root' })
export class UserResolver implements Resolve<User> {
  constructor(private userService: UserService) { }
  
  resolve(route: ActivatedRouteSnapshot): Observable<User> {
    const id = route.paramMap.get('id')!;
    return this.userService.getUserById(+id);
  }
}

// Route configuration
const routes: Routes = [
  {
    path: 'user/:id',
    component: UserComponent,
    resolve: {
      user: userResolver
    }
  }
];

// Using resolved data
@Component({
  selector: 'app-user'
})
export class UserComponent implements OnInit {
  user!: User;
  
  constructor(private route: ActivatedRoute) { }
  
  ngOnInit(): void {
    // Snapshot
    this.user = this.route.snapshot.data['user'];
    
    // Or reactive
    this.route.data.subscribe(data => {
      this.user = data['user'];
    });
  }
}
```

---

## Child Routes

### Answer:
- **Child routes** create nested navigation
- Rendered in parent's `<router-outlet>`
- Share parent's URL path

### Example:
```typescript
const routes: Routes = [
  {
    path: 'products',
    component: ProductsLayoutComponent,
    children: [
      { path: '', component: ProductListComponent },
      { path: ':id', component: ProductDetailComponent },
      { path: ':id/reviews', component: ProductReviewsComponent }
    ]
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: AdminDashboardComponent },
      {
        path: 'users',
        component: UsersLayoutComponent,
        children: [
          { path: '', component: UserListComponent },
          { path: ':id', component: UserDetailComponent }
        ]
      }
    ]
  }
];

// Layout component with router-outlet
@Component({
  template: `
    <div class="admin-layout">
      <nav class="sidebar">
        <a routerLink="dashboard">Dashboard</a>
        <a routerLink="users">Users</a>
      </nav>
      <main>
        <router-outlet></router-outlet>
      </main>
    </div>
  `
})
export class AdminLayoutComponent { }
```

---

## Named Router Outlets

### Answer:
- Multiple `<router-outlet>` with names
- Display **auxiliary routes** simultaneously
- Independent navigation

### Example:
```typescript
const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'chat', component: ChatComponent, outlet: 'sidebar' },
  { path: 'notifications', component: NotificationsComponent, outlet: 'sidebar' }
];

@Component({
  template: `
    <main>
      <router-outlet></router-outlet>
    </main>
    
    <aside>
      <router-outlet name="sidebar"></router-outlet>
    </aside>
    
    <!-- Navigation -->
    <a [routerLink]="[{ outlets: { primary: 'home', sidebar: 'chat' } }]">
      Home with Chat
    </a>
    <a [routerLink]="[{ outlets: { sidebar: null } }]">
      Close Sidebar
    </a>
  `
})
export class AppComponent { }

// URL: /home(sidebar:chat)
```

---
