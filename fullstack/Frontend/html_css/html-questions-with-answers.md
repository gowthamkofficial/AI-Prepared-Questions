# HTML Interview Questions with Answers

## **Most Important & Frequently Asked Questions**

### **1. What is HTML?**
**Answer:** HTML (HyperText Markup Language) is the standard markup language used to create web pages. It describes the structure and content of a webpage using elements and tags.

---

### **2. What is the difference between HTML and HTML5?**
**Answer:**
- **HTML5** is the latest version with new features like semantic tags, multimedia support, and APIs
- **New Elements:** `<header>`, `<footer>`, `<article>`, `<section>`, `<nav>`, `<aside>`
- **Multimedia:** `<audio>`, `<video>` tags without plugins
- **Graphics:** `<canvas>`, `<svg>` support
- **Storage:** localStorage and sessionStorage
- **Form Controls:** New input types (email, date, range, color)
- **APIs:** Geolocation, Drag & Drop, Web Workers

---

### **3. What are semantic HTML tags?**
**Answer:** Semantic tags clearly describe their meaning to both browser and developer.

**Examples:**
- `<header>` - Header section
- `<nav>` - Navigation links
- `<main>` - Main content
- `<article>` - Independent content
- `<section>` - Thematic grouping
- `<aside>` - Side content
- `<footer>` - Footer section
- `<figure>` - Self-contained content
- `<time>` - Date/time

**Benefits:**
- Better SEO
- Improved accessibility
- Easier to read and maintain

---

### **4. What is the difference between `<div>` and `<span>`?**
**Answer:**
- **`<div>`**: Block-level element, takes full width, starts on new line
- **`<span>`**: Inline element, takes only necessary width, doesn't break line

```html
<div>This is a block element</div>
<span>This is inline</span> <span>Same line</span>
```

---

### **5. What is the DOCTYPE declaration?**
**Answer:** DOCTYPE tells the browser which HTML version is being used.

```html
<!DOCTYPE html> <!-- HTML5 -->
```

**Purpose:**
- Ensures browser renders page in standards mode
- Prevents quirks mode rendering
- Must be the first line in HTML document

---

### **6. What are meta tags?**
**Answer:** Meta tags provide metadata about the HTML document.

```html
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Page description for SEO">
<meta name="keywords" content="HTML, CSS, JavaScript">
<meta name="author" content="Your Name">
```

**Common Uses:**
- Character encoding
- Viewport settings for responsive design
- SEO optimization
- Social media sharing (Open Graph)

---

### **7. What is the difference between `<link>` and `<a>` tags?**
**Answer:**
- **`<link>`**: Defines relationship between document and external resource (in `<head>`)
- **`<a>`**: Creates hyperlinks (in `<body>`)

```html
<head>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <a href="page.html">Click here</a>
</body>
```

---

### **8. What are data attributes?**
**Answer:** Custom attributes to store extra information on HTML elements.

```html
<div data-user-id="12345" data-role="admin">User Info</div>

<script>
  const div = document.querySelector('div');
  console.log(div.dataset.userId); // "12345"
  console.log(div.dataset.role);   // "admin"
</script>
```

---

### **9. What is the difference between `<script>`, `<script async>`, and `<script defer>`?**
**Answer:**

| Type | HTML Parsing | Script Download | Script Execution |
|------|--------------|-----------------|------------------|
| `<script>` | Paused | Immediate | Immediate (blocks parsing) |
| `<script async>` | Continues | Parallel | When downloaded (may block) |
| `<script defer>` | Continues | Parallel | After HTML parsing |

```html
<script src="app.js"></script>           <!-- Blocks parsing -->
<script src="app.js" async></script>     <!-- Executes when ready -->
<script src="app.js" defer></script>     <!-- Executes after DOM -->
```

**Best Practice:** Use `defer` for scripts that need DOM access.

---

### **10. What are void/self-closing elements?**
**Answer:** Elements that don't have closing tags or content.

```html
<img src="image.jpg" alt="Image">
<br>
<hr>
<input type="text">
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
<area>
<base>
<col>
<embed>
<source>
```

---

### **11. What is the difference between `id` and `class`?**
**Answer:**

| Feature | id | class |
|---------|-----|-------|
| Uniqueness | Must be unique | Can be reused |
| Per Element | Only one id | Multiple classes |
| CSS Selector | `#idName` | `.className` |
| JavaScript | `getElementById()` | `getElementsByClassName()` |
| Specificity | Higher (100) | Lower (10) |

```html
<div id="unique" class="box primary">Content</div>
<div class="box">Another box</div>
```

---

### **12. What are form elements and input types in HTML5?**
**Answer:**

**New Input Types:**
```html
<input type="email">
<input type="url">
<input type="tel">
<input type="number">
<input type="range">
<input type="date">
<input type="time">
<input type="datetime-local">
<input type="month">
<input type="week">
<input type="color">
<input type="search">
```

**Form Attributes:**
- `required` - Mandatory field
- `placeholder` - Hint text
- `pattern` - Regex validation
- `min`, `max` - Range limits
- `autocomplete` - Auto-fill
- `autofocus` - Auto focus on load

---

### **13. What is the difference between `<section>`, `<article>`, and `<div>`?**
**Answer:**

- **`<section>`**: Thematic grouping of content with heading
- **`<article>`**: Independent, self-contained content
- **`<div>`**: Generic container with no semantic meaning

```html
<article>
  <h2>Blog Post Title</h2>
  <section>
    <h3>Introduction</h3>
    <p>Content...</p>
  </section>
  <section>
    <h3>Main Content</h3>
    <p>Content...</p>
  </section>
</article>

<div class="wrapper">Generic container</div>
```

---

### **14. What is the `alt` attribute in images?**
**Answer:** Alternative text displayed when image cannot load.

```html
<img src="logo.png" alt="Company Logo">
```

**Importance:**
- Accessibility for screen readers
- SEO optimization
- Displayed when image fails to load
- Required for valid HTML

---

### **15. What are `<iframe>` elements?**
**Answer:** Embeds another HTML document within current page.

```html
<iframe src="https://example.com" 
        width="600" 
        height="400"
        frameborder="0"
        sandbox="allow-scripts">
</iframe>
```

**Common Uses:**
- Embed YouTube videos
- Google Maps
- Third-party widgets
- Ads

**Security:** Use `sandbox` attribute to restrict capabilities.

---

### **16. What is the difference between `<strong>` and `<b>`, `<em>` and `<i>`?**
**Answer:**

| Semantic | Visual | Purpose |
|----------|--------|---------|
| `<strong>` | `<b>` | Strong importance |
| `<em>` | `<i>` | Emphasis |

```html
<strong>Important text</strong> <!-- Semantic + Bold -->
<b>Bold text</b>                <!-- Just bold -->

<em>Emphasized text</em>        <!-- Semantic + Italic -->
<i>Italic text</i>              <!-- Just italic -->
```

**Best Practice:** Use semantic tags for better accessibility and SEO.

---

### **17. What are HTML entities?**
**Answer:** Special characters represented by codes.

```html
&lt;    <!-- < -->
&gt;    <!-- > -->
&amp;   <!-- & -->
&quot;  <!-- " -->
&apos;  <!-- ' -->
&nbsp;  <!-- Non-breaking space -->
&copy;  <!-- © -->
&reg;   <!-- ® -->
```

---

### **18. What is the `target` attribute in anchor tags?**
**Answer:** Specifies where to open linked document.

```html
<a href="page.html" target="_self">Same tab (default)</a>
<a href="page.html" target="_blank">New tab</a>
<a href="page.html" target="_parent">Parent frame</a>
<a href="page.html" target="_top">Full window</a>
```

**Security:** Use `rel="noopener noreferrer"` with `target="_blank"`.

---

### **19. What is the difference between `<ol>`, `<ul>`, and `<dl>`?**
**Answer:**

```html
<!-- Ordered List -->
<ol>
  <li>First item</li>
  <li>Second item</li>
</ol>

<!-- Unordered List -->
<ul>
  <li>Bullet point</li>
  <li>Another point</li>
</ul>

<!-- Description List -->
<dl>
  <dt>Term</dt>
  <dd>Definition</dd>
</dl>
```

---

### **20. What are `<table>` elements and their structure?**
**Answer:**

```html
<table>
  <thead>
    <tr>
      <th>Header 1</th>
      <th>Header 2</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Data 1</td>
      <td>Data 2</td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <td>Footer 1</td>
      <td>Footer 2</td>
    </tr>
  </tfoot>
</table>
```

**Attributes:**
- `colspan` - Span multiple columns
- `rowspan` - Span multiple rows

---

### **21. What is the `viewport` meta tag?**
**Answer:** Controls layout on mobile browsers.

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

**Properties:**
- `width=device-width` - Match screen width
- `initial-scale=1.0` - Initial zoom level
- `maximum-scale` - Max zoom
- `minimum-scale` - Min zoom
- `user-scalable=no` - Disable zoom (not recommended)

---

### **22. What are `<audio>` and `<video>` tags?**
**Answer:**

```html
<audio controls>
  <source src="audio.mp3" type="audio/mpeg">
  <source src="audio.ogg" type="audio/ogg">
  Your browser doesn't support audio.
</audio>

<video width="640" height="360" controls>
  <source src="video.mp4" type="video/mp4">
  <source src="video.webm" type="video/webm">
  Your browser doesn't support video.
</video>
```

**Attributes:**
- `controls` - Show controls
- `autoplay` - Auto play
- `loop` - Repeat
- `muted` - Mute audio
- `poster` - Thumbnail (video only)

---

### **23. What is the `<canvas>` element?**
**Answer:** Used to draw graphics via JavaScript.

```html
<canvas id="myCanvas" width="500" height="300"></canvas>

<script>
  const canvas = document.getElementById('myCanvas');
  const ctx = canvas.getContext('2d');
  ctx.fillStyle = 'blue';
  ctx.fillRect(10, 10, 100, 100);
</script>
```

---

### **24. What is local storage vs session storage?**
**Answer:**

| Feature | localStorage | sessionStorage |
|---------|--------------|----------------|
| Lifetime | Permanent | Until tab closes |
| Scope | All tabs | Single tab |
| Capacity | ~5-10MB | ~5-10MB |

```html
<script>
  // localStorage
  localStorage.setItem('key', 'value');
  localStorage.getItem('key');
  localStorage.removeItem('key');
  
  // sessionStorage
  sessionStorage.setItem('key', 'value');
  sessionStorage.getItem('key');
</script>
```

---

### **25. What is the difference between `GET` and `POST` methods?**
**Answer:**

| Feature | GET | POST |
|---------|-----|------|
| Data Location | URL | Request body |
| Visibility | Visible in URL | Hidden |
| Security | Less secure | More secure |
| Data Limit | ~2048 characters | No limit |
| Caching | Can be cached | Not cached |
| Bookmarkable | Yes | No |

```html
<form action="/search" method="GET">
  <input type="text" name="q">
</form>

<form action="/submit" method="POST">
  <input type="password" name="pwd">
</form>
```

---

## **Additional Important Questions**

### **26. What is accessibility (a11y) in HTML?**
**Answer:** Making web content usable for people with disabilities.

**Best Practices:**
```html
<!-- Use semantic HTML -->
<nav>, <main>, <article>

<!-- Alt text for images -->
<img src="logo.png" alt="Company Logo">

<!-- Labels for form inputs -->
<label for="email">Email:</label>
<input type="email" id="email">

<!-- ARIA attributes -->
<button aria-label="Close">×</button>

<!-- Keyboard navigation -->
<a href="#" tabindex="0">Link</a>
```

---

### **27. What is SEO in HTML?**
**Answer:** Search Engine Optimization techniques.

```html
<head>
  <title>Page Title - Brand Name</title>
  <meta name="description" content="Page description">
  <meta name="keywords" content="keyword1, keyword2">
  
  <!-- Open Graph for social media -->
  <meta property="og:title" content="Title">
  <meta property="og:description" content="Description">
  <meta property="og:image" content="image.jpg">
  
  <!-- Canonical URL -->
  <link rel="canonical" href="https://example.com/page">
</head>
```

---

### **28. What are HTML5 APIs?**
**Answer:**
- **Geolocation API** - Get user location
- **Drag and Drop API** - Drag elements
- **Web Storage API** - localStorage/sessionStorage
- **Web Workers API** - Background threads
- **Canvas API** - Draw graphics
- **History API** - Manipulate browser history
- **Fetch API** - Network requests

---

### **29. What is the difference between `<head>` and `<header>`?**
**Answer:**
- **`<head>`**: Contains metadata (not visible)
- **`<header>`**: Contains introductory content (visible)

```html
<head>
  <title>Page Title</title>
  <meta charset="UTF-8">
</head>
<body>
  <header>
    <h1>Website Header</h1>
    <nav>Navigation</nav>
  </header>
</body>
```

---

### **30. What is the `srcset` attribute in images?**
**Answer:** Provides multiple image sources for responsive images.

```html
<img src="small.jpg"
     srcset="small.jpg 500w,
             medium.jpg 1000w,
             large.jpg 1500w"
     sizes="(max-width: 600px) 500px,
            (max-width: 1200px) 1000px,
            1500px"
     alt="Responsive image">
```

---

## **Quick Revision Checklist**

✅ HTML5 new features and semantic tags  
✅ Difference between block and inline elements  
✅ Form elements and validation  
✅ Meta tags and SEO  
✅ Script loading (async/defer)  
✅ Data attributes  
✅ Accessibility basics  
✅ Storage APIs  
✅ Multimedia elements  
✅ Table structure  

---

**Note:** These questions are frequently asked in service-based companies like Infosys, Wipro, TCS, Cognizant, and Capgemini. Focus on explaining concepts clearly with examples.
