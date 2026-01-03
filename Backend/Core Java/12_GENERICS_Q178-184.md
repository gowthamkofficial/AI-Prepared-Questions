
## Topic: GENERICS (Questions 178-184)


---

### 178. WHAT ARE GENERICS?

**Expected Answer :**
Generics enable classes and methods to operate on types specified as parameters, providing compile-time type safety and removing the need for casts (e.g., `List<String>`).

**Key Theoretical Concepts:**
- Type parameters, compile-time type checking, type erasure

**Interviewer Expectation:**
Should explain benefits and basic syntax.

**Red Flags:**
- Ignoring raw types

**Depth Expected:** Intermediate

---

### 179. WHY DO WE NEED GENERICS? CAN YOU GIVE AN EXAMPLE OF HOW GENERICS MAKE A PROGRAM MORE FLEXIBLE?

**Expected Answer :**
Generics provide type safety and reduce casts. Example: `List<String>` prevents adding non-string elements and eliminates casting on retrieval. Generics allow reusable algorithms that work across types.

**Key Theoretical Concepts:**
- Reusability and safety, generic APIs

**Interviewer Expectation:**
Should present a simple before/after example.

**Depth Expected:** Beginner to Intermediate

---

### 180. HOW DO YOU DECLARE A GENERIC CLASS?

**Expected Answer :**
`class Box<T> { private T value; public T get(){return value;} public void set(T v){value = v;} }`

**Key Theoretical Concepts:**
- Type parameter syntax and usage

**Interviewer Expectation:**
Should provide correct syntax and usage example.

**Depth Expected:** Beginner

---

### 181. WHAT ARE THE RESTRICTIONS IN USING GENERIC TYPE THAT IS DECLARED IN A CLASS DECLARATION?

**Expected Answer :**
Restrictions include: cannot instantiate `new T()`, cannot create arrays of parameterized types (`new T[10]`), cannot use primitive type parameters, and type parameters are erased at runtime (type erasure).

**Key Theoretical Concepts:**
- Type erasure implications, workarounds (Class<T> token, reflection)

**Interviewer Expectation:**
Should list common limitations and practical workarounds.

**Depth Expected:** Intermediate

---

### 182. HOW CAN WE RESTRICT GENERICS TO A SUBCLASS OF PARTICULAR CLASS?

**Expected Answer :**
Use bounded type parameters: `class Foo<T extends Number> {}` restricts `T` to `Number` or subclasses.

**Key Theoretical Concepts:**
- Upper bounded wildcards and type parameters

**Interviewer Expectation:**
Should give syntax examples and explain rationale.

**Depth Expected:** Intermediate

---

### 183. HOW CAN WE RESTRICT GENERICS TO A SUPER CLASS OF PARTICULAR CLASS?

**Expected Answer :**
Use lower-bounded wildcards when consuming: `List<? super Integer>` accepts lists of `Integer` or its supertypes. For methods, use `? super` in parameters.

**Key Theoretical Concepts:**
- PECS (Producer Extends, Consumer Super)

**Interviewer Expectation:**
Should explain PECS rule and show example.

**Depth Expected:** Intermediate

---

### 184. CAN YOU GIVE AN EXAMPLE OF A GENERIC METHOD?

**Expected Answer :**
`public static <T> T pickFirst(List<T> list) { return list.get(0); }`

**Key Theoretical Concepts:**
- Method-level generic parameters, type inference

**Interviewer Expectation:**
Should provide correct syntax and usage.

**Depth Expected:** Beginner to Intermediate

---

End of GENERICS (Questions 178-184)
