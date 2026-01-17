# Dynamic Programming Problems - Complete Solutions Guide

## 1. Fibonacci Number

### Question
Given an integer n, return the nth Fibonacci number.

### Input
```
n = 5
```

### Expected Output
```
5
```

### Explanation
F(0) = 0, F(1) = 1
F(2) = 1, F(3) = 2, F(4) = 3, F(5) = 5

### TypeScript Solution
```typescript
// Memoization approach (Top-down DP)
function fibonacci(n: number, memo: Map<number, number> = new Map()): number {
  if (n <= 1) return n;
  
  if (memo.has(n)) return memo.get(n)!;
  
  const result = fibonacci(n - 1, memo) + fibonacci(n - 2, memo);
  memo.set(n, result);
  return result;
}

// Tabulation approach (Bottom-up DP)
function fibonacciTabulation(n: number): number {
  if (n <= 1) return n;
  
  const dp = [0, 1];
  
  for (let i = 2; i <= n; i++) {
    dp[i] = dp[i - 1] + dp[i - 2];
  }
  
  return dp[n];
}

// Space-optimized approach
function fibonacciOptimized(n: number): number {
  if (n <= 1) return n;
  
  let prev = 0, curr = 1;
  
  for (let i = 2; i <= n; i++) {
    const next = prev + curr;
    prev = curr;
    curr = next;
  }
  
  return curr;
}

// Test cases
console.log(fibonacci(5)); // 5
console.log(fibonacci(10)); // 55
console.log(fibonacciTabulation(5)); // 5
console.log(fibonacciOptimized(5)); // 5
```

**Time Complexity:** O(n) for tabulation, O(2^n) for naive recursion  
**Space Complexity:** O(n) for memoization, O(1) for optimized

---

## 2. Climbing Stairs

### Question
You are climbing a staircase with n steps. Each time you can climb 1 or 2 steps. How many distinct ways can you climb to the top?

### Input
```
n = 3
```

### Expected Output
```
3
```

### Explanation
Climb 1 step + 1 step + 1 step, or 1 step + 2 steps, or 2 steps + 1 step = 3 ways

### TypeScript Solution
```typescript
function climbStairs(n: number): number {
  if (n <= 2) return n;
  
  let prev = 1, curr = 2;
  
  for (let i = 3; i <= n; i++) {
    const next = prev + curr;
    prev = curr;
    curr = next;
  }
  
  return curr;
}

// With DP array
function climbStairsDP(n: number): number {
  if (n <= 2) return n;
  
  const dp = [0, 1, 2];
  
  for (let i = 3; i <= n; i++) {
    dp[i] = dp[i - 1] + dp[i - 2];
  }
  
  return dp[n];
}

// Test cases
console.log(climbStairs(1)); // 1
console.log(climbStairs(2)); // 2
console.log(climbStairs(3)); // 3
console.log(climbStairs(5)); // 8
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) for optimized, O(n) for DP array

---

## 3. House Robber

### Question
Given an array of non-negative integers representing amount in each house, determine maximum amount of money you can rob without robbing two adjacent houses.

### Input
```
nums = [1, 2, 3, 1]
```

### Expected Output
```
4
```

### Explanation
Rob house 1 (1) + house 3 (3) = 4

### TypeScript Solution
```typescript
function rob(nums: number[]): number {
  if (nums.length === 0) return 0;
  if (nums.length === 1) return nums[0];
  
  let prev = 0, curr = 0;
  
  for (const num of nums) {
    const next = Math.max(prev + num, curr);
    prev = curr;
    curr = next;
  }
  
  return curr;
}

// With DP array for clarity
function robDP(nums: number[]): number {
  if (nums.length === 0) return 0;
  
  const dp = [nums[0]];
  
  for (let i = 1; i < nums.length; i++) {
    dp[i] = Math.max(dp[i - 1], (dp[i - 2] || 0) + nums[i]);
  }
  
  return dp[nums.length - 1];
}

// Test cases
console.log(rob([1, 2, 3, 1])); // 4
console.log(rob([2, 7, 9, 3])); // 9 (rob 2 and 9)
console.log(rob([5, 3, 4, 11, 2])); // 16 (rob 5, 4, 2)
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) for optimized, O(n) for DP array

---

## 4. Coin Change

### Question
Given an integer array coins and target amount, return the fewest number of coins needed to make that amount.

### Input
```
coins = [1, 2, 5], amount = 5
```

### Expected Output
```
1
```

### Explanation
Use 1 coin of value 5.

### TypeScript Solution
```typescript
function coinChange(coins: number[], amount: number): number {
  const dp = Array(amount + 1).fill(Infinity);
  dp[0] = 0;
  
  for (let i = 1; i <= amount; i++) {
    for (const coin of coins) {
      if (coin <= i) {
        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
      }
    }
  }
  
  return dp[amount] === Infinity ? -1 : dp[amount];
}

// Test cases
console.log(coinChange([1, 2, 5], 5)); // 1
console.log(coinChange([2], 3)); // -1
console.log(coinChange([10], 10)); // 1
console.log(coinChange([1, 3, 4], 6)); // 2 (3 + 3)
```

**Time Complexity:** O(amount × coins.length)  
**Space Complexity:** O(amount)

---

## 5. Longest Increasing Subsequence

### Question
Given an integer array nums, return the length of the longest strictly increasing subsequence.

### Input
```
nums = [10, 9, 2, 5, 3, 7, 101, 18]
```

### Expected Output
```
4
```

### Explanation
The longest increasing subsequence is [2, 3, 7, 101], length 4.

### TypeScript Solution
```typescript
// DP approach - O(n²)
function lengthOfLIS(nums: number[]): number {
  const dp = Array(nums.length).fill(1);
  
  for (let i = 1; i < nums.length; i++) {
    for (let j = 0; j < i; j++) {
      if (nums[j] < nums[i]) {
        dp[i] = Math.max(dp[i], dp[j] + 1);
      }
    }
  }
  
  return Math.max(...dp);
}

// Binary search approach - O(n log n)
function lengthOfLISOptimal(nums: number[]): number {
  const tails: number[] = [];
  
  for (const num of nums) {
    let left = 0, right = tails.length;
    
    while (left < right) {
      const mid = Math.floor((left + right) / 2);
      if (tails[mid] < num) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }
    
    tails[left] = num;
  }
  
  return tails.length;
}

// Test cases
console.log(lengthOfLIS([10, 9, 2, 5, 3, 7, 101, 18])); // 4
console.log(lengthOfLIS([0, 1, 0, 4, 4, 4, 3, 5, 6])); // 5 [0, 1, 3, 5, 6]
console.log(lengthOfLISOptimal([10, 9, 2, 5, 3, 7, 101, 18])); // 4
```

**Time Complexity:** O(n²) DP, O(n log n) binary search  
**Space Complexity:** O(n)

---

## 6. Edit Distance

### Question
Given two strings word1 and word2, return the minimum number of operations to convert word1 to word2.

Operations: insert, delete, replace a character.

### Input
```
word1 = "horse", word2 = "ros"
```

### Expected Output
```
3
```

### Explanation
"horse" → "rorse" (replace h with r) → "rose" (delete r) → "ros" (delete e) = 3 operations

### TypeScript Solution
```typescript
function editDistance(word1: string, word2: string): number {
  const m = word1.length, n = word2.length;
  const dp = Array(m + 1).fill(null).map(() => Array(n + 1).fill(0));
  
  // Base cases
  for (let i = 0; i <= m; i++) dp[i][0] = i;
  for (let j = 0; j <= n; j++) dp[0][j] = j;
  
  // Fill DP table
  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (word1[i - 1] === word2[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1];
      } else {
        dp[i][j] = 1 + Math.min(
          dp[i - 1][j],     // delete
          dp[i][j - 1],     // insert
          dp[i - 1][j - 1]  // replace
        );
      }
    }
  }
  
  return dp[m][n];
}

// Test cases
console.log(editDistance("horse", "ros")); // 3
console.log(editDistance("intention", "execution")); // 5
console.log(editDistance("", "a")); // 1
```

**Time Complexity:** O(m × n)  
**Space Complexity:** O(m × n)

---

## 7. Unique Paths

### Question
A robot is on an m x n grid. It can only move right or down. How many unique paths are there?

### Input
```
m = 3, n = 7
```

### Expected Output
```
28
```

### Explanation
Number of paths from top-left to bottom-right.

### TypeScript Solution
```typescript
function uniquePaths(m: number, n: number): number {
  const dp = Array(m).fill(null).map(() => Array(n).fill(1));
  
  for (let i = 1; i < m; i++) {
    for (let j = 1; j < n; j++) {
      dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
    }
  }
  
  return dp[m - 1][n - 1];
}

// Space-optimized
function uniquePathsOptimized(m: number, n: number): number {
  const dp = Array(n).fill(1);
  
  for (let i = 1; i < m; i++) {
    for (let j = 1; j < n; j++) {
      dp[j] += dp[j - 1];
    }
  }
  
  return dp[n - 1];
}

// Mathematical approach (Combinations)
function uniquePathsMath(m: number, n: number): number {
  const numerator = 1;
  const denominator = 1;
  
  for (let i = 1; i < Math.min(m, n); i++) {
    numerator *= (m + n - 1 - i);
    denominator *= i;
  }
  
  return Math.floor(numerator / denominator);
}

// Test cases
console.log(uniquePaths(3, 7)); // 28
console.log(uniquePaths(3, 2)); // 3
console.log(uniquePaths(1, 1)); // 1
```

**Time Complexity:** O(m × n)  
**Space Complexity:** O(m × n) or O(n) optimized

---

## 8. Maximum Product Subarray

### Question
Given an integer array nums, find the contiguous subarray with the largest product.

### Input
```
nums = [2, 3, -2, 4]
```

### Expected Output
```
6
```

### Explanation
Subarray [2, 3] has the largest product = 6.

### TypeScript Solution
```typescript
function maxProduct(nums: number[]): number {
  let maxCurr = nums[0];
  let minCurr = nums[0];
  let maxGlobal = nums[0];
  
  for (let i = 1; i < nums.length; i++) {
    const [maxPrev, minPrev] = [maxCurr, minCurr];
    
    maxCurr = Math.max(nums[i], nums[i] * maxPrev, nums[i] * minPrev);
    minCurr = Math.min(nums[i], nums[i] * maxPrev, nums[i] * minPrev);
    
    maxGlobal = Math.max(maxGlobal, maxCurr);
  }
  
  return maxGlobal;
}

// Test cases
console.log(maxProduct([2, 3, -2, 4])); // 6
console.log(maxProduct([0, 2])); // 2
console.log(maxProduct([-2])); // -2
console.log(maxProduct([-2, 3, -4])); // 24
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

