# Java Interview Questions & Answers Guide
## Topic: FUNCTIONAL PROGRAMMING - LAMBDA EXPRESSIONS AND STREAMS (Questions 208-220)
### For 2-Year Experienced Java Backend Developers

---

### 208. WHAT IS FUNCTIONAL PROGRAMMING?

**Expected Answer (2-Year Level):**
Functional programming emphasizes functions as first-class values, immutability, and side-effect-free operations. In Java, this is supported via lambdas, method references, and streams for declarative data processing.

**Key Theoretical Concepts:**
- Pure functions, immutability, higher-order functions

**Interviewer Expectation:**
Should explain basic FP principles and how Java adopts some of them.

**Depth Expected:** Beginner to Intermediate

---

### 209. CAN YOU GIVE AN EXAMPLE OF FUNCTIONAL PROGRAMMING?

**Expected Answer (2-Year Level):**
Using streams to transform and collect:
```java
List<String> names = people.stream()
    .map(Person::getName)
    .filter(name -> name.startsWith("A"))
    .collect(Collectors.toList());
```

**Key Theoretical Concepts:**
- Declarative pipelines, immutability of intermediate results

**Interviewer Expectation:**
Should provide concise example using lambdas/streams.

**Depth Expected:** Intermediate

---

### 210. WHAT IS A STREAM?

**Expected Answer (2-Year Level):**
A `Stream` is a sequence of elements supporting functional-style operations (map, filter, reduce). Streams are lazy, can be parallelized, and do not modify the underlying data source.

**Key Theoretical Concepts:**
- Intermediate vs terminal operations, laziness, short-circuiting

**Interviewer Expectation:**
Should know stream lifecycle and basic operations.

**Depth Expected:** Intermediate

---

### 211. EXPLAIN ABOUT STREAMS WITH AN EXAMPLE?

**Expected Answer (2-Year Level):**
Example pipeline:
```java
int sum = list.stream()
    .filter(x -> x % 2 == 0)
    .mapToInt(Integer::intValue)
    .sum();
```
Explain that intermediate ops build pipeline; terminal op triggers processing.

**Key Theoretical Concepts:**
- Stateless vs stateful ops, parallel streams caveats

**Interviewer Expectation:**
Should show awareness of side-effects and ordering.

**Depth Expected:** Intermediate

---

### 212. WHAT ARE TERMINAL OPERATIONS IN STREAMS?

**Expected Answer (2-Year Level):**
Terminal operations produce a result or side-effect and trigger pipeline evaluation: `collect`, `forEach`, `reduce`, `count`, `sum`, `findFirst`.

**Key Theoretical Concepts:**
- Laziness: without terminal op, pipelines aren't executed

**Interviewer Expectation:**
Should list common terminal ops and differences.

**Depth Expected:** Beginner to Intermediate

---

### 213. WHAT ARE METHOD REFERENCES?

**Expected Answer (2-Year Level):**
Method references are shorthand lambda expressions referencing existing methods: `Class::staticMethod`, `instance::instanceMethod`, `Class::instanceMethod`, `Class::new` (constructor references).

**Key Theoretical Concepts:**
- Readability, method reference forms

**Interviewer Expectation:**
Should show examples and when they simplify lambdas.

**Depth Expected:** Beginner to Intermediate

---

### 214. WHAT ARE LAMBDA EXPRESSIONS?

**Expected Answer (2-Year Level):**
Lambdas are anonymous functions introduced in Java 8 with syntax `(params) -> expression` or block body. They implement functional interfaces (single abstract method).

**Key Theoretical Concepts:**
- Target typing, functional interface, closure capturing final/effectively final variables

**Interviewer Expectation:**
Should write a simple lambda and explain captured variable rules.

**Depth Expected:** Intermediate

---

### 215. CAN YOU GIVE AN EXAMPLE OF LAMBDA EXPRESSION?

**Expected Answer (2-Year Level):**
`Comparator<String> cmp = (a, b) -> a.length() - b.length();` or `Runnable r = () -> System.out.println("hi");`

**Key Theoretical Concepts:**
- Type inference and functional interfaces

**Interviewer Expectation:**
Should provide concise examples.

**Depth Expected:** Beginner

---

### 216. CAN YOU EXPLAIN THE RELATIONSHIP BETWEEN LAMBDA EXPRESSION AND FUNCTIONAL INTERFACES?

**Expected Answer (2-Year Level):**
Lambdas implement functional interfaces (interfaces with a single abstract method). The compiler infers the target type, enabling concise lambda syntax where a functional interface is expected.

**Key Theoretical Concepts:**
- `@FunctionalInterface` annotation, SAM type

**Interviewer Expectation:**
Should explain mapping and give examples like `Predicate`, `Function`, `Consumer`.

**Depth Expected:** Intermediate

---

### 217. WHAT IS A PREDICATE?

**Expected Answer (2-Year Level):**
`Predicate<T>` is a functional interface representing a boolean-valued function `T -> boolean`. Commonly used in `filter` operations: `Predicate<String> p = s -> s.isEmpty();`.

**Key Theoretical Concepts:**
- Combinators: `and`, `or`, `negate`

**Interviewer Expectation:**
Should know usage and combinators.

**Depth Expected:** Beginner to Intermediate

---

### 218. WHAT IS THE FUNCTIONAL INTERFACE - FUNCTION?

**Expected Answer (2-Year Level):**
`Function<T,R>` represents a function that accepts `T` and returns `R`. Useful in `map` operations: `Function<Person,String> f = Person::getName;`.

**Key Theoretical Concepts:**
- `andThen`, `compose` combinators

**Interviewer Expectation:**
Should show basic example and composition.

**Depth Expected:** Intermediate

---

### 219. WHAT IS A CONSUMER?

**Expected Answer (2-Year Level):**
`Consumer<T>` represents an operation that accepts a single argument and returns no result (side-effect). Example: `Consumer<String> c = System.out::println;` used in `forEach`.

**Key Theoretical Concepts:**
- Chaining consumers via `andThen`

**Interviewer Expectation:**
Should understand typical use for side-effectful operations.

**Depth Expected:** Beginner

---

### 220. CAN YOU GIVE EXAMPLES OF FUNCTIONAL INTERFACES WITH MULTIPLE ARGUMENTS?

**Expected Answer (2-Year Level):**
`BiFunction<T,U,R>`, `BiConsumer<T,U>`, `BiPredicate<T,U>` are common multi-arg functional interfaces. Example: `BiFunction<Integer,Integer,Integer> add = (a,b) -> a+b;`.

**Key Theoretical Concepts:**
- Family of function interfaces for arity >1

**Interviewer Expectation:**
Should give examples and use-cases.

**Depth Expected:** Intermediate

---

End of FUNCTIONAL PROGRAMMING (Questions 208-220)
