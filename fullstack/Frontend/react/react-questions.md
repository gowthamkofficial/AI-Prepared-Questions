# React Interview - Questions Bank

## Common React Interview Questions

### Frequently Asked Questions

**Q1: What is reconciliation?**
Reconciliation is the process React uses to determine which parts of the UI need to be updated. React compares the previous and current virtual DOM to identify what changed.

**Q2: What is the purpose of keys in list?**
Keys help React identify which items have changed, been added, or removed. They help preserve component state and improve performance.

**Q3: When should you use class components vs functional components?**
Use functional components with hooks (modern approach). Class components are only needed for error boundaries or if you specifically need lifecycle methods.

**Q4: What are the lifecycle methods?**
- `componentDidMount` - After mount
- `componentDidUpdate` - After update
- `componentWillUnmount` - Before unmount
- `shouldComponentUpdate` - Performance optimization

**Q5: How do you prevent memory leaks?**
- Clean up subscriptions in useEffect cleanup function
- Cancel pending requests in cleanup
- Remove event listeners
- Clear timers

**Q6: What is the virtual DOM?**
A lightweight JavaScript representation of the real DOM that React uses for diffing and rendering optimization.

**Q7: What are fragments?**
Fragments allow grouping multiple elements without adding an extra DOM node using `<>...</>` or `<React.Fragment>...</React.Fragment>`.

**Q8: What is StrictMode?**
StrictMode is a tool for highlighting potential problems in your application. It runs checks in development mode only.

```jsx
<React.StrictMode>
  <App />
</React.StrictMode>
```

**Q9: How do you handle errors in async operations?**
Use try-catch blocks in async functions or `.catch()` for promises.

**Q10: What is a pure component?**
A component that renders the same output for the same props. Use `React.memo` or `PureComponent`.

---

## Scenario-Based Questions

**Q1: How would you build a multi-step form?**
Use state to track current step, display conditional rendering based on step.

**Q2: How would you implement a real-time search with API calls?**
Use debouncing to limit API calls and store results in state.

**Q3: How would you handle file uploads with progress?**
Use XMLHttpRequest with progress events or libraries like axios.

**Q4: How would you implement drag and drop?**
Use HTML5 drag and drop API or libraries like react-beautiful-dnd.

**Q5: How would you handle offline functionality?**
Use Service Workers and localStorage to cache data.

---

## React vs Other Frameworks

| Feature | React | Vue | Angular |
|---------|-------|-----|---------|
| Learning Curve | Medium | Easy | Steep |
| Bundle Size | ~42KB | ~33KB | ~900KB+ |
| Performance | Excellent | Excellent | Good |
| Type Safety | With TypeScript | Optional | Built-in |
| Community | Largest | Growing | Large |
| Companies | Facebook, Netflix, Uber | Alibaba, GitLab | Google, Microsoft |

