# SCSS/Sass - Interview Questions

## Q1: What is SCSS and why use it?
**Answer:**
SCSS (Sassy CSS) is a preprocessor that extends CSS with variables, nesting, and mixins, making stylesheets more maintainable.

```scss
// Variables
$primary-color: #3498db;
$border-radius: 4px;

// Usage
.button {
  background-color: $primary-color;
  border-radius: $border-radius;
  padding: 10px 20px;
  border: none;
  cursor: pointer;

  &:hover {
    background-color: darken($primary-color, 10%);
  }
}

// Compiles to:
// .button { background-color: #3498db; ... }
// .button:hover { background-color: ... }
```

## Q2: What are SCSS Variables?
**Answer:**
```scss
// Syntax: $variable-name: value;

// Colors
$primary: #3498db;
$secondary: #2ecc71;
$danger: #e74c3c;

// Sizes
$spacing-unit: 8px;
$border-radius: 4px;
$font-size-base: 16px;

// Usage
.container {
  background: $primary;
  padding: $spacing-unit * 2;
  border-radius: $border-radius;
  font-size: $font-size-base;
}

// Scope
$global: red;

.element {
  $local: blue;      // Local to this block
  color: $local;
}
```

## Q3: What is SCSS Nesting?
**Answer:**
```scss
.card {
  padding: 20px;
  border: 1px solid #ddd;

  // Nested selectors
  .header {
    font-size: 1.5em;
    font-weight: bold;
  }

  .body {
    margin: 10px 0;
  }

  .footer {
    color: #999;
    font-size: 0.9em;
  }

  // Parent selector reference
  &:hover {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }

  &.active {
    border-color: #3498db;
  }

  // BEM naming
  &__title {
    font-weight: bold;
  }

  &--large {
    padding: 40px;
  }
}

// Compiles to:
// .card { padding: 20px; ... }
// .card .header { ... }
// .card:hover { ... }
// .card.active { ... }
```

## Q4: What are SCSS Mixins?
**Answer:**
Mixins are reusable blocks of code that can be included in other rules.

```scss
// Basic mixin
@mixin button-reset {
  border: none;
  padding: 0;
  background: none;
  cursor: pointer;
}

.custom-button {
  @include button-reset;
  color: #3498db;
}

// Mixin with parameters
@mixin flexbox($direction: row, $justify: center, $align: center) {
  display: flex;
  flex-direction: $direction;
  justify-content: $justify;
  align-items: $align;
}

.container {
  @include flexbox(column, space-between);
}

// Mixin with content block
@mixin responsive($breakpoint) {
  @if $breakpoint == 'mobile' {
    @media (max-width: 768px) {
      @content;
    }
  } @else if $breakpoint == 'desktop' {
    @media (min-width: 1024px) {
      @content;
    }
  }
}

.element {
  @include responsive('mobile') {
    font-size: 14px;
  }
}

// Mixin with vendor prefixes
@mixin transition($property: all, $duration: 0.3s, $timing: ease) {
  -webkit-transition: $property $duration $timing;
  -moz-transition: $property $duration $timing;
  transition: $property $duration $timing;
}

.button {
  @include transition(background-color, 0.3s);
}
```

## Q5: What are SCSS Functions?
**Answer:**
```scss
// Color functions
$primary: #3498db;

.button {
  background: $primary;
  border: 1px solid darken($primary, 20%);  // 20% darker
  
  &:hover {
    background: lighten($primary, 10%);     // 10% lighter
  }

  &:disabled {
    background: desaturate($primary, 50%);  // Remove color
  }
}

// Math functions
$base-size: 16px;

.text-small { font-size: round($base-size * 0.75); }
.text-large { font-size: ceil($base-size * 1.5); }
.text-ratio { font-size: percentage($base-size / 12); }

// String functions
$name: "button";
.#{$name}-primary {
  color: blue;
}

// List functions
$colors: red, green, blue;
@each $color in $colors {
  .bg-#{$color} {
    background-color: $color;
  }
}

// Custom function
@function calculate-size($base, $multiplier) {
  @return $base * $multiplier;
}

.element {
  width: calculate-size(100px, 2);  // 200px
}
```

## Q6: What are SCSS Control Directives?
**Answer:**

**@if / @else:**
```scss
$theme: 'dark';

@if $theme == 'dark' {
  body {
    background: #1a1a1a;
    color: #fff;
  }
} @else if $theme == 'light' {
  body {
    background: #fff;
    color: #000;
  }
} @else {
  body {
    background: #f5f5f5;
    color: #333;
  }
}
```

**@for:**
```scss
@for $i from 1 through 3 {
  .col-#{$i} {
    width: (100% / $i);
  }
}

// Generates:
// .col-1 { width: 100%; }
// .col-2 { width: 50%; }
// .col-3 { width: 33.333%; }
```

**@each:**
```scss
$sizes: ('small': 12px, 'medium': 16px, 'large': 20px);

@each $name, $size in $sizes {
  .text-#{$name} {
    font-size: $size;
  }
}
```

**@while:**
```scss
$i: 1;
@while $i <= 3 {
  .item-#{$i} {
    margin: $i * 10px;
  }
  $i: $i + 1;
}
```

## Q7: What are SCSS Extends?
**Answer:**
Extends allow you to share a set of CSS properties from one selector to another.

```scss
// Base styles
%btn-base {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

// Extend to multiple selectors
.btn-primary {
  @extend %btn-base;
  background: #3498db;
  color: white;
}

.btn-secondary {
  @extend %btn-base;
  background: #95a5a6;
  color: white;
}

// Compiles to:
// .btn-primary, .btn-secondary { padding: 10px 20px; ... }
// .btn-primary { background: #3498db; ... }
// .btn-secondary { background: #95a5a6; ... }
```

**Extends vs Mixins:**
```scss
// Mixin - includes all code
@mixin button-styles {
  padding: 10px 20px;
  border-radius: 4px;
}

// Creates duplicated CSS
.btn-primary { @include button-styles; }
.btn-secondary { @include button-styles; }

// Extend - groups selectors
%button-styles {
  padding: 10px 20px;
  border-radius: 4px;
}

// More efficient - groups selectors
.btn-primary { @extend %button-styles; }
.btn-secondary { @extend %button-styles; }
```

## Q8: What are SCSS Imports?
**Answer:**
```scss
// _variables.scss
$primary: #3498db;
$secondary: #2ecc71;

// _mixins.scss
@mixin flexbox {
  display: flex;
}

// main.scss
@import 'variables';
@import 'mixins';
@import 'components/button';
@import 'components/card';
@import 'layouts/grid';

// Usage
body {
  @include flexbox;
  background: $primary;
}
```

## Q9: What is SCSS Partials?
**Answer:**
Partials are SCSS files that start with underscore (_) and are meant to be imported.

```scss
// _colors.scss
$primary: #3498db;

// _typography.scss
$font-base: 16px;

// main.scss
@import 'colors';
@import 'typography';
```

## Q10: Best Practices for SCSS
**Answer:**

```scss
// 1. Use 7-1 Architecture
// sass/
//   abstracts/     (_variables, _mixins, _functions)
//   base/          (_reset, _typography)
//   components/    (_buttons, _cards, _forms)
//   layout/        (_navigation, _header, _footer)
//   pages/         (_home, _about)
//   themes/        (_dark, _light)
//   vendors/       (normalize, bootstrap)
//   main.scss      (imports everything)

// 2. Use meaningful variable names
$color-primary-default: #3498db;
$color-primary-hover: #2980b9;
$spacing-unit: 8px;

// 3. Nest wisely (max 3 levels)
.card {
  .header {
    .title { }  // ✓ OK (3 levels)
  }
}

// 4. Use mixins for browser prefixes
@mixin vendor($prop, $value) {
  -webkit-#{$prop}: $value;
  -moz-#{$prop}: $value;
  #{$prop}: $value;
}

// 5. Keep it DRY (Don't Repeat Yourself)
@mixin border-radius($radius) {
  border-radius: $radius;
}

// 6. Use extends for related styles
%input-base {
  padding: 8px;
  border: 1px solid #ddd;
}
```

