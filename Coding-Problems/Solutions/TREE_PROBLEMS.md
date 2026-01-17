# Tree Problems - Complete Solutions Guide

## 1. Binary Tree Inorder Traversal

### Question
Given the root of a binary tree, return the inorder traversal of its nodes' values.

### Input
```
    1
   / \
  2   3
```

### Expected Output
```
[2, 1, 3]
```

### Explanation
Left subtree, root, right subtree: 2, 1, 3

### TypeScript Solution
```typescript
class TreeNode {
  val: number;
  left: TreeNode | null = null;
  right: TreeNode | null = null;
  constructor(val: number = 0) {
    this.val = val;
  }
}

// Recursive approach
function inorderTraversal(root: TreeNode | null): number[] {
  const result: number[] = [];
  
  function traverse(node: TreeNode | null): void {
    if (!node) return;
    traverse(node.left); // Left
    result.push(node.val); // Root
    traverse(node.right); // Right
  }
  
  traverse(root);
  return result;
}

// Iterative approach
function inorderTraversalIterative(root: TreeNode | null): number[] {
  const result: number[] = [];
  const stack: TreeNode[] = [];
  let current = root;
  
  while (current || stack.length > 0) {
    while (current) {
      stack.push(current);
      current = current.left;
    }
    current = stack.pop()!;
    result.push(current.val);
    current = current.right;
  }
  
  return result;
}

// Test cases
const root = new TreeNode(1);
root.left = new TreeNode(2);
root.right = new TreeNode(3);
console.log(inorderTraversal(root)); // [2, 1, 3]
console.log(inorderTraversalIterative(root)); // [2, 1, 3]
```

**Time Complexity:** O(n) - visit each node once  
**Space Complexity:** O(n) for result, O(h) for recursion stack (h = height)

---

## 2. Maximum Depth of Binary Tree

### Question
Given the root of a binary tree, return its maximum depth.

### Input
```
    3
   / \
  9  20
    /  \
   15   7
```

### Expected Output
```
3
```

### Explanation
Maximum depth from root to deepest leaf is 3.

### TypeScript Solution
```typescript
// Recursive approach
function maxDepth(root: TreeNode | null): number {
  if (!root) return 0;
  return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}

// Iterative BFS approach
function maxDepthBFS(root: TreeNode | null): number {
  if (!root) return 0;
  
  const queue: TreeNode[] = [root];
  let depth = 0;
  
  while (queue.length > 0) {
    depth++;
    const levelSize = queue.length;
    
    for (let i = 0; i < levelSize; i++) {
      const node = queue.shift()!;
      if (node.left) queue.push(node.left);
      if (node.right) queue.push(node.right);
    }
  }
  
  return depth;
}

// Test cases
const root = new TreeNode(3);
root.left = new TreeNode(9);
root.right = new TreeNode(20);
root.right.left = new TreeNode(15);
root.right.right = new TreeNode(7);

console.log(maxDepth(root)); // 3
console.log(maxDepthBFS(root)); // 3
```

**Time Complexity:** O(n) - visit each node  
**Space Complexity:** O(h) for recursion, O(w) for BFS (w = width)

---

## 3. Lowest Common Ancestor of Binary Tree

### Question
Find the lowest common ancestor (LCA) of two nodes in a binary tree.

### Input
```
root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]
p = 5, q = 1
```

### Expected Output
```
3
```

### Explanation
The LCA of 5 and 1 is 3.

### TypeScript Solution
```typescript
function lowestCommonAncestor(root: TreeNode | null, p: TreeNode, q: TreeNode): TreeNode {
  if (!root) return root!;
  
  // If either p or q matches root, root is LCA
  if (root === p || root === q) return root;
  
  // Look for p and q in left and right subtrees
  const left = lowestCommonAncestor(root.left, p, q);
  const right = lowestCommonAncestor(root.right, p, q);
  
  // If both exist in different subtrees, root is LCA
  if (left && right) return root;
  
  // If both exist on same side, return that side
  return left ? left : right!;
}

// Test cases
const root = new TreeNode(3);
root.left = new TreeNode(5);
root.right = new TreeNode(1);
root.left.left = new TreeNode(6);
root.left.right = new TreeNode(2);

const p = root.left; // Node 5
const q = root.right; // Node 1
console.log(lowestCommonAncestor(root, p, q)?.val); // 3
```

**Time Complexity:** O(n) - visit each node  
**Space Complexity:** O(h) - recursion stack

---

## 4. Validate Binary Search Tree

### Question
Determine if a binary tree is a valid BST.

### Input
```
    2
   / \
  1   3
```

### Expected Output
```
true
```

### Explanation
All values satisfy left < node < right.

### TypeScript Solution
```typescript
function isValidBST(root: TreeNode | null): boolean {
  function validate(node: TreeNode | null, min: number, max: number): boolean {
    if (!node) return true;
    
    if (node.val <= min || node.val >= max) return false;
    
    return (
      validate(node.left, min, node.val) &&
      validate(node.right, node.val, max)
    );
  }
  
  return validate(root, -Infinity, Infinity);
}

// Inorder traversal approach
function isValidBSTInorder(root: TreeNode | null): boolean {
  let prev = -Infinity;
  
  function inorder(node: TreeNode | null): boolean {
    if (!node) return true;
    
    if (!inorder(node.left)) return false;
    
    if (node.val <= prev) return false;
    prev = node.val;
    
    return inorder(node.right);
  }
  
  return inorder(root);
}

// Test cases
const root = new TreeNode(2);
root.left = new TreeNode(1);
root.right = new TreeNode(3);

console.log(isValidBST(root)); // true

const invalid = new TreeNode(5);
invalid.left = new TreeNode(1);
invalid.right = new TreeNode(4);
invalid.right.left = new TreeNode(3);
invalid.right.right = new TreeNode(6);
console.log(isValidBST(invalid)); // false
```

**Time Complexity:** O(n)  
**Space Complexity:** O(h)

---

## 5. Binary Tree Level Order Traversal

### Question
Given the root of a binary tree, return the level order traversal (BFS).

### Input
```
    3
   / \
  9  20
    /  \
   15   7
```

### Expected Output
```
[[3], [9, 20], [15, 7]]
```

### Explanation
Return nodes grouped by level.

### TypeScript Solution
```typescript
function levelOrderTraversal(root: TreeNode | null): number[][] {
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

// Test cases
const root = new TreeNode(3);
root.left = new TreeNode(9);
root.right = new TreeNode(20);
root.right.left = new TreeNode(15);
root.right.right = new TreeNode(7);

console.log(levelOrderTraversal(root)); // [[3], [9, 20], [15, 7]]
```

**Time Complexity:** O(n)  
**Space Complexity:** O(w) where w is maximum width

---

## 6. Path Sum

### Question
Given tree and target sum, check if any root-to-leaf path sums to target.

### Input
```
root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]
targetSum = 22
```

### Expected Output
```
true
```

### Explanation
Path [5, 4, 11, 2] sums to 22.

### TypeScript Solution
```typescript
function hasPathSum(root: TreeNode | null, targetSum: number): boolean {
  function dfs(node: TreeNode | null, remaining: number): boolean {
    if (!node) return false;
    
    // Reached leaf node
    if (!node.left && !node.right) {
      return remaining === node.val;
    }
    
    remaining -= node.val;
    
    return (
      dfs(node.left, remaining) ||
      dfs(node.right, remaining)
    );
  }
  
  return dfs(root, targetSum);
}

// Test cases
const root = new TreeNode(5);
root.left = new TreeNode(4);
root.right = new TreeNode(8);
root.left.left = new TreeNode(11);
root.right.left = new TreeNode(13);
root.right.right = new TreeNode(4);
root.left.left.left = new TreeNode(7);
root.left.left.right = new TreeNode(2);
root.right.right.right = new TreeNode(1);

console.log(hasPathSum(root, 22)); // true
```

**Time Complexity:** O(n)  
**Space Complexity:** O(h)

---

## 7. Diameter of Binary Tree

### Question
Given a binary tree, return the diameter (longest path between any two nodes).

### Input
```
      1
     / \
    2   3
   / \
  4   5
```

### Expected Output
```
3
```

### Explanation
Path [4, 2, 1, 3] has length 3.

### TypeScript Solution
```typescript
function diameterOfBinaryTree(root: TreeNode | null): number {
  let diameter = 0;
  
  function height(node: TreeNode | null): number {
    if (!node) return 0;
    
    const left = height(node.left);
    const right = height(node.right);
    
    diameter = Math.max(diameter, left + right);
    
    return 1 + Math.max(left, right);
  }
  
  height(root);
  return diameter;
}

// Test cases
const root = new TreeNode(1);
root.left = new TreeNode(2);
root.right = new TreeNode(3);
root.left.left = new TreeNode(4);
root.left.right = new TreeNode(5);

console.log(diameterOfBinaryTree(root)); // 3
```

**Time Complexity:** O(n)  
**Space Complexity:** O(h)

---

## 8. Balanced Binary Tree

### Question
Check if a binary tree is height-balanced (difference of heights of left and right subtrees ≤ 1).

### Input
```
    3
   / \
  9  20
    /  \
   15   7
```

### Expected Output
```
true
```

### Explanation
All subtrees are balanced.

### TypeScript Solution
```typescript
function isBalanced(root: TreeNode | null): boolean {
  function checkBalance(node: TreeNode | null): [boolean, number] {
    if (!node) return [true, 0];
    
    const [leftBalanced, leftHeight] = checkBalance(node.left);
    if (!leftBalanced) return [false, 0];
    
    const [rightBalanced, rightHeight] = checkBalance(node.right);
    if (!rightBalanced) return [false, 0];
    
    const isBalanced = Math.abs(leftHeight - rightHeight) <= 1;
    const height = 1 + Math.max(leftHeight, rightHeight);
    
    return [isBalanced, height];
  }
  
  return checkBalance(root)[0];
}

// Test cases
const root = new TreeNode(3);
root.left = new TreeNode(9);
root.right = new TreeNode(20);
root.right.left = new TreeNode(15);
root.right.right = new TreeNode(7);

console.log(isBalanced(root)); // true
```

**Time Complexity:** O(n)  
**Space Complexity:** O(h)

