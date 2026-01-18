# ✅ Currying Questions & Answers Added

## Summary

Added comprehensive **currying and partial application** answers to the JavaScript interview question bank.

---

## Files Created/Updated

### 1. **NEW FILE: 12_Currying_and_Partial_Application.md**
**Location:** `Question Bank/Frontend/javascript/12_Currying_and_Partial_Application.md`

**Content:**
- ✅ **What is Currying?** - Complete explanation with examples
- ✅ **Difference between Currying and Partial Application** - Detailed comparison
- ✅ **Use Cases in Real Projects** - Practical scenarios (loggers, validators, API config)
- ✅ **Interview Tips** - Common questions and key points
- ✅ **Code Examples** - 15+ working code snippets
- ✅ **Interview Solutions** - Generic curry() and partial() implementations

### 2. **UPDATED: javascript-questions.md**
**Location:** `Question Bank/Frontend/javascript/javascript-questions.md`

**Changes:**
```markdown
Added to "Closures and `this` Keyword" section:
54. What is currying?
55. Difference between currying and partial application
```

---

## Topics Covered in New File

### 📚 Main Topics

1. **Currying Fundamentals**
   - Definition and concept
   - Theoretical keywords
   - Multiple implementation approaches
   - Real-world API example

2. **Partial Application**
   - How it differs from currying
   - When to use each approach
   - Practical examples with API requests

3. **Use Cases**
   - Logger creation with context
   - Email validator factory
   - Database query builder
   - Discount calculator
   - Function composition pipeline

4. **Interview Preparation**
   - 5 Common interview questions
   - 5 Key points to mention
   - 5 Common mistakes to avoid
   - 2 Practical interview code challenges

---

## Code Examples Included

### ✅ Basic Currying
```javascript
const curriedAdd = a => b => c => a + b + c;
console.log(curriedAdd(1)(2)(3));  // 6
```

### ✅ Generic Curry Function
```javascript
function curry(fn) {
    const arity = fn.length;
    return function $curry(...args) {
        if (args.length >= arity) {
            return fn.apply(null, args);
        }
        return (...nextArgs) => $curry(...args, ...nextArgs);
    };
}
```

### ✅ Real-World Examples
- API request builder (baseUrl → endpoint → params)
- Logger with context (level → module → message)
- Database query builder (table → condition → select)
- Discount calculator (rate → quantity → price)

### ✅ Function Composition
- Pipe operator for chaining transformations
- Data transformation pipelines
- Complex operation building

---

## Interview Value

This addition provides:
- 📖 **Complete explanation** of currying concept
- 💡 **Real-world use cases** developers actually encounter
- 🔧 **Practical code examples** to study and learn from
- 🎯 **Interview preparation** with common questions
- ✅ **Best practices** and common mistakes to avoid

---

## How to Use

### For Self-Study:
1. Read the explanation in `12_Currying_and_Partial_Application.md`
2. Study the code examples
3. Try implementing your own curry function
4. Practice with the use case examples

### For Interview Prep:
1. Review "Interview Tips" section
2. Understand the difference from partial application
3. Be able to explain real-world use cases
4. Practice writing generic curry() function
5. Know when and why to use currying

---

## Related Topics

These currying answers complement:
- ✅ **Closures** (Question 49) - Currying relies on closures
- ✅ **Higher-order functions** (Callback functions, Question 22)
- ✅ **Arrow functions** (Questions 20-21) - Used in currying examples
- ✅ **Functional programming** concepts

---

## Quick Reference

### Currying Key Points:
- Transforms `f(a, b, c)` → `f(a)(b)(c)`
- Uses **closures** to preserve arguments
- Enables **function composition** and **reusability**
- Returns **unary functions** (one argument each)

### Partial Application Key Points:
- Transforms `f(a, b, c)` → `f(a)(b, c)` or variations
- **Flexible** argument count
- Also uses **closures**
- Returns **multi-argument functions**

---

## Statistics

| Item | Count |
|------|-------|
| Code Examples | 15+ |
| Use Case Scenarios | 5+ |
| Interview Questions | 6 |
| Theoretical Keywords | 30+ |
| Interview Tips | 15+ |
| Total Lines of Content | 300+ |

---

**Status:** ✅ **COMPLETE AND READY FOR INTERVIEW PREP**

You now have comprehensive currying answers for your JavaScript interview questions!
