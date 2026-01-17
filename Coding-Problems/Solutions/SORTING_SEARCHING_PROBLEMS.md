# Sorting & Searching Problems - Complete Solutions Guide

## 1. Merge Sort

### Question
Implement merge sort to sort an array in ascending order.

### Input
```
nums = [38, 27, 43, 3, 9, 82, 10]
```

### Expected Output
```
[3, 9, 10, 27, 38, 43, 82]
```

### Explanation
Divide and conquer algorithm that divides array in half, recursively sorts, then merges.

### TypeScript Solution
```typescript
function mergeSort(arr: number[]): number[] {
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
  
  return result.concat(left.slice(i), right.slice(j));
}

// Test cases
console.log(mergeSort([38, 27, 43, 3, 9, 82, 10])); // [3, 9, 10, 27, 38, 43, 82]
console.log(mergeSort([5, 2, 8, 1, 9])); // [1, 2, 5, 8, 9]
console.log(mergeSort([1])); // [1]
console.log(mergeSort([])); // []
```

**Time Complexity:** O(n log n) - best, average, worst  
**Space Complexity:** O(n) - auxiliary space

---

## 2. Quick Sort

### Question
Implement quick sort to sort an array using partition method.

### Input
```
nums = [10, 7, 8, 9, 1, 5]
```

### Expected Output
```
[1, 5, 7, 8, 9, 10]
```

### Explanation
Pivot-based divide and conquer, partitions around pivot, recursively sorts.

### TypeScript Solution
```typescript
function quickSort(arr: number[], left = 0, right = arr.length - 1): number[] {
  if (left < right) {
    const pi = partition(arr, left, right);
    quickSort(arr, left, pi - 1);
    quickSort(arr, pi + 1, right);
  }
  return arr;
}

function partition(arr: number[], left: number, right: number): number {
  const pivot = arr[right];
  let i = left - 1;
  
  for (let j = left; j < right; j++) {
    if (arr[j] < pivot) {
      i++;
      [arr[i], arr[j]] = [arr[j], arr[i]];
    }
  }
  
  [arr[i + 1], arr[right]] = [arr[right], arr[i + 1]];
  return i + 1;
}

// Test cases
console.log(quickSort([10, 7, 8, 9, 1, 5])); // [1, 5, 7, 8, 9, 10]
console.log(quickSort([3, 6, 8, 10, 1, 2])); // [1, 2, 3, 6, 8, 10]
console.log(quickSort([1])); // [1]
```

**Time Complexity:** O(n log n) average, O(n²) worst case  
**Space Complexity:** O(log n) - recursion stack

---

## 3. Heap Sort

### Question
Implement heap sort to sort an array using a heap data structure.

### Input
```
nums = [12, 11, 13, 5, 6, 7]
```

### Expected Output
```
[5, 6, 7, 11, 12, 13]
```

### Explanation
Build max heap, repeatedly extract max element and heapify.

### TypeScript Solution
```typescript
function heapSort(arr: number[]): number[] {
  const n = arr.length;
  
  // Build max heap
  for (let i = Math.floor(n / 2) - 1; i >= 0; i--) {
    heapify(arr, n, i);
  }
  
  // Extract elements from heap
  for (let i = n - 1; i > 0; i--) {
    [arr[0], arr[i]] = [arr[i], arr[0]];
    heapify(arr, i, 0);
  }
  
  return arr;
}

function heapify(arr: number[], n: number, i: number): void {
  let largest = i;
  const left = 2 * i + 1;
  const right = 2 * i + 2;
  
  if (left < n && arr[left] > arr[largest]) {
    largest = left;
  }
  
  if (right < n && arr[right] > arr[largest]) {
    largest = right;
  }
  
  if (largest !== i) {
    [arr[i], arr[largest]] = [arr[largest], arr[i]];
    heapify(arr, n, largest);
  }
}

// Test cases
console.log(heapSort([12, 11, 13, 5, 6, 7])); // [5, 6, 7, 11, 12, 13]
console.log(heapSort([1, 3, 2, 5, 4])); // [1, 2, 3, 4, 5]
```

**Time Complexity:** O(n log n)  
**Space Complexity:** O(1) - in-place

---

## 4. Binary Search

### Question
Given a sorted array and target value, return the index of target or -1 if not found.

### Input
```
nums = [-1, 0, 3, 5, 9, 12], target = 9
```

### Expected Output
```
4
```

### Explanation
Target 9 is at index 4.

### TypeScript Solution
```typescript
function binarySearch(nums: number[], target: number): number {
  let left = 0, right = nums.length - 1;
  
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    
    if (nums[mid] === target) return mid;
    else if (nums[mid] < target) left = mid + 1;
    else right = mid - 1;
  }
  
  return -1;
}

// Recursive approach
function binarySearchRecursive(
  nums: number[],
  target: number,
  left = 0,
  right = nums.length - 1
): number {
  if (left > right) return -1;
  
  const mid = Math.floor((left + right) / 2);
  
  if (nums[mid] === target) return mid;
  else if (nums[mid] < target) return binarySearchRecursive(nums, target, mid + 1, right);
  else return binarySearchRecursive(nums, target, left, mid - 1);
}

// Test cases
console.log(binarySearch([-1, 0, 3, 5, 9, 12], 9)); // 4
console.log(binarySearch([-1, 0, 3, 5, 9, 12], 13)); // -1
console.log(binarySearch([5], 5)); // 0
```

**Time Complexity:** O(log n)  
**Space Complexity:** O(1) iterative, O(log n) recursive

---

## 5. Majority Element

### Question
Given an array, find the element appearing more than n/2 times.

### Input
```
nums = [3, 2, 3]
```

### Expected Output
```
3
```

### Explanation
Element 3 appears 2 times which is more than 3/2 = 1.5.

### TypeScript Solution
```typescript
// Boyer-Moore Voting Algorithm
function majorityElement(nums: number[]): number {
  let candidate = 0, count = 0;
  
  for (const num of nums) {
    if (count === 0) {
      candidate = num;
    }
    count += (num === candidate) ? 1 : -1;
  }
  
  return candidate;
}

// HashMap approach
function majorityElementHashMap(nums: number[]): number {
  const map = new Map<number, number>();
  const threshold = Math.floor(nums.length / 2);
  
  for (const num of nums) {
    map.set(num, (map.get(num) || 0) + 1);
    if (map.get(num)! > threshold) {
      return num;
    }
  }
  
  return -1;
}

// Test cases
console.log(majorityElement([3, 2, 3])); // 3
console.log(majorityElement([2, 2, 1, 1, 1, 2, 2])); // 2
console.log(majorityElement([1])); // 1
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) voting, O(n) hashmap

---

## 6. First Unique Character in a String

### Question
Given a string, find the first non-repeating character and return its index.

### Input
```
s = "leetcode"
```

### Expected Output
```
0
```

### Explanation
'l' appears at index 0 and appears only once.

### TypeScript Solution
```typescript
function firstUniqueChar(s: string): number {
  const charCount = new Map<string, number>();
  
  // Count occurrences
  for (const char of s) {
    charCount.set(char, (charCount.get(char) || 0) + 1);
  }
  
  // Find first unique
  for (let i = 0; i < s.length; i++) {
    if (charCount.get(s[i]) === 1) {
      return i;
    }
  }
  
  return -1;
}

// Test cases
console.log(firstUniqueChar("leetcode")); // 0
console.log(firstUniqueChar("loveleetcode")); // 2
console.log(firstUniqueChar("aabb")); // -1
console.log(firstUniqueChar("abacabad")); // 0
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) - max 26 characters

---

## 7. Hamming Distance

### Question
Given two integers x and y, return the hamming distance (number of different bits).

### Input
```
x = 1, y = 4
```

### Expected Output
```
2
```

### Explanation
1 = 0001, 4 = 0100, differ at positions 0 and 2.

### TypeScript Solution
```typescript
function hammingDistance(x: number, y: number): number {
  let xor = x ^ y;
  let count = 0;
  
  while (xor) {
    count += xor & 1;
    xor = xor >>> 1;
  }
  
  return count;
}

// Using Brian Kernighan's algorithm
function hammingDistanceOptimal(x: number, y: number): number {
  let xor = x ^ y;
  let count = 0;
  
  while (xor) {
    xor &= xor - 1; // Remove rightmost set bit
    count++;
  }
  
  return count;
}

// Test cases
console.log(hammingDistance(1, 4)); // 2
console.log(hammingDistance(3, 1)); // 1
console.log(hammingDistance(1, 1)); // 0
```

**Time Complexity:** O(log(max(x, y)))  
**Space Complexity:** O(1)

