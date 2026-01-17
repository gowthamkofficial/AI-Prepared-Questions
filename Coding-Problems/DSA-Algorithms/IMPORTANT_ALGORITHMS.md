# Essential DSA Algorithms for Interviews

## Table of Contents
1. [Sorting Algorithms](#sorting-algorithms)
2. [Searching Algorithms](#searching-algorithms)
3. [Graph Algorithms](#graph-algorithms)
4. [String Algorithms](#string-algorithms)
5. [Number Theory](#number-theory)
6. [Greedy Algorithms](#greedy-algorithms)

---

## SORTING ALGORITHMS

### 1. **Merge Sort**
**Category:** Divide & Conquer
**Time:** O(n log n) | **Space:** O(n)

**Stability:** Stable
**When to Use:** External sorting, linked list sorting, guaranteed O(n log n)

```
Algorithm:
1. Divide array into halves recursively
2. Merge sorted halves back together
3. During merge, compare elements and place smaller first

Example:
Array: [38, 27, 43, 3, 9, 82, 10]
Step 1: Split into singles [38], [27], [43], [3], [9], [82], [10]
Step 2: Merge: [27,38], [3,43], [9,82], [10]
Step 3: Merge: [3,27,38,43], [9,10,82]
Step 4: Final: [3,9,10,27,38,43,82]
```

**Pros:**
- O(n log n) guaranteed
- Stable sort
- Good for linked lists

**Cons:**
- O(n) extra space
- Not in-place
- Slower for small arrays

---

### 2. **Quick Sort**
**Category:** Divide & Conquer
**Time:** O(n log n) avg | O(n²) worst | **Space:** O(log n)

**Stability:** Unstable
**When to Use:** Average case best, in-place sorting preferred

```
Algorithm:
1. Choose pivot element
2. Partition: elements < pivot | pivot | elements > pivot
3. Recursively sort partitions

Pivot Selection Strategies:
- First element
- Last element
- Random element (randomized quicksort)
- Median-of-three
```

**Pros:**
- O(log n) space
- In-place sorting
- Fast average case O(n log n)
- Cache efficient

**Cons:**
- O(n²) worst case
- Unstable
- Requires careful pivot selection

---

### 3. **Heap Sort**
**Category:** Selection Sort
**Time:** O(n log n) | **Space:** O(1)

**Stability:** Unstable
**When to Use:** Guaranteed O(n log n), need in-place sorting

```
Algorithm:
1. Build max heap from array
2. Repeatedly extract max element to end
3. Reduce heap size and repeat

Heap Structure (min 2n operations):
- Heapify-up when inserting
- Heapify-down when removing
```

**Pros:**
- O(n log n) guaranteed
- O(1) space
- In-place
- No worst case

**Cons:**
- Unstable
- Slower in practice (more moves)
- Poor cache locality

---

### 4. **Insertion Sort**
**Category:** Simple Sort
**Time:** O(n²) | **Space:** O(1)

**Stability:** Stable
**When to Use:** Small arrays, nearly sorted data, adaptive sorting

```
Algorithm:
1. Start from second element
2. Compare with elements before it
3. Shift larger elements right
4. Insert at correct position
```

**Pros:**
- O(n) for nearly sorted data (adaptive)
- Simple implementation
- O(1) space
- Stable
- Good for small n

**Cons:**
- O(n²) for random data
- Not suitable for large data

---

### 5. **Counting Sort**
**Category:** Non-comparative Sort
**Time:** O(n + k) | **Space:** O(k)

**When to Use:** Small range of integers (k is small)

```
Algorithm:
1. Create count array of size k (max value)
2. Count occurrences of each number
3. Reconstruct sorted array using counts
```

**Pros:**
- Linear time O(n + k)
- Stable
- Simple

**Cons:**
- Only for integers
- O(k) space where k is range
- Not good for large ranges

---

### 6. **Radix Sort**
**Category:** Non-comparative Sort
**Time:** O(d * (n + k)) | **Space:** O(n + k)

**When to Use:** Large datasets with many digits/characters

```
Algorithm:
1. Sort by least significant digit
2. Use stable sort (counting sort)
3. Move to next digit until done

Time = O(d * (n + k)) where d = digits, k = base (10)
```

**Pros:**
- Linear time O(d*n)
- Works well for large numbers
- Good for strings

**Cons:**
- Only for integers/strings
- O(n) space needed

---

## SEARCHING ALGORITHMS

### 1. **Binary Search**
**Category:** Divide & Conquer
**Time:** O(log n) | **Space:** O(1)

**Precondition:** Array must be sorted

```
Algorithm:
1. Start with left=0, right=n-1
2. Calculate mid = (left + right) / 2
3. If arr[mid] == target: return mid
4. If arr[mid] < target: left = mid + 1
5. Else: right = mid - 1
6. Repeat until found or left > right

Variations:
- First occurrence: keep going left
- Last occurrence: keep going right
- Ceiling/Floor element
- Rotation point
```

**Pros:**
- O(log n) very fast
- Minimal space

**Cons:**
- Requires sorted array
- Can't use on unsorted data

---

### 2. **Linear Search**
**Category:** Brute Force
**Time:** O(n) | **Space:** O(1)

**When to Use:** Unsorted data, small arrays, linked lists

---

## GRAPH ALGORITHMS

### 1. **Depth-First Search (DFS)**
**Time:** O(V + E) | **Space:** O(V)

```
Algorithm:
1. Start at source vertex
2. Mark as visited
3. Recursively visit all unvisited neighbors
4. Backtrack when no more neighbors

Uses:
- Topological sorting
- Cycle detection
- Path finding
- Connected components
```

**Implementation Options:**
- Recursive (call stack)
- Iterative (explicit stack)

---

### 2. **Breadth-First Search (BFS)**
**Time:** O(V + E) | **Space:** O(V)

```
Algorithm:
1. Start at source, add to queue
2. While queue not empty:
   - Dequeue vertex
   - Mark as visited
   - Add all unvisited neighbors to queue

Uses:
- Shortest path (unweighted)
- Level-order traversal
- Connected components
- Bipartite checking
```

---

### 3. **Dijkstra's Algorithm**
**Category:** Shortest Path
**Time:** O((V + E) log V) with min-heap | **Space:** O(V)

**Precondition:** No negative edge weights

```
Algorithm:
1. Initialize distances: source=0, others=∞
2. Create priority queue with source
3. While queue not empty:
   - Get vertex with min distance
   - For each neighbor:
     - Calculate new distance
     - If shorter, update and add to queue

Greedy approach: Always pick closest unvisited vertex
```

**Best For:** GPS navigation, network routing

---

### 4. **Bellman-Ford Algorithm**
**Category:** Shortest Path
**Time:** O(V * E) | **Space:** O(V)

**Handles:** Negative edge weights, detects negative cycles

```
Algorithm:
1. Initialize distances: source=0, others=∞
2. Relax all edges V-1 times
3. Check for negative cycles in Vth iteration

Detects negative cycles: If distance still decreases
```

**Slower but more flexible than Dijkstra**

---

### 5. **Floyd-Warshall Algorithm**
**Category:** All-Pairs Shortest Path
**Time:** O(V³) | **Space:** O(V²)

```
Algorithm:
1. Initialize distance matrix with edge weights
2. For each intermediate vertex k:
   - For each pair (i,j):
     - dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

Handles negative edges (not negative cycles)
```

---

### 6. **Topological Sort**
**Time:** O(V + E) | **Space:** O(V)

```
Algorithm (DFS-based):
1. Use DFS to traverse all vertices
2. Push vertex to stack after visiting all children
3. Pop stack to get topological order

Uses:
- Course scheduling
- Build systems
- Dependency resolution
```

---

### 7. **Kruskal's Algorithm (MST)**
**Category:** Minimum Spanning Tree
**Time:** O(E log E) | **Space:** O(V)

```
Algorithm:
1. Sort edges by weight
2. Use Union-Find to track components
3. For each edge:
   - If connects different components, add to MST
   - Union the components

Uses Union-Find for cycle detection
```

---

### 8. **Prim's Algorithm (MST)**
**Category:** Minimum Spanning Tree
**Time:** O((V + E) log V) with min-heap | **Space:** O(V)

```
Algorithm:
1. Start with arbitrary vertex
2. Add all adjacent edges to priority queue
3. While edges in queue:
   - Take minimum edge
   - If it connects new vertex, add to MST
```

---

## STRING ALGORITHMS

### 1. **KMP (Knuth-Morris-Pratt)**
**Category:** String Matching
**Time:** O(n + m) | **Space:** O(m)

**Purpose:** Find pattern in text efficiently

```
Algorithm:
1. Build failure function (LPS array):
   - For each position, find longest proper prefix that is suffix
2. Use failure function to avoid redundant comparisons

Example:
Pattern: "ABAB"
LPS: [0, 0, 1, 2]

Pattern "ABAB" at position 1 fails:
- Use LPS value to skip comparisons
- Jump to position 2 instead of 1
```

---

### 2. **Boyer-Moore Algorithm**
**Category:** String Matching
**Time:** O(n/m) best, O(nm) worst | **Space:** O(m)

**Better than KMP for large alphabets (text editors, DNA)**

```
Uses 2 heuristics:
1. Bad character: Skip when mismatch found
2. Good suffix: Use matched part to skip

Searches right-to-left in pattern
```

---

### 3. **Rabin-Karp Algorithm**
**Category:** String Matching (Hashing)
**Time:** O(n + m) average | **Space:** O(1)

```
Algorithm:
1. Compute hash of pattern
2. Slide window computing hash of substrings
3. Compare hashes (and verify on match)

Uses rolling hash to achieve O(1) window updates:
- Remove first char value
- Add new char value
```

---

### 4. **Z-Algorithm**
**Category:** String Matching
**Time:** O(n + m) | **Space:** O(n + m)

```
Algorithm:
1. Compute Z-array: Z[i] = length of longest substring
   starting from i which is also prefix
2. For pattern matching: Concatenate pattern + "$" + text
3. Find Z values ≥ pattern length
```

---

### 5. **Longest Common Subsequence (LCS)**
**Category:** Dynamic Programming
**Time:** O(m * n) | **Space:** O(m * n)

```
Algorithm (DP):
dp[i][j] = length of LCS of s1[0..i-1] and s2[0..j-1]

If s1[i-1] == s2[j-1]: dp[i][j] = dp[i-1][j-1] + 1
Else: dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

---

### 6. **Longest Palindromic Substring**
**Category:** Dynamic Programming / Expand Around Center
**Time:** O(n²) | **Space:** O(1) or O(n²)

```
Approach 1: Expand Around Center O(n²) time, O(1) space
- For each possible center, expand outward
- Track maximum palindrome found

Approach 2: DP O(n²) time, O(n²) space
- dp[i][j] = true if s[i..j] is palindrome
- Fill table checking s[i] == s[j] and dp[i+1][j-1]
```

---

## NUMBER THEORY

### 1. **Greatest Common Divisor (GCD)**
**Time:** O(log(min(a,b))) | **Space:** O(1)

```
Euclidean Algorithm:
gcd(a, b) = gcd(b, a % b)
Base: gcd(a, 0) = a

Recursive:
gcd(12, 8) → gcd(8, 4) → gcd(4, 0) → 4
```

---

### 2. **Extended Euclidean Algorithm**
**Finds:** x, y such that ax + by = gcd(a, b)

```
Recursive approach:
1. Find gcd and coefficients simultaneously
2. Backtrack to calculate x, y values
```

---

### 3. **Sieve of Eratosthenes**
**Category:** Prime Sieve
**Time:** O(n log log n) | **Space:** O(n)

```
Algorithm:
1. Mark all numbers as prime
2. For each prime p ≤ √n:
   - Mark all multiples of p as composite
3. Remaining marked numbers are prime

Optimizations:
- Start marking from p²
- Only check odd numbers
- Use bitset for space efficiency
```

---

### 4. **Modular Arithmetic**
**Important for:** Competitive programming, cryptography

```
Key Formulas:
(a + b) mod m = ((a mod m) + (b mod m)) mod m
(a * b) mod m = ((a mod m) * (b mod m)) mod m
a^b mod m = use fast exponentiation

Fast Exponentiation:
a^b mod m computed in O(log b) time
```

---

## GREEDY ALGORITHMS

### 1. **Activity Selection Problem**
**Time:** O(n log n) | **Space:** O(1)

```
Problem: Select maximum non-overlapping activities

Algorithm:
1. Sort activities by end time
2. Select first activity
3. For each activity:
   - If it starts after previous ends, select it

Greedy choice: Always pick earliest ending activity
```

---

### 2. **Huffman Coding**
**Category:** Optimal Prefix Codes
**Time:** O(n log n) | **Space:** O(n)

```
Algorithm:
1. Create leaf node for each character
2. Build min-heap by frequency
3. While heap > 1:
   - Extract 2 nodes with min frequency
   - Create parent with sum frequency
   - Add parent back to heap
4. Remaining node is root

Generates variable-length codes based on frequency
```

---

### 3. **Interval Scheduling**
**Time:** O(n log n) | **Space:** O(1)

```
Variants:
1. Non-overlapping intervals: Sort by end time
2. Weighted intervals: Use DP + sorting
3. Interval partitioning: Greedy with priority queue
```

---

## IMPORTANT PATTERNS

### 1. **Sliding Window**
**Use For:** Subarray problems with window property

```
Pattern:
1. Define window boundaries (left, right)
2. Expand window (move right) to include elements
3. Shrink window (move left) to exclude elements
4. Track answer at each step

Problems:
- Longest substring without repeating
- Maximum subarray of size k
- Minimum window substring
```

### 2. **Two Pointers**
**Use For:** Sorted arrays, linked lists, palindromes

```
Pattern:
1. Initialize pointers at start/end or start/start
2. Move pointers based on condition
3. Continue until they meet

Problems:
- Two sum in sorted array
- Container with most water
- Reverse string
```

### 3. **Union-Find (Disjoint Set)**
**Use For:** Connected components, cycle detection

```
Operations:
- find(x): Find parent with path compression O(α(n))
- union(x, y): Join sets with union by rank O(α(n))

Problems:
- Number of islands
- Detect cycle in graph
- Kruskal's MST
```

### 4. **Segment Tree / Fenwick Tree**
**Use For:** Range queries and updates

```
Segment Tree:
- Range min/max/sum: O(log n) per query
- Space: O(n)

Fenwick Tree:
- Prefix sum updates: O(log n)
- Space: O(n)
```

### 5. **Trie (Prefix Tree)**
**Use For:** Prefix matching, autocomplete, word dictionary

```
Structure:
- Each node has children for each character
- Boolean flag to mark word ends
- O(m) time per operation where m = word length
```

---

## Complexity Cheat Sheet

| Operation | Best | Average | Worst | Space |
|-----------|------|---------|-------|-------|
| Binary Search | O(1) | O(log n) | O(log n) | O(1) |
| Linear Search | O(1) | O(n) | O(n) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| DFS | O(V+E) | O(V+E) | O(V+E) | O(V) |
| BFS | O(V+E) | O(V+E) | O(V+E) | O(V) |
| Dijkstra | O((V+E)logV) | O((V+E)logV) | O((V+E)logV) | O(V) |
| KMP | O(n+m) | O(n+m) | O(n+m) | O(m) |

