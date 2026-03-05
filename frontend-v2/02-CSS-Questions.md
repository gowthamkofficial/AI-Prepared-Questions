# CSS - Most Important Interview Questions

## **Top 30 CSS Questions**

### 1. What is the CSS Box Model?
**Answer:** Content → Padding → Border → Margin. Total width = content + padding + border + margin.

### 2. What is `box-sizing: border-box`?
**Answer:** Includes padding and border in width. Recommended for all elements.

### 3. CSS Specificity?
**Answer:** Inline (1000) > ID (100) > Class (10) > Element (1). `!important` overrides all.

### 4. Flexbox vs Grid?
**Answer:** Flexbox is 1D (row/column), Grid is 2D (rows + columns). Flexbox for components, Grid for layouts.

### 5. CSS Position values?
**Answer:**
- `static`: Normal flow
- `relative`: Relative to normal position
- `absolute`: Relative to positioned ancestor
- `fixed`: Relative to viewport
- `sticky`: Hybrid of relative/fixed

### 6. `display: none` vs `visibility: hidden`?
**Answer:** `display: none` removes from layout, `visibility: hidden` keeps space.

### 7. `em` vs `rem`?
**Answer:** `em` relative to parent font-size, `rem` relative to root (html) font-size.

### 8. Pseudo-classes vs pseudo-elements?
**Answer:** Pseudo-class (`:hover`, `:focus`) selects state, pseudo-element (`::before`, `::after`) creates element.

### 9. Media queries?
**Answer:** Responsive design:
```css
@media (min-width: 768px) { /* Tablet */ }
@media (min-width: 1024px) { /* Desktop */ }
```

### 10. Margin vs padding?
**Answer:** Margin is outside border, padding is inside. Margin can be negative, padding cannot.

### 11. Margin collapse?
**Answer:** Vertical margins of adjacent elements combine. Prevent with padding, border, or flexbox.

### 12. CSS transitions?
**Answer:** Smooth property changes:
```css
transition: property duration timing-function;
transition: all 0.3s ease;
```

### 13. CSS animations?
**Answer:** Complex animations with keyframes:
```css
@keyframes slide { 0% { left: 0; } 100% { left: 100px; } }
animation: slide 1s ease;
```

### 14. Transform property?
**Answer:** `translate()`, `rotate()`, `scale()`, `skew()`. Doesn't affect layout.

### 15. Flexbox properties?
**Answer:**
- Container: `display: flex`, `justify-content`, `align-items`, `flex-direction`, `flex-wrap`
- Item: `flex-grow`, `flex-shrink`, `flex-basis`, `align-self`

### 16. Grid properties?
**Answer:**
```css
display: grid;
grid-template-columns: 1fr 2fr 1fr;
grid-template-rows: 100px auto;
gap: 20px;
```

### 17. Center element with Flexbox?
**Answer:**
```css
display: flex;
justify-content: center;
align-items: center;
```

### 18. Center element with Grid?
**Answer:**
```css
display: grid;
place-items: center;
```

### 19. CSS variables?
**Answer:**
```css
:root { --primary: #3498db; }
.button { background: var(--primary); }
```

### 20. `calc()` function?
**Answer:** Calculations: `width: calc(100% - 50px);`

### 21. `z-index`?
**Answer:** Stacking order for positioned elements. Higher value = on top.

### 22. `float` property?
**Answer:** Positions element left/right, text wraps. Use Flexbox/Grid instead.

### 23. Inline vs block vs inline-block?
**Answer:**
- `inline`: No width/height, same line
- `block`: Full width, new line
- `inline-block`: Width/height, same line

### 24. `overflow` values?
**Answer:** `visible` (default), `hidden`, `scroll`, `auto`.

### 25. `!important`?
**Answer:** Overrides all declarations. Avoid; increase specificity instead.

### 26. CSS Reset vs Normalize?
**Answer:** Reset removes all styles, Normalize preserves useful defaults.

### 27. `nth-child` vs `nth-of-type`?
**Answer:** `nth-child` counts all children, `nth-of-type` counts specific type.

### 28. BEM methodology?
**Answer:** Block__Element--Modifier naming:
```css
.card { }
.card__title { }
.card--featured { }
```

### 29. `object-fit`?
**Answer:** How images fit container: `cover`, `contain`, `fill`, `none`, `scale-down`.

### 30. CSS units?
**Answer:**
- Absolute: `px`, `pt`, `cm`
- Relative: `%`, `em`, `rem`, `vw`, `vh`, `vmin`, `vmax`
