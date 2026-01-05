# **INDEXES AND PERFORMANCE ANSWERS**

## **Indexes and Performance (Questions 64-73)**

### **64. What is an index?**
**Interviewer Answer:**
- **Index**: A database structure that improves data retrieval speed
- **Analogy**: Like a book's index or library catalog system
- **Purpose**: Provide quick lookup path to data without scanning entire table
- **Creates**: Sorted reference to table data based on indexed columns

**Key Characteristics**:
1. **Data Structure**: Typically B-tree (balanced tree) in most databases
2. **Separate Storage**: Stored separately from table data (except clustered)
3. **Contains**: Index keys + pointers to actual data rows
4. **Trade-off**: Faster reads vs slower writes (index maintenance)

**How It Works**:
```
Without Index (Full Table Scan):
Table Scan (Slow) - Check every row sequentially
┌─────────┐
│ Row 1   │
│ Row 2   │ ← Sequential scanning
│ Row 3   │
│ ...     │
│ Row N   │
└─────────┘

With Index (Index Seek):
Index Structure (Fast)
┌─────────┐
│ Key A → Row1│
│ Key B → Row4│ ← Direct lookup
│ Key C → Row2│
│ Key D → Row3│
└─────────┘
```

**Theoretical Keywords**: B-tree structure, Lookup optimization, Data structure, Search performance, Storage trade-off

### **65. Types of index**
**Interviewer Answer:**
**Primary Index Types**:

1. **Clustered Index**:
   - Defines physical storage order of table data
   - Table data sorted according to clustered index key
   - One per table (since data can only be physically ordered one way)

2. **Non-clustered Index**:
   - Separate structure with sorted index keys + row pointers
   - Multiple per table allowed
   - Contains index keys + pointers to data rows

3. **Unique Index**:
   - Ensures index key values are unique
   - Can be clustered or non-clustered
   - Created automatically for PRIMARY KEY and UNIQUE constraints

4. **Composite Index** (Multi-column Index):
   - Index on multiple columns
   - Column order matters (leftmost prefix principle)
   - Useful for queries filtering/sorting on multiple columns

5. **Full-Text Index**:
   - Specialized for text searching
   - Supports linguistic searches, proximity searches
   - Used for documents, articles, large text fields

6. **Bitmap Index**:
   - Uses bitmap vectors for low-cardinality columns
   - Efficient for columns with few distinct values (gender, status flags)
   - Common in data warehousing, not OLTP

7. **Hash Index**:
   - Uses hash table for exact match lookups
   - Fast for equality comparisons (=)
   - Inefficient for range queries (> , <, BETWEEN)

**Database-Specific Types**:
- **Spatial Index**: For geographical data (GIS)
- **XML Index**: For XML data type columns
- **Filtered Index**: Index on subset of rows (SQL Server)
- **Partial Index**: Similar to filtered (PostgreSQL)
- **Covering Index**: Includes all columns needed by query

**Theoretical Keywords**: Clustered/non-clustered, B-tree/hash/bitmap, Single/multi-column, Storage structure, Indexing methods

### **66. How does an index improve performance?**
**Interviewer Answer:**
**Performance Improvement Mechanisms**:

1. **Reduced I/O Operations**:
   - Instead of scanning entire table (sequential I/O), index enables direct lookup
   - B-tree structure minimizes disk reads (logarithmic search complexity)

2. **Efficient Sorting**:
   - Index maintains sorted order, eliminating need for runtime sorting
   - ORDER BY queries benefit significantly from indexes

3. **Quick Row Location**:
   - Index acts as roadmap to data location
   - Especially effective for selective queries (small result sets)

4. **Covering Index Advantage**:
   - If index contains all columns needed by query, no table access needed
   - Query executed entirely from index (index-only scan)

**Mathematical Efficiency**:
```
Without Index: O(n) linear search
With B-tree Index: O(log n) logarithmic search

Example: 1 million rows
- Full scan: ~1,000,000 comparisons
- Index search: ~20 comparisons (log2(1,000,000) ≈ 20)
```

**How B-tree Index Works**:
```
B-tree Structure:
          [Root Node]
         /     |     \
   [Leaf Nodes] ... [Leaf Nodes]
   /  |  \          /  |  \
[Data Pointers]  [Data Pointers]

Search Process for value 42:
1. Start at root node
2. Compare 42 with node values
3. Follow appropriate branch
4. Repeat at child node
5. Reach leaf node with pointer to row
6. Retrieve data
Typically 3-4 disk reads vs thousands for full scan
```

**Join Optimization**:
- Indexes on join columns enable efficient merge/hash joins
- Without indexes, database may use inefficient nested loop joins

**Theoretical Keywords**: I/O reduction, Logarithmic search, B-tree efficiency, Covering index, Query optimization

### **67. When should an index not be used?**
**Interviewer Answer:**
**When Indexes Are Ineffective or Harmful**:

1. **Small Tables**:
   - Table scan may be faster than index lookup + row retrieval
   - Rule of thumb: < 1000 rows often better without indexes

2. **Columns with Low Cardinality**:
   - Columns with few distinct values (gender, status flags)
   - Index selectivity too low to be useful
   - Exception: Bitmap indexes for data warehousing

3. **Frequently Modified Tables**:
   - Every INSERT/UPDATE/DELETE requires index maintenance
   - High-write tables suffer performance degradation
   - Write-heavy OLTP systems need careful indexing

4. **Columns Used in Calculations**:
   ```sql
   WHERE salary * 1.1 > 50000  -- Index on salary won't help
   WHERE UPPER(name) = 'JOHN'  -- Index on name won't help
   ```

5. **Leading Wildcard Searches**:
   ```sql
   WHERE name LIKE '%son'  -- Index ineffective
   WHERE name LIKE 'Joh%'  -- Index effective (trailing wildcard)
   ```

6. **Tables with Frequent Bulk Operations**:
   - Bulk INSERT/UPDATE/DELETE operations
   - Consider dropping indexes before bulk load, recreating after

7. **Columns Rarely Used in WHERE/JOIN/ORDER BY**:
   - Indexes unused by queries provide no benefit
   - Only maintenance overhead

**Specific Scenarios to Avoid**:

1. **Over-Indexing**:
   - Too many indexes on same table
   - Storage overhead + maintenance cost
   - Confusion for query optimizer

2. **Wide Indexes**:
   - Indexes on many/long columns
   - Large index size reduces cache efficiency
   - Slower index maintenance

3. **Volatile Tables**:
   - Tables with frequent structural changes
   - Index fragmentation issues

**Cost-Benefit Analysis Factors**:
- Read vs Write ratio
- Query patterns
- Data volume
- Hardware resources
- Maintenance windows

**Theoretical Keywords**: Index selectivity, Write overhead, Cardinality, Query patterns, Maintenance cost, Storage overhead

### **68. Difference between clustered and non-clustered index**
**Interviewer Answer:**
| **Clustered Index** | **Non-clustered Index** |
|---------------------|-------------------------|
| **Physical Order**: Determines physical storage order of table data | **Physical Order**: Does not affect physical storage order |
| **Number**: One per table (data can only be sorted one way) | **Number**: Multiple per table allowed |
| **Storage**: Table data stored in index order (index is the table) | **Storage**: Separate structure with pointers to data |
| **Leaf Nodes**: Contain actual data rows | **Leaf Nodes**: Contain index keys + row pointers |
| **Performance**: Faster for range queries (data contiguous) | **Performance**: Additional lookup needed (key → pointer → data) |
| **Size**: No additional storage (data is index) | **Size**: Additional storage required |
| **Primary Use**: Primary key typically clustered | **Primary Use**: Foreign keys, search columns |

**Visual Comparison**:
```
Clustered Index:
Table data physically sorted by index key
┌─────────┐
│ Leaf    │ ← Contains actual data rows
│ Nodes   │    in sorted order
└─────────┘

Non-clustered Index:
Separate index structure
┌─────────┐      ┌─────────┐
│ Index   │ ---→ │ Table   │
│ Structure│      │ Data    │
└─────────┘      └─────────┘
Pointers to data   Actual unordered data
```

**Key Technical Differences**:

1. **Data Organization**:
   - **Clustered**: Data pages physically ordered by index key
   - **Non-clustered**: Data pages unordered, index has separate sorted keys

2. **Lookup Operation**:
   ```sql
   -- Clustered index lookup (1 step):
   Index Key → Data Row (immediate)
   
   -- Non-clustered index lookup (2 steps):
   Index Key → Row Pointer → Data Row
   ```

3. **Range Query Efficiency**:
   - **Clustered**: Excellent (sequential disk reads)
   - **Non-clustered**: Good but may require random I/O

4. **Insertion Cost**:
   - **Clustered**: Higher (may cause page splits)
   - **Non-clustered**: Lower (append to index)

**Storage Implications**:
- **Clustered**: No duplicate storage
- **Non-clustered**: Additional storage for index structure

**Theoretical Keywords**: Physical ordering, Leaf node content, Storage structure, Lookup complexity, Range query performance

### **69. How many clustered indexes can a table have?**
**Interviewer Answer:**
- **Exactly ONE clustered index per table**
- **Fundamental limitation**: Data can only be physically sorted in one order
- **Reason**: Clustered index determines physical storage order of data rows
- **Implied constraint**: Cannot store same data sorted multiple ways physically

**Why Only One**:
```
Physical Storage Reality:
Data pages on disk can only be arranged in one sequence

Imagine a bookshelf:
- Books can be sorted by author (one physical order)
- OR by title (different physical order)
- But NOT both simultaneously

Similarly, table data pages can only be:
- Sorted by primary key (clustered index)
- OR by another column
- But NOT multiple orders at once
```

**Technical Justification**:
1. **Data Pages**: Stored in doubly-linked list in clustered index order
2. **Page Splits**: Maintained during inserts to preserve order
3. **Single Sort Order**: Physically impossible to maintain multiple sort orders

**What You Can Have Instead**:
1. **One clustered index** (primary physical order)
2. **Multiple non-clustered indexes** (secondary logical orders)
3. **Indexed views** (materialized secondary sort orders in some DBMS)

**Default Behavior**:
- **Primary key** typically creates clustered index (but not mandatory)
- If no clustered index defined, table is **heap** (unordered storage)

**Choosing Clustered Index Column**:
Consider columns with:
1. **Ever-increasing values** (identity/sequence columns)
2. **Used in range queries** (BETWEEN, >, <)
3. **Used in ORDER BY frequently**
4. **Low update frequency** (to avoid page splits)
5. **Reasonable size** (not too wide)

**Theoretical Keywords**: Physical storage limitation, Single sort order, Data page organization, Heap tables, Index selection

### **70. What is a composite index?**
**Interviewer Answer:**
- **Composite Index**: Index on multiple columns (2+ columns)
- **Also called**: Multi-column index, concatenated index
- **Column Order Matters**: Leftmost prefix principle determines usability
- **Structure**: Index keys are concatenation of column values

**Key Characteristics**:

1. **Leftmost Prefix Principle**:
   - Index (A, B, C) can be used for queries on:
     - (A)
     - (A, B)
     - (A, B, C)
   - But NOT for:
     - (B) or (C) alone
     - (B, C) without A

2. **Column Order Significance**:
   ```sql
   -- Index (last_name, first_name) useful for:
   WHERE last_name = 'Smith'
   WHERE last_name = 'Smith' AND first_name = 'John'
   
   -- But NOT for:
   WHERE first_name = 'John'  -- Can't use index
   ```

3. **Sort Order Per Column**:
   - Each column can have ASC/DESC order
   - Important for ORDER BY optimization

**Internal Structure**:
```
Composite Index (A, B, C):
Index entries sorted by:
1. Column A values
2. Then Column B values (for same A)
3. Then Column C values (for same A and B)

Example Index entries:
[A=1, B=10, C=100] → Row Pointer
[A=1, B=10, C=200] → Row Pointer
[A=1, B=20, C=100] → Row Pointer
[A=2, B=10, C=100] → Row Pointer
```

**When to Use Composite Indexes**:

1. **Multiple Column WHERE Clauses**:
   ```sql
   -- Good for: WHERE dept = 'IT' AND status = 'Active'
   CREATE INDEX idx_dept_status ON Employees(dept, status);
   ```

2. **Covering Queries**:
   ```sql
   -- Index includes all selected columns
   CREATE INDEX idx_covering ON Orders(customer_id, order_date, amount);
   SELECT customer_id, order_date, amount 
   FROM Orders 
   WHERE customer_id = 123;  -- Index-only scan
   ```

3. **Multi-column Sorting**:
   ```sql
   -- Good for: ORDER BY last_name, first_name
   CREATE INDEX idx_name_sort ON Employees(last_name, first_name);
   ```

4. **Join Optimization**:
   ```sql
   -- Composite index on foreign key + frequently filtered column
   CREATE INDEX idx_order_customer_status ON Orders(customer_id, status);
   ```

**Design Considerations**:

1. **Column Cardinality**: Put high-selectivity columns first
2. **Query Patterns**: Design based on actual query WHERE/ORDER BY clauses
3. **Index Width**: Avoid too many/large columns
4. **Maintenance Cost**: More columns = larger index = slower updates

**Theoretical Keywords**: Multi-column indexing, Leftmost prefix, Column ordering, Concatenated keys, Covering indexes

### **71. How does indexing affect INSERT and UPDATE?**
**Interviewer Answer:**
**Negative Impact on Write Operations**:

1. **INSERT Operations**:
   - **Additional I/O**: Each index must be updated with new entry
   - **Sort Maintenance**: Entries inserted in correct sorted position
   - **Page Splits** (clustered): May require data page reorganization
   - **Lock Contention**: Multiple indexes may need locking

2. **UPDATE Operations**:
   - **Index Key Update**: If indexed column updated, index entry must move
   - **Double Write**: Old index entry deleted, new one inserted
   - **Non-key Update**: If non-indexed column updated, only data page affected

3. **DELETE Operations**:
   - **Index Entry Removal**: Index entries for deleted row must be removed
   - **Fragmentation**: Leaves empty space in index pages

**Performance Degradation Factors**:

1. **Number of Indexes**:
   ```
   Write Cost = Data write + (N × Index write)
   Where N = number of indexes
   More indexes → higher write cost
   ```

2. **Index Type**:
   - **Clustered**: Higher cost (physical data reorganization)
   - **Non-clustered**: Lower cost but still significant
   - **Unique**: Additional uniqueness check overhead

3. **Index Fragmentation**:
   - Frequent updates cause index fragmentation
   - Requires periodic maintenance (REBUILD/REORGANIZE)

**Specific Scenarios**:

1. **Bulk INSERTs**:
   ```sql
   -- Without indexes: Very fast
   -- With indexes: Significantly slower
   -- Best practice: Drop indexes → Bulk insert → Recreate indexes
   ```

2. **High-Volume OLTP**:
   - Each transaction updates multiple indexes
   - Lock contention on index pages
   - Log file growth from index changes

3. **Index Maintenance Overhead**:
   - Statistics updates
   - Page split management
   - Logging index changes

**Mitigation Strategies**:

1. **Batch Operations**: Group writes to amortize index maintenance
2. **Index Design**: Only essential indexes on write-heavy tables
3. **Fill Factor**: Configure to reduce page splits
4. **Maintenance Windows**: Schedule index rebuilds during low activity

**Trade-off Decision Matrix**:
```
High Reads + Low Writes: More indexes beneficial
Low Reads + High Writes: Fewer indexes better
Balanced: Selective indexing based on query patterns
```

**Theoretical Keywords**: Write amplification, Page splits, Lock contention, Maintenance overhead, I/O operations

### **72. What is query optimization?**
**Interviewer Answer:**
- **Query Optimization**: Process where database determines most efficient way to execute SQL query
- **Performed by**: Query Optimizer component of DBMS
- **Goal**: Minimize resource usage (CPU, I/O, memory) while returning correct results
- **Complexity**: NP-hard problem (exponential possibilities)

**Optimization Process Stages**:

1. **Parsing & Validation**:
   - Syntax checking
   - Semantic validation
   - Object existence verification

2. **Query Rewriting**:
   - View expansion
   - Subquery flattening
   - Predicate pushdown
   - Constant folding

3. **Cost-Based Optimization**:
   - Generate multiple execution plans
   - Estimate cost for each plan
   - Choose lowest cost plan

4. **Execution Plan Generation**:
   - Create optimized execution plan
   - Plan caching for reuse

**Key Optimization Techniques**:

1. **Access Path Selection**:
   - Choose between: Table scan vs Index scan vs Index seek
   - Based on selectivity, data distribution, index statistics

2. **Join Order Optimization**:
   - Determine optimal table join order
   - N tables → N! possible join orders
   - Uses dynamic programming/heuristics

3. **Join Algorithm Selection**:
   - **Nested Loop**: Small outer table, indexed inner
   - **Hash Join**: Large tables, equality joins
   - **Merge Join**: Sorted data, range joins

4. **Predicate Optimization**:
   - Push filters early in execution
   - Use indexes for sargable predicates
   - Transform non-sargable to sargable when possible

**Optimizer Statistics**:
- **Crucial for**: Accurate cost estimation
- **Includes**: Table row count, column value distribution, index statistics
- **Maintenance**: AUTO_UPDATE_STATISTICS or manual updates

**Limitations & Challenges**:
1. **Statistics Accuracy**: Outdated stats lead to poor plans
2. **Parameter Sniffing**: First execution parameters affect cached plan
3. **Complex Queries**: Optimization time itself can be costly
4. **Hardware Factors**: Optimizer may not know actual hardware capabilities

**Theoretical Keywords**: Cost-based optimization, Execution plan, Join algorithms, Statistics, Heuristics, NP-hard problem

### **73. What is an execution plan?**
**Interviewer Answer:**
- **Execution Plan**: Step-by-step instructions database follows to execute query
- **Visual representation**: Query execution strategy chosen by optimizer
- **Contains**: Operations, access paths, join methods, cost estimates
- **Purpose**: Understand and optimize query performance

**Plan Components**:

1. **Operators** (Physical Operations):
   - **Table Scan**: Read all rows from table
   - **Index Scan**: Read all rows from index
   - **Index Seek**: Use index to find specific rows
   - **Hash Match**: Hash join/aggregation
   - **Sort**: Sort data
   - **Nested Loops**: Nested loop join

2. **Tree Structure**:
   ```
   Root (final result)
   ├── Join Operator
   │   ├── Index Seek (Table A)
   │   └── Index Scan (Table B)
   └── Sort Operator
   ```

3. **Cost Metrics**:
   - **Estimated Rows**: Rows expected from each operation
   - **Estimated Cost**: Relative resource usage (I/O, CPU)
   - **Actual vs Estimated**: Runtime vs optimization time

**Reading Execution Plans**:

1. **Flow Direction**:
   - Right-to-left (SQL Server)
   - Bottom-to-top (Oracle)
   - Inner-to-outer (nested operations first)

2. **Key Indicators**:
   - **Thick arrows**: Many rows processed
   - **High cost percentages**: Performance bottlenecks
   - **Warnings**: Missing indexes, implicit conversions

**Types of Execution Plans**:

1. **Estimated Execution Plan**:
   - Generated during optimization
   - Based on statistics
   - No query execution

2. **Actual Execution Plan**:
   - Captured during query execution
   - Includes runtime metrics
   - More accurate but requires execution

**Common Plan Patterns**:

1. **Index Seek + Key Lookup**:
   - Good for selective queries
   - Index finds rows, then fetches data

2. **Clustered Index Scan**:
   - Table scan equivalent
   - May be optimal for large result sets

3. **Hash Join**:
   - Build hash table from smaller table
   - Probe with larger table

**Using Plans for Optimization**:

1. **Identify Problems**:
   - Table scans on large tables
   - Key lookups on many rows
   - Sort operations without indexes
   - Implicit type conversions

2. **Solution Strategies**:
   - Add missing indexes
   - Update statistics
   - Rewrite query
   - Use query hints (last resort)

**Plan Caching & Reuse**:
- Plans cached for performance
- Parameterization affects reuse
- Plan invalidation on schema/statistics changes

**Theoretical Keywords**: Query operators, Cost estimation, Plan tree, Physical operations, Performance analysis

---

## **Indexing & Performance Summary**

### **Index Design Principles**:

1. **Selectivity First**: Index high-cardinality columns
2. **Query-Driven**: Design indexes based on actual query patterns
3. **Covering Strategy**: Include frequently selected columns in indexes
4. **Minimal Width**: Keep indexes narrow when possible
5. **Maintenance Awareness**: Balance read vs write performance

### **Performance Tuning Hierarchy**:
```
Most Impact:
1. Query Design & Structure
2. Index Strategy
3. Hardware & Configuration
4. Database Schema
Least Impact:
5. DBMS Settings Tweaks
```

### **Critical Metrics for Indexing**:
- **Selectivity**: (Distinct values / Total rows)
- **Fill Factor**: Page space utilization
- **Fragmentation**: Logical vs physical order mismatch
- **Statistics Freshness**: Accuracy of data distribution info

### **When to Re-evaluate Indexes**:
1. **Query Performance Degradation**
2. **Schema Changes**
3. **Data Volume Changes**
4. **Workload Pattern Shifts**
5. **Regular Maintenance Windows**

**You now have comprehensive theoretical knowledge of Database Indexes and Performance!** This understanding is crucial for database design, query optimization, and system performance tuning in interviews. 📊🚀