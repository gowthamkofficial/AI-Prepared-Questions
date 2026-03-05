# Angular Configuration Files Guide

## **angular.json**

### What is angular.json?
**Answer:** Main configuration file for Angular CLI workspace. Defines build, serve, test, and deployment settings.

```json
{
  "version": 1,
  "newProjectRoot": "projects",
  "projects": {
    "my-app": {
      "projectType": "application",
      "root": "",
      "sourceRoot": "src",
      "prefix": "app",
      "architect": {
        "build": {
          "builder": "@angular-devkit/build-angular:browser",
          "options": {
            "outputPath": "dist/my-app",
            "index": "src/index.html",
            "main": "src/main.ts",
            "polyfills": ["zone.js"],
            "tsConfig": "tsconfig.app.json",
            "assets": [
              "src/favicon.ico",
              "src/assets"
            ],
            "styles": [
              "src/styles.css"
            ],
            "scripts": []
          },
          "configurations": {
            "production": {
              "budgets": [
                {
                  "type": "initial",
                  "maximumWarning": "500kb",
                  "maximumError": "1mb"
                }
              ],
              "outputHashing": "all",
              "optimization": true,
              "sourceMap": false,
              "namedChunks": false,
              "aot": true,
              "extractLicenses": true,
              "vendorChunk": false,
              "buildOptimizer": true
            },
            "development": {
              "optimization": false,
              "sourceMap": true,
              "namedChunks": true
            }
          }
        },
        "serve": {
          "builder": "@angular-devkit/build-angular:dev-server",
          "options": {
            "browserTarget": "my-app:build"
          },
          "configurations": {
            "production": {
              "browserTarget": "my-app:build:production"
            },
            "development": {
              "browserTarget": "my-app:build:development"
            }
          }
        },
        "test": {
          "builder": "@angular-devkit/build-angular:karma",
          "options": {
            "main": "src/test.ts",
            "polyfills": ["zone.js", "zone.js/testing"],
            "tsConfig": "tsconfig.spec.json",
            "karmaConfig": "karma.conf.js",
            "assets": ["src/favicon.ico", "src/assets"],
            "styles": ["src/styles.css"],
            "scripts": []
          }
        }
      }
    }
  }
}
```

**Key Sections:**
- `projects`: All projects in workspace
- `architect.build`: Build configuration
- `architect.serve`: Dev server configuration
- `architect.test`: Testing configuration
- `assets`: Static files to copy
- `styles`: Global stylesheets
- `scripts`: Global JavaScript files

---

## **package.json**

### What is package.json?
**Answer:** Defines project dependencies, scripts, and metadata.

```json
{
  "name": "my-angular-app",
  "version": "1.0.0",
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "build:prod": "ng build --configuration production",
    "watch": "ng build --watch --configuration development",
    "test": "ng test",
    "lint": "ng lint"
  },
  "private": true,
  "dependencies": {
    "@angular/animations": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/compiler": "^17.0.0",
    "@angular/core": "^17.0.0",
    "@angular/forms": "^17.0.0",
    "@angular/platform-browser": "^17.0.0",
    "@angular/platform-browser-dynamic": "^17.0.0",
    "@angular/router": "^17.0.0",
    "rxjs": "~7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.14.0"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "^17.0.0",
    "@angular/cli": "^17.0.0",
    "@angular/compiler-cli": "^17.0.0",
    "@types/jasmine": "~5.1.0",
    "jasmine-core": "~5.1.0",
    "karma": "~6.4.0",
    "karma-chrome-launcher": "~3.2.0",
    "karma-coverage": "~2.2.0",
    "karma-jasmine": "~5.1.0",
    "karma-jasmine-html-reporter": "~2.1.0",
    "typescript": "~5.2.2"
  }
}
```

**Version Symbols:**
- `^17.0.0`: Compatible with 17.x.x (minor updates)
- `~7.8.0`: Compatible with 7.8.x (patch updates)
- `17.0.0`: Exact version

---

## **tsconfig.json**

### What is tsconfig.json?
**Answer:** TypeScript compiler configuration.

```json
{
  "compileOnSave": false,
  "compilerOptions": {
    "baseUrl": "./",
    "outDir": "./dist/out-tsc",
    "forceConsistentCasingInFileNames": true,
    "strict": true,
    "noImplicitOverride": true,
    "noPropertyAccessFromIndexSignature": true,
    "noImplicitReturns": true,
    "noFallthroughCasesInSwitch": true,
    "sourceMap": true,
    "declaration": false,
    "downlevelIteration": true,
    "experimentalDecorators": true,
    "moduleResolution": "node",
    "importHelpers": true,
    "target": "ES2022",
    "module": "ES2022",
    "useDefineForClassFields": false,
    "lib": [
      "ES2022",
      "dom"
    ],
    "paths": {
      "@app/*": ["src/app/*"],
      "@env/*": ["src/environments/*"]
    }
  },
  "angularCompilerOptions": {
    "enableI18nLegacyMessageIdFormat": false,
    "strictInjectionParameters": true,
    "strictInputAccessModifiers": true,
    "strictTemplates": true
  }
}
```

**Key Options:**
- `strict`: Enable all strict type checking
- `experimentalDecorators`: Enable decorators
- `paths`: Path aliases
- `strictTemplates`: Strict template type checking

---

## **main.ts**

### What is main.ts?
**Answer:** Entry point of Angular application. Bootstraps the root module.

```typescript
// Traditional NgModule approach
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch(err => console.error(err));

// Standalone approach (Angular 14+)
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient()
  ]
}).catch(err => console.error(err));

// With environment configuration
import { environment } from './environments/environment';
import { enableProdMode } from '@angular/core';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch(err => console.error(err));
```

---

## **environment files**

### What are environment files?
**Answer:** Store environment-specific configuration.

```typescript
// environment.ts (development)
export const environment = {
  production: false,
  apiUrl: 'http://localhost:3000/api',
  enableDebug: true
};

// environment.prod.ts (production)
export const environment = {
  production: true,
  apiUrl: 'https://api.example.com',
  enableDebug: false
};

// Usage in code
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private apiUrl = environment.apiUrl;
  
  getData() {
    return this.http.get(`${this.apiUrl}/data`);
  }
}
```

**Configuration in angular.json:**
```json
"configurations": {
  "production": {
    "fileReplacements": [
      {
        "replace": "src/environments/environment.ts",
        "with": "src/environments/environment.prod.ts"
      }
    ]
  }
}
```

---

## **polyfills.ts**

### What is polyfills.ts?
**Answer:** Imports polyfills for browser compatibility.

```typescript
// Angular 15+ (in main.ts or angular.json)
import 'zone.js';

// Older versions (polyfills.ts)
import 'zone.js/dist/zone';

// Optional polyfills for older browsers
import 'core-js/es/symbol';
import 'core-js/es/object';
import 'core-js/es/function';
import 'core-js/es/parse-int';
import 'core-js/es/parse-float';
import 'core-js/es/number';
import 'core-js/es/math';
import 'core-js/es/string';
import 'core-js/es/date';
import 'core-js/es/array';
import 'core-js/es/regexp';
import 'core-js/es/map';
import 'core-js/es/weak-map';
import 'core-js/es/set';
```

---

## **.browserslistrc**

### What is .browserslistrc?
**Answer:** Defines target browsers for build optimization.

```
# Browsers that we support

last 2 Chrome versions
last 2 Firefox versions
last 2 Edge versions
last 2 Safari versions
last 2 iOS versions
Firefox ESR
```

---

## **karma.conf.js**

### What is karma.conf.js?
**Answer:** Configuration for Karma test runner.

```javascript
module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    client: {
      jasmine: {
        // Jasmine configuration
      },
      clearContext: false
    },
    jasmineHtmlReporter: {
      suppressAll: true
    },
    coverageReporter: {
      dir: require('path').join(__dirname, './coverage/my-app'),
      subdir: '.',
      reporters: [
        { type: 'html' },
        { type: 'text-summary' }
      ]
    },
    reporters: ['progress', 'kjhtml'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false,
    restartOnFileChange: true
  });
};
```

---

## **proxy.conf.json**

### What is proxy.conf.json?
**Answer:** Proxy configuration for development server.

```json
{
  "/api": {
    "target": "http://localhost:3000",
    "secure": false,
    "changeOrigin": true,
    "logLevel": "debug",
    "pathRewrite": {
      "^/api": ""
    }
  }
}
```

**Usage:**
```json
// angular.json
"serve": {
  "options": {
    "proxyConfig": "proxy.conf.json"
  }
}
```

```bash
ng serve --proxy-config proxy.conf.json
```

---

## **Angular Version-Based Changes**

### Angular 15
- Standalone components stable
- Directive composition API
- Image directive (NgOptimizedImage)
- Functional guards

### Angular 16
- **Signals** introduced
- Required inputs
- Router input binding
- DestroyRef for cleanup

### Angular 17
- New control flow syntax (@if, @for)
- Deferred loading (@defer)
- Built-in control flow
- Improved SSR

```typescript
// Angular 17 new syntax
@Component({
  template: `
    @if (isVisible) {
      <p>Visible</p>
    } @else {
      <p>Hidden</p>
    }
    
    @for (item of items; track item.id) {
      <div>{{item.name}}</div>
    }
    
    @defer (on viewport) {
      <heavy-component />
    } @placeholder {
      <p>Loading...</p>
    }
  `
})
```

---

## **Quick Reference**

### File Structure
```
my-app/
├── angular.json          # Angular CLI config
├── package.json          # Dependencies
├── tsconfig.json         # TypeScript config
├── .browserslistrc       # Browser targets
├── karma.conf.js         # Test config
├── proxy.conf.json       # Dev proxy
├── src/
│   ├── main.ts          # Entry point
│   ├── index.html       # HTML template
│   ├── styles.css       # Global styles
│   ├── environments/    # Environment configs
│   └── app/
│       ├── app.module.ts
│       ├── app.component.ts
│       └── app-routing.module.ts
```

### Common Commands
```bash
# Create new app
ng new my-app

# Serve
ng serve
ng serve --port 4300
ng serve --open

# Build
ng build
ng build --configuration production

# Generate
ng generate component my-component
ng generate service my-service
ng generate module my-module

# Test
ng test
ng test --code-coverage

# Lint
ng lint

# Update
ng update @angular/cli @angular/core
```
