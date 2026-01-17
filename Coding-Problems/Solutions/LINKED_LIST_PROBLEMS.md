# Linked List Problems - Complete Solutions Guide

## 1. Reverse Linked List

### Question
Given the head of a singly linked list, reverse the list, and return the reversed list.

### Input
```
head = [1, 2, 3, 4, 5]
```

### Expected Output
```
[5, 4, 3, 2, 1]
```

### Explanation
The linked list is reversed iteratively.

### TypeScript Solution
```typescript
class ListNode {
  val: number;
  next: ListNode | null = null;
  constructor(val: number = 0) {
    this.val = val;
  }
}

function reverseLinkedList(head: ListNode | null): ListNode | null {
  let prev: ListNode | null = null;
  let curr = head;
  
  while (curr) {
    const next = curr.next; // Save next node
    curr.next = prev; // Reverse the link
    prev = curr; // Move prev forward
    curr = next; // Move curr forward
  }
  
  return prev;
}

// Test cases
function createLinkedList(arr: number[]): ListNode | null {
  if (arr.length === 0) return null;
  const head = new ListNode(arr[0]);
  let curr = head;
  for (let i = 1; i < arr.length; i++) {
    curr.next = new ListNode(arr[i]);
    curr = curr.next;
  }
  return head;
}

function linkedListToArray(head: ListNode | null): number[] {
  const result: number[] = [];
  let curr = head;
  while (curr) {
    result.push(curr.val);
    curr = curr.next;
  }
  return result;
}

const head = createLinkedList([1, 2, 3, 4, 5]);
const reversed = reverseLinkedList(head);
console.log(linkedListToArray(reversed)); // [5, 4, 3, 2, 1]
```

**Time Complexity:** O(n) - single pass  
**Space Complexity:** O(1) - only pointers

---

## 2. Merge Two Sorted Linked Lists

### Question
Merge two sorted linked lists and return it as a new sorted list.

### Input
```
list1 = [1, 2, 4]
list2 = [1, 3, 4]
```

### Expected Output
```
[1, 1, 2, 3, 4, 4]
```

### Explanation
Merge two sorted lists maintaining order.

### TypeScript Solution
```typescript
function mergeTwoLists(list1: ListNode | null, list2: ListNode | null): ListNode | null {
  const dummy = new ListNode(0);
  let curr = dummy;
  let l1 = list1, l2 = list2;
  
  while (l1 && l2) {
    if (l1.val <= l2.val) {
      curr.next = l1;
      l1 = l1.next;
    } else {
      curr.next = l2;
      l2 = l2.next;
    }
    curr = curr.next;
  }
  
  // Attach remaining nodes
  curr.next = l1 ? l1 : l2;
  
  return dummy.next;
}

// Test cases
const list1 = createLinkedList([1, 2, 4]);
const list2 = createLinkedList([1, 3, 4]);
const merged = mergeTwoLists(list1, list2);
console.log(linkedListToArray(merged)); // [1, 1, 2, 3, 4, 4]
```

**Time Complexity:** O(n + m)  
**Space Complexity:** O(1) - only pointers

---

## 3. Detect Cycle in Linked List

### Question
Given a linked list, determine if it has a cycle.

### Input
```
head = [3, 2, 0, -4], pos = 1 (cycle at index 1)
```

### Expected Output
```
true
```

### Explanation
There is a cycle where node 1 connects back to node 2.

### TypeScript Solution
```typescript
function hasCycle(head: ListNode | null): boolean {
  if (!head || !head.next) return false;
  
  let slow = head, fast = head.next;
  
  while (slow !== fast) {
    if (!fast || !fast.next) return false;
    slow = slow.next;
    fast = fast.next.next;
  }
  
  return true;
}

// Test cases
// Create a list with cycle
const head = createLinkedList([3, 2, 0, -4]);
if (head && head.next) {
  let temp = head;
  while (temp.next) temp = temp.next;
  temp.next = head.next; // Create cycle
  console.log(hasCycle(head)); // true
}

console.log(hasCycle(createLinkedList([1, 2]))); // false
```

**Time Complexity:** O(n) - Floyd's cycle detection  
**Space Complexity:** O(1) - only pointers

---

## 4. Middle of the Linked List

### Question
Given the head of a singly linked list, find the middle node.

### Input
```
head = [1, 2, 3, 4, 5]
```

### Expected Output
```
[3, 4, 5]
```

### Explanation
The middle node is 3, return the list from 3.

### TypeScript Solution
```typescript
function middleOfLinkedList(head: ListNode | null): ListNode | null {
  let slow = head, fast = head;
  
  while (fast && fast.next) {
    slow = slow!.next;
    fast = fast.next.next;
  }
  
  return slow;
}

// Test cases
const head = createLinkedList([1, 2, 3, 4, 5]);
const middle = middleOfLinkedList(head);
console.log(linkedListToArray(middle)); // [3, 4, 5]

const head2 = createLinkedList([1, 2, 3, 4, 5, 6]);
const middle2 = middleOfLinkedList(head2);
console.log(linkedListToArray(middle2)); // [4, 5, 6]
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

## 5. Remove Nth Node From End

### Question
Remove the nth node from the end of the list and return the head.

### Input
```
head = [1, 2, 3, 4, 5], n = 2
```

### Expected Output
```
[1, 2, 3, 5]
```

### Explanation
Remove the 2nd node from end (node with value 4).

### TypeScript Solution
```typescript
function removeNthFromEnd(head: ListNode | null, n: number): ListNode | null {
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

// Test cases
const head = createLinkedList([1, 2, 3, 4, 5]);
const result = removeNthFromEnd(head, 2);
console.log(linkedListToArray(result)); // [1, 2, 3, 5]
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

## 6. Palindrome Linked List

### Question
Check if a singly linked list is a palindrome.

### Input
```
head = [1, 2, 2, 1]
```

### Expected Output
```
true
```

### Explanation
The list reads the same forwards and backwards.

### TypeScript Solution
```typescript
function isPalindromeList(head: ListNode | null): boolean {
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

// Test cases
console.log(isPalindromeList(createLinkedList([1, 2, 2, 1]))); // true
console.log(isPalindromeList(createLinkedList([1, 2]))); // false
console.log(isPalindromeList(createLinkedList([9, 9, 9, 9]))); // true
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

## 7. Linked List Cycle II

### Question
Given a linked list, return the node where the cycle begins. If no cycle, return null.

### Input
```
head = [3, 2, 0, -4], pos = 1
```

### Expected Output
```
Node with value 2
```

### Explanation
The cycle starts at node with value 2.

### TypeScript Solution
```typescript
function detectCycleStart(head: ListNode | null): ListNode | null {
  if (!head || !head.next) return null;
  
  // Detect cycle with Floyd's algorithm
  let slow = head, fast = head;
  
  while (fast && fast.next) {
    slow = slow!.next;
    fast = fast.next.next;
    
    if (slow === fast) {
      // Cycle detected, find start
      let start = head;
      while (start !== slow) {
        start = start!.next;
        slow = slow!.next;
      }
      return start;
    }
  }
  
  return null;
}

// Test cases (cycle detection)
const head = createLinkedList([3, 2, 0, -4]);
if (head && head.next && head.next.next) {
  let temp = head;
  while (temp.next) temp = temp.next;
  temp.next = head.next; // Create cycle at index 1
  console.log(detectCycleStart(head)?.val); // 2
}
```

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

---

## 8. Intersection of Two Linked Lists

### Question
Find the node where two linked lists intersect.

### Input
```
list1 = [4, 1, 8, 4, 5]
list2 = [5, 6, 1, 8, 4, 5]
intersectVal = 8 (at node 8)
```

### Expected Output
```
8
```

### Explanation
The lists intersect at node with value 8.

### TypeScript Solution
```typescript
function getIntersectionNode(
  headA: ListNode | null,
  headB: ListNode | null
): ListNode | null {
  if (!headA || !headB) return null;
  
  let pointerA = headA, pointerB = headB;
  
  // Traverse both lists
  while (pointerA !== pointerB) {
    pointerA = pointerA ? pointerA.next : headB;
    pointerB = pointerB ? pointerB.next : headA;
  }
  
  return pointerA;
}

// Test cases
// Create intersecting lists
const common = createLinkedList([8, 4, 5]);
const headA = createLinkedList([4, 1]);
const headB = createLinkedList([5, 6, 1]);
if (headA) {
  let temp = headA;
  while (temp.next) temp = temp.next;
  temp.next = common;
}
if (headB) {
  let temp = headB;
  while (temp.next) temp = temp.next;
  temp.next = common;
}

console.log(getIntersectionNode(headA, headB)?.val); // 8
```

**Time Complexity:** O(n + m)  
**Space Complexity:** O(1)

