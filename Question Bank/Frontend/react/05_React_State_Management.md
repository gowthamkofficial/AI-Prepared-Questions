# React State Management - Interview Questions

## Q1: What are the common state management solutions?
**Answer:**

**Options:**
1. **useState/useContext** - Built-in, good for small apps
2. **Redux** - Predictable, centralized, steeper learning curve
3. **Zustand** - Lightweight, minimal boilerplate
4. **Recoil** - Experimental, atom-based
5. **MobX** - Reactive, automatic tracking
6. **Jotai** - Primitive atoms, like Recoil
7. **TanStack Query (React Query)** - Server state management

## Q2: How do you use Context API for state management?
**Answer:**
```jsx
const AppContext = createContext();

function AppProvider({ children }) {
  const [user, setUser] = useState(null);
  const [theme, setTheme] = useState('light');

  const value = {
    user,
    setUser,
    theme,
    setTheme,
    toggleTheme: () => setTheme(t => t === 'light' ? 'dark' : 'light')
  };

  return (
    <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
  );
}

function useAppContext() {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useAppContext must be used within AppProvider');
  }
  return context;
}

// Usage
function App() {
  return (
    <AppProvider>
      <Header />
      <Main />
    </AppProvider>
  );
}

function Header() {
  const { user, setUser } = useAppContext();
  return <div>{user?.name}</div>;
}
```

## Q3: Explain Redux architecture
**Answer:**
Redux has three main concepts:

**1. Store** - Holds the entire application state
```jsx
const store = createStore(rootReducer);
```

**2. Reducers** - Pure functions that update state
```jsx
function userReducer(state = initialState, action) {
  switch(action.type) {
    case 'SET_USER':
      return { ...state, user: action.payload };
    case 'LOGOUT':
      return { ...state, user: null };
    default:
      return state;
  }
}
```

**3. Actions** - Objects describing what happened
```jsx
const setUserAction = (user) => ({
  type: 'SET_USER',
  payload: user
});
```

**Usage:**
```jsx
function App() {
  return (
    <Provider store={store}>
      <Main />
    </Provider>
  );
}

function Main() {
  const dispatch = useDispatch();
  const user = useSelector(state => state.user);

  return (
    <div>
      <p>{user?.name}</p>
      <button onClick={() => dispatch(setUserAction({ name: 'John' }))}>
        Set User
      </button>
    </div>
  );
}
```

## Q4: What is Redux Thunk?
**Answer:**
Redux Thunk is middleware for handling async actions in Redux.

```jsx
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

// Create async thunk
export const fetchUser = createAsyncThunk(
  'user/fetchUser',
  async (userId) => {
    const response = await fetch(`/api/users/${userId}`);
    return response.json();
  }
);

// Create slice
const userSlice = createSlice({
  name: 'user',
  initialState: { data: null, loading: false, error: null },
  extraReducers: (builder) => {
    builder
      .addCase(fetchUser.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchUser.fulfilled, (state, action) => {
        state.loading = false;
        state.data = action.payload;
      })
      .addCase(fetchUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  }
});

// Usage
function Component() {
  const dispatch = useDispatch();
  const { data, loading, error } = useSelector(state => state.user);

  useEffect(() => {
    dispatch(fetchUser(1));
  }, [dispatch]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  return <div>{data?.name}</div>;
}
```

## Q5: What is Redux Toolkit?
**Answer:**
Redux Toolkit is the official, opinionated way to write Redux code. It simplifies Redux development.

**Key features:**
- `configureStore()` - Easier store setup
- `createSlice()` - Combines actions and reducers
- `createAsyncThunk()` - Handles async operations
- Immer integration for immutable updates

```jsx
import { configureStore, createSlice } from '@reduxjs/toolkit';

const counterSlice = createSlice({
  name: 'counter',
  initialState: { value: 0 },
  reducers: {
    increment: (state) => {
      state.value += 1; // Immer handles immutability
    },
    decrement: (state) => {
      state.value -= 1;
    },
    incrementByAmount: (state, action) => {
      state.value += action.payload;
    }
  }
});

export const { increment, decrement, incrementByAmount } = counterSlice.actions;

const store = configureStore({
  reducer: {
    counter: counterSlice.reducer
  }
});
```

## Q6: What is Zustand?
**Answer:**
Zustand is a lightweight state management library with minimal boilerplate.

```jsx
import create from 'zustand';

const useStore = create((set) => ({
  count: 0,
  user: null,
  
  increment: () => set((state) => ({ count: state.count + 1 })),
  decrement: () => set((state) => ({ count: state.count - 1 })),
  setUser: (user) => set({ user }),
  
  // Async action
  fetchUser: async (id) => {
    const response = await fetch(`/api/users/${id}`);
    const data = await response.json();
    set({ user: data });
  }
}));

// Usage
function Counter() {
  const count = useStore((state) => state.count);
  const increment = useStore((state) => state.increment);

  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={increment}>Increment</button>
    </div>
  );
}
```

## Q7: What is the difference between local state and global state?
**Answer:**

**Local State (useState):**
```jsx
function Component() {
  const [count, setCount] = useState(0); // Only this component
  return <div>{count}</div>;
}
```
- Component-specific
- No prop drilling needed
- Use for UI state (modals, forms, toggles)

**Global State (Redux, Context, Zustand):**
```jsx
// Needed by multiple components
const user = useSelector(state => state.user);
```
- Shared across multiple components
- Prevents prop drilling
- Use for app-wide data (user, theme, auth)

## Q8: What is prop drilling and how do you avoid it?
**Answer:**
Prop drilling occurs when you pass props through many levels of components that don't use them.

**Problem:**
```jsx
function App() {
  const user = { name: 'John' };
  return <Level1 user={user} />;
}

function Level1({ user }) {
  return <Level2 user={user} />;
}

function Level2({ user }) {
  return <Level3 user={user} />;
}

function Level3({ user }) {
  return <div>{user.name}</div>;
}
```

**Solutions:**

1. **Context API:**
```jsx
const UserContext = createContext();

function App() {
  const user = { name: 'John' };
  return (
    <UserContext.Provider value={user}>
      <Level1 />
    </UserContext.Provider>
  );
}

function Level3() {
  const user = useContext(UserContext);
  return <div>{user.name}</div>;
}
```

2. **Component Composition:**
```jsx
function App() {
  return <Layout content={<Level3 />} />;
}
```

3. **State Management Libraries** (Redux, Zustand)

## Q9: How do you handle side effects in Redux?
**Answer:**
Use middleware like Redux Thunk or Redux Saga.

**Redux Thunk:**
```jsx
const fetchUserThunk = (id) => async (dispatch) => {
  dispatch({ type: 'FETCH_START' });
  try {
    const response = await fetch(`/api/users/${id}`);
    const data = await response.json();
    dispatch({ type: 'FETCH_SUCCESS', payload: data });
  } catch (error) {
    dispatch({ type: 'FETCH_ERROR', payload: error });
  }
};
```

**Redux Saga (more advanced):**
```jsx
function* fetchUserSaga() {
  try {
    yield put({ type: 'FETCH_START' });
    const data = yield call(fetchUserAPI);
    yield put({ type: 'FETCH_SUCCESS', payload: data });
  } catch (error) {
    yield put({ type: 'FETCH_ERROR', payload: error });
  }
}
```

## Q10: Should I use Redux or Context API?
**Answer:**

**Use Context API if:**
- Small to medium-sized application
- Simple state requirements
- Want to avoid external dependencies
- State doesn't change frequently

**Use Redux if:**
- Large-scale application
- Complex state logic
- Need dev tools and time-travel debugging
- Team already familiar with Redux
- State changes frequently

**Rule of thumb:** Start with useState/Context, move to Redux if needed.

