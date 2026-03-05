# Next.js Fundamentals - Interview Questions

## Q1: What is Next.js?
**Answer:**
Next.js is a React framework for building full-stack web applications with built-in support for:
- Server-side rendering (SSR)
- Static site generation (SSG)
- API routes
- Automatic code splitting
- File-based routing
- Image optimization

```jsx
// pages/index.js
export default function Home() {
  return <h1>Welcome to Next.js</h1>;
}
```

## Q2: What is the difference between SSR, SSG, and ISR?
**Answer:**

**Server-Side Rendering (SSR):**
- Page rendered on every request
- Always up-to-date
- Slower response time
- Use with `getServerSideProps`

```jsx
export default function Page({ data }) {
  return <div>{data}</div>;
}

export async function getServerSideProps() {
  const res = await fetch('https://api.example.com/data');
  const data = await res.json();
  return { props: { data } };
}
```

**Static Site Generation (SSG):**
- Page pre-rendered at build time
- Fastest, served from CDN
- Static content
- Use with `getStaticProps`

```jsx
export default function Blog({ posts }) {
  return (
    <ul>
      {posts.map(post => <li key={post.id}>{post.title}</li>)}
    </ul>
  );
}

export async function getStaticProps() {
  const posts = await fetch('https://api.example.com/posts').then(r => r.json());
  return {
    props: { posts },
    revalidate: 3600 // Revalidate every hour
  };
}
```

**Incremental Static Regeneration (ISR):**
- Pre-rendered at build time
- Revalidate in background
- Combines benefits of SSG and SSR

```jsx
export async function getStaticProps() {
  return {
    props: { data: 'initial' },
    revalidate: 60 // Revalidate every 60 seconds
  };
}
```

| Method | Speed | Freshness | Use Case |
|--------|-------|-----------|----------|
| SSG | Fastest | Stale | Blog posts, documentation |
| ISR | Fast | Fresh | E-commerce products |
| SSR | Slow | Fresh | Real-time data |

## Q3: Explain the difference between pages and app directories
**Answer:**

**Pages Directory (Legacy but still used):**
```
pages/
├── index.js          // /
├── about.js          // /about
├── [id].js           // Dynamic routes /1, /2, etc
└── api/
    └── users.js      // /api/users
```

**App Directory (New - App Router):**
```
app/
├── layout.js         // Root layout
├── page.js           // / route
├── about/
│   └── page.js       // /about route
├── [id]/
│   └── page.js       // Dynamic routes
└── api/
    └── users/
        └── route.js  // API routes
```

**Key Differences:**
- App Router uses folders instead of files
- Supports React Server Components
- Better nested layouts
- More flexible routing

## Q4: What are dynamic routes?
**Answer:**

**Single dynamic route:**
```
pages/post/[id].js → /post/1, /post/2, etc

export default function Post({ post }) {
  return <h1>{post.title}</h1>;
}

export async function getStaticProps({ params }) {
  const post = await fetch(`/api/posts/${params.id}`).then(r => r.json());
  return { props: { post } };
}

export async function getStaticPaths() {
  return {
    paths: [
      { params: { id: '1' } },
      { params: { id: '2' } }
    ],
    fallback: 'blocking'
  };
}
```

**Catch-all routes:**
```
pages/post/[...slug].js → /post/2023/01/my-post

export default function Post({ slug }) {
  return <div>{slug.join('/')}</div>;
}
```

**Optional catch-all routes:**
```
pages/post/[[...slug]].js → /post, /post/2023, /post/2023/01
```

## Q5: How do you create API routes?
**Answer:**

```jsx
// pages/api/users.js
export default function handler(req, res) {
  if (req.method === 'GET') {
    res.status(200).json([
      { id: 1, name: 'John' },
      { id: 2, name: 'Jane' }
    ]);
  } else if (req.method === 'POST') {
    const newUser = req.body;
    res.status(201).json(newUser);
  } else {
    res.status(405).end();
  }
}

// Call from client
const response = await fetch('/api/users');
const users = await response.json();
```

**API routes with dynamic parameters:**
```jsx
// pages/api/users/[id].js
export default function handler(req, res) {
  const { id } = req.query;
  res.status(200).json({ id, name: 'User ' + id });
}
```

## Q6: What is Image Optimization?
**Answer:**
Next.js automatically optimizes images for performance.

```jsx
import Image from 'next/image';

export default function MyComponent() {
  return (
    <Image
      src="/images/profile.jpg"
      alt="Profile picture"
      width={200}
      height={200}
      priority={true}  // Load immediately
      quality={75}     // Compression quality
      placeholder="blur" // Blur while loading
    />
  );
}
```

**Benefits:**
- Automatic responsive images
- AVIF and WebP formats
- Lazy loading
- Prevention of Cumulative Layout Shift (CLS)

## Q7: What is Font Optimization?
**Answer:**
```jsx
import { Poppins, Open_Sans } from '@next/font/google';

const poppins = Poppins({
  weight: '400',
  subsets: ['latin']
});

export default function Home() {
  return <div className={poppins.className}>Text</div>;
}
```

**Benefits:**
- Reduces layout shift
- Self-hosted fonts
- No external requests

## Q8: How do you use middleware?
**Answer:**

```jsx
// middleware.js (at root)
import { NextResponse } from 'next/server';

export function middleware(request) {
  const token = request.cookies.get('auth');
  
  if (!token && request.nextUrl.pathname.startsWith('/dashboard')) {
    return NextResponse.redirect(new URL('/login', request.url));
  }
  
  return NextResponse.next();
}

export const config = {
  matcher: ['/dashboard/:path*', '/admin/:path*']
};
```

**Use cases:**
- Authentication checks
- Request logging
- Redirects
- Adding headers

## Q9: How do you enable Static Export?
**Answer:**

```jsx
// next.config.js
const nextConfig = {
  output: 'export',
  // App Router is required for static export
};

module.exports = nextConfig;
```

**Limitations:**
- No API routes
- No SSR or ISR
- No dynamic routes with fallback
- No image optimization (unless using external service)

## Q10: What are Environment Variables?
**Answer:**

```bash
# .env.local
DATABASE_URL=postgresql://...
API_KEY=secret123

# .env.production
API_URL=https://api.example.com
```

**Usage:**
```jsx
// Available in server and browser
const apiUrl = process.env.NEXT_PUBLIC_API_URL;

// Server-side only
const dbUrl = process.env.DATABASE_URL;
```

## Q11: How do you handle redirects and rewrites?
**Answer:**

```jsx
// next.config.js
const nextConfig = {
  async redirects() {
    return [
      {
        source: '/old-page',
        destination: '/new-page',
        permanent: true // 301 redirect
      }
    ];
  },

  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'https://api.example.com/:path*'
      }
    ];
  }
};

module.exports = nextConfig;
```

## Q12: How do you implement authentication?
**Answer:**

```jsx
// pages/api/auth/login.js
import jwt from 'jsonwebtoken';

export default function handler(req, res) {
  if (req.method !== 'POST') {
    return res.status(405).end();
  }

  const { email, password } = req.body;
  
  // Verify credentials
  if (email === 'user@example.com' && password === 'password') {
    const token = jwt.sign({ email }, process.env.JWT_SECRET, {
      expiresIn: '7d'
    });

    res.setHeader('Set-Cookie', `auth=${token}; Path=/; HttpOnly`);
    return res.status(200).json({ message: 'Logged in' });
  }

  res.status(401).json({ message: 'Invalid credentials' });
}

// Middleware to protect routes
function withAuth(handler) {
  return (req, res) => {
    const token = req.cookies.auth;
    
    if (!token) {
      return res.status(401).json({ message: 'Unauthorized' });
    }

    try {
      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      req.user = decoded;
      return handler(req, res);
    } catch (error) {
      return res.status(401).json({ message: 'Invalid token' });
    }
  };
}

// Usage
export default withAuth((req, res) => {
  res.json({ user: req.user });
});
```

