# Coding Problems - Arrays, Strings, Objects

## **Array Problems (20 Problems)**

### 1. Find largest number in array
```typescript
function findLargest(arr: number[]): number {
  return Math.max(...arr);
}
// findLargest([1, 5, 3, 9, 2]) → 9
```

### 2. Remove duplicates from array
```typescript
function removeDuplicates(arr: number[]): number[] {
  return [...new Set(arr)];
}
// removeDuplicates([1, 2, 2, 3, 4, 4]) → [1, 2, 3, 4]
```

### 3. Reverse an array
```typescript
function reverseArray(arr: any[]): any[] {
  return arr.reverse();
}
// reverseArray([1, 2, 3]) → [3, 2, 1]
```

### 4. Find second largest number
```typescript
function secondLargest(arr: number[]): number {
  const unique = [...new Set(arr)].sort((a, b) => b - a);
  return unique[1];
}
// secondLargest([1, 5, 3, 9, 2]) → 5
```

### 5. Flatten nested array
```typescript
function flattenArray(arr: any[]): any[] {
  return arr.flat(Infinity);
}
// flattenArray([1, [2, [3, [4]]]]) → [1, 2, 3, 4]
```

### 6. Find missing number in sequence
```typescript
function findMissing(arr: number[]): number {
  const n = arr.length + 1;
  const expectedSum = (n * (n + 1)) / 2;
  const actualSum = arr.reduce((sum, num) => sum + num, 0);
  return expectedSum - actualSum;
}
// findMissing([1, 2, 4, 5]) → 3
```

### 7. Rotate array by k positions
```typescript
function rotateArray(arr: number[], k: number): number[] {
  k = k % arr.length;
  return [...arr.slice(-k), ...arr.slice(0, -k)];
}
// rotateArray([1, 2, 3, 4, 5], 2) → [4, 5, 1, 2, 3]
```

### 8. Find intersection of two arrays
```typescript
function intersection(arr1: number[], arr2: number[]): number[] {
  const set2 = new Set(arr2);
  return arr1.filter(item => set2.has(item));
}
// intersection([1, 2, 3], [2, 3, 4]) → [2, 3]
```

### 9. Chunk array into groups
```typescript
function chunkArray(arr: any[], size: number): any[][] {
  const result = [];
  for (let i = 0; i < arr.length; i += size) {
    result.push(arr.slice(i, i + size));
  }
  return result;
}
// chunkArray([1, 2, 3, 4, 5], 2) → [[1, 2], [3, 4], [5]]
```

### 10. Find pairs that sum to target
```typescript
function findPairs(arr: number[], target: number): number[][] {
  const pairs: number[][] = [];
  const seen = new Set();
  
  for (const num of arr) {
    const complement = target - num;
    if (seen.has(complement)) {
      pairs.push([complement, num]);
    }
    seen.add(num);
  }
  return pairs;
}
// findPairs([1, 2, 3, 4, 5], 5) → [[1, 4], [2, 3]]
```

### 11. Move zeros to end
```typescript
function moveZeros(arr: number[]): number[] {
  return [...arr.filter(x => x !== 0), ...arr.filter(x => x === 0)];
}
// moveZeros([0, 1, 0, 3, 12]) → [1, 3, 12, 0, 0]
```

### 12. Find frequency of elements
```typescript
function frequency(arr: any[]): Record<string, number> {
  return arr.reduce((acc, item) => {
    acc[item] = (acc[item] || 0) + 1;
    return acc;
  }, {});
}
// frequency([1, 2, 2, 3, 3, 3]) → {1: 1, 2: 2, 3: 3}
```

### 13. Merge two sorted arrays
```typescript
function mergeSorted(arr1: number[], arr2: number[]): number[] {
  const result = [];
  let i = 0, j = 0;
  
  while (i < arr1.length && j < arr2.length) {
    if (arr1[i] < arr2[j]) {
      result.push(arr1[i++]);
    } else {
      result.push(arr2[j++]);
    }
  }
  
  return [...result, ...arr1.slice(i), ...arr2.slice(j)];
}
// mergeSorted([1, 3, 5], [2, 4, 6]) → [1, 2, 3, 4, 5, 6]
```

### 14. Find common elements in multiple arrays
```typescript
function commonElements(...arrays: number[][]): number[] {
  return arrays.reduce((acc, arr) => 
    acc.filter(item => arr.includes(item))
  );
}
// commonElements([1, 2, 3], [2, 3, 4], [2, 3, 5]) → [2, 3]
```

### 15. Array difference
```typescript
function difference(arr1: number[], arr2: number[]): number[] {
  return arr1.filter(item => !arr2.includes(item));
}
// difference([1, 2, 3, 4], [2, 4]) → [1, 3]
```

### 16. Find maximum subarray sum (Kadane's Algorithm)
```typescript
function maxSubarraySum(arr: number[]): number {
  let maxSum = arr[0];
  let currentSum = arr[0];
  
  for (let i = 1; i < arr.length; i++) {
    currentSum = Math.max(arr[i], currentSum + arr[i]);
    maxSum = Math.max(maxSum, currentSum);
  }
  
  return maxSum;
}
// maxSubarraySum([-2, 1, -3, 4, -1, 2, 1, -5, 4]) → 6
```

### 17. Find first duplicate
```typescript
function firstDuplicate(arr: number[]): number | null {
  const seen = new Set();
  for (const num of arr) {
    if (seen.has(num)) return num;
    seen.add(num);
  }
  return null;
}
// firstDuplicate([1, 2, 3, 2, 4]) → 2
```

### 18. Sort array by frequency
```typescript
function sortByFrequency(arr: number[]): number[] {
  const freq = frequency(arr);
  return arr.sort((a, b) => freq[b] - freq[a]);
}
// sortByFrequency([1, 1, 2, 2, 2, 3]) → [2, 2, 2, 1, 1, 3]
```

### 19. Find kth largest element
```typescript
function kthLargest(arr: number[], k: number): number {
  return arr.sort((a, b) => b - a)[k - 1];
}
// kthLargest([3, 2, 1, 5, 6, 4], 2) → 5
```

### 20. Check if array is sorted
```typescript
function isSorted(arr: number[]): boolean {
  for (let i = 1; i < arr.length; i++) {
    if (arr[i] < arr[i - 1]) return false;
  }
  return true;
}
// isSorted([1, 2, 3, 4]) → true
```

---

## **String Problems (20 Problems)**

### 21. Reverse a string
```typescript
function reverseString(str: string): string {
  return str.split('').reverse().join('');
}
// reverseString("hello") → "olleh"
```

### 22. Check if palindrome
```typescript
function isPalindrome(str: string): boolean {
  const cleaned = str.toLowerCase().replace(/[^a-z0-9]/g, '');
  return cleaned === cleaned.split('').reverse().join('');
}
// isPalindrome("A man, a plan, a canal: Panama") → true
```

### 23. Count vowels in string
```typescript
function countVowels(str: string): number {
  return (str.match(/[aeiou]/gi) || []).length;
}
// countVowels("hello world") → 3
```

### 24. Find first non-repeating character
```typescript
function firstNonRepeating(str: string): string | null {
  const freq: Record<string, number> = {};
  
  for (const char of str) {
    freq[char] = (freq[char] || 0) + 1;
  }
  
  for (const char of str) {
    if (freq[char] === 1) return char;
  }
  
  return null;
}
// firstNonRepeating("swiss") → "w"
```

### 25. Check if anagram
```typescript
function isAnagram(str1: string, str2: string): boolean {
  const sort = (s: string) => s.toLowerCase().split('').sort().join('');
  return sort(str1) === sort(str2);
}
// isAnagram("listen", "silent") → true
```

### 26. Capitalize first letter of each word
```typescript
function capitalize(str: string): string {
  return str.split(' ').map(word => 
    word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
  ).join(' ');
}
// capitalize("hello world") → "Hello World"
```

### 27. Remove duplicates from string
```typescript
function removeDuplicateChars(str: string): string {
  return [...new Set(str)].join('');
}
// removeDuplicateChars("hello") → "helo"
```

### 28. Count word occurrences
```typescript
function wordCount(str: string): Record<string, number> {
  return str.toLowerCase().split(/\s+/).reduce((acc, word) => {
    acc[word] = (acc[word] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);
}
// wordCount("hello world hello") → {hello: 2, world: 1}
```

### 29. Longest word in string
```typescript
function longestWord(str: string): string {
  return str.split(' ').reduce((longest, word) => 
    word.length > longest.length ? word : longest, ''
  );
}
// longestWord("The quick brown fox") → "quick"
```

### 30. Truncate string
```typescript
function truncate(str: string, length: number): string {
  return str.length > length ? str.slice(0, length) + '...' : str;
}
// truncate("Hello World", 5) → "Hello..."
```

### 31. Count character occurrences
```typescript
function charCount(str: string, char: string): number {
  return str.split('').filter(c => c === char).length;
}
// charCount("hello", "l") → 2
```

### 32. Remove whitespace
```typescript
function removeWhitespace(str: string): string {
  return str.replace(/\s+/g, '');
}
// removeWhitespace("hello world") → "helloworld"
```

### 33. Check if string contains only digits
```typescript
function isNumeric(str: string): boolean {
  return /^\d+$/.test(str);
}
// isNumeric("12345") → true
```

### 34. Reverse words in string
```typescript
function reverseWords(str: string): string {
  return str.split(' ').reverse().join(' ');
}
// reverseWords("hello world") → "world hello"
```

### 35. Find longest substring without repeating characters
```typescript
function longestUniqueSubstring(str: string): number {
  let maxLength = 0;
  let start = 0;
  const seen = new Map();
  
  for (let end = 0; end < str.length; end++) {
    if (seen.has(str[end])) {
      start = Math.max(start, seen.get(str[end]) + 1);
    }
    seen.set(str[end], end);
    maxLength = Math.max(maxLength, end - start + 1);
  }
  
  return maxLength;
}
// longestUniqueSubstring("abcabcbb") → 3
```

### 36. Check if substring exists
```typescript
function hasSubstring(str: string, sub: string): boolean {
  return str.includes(sub);
}
// hasSubstring("hello world", "world") → true
```

### 37. Replace all occurrences
```typescript
function replaceAll(str: string, find: string, replace: string): string {
  return str.split(find).join(replace);
}
// replaceAll("hello world", "o", "0") → "hell0 w0rld"
```

### 38. Check if valid parentheses
```typescript
function isValidParentheses(str: string): boolean {
  const stack = [];
  const pairs: Record<string, string> = { '(': ')', '[': ']', '{': '}' };
  
  for (const char of str) {
    if (char in pairs) {
      stack.push(char);
    } else if (Object.values(pairs).includes(char)) {
      if (pairs[stack.pop()!] !== char) return false;
    }
  }
  
  return stack.length === 0;
}
// isValidParentheses("()[]{}") → true
```

### 39. Convert string to camelCase
```typescript
function toCamelCase(str: string): string {
  return str.replace(/[-_](.)/g, (_, char) => char.toUpperCase());
}
// toCamelCase("hello-world") → "helloWorld"
```

### 40. Count substring occurrences
```typescript
function countSubstring(str: string, sub: string): number {
  return (str.match(new RegExp(sub, 'g')) || []).length;
}
// countSubstring("hello hello", "hello") → 2
```

---

## **Object Problems (10 Problems)**

### 41. Deep clone object
```typescript
function deepClone<T>(obj: T): T {
  return JSON.parse(JSON.stringify(obj));
}
```

### 42. Merge two objects
```typescript
function mergeObjects(obj1: any, obj2: any): any {
  return { ...obj1, ...obj2 };
}
```

### 43. Check if object is empty
```typescript
function isEmpty(obj: object): boolean {
  return Object.keys(obj).length === 0;
}
```

### 44. Invert object (swap keys and values)
```typescript
function invertObject(obj: Record<string, any>): Record<string, string> {
  return Object.entries(obj).reduce((acc, [key, value]) => {
    acc[value] = key;
    return acc;
  }, {} as Record<string, string>);
}
// invertObject({a: 1, b: 2}) → {1: "a", 2: "b"}
```

### 45. Filter object by condition
```typescript
function filterObject(
  obj: Record<string, any>, 
  predicate: (value: any) => boolean
): Record<string, any> {
  return Object.entries(obj)
    .filter(([_, value]) => predicate(value))
    .reduce((acc, [key, value]) => ({ ...acc, [key]: value }), {});
}
// filterObject({a: 1, b: 2, c: 3}, v => v > 1) → {b: 2, c: 3}
```

### 46. Group array of objects by property
```typescript
function groupBy<T>(arr: T[], key: keyof T): Record<string, T[]> {
  return arr.reduce((acc, item) => {
    const group = String(item[key]);
    acc[group] = acc[group] || [];
    acc[group].push(item);
    return acc;
  }, {} as Record<string, T[]>);
}
```

### 47. Pick properties from object
```typescript
function pick<T, K extends keyof T>(obj: T, keys: K[]): Pick<T, K> {
  return keys.reduce((acc, key) => {
    if (key in obj) acc[key] = obj[key];
    return acc;
  }, {} as Pick<T, K>);
}
// pick({a: 1, b: 2, c: 3}, ['a', 'c']) → {a: 1, c: 3}
```

### 48. Omit properties from object
```typescript
function omit<T, K extends keyof T>(obj: T, keys: K[]): Omit<T, K> {
  const result = { ...obj };
  keys.forEach(key => delete result[key]);
  return result;
}
// omit({a: 1, b: 2, c: 3}, ['b']) → {a: 1, c: 3}
```

### 49. Flatten nested object
```typescript
function flattenObject(obj: any, prefix = ''): Record<string, any> {
  return Object.keys(obj).reduce((acc, key) => {
    const newKey = prefix ? `${prefix}.${key}` : key;
    if (typeof obj[key] === 'object' && obj[key] !== null) {
      Object.assign(acc, flattenObject(obj[key], newKey));
    } else {
      acc[newKey] = obj[key];
    }
    return acc;
  }, {} as Record<string, any>);
}
// flattenObject({a: {b: {c: 1}}}) → {"a.b.c": 1}
```

### 50. Deep equality check
```typescript
function deepEqual(obj1: any, obj2: any): boolean {
  return JSON.stringify(obj1) === JSON.stringify(obj2);
}
```

---

## **Quick Tips**

### Array Methods
- `map()` - Transform
- `filter()` - Select
- `reduce()` - Accumulate
- `find()` - First match
- `some()` - Any match
- `every()` - All match

### String Methods
- `split()` - To array
- `join()` - From array
- `slice()` - Extract
- `replace()` - Replace
- `trim()` - Remove whitespace

### Object Methods
- `Object.keys()` - Keys
- `Object.values()` - Values
- `Object.entries()` - Key-value pairs
- `Object.assign()` - Merge

---

**Total: 50 coding problems with solutions**
