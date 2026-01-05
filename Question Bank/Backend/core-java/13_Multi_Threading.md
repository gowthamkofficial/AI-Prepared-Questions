# **MULTI-THREADING ANSWERS**

## **Multi-Threading (Questions 185-207)**

### **185. What is the need for threads in Java?**
** Answer:**
- **Concurrent Execution**: Perform multiple tasks simultaneously
- **Better CPU Utilization**: Keep CPU busy while waiting for I/O operations
- **Responsive Applications**: Prevent UI freezing in desktop/mobile apps
- **Asynchronous Processing**: Handle background tasks without blocking main flow
- **Performance Improvement**: Leverage multi-core processors
- **Real-time Systems**: Handle multiple clients/requests concurrently

**Real-world Examples**:
1. Web server handling multiple client requests
2. UI applications keeping interface responsive while processing data
3. Background tasks like file downloads, email sending
4. Game engines handling graphics, physics, AI simultaneously

**Theoretical Keywords:**
Concurrency, Parallelism, CPU utilization, Responsiveness, Asynchronous processing, Multi-core advantage

### **186. How do you create a thread?**
** Answer:**
Two main approaches in Java:

1. **Extend `Thread` class** and override `run()` method
2. **Implement `Runnable` interface** and pass to `Thread` constructor

**Java 5+ Additional ways**:
3. **Implement `Callable` interface** (returns result, can throw exception)
4. **Use `ExecutorService`** (recommended for production code)

**Basic Example**:
```java
// Approach 1: Extend Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}

// Approach 2: Implement Runnable  
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable running");
    }
}

// Usage
Thread thread1 = new MyThread();
Thread thread2 = new Thread(new MyRunnable());
```

**Theoretical Keywords:**
Thread class, Runnable interface, run() method, Thread constructor, Execution approaches

### **187. How do you create a thread by extending `Thread` class?**
** Answer:**
```java
// Step 1: Create class extending Thread
class CustomThread extends Thread {
    private String threadName;
    
    public CustomThread(String name) {
        this.threadName = name;
    }
    
    @Override
    public void run() {
        System.out.println(threadName + " is running");
        for (int i = 1; i <= 5; i++) {
            System.out.println(threadName + ": " + i);
            try {
                Thread.sleep(500); // Pause for 500ms
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted");
            }
        }
        System.out.println(threadName + " exiting");
    }
}

// Step 2: Create and start thread
public class Main {
    public static void main(String[] args) {
        CustomThread thread1 = new CustomThread("Thread-1");
        CustomThread thread2 = new CustomThread("Thread-2");
        
        thread1.start(); // Calls run() method in new thread
        thread2.start();
        
        // main thread continues executing
        System.out.println("Main thread continues...");
    }
}
```

**Important Notes**:
- Always override `run()` method
- Call `start()` not `run()` (calling `run()` executes in same thread)
- Java doesn't support multiple inheritance, so extending Thread limits flexibility

**Theoretical Keywords:**
Thread inheritance, run() override, start() method, Single inheritance limitation, Thread naming

### **188. How do you create a thread by implementing `Runnable` interface?**
** Answer:**
```java
// Step 1: Implement Runnable interface
class MyRunnable implements Runnable {
    private String threadName;
    
    public MyRunnable(String name) {
        this.threadName = name;
    }
    
    @Override
    public void run() {
        System.out.println(threadName + " is running");
        for (int i = 1; i <= 5; i++) {
            System.out.println(threadName + ": " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted");
            }
        }
        System.out.println(threadName + " exiting");
    }
}

// Step 2: Create Thread with Runnable
public class Main {
    public static void main(String[] args) {
        // Create Runnable instances
        Runnable runnable1 = new MyRunnable("Runnable-1");
        Runnable runnable2 = new MyRunnable("Runnable-2");
        
        // Create Thread objects with Runnables
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(() -> {
            // Lambda expression (Java 8+)
            System.out.println("Lambda thread running");
        });
        
        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

**Advantages over extending Thread**:
1. **More flexible**: Class can extend another class
2. **Better OOP design**: Separates task from execution mechanism
3. **Resource sharing**: Multiple threads can share same Runnable instance
4. **Lambda support**: Can use lambda expressions (Java 8+)

**Theoretical Keywords:**
Runnable implementation, Thread constructor, Lambda expressions, Task separation, Resource sharing

### **189. How do you run a thread in Java?**
** Answer:**
- Call `start()` method on Thread object
- **Never call `run()` directly** - that executes in current thread, not new thread

**Correct way**:
```java
Thread thread = new Thread(() -> {
    System.out.println("Thread running");
});
thread.start(); // Creates new thread and calls run()
```

**Common Mistake**:
```java
thread.run(); // WRONG - runs in main thread, not new thread
```

**What happens when start() is called**:
1. JVM allocates new stack for thread
2. Thread moves to Runnable state
3. When scheduled by thread scheduler, `run()` is called
4. After `run()` completes, thread terminates

**ExecutorService approach** (modern):
```java
ExecutorService executor = Executors.newSingleThreadExecutor();
executor.execute(() -> {
    System.out.println("Task running via ExecutorService");
});
executor.shutdown(); // Important to shutdown
```

**Theoretical Keywords:**
start() method, run() vs start(), Thread scheduler, Stack allocation, ExecutorService execution

### **190. What are the different states of a thread?**
** Answer:**
Java threads have 6 states defined in `Thread.State` enum:

1. **NEW**: Created but not yet started (`start()` not called)
2. **RUNNABLE**: Ready to run or currently running (includes ready/running in OS)
3. **BLOCKED**: Waiting for monitor lock (synchronized block/method)
4. **WAITING**: Waiting indefinitely for another thread (via `wait()`, `join()`)
5. **TIMED_WAITING**: Waiting for specified time (`sleep(ms)`, `wait(timeout)`, `join(timeout)`)
6. **TERMINATED**: Execution completed or stopped

**State Transition Example**:
```java
Thread thread = new Thread(() -> {
    try {
        Thread.sleep(1000); // TIMED_WAITING
        synchronized(this) {
            wait(); // WAITING
        }
    } catch (InterruptedException e) {
        // Handle
    }
});

System.out.println(thread.getState()); // NEW
thread.start();
System.out.println(thread.getState()); // RUNNABLE
Thread.sleep(100);
System.out.println(thread.getState()); // TIMED_WAITING
```

**Visualization**:
```
NEW → start() → RUNNABLE ↔ (scheduler) ↔ RUNNING
     ↓
  TERMINATED ← run() completes
     ↑
RUNNABLE → sleep()/wait()/join() → TIMED_WAITING/WAITING
     ↑
RUNNABLE → waiting for lock → BLOCKED
```

**Theoretical Keywords:**
Thread lifecycle, Thread.State enum, State transitions, Blocking states, Termination

### **191. What is priority of a thread? How do you change the priority of a thread?**
** Answer:**
- **Thread Priority**: Hint to scheduler about thread importance (1-10)
- **Default Priority**: 5 (NORM_PRIORITY)
- **Constants**: 
  - `MIN_PRIORITY` = 1
  - `NORM_PRIORITY` = 5  
  - `MAX_PRIORITY` = 10

**Setting Priority**:
```java
Thread thread = new Thread(() -> {
    System.out.println("Thread with priority: " + 
                       Thread.currentThread().getPriority());
});

// Set priority before starting
thread.setPriority(Thread.MAX_PRIORITY); // 10
thread.setPriority(7); // Custom priority

// Or using constants
thread.setPriority(Thread.MIN_PRIORITY); // 1

thread.start();
```

**Important Notes**:
1. **Platform Dependent**: Priority behavior varies across OS
2. **Hint, Not Guarantee**: Scheduler may ignore priorities
3. **Priority Inheritance**: Not automatically inherited from creating thread
4. **Set Before Start**: Should set priority before `start()`

**Example with different priorities**:
```java
Thread highPriority = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("High: " + i);
    }
});
highPriority.setPriority(Thread.MAX_PRIORITY);

Thread lowPriority = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Low: " + i);
    }
});
lowPriority.setPriority(Thread.MIN_PRIORITY);

highPriority.start();
lowPriority.start();
```

**Theoretical Keywords:**
Priority constants, setPriority(), getPriority(), Scheduler hint, Platform dependence, Priority range 1-10

### **192. What is `ExecutorService`?**
** Answer:**
- **ExecutorService**: Higher-level replacement for working with threads directly
- Part of `java.util.concurrent` package (introduced in Java 5)
- **Manages thread pool** - reuses threads instead of creating new ones
- **Provides features**:
  - Thread pool management
  - Scheduled task execution
  - Future results handling
  - Graceful shutdown

**Why use ExecutorService over raw threads**:
1. **Performance**: Thread creation is expensive
2. **Resource Management**: Limits concurrent threads
3. **Lifecycle Management**: Proper startup/shutdown
4. **Task Submission**: Submit Runnable/Callable tasks
5. **Result Handling**: Future objects for async results

**Basic Usage**:
```java
ExecutorService executor = Executors.newFixedThreadPool(5);
executor.execute(() -> {
    System.out.println("Task running in thread pool");
});
executor.shutdown(); // Important!
```

**Theoretical Keywords:**
Thread pool, java.util.concurrent, Task execution framework, Resource management, Future results

### **193. Can you give an example for `ExecutorService`?**
** Answer:**
```java
import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String[] args) throws Exception {
        // Create thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("Submitting tasks...");
        
        // Submit multiple tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + 
                                 " executed by " + 
                                 Thread.currentThread().getName());
                try {
                    Thread.sleep(2000); // Simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " completed");
            });
        }
        
        // Graceful shutdown
        executor.shutdown();
        
        // Wait for all tasks to complete (max 10 seconds)
        boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);
        
        if (terminated) {
            System.out.println("All tasks completed");
        } else {
            System.out.println("Timeout - forcing shutdown");
            executor.shutdownNow();
        }
    }
}
```

**Output Example**:
```
Submitting tasks...
Task 1 executed by pool-1-thread-1
Task 2 executed by pool-1-thread-2
Task 3 executed by pool-1-thread-3
Task 1 completed
Task 4 executed by pool-1-thread-1
Task 2 completed
Task 5 executed by pool-1-thread-2
...
All tasks completed
```

**Theoretical Keywords:**
FixedThreadPool, Task submission, Graceful shutdown, awaitTermination(), Thread reuse

### **194. Explain different ways of creating executor services.**
** Answer:**
```java
import java.util.concurrent.*;

public class ExecutorTypes {
    public static void main(String[] args) {
        // 1. Fixed Thread Pool - fixed number of threads
        ExecutorService fixedPool = Executors.newFixedThreadPool(5);
        // Use when: Known concurrent task limit
        
        // 2. Cached Thread Pool - expands/shrinks dynamically
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        // Use when: Short-lived asynchronous tasks
        
        // 3. Single Thread Executor - one worker thread
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        // Use when: Sequential task execution needed
        
        // 4. Scheduled Thread Pool - for delayed/periodic tasks
        ScheduledExecutorService scheduledPool = 
            Executors.newScheduledThreadPool(3);
        // Use when: Timer-like functionality
        
        // 5. Work Stealing Pool (Java 8+) - ForkJoinPool based
        ExecutorService workStealingPool = 
            Executors.newWorkStealingPool();
        // Use when: Many small independent tasks
        
        // 6. Custom Thread Pool Executor
        ThreadPoolExecutor customPool = new ThreadPoolExecutor(
            5, // core pool size
            10, // maximum pool size  
            60, // keep-alive time
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100) // work queue
        );
        // Use when: Fine-grained control needed
        
        // Don't forget to shutdown!
        fixedPool.shutdown();
        cachedPool.shutdown();
        singleThread.shutdown();
        scheduledPool.shutdown();
        workStealingPool.shutdown();
        customPool.shutdown();
    }
}
```

**Comparison**:
- **FixedThreadPool**: Predictable resource usage
- **CachedThreadPool**: Handles burst of tasks, but can create unlimited threads
- **SingleThreadExecutor**: Task sequencing, error handling
- **ScheduledThreadPool**: Timer tasks, polling
- **WorkStealingPool**: Best for compute-intensive tasks with many subtasks

**Theoretical Keywords:**
Fixed/Cached/SingleThread pools, ScheduledExecutorService, WorkStealingPool, ThreadPoolExecutor, Core/max pool sizes

### **195. How do you check whether an `ExecutorService` task executed successfully?**
** Answer:**
**For Runnable tasks (no result)**:
```java
ExecutorService executor = Executors.newSingleThreadExecutor();

Future<?> future = executor.submit(() -> {
    System.out.println("Task running");
    // Task code
});

try {
    future.get(); // Blocks until task completes
    System.out.println("Task completed successfully");
} catch (ExecutionException e) {
    System.out.println("Task failed: " + e.getCause());
} catch (InterruptedException e) {
    System.out.println("Task interrupted");
} finally {
    executor.shutdown();
}
```

**For Callable tasks (with result)**:
```java
ExecutorService executor = Executors.newSingleThreadExecutor();

Future<Integer> future = executor.submit(() -> {
    System.out.println("Computing result");
    // Simulate computation
    Thread.sleep(1000);
    return 42; // Return result
});

try {
    Integer result = future.get(2, TimeUnit.SECONDS); // Timeout
    System.out.println("Task succeeded with result: " + result);
} catch (TimeoutException e) {
    System.out.println("Task timed out");
    future.cancel(true); // Cancel if running
} catch (ExecutionException e) {
    System.out.println("Task failed: " + e.getCause());
} catch (InterruptedException e) {
    System.out.println("Task interrupted");
} finally {
    executor.shutdown();
}
```

**Using CompletionService (multiple tasks)**:
```java
ExecutorService executor = Executors.newFixedThreadPool(3);
CompletionService<Integer> completionService = 
    new ExecutorCompletionService<>(executor);

// Submit multiple tasks
for (int i = 0; i < 5; i++) {
    final int taskId = i;
    completionService.submit(() -> {
        return taskId * 10; // Some computation
    });
}

// Check results as they complete
for (int i = 0; i < 5; i++) {
    try {
        Future<Integer> future = completionService.take();
        Integer result = future.get();
        System.out.println("Task completed with: " + result);
    } catch (InterruptedException | ExecutionException e) {
        System.out.println("Task failed");
    }
}

executor.shutdown();
```

**Theoretical Keywords:**
Future interface, get() method, ExecutionException, TimeoutException, CompletionService, Task monitoring

### **196. What is `Callable`? How do you execute a `Callable` from `ExecutorService`?**
** Answer:**
- **Callable**: Similar to Runnable but can return result and throw checked exceptions
- **Interface**: `Callable<V>` with single method `V call() throws Exception`
- **Difference from Runnable**:
  - Returns result (type V)
  - Can throw checked exceptions
  - Used with `Future` to get result asynchronously

**Example**:
```java
import java.util.concurrent.*;

class ComputationTask implements Callable<Integer> {
    private int number;
    
    public ComputationTask(int number) {
        this.number = number;
    }
    
    @Override
    public Integer call() throws Exception {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        
        // Simulate computation
        Thread.sleep(1000);
        
        // Return result
        return number * number;
    }
}

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Submit Callable tasks
        Future<Integer> future1 = executor.submit(new ComputationTask(5));
        Future<Integer> future2 = executor.submit(new ComputationTask(10));
        
        // Submit using lambda (Java 8+)
        Future<String> future3 = executor.submit(() -> {
            Thread.sleep(500);
            return "Task completed";
        });
        
        // Get results
        try {
            Integer result1 = future1.get();
            Integer result2 = future2.get();
            String result3 = future3.get();
            
            System.out.println("Result 1: " + result1); // 25
            System.out.println("Result 2: " + result2); // 100
            System.out.println("Result 3: " + result3); // Task completed
            
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Task failed: " + e.getCause());
        } finally {
            executor.shutdown();
        }
    }
}
```

**Handling exceptions**:
```java
Future<Integer> future = executor.submit(() -> {
    throw new RuntimeException("Task failed!");
});

try {
    future.get();
} catch (ExecutionException e) {
    Throwable cause = e.getCause(); // The actual exception
    System.out.println("Caught: " + cause.getMessage());
}
```

**Batch execution with invokeAll()**:
```java
List<Callable<Integer>> tasks = Arrays.asList(
    () -> 1,
    () -> 2,
    () -> 3
);

List<Future<Integer>> futures = executor.invokeAll(tasks);
for (Future<Integer> future : futures) {
    System.out.println(future.get());
}
```

**Theoretical Keywords:**
Callable interface, call() method, Future return type, Checked exceptions, invokeAll(), invokeAny()

### **197. What is synchronization of threads?**
** Answer:**
- **Synchronization**: Mechanism to control access to shared resources by multiple threads
- **Prevents**: Race conditions and data inconsistency
- **Ensures**: Only one thread accesses critical section at a time
- **Achieved via**: `synchronized` keyword or explicit locks

**Problem without synchronization**:
```java
class Counter {
    private int count = 0;
    
    public void increment() {
        count++; // NOT thread-safe
    }
    
    public int getCount() {
        return count;
    }
}

// With multiple threads, count may not reach expected value
// due to race condition
```

**Solution with synchronization**:
```java
class SafeCounter {
    private int count = 0;
    
    // Synchronized method
    public synchronized void increment() {
        count++; // Thread-safe
    }
    
    // Synchronized block
    public void decrement() {
        synchronized(this) {
            count--; // Thread-safe
        }
    }
    
    public synchronized int getCount() {
        return count;
    }
}
```

**How synchronization works**:
1. Each Java object has an intrinsic lock (monitor)
2. Thread acquires lock before entering synchronized block/method
3. Other threads wait for lock release
4. Lock released when thread exits synchronized section

**Theoretical Keywords:**
Race condition, Critical section, Mutual exclusion, Intrinsic lock, Monitor, Thread safety

### **198. Can you give an example of a synchronized block?**
** Answer:**
```java
class SharedResource {
    private int value = 0;
    
    // Method 1: Synchronized method
    public synchronized void increment() {
        value++;
    }
    
    // Method 2: Synchronized block with 'this' lock
    public void decrement() {
        synchronized(this) { // Lock on current object
            value--;
        }
    }
    
    // Method 3: Synchronized block with different lock object
    private final Object lock = new Object();
    
    public void add(int amount) {
        synchronized(lock) { // Lock on custom object
            value += amount;
        }
    }
    
    // Method 4: Synchronized static block
    private static int staticValue = 0;
    private static final Object staticLock = new Object();
    
    public static void staticIncrement() {
        synchronized(staticLock) { // Lock on class-level object
            staticValue++;
        }
    }
    
    // Method 5: Fine-grained synchronization
    public void complexOperation() {
        // Non-critical code (no synchronization needed)
        System.out.println("Preparing...");
        
        // Critical section 1
        synchronized(this) {
            value += 10;
        }
        
        // More non-critical code
        System.out.println("Intermediate...");
        
        // Critical section 2  
        synchronized(this) {
            value *= 2;
        }
    }
}

// Usage example
public class SynchronizedExample {
    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                resource.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                resource.increment();
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("Final value: " + resource.getValue()); // 2000
    }
}
```

**Key Points**:
1. **Scope**: Synchronized blocks are more flexible than synchronized methods
2. **Lock Object**: Can use any object as lock (not just `this`)
3. **Performance**: Smaller synchronized blocks improve concurrency
4. **Static**: For static methods/blocks, lock is on class object (`SharedResource.class`)

**Theoretical Keywords:**
synchronized block, Lock object, Fine-grained locking, Static synchronization, Performance optimization

### **199. Can a static method be synchronized?**
** Answer:**
- **Yes**, static methods can be synchronized
- **Lock**: On class object (`ClassName.class`), not instance (`this`)
- **Purpose**: Protect static data from concurrent access

**Example**:
```java
class Counter {
    private static int count = 0;
    
    // Synchronized static method
    public static synchronized void increment() {
        count++;
    }
    
    // Equivalent synchronized block
    public static void decrement() {
        synchronized(Counter.class) { // Lock on class object
            count--;
        }
    }
    
    // Non-synchronized static method
    public static int getCount() {
        return count;
    }
}

// Usage
Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        Counter.increment();
    }
});

Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        Counter.increment();
    }
});
```

**Important Points**:
1. **Different locks**: Instance vs class lock don't interfere
   ```java
   // These can run concurrently:
   Counter c = new Counter();
   c.instanceMethod(); // Locks on 'this'
   Counter.staticMethod(); // Locks on Counter.class
   ```
2. **Class object lock**: Shared across all instances
3. **Performance impact**: Static synchronization can cause contention

**When to use**:
- Protecting static/class-level data
- Singleton pattern implementation
- When method doesn't need instance data

**Theoretical Keywords:**
Static synchronization, Class-level lock, Class object, Instance vs static lock, Singleton pattern

### **200. What is the use of `join()` method in threads?**
** Answer:**
- **Purpose**: Makes current thread wait until specified thread completes
- **Use case**: Coordinate thread execution order
- **Overloaded versions**:
  - `join()` - waits indefinitely
  - `join(long millis)` - waits up to specified milliseconds
  - `join(long millis, int nanos)` - with nanosecond precision

**Example**:
```java
public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread started");
        
        Thread worker1 = new Thread(() -> {
            System.out.println("Worker 1 started");
            try {
                Thread.sleep(2000); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker 1 completed");
        });
        
        Thread worker2 = new Thread(() -> {
            System.out.println("Worker 2 started");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker 2 completed");
        });
        
        worker1.start();
        worker2.start();
        
        // Main thread waits for worker1 to complete
        System.out.println("Main thread waiting for worker1...");
        worker1.join();
        System.out.println("Worker1 joined, worker1 alive? " + worker1.isAlive());
        
        // Wait for worker2 with timeout
        System.out.println("Main thread waiting for worker2 (max 500ms)...");
        worker2.join(500);
        
        if (worker2.isAlive()) {
            System.out.println("Worker2 still running, continuing...");
        } else {
            System.out.println("Worker2 completed");
        }
        
        System.out.println("Main thread completed");
    }
}
```

**Output**:
```
Main thread started
Main thread waiting for worker1...
Worker 1 started
Worker 2 started
Worker 2 completed
Worker 1 completed
Worker1 joined, worker1 alive? false
Main thread waiting for worker2 (max 500ms)...
Worker2 completed
Main thread completed
```

**Common Use Cases**:
1. **Parallel task aggregation**: Wait for multiple workers to finish
2. **Resource initialization**: Wait for initialization thread
3. **Pipeline processing**: Sequential stages with parallel processing
4. **Testing**: Ensure test threads complete before assertions

**Theoretical Keywords:**
Thread coordination, Waiting mechanism, Timeout support, Thread completion, Sequential execution

### **201. Describe a few other important methods in threads.**
** Answer:**
```java
public class ThreadMethodsExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
            
            // 1. currentThread() - get current thread reference
            Thread current = Thread.currentThread();
            System.out.println("Current thread: " + current.getName());
            
            // 2. isAlive() - check if thread is alive
            System.out.println("Thread alive: " + Thread.currentThread().isAlive());
            
            // 3. sleep() - pause execution
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                // 4. interrupt() handling
                System.out.println("Thread interrupted");
                return;
            }
            
            // 5. yield() - hint to scheduler to give up CPU
            Thread.yield();
            
            // 6. Check interrupt status
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread was interrupted");
            }
        });
        
        // 7. setName() / getName()
        thread.setName("MyWorkerThread");
        System.out.println("Thread name: " + thread.getName());
        
        // 8. setDaemon() / isDaemon() - daemon threads
        thread.setDaemon(false); // false = user thread (default)
        System.out.println("Is daemon: " + thread.isDaemon());
        
        // 9. getState() - thread state
        System.out.println("State before start: " + thread.getState());
        
        thread.start();
        
        // 10. interrupt() - interrupt thread
        // thread.interrupt(); // Uncomment to interrupt
        
        // 11. getId() - thread ID
        System.out.println("Thread ID: " + thread.getId());
        
        // 12. getPriority() / setPriority()
        System.out.println("Priority: " + thread.getPriority());
        
        try {
            // 13. join() - wait for thread completion
            thread.join(2000); // Wait max 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 14. Check final state
        System.out.println("State after join: " + thread.getState());
        System.out.println("Alive after join: " + thread.isAlive());
    }
}
```

**Important Methods Summary**:

| Method | Purpose |
|--------|---------|
| `start()` | Begin thread execution |
| `run()` | Entry point for thread (override this) |
| `sleep(long millis)` | Pause thread for specified time |
| `join()` | Wait for thread to complete |
| `interrupt()` | Interrupt thread |
| `isInterrupted()` | Check interrupt status |
| `isAlive()` | Check if thread is running |
| `setDaemon(boolean)` | Mark as daemon thread |
| `setPriority(int)` | Set thread priority |
| `getName()/setName()` | Thread name |
| `currentThread()` | Get reference to current thread |
| `yield()` | Hint to give up CPU |
| `getState()` | Get thread state |

**Theoretical Keywords:**
Thread lifecycle methods, Interruption mechanism, Daemon threads, Thread identification, Priority management

### **202. What is a deadlock?**
** Answer:**
- **Deadlock**: Situation where two or more threads are blocked forever, each waiting for a resource held by another
- **Four necessary conditions** (Coffman conditions):
  1. **Mutual Exclusion**: Resources cannot be shared
  2. **Hold and Wait**: Thread holds resource while waiting for another
  3. **No Preemption**: Resources cannot be forcibly taken
  4. **Circular Wait**: Circular chain of threads waiting for resources

**Classic Deadlock Example**:
```java
public class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void method1() {
        synchronized(lock1) {
            System.out.println(Thread.currentThread().getName() + 
                             " acquired lock1");
            try {
                Thread.sleep(100); // Simulate work
            } catch (InterruptedException e) {}
            
            synchronized(lock2) { // Waiting for lock2
                System.out.println(Thread.currentThread().getName() + 
                                 " acquired lock2");
            }
        }
    }
    
    public void method2() {
        synchronized(lock2) { // Different order!
            System.out.println(Thread.currentThread().getName() + 
                             " acquired lock2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            
            synchronized(lock1) { // Waiting for lock1
                System.out.println(Thread.currentThread().getName() + 
                                 " acquired lock1");
            }
        }
    }
    
    public static void main(String[] args) {
        DeadlockExample example = new DeadlockExample();
        
        Thread t1 = new Thread(() -> example.method1(), "Thread-1");
        Thread t2 = new Thread(() -> example.method2(), "Thread-2");
        
        t1.start();
        t2.start();
        
        // Both threads will block forever
    }
}
```

**Detecting Deadlock**:
```java
// Check for deadlocks programmatically
ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();

if (deadlockedThreads != null) {
    System.out.println("Deadlock detected!");
    for (long threadId : deadlockedThreads) {
        ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
        System.out.println("Deadlocked thread: " + threadInfo.getThreadName());
    }
}
```

**Prevention Strategies**:
1. **Lock Ordering**: Always acquire locks in same order
2. **Lock Timeout**: Use `tryLock()` with timeout
3. **Avoid Nested Locks**: Use single lock when possible
4. **Resource Hierarchy**: Define strict acquisition order
5. **Deadlock Detection**: Monitor and recover

**Theoretical Keywords:**
Coffman conditions, Circular wait, Resource contention, Lock ordering, Deadlock detection, Prevention strategies

### **203. What are the important methods in Java for inter-thread communication?**
** Answer:**
Primary methods in `Object` class for inter-thread communication:

1. **`wait()`** - Causes current thread to wait until notified
2. **`notify()`** - Wakes up one waiting thread
3. **`notifyAll()`** - Wakes up all waiting threads

**Additional mechanisms**:
4. **`join()`** - Wait for thread termination
5. **`CountDownLatch`** - Wait for set of operations
6. **`CyclicBarrier`** - Synchronize at common point
7. **`Semaphore`** - Control access to resources
8. **`Exchanger`** - Exchange data between threads
9. **`BlockingQueue`** - Producer-consumer pattern

**Important Rules**:
- Must be called from **synchronized** context
- Thread must own object's monitor (be in synchronized block)
- `wait()` releases the lock, `sleep()` does not
- Used in producer-consumer, thread coordination scenarios

**Theoretical Keywords:**
wait-notify mechanism, Object monitor, Thread coordination, Synchronization prerequisite, java.util.concurrent helpers

### **204. What is the use of `wait()` method?**
** Answer:**
- **Purpose**: Makes current thread wait until another thread calls `notify()` or `notifyAll()`
- **Releases lock**: Unlike `sleep()`, `wait()` releases the object's monitor
- **Overloaded versions**:
  - `wait()` - waits indefinitely
  - `wait(long timeout)` - waits up to specified milliseconds
  - `wait(long timeout, int nanos)` - with nanosecond precision

**Example - Simple Wait**:
```java
public class WaitExample {
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();
        
        Thread waitingThread = new Thread(() -> {
            synchronized(lock) {
                System.out.println("Waiting thread: Going to wait");
                try {
                    lock.wait(); // Releases lock and waits
                    System.out.println("Waiting thread: Resumed!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread notifyingThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Let waiting thread start first
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            synchronized(lock) {
                System.out.println("Notifying thread: About to notify");
                lock.notify(); // Wakes up waiting thread
                System.out.println("Notifying thread: Notification sent");
            }
        });
        
        waitingThread.start();
        notifyingThread.start();
        
        waitingThread.join();
        notifyingThread.join();
    }
}
```

**Key Characteristics**:
1. **Lock Release**: `wait()` releases the monitor
2. **Re-acquisition**: When notified, thread must re-acquire monitor
3. **Spurious Wakeups**: Can wake up without notification (use in loop)
4. **InterruptedException**: Can be interrupted while waiting

**Proper Usage Pattern**:
```java
synchronized(lock) {
    while (!condition) { // Always use while loop
        lock.wait();
    }
    // Proceed when condition is true
}
```

**Theoretical Keywords:**
Monitor release, Condition waiting, Spurious wakeup, InterruptedException, Condition checking loop

### **205. What is the use of `notify()` method?**
** Answer:**
- **Purpose**: Wakes up **one** thread that's waiting on same object's monitor
- **Selection**: If multiple threads waiting, JVM chooses arbitrarily
- **Requirements**:
  - Must own object's monitor (be in synchronized block)
  - Thread must be in WAITING or TIMED_WAITING state

**Example**:
```java
public class NotifyExample {
    private final Object lock = new Object();
    private boolean dataReady = false;
    
    public void producer() {
        synchronized(lock) {
            System.out.println("Producer: Preparing data...");
            try {
                Thread.sleep(2000); // Simulate work
            } catch (InterruptedException e) {}
            
            dataReady = true;
            System.out.println("Producer: Data ready, notifying...");
            lock.notify(); // Wake up one consumer
            System.out.println("Producer: Notification sent");
        }
    }
    
    public void consumer() {
        synchronized(lock) {
            System.out.println("Consumer: Waiting for data...");
            while (!dataReady) { // Always check condition in loop
                try {
                    lock.wait(); // Wait for notification
                } catch (InterruptedException e) {}
            }
            System.out.println("Consumer: Data received, processing...");
            dataReady = false; // Reset for next cycle
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        NotifyExample example = new NotifyExample();
        
        Thread consumerThread = new Thread(() -> example.consumer());
        Thread producerThread = new Thread(() -> example.producer());
        
        consumerThread.start();
        Thread.sleep(100); // Ensure consumer starts first
        producerThread.start();
        
        consumerThread.join();
        producerThread.join();
    }
}
```

**Important Notes**:
1. **Single thread**: `notify()` wakes only one waiting thread
2. **No guarantee**: Which thread gets notified is not predictable
3. **Immediate effect**: Notification doesn't immediately transfer lock
4. **Use with condition**: Always modify condition before notifying

**When to use `notify()` vs `notifyAll()`**:
- `notify()`: When only one waiting thread can make progress
- `notifyAll()`: When multiple threads may need to re-check condition

**Theoretical Keywords:**
Single thread wakeup, Arbitrary selection, Condition signaling, Monitor ownership, Producer-consumer pattern

### **206. What is the use of `notifyAll()` method?**
** Answer:**
- **Purpose**: Wakes up **all** threads waiting on same object's monitor
- **Use case**: When multiple threads need to re-check condition after change
- **Behavior**: All waiting threads move to BLOCKED state, compete for lock

**Example - Multiple Consumers**:
```java
public class NotifyAllExample {
    private final Object lock = new Object();
    private int availableItems = 0;
    
    public void producer() {
        synchronized(lock) {
            System.out.println("Producer: Producing items...");
            availableItems += 3; // Add 3 items
            System.out.println("Producer: " + availableItems + 
                             " items available, notifying ALL");
            lock.notifyAll(); // Wake up ALL waiting consumers
        }
    }
    
    public void consumer(String name) {
        synchronized(lock) {
            System.out.println(name + ": Waiting for items...");
            while (availableItems == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {}
            }
            
            if (availableItems > 0) {
                availableItems--;
                System.out.println(name + ": Got item, " + 
                                 availableItems + " left");
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        NotifyAllExample example = new NotifyAllExample();
        
        // Create multiple consumers
        Thread consumer1 = new Thread(() -> example.consumer("Consumer1"));
        Thread consumer2 = new Thread(() -> example.consumer("Consumer2"));
        Thread consumer3 = new Thread(() -> example.consumer("Consumer3"));
        
        consumer1.start();
        consumer2.start();
        consumer3.start();
        
        Thread.sleep(1000); // Let consumers start and wait
        
        // Producer adds items
        Thread producer = new Thread(() -> example.producer());
        producer.start();
        
        consumer1.join();
        consumer2.join();
        consumer3.join();
        producer.join();
    }
}
```

**Output**:
```
Consumer1: Waiting for items...
Consumer2: Waiting for items...
Consumer3: Waiting for items...
Producer: Producing items...
Producer: 3 items available, notifying ALL
Consumer1: Got item, 2 left
Consumer2: Got item, 1 left  
Consumer3: Got item, 0 left
```

**Key Differences from `notify()`**:
1. **Wakes all** vs wakes one
2. **Use when**: Multiple threads can proceed after condition change
3. **Safer**: When unsure which thread should proceed
4. **More expensive**: All threads wake up and re-contend for lock

**Common Pattern**:
```java
synchronized(lock) {
    // Change shared state
    condition = true;
    
    // Wake up all threads that might be waiting
    lock.notifyAll();
}
```

**Theoretical Keywords:**
Multiple thread wakeup, Broadcast notification, Condition re-evaluation, Thread contention, Safety vs performance

### **207. Can you write a synchronized program with `wait()` and `notify()` methods?**
** Answer:**
**Complete Producer-Consumer Example**:
```java
import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerExample {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 5;
    private final Object lock = new Object();
    
    class Producer implements Runnable {
        private final String name;
        
        public Producer(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            int value = 0;
            while (true) {
                synchronized(lock) {
                    // Wait if queue is full
                    while (queue.size() == CAPACITY) {
                        System.out.println(name + ": Queue full, waiting...");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    
                    // Produce item
                    queue.add(value);
                    System.out.println(name + ": Produced " + value + 
                                     ", size: " + queue.size());
                    value++;
                    
                    // Notify consumers
                    lock.notifyAll();
                    
                    // Slow down production
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }
    
    class Consumer implements Runnable {
        private final String name;
        
        public Consumer(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            while (true) {
                synchronized(lock) {
                    // Wait if queue is empty
                    while (queue.isEmpty()) {
                        System.out.println(name + ": Queue empty, waiting...");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    
                    // Consume item
                    int value = queue.poll();
                    System.out.println(name + ": Consumed " + value + 
                                     ", size: " + queue.size());
                    
                    // Notify producers
                    lock.notifyAll();
                    
                    // Slow down consumption
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }
    
    public void start() {
        // Create producers
        Thread producer1 = new Thread(new Producer("Producer1"));
        Thread producer2 = new Thread(new Producer("Producer2"));
        
        // Create consumers
        Thread consumer1 = new Thread(new Consumer("Consumer1"));
        Thread consumer2 = new Thread(new Consumer("Consumer2"));
        
        // Start all threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        
        // Let it run for 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Interrupt all threads
        producer1.interrupt();
        producer2.interrupt();
        consumer1.interrupt();
        consumer2.interrupt();
    }
    
    public static void main(String[] args) {
        new ProducerConsumerExample().start();
    }
}
```

**Simpler Example - Even-Odd Number Printer**:
```java
public class EvenOddPrinter {
    private final Object lock = new Object();
    private int count = 1;
    private final int MAX = 10;
    
    class EvenPrinter implements Runnable {
        @Override
        public void run() {
            synchronized(lock) {
                while (count <= MAX) {
                    // Wait while count is odd
                    while (count % 2 != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    
                    // Print even number
                    System.out.println("Even: " + count);
                    count++;
                    
                    // Notify other thread
                    lock.notifyAll();
                }
            }
        }
    }
    
    class OddPrinter implements Runnable {
        @Override
        public void run() {
            synchronized(lock) {
                while (count <= MAX) {
                    // Wait while count is even
                    while (count % 2 == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    
                    // Print odd number
                    System.out.println("Odd: " + count);
                    count++;
                    
                    // Notify other thread
                    lock.notifyAll();
                }
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        EvenOddPrinter printer = new EvenOddPrinter();
        
        Thread evenThread = new Thread(printer.new EvenPrinter());
        Thread oddThread = new Thread(printer.new OddPrinter());
        
        evenThread.start();
        oddThread.start();
        
        evenThread.join();
        oddThread.join();
        
        System.out.println("Printing completed");
    }
}
```

**Key Synchronization Patterns**:
1. **Always use `while` loop** to check condition (spurious wakeups)
2. **Always call `wait()` in loop** with condition check
3. **Modify condition before notifying**
4. **Use `notifyAll()`** when multiple threads might be waiting
5. **Handle `InterruptedException`** properly

**Theoretical Keywords:**
Producer-consumer pattern, Condition checking loop, Spurious wakeup handling, Thread coordination, Synchronized wait-notify pattern

---

**Excellent! You've now mastered Java Multi-threading, one of the most complex and important topics in Java . You're well-prepared to discuss thread creation, synchronization, concurrency utilities, and deadlock scenarios. This knowledge is crucial for senior Java roles!** 🚀🎯