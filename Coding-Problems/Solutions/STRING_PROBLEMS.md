# String Problems - Complete Solutions Guide

## 1. Palindrome Check

### Question
Given a string s, determine if it is a valid palindrome considering only alphanumeric characters and ignoring cases.

### Input
```
s = "A man, a plan, a canal: Panama"
```

### Expected Output
```
true
```

### Explanation
After removing non-alphanumeric characters and converting to lowercase: "amanaplanacanalpanama"
Reading forwards and backwards gives the same string.

### TypeScript Solution
```typescript
function isPalindrome(s: string): boolean {
  const clean = s.toLowerCase().replace(/[^a-z0-9]/g, '');
  let left = 0, right = clean.length - 1;
  
  while (left < right) {
    if (clean[left] !== clean[right]) return false;
    left++;
    right--;
  }
  
  return true;
}

// Test cases
console.log(isPalindrome("A man, a plan, a canal: Panama")); // true
console.log(isPalindrome("race a car")); // false
console.log(isPalindrome(" ")); // true
```

**Time Complexity:** O(n) - single pass through string  
**Space Complexity:** O(n) - for cleaned string

---

## 2. Reverse String

### Question
Write a function that reverses a string. You may assume the string consists of English letters only.

### Input
```
s = "hello"
```

### Expected Output
```
"olleh"
```

### Explanation
The string is reversed character by character from end to beginning.

### TypeScript Solution
```typescript
function reverseString(s: string): string {
  return s.split('').reverse().join('');
}

// In-place version (with character array)
function reverseStringInPlace(chars: string[]): void {
  let left = 0, right = chars.length - 1;
  
  while (left < right) {
    [chars[left], chars[right]] = [chars[right], chars[left]];
    left++;
    right--;
  }
}

// Test cases
console.log(reverseString("hello")); // "olleh"
console.log(reverseString("world")); // "dlrow"

const arr = ['h', 'e', 'l', 'l', 'o'];
reverseStringInPlace(arr);
console.log(arr.join('')); // "olleh"
```

**Time Complexity:** O(n)  
**Space Complexity:** O(n) for string version, O(1) for in-place version

---

## 3. Longest Substring Without Repeating Characters

### Question
Given a string s, find the length of the longest substring without repeating characters.

### Input
```
s = "abcabcbb"
```

### Expected Output
```
3
```

### Explanation
The longest substring without repeating characters is "abc" with length 3.

### TypeScript Solution
```typescript
function lengthOfLongestSubstring(s: string): number {
  const charMap = new Map<string, number>();
  let maxLen = 0, start = 0;
  
  for (let end = 0; end < s.length; end++) {
    const char = s[end];
    
    // If character exists and is within current window
    if (charMap.has(char) && charMap.get(char)! >= start) {
      start = charMap.get(char)! + 1;
    }
    
    charMap.set(char, end);
    maxLen = Math.max(maxLen, end - start + 1);
  }
  
  return maxLen;
}

// Test cases
console.log(lengthOfLongestSubstring("abcabcbb")); // 3 ("abc")
console.log(lengthOfLongestSubstring("bbbbb")); // 1 ("b")
console.log(lengthOfLongestSubstring("pwwkew")); // 3 ("wke")
console.log(lengthOfLongestSubstring("au")); // 2 ("au")
console.log(lengthOfLongestSubstring("")); // 0
```

**Time Complexity:** O(n) - single pass with sliding window  
**Space Complexity:** O(min(n, m)) - where m is charset size (usually 26 for letters)

---

## 4. Valid Parentheses

### Question
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:
- Open brackets must be closed by the same type
- Open brackets must be closed in the correct order

### Input
```
s = "({[]})"
```

### Expected Output
```
true
```

### Explanation
All brackets are properly opened and closed in the correct order.

### TypeScript Solution
```typescript
function isValidParentheses(s: string): boolean {
  const stack: string[] = [];
  const pairs: Record<string, string> = {
    ')': '(',
    ']': '[',
    '}': '{'
  };
  
  for (const char of s) {
    if (char in pairs) {
      // Closing bracket
      if (stack.pop() !== pairs[char]) return false;
    } else {
      // Opening bracket
      stack.push(char);
    }
  }
  
  return stack.length === 0;
}

// Test cases
console.log(isValidParentheses("()")); // true
console.log(isValidParentheses("({[]})")); // true
console.log(isValidParentheses("(]")); // false
console.log(isValidParentheses("([)]")); // false
console.log(isValidParentheses("{[]}")); // true
```

**Time Complexity:** O(n) - single pass through string  
**Space Complexity:** O(n) - worst case when all opening brackets

---

## 5. Longest Palindromic Substring

### Question
Given a string s, return the longest palindromic substring in s.

### Input
```
s = "babad"
```

### Expected Output
```
"bab" or "aba"
```

### Explanation
Both "bab" and "aba" are valid longest palindromic substrings of length 3.

### TypeScript Solution
```typescript
function longestPalindrome(s: string): string {
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
    const len1 = expandAroundCenter(i, i); // odd length palindromes
    const len2 = expandAroundCenter(i, i + 1); // even length palindromes
    const len = Math.max(len1, len2);
    
    if (len > maxLen) {
      maxLen = len;
      start = i - Math.floor((len - 1) / 2);
    }
  }
  
  return s.substring(start, start + maxLen);
}

// Test cases
console.log(longestPalindrome("babad")); // "bab" or "aba"
console.log(longestPalindrome("cbbd")); // "bb"
console.log(longestPalindrome("a")); // "a"
console.log(longestPalindrome("ac")); // "a" or "c"
```

**Time Complexity:** O(n²) - n expansions, each can take O(n)  
**Space Complexity:** O(1) - only tracking indices

---

## 6. Valid Anagram

### Question
Given two strings s and t, return true if t is an anagram of s, and false otherwise.

### Input
```
s = "anagram"
t = "nagaram"
```

### Expected Output
```
true
```

### Explanation
Both strings contain the same characters with the same frequencies.

### TypeScript Solution
```typescript
function isAnagram(s: string, t: string): boolean {
  if (s.length !== t.length) return false;
  
  const charCount = new Map<string, number>();
  
  // Count characters in s
  for (const char of s) {
    charCount.set(char, (charCount.get(char) || 0) + 1);
  }
  
  // Verify with t
  for (const char of t) {
    if (!charCount.has(char)) return false;
    charCount.set(char, charCount.get(char)! - 1);
    if (charCount.get(char) === 0) charCount.delete(char);
  }
  
  return charCount.size === 0;
}

// Test cases
console.log(isAnagram("anagram", "nagaram")); // true
console.log(isAnagram("rat", "car")); // false
console.log(isAnagram("ab", "ba")); // true
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1) - at most 26 characters for English alphabet

---

## 7. Group Anagrams

### Question
Given an array of strings strs, group the anagrams together. You can return the answer in any order.

### Input
```
strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
```

### Expected Output
```
[["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
```

### Explanation
Anagrams are grouped together regardless of order.

### TypeScript Solution
```typescript
function groupAnagrams(strs: string[]): string[][] {
  const map = new Map<string, string[]>();
  
  for (const str of strs) {
    // Sort characters to create canonical form
    const key = str.split('').sort().join('');
    
    if (!map.has(key)) {
      map.set(key, []);
    }
    map.get(key)!.push(str);
  }
  
  return Array.from(map.values());
}

// Test cases
console.log(groupAnagrams(["eat", "tea", "tan", "ate", "nat", "bat"]));
// Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]

console.log(groupAnagrams([""])); // [[""]]
console.log(groupAnagrams(["a"])); // [["a"]]
```

**Time Complexity:** O(n * k log k) - where n is number of strings, k is max length  
**Space Complexity:** O(n * k) - for storing all strings

---

## 8. Longest Common Prefix

### Question
Write a function to find the longest common prefix string amongst an array of strings.

### Input
```
strs = ["flower", "flow", "flight"]
```

### Expected Output
```
"fl"
```

### Explanation
All strings share the prefix "fl".

### TypeScript Solution
```typescript
function longestCommonPrefix(strs: string[]): string {
  if (strs.length === 0) return "";
  
  let prefix = strs[0];
  
  for (let i = 1; i < strs.length; i++) {
    while (strs[i].indexOf(prefix) !== 0) {
      prefix = prefix.substring(0, prefix.length - 1);
      if (prefix === "") return "";
    }
  }
  
  return prefix;
}

// Test cases
console.log(longestCommonPrefix(["flower", "flow", "flight"])); // "fl"
console.log(longestCommonPrefix(["dog", "racecar", "car"])); // ""
console.log(longestCommonPrefix(["interspecies", "interstellar", "interstate"])); // "inters"
```

**Time Complexity:** O(s) - where s is sum of all characters  
**Space Complexity:** O(1) - excluding output

---

## 9. Compress String

### Question
Implement a method to perform basic string compression using the counts of repeated characters.

### Input
```
s = "aabcccccaadddddeeeeaabbbcd"
```

### Expected Output
```
"a2b1c5a3d5e4a2b3c1d1"
```

### Explanation
Each character is followed by its count. If compressed is longer, return original.

### TypeScript Solution
```typescript
function compressString(s: string): string {
  if (s.length === 0) return "";
  
  let compressed = "";
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

// Test cases
console.log(compressString("aabcccccaadddddeeeeaabbbcd"));
// "a2b1c5a3d5e4a2b3c1d1"

console.log(compressString("abcdef")); // "abcdef" (not compressed, longer)
console.log(compressString("aabbcc")); // "aabbcc"
```

**Time Complexity:** O(n)  
**Space Complexity:** O(n) - for compressed string

