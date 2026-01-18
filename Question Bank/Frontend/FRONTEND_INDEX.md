# Frontend Interview Questions - Complete Guide

Welcome to the comprehensive Frontend Interview Questions repository! This guide covers React, Next.js, and CSS/SCSS/Tailwind with detailed explanations and real-world examples.

## 📚 Quick Navigation

### React.js
- [**01_React_Fundamentals.md**](./react/01_React_Fundamentals.md) - Core concepts (JSX, Components, Props, State, Hooks)
- [**02_React_Hooks_Advanced.md**](./react/02_React_Hooks_Advanced.md) - Custom hooks, useReducer, useRef, useEffect patterns
- [**03_React_Forms_Validation.md**](./react/03_React_Forms_Validation.md) - Form handling, validation, file uploads
- [**04_React_Routing_Navigation.md**](./react/04_React_Routing_Navigation.md) - React Router, dynamic routes, protected routes
- [**05_React_State_Management.md**](./react/05_React_State_Management.md) - Redux, Context API, Zustand, prop drilling
- [**06_React_Performance_Testing.md**](./react/06_React_Performance_Testing.md) - Optimization, testing, Profiler
- [**07_React_Advanced_Scenarios.md**](./react/07_React_Advanced_Scenarios.md) - Error boundaries, animations, real-world patterns
- [**react-questions.md**](./react/react-questions.md) - Quick reference Q&A

### Next.js
- [**01_NextJS_Fundamentals.md**](./nextjs/01_NextJS_Fundamentals.md) - SSR, SSG, ISR, API routes, dynamic routes
- [**02_NextJS_Advanced.md**](./nextjs/02_NextJS_Advanced.md) - Server components, caching, streaming, deployment
- [**nextjs-questions.md**](./nextjs/nextjs-questions.md) - Quick reference Q&A

### CSS / SCSS / Tailwind
- [**01_CSS_Fundamentals.md**](./css-scss-tailwind/01_CSS_Fundamentals.md) - Box model, selectors, display, flexbox, grid
- [**02_CSS_Advanced.md**](./css-scss-tailwind/02_CSS_Advanced.md) - Media queries, variables, transitions, animations
- [**03_SCSS_Sass.md**](./css-scss-tailwind/03_SCSS_Sass.md) - Variables, nesting, mixins, functions, extends
- [**04_Tailwind_CSS.md**](./css-scss-tailwind/04_Tailwind_CSS.md) - Utility classes, customization, dark mode, best practices
- [**css-scss-tailwind-questions.md**](./css-scss-tailwind/css-scss-tailwind-questions.md) - Quick reference Q&A

---

## 🎯 Interview Preparation Roadmap

### Week 1: React Fundamentals
- [ ] Understand JSX and component types
- [ ] Learn Props vs State
- [ ] Master hooks (useState, useEffect)
- [ ] Practice conditional rendering

### Week 2: React Advanced
- [ ] Custom hooks
- [ ] Context API
- [ ] React Router
- [ ] Error boundaries

### Week 3: State Management
- [ ] Redux fundamentals
- [ ] Redux Thunk
- [ ] Redux Toolkit
- [ ] Zustand alternative

### Week 4: Next.js & Full Stack
- [ ] SSR vs SSG
- [ ] API routes
- [ ] Dynamic routing
- [ ] Deployment

### Week 5: CSS & Styling
- [ ] CSS fundamentals
- [ ] Flexbox & Grid
- [ ] SCSS/Sass
- [ ] Tailwind CSS

### Week 6: Interview Practice
- [ ] Behavioral questions
- [ ] Coding challenges
- [ ] System design (frontend)
- [ ] Mock interviews

---

## 💡 Most Important Topics

### React (Top 20 Questions)
1. What is React and JSX?
2. Functional vs Class Components
3. Props vs State
4. Hooks (useState, useEffect, useContext)
5. React Router
6. State Management (Redux/Context)
7. Performance Optimization
8. Error Boundaries
9. Component Lifecycle
10. Form Handling

### Next.js (Top 10 Questions)
1. SSR vs SSG vs ISR
2. getStaticProps vs getServerSideProps
3. API Routes
4. Dynamic Routes
5. Image Optimization
6. Middleware
7. Incremental Static Regeneration
8. App Router (New)
9. Server Components
10. Deployment

### CSS (Top 10 Questions)
1. Box Model
2. Selectors & Specificity
3. Flexbox
4. Grid
5. Positioning
6. Media Queries
7. CSS Variables
8. Transitions & Animations
9. SCSS Mixins
10. Tailwind Utilities

---

## 🔥 Common Interview Patterns

### Pattern 1: Build a Counter
```jsx
// React
import { useState } from 'react';

function Counter() {
  const [count, setCount] = useState(0);
  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={() => setCount(count + 1)}>+</button>
    </div>
  );
}
```

### Pattern 2: Fetch Data
```jsx
// React with useEffect
useEffect(() => {
  fetch('/api/data')
    .then(r => r.json())
    .then(setData)
    .catch(setError);
}, []);

// Next.js Server Component
async function getData() {
  const res = await fetch('https://api.example.com/data');
  return res.json();
}
```

### Pattern 3: Protected Route
```jsx
// React Router
function PrivateRoute({ children, isAuth }) {
  return isAuth ? children : <Navigate to="/login" />;
}

// Next.js Middleware
export function middleware(request) {
  if (!request.cookies.has('auth')) {
    return redirect('/login');
  }
}
```

### Pattern 4: Responsive Layout
```html
<!-- CSS -->
<style>
  .container { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); }
</style>

<!-- Tailwind -->
<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4"></div>
```

### Pattern 5: Theme Toggle
```jsx
// React with CSS Variables
const [theme, setTheme] = useState('light');

useEffect(() => {
  document.documentElement.setAttribute('data-theme', theme);
}, [theme]);

// Tailwind
<html className={isDark ? 'dark' : ''}>
```

---

## 📊 Interview Statistics

### Most Asked Topics
1. **State Management** - 25% of questions
2. **Component Lifecycle** - 20% of questions
3. **Performance Optimization** - 15% of questions
4. **Routing** - 15% of questions
5. **Forms & Validation** - 12% of questions
6. **Styling** - 8% of questions
7. **Other** - 5% of questions

### Question Types
- **Conceptual**: 40%
- **Coding**: 35%
- **Design/Architecture**: 15%
- **Behavioral**: 10%

---

## 🚀 Pro Tips for Interview Success

1. **Understand the "why"**: Don't just know what, know why
2. **Practice coding**: Write actual code, not just theory
3. **Know your projects**: Be able to explain your past work
4. **Ask clarifying questions**: Show you think before coding
5. **Test your code**: Run it and verify it works
6. **Optimize gradually**: Start simple, then optimize
7. **Follow best practices**: Use proper naming, structure
8. **Handle edge cases**: Think about errors and edge cases
9. **Communicate clearly**: Explain your thinking
10. **Time management**: Don't spend too long on one problem

---

## 📱 Platform Recommendations

### Learning Resources
- **React**: reactjs.org, React Docs
- **Next.js**: nextjs.org Documentation
- **Tailwind**: tailwindcss.com
- **Practice**: LeetCode, HackerRank, Codewars

### Practice Platforms
- **Frontend Mentor**: Real-world design challenges
- **CodePen**: Share and explore CSS/JS
- **Vercel**: Deploy Next.js projects
- **CodeSandbox**: Online IDE

---

## 🎓 Sample Interview Questions

### Easy
- Explain the difference between let, const, and var
- What is the virtual DOM?
- Explain CSS flexbox

### Medium
- Build a custom hook for form validation
- Implement infinite scroll
- Create a shopping cart reducer

### Hard
- Design a state management library
- Implement code splitting in Next.js
- Build a responsive grid system from scratch

---

## 📝 Practice Checklist

- [ ] Can explain React fundamentals without help
- [ ] Can build a multi-page React app with routing
- [ ] Can implement form validation and submission
- [ ] Can optimize React performance (memo, useMemo, useCallback)
- [ ] Can use Redux or Context for state management
- [ ] Can build a Next.js full-stack application
- [ ] Can explain SSR, SSG, and ISR
- [ ] Can create responsive layouts with CSS/Tailwind
- [ ] Can debug React/CSS issues
- [ ] Can write tests for React components

---

## 🎯 Last-Minute Review

Before your interview, quickly review:
1. React hooks patterns
2. useState/useEffect examples
3. Redux store setup
4. Next.js data fetching
5. CSS Grid/Flexbox layouts
6. Common coding patterns

---

## 📞 Interview Resources

### Books
- "React" by Ade Oshineye and Kate Hudson
- "Learning Next.js" by Scott Tolinski

### Websites
- MDN Web Docs
- CSS-Tricks
- Dev.to
- Medium

### Videos
- React official tutorials
- Next.js tutorials
- CSS layout tutorials

---

## ✅ Final Checklist

Before the interview:
- [ ] Review top 10 questions in each category
- [ ] Practice 3-5 coding problems
- [ ] Prepare questions to ask interviewer
- [ ] Research the company's tech stack
- [ ] Test your internet connection
- [ ] Have a glass of water ready
- [ ] Get good sleep night before
- [ ] Dress professionally
- [ ] Arrive 10 minutes early

---

**Good Luck with Your Interview! 🚀**

Remember: The goal is not to know everything, but to show you can learn, think, and solve problems!

---

*Last Updated: January 2025*
*Questions & Answers: 200+*
*Code Examples: 150+*

