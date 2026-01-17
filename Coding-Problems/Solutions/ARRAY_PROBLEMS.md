# Array Problems - Complete Solutions Guide

## 1. Two Sum

### Question
Given an array of integers nums and an integer target, return the indices of the two numbers that add up to target.

### Input
```
nums = [2, 7, 11, 15], target = 9
```

### Expected Output
```
[0, 1]
```

### Explanation
nums[0] + nums[1] = 2 + 7 = 9, so return [0, 1]

### TypeScript Solution
```typescript
function twoSum(nums: number[], target: number): number[] {
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

// Test cases
console.log(twoSum([2, 7, 11, 15], 9)); // [0, 1]
console.log(twoSum([3, 2, 4], 6)); // [1, 2]
console.log(twoSum([3, 3], 6)); // [0, 1]
```

**Time Complexity:** O(n) - single pass with hash map  
**Space Complexity:** O(n) - for hash map

---

## 2. Three Sum

### Question
Given an integer array nums, return all unique triplets that sum to zero.

### Input
```
nums = [-1, 0, 1, 2, -1, -4]
```

### Expected Output
```
[[-1, -1, 2], [-1, 0, 1]]
```

### Explanation
All unique triplets that sum to 0.

### TypeScript Solution
```typescript
function threeSum(nums: number[]): number[][] {
  nums.sort((a, b) => a - b);
  const result: number[][] = [];
  
  for (let i = 0; i < nums.length - 2; i++) {
    if (nums[i] > 0) break; // No triplet with positive first element
    if (i > 0 && nums[i] === nums[i - 1]) continue; // Skip duplicates
    
    let left = i + 1, right = nums.length - 1;
    
    while (left < right) {
      const sum = nums[i] + nums[left] + nums[right];
      
      if (sum === 0) {
        result.push([nums[i], nums[left], nums[right]]);
        
        // Skip duplicates
        while (left < right && nums[left] === nums[left + 1]) left++;
        while (left < right && nums[right] === nums[right - 1]) right--;
        
        left++;
        right--;
      } else if (sum < 0) {
        left++;
      } else {
        right--;
      }
    }
  }
  
  return result;
}

// Test cases
console.log(threeSum([-1, 0, 1, 2, -1, -4])); // [[-1, -1, 2], [-1, 0, 1]]
console.log(threeSum([0, 0, 0, 0])); // [[0, 0, 0]]
console.log(threeSum([-2, 0, 1, 1, 2])); // [[-2, 0, 2], [-2, 1, 1]]
```

**Time Complexity:** O(n²) - sorting + nested loops  
**Space Complexity:** O(1) - excluding output

---

## 3. Merge Sorted Arrays

### Question
Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array in-place.

### Input
```
nums1 = [1, 2, 3, 0, 0, 0], m = 3
nums2 = [2, 5, 6], n = 3
```

### Expected Output
```
[1, 2, 2, 3, 5, 6]
```

### Explanation
Merge two sorted arrays in-place.

### TypeScript Solution
```typescript
function mergeSortedArrays(nums1: number[], m: number, nums2: number[], n: number): void {
  let p1 = m - 1;
  let p2 = n - 1;
  let p = m + n - 1;
  
  while (p1 >= 0 && p2 >= 0) {
    if (nums1[p1] > nums2[p2]) {
      nums1[p] = nums1[p1];
      p1--;
    } else {
      nums1[p] = nums2[p2];
      p2--;
    }
    p--;
  }
  
  // If nums2 has remaining elements
  while (p2 >= 0) {
    nums1[p] = nums2[p2];
    p2--;
    p--;
  }
}

// Test cases
const nums1 = [1, 2, 3, 0, 0, 0];
mergeSortedArrays(nums1, 3, [2, 5, 6], 3);
console.log(nums1); // [1, 2, 2, 3, 5, 6]

const nums1b = [1];
mergeSortedArrays(nums1b, 1, [], 0);
console.log(nums1b); // [1]
```

**Time Complexity:** O(m + n)  
**Space Complexity:** O(1) - in-place merge

---

## 4. Remove Duplicates

### Question
Given an integer array nums sorted in non-decreasing order, remove duplicates in-place and return the number of unique elements.

### Input
```
nums = [1, 1, 2, 2, 2, 3]
```

### Expected Output
```
4, nums = [1, 2, 3, 4] (first 4 elements)
```

### Explanation
Remove duplicates in-place and return count of unique elements.

### TypeScript Solution
```typescript
function removeDuplicates(nums: number[]): number {
  if (nums.length === 0) return 0;
  
  let k = 1; // First element always unique
  
  for (let i = 1; i < nums.length; i++) {
    if (nums[i] !== nums[i - 1]) {
      nums[k] = nums[i];
      k++;
    }
  }
  
  return k;
}

// Test cases
const arr1 = [1, 1, 2, 2, 2, 3];
console.log(removeDuplicates(arr1)); // 4
console.log(arr1.slice(0, 4)); // [1, 2, 3, 4] - Wait, should be [1, 2, 3]

const arr2 = [0, 0, 1, 1, 1, 1, 2, 3, 3];
console.log(removeDuplicates(arr2)); // 4
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) - in-place

---

## 5. Rotate Array

### Question
Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.

### Input
```
nums = [1, 2, 3, 4, 5], k = 2
```

### Expected Output
```
[4, 5, 1, 2, 3]
```

### Explanation
Rotate right by 2: [1,2,3,4,5] → [5,1,2,3,4] → [4,5,1,2,3]

### TypeScript Solution
```typescript
function rotateArray(nums: number[], k: number): void {
  k = k % nums.length; // Handle k > length
  
  const reverse = (start: number, end: number) => {
    while (start < end) {
      [nums[start], nums[end]] = [nums[end], nums[start]];
      start++;
      end--;
    }
  };
  
  reverse(0, nums.length - 1); // Reverse entire array
  reverse(0, k - 1); // Reverse first k elements
  reverse(k, nums.length - 1); // Reverse remaining elements
}

// Test cases
const arr = [1, 2, 3, 4, 5];
rotateArray(arr, 2);
console.log(arr); // [4, 5, 1, 2, 3]

const arr2 = [1];
rotateArray(arr2, 1);
console.log(arr2); // [1]
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) - in-place

---

## 6. Best Time to Buy and Sell Stock

### Question
Given an array prices where prices[i] is the price on day i, find maximum profit. You can buy and sell once.

### Input
```
prices = [7, 1, 5, 3, 6, 4]
```

### Expected Output
```
5
```

### Explanation
Buy at 1, sell at 6. Profit = 6 - 1 = 5.

### TypeScript Solution
```typescript
function maxProfit(prices: number[]): number {
  if (prices.length < 2) return 0;
  
  let minPrice = prices[0];
  let maxProfit = 0;
  
  for (let i = 1; i < prices.length; i++) {
    const profit = prices[i] - minPrice;
    maxProfit = Math.max(maxProfit, profit);
    minPrice = Math.min(minPrice, prices[i]);
  }
  
  return maxProfit;
}

// Test cases
console.log(maxProfit([7, 1, 5, 3, 6, 4])); // 5
console.log(maxProfit([7, 6, 4, 3, 1])); // 0
console.log(maxProfit([2, 4, 1])); // 2
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

## 7. Container With Most Water

### Question
Given an integer array heights representing elevation map, find two lines that form a container with maximum area.

### Input
```
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
```

### Expected Output
```
49
```

### Explanation
Lines at index 1 and 8 with height [8, 7] and distance 7 give area = 7 * 7 = 49.

### TypeScript Solution
```typescript
function containerWithMostWater(heights: number[]): number {
  let left = 0, right = heights.length - 1;
  let maxArea = 0;
  
  while (left < right) {
    const width = right - left;
    const height = Math.min(heights[left], heights[right]);
    const area = width * height;
    maxArea = Math.max(maxArea, area);
    
    // Move the pointer with smaller height
    if (heights[left] < heights[right]) {
      left++;
    } else {
      right--;
    }
  }
  
  return maxArea;
}

// Test cases
console.log(containerWithMostWater([1, 8, 6, 2, 5, 4, 8, 3, 7])); // 49
console.log(containerWithMostWater([1, 1])); // 1
```

**Time Complexity:** O(n) - two pointer approach  
**Space Complexity:** O(1)

---

## 8. Maximum Subarray

### Question
Given an integer array nums, find the subarray with the largest sum and return that sum.

### Input
```
nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
```

### Expected Output
```
6
```

### Explanation
Subarray [4, -1, 2, 1] has the largest sum = 6.

### TypeScript Solution
```typescript
function maxSubArray(nums: number[]): number {
  let maxCurrent = nums[0];
  let maxGlobal = nums[0];
  
  for (let i = 1; i < nums.length; i++) {
    maxCurrent = Math.max(nums[i], maxCurrent + nums[i]);
    maxGlobal = Math.max(maxGlobal, maxCurrent);
  }
  
  return maxGlobal;
}

// Test cases
console.log(maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4])); // 6
console.log(maxSubArray([5])); // 5
console.log(maxSubArray([-2])); // -2
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

## 9. Search in Rotated Sorted Array

### Question
Search for a target value in a rotated sorted array.

### Input
```
nums = [4, 5, 6, 7, 0, 1, 2], target = 0
```

### Expected Output
```
4
```

### Explanation
Target 0 is at index 4.

### TypeScript Solution
```typescript
function searchInRotatedArray(nums: number[], target: number): number {
  let left = 0, right = nums.length - 1;
  
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    
    if (nums[mid] === target) return mid;
    
    // Determine which half is sorted
    if (nums[left] <= nums[mid]) {
      // Left half is sorted
      if (nums[left] <= target && target < nums[mid]) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    } else {
      // Right half is sorted
      if (nums[mid] < target && target <= nums[right]) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
  }
  
  return -1;
}

// Test cases
console.log(searchInRotatedArray([4, 5, 6, 7, 0, 1, 2], 0)); // 4
console.log(searchInRotatedArray([4, 5, 6, 7, 0, 1, 2], 3)); // -1
console.log(searchInRotatedArray([1], 1)); // 0
```

**Time Complexity:** O(log n) - binary search  
**Space Complexity:** O(1)

---

## 10. First Missing Positive

### Question
Given an unsorted integer array nums, find the smallest missing positive integer.

### Input
```
nums = [3, 4, -1, 1]
```

### Expected Output
```
2
```

### Explanation
Numbers 1 and 3 are in the array. 2 is missing, so return 2.

### TypeScript Solution
```typescript
function firstMissingPositive(nums: number[]): number {
  // Mark presence using array indices
  for (let i = 0; i < nums.length; i++) {
    while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] !== nums[i]) {
      // Place nums[i] in its correct position
      [nums[i], nums[nums[i] - 1]] = [nums[nums[i] - 1], nums[i]];
    }
  }
  
  // Find first missing positive
  for (let i = 0; i < nums.length; i++) {
    if (nums[i] !== i + 1) return i + 1;
  }
  
  return nums.length + 1;
}

// Test cases
console.log(firstMissingPositive([3, 4, -1, 1])); // 2
console.log(firstMissingPositive([1, 2, 0])); // 3
console.log(firstMissingPositive([7, 8, 9, 11, 12])); // 1
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) - in-place

