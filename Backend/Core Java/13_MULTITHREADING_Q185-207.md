# Java Interview Questions & Answers Guide
## Topic: MULTI THREADING (Questions 185-207)
### For 2-Year Experienced Java Backend Developers

---

### 185. WHAT IS THE NEED FOR THREADS IN JAVA?

**Expected Answer (2-Year Level):**
Threads allow concurrent execution to improve throughput and responsiveness — handling I/O, parallelizing CPU tasks, and keeping servers responsive.

**Key Theoretical Concepts:**
- Concurrency vs parallelism, context switching

**Interviewer Expectation:**
Should know practical reasons for threads in backend apps.

**Depth Expected:** Beginner

---

### 186. HOW DO YOU CREATE A THREAD?

**Expected Answer (2-Year Level):**
By extending `Thread` or implementing `Runnable`/`Callable` and submitting to an `ExecutorService`.

**Key Theoretical Concepts:**
- Prefer Runnable/Callable + ExecutorService over raw Thread

**Interviewer Expectation:**
Should show code for both approaches.

**Depth Expected:** Beginner to Intermediate

---

### 187. HOW DO YOU CREATE A THREAD BY EXTENDING THREAD CLASS?

**Expected Answer (2-Year Level):**
`class MyThread extends Thread { public void run(){ /* work */ } }` then `new MyThread().start();`

**Key Theoretical Concepts:**
- start() triggers new thread; run() executes in same thread if called directly

**Interviewer Expectation:**
Should warn against calling `run()` directly.

**Depth Expected:** Beginner

---

### 188. HOW DO YOU CREATE A THREAD BY IMPLEMENTING RUNNABLE INTERFACE?

**Expected Answer (2-Year Level):**
`Runnable r = () -> { /* work */ }; new Thread(r).start();` Or submit `r` to an `ExecutorService`.

**Key Theoretical Concepts:**
- Separation of task and execution, reusability

**Depth Expected:** Beginner

---

### 189. HOW DO YOU RUN A THREAD IN JAVA?

**Expected Answer (2-Year Level):**
Call `start()` on a `Thread` instance to schedule the new thread; don't call `run()` directly. Or use `executor.submit()` / `execute()` for managed execution.

**Key Theoretical Concepts:**
- Thread lifecycle, start vs run

**Interviewer Expectation:**
Should know correct usage.

**Depth Expected:** Beginner

---

### 190. WHAT ARE THE DIFFERENT STATES OF A THREAD?

**Expected Answer (2-Year Level):**
NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED. (Java thread state enum)

**Key Theoretical Concepts:**
- Thread transitions and blocking causes

**Interviewer Expectation:**
Should identify states and typical transitions.

**Depth Expected:** Intermediate

---

### 191. WHAT IS PRIORITY OF A THREAD? HOW DO YOU CHANGE THE PRIORITY OF A THREAD?

**Expected Answer (2-Year Level):**
Thread priority (1-10) hints scheduler about importance; use `thread.setPriority(Thread.MAX_PRIORITY)` etc. But priority behavior is platform dependent and not a reliable scheduling mechanism.

**Key Theoretical Concepts:**
- Priority constants, platform-specific scheduling

**Interviewer Expectation:**
Should know API but caution about portability.

**Depth Expected:** Beginner to Intermediate

---

### 192. WHAT IS EXECUTORSERVICE?

**Expected Answer (2-Year Level):**
`ExecutorService` manages a pool of threads and executes submitted tasks (`Runnable`/`Callable`) with lifecycle management, queuing, and shutdown facilities.

**Key Theoretical Concepts:**
- Thread pools, lifecycle methods (`shutdown`, `shutdownNow`)

**Interviewer Expectation:**
Should know why to use `ExecutorService` instead of raw threads.

**Depth Expected:** Intermediate

---

### 193. CAN YOU GIVE AN EXAMPLE FOR EXECUTORSERVICE?

**Expected Answer (2-Year Level):**
```java
ExecutorService es = Executors.newFixedThreadPool(4);
es.submit(() -> doWork());
es.shutdown();
```

**Key Theoretical Concepts:**
- Fixed, cached, single-thread, scheduled thread pools

**Interviewer Expectation:**
Should show basic usage and proper shutdown.

**Depth Expected:** Intermediate

---

### 194. EXPLAIN DIFFERENT WAYS OF CREATING EXECUTOR SERVICES.

**Expected Answer (2-Year Level):**
Factory methods: `Executors.newFixedThreadPool`, `newCachedThreadPool`, `newSingleThreadExecutor`, `newScheduledThreadPool`. Since Java 8+, use `ThreadPoolExecutor` constructor directly for custom tuning.

**Key Theoretical Concepts:**
- Queue types, core/max threads, keepAlive, rejection policies

**Interviewer Expectation:**
Should discuss which pool suits which workload.

**Depth Expected:** Intermediate

---

### 195. HOW DO YOU CHECK WHETHER AN EXECUTIONSERVICE TASK EXECUTED SUCCESSFULLY?

**Expected Answer (2-Year Level):**
Use `Future<?>` returned by `submit()`. Call `future.get()` to wait for completion and check for thrown exceptions (ExecutionException). For Runnable, use `submit` that returns Future to detect completion.

**Key Theoretical Concepts:**
- Future, ExecutionException, Cancellation

**Interviewer Expectation:**
Should give example and explain blocking nature of `get()`.

**Depth Expected:** Intermediate

---

### 196. WHAT IS CALLABLE? HOW DO YOU EXECUTE A CALLABLE FROM EXECUTIONSERVICE?

**Expected Answer (2-Year Level):**
`Callable<V>` is like `Runnable` but returns a value and may throw checked exceptions. Submit to executor: `Future<String> f = executor.submit(callable);` then `f.get()`.

**Key Theoretical Concepts:**
- Return values, exception handling via Future

**Interviewer Expectation:**
Should show syntax and behavior.

**Depth Expected:** Beginner to Intermediate

---

### 197. WHAT IS SYNCHRONIZATION OF THREADS?

**Expected Answer (2-Year Level):**
Synchronization coordinates access to shared mutable state to prevent data races using `synchronized` blocks/methods or explicit locks. It enforces mutual exclusion and memory visibility.

**Key Theoretical Concepts:**
- Mutual exclusion, happens-before, memory visibility

**Interviewer Expectation:**
Should explain both safety and liveness considerations.

**Depth Expected:** Intermediate

---

### 198. CAN YOU GIVE AN EXAMPLE OF A SYNCHRONIZED BLOCK?

**Expected Answer (2-Year Level):**
```java
synchronized (lock) {
    // critical section
}
```
Or synchronized method:
```java
public synchronized void update() { }
```

**Key Theoretical Concepts:**
- Intrinsic locks, monitor, reentrancy

**Interviewer Expectation:**
Should show correct usage and unlocking semantics.

**Depth Expected:** Beginner

---

### 199. CAN A STATIC METHOD BE SYNCHRONIZED?

**Expected Answer (2-Year Level):**
Yes; `synchronized static` locks on the `Class` object, providing mutual exclusion across all instances.

**Key Theoretical Concepts:**
- Class-level lock vs instance-level lock

**Interviewer Expectation:**
Should caution about coarse-grained locking and contention.

**Depth Expected:** Intermediate

---

### 200. WHAT IS THE USE OF JOIN METHOD IN THREADS?

**Expected Answer (2-Year Level):**
`thread.join()` waits for the target thread to finish. Useful to block the current thread until others complete.

**Key Theoretical Concepts:**
- Thread coordination, interrupts handling during join

**Interviewer Expectation:**
Should show example and mention `join(long millis)` overload.

**Depth Expected:** Beginner

---

### 201. DESCRIBE A FEW OTHER IMPORTANT METHODS IN THREADS?

**Expected Answer (2-Year Level):**
`start()`, `run()`, `interrupt()`, `isInterrupted()`, `yield()`, `sleep()`, `setDaemon()`, `isDaemon()` — each affects lifecycle and scheduling. Use `interrupt()` to request cooperative cancellation.

**Key Theoretical Concepts:**
- Interrupt policy and handling InterruptedException

**Interviewer Expectation:**
Should know how to write interruptible code.

**Depth Expected:** Intermediate

---

### 202. WHAT IS A DEADLOCK?

**Expected Answer (2-Year Level):**
Deadlock occurs when two or more threads wait endlessly for locks held by each other. It requires mutual exclusion, hold-and-wait, no-preemption, and circular wait.

**Key Theoretical Concepts:**
- Deadlock conditions, detection and avoidance (lock ordering, timeouts)

**Interviewer Expectation:**
Should explain prevention strategies.

**Depth Expected:** Intermediate

---

### 203. WHAT ARE THE IMPORTANT METHODS IN JAVA FOR INTER-THREAD COMMUNICATION?

**Expected Answer (2-Year Level):**
`wait()`, `notify()`, and `notifyAll()` (object monitor methods) and higher-level constructs: `BlockingQueue`, `CountDownLatch`, `CyclicBarrier`, `Semaphore`, `Exchanger`.

**Key Theoretical Concepts:**
- Monitor-based communication vs concurrent utilities

**Interviewer Expectation:**
Should prefer java.util.concurrent utilities for clarity and safety.

**Depth Expected:** Intermediate

---

### 204. WHAT IS THE USE OF WAIT METHOD?

**Expected Answer (2-Year Level):**
`wait()` releases the monitor and suspends the thread until `notify()`/`notifyAll()` is called on the same object or it is interrupted. It must be called while holding the monitor (`synchronized`).

**Key Theoretical Concepts:**
- Monitor release, spurious wakeups, loop condition in waits

**Interviewer Expectation:**
Should show correct usage pattern in a loop.

**Depth Expected:** Intermediate

---

### 205. WHAT IS THE USE OF NOTIFY METHOD?

**Expected Answer (2-Year Level):**
`notify()` wakes one thread waiting on the object's monitor. The awakened thread will reacquire the monitor and resume. Prefer `notifyAll()` when multiple threads may be waiting.

**Key Theoretical Concepts:**
- Thread wakeup semantics, ordering

**Interviewer Expectation:**
Should caution about lost notifications and spurious wakeups.

**Depth Expected:** Intermediate

---

### 206. WHAT IS THE USE OF NOTIFYALL METHOD?

**Expected Answer (2-Year Level):**
`notifyAll()` wakes all threads waiting on the object's monitor; each will compete for the monitor. Useful to avoid missed signals when multiple conditions are involved.

**Key Theoretical Concepts:**
- Use in condition-based waiting

**Interviewer Expectation:**
Should explain when to use notifyAll over notify.

**Depth Expected:** Intermediate

---

### 207. CAN YOU WRITE A SYNCHRONIZED PROGRAM WITH WAIT AND NOTIFY METHODS?

**Expected Answer (2-Year Level):**
Yes — classic producer-consumer using a shared buffer:
```java
class Buffer {
  private Queue<Integer> q = new LinkedList<>();
  private int capacity = 5;
  public synchronized void put(int v) throws InterruptedException {
    while (q.size() == capacity) wait();
    q.add(v);
    notifyAll();
  }
  public synchronized int take() throws InterruptedException {
    while (q.isEmpty()) wait();
    int v = q.remove();
    notifyAll();
    return v;
  }
}
```

**Key Theoretical Concepts:**
- Monitor pattern, wait-loop, notifyAll to avoid lost wakeups

**Interviewer Expectation:**
Should write correct wait-loop patterns and explain potential issues.

**Depth Expected:** Intermediate to practical

---

End of MULTI THREADING (Questions 185-207)
