# React Performance & Testing - Interview Questions

## Q1: What are performance optimization techniques in React?
**Answer:**

**1. Code Splitting:**
```jsx
import { lazy, Suspense } from 'react';

const Dashboard = lazy(() => import('./Dashboard'));

function App() {
  return (
    <Suspense fallback={<Loading />}>
      <Dashboard />
    </Suspense>
  );
}
```

**2. Memoization:**
```jsx
const ExpensiveComponent = React.memo(({ data }) => {
  return <div>{data.name}</div>;
});
```

**3. useCallback & useMemo:**
```jsx
const handleClick = useCallback(() => {
  console.log('clicked');
}, [dependencies]);

const expensiveValue = useMemo(() => {
  return complexCalculation(data);
}, [data]);
```

**4. Bundle Analysis:**
```bash
npm install --save-dev webpack-bundle-analyzer
```

**5. Lazy Loading Images:**
```jsx
<img loading="lazy" src="image.jpg" alt="description" />
```

**6. Virtual Scrolling:**
- Use libraries like `react-window` for long lists

**7. Pagination Instead of Infinite Scroll**

**8. Image Optimization:**
- Use WebP format
- Responsive images with srcset
- Proper sizing

## Q2: How do you test React components?
**Answer:**

**Jest with React Testing Library:**
```jsx
import { render, screen, fireEvent } from '@testing-library/react';
import Counter from './Counter';

describe('Counter Component', () => {
  test('renders counter with initial value', () => {
    render(<Counter initialValue={0} />);
    expect(screen.getByText('Count: 0')).toBeInTheDocument();
  });

  test('increments count when button is clicked', () => {
    render(<Counter initialValue={0} />);
    const button = screen.getByText('Increment');
    fireEvent.click(button);
    expect(screen.getByText('Count: 1')).toBeInTheDocument();
  });
});
```

**Testing with Enzyme (older approach):**
```jsx
import { shallow } from 'enzyme';

describe('Counter', () => {
  it('increments count', () => {
    const wrapper = shallow(<Counter />);
    wrapper.find('button').simulate('click');
    expect(wrapper.find('p').text()).toBe('Count: 1');
  });
});
```

## Q3: What are testing best practices?
**Answer:**

**1. Test user behavior, not implementation:**
```jsx
// ❌ Bad - testing implementation
test('state updates', () => {
  const wrapper = shallow(<Counter />);
  expect(wrapper.state('count')).toBe(1);
});

// ✅ Good - testing behavior
test('shows updated count', () => {
  render(<Counter />);
  fireEvent.click(screen.getByRole('button', { name: /increment/i }));
  expect(screen.getByText('Count: 1')).toBeInTheDocument();
});
```

**2. Use meaningful test descriptions:**
```jsx
// ❌ Unclear
test('works', () => {});

// ✅ Clear
test('displays user name and email after fetching data', () => {});
```

**3. Test accessibility:**
```jsx
test('button is keyboard accessible', () => {
  render(<Button />);
  const button = screen.getByRole('button');
  button.focus();
  expect(button).toHaveFocus();
});
```

**4. Mock external dependencies:**
```jsx
jest.mock('./api', () => ({
  fetchUser: jest.fn(() => Promise.resolve({ name: 'John' }))
}));
```

## Q4: How do you test async operations?
**Answer:**

```jsx
import { render, screen, waitFor } from '@testing-library/react';

test('fetches and displays user data', async () => {
  render(<UserProfile userId={1} />);
  
  // Wait for async operation
  await waitFor(() => {
    expect(screen.getByText('John Doe')).toBeInTheDocument();
  });
});

// Or using findBy
test('displays user data', async () => {
  render(<UserProfile userId={1} />);
  
  const userName = await screen.findByText('John Doe');
  expect(userName).toBeInTheDocument();
});
```

## Q5: How do you test hooks?
**Answer:**

```jsx
import { renderHook, act } from '@testing-library/react';
import { useCounter } from './useCounter';

test('increment works', () => {
  const { result } = renderHook(() => useCounter());
  
  act(() => {
    result.current.increment();
  });
  
  expect(result.current.count).toBe(1);
});

// Testing with initial props
test('initializes with custom value', () => {
  const { result } = renderHook(
    ({ initialValue }) => useCounter(initialValue),
    { initialProps: { initialValue: 10 } }
  );
  
  expect(result.current.count).toBe(10);
});
```

## Q6: How do you use React Profiler?
**Answer:**

```jsx
import { Profiler } from 'react';

function onRenderCallback(
  id,           // the "id" of the Profiler where the update occurred
  phase,        // either "mount" or "update"
  actualDuration,    // time spent rendering
  baseDuration,      // estimated time without memoization
  startTime,    // React assigned time of the update
  commitTime,   // React assigned time of commit
  interactions  // Set of interactions
) {
  console.log(`${id} (${phase}) took ${actualDuration}ms`);
}

function App() {
  return (
    <Profiler id="App" onRender={onRenderCallback}>
      <Header />
      <Main />
      <Footer />
    </Profiler>
  );
}
```

**Using Chrome DevTools:**
1. Open React DevTools
2. Click on Profiler tab
3. Record interactions
4. Analyze performance

## Q7: What are common performance pitfalls?
**Answer:**

**1. Unnecessary re-renders:**
```jsx
// ❌ Bad - all children re-render
function Parent() {
  const [count, setCount] = useState(0);
  return (
    <div>
      <Child1 />
      <Child2 />
      <button onClick={() => setCount(count + 1)}>Increment</button>
    </div>
  );
}

// ✅ Good - use Context or memoization
const CountContext = createContext();
function Parent() {
  const [count, setCount] = useState(0);
  return (
    <CountContext.Provider value={count}>
      <MemoChild1 />
      <MemoChild2 />
      <button onClick={() => setCount(count + 1)}>Increment</button>
    </CountContext.Provider>
  );
}
```

**2. Missing dependencies in useEffect:**
```jsx
// ❌ Bad - infinite loop
useEffect(() => {
  fetchData();
}, []); // Missing dependency

// ✅ Good - correct dependencies
useEffect(() => {
  fetchData(userId);
}, [userId]);
```

**3. Creating new objects in render:**
```jsx
// ❌ Bad - new object every render
const style = { color: 'red' };
<Component style={style} />

// ✅ Good - memoize or move outside
const style = useMemo(() => ({ color: 'red' }), []);
<Component style={style} />
```

