# Quick Reference Guide - Interview Preparation

## 🎯 Problem Difficulty Quick Picker

### Easy Problems (Perfect for warm-up)
- Palindrome Check
- Reverse String
- Valid Parentheses
- Two Sum
- Remove Duplicates
- Best Time to Buy and Sell Stock
- Maximum Subarray (Kadane's)
- Reverse Linked List
- Binary Tree Max Depth
- Climbing Stairs

### Medium Problems (Core competency)
- Longest Substring Without Repeating
- Three Sum
- Rotate Array
- Container With Most Water
- Next Permutation
- Longest Palindromic Substring
- Group Anagrams
- LCS / Edit Distance
- Number of Islands
- Coin Change

### Hard Problems (Advanced)
- Trapping Rain Water
- First Missing Positive
- Serialize/Deserialize Tree
- Word Ladder
- Regular Expression Matching
- Median of Two Sorted Arrays
- Edit Distance
- Largest Rectangle in Histogram
- N-Queens Problem

---

## 🔑 Essential Problem Patterns

### Pattern 1: Sliding Window
**Use for:** Subarray/substring problems with a condition
**Template:**
```typescript
let left = 0;
for (let right = 0; right < n; right++) {
  // Add right element to window
  while (condition) {
    // Shrink window from left
    left++;
  }
  // Update answer
}
```
**Problems:** Longest substring without repeating, max subarray of size k

---

### Pattern 2: Two Pointers
**Use for:** Sorted arrays, meeting points, palindromes
**Template:**
```typescript
let left = 0, right = n - 1;
while (left < right) {
  if (condition) {
    left++;
  } else {
    right--;
  }
}
```
**Problems:** Two sum (sorted), container with most water, reverse string

---

### Pattern 3: Binary Search
**Use for:** Sorted arrays, finding target
**Template:**
```typescript
let left = 0, right = n - 1;
while (left <= right) {
  const mid = left + Math.floor((right - left) / 2);
  if (arr[mid] === target) return mid;
  if (arr[mid] < target) left = mid + 1;
  else right = mid - 1;
}
return -1;
```
**Problems:** Search rotated array, find ceiling/floor, median of arrays

---

### Pattern 4: DFS/BFS
**Use for:** Trees, graphs, connected components
**DFS Template:**
```typescript
function dfs(node, visited) {
  visited.add(node);
  for (const neighbor of node.neighbors) {
    if (!visited.has(neighbor)) {
      dfs(neighbor, visited);
    }
  }
}
```
**BFS Template:**
```typescript
const queue = [start];
const visited = new Set([start]);
while (queue.length > 0) {
  const node = queue.shift();
  for (const neighbor of node.neighbors) {
    if (!visited.has(neighbor)) {
      visited.add(neighbor);
      queue.push(neighbor);
    }
  }
}
```
**Problems:** Number of islands, clone graph, max depth of tree

---

### Pattern 5: Dynamic Programming
**Use for:** Optimization, overlapping subproblems
**Template:**
```typescript
const dp = []; // or Map
// Base cases
dp[0] = base_value;
// Fill dp
for (let i = 1; i < n; i++) {
  dp[i] = Math.max(
    dp[i-1] + current,
    dp[i-2] + current,
    ...
  );
}
return dp[n-1];
```
**Problems:** House robber, coin change, climbing stairs, LIS

---

### Pattern 6: Union-Find
**Use for:** Connected components, cycle detection
**Template:**
```typescript
class UnionFind {
  find(x) { // with path compression
    if (parent[x] !== x) {
      parent[x] = this.find(parent[x]);
    }
    return parent[x];
  }
  
  union(x, y) { // with union by rank
    const rootX = this.find(x);
    const rootY = this.find(y);
    if (rootX === rootY) return false;
    // merge logic
    return true;
  }
}
```
**Problems:** Number of islands, detect cycle, Kruskal's MST

---

### Pattern 7: Backtracking
**Use for:** Permutations, combinations, puzzles
**Template:**
```typescript
function backtrack(path, remaining) {
  if (isComplete(path)) {
    result.push([...path]);
    return;
  }
  
  for (const choice of remaining) {
    path.push(choice);
    backtrack(path, remaining - choice);
    path.pop();
  }
}
```
**Problems:** Permutations, N-queens, word search

---

## ⏱️ Time Complexity Quick Reference

| Data Structure | Access | Search | Insert | Delete |
|---|---|---|---|---|
| Array | O(1) | O(n) | O(n) | O(n) |
| Linked List | O(n) | O(n) | O(1) | O(1) |
| Binary Search Tree | O(log n) | O(log n) | O(log n) | O(log n) |
| Hash Table | - | O(1) | O(1) | O(1) |
| Binary Heap | - | O(n) | O(log n) | O(log n) |

| Algorithm | Best | Average | Worst | Space |
|---|---|---|---|---|
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Binary Search | O(1) | O(log n) | O(log n) | O(1) |

---

## 🛠️ Common Techniques

### 1. Memoization (Top-Down DP)
```typescript
const memo = new Map();
function solve(n) {
  if (memo.has(n)) return memo.get(n);
  // recursive logic
  memo.set(n, result);
  return result;
}
```

### 2. Tabulation (Bottom-Up DP)
```typescript
const dp = new Array(n + 1).fill(0);
for (let i = 1; i <= n; i++) {
  dp[i] = calculation based on previous states
}
return dp[n];
```

### 3. Hash Map for Frequency
```typescript
const freq = new Map();
for (const item of items) {
  freq.set(item, (freq.get(item) ?? 0) + 1);
}
```

### 4. Sorting with Custom Comparator
```typescript
// TypeScript
arr.sort((a, b) => {
  if (condition1) return -1;
  if (condition2) return 1;
  return 0;
});

// Java
Collections.sort(arr, (a, b) -> {
  if (condition1) return -1;
  if (condition2) return 1;
  return 0;
});
```

### 5. Min/Max Tracking
```typescript
let minVal = arr[0], maxVal = arr[0];
for (let i = 1; i < arr.length; i++) {
  minVal = Math.min(minVal, arr[i]);
  maxVal = Math.max(maxVal, arr[i]);
}
```

---

## 🎓 Study Schedule (8-Week Plan)

### Week 1: Foundation & Basics
- Day 1-2: Array basics (5 easy problems)
- Day 3-4: String basics (5 easy problems)
- Day 5: Sorting/Searching (merge sort, binary search)
- Day 6-7: Practice & Review

### Week 2: Data Structures
- Day 1-2: Linked Lists (4 problems)
- Day 3-4: Trees (BFS, DFS, inorder/preorder/postorder)
- Day 5: Hash Maps (3 problems)
- Day 6-7: Practice & Review

### Week 3: Core Patterns
- Day 1: Sliding Window (3-4 problems)
- Day 2: Two Pointers (3-4 problems)
- Day 3: Binary Search (3-4 problems)
- Day 4: Graph Basics (DFS/BFS - 2 problems)
- Day 5-7: Mix & Practice

### Week 4: Dynamic Programming
- Day 1: Simple DP (Fibonacci, stairs)
- Day 2: Knapsack & variants
- Day 3: LIS, LCS
- Day 4: Matrix DP (unique paths)
- Day 5: Complex DP problems
- Day 6-7: Practice & Review

### Week 5: Advanced Algorithms
- Day 1-2: Sorting algorithms (merge, quick, heap)
- Day 3: Topological sort & DAG
- Day 4: Dijkstra & shortest paths
- Day 5: Union-Find & applications
- Day 6-7: Practice & Review

### Week 6: Hard Problems
- Day 1-2: Hard array problems
- Day 3: Hard string problems
- Day 4: Hard tree/graph problems
- Day 5: Hard DP problems
- Day 6-7: Practice & Review

### Week 7: Mixed Practice
- Solve random problems from all categories
- Focus on weak areas
- Practice under time constraints (45 mins)
- Do mock interviews

### Week 8: Final Review
- Review all weak topics
- Practice trickiest problems
- Do full mock interviews
- Prepare for interview day

---

## 🚨 Common Interview Mistakes

❌ **DON'T:**
- Jump into coding without understanding problem
- Assume input constraints
- Forget edge cases
- Write messy, unreadable code
- Go silent during problem-solving
- Forget to discuss complexity
- Ignore follow-up questions

✅ **DO:**
- Ask clarifying questions
- Start with brute force
- Think about edge cases (empty, single element, duplicates)
- Write clean code with meaningful variables
- Explain your approach
- Optimize after getting working solution
- Test with examples

---

## 🎯 Interview Checklist

Before the interview:
- [ ] Review all problem patterns
- [ ] Practice 3-5 problems each day
- [ ] Do mock interviews
- [ ] Review data structure implementations
- [ ] Know time/space complexities
- [ ] Practice both TypeScript and Java
- [ ] Get good sleep night before

During the interview:
- [ ] Take 2-3 minutes to understand
- [ ] Ask clarifying questions
- [ ] Discuss approach before coding
- [ ] Code slowly and clearly
- [ ] Test with examples
- [ ] Discuss optimizations
- [ ] Handle edge cases

---

## 📱 Mobile Study Tips

### Quick Review (5 mins)
- Memorize pattern templates
- Review complexity cheat sheet
- Quick recap of one algorithm

### Medium Session (30 mins)
- Solve one easy problem
- Review one pattern
- Read one algorithm explanation

### Deep Dive (60+ mins)
- Solve 1-2 medium problems
- Implement algorithm from scratch
- Do mock interview

---

## 🏆 Success Metrics

### Week 1-2:
- ✅ Solve 20+ easy problems
- ✅ Understand all 10 patterns
- ✅ Code in both languages

### Week 3-4:
- ✅ Solve 20+ medium problems
- ✅ Master DP concepts
- ✅ Interview time: 60-90 mins

### Week 5-6:
- ✅ Solve 10+ hard problems
- ✅ Master all DSA algorithms
- ✅ Interview time: 45-60 mins

### Week 7-8:
- ✅ Solve random problems correctly
- ✅ Interview time: 30-45 mins
- ✅ Ready for real interview!

---

## 🌟 Final Tips for Success

1. **Consistency** - Practice daily, even 30 mins helps
2. **Depth** - Understand concepts, don't just memorize
3. **Practice** - Code on paper first, then on computer
4. **Communication** - Talk through your approach
5. **Optimization** - Improve after getting working solution
6. **Testing** - Always test with examples and edge cases
7. **Review** - Learn from each problem, identify patterns
8. **Confidence** - You've got this! Trust your preparation

---

## 💬 During Mock Interview Template

```
Step 1: Clarify (2 mins)
"Let me make sure I understand the problem...
- The input is [describe input]
- Expected output is [describe output]
- Constraints are [list constraints]"

Step 2: Approach (3 mins)
"My approach:
1. [First step of approach]
2. [Second step]
3. [Third step]
Time: O(...) | Space: O(...)"

Step 3: Code (15-20 mins)
[Write code clearly and methodically]

Step 4: Test (5 mins)
"Let me test with the examples:
- Example 1: [trace through]
- Edge case: [test edge case]"

Step 5: Optimize (3 mins)
"Could we improve by...
[discuss optimizations]"

Step 6: Q&A (2 mins)
"Do you have any questions about my solution?"
```

---

**Remember: The best interview preparation is consistent practice!**

Good luck! 🎓✨
