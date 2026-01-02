# Java Interview Questions & Answers Guide
## Topic: COLLECTIONS (Questions 134-166)
### For 2-Year Experienced Java Backend Developers

---

### 134. WHY DO WE NEED COLLECTIONS IN JAVA?

**Expected Answer (2-Year Level):**
Collections provide data structures to store and manipulate groups of objects (lists, sets, maps, queues) with ready-made implementations, freeing developers from implementing common algorithms and allowing code reuse and performance optimizations.

**Key Theoretical Concepts:**
- Abstractions: Collection, List, Set, Map
- Benefits: convenience, performance, consistent APIs

**Interviewer Expectation:**
Should articulate practical needs and examples where arrays aren't sufficient.

**Depth Expected:** Beginner to Intermediate

---

### 135. WHAT ARE THE IMPORTANT INTERFACES IN THE COLLECTION HIERARCHY?

**Expected Answer (2-Year Level):**
Core interfaces: `Collection`, `List`, `Set`, `Queue`, `Deque`, and `Map` (not extending Collection). `SortedSet` and `NavigableSet` add ordering semantics.

**Key Theoretical Concepts:**
- Interface hierarchy and purpose of each

**Interviewer Expectation:**
Should list major interfaces and how they differ.

**Depth Expected:** Intermediate

---

### 136. WHAT ARE THE IMPORTANT METHODS THAT ARE DECLARED IN THE COLLECTION INTERFACE?

**Expected Answer (2-Year Level):**
Common methods: `add()`, `remove()`, `contains()`, `size()`, `isEmpty()`, `iterator()`, `clear()`, `toArray()`.

**Key Theoretical Concepts:**
- Iterator-based traversal
- Bulk operations

**Interviewer Expectation:**
Should know typical operations and their complexity expectations.

**Depth Expected:** Intermediate

---

### 137. CAN YOU EXPLAIN BRIEFLY ABOUT THE LIST INTERFACE?

**Expected Answer (2-Year Level):**
`List` is an ordered collection (sequence) that allows duplicates and positional access via index. Implementations include `ArrayList`, `LinkedList`, and `Vector`.

**Key Theoretical Concepts:**
- Ordered collection, duplicates allowed, index-based access

**Interviewer Expectation:**
Should give examples and typical use-cases.

**Depth Expected:** Intermediate

---

### 138. EXPLAIN ABOUT ARRAYLIST WITH AN EXAMPLE?

**Expected Answer (2-Year Level):**
`ArrayList` is a resizable array implementation of `List`. It provides fast random access (`O(1)`), while insert/remove in middle is `O(n)`. Default initial capacity grows by ~1.5x or 2x depending on JDK.

**Example:**
```java
List<String> list = new ArrayList<>();
list.add("a");
String s = list.get(0);
```

**Key Theoretical Concepts:**
- Backed by array, resizing behavior, amortized cost

**Interviewer Expectation:**
Should know complexity trade-offs and typical operations.

**Depth Expected:** Intermediate

---

### 139. CAN AN ARRAYLIST HAVE DUPLICATE ELEMENTS?

**Expected Answer (2-Year Level):**
Yes. `ArrayList` allows duplicates and preserves insertion order.

**Key Theoretical Concepts:**
- List semantics vs Set semantics

**Interviewer Expectation:**
Simple correctness check.

**Depth Expected:** Beginner

---

### 140. HOW DO YOU ITERATE AROUND AN ARRAYLIST USING ITERATOR?

**Expected Answer (2-Year Level):**
Use `Iterator`:
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
}
```
For removal while iterating use `it.remove()`.

**Key Theoretical Concepts:**
- Fail-fast iterator behavior (ConcurrentModificationException)

**Interviewer Expectation:**
Should show safe removal pattern with iterator.

**Depth Expected:** Intermediate

---

### 141. HOW DO YOU SORT AN ARRAYLIST?

**Expected Answer (2-Year Level):**
Use `Collections.sort(list)` for natural order or `list.sort(comparator)` (Java 8+) for custom comparators. For arrays, use `Arrays.sort()`.

**Example:**
```java
Collections.sort(list); // natural order
Collections.sort(list, Comparator.reverseOrder());
// or
list.sort((a,b) -> a.length() - b.length());
```

**Key Theoretical Concepts:**
- TimSort algorithm used in Java (stable sort)

**Interviewer Expectation:**
Should know API and simple comparator examples.

**Depth Expected:** Intermediate

---

### 142. HOW DO YOU SORT ELEMENTS IN AN ARRAYLIST USING COMPARABLE INTERFACE?

**Expected Answer (2-Year Level):**
Make class implement `Comparable<T>` and implement `compareTo()`; then `Collections.sort(list)` will use natural ordering.

**Example:**
```java
class Person implements Comparable<Person> {
    int age;
    public int compareTo(Person o) {
        return Integer.compare(this.age, o.age);
    }
}
```

**Key Theoretical Concepts:**
- Natural ordering, compareTo contract

**Interviewer Expectation:**
Should write a simple compareTo and mention consistency with equals.

**Depth Expected:** Intermediate

---

### 143. HOW DO YOU SORT ELEMENTS IN AN ARRAYLIST USING COMPARATOR INTERFACE?

**Expected Answer (2-Year Level):**
Provide a `Comparator<T>` implementation and pass it to `Collections.sort(list, comparator)` or `list.sort(comparator)`.

**Example:**
```java
Collections.sort(people, Comparator.comparing(Person::getName));
```

**Key Theoretical Concepts:**
- Flexibility vs natural order
- Null-safe comparators

**Interviewer Expectation:**
Should show lambda-based comparator familiarity (Java 8+).

**Depth Expected:** Intermediate

---

### 144. WHAT IS VECTOR CLASS? HOW IS IT DIFFERENT FROM AN ARRAYLIST?

**Expected Answer (2-Year Level):**
`Vector` is a legacy synchronized list implementation. Differences: `Vector` methods are synchronized (thread-safe) which adds overhead; `ArrayList` is unsynchronized and preferred for single-threaded use.

**Key Theoretical Concepts:**
- Legacy APIs and synchronization overhead

**Interviewer Expectation:**
Should recommend `ArrayList` or `CopyOnWriteArrayList` instead of `Vector` in modern code.

**Depth Expected:** Intermediate

---

### 145. WHAT IS LINKEDLIST? WHAT INTERFACES DOES IT IMPLEMENT? HOW IS IT DIFFERENT FROM AN ARRAYLIST?

**Expected Answer (2-Year Level):**
`LinkedList` is a doubly-linked list implementing `List`, `Deque`, and `Queue`. It offers fast insert/remove at ends (`O(1)`), but slower random access (`O(n)`) compared to `ArrayList`'s `O(1)` get.

**Key Theoretical Concepts:**
- Use-cases for queues/deques, memory overhead per node

**Interviewer Expectation:**
Should compare complexities and mention when to prefer one over the other.

**Depth Expected:** Intermediate

---

### 146. CAN YOU BRIEFLY EXPLAIN ABOUT THE SET INTERFACE?

**Expected Answer (2-Year Level):**
`Set` is a collection that does not allow duplicate elements. Implementations include `HashSet`, `LinkedHashSet`, and `TreeSet`.

**Key Theoretical Concepts:**
- Uniqueness guarantee, no positional access

**Interviewer Expectation:**
Should describe primary use-cases of sets.

**Depth Expected:** Beginner to Intermediate

---

### 147. WHAT ARE THE IMPORTANT INTERFACES RELATED TO THE SET INTERFACE?

**Expected Answer (2-Year Level):**
`SortedSet` and `NavigableSet` add sorted ordering and navigation operations (`lower`, `higher`, `floor`, `ceiling`).

**Key Theoretical Concepts:**
- Ordering semantics and navigation methods

**Interviewer Expectation:**
Should know difference and example implementations.

**Depth Expected:** Intermediate

---

### 148. WHAT IS THE DIFFERENCE BETWEEN SET AND SORTEDSET INTERFACES?

**Expected Answer (2-Year Level):**
`Set` has no ordering guarantee. `SortedSet` maintains elements in sorted order (natural or comparator) and provides range-view operations.

**Key Theoretical Concepts:**
- Sorted vs unsorted collections

**Interviewer Expectation:**
Should know use-cases and performance trade-offs.

**Depth Expected:** Intermediate

---

### 149. CAN YOU GIVE EXAMPLE OF CLASSES THAT IMPLEMENT THE SET INTERFACE?

**Expected Answer (2-Year Level):**
`HashSet`, `LinkedHashSet`, `TreeSet`, and `ConcurrentSkipListSet`.

**Key Theoretical Concepts:**
- Differences: hashing vs linking vs tree-based

**Interviewer Expectation:**
Basic familiarity with common implementations.

**Depth Expected:** Beginner to Intermediate

---

### 150. WHAT IS A HASHSET?

**Expected Answer (2-Year Level):**
`HashSet` is an implementation of `Set` backed by a `HashMap`. It provides constant time `add`, `remove`, and `contains` on average, and does not guarantee order.

**Key Theoretical Concepts:**
- Hashing, equals/hashCode contract

**Interviewer Expectation:**
Should know performance characteristics and collision implications.

**Depth Expected:** Intermediate

---

### 151. WHAT IS A LINKEDHASHSET? HOW IS DIFFERENT FROM A HASHSET?

**Expected Answer (2-Year Level):**
`LinkedHashSet` maintains insertion order by maintaining a doubly-linked list of entries in addition to hashing. It has slightly higher memory overhead but predictable iteration order.

**Key Theoretical Concepts:**
- Ordering via linked list, cost vs benefit

**Interviewer Expectation:**
Should know when ordered set iteration is needed.

**Depth Expected:** Intermediate

---

### 152. WHAT IS A TREESET? HOW IS DIFFERENT FROM A HASHSET?

**Expected Answer (2-Year Level):**
`TreeSet` is a sorted set backed by a Red-Black tree. It keeps elements sorted (logarithmic time for add/remove/contains) and implements `NavigableSet`.

**Key Theoretical Concepts:**
- Balanced tree properties, comparator/natural order

**Interviewer Expectation:**
Should know complexity and ordering guarantees.

**Depth Expected:** Intermediate

---

### 153. CAN YOU GIVE EXAMPLE OF IMPLEMENTATIONS OF NAVIGABLESET?

**Expected Answer (2-Year Level):**
`TreeSet` and `ConcurrentSkipListSet` implement `NavigableSet`.

**Key Theoretical Concepts:**
- Navigable operations and concurrent ordered set

**Depth Expected:** Intermediate

---

### 154. EXPLAIN BRIEFLY ABOUT QUEUE INTERFACE?

**Expected Answer (2-Year Level):**
`Queue` represents a collection designed for holding elements prior to processing, typically FIFO. Implementations include `LinkedList`, `ArrayDeque`, `PriorityQueue`, and blocking queues.

**Key Theoretical Concepts:**
- FIFO semantics, methods `offer`, `poll`, `peek`

**Interviewer Expectation:**
Should differentiate queue methods for throwing vs returning null on failure (`add` vs `offer`).

**Depth Expected:** Intermediate

---

### 155. WHAT ARE THE IMPORTANT INTERFACES RELATED TO THE QUEUE INTERFACE?

**Expected Answer (2-Year Level):**
`Deque` (double-ended queue) and `BlockingQueue` (thread-safe blocking operations) are key related interfaces.

**Key Theoretical Concepts:**
- Deque adds stack and queue behavior, BlockingQueue supports blocking producers/consumers

**Depth Expected:** Intermediate

---

### 156. EXPLAIN ABOUT THE DEQUE INTERFACE?

**Expected Answer (2-Year Level):**
`Deque` supports insertion, removal, and inspection at both ends. Implementations include `ArrayDeque` and `LinkedList`. Use `Deque` for stack (`push`/`pop`) and queue behaviors.

**Key Theoretical Concepts:**
- Double-ended operations, performance considerations

**Interviewer Expectation:**
Should recommend `ArrayDeque` over `Stack` for stack-like behavior.

**Depth Expected:** Intermediate

---

### 157. EXPLAIN THE BLOCKINGQUEUE INTERFACE?

**Expected Answer (2-Year Level):**
`BlockingQueue` adds blocking operations like `put()` and `take()` that wait for space or elements. Useful for producer-consumer concurrency patterns. Implementations include `ArrayBlockingQueue`, `LinkedBlockingQueue`, `SynchronousQueue`.

**Key Theoretical Concepts:**
- Blocking semantics, capacity bounds
- Thread coordination without explicit wait/notify

**Interviewer Expectation:**
Should describe typical usage in thread pools and messaging.

**Depth Expected:** Intermediate

---

### 158. WHAT IS A PRIORITYQUEUE?

**Expected Answer (2-Year Level):**
`PriorityQueue` orders elements according to natural order or a provided comparator. It is not FIFO; removal returns the smallest (or highest priority) element. It's backed by a heap and provides `O(log n)` insert and remove.

**Key Theoretical Concepts:**
- Heap-based implementation, comparator-driven ordering

**Interviewer Expectation:**
Should know semantics and complexity.

**Depth Expected:** Intermediate

---

### 159. CAN YOU GIVE EXAMPLE IMPLEMENTATIONS OF THE BLOCKINGQUEUE INTERFACE?

**Expected Answer (2-Year Level):**
`ArrayBlockingQueue`, `LinkedBlockingQueue`, `PriorityBlockingQueue`, `SynchronousQueue`, `DelayQueue`.

**Key Theoretical Concepts:**
- Bounded vs unbounded queues, synchronous handoff

**Interviewer Expectation:**
Should match implementations to use-cases.

**Depth Expected:** Intermediate

---

### 160. CAN YOU BRIEFLY EXPLAIN ABOUT THE MAP INTERFACE?

**Expected Answer (2-Year Level):**
`Map` maps keys to values with unique keys. Important implementations: `HashMap`, `LinkedHashMap`, `TreeMap`, `ConcurrentHashMap`. `Map` is not a `Collection`.

**Key Theoretical Concepts:**
- Key uniqueness, hashing and ordering, retrieval complexity

**Interviewer Expectation:**
Should know common methods (`put`, `get`, `containsKey`, `entrySet`) and iteration patterns.

**Depth Expected:** Intermediate

---

### 161. WHAT IS DIFFERENCE BETWEEN MAP AND SORTEDMAP?

**Expected Answer (2-Year Level):**
`Map` has no ordering guarantee. `SortedMap` (e.g., `TreeMap`) maintains keys in sorted order and provides navigation methods.

**Key Theoretical Concepts:**
- Ordering semantics and comparator usage

**Depth Expected:** Intermediate

---

### 162. WHAT IS A HASHMAP?

**Expected Answer (2-Year Level):**
`HashMap` is a hash table-based implementation of `Map`. It allows `null` keys/values, offers average `O(1)` get/put, and is not synchronized.

**Key Theoretical Concepts:**
- Hash buckets, load factor, rehashing

**Interviewer Expectation:**
Should know load factor, initial capacity, and `null` behavior.

**Depth Expected:** Intermediate

---

### 163. WHAT ARE THE DIFFERENT METHODS IN A HASH MAP?

**Expected Answer (2-Year Level):**
Common methods: `put()`, `get()`, `remove()`, `containsKey()`, `containsValue()`, `keySet()`, `values()`, `entrySet()`, `putIfAbsent()`, `computeIfAbsent()`, `compute()`, `merge()` (Java 8+).

**Key Theoretical Concepts:**
- Bulk and atomic operations (Java 8 enhancements)

**Interviewer Expectation:**
Should be familiar with modern map utilities for concurrency and compute operations.

**Depth Expected:** Intermediate

---

### 164. WHAT IS A TREEMAP? HOW IS DIFFERENT FROM A HASHMAP?

**Expected Answer (2-Year Level):**
`TreeMap` is a sorted map backed by a Red-Black tree, with `O(log n)` operations and keys ordered by natural order or a comparator. `HashMap` is unordered and faster for average `O(1)` ops.

**Key Theoretical Concepts:**
- Sorted vs unsorted maps, complexity trade-offs

**Interviewer Expectation:**
Should choose appropriate implementation based on ordering needs.

**Depth Expected:** Intermediate

---

### 165. CAN YOU GIVE AN EXAMPLE OF IMPLEMENTATION OF NAVIGABLEMAP INTERFACE?

**Expected Answer (2-Year Level):**
`TreeMap` implements `NavigableMap`. `ConcurrentSkipListMap` is a concurrent implementation.

**Key Theoretical Concepts:**
- Navigation operations and concurrent ordered maps

**Depth Expected:** Intermediate

---

### 166. WHAT ARE THE STATIC METHODS PRESENT IN THE COLLECTIONS CLASS?

**Expected Answer (2-Year Level):**
`Collections` (utility class) has static methods like `sort`, `reverse`, `shuffle`, `binarySearch`, `synchronizedList`, `unmodifiableList`, `emptyList`, `singletonList`, `frequency`, `max`, `min`, `fill`, `copy`.

**Key Theoretical Concepts:**
- Utility wrappers and unmodifiable/synchronized/adapters

**Interviewer Expectation:**
Should be familiar with commonly used utilities and adapters.

**Depth Expected:** Intermediate

---

End of COLLECTIONS (Questions 134-166)
