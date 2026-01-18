# React Advanced Topics & Scenario Questions

## Q1: What are error boundaries?
**Answer:**
Error boundaries catch JavaScript errors anywhere in the component tree and display a fallback UI.

```jsx
class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    console.error('Error caught:', error, errorInfo);
    // Log to error reporting service
  }

  render() {
    if (this.state.hasError) {
      return <div>Error: {this.state.error?.message}</div>;
    }

    return this.props.children;
  }
}

// Usage
<ErrorBoundary>
  <App />
</ErrorBoundary>
```

**Limitations:**
- Doesn't catch events
- Doesn't catch async errors
- Doesn't catch server-side rendering errors
- Class components only

## Q2: How do you handle animations in React?
**Answer:**

**Using CSS Transitions:**
```jsx
function Modal({ isOpen, onClose }) {
  return (
    <div className={`modal ${isOpen ? 'open' : 'closed'}`}>
      <button onClick={onClose}>Close</button>
    </div>
  );
}
```

```css
.modal {
  opacity: 0;
  transition: opacity 0.3s ease;
}

.modal.open {
  opacity: 1;
}
```

**Using Framer Motion:**
```jsx
import { motion } from 'framer-motion';

function AnimatedBox() {
  return (
    <motion.div
      initial={{ opacity: 0, y: -10 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -10 }}
      transition={{ duration: 0.3 }}
    >
      Hello
    </motion.div>
  );
}
```

**Using React Transition Group:**
```jsx
import { CSSTransition } from 'react-transition-group';

function App() {
  const [show, setShow] = useState(false);
  const nodeRef = useRef(null);

  return (
    <>
      <button onClick={() => setShow(!show)}>Toggle</button>
      <CSSTransition
        in={show}
        timeout={300}
        classNames="fade"
        unmountOnExit
        nodeRef={nodeRef}
      >
        <div ref={nodeRef}>Content</div>
      </CSSTransition>
    </>
  );
}
```

## Q3: How do you implement infinite scroll?
**Answer:**

**Using Intersection Observer:**
```jsx
function InfiniteScroll({ onLoadMore, hasMore, children }) {
  const observerTarget = useRef(null);

  useEffect(() => {
    const observer = new IntersectionObserver(
      entries => {
        if (entries[0].isIntersecting && hasMore) {
          onLoadMore();
        }
      },
      { threshold: 0.1 }
    );

    if (observerTarget.current) {
      observer.observe(observerTarget.current);
    }

    return () => observer.disconnect();
  }, [hasMore, onLoadMore]);

  return (
    <>
      {children}
      <div ref={observerTarget}>Loading...</div>
    </>
  );
}

// Usage
function App() {
  const [items, setItems] = useState([]);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  const handleLoadMore = useCallback(async () => {
    const response = await fetch(`/api/items?page=${page + 1}`);
    const data = await response.json();
    setItems(prev => [...prev, ...data.items]);
    setPage(prev => prev + 1);
    setHasMore(data.hasMore);
  }, [page]);

  return (
    <InfiniteScroll onLoadMore={handleLoadMore} hasMore={hasMore}>
      {items.map(item => <div key={item.id}>{item.name}</div>)}
    </InfiniteScroll>
  );
}
```

## Q4: How do you implement search with debouncing?
**Answer:**

```jsx
function useDebounce(value, delay) {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    return () => clearTimeout(handler);
  }, [value, delay]);

  return debouncedValue;
}

function SearchUsers() {
  const [search, setSearch] = useState('');
  const [results, setResults] = useState([]);
  const debouncedSearch = useDebounce(search, 500);

  useEffect(() => {
    if (debouncedSearch) {
      fetch(`/api/users?q=${debouncedSearch}`)
        .then(r => r.json())
        .then(data => setResults(data));
    }
  }, [debouncedSearch]);

  return (
    <>
      <input
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        placeholder="Search users..."
      />
      <ul>
        {results.map(user => <li key={user.id}>{user.name}</li>)}
      </ul>
    </>
  );
}
```

## Q5: How do you handle modals/dialogs?
**Answer:**

```jsx
import { createPortal } from 'react-dom';

function Modal({ isOpen, title, children, onClose }) {
  if (!isOpen) return null;

  return createPortal(
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>{title}</h2>
          <button onClick={onClose}>✕</button>
        </div>
        <div className="modal-body">{children}</div>
      </div>
    </div>,
    document.body
  );
}

function App() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <button onClick={() => setIsOpen(true)}>Open Modal</button>
      <Modal
        isOpen={isOpen}
        title="My Modal"
        onClose={() => setIsOpen(false)}
      >
        <p>Modal content here</p>
      </Modal>
    </>
  );
}
```

## Q6: How do you handle keyboard shortcuts?
**Answer:**

```jsx
function useKeyPress(targetKey) {
  const [keyPressed, setKeyPressed] = useState(false);

  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.key === targetKey) {
        setKeyPressed(true);
      }
    };

    const handleKeyUp = (event) => {
      if (event.key === targetKey) {
        setKeyPressed(false);
      }
    };

    window.addEventListener('keydown', handleKeyDown);
    window.addEventListener('keyup', handleKeyUp);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
      window.removeEventListener('keyup', handleKeyUp);
    };
  }, [targetKey]);

  return keyPressed;
}

function Editor() {
  const saved = useKeyPress('s');
  const enterPressed = useKeyPress('Enter');

  useEffect(() => {
    if (saved) {
      console.log('Save document');
    }
  }, [saved]);

  return <textarea placeholder="Edit text..." />;
}
```

## Q7: How do you optimize lists with virtualization?
**Answer:**

```jsx
import { FixedSizeList } from 'react-window';

function VirtualizedList({ items }) {
  const Row = ({ index, style }) => (
    <div style={style}>
      {items[index].name}
    </div>
  );

  return (
    <FixedSizeList
      height={600}
      itemCount={items.length}
      itemSize={35}
      width="100%"
    >
      {Row}
    </FixedSizeList>
  );
}

// Usage with 10,000 items
const items = Array.from({ length: 10000 }, (_, i) => ({
  id: i,
  name: `Item ${i}`
}));

<VirtualizedList items={items} />
```

## Q8: Real-world scenario - User Authentication
**Answer:**

```jsx
// AuthContext.js
const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Check if user is logged in
    fetch('/api/me')
      .then(r => r.json())
      .then(data => setUser(data))
      .catch(() => setUser(null))
      .finally(() => setLoading(false));
  }, []);

  const login = async (email, password) => {
    const response = await fetch('/api/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });

    if (response.ok) {
      const data = await response.json();
      setUser(data);
      return data;
    }
    throw new Error('Login failed');
  };

  const logout = async () => {
    await fetch('/api/logout', { method: 'POST' });
    setUser(null);
  };

  const value = { user, loading, login, logout };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
}

// Protected Route
function ProtectedRoute({ children }) {
  const { user, loading } = useAuth();
  const navigate = useNavigate();

  if (loading) return <div>Loading...</div>;

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  return children;
}
```

## Q9: Real-world scenario - Shopping Cart
**Answer:**

```jsx
function useCart() {
  const [cart, setCart] = useState([]);

  const addItem = (product) => {
    setCart(prev => {
      const existing = prev.find(item => item.id === product.id);
      if (existing) {
        return prev.map(item =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }
      return [...prev, { ...product, quantity: 1 }];
    });
  };

  const removeItem = (productId) => {
    setCart(prev => prev.filter(item => item.id !== productId));
  };

  const updateQuantity = (productId, quantity) => {
    setCart(prev =>
      prev.map(item =>
        item.id === productId
          ? { ...item, quantity: Math.max(0, quantity) }
          : item
      ).filter(item => item.quantity > 0)
    );
  };

  const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

  return { cart, addItem, removeItem, updateQuantity, total };
}
```

