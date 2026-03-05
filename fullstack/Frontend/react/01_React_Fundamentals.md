# React Fundamentals - Interview Questions

## Basic Concepts

### Q1: What is React and why is it used?
**Answer:**
React is a JavaScript library for building user interfaces using components. It's used because:
- **Component-based**: Reusable UI components
- **Virtual DOM**: Efficient updates and rendering
- **Unidirectional data flow**: Easier to debug and maintain
- **Large ecosystem**: Rich set of libraries and tools
- **SEO-friendly**: Can be rendered on server-side
- **Performance**: Fast and optimized rendering

### Q2: What are the key differences between React, Vue, and Angular?
**Answer:**
| Feature | React | Vue | Angular |
|---------|-------|-----|---------|
| **Type** | Library | Framework | Framework |
| **Learning Curve** | Medium | Easy | Steep |
| **Performance** | Excellent | Excellent | Good |
| **Size** | 42KB | 33KB | 900KB+ |
| **Bundle Size** | Smaller | Smaller | Larger |
| **Flexibility** | Very High | High | Low |
| **Language** | JSX | Templates | TypeScript |
| **Community** | Largest | Growing | Large |

### Q3: What is JSX?
**Answer:**
JSX stands for JavaScript XML. It allows you to write HTML-like syntax in JavaScript.

```jsx
// JSX
const element = <h1>Hello, {name}!</h1>;

// Compiled to:
const element = React.createElement('h1', null, `Hello, ${name}!`);
```

**Benefits:**
- More readable and intuitive
- Familiar HTML syntax
- Type-safe (when using TypeScript)
- Better IDE support

### Q4: Explain the difference between functional and class components
**Answer:**

**Functional Components:**
```jsx
const Welcome = (props) => {
  return <h1>Hello, {props.name}</h1>;
};
```

**Class Components:**
```jsx
class Welcome extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}
```

| Aspect | Functional | Class |
|--------|-----------|-------|
| **Syntax** | Simple function | ES6 class |
| **State** | Hooks (useState) | this.state |
| **Lifecycle** | Hooks (useEffect) | Lifecycle methods |
| **Binding** | Not needed | Need binding |
| **Performance** | Slightly faster | Slightly slower |
| **Preferred** | Modern approach | Legacy approach |

### Q5: What is the Virtual DOM?
**Answer:**
The Virtual DOM is a lightweight JavaScript representation of the real DOM.

**How it works:**
1. React creates Virtual DOM when rendering components
2. When state changes, React creates a new Virtual DOM
3. React compares (diffs) old and new Virtual DOM
4. React updates only changed elements in real DOM (reconciliation)
5. Batch updates for better performance

**Advantages:**
- Better performance (batch updates)
- Abstracts away DOM complexity
- Enables cross-platform rendering

### Q6: What are props and state?
**Answer:**

**Props (Properties):**
- Read-only data passed from parent to child
- Cannot be modified by child component
- Used for configuration and data flow down

```jsx
function Child({ name, age }) {
  return <p>{name}, {age}</p>;
}

<Child name="John" age={30} />
```

**State:**
- Mutable data managed within the component
- Can be changed using setState or setters
- Triggers re-render when changed

```jsx
const [count, setCount] = useState(0);

<button onClick={() => setCount(count + 1)}>
  Count: {count}
</button>
```

### Q7: What is the difference between controlled and uncontrolled components?
**Answer:**

**Controlled Components:**
- React state is the "single source of truth"
- Value is controlled by React component

```jsx
const [value, setValue] = useState('');
<input 
  value={value} 
  onChange={(e) => setValue(e.target.value)} 
/>
```

**Uncontrolled Components:**
- DOM is the source of truth
- Use refs to access DOM values

```jsx
const inputRef = useRef(null);
<input ref={inputRef} />
<button onClick={() => console.log(inputRef.current.value)}>
  Get Value
</button>
```

**When to use:**
- **Controlled**: Form validation, dynamic updates needed
- **Uncontrolled**: Simple forms, file inputs, integration with non-React code

### Q8: What are keys in React lists?
**Answer:**
Keys help React identify which items have changed, been added, or been removed.

**Example:**
```jsx
const items = ['Apple', 'Banana', 'Cherry'];
{items.map((item, index) => (
  <li key={item}>{item}</li>  // Use unique identifier
))}
```

**Why use keys:**
- Helps maintain component state
- Improves performance in lists
- Prevents bugs in dynamic lists
- Don't use index as key if list can be reordered

### Q9: What is lifting state up?
**Answer:**
Moving state to the nearest common ancestor of components that need to share state.

```jsx
// Before - duplicate state
const [count, setCount] = useState(0);  // In ComponentA
const [count, setCount] = useState(0);  // In ComponentB

// After - shared state
function Parent() {
  const [count, setCount] = useState(0);
  return (
    <>
      <ComponentA count={count} setCount={setCount} />
      <ComponentB count={count} setCount={setCount} />
    </>
  );
}
```

**Benefits:**
- Single source of truth
- Easier to debug
- Data flows consistently

### Q10: What is React.Fragment?
**Answer:**
Fragment allows you to group multiple elements without adding an extra DOM node.

```jsx
// Without Fragment - adds extra div
<div>
  <Component1 />
  <Component2 />
</div>

// With Fragment - no extra div
<React.Fragment>
  <Component1 />
  <Component2 />
</React.Fragment>

// Shorthand syntax
<>
  <Component1 />
  <Component2 />
</>
```

**Use cases:**
- Returning multiple elements from render
- Avoiding wrapper divs
- Maintaining DOM structure

---

## Intermediate Concepts

### Q11: What are React Hooks?
**Answer:**
Hooks are functions that let you "hook into" React features. They allow you to use state and other React features in functional components.

**Common Hooks:**
- `useState`: Add state to functional components
- `useEffect`: Handle side effects
- `useContext`: Access context
- `useReducer`: Complex state logic
- `useCallback`: Memoize functions
- `useMemo`: Memoize values
- `useRef`: Access DOM directly
- `useCustomHook`: Create custom hooks

### Q12: Explain the useEffect Hook
**Answer:**
`useEffect` runs side effects after rendering the component.

```jsx
// No dependency - runs after every render
useEffect(() => {
  console.log('Rendered!');
});

// Empty dependency - runs once after mount
useEffect(() => {
  console.log('Mounted!');
}, []);

// Specific dependency - runs when dependency changes
useEffect(() => {
  console.log('Count changed:', count);
}, [count]);

// Cleanup function - runs before unmount
useEffect(() => {
  const timer = setInterval(() => {}, 1000);
  return () => clearInterval(timer);
}, []);
```

### Q13: What is the difference between useCallback and useMemo?
**Answer:**

**useCallback:**
- Memoizes a function
- Prevents unnecessary function recreations
- Useful for passing callbacks to optimized child components

```jsx
const handleClick = useCallback(() => {
  console.log('Clicked');
}, [dependency]);
```

**useMemo:**
- Memoizes a value
- Prevents expensive calculations
- Useful for expensive computations

```jsx
const expensiveValue = useMemo(() => {
  return complexCalculation(data);
}, [data]);
```

### Q14: What is React.memo?
**Answer:**
`React.memo` is a higher-order component (HOC) that memoizes a component. It prevents re-renders if props haven't changed.

```jsx
const MyComponent = React.memo(function MyComponent(props) {
  return <div>{props.value}</div>;
});

// With custom comparison
const MyComponent = React.memo(
  function MyComponent(props) {
    return <div>{props.value}</div>;
  },
  (prevProps, nextProps) => {
    return prevProps.value === nextProps.value;
  }
);
```

### Q15: What is the Context API?
**Answer:**
Context API provides a way to pass data through the component tree without having to pass props down manually at every level.

```jsx
const ThemeContext = React.createContext('light');

function App() {
  return (
    <ThemeContext.Provider value="dark">
      <Child />
    </ThemeContext.Provider>
  );
}

function Child() {
  const theme = useContext(ThemeContext);
  return <div>Theme: {theme}</div>;
}
```

**Use cases:**
- Theme switching
- Authentication
- Language preferences
- Global state (for small apps)

---

## Advanced Concepts

### Q16: What are Higher-Order Components (HOC)?
**Answer:**
An HOC is a function that takes a component and returns an enhanced component.

```jsx
function withTheme(Component) {
  return function ThemedComponent(props) {
    const [theme, setTheme] = useState('light');
    return <Component theme={theme} {...props} />;
  };
}

const ThemedButton = withTheme(Button);
```

**Patterns:**
- Props proxy
- Inheritance inversion
- Static method copying

### Q17: What is render props pattern?
**Answer:**
A technique for sharing code between React components using a prop that is a function.

```jsx
function RenderPropsComponent({ render }) {
  const [count, setCount] = useState(0);
  return render(count, setCount);
}

<RenderPropsComponent 
  render={(count, setCount) => (
    <button onClick={() => setCount(count + 1)}>
      Count: {count}
    </button>
  )}
/>
```

### Q18: What is suspense in React?
**Answer:**
Suspense lets components "wait" for something before rendering.

```jsx
const LazyComponent = React.lazy(() => import('./LazyComponent'));

<Suspense fallback={<Loading />}>
  <LazyComponent />
</Suspense>
```

**Use cases:**
- Code splitting with lazy loading
- Data fetching
- Resource loading

---

## Performance Optimization

### Q19: How do you optimize React applications?
**Answer:**
Key optimization techniques:
1. **Code Splitting**: Use `React.lazy` and `Suspense`
2. **Memoization**: Use `React.memo`, `useCallback`, `useMemo`
3. **Key Optimization**: Use proper keys in lists
4. **Component Splitting**: Smaller, focused components
5. **Lazy Loading**: Load components when needed
6. **Image Optimization**: Use appropriate formats and sizes
7. **Bundle Analysis**: Analyze and reduce bundle size
8. **Virtual Scrolling**: For long lists
9. **Pagination**: Load data in chunks
10. **Service Worker**: Caching and offline support

### Q20: What is React Profiler?
**Answer:**
React Profiler is a tool for measuring component performance and identifying bottlenecks.

**How to use:**
```jsx
import { Profiler } from 'react';

function onRenderCallback(
  id, phase, actualDuration, baseDuration, startTime, commitTime
) {
  console.log(`${id} (${phase}) took ${actualDuration}ms`);
}

<Profiler id="App" onRender={onRenderCallback}>
  <App />
</Profiler>
```

**Metrics:**
- **Render duration**: Time to render component
- **Commit duration**: Time to update DOM
- **Phase**: "mount" or "update"

