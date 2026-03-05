# React Hooks - Advanced Interview Questions

## Q1: Explain Custom Hooks
**Answer:**
Custom hooks are JavaScript functions that use React hooks. They allow you to extract component logic into reusable functions.

```jsx
// Custom Hook
function useWindowSize() {
  const [size, setSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight
  });

  useEffect(() => {
    const handleResize = () => {
      setSize({
        width: window.innerWidth,
        height: window.innerHeight
      });
    };

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return size;
}

// Usage
function MyComponent() {
  const { width, height } = useWindowSize();
  return <div>Size: {width}x{height}</div>;
}
```

**Rules of Hooks:**
1. Only call hooks at the top level
2. Only call hooks from React functions
3. Use ESLint plugin to enforce

## Q2: What is useReducer?
**Answer:**
`useReducer` is for managing complex state logic with multiple sub-values.

```jsx
const initialState = { count: 0 };

function reducer(state, action) {
  switch(action.type) {
    case 'INCREMENT':
      return { count: state.count + 1 };
    case 'DECREMENT':
      return { count: state.count - 1 };
    case 'RESET':
      return { count: 0 };
    default:
      return state;
  }
}

function Counter() {
  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <div>
      <p>Count: {state.count}</p>
      <button onClick={() => dispatch({ type: 'INCREMENT' })}>+</button>
      <button onClick={() => dispatch({ type: 'DECREMENT' })}>-</button>
      <button onClick={() => dispatch({ type: 'RESET' })}>Reset</button>
    </div>
  );
}
```

## Q3: What is useRef?
**Answer:**
`useRef` returns a mutable ref object that persists across renders.

```jsx
function TextInput() {
  const inputRef = useRef(null);

  const focusInput = () => {
    inputRef.current.focus();
  };

  const clearInput = () => {
    inputRef.current.value = '';
  };

  return (
    <>
      <input ref={inputRef} />
      <button onClick={focusInput}>Focus</button>
      <button onClick={clearInput}>Clear</button>
    </>
  );
}
```

**Use cases:**
- Managing focus, text selection, or media playback
- Triggering imperative animations
- Integrating with third-party DOM libraries

## Q4: What is useLayoutEffect?
**Answer:**
`useLayoutEffect` is similar to `useEffect` but fires synchronously after all DOM mutations.

```jsx
// useEffect - fires after render, non-blocking
useEffect(() => {
  // Runs after browser has painted
}, []);

// useLayoutEffect - fires before browser paints
useLayoutEffect(() => {
  // Runs before browser paints
  // Use for DOM measurements or positioning
}, []);
```

**Difference:**
- **useEffect**: Asynchronous, doesn't block paint
- **useLayoutEffect**: Synchronous, blocks paint
- **Performance**: useEffect is usually preferred

## Q5: What is useImperativeHandle?
**Answer:**
`useImperativeHandle` customizes the instance value that is exposed to parent components when using `ref`.

```jsx
const Child = forwardRef((props, ref) => {
  const inputRef = useRef(null);

  useImperativeHandle(ref, () => ({
    focus: () => inputRef.current.focus(),
    clear: () => inputRef.current.value = '',
    getValue: () => inputRef.current.value
  }));

  return <input ref={inputRef} />;
});

function Parent() {
  const childRef = useRef(null);

  return (
    <>
      <Child ref={childRef} />
      <button onClick={() => childRef.current.focus()}>Focus</button>
    </>
  );
}
```

## Q6: What is useTransition?
**Answer:**
`useTransition` marks updates as transitions, allowing non-urgent updates to be interrupted.

```jsx
function TabContainer() {
  const [tab, setTab] = useState('home');
  const [isPending, startTransition] = useTransition();

  const handleTabClick = (nextTab) => {
    startTransition(() => {
      setTab(nextTab);
    });
  };

  return (
    <>
      <button 
        onClick={() => handleTabClick('home')}
        disabled={isPending}
      >
        Home
      </button>
      <button onClick={() => handleTabClick('profile')}>Profile</button>
      {isPending && <Spinner />}
    </>
  );
}
```

## Q7: What is useDeferredValue?
**Answer:**
`useDeferredValue` defers updating a value until more urgent updates are done.

```jsx
function SearchResults() {
  const [input, setInput] = useState('');
  const deferredInput = useDeferredValue(input);

  return (
    <>
      <input 
        value={input}
        onChange={(e) => setInput(e.target.value)}
      />
      <SearchList query={deferredInput} />
    </>
  );
}
```

**Use cases:**
- Search inputs with large result lists
- Form inputs with dependent calculations
- Filtering large datasets

## Q8: What is useContext Hook?
**Answer:**
`useContext` lets you subscribe to React context without nesting.

```jsx
const UserContext = createContext(null);

function App() {
  const [user, setUser] = useState(null);

  return (
    <UserContext.Provider value={{ user, setUser }}>
      <Profile />
    </UserContext.Provider>
  );
}

function Profile() {
  const { user, setUser } = useContext(UserContext);
  return <div>{user?.name}</div>;
}
```

## Q9: How do you handle async operations in useEffect?
**Answer:**
You cannot make `useEffect` async directly, but you can create an async function inside it.

```jsx
// ❌ Incorrect - don't make useEffect itself async
useEffect(async () => {
  // ...
}, []);

// ✅ Correct - create async function inside
useEffect(() => {
  async function fetchData() {
    const response = await fetch('/api/data');
    const data = await response.json();
    setData(data);
  }

  fetchData();
}, []);

// ✅ With error handling and cleanup
useEffect(() => {
  let isMounted = true;

  async function fetchData() {
    try {
      const response = await fetch('/api/data');
      const data = await response.json();
      if (isMounted) {
        setData(data);
      }
    } catch (error) {
      if (isMounted) {
        setError(error);
      }
    }
  }

  fetchData();

  return () => {
    isMounted = false;
  };
}, []);
```

## Q10: What is the AbortController pattern in useEffect?
**Answer:**
Use `AbortController` to cancel fetch requests when component unmounts.

```jsx
useEffect(() => {
  const controller = new AbortController();

  async function fetchData() {
    try {
      const response = await fetch('/api/data', {
        signal: controller.signal
      });
      const data = await response.json();
      setData(data);
    } catch (error) {
      if (error.name !== 'AbortError') {
        setError(error);
      }
    }
  }

  fetchData();

  return () => controller.abort();
}, []);
```

## Q11: Custom Hook - useAsync
**Answer:**
A reusable hook for handling async operations.

```jsx
function useAsync(asyncFunction, immediate = true) {
  const [status, setStatus] = useState('idle');
  const [value, setValue] = useState(null);
  const [error, setError] = useState(null);

  const execute = useCallback(async () => {
    setStatus('pending');
    setValue(null);
    setError(null);

    try {
      const response = await asyncFunction();
      setValue(response);
      setStatus('success');
    } catch (error) {
      setError(error);
      setStatus('error');
    }
  }, [asyncFunction]);

  useEffect(() => {
    if (immediate) {
      execute();
    }
  }, [execute, immediate]);

  return { execute, status, value, error };
}

// Usage
function Component() {
  const { status, value, error } = useAsync(
    () => fetch('/api/data').then(r => r.json())
  );

  if (status === 'pending') return <div>Loading...</div>;
  if (status === 'error') return <div>Error: {error.message}</div>;
  return <div>{JSON.stringify(value)}</div>;
}
```

## Q12: Custom Hook - useLocalStorage
**Answer:**
A hook for syncing state with localStorage.

```jsx
function useLocalStorage(key, initialValue) {
  const [storedValue, setStoredValue] = useState(() => {
    try {
      const item = window.localStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch (error) {
      console.error(error);
      return initialValue;
    }
  });

  const setValue = (value) => {
    try {
      const valueToStore = value instanceof Function ? value(storedValue) : value;
      setStoredValue(valueToStore);
      window.localStorage.setItem(key, JSON.stringify(valueToStore));
    } catch (error) {
      console.error(error);
    }
  };

  return [storedValue, setValue];
}

// Usage
function App() {
  const [name, setName] = useLocalStorage('name', 'John');
  return <input value={name} onChange={(e) => setName(e.target.value)} />;
}
```

