# HTML & CSS - Quick Interview Guide

## **Top 20 Most Frequently Asked Questions**

### **HTML Questions**

1. **What is HTML5 and its new features?**
   - Semantic tags, multimedia support, APIs, form controls

2. **Difference between `<div>` and `<span>`?**
   - Block vs Inline elements

3. **What are semantic HTML tags?**
   - `<header>`, `<nav>`, `<main>`, `<article>`, `<section>`, `<footer>`

4. **Difference between `id` and `class`?**
   - Unique vs Reusable, Specificity difference

5. **What is the DOCTYPE declaration?**
   - Tells browser HTML version

6. **Explain `<script>`, `<script async>`, `<script defer>`**
   - Loading and execution timing

7. **What are data attributes?**
   - Custom attributes: `data-*`

8. **Difference between localStorage and sessionStorage?**
   - Permanent vs Session-based storage

9. **What are meta tags?**
   - Metadata for SEO, viewport, charset

10. **What is the difference between GET and POST?**
    - URL vs Body, Security, Caching

---

### **CSS Questions**

11. **What is the CSS Box Model?**
    - Content, Padding, Border, Margin

12. **What is CSS Specificity?**
    - Inline (1000) > ID (100) > Class (10) > Element (1)

13. **Difference between Flexbox and Grid?**
    - 1D vs 2D layout systems

14. **What are CSS position values?**
    - static, relative, absolute, fixed, sticky

15. **Difference between `display: none` and `visibility: hidden`?**
    - Space occupied vs not

16. **What is the difference between `em` and `rem`?**
    - Relative to parent vs root

17. **What are pseudo-classes and pseudo-elements?**
    - `:hover` vs `::before`

18. **What are media queries?**
    - Responsive design breakpoints

19. **Difference between `margin` and `padding`?**
    - Outside vs Inside border

20. **What is `box-sizing: border-box`?**
    - Includes padding and border in width

---

## **HTML - Essential Concepts**

### **Semantic HTML Structure**
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Page Title</title>
</head>
<body>
  <header>
    <nav>Navigation</nav>
  </header>
  
  <main>
    <article>
      <section>Content</section>
    </article>
    <aside>Sidebar</aside>
  </main>
  
  <footer>Footer</footer>
</body>
</html>
```

### **Form Elements**
```html
<form action="/submit" method="POST">
  <input type="text" required>
  <input type="email" placeholder="Email">
  <input type="password" minlength="8">
  <input type="number" min="0" max="100">
  <input type="date">
  <input type="checkbox">
  <input type="radio" name="group">
  <select><option>Option</option></select>
  <textarea rows="4"></textarea>
  <button type="submit">Submit</button>
</form>
```

### **Multimedia**
```html
<img src="image.jpg" alt="Description" loading="lazy">
<video controls><source src="video.mp4"></video>
<audio controls><source src="audio.mp3"></audio>
```

---

## **CSS - Essential Concepts**

### **Selectors**
```css
*                    /* Universal */
element              /* Element */
.class               /* Class */
#id                  /* ID */
element.class        /* Element with class */
element > child      /* Direct child */
element descendant   /* Descendant */
element + adjacent   /* Adjacent sibling */
element ~ sibling    /* General sibling */
[attribute]          /* Attribute */
:hover, :focus       /* Pseudo-class */
::before, ::after    /* Pseudo-element */
```

### **Flexbox Cheat Sheet**
```css
.container {
  display: flex;
  flex-direction: row | column;
  justify-content: flex-start | center | space-between | space-around;
  align-items: stretch | center | flex-start | flex-end;
  flex-wrap: nowrap | wrap;
  gap: 20px;
}

.item {
  flex: 1;              /* Grow to fill space */
  flex-grow: 1;
  flex-shrink: 1;
  flex-basis: auto;
  align-self: center;
}
```

### **Grid Cheat Sheet**
```css
.container {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: 100px auto;
  gap: 20px;
  justify-items: center;
  align-items: center;
}

.item {
  grid-column: 1 / 3;   /* Span columns */
  grid-row: span 2;     /* Span rows */
}
```

### **Responsive Design**
```css
/* Mobile First */
.container { width: 100%; }

@media (min-width: 768px) {
  .container { width: 750px; }
}

@media (min-width: 1024px) {
  .container { width: 1000px; }
}
```

### **Centering Techniques**
```css
/* Flexbox */
.center {
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Grid */
.center {
  display: grid;
  place-items: center;
}

/* Position */
.center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

/* Margin (horizontal only) */
.center {
  margin: 0 auto;
  width: 500px;
}
```

---

## **Common Interview Scenarios**

### **1. Create a responsive navbar**
```html
<nav class="navbar">
  <div class="logo">Logo</div>
  <ul class="nav-links">
    <li><a href="#">Home</a></li>
    <li><a href="#">About</a></li>
    <li><a href="#">Contact</a></li>
  </ul>
</nav>
```

```css
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: #333;
}

.nav-links {
  display: flex;
  gap: 2rem;
  list-style: none;
}

.nav-links a {
  color: white;
  text-decoration: none;
}

@media (max-width: 768px) {
  .navbar {
    flex-direction: column;
  }
  .nav-links {
    flex-direction: column;
    gap: 1rem;
  }
}
```

### **2. Create a card layout**
```html
<div class="card">
  <img src="image.jpg" alt="Card image">
  <div class="card-content">
    <h3>Card Title</h3>
    <p>Card description</p>
    <button>Read More</button>
  </div>
</div>
```

```css
.card {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.3s;
}

.card:hover {
  transform: translateY(-5px);
}

.card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.card-content {
  padding: 1.5rem;
}
```

### **3. Create a grid layout**
```css
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  padding: 2rem;
}
```

---

## **Performance & Best Practices**

### **HTML Best Practices**
✅ Use semantic HTML  
✅ Add alt text to images  
✅ Use proper heading hierarchy (h1 → h6)  
✅ Include meta tags for SEO  
✅ Use `defer` for scripts  
✅ Validate HTML  
✅ Use ARIA labels for accessibility  

### **CSS Best Practices**
✅ Use `box-sizing: border-box`  
✅ Mobile-first approach  
✅ Use CSS variables for theming  
✅ Minimize use of `!important`  
✅ Use shorthand properties  
✅ Organize CSS logically  
✅ Use BEM or consistent naming  
✅ Optimize selectors  

---

## **Common Mistakes to Avoid**

### **HTML**
❌ Missing DOCTYPE  
❌ Not closing tags  
❌ Using deprecated tags (`<center>`, `<font>`)  
❌ Multiple elements with same ID  
❌ Not using semantic tags  
❌ Missing alt attributes  

### **CSS**
❌ Not using `box-sizing: border-box`  
❌ Overusing `!important`  
❌ Not considering specificity  
❌ Inline styles everywhere  
❌ Not testing responsive design  
❌ Forgetting vendor prefixes  

---

## **Quick Reference Tables**

### **HTML5 Input Types**
| Type | Purpose |
|------|---------|
| text | Single-line text |
| email | Email validation |
| password | Hidden text |
| number | Numeric input |
| date | Date picker |
| time | Time picker |
| color | Color picker |
| range | Slider |
| tel | Phone number |
| url | URL validation |
| search | Search field |
| checkbox | Multiple selection |
| radio | Single selection |

### **CSS Display Values**
| Value | Behavior |
|-------|----------|
| block | Full width, new line |
| inline | Content width, same line |
| inline-block | Content width, same line, accepts width/height |
| flex | Flexbox container |
| grid | Grid container |
| none | Hidden, no space |

### **CSS Position Values**
| Value | Behavior |
|-------|----------|
| static | Normal flow (default) |
| relative | Relative to normal position |
| absolute | Relative to positioned ancestor |
| fixed | Relative to viewport |
| sticky | Hybrid of relative and fixed |

### **CSS Units**
| Unit | Description |
|------|-------------|
| px | Pixels (absolute) |
| % | Percentage of parent |
| em | Relative to parent font-size |
| rem | Relative to root font-size |
| vw | 1% of viewport width |
| vh | 1% of viewport height |

---

## **Interview Tips**

### **What Interviewers Look For**
1. **Understanding of fundamentals** - Box model, specificity, semantic HTML
2. **Practical knowledge** - Can you build layouts?
3. **Responsive design** - Mobile-first approach
4. **Best practices** - Accessibility, SEO, performance
5. **Problem-solving** - How you approach layout challenges

### **How to Answer**
1. **Start with definition** - What is it?
2. **Explain with example** - Show code
3. **Mention use cases** - When to use?
4. **Discuss alternatives** - Other approaches?
5. **Best practices** - What's recommended?

### **Common Follow-up Questions**
- "Can you show me an example?"
- "When would you use this?"
- "What are the alternatives?"
- "How did you use this in your project?"
- "What are the browser compatibility issues?"

---

## **Last-Minute Revision**

### **Must Know HTML**
- Semantic tags
- Form elements and validation
- Meta tags
- Script loading (async/defer)
- Data attributes
- Storage APIs

### **Must Know CSS**
- Box model
- Specificity
- Flexbox
- Grid
- Position
- Media queries
- Transitions
- Units (em, rem, %, vw, vh)

### **Must Practice**
- Create responsive navbar
- Build card layouts
- Center elements
- Create grid layouts
- Form styling
- Hover effects

---

## **Resources for Practice**

**Practice Websites:**
- CSS Tricks
- MDN Web Docs
- W3Schools
- Frontend Mentor
- CodePen

**Tools:**
- Chrome DevTools
- Flexbox Froggy (game)
- Grid Garden (game)
- Can I Use (browser support)

---

**Final Note:** Service-based companies (Infosys, Wipro, TCS, Cognizant, Capgemini) focus on:
- Fundamental concepts
- Practical implementation
- Responsive design
- Clean, maintainable code
- Ability to explain clearly

Practice building layouts from scratch and explaining your approach step by step!
