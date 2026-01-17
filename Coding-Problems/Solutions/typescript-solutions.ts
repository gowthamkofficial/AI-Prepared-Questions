/**
 * TYPESCRIPT SOLUTIONS FOR FREQUENTLY ASKED INTERVIEW PROBLEMS
 * All solutions include time and space complexity analysis
 */

// ==================== STRING PROBLEMS ====================

/**
 * 1. PALINDROME CHECK
 * Time: O(n) | Space: O(1)
 */
export function isPalindrome(s: string): boolean {
  const clean = s.toLowerCase().replace(/[^a-z0-9]/g, '');
  let left = 0, right = clean.length - 1;
  while (left < right) {
    if (clean[left] !== clean[right]) return false;
    left++;
    right--;
  }
  return true;
}

/**
 * 2. REVERSE STRING
 * Time: O(n) | Space: O(n) for string
 */
export function reverseString(s: string): string {
  return s.split('').reverse().join('');
}

export function reverseStringInPlace(chars: string[]): void {
  let left = 0, right = chars.length - 1;
  while (left < right) {
    [chars[left], chars[right]] = [chars[right], chars[left]];
    left++;
    right--;
  }
}

/**
 * 3. LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS
 * Time: O(n) | Space: O(min(n, charset))
 */
export function lengthOfLongestSubstring(s: string): number {
  const charMap = new Map<string, number>();
  let maxLen = 0, start = 0;
  
  for (let end = 0; end < s.length; end++) {
    const char = s[end];
    if (charMap.has(char) && charMap.get(char)! >= start) {
      start = charMap.get(char)! + 1;
    }
    charMap.set(char, end);
    maxLen = Math.max(maxLen, end - start + 1);
  }
  
  return maxLen;
}

/**
 * 4. VALID PARENTHESES
 * Time: O(n) | Space: O(n)
 */
export function isValidParentheses(s: string): boolean {
  const stack: string[] = [];
  const pairs: Record<string, string> = { ')': '(', ']': '[', '}': '{' };
  
  for (const char of s) {
    if (char in pairs) {
      if (stack.pop() !== pairs[char]) return false;
    } else {
      stack.push(char);
    }
  }
  
  return stack.length === 0;
}

/**
 * 5. LONGEST PALINDROMIC SUBSTRING
 * Time: O(n²) | Space: O(1)
 */
export function longestPalindrome(s: string): string {
  if (s.length < 2) return s;
  
  let start = 0, maxLen = 1;
  
  const expandAroundCenter = (left: number, right: number): number => {
    while (left >= 0 && right < s.length && s[left] === s[right]) {
      left--;
      right++;
    }
    return right - left - 1; // length of palindrome
  };
  
  for (let i = 0; i < s.length; i++) {
    const len1 = expandAroundCenter(i, i); // odd length
    const len2 = expandAroundCenter(i, i + 1); // even length
    const len = Math.max(len1, len2);
    
    if (len > maxLen) {
      maxLen = len;
      start = i - Math.floor((len - 1) / 2);
    }
  }
  
  return s.substring(start, start + maxLen);
}

/**
 * 6. ANAGRAM CHECK
 * Time: O(n) | Space: O(1) - 26 letters max
 */
export function isAnagram(s: string, t: string): boolean {
  if (s.length !== t.length) return false;
  
  const count = new Map<string, number>();
  for (const char of s) {
    count.set(char, (count.get(char) ?? 0) + 1);
  }
  
  for (const char of t) {
    if (!count.has(char)) return false;
    count.set(char, count.get(char)! - 1);
    if (count.get(char) === 0) count.delete(char);
  }
  
  return count.size === 0;
}

/**
 * 7. GROUP ANAGRAMS
 * Time: O(n * k log k) where k is max string length | Space: O(n * k)
 */
export function groupAnagrams(strs: string[]): string[][] {
  const map = new Map<string, string[]>();
  
  for (const str of strs) {
    const sorted = str.split('').sort().join('');
    if (!map.has(sorted)) map.set(sorted, []);
    map.get(sorted)!.push(str);
  }
  
  return Array.from(map.values());
}

/**
 * 8. LONGEST COMMON PREFIX
 * Time: O(S * n) where S is sum of all characters | Space: O(1)
 */
export function longestCommonPrefix(strs: string[]): string {
  if (strs.length === 0) return '';
  
  for (let i = 0; i < strs[0].length; i++) {
    const char = strs[0][i];
    for (let j = 1; j < strs.length; j++) {
      if (i >= strs[j].length || strs[j][i] !== char) {
        return strs[0].substring(0, i);
      }
    }
  }
  
  return strs[0];
}

/**
 * 9. STRING COMPRESSION
 * Time: O(n) | Space: O(1) if modifying in place
 */
export function compressString(s: string): string {
  let compressed = '';
  let count = 1;
  
  for (let i = 0; i < s.length; i++) {
    if (i + 1 >= s.length || s[i] !== s[i + 1]) {
      compressed += s[i] + count;
      count = 1;
    } else {
      count++;
    }
  }
  
  return compressed.length < s.length ? compressed : s;
}

// ==================== ARRAY PROBLEMS ====================

/**
 * 11. TWO SUM
 * Time: O(n) | Space: O(n)
 */
export function twoSum(nums: number[], target: number): number[] {
  const map = new Map<number, number>();
  
  for (let i = 0; i < nums.length; i++) {
    const complement = target - nums[i];
    if (map.has(complement)) {
      return [map.get(complement)!, i];
    }
    map.set(nums[i], i);
  }
  
  return [];
}

/**
 * 12. THREE SUM
 * Time: O(n²) | Space: O(1) excluding output
 */
export function threeSum(nums: number[]): number[][] {
  nums.sort((a, b) => a - b);
  const result: number[][] = [];
  
  for (let i = 0; i < nums.length - 2; i++) {
    if (nums[i] > 0) break; // optimization
    if (i > 0 && nums[i] === nums[i - 1]) continue; // skip duplicates
    
    let left = i + 1, right = nums.length - 1;
    const target = -nums[i];
    
    while (left < right) {
      const sum = nums[left] + nums[right];
      if (sum === target) {
        result.push([nums[i], nums[left], nums[right]]);
        while (left < right && nums[left] === nums[left + 1]) left++;
        while (left < right && nums[right] === nums[right - 1]) right--;
        left++;
        right--;
      } else if (sum < target) {
        left++;
      } else {
        right--;
      }
    }
  }
  
  return result;
}

/**
 * 13. MERGE SORTED ARRAYS
 * Time: O(n + m) | Space: O(n + m)
 */
export function mergeSortedArrays(arr1: number[], arr2: number[]): number[] {
  const result: number[] = [];
  let i = 0, j = 0;
  
  while (i < arr1.length && j < arr2.length) {
    if (arr1[i] <= arr2[j]) {
      result.push(arr1[i++]);
    } else {
      result.push(arr2[j++]);
    }
  }
  
  return result.concat(arr1.slice(i)).concat(arr2.slice(j));
}

/**
 * 14. REMOVE DUPLICATES FROM SORTED ARRAY
 * Time: O(n) | Space: O(1)
 */
export function removeDuplicates(nums: number[]): number {
  if (nums.length === 0) return 0;
  
  let j = 0;
  for (let i = 1; i < nums.length; i++) {
    if (nums[i] !== nums[j]) {
      j++;
      nums[j] = nums[i];
    }
  }
  
  return j + 1;
}

/**
 * 15. ROTATE ARRAY
 * Time: O(n) | Space: O(1)
 */
export function rotateArray(nums: number[], k: number): void {
  k = k % nums.length;
  
  const reverse = (start: number, end: number) => {
    while (start < end) {
      [nums[start], nums[end]] = [nums[end], nums[start]];
      start++;
      end--;
    }
  };
  
  reverse(0, nums.length - 1);
  reverse(0, k - 1);
  reverse(k, nums.length - 1);
}

/**
 * 16. CONTAINER WITH MOST WATER
 * Time: O(n) | Space: O(1)
 */
export function maxArea(height: number[]): number {
  let maxA = 0;
  let left = 0, right = height.length - 1;
  
  while (left < right) {
    const width = right - left;
    const currentHeight = Math.min(height[left], height[right]);
    maxA = Math.max(maxA, width * currentHeight);
    
    if (height[left] < height[right]) {
      left++;
    } else {
      right--;
    }
  }
  
  return maxA;
}

/**
 * 17. BEST TIME TO BUY AND SELL STOCK
 * Time: O(n) | Space: O(1)
 */
export function maxProfit(prices: number[]): number {
  let minPrice = prices[0], maxProfit = 0;
  
  for (let i = 1; i < prices.length; i++) {
    const profit = prices[i] - minPrice;
    maxProfit = Math.max(maxProfit, profit);
    minPrice = Math.min(minPrice, prices[i]);
  }
  
  return maxProfit;
}

/**
 * 18. MAXIMUM SUBARRAY (KADANE'S ALGORITHM)
 * Time: O(n) | Space: O(1)
 */
export function maxSubArray(nums: number[]): number {
  let currentSum = nums[0], maxSum = nums[0];
  
  for (let i = 1; i < nums.length; i++) {
    currentSum = Math.max(nums[i], currentSum + nums[i]);
    maxSum = Math.max(maxSum, currentSum);
  }
  
  return maxSum;
}

/**
 * 19. NEXT PERMUTATION
 * Time: O(n) | Space: O(1)
 */
export function nextPermutation(nums: number[]): void {
  let i = nums.length - 2;
  
  // Find the first decreasing element from the right
  while (i >= 0 && nums[i] >= nums[i + 1]) i--;
  
  if (i >= 0) {
    // Find the smallest element larger than nums[i]
    let j = nums.length - 1;
    while (j > i && nums[j] <= nums[i]) j--;
    
    // Swap
    [nums[i], nums[j]] = [nums[j], nums[i]];
  }
  
  // Reverse from i + 1 to end
  let left = i + 1, right = nums.length - 1;
  while (left < right) {
    [nums[left], nums[right]] = [nums[right], nums[left]];
    left++;
    right--;
  }
}

/**
 * 20. SEARCH IN ROTATED SORTED ARRAY
 * Time: O(log n) | Space: O(1)
 */
export function searchInRotatedArray(nums: number[], target: number): number {
  let left = 0, right = nums.length - 1;
  
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    
    if (nums[mid] === target) return mid;
    
    if (nums[left] <= nums[mid]) {
      // Left half is sorted
      if (target >= nums[left] && target < nums[mid]) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    } else {
      // Right half is sorted
      if (target > nums[mid] && target <= nums[right]) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
  }
  
  return -1;
}

/**
 * 21. FIRST MISSING POSITIVE
 * Time: O(n) | Space: O(1)
 */
export function firstMissingPositive(nums: number[]): number {
  // Place each positive number in its correct position
  for (let i = 0; i < nums.length; i++) {
    while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] !== nums[i]) {
      [nums[nums[i] - 1], nums[i]] = [nums[i], nums[nums[i] - 1]];
    }
  }
  
  // Find first position where number doesn't match index
  for (let i = 0; i < nums.length; i++) {
    if (nums[i] !== i + 1) return i + 1;
  }
  
  return nums.length + 1;
}

/**
 * 22. TRAPPING RAIN WATER
 * Time: O(n) | Space: O(1) - Two pointer approach
 */
export function trap(height: number[]): number {
  let left = 0, right = height.length - 1;
  let leftMax = 0, rightMax = 0;
  let water = 0;
  
  while (left < right) {
    if (height[left] < height[right]) {
      if (height[left] >= leftMax) {
        leftMax = height[left];
      } else {
        water += leftMax - height[left];
      }
      left++;
    } else {
      if (height[right] >= rightMax) {
        rightMax = height[right];
      } else {
        water += rightMax - height[right];
      }
      right--;
    }
  }
  
  return water;
}

// ==================== LINKED LIST PROBLEMS ====================

class ListNode {
  val: number;
  next: ListNode | null = null;
  
  constructor(val: number = 0) {
    this.val = val;
  }
}

/**
 * 23. REVERSE LINKED LIST
 * Time: O(n) | Space: O(1) iterative, O(n) recursive
 */
export function reverseList(head: ListNode | null): ListNode | null {
  let prev: ListNode | null = null;
  let curr = head;
  
  while (curr) {
    const next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
  }
  
  return prev;
}

/**
 * 24. MERGE TWO SORTED LISTS
 * Time: O(n + m) | Space: O(1)
 */
export function mergeTwoLists(
  list1: ListNode | null,
  list2: ListNode | null
): ListNode | null {
  const dummy = new ListNode(0);
  let current = dummy;
  
  while (list1 && list2) {
    if (list1.val <= list2.val) {
      current.next = list1;
      list1 = list1.next;
    } else {
      current.next = list2;
      list2 = list2.next;
    }
    current = current.next;
  }
  
  current.next = list1 || list2;
  
  return dummy.next;
}

/**
 * 25. LINKED LIST CYCLE DETECTION (FLOYD'S ALGORITHM)
 * Time: O(n) | Space: O(1)
 */
export function hasCycle(head: ListNode | null): boolean {
  let slow = head, fast = head;
  
  while (fast && fast.next) {
    slow = slow!.next;
    fast = fast.next.next;
    if (slow === fast) return true;
  }
  
  return false;
}

/**
 * 26. MIDDLE OF LINKED LIST
 * Time: O(n) | Space: O(1)
 */
export function middleOfList(head: ListNode | null): ListNode | null {
  let slow = head, fast = head;
  
  while (fast && fast.next) {
    slow = slow!.next;
    fast = fast.next.next;
  }
  
  return slow;
}

/**
 * 27. REMOVE NTH NODE FROM END
 * Time: O(n) | Space: O(1)
 */
export function removeNthFromEnd(head: ListNode | null, n: number): ListNode | null {
  const dummy = new ListNode(0);
  dummy.next = head;
  let first: ListNode | null = dummy, second: ListNode | null = dummy;
  
  // Move first pointer n+1 steps ahead
  for (let i = 0; i <= n; i++) {
    if (first) first = first.next;
  }
  
  // Move both pointers until first reaches end
  while (first) {
    first = first.next;
    second = second ? second.next : null;
  }
  
  if (second && second.next) {
    second.next = second.next.next;
  }
  
  return dummy.next;
}

/**
 * 30. PALINDROME LINKED LIST
 * Time: O(n) | Space: O(1)
 */
export function isPalindromeList(head: ListNode | null): boolean {
  if (!head || !head.next) return true;
  
  // Find middle
  let slow: ListNode | null = head, fast: ListNode | null = head;
  while (fast && fast.next) {
    slow = slow ? slow.next : null;
    fast = fast.next ? fast.next.next : null;
  }
  
  // Reverse second half
  let prev: ListNode | null = null;
  let curr: ListNode | null = slow;
  while (curr) {
    const next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
  }
  
  // Compare
  let left: ListNode | null = head, right: ListNode | null = prev;
  while (left && right) {
    if (left.val !== right.val) return false;
    left = left.next;
    right = right.next;
  }
  
  return true;
}

// ==================== TREE PROBLEMS ====================

interface TreeNode {
  val: number;
  left: TreeNode | null;
  right: TreeNode | null;
}

/**
 * 31. BINARY TREE TRAVERSALS
 * Time: O(n) | Space: O(h) where h is height
 */
export function inorderTraversal(root: TreeNode | null): number[] {
  const result: number[] = [];
  
  const traverse = (node: TreeNode | null) => {
    if (!node) return;
    traverse(node.left);
    result.push(node.val);
    traverse(node.right);
  };
  
  traverse(root);
  return result;
}

export function preorderTraversal(root: TreeNode | null): number[] {
  const result: number[] = [];
  
  const traverse = (node: TreeNode | null) => {
    if (!node) return;
    result.push(node.val);
    traverse(node.left);
    traverse(node.right);
  };
  
  traverse(root);
  return result;
}

export function postorderTraversal(root: TreeNode | null): number[] {
  const result: number[] = [];
  
  const traverse = (node: TreeNode | null) => {
    if (!node) return;
    traverse(node.left);
    traverse(node.right);
    result.push(node.val);
  };
  
  traverse(root);
  return result;
}

/**
 * 32. LEVEL ORDER TRAVERSAL (BFS)
 * Time: O(n) | Space: O(w) where w is max width
 */
export function levelOrderTraversal(root: TreeNode | null): number[][] {
  if (!root) return [];
  
  const result: number[][] = [];
  const queue: TreeNode[] = [root];
  
  while (queue.length > 0) {
    const levelSize = queue.length;
    const currentLevel: number[] = [];
    
    for (let i = 0; i < levelSize; i++) {
      const node = queue.shift()!;
      currentLevel.push(node.val);
      if (node.left) queue.push(node.left);
      if (node.right) queue.push(node.right);
    }
    
    result.push(currentLevel);
  }
  
  return result;
}

/**
 * 33. MAXIMUM DEPTH OF BINARY TREE
 * Time: O(n) | Space: O(h)
 */
export function maxDepth(root: TreeNode | null): number {
  if (!root) return 0;
  return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}

/**
 * 34. LOWEST COMMON ANCESTOR
 * Time: O(n) | Space: O(h)
 */
export function lowestCommonAncestor(
  root: TreeNode | null,
  p: TreeNode,
  q: TreeNode
): TreeNode | null {
  if (!root) return null;
  if (root.val === p.val || root.val === q.val) return root;
  
  const left = lowestCommonAncestor(root.left, p, q);
  const right = lowestCommonAncestor(root.right, p, q);
  
  if (left && right) return root;
  return left || right;
}

/**
 * 36. VALIDATE BINARY SEARCH TREE
 * Time: O(n) | Space: O(h)
 */
export function isValidBST(root: TreeNode | null): boolean {
  const validate = (node: TreeNode | null, min: number, max: number): boolean => {
    if (!node) return true;
    if (node.val <= min || node.val >= max) return false;
    
    return (
      validate(node.left, min, node.val) &&
      validate(node.right, node.val, max)
    );
  };
  
  return validate(root, -Infinity, Infinity);
}

/**
 * 37. PATH SUM
 * Time: O(n) | Space: O(h)
 */
export function hasPathSum(root: TreeNode | null, targetSum: number): boolean {
  if (!root) return false;
  
  if (!root.left && !root.right) return root.val === targetSum;
  
  return (
    hasPathSum(root.left, targetSum - root.val) ||
    hasPathSum(root.right, targetSum - root.val)
  );
}

/**
 * 39. DIAMETER OF BINARY TREE
 * Time: O(n) | Space: O(h)
 */
export function diameterOfBinaryTree(root: TreeNode | null): number {
  let diameter = 0;
  
  const dfs = (node: TreeNode | null): number => {
    if (!node) return 0;
    
    const left = dfs(node.left);
    const right = dfs(node.right);
    
    diameter = Math.max(diameter, left + right);
    
    return 1 + Math.max(left, right);
  };
  
  dfs(root);
  return diameter;
}

/**
 * 40. BALANCED BINARY TREE
 * Time: O(n) | Space: O(h)
 */
export function isBalanced(root: TreeNode | null): boolean {
  const checkBalance = (node: TreeNode | null): [boolean, number] => {
    if (!node) return [true, 0];
    
    const [leftBalanced, leftHeight] = checkBalance(node.left);
    if (!leftBalanced) return [false, 0];
    
    const [rightBalanced, rightHeight] = checkBalance(node.right);
    if (!rightBalanced) return [false, 0];
    
    return [Math.abs(leftHeight - rightHeight) <= 1, 1 + Math.max(leftHeight, rightHeight)];
  };
  
  return checkBalance(root)[0];
}

/**
 * 41. SYMMETRIC TREE
 * Time: O(n) | Space: O(h)
 */
export function isSymmetric(root: TreeNode | null): boolean {
  const isMirror = (left: TreeNode | null, right: TreeNode | null): boolean => {
    if (!left && !right) return true;
    if (!left || !right) return false;
    
    return (
      left.val === right.val &&
      isMirror(left.left, right.right) &&
      isMirror(left.right, right.left)
    );
  };
  
  return isMirror(root, root);
}

// ==================== DYNAMIC PROGRAMMING ====================

/**
 * 51. FIBONACCI NUMBER
 * Time: O(n) | Space: O(n) with memoization
 */
export function fib(n: number, memo: Map<number, number> = new Map()): number {
  if (n <= 1) return n;
  if (memo.has(n)) return memo.get(n)!;
  
  const result = fib(n - 1, memo) + fib(n - 2, memo);
  memo.set(n, result);
  
  return result;
}

/**
 * 52. CLIMBING STAIRS
 * Time: O(n) | Space: O(n)
 */
export function climbStairs(n: number): number {
  if (n <= 2) return n;
  
  const dp: number[] = [0, 1, 2];
  
  for (let i = 3; i <= n; i++) {
    dp[i] = dp[i - 1] + dp[i - 2];
  }
  
  return dp[n];
}

/**
 * 53. HOUSE ROBBER
 * Time: O(n) | Space: O(n)
 */
export function rob(nums: number[]): number {
  if (nums.length === 0) return 0;
  if (nums.length === 1) return nums[0];
  
  const dp: number[] = [nums[0], Math.max(nums[0], nums[1])];
  
  for (let i = 2; i < nums.length; i++) {
    dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
  }
  
  return dp[nums.length - 1];
}

/**
 * 55. COIN CHANGE
 * Time: O(n * amount) | Space: O(amount)
 */
export function coinChange(coins: number[], amount: number): number {
  const dp: number[] = new Array(amount + 1).fill(amount + 1);
  dp[0] = 0;
  
  for (let i = 1; i <= amount; i++) {
    for (const coin of coins) {
      if (coin <= i) {
        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
      }
    }
  }
  
  return dp[amount] > amount ? -1 : dp[amount];
}

/**
 * 57. LONGEST INCREASING SUBSEQUENCE
 * Time: O(n log n) with binary search | Space: O(n)
 */
export function lengthOfLIS(nums: number[]): number {
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

/**
 * 58. EDIT DISTANCE
 * Time: O(m * n) | Space: O(m * n)
 */
export function editDistance(word1: string, word2: string): number {
  const m = word1.length, n = word2.length;
  const dp: number[][] = Array(m + 1).fill(null).map(() => Array(n + 1).fill(0));
  
  for (let i = 0; i <= m; i++) dp[i][0] = i;
  for (let j = 0; j <= n; j++) dp[0][j] = j;
  
  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (word1[i - 1] === word2[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1];
      } else {
        dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
      }
    }
  }
  
  return dp[m][n];
}

/**
 * 60. UNIQUE PATHS
 * Time: O(m * n) | Space: O(m * n)
 */
export function uniquePaths(m: number, n: number): number {
  const dp: number[][] = Array(m).fill(null).map(() => Array(n).fill(1));
  
  for (let i = 1; i < m; i++) {
    for (let j = 1; j < n; j++) {
      dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
    }
  }
  
  return dp[m - 1][n - 1];
}

/**
 * 61. MAXIMUM PRODUCT SUBARRAY
 * Time: O(n) | Space: O(1)
 */
export function maxProduct(nums: number[]): number {
  let maxProd = nums[0], minProd = nums[0], result = nums[0];
  
  for (let i = 1; i < nums.length; i++) {
    const temp = maxProd;
    maxProd = Math.max(nums[i], nums[i] * maxProd, nums[i] * minProd);
    minProd = Math.min(nums[i], nums[i] * temp, nums[i] * minProd);
    result = Math.max(result, maxProd);
  }
  
  return result;
}

// ==================== SORTING ====================

/**
 * 65. MERGE SORT
 * Time: O(n log n) | Space: O(n)
 */
export function mergeSort(arr: number[]): number[] {
  if (arr.length <= 1) return arr;
  
  const mid = Math.floor(arr.length / 2);
  const left = mergeSort(arr.slice(0, mid));
  const right = mergeSort(arr.slice(mid));
  
  return merge(left, right);
}

function merge(left: number[], right: number[]): number[] {
  const result: number[] = [];
  let i = 0, j = 0;
  
  while (i < left.length && j < right.length) {
    if (left[i] <= right[j]) {
      result.push(left[i++]);
    } else {
      result.push(right[j++]);
    }
  }
  
  return result.concat(left.slice(i)).concat(right.slice(j));
}

/**
 * 66. QUICK SORT
 * Time: O(n log n) average, O(n²) worst | Space: O(log n)
 */
export function quickSort(arr: number[], low: number = 0, high: number = arr.length - 1): number[] {
  if (low < high) {
    const pi = partition(arr, low, high);
    quickSort(arr, low, pi - 1);
    quickSort(arr, pi + 1, high);
  }
  return arr;
}

function partition(arr: number[], low: number, high: number): number {
  const pivot = arr[high];
  let i = low - 1;
  
  for (let j = low; j < high; j++) {
    if (arr[j] < pivot) {
      i++;
      [arr[i], arr[j]] = [arr[j], arr[i]];
    }
  }
  
  [arr[i + 1], arr[high]] = [arr[high], arr[i + 1]];
  return i + 1;
}

/**
 * 68. BINARY SEARCH
 * Time: O(log n) | Space: O(1)
 */
export function binarySearch(arr: number[], target: number): number {
  let left = 0, right = arr.length - 1;
  
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    if (arr[mid] === target) return mid;
    if (arr[mid] < target) {
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }
  
  return -1;
}

// ==================== HASH MAP PROBLEMS ====================

/**
 * 81. VALID ANAGRAM
 * Already implemented above as isAnagram
 */

/**
 * 82. MAJORITY ELEMENT
 * Time: O(n) | Space: O(1) using Boyer-Moore voting
 */
export function majorityElement(nums: number[]): number {
  let count = 0, candidate = 0;
  
  for (const num of nums) {
    if (count === 0) candidate = num;
    count += num === candidate ? 1 : -1;
  }
  
  return candidate;
}

/**
 * 85. FIRST UNIQUE CHARACTER
 * Time: O(n) | Space: O(1) - 26 letters max
 */
export function firstUniqueChar(s: string): number {
  const count = new Map<string, number>();
  
  for (const char of s) {
    count.set(char, (count.get(char) ?? 0) + 1);
  }
  
  for (let i = 0; i < s.length; i++) {
    if (count.get(s[i]) === 1) return i;
  }
  
  return -1;
}

// ==================== BIT MANIPULATION ====================

/**
 * 89. SINGLE NUMBER (XOR)
 * Time: O(n) | Space: O(1)
 */
export function singleNumber(nums: number[]): number {
  let result = 0;
  for (const num of nums) {
    result ^= num;
  }
  return result;
}

/**
 * 91. NUMBER OF 1 BITS
 * Time: O(1) for 32-bit | Space: O(1)
 */
export function hammingWeight(n: number): number {
  let count = 0;
  while (n) {
    count += n & 1;
    n = n >>> 1;
  }
  return count;
}

/**
 * 92. POWER OF TWO
 * Time: O(1) | Space: O(1)
 */
export function isPowerOfTwo(n: number): boolean {
  return n > 0 && (n & (n - 1)) === 0;
}

/**
 * 93. MISSING NUMBER
 * Time: O(n) | Space: O(1)
 */
export function missingNumber(nums: number[]): number {
  let xor = 0;
  for (let i = 0; i < nums.length; i++) {
    xor ^= i ^ nums[i];
  }
  return xor ^ nums.length;
}

/**
 * 96. HAMMING DISTANCE
 * Time: O(1) | Space: O(1)
 */
export function hammingDistance(x: number, y: number): number {
  let xor = x ^ y;
  let count = 0;
  while (xor) {
    count += xor & 1;
    xor = xor >>> 1;
  }
  return count;
}

// ==================== UTILITY FUNCTIONS ====================

/**
 * Helper: Create linked list from array
 */
export function createLinkedList(arr: number[]): ListNode | null {
  if (arr.length === 0) return null;
  const head = new ListNode(arr[0]);
  let current = head;
  for (let i = 1; i < arr.length; i++) {
    current.next = new ListNode(arr[i]);
    current = current.next;
  }
  return head;
}

/**
 * Helper: Convert linked list to array
 */
export function linkedListToArray(head: ListNode | null): number[] {
  const result: number[] = [];
  let current = head;
  while (current) {
    result.push(current.val);
    current = current.next;
  }
  return result;
}

export { ListNode };
