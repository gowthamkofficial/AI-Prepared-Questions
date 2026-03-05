# CSS Advanced - Interview Questions

## Q1: What are CSS Media Queries?
**Answer:**
Media queries allow responsive styling based on device characteristics.

```css
/* Mobile first approach */
.container {
  font-size: 14px;
}

/* Tablet */
@media (min-width: 768px) {
  .container {
    font-size: 16px;
  }
}

/* Desktop */
@media (min-width: 1024px) {
  .container {
    font-size: 18px;
  }
}

/* Large desktop */
@media (min-width: 1440px) {
  .container {
    font-size: 20px;
  }
}
```

**Common Media Query Features:**
```css
@media (max-width: 600px) { }        /* Max width */
@media (min-width: 600px) { }        /* Min width */
@media (orientation: portrait) { }   /* Portrait/landscape */
@media (prefers-color-scheme: dark) { } /* Dark mode preference */
@media (prefers-reduced-motion: reduce) { } /* Reduced motion preference */
@media (hover: hover) { }            /* Hover capability */
@media print { }                     /* Print styles */
```

## Q2: What are CSS Custom Properties (Variables)?
**Answer:**
CSS variables allow reusable values throughout stylesheets.

```css
:root {
  --primary-color: #3498db;
  --secondary-color: #2ecc71;
  --spacing-unit: 8px;
  --font-size-base: 16px;
}

.button {
  background-color: var(--primary-color);
  padding: calc(var(--spacing-unit) * 2);
  font-size: var(--font-size-base);
}

/* With fallback */
.button {
  color: var(--primary-color, blue);
}

/* JavaScript access */
document.documentElement.style.setProperty('--primary-color', '#e74c3c');
```

**Dynamic theming:**
```css
/* Light theme */
:root {
  --bg-color: #ffffff;
  --text-color: #000000;
}

/* Dark theme */
@media (prefers-color-scheme: dark) {
  :root {
    --bg-color: #1a1a1a;
    --text-color: #ffffff;
  }
}

body {
  background: var(--bg-color);
  color: var(--text-color);
}
```

## Q3: What is CSS Transitions?
**Answer:**
Transitions smoothly animate CSS property changes.

```css
.button {
  background-color: blue;
  transition: background-color 0.3s ease-in-out;
}

.button:hover {
  background-color: red;
}
```

**Properties:**
```css
transition-property: background-color, color;
transition-duration: 0.3s;
transition-timing-function: ease, linear, ease-in, ease-out, ease-in-out;
transition-delay: 0.1s;

/* Shorthand */
transition: all 0.3s ease 0.1s;
```

## Q4: What is CSS Animations?
**Answer:**
Animations allow complex motion effects using keyframes.

```css
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

.element {
  animation: slideIn 0.5s ease-in-out;
  animation-iteration-count: infinite;
  animation-direction: alternate;
  animation-fill-mode: forwards;
}
```

**Animation Properties:**
```css
animation-name: slideIn
animation-duration: 0.5s
animation-timing-function: ease
animation-delay: 0s
animation-iteration-count: 1 | infinite
animation-direction: normal | reverse | alternate | alternate-reverse
animation-fill-mode: none | forwards | backwards | both
animation-play-state: running | paused
```

## Q5: What are Transform and Filter?
**Answer:**

**Transform:**
```css
.element {
  transform: translateX(50px);      /* Move */
  transform: rotate(45deg);         /* Rotate */
  transform: scale(1.5);            /* Scale */
  transform: skewX(10deg);          /* Skew */
  
  /* Combined */
  transform: translateX(50px) rotate(45deg) scale(1.5);
}
```

**Filter:**
```css
.image {
  filter: blur(5px);
  filter: brightness(1.5);
  filter: contrast(1.2);
  filter: grayscale(100%);
  filter: hue-rotate(90deg);
  filter: invert(100%);
  filter: opacity(50%);
  filter: saturate(1.5);
  filter: sepia(100%);
  
  /* Combined */
  filter: blur(5px) brightness(1.2) contrast(1.1);
}
```

## Q6: What are CSS Gradients?
**Answer:**

**Linear Gradient:**
```css
.element {
  background: linear-gradient(to right, red, blue);
  background: linear-gradient(45deg, #ff0000, #00ff00, #0000ff);
  background: linear-gradient(to right, 
    red 0%, 
    yellow 25%, 
    green 50%, 
    blue 100%
  );
}
```

**Radial Gradient:**
```css
.element {
  background: radial-gradient(circle, red, blue);
  background: radial-gradient(ellipse at top, red, transparent);
  background: radial-gradient(circle at 30% 30%, yellow 0%, blue 100%);
}
```

**Conic Gradient:**
```css
.pie {
  background: conic-gradient(red 25%, yellow 0deg 50%, blue 0deg);
}
```

## Q7: What are CSS Shadows and Outlines?
**Answer:**

**Box Shadow:**
```css
.element {
  /* x-offset y-offset blur spread color */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  
  /* Multiple shadows */
  box-shadow: 
    0 2px 4px rgba(0, 0, 0, 0.1),
    0 8px 16px rgba(0, 0, 0, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
}
```

**Text Shadow:**
```css
.heading {
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}
```

**Outline:**
```css
.button:focus {
  outline: 2px solid #3498db;
  outline-offset: 2px;
}
```

## Q8: What is CSS Overflow?
**Answer:**

```css
/* Overflow behavior */
overflow: visible;   /* Content overflows (default) */
overflow: hidden;    /* Content is clipped */
overflow: scroll;    /* Scrollbar always shown */
overflow: auto;      /* Scrollbar when needed */
overflow: clip;      /* Clipped, no scrollbar */

/* Separate x and y */
overflow-x: auto;
overflow-y: hidden;

/* Text overflow */
white-space: nowrap;
overflow: hidden;
text-overflow: ellipsis;  /* ... for truncated text */
```

## Q9: What is CSS Flexbox Advanced?
**Answer:**

**Flex grow/shrink/basis:**
```css
.item {
  /* flex: [grow] [shrink] [basis] */
  flex: 1 1 100px;
  
  /* Or separate */
  flex-grow: 1;      /* How much to grow */
  flex-shrink: 1;    /* How much to shrink */
  flex-basis: 100px; /* Base size */
}
```

**Order:**
```css
.item {
  order: 1;  /* Default is 0 */
}

.item:first-child {
  order: 2;  /* Appears second */
}
```

**Alignment properties:**
```css
.container {
  justify-content: space-between;  /* Main axis */
  align-items: center;             /* Cross axis */
  align-content: center;           /* Multiple lines */
  gap: 20px;
}

.item {
  align-self: flex-end;  /* Override container alignment */
}
```

## Q10: What is CSS Grid Advanced?
**Answer:**

**Explicit vs Implicit Grid:**
```css
.container {
  /* Explicit grid */
  grid-template-columns: 100px 200px 100px;
  grid-template-rows: 50px 100px;
  
  /* Implicit grid (auto-generated) */
  grid-auto-columns: minmax(100px, 1fr);
  grid-auto-rows: 100px;
  grid-auto-flow: row dense;  /* Dense packing */
}
```

**Grid Areas:**
```css
.container {
  grid-template-areas:
    "header  header  header"
    "sidebar main    main"
    "footer  footer  footer";
}

.header { grid-area: header; }
.sidebar { grid-area: sidebar; }
.main { grid-area: main; }
.footer { grid-area: footer; }
```

**Named Lines:**
```css
.container {
  grid-template-columns: [start] 1fr [middle] 1fr [end];
}

.item {
  grid-column: start / end;
}
```

