# HTML - Most Important Interview Questions

## **Top 25 HTML Questions**

### 1. What is HTML5 and its new features?
**Answer:** HTML5 is the latest version with semantic tags (`<header>`, `<nav>`, `<article>`), multimedia (`<audio>`, `<video>`), APIs (Geolocation, Storage), new input types (email, date), and `<canvas>` for graphics.

### 2. Difference between `<div>` and `<span>`?
**Answer:** `<div>` is block-level (full width, new line), `<span>` is inline (content width, same line).

### 3. What are semantic HTML tags?
**Answer:** Tags with meaning: `<header>`, `<nav>`, `<main>`, `<article>`, `<section>`, `<aside>`, `<footer>`. Benefits: Better SEO, accessibility, readability.

### 4. Difference between `id` and `class`?
**Answer:** `id` is unique (one per page), `class` is reusable. `id` has higher specificity (100 vs 10).

### 5. What is DOCTYPE?
**Answer:** `<!DOCTYPE html>` tells browser to use HTML5 standards mode.

### 6. Explain `<script>`, `<script async>`, `<script defer>`
**Answer:**
- `<script>`: Blocks HTML parsing
- `async`: Downloads parallel, executes when ready
- `defer`: Downloads parallel, executes after HTML parsing

### 7. What are data attributes?
**Answer:** Custom attributes: `data-user-id="123"`. Access via `element.dataset.userId`.

### 8. localStorage vs sessionStorage?
**Answer:** localStorage persists forever, sessionStorage clears on tab close. Both ~5-10MB.

### 9. What are meta tags?
**Answer:** Metadata for SEO, viewport, charset:
```html
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Page description">
```

### 10. GET vs POST?
**Answer:** GET sends data in URL (visible, cacheable, limited), POST in body (hidden, secure, unlimited).

### 11. What is `alt` attribute?
**Answer:** Alternative text for images. Required for accessibility and SEO.

### 12. Semantic vs non-semantic elements?
**Answer:** Semantic (`<article>`) has meaning, non-semantic (`<div>`) doesn't.

### 13. HTML5 form validation?
**Answer:** `required`, `pattern`, `min`, `max`, `type="email"`, `type="url"`.

### 14. What is `<canvas>`?
**Answer:** Element for drawing graphics via JavaScript using 2D context.

### 15. Inline vs block vs inline-block?
**Answer:** Inline (no width/height), block (full width), inline-block (width/height, same line).

### 16. What are void elements?
**Answer:** Self-closing: `<img>`, `<br>`, `<hr>`, `<input>`, `<meta>`, `<link>`.

### 17. `<strong>` vs `<b>`, `<em>` vs `<i>`?
**Answer:** `<strong>`/`<em>` are semantic (importance), `<b>`/`<i>` are visual only.

### 18. What is `srcset`?
**Answer:** Responsive images with multiple sources:
```html
<img srcset="small.jpg 500w, large.jpg 1000w">
```

### 19. What is `target="_blank"`?
**Answer:** Opens link in new tab. Use `rel="noopener noreferrer"` for security.

### 20. HTML entities?
**Answer:** `&lt;` (<), `&gt;` (>), `&amp;` (&), `&nbsp;` (space), `&copy;` (©).

### 21. `<section>` vs `<article>` vs `<div>`?
**Answer:** `<section>` groups content, `<article>` is independent content, `<div>` is generic.

### 22. What is accessibility (a11y)?
**Answer:** Making web usable for disabilities. Use semantic HTML, alt text, ARIA labels, keyboard navigation.

### 23. `<head>` vs `<header>`?
**Answer:** `<head>` contains metadata (invisible), `<header>` is page header (visible).

### 24. What is `<iframe>`?
**Answer:** Embeds another HTML page. Use `sandbox` for security.

### 25. HTML5 APIs?
**Answer:** Geolocation, Web Storage, Web Workers, Canvas, Drag & Drop, History, Fetch.
