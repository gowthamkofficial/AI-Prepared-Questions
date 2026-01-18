# Tailwind CSS - Interview Questions

## Q1: What is Tailwind CSS?
**Answer:**
Tailwind CSS is a utility-first CSS framework that provides low-level utility classes to build custom designs without leaving your HTML.

**Installation:**
```bash
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p
```

**Configuration:**
```js
// tailwind.config.js
module.exports = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#3498db',
      }
    }
  }
}
```

## Q2: What are Tailwind Utility Classes?
**Answer:**
Utility classes are single-purpose CSS classes that can be composed together.

```html
<!-- Without Tailwind -->
<style>
  .button { padding: 10px 20px; background: blue; color: white; border-radius: 4px; }
</style>
<button class="button">Click me</button>

<!-- With Tailwind -->
<button class="px-5 py-2 bg-blue-500 text-white rounded">Click me</button>
```

**Common Tailwind Classes:**
```html
<!-- Spacing -->
<div class="m-4">Margin</div>      <!-- margin: 1rem -->
<div class="p-4">Padding</div>     <!-- padding: 1rem -->
<div class="mx-auto">Center</div>  <!-- margin: 0 auto -->

<!-- Colors -->
<div class="bg-red-500">Background</div>
<div class="text-blue-600">Text</div>
<div class="border-green-400">Border</div>

<!-- Typography -->
<h1 class="text-3xl font-bold">Heading</h1>
<p class="text-sm text-gray-600">Paragraph</p>

<!-- Layout -->
<div class="flex justify-center items-center">Center</div>
<div class="grid grid-cols-3 gap-4">Grid</div>

<!-- Sizing -->
<div class="w-1/2 h-64">Half width, fixed height</div>

<!-- Responsive -->
<div class="text-sm md:text-base lg:text-lg">Responsive text</div>

<!-- Hover/Active States -->
<button class="bg-blue-500 hover:bg-blue-700 active:bg-blue-900">Button</button>
```

## Q3: What are Tailwind Responsive Breakpoints?
**Answer:**

```html
<!-- Mobile first approach -->
<div class="w-full md:w-1/2 lg:w-1/3 xl:w-1/4">
  Full width on mobile
  Half width on tablet (md)
  Third on desktop (lg)
  Quarter on large (xl)
</div>

<!-- Tailwind Breakpoints -->
sm: 640px
md: 768px
lg: 1024px
xl: 1280px
2xl: 1536px

<!-- Custom breakpoint -->
3xl: 1920px (custom)
```

**Container queries:**
```html
<div class="container">
  Content stays centered
</div>
```

## Q4: How do you create custom components with Tailwind?
**Answer:**

**Using @apply:**
```css
/* styles.css */
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer components {
  .btn {
    @apply px-4 py-2 rounded font-medium transition-colors;
  }

  .btn-primary {
    @apply btn bg-blue-500 text-white hover:bg-blue-600;
  }

  .btn-secondary {
    @apply btn bg-gray-300 text-gray-800 hover:bg-gray-400;
  }
}
```

**Using it in HTML:**
```html
<button class="btn-primary">Primary Button</button>
<button class="btn-secondary">Secondary Button</button>
```

**Component approach (React):**
```jsx
function Button({ variant = 'primary', children, ...props }) {
  const variants = {
    primary: 'bg-blue-500 text-white hover:bg-blue-600',
    secondary: 'bg-gray-300 text-gray-800 hover:bg-gray-400',
    danger: 'bg-red-500 text-white hover:bg-red-600'
  };

  return (
    <button className={`px-4 py-2 rounded font-medium ${variants[variant]}`} {...props}>
      {children}
    </button>
  );
}
```

## Q5: What is Tailwind CSS Plugins?
**Answer:**

**Using built-in plugins:**
```js
// tailwind.config.js
module.exports = {
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography'),
    require('@tailwindcss/aspect-ratio'),
  ]
}
```

**Creating custom plugin:**
```js
// tailwind.config.js
const plugin = require('tailwindcss/plugin');

module.exports = {
  plugins: [
    plugin(function({ addUtilities, theme }) {
      const newUtilities = {
        '.skew-10deg': {
          transform: 'skewY(-10deg)',
        },
        '.skew-10deg-neg': {
          transform: 'skewY(10deg)',
        },
      };

      addUtilities(newUtilities);
    })
  ]
}
```

## Q6: What are Tailwind Color Customizations?
**Answer:**

```js
// tailwind.config.js
module.exports = {
  theme: {
    extend: {
      colors: {
        // Add custom color
        primary: '#3498db',
        
        // Add color palette
        brand: {
          50: '#f0f9ff',
          100: '#e0f2fe',
          500: '#3498db',
          900: '#0c2e50',
        },
        
        // Override default
        gray: {
          50: '#f9fafb',
          100: '#f3f4f6',
          900: '#111827',
        }
      }
    }
  }
}
```

**Usage:**
```html
<div class="bg-primary">Primary color</div>
<div class="bg-brand-500">Brand color</div>
<div class="text-brand-900">Dark text</div>
```

## Q7: What are Tailwind Dark Mode?
**Answer:**

**Configuration:**
```js
// tailwind.config.js
module.exports = {
  darkMode: 'class', // or 'media'
  theme: {
    extend: {}
  }
}
```

**Usage:**
```html
<!-- Enable dark mode -->
<html class="dark">
  <body class="bg-white dark:bg-gray-900 text-gray-900 dark:text-white">
    Content
  </body>
</html>
```

**React example:**
```jsx
function App() {
  const [isDark, setIsDark] = useState(false);

  useEffect(() => {
    if (isDark) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  }, [isDark]);

  return (
    <div className="bg-white dark:bg-gray-900 text-gray-900 dark:text-white">
      <button onClick={() => setIsDark(!isDark)}>
        Toggle Dark Mode
      </button>
    </div>
  );
}
```

## Q8: Tailwind vs Regular CSS
**Answer:**

| Feature | Tailwind | Regular CSS |
|---------|----------|-------------|
| **Approach** | Utility-first | Custom writing |
| **Learning Curve** | Moderate | Low (but needs discipline) |
| **Bundle Size** | Larger but purged | Smaller (less used) |
| **Development Speed** | Faster | Slower |
| **Consistency** | High (enforced) | Low (team dependent) |
| **Customization** | Easy | Maximum |
| **Maintenance** | Easy | Complex (naming) |

## Q9: Tailwind Best Practices
**Answer:**

```html
<!-- 1. Use component layer for repeated patterns -->
<!-- Instead of duplicating classes -->
<style>
  @layer components {
    .card { @apply p-4 bg-white rounded shadow; }
  }
</style>

<div class="card">Content</div>

<!-- 2. Keep markup readable -->
<!-- ❌ Hard to read -->
<div class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white shadow-lg rounded-lg p-8 max-w-md w-full">

<!-- ✅ More readable -->
<div class="
  absolute inset-1/2 transform -translate-x-1/2 -translate-y-1/2
  bg-white shadow-lg rounded-lg p-8 max-w-md w-full
">

<!-- 3. Extract repeated patterns -->
<!-- Create component for reusable patterns -->

<!-- 4. Use arbitrary values when needed -->
<div class="top-[117px]">Arbitrary value</div>

<!-- 5. Group related classes -->
<div class="
  <!-- Layout -->
  flex items-center justify-between
  <!-- Spacing -->
  p-4 gap-2
  <!-- Colors -->
  bg-white text-gray-900
  <!-- Responsive -->
  flex-col md:flex-row
">

<!-- 6. Use extends for custom colors -->
<!-- In tailwind.config.js -->
theme: {
  extend: {
    colors: {
      brand: '#3498db'
    }
  }
}
```

## Q10: Tailwind Performance Tips
**Answer:**

```js
// 1. Only include content you use
// tailwind.config.js
module.exports = {
  content: [
    './src/**/*.{js,jsx,ts,tsx}',
    './public/index.html',
  ]
}

// 2. Purge unused styles in production
// Automatically done with proper content config

// 3. Use CSS variables for dynamic colors
// app.css
:root {
  --color-primary: 52, 152, 219; /* RGB values */
}

.primary {
  @apply bg-opacity-100;
  background-color: rgb(var(--color-primary) / 1);
}

// 4. Lazy load plugins
const withOptimizedImages = require('next-image-export-optimizer');

// 5. Limit custom configurations
// Only add what you actually need
```

