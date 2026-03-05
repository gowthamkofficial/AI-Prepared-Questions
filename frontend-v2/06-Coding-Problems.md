# Coding Problems - Arrays, Strings, Objects

## **Array Problems**

### 1. Find the largest number in an array
```typescript
function findLargest(arr: number[]): number {
  return Math.max(...arr);
  // OR
  return arr.reduce((max, num) => num > max ? num : max, arr[0]);
}
// findLargest([1, 5, 3, 9, 2]) → 9
```

### 2. Remove duplicates from array
```typescript
function removeDuplicates(arr: number[]): number[] {
  return [...new Set(arr)];
  // OR
  return arr.filter((item, index) => arr.indexOf(item) === index);
}
// removeDuplicates([1, 2, 2, 3, 4, 4, 5]) → [1, 2, 3, 4, 5]
```

### 3. Reverse an array
```typescript
function reverseArray(arr: any[]): any[] {
  return arr.reverse();
  // OR (without mutating)
  return [...arr].reverse();
  // OR (manual)
  return arr.reduce((acc, item) => [item, ...acc], []);
}
// reverseArray([1, 2, 3, 4]) → [4, 3, 2, 1]
```

### 4. Find second largest number
```typescript
function secondLargest(arr: number[]): number {
  const unique = [...new Set(arr)].sort((a, b) => b - a);
  return unique[1];
}
// secondLargest([1, 5, 3, 9, 2, 9]) → 5
```

### 5. Flatten nested array
```typescript
function flattenArray(arr: any[]): any[] {
  return arr.flat(Infinity);
  // OR (recursive)
  return arr.reduce((acc, item) => 
    Array.isArray(item) ? [...acc, ...flattenArray(item)] : [...acc, item], []);
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
// findMissing([1, 2, 4, 5, 6]) → 3
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
  return arr1.filter(item => arr2.includes(item));
  // OR (using Set for better performance)
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
  return [...arr1, ...arr2].sort((a, b) => a - b);
  // OR (efficient merge)
  const result = [];
  let i = 0, j = 0;
  while (i < arr1.length && j < arr2.length) {
    if (arr1[i] < arr2[j]) result.push(arr1[i++]);
    else result.push(arr2[j++]);
  }
  return [...result, ...arr1.slice(i), ...arr2.slice(j)];
}
```

### 14. Find common elements in multiple arrays
```typescript
function commonElements(...arrays: number[][]): number[] {
  return arrays.reduce((acc, arr) => acc.filter(item => arr.includes(item)));
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

---

## **String Problems**

### 16. Reverse a string
```typescript
function reverseString(str: string): string {
  return str.split('').reverse().join('');
  // OR
  return [...str].reverse().join('');
}
// reverseString("hello") → "olleh"
```

### 17. Check if palindrome
```typescript
function isPalindrome(str: string): boolean {
  const cleaned = str.toLowerCase().replace(/[^a-z0-9]/g, '');
  return cleaned === cleaned.split('').reverse().join('');
}
// isPalindrome("A man, a plan, a canal: Panama") → true
```

### 18. Count vowels in string
```typescript
function countVowels(str: string): number {
  return (str.match(/[aeiou]/gi) || []).length;
}
// countVowels("hello world") → 3
```

### 19. Find first non-repeating character
```typescript
function firstNonRepeating(str: string): string | null {
  const freq = {};
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

### 20. Check if anagram
```typescript
function isAnagram(str1: string, str2: string): boolean {
  const sort = (s: string) => s.toLowerCase().split('').sort().join('');
  return sort(str1) === sort(str2);
}
// isAnagram("listen", "silent") → true
```

### 21. Capitalize first letter of each word
```typescript
function capitalize(str: string): string {
  return str.split(' ').map(word => 
    word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
  ).join(' ');
}
// capitalize("hello world") → "Hello World"
```

### 22. Remove duplicates from string
```typescript
function removeDuplicateChars(str: string): string {
  return [...new Set(str)].join('');
}
// removeDuplicateChars("hello") → "helo"
```

### 23. Count word occurrences
```typescript
function wordCount(str: string): Record<string, number> {
  return str.toLowerCase().split(/\s+/).reduce((acc, word) => {
    acc[word] = (acc[word] || 0) + 1;
    return acc;
  }, {});
}
// wordCount("hello world hello") → {hello: 2, world: 1}
```

### 24. Longest word in string
```typescript
function longestWord(str: string): string {
  return str.split(' ').reduce((longest, word) => 
    word.length > longest.length ? word : longest, '');
}
// longestWord("The quick brown fox") → "quick"
```

### 25. Check if substring exists
```typescript
function hasSubstring(str: string, sub: string): boolean {
  return str.includes(sub);
  // OR
  return str.indexOf(sub) !== -1;
}
// hasSubstring("hello world", "world") → true
```

### 26. Truncate string
```typescript
function truncate(str: string, length: number): string {
  return str.length > length ? str.slice(0, length) + '...' : str;
}
// truncate("Hello World", 5) → "Hello..."
```

### 27. Count character occurrences
```typescript
function charCount(str: string, char: string): number {
  return str.split('').filter(c => c === char).length;
  // OR
  return (str.match(new RegExp(char, 'g')) || []).length;
}
// charCount("hello", "l") → 2
```

### 28. Remove whitespace
```typescript
function removeWhitespace(str: string): string {
  return str.replace(/\s+/g, '');
}
// removeWhitespace("hello world") → "helloworld"
```

### 29. Check if string contains only digits
```typescript
function isNumeric(str: string): boolean {
  return /^\d+$/.test(str);
}
// isNumeric("12345") → true
```

### 30. Reverse words in string
```typescript
function reverseWords(str: string): string {
  return str.split(' ').reverse().join(' ');
}
// reverseWords("hello world") → "world hello"
```

---

## **Object Problems**

### 31. Deep clone object
```typescript
function deepClone<T>(obj: T): T {
  return JSON.parse(JSON.stringify(obj));
  // OR (handles functions, dates)
  return structuredClone(obj);
}
```

### 32. Merge two objects
```typescript
function mergeObjects(obj1: any, obj2: any): any {
  return { ...obj1, ...obj2 };
  // OR (deep merge)
  return Object.assign({}, obj1, obj2);
}
```

### 33. Get object keys
```typescript
function getKeys(obj: object): string[] {
  return Object.keys(obj);
}
// getKeys({a: 1, b: 2}) → ["a", "b"]
```

### 34. Get object values
```typescript
function getValues(obj: object): any[] {
  return Object.values(obj);
}
// getValues({a: 1, b: 2}) → [1, 2]
```

### 35. Check if object is empty
```typescript
function isEmpty(obj: object): boolean {
  return Object.keys(obj).length === 0;
}
// isEmpty({}) → true
```

### 36. Invert object (swap keys and values)
```typescript
function invertObject(obj: Record<string, any>): Record<string, string> {
  return Object.entries(obj).reduce((acc, [key, value]) => {
    acc[value] = key;
    return acc;
  }, {});
}
// invertObject({a: 1, b: 2}) → {1: "a", 2: "b"}
```

### 37. Filter object by condition
```typescript
function filterObject(obj: Record<string, any>, predicate: (value: any) => boolean): Record<string, any> {
  return Object.entries(obj)
    .filter(([_, value]) => predicate(value))
    .reduce((acc, [key, value]) => ({ ...acc, [key]: value }), {});
}
// filterObject({a: 1, b: 2, c: 3}, v => v > 1) → {b: 2, c: 3}
```

### 38. Group array of objects by property
```typescript
function groupBy<T>(arr: T[], key: keyof T): Record<string, T[]> {
  return arr.reduce((acc, item) => {
    const group = String(item[key]);
    acc[group] = acc[group] || [];
    acc[group].push(item);
    return acc;
  }, {} as Record<string, T[]>);
}
// groupBy([{age: 20}, {age: 30}, {age: 20}], 'age') → {20: [...], 30: [...]}
```

### 39. Pick properties from object
```typescript
function pick<T, K extends keyof T>(obj: T, keys: K[]): Pick<T, K> {
  return keys.reduce((acc, key) => {
    if (key in obj) acc[key] = obj[key];
    return acc;
  }, {} as Pick<T, K>);
}
// pick({a: 1, b: 2, c: 3}, ['a', 'c']) → {a: 1, c: 3}
```

### 40. Omit properties from object
```typescript
function omit<T, K extends keyof T>(obj: T, keys: K[]): Omit<T, K> {
  const result = { ...obj };
  keys.forEach(key => delete result[key]);
  return result;
}
// omit({a: 1, b: 2, c: 3}, ['b']) → {a: 1, c: 3}
```

### 41. Flatten nested object
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
  }, {});
}
// flattenObject({a: {b: {c: 1}}}) → {"a.b.c": 1}
```

### 42. Deep equality check
```typescript
function deepEqual(obj1: any, obj2: any): boolean {
  return JSON.stringify(obj1) === JSON.stringify(obj2);
}
```

### 43. Count properties in object
```typescript
function countProperties(obj: object): number {
  return Object.keys(obj).length;
}
```

### 44. Convert object to array of key-value pairs
```typescript
function toEntries(obj: object): [string, any][] {
  return Object.entries(obj);
}
// toEntries({a: 1, b: 2}) → [["a", 1], ["b", 2]]
```

### 45. Convert array to object
```typescript
function arrayToObject<T>(arr: T[], key: keyof T): Record<string, T> {
  return arr.reduce((acc, item) => {
    acc[String(item[key])] = item;
    return acc;
  }, {});
}
```

---

## **Combined Problems**

### 46. Find most frequent element
```typescript
function mostFrequent(arr: any[]): any {
  const freq = arr.reduce((acc, item) => {
    acc[item] = (acc[item] || 0) + 1;
    return acc;
  }, {});
  return Object.keys(freq).reduce((a, b) => freq[a] > freq[b] ? a : b);
}
```

### 47. Remove falsy values
```typescript
function compact(arr: any[]): any[] {
  return arr.filter(Boolean);
}
// compact([0, 1, false, 2, '', 3]) → [1, 2, 3]
```

### 48. Unique array of objects by property
```typescript
function uniqueBy<T>(arr: T[], key: keyof T): T[] {
  const seen = new Set();
  return arr.filter(item => {
    const value = item[key];
    if (seen.has(value)) return false;
    seen.add(value);
    return true;
  });
}
```

### 49. Sort array of objects
```typescript
function sortBy<T>(arr: T[], key: keyof T): T[] {
  return [...arr].sort((a, b) => {
    if (a[key] < b[key]) return -1;
    if (a[key] > b[key]) return 1;
    return 0;
  });
}
```

### 50. Debounce function
```typescript
function debounce(fn: Function, delay: number) {
  let timeoutId: NodeJS.Timeout;
  return function(...args: any[]) {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => fn(...args), delay);
  };
}
```

---

## **Quick Tips**

### Array Methods
- `map()`: Transform elements
- `filter()`: Select elements
- `reduce()`: Accumulate values
- `find()`: Find first match
- `some()`: Check if any match
- `every()`: Check if all match

### String Methods
- `split()`: String to array
- `join()`: Array to string
- `slice()`: Extract substring
- `replace()`: Replace text
- `trim()`: Remove whitespace

### Object Methods
- `Object.keys()`: Get keys
- `Object.values()`: Get values
- `Object.entries()`: Get key-value pairs
- `Object.assign()`: Merge objects
- `Object.freeze()`: Make immutable
