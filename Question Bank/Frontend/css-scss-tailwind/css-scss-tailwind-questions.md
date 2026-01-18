# CSS/SCSS/Tailwind Interview Questions Bank

## Quick Reference

### CSS Questions

**Q1: What is the difference between margin and padding?**
- **Margin**: Space outside the element's border
- **Padding**: Space inside the element, between border and content

**Q2: Explain the CSS cascade.**
CSS rules are applied based on: Origin → Specificity → Order

**Q3: What is the difference between display: none and visibility: hidden?**
- `display: none`: Element is removed from flow
- `visibility: hidden`: Element is hidden but takes up space

**Q4: What are pseudo-classes?**
Keywords added to selectors that specify a special state: `:hover`, `:focus`, `:active`, `:first-child`

**Q5: What is the difference between justify-content and align-items?**
- `justify-content`: Aligns items along main axis (flexbox)
- `align-items`: Aligns items along cross axis (flexbox)

**Q6: Explain the difference between relative and absolute positioning.**
- **Relative**: Positioned relative to its normal position
- **Absolute**: Positioned relative to nearest positioned parent

**Q7: What is the CSS Box Model?**
Content → Padding → Border → Margin

**Q8: What is em vs rem?**
- **em**: Relative to element's font-size (2em = 2x element's size)
- **rem**: Relative to root's font-size (2rem = 2x root size)

**Q9: What is CSS specificity?**
ID (100) > Class/Attribute/Pseudo (10) > Element (1)

**Q10: Explain CSS Variables.**
Variables defined with `--name` and used with `var(--name)`. Can be scoped and dynamic.

---

### SCSS Questions

**Q1: What is the advantage of using SCSS over CSS?**
- Variables for reusable values
- Nesting for organized code
- Mixins for DRY code
- Functions for calculations
- Imports for modularization

**Q2: What is the difference between @extend and @include?**
- `@extend`: Shares CSS (groups selectors)
- `@include`: Copies CSS (duplicates code)

**Q3: What are SCSS Variables used for?**
Storing reusable values: colors, sizes, fonts, breakpoints

**Q4: Explain nesting in SCSS.**
Allows writing nested CSS selectors within parent selectors for better organization.

**Q5: What is a SCSS mixin?**
A reusable block of CSS code that can be included in other rules using `@include`.

**Q6: How do you import SCSS files?**
Using `@import 'filename'` (omit underscore and extension)

**Q7: What are SCSS functions?**
Built-in functions for calculations: `darken()`, `lighten()`, `round()`, `percentage()`

**Q8: Explain SCSS control directives.**
`@if`, `@else`, `@for`, `@each`, `@while` for conditional and loop logic

**Q9: What is a partial in SCSS?**
A file starting with `_` meant to be imported into other files: `_variables.scss`

**Q10: What is the 7-1 architecture?**
Folder structure: abstracts, base, components, layout, pages, themes, vendors

---

### Tailwind CSS Questions

**Q1: What is Tailwind CSS?**
A utility-first CSS framework providing low-level utility classes for custom design.

**Q2: What are utility classes?**
Single-purpose CSS classes: `p-4` (padding), `bg-blue-500` (background)

**Q3: How does Tailwind handle responsiveness?**
Using breakpoint prefixes: `md:`, `lg:`, `xl:` for responsive classes

**Q4: What are Tailwind breakpoints?**
- sm: 640px
- md: 768px
- lg: 1024px
- xl: 1280px
- 2xl: 1536px

**Q5: How do you customize Tailwind?**
Using `tailwind.config.js` to extend or override theme, add plugins

**Q6: What is @apply in Tailwind?**
CSS at-rule to extract groups of utility classes into component classes

**Q7: How do you create custom components in Tailwind?**
Using `@apply` in CSS or creating React components that apply Tailwind classes

**Q8: What are Tailwind Plugins?**
Extensions that add custom utilities: `@tailwindcss/forms`, `@tailwindcss/typography`

**Q9: How do you implement dark mode in Tailwind?**
Set `darkMode: 'class'` in config, use `dark:` prefix for dark styles

**Q10: Tailwind vs CSS Framework (Bootstrap)?**
- Tailwind: Utility-first, more flexible, larger initial bundle
- Bootstrap: Component-based, faster setup, less customizable

---

## Scenario Questions

**Scenario 1: Responsive Navigation Bar**
Use flexbox with `md:` breakpoint to change from mobile to desktop layout.

**Scenario 2: Dark Mode Toggle**
Use CSS variables or Tailwind's `dark:` prefix with JavaScript toggle.

**Scenario 3: Reusable Button Component**
Create SCSS mixin or Tailwind component class with variants.

**Scenario 4: Custom Theme Colors**
SCSS: Store in variables file
Tailwind: Extend theme in config.js

**Scenario 5: Optimized Production Build**
CSS: Minify with PostCSS
Tailwind: Tree-shake unused utilities
SCSS: Minify compiled CSS

---

## Performance Tips

1. **Minimize CSS**: Use production builds, minification
2. **Critical CSS**: Inline above-the-fold styles
3. **CSS-in-JS**: Load asynchronously when possible
4. **Purge unused**: Tailwind automatically purges unused utilities
5. **Caching**: Cache static CSS files
6. **Compression**: Use gzip/brotli compression
7. **Media queries**: Optimize media query organization
8. **Selectors**: Keep CSS specificity low

