/**
 * SORTING ALGORITHMS - COMPLETE IMPLEMENTATIONS
 */

// ==================== MERGE SORT ====================
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

// ==================== QUICK SORT ====================
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

// ==================== HEAP SORT ====================
export function heapSort(arr: number[]): number[] {
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

// ==================== INSERTION SORT ====================
export function insertionSort(arr: number[]): number[] {
  for (let i = 1; i < arr.length; i++) {
    const key = arr[i];
    let j = i - 1;
    
    while (j >= 0 && arr[j] > key) {
      arr[j + 1] = arr[j];
      j--;
    }
    arr[j + 1] = key;
  }
  
  return arr;
}

// ==================== COUNTING SORT ====================
export function countingSort(arr: number[], max: number): number[] {
  const count = new Array(max + 1).fill(0);
  const result: number[] = [];
  
  for (const num of arr) {
    count[num]++;
  }
  
  for (let i = 0; i <= max; i++) {
    for (let j = 0; j < count[i]; j++) {
      result.push(i);
    }
  }
  
  return result;
}

// ==================== RADIX SORT ====================
export function radixSort(arr: number[]): number[] {
  if (arr.length === 0) return arr;
  
  const max = Math.max(...arr);
  let exp = 1;
  
  while (Math.floor(max / exp) > 0) {
    countingSortByDigit(arr, exp);
    exp *= 10;
  }
  
  return arr;
}

function countingSortByDigit(arr: number[], exp: number): void {
  const count = new Array(10).fill(0);
  const output = new Array(arr.length);
  
  for (const num of arr) {
    count[Math.floor((num / exp) % 10)]++;
  }
  
  for (let i = 1; i < 10; i++) {
    count[i] += count[i - 1];
  }
  
  for (let i = arr.length - 1; i >= 0; i--) {
    const digit = Math.floor((arr[i] / exp) % 10);
    output[count[digit] - 1] = arr[i];
    count[digit]--;
  }
  
  for (let i = 0; i < arr.length; i++) {
    arr[i] = output[i];
  }
}

/**
 * SEARCHING ALGORITHMS - COMPLETE IMPLEMENTATIONS
 */

// ==================== BINARY SEARCH ====================
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

// Find first occurrence of target
export function binarySearchFirst(arr: number[], target: number): number {
  let left = 0, right = arr.length - 1;
  let result = -1;
  
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    
    if (arr[mid] === target) {
      result = mid;
      right = mid - 1; // Continue searching left
    } else if (arr[mid] < target) {
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }
  
  return result;
}

// Find last occurrence of target
export function binarySearchLast(arr: number[], target: number): number {
  let left = 0, right = arr.length - 1;
  let result = -1;
  
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    
    if (arr[mid] === target) {
      result = mid;
      left = mid + 1; // Continue searching right
    } else if (arr[mid] < target) {
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }
  
  return result;
}

/**
 * GRAPH ALGORITHMS - COMPLETE IMPLEMENTATIONS
 */

interface Graph {
  [key: number]: number[];
}

// ==================== DFS ====================
export function dfs(graph: Graph, start: number): number[] {
  const visited = new Set<number>();
  const result: number[] = [];
  
  const explore = (node: number) => {
    visited.add(node);
    result.push(node);
    
    for (const neighbor of graph[node]) {
      if (!visited.has(neighbor)) {
        explore(neighbor);
      }
    }
  };
  
  explore(start);
  return result;
}

// DFS Iterative
export function dfsIterative(graph: Graph, start: number): number[] {
  const visited = new Set<number>();
  const stack = [start];
  const result: number[] = [];
  
  while (stack.length > 0) {
    const node = stack.pop()!;
    
    if (!visited.has(node)) {
      visited.add(node);
      result.push(node);
      
      for (const neighbor of graph[node]) {
        if (!visited.has(neighbor)) {
          stack.push(neighbor);
        }
      }
    }
  }
  
  return result;
}

// ==================== BFS ====================
export function bfs(graph: Graph, start: number): number[] {
  const visited = new Set<number>([start]);
  const queue = [start];
  const result: number[] = [];
  
  while (queue.length > 0) {
    const node = queue.shift()!;
    result.push(node);
    
    for (const neighbor of graph[node]) {
      if (!visited.has(neighbor)) {
        visited.add(neighbor);
        queue.push(neighbor);
      }
    }
  }
  
  return result;
}

// ==================== DIJKSTRA'S ALGORITHM ====================
interface WeightedGraph {
  [key: number]: Array<[number, number]>; // [neighbor, weight]
}

export function dijkstra(graph: WeightedGraph, start: number): Record<number, number> {
  const distances: Record<number, number> = {};
  const visited = new Set<number>();
  
  // Initialize distances
  for (const node in graph) {
    distances[node] = Infinity;
  }
  distances[start] = 0;
  
  while (visited.size < Object.keys(graph).length) {
    // Find unvisited node with minimum distance
    let minNode = -1;
    let minDist = Infinity;
    
    for (const node in distances) {
      if (!visited.has(Number(node)) && distances[node] < minDist) {
        minNode = Number(node);
        minDist = distances[node];
      }
    }
    
    if (minNode === -1) break;
    
    visited.add(minNode);
    
    // Update distances of neighbors
    for (const [neighbor, weight] of graph[minNode]) {
      if (!visited.has(neighbor)) {
        distances[neighbor] = Math.min(
          distances[neighbor],
          distances[minNode] + weight
        );
      }
    }
  }
  
  return distances;
}

// ==================== TOPOLOGICAL SORT ====================
export function topologicalSort(graph: Graph): number[] {
  const visited = new Set<number>();
  const stack: number[] = [];
  
  const dfs = (node: number) => {
    visited.add(node);
    
    for (const neighbor of graph[node]) {
      if (!visited.has(neighbor)) {
        dfs(neighbor);
      }
    }
    
    stack.push(node);
  };
  
  for (const node in graph) {
    if (!visited.has(Number(node))) {
      dfs(Number(node));
    }
  }
  
  return stack.reverse();
}

// Kahn's Algorithm (BFS-based)
export function topologicalSortKahn(graph: Graph): number[] {
  const inDegree: Record<number, number> = {};
  const allNodes = new Set<number>();
  
  // Initialize in-degrees
  for (const node in graph) {
    inDegree[node] = 0;
    allNodes.add(Number(node));
  }
  
  for (const node in graph) {
    for (const neighbor of graph[node]) {
      inDegree[neighbor] = (inDegree[neighbor] || 0) + 1;
      allNodes.add(neighbor);
    }
  }
  
  const queue: number[] = [];
  for (const node of allNodes) {
    if ((inDegree[node] || 0) === 0) {
      queue.push(node);
    }
  }
  
  const result: number[] = [];
  
  while (queue.length > 0) {
    const node = queue.shift()!;
    result.push(node);
    
    for (const neighbor of (graph[node] || [])) {
      inDegree[neighbor]--;
      if (inDegree[neighbor] === 0) {
        queue.push(neighbor);
      }
    }
  }
  
  return result.length === allNodes.size ? result : []; // -1 for cycle
}

/**
 * STRING ALGORITHMS - COMPLETE IMPLEMENTATIONS
 */

// ==================== KMP (KNUTH-MORRIS-PRATT) ====================
export function kmpSearch(text: string, pattern: string): number {
  const lps = buildLPS(pattern);
  let i = 0, j = 0;
  
  while (i < text.length) {
    if (pattern[j] === text[i]) {
      i++;
      j++;
    }
    
    if (j === pattern.length) {
      return i - j; // Pattern found
    } else if (i < text.length && pattern[j] !== text[i]) {
      j = j !== 0 ? lps[j - 1] : 0;
      if (pattern[j] === text[i]) {
        i++;
        j++;
      }
    }
  }
  
  return -1; // Pattern not found
}

function buildLPS(pattern: string): number[] {
  const lps = new Array(pattern.length).fill(0);
  let len = 0;
  let i = 1;
  
  while (i < pattern.length) {
    if (pattern[i] === pattern[len]) {
      len++;
      lps[i] = len;
      i++;
    } else {
      if (len !== 0) {
        len = lps[len - 1];
      } else {
        lps[i] = 0;
        i++;
      }
    }
  }
  
  return lps;
}

// ==================== RABIN-KARP ====================
export function rabinKarp(text: string, pattern: string): number {
  const BASE = 256;
  const PRIME = 101;
  const patternHash = hashFunction(pattern, BASE, PRIME);
  
  for (let i = 0; i <= text.length - pattern.length; i++) {
    const windowHash = hashFunction(text.substring(i, i + pattern.length), BASE, PRIME);
    
    if (patternHash === windowHash) {
      if (text.substring(i, i + pattern.length) === pattern) {
        return i;
      }
    }
  }
  
  return -1;
}

function hashFunction(str: string, base: number, prime: number): number {
  let hash = 0;
  let pow = 1;
  
  for (let i = str.length - 1; i >= 0; i--) {
    hash = (hash + str.charCodeAt(i) * pow) % prime;
    pow = (pow * base) % prime;
  }
  
  return hash;
}

/**
 * DYNAMIC PROGRAMMING ALGORITHMS
 */

// ==================== LONGEST COMMON SUBSEQUENCE ====================
export function lcs(s1: string, s2: string): string {
  const m = s1.length, n = s2.length;
  const dp = Array(m + 1).fill(null).map(() => Array(n + 1).fill(0));
  
  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (s1[i - 1] === s2[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1] + 1;
      } else {
        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
      }
    }
  }
  
  // Reconstruct LCS
  let result = '';
  let i = m, j = n;
  
  while (i > 0 && j > 0) {
    if (s1[i - 1] === s2[j - 1]) {
      result = s1[i - 1] + result;
      i--;
      j--;
    } else if (dp[i - 1][j] > dp[i][j - 1]) {
      i--;
    } else {
      j--;
    }
  }
  
  return result;
}

// ==================== EDIT DISTANCE ====================
export function editDistance(s1: string, s2: string): number {
  const m = s1.length, n = s2.length;
  const dp = Array(m + 1).fill(null).map(() => Array(n + 1).fill(0));
  
  for (let i = 0; i <= m; i++) dp[i][0] = i;
  for (let j = 0; j <= n; j++) dp[0][j] = j;
  
  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (s1[i - 1] === s2[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1];
      } else {
        dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
      }
    }
  }
  
  return dp[m][n];
}

/**
 * UTILITY DATA STRUCTURES
 */

// ==================== UNION-FIND ====================
export class UnionFind {
  parent: number[];
  rank: number[];
  
  constructor(n: number) {
    this.parent = Array.from({ length: n }, (_, i) => i);
    this.rank = new Array(n).fill(0);
  }
  
  find(x: number): number {
    if (this.parent[x] !== x) {
      this.parent[x] = this.find(this.parent[x]); // Path compression
    }
    return this.parent[x];
  }
  
  union(x: number, y: number): boolean {
    const rootX = this.find(x);
    const rootY = this.find(y);
    
    if (rootX === rootY) return false;
    
    // Union by rank
    if (this.rank[rootX] < this.rank[rootY]) {
      this.parent[rootX] = rootY;
    } else if (this.rank[rootX] > this.rank[rootY]) {
      this.parent[rootY] = rootX;
    } else {
      this.parent[rootY] = rootX;
      this.rank[rootX]++;
    }
    
    return true;
  }
  
  connected(x: number, y: number): boolean {
    return this.find(x) === this.find(y);
  }
}

// ==================== TRIE ====================
class TrieNode {
  children: Map<string, TrieNode> = new Map();
  isEndOfWord = false;
}

export class Trie {
  root = new TrieNode();
  
  insert(word: string): void {
    let node = this.root;
    
    for (const char of word) {
      if (!node.children.has(char)) {
        node.children.set(char, new TrieNode());
      }
      node = node.children.get(char)!;
    }
    
    node.isEndOfWord = true;
  }
  
  search(word: string): boolean {
    let node = this.root;
    
    for (const char of word) {
      if (!node.children.has(char)) {
        return false;
      }
      node = node.children.get(char)!;
    }
    
    return node.isEndOfWord;
  }
  
  startsWith(prefix: string): boolean {
    let node = this.root;
    
    for (const char of prefix) {
      if (!node.children.has(char)) {
        return false;
      }
      node = node.children.get(char)!;
    }
    
    return true;
  }
}

// ==================== SEGMENT TREE ====================
export class SegmentTree {
  tree: number[];
  n: number;
  
  constructor(arr: number[]) {
    this.n = arr.length;
    this.tree = new Array(4 * this.n).fill(0);
    this.build(arr, 0, 0, this.n - 1);
  }
  
  private build(arr: number[], node: number, start: number, end: number): void {
    if (start === end) {
      this.tree[node] = arr[start];
    } else {
      const mid = Math.floor((start + end) / 2);
      this.build(arr, 2 * node + 1, start, mid);
      this.build(arr, 2 * node + 2, mid + 1, end);
      this.tree[node] = this.tree[2 * node + 1] + this.tree[2 * node + 2];
    }
  }
  
  query(node: number, start: number, end: number, l: number, r: number): number {
    if (r < start || end < l) return 0;
    if (l <= start && end <= r) return this.tree[node];
    
    const mid = Math.floor((start + end) / 2);
    return this.query(2 * node + 1, start, mid, l, r) +
           this.query(2 * node + 2, mid + 1, end, l, r);
  }
  
  rangeSum(l: number, r: number): number {
    return this.query(0, 0, this.n - 1, l, r);
  }
}
