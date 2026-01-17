# PROJECT CONFIGURATION FILES ANSWERS

---

## angular.json

---

## 18. What is `angular.json`?

### Answer:
- **angular.json** is the **workspace configuration** file for Angular CLI
- Contains all **project settings**, build configurations, and CLI defaults
- Located at the **root** of Angular workspace
- Defines how Angular CLI commands work (build, serve, test, lint)
- Manages **multiple projects** in a single workspace

### Theoretical Keywords:
**Workspace configuration**, **Angular CLI**, **Build settings**,  
**Project management**, **CLI defaults**, **Architect targets**

### Example:
```json
{
  "$schema": "./node_modules/@angular/cli/lib/config/schema.json",
  "version": 1,
  "newProjectRoot": "projects",
  "projects": {
    "my-app": {
      "projectType": "application",
      "root": "",
      "sourceRoot": "src",
      "prefix": "app",
      "architect": {
        "build": { },
        "serve": { },
        "test": { }
      }
    }
  },
  "defaultProject": "my-app"
}
```

---

## 19. What is its role in an Angular project?

### Answer:
The **angular.json** file serves several critical purposes:
- **Build configuration**: Output paths, optimization, bundling
- **Development server**: Port, proxy, SSL settings
- **Asset management**: Static files, styles, scripts
- **Environment configuration**: Development vs production
- **Testing configuration**: Karma, test files
- **Multiple project management**: Monorepo support

### Theoretical Keywords:
**Build configuration**, **Serve settings**, **Asset management**,  
**Environment handling**, **Test configuration**, **Monorepo**

---

## 20. How does `angular.json` manage build and serve configurations?

### Answer:
- Uses **architect targets** for different operations
- **build**: Compiles the application
- **serve**: Runs development server
- Each target has **options** (defaults) and **configurations** (overrides)

### Example:
```json
{
  "projects": {
    "my-app": {
      "architect": {
        "build": {
          "builder": "@angular-devkit/build-angular:browser",
          "options": {
            "outputPath": "dist/my-app",
            "index": "src/index.html",
            "main": "src/main.ts",
            "polyfills": "src/polyfills.ts",
            "tsConfig": "tsconfig.app.json",
            "aot": true,
            "assets": ["src/favicon.ico", "src/assets"],
            "styles": ["src/styles.scss"],
            "scripts": []
          },
          "configurations": {
            "production": {
              "budgets": [
                { "type": "initial", "maximumWarning": "500kb", "maximumError": "1mb" }
              ],
              "fileReplacements": [
                { "replace": "src/environments/environment.ts", "with": "src/environments/environment.prod.ts" }
              ],
              "outputHashing": "all",
              "optimization": true,
              "sourceMap": false
            },
            "development": {
              "buildOptimizer": false,
              "optimization": false,
              "sourceMap": true
            }
          },
          "defaultConfiguration": "production"
        },
        "serve": {
          "builder": "@angular-devkit/build-angular:dev-server",
          "options": {
            "browserTarget": "my-app:build",
            "port": 4200,
            "open": true
          },
          "configurations": {
            "production": {
              "browserTarget": "my-app:build:production"
            },
            "development": {
              "browserTarget": "my-app:build:development"
            }
          }
        }
      }
    }
  }
}
```

---

## 21. How do you configure environments in `angular.json`?

### Answer:
- Use **fileReplacements** in build configurations
- Create environment files for each configuration
- Angular CLI swaps files during build

### Example:
```json
// angular.json
{
  "configurations": {
    "production": {
      "fileReplacements": [
        {
          "replace": "src/environments/environment.ts",
          "with": "src/environments/environment.prod.ts"
        }
      ]
    },
    "staging": {
      "fileReplacements": [
        {
          "replace": "src/environments/environment.ts",
          "with": "src/environments/environment.staging.ts"
        }
      ]
    }
  }
}
```

```typescript
// src/environments/environment.ts (development)
export const environment = {
  production: false,
  apiUrl: 'http://localhost:3000/api'
};

// src/environments/environment.prod.ts
export const environment = {
  production: true,
  apiUrl: 'https://api.myapp.com'
};

// src/environments/environment.staging.ts
export const environment = {
  production: false,
  apiUrl: 'https://staging-api.myapp.com'
};

// Usage in application
import { environment } from '../environments/environment';

@Injectable()
export class ApiService {
  private apiUrl = environment.apiUrl;
}

// Build commands:
// ng build --configuration=production
// ng build --configuration=staging
```

---

## 22. How do you add assets and styles globally using `angular.json`?

### Answer:
- **assets**: Static files copied to output directory
- **styles**: Global CSS/SCSS files
- **scripts**: Global JavaScript files

### Example:
```json
{
  "architect": {
    "build": {
      "options": {
        "assets": [
          "src/favicon.ico",
          "src/assets",
          {
            "glob": "**/*",
            "input": "node_modules/some-library/assets",
            "output": "/library-assets/"
          },
          {
            "glob": "manifest.json",
            "input": "src",
            "output": "/"
          }
        ],
        "styles": [
          "src/styles.scss",
          "node_modules/bootstrap/dist/css/bootstrap.min.css",
          {
            "input": "node_modules/@fortawesome/fontawesome-free/css/all.min.css",
            "bundleName": "fontawesome"
          }
        ],
        "scripts": [
          "node_modules/jquery/dist/jquery.min.js",
          {
            "input": "node_modules/chart.js/dist/chart.min.js",
            "bundleName": "charts",
            "inject": false
          }
        ]
      }
    }
  }
}
```

---

## package.json

---

## 23. What is `package.json`?

### Answer:
- **package.json** is the **Node.js project manifest** file
- Defines project **metadata** and **dependencies**
- Contains **scripts** for running tasks
- Required for **npm/yarn** package management
- Lists both runtime and development dependencies

### Theoretical Keywords:
**Project manifest**, **Dependencies**, **Scripts**, **npm/yarn**,  
**Metadata**, **Package management**

### Example:
```json
{
  "name": "my-angular-app",
  "version": "1.0.0",
  "description": "My Angular Application",
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "test": "ng test",
    "lint": "ng lint"
  },
  "dependencies": {
    "@angular/core": "^17.0.0",
    "rxjs": "^7.8.0"
  },
  "devDependencies": {
    "@angular/cli": "^17.0.0",
    "typescript": "~5.2.0"
  }
}
```

---

## 24. Why is `package.json` important?

### Answer:
- **Dependency tracking**: Lists all required packages
- **Version management**: Controls package versions
- **Script automation**: Custom commands for development
- **Project sharing**: Others can install dependencies with `npm install`
- **CI/CD integration**: Build systems use it to install dependencies

### Theoretical Keywords:
**Dependency tracking**, **Version control**, **Automation**,  
**Reproducibility**, **CI/CD**, **Team collaboration**

---

## 25. How does `package.json` manage dependencies?

### Answer:
- Uses **semantic versioning** (semver)
- **^** prefix: Allow minor updates (^1.2.3 → 1.x.x)
- **~** prefix: Allow patch updates (~1.2.3 → 1.2.x)
- **Exact**: No prefix (1.2.3 → only 1.2.3)
- **package-lock.json**: Locks exact versions installed

### Example:
```json
{
  "dependencies": {
    "exact-version": "1.2.3",
    "minor-updates": "^1.2.3",
    "patch-updates": "~1.2.3",
    "any-version": "*",
    "range": ">=1.0.0 <2.0.0"
  }
}
```

---

## 26. What are `devDependencies` and `dependencies`?

### Answer:

| Type | Purpose | Example | Bundled in Production |
|------|---------|---------|----------------------|
| **dependencies** | Required at runtime | @angular/core, rxjs | Yes |
| **devDependencies** | Development only | TypeScript, Karma, ESLint | No |

### Example:
```json
{
  "dependencies": {
    "@angular/core": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/router": "^17.0.0",
    "rxjs": "^7.8.0",
    "zone.js": "^0.14.0"
  },
  "devDependencies": {
    "@angular/cli": "^17.0.0",
    "@angular/compiler-cli": "^17.0.0",
    "@types/node": "^20.0.0",
    "typescript": "~5.2.0",
    "karma": "^6.4.0",
    "jasmine-core": "^5.0.0",
    "eslint": "^8.0.0"
  }
}
```

---

## 27. What are scripts in `package.json` and how are they used?

### Answer:
- **Scripts** are custom commands defined in package.json
- Run using `npm run <script-name>`
- Some scripts have shortcuts: `npm start`, `npm test`
- Can chain commands and pass arguments

### Example:
```json
{
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "start:prod": "ng serve --configuration=production",
    "start:ssl": "ng serve --ssl --ssl-key ./ssl/server.key --ssl-cert ./ssl/server.crt",
    "build": "ng build",
    "build:prod": "ng build --configuration=production",
    "build:analyze": "ng build --stats-json && webpack-bundle-analyzer dist/my-app/stats.json",
    "test": "ng test",
    "test:ci": "ng test --no-watch --code-coverage",
    "lint": "ng lint",
    "lint:fix": "ng lint --fix",
    "e2e": "ng e2e",
    "format": "prettier --write \"src/**/*.{ts,html,scss}\"",
    "precommit": "npm run lint && npm run test:ci",
    "deploy": "npm run build:prod && firebase deploy"
  }
}
```

---

## 28. How to run custom npm scripts defined in `package.json`?

### Answer:
```bash
# Standard scripts (shortcuts available)
npm start          # Runs "start" script
npm test           # Runs "test" script

# Custom scripts (require "run")
npm run build:prod
npm run lint:fix
npm run deploy

# Pass arguments
npm run start -- --port=4300
npm run build -- --configuration=staging

# Run multiple scripts
npm run lint && npm run test

# Using npx for one-off commands
npx ng generate component my-component
```

---

## tsconfig.json

---

## 29. What is `tsconfig.json`?

### Answer:
- **tsconfig.json** is the **TypeScript compiler configuration** file
- Defines how TypeScript code is **compiled to JavaScript**
- Contains **compiler options**, **file inclusion/exclusion**
- Angular has multiple tsconfig files for different purposes

### Example:
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
    "lib": ["ES2022", "dom"]
  },
  "angularCompilerOptions": {
    "enableI18nLegacyMessageIdFormat": false,
    "strictInjectionParameters": true,
    "strictInputAccessModifiers": true,
    "strictTemplates": true
  }
}
```

---

## 30. What compiler options are most important?

### Answer:

| Option | Purpose |
|--------|---------|
| **target** | Output JavaScript version |
| **module** | Module code generation |
| **strict** | Enable all strict options |
| **baseUrl** | Base for module resolution |
| **paths** | Path mapping for imports |
| **experimentalDecorators** | Enable decorators |
| **sourceMap** | Generate source maps |

### Example:
```json
{
  "compilerOptions": {
    "target": "ES2022",
    "module": "ES2022",
    "strict": true,
    "baseUrl": "src",
    "paths": {
      "@app/*": ["app/*"],
      "@core/*": ["app/core/*"],
      "@shared/*": ["app/shared/*"],
      "@env/*": ["environments/*"]
    },
    "experimentalDecorators": true,
    "emitDecoratorMetadata": true,
    "sourceMap": true
  }
}
```

---

## 31. What does strict mode do?

### Answer:
**strict: true** enables all strict type-checking options:
- `strictNullChecks`: null/undefined handled explicitly
- `noImplicitAny`: Must specify types (no implicit any)
- `strictFunctionTypes`: Strict function type checking
- `strictBindCallApply`: Check bind, call, apply
- `strictPropertyInitialization`: Class properties must be initialized

### Example:
```typescript
// With strict mode enabled:

// strictNullChecks
let name: string;
// name.toUpperCase();  // Error: possibly undefined

// noImplicitAny
function greet(name) {  // Error: parameter has implicit any
  return `Hello, ${name}`;
}

// strictPropertyInitialization
class User {
  name: string;  // Error: not initialized
  
  constructor() {
    // Must initialize here or use !
  }
}

// Correct version
class UserCorrect {
  name: string;
  
  constructor(name: string) {
    this.name = name;
  }
}
```

---

## 32. Difference between `target` and `module`?

### Answer:

| Option | Purpose | Values |
|--------|---------|--------|
| **target** | Output JS version | ES5, ES6, ES2020, ESNext |
| **module** | Module system | CommonJS, ES2020, ESNext |

- **target**: Affects syntax (let, const, async/await)
- **module**: Affects import/export handling

```json
{
  "compilerOptions": {
    "target": "ES2022",
    "module": "ES2022"
  }
}
```

---

## 33. How to enable `strictNullChecks` and `noImplicitAny`?

### Answer:
```json
{
  "compilerOptions": {
    "strict": true,
    // Or individually:
    "strictNullChecks": true,
    "noImplicitAny": true
  }
}
```

---

## Webpack

---

## 34. What is Webpack?

### Answer:
- **Webpack** is a **module bundler** for JavaScript applications
- Takes modules with dependencies and generates **static bundles**
- Handles: JavaScript, CSS, images, fonts, and more
- Uses **loaders** to transform files and **plugins** for optimization

### Theoretical Keywords:
**Module bundler**, **Static assets**, **Loaders**, **Plugins**,  
**Code splitting**, **Tree shaking**, **Hot Module Replacement**

---

## 35. How does Angular use Webpack internally?

### Answer:
- Angular CLI uses Webpack **under the hood**
- Pre-configured through `@angular-devkit/build-angular`
- Handles: TypeScript compilation, bundling, optimization
- Features: Code splitting, lazy loading, tree shaking
- **Hidden by default** but can be customized

### Theoretical Keywords:
**Angular CLI**, **Build system**, **Code splitting**,  
**Tree shaking**, **AOT compilation**, **Dev server**

---

## 36. What is the role of loaders and plugins in Webpack?

### Answer:
- **Loaders**: Transform files before bundling
- **Plugins**: Perform broader tasks (optimization, asset management)

| Type | Purpose | Examples |
|------|---------|----------|
| **Loaders** | Transform source files | ts-loader, sass-loader, file-loader |
| **Plugins** | Bundle optimization | HtmlWebpackPlugin, MiniCssExtractPlugin |

---

## 37. Difference between development and production build using Webpack?

### Answer:

| Feature | Development | Production |
|---------|-------------|------------|
| **Mode** | development | production |
| **Source Maps** | Full (inline) | Minimal/none |
| **Optimization** | Disabled | Enabled |
| **Minification** | No | Yes |
| **Tree Shaking** | No | Yes |
| **Hot Reload** | Enabled | Disabled |

```bash
ng build                        # Production (default)
ng build --configuration=development  # Development
```

---

## 38. How can you customize Webpack in Angular?

### Answer:
- Use **@angular-builders/custom-webpack**
- Create custom webpack configuration
- Merge with Angular's default config

### Example:
```bash
npm install @angular-builders/custom-webpack --save-dev
```

```json
// angular.json
{
  "architect": {
    "build": {
      "builder": "@angular-builders/custom-webpack:browser",
      "options": {
        "customWebpackConfig": {
          "path": "./webpack.config.js",
          "mergeRules": {
            "externals": "replace"
          }
        }
      }
    }
  }
}
```

```javascript
// webpack.config.js
module.exports = {
  resolve: {
    alias: {
      '@components': path.resolve(__dirname, 'src/app/components')
    }
  },
  plugins: [
    new webpack.DefinePlugin({
      'APP_VERSION': JSON.stringify(require('./package.json').version)
    })
  ]
};
```

---
