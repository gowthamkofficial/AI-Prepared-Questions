# **ADVANCED COLLECTIONS ANSWERS**

## **Advanced Collections (Questions 167-177)**

### **167. What is the difference between synchronized and concurrent collections in Java?**
**Interviewer Answer:**
| **Synchronized Collections** | **Concurrent Collections** |
|-----------------------------|----------------------------|
| Legacy approach (Java 1.2) | New approach (Java 5+) |
| Use external synchronization | Built-in thread safety |
| Coarse-grained locking | Fine-grained locking or lock-free |
| Poor performance under contention | Better scalability |
| Obtained via `Collections.synchronizedX()` | Classes in `java.util.concurrent` |
| Iteration requires external sync | Iterators are weakly consistent |
| Example: `Collections.synchronizedList()` | Example: `ConcurrentHashMap` |

**Key Insight**: Synchronized collections lock the entire collection during operations, while concurrent collections use advanced techniques (like lock striping in ConcurrentHashMap) for better performance.

**Theoretical Keywords:**
External vs internal synchronization, Coarse-grained vs fine-grained locking, Performance under contention, Weakly consistent iterators, java.util.concurrent package

### **168. Explain the new concurrent collections in Java.**
**Interviewer Answer:**
Java 5+ introduced these concurrent collections in `java.util.concurrent`:

1. **ConcurrentHashMap**: Thread-safe HashMap alternative using lock striping
2. **CopyOnWriteArrayList**: Thread-safe List for read-heavy scenarios
3. **CopyOnWriteArraySet**: Thread-safe Set based on CopyOnWriteArrayList
4. **ConcurrentSkipListMap**: Thread-safe sorted Map (Concurrent TreeMap)
5. **ConcurrentSkipListSet**: Thread-safe sorted Set
6. **BlockingQueue implementations**:
   - `ArrayBlockingQueue`: Bounded array-backed
   - `LinkedBlockingQueue`: Optional bounded linked nodes
   - `PriorityBlockingQueue`: Unbounded priority-based
   - `DelayQueue`: Elements with delay
   - `SynchronousQueue`: Direct handoff
7. **ConcurrentLinkedQueue**: Unbounded thread-safe queue (non-blocking)
8. **ConcurrentLinkedDeque**: Thread-safe double-ended queue

**Example**:
```java
// Thread-safe map without external synchronization
ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("key1", 100);
map.putIfAbsent("key1", 200); // Won't replace existing

// Thread-safe list
List<String> list = new CopyOnWriteArrayList<>();
list.add("item1"); // Creates new array copy
```

**Theoretical Keywords:**
java.util.concurrent package, Lock striping, Copy-on-write, Non-blocking algorithms, Skip lists, Thread-safe collections

### **169. Explain the copy-on-write concurrent collections approach.**
**Interviewer Answer:**
- **Concept**: Whenever collection is modified, create a new copy of underlying array
- **Read operations**: Work on snapshot (no locking needed)
- **Write operations**: Create new copy, then atomically replace reference
- **Best for**: Read-heavy, write-rarely scenarios
- **Implementations**: `CopyOnWriteArrayList`, `CopyOnWriteArraySet`

**How it works**:
```java
// Under the hood of CopyOnWriteArrayList
public boolean add(E element) {
    synchronized(lock) {
        Object[] newElements = Arrays.copyOf(elements, elements.length + 1);
        newElements[newElements.length - 1] = element;
        elements = newElements; // Atomic reference update
        return true;
    }
}

public E get(int index) {
    return elements[index]; // No synchronization - reading snapshot
}
```

**Pros**: 
- Excellent read performance (no locks)
- Thread-safe for iteration (iterator uses snapshot)
- Simple implementation

**Cons**:
- Memory overhead (copying on writes)
- Write operations are expensive
- Not suitable for write-heavy scenarios
- Iterator doesn't reflect concurrent modifications

**Theoretical Keywords:**
Snapshot iteration, Read optimization, Write overhead, Memory consumption, Atomic reference swap, Write-rarely pattern

### **170. What is compare-and-swap approach?**
**Interviewer Answer:**
- **CAS**: Atomic operation that updates a value only if it matches expected value
- **Formula**: `CAS(address, expectedValue, newValue)`
- Returns true if update succeeded (value was as expected)
- Foundation for lock-free/non-blocking algorithms
- Implemented at hardware level (CPU instructions)

**Java Implementation**:
```java
// In java.util.concurrent.atomic package
AtomicInteger atomicInt = new AtomicInteger(0);

// Traditional approach (synchronized)
public synchronized void increment() {
    value++;
}

// CAS approach
public void increment() {
    int current;
    do {
        current = atomicInt.get();  // Read current value
    } while (!atomicInt.compareAndSet(current, current + 1));
}

// Or simply:
atomicInt.incrementAndGet(); // Uses CAS internally
```

**Benefits over locking**:
- No thread blocking (higher throughput)
- No deadlocks
- Better scalability on multi-core systems

**Used in**: `AtomicInteger`, `AtomicLong`, `AtomicReference`, `ConcurrentHashMap`

**Theoretical Keywords:**
Non-blocking algorithm, Lock-free, Hardware atomic instruction, ABA problem, Optimistic concurrency, Atomic variables

### **171. What is a lock? How is it different from using `synchronized`?**
**Interviewer Answer:**
| **synchronized** | **Lock Interface** |
|-----------------|-------------------|
| Built-in keyword | Interface in `java.util.concurrent.locks` |
| Implicit release | Explicit release (must use try-finally) |
| No timeout support | Supports timed and interruptible lock attempts |
| Always reentrant | Can be non-reentrant (e.g., `StampedLock`) |
| No fairness guarantee | Can create fair locks |
| Simpler syntax | More features and control |

**Lock Example**:
```java
Lock lock = new ReentrantLock();

lock.lock(); // Can use lock.tryLock(10, TimeUnit.SECONDS)
try {
    // Critical section
    sharedResource.modify();
} finally {
    lock.unlock(); // MUST be in finally block
}
```

**Advanced Lock Types**:
1. **ReentrantLock**: Similar to synchronized but with extra features
2. **ReadWriteLock**: Allows multiple readers or single writer
3. **StampedLock**: Optimistic reading, better performance

**When to use Lock over synchronized**:
- Need timeout or interruptible locking
- Need fair ordering
- Need non-block-structured locking (lock/unlock in different methods)
- Need read-write locking

**Theoretical Keywords:**
Explicit vs implicit locking, Reentrancy, Fairness, Timed lock attempts, ReadWriteLock, Condition objects

### **172. What is initial capacity of a Java collection?**
**Interviewer Answer:**
- Number of buckets/elements allocated when collection is created
- Affects memory usage vs performance trade-off
- **Default values**:
  - `ArrayList`: 10
  - `HashMap`: 16
  - `HashSet`: 16 (backed by HashMap)
  - `Vector`: 10
  - `Hashtable`: 11
- **Setting initial capacity**:
```java
// Pre-allocate for known size
List<String> list = new ArrayList<>(1000); // Avoids reallocation
Map<String, Integer> map = new HashMap<>(64); // Sets bucket count
```
- **Too small**: Frequent resizing (performance hit)
- **Too large**: Wasted memory
- **Rule of thumb**: Estimate maximum size, add 25% buffer

**Theoretical Keywords:**
Pre-allocation, Performance optimization, Memory trade-off, Resizing overhead, Bucket allocation

### **173. What is load factor?**
**Interviewer Answer:**
- Measure of how full a hash-based collection can get before resizing
- Ratio: `size / capacity`
- **Default values**:
  - `HashMap`: 0.75
  - `HashSet`: 0.75
  - `Hashtable`: 0.75
- **What happens when load factor is reached**:
  1. Collection creates new, larger array (usually double)
  2. Rehashes all elements to new buckets
  3. This is expensive operation (O(n))

**Trade-off**:
- **High load factor** (e.g., 0.9): Less memory, more collisions
- **Low load factor** (e.g., 0.5): More memory, fewer collisions

**Example**:
```java
// HashMap with capacity 16, load factor 0.75
// Resizes when 12 elements are added (16 * 0.75)

Map<String, Integer> map = new HashMap<>(16, 0.8f);
// Will resize at 13 elements (16 * 0.8)

Map<String, Integer> denseMap = new HashMap<>(16, 1.0f);
// Will resize only when completely full
```

**Theoretical Keywords:**
Hash collision management, Resizing threshold, Performance/memory trade-off, Rehashing cost, Bucket density

### **174. When does a Java collection throw `UnsupportedOperationException`?**
**Interviewer Answer:**
- Thrown by optional operations that are not supported by a collection
- **Common scenarios**:

1. **Unmodifiable collections** (from `Collections.unmodifiableX()`):
   ```java
   List<String> unmodifiable = Collections.unmodifiableList(list);
   unmodifiable.add("new"); // UnsupportedOperationException
   ```

2. **Fixed-size collections** (from `Arrays.asList()`):
   ```java
   List<String> fixed = Arrays.asList("a", "b", "c");
   fixed.add("d"); // UnsupportedOperationException
   ```

3. **Singleton collections**:
   ```java
   Set<String> single = Collections.singleton("one");
   single.add("two"); // UnsupportedOperationException
   ```

4. **Empty collections**:
   ```java
   List<String> empty = Collections.emptyList();
   empty.add("item"); // UnsupportedOperationException
   ```

5. **View collections** (subList, headSet, etc.):
   ```java
   List<String> sub = list.subList(0, 2);
   sub.add("new"); // May throw if backing list doesn't support
   ```

**Best Practice**: Check documentation or use `add()`/`remove()` cautiously when working with collection views or wrappers.

**Theoretical Keywords:**
Optional operations, Unmodifiable wrappers, Fixed-size collections, Collection views, Design by contract

### **175. What is the difference between fail-safe and fail-fast iterators?**
**Interviewer Answer:**
| **Fail-Fast Iterator** | **Fail-Safe Iterator** |
|------------------------|------------------------|
| Throws `ConcurrentModificationException` if collection modified during iteration | Doesn't throw exception on modification |
| Works on original collection | Works on snapshot/copy of collection |
| Doesn't require extra memory | May require extra memory for snapshot |
| Examples: `ArrayList`, `HashMap`, `HashSet` | Examples: `CopyOnWriteArrayList`, `ConcurrentHashMap` |
| Detects structural modification | Allows concurrent modification |
| **How it works**: Maintains `modCount` that changes on modification | **How it works**: Iterates over snapshot taken at iterator creation |

**Fail-Fast Example**:
```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String item = it.next();
    if (item.equals("B")) {
        list.remove("B"); // Throws ConcurrentModificationException
        // Use it.remove() instead - safe
    }
}
```

**Fail-Safe Example**:
```java
List<String> list = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String item = it.next();
    if (item.equals("B")) {
        list.remove("B"); // OK - iterator uses snapshot
        // But iterator won't see the removal
    }
}
```

**Theoretical Keywords:**
ConcurrentModificationException, modCount tracking, Snapshot iteration, Structural modification, Iterator safety

### **176. What are atomic operations in Java?**
**Interviewer Answer:**
- Operations that complete in single step without interference
- Thread-safe without explicit synchronization
- Implemented using CAS (Compare-And-Swap) at hardware level
- **Classes in `java.util.concurrent.atomic`**:

1. **Atomic primitives**:
   - `AtomicInteger`, `AtomicLong`, `AtomicBoolean`
   ```java
   AtomicInteger counter = new AtomicInteger(0);
   counter.incrementAndGet(); // Thread-safe increment
   counter.compareAndSet(5, 10); // Update if current is 5
   ```

2. **Atomic references**:
   - `AtomicReference<T>`, `AtomicStampedReference<T>`, `AtomicMarkableReference<T>`
   ```java
   AtomicReference<String> ref = new AtomicReference<>("initial");
   ref.set("new"); // Atomic update
   ```

3. **Atomic arrays**:
   - `AtomicIntegerArray`, `AtomicLongArray`, `AtomicReferenceArray<T>`

4. **Field updaters**:
   - `AtomicIntegerFieldUpdater`, `AtomicLongFieldUpdater`, `AtomicReferenceFieldUpdater`

**Common methods**:
- `get()`, `set()`
- `getAndSet(newValue)`
- `compareAndSet(expected, update)`
- `incrementAndGet()`, `getAndIncrement()`
- `addAndGet(delta)`, `getAndAdd(delta)`

**Use Cases**:
- Counters, sequence generators
- Status flags
- Non-blocking algorithms
- Implementing efficient locks

**Theoretical Keywords:**
java.util.concurrent.atomic, Compare-And-Swap (CAS), Lock-free programming, Atomic variables, Non-blocking synchronization

### **177. What is `BlockingQueue` in Java?**
**Interviewer Answer:**
- Thread-safe Queue that supports blocking operations
- **Key characteristics**:
  1. Thread-safe for concurrent access
  2. Can be bounded (fixed capacity) or unbounded
  3. Blocking operations wait for space/availability
  4. Used in producer-consumer patterns

**Core Methods**:
```java
// Blocking operations
queue.put(element);     // Waits if full
queue.take();           // Waits if empty

// Timed operations
queue.offer(element, timeout, timeUnit); // Waits up to timeout
queue.poll(timeout, timeUnit);           // Waits up to timeout

// Non-blocking versions
queue.offer(element);   // Returns false if full
queue.poll();           // Returns null if empty
```

**Common Implementations**:
1. **ArrayBlockingQueue**: Bounded, array-backed, FIFO
2. **LinkedBlockingQueue**: Optional bounded, linked nodes, FIFO
3. **PriorityBlockingQueue**: Unbounded, priority ordering
4. **DelayQueue**: Elements taken after delay elapsed
5. **SynchronousQueue**: Direct handoff (no storage)

**Producer-Consumer Example**:
```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

// Producer thread
new Thread(() -> {
    while (true) {
        String item = produceItem();
        queue.put(item); // Blocks if queue full
    }
}).start();

// Consumer thread  
new Thread(() -> {
    while (true) {
        String item = queue.take(); // Blocks if queue empty
        processItem(item);
    }
}).start();
```

**Use Cases**:
- Thread pools (ExecutorService)
- Message passing systems
- Pipeline processing
- Work queues

**Theoretical Keywords:**
Producer-consumer pattern, Bounded buffer, Thread coordination, Blocking operations, ExecutorService backing

---

**Excellent! You've now covered advanced Java collections and concurrency concepts. These topics are crucial for senior Java developer positions and system design interviews. You're building a strong foundation for tackling complex real-world scenarios!** 🚀💪