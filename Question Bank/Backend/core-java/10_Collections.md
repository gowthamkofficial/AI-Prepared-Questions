I will maintain the exact wording and structure of the **Interviewer Answers** and **Theoretical Keywords** as provided in your list.

Here are the **Collections (Questions 134-166)** formatted for clarity:

---

# COLLECTIONS (QUESTIONS 134-166)

## 134. Why do we need collections in Java?

**Interviewer Answer:**

* Store and manipulate groups of objects efficiently
* Overcome limitations of arrays (fixed size, homogeneous types)
* Provide ready-made data structures and algorithms
* Improve code reusability and maintainability
* Handle dynamic data (add/remove elements at runtime)
* Support different access patterns (sequential, random, key-value)
* Thread-safe options available for concurrent programming

**Theoretical Keywords:**
Dynamic storage, Data structures, Algorithms, Code reuse, Type safety, Performance optimization

---

## 135. What are the important interfaces in the collection hierarchy?

**Interviewer Answer:**

```text
Iterable (top-level)
└── Collection (root interface for collections)
    ├── List (ordered, indexed, allows duplicates)
    ├── Set (unique elements, no duplicates) 
    │   ├── SortedSet → NavigableSet (sorted unique)
    └── Queue (FIFO/LIFO processing)
        ├── Deque (double-ended queue)
        └── BlockingQueue (thread-safe)

Map (separate hierarchy - key-value pairs)
├── SortedMap → NavigableMap (sorted keys)

```

**Theoretical Keywords:**
Collection interface, List/Set/Queue, Map interface, Hierarchy structure, Iterable root

---

## 136. What are the important methods that are declared in the Collection interface?

**Interviewer Answer:**

* **Basic operations:** `add(E e)`, `remove(Object o)`, `contains(Object o)`, `size()`, `isEmpty()`
* **Bulk operations:** `addAll(Collection c)`, `removeAll(Collection c)`, `retainAll(Collection c)`, `clear()`
* **Array operations:** `toArray()` - convert to array
* **Iteration:** `iterator()` - returns Iterator
* **Java 8+:** `stream()`, `parallelStream()`
* **Comparison:** `equals(Object o)`, `hashCode()`

**Theoretical Keywords:**
CRUD operations, Bulk operations, Array conversion, Iterator pattern, Stream API

---

## 137. Can you explain briefly about the List interface?

**Interviewer Answer:**

* Ordered collection (sequence) that maintains insertion order
* Allows duplicate elements
* Index-based access (get/set by position)
* Can have `null` elements
* Main implementations: `ArrayList`, `LinkedList`, `Vector`
* Methods: `get(int index)`, `set(int index, E element)`, `add(int index, E element)`
* Example: Shopping cart items, student names list

**Theoretical Keywords:**
Ordered collection, Duplicates allowed, Index access, Position-based operations, ArrayList/LinkedList

---

## 138. Explain about ArrayList with an example.

**Interviewer Answer:**

* Resizable array implementation of List interface
* Fast random access ( for get/set)
* Slow insertions/deletions in middle (need to shift elements)
* Default initial capacity: 10, grows by 50% when full
* Not synchronized (use `Collections.synchronizedList()` for thread safety)
* **Example:**

```java
List<String> names = new ArrayList<>();
names.add("Alice");  // Adds at end
names.add("Bob");
names.add(1, "Charlie");  // Inserts at index 1
String name = names.get(0);  // "Alice" - O(1) access
names.remove(1);  // Removes "Charlie"

```

**Theoretical Keywords:**
Resizable array, Random access O(1), Amortized growth, Not thread-safe, Index operations

---

## 139. Can an ArrayList have duplicate elements?

**Interviewer Answer:**

* Yes, ArrayList allows duplicate elements
* List interface specifically allows duplicates
* Elements are stored in insertion order
* Duplicates are treated as separate elements
* Example: `[1, 2, 1, 3]` is valid ArrayList
* Use `Set` if you need unique elements

**Theoretical Keywords:**
Duplicates allowed, List contract, Insertion order, Separate elements, Set alternative

---

## 140. How do you iterate an ArrayList using an Iterator?

**Interviewer Answer:**

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

// Method 1: Using Iterator explicitly
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String element = iterator.next();
    System.out.println(element);
    if (element.equals("B")) {
        iterator.remove(); // Safe removal during iteration
    }
}

// Method 2: Enhanced for loop (uses Iterator internally)
for (String element : list) {
    System.out.println(element);
    // Cannot call remove() here
}

```

**Theoretical Keywords:**
Iterator pattern, hasNext()/next(), Safe removal, ConcurrentModificationException, Enhanced for loop

---

## 141. How do you sort an ArrayList?

**Interviewer Answer:**

```java
List<Integer> numbers = new ArrayList<>();
numbers.add(5);
numbers.add(2);
numbers.add(8);

// Method 1: Collections.sort() - natural ordering
Collections.sort(numbers);  // [2, 5, 8]

// Method 2: List.sort() (Java 8+)
numbers.sort(Comparator.naturalOrder());

// Method 3: With custom Comparator
Collections.sort(numbers, Comparator.reverseOrder());  // [8, 5, 2]

// Method 4: Stream API (creates new sorted list)
List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());

```

**Theoretical Keywords:**
Collections.sort(), List.sort(), Natural ordering, Comparator, Stream API, In-place sorting

---

## 142. How do you sort elements in an ArrayList using Comparable?

**Interviewer Answer:**

* Make element class implement `Comparable<T>` interface
* Override `compareTo(T o)` method
* Use `Collections.sort()` or `List.sort()` without Comparator
* **Example:**

```java
class Student implements Comparable<Student> {
    private String name;
    private int grade;
    
    @Override
    public int compareTo(Student other) {
        // Natural ordering by grade
        return Integer.compare(this.grade, other.grade);
    }
}
List<Student> students = new ArrayList<>();
students.add(new Student("Alice", 85));
students.add(new Student("Bob", 75));
Collections.sort(students); // Sorted by grade automatically

```

**Theoretical Keywords:**
Comparable interface, compareTo() method, Natural ordering, Class implementation, Automatic sorting

---

## 143. How do you sort elements in an ArrayList using Comparator?

**Interviewer Answer:**

```java
class Student {
    private String name;
    private int grade;
    // constructor, getters
}
List<Student> students = new ArrayList<>();

// Method 1: Anonymous Comparator class
Collections.sort(students, new Comparator<Student>() {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName());
    }
});

// Method 2: Lambda expression (Java 8+)
Collections.sort(students, (s1, s2) -> s1.getName().compareTo(s2.getName()));

// Method 3: Comparator.comparing() (Java 8+)
Collections.sort(students, Comparator.comparing(Student::getName));

// Method 4: Multiple criteria
Collections.sort(students, 
    Comparator.comparing(Student::getGrade)
              .thenComparing(Student::getName));

```

**Theoretical Keywords:**
Comparator interface, compare() method, Lambda expressions, Method references, Multiple criteria

---

## 144. What is Vector class? How is it different from an ArrayList?

**Interviewer Answer:**

| Feature | Vector | ArrayList |
| --- | --- | --- |
| **Synchronization** | Synchronized (thread-safe) | Not synchronized (faster) |
| **Legacy** | Legacy class (Java 1.0) | Newer (Java 1.2) |
| **Growth** | Default growth: doubles size | Default growth: increases by 50% |
| **Iteration** | Enumeration for iteration | Iterator/ListIterator |
| **Performance** | Slower due to synchronization | Faster in single-threaded |
| **Modern Use** | Still part of Collections Framework | Preferred in most cases |

* Use `ArrayList` unless thread safety needed (then use `Collections.synchronizedList()`)

**Theoretical Keywords:**
Synchronized, Thread-safe, Legacy class, Enumeration, Performance trade-off

---

## 145. What is LinkedList? What interfaces does it implement? How is it different from an ArrayList?

**Interviewer Answer:**

* **LinkedList:** Doubly-linked list implementation of List and Deque interfaces
* **Implements:** `List`, `Deque` (and thus `Queue`)
* **Differences from ArrayList:**
* **Structure:** Linked nodes vs contiguous array
* **Random access:**  vs  for ArrayList
* **Insert/Delete:**  at ends vs  for ArrayList
* **Memory:** More overhead (node objects) vs less


* **Use LinkedList when:** Frequent insertions/deletions, using as Queue/Deque
* **Use ArrayList when:** Frequent random access, less modifications

**Theoretical Keywords:**
Doubly-linked list, List + Deque, Node structure, O(1) insert/delete, Memory overhead

---

## 146. Can you briefly explain about the Set interface?

**Interviewer Answer:**

* Collection that contains no duplicate elements
* Models mathematical set abstraction
* No positional access (no `get(index)` method)
* At most one `null` element allowed
* Main implementations: `HashSet`, `LinkedHashSet`, `TreeSet`
* Equality based on `equals()` method
* Example: Unique user IDs, dictionary words

**Theoretical Keywords:**
No duplicates, Mathematical set, No index access, Unique elements, equals()/hashCode()

---

## 147. What are the important interfaces related to the Set interface?

**Interviewer Answer:**

```text
Set (basic set operations)
├── SortedSet (elements in sorted order)
│   └── NavigableSet (navigation methods)
└── (Direct implementations: HashSet, LinkedHashSet)

```

* **SortedSet:** Maintains elements in sorted order (natural or Comparator)
* **NavigableSet:** Extends SortedSet with navigation methods: `lower(E)`, `floor(E)`, `ceiling(E)`, `higher(E)`, `pollFirst()`, `pollLast()`, `descendingSet()`

**Theoretical Keywords:**
SortedSet, NavigableSet, Sorted order, Navigation methods, TreeSet implementation

---

## 148. What is the difference between Set and SortedSet interfaces?

**Interviewer Answer:**

| Feature | Set | SortedSet |
| --- | --- | --- |
| **Ordering** | No ordering guarantee | Elements in sorted order |
| **Operations** | Basic set operations | All Set operations + sorting |
| **Constraint** | Equality by `equals()` | Ordering by `compareTo()`/Comparator |
| **Implementations** | HashSet, LinkedHashSet | TreeSet |
| **Navigation** | No navigation methods | Navigation methods (subset, headSet, tailSet) |

* SortedSet extends Set, adding sorting capability

**Theoretical Keywords:**
Ordering guarantee, Sorting capability, Comparator/Comparable, Navigation methods, TreeSet

---

## 149. Can you give examples of classes that implement the Set interface?

**Interviewer Answer:**

* **HashSet:** Hash table implementation, fastest, no ordering
* **LinkedHashSet:** Hash table + linked list, insertion order
* **TreeSet:** Red-Black tree, sorted order (natural or Comparator)
* **EnumSet:** Specialized for enum types, very efficient
* **CopyOnWriteArraySet:** Thread-safe, snapshot iteration
* **ConcurrentSkipListSet:** Concurrent, sorted

**Theoretical Keywords:**
HashSet, LinkedHashSet, TreeSet, EnumSet, Concurrent implementations, Specialized sets

---

## 150. What is a HashSet?

**Interviewer Answer:**

* Hash table implementation of Set interface
* Backed by HashMap instance
* No guarantees about iteration order
* Allows `null` element
* Constant time performance for basic operations (add, remove, contains)
* Performance depends on load factor and initial capacity
* **Example:**

```java
Set<String> uniqueNames = new HashSet<>();
uniqueNames.add("Alice");
uniqueNames.add("Bob");
uniqueNames.add("Alice");  // Ignored - duplicate
System.out.println(uniqueNames);  // [Alice, Bob] (order not guaranteed)

```

**Theoretical Keywords:**
Hash table implementation, No ordering, Constant time O(1), HashMap backing, Load factor

---

## 151. What is a LinkedHashSet? How is it different from a HashSet?

**Interviewer Answer:**

* **LinkedHashSet:** Hash table + linked list implementation
* Maintains insertion order (doubly-linked list)
* Slightly slower than HashSet due to linked list overhead
* Still provides  performance for basic operations
* **Difference from HashSet:**
* Predictable iteration order (insertion order)
* Slightly more memory overhead
* Slightly slower due to linked list maintenance


* Use when you need Set uniqueness with predictable iteration order

**Theoretical Keywords:**
Linked list + hash table, Insertion order, Predictable iteration, Memory overhead, Order preservation

---

## 152. What is a TreeSet? How is it different from a HashSet?

**Interviewer Answer:**

* **TreeSet:** Red-Black tree implementation of NavigableSet
* Elements stored in sorted order (natural or Comparator)
* **Differences from HashSet:**
* **Ordering:** Sorted vs no ordering
* **Performance:**  vs 
* **Null elements:** Not allowed vs allowed
* **Implementation:** Tree vs Hash table
* **Operations:** Additional navigation methods


* Use when you need sorted unique elements

**Theoretical Keywords:**
Red-Black tree, Sorted order, O(log n) operations, NavigableSet, No nulls allowed

---

## 153. Can you give examples of implementations of NavigableSet?

**Interviewer Answer:**

* **TreeSet:** Primary implementation
* **ConcurrentSkipListSet:** Thread-safe concurrent implementation
* **TreeSet example with navigation:**

```java
NavigableSet<Integer> numbers = new TreeSet<>();
numbers.addAll(Arrays.asList(10, 20, 30, 40, 50));
System.out.println(numbers.lower(30));   // 20 (greatest < 30)
System.out.println(numbers.higher(30));  // 40 (least > 30)
System.out.println(numbers.floor(25));   // 20 (greatest ≤ 25)
System.out.println(numbers.ceiling(25)); // 30 (least ≥ 25)
NavigableSet<Integer> headSet = numbers.headSet(30, true);  // ≤ 30
NavigableSet<Integer> tailSet = numbers.tailSet(30, false); // > 30

```

**Theoretical Keywords:**
TreeSet, ConcurrentSkipListSet, Navigation methods, Subset operations, Sorted navigation

---

## 154. Explain briefly about the Queue interface.

**Interviewer Answer:**

* Designed for holding elements prior to processing
* Typically FIFO (First-In-First-Out) but not always
* **Core operations:**
* `offer(E e)` - insert, returns false if full
* `poll()` - remove and return head, returns null if empty
* `peek()` - examine head without removing


* **Throws-exception versions:**
* `add(E e)` - throws exception if full
* `remove()` - throws if empty
* `element()` - throws if empty


* Implementations: `LinkedList`, `PriorityQueue`, `ArrayDeque`

**Theoretical Keywords:**
FIFO typically, Element processing, Insert/remove/examine, PriorityQueue, BlockingQueue

---

## 155. What are the important interfaces related to the Queue interface?

**Interviewer Answer:**

```text
Queue (basic queue)
├── Deque (double-ended queue)
│   ├── ArrayDeque (array implementation)
│   └── LinkedList (list implementation)
├── BlockingQueue (thread-safe with capacity)
│   ├── ArrayBlockingQueue
│   ├── LinkedBlockingQueue
│   └── PriorityBlockingQueue
└── TransferQueue (extends BlockingQueue)
    └── LinkedTransferQueue

```

* **Deque:** Insert/remove from both ends
* **BlockingQueue:** Thread-safe, blocking operations
* **TransferQueue:** Producer waits for consumer

**Theoretical Keywords:**
Deque, BlockingQueue, TransferQueue, Double-ended, Thread-safe, Capacity bounds

---

## 156. Explain the Deque interface.

**Interviewer Answer:**

* "Double-ended queue" - insert/remove from both ends
* Can be used as Stack (LIFO) or Queue (FIFO)
* **Main methods:**
* **First end:** `addFirst()`, `offerFirst()`, `removeFirst()`, `pollFirst()`, `getFirst()`, `peekFirst()`
* **Last end:** `addLast()`, `offerLast()`, `removeLast()`, `pollLast()`, `getLast()`, `peekLast()`


* **Stack operations:** `push(E)` = `addFirst()`, `pop()` = `removeFirst()`, `peek()` = `peekFirst()`
* Implementations: `ArrayDeque`, `LinkedList`

**Theoretical Keywords:**
Double-ended queue, Stack/Queue usage, ArrayDeque, LinkedList, LIFO/FIFO

---

## 157. Explain the BlockingQueue interface.

**Interviewer Answer:**

* Thread-safe Queue with capacity bounds
* Blocking operations: waits for space/availability
* **Key methods:**
* `put(E e)`: Inserts, waits if full
* `take()`: Removes, waits if empty
* `offer(E e, long timeout, TimeUnit unit)`: Timed wait
* `poll(long timeout, TimeUnit unit)`: Timed wait


* Used in producer-consumer patterns
* Implementations: `ArrayBlockingQueue`, `LinkedBlockingQueue`

**Theoretical Keywords:**
Thread-safe, Capacity bound, Blocking operations, Producer-consumer, Timed waits

---

## 158. What is a PriorityQueue?

**Interviewer Answer:**

* Queue where elements are ordered by natural ordering or Comparator
* Head is least element according to ordering
* Not FIFO - elements processed by priority
* Implemented as priority heap (min-heap by default)
* Not thread-safe
* **Example:**

```java
Queue<Integer> pq = new PriorityQueue<>();
pq.offer(5);
pq.offer(1);
pq.offer(3);
System.out.println(pq.poll()); // 1 (smallest)
System.out.println(pq.poll()); // 3
System.out.println(pq.poll()); // 5

```

**Theoretical Keywords:**
Priority ordering, Heap implementation, Natural ordering/Comparator, Not FIFO, Task scheduling

---

## 159. Can you give example implementations of the BlockingQueue interface?

**Interviewer Answer:**

* **ArrayBlockingQueue:** Bounded, array-backed, FIFO

```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

```

* **LinkedBlockingQueue:** Optional bounded, linked nodes, FIFO

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(); // unbounded
BlockingQueue<String> bounded = new LinkedBlockingQueue<>(100);

```

* **PriorityBlockingQueue:** Unbounded, priority ordering

```java
BlockingQueue<String> queue = new PriorityBlockingQueue<>();

```

* **DelayQueue:** Elements taken after delay elapsed
* **SynchronousQueue:** Direct handoff between threads

**Theoretical Keywords:**
ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue, DelayQueue, SynchronousQueue

---

## 160. Can you briefly explain about the Map interface?

**Interviewer Answer:**

* Object that maps keys to values (key-value pairs)
* Cannot contain duplicate keys
* Each key maps to at most one value
* **Basic operations:** `put(K, V)`, `get(K)`, `remove(K)`, `containsKey(K)`
* **Three collection views:** `keySet()`, `values()`, `entrySet()`
* Implementations: `HashMap`, `TreeMap`, `LinkedHashMap`

**Theoretical Keywords:**
Key-value mapping, No duplicate keys, Collection views, HashMap/TreeMap, Cache implementation

---

## 161. What is the difference between Map and SortedMap?

**Interviewer Answer:**

| Feature | Map | SortedMap |
| --- | --- | --- |
| **Ordering** | No ordering guarantee | Keys in sorted order |
| **Operations** | Basic map operations | Navigation methods |
| **Constraint** | Equality by `equals()` | Ordering by `compareTo()`/Comparator |
| **Implementations** | HashMap, LinkedHashMap | TreeMap |
| **Nulls** | Allows null keys/values (depends) | May restrict nulls |

* SortedMap extends Map, adding sorting by keys
* Provides `subMap()`, `headMap()`, `tailMap()` methods

**Theoretical Keywords:**
Key ordering, Navigation methods, Comparator/Comparable, TreeMap, Submap operations

---

## 162. What is a HashMap?

**Interviewer Answer:**

* Hash table implementation of Map interface
* Allows one `null` key and multiple `null` values
* No ordering guarantees
* Constant time  for get/put (assuming good hash function)
* Backed by array of buckets (linked lists/ trees in Java 8+)
* **Important parameters:**
* Initial capacity: default 16
* Load factor: default 0.75 (when to resize)



**Theoretical Keywords:**
Hash table, O(1) operations, Bucket array, Load factor, Null key allowed, Java 8 tree bins

---

## 163. What are the different methods in a HashMap?

**Interviewer Answer:**

* **Basic operations:** `put(K key, V value)`, `get(Object key)`, `remove(Object key)`, `containsKey(Object key)`, `containsValue(Object value)`
* **Bulk operations:** `putAll(Map m)`, `clear()`
* **Collection views:** `keySet()` (Set of keys), `values()` (Collection of values), `entrySet()` (Set of Map.Entry objects)
* **Java 8+ enhancements:** `getOrDefault()`, `putIfAbsent()`, `compute()`, `merge()`
* **Utility:** `size()`, `isEmpty()`

**Theoretical Keywords:**
CRUD operations, Collection views, Map.Entry, Java 8 methods, Default methods

---

## 164. What is a TreeMap? How is it different from a HashMap?

**Interviewer Answer:**

* **TreeMap:** Red-Black tree implementation of NavigableMap
* Keys stored in sorted order (natural or Comparator)
* **Differences from HashMap:**
* **Ordering:** Sorted keys vs no ordering
* **Performance:**  vs 
* **Null keys:** Not allowed vs allowed
* **Implementation:** Tree vs Hash table
* **Operations:** Navigation methods available


* Use when you need sorted key-value pairs

**Theoretical Keywords:**
Red-Black tree, Sorted keys, O(log n) operations, NavigableMap, No null keys

---

## 165. Can you give an example of implementation of NavigableMap interface?

**Interviewer Answer:**

* **TreeMap** is the primary implementation

```java
NavigableMap<Integer, String> map = new TreeMap<>();
map.put(10, "Ten");
map.put(20, "Twenty");
map.put(30, "Thirty");
map.put(40, "Forty");
System.out.println(map.lowerKey(25));  // 20
System.out.println(map.higherKey(25)); // 30
System.out.println(map.floorKey(25));  // 20
System.out.println(map.ceilingKey(25)); // 30
// Navigation
NavigableMap<Integer, String> headMap = map.headMap(30, true);  // keys ≤ 30
NavigableMap<Integer, String> tailMap = map.tailMap(30, false); // keys > 30
NavigableMap<Integer, String> subMap = map.subMap(20, true, 40, false);
// Reverse order view
NavigableMap<Integer, String> descending = map.descendingMap();

```

**Theoretical Keywords:**
TreeMap implementation, Key navigation, Submap operations, Descending view, Sorted navigation

---

## 166. What are the static methods present in the Collections class?

**Interviewer Answer:**

* **Sorting:** `sort(List)`, `sort(List, Comparator)`
* **Searching:** `binarySearch(List, key)`, `binarySearch(List, key, Comparator)`
* **Synchronization wrappers:** `synchronizedList(List)`, `synchronizedSet(Set)`, `synchronizedMap(Map)`
* **Unmodifiable wrappers:** `unmodifiableList(List)`, `unmodifiableSet(Set)`, `unmodifiableMap(Map)`
* **Utility:** `reverse(List)`, `shuffle(List)`, `swap(List, i, j)`, `fill(List, obj)`, `copy(List dest, List src)`, `min(Collection)`, `max(Collection)`, `frequency(Collection, obj)`
* **Singleton:** `singleton(T)`, `singletonList(T)`, `singletonMap(K, V)`
* **Empty collections:** `emptyList()`, `emptySet()`, `emptyMap()`

**Theoretical Keywords:**
Utility methods, Sorting/searching, Synchronization wrappers, Unmodifiable views, Singleton collections

---

Would you like me to continue with the next set of questions, or shall we dive deeper into one of these collection types?