# Frequently Asked Interview Coding Problems

## Table of Contents
1. [String Problems](#string-problems)
2. [Array Problems](#array-problems)
3. [Linked List Problems](#linked-list-problems)
4. [Tree Problems](#tree-problems)
5. [Graph Problems](#graph-problems)
6. [Dynamic Programming](#dynamic-programming)
7. [Sorting & Searching](#sorting--searching)
8. [Stack & Queue](#stack--queue)
9. [Hash Table/Map](#hash-tablemapmap)
10. [Bit Manipulation](#bit-manipulation)

---

## String Problems

### 1. **Palindrome Check**
**Difficulty:** Easy
**Tags:** String, Two Pointers
- Check if a given string is a palindrome
- Ignore spaces, punctuation, and case
- Example: "A man, a plan, a canal: Panama" → true

### 2. **Reverse String**
**Difficulty:** Easy
**Tags:** String, Array
- Reverse a string without using built-in reverse function
- Handle unicode characters properly

### 3. **Longest Substring Without Repeating Characters**
**Difficulty:** Medium
**Tags:** String, Sliding Window
- Find the longest substring without repeating characters
- Example: "abcabcbb" → 3 ("abc")

### 4. **Valid Parentheses**
**Difficulty:** Easy
**Tags:** String, Stack
- Check if parentheses are balanced
- Handle multiple types: (), [], {}

### 5. **Longest Palindromic Substring**
**Difficulty:** Medium
**Tags:** String, Dynamic Programming, Expand Around Center
- Find the longest palindrome substring in a string
- Time complexity should be O(n²)

### 6. **Anagram Check**
**Difficulty:** Easy
**Tags:** String, Hash Map
- Check if two strings are anagrams
- Example: "listen" and "silent" → true

### 7. **Group Anagrams**
**Difficulty:** Medium
**Tags:** String, Hash Map, Sorting
- Group all anagrams together from a list of strings

### 8. **Longest Common Prefix**
**Difficulty:** Easy
**Tags:** String, Array
- Find the longest common prefix among multiple strings

### 9. **String Compression**
**Difficulty:** Easy
**Tags:** String, Two Pointers
- Compress string with counts (e.g., "aabcccccc" → "a2b1c5")

### 10. **Word Ladder**
**Difficulty:** Hard
**Tags:** String, Graph, BFS
- Find shortest sequence of transformations between words

---

## Array Problems

### 11. **Two Sum**
**Difficulty:** Easy
**Tags:** Array, Hash Map
- Find two numbers that add up to target
- Example: [2,7,11,15], target=9 → [0,1]

### 12. **Three Sum**
**Difficulty:** Medium
**Tags:** Array, Two Pointers, Sorting
- Find all unique triplets that sum to target

### 13. **Merge Sorted Arrays**
**Difficulty:** Easy
**Tags:** Array, Merge, Two Pointers
- Merge two sorted arrays into one sorted array

### 14. **Remove Duplicates from Sorted Array**
**Difficulty:** Easy
**Tags:** Array, Two Pointers
- Remove duplicates in-place and return unique count

### 15. **Rotate Array**
**Difficulty:** Medium
**Tags:** Array, Rotation
- Rotate array to the right by k steps
- Example: [1,2,3,4,5], k=2 → [4,5,1,2,3]

### 16. **Container With Most Water**
**Difficulty:** Medium
**Tags:** Array, Two Pointers, Greedy
- Find two lines that can hold the most water

### 17. **Best Time to Buy and Sell Stock**
**Difficulty:** Easy
**Tags:** Array, Tracking Min
- Find maximum profit from buying and selling once

### 18. **Maximum Subarray (Kadane's Algorithm)**
**Difficulty:** Easy
**Tags:** Array, Dynamic Programming, Greedy
- Find contiguous subarray with maximum sum

### 19. **Next Permutation**
**Difficulty:** Medium
**Tags:** Array, Two Pointers
- Find next lexicographically greater permutation

### 20. **Search in Rotated Sorted Array**
**Difficulty:** Medium
**Tags:** Array, Binary Search
- Search element in rotated sorted array (O(log n))

### 21. **First Missing Positive**
**Difficulty:** Hard
**Tags:** Array, Hashing
- Find smallest missing positive integer

### 22. **Trapping Rain Water**
**Difficulty:** Hard
**Tags:** Array, Two Pointers, Dynamic Programming
- Calculate water trapped between elevation heights

---

## Linked List Problems

### 23. **Reverse Linked List**
**Difficulty:** Easy
**Tags:** Linked List, Recursion, Iteration
- Reverse a linked list iteratively or recursively

### 24. **Merge Two Sorted Lists**
**Difficulty:** Easy
**Tags:** Linked List, Merge
- Merge two sorted linked lists

### 25. **Linked List Cycle Detection**
**Difficulty:** Easy
**Tags:** Linked List, Floyd's Cycle Detection
- Detect if a linked list has a cycle (Floyd's Algorithm)

### 26. **Middle of Linked List**
**Difficulty:** Easy
**Tags:** Linked List, Slow-Fast Pointers
- Find middle node using slow and fast pointers

### 27. **Remove Nth Node From End**
**Difficulty:** Medium
**Tags:** Linked List, Two Pointers
- Remove nth node from the end of the list

### 28. **Add Two Numbers (Linked List)**
**Difficulty:** Medium
**Tags:** Linked List, Math
- Add two numbers represented as linked lists

### 29. **Reorder Linked List**
**Difficulty:** Medium
**Tags:** Linked List, Two Pointers
- Reorder list as: L0 → Ln → L1 → Ln-1 → ...

### 30. **Palindrome Linked List**
**Difficulty:** Easy
**Tags:** Linked List, Two Pointers, Stack
- Check if a linked list is a palindrome

---

## Tree Problems

### 31. **Binary Tree Traversals (In/Pre/Post Order)**
**Difficulty:** Easy
**Tags:** Tree, DFS, Recursion
- Implement inorder, preorder, postorder traversals

### 32. **Level Order Traversal (BFS)**
**Difficulty:** Easy
**Tags:** Tree, BFS, Queue
- Traverse tree level by level

### 33. **Maximum Depth of Binary Tree**
**Difficulty:** Easy
**Tags:** Tree, DFS, Recursion
- Find height of binary tree

### 34. **Lowest Common Ancestor (LCA)**
**Difficulty:** Medium
**Tags:** Tree, DFS, Binary Search Tree
- Find LCA in binary search tree or binary tree

### 35. **Serialize and Deserialize Binary Tree**
**Difficulty:** Hard
**Tags:** Tree, DFS, BFS
- Convert tree to string and back

### 36. **Validate Binary Search Tree**
**Difficulty:** Medium
**Tags:** Tree, BST, DFS
- Verify if tree is a valid binary search tree

### 37. **Path Sum**
**Difficulty:** Easy
**Tags:** Tree, DFS, Backtracking
- Check if path from root to leaf equals target sum

### 38. **Binary Tree Right Side View**
**Difficulty:** Medium
**Tags:** Tree, BFS, DFS
- Get rightmost node at each level

### 39. **Diameter of Binary Tree**
**Difficulty:** Easy
**Tags:** Tree, DFS
- Find longest path between any two nodes

### 40. **Balanced Binary Tree**
**Difficulty:** Easy
**Tags:** Tree, DFS
- Check if binary tree is height-balanced

### 41. **Symmetric Tree**
**Difficulty:** Easy
**Tags:** Tree, DFS, Recursion
- Check if tree is mirror of itself

### 42. **Construct Tree from Traversals**
**Difficulty:** Medium
**Tags:** Tree, Recursion, Hashing
- Build tree from inorder and preorder/postorder

---

## Graph Problems

### 43. **Number of Islands**
**Difficulty:** Medium
**Tags:** Graph, DFS, BFS
- Count disconnected islands in grid

### 44. **Clone Graph**
**Difficulty:** Medium
**Tags:** Graph, DFS, BFS, Hashing
- Deep clone an undirected graph

### 45. **Course Schedule**
**Difficulty:** Medium
**Tags:** Graph, Topological Sort, DFS
- Detect cycle in directed graph (course dependencies)

### 46. **Surrounded Regions**
**Difficulty:** Medium
**Tags:** Graph, DFS, Union-Find
- Capture regions surrounded by 'X'

### 47. **Alien Dictionary**
**Difficulty:** Hard
**Tags:** Graph, Topological Sort
- Determine order of characters in alien language

### 48. **Word Ladder**
**Difficulty:** Hard
**Tags:** Graph, BFS
- Shortest transformation path between words

### 49. **Pacific Atlantic Water Flow**
**Difficulty:** Medium
**Tags:** Graph, DFS
- Find cells from which water flows to both oceans

### 50. **Minimum Height Trees**
**Difficulty:** Medium
**Tags:** Graph, Topological Sort, BFS
- Find minimum height trees in a graph

---

## Dynamic Programming

### 51. **Fibonacci Number**
**Difficulty:** Easy
**Tags:** DP, Recursion, Memoization
- Calculate nth Fibonacci number efficiently

### 52. **Climbing Stairs**
**Difficulty:** Easy
**Tags:** DP
- Number of ways to climb n stairs (1 or 2 steps)

### 53. **House Robber**
**Difficulty:** Easy
**Tags:** DP
- Maximize money robbed without robbing adjacent houses

### 54. **House Robber II (Circular)**
**Difficulty:** Medium
**Tags:** DP
- Extended version with circular house arrangement

### 55. **Coin Change**
**Difficulty:** Medium
**Tags:** DP, BFS
- Minimum coins to make amount

### 56. **Coin Change II (Combinations)**
**Difficulty:** Medium
**Tags:** DP
- Number of combinations to make amount

### 57. **Longest Increasing Subsequence (LIS)**
**Difficulty:** Medium
**Tags:** DP, Binary Search
- Find length of longest increasing subsequence

### 58. **Edit Distance (Levenshtein Distance)**
**Difficulty:** Hard
**Tags:** DP, String
- Minimum operations to transform one string to another

### 59. **Word Break**
**Difficulty:** Medium
**Tags:** DP, Hashing
- Check if string can be segmented using dictionary

### 60. **Unique Paths**
**Difficulty:** Easy
**Tags:** DP, Combinatorics
- Count paths in grid from top-left to bottom-right

### 61. **Maximum Product Subarray**
**Difficulty:** Medium
**Tags:** DP, Tracking States
- Find maximum product of contiguous subarray

### 62. **0/1 Knapsack**
**Difficulty:** Medium
**Tags:** DP, Optimization
- Maximize value with weight constraint

### 63. **Longest Common Subsequence (LCS)**
**Difficulty:** Medium
**Tags:** DP, String
- Find longest common subsequence of two strings

### 64. **Partition Equal Subset Sum**
**Difficulty:** Medium
**Tags:** DP, Knapsack
- Check if array can be partitioned into equal sum subsets

---

## Sorting & Searching

### 65. **Merge Sort Implementation**
**Difficulty:** Easy
**Tags:** Sorting, Divide & Conquer
- Implement merge sort (O(n log n) time, O(n) space)

### 66. **Quick Sort Implementation**
**Difficulty:** Easy
**Tags:** Sorting, Divide & Conquer
- Implement quick sort with pivot selection

### 67. **Heap Sort Implementation**
**Difficulty:** Medium
**Tags:** Sorting, Heap
- Implement heap sort (O(n log n) time, O(1) space)

### 68. **Binary Search**
**Difficulty:** Easy
**Tags:** Searching, Binary Search
- Implement binary search correctly

### 69. **First and Last Position of Element**
**Difficulty:** Medium
**Tags:** Binary Search
- Find first and last occurrence in sorted array

### 70. **Find Peak Element**
**Difficulty:** Medium
**Tags:** Binary Search
- Find peak element in unsorted array

### 71. **Kth Largest Element**
**Difficulty:** Medium
**Tags:** Heap, QuickSelect
- Find kth largest element efficiently

### 72. **Median of Two Sorted Arrays**
**Difficulty:** Hard
**Tags:** Binary Search
- Find median of two sorted arrays in O(log(min(m,n)))

---

## Stack & Queue

### 73. **Valid Parentheses**
**Difficulty:** Easy
**Tags:** Stack
- Check balanced parentheses using stack

### 74. **Evaluate Reverse Polish Notation**
**Difficulty:** Medium
**Tags:** Stack, Math
- Evaluate expression in postfix notation

### 75. **Daily Temperatures**
**Difficulty:** Medium
**Tags:** Stack, Monotonic Stack
- Find days until warmer temperature using monotonic stack

### 76. **Largest Rectangle in Histogram**
**Difficulty:** Hard
**Tags:** Stack, Monotonic Stack
- Find largest rectangle area in histogram

### 77. **Implement Queue using Stacks**
**Difficulty:** Easy
**Tags:** Queue, Stack, Design
- Implement queue with two stacks

### 78. **LRU Cache**
**Difficulty:** Medium
**Tags:** Design, Hash Map, Doubly Linked List
- Implement LRU cache with O(1) operations

### 79. **Trapping Rain Water II (2D)**
**Difficulty:** Hard
**Tags:** Priority Queue, BFS
- Trap water in 2D elevation map

---

## Hash Table/Map

### 80. **Two Sum**
**Difficulty:** Easy
**Tags:** Hash Map
- Already listed above

### 81. **Valid Anagram**
**Difficulty:** Easy
**Tags:** Hash Map, Sorting
- Check if two strings are anagrams

### 82. **Majority Element**
**Difficulty:** Easy
**Tags:** Hash Map, Boyer-Moore
- Find element appearing more than n/2 times

### 83. **Majority Element II**
**Difficulty:** Medium
**Tags:** Hash Map
- Find all elements appearing more than n/3 times

### 84. **LRU Cache**
**Difficulty:** Medium
**Tags:** Hash Map, Doubly Linked List, Design
- Already listed above

### 85. **First Unique Character**
**Difficulty:** Easy
**Tags:** Hash Map, String
- Find first non-repeating character

### 86. **Intersection of Two Arrays**
**Difficulty:** Easy
**Tags:** Hash Map, Two Pointers
- Find common elements in two arrays

### 87. **Happy Number**
**Difficulty:** Easy
**Tags:** Hash Map, Math
- Determine if number is happy using cycle detection

### 88. **Isomorphic Strings**
**Difficulty:** Easy
**Tags:** Hash Map
- Check if strings follow same pattern

---

## Bit Manipulation

### 89. **Single Number (XOR)**
**Difficulty:** Easy
**Tags:** Bit Manipulation, XOR
- Find single number appearing once in array of duplicates

### 90. **Single Number II**
**Difficulty:** Medium
**Tags:** Bit Manipulation
- Find single number appearing once when others appear 3 times

### 91. **Number of 1 Bits**
**Difficulty:** Easy
**Tags:** Bit Manipulation
- Count number of 1 bits in binary representation

### 92. **Power of Two**
**Difficulty:** Easy
**Tags:** Bit Manipulation
- Check if number is power of 2

### 93. **Missing Number**
**Difficulty:** Easy
**Tags:** Bit Manipulation, XOR, Math
- Find missing number in array using XOR

### 94. **Reverse Bits**
**Difficulty:** Easy
**Tags:** Bit Manipulation
- Reverse bits of a 32-bit integer

### 95. **Sum of Two Integers (without + or -)**
**Difficulty:** Medium
**Tags:** Bit Manipulation
- Add two integers using bitwise operations

### 96. **Hamming Distance**
**Difficulty:** Easy
**Tags:** Bit Manipulation, XOR
- Count differing bits between two numbers

---

## Additional Important Problems

### 97. **Regular Expression Matching**
**Difficulty:** Hard
**Tags:** Dynamic Programming, Backtracking
- Match pattern with '.' and '*' wildcards

### 98. **Word Search**
**Difficulty:** Medium
**Tags:** Backtracking, DFS
- Find word in grid of characters

### 99. **Permutations and Combinations**
**Difficulty:** Medium
**Tags:** Backtracking
- Generate all permutations/combinations

### 100. **N-Queens Problem**
**Difficulty:** Hard
**Tags:** Backtracking, Constraint Satisfaction
- Place n queens on chessboard without conflicts

---

## Interview Tips

✅ **Before Coding:**
- Understand the problem completely
- Ask clarifying questions
- Discuss approach and time/space complexity
- Consider edge cases

✅ **While Coding:**
- Write clean, readable code
- Explain your thought process
- Handle edge cases
- Test with examples

✅ **Optimization Focus:**
- Brute force → Optimized approaches
- Understand trade-offs (Time vs Space)
- Use appropriate data structures
- Recognize common patterns (sliding window, two pointers, etc.)

✅ **Time Complexity Targets:**
- O(1) - Constant
- O(log n) - Binary search
- O(n) - Linear scan
- O(n log n) - Sorting, divide & conquer
- O(n²) - Nested loops
- O(2ⁿ) - Exponential (backtracking)
- O(n!) - Permutations

