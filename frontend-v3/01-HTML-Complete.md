# HTML - Complete Interview Questions & Answers

## **HTML Fundamentals**

### 1. What is HTML and what does it stand for?
**Answer:** HTML stands for HyperText Markup Language. It is the standard markup language used to create and structure content on the web. HTML uses tags to define elements like headings, paragraphs, links, images, and other content that browsers can interpret and display.

### 2. What is the difference between HTML and HTML5?
**Answer:** 
HTML5 is the latest version of HTML with significant improvements:
- **New Semantic Elements**: `<header>`, `<footer>`, `<article>`, `<section>`, `<nav>`, `<aside>`, `<main>`
- **Multimedia Support**: `<audio>` and `<video>` tags without plugins
- **Graphics**: `<canvas>` for drawing, `<svg>` support
- **Form Enhancements**: New input types (email, date, range, color, tel, url)
- **Storage APIs**: localStorage and sessionStorage
- **New APIs**: Geolocation, Drag & Drop, Web Workers, WebSocket
- **Better Performance**: Improved parsing and rendering
- **Removed Elements**: `<font>`, `<center>`, `<frame>` are deprecated

### 3. What is DOCTYPE and why is it important?
**Answer:** 
DOCTYPE (Document Type Declaration) tells the browser which version of HTML the page is written in. It must be the first line in an HTML document.

```html
<!DOCTYPE html>  <!-- HTML5 -->
```

**Importance:**
- Ensures browser renders page in standards mode (not quirks mode)
- Prevents inconsistent rendering across browsers
- Required for HTML validation
- Affects CSS and JavaScript behavior

### 4. What are semantic HTML elements?
**Answer:** 
Semantic elements clearly describe their meaning to both browser and developer.

**Common Semantic Elements:**
```html
<header>    <!-- Page or section header -->
<nav>       <!-- Navigation links -->
<main>      <!-- Main content -->
<article>   <!-- Independent, self-contained content -->
<section>   <!-- Thematic grouping of content -->
<aside>     <!-- Side content, sidebars -->
<footer>    <!-- Page or section footer -->
<figure>    <!-- Self-contained content like images -->
<figcaption><!-- Caption for figure -->
<time>      <!-- Date/time -->
<mark>      <!-- Highlighted text -->
```

**Benefits:**
- Improved SEO (search engines understand content better)
- Better accessibility (screen readers navigate easier)
- Cleaner, more maintainable code
- Easier to style and target with CSS

### 5. What is the difference between `<div>` and `<span>`?
**Answer:**

| Feature | `<div>` | `<span>` |
|---------|---------|----------|
| Type | Block-level | Inline |
| Width | Takes full width | Takes only necessary width |
| Line Break | Starts on new line | Stays on same line |
| Use Case | Container for sections | Container for small text |

```html
<div>This is a block element</div>
<div>This starts on a new line</div>

<span>This is inline</span> <span>Same line</span>
```

### 6. What are meta tags and their uses?
**Answer:** 
Meta tags provide metadata about the HTML document. They're placed in `<head>` and not displayed on page.

```html
<!-- Character encoding -->
<meta charset="UTF-8">

<!-- Viewport for responsive design -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- SEO Description -->
<meta name="description" content="Page description for search engines">

<!-- Keywords for SEO -->
<meta name="keywords" content="HTML, CSS, JavaScript">

<!-- Author -->
<meta name="author" content="Your Name">

<!-- Refresh/Redirect -->
<meta http-equiv="refresh" content="30">

<!-- Open Graph for social media -->
<meta property="og:title" content="Page Title">
<meta property="og:description" content="Description">
<meta property="og:image" content="image.jpg">

<!-- Twitter Card -->
<meta name="twitter:card" content="summary_large_image">
```

### 7. What are data attributes (data-*)?
**Answer:** 
Custom attributes to store extra information on HTML elements without using non-standard attributes.

```html
<div data-user-id="12345" 
     data-role="admin" 
     data-status="active">
  User Info
</div>

<script>
  const div = document.querySelector('div');
  
  // Access via dataset
  console.log(div.dataset.userId);   // "12345"
  console.log(div.dataset.role);     // "admin"
  console.log(div.dataset.status);   // "active"
  
  // Set data attribute
  div.dataset.newAttr = "value";
  
  // Remove data attribute
  delete div.dataset.status;
</script>
```

**Use Cases:**
- Store configuration data
- Pass data to JavaScript
- Store state information
- CSS styling hooks

### 8. Explain the difference between `<script>`, `<script async>`, and `<script defer>`
**Answer:**

| Attribute | HTML Parsing | Script Download | Script Execution | Use Case |
|-----------|--------------|-----------------|------------------|----------|
| None | Paused | Immediate | Immediate (blocks) | Legacy scripts |
| `async` | Continues | Parallel | When downloaded | Independent scripts |
| `defer` | Continues | Parallel | After HTML parsed | Scripts needing DOM |

```html
<!-- Blocks HTML parsing -->
<script src="app.js"></script>

<!-- Downloads parallel, executes when ready (may block) -->
<script src="analytics.js" async></script>

<!-- Downloads parallel, executes after DOM ready -->
<script src="main.js" defer></script>
```

**Best Practices:**
- Use `defer` for scripts that need DOM access
- Use `async` for independent scripts (analytics, ads)
- Place scripts at end of `<body>` if not using async/defer

### 9. What is the difference between `id` and `class` attributes?
**Answer:**

| Feature | id | class |
|---------|-----|-------|
| Uniqueness | Must be unique on page | Can be reused |
| Per Element | Only one id | Multiple classes |
| CSS Selector | `#idName` | `.className` |
| JavaScript | `getElementById()` | `getElementsByClassName()` |
| Specificity | Higher (100) | Lower (10) |
| Use Case | Unique elements | Grouped styling |

```html
<!-- ID - Unique -->
<div id="header">Header</div>

<!-- Class - Reusable -->
<div class="card primary">Card 1</div>
<div class="card">Card 2</div>
<div class="card primary featured">Card 3</div>

<style>
  #header { background: black; }      /* Specificity: 100 */
  .card { padding: 20px; }            /* Specificity: 10 */
  .primary { color: blue; }
</style>
```

### 10. What are void/self-closing elements?
**Answer:** 
Elements that don't have closing tags or content between opening and closing tags.

```html
<!-- Images -->
<img src="image.jpg" alt="Description">

<!-- Line breaks -->
<br>
<hr>

<!-- Form inputs -->
<input type="text" name="username">

<!-- Meta information -->
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">

<!-- Others -->
<area>
<base>
<col>
<embed>
<source>
<track>
<wbr>
```

**Note:** In HTML5, self-closing slash is optional: `<img>` or `<img />`

### 11. What is the difference between `<section>`, `<article>`, and `<div>`?
**Answer:**

**`<section>`**: Thematic grouping of content, typically with a heading
```html
<section>
  <h2>About Us</h2>
  <p>Company information...</p>
</section>
```

**`<article>`**: Independent, self-contained content that could be distributed separately
```html
<article>
  <h2>Blog Post Title</h2>
  <p>Post content...</p>
</article>
```

**`<div>`**: Generic container with no semantic meaning
```html
<div class="wrapper">
  <p>Generic content...</p>
</div>
```

**When to use:**
- `<article>`: Blog posts, news articles, comments, widgets
- `<section>`: Chapters, tabs, themed content groups
- `<div>`: Styling wrappers, layout containers

### 12. What are HTML5 form input types?
**Answer:**

```html
<!-- Text inputs -->
<input type="text">
<input type="email">        <!-- Email validation -->
<input type="password">     <!-- Hidden text -->
<input type="tel">          <!-- Phone number -->
<input type="url">          <!-- URL validation -->
<input type="search">       <!-- Search field -->

<!-- Numeric inputs -->
<input type="number" min="0" max="100" step="5">
<input type="range" min="0" max="100">

<!-- Date/Time inputs -->
<input type="date">
<input type="time">
<input type="datetime-local">
<input type="month">
<input type="week">

<!-- Other inputs -->
<input type="color">        <!-- Color picker -->
<input type="file">         <!-- File upload -->
<input type="checkbox">     <!-- Checkbox -->
<input type="radio">        <!-- Radio button -->
```

**Form Attributes:**
```html
<input type="email" 
       required 
       placeholder="Enter email"
       pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
       minlength="5"
       maxlength="50"
       autocomplete="email"
       autofocus>
```

### 13. What is the difference between localStorage and sessionStorage?
**Answer:**

| Feature | localStorage | sessionStorage |
|---------|--------------|----------------|
| Lifetime | Permanent (until cleared) | Until tab/window closes |
| Scope | All tabs/windows | Single tab/window |
| Capacity | ~5-10MB | ~5-10MB |
| Persistence | Survives browser restart | Lost on tab close |

```javascript
// localStorage
localStorage.setItem('username', 'John');
localStorage.getItem('username');        // "John"
localStorage.removeItem('username');
localStorage.clear();                    // Clear all

// sessionStorage
sessionStorage.setItem('token', 'abc123');
sessionStorage.getItem('token');
sessionStorage.removeItem('token');
sessionStorage.clear();

// Store objects
const user = { name: 'John', age: 30 };
localStorage.setItem('user', JSON.stringify(user));
const storedUser = JSON.parse(localStorage.getItem('user'));
```

**Use Cases:**
- **localStorage**: User preferences, theme, shopping cart
- **sessionStorage**: Form data, temporary state, session tokens

### 14. What is the difference between GET and POST methods?
**Answer:**

| Feature | GET | POST |
|---------|-----|------|
| Data Location | URL query string | Request body |
| Visibility | Visible in URL | Hidden |
| Security | Less secure | More secure |
| Data Size | Limited (~2048 chars) | Unlimited |
| Caching | Can be cached | Not cached |
| Bookmarkable | Yes | No |
| Back Button | Safe | May resubmit |
| Use Case | Retrieve data | Submit data |

```html
<!-- GET - Data in URL -->
<form action="/search" method="GET">
  <input type="text" name="q">
  <button type="submit">Search</button>
</form>
<!-- URL: /search?q=keyword -->

<!-- POST - Data in body -->
<form action="/login" method="POST">
  <input type="email" name="email">
  <input type="password" name="password">
  <button type="submit">Login</button>
</form>
```

### 15. What are `<audio>` and `<video>` tags?
**Answer:**

```html
<!-- Audio -->
<audio controls autoplay loop muted>
  <source src="audio.mp3" type="audio/mpeg">
  <source src="audio.ogg" type="audio/ogg">
  Your browser doesn't support audio.
</audio>

<!-- Video -->
<video width="640" height="360" controls poster="thumbnail.jpg">
  <source src="video.mp4" type="video/mp4">
  <source src="video.webm" type="video/webm">
  <track src="subtitles.vtt" kind="subtitles" srclang="en" label="English">
  Your browser doesn't support video.
</video>
```

**Attributes:**
- `controls`: Show playback controls
- `autoplay`: Auto play (often blocked by browsers)
- `loop`: Repeat playback
- `muted`: Mute audio
- `preload`: `none`, `metadata`, `auto`
- `poster`: Thumbnail image (video only)

**JavaScript Control:**
```javascript
const video = document.querySelector('video');
video.play();
video.pause();
video.currentTime = 10;  // Seek to 10 seconds
video.volume = 0.5;      // 50% volume
```

### 16. What is the `<canvas>` element?
**Answer:** 
HTML5 element for drawing graphics via JavaScript.

```html
<canvas id="myCanvas" width="500" height="300"></canvas>

<script>
  const canvas = document.getElementById('myCanvas');
  const ctx = canvas.getContext('2d');
  
  // Draw rectangle
  ctx.fillStyle = 'blue';
  ctx.fillRect(10, 10, 100, 100);
  
  // Draw circle
  ctx.beginPath();
  ctx.arc(200, 150, 50, 0, 2 * Math.PI);
  ctx.fillStyle = 'red';
  ctx.fill();
  
  // Draw text
  ctx.font = '30px Arial';
  ctx.fillStyle = 'black';
  ctx.fillText('Hello Canvas', 50, 200);
  
  // Draw line
  ctx.beginPath();
  ctx.moveTo(0, 0);
  ctx.lineTo(200, 100);
  ctx.stroke();
</script>
```

**Use Cases:**
- Charts and graphs
- Game graphics
- Image manipulation
- Animations
- Data visualization

### 17. What is the difference between `<strong>` and `<b>`, `<em>` and `<i>`?
**Answer:**

| Semantic | Visual | Purpose | SEO/Accessibility |
|----------|--------|---------|-------------------|
| `<strong>` | `<b>` | Strong importance | Better |
| `<em>` | `<i>` | Emphasis | Better |

```html
<!-- Semantic (recommended) -->
<strong>Important text</strong>  <!-- Strong importance -->
<em>Emphasized text</em>         <!-- Emphasis -->

<!-- Visual only -->
<b>Bold text</b>                 <!-- Just bold -->
<i>Italic text</i>               <!-- Just italic -->
```

**Best Practice:** Use semantic tags for better accessibility and SEO.

### 18. What are HTML entities?
**Answer:** 
Special characters represented by codes starting with `&` and ending with `;`.

```html
&lt;      <!-- < (less than) -->
&gt;      <!-- > (greater than) -->
&amp;     <!-- & (ampersand) -->
&quot;    <!-- " (double quote) -->
&apos;    <!-- ' (apostrophe) -->
&nbsp;    <!-- Non-breaking space -->
&copy;    <!-- © (copyright) -->
&reg;     <!-- ® (registered) -->
&trade;   <!-- ™ (trademark) -->
&euro;    <!-- € (euro) -->
&pound;   <!-- £ (pound) -->
&yen;     <!-- ¥ (yen) -->
```

**Why use entities:**
- Display reserved HTML characters
- Display characters not on keyboard
- Prevent HTML parsing issues

### 19. What is the `target` attribute in anchor tags?
**Answer:**

```html
<!-- Same tab (default) -->
<a href="page.html" target="_self">Link</a>

<!-- New tab/window -->
<a href="page.html" target="_blank" rel="noopener noreferrer">Link</a>

<!-- Parent frame -->
<a href="page.html" target="_parent">Link</a>

<!-- Full window (breaks out of frames) -->
<a href="page.html" target="_top">Link</a>

<!-- Named frame/window -->
<a href="page.html" target="myFrame">Link</a>
```

**Security Note:** Always use `rel="noopener noreferrer"` with `target="_blank"` to prevent:
- `window.opener` access (security risk)
- Referrer information leakage

### 20. What is the difference between `<ol>`, `<ul>`, and `<dl>`?
**Answer:**

```html
<!-- Ordered List (numbered) -->
<ol>
  <li>First item</li>
  <li>Second item</li>
  <li>Third item</li>
</ol>

<!-- Unordered List (bullets) -->
<ul>
  <li>Bullet point</li>
  <li>Another point</li>
</ul>

<!-- Description List (term-definition pairs) -->
<dl>
  <dt>HTML</dt>
  <dd>HyperText Markup Language</dd>
  
  <dt>CSS</dt>
  <dd>Cascading Style Sheets</dd>
</dl>
```

**Ordered List Attributes:**
```html
<ol type="A" start="3" reversed>
  <li>Item C</li>
  <li>Item D</li>
</ol>
```

### 21. What is the structure of HTML tables?
**Answer:**

```html
<table>
  <caption>Employee Data</caption>
  
  <thead>
    <tr>
      <th>Name</th>
      <th>Age</th>
      <th>Department</th>
    </tr>
  </thead>
  
  <tbody>
    <tr>
      <td>John</td>
      <td>30</td>
      <td>IT</td>
    </tr>
    <tr>
      <td>Jane</td>
      <td>25</td>
      <td>HR</td>
    </tr>
  </tbody>
  
  <tfoot>
    <tr>
      <td colspan="3">Total: 2 employees</td>
    </tr>
  </tfoot>
</table>
```

**Table Attributes:**
```html
<!-- Span multiple columns -->
<td colspan="2">Spans 2 columns</td>

<!-- Span multiple rows -->
<td rowspan="3">Spans 3 rows</td>
```

### 22. What is the `viewport` meta tag?
**Answer:** 
Controls layout on mobile browsers for responsive design.

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

**Properties:**
- `width=device-width`: Match screen width
- `initial-scale=1.0`: Initial zoom level (1 = 100%)
- `maximum-scale=5.0`: Maximum zoom
- `minimum-scale=0.5`: Minimum zoom
- `user-scalable=yes`: Allow zoom (default)

**Common Configurations:**
```html
<!-- Standard responsive -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Prevent zoom (not recommended for accessibility) -->
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">

<!-- Fixed width -->
<meta name="viewport" content="width=1024">
```

### 23. What is the `<iframe>` element?
**Answer:** 
Embeds another HTML document within the current page.

```html
<iframe src="https://example.com" 
        width="600" 
        height="400"
        frameborder="0"
        sandbox="allow-scripts allow-same-origin"
        loading="lazy"
        title="Embedded content">
</iframe>
```

**Attributes:**
- `src`: URL to embed
- `sandbox`: Security restrictions
- `loading`: `lazy` or `eager`
- `allow`: Feature policy

**Sandbox Values:**
- `allow-scripts`: Allow JavaScript
- `allow-forms`: Allow form submission
- `allow-same-origin`: Allow same-origin access
- `allow-popups`: Allow popups

**Use Cases:**
- Embed YouTube videos
- Google Maps
- Third-party widgets
- Ads

### 24. What is the `alt` attribute in images?
**Answer:**

```html
<img src="logo.png" alt="Company Logo" width="200" height="100">
```

**Importance:**
1. **Accessibility**: Screen readers read alt text for visually impaired users
2. **SEO**: Search engines use alt text to understand images
3. **Fallback**: Displayed when image fails to load
4. **Required**: HTML validation requires alt attribute

**Best Practices:**
```html
<!-- Descriptive alt text -->
<img src="sunset.jpg" alt="Orange sunset over ocean with palm trees">

<!-- Decorative images (empty alt) -->
<img src="decoration.png" alt="">

<!-- Avoid redundant text -->
<!-- Bad: alt="Image of sunset" -->
<!-- Good: alt="Sunset over ocean" -->
```

### 25. What is the difference between `<link>` and `<a>` tags?
**Answer:**

**`<link>`**: Defines relationship between document and external resource (in `<head>`)
```html
<head>
  <link rel="stylesheet" href="style.css">
  <link rel="icon" href="favicon.ico">
  <link rel="preload" href="font.woff2" as="font">
  <link rel="canonical" href="https://example.com/page">
</head>
```

**`<a>`**: Creates hyperlinks (in `<body>`)
```html
<body>
  <a href="page.html">Click here</a>
  <a href="mailto:email@example.com">Email us</a>
  <a href="tel:+1234567890">Call us</a>
</body>
```

### 26. What is accessibility (a11y) in HTML?
**Answer:** 
Making web content usable for people with disabilities.

**Best Practices:**
```html
<!-- Semantic HTML -->
<nav>, <main>, <article>, <header>, <footer>

<!-- Alt text for images -->
<img src="logo.png" alt="Company Logo">

<!-- Labels for form inputs -->
<label for="email">Email:</label>
<input type="email" id="email" name="email">

<!-- ARIA attributes -->
<button aria-label="Close dialog">×</button>
<div role="alert" aria-live="polite">Message sent!</div>

<!-- Keyboard navigation -->
<a href="#" tabindex="0">Focusable link</a>

<!-- Skip links -->
<a href="#main-content" class="skip-link">Skip to main content</a>

<!-- Language attribute -->
<html lang="en">

<!-- Heading hierarchy -->
<h1>Main Title</h1>
<h2>Section Title</h2>
<h3>Subsection Title</h3>
```

**ARIA Roles:**
- `role="navigation"`
- `role="main"`
- `role="banner"`
- `role="contentinfo"`
- `role="alert"`

### 27. What is SEO in HTML?
**Answer:** 
Search Engine Optimization techniques to improve search rankings.

```html
<head>
  <!-- Title (most important) -->
  <title>Page Title - Brand Name (50-60 chars)</title>
  
  <!-- Meta description -->
  <meta name="description" content="Page description (150-160 chars)">
  
  <!-- Keywords (less important now) -->
  <meta name="keywords" content="keyword1, keyword2, keyword3">
  
  <!-- Canonical URL (prevent duplicate content) -->
  <link rel="canonical" href="https://example.com/page">
  
  <!-- Open Graph (social media) -->
  <meta property="og:title" content="Page Title">
  <meta property="og:description" content="Description">
  <meta property="og:image" content="https://example.com/image.jpg">
  <meta property="og:url" content="https://example.com/page">
  <meta property="og:type" content="website">
  
  <!-- Twitter Card -->
  <meta name="twitter:card" content="summary_large_image">
  <meta name="twitter:title" content="Page Title">
  <meta name="twitter:description" content="Description">
  <meta name="twitter:image" content="https://example.com/image.jpg">
  
  <!-- Robots -->
  <meta name="robots" content="index, follow">
</head>

<body>
  <!-- Semantic HTML -->
  <header>
    <h1>Main Heading (only one per page)</h1>
  </header>
  
  <!-- Structured data (Schema.org) -->
  <script type="application/ld+json">
  {
    "@context": "https://schema.org",
    "@type": "Article",
    "headline": "Article Title",
    "author": "Author Name"
  }
  </script>
</body>
```

### 28. What are HTML5 APIs?
**Answer:**

**1. Geolocation API:**
```javascript
navigator.geolocation.getCurrentPosition(
  position => {
    console.log(position.coords.latitude);
    console.log(position.coords.longitude);
  },
  error => console.error(error)
);
```

**2. Drag and Drop API:**
```html
<div draggable="true" ondragstart="drag(event)">Drag me</div>
<div ondrop="drop(event)" ondragover="allowDrop(event)">Drop here</div>
```

**3. Web Storage API:**
```javascript
localStorage.setItem('key', 'value');
sessionStorage.setItem('key', 'value');
```

**4. Web Workers API:**
```javascript
const worker = new Worker('worker.js');
worker.postMessage('Hello');
worker.onmessage = e => console.log(e.data);
```

**5. History API:**
```javascript
history.pushState({page: 1}, "title", "?page=1");
history.back();
history.forward();
```

**6. Fetch API:**
```javascript
fetch('/api/data')
  .then(response => response.json())
  .then(data => console.log(data));
```

### 29. What is the difference between `<head>` and `<header>`?
**Answer:**

**`<head>`**: Contains metadata (not visible on page)
```html
<head>
  <title>Page Title</title>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="style.css">
  <script src="app.js"></script>
</head>
```

**`<header>`**: Contains introductory content (visible on page)
```html
<body>
  <header>
    <h1>Website Title</h1>
    <nav>
      <a href="/">Home</a>
      <a href="/about">About</a>
    </nav>
  </header>
</body>
```

### 30. What is the `srcset` attribute in images?
**Answer:** 
Provides multiple image sources for responsive images.

```html
<!-- Different resolutions -->
<img src="small.jpg"
     srcset="small.jpg 500w,
             medium.jpg 1000w,
             large.jpg 1500w"
     sizes="(max-width: 600px) 500px,
            (max-width: 1200px) 1000px,
            1500px"
     alt="Responsive image">

<!-- Different pixel densities -->
<img src="image.jpg"
     srcset="image.jpg 1x,
             image@2x.jpg 2x,
             image@3x.jpg 3x"
     alt="High DPI image">
```

**Benefits:**
- Serves appropriate image size
- Reduces bandwidth
- Improves performance
- Better user experience

---

## **Additional Important Concepts**

### 31. What is the difference between inline and block elements?
**Answer:**

**Block Elements:**
- Take full width available
- Start on new line
- Can contain inline and block elements
- Examples: `<div>`, `<p>`, `<h1>`, `<section>`, `<article>`

**Inline Elements:**
- Take only necessary width
- Stay on same line
- Can only contain inline elements
- Examples: `<span>`, `<a>`, `<strong>`, `<em>`, `<img>`

**Inline-Block:**
- Hybrid: stays inline but accepts width/height
- Example: `<button>`, `<input>`

### 32. What are HTML comments?
**Answer:**
```html
<!-- This is a comment -->

<!-- 
  Multi-line comment
  Can span multiple lines
-->

<!-- TODO: Add more content here -->
```

**Note:** Comments are visible in page source but not rendered.

### 33. What is the `<base>` tag?
**Answer:**
```html
<head>
  <base href="https://example.com/" target="_blank">
</head>

<!-- All relative URLs will use base -->
<a href="page.html">Link</a>  <!-- Goes to https://example.com/page.html -->
```

### 34. What are HTML forms and form validation?
**Answer:**
```html
<form action="/submit" method="POST" novalidate>
  <!-- Required field -->
  <input type="text" name="username" required>
  
  <!-- Pattern validation -->
  <input type="text" 
         pattern="[A-Za-z]{3,}" 
         title="At least 3 letters">
  
  <!-- Min/Max length -->
  <input type="password" 
         minlength="8" 
         maxlength="20">
  
  <!-- Min/Max value -->
  <input type="number" min="1" max="100">
  
  <!-- Email validation -->
  <input type="email" required>
  
  <!-- Custom validation message -->
  <input type="text" 
         oninvalid="this.setCustomValidity('Please enter valid data')"
         oninput="this.setCustomValidity('')">
  
  <button type="submit">Submit</button>
</form>
```

### 35. What is the `loading` attribute for images?
**Answer:**
```html
<!-- Lazy loading (loads when near viewport) -->
<img src="image.jpg" loading="lazy" alt="Lazy loaded image">

<!-- Eager loading (loads immediately) -->
<img src="logo.jpg" loading="eager" alt="Logo">
```

**Benefits:**
- Improves initial page load
- Reduces bandwidth
- Better performance

---

**Total Questions: 35+ comprehensive HTML interview questions with detailed answers**
