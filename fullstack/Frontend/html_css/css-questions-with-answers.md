# CSS Interview Questions with Answers

## **Most Important & Frequently Asked Questions**

### **1. What is CSS?**
**Answer:** CSS (Cascading Style Sheets) is a stylesheet language used to describe the presentation and styling of HTML documents.

**Purpose:**
- Control layout and design
- Separate content from presentation
- Reusable styles
- Responsive design

---

### **2. What are the different ways to add CSS?**
**Answer:**

**1. Inline CSS:**
```html
<p style="color: red; font-size: 16px;">Text</p>
```

**2. Internal CSS:**
```html
<head>
  <style>
    p { color: red; }
  </style>
</head>
```

**3. External CSS:**
```html
<head>
  <link rel="stylesheet" href="style.css">
</head>
```

**Priority:** Inline > Internal > External > Browser Default

---

### **3. What is the CSS Box Model?**
**Answer:** Every element is a rectangular box with:

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
  border: 5px solid black; /* Border */
  margin: 10px;           /* Space outside border */
}
```

**Total Width = width + padding-left + padding-right + border-left + border-right + margin-left + margin-right**

---

### **4. What is `box-sizing` property?**
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
* {
  box-sizing: border-box;
}
```

---

### **5. What is CSS Specificity?**
**Answer:** Determines which CSS rule applies when multiple rules target the same element.

**Specificity Hierarchy:**
1. **Inline styles** - 1000 points
2. **IDs** - 100 points
3. **Classes, attributes, pseudo-classes** - 10 points
4. **Elements, pseudo-elements** - 1 point

```css
/* Specificity: 1 */
p { color: black; }

/* Specificity: 10 */
.text { color: blue; }

/* Specificity: 100 */
#unique { color: green; }

/* Specificity: 1000 */
<p style="color: red;">Text</p>

/* Specificity: 111 */
#unique p.text { color: purple; }
```

**!important overrides everything (avoid using it)**

---

### **6. What is the difference between `class` and `id` selectors?**
**Answer:**

| Feature | Class | ID |
|---------|-------|-----|
| Syntax | `.className` | `#idName` |
| Reusability | Multiple elements | Single element |
| Specificity | 10 | 100 |
| JavaScript | `getElementsByClassName()` | `getElementById()` |

```css
.button { background: blue; }      /* Can reuse */
#header { background: black; }     /* Unique */
```

---

### **7. What are CSS Selectors?**
**Answer:**

```css
/* Universal Selector */
* { margin: 0; }

/* Element Selector */
p { color: black; }

/* Class Selector */
.container { width: 100%; }

/* ID Selector */
#header { height: 60px; }

/* Descendant Selector */
div p { color: blue; }

/* Child Selector */
div > p { color: red; }

/* Adjacent Sibling */
h1 + p { margin-top: 0; }

/* General Sibling */
h1 ~ p { color: gray; }

/* Attribute Selector */
input[type="text"] { border: 1px solid; }

/* Pseudo-class */
a:hover { color: red; }

/* Pseudo-element */
p::before { content: "→"; }
```

---

### **8. What is the difference between `display: none` and `visibility: hidden`?**
**Answer:**

| Property | Space Occupied | DOM Present | Accessibility |
|----------|----------------|-------------|---------------|
| `display: none` | No | Yes | Not read by screen readers |
| `visibility: hidden` | Yes | Yes | Not read by screen readers |
| `opacity: 0` | Yes | Yes | Read by screen readers |

```css
.hidden1 { display: none; }        /* Removes from layout */
.hidden2 { visibility: hidden; }   /* Invisible but takes space */
.hidden3 { opacity: 0; }           /* Transparent but interactive */
```

---

### **9. What is the difference between `position` values?**
**Answer:**

```css
/* Static (default) - normal flow */
.static { position: static; }

/* Relative - relative to normal position */
.relative {
  position: relative;
  top: 10px;
  left: 20px;
}

/* Absolute - relative to nearest positioned ancestor */
.absolute {
  position: absolute;
  top: 0;
  right: 0;
}

/* Fixed - relative to viewport */
.fixed {
  position: fixed;
  bottom: 0;
  right: 0;
}

/* Sticky - hybrid of relative and fixed */
.sticky {
  position: sticky;
  top: 0;
}
```

---

### **10. What is Flexbox?**
**Answer:** One-dimensional layout system for arranging items in rows or columns.

```css
.container {
  display: flex;
  
  /* Direction */
  flex-direction: row | column | row-reverse | column-reverse;
  
  /* Wrapping */
  flex-wrap: nowrap | wrap | wrap-reverse;
  
  /* Main axis alignment */
  justify-content: flex-start | center | space-between | space-around;
  
  /* Cross axis alignment */
  align-items: stretch | flex-start | center | flex-end;
  
  /* Multiple lines */
  align-content: flex-start | center | space-between;
}

.item {
  flex: 1;              /* flex-grow flex-shrink flex-basis */
  flex-grow: 1;         /* Grow factor */
  flex-shrink: 1;       /* Shrink factor */
  flex-basis: auto;     /* Initial size */
  align-self: center;   /* Override align-items */
  order: 2;             /* Change order */
}
```

---

### **11. What is CSS Grid?**
**Answer:** Two-dimensional layout system for rows and columns.

```css
.container {
  display: grid;
  
  /* Define columns */
  grid-template-columns: 200px 1fr 2fr;
  grid-template-columns: repeat(3, 1fr);
  
  /* Define rows */
  grid-template-rows: 100px auto 50px;
  
  /* Gap between items */
  gap: 20px;
  column-gap: 10px;
  row-gap: 15px;
  
  /* Alignment */
  justify-items: start | center | end | stretch;
  align-items: start | center | end | stretch;
}

.item {
  /* Span columns */
  grid-column: 1 / 3;
  grid-column: span 2;
  
  /* Span rows */
  grid-row: 1 / 4;
  
  /* Shorthand */
  grid-area: 1 / 1 / 3 / 3;
}
```

---

### **12. What is the difference between Flexbox and Grid?**
**Answer:**

| Feature | Flexbox | Grid |
|---------|---------|------|
| Dimension | 1D (row or column) | 2D (rows and columns) |
| Use Case | Components, small layouts | Page layouts |
| Content | Content-first | Layout-first |
| Alignment | One direction | Both directions |

**When to use:**
- **Flexbox**: Navigation bars, card layouts, centering
- **Grid**: Page layouts, complex structures

---

### **13. What are CSS units?**
**Answer:**

**Absolute Units:**
```css
px    /* Pixels */
pt    /* Points */
cm    /* Centimeters */
mm    /* Millimeters */
in    /* Inches */
```

**Relative Units:**
```css
%     /* Percentage of parent */
em    /* Relative to parent font-size */
rem   /* Relative to root font-size */
vw    /* 1% of viewport width */
vh    /* 1% of viewport height */
vmin  /* 1% of smaller viewport dimension */
vmax  /* 1% of larger viewport dimension */
```

**Example:**
```css
html { font-size: 16px; }

.parent {
  font-size: 20px;
}

.child {
  font-size: 2em;    /* 40px (2 × 20px) */
  font-size: 2rem;   /* 32px (2 × 16px) */
  width: 50%;        /* 50% of parent width */
  height: 100vh;     /* Full viewport height */
}
```

---

### **14. What is the difference between `em` and `rem`?**
**Answer:**

```css
html { font-size: 16px; }

.parent {
  font-size: 20px;
}

.child {
  font-size: 2em;    /* 40px (2 × parent 20px) */
  padding: 1em;      /* 40px (based on own font-size) */
}

.another {
  font-size: 2rem;   /* 32px (2 × root 16px) */
  padding: 1rem;     /* 16px (always based on root) */
}
```

**Best Practice:** Use `rem` for consistency.

---

### **15. What are pseudo-classes and pseudo-elements?**
**Answer:**

**Pseudo-classes (single colon):**
```css
/* Link states */
a:link { color: blue; }
a:visited { color: purple; }
a:hover { color: red; }
a:active { color: orange; }

/* Form states */
input:focus { border-color: blue; }
input:disabled { opacity: 0.5; }
input:checked { background: green; }

/* Structural */
li:first-child { font-weight: bold; }
li:last-child { margin-bottom: 0; }
li:nth-child(2n) { background: #f0f0f0; }
p:not(.special) { color: gray; }
```

**Pseudo-elements (double colon):**
```css
/* Insert content */
p::before { content: "→ "; }
p::after { content: " ←"; }

/* Style parts */
p::first-letter { font-size: 2em; }
p::first-line { font-weight: bold; }

/* Selection */
::selection { background: yellow; }
```

---

### **16. What is the `z-index` property?**
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
```

**Rules:**
- Only works on positioned elements (not static)
- Higher value = on top
- Creates stacking context

---

### **17. What is CSS Flexbox centering?**
**Answer:**

```css
/* Horizontal and Vertical Center */
.container {
  display: flex;
  justify-content: center;  /* Horizontal */
  align-items: center;      /* Vertical */
  height: 100vh;
}

/* Alternative methods */
.center1 {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.center2 {
  display: grid;
  place-items: center;
}

.center3 {
  margin: 0 auto;  /* Horizontal only */
  width: 500px;
}
```

---

### **18. What are media queries?**
**Answer:** Apply styles based on device characteristics.

```css
/* Mobile First */
.container { width: 100%; }

/* Tablet */
@media (min-width: 768px) {
  .container { width: 750px; }
}

/* Desktop */
@media (min-width: 1024px) {
  .container { width: 1000px; }
}

/* Orientation */
@media (orientation: landscape) {
  .sidebar { display: block; }
}

/* Print */
@media print {
  .no-print { display: none; }
}

/* Dark Mode */
@media (prefers-color-scheme: dark) {
  body { background: black; color: white; }
}
```

**Common Breakpoints:**
- Mobile: < 768px
- Tablet: 768px - 1024px
- Desktop: > 1024px

---

### **19. What is the difference between `margin` and `padding`?**
**Answer:**

```css
.box {
  margin: 20px;      /* Space OUTSIDE border */
  padding: 20px;     /* Space INSIDE border */
  border: 2px solid;
}
```

**Margin:**
- Creates space between elements
- Can be negative
- Collapses vertically
- Transparent (shows parent background)

**Padding:**
- Creates space inside element
- Cannot be negative
- Doesn't collapse
- Shows element's background

---

### **20. What is margin collapse?**
**Answer:** Vertical margins of adjacent elements combine into single margin.

```css
.box1 { margin-bottom: 30px; }
.box2 { margin-top: 20px; }
/* Actual gap = 30px (not 50px) */
```

**Prevention:**
- Use padding instead
- Add border or padding to parent
- Use flexbox/grid
- Use `overflow: hidden` on parent

---

### **21. What are CSS transitions?**
**Answer:** Smooth animation between property values.

```css
.button {
  background: blue;
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
```

**Timing Functions:**
- `ease` - Slow start, fast middle, slow end
- `linear` - Constant speed
- `ease-in` - Slow start
- `ease-out` - Slow end
- `ease-in-out` - Slow start and end

---

### **22. What are CSS animations?**
**Answer:** Complex animations with keyframes.

```css
@keyframes slideIn {
  0% {
    transform: translateX(-100%);
    opacity: 0;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
}

.element {
  animation: slideIn 1s ease-in-out;
  animation-delay: 0.5s;
  animation-iteration-count: infinite;
  animation-direction: alternate;
  animation-fill-mode: forwards;
}

/* Shorthand */
.box {
  animation: slideIn 1s ease-in-out 0.5s infinite alternate forwards;
}
```

---

### **23. What is the `transform` property?**
**Answer:** Modify element's appearance without affecting layout.

```css
/* Translate (move) */
.move { transform: translate(50px, 100px); }

/* Rotate */
.rotate { transform: rotate(45deg); }

/* Scale */
.scale { transform: scale(1.5); }

/* Skew */
.skew { transform: skew(10deg, 5deg); }

/* Multiple transforms */
.combined {
  transform: translate(50px, 50px) rotate(45deg) scale(1.2);
}

/* 3D transforms */
.flip { transform: rotateY(180deg); }
```

---

### **24. What is the difference between `absolute` and `relative` positioning?**
**Answer:**

```css
/* Relative - relative to normal position */
.relative {
  position: relative;
  top: 20px;        /* Moves 20px down from normal position */
  left: 30px;       /* Original space preserved */
}

/* Absolute - relative to nearest positioned ancestor */
.parent {
  position: relative;  /* Creates positioning context */
}

.absolute {
  position: absolute;
  top: 0;           /* Relative to parent */
  right: 0;
  /* Removed from normal flow */
}
```

---

### **25. What is the `float` property?**
**Answer:** Positions element to left or right, allowing text to wrap.

```css
.image {
  float: left;
  margin-right: 20px;
}

/* Clear floats */
.clearfix::after {
  content: "";
  display: table;
  clear: both;
}
```

**Modern Alternative:** Use Flexbox or Grid instead.

---

### **26. What is the difference between `inline`, `block`, and `inline-block`?**
**Answer:**

| Property | Width/Height | Line Break | Margin/Padding |
|----------|--------------|------------|----------------|
| `inline` | No | No | Horizontal only |
| `block` | Yes | Yes | All sides |
| `inline-block` | Yes | No | All sides |

```css
span { display: inline; }        /* Default for span */
div { display: block; }          /* Default for div */
button { display: inline-block; } /* Hybrid */
```

---

### **27. What are CSS variables (Custom Properties)?**
**Answer:**

```css
:root {
  --primary-color: #3498db;
  --spacing: 16px;
  --font-size: 14px;
}

.button {
  background: var(--primary-color);
  padding: var(--spacing);
  font-size: var(--font-size);
}

/* With fallback */
.text {
  color: var(--text-color, black);
}

/* JavaScript access */
document.documentElement.style.setProperty('--primary-color', 'red');
```

---

### **28. What is the `calc()` function?**
**Answer:** Perform calculations in CSS.

```css
.container {
  width: calc(100% - 50px);
  height: calc(100vh - 60px);
  padding: calc(1rem + 5px);
  font-size: calc(16px + 0.5vw);
}

/* With variables */
:root {
  --header-height: 60px;
}

.content {
  height: calc(100vh - var(--header-height));
}
```

---

### **29. What is the difference between `overflow` values?**
**Answer:**

```css
/* Hide overflow */
.box1 { overflow: hidden; }

/* Show scrollbars */
.box2 { overflow: scroll; }

/* Auto scrollbars (only when needed) */
.box3 { overflow: auto; }

/* Show overflow (default) */
.box4 { overflow: visible; }

/* Separate axes */
.box5 {
  overflow-x: hidden;
  overflow-y: auto;
}
```

---

### **30. What is the `!important` rule?**
**Answer:** Overrides all other declarations (use sparingly).

```css
.text {
  color: blue !important;  /* Highest priority */
}

/* Even this won't override */
#unique.text {
  color: red;  /* Won't apply */
}
```

**When to use:**
- Overriding third-party CSS
- Utility classes
- Quick fixes (not recommended)

**Better approach:** Increase specificity instead.

---

## **Additional Important Concepts**

### **31. What is CSS Reset vs Normalize?**
**Answer:**

**CSS Reset:** Removes all default styles
```css
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
```

**Normalize.css:** Preserves useful defaults, fixes bugs

---

### **32. What is the difference between `nth-child` and `nth-of-type`?**
**Answer:**

```css
/* nth-child - counts all children */
li:nth-child(2) { color: red; }

/* nth-of-type - counts specific type */
li:nth-of-type(2) { color: blue; }
```

```html
<ul>
  <p>Not counted by nth-of-type</p>
  <li>First li (2nd child, 1st of type)</li>
  <li>Second li (3rd child, 2nd of type)</li>
</ul>
```

---

### **33. What is BEM methodology?**
**Answer:** Block Element Modifier naming convention.

```css
/* Block */
.card { }

/* Element */
.card__title { }
.card__image { }

/* Modifier */
.card--featured { }
.card__title--large { }
```

```html
<div class="card card--featured">
  <h2 class="card__title card__title--large">Title</h2>
  <img class="card__image" src="image.jpg">
</div>
```

---

### **34. What is CSS Grid `fr` unit?**
**Answer:** Fractional unit for flexible sizing.

```css
.container {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  /* First column: 25%, Second: 50%, Third: 25% */
}
```

---

### **35. What is the `object-fit` property?**
**Answer:** Controls how images/videos fit their container.

```css
img {
  width: 300px;
  height: 200px;
  object-fit: cover;      /* Crop to fill */
  object-fit: contain;    /* Fit inside */
  object-fit: fill;       /* Stretch */
  object-fit: none;       /* Original size */
  object-fit: scale-down; /* Smaller of none/contain */
}
```

---

## **Quick Revision Checklist**

✅ Box Model and box-sizing  
✅ CSS Specificity  
✅ Flexbox vs Grid  
✅ Position values  
✅ Display values  
✅ Units (px, em, rem, %, vw, vh)  
✅ Pseudo-classes and pseudo-elements  
✅ Media queries  
✅ Transitions and animations  
✅ Transform property  
✅ CSS variables  
✅ Selectors and combinators  

---

**Note:** These questions cover 90% of CSS interviews in service-based companies. Practice creating layouts using Flexbox and Grid, and understand the Box Model thoroughly.
