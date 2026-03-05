# General Frontend Topics - Most Important Questions

## **Webpack**

### 1. What is Webpack?
**Answer:** Module bundler that bundles JavaScript, CSS, images into optimized files.

### 2. Core concepts of Webpack?
**Answer:**
- **Entry**: Starting point (`entry: './src/index.js'`)
- **Output**: Where to emit bundles (`output: { path: './dist' }`)
- **Loaders**: Transform files (`babel-loader`, `css-loader`)
- **Plugins**: Perform broader tasks (`HtmlWebpackPlugin`)
- **Mode**: `development` or `production`

### 3. Loaders vs Plugins?
**Answer:**
- Loaders: Transform individual files (e.g., TypeScript to JavaScript)
- Plugins: Perform tasks on entire bundle (e.g., minification)

### 4. Common loaders?
**Answer:**
- `babel-loader`: Transpile ES6+
- `ts-loader`: TypeScript
- `css-loader`: Import CSS
- `style-loader`: Inject CSS into DOM
- `file-loader`: Handle images/fonts
- `sass-loader`: Compile SCSS

### 5. Common plugins?
**Answer:**
- `HtmlWebpackPlugin`: Generate HTML
- `MiniCssExtractPlugin`: Extract CSS to files
- `CleanWebpackPlugin`: Clean output folder
- `DefinePlugin`: Define environment variables
- `TerserPlugin`: Minify JavaScript

### 6. Code splitting?
**Answer:** Split code into chunks for lazy loading:
```javascript
import(/* webpackChunkName: "module" */ './module').then(module => {});
```

### 7. Tree shaking?
**Answer:** Remove unused code. Works with ES6 modules in production mode.

### 8. Hot Module Replacement (HMR)?
**Answer:** Update modules without full refresh during development.

### 9. Webpack Dev Server?
**Answer:** Development server with live reloading and HMR.

### 10. Source maps?
**Answer:** Map bundled code to original source for debugging:
```javascript
devtool: 'source-map'
```

---

## **JIT vs AOT Compilation**

### 11. What is JIT (Just-in-Time)?
**Answer:** Compiles application in browser at runtime.

**Pros:**
- Faster development builds
- Better error messages

**Cons:**
- Slower initial load
- Larger bundle size
- Requires Angular compiler in browser

### 12. What is AOT (Ahead-of-Time)?
**Answer:** Compiles application during build process.

**Pros:**
- Faster rendering
- Smaller bundle size (no compiler)
- Detects template errors early
- Better security

**Cons:**
- Slower build time

### 13. JIT vs AOT comparison?
**Answer:**

| Feature | JIT | AOT |
|---------|-----|-----|
| Compilation | Runtime | Build time |
| Bundle Size | Larger | Smaller |
| Performance | Slower | Faster |
| Error Detection | Runtime | Build time |
| Production | Not recommended | Recommended |

### 14. When to use JIT vs AOT?
**Answer:**
- **JIT**: Development (faster builds)
- **AOT**: Production (better performance)

### 15. How to enable AOT in Angular?
**Answer:**
```bash
ng build --aot
ng serve --aot
```
Default in production builds.

---

## **package.json Versioning (^ ~ *)**

### 16. What is semantic versioning (SemVer)?
**Answer:** `MAJOR.MINOR.PATCH` (e.g., `1.2.3`)
- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes

### 17. What does `^` (caret) mean?
**Answer:** Allows updates that don't change leftmost non-zero digit.

**Examples:**
- `^1.2.3` → `>=1.2.3 <2.0.0` (allows 1.x.x)
- `^0.2.3` → `>=0.2.3 <0.3.0` (allows 0.2.x)
- `^0.0.3` → `>=0.0.3 <0.0.4` (exact version)

### 18. What does `~` (tilde) mean?
**Answer:** Allows patch-level updates only.

**Examples:**
- `~1.2.3` → `>=1.2.3 <1.3.0` (allows 1.2.x)
- `~1.2` → `>=1.2.0 <1.3.0`
- `~1` → `>=1.0.0 <2.0.0`

### 19. What does `*` (asterisk) mean?
**Answer:** Accepts any version (not recommended).

### 20. `^` vs `~` comparison?
**Answer:**

| Symbol | Updates | Example | Range |
|--------|---------|---------|-------|
| `^` | Minor + Patch | `^1.2.3` | `1.2.3` to `1.x.x` |
| `~` | Patch only | `~1.2.3` | `1.2.3` to `1.2.x` |
| None | Exact | `1.2.3` | `1.2.3` only |

### 21. Best practices for versioning?
**Answer:**
- Use `^` for most dependencies (safe updates)
- Use `~` for critical dependencies (conservative)
- Use exact versions for production stability
- Lock versions with `package-lock.json`

### 22. What is `package-lock.json`?
**Answer:** Locks exact versions of all dependencies for consistent installs.

### 23. `dependencies` vs `devDependencies`?
**Answer:**
- **dependencies**: Required for production
- **devDependencies**: Only for development (testing, build tools)

### 24. `peerDependencies`?
**Answer:** Specifies compatible versions of packages that should be installed by the consumer.

### 25. npm vs yarn vs pnpm?
**Answer:**
- **npm**: Default, slower, larger node_modules
- **yarn**: Faster, better caching
- **pnpm**: Fastest, disk-efficient (symlinks)

---

## **Build Tools & Module Systems**

### 26. CommonJS vs ES Modules?
**Answer:**

| Feature | CommonJS | ES Modules |
|---------|----------|------------|
| Syntax | `require()` / `module.exports` | `import` / `export` |
| Loading | Synchronous | Asynchronous |
| Tree Shaking | No | Yes |
| Browser | No | Yes (native) |

### 27. What is Babel?
**Answer:** JavaScript transpiler that converts modern JS to older versions for browser compatibility.

### 28. What is Vite?
**Answer:** Fast build tool using native ES modules. Alternative to Webpack.

### 29. What is esbuild?
**Answer:** Extremely fast JavaScript bundler written in Go.

### 30. Webpack vs Vite?
**Answer:**
- **Webpack**: Mature, more plugins, slower
- **Vite**: Faster dev server, HMR, simpler config

---

## **Performance & Optimization**

### 31. Code splitting strategies?
**Answer:**
- Route-based splitting
- Component-based splitting
- Vendor splitting (separate third-party code)

### 32. Lazy loading benefits?
**Answer:** Reduces initial bundle size, faster load time, loads on demand.

### 33. What is bundle analysis?
**Answer:** Analyze bundle size to identify large dependencies:
```bash
npm install --save-dev webpack-bundle-analyzer
```

### 34. Minification vs Uglification?
**Answer:**
- **Minification**: Remove whitespace, comments
- **Uglification**: Minification + variable renaming

### 35. What is Gzip compression?
**Answer:** Server-side compression to reduce file size (70-90% reduction).

---

## **Environment & Configuration**

### 36. Environment variables?
**Answer:**
```javascript
// .env
API_URL=https://api.example.com

// Access
process.env.API_URL
```

### 37. Development vs Production builds?
**Answer:**

| Feature | Development | Production |
|---------|-------------|------------|
| Minification | No | Yes |
| Source Maps | Detailed | Minimal |
| Optimization | No | Yes |
| Size | Large | Small |
| Speed | Fast build | Slow build |

### 38. What is polyfill?
**Answer:** Code that provides modern functionality on older browsers.

### 39. What is transpilation?
**Answer:** Converting modern JavaScript to older versions (ES6 → ES5).

### 40. What is bundling?
**Answer:** Combining multiple files into single/few files for production.

---

## **Version Control & Dependencies**

### 41. What is `npm audit`?
**Answer:** Checks for security vulnerabilities in dependencies.

### 42. How to update dependencies?
**Answer:**
```bash
npm update              # Update within semver range
npm outdated            # Check outdated packages
npm install package@latest  # Update to latest
```

### 43. What is `npx`?
**Answer:** Execute npm packages without installing globally:
```bash
npx create-react-app my-app
```

### 44. Lock file conflicts?
**Answer:** Delete `package-lock.json` and `node_modules`, then `npm install`.

### 45. Monorepo tools?
**Answer:** Nx, Lerna, Turborepo for managing multiple packages.

---

## **Module Federation (Webpack 5)**

### 46. What is Module Federation?
**Answer:** Share code between separate builds/applications at runtime.

### 47. Micro-frontends?
**Answer:** Architecture where frontend is composed of independent applications.

---

## **Quick Reference**

### Version Symbols
```json
{
  "dependencies": {
    "package1": "1.2.3",      // Exact version
    "package2": "^1.2.3",     // 1.x.x (recommended)
    "package3": "~1.2.3",     // 1.2.x (conservative)
    "package4": "*",          // Any version (avoid)
    "package5": ">=1.2.3",    // Greater than or equal
    "package6": "1.2.x"       // Any patch version
  }
}
```

### Webpack Config Example
```javascript
module.exports = {
  entry: './src/index.js',
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].[contenthash].js'
  },
  module: {
    rules: [
      { test: /\.ts$/, use: 'ts-loader' },
      { test: /\.css$/, use: ['style-loader', 'css-loader'] }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin(),
    new MiniCssExtractPlugin()
  ],
  optimization: {
    splitChunks: { chunks: 'all' }
  }
};
```
