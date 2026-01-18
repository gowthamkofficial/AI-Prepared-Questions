# Next.js Interview Questions Bank

## Common Next.js Interview Questions

### Quick Answers

**Q1: What is Next.js and why use it?**
Next.js is a React framework that enables SSR, SSG, API routes, and automatic code splitting for building full-stack applications.

**Q2: What are the advantages of Next.js?**
- Built-in SSR and SSG
- API routes
- Automatic code splitting
- Image optimization
- File-based routing
- SEO friendly
- Great performance

**Q3: What's the difference between next/link and <a>?**
`next/link` prefetches pages and enables client-side navigation without full page reload. `<a>` causes a full page reload.

**Q4: How does Next.js handle routing?**
File-based routing: files in `pages/` or `app/` directory automatically become routes.

**Q5: What is getInitialProps?**
Deprecated method for data fetching. Use `getStaticProps` or `getServerSideProps` instead.

**Q6: Can you use Next.js without a server?**
Yes, with static export. Set `output: 'export'` in next.config.js.

**Q7: What is the _app.js file?**
A wrapper component that runs on every page, useful for global styles, layouts, and state management.

**Q8: How do you deploy Next.js?**
Deploy to Vercel (easiest), AWS, Google Cloud, Azure, or self-hosted servers using `npm run build && npm run start`.

**Q9: What's the purpose of the public folder?**
Static files served at the root of your domain (/image.jpg maps to /public/image.jpg).

**Q10: How do you handle CORS in Next.js?**
Use middleware or add headers in API routes.

---

## Performance-Related Questions

**Q1: How does Next.js improve performance?**
- Automatic code splitting
- Image optimization
- CSS-in-JS optimization
- Caching strategies
- Compression
- Tree-shaking

**Q2: What is the difference between a static asset and a dynamic resource?**
Static assets are served from `/public` with caching. Dynamic resources are generated on-demand.

**Q3: How do you measure performance in Next.js?**
Use Web Vitals (LCP, FID, CLS), Lighthouse, PageSpeed Insights.

**Q4: What are Web Vitals?**
- LCP (Largest Contentful Paint): ~2.5s
- FID (First Input Delay): ~100ms
- CLS (Cumulative Layout Shift): ~0.1

---

## Architecture & Best Practices

**Q1: What's the recommended folder structure?**
```
project/
├── pages/
│   ├── index.js
│   ├── api/
│   └── [id].js
├── public/
├── styles/
├── components/
├── lib/
├── hooks/
└── utils/
```

**Q2: How do you organize API routes?**
Group by feature: `api/users.js`, `api/posts.js`, or `api/users/[id].js`

**Q3: What are best practices for performance?**
- Use SSG when possible
- Lazy load components
- Optimize images
- Minimize JavaScript
- Use CDN
- Cache aggressively

**Q4: How do you handle errors?**
Use try-catch in API routes, error boundaries in components, and custom error pages.

---

## Testing & Debugging

**Q1: How do you test Next.js applications?**
Use Jest, React Testing Library, and Cypress for e2e testing.

**Q2: How do you debug Next.js?**
- VSCode debugger
- browser DevTools
- console.log
- Next.js debug mode: `DEBUG=* npm run dev`

---

## Migration & Upgrades

**Q1: How do you migrate from Pages to App Router?**
- Move pages to `app/` folder
- Use new file structure
- Convert `getServerSideProps` to direct fetch
- Use new layouts system

**Q2: What's the difference between Next.js 12 and 13?**
- App Router (new)
- React Server Components
- New image optimization
- Streaming support
- Smaller bundles

---

## Common Patterns

**Authentication Pattern:**
```jsx
// pages/api/auth/[...nextauth].js
import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials"

export const authOptions = {
  providers: [
    CredentialsProvider({...})
  ]
}

export default NextAuth(authOptions)
```

**Database Pattern:**
```jsx
// lib/db.js
let db

export async function getDB() {
  if (!db) {
    db = await initDB()
  }
  return db
}
```

**API Error Handling:**
```jsx
export default function handler(req, res) {
  try {
    // API logic
  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}
```

