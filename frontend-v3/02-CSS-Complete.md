# CSS - Complete Interview Questions & Answers (Part 1)

## **CSS Fundamentals**

### 1. What is CSS and why is it used?
**Answer:** CSS (Cascading Style Sheets) is a stylesheet language used to describe the presentation and styling of HTML documents. It controls layout, colors, fonts, spacing, and visual appearance.

**Benefits:**
- Separates content from presentation
- Reusable styles across multiple pages
- Easier maintenance
- Responsive design capabilities
- Better performance (cached stylesheets)

### 2. What are the different ways to add CSS?
**Answer:**

**1. Inline CSS:**
```html
<p style="color: red; font-size: 16px;">Text</p>
```
- Highest priority
- Not reusable
- Hard to maintain

**2. Internal CSS:**
```html
<head>
  <style>
    p { color: red; }
  </style>
</head>
```
- Applies to single page
- Better than inline
- Still not reusable

**3. External CSS:**
```html
<head>
  <link rel="stylesheet" href="style.css">
</head>
```
- Most recommended
- Reusable across pages
- Cached by browser
- Easy to maintain

**Priority:** Inline > Internal > External > Browser Default

### 3. What is the CSS Box Model?
**Answer:** Every HTML element is a rectangular box consisting of:

```
┌─────────────────────────────┐
│         Margin              │
│  ┌──────────────────────┐   │
│  │      Border          │   │
│  │  ┌───────────────┐   │   │
│  │  │   Padding     │   │   │
│  │  │  ┌────────┐   │   │   │
│  │  │  │Content │   │   │   │
│  │  │  └────────┘   │   │   │
│  │  └───────────────┘   │   │
│  └──────────────────────┘   │
└─────────────────────────────┘
```

```css
.box {
  width: 200px;           /* Content width */
  height: 100px;          /* Content height */
  padding: 20px;          /* Space inside border */
  border: 5px solid black;/* Border */
  margin: 10px;           /* Space outside border */
}
```

**Total Width = width + padding-left + padding-right + border-left + border-right + margin-left + margin-right**

**Example:**
```css
.box {
  width: 200px;
  padding: 20px;    /* 20px left + 20px right = 40px */
  border: 5px solid;/* 5px left + 5px right = 10px */
  margin: 10px;     /* 10px left + 10px right = 20px */
}
/* Total width = 200 + 40 + 10 + 20 = 270px */
```

### 4. What is box-sizing property?
**Answer:**

```css
/* Default - content-box */
.box1 {
  box-sizing: content-box;
  width: 200px;
  padding: 20px;
  border: 5px solid;
  /* Total width = 200 + 40 + 10 = 250px */
}

/* border-box (recommended) */
.box2 {
  box-sizing: border-box;
  width: 200px;
  padding: 20px;
  border: 5px solid;
  /* Total width = 200px (includes padding & border) */
}
```

**Best Practice:**
```css
*, *::before, *::after {
  box-sizing: border-box;
}
```

**Why border-box is better:**
- Easier calculations
- Predictable sizing
- Better for responsive design

### 5. What is CSS Specificity?
**Answer:** Specificity determines which CSS rule applies when multiple rules target the same element.

**Specificity Hierarchy (points):**
1. Inline styles: 1000
2. IDs: 100
3. Classes, attributes, pseudo-classes: 10
4. Elements, pseudo-elements: 1

```css
/* Specificity: 1 */
p { color: black; }

/* Specificity: 10 */
.text { color: blue; }

/* Specificity: 100 */
#unique { color: green; }

/* Specificity: 1000 */
<p style="color: red;">Text</p>

/* Specificity: 111 (100 + 10 + 1) */
#unique p.text { color: purple; }

/* Specificity: 21 (10 + 10 + 1) */
.container .text p { color: orange; }
```

**!important overrides everything:**
```css
p { color: red !important; }  /* Highest priority */
```

**Best Practice:** Avoid !important, increase specificity instead.

### 6. What are CSS Selectors?
**Answer:**

```css
/* Universal Selector */
* { margin: 0; padding: 0; }

/* Element Selector */
p { color: black; }
h1 { font-size: 32px; }

/* Class Selector */
.container { width: 100%; }
.btn { padding: 10px; }

/* ID Selector */
#header { height: 60px; }

/* Descendant Selector (space) */
div p { color: blue; }  /* All p inside div */

/* Child Selector (>) */
div > p { color: red; }  /* Direct children only */

/* Adjacent Sibling (+) */
h1 + p { margin-top: 0; }  /* p immediately after h1 */

/* General Sibling (~) */
h1 ~ p { color: gray; }  /* All p siblings after h1 */

/* Attribute Selector */
input[type="text"] { border: 1px solid; }
a[href^="https"] { color: green; }  /* Starts with */
a[href$=".pdf"] { color: red; }     /* Ends with */
a[href*="google"] { color: blue; }  /* Contains */

/* Multiple Selectors */
h1, h2, h3 { font-family: Arial; }

/* Combining Selectors */
div.container { }  /* div with class container */
p#intro { }        /* p with id intro */
```

### 7. What are pseudo-classes?
**Answer:** Pseudo-classes select elements based on their state.

```css
/* Link states */
a:link { color: blue; }
a:visited { color: purple; }
a:hover { color: red; }
a:active { color: orange; }
a:focus { outline: 2px solid blue; }

/* Form states */
input:focus { border-color: blue; }
input:disabled { opacity: 0.5; }
input:enabled { cursor: pointer; }
input:checked { background: green; }
input:required { border-color: red; }
input:optional { border-color: gray; }
input:valid { border-color: green; }
input:invalid { border-color: red; }

/* Structural pseudo-classes */
li:first-child { font-weight: bold; }
li:last-child { margin-bottom: 0; }
li:nth-child(2) { color: red; }
li:nth-child(odd) { background: #f0f0f0; }
li:nth-child(even) { background: white; }
li:nth-child(3n) { color: blue; }  /* Every 3rd */
li:nth-last-child(2) { color: green; }

/* Type-based */
p:first-of-type { font-size: 20px; }
p:last-of-type { margin-bottom: 0; }
p:nth-of-type(2) { color: red; }

/* Other pseudo-classes */
p:not(.special) { color: gray; }
div:empty { display: none; }
:root { --primary: blue; }
```

### 8. What are pseudo-elements?
**Answer:** Pseudo-elements style specific parts of elements.

```css
/* Insert content */
p::before {
  content: "→ ";
  color: blue;
}

p::after {
  content: " ←";
  color: red;
}

/* Style first letter */
p::first-letter {
  font-size: 2em;
  font-weight: bold;
  float: left;
}

/* Style first line */
p::first-line {
  font-weight: bold;
  color: blue;
}

/* Selection styling */
::selection {
  background: yellow;
  color: black;
}

/* Placeholder styling */
input::placeholder {
  color: gray;
  font-style: italic;
}

/* Marker styling (list bullets) */
li::marker {
  color: red;
  font-size: 1.5em;
}
```

**Note:** Use double colon (::) for pseudo-elements, single colon (:) for pseudo-classes.

### 9. What is the difference between margin and padding?
**Answer:**

| Feature | Margin | Padding |
|---------|--------|---------|
| Location | Outside border | Inside border |
| Background | Transparent | Shows element background |
| Negative Values | Allowed | Not allowed |
| Collapse | Vertical margins collapse | Never collapses |
| Use Case | Space between elements | Space inside element |

```css
.box {
  margin: 20px;      /* Space OUTSIDE border */
  padding: 20px;     /* Space INSIDE border */
  border: 2px solid black;
  background: lightblue;
}

/* Margin shows parent background */
/* Padding shows element background */
```

**Margin Collapse:**
```css
.box1 { margin-bottom: 30px; }
.box2 { margin-top: 20px; }
/* Actual gap = 30px (not 50px) - margins collapse */
```

### 10. What is margin collapse and how to prevent it?
**Answer:** Vertical margins of adjacent elements combine into a single margin.

**When it happens:**
- Adjacent siblings
- Parent and first/last child
- Empty blocks

```css
/* Example */
.box1 { margin-bottom: 30px; }
.box2 { margin-top: 20px; }
/* Gap = 30px (larger margin wins) */
```

**Prevention methods:**
```css
/* 1. Use padding instead */
.parent { padding-top: 20px; }

/* 2. Add border */
.parent { border-top: 1px solid transparent; }

/* 3. Add overflow */
.parent { overflow: hidden; }

/* 4. Use flexbox/grid */
.parent { display: flex; flex-direction: column; }

/* 5. Add clearfix */
.parent::before { content: ""; display: table; }
```

---

## **CSS Layout**

### 11. What is Flexbox?
**Answer:** One-dimensional layout system for arranging items in rows or columns.

**Container Properties:**
```css
.container {
  display: flex;
  
  /* Direction */
  flex-direction: row;           /* Default */
  flex-direction: column;
  flex-direction: row-reverse;
  flex-direction: column-reverse;
  
  /* Wrapping */
  flex-wrap: nowrap;             /* Default */
  flex-wrap: wrap;
  flex-wrap: wrap-reverse;
  
  /* Main axis alignment */
  justify-content: flex-start;   /* Default */
  justify-content: center;
  justify-content: flex-end;
  justify-content: space-between;
  justify-content: space-around;
  justify-content: space-evenly;
  
  /* Cross axis alignment */
  align-items: stretch;          /* Default */
  align-items: flex-start;
  align-items: center;
  align-items: flex-end;
  align-items: baseline;
  
  /* Multiple lines */
  align-content: stretch;
  align-content: flex-start;
  align-content: center;
  align-content: space-between;
  
  /* Gap */
  gap: 20px;
  row-gap: 10px;
  column-gap: 15px;
}
```

**Item Properties:**
```css
.item {
  /* Shorthand: grow shrink basis */
  flex: 1;              /* flex: 1 1 0% */
  flex: 0 1 auto;       /* Default */
  
  /* Individual properties */
  flex-grow: 1;         /* Grow to fill space */
  flex-shrink: 1;       /* Shrink if needed */
  flex-basis: auto;     /* Initial size */
  
  /* Override alignment */
  align-self: center;
  
  /* Change order */
  order: 2;
}
```

### 12. What is CSS Grid?
**Answer:** Two-dimensional layout system for rows and columns.

```css
.container {
  display: grid;
  
  /* Define columns */
  grid-template-columns: 200px 1fr 2fr;
  grid-template-columns: repeat(3, 1fr);
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  
  /* Define rows */
  grid-template-rows: 100px auto 50px;
  grid-template-rows: repeat(3, 100px);
  
  /* Gap */
  gap: 20px;
  row-gap: 10px;
  column-gap: 15px;
  
  /* Alignment */
  justify-items: start | center | end | stretch;
  align-items: start | center | end | stretch;
  justify-content: start | center | end | space-between;
  align-content: start | center | end | space-between;
  
  /* Named areas */
  grid-template-areas:
    "header header header"
    "sidebar main main"
    "footer footer footer";
}

.item {
  /* Span columns */
  grid-column: 1 / 3;
  grid-column: span 2;
  grid-column-start: 1;
  grid-column-end: 3;
  
  /* Span rows */
  grid-row: 1 / 4;
  grid-row: span 2;
  
  /* Shorthand */
  grid-area: 1 / 1 / 3 / 3;  /* row-start / col-start / row-end / col-end */
  
  /* Named area */
  grid-area: header;
  
  /* Alignment */
  justify-self: center;
  align-self: center;
}
```

### 13. What is the difference between Flexbox and Grid?
**Answer:**

| Feature | Flexbox | Grid |
|---------|---------|------|
| Dimension | 1D (row OR column) | 2D (rows AND columns) |
| Use Case | Components, small layouts | Page layouts, complex structures |
| Content | Content-first (flexible) | Layout-first (fixed structure) |
| Alignment | One direction at a time | Both directions simultaneously |
| Browser Support | Better (older browsers) | Good (modern browsers) |

**When to use:**
- **Flexbox**: Navigation bars, card layouts, centering, toolbars
- **Grid**: Page layouts, galleries, dashboards, complex structures

**Example:**
```css
/* Flexbox - Navigation */
.nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Grid - Page Layout */
.page {
  display: grid;
  grid-template-areas:
    "header header"
    "sidebar main"
    "footer footer";
}
```

### 14. What are CSS position values?
**Answer:**

```css
/* Static (default) - normal flow */
.static {
  position: static;
  /* top, right, bottom, left have no effect */
}

/* Relative - relative to normal position */
.relative {
  position: relative;
  top: 10px;      /* Moves 10px down from normal position */
  left: 20px;     /* Moves 20px right */
  /* Original space is preserved */
}

/* Absolute - relative to nearest positioned ancestor */
.parent {
  position: relative;  /* Creates positioning context */
}
.absolute {
  position: absolute;
  top: 0;
  right: 0;
  /* Removed from normal flow */
  /* Positioned relative to .parent */
}

/* Fixed - relative to viewport */
.fixed {
  position: fixed;
  bottom: 0;
  right: 0;
  /* Stays in place when scrolling */
}

/* Sticky - hybrid of relative and fixed */
.sticky {
  position: sticky;
  top: 0;
  /* Acts relative until scroll threshold, then fixed */
}
```

**Use Cases:**
- **Relative**: Slight adjustments, positioning context
- **Absolute**: Tooltips, dropdowns, overlays
- **Fixed**: Headers, footers, floating buttons
- **Sticky**: Table headers, navigation bars

### 15. What is the z-index property?
**Answer:** Controls stacking order of positioned elements.

```css
.layer1 {
  position: relative;
  z-index: 1;
}

.layer2 {
  position: absolute;
  z-index: 10;      /* Appears above layer1 */
}

.layer3 {
  position: fixed;
  z-index: 100;     /* Appears above all */
}

/* Negative z-index */
.background {
  position: absolute;
  z-index: -1;      /* Behind parent */
}
```

**Rules:**
- Only works on positioned elements (not static)
- Higher value = on top
- Creates stacking context
- Default z-index is auto (0)

**Stacking Context:**
```css
.parent {
  position: relative;
  z-index: 1;
}
.child {
  position: absolute;
  z-index: 9999;  /* Still behind elements with z-index: 2 outside parent */
}
```

---

**Continue to Part 2 for more CSS questions...**

## **Display & Visibility**

### 16. What is the difference between display: none and visibility: hidden?
**Answer:**

| Property | Space Occupied | DOM Present | Events | Screen Readers |
|----------|----------------|-------------|--------|----------------|
| `display: none` | No | Yes | No | Not read |
| `visibility: hidden` | Yes | Yes | No | Not read |
| `opacity: 0` | Yes | Yes | Yes | Read |

```css
.hidden1 {
  display: none;        /* Removes from layout completely */
}

.hidden2 {
  visibility: hidden;   /* Invisible but takes space */
}

.hidden3 {
  opacity: 0;           /* Transparent but interactive */
}
```

### 17. What are display property values?
**Answer:**

```css
/* Block - full width, new line */
div { display: block; }

/* Inline - content width, same line */
span { display: inline; }

/* Inline-block - hybrid */
button { display: inline-block; }

/* Flex container */
.container { display: flex; }

/* Grid container */
.container { display: grid; }

/* Table display */
.table { display: table; }
.row { display: table-row; }
.cell { display: table-cell; }

/* None - hidden */
.hidden { display: none; }
```

---

## **CSS Units**

### 18. What are CSS units?
**Answer:**

**Absolute Units:**
```css
px    /* Pixels - most common */
pt    /* Points (1pt = 1/72 inch) */
cm    /* Centimeters */
mm    /* Millimeters */
in    /* Inches */
pc    /* Picas (1pc = 12pt) */
```

**Relative Units:**
```css
%     /* Percentage of parent */
em    /* Relative to parent font-size */
rem   /* Relative to root (html) font-size */
vw    /* 1% of viewport width */
vh    /* 1% of viewport height */
vmin  /* 1% of smaller viewport dimension */
vmax  /* 1% of larger viewport dimension */
ch    /* Width of "0" character */
ex    /* Height of "x" character */
```

**Examples:**
```css
html { font-size: 16px; }

.parent {
  font-size: 20px;
}

.child {
  font-size: 2em;    /* 40px (2 × parent 20px) */
  font-size: 2rem;   /* 32px (2 × root 16px) */
  width: 50%;        /* 50% of parent width */
  height: 100vh;     /* Full viewport height */
  padding: 1vw;      /* 1% of viewport width */
}
```

### 19. What is the difference between em and rem?
**Answer:**

```css
html { font-size: 16px; }

.parent {
  font-size: 20px;
}

.child {
  /* em - relative to parent */
  font-size: 2em;    /* 40px (2 × 20px) */
  padding: 1em;      /* 40px (based on own font-size) */
  
  /* rem - relative to root */
  font-size: 2rem;   /* 32px (2 × 16px) */
  padding: 1rem;     /* 16px (always based on root) */
}
```

**When to use:**
- **em**: Component-based scaling, buttons, spacing within components
- **rem**: Consistent sizing, typography, layout spacing

**Best Practice:** Use rem for predictable sizing.

---

## **Colors & Backgrounds**

### 20. What are CSS color formats?
**Answer:**

```css
/* Named colors */
color: red;
color: blue;

/* Hexadecimal */
color: #ff0000;      /* Red */
color: #f00;         /* Short form */
color: #ff000080;    /* With alpha (50% opacity) */

/* RGB */
color: rgb(255, 0, 0);
color: rgba(255, 0, 0, 0.5);  /* With alpha */

/* HSL (Hue, Saturation, Lightness) */
color: hsl(0, 100%, 50%);     /* Red */
color: hsla(0, 100%, 50%, 0.5);

/* Modern formats */
color: rgb(255 0 0 / 0.5);    /* Space-separated with alpha */
color: hsl(0deg 100% 50% / 0.5);
```

### 21. What are background properties?
**Answer:**

```css
.element {
  /* Color */
  background-color: #f0f0f0;
  
  /* Image */
  background-image: url('image.jpg');
  
  /* Repeat */
  background-repeat: no-repeat;
  background-repeat: repeat-x;
  background-repeat: repeat-y;
  
  /* Position */
  background-position: center;
  background-position: top right;
  background-position: 50% 50%;
  
  /* Size */
  background-size: cover;      /* Fill container */
  background-size: contain;    /* Fit inside */
  background-size: 100% auto;
  
  /* Attachment */
  background-attachment: fixed;   /* Fixed on scroll */
  background-attachment: scroll;  /* Scrolls with page */
  
  /* Origin & Clip */
  background-origin: border-box;
  background-clip: padding-box;
  
  /* Shorthand */
  background: #f0f0f0 url('image.jpg') no-repeat center/cover;
}

/* Multiple backgrounds */
.element {
  background:
    url('front.png') no-repeat center,
    url('back.jpg') no-repeat center/cover;
}
```

---

## **Transitions & Animations**

### 22. What are CSS transitions?
**Answer:** Smooth animation between property values.

```css
.button {
  background: blue;
  color: white;
  padding: 10px 20px;
  
  /* Transition syntax: property duration timing-function delay */
  transition: background 0.3s ease;
}

.button:hover {
  background: red;
}

/* Multiple properties */
.box {
  transition: width 0.3s ease, height 0.5s linear;
}

/* All properties */
.element {
  transition: all 0.3s ease-in-out;
}

/* Individual properties */
.element {
  transition-property: transform, opacity;
  transition-duration: 0.3s, 0.5s;
  transition-timing-function: ease, linear;
  transition-delay: 0s, 0.1s;
}
```

**Timing Functions:**
```css
ease          /* Slow start, fast middle, slow end (default) */
linear        /* Constant speed */
ease-in       /* Slow start */
ease-out      /* Slow end */
ease-in-out   /* Slow start and end */
cubic-bezier(0.1, 0.7, 1.0, 0.1)  /* Custom */
```

### 23. What are CSS animations?
**Answer:** Complex animations with keyframes.

```css
/* Define keyframes */
@keyframes slideIn {
  0% {
    transform: translateX(-100%);
    opacity: 0;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
}

/* Alternative syntax */
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/* Apply animation */
.element {
  animation-name: slideIn;
  animation-duration: 1s;
  animation-timing-function: ease-in-out;
  animation-delay: 0.5s;
  animation-iteration-count: infinite;
  animation-direction: alternate;
  animation-fill-mode: forwards;
  animation-play-state: running;
  
  /* Shorthand: name duration timing delay iteration direction fill-mode */
  animation: slideIn 1s ease-in-out 0.5s infinite alternate forwards;
}

/* Multiple animations */
.element {
  animation: 
    slideIn 1s ease-in-out,
    fadeIn 0.5s ease;
}
```

**Animation Properties:**
- `normal`: Play forward
- `reverse`: Play backward
- `alternate`: Forward then backward
- `alternate-reverse`: Backward then forward

**Fill Mode:**
- `none`: No styles before/after
- `forwards`: Keep final state
- `backwards`: Apply first keyframe before start
- `both`: Both forwards and backwards

### 24. What is the transform property?
**Answer:** Modify element's appearance without affecting layout.

```css
/* Translate (move) */
transform: translate(50px, 100px);
transform: translateX(50px);
transform: translateY(100px);

/* Rotate */
transform: rotate(45deg);
transform: rotateX(45deg);
transform: rotateY(45deg);
transform: rotateZ(45deg);

/* Scale */
transform: scale(1.5);
transform: scale(2, 0.5);  /* x, y */
transform: scaleX(2);
transform: scaleY(0.5);

/* Skew */
transform: skew(10deg, 5deg);
transform: skewX(10deg);
transform: skewY(5deg);

/* Multiple transforms */
transform: translate(50px, 50px) rotate(45deg) scale(1.2);

/* 3D transforms */
transform: perspective(500px) rotateY(45deg);
transform: translate3d(10px, 20px, 30px);

/* Transform origin */
transform-origin: center;
transform-origin: top left;
transform-origin: 50% 50%;
```

---

## **Responsive Design**

### 25. What are media queries?
**Answer:** Apply styles based on device characteristics.

```css
/* Mobile First Approach */
.container {
  width: 100%;
  padding: 10px;
}

/* Tablet (768px and up) */
@media (min-width: 768px) {
  .container {
    width: 750px;
    padding: 20px;
  }
}

/* Desktop (1024px and up) */
@media (min-width: 1024px) {
  .container {
    width: 1000px;
  }
}

/* Large Desktop (1440px and up) */
@media (min-width: 1440px) {
  .container {
    width: 1200px;
  }
}

/* Max-width (Desktop First) */
@media (max-width: 768px) {
  .container {
    width: 100%;
  }
}

/* Range */
@media (min-width: 768px) and (max-width: 1024px) {
  .container {
    width: 750px;
  }
}

/* Orientation */
@media (orientation: landscape) {
  .sidebar {
    display: block;
  }
}

@media (orientation: portrait) {
  .sidebar {
    display: none;
  }
}

/* Print */
@media print {
  .no-print {
    display: none;
  }
  body {
    color: black;
    background: white;
  }
}

/* Dark Mode */
@media (prefers-color-scheme: dark) {
  body {
    background: black;
    color: white;
  }
}

/* Reduced Motion */
@media (prefers-reduced-motion: reduce) {
  * {
    animation: none !important;
    transition: none !important;
  }
}
```

**Common Breakpoints:**
- Mobile: < 768px
- Tablet: 768px - 1024px
- Desktop: > 1024px
- Large Desktop: > 1440px

---

## **Advanced CSS**

### 26. What are CSS variables (Custom Properties)?
**Answer:**

```css
:root {
  --primary-color: #3498db;
  --secondary-color: #2ecc71;
  --spacing: 16px;
  --font-size: 14px;
  --border-radius: 4px;
}

.button {
  background: var(--primary-color);
  padding: var(--spacing);
  font-size: var(--font-size);
  border-radius: var(--border-radius);
}

/* With fallback */
.text {
  color: var(--text-color, black);
}

/* Scoped variables */
.dark-theme {
  --primary-color: #1a1a1a;
  --text-color: white;
}

/* JavaScript access */
const root = document.documentElement;
root.style.setProperty('--primary-color', 'red');
const color = getComputedStyle(root).getPropertyValue('--primary-color');
```

### 27. What is the calc() function?
**Answer:** Perform calculations in CSS.

```css
.container {
  /* Subtract fixed value from percentage */
  width: calc(100% - 50px);
  
  /* Viewport calculations */
  height: calc(100vh - 60px);
  
  /* Mix units */
  padding: calc(1rem + 5px);
  font-size: calc(16px + 0.5vw);
  
  /* Complex calculations */
  width: calc((100% - 40px) / 3);
  margin: calc(var(--spacing) * 2);
}

/* With variables */
:root {
  --header-height: 60px;
  --footer-height: 40px;
}

.content {
  height: calc(100vh - var(--header-height) - var(--footer-height));
}
```

### 28. What is the difference between overflow values?
**Answer:**

```css
/* Hide overflow content */
.box1 {
  overflow: hidden;
}

/* Always show scrollbars */
.box2 {
  overflow: scroll;
}

/* Show scrollbars only when needed */
.box3 {
  overflow: auto;
}

/* Show overflow (default) */
.box4 {
  overflow: visible;
}

/* Separate axes */
.box5 {
  overflow-x: hidden;
  overflow-y: auto;
}

/* Clip content */
.box6 {
  overflow: clip;
}
```

### 29. What is the !important rule?
**Answer:** Overrides all other declarations (use sparingly).

```css
.text {
  color: blue !important;  /* Highest priority */
}

/* Even this won't override */
#unique.text {
  color: red;  /* Won't apply */
}

/* Only another !important with higher specificity can override */
#unique.text.special {
  color: green !important;  /* This will apply */
}
```

**When to use:**
- Overriding third-party CSS
- Utility classes
- Quick fixes (not recommended)

**Better approach:** Increase specificity instead.

### 30. What is the float property?
**Answer:** Positions element to left or right, allowing text to wrap.

```css
.image {
  float: left;
  margin-right: 20px;
  width: 200px;
}

.image-right {
  float: right;
  margin-left: 20px;
}

/* Clear floats */
.clear {
  clear: both;
}

/* Clearfix hack */
.clearfix::after {
  content: "";
  display: table;
  clear: both;
}
```

**Modern Alternative:** Use Flexbox or Grid instead of floats.

---

## **CSS Best Practices**

### 31. What is BEM methodology?
**Answer:** Block Element Modifier naming convention.

```css
/* Block */
.card { }

/* Element (child of block) */
.card__title { }
.card__image { }
.card__description { }

/* Modifier (variation of block/element) */
.card--featured { }
.card--large { }
.card__title--bold { }
```

```html
<div class="card card--featured">
  <img class="card__image" src="image.jpg">
  <h2 class="card__title card__title--bold">Title</h2>
  <p class="card__description">Description</p>
</div>
```

**Benefits:**
- Clear naming structure
- Avoids specificity issues
- Reusable components
- Easy to understand

### 32. What is CSS Reset vs Normalize?
**Answer:**

**CSS Reset:** Removes all default browser styles
```css
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
```

**Normalize.css:** Preserves useful defaults, fixes bugs, improves consistency

**Comparison:**
- Reset: Aggressive, removes everything
- Normalize: Conservative, keeps useful defaults

### 33. What is the difference between nth-child and nth-of-type?
**Answer:**

```css
/* nth-child - counts all children */
li:nth-child(2) {
  color: red;  /* 2nd child regardless of type */
}

/* nth-of-type - counts specific type only */
li:nth-of-type(2) {
  color: blue;  /* 2nd li element */
}
```

```html
<ul>
  <p>Paragraph</p>      <!-- 1st child, not counted by nth-of-type(li) -->
  <li>First li</li>     <!-- 2nd child, 1st li -->
  <li>Second li</li>    <!-- 3rd child, 2nd li -->
</ul>
```

### 34. What is object-fit property?
**Answer:** Controls how images/videos fit their container.

```css
img {
  width: 300px;
  height: 200px;
  
  /* Cover - crop to fill */
  object-fit: cover;
  
  /* Contain - fit inside */
  object-fit: contain;
  
  /* Fill - stretch (default) */
  object-fit: fill;
  
  /* None - original size */
  object-fit: none;
  
  /* Scale-down - smaller of none/contain */
  object-fit: scale-down;
  
  /* Position */
  object-position: center;
  object-position: top right;
}
```

### 35. What are CSS Grid fr units?
**Answer:** Fractional unit for flexible sizing.

```css
.container {
  display: grid;
  
  /* Equal columns */
  grid-template-columns: 1fr 1fr 1fr;
  
  /* Different ratios */
  grid-template-columns: 1fr 2fr 1fr;
  /* First: 25%, Second: 50%, Third: 25% */
  
  /* Mix with fixed */
  grid-template-columns: 200px 1fr 2fr;
  
  /* Repeat */
  grid-template-columns: repeat(3, 1fr);
}
```

---

**Total: 35+ comprehensive CSS questions with detailed interview-ready answers**
