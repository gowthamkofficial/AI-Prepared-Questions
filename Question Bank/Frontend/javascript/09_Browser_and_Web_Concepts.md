# BROWSER AND WEB CONCEPTS ANSWERS

---

## 54. What is the DOM?

### Answer:
- DOM stands for **Document Object Model**
- Tree-like **representation** of HTML document in memory
- Allows JavaScript to **access and manipulate** HTML elements
- Each HTML element becomes a **node** in the DOM tree
- DOM is an **API** provided by the browser
- Changes to DOM are **reflected** in the webpage immediately

### Theoretical Keywords:
**Document Object Model**, **Node tree**, **Element manipulation**,  
**Browser API**, **Dynamic content**, **DOM nodes**,  
**Document interface**, **Real-time updates**

### Example:
```javascript
// DOM Tree Structure
/*
  document
    └── html
        ├── head
        │   └── title
        └── body
            ├── div
            │   └── p
            └── ul
                ├── li
                └── li
*/

// Selecting elements
const element = document.getElementById('myId');
const elements = document.getElementsByClassName('myClass');
const tags = document.getElementsByTagName('div');
const query = document.querySelector('.myClass');      // First match
const queryAll = document.querySelectorAll('.myClass'); // All matches

// Modifying elements
element.textContent = "New text";
element.innerHTML = "<strong>Bold text</strong>";
element.style.color = "red";
element.classList.add('active');
element.setAttribute('data-id', '123');

// Creating elements
const newDiv = document.createElement('div');
newDiv.textContent = "Hello";
document.body.appendChild(newDiv);

// Removing elements
element.remove();
// or
element.parentNode.removeChild(element);

// Traversing DOM
const parent = element.parentNode;
const children = element.childNodes;
const firstChild = element.firstChild;
const nextSibling = element.nextSibling;
```

---

## 55. What is BOM?

### Answer:
- BOM stands for **Browser Object Model**
- Allows JavaScript to **interact with the browser** (not the document)
- Includes **window**, **navigator**, **screen**, **location**, **history** objects
- **window** is the global object in browsers
- BOM is **not standardized** (varies slightly between browsers)
- Provides browser information, navigation, and control

### Theoretical Keywords:
**Browser Object Model**, **window object**, **navigator object**,  
**location object**, **history object**, **screen object**,  
**Browser interaction**, **Global object**

### Example:
```javascript
// WINDOW OBJECT (global)
console.log(window.innerWidth);   // Browser window width
console.log(window.innerHeight);  // Browser window height
window.alert("Hello!");           // Alert dialog
window.confirm("Are you sure?");  // Confirm dialog
window.prompt("Enter name:");     // Input dialog

// Global variables are window properties
var globalVar = "test";
console.log(window.globalVar);    // "test"

// NAVIGATOR OBJECT (browser info)
console.log(navigator.userAgent);    // Browser user agent
console.log(navigator.language);     // Browser language
console.log(navigator.platform);     // Operating system
console.log(navigator.onLine);       // Online status
navigator.geolocation.getCurrentPosition(pos => {
    console.log(pos.coords.latitude);
});

// LOCATION OBJECT (URL info)
console.log(location.href);     // Full URL
console.log(location.hostname); // Domain name
console.log(location.pathname); // Path
console.log(location.search);   // Query string
location.reload();              // Refresh page
location.href = "https://google.com";  // Navigate

// HISTORY OBJECT (browser history)
history.back();      // Go back
history.forward();   // Go forward
history.go(-2);      // Go back 2 pages
console.log(history.length);

// SCREEN OBJECT (screen info)
console.log(screen.width);        // Screen width
console.log(screen.height);       // Screen height
console.log(screen.availWidth);   // Available width
console.log(screen.colorDepth);   // Color depth
```

---

## 56. Difference between `localStorage`, `sessionStorage`, and cookies

### Answer:
- **localStorage**: Persists **forever** until manually cleared, ~5-10MB
- **sessionStorage**: Cleared when **tab/browser closes**, ~5-10MB
- **Cookies**: Can set **expiry**, sent with every HTTP request, ~4KB
- **Scope**: localStorage/sessionStorage are per origin, cookies can be domain-wide
- **Access**: localStorage/sessionStorage via JS only, cookies via JS and HTTP headers
- **Use case**: localStorage for preferences, sessionStorage for session data, cookies for auth

### Theoretical Keywords:
**Persistent storage**, **Session storage**, **HTTP cookies**,  
**Expiration**, **Size limits**, **Same-origin policy**,  
**Server access**, **Client-side storage**

### Example:
```javascript
// LOCAL STORAGE - persists forever
localStorage.setItem('username', 'John');
localStorage.getItem('username');         // "John"
localStorage.removeItem('username');
localStorage.clear();                      // Clear all
// Persists across browser restarts

// SESSION STORAGE - cleared on tab close
sessionStorage.setItem('tempData', 'value');
sessionStorage.getItem('tempData');        // "value"
// Gone when tab closes

// Storage only accepts strings
localStorage.setItem('user', JSON.stringify({ name: 'John' }));
const user = JSON.parse(localStorage.getItem('user'));

// COOKIES
// Set cookie
document.cookie = "username=John";
document.cookie = "username=John; expires=Thu, 31 Dec 2025 23:59:59 GMT";
document.cookie = "username=John; max-age=86400";  // 1 day
document.cookie = "username=John; path=/; secure; SameSite=Strict";

// Read cookies (returns all as string)
console.log(document.cookie);  // "username=John; other=value"

// Parse cookies
const cookies = document.cookie.split(';').reduce((acc, cookie) => {
    const [key, value] = cookie.trim().split('=');
    acc[key] = value;
    return acc;
}, {});

// COMPARISON TABLE
/*
Feature          | localStorage  | sessionStorage | Cookies
-----------------+---------------+----------------+-----------
Capacity         | ~5-10MB       | ~5-10MB        | ~4KB
Expiration       | Never         | Tab close      | Configurable
Sent to server   | No            | No             | Yes (every request)
Accessible from  | Same origin   | Same tab       | All subdomains*
API              | Simple        | Simple         | Complex string
*/
```

---

## 57. What is event bubbling?

### Answer:
- Event bubbling is when an event **propagates from target to ancestors**
- Event first triggers on **innermost element**, then bubbles up
- Goes from **child → parent → grandparent → ... → document → window**
- Most events bubble (click, change, keydown, etc.)
- Some events don't bubble (focus, blur, load)
- Can be stopped with **event.stopPropagation()**

### Theoretical Keywords:
**Event propagation**, **Bubbling phase**, **Target element**,  
**Parent elements**, **stopPropagation**, **Event flow**,  
**DOM tree traversal**, **Ancestor elements**

### Example:
```html
<div id="grandparent">
    Grandparent
    <div id="parent">
        Parent
        <button id="child">Child Button</button>
    </div>
</div>
```

```javascript
// Event bubbling demonstration
document.getElementById('grandparent').addEventListener('click', () => {
    console.log('Grandparent clicked');
});

document.getElementById('parent').addEventListener('click', () => {
    console.log('Parent clicked');
});

document.getElementById('child').addEventListener('click', () => {
    console.log('Child clicked');
});

// Clicking the button outputs:
// "Child clicked"
// "Parent clicked"
// "Grandparent clicked"
// Event bubbles up!

// Stop propagation
document.getElementById('child').addEventListener('click', (event) => {
    console.log('Child clicked');
    event.stopPropagation();  // Stops bubbling here
});
// Now only "Child clicked" appears

// Check if event bubbles
event.bubbles;  // true for most events

// Event target vs currentTarget
document.getElementById('parent').addEventListener('click', (e) => {
    console.log('Target:', e.target.id);        // What was clicked
    console.log('CurrentTarget:', e.currentTarget.id);  // Handler's element
});
// Clicking child: target = "child", currentTarget = "parent"
```

---

## 58. What is event capturing?

### Answer:
- Event capturing is when event propagates from **ancestors to target**
- Goes from **window → document → ... → grandparent → parent → target**
- Also called **"trickling down"** phase
- Capturing happens **BEFORE** bubbling
- Enable with `addEventListener(event, handler, true)` or `{capture: true}`
- Rarely used, bubbling is more common

### Theoretical Keywords:
**Capturing phase**, **Event trickling**, **Top-down propagation**,  
**addEventListener capture**, **useCapture parameter**, **Event phases**,  
**DOM event flow**, **Ancestor to descendant**

### Example:
```javascript
// Event flow: Capturing → Target → Bubbling

// Enable capturing with third parameter = true
document.getElementById('grandparent').addEventListener('click', () => {
    console.log('Grandparent - Capturing');
}, true);  // Capturing phase

document.getElementById('parent').addEventListener('click', () => {
    console.log('Parent - Capturing');
}, { capture: true });  // Alternative syntax

document.getElementById('child').addEventListener('click', () => {
    console.log('Child - Target');
});

// Bubbling handlers (default)
document.getElementById('grandparent').addEventListener('click', () => {
    console.log('Grandparent - Bubbling');
});

document.getElementById('parent').addEventListener('click', () => {
    console.log('Parent - Bubbling');
});

// Clicking child outputs (in order):
// "Grandparent - Capturing"  (capturing, top-down)
// "Parent - Capturing"        (capturing, top-down)
// "Child - Target"            (target phase)
// "Parent - Bubbling"         (bubbling, bottom-up)
// "Grandparent - Bubbling"    (bubbling, bottom-up)

// Event phases constants
event.CAPTURING_PHASE;  // 1
event.AT_TARGET;        // 2
event.BUBBLING_PHASE;   // 3
console.log(event.eventPhase);  // Current phase

// stopPropagation stops both capturing and bubbling
document.getElementById('parent').addEventListener('click', (e) => {
    e.stopPropagation();
}, true);  // Stops in capturing phase
```

---

## 59. What is event delegation?

### Answer:
- Event delegation is attaching **one handler to parent** for multiple children
- Uses **event bubbling** to catch events from descendants
- More **efficient** than attaching handlers to each child
- Works for **dynamically added elements**
- Use `event.target` to identify which child triggered event
- Common pattern for lists, tables, and dynamic content

### Theoretical Keywords:
**Parent handler**, **Event bubbling utilization**, **Dynamic elements**,  
**Memory efficiency**, **event.target**, **Performance optimization**,  
**List handling**, **Single handler pattern**

### Example:
```javascript
// WITHOUT Delegation - inefficient
const buttons = document.querySelectorAll('.btn');
buttons.forEach(btn => {
    btn.addEventListener('click', handleClick);
});
// Problem: Many handlers, doesn't work for new buttons

// WITH Delegation - efficient
document.getElementById('button-container').addEventListener('click', (e) => {
    if (e.target.matches('.btn')) {  // Check if click was on button
        console.log('Button clicked:', e.target.textContent);
    }
});
// Benefits: One handler, works for new buttons!

// Dynamic elements example
const list = document.getElementById('todo-list');

// Add handler to parent
list.addEventListener('click', (e) => {
    if (e.target.matches('.delete-btn')) {
        e.target.parentElement.remove();  // Delete item
    }
    if (e.target.matches('.edit-btn')) {
        // Edit logic
    }
});

// New items will automatically work!
function addItem(text) {
    const li = document.createElement('li');
    li.innerHTML = `
        ${text}
        <button class="delete-btn">Delete</button>
        <button class="edit-btn">Edit</button>
    `;
    list.appendChild(li);
}

// closest() for nested elements
container.addEventListener('click', (e) => {
    const card = e.target.closest('.card');  // Find ancestor
    if (card) {
        console.log('Card clicked:', card.dataset.id);
    }
});

// Benefits of Event Delegation:
// 1. Memory efficient (one handler vs many)
// 2. Works with dynamic elements
// 3. Less code to write and maintain
// 4. Easier to add/remove child elements
```

---
