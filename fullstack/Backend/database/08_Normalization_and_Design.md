# **NORMALIZATION AND DESIGN ANSWERS**

## **Normalization and Design (Questions 85-91)**

### **85. What is normalization?**
** Answer:**
- **Normalization**: Systematic process of organizing data in a database to reduce redundancy and improve data integrity
- **Goal**: Eliminate data anomalies (insertion, update, deletion anomalies) and minimize data duplication
- **Method**: Decompose tables into smaller, related tables while preserving relationships
- **Result**: Database schema that adheres to progressive normal forms (1NF, 2NF, 3NF, etc.)

**Key Objectives**:
1. **Eliminate Redundancy**: Store each fact only once
2. **Ensure Data Integrity**: Maintain accuracy and consistency
3. **Simplify Maintenance**: Easier updates, inserts, deletes
4. **Improve Flexibility**: Easier schema evolution
5. **Reduce Storage**: Minimize duplicate data

**Normalization Process**:
```
Unnormalized Data 
    ↓ Apply Rules
First Normal Form (1NF)
    ↓ Apply Rules  
Second Normal Form (2NF)
    ↓ Apply Rules
Third Normal Form (3NF)
    ↓ Apply Rules
Higher Normal Forms (BCNF, 4NF, 5NF)
```

**Trade-offs**:
- **Advantages**: Data consistency, integrity, easier maintenance
- **Disadvantages**: More tables, complex joins, potential performance impact

**Theoretical Foundation**: Based on relational theory and functional dependencies

**Theoretical Keywords**: Data organization, Redundancy elimination, Anomaly prevention, Functional dependencies, Normal forms

### **86. Types of normalization**
** Answer:**
**Normal Forms Hierarchy** (from lowest to highest):

1. **First Normal Form (1NF)**:
   - Basic structural requirements
   - Atomic values, no repeating groups

2. **Second Normal Form (2NF)**:
   - Builds on 1NF
   - Removes partial dependencies

3. **Third Normal Form (3NF)**:
   - Builds on 2NF
   - Removes transitive dependencies

**Higher Normal Forms**:

4. **Boyce-Codd Normal Form (BCNF)**:
   - Stricter version of 3NF
   - All determinants must be candidate keys

5. **Fourth Normal Form (4NF)**:
   - Handles multi-valued dependencies
   - Eliminate independent multi-valued facts

6. **Fifth Normal Form (5NF) / Project-Join Normal Form (PJNF)**:
   - Eliminate join dependencies
   - Lossless decomposition

**Specialized Normal Forms**:

7. **Domain-Key Normal Form (DKNF)**:
   - Ultimate normalization
   - Every constraint is logical consequence of domain/key constraints

**Normalization Spectrum**:
```
Practical Use:      1NF → 2NF → 3NF → BCNF
Theoretical:      4NF → 5NF → DKNF
```

**Application in Practice**:
- **Most databases**: Normalized to 3NF
- **Highly normalized**: BCNF or higher for critical systems
- **Denormalization**: Often applied after 3NF for performance

**Normalization vs Denormalization Balance**:
```
Over-normalized: Many joins, poor performance
Under-normalized: Redundancy, anomalies
Optimal: 3NF with selective denormalization
```

**Theoretical Keywords**: Normal form hierarchy, Functional dependencies, Multi-valued dependencies, Join dependencies, Progressive refinement

### **87. What is 1NF?**
** Answer:**
- **First Normal Form (1NF)**: Fundamental structural requirement for relational tables
- **Requirements**:
  1. **Atomic Values**: Each column contains only atomic (indivisible) values
  2. **Single Value per Cell**: No repeating groups or arrays in single cell
  3. **Unique Column Names**: Each column has distinct name
  4. **Order Doesn't Matter**: Row/column order insignificant
  5. **No Duplicate Rows**: Each row uniquely identifiable

**1NF Violations and Fixes**:

1. **Repeating Groups** (Comma-separated values):
   ```
   Violation:
   StudentID | Courses
   101       | Math, Science, History
   
   Fix (1NF):
   StudentID | Course
   101       | Math
   101       | Science  
   101       | History
   ```

2. **Multi-valued Cells** (Arrays/Lists):
   ```
   Violation:
   OrderID | Products
   1001    | [{"id":1,"qty":2},{"id":3,"qty":1}]
   
   Fix (1NF):
   OrderID | ProductID | Quantity
   1001    | 1         | 2
   1001    | 3         | 1
   ```

3. **Composite Values**:
   ```
   Violation:
   Employee | FullName
   E101     | John Smith (First: John, Last: Smith)
   
   Fix (1NF):
   Employee | FirstName | LastName
   E101     | John      | Smith
   ```

**Key Characteristics**:
1. **Flat Structure**: No nested tables or repeating groups
2. **Primary Key**: Identifies each row uniquely (implicit requirement)
3. **Deterministic Access**: Each cell value accessible via table/column/row coordinates

**Technical Implications**:
- **Storage**: Fixed column structure
- **Querying**: Simple column references
- **Constraints**: Domain constraints apply to atomic values

**1NF Example Transformation**:
```
UNF (Unnormalized):
┌─────────┬──────────────────────────────┐
│ OrderID │ Products                     │
├─────────┼──────────────────────────────┤
│ 1001    │ Pen(2), Paper(5), Stapler(1) │
└─────────┴──────────────────────────────┘

1NF:
┌─────────┬───────────┬──────────┐
│ OrderID │ Product   │ Quantity │
├─────────┼───────────┼──────────┤
│ 1001    │ Pen       │ 2        │
│ 1001    │ Paper     │ 5        │
│ 1001    │ Stapler   │ 1        │
└─────────┴───────────┴──────────┘
```

**Importance**: Foundation for all higher normal forms
**Limitation**: Still allows redundancy and anomalies

**Theoretical Keywords**: Atomic values, Repeating groups elimination, Flat table structure, Primary key requirement

### **88. What is 2NF?**
** Answer:**
- **Second Normal Form (2NF)**: Builds on 1NF by eliminating partial dependencies
- **Requirements**:
  1. **Must be in 1NF**
  2. **No Partial Dependencies**: Every non-prime attribute fully functionally dependent on primary key
- **Partial Dependency**: Non-key attribute depends on only part of composite primary key

**Understanding Dependencies**:

1. **Full Functional Dependency**:
   ```
   Composite PK (A, B) → C
   Both A and B together determine C
   C is fully dependent on PK
   ```

2. **Partial Dependency**:
   ```
   Composite PK (A, B) → C
   But actually: A → C (C depends only on A)
   C is partially dependent on PK (2NF violation)
   ```

**2NF Violation Example**:
```
Violation (Order Items table):
┌──────────┬────────────┬─────────────┬──────────────┐
│ OrderID  │ ProductID  │ ProductName │ Quantity     │ ← 2NF Violation
├──────────┼────────────┼─────────────┼──────────────┤
│ 1001     │ P001       │ Laptop      │ 2            │
│ 1001     │ P002       │ Mouse       │ 3            │
│ 1002     │ P001       │ Laptop      │ 1            │
└──────────┴────────────┴─────────────┴──────────────┘
Composite PK: (OrderID, ProductID)

Problem: ProductName depends only on ProductID (partial dependency)
Fix: Move ProductName to separate Products table
```

**2NF Normalization Process**:

1. **Identify Composite Primary Key**
2. **Find Partial Dependencies**: Non-key attributes depending on part of PK
3. **Create New Tables**: Move partially dependent attributes to new tables
4. **Establish Relationships**: Foreign keys to maintain relationships

**After 2NF Example**:
```
Orders Table:                    Products Table:
┌──────────┬────────────┐        ┌────────────┬─────────────┐
│ OrderID  │ ProductID  │        │ ProductID  │ ProductName │
├──────────┼────────────┤        ├────────────┼─────────────┤
│ 1001     │ P001       │        │ P001       │ Laptop      │
│ 1001     │ P002       │        │ P002       │ Mouse       │
│ 1002     │ P001       │        └────────────┴─────────────┘
└──────────┴────────────┘

OrderItems Table:
┌──────────┬────────────┬──────────┐
│ OrderID  │ ProductID  │ Quantity │
├──────────┼────────────┼──────────┤
│ 1001     │ P001       │ 2        │
│ 1001     │ P002       │ 3        │
│ 1002     │ P001       │ 1        │
└──────────┴────────────┴──────────┘
```

**When 2NF Applies**:
- Only relevant for tables with **composite primary keys**
- Tables with single-column PK automatically satisfy 2NF

**Benefits**:
1. **Reduced Redundancy**: ProductName stored once per product
2. **Update Consistency**: Change product name in one place
3. **Insert Anomaly Prevention**: Can add products without orders
4. **Delete Anomaly Prevention**: Delete orders without losing product info

**Theoretical Keywords**: Partial dependency elimination, Full functional dependency, Composite key handling, Non-prime attributes

### **89. What is 3NF?**
** Answer:**
- **Third Normal Form (3NF)**: Builds on 2NF by eliminating transitive dependencies
- **Requirements**:
  1. **Must be in 2NF**
  2. **No Transitive Dependencies**: No non-key attribute depends on another non-key attribute
- **Transitive Dependency**: A → B → C, where A is PK, B and C are non-key attributes

**Transitive Dependency Concept**:
```
If: PK → B and B → C
Then: PK → C (transitively through B)
3NF requires: Remove C if it depends on B instead of directly on PK
```

**3NF Violation Example**:
```
Violation (Employees table):
┌──────────┬──────────┬────────────┬──────────────┐
│ EmpID    │ DeptID   │ DeptName   │ DeptLocation │ ← 3NF Violation
├──────────┼──────────┼────────────┼──────────────┤
│ E101     │ D001     │ IT         │ Building A   │
│ E102     │ D001     │ IT         │ Building A   │
│ E103     │ D002     │ HR         │ Building B   │
└──────────┴──────────┴────────────┴──────────────┘
PK: EmpID

Problem: 
EmpID → DeptID (direct dependency)
DeptID → DeptName, DeptLocation (direct)
Therefore: EmpID → DeptName, DeptLocation (transitive)
DeptName and DeptLocation depend on DeptID, not EmpID
```

**3NF Normalization Process**:

1. **Identify Transitive Dependencies**: Find non-key attributes depending on other non-key attributes
2. **Create New Tables**: Move transitively dependent attributes to new tables
3. **Establish Relationships**: Foreign keys to maintain relationships

**After 3NF Example**:
```
Employees Table:              Departments Table:
┌──────────┬──────────┐        ┌──────────┬────────────┬──────────────┐
│ EmpID    │ DeptID   │        │ DeptID   │ DeptName   │ DeptLocation │
├──────────┼──────────┤        ├──────────┼────────────┼──────────────┤
│ E101     │ D001     │        │ D001     │ IT         │ Building A   │
│ E102     │ D001     │        │ D002     │ HR         │ Building B   │
│ E103     │ D002     │        └──────────┴────────────┴──────────────┘
└──────────┴──────────┘
```

**3NF Formal Definition** (Codd's Definition):
- For every non-trivial functional dependency X → Y:
  1. X is a superkey, OR
  2. Y is a prime attribute (part of candidate key)

**Alternative Definition** (Simpler):
- Every non-key attribute must provide a fact about:
  1. The key
  2. The whole key
  3. And nothing but the key

**Benefits of 3NF**:
1. **Eliminate Update Anomalies**: Change department info in one place
2. **Eliminate Insert Anomalies**: Add departments without employees
3. **Eliminate Delete Anomalies**: Delete employees without losing department info
4. **Data Integrity**: Maintains referential integrity

**Common 3NF Patterns**:
1. **Lookup Tables**: Separate tables for codes/descriptions
2. **Reference Data**: Countries, states, categories
3. **Master-Detail**: Orders/OrderItems, Customers/Addresses

**3NF vs 2NF**:
- **2NF**: Eliminates partial dependencies (composite PK issues)
- **3NF**: Eliminates transitive dependencies (non-key to non-key dependencies)

**Theoretical Keywords**: Transitive dependency elimination, Non-key attribute independence, Boyce-Codd refinement, Fact about the key

### **90. What is denormalization?**
** Answer:**
- **Denormalization**: Intentional introduction of redundancy into a normalized database for performance optimization
- **Purpose**: Improve read performance at the cost of write performance and data integrity
- **Controlled Redundancy**: Strategic duplication of data to reduce joins
- **Trade-off**: Balance between normalization principles and performance requirements

**Denormalization Techniques**:

1. **Flattening Tables**:
   - Combine normalized tables into fewer tables
   - Reduce join operations
   ```sql
   -- Normalized:
   Orders JOIN Customers JOIN Addresses
   -- Denormalized:
   Orders with customer and address columns
   ```

2. **Adding Derived Columns**:
   - Store calculated values
   - Avoid runtime calculations
   ```sql
   -- Normalized: Calculate total from order items
   -- Denormalized: Store total_amount in orders table
   ```

3. **Creating Summary Tables**:
   - Pre-aggregated data
   - Fast reporting
   ```sql
   -- Daily sales summary table
   -- Instead of aggregating millions of rows daily
   ```

4. **Horizontal Partitioning**:
   - Duplicate frequently accessed columns
   - Reduce table width
   ```sql
   -- Hot columns in main table
   -- Cold columns in separate table
   ```

5. **Materialized Views**:
   - Pre-computed query results
   - Automatically refreshed

**Denormalization Example**:
```
Normalized (3NF):
Orders: OrderID, CustomerID
Customers: CustomerID, Name, Email
OrderItems: OrderID, ProductID, Quantity
Products: ProductID, Name, Price

Denormalized for reporting:
Order_Report: OrderID, CustomerName, ProductName, Quantity, Price, Total
-- Combines data from 4 tables into 1
```

**Characteristics of Denormalized Data**:
1. **Controlled Redundancy**: Intentional, not accidental
2. **Performance-Driven**: Justified by measurable performance gains
3. **Documented**: Clearly documented in schema design
4. **Maintained**: Update strategies defined (triggers, ETL, application logic)

**When Denormalization is Appropriate**:
1. **Read-Intensive Workloads**: Reporting, analytics, dashboards
2. **Real-time Requirements**: Low-latency read operations
3. **Historical Data**: Data rarely changes after creation
4. **Cache-like Behavior**: Frequently accessed reference data

**Risks and Challenges**:
1. **Data Integrity**: Redundancy can lead to inconsistencies
2. **Update Anomalies**: Multiple copies to update
3. **Storage Overhead**: Increased disk space
4. **Complex Maintenance**: Need for synchronization mechanisms

**Theoretical Keywords**: Controlled redundancy, Performance optimization, Read vs write trade-off, Strategic duplication, Schema denormalization

### **91. When to use denormalization?**
** Answer:**
**Strategic Denormalization Decision Points**:

1. **Performance Bottlenecks**:
   - Excessive joins causing slow queries
   - Real-time performance requirements not met
   - Read-heavy workloads with normalized schema

2. **Reporting and Analytics**:
   - Data warehousing/BI applications
   - Complex aggregations across multiple tables
   - Historical data analysis
   ```sql
   -- Data warehouse fact tables often denormalized
   -- Star/snowflake schemas use controlled redundancy
   ```

3. **Caching Frequently Accessed Data**:
   - Reference/lookup data used in many queries
   - Static or slowly-changing dimensions
   - Country lists, product categories, status codes

4. **Real-time Applications**:
   - High-frequency trading systems
   - Gaming leaderboards
   - E-commerce product listings
   - Need for sub-millisecond response times

5. **Historical/Archival Data**:
   - Data that won't change after creation
   - Audit trails, logs, historical records
   - No update/delete operations expected

6. **Mobile/Offline Applications**:
   - Local databases with limited join capabilities
   - Reduced complexity for client-side processing
   - Bandwidth optimization

**Specific Scenarios**:

1. **E-commerce Product Display**:
   ```
   Normalized: Products, Categories, Suppliers, Inventory
   Denormalized: Product_Display table with all needed columns
   Reason: Product pages need data from multiple tables instantly
   ```

2. **Dashboard/Reporting**:
   ```
   Normalized: Sales, Customers, Products, Time dimensions
   Denormalized: Sales_Summary with pre-joined, aggregated data
   Reason: Dashboard queries need fast response times
   ```

3. **Social Media Feeds**:
   ```
   Normalized: Users, Posts, Comments, Likes
   Denormalized: Feed table with user names, post content
   Reason: Real-time feed generation requires low latency
   ```

**Decision Framework**:

1. **Measure First**:
   - Profile query performance
   - Identify actual bottlenecks
   - Don't denormalize prematurely

2. **Cost-Benefit Analysis**:
   ```
   Benefits: Faster reads, simpler queries
   Costs: Slower writes, storage, maintenance complexity
   ```

3. **Consider Alternatives First**:
   - Better indexing
   - Query optimization
   - Materialized views
   - Database tuning

4. **Implementation Strategies**:
   - Start with normalized design
   - Denormalize incrementally
   - Use materialized views for transparency
   - Implement synchronization mechanisms

**When NOT to Denormalize**:

1. **OLTP Systems**: Where data integrity is critical
2. **Frequently Updated Data**: High write/update volumes
3. **Limited Resources**: Can't maintain synchronization
4. **Early Development**: Premature optimization

**Modern Approaches**:
1. **Read Replicas**: Denormalized copies for reporting
2. **CQRS Pattern**: Separate read/write models
3. **Event Sourcing**: Derive denormalized views from events
4. **CDC (Change Data Capture)**: Synchronize denormalized tables

**Best Practices**:
1. **Document Thoroughly**: Every denormalization decision
2. **Implement Synchronization**: Triggers, jobs, or application logic
3. **Monitor Consistency**: Regular data integrity checks
4. **Review Periodically**: Re-evaluate as usage patterns change

**Theoretical Keywords**: Performance optimization, Read vs write trade-off, Strategic redundancy, Use case analysis, Cost-benefit evaluation

---

## **Normalization vs Denormalization Summary**

### **Normalization Spectrum**:
```
Under-normalized:   Redundancy → Anomalies → Inconsistency
         ↓
       1NF: Atomic values, no repeating groups
         ↓
       2NF: No partial dependencies (composite PKs)
         ↓
       3NF: No transitive dependencies (most practical)
         ↓
       BCNF: Stricter 3NF (all determinants are keys)
         ↓
Over-normalized:   Many joins → Complexity → Performance issues
```

### **Practical Guidelines**:

**Normalize When**:
1. Designing OLTP systems
2. Data integrity is paramount
3. Multiple applications access data
4. Long-term maintainability needed

**Denormalize When**:
1. Read performance is critical
2. Reporting/analytics workloads
3. Historical/static data
4. Measured performance bottlenecks

### **Hybrid Approach**:
```
Base Schema: Fully normalized (3NF/BCNF)
    ↓
Reporting Layer: Denormalized views/tables
    ↓
Caching Layer: Highly denormalized for performance
```

### **Common Patterns**:

1. **OLTP Backend**: Normalized (3NF)
2. **Data Warehouse**: Denormalized (star schema)
3. **Reporting Database**: Denormalized views of OLTP
4. **Cache/Index**: Highly denormalized (Elasticsearch, Redis)

### **Evolution Strategy**:
1. Start with normalized design
2. Monitor performance
3. Identify bottlenecks
4. Apply targeted denormalization
5. Implement synchronization
6. Continuously monitor and adjust

**You now have comprehensive theoretical knowledge of Database Normalization and Design!** This understanding is crucial for designing efficient, maintainable database schemas and is frequently tested in system design . 📐🚀