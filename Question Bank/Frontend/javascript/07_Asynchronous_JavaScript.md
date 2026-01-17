# ASYNCHRONOUS JAVASCRIPT ANSWERS

---

## 42. What is synchronous vs asynchronous execution?

### Answer:
- **Synchronous**: Code executes **line by line**, blocking until complete
- **Asynchronous**: Code can **run in background**, doesn't block execution
- Synchronous waits for each operation to complete
- Asynchronous allows other code to run while waiting
- JavaScript uses **event loop** for async operations
- Network requests, timers, file I/O are typically async

### Theoretical Keywords:
**Blocking vs non-blocking**, **Sequential execution**, **Concurrent execution**,  
**Event loop**, **Callback**, **Promise**, **async/await**,  
**Web APIs**, **Task queue**

### Example:
```javascript
// SYNCHRONOUS - Blocking
console.log("1");
console.log("2");
console.log("3");
// Output: 1, 2, 3 (in order)

// Each line waits for previous to complete
function syncOperation() {
    for (let i = 0; i < 1000000000; i++) {}  // Blocks everything!
    return "Done";
}
console.log(syncOperation());  // UI freezes until done

// ASYNCHRONOUS - Non-blocking
console.log("1");
setTimeout(() => {
    console.log("2");  // Runs after delay
}, 1000);
console.log("3");
// Output: 1, 3, 2 (3 doesn't wait for 2)

// Async doesn't block
fetch('/api/data')
    .then(response => response.json())
    .then(data => console.log(data));
console.log("This runs immediately!");  // Doesn't wait for fetch
```

---

## 43. What is callback hell?

### Answer:
- Callback hell is **deeply nested callbacks** that form a pyramid shape
- Also called **"Pyramid of Doom"**
- Makes code **hard to read, maintain, and debug**
- Occurs when handling multiple sequential async operations
- **Error handling** becomes complex and repetitive
- Solved by **Promises** and **async/await**

### Theoretical Keywords:
**Pyramid of Doom**, **Nested callbacks**, **Inversion of control**,  
**Code readability**, **Error propagation**, **Maintainability**,  
**Promise solution**, **Async/await solution**

### Example:
```javascript
// CALLBACK HELL EXAMPLE
// Getting user, then orders, then order details, then shipping
getUser(userId, function(user) {
    getOrders(user.id, function(orders) {
        getOrderDetails(orders[0].id, function(details) {
            getShippingInfo(details.shippingId, function(shipping) {
                updateUI(user, orders, details, shipping, function() {
                    // More nested callbacks...
                    console.log("Finally done!");
                }, function(error) {
                    console.log("UI error:", error);
                });
            }, function(error) {
                console.log("Shipping error:", error);
            });
        }, function(error) {
            console.log("Details error:", error);
        });
    }, function(error) {
        console.log("Orders error:", error);
    });
}, function(error) {
    console.log("User error:", error);
});

// PROBLEMS:
// 1. Hard to read (pyramid shape)
// 2. Error handling repeated at each level
// 3. Difficult to maintain and modify
// 4. No easy way to run operations in parallel

// SOLUTION WITH PROMISES
getUser(userId)
    .then(user => getOrders(user.id))
    .then(orders => getOrderDetails(orders[0].id))
    .then(details => getShippingInfo(details.shippingId))
    .then(shipping => updateUI(shipping))
    .catch(error => console.log("Error:", error));  // Single error handler!

// SOLUTION WITH ASYNC/AWAIT
async function loadUserData(userId) {
    try {
        const user = await getUser(userId);
        const orders = await getOrders(user.id);
        const details = await getOrderDetails(orders[0].id);
        const shipping = await getShippingInfo(details.shippingId);
        await updateUI(shipping);
    } catch (error) {
        console.log("Error:", error);
    }
}
```

---

## 44. What is a `Promise`?

### Answer:
- A Promise is an object representing **eventual completion or failure** of async operation
- Has three states: **pending**, **fulfilled**, **rejected**
- Created using `new Promise((resolve, reject) => {})`
- **resolve()** for success, **reject()** for failure
- Use **.then()** for success, **.catch()** for errors
- Solves callback hell with **cleaner chaining**

### Theoretical Keywords:
**Asynchronous operation**, **Pending/Fulfilled/Rejected**,  
**resolve/reject**, **then/catch/finally**, **Promise chaining**,  
**Error handling**, **Thenable**, **Microtask queue**

### Example:
```javascript
// Creating a Promise
const myPromise = new Promise((resolve, reject) => {
    const success = true;
    
    setTimeout(() => {
        if (success) {
            resolve("Operation successful!");  // Fulfilled
        } else {
            reject("Operation failed!");       // Rejected
        }
    }, 1000);
});

// Consuming a Promise
myPromise
    .then(result => {
        console.log(result);  // "Operation successful!"
        return "Next step";
    })
    .then(next => {
        console.log(next);    // "Next step"
    })
    .catch(error => {
        console.log(error);   // If rejected
    })
    .finally(() => {
        console.log("Cleanup");  // Always runs
    });

// Real-world example: Fetch API returns Promise
fetch('https://api.example.com/data')
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));

// Promise utility methods
Promise.all([promise1, promise2])     // Wait for all
Promise.race([promise1, promise2])    // First to complete
Promise.allSettled([p1, p2])          // All results (success or fail)
Promise.any([promise1, promise2])     // First successful
```

---

## 45. Promise states

### Answer:
- **Pending**: Initial state, neither fulfilled nor rejected
- **Fulfilled**: Operation completed successfully (resolved)
- **Rejected**: Operation failed (rejected with error)
- Promise state can only change **once** (pending → fulfilled/rejected)
- Once settled (fulfilled/rejected), state is **immutable**
- Fulfilled calls **.then()** handlers, Rejected calls **.catch()**

### Theoretical Keywords:
**Pending state**, **Fulfilled state**, **Rejected state**,  
**State transition**, **Settlement**, **Immutable state**,  
**resolve callback**, **reject callback**

### Example:
```javascript
// PENDING State - initial
const pendingPromise = new Promise((resolve, reject) => {
    // Not resolved or rejected yet
    console.log("Promise is pending...");
});
console.log(pendingPromise);  // Promise {<pending>}

// FULFILLED State - success
const fulfilledPromise = new Promise((resolve, reject) => {
    resolve("Success!");
});
console.log(fulfilledPromise);  // Promise {<fulfilled>: "Success!"}

// REJECTED State - failure
const rejectedPromise = new Promise((resolve, reject) => {
    reject("Error!");
});
console.log(rejectedPromise);  // Promise {<rejected>: "Error!"}

// State transitions
const example = new Promise((resolve, reject) => {
    resolve("First");   // State: pending → fulfilled
    resolve("Second");  // Ignored! State already settled
    reject("Error");    // Ignored! State already settled
});

example.then(result => console.log(result));  // "First"

// Checking promise state (for debugging)
const checkState = async (promise) => {
    const result = await Promise.race([
        promise.then(() => 'fulfilled', () => 'rejected'),
        Promise.resolve('pending')
    ]);
    return result;
};

// Quick promises
const resolved = Promise.resolve("value");    // Immediately fulfilled
const rejected = Promise.reject("error");     // Immediately rejected
```

---

## 46. Promise chaining

### Answer:
- **Chaining** connects multiple `.then()` calls sequentially
- Each `.then()` returns a **new Promise**
- Return value becomes the next `.then()`'s input
- Allows **sequential async operations** without nesting
- Single `.catch()` at end handles **any error** in chain
- Can return **values** or **Promises** from `.then()`

### Theoretical Keywords:
**Sequential execution**, **then returns Promise**, **Value propagation**,  
**Error propagation**, **Flat structure**, **Avoid nesting**,  
**Return value**, **Next handler**

### Example:
```javascript
// Basic Promise chaining
fetchUser(1)
    .then(user => {
        console.log("User:", user);
        return fetchOrders(user.id);  // Returns Promise
    })
    .then(orders => {
        console.log("Orders:", orders);
        return orders.length;  // Returns value
    })
    .then(count => {
        console.log("Order count:", count);
    })
    .catch(error => {
        console.log("Error:", error);  // Catches any error above
    });

// Each .then() returns a new Promise
const promise1 = Promise.resolve(1);
const promise2 = promise1.then(x => x + 1);
const promise3 = promise2.then(x => x + 1);

console.log(promise1 === promise2);  // false (different promises)

// Value transformation through chain
Promise.resolve(10)
    .then(x => x * 2)     // 20
    .then(x => x + 5)     // 25
    .then(x => x / 5)     // 5
    .then(console.log);   // Output: 5

// Returning Promise in chain
Promise.resolve()
    .then(() => {
        return new Promise(resolve => {
            setTimeout(() => resolve("Delayed"), 1000);
        });
    })
    .then(result => console.log(result));  // "Delayed" after 1 second

// Error in chain skips to catch
Promise.resolve()
    .then(() => { throw new Error("Oops!"); })
    .then(() => console.log("Skipped!"))  // Never runs
    .then(() => console.log("Also skipped!"))  // Never runs
    .catch(error => console.log("Caught:", error.message));  // "Caught: Oops!"
```

---

## 47. What is `async` and `await`?

### Answer:
- **async**: Declares a function that returns a Promise
- **await**: Pauses execution until Promise resolves
- Makes async code look and behave like **synchronous** code
- `await` can only be used inside **async functions** (or top-level modules)
- **Error handling** with try-catch instead of .catch()
- Introduced in **ES2017 (ES8)**

### Theoretical Keywords:
**Syntactic sugar**, **Promise wrapper**, **Synchronous-looking async**,  
**Error handling**, **try-catch**, **Top-level await**,  
**ES2017 feature**, **Async function**

### Example:
```javascript
// async function always returns a Promise
async function greet() {
    return "Hello";  // Wrapped in Promise.resolve()
}
greet().then(console.log);  // "Hello"

// await pauses until Promise resolves
async function fetchData() {
    console.log("Fetching...");
    const response = await fetch('/api/data');  // Pauses here
    const data = await response.json();          // Pauses here
    console.log("Data:", data);
    return data;
}

// Error handling with try-catch
async function safeFetch() {
    try {
        const response = await fetch('/api/data');
        if (!response.ok) throw new Error('HTTP error');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error:", error);
        return null;  // Handle gracefully
    } finally {
        console.log("Cleanup");
    }
}

// Sequential vs Parallel execution
async function sequential() {
    const a = await fetch('/api/a');  // Wait
    const b = await fetch('/api/b');  // Then wait
    // Total time: a + b
}

async function parallel() {
    const [a, b] = await Promise.all([
        fetch('/api/a'),
        fetch('/api/b')
    ]);
    // Total time: max(a, b)
}

// Top-level await (ES2022, in modules)
// const data = await fetchData();
```

---

## 48. Difference between `async/await` and promises

### Answer:
- **Syntax**: async/await looks synchronous, Promises use .then() chains
- **Readability**: async/await more readable for sequential operations
- **Error handling**: async/await uses try-catch, Promises use .catch()
- **Debugging**: async/await easier to debug (better stack traces)
- **Underlying**: async/await is **syntactic sugar** over Promises
- **Both**: Work with Promises, can be used together

### Theoretical Keywords:
**Syntactic sugar**, **Readability**, **Error handling**,  
**Sequential code**, **Debugging**, **Stack traces**,  
**try-catch vs catch**, **Code structure**

### Example:
```javascript
// PROMISES approach
function getDataWithPromises(userId) {
    return fetch(`/api/users/${userId}`)
        .then(response => response.json())
        .then(user => fetch(`/api/orders/${user.id}`))
        .then(response => response.json())
        .then(orders => {
            console.log(orders);
            return orders;
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

// ASYNC/AWAIT approach (same functionality)
async function getDataWithAsync(userId) {
    try {
        const userResponse = await fetch(`/api/users/${userId}`);
        const user = await userResponse.json();
        
        const ordersResponse = await fetch(`/api/orders/${user.id}`);
        const orders = await ordersResponse.json();
        
        console.log(orders);
        return orders;
    } catch (error) {
        console.error("Error:", error);
    }
}

// Async/await is easier to read and debug
// But both use Promises underneath

// Combining both approaches
async function hybrid() {
    // await with Promise methods
    const results = await Promise.all([
        fetch('/api/a').then(r => r.json()),
        fetch('/api/b').then(r => r.json())
    ]);
    return results;
}

// When Promises might be preferred:
// 1. Simple one-liner transformations
const getData = () => fetch('/api').then(r => r.json());

// 2. Parallel execution is clearer
Promise.all([p1, p2, p3]).then(([a, b, c]) => {});

// 3. Race conditions
Promise.race([timeout, fetch]).then(result => {});
```

---
