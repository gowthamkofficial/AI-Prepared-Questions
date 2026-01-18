# CSS Fundamentals - Interview Questions

## Q1: What is the CSS Box Model?
**Answer:**
The box model describes how elements are structured and spaced:

```
┌─────────────────────────────┐
│         Margin              │
│  ┌────────────────────────┐ │
│  │     Border             │ │
│  │  ┌──────────────────┐  │ │
│  │  │   Padding        │  │ │
│  │  │ ┌──────────────┐ │  │ │
│  │  │ │   Content    │ │  │ │
│  │  │ └──────────────┘ │  │ │
│  │  └──────────────────┘  │ │
│  └────────────────────────┘ │
└─────────────────────────────┘
```

```css
.box {
  width: 200px;
  padding: 20px;
  border: 2px solid black;
  margin: 10px;
}

/* Total width = 200 + 40 (padding) + 4 (border) + 20 (margin) = 264px */
```

**box-sizing:**
```css
/* border-box includes padding and border in width */
* {
  box-sizing: border-box;
}
```

## Q2: What are CSS Selectors?
**Answer:**

**Basic Selectors:**
```css
/* Element selector */
h1 { color: red; }

/* Class selector */
.class-name { color: blue; }

/* ID selector */
#id-name { color: green; }

/* Attribute selector */
input[type="text"] { border: 1px solid gray; }
```

**Combinators:**
```css
/* Descendant */
.parent p { color: red; }

/* Child */
.parent > p { color: blue; }

/* Adjacent sibling */
h1 + p { margin-top: 0; }

/* General sibling */
h1 ~ p { color: gray; }
```

**Pseudo-classes:**
```css
/* Link states */
a:link { color: blue; }
a:visited { color: purple; }
a:hover { color: red; }
a:active { color: orange; }

/* Structural */
li:first-child { color: red; }
li:last-child { color: blue; }
li:nth-child(2n) { background: gray; }

/* States */
input:focus { outline: 2px solid blue; }
input:disabled { opacity: 0.5; }
```

**Pseudo-elements:**
```css
/* Before and after */
p::before { content: ">> "; }
p::after { content: " <<"; }

/* First line and letter */
p::first-line { font-weight: bold; }
p::first-letter { font-size: 2em; }

/* Selection */
::selection { background: yellow; }
```

## Q3: What is CSS Specificity?
**Answer:**
Specificity determines which CSS rule is applied when multiple rules target the same element.

**Calculation:**
```
ID selectors = 100 points
Class/Attribute/Pseudo-class selectors = 10 points
Element selectors = 1 point
```

**Examples:**
```css
p { }                          /* Specificity: 1 */
.class { }                     /* Specificity: 10 */
#id { }                        /* Specificity: 100 */
div.class { }                  /* Specificity: 11 */
#id.class { }                  /* Specificity: 110 */
div > p { }                    /* Specificity: 2 */
!important                     /* Highest priority */
```

**Specificity War Prevention:**
```css
/* ❌ Avoid this */
#id { color: red !important; }

/* ✅ Good */
#id { color: red; }
```

## Q4: What is the CSS Cascade?
**Answer:**
The cascade determines which CSS rules are applied based on:

1. **Origin**: Browser → User → Author
2. **Specificity**: More specific wins
3. **Order**: Later rules override earlier ones

```css
p { color: blue; }          /* Applied first */
p { color: red; }           /* Overrides blue */
```

## Q5: What are CSS Display Properties?
**Answer:**

**block:**
```css
.block {
  display: block;
  width: 100%;
  margin-top: 10px;
}
/* Takes full width, starts new line */
```

**inline:**
```css
.inline {
  display: inline;
  width: auto;  /* Ignored */
  margin-top: auto;  /* Ignored */
}
/* Flows with text, width/height ignored */
```

**inline-block:**
```css
.inline-block {
  display: inline-block;
  width: 100px;
  height: 50px;
}
/* Block properties but flows inline */
```

**none:**
```css
.hidden {
  display: none;
}
/* Removed from DOM flow */
```

| Property | Width/Height | Margin | Padding | New Line |
|----------|----------|--------|---------|----------|
| block | ✅ | ✅ | ✅ | ✅ |
| inline | ❌ | ❌ | ✅ | ❌ |
| inline-block | ✅ | ✅ | ✅ | ❌ |

## Q6: What is Flexbox?
**Answer:**
Flexbox is a one-dimensional layout method for arranging items in rows or columns.

```css
.container {
  display: flex;
  flex-direction: row;           /* row, column, row-reverse, column-reverse */
  justify-content: center;       /* Main axis alignment */
  align-items: center;           /* Cross axis alignment */
  gap: 10px;                     /* Space between items */
}

.item {
  flex: 1;                       /* flex-grow: 1 */
  flex-basis: 100px;
}
```

**Common properties:**
```css
/* Container */
flex-direction: row | column
justify-content: flex-start | center | flex-end | space-between | space-around
align-items: flex-start | center | flex-end | stretch
flex-wrap: nowrap | wrap
gap: 10px

/* Items */
flex: 1
flex-grow: 1
flex-shrink: 1
flex-basis: 100px
align-self: center
order: 2
```

## Q7: What is Grid?
**Answer:**
Grid is a two-dimensional layout method for creating complex layouts.

```css
.container {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;  /* Three columns */
  grid-template-rows: 100px auto 100px;
  gap: 20px;
  grid-auto-flow: row;
}

.item {
  grid-column: 1 / 3;  /* Span columns 1-3 */
  grid-row: 2 / 4;     /* Span rows 2-4 */
}
```

**Grid vs Flexbox:**

| Feature | Grid | Flexbox |
|---------|------|---------|
| **Dimensions** | 2D | 1D |
| **Use Case** | Page layouts | Component layouts |
| **Browser Support** | Excellent | Excellent |
| **Complexity** | More complex | Simpler |

## Q8: What is positioning?
**Answer:**

**static (default):**
```css
position: static;
/* Elements flow normally */
```

**relative:**
```css
position: relative;
top: 10px;
left: 20px;
/* Positioned relative to its normal position */
```

**absolute:**
```css
position: absolute;
top: 0;
left: 0;
/* Positioned relative to nearest positioned parent */
```

**fixed:**
```css
position: fixed;
top: 0;
right: 0;
/* Positioned relative to viewport, stays during scroll */
```

**sticky:**
```css
position: sticky;
top: 0;
/* Toggles between relative and fixed based on scroll */
```

## Q9: What is the z-index?
**Answer:**
Controls stacking order of positioned elements.

```css
.below { z-index: 1; }
.above { z-index: 2; }
.top { z-index: 100; }
```

**Rules:**
- Only works on positioned elements (not static)
- Higher values appear on top
- Stacking context creates new z-index scale
- Negative values allowed

## Q10: What are CSS Units?
**Answer:**

**Absolute:**
```css
/* Fixed, don't scale */
px    /* pixels */
pt    /* points (1/72 inch) */
cm    /* centimeters */
mm    /* millimeters */
```

**Relative:**
```css
em    /* Relative to font-size of element (2em = 2x) */
rem   /* Relative to root font-size */
%     /* Relative to parent */
vw    /* Viewport width (1vw = 1% of viewport width) */
vh    /* Viewport height */
vmin  /* Smaller of vw/vh */
vmax  /* Larger of vw/vh */
```

**Best Practices:**
```css
/* Use rem for scaling */
html { font-size: 16px; }
body { font-size: 1rem; }

/* Use em for component-relative sizing */
.button { font-size: 1em; padding: 0.5em; }

/* Use % for responsive */
.container { width: 80%; }

/* Use px for borders and shadows */
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
```

