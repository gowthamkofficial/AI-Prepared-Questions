# Next.js Advanced Topics - Interview Questions

## Q1: What are React Server Components?
**Answer:**
React Server Components (RSC) are a new paradigm in Next.js that allows rendering components on the server.

**Server Components:**
```jsx
// app/components/ServerComponent.js
export default async function ServerComponent() {
  const data = await fetch('https://api.example.com/data').then(r => r.json());
  
  return <div>{data}</div>;
}
```

**Client Components:**
```jsx
// app/components/ClientComponent.js
'use client'; // Mark as client component

import { useState } from 'react';

export default function ClientComponent() {
  const [count, setCount] = useState(0);
  
  return <button onClick={() => setCount(count + 1)}>Count: {count}</button>;
}
```

**Benefits:**
- Fetch data directly on server
- Keep sensitive API keys on server
- Reduce JavaScript sent to browser
- Better performance

## Q2: Explain data fetching patterns in Next.js
**Answer:**

**1. getStaticProps (SSG):**
```jsx
// Cached at build time
export async function getStaticProps() {
  const data = await fetch('/api/data').then(r => r.json());
  return {
    props: { data },
    revalidate: 3600 // ISR: revalidate every hour
  };
}
```

**2. getServerSideProps (SSR):**
```jsx
// Fetched on every request
export async function getServerSideProps({ params }) {
  const data = await fetch(`/api/data/${params.id}`).then(r => r.json());
  return { props: { data } };
}
```

**3. Client-side fetch:**
```jsx
'use client';
import { useEffect, useState } from 'react';

export default function Page() {
  const [data, setData] = useState(null);

  useEffect(() => {
    fetch('/api/data')
      .then(r => r.json())
      .then(setData);
  }, []);

  return <div>{data}</div>;
}
```

**4. App Router fetch (Recommended):**
```jsx
// app/page.js
async function getData() {
  const res = await fetch('https://api.example.com/data', {
    next: { revalidate: 3600 } // ISR
  });
  return res.json();
}

export default async function Page() {
  const data = await getData();
  return <div>{data}</div>;
}
```

## Q3: How does Next.js handle code splitting?
**Answer:**
Next.js automatically splits code at the page level.

**Automatic code splitting:**
```
// Each page is its own bundle
pages/index.js → index.js bundle
pages/about.js → about.js bundle
pages/contact.js → contact.js bundle
```

**Dynamic imports:**
```jsx
import dynamic from 'next/dynamic';

// Load component only when needed
const DynamicComponent = dynamic(() => import('../components/DynamicComponent'), {
  loading: () => <p>Loading...</p>,
  ssr: false // Optional: disable SSR
});

export default function Home() {
  return <DynamicComponent />;
}
```

## Q4: What is the difference between next/image and <img>?
**Answer:**

**<img>:**
```jsx
<img src="/image.jpg" alt="description" />
// Downloads full-size image
// No optimization
// Layout shift risk
```

**next/image:**
```jsx
import Image from 'next/image';

<Image
  src="/image.jpg"
  alt="description"
  width={500}
  height={300}
  quality={75}
  placeholder="blur"
  priority={false}
/>
```

**Benefits of next/image:**
- Automatic responsive images
- Modern formats (AVIF, WebP)
- Lazy loading
- Prevents CLS
- Automatic sizing

## Q5: How do you handle errors in Next.js?
**Answer:**

**Error handling with error.js (App Router):**
```jsx
// app/error.js
'use client';

import { useEffect } from 'react';

export default function Error({ error, reset }) {
  useEffect(() => {
    console.error(error);
  }, [error]);

  return (
    <div>
      <h2>Something went wrong!</h2>
      <button onClick={() => reset()}>Try again</button>
    </div>
  );
}
```

**Global error handling:**
```jsx
// app/global-error.js
'use client';

export default function GlobalError({ error, reset }) {
  return (
    <html>
      <body>
        <h2>Something went wrong!</h2>
        <button onClick={() => reset()}>Try again</button>
      </body>
    </html>
  );
}
```

**Custom error pages:**
```jsx
// pages/404.js
export default function Custom404() {
  return <h1>404 - Page Not Found</h1>;
}

// pages/500.js
export default function Custom500() {
  return <h1>500 - Server Error</h1>;
}
```

## Q6: How do you implement layouts?
**Answer:**

**Nested Layouts (App Router):**
```jsx
// app/layout.js (Root layout)
export default function RootLayout({ children }) {
  return (
    <html>
      <body>
        <nav>Navigation</nav>
        {children}
        <footer>Footer</footer>
      </body>
    </html>
  );
}

// app/dashboard/layout.js (Nested layout)
export default function DashboardLayout({ children }) {
  return (
    <div>
      <aside>Dashboard Sidebar</aside>
      <main>{children}</main>
    </div>
  );
}
```

## Q7: How do you use Streaming?
**Answer:**
Streaming sends HTML to the browser in chunks as it's generated.

```jsx
// app/page.js
import { Suspense } from 'react';

async function SlowComponent() {
  await new Promise(resolve => setTimeout(resolve, 2000));
  return <div>Slow content</div>;
}

export default function Page() {
  return (
    <div>
      <h1>Fast content</h1>
      <Suspense fallback={<div>Loading...</div>}>
        <SlowComponent />
      </Suspense>
    </div>
  );
}
```

**Benefits:**
- Users see content faster
- Better perceived performance
- Improved SEO

## Q8: How do you implement caching in Next.js?
**Answer:**

```jsx
// next.config.js
const nextConfig = {
  headers: async () => {
    return [
      {
        source: '/images/:path*',
        headers: [
          {
            key: 'Cache-Control',
            value: 'public, max-age=31536000, immutable'
          }
        ]
      }
    ];
  }
};

module.exports = nextConfig;

// Or in API route
export default function handler(req, res) {
  res.setHeader('Cache-Control', 'public, max-age=3600');
  res.json({ data: 'cached' });
}

// Or with fetch
const data = await fetch('https://api.example.com/data', {
  next: { revalidate: 3600 } // Cache for 1 hour
}).then(r => r.json());
```

## Q9: How do you implement internationalization (i18n)?
**Answer:**

```jsx
// next.config.js
const nextConfig = {
  i18n: {
    locales: ['en', 'fr', 'de'],
    defaultLocale: 'en'
  }
};

// pages/[locale]/index.js
import { useRouter } from 'next/router';

export default function Home() {
  const router = useRouter();
  const { locale } = router.query;

  return (
    <div>
      <h1>Current locale: {locale}</h1>
      <button onClick={() => router.push(`/en/`)}>English</button>
      <button onClick={() => router.push(`/fr/`)}>Français</button>
    </div>
  );
}
```

## Q10: How do you handle webhooks?
**Answer:**

```jsx
// pages/api/webhooks/stripe.js
import { buffer } from 'micro';
import Stripe from 'stripe';

const stripe = new Stripe(process.env.STRIPE_SECRET_KEY);

export const config = {
  api: {
    bodyParser: false
  }
};

export default async function handler(req, res) {
  if (req.method !== 'POST') {
    return res.status(405).end();
  }

  const buf = await buffer(req);
  const sig = req.headers['stripe-signature'];

  let event;

  try {
    event = stripe.webhooks.constructEvent(
      buf,
      sig,
      process.env.STRIPE_WEBHOOK_SECRET
    );
  } catch (err) {
    return res.status(400).send(`Webhook Error: ${err.message}`);
  }

  switch (event.type) {
    case 'payment_intent.succeeded':
      console.log('Payment succeeded');
      // Handle payment
      break;
    case 'payment_intent.payment_failed':
      console.log('Payment failed');
      // Handle failure
      break;
  }

  res.json({ received: true });
}
```

## Q11: How do you optimize Next.js for production?
**Answer:**

**1. Analyze bundle size:**
```bash
npm run build
npm install --save-dev @next/bundle-analyzer
```

**2. Use compression:**
```jsx
// next.config.js
const withCompression = require('next-compression');

const nextConfig = {
  compress: true
};

module.exports = withCompression(nextConfig);
```

**3. Image optimization:**
```jsx
const nextConfig = {
  images: {
    formats: ['image/avif', 'image/webp'],
    deviceSizes: [640, 750, 828, 1080, 1200, 1920],
    imageSizes: [16, 32, 48, 64, 96, 128, 256, 384]
  }
};
```

**4. Database connection pooling:**
```jsx
// API route best practice
let db;

export default async function handler(req, res) {
  if (!db) {
    db = await initializeDB();
  }

  const result = await db.query('SELECT * FROM users');
  res.json(result);
}
```

## Q12: Real-world scenario - Blog Platform
**Answer:**

```jsx
// app/posts/[slug]/page.js
import { notFound } from 'next/navigation';
import { Metadata } from 'next';

async function getPost(slug) {
  const res = await fetch(`https://api.example.com/posts/${slug}`, {
    next: { revalidate: 3600 }
  });

  if (!res.ok) {
    notFound();
  }

  return res.json();
}

export async function generateMetadata({ params }) {
  const post = await getPost(params.slug);
  return {
    title: post.title,
    description: post.excerpt
  };
}

export async function generateStaticParams() {
  const posts = await fetch('https://api.example.com/posts').then(r => r.json());
  return posts.map(post => ({ slug: post.slug }));
}

export default async function PostPage({ params }) {
  const post = await getPost(params.slug);

  return (
    <article>
      <h1>{post.title}</h1>
      <p>{post.date}</p>
      <div>{post.content}</div>
    </article>
  );
}
```

