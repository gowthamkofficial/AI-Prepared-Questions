# React Router & Navigation - Interview Questions

## Q1: What is React Router?
**Answer:**
React Router is a library for handling navigation and routing in single-page applications (SPAs).

```jsx
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/contact">Contact</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
    </BrowserRouter>
  );
}
```

## Q2: What is the difference between Link and NavLink?
**Answer:**

**Link:**
- Simple navigation component
- Doesn't add active styles

```jsx
<Link to="/about">About</Link>
```

**NavLink:**
- Adds active class when route matches
- Better for navigation highlighting

```jsx
<NavLink to="/about" className={({ isActive }) => isActive ? 'active' : ''}>
  About
</NavLink>

// Or with style
<NavLink 
  to="/about" 
  style={({ isActive }) => ({ color: isActive ? 'red' : 'black' })}
>
  About
</NavLink>
```

## Q3: How do you handle dynamic routes?
**Answer:**
```jsx
function App() {
  return (
    <Routes>
      <Route path="/user/:id" element={<User />} />
      <Route path="/post/:postId/comment/:commentId" element={<Comment />} />
    </Routes>
  );
}

function User() {
  const { id } = useParams();
  return <div>User ID: {id}</div>;
}

function Comment() {
  const { postId, commentId } = useParams();
  return <div>Post: {postId}, Comment: {commentId}</div>;
}
```

## Q4: How do you use useNavigate hook?
**Answer:**
```jsx
import { useNavigate } from 'react-router-dom';

function LoginForm() {
  const navigate = useNavigate();

  const handleLogin = async (credentials) => {
    const response = await fetch('/api/login', {
      method: 'POST',
      body: JSON.stringify(credentials)
    });

    if (response.ok) {
      navigate('/dashboard');
      // Or with replace
      navigate('/dashboard', { replace: true });
      // Or go back
      navigate(-1);
    }
  };

  return (
    <form onSubmit={(e) => {
      e.preventDefault();
      handleLogin({ email: 'test@example.com', password: 'password' });
    }}>
      <button>Login</button>
    </form>
  );
}
```

## Q5: How do you pass state through navigation?
**Answer:**
```jsx
// Send state
function Home() {
  const navigate = useNavigate();

  const goToAbout = () => {
    navigate('/about', { state: { name: 'John', age: 30 } });
  };

  return <button onClick={goToAbout}>Go to About</button>;
}

// Receive state
function About() {
  const location = useLocation();
  const state = location.state;

  return <div>Data: {state?.name}, {state?.age}</div>;
}
```

## Q6: How do you create nested routes?
**Answer:**
```jsx
function App() {
  return (
    <Routes>
      <Route path="/dashboard" element={<Dashboard />}>
        <Route path="profile" element={<Profile />} />
        <Route path="settings" element={<Settings />} />
      </Route>
    </Routes>
  );
}

function Dashboard() {
  return (
    <div>
      <h1>Dashboard</h1>
      <nav>
        <Link to="profile">Profile</Link>
        <Link to="settings">Settings</Link>
      </nav>
      <Outlet /> {/* Renders nested route components here */}
    </div>
  );
}
```

## Q7: How do you implement route guards/protected routes?
**Answer:**
```jsx
function PrivateRoute({ children, isAuthenticated }) {
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        path="/dashboard"
        element={
          <PrivateRoute isAuthenticated={isAuthenticated}>
            <Dashboard />
          </PrivateRoute>
        }
      />
    </Routes>
  );
}
```

## Q8: How do you handle query parameters?
**Answer:**
```jsx
import { useSearchParams } from 'react-router-dom';

function SearchResults() {
  const [searchParams, setSearchParams] = useSearchParams();
  
  const page = searchParams.get('page') || '1';
  const filter = searchParams.get('filter') || 'all';

  const handlePageChange = (newPage) => {
    setSearchParams({ page: newPage, filter });
  };

  return (
    <div>
      <p>Page: {page}, Filter: {filter}</p>
      <button onClick={() => handlePageChange('2')}>Next Page</button>
    </div>
  );
}

// URL: /search?page=1&filter=active
```

## Q9: How do you implement lazy loading routes?
**Answer:**
```jsx
import { lazy, Suspense } from 'react';

const Home = lazy(() => import('./pages/Home'));
const About = lazy(() => import('./pages/About'));
const Dashboard = lazy(() => import('./pages/Dashboard'));

function App() {
  return (
    <Suspense fallback={<Loading />}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Suspense>
  );
}

function Loading() {
  return <div>Loading...</div>;
}
```

## Q10: How do you handle 404 routes?
**Answer:**
```jsx
function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/about" element={<About />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

function NotFound() {
  const navigate = useNavigate();

  return (
    <div>
      <h1>404 - Page Not Found</h1>
      <button onClick={() => navigate('/')}>Go Home</button>
    </div>
  );
}
```

