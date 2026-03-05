# CLASSES AND OOP ANSWERS

---

## 32. How do classes work in TypeScript?

### Answer:
- TypeScript classes are **syntactic sugar** over JavaScript prototypes
- Support **type annotations** for properties and methods
- Include **access modifiers** (public, private, protected)
- Support **interfaces implementation** with `implements`
- Can have **abstract classes** and **inheritance**
- Compile to ES5/ES6 JavaScript classes

### Theoretical Keywords:
**Class syntax**, **Constructor**, **Properties**, **Methods**,  
**Access modifiers**, **Inheritance**, **implements keyword**

### Example:
```typescript
// Basic class
class Person {
    // Properties with types
    name: string;
    age: number;
    
    // Constructor
    constructor(name: string, age: number) {
        this.name = name;
        this.age = age;
    }
    
    // Method
    greet(): string {
        return `Hello, I'm ${this.name}`;
    }
}

const person = new Person("John", 25);
console.log(person.greet());  // "Hello, I'm John"

// Class with access modifiers
class Employee {
    public name: string;
    private salary: number;
    protected department: string;
    
    constructor(name: string, salary: number, department: string) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
    
    getSalary(): number {
        return this.salary;  // Can access private within class
    }
}

// Class implementing interface
interface Printable {
    print(): void;
}

class Document implements Printable {
    constructor(public content: string) {}
    
    print(): void {
        console.log(this.content);
    }
}
```

---

## 33. Access modifiers: `public`, `private`, `protected`

### Answer:
- **public**: Accessible from **anywhere** (default)
- **private**: Accessible only **within the class** itself
- **protected**: Accessible within class and **subclasses**
- TypeScript-only enforcement (compile-time check)
- ES2022 `#` syntax for true private (runtime enforcement)

### Theoretical Keywords:
**Encapsulation**, **Visibility**, **Access control**,  
**Class members**, **Inheritance access**, **Data hiding**

### Example:
```typescript
class BankAccount {
    public accountNumber: string;      // Accessible everywhere
    private balance: number;           // Only within this class
    protected accountType: string;     // This class + subclasses
    
    constructor(accountNumber: string, initialBalance: number) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.accountType = "Standard";
    }
    
    // Public method
    public getBalance(): number {
        return this.balance;  // Can access private
    }
    
    // Private method
    private logTransaction(amount: number): void {
        console.log(`Transaction: ${amount}`);
    }
    
    // Protected method
    protected calculateInterest(): number {
        return this.balance * 0.05;
    }
    
    deposit(amount: number): void {
        this.balance += amount;
        this.logTransaction(amount);
    }
}

class SavingsAccount extends BankAccount {
    constructor(accountNumber: string, initialBalance: number) {
        super(accountNumber, initialBalance);
        this.accountType = "Savings";  // ✅ Can access protected
    }
    
    addInterest(): void {
        const interest = this.calculateInterest();  // ✅ Can access protected
        // this.balance += interest;  // ❌ Cannot access private
        // this.logTransaction(interest);  // ❌ Cannot access private
    }
}

const account = new BankAccount("123", 1000);
console.log(account.accountNumber);  // ✅ public
// console.log(account.balance);     // ❌ private
// console.log(account.accountType); // ❌ protected
```

---

## 34. What is abstract class?

### Answer:
- **Abstract class** is a class that **cannot be instantiated** directly
- Serves as a **base class** for other classes
- Can contain **abstract methods** (no implementation)
- Can also contain **concrete methods** (with implementation)
- Subclasses **must implement** all abstract methods
- Used to define **common behavior** and **enforce contracts**

### Theoretical Keywords:
**Cannot instantiate**, **Base class**, **Abstract methods**,  
**Concrete methods**, **Must implement**, **Template pattern**

### Example:
```typescript
// Abstract class
abstract class Shape {
    // Concrete property
    color: string;
    
    constructor(color: string) {
        this.color = color;
    }
    
    // Abstract methods - must be implemented by subclasses
    abstract getArea(): number;
    abstract getPerimeter(): number;
    
    // Concrete method - shared implementation
    describe(): string {
        return `A ${this.color} shape with area ${this.getArea()}`;
    }
}

// Cannot instantiate abstract class
// const shape = new Shape("red");  // ❌ Error

// Concrete class extending abstract
class Rectangle extends Shape {
    constructor(
        color: string,
        private width: number,
        private height: number
    ) {
        super(color);
    }
    
    // Must implement abstract methods
    getArea(): number {
        return this.width * this.height;
    }
    
    getPerimeter(): number {
        return 2 * (this.width + this.height);
    }
}

class Circle extends Shape {
    constructor(color: string, private radius: number) {
        super(color);
    }
    
    getArea(): number {
        return Math.PI * this.radius ** 2;
    }
    
    getPerimeter(): number {
        return 2 * Math.PI * this.radius;
    }
}

const rect = new Rectangle("blue", 10, 5);
console.log(rect.describe());  // "A blue shape with area 50"
console.log(rect.getPerimeter());  // 30
```

---

## 35. Difference between abstract class and interface

### Answer:

| Feature | Abstract Class | Interface |
|---------|----------------|-----------|
| **Instantiation** | Cannot instantiate | Cannot instantiate |
| **Implementation** | Can have method bodies | No implementation (TS) |
| **Properties** | Can have values | Only declarations |
| **Constructor** | Can have constructor | No constructor |
| **Access Modifiers** | Supported | Not supported |
| **Multiple** | Single inheritance | Multiple implementation |
| **Runtime** | Exists in JavaScript | Erased (type-only) |

### Theoretical Keywords:
**Implementation vs contract**, **Single vs multiple inheritance**,  
**Runtime existence**, **Shared behavior**, **Type checking only**

### Example:
```typescript
// Interface - pure contract
interface Flyable {
    fly(): void;
    altitude: number;
}

interface Swimmable {
    swim(): void;
    depth: number;
}

// Abstract class - partial implementation
abstract class Animal {
    constructor(public name: string) {}
    
    // Concrete method with implementation
    eat(): void {
        console.log(`${this.name} is eating`);
    }
    
    // Abstract method - no implementation
    abstract makeSound(): void;
}

// Class can extend ONE abstract class
// Class can implement MULTIPLE interfaces
class Duck extends Animal implements Flyable, Swimmable {
    altitude: number = 0;
    depth: number = 0;
    
    constructor(name: string) {
        super(name);
    }
    
    makeSound(): void {
        console.log("Quack!");
    }
    
    fly(): void {
        this.altitude = 100;
        console.log("Flying...");
    }
    
    swim(): void {
        this.depth = 5;
        console.log("Swimming...");
    }
}

const duck = new Duck("Donald");
duck.eat();        // From abstract class
duck.makeSound();  // Implemented abstract method
duck.fly();        // From Flyable interface
duck.swim();       // From Swimmable interface
```

---

## 36. What is constructor parameter properties?

### Answer:
- **Shorthand syntax** to declare and initialize class properties
- Add **access modifier** or **readonly** in constructor parameters
- Automatically creates and assigns the property
- Reduces **boilerplate code** significantly
- Works with `public`, `private`, `protected`, and `readonly`

### Theoretical Keywords:
**Parameter properties**, **Shorthand syntax**, **Auto-initialization**,  
**Boilerplate reduction**, **Constructor simplification**

### Example:
```typescript
// Without parameter properties (verbose)
class PersonVerbose {
    public name: string;
    private age: number;
    readonly id: number;
    
    constructor(name: string, age: number, id: number) {
        this.name = name;
        this.age = age;
        this.id = id;
    }
}

// With parameter properties (concise)
class PersonConcise {
    constructor(
        public name: string,
        private age: number,
        readonly id: number
    ) {
        // Properties automatically created and assigned
    }
    
    getAge(): number {
        return this.age;
    }
}

const person = new PersonConcise("John", 25, 1);
console.log(person.name);  // ✅ public
// console.log(person.age); // ❌ private
console.log(person.id);    // ✅ readonly

// Mixing parameter properties with regular parameters
class Employee {
    department: string;
    
    constructor(
        public name: string,
        private salary: number,
        department: string  // No modifier = regular parameter
    ) {
        this.department = department.toUpperCase();
    }
}

// With default values
class Config {
    constructor(
        public apiUrl: string = "https://api.example.com",
        public timeout: number = 5000,
        private apiKey: string = ""
    ) {}
}

const config = new Config();  // Uses all defaults
```

---

## 37. What is inheritance in TypeScript?

### Answer:
- **Inheritance** allows a class to inherit properties and methods from another
- Uses the **`extends`** keyword
- Child class can **override** parent methods
- **`super`** keyword accesses parent class constructor/methods
- TypeScript supports **single inheritance** (one parent class)
- Can combine with **interface implementation** for multiple inheritance-like behavior

### Theoretical Keywords:
**extends keyword**, **super keyword**, **Method overriding**,  
**Parent/child relationship**, **Single inheritance**, **Polymorphism**

### Example:
```typescript
// Parent class
class Vehicle {
    constructor(
        public brand: string,
        public year: number
    ) {}
    
    start(): void {
        console.log(`${this.brand} is starting...`);
    }
    
    stop(): void {
        console.log(`${this.brand} is stopping...`);
    }
    
    getInfo(): string {
        return `${this.year} ${this.brand}`;
    }
}

// Child class
class Car extends Vehicle {
    constructor(
        brand: string,
        year: number,
        public numDoors: number
    ) {
        super(brand, year);  // Call parent constructor
    }
    
    // Override parent method
    start(): void {
        console.log("Checking seatbelt...");
        super.start();  // Call parent method
    }
    
    // New method
    honk(): void {
        console.log("Beep beep!");
    }
    
    // Override with additional info
    getInfo(): string {
        return `${super.getInfo()} with ${this.numDoors} doors`;
    }
}

class ElectricCar extends Car {
    constructor(
        brand: string,
        year: number,
        numDoors: number,
        public batteryCapacity: number
    ) {
        super(brand, year, numDoors);
    }
    
    charge(): void {
        console.log(`Charging ${this.batteryCapacity}kWh battery...`);
    }
    
    start(): void {
        console.log("Electric motor starting silently...");
        // Different behavior, doesn't call super.start()
    }
}

const myCar = new Car("Toyota", 2023, 4);
myCar.start();      // "Checking seatbelt..." then "Toyota is starting..."
console.log(myCar.getInfo());  // "2023 Toyota with 4 doors"

const tesla = new ElectricCar("Tesla", 2024, 4, 100);
tesla.start();      // "Electric motor starting silently..."
tesla.charge();     // "Charging 100kWh battery..."
```

---
