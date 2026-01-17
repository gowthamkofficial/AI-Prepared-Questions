# Interview Preparation - Complete Coding Problems & DSA Algorithms Guide

## 📁 Directory Structure

```
Coding-Problems/
├── INTERVIEW_QUESTIONS.md          # All 100+ frequently asked interview problems
├── Solutions/
│   ├── typescript-solutions.ts      # TypeScript implementations (80+ problems)
│   └── JavaSolutions.java           # Java implementations (80+ problems)
└── DSA-Algorithms/
    ├── IMPORTANT_ALGORITHMS.md      # Complete algorithm explanations and patterns
    ├── algorithm-implementations.ts # TypeScript algorithm implementations
    └── AlgorithmImplementations.java # Java algorithm implementations
```

---

## 📋 INTERVIEW_QUESTIONS.md

**Contains:** 100+ frequently asked interview problems organized by category

### Categories Covered:
1. **String Problems (10)** - Palindromes, substrings, anagrams, compressions
2. **Array Problems (11)** - Two sum, merging, sorting, rotation, water trapping
3. **Linked List Problems (8)** - Reversal, merging, cycle detection, palindromes
4. **Tree Problems (12)** - Traversals, LCA, validation, serialization
5. **Graph Problems (8)** - Islands, cloning, topological sort, paths
6. **Dynamic Programming (14)** - Fibonacci, knapsack, LIS, edit distance
7. **Sorting & Searching (8)** - Merge sort, quick sort, binary search, medians
8. **Stack & Queue (7)** - Parentheses, RPN, monotonic stack, LRU cache
9. **Hash Table/Map (9)** - Two sum, anagrams, majority elements, LRU
10. **Bit Manipulation (8)** - XOR, hamming distance, power of two
11. **Additional (3)** - Regex, backtracking, N-queens

**Each problem includes:**
- Difficulty level (Easy/Medium/Hard)
- Tags for quick reference
- Brief description
- Example if applicable

---

## 🔧 Solutions/typescript-solutions.ts

**Complete TypeScript implementations with:**
- ✅ All 80+ problems implemented
- ⏱️ Time & Space complexity for each solution
- 📝 Clear comments explaining approach
- 🧪 Production-ready code
- 📦 Exported functions for easy import

### Problem Coverage:
```typescript
// String Problems (10 solutions)
- isPalindrome(), reverseString(), lengthOfLongestSubstring()
- isValidParentheses(), longestPalindrome(), isAnagram()
- groupAnagrams(), longestCommonPrefix(), compressString()

// Array Problems (12 solutions)
- twoSum(), threeSum(), mergeSortedArrays()
- removeDuplicates(), rotateArray(), maxArea()
- maxProfit(), maxSubArray(), nextPermutation()
- searchInRotatedArray(), firstMissingPositive(), trap()

// Linked List Problems (7 solutions)
- reverseList(), mergeTwoLists(), hasCycle()
- middleOfList(), removeNthFromEnd(), isPalindromeList()

// Tree Problems (11 solutions)
- inorderTraversal(), preorderTraversal(), postorderTraversal()
- levelOrderTraversal(), maxDepth(), lowestCommonAncestor()
- isValidBST(), hasPathSum(), diameterOfBinaryTree()
- isBalanced(), isSymmetric()

// Dynamic Programming (12 solutions)
- fib(), climbStairs(), rob(), coinChange()
- lengthOfLIS(), editDistance(), uniquePaths()
- maxProduct(), and more...

// Searching & Sorting (6 solutions)
- mergeSort(), quickSort(), binarySearch()

// Hash Map (6 solutions)
- majorityElement(), firstUniqueChar(), etc.

// Bit Manipulation (6 solutions)
- singleNumber(), hammingWeight(), isPowerOfTwo()
- missingNumber(), hammingDistance()
```

---

## ☕ Solutions/JavaSolutions.java

**Complete Java implementations with:**
- ✅ Parallel to TypeScript solutions
- 📦 Ready to use in your Java IDE
- 🎯 Interview-style code
- 🧬 Proper Java conventions

### Key Classes:
```java
- ListNode class for linked list problems
- TreeNode class for tree problems
- Complete implementations of all major algorithms
```

---

## 📚 DSA-Algorithms/IMPORTANT_ALGORITHMS.md

**Comprehensive guide covering 30+ essential algorithms:**

### 1. **Sorting Algorithms**
   - Merge Sort (O(n log n), stable)
   - Quick Sort (O(n log n) avg, in-place)
   - Heap Sort (O(n log n), guaranteed)
   - Insertion Sort (O(n²), adaptive)
   - Counting Sort (O(n+k), non-comparative)
   - Radix Sort (O(d*n), for integers/strings)

### 2. **Searching Algorithms**
   - Binary Search (O(log n))
   - Linear Search (O(n))

### 3. **Graph Algorithms**
   - DFS - Depth First Search
   - BFS - Breadth First Search
   - Dijkstra's Algorithm (shortest path)
   - Bellman-Ford (handles negative weights)
   - Floyd-Warshall (all-pairs shortest path)
   - Topological Sort
   - Kruskal's Algorithm (MST)
   - Prim's Algorithm (MST)

### 4. **String Algorithms**
   - KMP (Knuth-Morris-Pratt)
   - Boyer-Moore
   - Rabin-Karp (rolling hash)
   - Z-Algorithm
   - Longest Common Subsequence
   - Longest Palindromic Substring

### 5. **Number Theory**
   - GCD & Extended Euclidean Algorithm
   - Sieve of Eratosthenes
   - Modular Arithmetic
   - Fast Exponentiation

### 6. **Greedy Algorithms**
   - Activity Selection
   - Huffman Coding
   - Interval Scheduling

### 7. **Important Patterns**
   - Sliding Window
   - Two Pointers
   - Union-Find
   - Segment Tree / Fenwick Tree
   - Trie (Prefix Tree)

**Each algorithm includes:**
- 📊 Time & Space complexity analysis
- 🧠 Clear algorithm explanation
- 💡 Pseudocode
- ✨ When to use this algorithm
- ⚡ Pros & Cons comparison

---

## 🚀 DSA-Algorithms/algorithm-implementations.ts

**TypeScript implementations of all DSA algorithms:**

```typescript
// Sorting
- mergeSort(), quickSort(), heapSort()
- insertionSort(), countingSort(), radixSort()

// Searching
- binarySearch(), binarySearchFirst(), binarySearchLast()

// Graph Algorithms
- dfs(), dfsIterative(), bfs()
- dijkstra(), topologicalSort(), topologicalSortKahn()

// String Matching
- kmpSearch(), rabinKarp()

// Dynamic Programming
- lcs(), editDistance()

// Data Structures
- UnionFind class (path compression + union by rank)
- Trie class (prefix tree)
- SegmentTree class (range queries)
```

---

## ☕ DSA-Algorithms/AlgorithmImplementations.java

**Java implementations of all DSA algorithms:**

```java
// All algorithms mirrored from TypeScript version
// Ready to use in Java projects
// Includes proper Java conventions and generics
```

---

## 🎯 How to Use This Repository

### 1. **Start with Questions**
```
1. Read INTERVIEW_QUESTIONS.md
2. Pick a problem by difficulty or category
3. Try to solve it yourself first (important!)
4. Check the complexity targets
```

### 2. **Look at Solutions**
```
1. If stuck, check TypeScript or Java solutions
2. Understand the approach, not just the code
3. Try to implement from scratch after understanding
4. Pay attention to edge cases
```

### 3. **Learn DSA Algorithms**
```
1. Read IMPORTANT_ALGORITHMS.md for theory
2. Understand the algorithm explanation
3. Check algorithm-implementations.ts/java for code
4. Try implementing from scratch
```

### 4. **Practice Strategy**
```
Beginner:
  → Start with Easy problems (2-3 weeks)
  → Focus on understanding concepts
  → Implement in TypeScript and Java

Intermediate:
  → Move to Medium problems (3-4 weeks)
  → Optimize for time and space
  → Practice pattern recognition

Advanced:
  → Solve Hard problems (2-3 weeks)
  → Combine multiple concepts
  → Optimize for interview time (45 mins)
```

---

## 📊 Problem Distribution

| Category | Count | Difficulty | Time |
|----------|-------|-----------|------|
| String | 10 | Easy-Medium | 15-30 mins |
| Array | 11 | Easy-Hard | 20-45 mins |
| Linked List | 8 | Easy-Medium | 20-30 mins |
| Tree | 12 | Easy-Hard | 25-45 mins |
| Graph | 8 | Medium-Hard | 30-45 mins |
| DP | 14 | Easy-Hard | 25-45 mins |
| Sorting/Searching | 8 | Easy-Hard | 20-40 mins |
| Stack/Queue | 7 | Easy-Medium | 15-30 mins |
| Hash Map | 9 | Easy-Medium | 15-25 mins |
| Bit Manipulation | 8 | Easy-Medium | 10-20 mins |
| **Total** | **95+** | **Mixed** | **~2000 mins** |

---

## 🏆 Interview Preparation Timeline

### Week 1-2: Foundation
- Review basic data structures (arrays, linked lists, trees)
- Learn important patterns (sliding window, two pointers)
- Solve 5-10 easy problems daily

### Week 3-4: Core Algorithms
- Study DSA algorithms
- Solve 3-5 medium problems daily
- Practice in both TypeScript and Java

### Week 5-6: Advanced Topics
- Tackle hard problems
- Focus on optimization
- Do mock interviews
- Solve 2-3 hard problems daily

### Week 7: Final Polish
- Review weak areas
- Practice under time constraints
- Do full mock interviews
- Review complexity analysis

---

## 💡 Interview Tips

### Before Coding
✅ Understand the problem completely
✅ Ask clarifying questions
✅ Discuss approach verbally
✅ Mention time/space complexity

### While Coding
✅ Write clean, readable code
✅ Explain your thought process
✅ Handle edge cases
✅ Test with examples

### After Coding
✅ Verify correctness
✅ Discuss optimizations
✅ Consider alternative approaches
✅ Ask for feedback

---

## 🔍 Common Patterns to Master

1. **Sliding Window** - substring problems
2. **Two Pointers** - array/string problems
3. **Binary Search** - sorted array problems
4. **DFS/BFS** - tree/graph problems
5. **Dynamic Programming** - optimization problems
6. **Union-Find** - connectivity problems
7. **Sorting + Hash Map** - grouping problems
8. **Greedy** - optimization problems
9. **Backtracking** - permutation/combination problems
10. **Trie** - prefix/word matching problems

---

## 📈 Complexity Cheat Sheet

| Operation | Complexity |
|-----------|-----------|
| Array access | O(1) |
| Array insertion/deletion | O(n) |
| Linked list insertion/deletion | O(1) with pointer |
| Hash map lookup/insert/delete | O(1) |
| Binary search | O(log n) |
| Merge sort | O(n log n) |
| Quick sort | O(n log n) avg, O(n²) worst |
| BFS/DFS | O(V + E) |
| Dijkstra | O((V + E) log V) |
| Binary tree traversal | O(n) |
| Dynamic programming | O(n × m) |

---

## 🎓 Resources for Further Learning

- **Books:** Cracking the Coding Interview, Elements of Programming Interviews
- **Online:** LeetCode, HackerRank, GeeksforGeeks
- **Videos:** TakeUforward, Back to Back SWE, NeetCode
- **Articles:** Medium blogs on DSA, official documentation

---

## 🚀 Next Steps

1. ✅ Fork/Clone this repository
2. ✅ Create your own branch for solutions
3. ✅ Start with easier problems
4. ✅ Time yourself (aim for 30-45 mins per problem)
5. ✅ Review and refactor code
6. ✅ Solve in both TypeScript and Java
7. ✅ Do mock interviews
8. ✅ Track progress

---

## ⭐ Quick Start

```
1. Read this README (5 mins)
2. Pick a problem from INTERVIEW_QUESTIONS.md
3. Solve it (30-45 mins)
4. Check solution (10-15 mins)
5. Optimize and refactor (10 mins)
6. Repeat with next problem

Goal: 2-3 problems per day, 5 days a week
Timeline: 8-10 weeks to master interview prep
```

---

## 📝 Notes

- All solutions include comments explaining the approach
- Time and space complexity is mentioned for every solution
- Practice with pen and paper first, then code
- Don't memorize solutions; understand the approach
- Focus on problem-solving skills, not memorization
- Communicate clearly during interviews
- Test edge cases thoroughly

---

**Good luck with your interview preparation! 🎯**

Remember: **Consistent practice beats last-minute cramming.**

Last Updated: January 17, 2026
