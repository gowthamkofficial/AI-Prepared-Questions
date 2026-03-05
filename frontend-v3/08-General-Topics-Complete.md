# General Frontend Topics - Complete Guide

## **Webpack**

### 1. What is Webpack?
**Answer:** Module bundler that transforms, bundles, and packages resources for the browser.

**Core Concepts:**
- Entry: Starting point
- Output: Where bundles are emitted
- Loaders: Transform files
- Plugins: Perform broader tasks
- Mode: development/production

### 2. Webpack Configuration
```javascript
// webpack.config.js
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
  // Entry point
  entry: './src/index.js',
  
  // Output configuration
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].[contenthash].js',
    clean: true
  },
  
  // Loaders
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: [MiniCssExtractPlugin.loader, 'css-loader']
      },
      {
        test: /\.scss$/,
        use: ['style-loader', 'css-loader', 'sass-loader']
      },
      {
        test: /\.(png|jpg|gif|svg)$/,
        type: 'asset/resource'
      }
    ]
  },
  
  // Plugins
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html'
    }),
    new MiniCssExtractPlugin({
      filename: '[name].[contenthash].css'
    })
  ],
  
  // Mode
  mode: 'production',
  
  // Dev server
  devServer: {
    static: './dist',
    port: 3000,
    hot: true
  },
  
  // Source maps
  devtool: 'source-map',
  
  // Optimization
  optimization: {
    splitChunks: {
      chunks: 'all'
    }
  }
};
```

### 3. Common Loaders
```javascript
// Babel loader - transpile ES6+
{
  test: /\.js$/,
  use: 'babel-loader',
  exclude: /node_modules/
}

// TypeScript loader
{
  test: /\.ts$/,
  use: 'ts-loader'
}

// CSS loader
{
  test: /\.css$/,
  use: ['style-loader', 'css-loader']
}

// SASS loader
{
  test: /\.scss$/,
  use: ['style-loader', 'css-loader', 'sass-loader']
}

// File loader
{
  test: /\.(png|jpg|gif)$/,
  type: 'asset/resource'
}
```

### 4. Common Plugins
```javascript
// HTML plugin
new HtmlWebpackPlugin({
  template: './src/index.html',
  minify: true
})

// CSS extract plugin
new MiniCssExtractPlugin({
  filename: '[name].[contenthash].css'
})

// Clean plugin
new CleanWebpackPlugin()

// Define plugin
new webpack.DefinePlugin({
  'process.env.API_URL': JSON.stringify('https://api.example.com')
})

// Terser plugin (minification)
new TerserPlugin({
  parallel: true
})
```

### 5. Code Splitting
```javascript
// Dynamic import
import(/* webpackChunkName: "module" */ './module').then(module => {
  module.doSomething();
});

// Split chunks configuration
optimization: {
  splitChunks: {
    chunks: 'all',
    cacheGroups: {
      vendor: {
        test: /[\\/]node_modules[\\/]/,
        name: 'vendors',
        chunks: 'all'
      }
    }
  }
}
```

### 6. Tree Shaking
**Answer:** Removes unused code from bundle.

**Requirements:**
- ES6 modules (import/export)
- Production mode
- No side effects

```javascript
// package.json
{
  "sideEffects": false
}

// Or specify files with side effects
{
  "sideEffects": ["*.css", "*.scss"]
}
```

---

## **JIT vs AOT Compilation**

### 7. What is JIT (Just-in-Time)?
**Answer:** Compiles application in browser at runtime.

**Process:**
1. Download Angular compiler (~1MB)
2. Compile templates in browser
3. Execute application

**Pros:**
- Faster development builds
- Better error messages
- Easier debugging

**Cons:**
- Slower initial load
- Larger bundle size
- Runtime errors

### 8. What is AOT (Ahead-of-Time)?
**Answer:** Compiles application during build process.

**Process:**
1. Compile templates at build time
2. Generate JavaScript code
3. No compiler in browser

**Pros:**
- Faster rendering
- Smaller bundle size (no compiler)
- Detects template errors early
- Better security (no eval)

**Cons:**
- Slower build time
- Less flexible

### 9. JIT vs AOT Comparison
| Feature | JIT | AOT |
|---------|-----|-----|
| Compilation | Runtime | Build time |
| Bundle Size | Larger (~1MB compiler) | Smaller |
| Performance | Slower initial load | Faster |
| Error Detection | Runtime | Build time |
| Development | Faster builds | Slower builds |
| Production | Not recommended | Recommended |
| Template Errors | Runtime | Compile time |

### 10. Enable AOT in Angular
```bash
# Development (JIT by default)
ng serve

# Production (AOT by default)
ng build --configuration production

# Force AOT in development
ng serve --aot

# Build with AOT
ng build --aot
```

---

## **package.json Versioning**

### 11. Semantic Versioning (SemVer)
**Answer:** `MAJOR.MINOR.PATCH` (e.g., `1.2.3`)

- **MAJOR**: Breaking changes (1.0.0 → 2.0.0)
- **MINOR**: New features, backward compatible (1.0.0 → 1.1.0)
- **PATCH**: Bug fixes (1.0.0 → 1.0.1)

### 12. Caret (^) Symbol
**Answer:** Allows updates that don't change leftmost non-zero digit.

```json
{
  "dependencies": {
    "package": "^1.2.3"
  }
}
```

**Examples:**
- `^1.2.3` → `>=1.2.3 <2.0.0` (allows 1.x.x)
- `^0.2.3` → `>=0.2.3 <0.3.0` (allows 0.2.x)
- `^0.0.3` → `>=0.0.3 <0.0.4` (exact version)

**Updates allowed:**
- `^1.2.3` can update to `1.2.4`, `1.3.0`, `1.9.9`
- `^1.2.3` cannot update to `2.0.0`

### 13. Tilde (~) Symbol
**Answer:** Allows patch-level updates only.

```json
{
  "dependencies": {
    "package": "~1.2.3"
  }
}
```

**Examples:**
- `~1.2.3` → `>=1.2.3 <1.3.0` (allows 1.2.x)
- `~1.2` → `>=1.2.0 <1.3.0`
- `~1` → `>=1.0.0 <2.0.0`

**Updates allowed:**
- `~1.2.3` can update to `1.2.4`, `1.2.9`
- `~1.2.3` cannot update to `1.3.0`

### 14. Version Symbols Comparison
| Symbol | Updates | Example | Range | Use Case |
|--------|---------|---------|-------|----------|
| `^` | Minor + Patch | `^1.2.3` | `1.2.3` to `1.x.x` | Recommended |
| `~` | Patch only | `~1.2.3` | `1.2.3` to `1.2.x` | Conservative |
| None | Exact | `1.2.3` | `1.2.3` only | Production |
| `*` | Any | `*` | Any version | Avoid |
| `>=` | Greater/equal | `>=1.2.3` | `1.2.3` and above | Flexible |
| `<` | Less than | `<2.0.0` | Below `2.0.0` | Max version |

### 15. Best Practices
```json
{
  "dependencies": {
    "react": "^18.2.0",           // Safe updates
    "lodash": "~4.17.21",         // Conservative
    "critical-lib": "1.0.0"       // Exact version
  },
  "devDependencies": {
    "webpack": "^5.88.0",         // Dev tools can be flexible
    "typescript": "~5.2.2"
  }
}
```

**Recommendations:**
- Use `^` for most dependencies
- Use `~` for critical dependencies
- Use exact versions for production stability
- Lock versions with `package-lock.json`

### 16. package-lock.json
**Answer:** Locks exact versions of all dependencies.

**Purpose:**
- Ensures consistent installs
- Records exact dependency tree
- Faster installs
- Security

**Never delete it!**

### 17. dependencies vs devDependencies
```json
{
  "dependencies": {
    "react": "^18.2.0",
    "axios": "^1.5.0"
  },
  "devDependencies": {
    "webpack": "^5.88.0",
    "jest": "^29.6.0",
    "typescript": "^5.2.2"
  }
}
```

**dependencies:**
- Required for production
- Installed in production builds
- Runtime dependencies

**devDependencies:**
- Only for development
- Not installed in production
- Build tools, testing, linting

### 18. peerDependencies
```json
{
  "peerDependencies": {
    "react": ">=16.8.0",
    "react-dom": ">=16.8.0"
  }
}
```

**Purpose:**
- Specifies compatible versions
- Consumer must install
- Prevents version conflicts

---

## **Build Tools**

### 19. npm vs yarn vs pnpm
| Feature | npm | yarn | pnpm |
|---------|-----|------|------|
| Speed | Slower | Faster | Fastest |
| Disk Space | More | More | Less (symlinks) |
| Lock File | package-lock.json | yarn.lock | pnpm-lock.yaml |
| Workspaces | Yes | Yes | Yes |
| Offline | Limited | Yes | Yes |

```bash
# npm
npm install
npm install package
npm update

# yarn
yarn
yarn add package
yarn upgrade

# pnpm
pnpm install
pnpm add package
pnpm update
```

### 20. Module Systems
**CommonJS:**
```javascript
// Export
module.exports = { name: 'John' };
exports.age = 30;

// Import
const user = require('./user');
```

**ES Modules:**
```javascript
// Export
export const name = 'John';
export default function() {}

// Import
import { name } from './user';
import user from './user';
```

**Comparison:**
| Feature | CommonJS | ES Modules |
|---------|----------|------------|
| Syntax | require/module.exports | import/export |
| Loading | Synchronous | Asynchronous |
| Tree Shaking | No | Yes |
| Browser | No | Yes (native) |
| Node.js | Default | .mjs or "type": "module" |

---

## **Performance Optimization**

### 21. Code Splitting Strategies
```javascript
// Route-based splitting
const Home = lazy(() => import('./Home'));
const About = lazy(() => import('./About'));

// Component-based splitting
const HeavyComponent = lazy(() => import('./HeavyComponent'));

// Vendor splitting
optimization: {
  splitChunks: {
    cacheGroups: {
      vendor: {
        test: /[\\/]node_modules[\\/]/,
        name: 'vendors',
        chunks: 'all'
      }
    }
  }
}
```

### 22. Lazy Loading Benefits
- Reduces initial bundle size
- Faster initial load
- Loads on demand
- Better user experience

### 23. Bundle Analysis
```bash
# Install
npm install --save-dev webpack-bundle-analyzer

# Use
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

plugins: [
  new BundleAnalyzerPlugin()
]
```

### 24. Minification vs Uglification
**Minification:**
- Removes whitespace
- Removes comments
- Shortens variable names

**Uglification:**
- Minification +
- Variable renaming
- Dead code elimination

```javascript
// TerserPlugin (Webpack 5)
const TerserPlugin = require('terser-webpack-plugin');

optimization: {
  minimize: true,
  minimizer: [new TerserPlugin()]
}
```

### 25. Compression
```javascript
// Gzip compression
const CompressionPlugin = require('compression-webpack-plugin');

plugins: [
  new CompressionPlugin({
    algorithm: 'gzip',
    test: /\.(js|css|html|svg)$/,
    threshold: 10240,
    minRatio: 0.8
  })
]
```

---

## **Environment Configuration**

### 26. Environment Variables
```javascript
// .env
API_URL=https://api.example.com
NODE_ENV=production

// webpack.config.js
const webpack = require('webpack');
const dotenv = require('dotenv');

dotenv.config();

plugins: [
  new webpack.DefinePlugin({
    'process.env.API_URL': JSON.stringify(process.env.API_URL)
  })
]

// Usage
const apiUrl = process.env.API_URL;
```

### 27. Development vs Production
| Feature | Development | Production |
|---------|-------------|------------|
| Minification | No | Yes |
| Source Maps | Detailed | Minimal |
| Optimization | No | Yes |
| Bundle Size | Large | Small |
| Build Speed | Fast | Slow |
| Hot Reload | Yes | No |

```javascript
module.exports = (env, argv) => {
  const isDev = argv.mode === 'development';
  
  return {
    mode: argv.mode,
    devtool: isDev ? 'eval-source-map' : 'source-map',
    optimization: {
      minimize: !isDev
    }
  };
};
```

---

## **Dependency Management**

### 28. npm Commands
```bash
# Install
npm install
npm install package
npm install package@version
npm install package --save-dev

# Update
npm update
npm update package
npm outdated

# Remove
npm uninstall package

# Audit
npm audit
npm audit fix

# Clean
npm cache clean --force

# List
npm list
npm list --depth=0
```

### 29. npx
**Answer:** Execute npm packages without installing globally.

```bash
# Create React app
npx create-react-app my-app

# Run local package
npx webpack

# Run specific version
npx webpack@5.88.0
```

### 30. Lock File Conflicts
**Solution:**
```bash
# Delete lock file and node_modules
rm package-lock.json
rm -rf node_modules

# Reinstall
npm install
```

---

## **Quick Reference**

### Version Symbols
```json
{
  "dependencies": {
    "package1": "1.2.3",      // Exact
    "package2": "^1.2.3",     // 1.x.x (recommended)
    "package3": "~1.2.3",     // 1.2.x (conservative)
    "package4": "*",          // Any (avoid)
    "package5": ">=1.2.3",    // Greater or equal
    "package6": "<2.0.0",     // Less than
    "package7": "1.2.x"       // Any patch
  }
}
```

### Webpack Loaders Order
```javascript
// Right to left execution
use: ['style-loader', 'css-loader', 'sass-loader']
// sass-loader → css-loader → style-loader
```

### Common Issues
1. **Large bundle size**: Use code splitting, tree shaking
2. **Slow builds**: Use caching, parallel builds
3. **Version conflicts**: Check peer dependencies
4. **Missing dependencies**: Run `npm install`

---

**Total: 30+ comprehensive questions on Webpack, JIT/AOT, and package.json versioning**
