# **TRICKY SQL QUESTIONS ANSWERS**

## **Tricky SQL Questions (92-114)**

### **92. Difference between WHERE and HAVING with example**
**Interviewer Answer:**
| **WHERE** | **HAVING** |
|-----------|------------|
| **Filters rows** before grouping | **Filters groups** after grouping |
| **Cannot use** aggregate functions | **Can use** aggregate functions |
| **Applied before** GROUP BY | **Applied after** GROUP BY |
| **Operates on** individual rows | **Operates on** aggregated results |
| **Syntax**: `WHERE condition` | **Syntax**: `HAVING condition` |

**Examples**:
```sql
-- WHERE: Filter individual rows (salary > 50000)
SELECT department, AVG(salary)
FROM Employees
WHERE salary > 50000           -- Filters rows first
GROUP BY department;

-- HAVING: Filter groups (avg salary > 60000)
SELECT department, AVG(salary)
FROM Employees
GROUP BY department
HAVING AVG(salary) > 60000;    -- Filters groups after

-- Combined: WHERE then HAVING
SELECT department, AVG(salary) as avg_sal
FROM Employees
WHERE hire_date > '2020-01-01'   -- Row filter
GROUP BY department
HAVING AVG(salary) > 50000      -- Group filter
ORDER BY avg_sal DESC;
```

**Key Insight**: WHERE filters data that goes INTO groups, HAVING filters what comes OUT of groups

**Theoretical Keywords**: Row vs group filtering, Pre-grouping vs post-grouping, Aggregate function usage

### **93. Find second highest salary from employee table**
**Interviewer Answer:**
**Multiple Approaches**:

1. **Using LIMIT/OFFSET** (MySQL, PostgreSQL):
   ```sql
   SELECT DISTINCT salary 
   FROM Employees 
   ORDER BY salary DESC 
   LIMIT 1 OFFSET 1;
   ```

2. **Using Subquery with MAX**:
   ```sql
   SELECT MAX(salary) 
   FROM Employees 
   WHERE salary < (SELECT MAX(salary) FROM Employees);
   ```

3. **Using ROW_NUMBER()**:
   ```sql
   SELECT salary FROM (
       SELECT salary, ROW_NUMBER() OVER (ORDER BY salary DESC) as rn
       FROM Employees
   ) ranked 
   WHERE rn = 2;
   ```

4. **Using DENSE_RANK()** (handles duplicate salaries):
   ```sql
   SELECT DISTINCT salary FROM (
       SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as dr
       FROM Employees
   ) ranked 
   WHERE dr = 2;
   ```

**Best Practice**: Use DENSE_RANK() if you want second highest distinct salary value

**Theoretical Keywords**: Ranking functions, Subquery filtering, Limit/offset pagination

### **94. Find nth highest salary**
**Interviewer Answer:**
**Generalized Solutions**:

1. **Using LIMIT with OFFSET** (n-1):
   ```sql
   -- 5th highest salary
   SELECT DISTINCT salary 
   FROM Employees 
   ORDER BY salary DESC 
   LIMIT 1 OFFSET 4;
   ```

2. **Using DENSE_RANK()** (handles duplicates):
   ```sql
   -- Parameterized (n = 5)
   SELECT DISTINCT salary FROM (
       SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as dr
       FROM Employees
   ) ranked 
   WHERE dr = 5;
   ```

3. **Using correlated subquery**:
   ```sql
   -- 5th highest (n=5)
   SELECT DISTINCT salary 
   FROM Employees e1
   WHERE 5 = (
       SELECT COUNT(DISTINCT salary) 
       FROM Employees e2 
       WHERE e2.salary >= e1.salary
   );
   ```

4. **Using Common Table Expression (CTE)**:
   ```sql
   WITH RankedSalaries AS (
       SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as rank
       FROM Employees
   )
   SELECT salary FROM RankedSalaries WHERE rank = 5;
   ```

**Performance Note**: Window functions (DENSE_RANK) generally most efficient

**Theoretical Keywords**: Ranking functions, Correlated subqueries, Position-based retrieval

### **95. Delete duplicate records from a table**
**Interviewer Answer:**
**Multiple Approaches**:

1. **Using ROW_NUMBER() with CTE** (Most efficient):
   ```sql
   WITH CTE AS (
       SELECT *, ROW_NUMBER() OVER (
           PARTITION BY column1, column2, column3 
           ORDER BY (SELECT NULL)
       ) as rn
       FROM Employees
   )
   DELETE FROM CTE WHERE rn > 1;
   ```

2. **Using MIN/MAX with GROUP BY**:
   ```sql
   DELETE FROM Employees
   WHERE id NOT IN (
       SELECT MIN(id)  -- or MAX(id)
       FROM Employees
       GROUP BY column1, column2, column3
   );
   ```

3. **Using self-join**:
   ```sql
   DELETE e1 FROM Employees e1
   JOIN Employees e2 
   ON e1.column1 = e2.column1 
      AND e1.column2 = e2.column2
      AND e1.column3 = e2.column3
   WHERE e1.id > e2.id;  -- Keep the smaller id
   ```

4. **Using temporary table**:
   ```sql
   -- Create temp table with unique records
   SELECT DISTINCT * INTO #TempTable FROM Employees;
   -- Truncate original
   TRUNCATE TABLE Employees;
   -- Insert back unique records
   INSERT INTO Employees SELECT * FROM #TempTable;
   DROP TABLE #TempTable;
   ```

**Important**: Always backup before mass deletions

**Theoretical Keywords**: Duplicate elimination, Window functions, Self-join deletion, Group by filtering

### **96. Find duplicate records in a table**
**Interviewer Answer:**
**Multiple Approaches**:

1. **Using GROUP BY with HAVING**:
   ```sql
   SELECT column1, column2, column3, COUNT(*) as duplicate_count
   FROM Employees
   GROUP BY column1, column2, column3
   HAVING COUNT(*) > 1;
   ```

2. **Using ROW_NUMBER()**:
   ```sql
   SELECT * FROM (
       SELECT *, ROW_NUMBER() OVER (
           PARTITION BY column1, column2, column3 
           ORDER BY id
       ) as rn
       FROM Employees
   ) ranked 
   WHERE rn > 1;
   ```

3. **Using EXISTS**:
   ```sql
   SELECT * FROM Employees e1
   WHERE EXISTS (
       SELECT 1 FROM Employees e2
       WHERE e2.column1 = e1.column1
         AND e2.column2 = e1.column2
         AND e2.column3 = e1.column3
         AND e2.id < e1.id  -- or e2.id != e1.id
   );
   ```

4. **Using INNER JOIN**:
   ```sql
   SELECT DISTINCT e1.*
   FROM Employees e1
   INNER JOIN Employees e2 
   ON e1.column1 = e2.column1 
      AND e1.column2 = e2.column2
      AND e1.column3 = e2.column3
   WHERE e1.id != e2.id;
   ```

**To see all duplicates (including original)**:
```sql
SELECT column1, column2, column3, 
       STRING_AGG(id, ',') as duplicate_ids,
       COUNT(*) as count
FROM Employees
GROUP BY column1, column2, column3
HAVING COUNT(*) > 1;
```

**Theoretical Keywords**: Duplicate detection, Group aggregation, Self-join identification

### **97. Difference between `COUNT(1)` and `COUNT(*)`**
**Interviewer Answer:**
| **COUNT(1)** | **COUNT(*)** |
|--------------|--------------|
| **Counts**: Number 1 for each row | **Counts**: All rows regardless of content |
| **Performance**: Same as COUNT(*) in most databases | **Performance**: Same as COUNT(1) in most databases |
| **NULL handling**: Counts all rows (even all NULL rows) | **NULL handling**: Counts all rows |
| **Use case**: Historical convention | **Use case**: Standard SQL, clearer intent |
| **Modern optimization**: Both optimized to same execution plan | **Modern optimization**: Both optimized identically |

**Database-Specific Behavior**:
- **Oracle**: Historically COUNT(1) slightly faster, now same
- **SQL Server**: No difference, same execution plan
- **MySQL**: No difference, both optimized
- **PostgreSQL**: No difference

**What they actually count**:
```sql
-- Both return total row count
SELECT COUNT(1) FROM table;   -- Returns: 100
SELECT COUNT(*) FROM table;   -- Returns: 100

-- Even if all columns are NULL
CREATE TABLE test (col1 INT NULL);
INSERT INTO test VALUES (NULL), (NULL), (NULL);
SELECT COUNT(1) FROM test;  -- 3
SELECT COUNT(*) FROM test;  -- 3
```

**COUNT(column) vs COUNT(1/asterisk)**:
```sql
-- Counts non-NULL values in column
SELECT COUNT(column) FROM table;  -- Excludes NULLs

-- Counts all rows regardless
SELECT COUNT(1) FROM table;      -- Includes NULL rows
SELECT COUNT(*) FROM table;      -- Includes NULL rows
```

**Best Practice**: Use `COUNT(*)` for clarity - it's standard SQL and clearly communicates intent

**Theoretical Keywords**: Row counting semantics, NULL handling, Query optimization

### **98. Difference between `CHAR` and `VARCHAR`**
**Interviewer Answer:**
| **CHAR(n)** | **VARCHAR(n)** |
|-------------|----------------|
| **Storage**: Fixed-length, pads with spaces | **Storage**: Variable-length, no padding |
| **Size**: Always n bytes (or characters) | **Size**: Actual length + overhead (1-2 bytes) |
| **Performance**: Faster for fixed-size data | **Performance**: Slower for updates that change length |
| **Space usage**: Wastes space for shorter strings | **Space usage**: Efficient for variable-length data |
| **Trailing spaces**: Removed on retrieval (in some DBMS) | **Trailing spaces**: Preserved |
| **Best for**: Fixed-length codes (SSN, ZIP) | **Best for**: Variable-length text (names, addresses) |

**Storage Example**:
```sql
CHAR(10) storing 'ABC':  'ABC       '  -- 7 spaces padded
VARCHAR(10) storing 'ABC': 'ABC'        -- no padding
-- CHAR uses 10 bytes, VARCHAR uses 3+overhead bytes
```

**Performance Implications**:
- **CHAR**: Predictable storage, faster for exact-length comparisons
- **VARCHAR**: Storage efficient, but updates causing length change may cause row movement

**Use Cases**:
- **Use CHAR for**: Country codes (US, IN), Status codes (A, I, D), Fixed-width data
- **Use VARCHAR for**: Names, addresses, descriptions, emails

**Modern Best Practice**: Use VARCHAR for most text, CHAR only when fixed length is guaranteed

**NCHAR/NVARCHAR**: Unicode versions with similar characteristics

**Theoretical Keywords**: Fixed vs variable length, Storage efficiency, Padding behavior

### **99. What happens if WHERE condition is not used in DELETE?**
**Interviewer Answer:**
- **Deletes ALL rows** from the table
- **Equivalent to**: `TRUNCATE TABLE` but with important differences
- **Transaction logged**: Each row deletion is logged (unlike TRUNCATE)
- **Can be rolled back**: Within a transaction (unlike TRUNCATE in some DBMS)
- **Triggers fire**: DELETE triggers execute (TRUNCATE may not)

**Example**:
```sql
-- DANGEROUS: Deletes everything
DELETE FROM Employees;
-- Table empty, but structure remains

-- Safer: Always use WHERE or explicit condition
DELETE FROM Employees WHERE 1=0;  -- Deletes nothing
-- Or use transaction
BEGIN TRANSACTION;
DELETE FROM Employees;
-- Can ROLLBACK if mistake
```

**Differences from TRUNCATE**:
```sql
-- DELETE: Logged, rollbackable, fires triggers, slower
DELETE FROM table;

-- TRUNCATE: Minimal logging, not rollbackable, faster
TRUNCATE TABLE table;
```

**Safety Measures**:
1. **Always use WHERE** unless intentionally deleting all
2. **Use transactions** for mass deletes
3. **Take backup** before mass DELETE operations
4. **Consider TRUNCATE** if deleting all rows intentionally

**Auto-increment behavior**:
- **DELETE**: Does not reset auto-increment counter
- **TRUNCATE**: Resets auto-increment counter (in most DBMS)

**Theoretical Keywords**: Mass deletion, Transaction logging, Trigger execution, Safety precautions

### **100. How to fetch records common in two tables**
**Interviewer Answer:**
**Multiple Approaches**:

1. **Using INNER JOIN** (Most common):
   ```sql
   SELECT a.*
   FROM TableA a
   INNER JOIN TableB b ON a.key_column = b.key_column;
   ```

2. **Using INTERSECT** (ANSI SQL):
   ```sql
   SELECT column1, column2 FROM TableA
   INTERSECT
   SELECT column1, column2 FROM TableB;
   ```

3. **Using EXISTS**:
   ```sql
   SELECT *
   FROM TableA a
   WHERE EXISTS (
       SELECT 1 FROM TableB b 
       WHERE b.key_column = a.key_column
   );
   ```

4. **Using IN**:
   ```sql
   SELECT *
   FROM TableA
   WHERE key_column IN (SELECT key_column FROM TableB);
   ```

**Example with specific scenario**:
```sql
-- Find employees who are also managers
SELECT e.*
FROM Employees e
INNER JOIN Managers m ON e.employee_id = m.manager_id;

-- Using INTERSECT
SELECT employee_id, name FROM Employees
INTERSECT
SELECT manager_id, name FROM Managers;
```

**Performance Considerations**:
- **JOIN**: Usually fastest, especially with indexes
- **EXISTS**: Good for correlated subqueries
- **IN**: Simple but may be slower with large lists
- **INTERSECT**: Removes duplicates automatically

**For comparing all columns**:
```sql
-- Using INTERSECT (removes duplicates)
SELECT * FROM TableA
INTERSECT
SELECT * FROM TableB;

-- Using INNER JOIN on all columns (manual)
SELECT a.*
FROM TableA a
INNER JOIN TableB b ON a.col1 = b.col1 
                    AND a.col2 = b.col2 
                    AND a.col3 = b.col3;
```

**Theoretical Keywords**: Set intersection, Common records, Join vs intersect, Duplicate handling

### **101. Difference between EXISTS and IN performance-wise**
**Interviewer Answer:**
| **EXISTS** | **IN** |
|------------|--------|
| **Execution**: Stops at first match (semi-join) | **Execution**: May process entire subquery result |
| **Performance**: Usually faster for correlated subqueries | **Performance**: May be faster for small static lists |
| **NULL handling**: Returns TRUE if any row exists (even NULL) | **NULL handling**: `value IN (NULL)` returns UNKNOWN |
| **Subquery**: Can be correlated (references outer query) | **Subquery**: Usually independent |
| **Use case**: Checking existence of related records | **Use case**: Matching against list of values |

**Performance Analysis**:

1. **Correlated Subqueries**:
   ```sql
   -- EXISTS (usually faster): Stops at first match
   SELECT * FROM Customers c
   WHERE EXISTS (
       SELECT 1 FROM Orders o 
       WHERE o.customer_id = c.id  -- Correlated
   );
   
   -- IN (may be slower): Processes all orders
   SELECT * FROM Customers
   WHERE id IN (SELECT customer_id FROM Orders);
   ```

2. **Large Result Sets**:
   - **EXISTS**: Better for large subquery results (early termination)
   - **IN**: May create large temporary list in memory

3. **Small Static Lists**:
   ```sql
   -- IN may be optimized better for static lists
   WHERE status IN ('Active', 'Pending', 'Approved');
   
   -- EXISTS equivalent (not recommended for static lists)
   WHERE EXISTS (SELECT 1 FROM (VALUES ('Active'), ('Pending'), ('Approved')) t(v) 
                 WHERE t.v = status);
   ```

**Modern Query Optimizers**: Often convert IN to EXISTS or JOIN automatically

**NULL Handling Critical Difference**:
```sql
-- NOT IN with NULL issue
SELECT * FROM Table1 
WHERE id NOT IN (SELECT id FROM Table2 WHERE id IS NULL);
-- Returns: Nothing! Because NULL comparison returns UNKNOWN

-- NOT EXISTS handles NULLs correctly
SELECT * FROM Table1 t1
WHERE NOT EXISTS (SELECT 1 FROM Table2 t2 WHERE t2.id = t1.id);
-- Returns: Correct results even with NULLs
```

**Best Practice**:
- Use **EXISTS** for correlated subqueries and existence checks
- Use **IN** for static lists and non-correlated subqueries
- Use **NOT EXISTS** instead of NOT IN when NULLs possible

**Theoretical Keywords**: Semi-join optimization, Early termination, NULL semantics, Correlated execution

### **102. How indexes work internally (B-Tree)**
**Interviewer Answer:**
**B-Tree Index Structure**:

```
B-Tree (Balanced Tree) Structure:
          [Root Node]
         /     |     \
   [Branch Nodes] ... [Branch Nodes]
     /  |  \            /  |  \
[Leaf Nodes]        [Leaf Nodes]
    ↓                     ↓
[Data Pointers]     [Data Pointers]
```

**Key Characteristics**:
1. **Balanced**: All leaf nodes at same depth
2. **Sorted**: Keys sorted within each node
3. **Self-balancing**: Maintains balance during inserts/deletes
4. **Fan-out**: Each node contains multiple keys (m-way tree)

**Search Process** (for value 42):
```
1. Start at root node
2. Compare 42 with node values
3. Follow appropriate branch pointer
4. Repeat at child node
5. Reach leaf node containing value 42
6. Follow pointer to actual data row
Typical: 3-4 disk reads for million-row table
```

**Node Structure**:
```
Leaf Node (for non-clustered index):
┌─────────────────┐
│ Key1 → RowPtr1  │
│ Key2 → RowPtr2  │
│ Key3 → RowPtr3  │
│ ...             │
│ Next Leaf Ptr → │
└─────────────────┘
```

**B+ Tree Variations** (common in databases):
- **B+ Tree**: Only leaf nodes contain data pointers
- **All keys in leaves**: Sequential scanning efficient
- **Linked leaves**: Leaf nodes linked for range queries

**Index Operations**:

1. **Index Seek** (Equality search):
   ```
   WHERE id = 100
   → Traverse tree directly to leaf with key 100
   ```

2. **Index Scan** (Range search):
   ```
   WHERE id BETWEEN 100 AND 200
   → Find first key (100), scan leaf nodes sequentially
   ```

3. **Index Creation**:
   - Sort data by key
   - Build balanced tree bottom-up

**Performance Characteristics**:
- **Search**: O(log n) - logarithmic time complexity
- **Insert/Delete**: O(log n) - may cause node splits/merges
- **Range queries**: Efficient due to sorted leaves

**Real-world Optimizations**:
1. **Fill Factor**: Space left in nodes for future inserts
2. **Page Splits**: When node full, split into two nodes
3. **Rebalancing**: Maintain tree balance after modifications

**Theoretical Keywords**: Balanced tree, Logarithmic search, Node structure, Sequential scanning, Tree operations

### **103. What happens when primary key is not defined?**
**Interviewer Answer:**
**Consequences of No Primary Key**:

1. **Heap Organization**: Table stored as heap (unordered collection of rows)
2. **No Clustered Index**: No physical ordering of data (in SQL Server)
3. **Row Identification**: System uses internal row identifiers (RID)
4. **Performance Impact**: Certain operations less efficient

**Technical Details**:

1. **Row Identification**:
   ```sql
   -- Without PK: Uses internal row identifier
   -- SQL Server: FileID:PageID:SlotNumber
   -- Oracle: ROWID pseudocolumn
   -- PostgreSQL: ctid system column
   ```

2. **Table Structure**:
   ```
   Heap Table (no clustered index):
   Data pages unordered, linked list structure
   New rows inserted wherever space available
   ```

3. **Performance Implications**:
   - **Scans**: Full table scans only option for searches
   - **Joins**: No efficient join path available
   - **Sorting**: Requires runtime sorting for ORDER BY
   - **Updates**: May cause forwarded records (fragmentation)

4. **Data Integrity Issues**:
   - No enforcement of uniqueness
   - Duplicate rows possible
   - Foreign key relationships problematic

**Workarounds Used by Applications**:
1. **Application-level PK**: Business logic enforces uniqueness
2. **Surrogate keys**: GUIDs or sequences managed in app
3. **Natural keys**: Using existing unique columns

**Best Practice**: Always define primary key unless:
1. **Staging tables**: Temporary data loading
2. **Log tables**: Append-only, never queried by key
3. **Materialized views**: Pre-computed results

**Creating PK Later**:
```sql
-- Add primary key to existing table
ALTER TABLE Employees ADD PRIMARY KEY (employee_id);
-- May fail if duplicate values exist
```

**Theoretical Keywords**: Heap tables, Row identifiers, Clustered index absence, Data organization

### **104. Can foreign key reference non-primary key?**
**Interviewer Answer:**
- **YES**, foreign key can reference non-primary key columns
- **Requirement**: Referenced column must have UNIQUE constraint or be part of UNIQUE index
- **Called**: "Referencing a candidate key" not just primary key
- **Purpose**: Establish relationships based on business keys

**Syntax**:
```sql
-- Foreign key referencing unique column (not PK)
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_email VARCHAR(100),
    FOREIGN KEY (customer_email) 
    REFERENCES Users(email)  -- email has UNIQUE constraint
);

-- Users table
CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100)
);
```

**Requirements**:
1. **Referenced column**: Must be UNIQUE (or primary key)
2. **Data types**: Must match exactly
3. **NULL handling**: Foreign key can be NULL unless NOT NULL specified

**Use Cases**:

1. **Natural Business Keys**:
   ```sql
   -- Reference by email instead of user_id
   FOREIGN KEY (customer_email) REFERENCES Users(email)
   ```

2. **Alternate Unique Identifiers**:
   ```sql
   -- Product table with SKU as unique business key
   FOREIGN KEY (product_sku) REFERENCES Products(sku)
   ```

3. **Composite Foreign Keys**:
   ```sql
   -- Reference composite unique key
   FOREIGN KEY (dept_code, location_code) 
   REFERENCES Departments(code, location)
   ```

**Considerations**:

1. **Performance**: Unique indexes required on referenced columns
2. **Maintenance**: Business keys may change (emails change)
3. **Size**: Business keys often larger than surrogate keys

**Best Practice**:
- **Prefer referencing PK**: Surrogate keys don't change
- **Use business keys**: When natural relationship exists
- **Document clearly**: When referencing non-PK

**Example with multiple relationships**:
```sql
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY,
    ssn CHAR(9) UNIQUE,
    email VARCHAR(100) UNIQUE
);

CREATE TABLE Dependents (
    dep_id INT PRIMARY KEY,
    employee_ssn CHAR(9),
    FOREIGN KEY (employee_ssn) REFERENCES Employees(ssn)
);

CREATE TABLE Equipment (
    eq_id INT PRIMARY KEY,
    assigned_to_email VARCHAR(100),
    FOREIGN KEY (assigned_to_email) REFERENCES Employees(email)
);
```

**Theoretical Keywords**: Candidate key references, Unique constraint requirement, Business key relationships

### **105. What is deadlock?**
**Interviewer Answer:**
- **Deadlock**: Situation where two or more transactions are blocked forever, each waiting for a resource held by another
- **Circular Wait**: Transaction A waits for resource held by B, B waits for resource held by A
- **Four Necessary Conditions** (Coffman conditions):
  1. **Mutual Exclusion**: Resources cannot be shared
  2. **Hold and Wait**: Process holds resources while waiting for others
  3. **No Preemption**: Resources cannot be forcibly taken
  4. **Circular Wait**: Circular chain of processes waiting for resources

**Deadlock Example**:
```
Transaction T1:                Transaction T2:
BEGIN;                         BEGIN;
UPDATE TableA SET ...          UPDATE TableB SET ...
(acquires lock on TableA)      (acquires lock on TableB)

UPDATE TableB SET ...          UPDATE TableA SET ...
(waits for T2's lock on B)     (waits for T1's lock on A)
╰──────────────┬───────────────╯
              DEADLOCK
```

**Database Deadlock Detection**:
- **Deadlock Detector**: Periodically checks for cycles in wait-for graph
- **Wait-for Graph**: Nodes = transactions, Edges = "waits for"
- **Cycle Detection**: Algorithm finds circular dependencies

**Deadlock vs Blocking**:
- **Blocking**: Normal, temporary wait for resource
- **Deadlock**: Permanent, requires intervention

**Common Deadlock Scenarios**:
1. **Different Access Order**:
   ```sql
   -- T1: A then B
   -- T2: B then A
   ```

2. **Lock Escalation**:
   - Row locks escalated to page/table locks

3. **Nested Transactions**:
   - Complex locking hierarchies

**Theoretical Keywords**: Circular wait, Coffman conditions, Wait-for graph, Resource contention

### **106. How to handle deadlock?**
**Interviewer Answer:**
**Database-Level Handling**:

1. **Automatic Deadlock Detection & Resolution**:
   - **Victim Selection**: DBMS chooses transaction to abort (usually less work)
   - **Rollback**: Victim transaction rolled back automatically
   - **Error Return**: Victim receives deadlock error (SQLSTATE 40001)

2. **Deadlock Prevention Strategies**:
   ```sql
   -- Access resources in same order
   -- T1 and T2 both: Lock TableA first, then TableB
   
   -- Use lock timeouts
   SET LOCK_TIMEOUT 5000;  -- 5 second timeout
   
   -- Use lower isolation levels when possible
   SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   ```

**Application-Level Handling**:

1. **Retry Logic**:
   ```python
   max_retries = 3
   retry_count = 0
   
   while retry_count < max_retries:
       try:
           execute_transaction()
           break
       except DeadlockError:
           retry_count += 1
           sleep(random.uniform(0, 2**retry_count))  # Exponential backoff
           continue
   ```

2. **Transaction Design**:
   ```sql
   -- Keep transactions short
   -- Acquire locks in consistent order
   -- Release locks as soon as possible
   -- Avoid user interaction within transactions
   ```

3. **Lock Hints** (Database-specific):
   ```sql
   -- SQL Server: Use UPDLOCK to prevent conversion
   SELECT * FROM Table WITH (UPDLOCK) WHERE id = 1;
   
   -- Use ROWLOCK to avoid lock escalation
   UPDATE Table WITH (ROWLOCK) SET value = 1 WHERE id = 1;
   ```

**Monitoring and Analysis**:

1. **Deadlock Graphs** (SQL Server):
   ```sql
   -- Enable trace flags
   DBCC TRACEON (1222, -1);  -- Log deadlocks to error log
   
   -- Query deadlock information
   SELECT * FROM sys.dm_tran_locks;
   SELECT * FROM sys.dm_os_waiting_tasks;
   ```

2. **Preventive Measures**:
   - **Indexing**: Proper indexes reduce lock contention
   - **Batch Processing**: Break large operations into batches
   - **Optimistic Concurrency**: Use versioning instead of locking

**Best Practices**:
1. **Implement retry logic** in application
2. **Monitor deadlock frequency**
3. **Analyze deadlock graphs** to identify patterns
4. **Design transactions carefully**

**Theoretical Keywords**: Victim selection, Retry patterns, Lock ordering, Timeout mechanisms

### **107. Why `SELECT *` is not recommended?**
**Interviewer Answer:**
**Performance Issues**:

1. **Unnecessary Data Transfer**:
   ```sql
   -- Transfers all columns (even unused ones)
   SELECT * FROM Employees;  -- 20 columns transferred
   vs
   SELECT id, name FROM Employees;  -- 2 columns transferred
   ```

2. **Index Inefficiency**:
   - May prevent index-only scans
   - Forces table access even when index covers query

3. **Network Overhead**:
   - More data over the wire
   - Slower response times

**Maintenance Issues**:

1. **Schema Changes Break Code**:
   ```sql
   -- Column reordering changes result set structure
   -- Column removal causes errors
   -- New columns may expose sensitive data
   ```

2. **Hidden Dependencies**:
   - Application depends on column order
   - Changes require full regression testing

3. **Security Risks**:
   ```sql
   -- May expose sensitive columns added later
   -- SELECT * from Users exposes password_hash if added later
   ```

**Best Practices**:

1. **Explicit Column Lists**:
   ```sql
   -- Good: Explicit columns
   SELECT id, name, email FROM Users;
   
   -- Bad: SELECT *
   SELECT * FROM Users;
   ```

2. **Use Aliases for Clarity**:
   ```sql
   SELECT 
       u.id as user_id,
       u.name as user_name,
       d.name as department_name
   FROM Users u
   JOIN Departments d ON u.dept_id = d.id;
   ```

**When SELECT * Might Be Acceptable**:

1. **Ad-hoc Queries**: One-time exploration
2. **EXISTS Subqueries**: `SELECT 1` or `SELECT *` (no data transferred)
3. **Quick Debugging**: Temporary investigation

**Modern Alternatives**:

1. **ORMs**: Generate optimized column lists
2. **View Definitions**: Encapsulate column selection
3. **Stored Procedures**: Control data exposure

**Theoretical Keywords**: Data transfer efficiency, Schema coupling, Security exposure, Maintenance complexity

### **108. Difference between TRUNCATE and DROP in terms of rollback**
**Interviewer Answer:**
| **TRUNCATE** | **DROP** |
|--------------|----------|
| **Operation**: Removes all rows, keeps structure | **Operation**: Removes table completely |
| **Rollback**: **Cannot** be rolled back in most DBMS | **Rollback**: **Cannot** be rolled back |
| **Auto-commit**: DDL operation, auto-commits | **Auto-commit**: DDL operation, auto-commits |
| **Transaction**: Not transactional in most DBMS | **Transaction**: Not transactional |
| **Recovery**: Can recover from backup only | **Recovery**: Must recreate table from schema |

**Rollback Behavior Details**:

1. **SQL Server**:
   ```sql
   BEGIN TRANSACTION;
   TRUNCATE TABLE Employees;  -- Can be rolled back (logged)
   ROLLBACK;  -- Works in SQL Server
   
   BEGIN TRANSACTION;
   DROP TABLE Employees;  -- Can be rolled back
   ROLLBACK;  -- Works in SQL Server
   ```

2. **Oracle**:
   ```sql
   -- TRUNCATE cannot be rolled back (minimal logging)
   -- DROP cannot be rolled back
   -- Both auto-commit
   ```

3. **PostgreSQL**:
   ```sql
   BEGIN;
   TRUNCATE TABLE employees;  -- Can be rolled back
   ROLLBACK;  -- Works
   
   BEGIN;
   DROP TABLE employees;  -- Can be rolled back  
   ROLLBACK;  -- Works
   ```

4. **MySQL**:
   ```sql
   -- TRUNCATE cannot be rolled back (similar to DROP + CREATE)
   -- DROP cannot be rolled back
   -- Both auto-commit in most storage engines
   ```

**Key Distinction**:
- **TRUNCATE**: Resets table to empty state
- **DROP**: Eliminates table from database

**Performance Implications**:
- **TRUNCATE**: Faster than DELETE (minimal logging)
- **DROP**: Fastest (removes metadata)

**Recovery Options**:
1. **TRUNCATE recovery**: 
   - Restore from backup
   - Flashback queries (Oracle)
   - Transaction log restore

2. **DROP recovery**:
   - Restore from backup
   - Recreate from schema scripts
   - Flashback drop (Oracle)

**Safety Measures**:
```sql
-- Always backup before TRUNCATE/DROP
-- Use transactions when supported
-- Consider DELETE with WHERE 1=1 if rollback needed
```

**Theoretical Keywords**: DDL operations, Transaction logging, Auto-commit behavior, Recovery strategies

### **109. What is stored procedure?**
**Interviewer Answer:**
- **Stored Procedure**: Precompiled collection of SQL statements stored in database
- **Characteristics**:
  1. **Named routine**: Called by name with parameters
  2. **Precompiled**: Compiled once, executed many times
  3. **Database-resident**: Stored in database catalog
  4. **Transactional**: Can contain transaction control

**Advantages**:
1. **Performance**: Precompiled, reduced network traffic
2. **Security**: Execute permissions, data hiding
3. **Maintenance**: Centralized business logic
4. **Reusability**: Called from multiple applications

**Basic Structure**:
```sql
CREATE PROCEDURE GetEmployeeDetails
    @EmployeeID INT,
    @IncludeSalary BIT = 0
AS
BEGIN
    SET NOCOUNT ON;
    
    IF @IncludeSalary = 1
        SELECT name, department, salary 
        FROM Employees 
        WHERE id = @EmployeeID;
    ELSE
        SELECT name, department 
        FROM Employees 
        WHERE id = @EmployeeID;
END
```

**Usage**:
```sql
-- Execute stored procedure
EXEC GetEmployeeDetails @EmployeeID = 101, @IncludeSalary = 1;

-- With default parameter
EXEC GetEmployeeDetails @EmployeeID = 101;
```

**Transaction Handling**:
```sql
CREATE PROCEDURE TransferFunds
    @FromAccount INT,
    @ToAccount INT,
    @Amount DECIMAL(10,2)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;
        
        UPDATE Accounts SET balance = balance - @Amount 
        WHERE account_id = @FromAccount;
        
        UPDATE Accounts SET balance = balance + @Amount 
        WHERE account_id = @ToAccount;
        
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;  -- Re-throw error
    END CATCH
END
```

**Theoretical Keywords**: Precompiled routines, Database encapsulation, Transaction management, Security abstraction

### **110. Difference between stored procedure and function**
**Interviewer Answer:**
| **Stored Procedure** | **Function** |
|----------------------|--------------|
| **Return type**: Can return 0, 1, or multiple values | **Return type**: Must return single value (scalar) or table |
| **Usage in SELECT**: Cannot be used in SELECT statement | **Usage in SELECT**: Can be used in SELECT (scalar functions) |
| **DML operations**: Can perform DML (INSERT/UPDATE/DELETE) | **DML operations**: Cannot perform DML (usually) |
| **Transaction control**: Can use BEGIN/COMMIT/ROLLBACK | **Transaction control**: Cannot use transaction control |
| **Error handling**: Can use TRY-CATCH blocks | **Error handling**: Limited error handling |
| **Output parameters**: Supports output parameters | **Output parameters**: Returns value via RETURN statement |
| **Calling**: EXEC or EXECUTE command | **Calling**: Used in expressions |

**Examples**:

1. **Stored Procedure** (can modify data):
   ```sql
   CREATE PROCEDURE UpdateSalary
       @EmployeeID INT,
       @NewSalary DECIMAL(10,2)
   AS
   BEGIN
       UPDATE Employees 
       SET salary = @NewSalary 
       WHERE id = @EmployeeID;
   END
   ```
2. **Scalar Function** (returns single value):
   ```sql
   CREATE FUNCTION CalculateBonus(@Salary DECIMAL(10,2))
   RETURNS DECIMAL(10,2)
   AS
   BEGIN
       DECLARE @Bonus DECIMAL(10,2);
       SET @Bonus = @Salary * 0.10; -- 10% bonus
       RETURN @Bonus;
   END
   
   -- Usage in SELECT
   SELECT name, salary, dbo.CalculateBonus(salary) AS bonus
   FROM Employees;
   ```

3. **Table-Valued Function** (returns table):
   ```sql
   CREATE FUNCTION GetDepartmentEmployees(@DeptID INT)
   RETURNS TABLE
   AS
   RETURN (
       SELECT id, name, salary
       FROM Employees
       WHERE department_id = @DeptID
   );
   
   -- Usage in SELECT
   SELECT * FROM dbo.GetDepartmentEmployees(5);
   ```

**Key Differences Summary**:
- **Purpose**: Procedures for actions, functions for calculations
- **Return**: Procedures optional, functions mandatory
- **SQL Context**: Functions can be embedded in queries
- **Side Effects**: Procedures can have, functions shouldn't

**Theoretical Keywords**: Scalar vs Table-valued, Deterministic vs Non-deterministic, SQL embeddability, Side effects

### **111. What is view?**
**Interviewer Answer:**
- **View**: Virtual table based on SQL query result set
- **Characteristics**:
  1. **No physical storage**: Doesn't store data (except materialized views)
  2. **Dynamic**: Always reflects current data
  3. **Security layer**: Can restrict columns/rows
  4. **Simplification**: Complex query abstraction

**Advantages**:
1. **Security**: Column/row-level data hiding
2. **Simplification**: Complex joins made simple
3. **Consistency**: Standardized data access
4. **Logical independence**: Underlying schema changes don't affect users

**Basic Structure**:
```sql
-- Simple view
CREATE VIEW ActiveEmployees AS
SELECT emp_id, name, department, hire_date
FROM Employees
WHERE status = 'Active'
  AND termination_date IS NULL;

-- Complex view with joins
CREATE VIEW EmployeeDetails AS
SELECT 
    e.emp_id,
    e.name,
    e.salary,
    d.department_name,
    m.name AS manager_name
FROM Employees e
JOIN Departments d ON e.department_id = d.dept_id
LEFT JOIN Employees m ON e.manager_id = m.emp_id
WHERE e.status = 'Active';

-- Usage (treat like table)
SELECT * FROM ActiveEmployees WHERE department = 'Sales';
SELECT department_name, AVG(salary) 
FROM EmployeeDetails 
GROUP BY department_name;
```

**Updatable Views** (conditions apply):
```sql
CREATE VIEW SalesEmployees AS
SELECT emp_id, name, salary, commission
FROM Employees
WHERE department = 'Sales'
WITH CHECK OPTION;  -- Ensures updates stay in view scope

-- Can perform DML on simple views
UPDATE SalesEmployees 
SET commission = commission * 1.1 
WHERE emp_id = 101;
```

**View Limitations**:
1. Cannot have ORDER BY without TOP/OFFSET-FETCH (in some DBMS)
2. Performance overhead (query runs each time)
3. Some views are read-only (complex joins, aggregates)

**Theoretical Keywords**: Virtual table, Dynamic result set, Security abstraction, Query simplification

### **112. Difference between view and table**
**Interviewer Answer**:
| **Aspect** | **View** | **Table** |
|------------|----------|-----------|
| **Storage** | Virtual, no physical data | Physical, stores actual data |
| **Data persistence** | Temporary, generated on access | Permanent, persisted on disk |
| **Indexes** | Cannot create indexes directly (except indexed views) | Can create multiple indexes |
| **Performance** | Can be slower (query runs each time) | Generally faster with indexes |
| **DML operations** | Limited (only simple views are updatable) | Full DML support |
| **Schema changes** | Affected by underlying table changes | Direct schema modifications |
| **Storage space** | Minimal (stores only definition) | Consumes actual disk space |

**Key Differences**:
1. **Physical vs Virtual**: Table = actual data storage, View = query result
2. **Data Freshness**: View always shows current table data
3. **Modification Rules**: Tables freely modifiable, views have restrictions
4. **Performance**: Tables optimized with indexes, views add overhead

**When to Use View**:
- Security (hide sensitive columns)
- Simplify complex queries
- Present aggregated data
- Backward compatibility during schema changes

**When to Use Table**:
- Actual data storage
- Frequent updates/deletes
- Performance-critical operations
- Need for indexing

**Theoretical Keywords**: Physical storage, Data persistence, Schema binding, Data abstraction

### **113. What is materialized view?**
**Interviewer Answer**:
- **Materialized View**: Physical copy of query result stored as table
- **Also called**: Indexed view (SQL Server), Snapshot (Oracle)
- **Characteristics**:
  1. **Physical storage**: Stores actual data
  2. **Periodic refresh**: Data can become stale
  3. **Performance**: Fast read access (pre-computed)
  4. **Storage cost**: Consumes disk space

**Advantages**:
1. **Performance**: Pre-computed complex joins/aggregates
2. **Query speed**: Milliseconds vs seconds for complex queries
3. **Load distribution**: Reduce load on source tables
4. **Offline access**: Available when source tables are busy

**Disadvantages**:
1. **Stale data**: Requires refresh mechanisms
2. **Storage overhead**: Duplicates data
3. **Maintenance**: Refresh scheduling needed
4. **Write overhead**: Source table changes trigger updates

**Basic Structure**:
```sql
-- SQL Server (Indexed View)
CREATE VIEW MonthlySalesSummary WITH SCHEMABINDING AS
SELECT 
    YEAR(order_date) AS order_year,
    MONTH(order_date) AS order_month,
    product_category,
    COUNT_BIG(*) AS total_orders,
    SUM(sale_amount) AS total_sales
FROM dbo.Sales
GROUP BY YEAR(order_date), MONTH(order_date), product_category;

-- Create clustered index to materialize
CREATE UNIQUE CLUSTERED INDEX IDX_MonthlySales
ON MonthlySalesSummary(order_year, order_month, product_category);

-- Oracle Materialized View
CREATE MATERIALIZED VIEW MonthlySalesSummary
REFRESH FAST ON COMMIT  -- or REFRESH COMPLETE ON DEMAND
AS
SELECT ...;  -- Same query as above
```

**Refresh Strategies**:
1. **Complete refresh**: Rebuild entirely (slow, resource-heavy)
2. **Fast/Incremental refresh**: Update only changes (requires log)
3. **On commit**: Immediate refresh after source changes
4. **On demand**: Manual or scheduled refresh
5. **Periodic**: Hourly/daily refresh

**Use Cases**:
1. **Data Warehousing**: Pre-aggregated facts
2. **Dashboard/reporting**: Fast summary data
3. **Geographically distributed**: Local copies
4. **Performance-critical**: Complex calculations pre-done

**Theoretical Keywords**: Pre-computed results, Query optimization, Data redundancy, Refresh strategies

### **114. How to improve slow SQL query performance?**
**Interviewer Answer**:
**Systematic Approach**:
1. **Diagnose** → 2. **Analyze** → 3. **Optimize** → 4. **Test**

**Step 1: Diagnosis & Measurement**
```sql
-- SQL Server: Actual Execution Plan
SET STATISTICS IO ON;
SET STATISTICS TIME ON;
-- Run query here

-- MySQL: EXPLAIN ANALYZE
EXPLAIN ANALYZE
SELECT * FROM Orders WHERE customer_id = 100;

-- PostgreSQL: EXPLAIN (BUFFERS, ANALYZE)
EXPLAIN (ANALYZE, BUFFERS)
SELECT * FROM products WHERE price > 1000;
```

**Step 2: Common Performance Issues & Solutions**

**A. Indexing Strategies**:
```sql
-- 1. Missing indexes (check execution plan)
CREATE INDEX IX_Orders_CustomerDate 
ON Orders(customer_id, order_date)
INCLUDE (total_amount, status);  -- Covering index

-- 2. Remove unused/redundant indexes
-- 3. Index maintenance
ALTER INDEX ALL ON Orders REORGANIZE;
UPDATE STATISTICS Orders WITH FULLSCAN;
```

**B. Query Rewriting**:
```sql
-- Bad: Nested subquery
SELECT name FROM Employees 
WHERE department_id IN (
    SELECT dept_id FROM Departments WHERE location = 'NYC'
);

-- Better: JOIN
SELECT e.name 
FROM Employees e
JOIN Departments d ON e.department_id = d.dept_id
WHERE d.location = 'NYC';

-- Bad: SELECT * (unnecessary columns)
SELECT * FROM Orders;

-- Better: Select only needed columns
SELECT order_id, order_date, total_amount 
FROM Orders;
```

**C. Join Optimization**:
```sql
-- Use EXISTS instead of IN for large datasets
SELECT name FROM Customers c
WHERE EXISTS (
    SELECT 1 FROM Orders o 
    WHERE o.customer_id = c.customer_id
);

-- Ensure join columns are indexed
-- Use appropriate join types (INNER vs LEFT)
```

**D. Aggregation & Sorting**:
```sql
-- Move filtering before grouping
SELECT customer_id, COUNT(*) as order_count
FROM Orders
WHERE order_date >= '2024-01-01'  -- Filter early
GROUP BY customer_id
HAVING COUNT(*) > 5;

-- Add indexes for GROUP BY/ORDER BY
CREATE INDEX IX_Orders_DateCustomer
ON Orders(order_date, customer_id);
```

**Step 3: Advanced Techniques**

**A. Partitioning**:
```sql
-- Partition by date range
CREATE PARTITION FUNCTION pf_OrderDates (DATE)
AS RANGE RIGHT FOR VALUES ('2024-01-01', '2024-07-01');

-- Partitioned table
CREATE TABLE Orders (
    order_id INT,
    order_date DATE,
    -- other columns
) ON pf_OrderDates(order_date);
```

**B. Query Hints (Use cautiously)**:
```sql
-- Force specific index
SELECT * FROM Orders WITH (INDEX(IX_Orders_CustomerDate))
WHERE customer_id = 100;

-- Optimize for known parameters
SELECT * FROM Products 
WHERE category_id = @category
OPTION (OPTIMIZE FOR (@category = 5));
```

**C. Temporary Tables/CTEs for Complex Queries**:
```sql
-- Break complex query into steps
WITH CustomerTotals AS (
    SELECT customer_id, SUM(amount) as total
    FROM Orders
    WHERE order_date >= DATEADD(MONTH, -1, GETDATE())
    GROUP BY customer_id
)
SELECT c.name, ct.total
FROM Customers c
JOIN CustomerTotals ct ON c.customer_id = ct.customer_id
WHERE ct.total > 10000;
```

**Step 4: Monitoring & Maintenance**

**Performance Checklist**:
1. □ Execution plan reviewed
2. □ Appropriate indexes exist
3. □ Statistics are updated
4. □ No table/scans in plan
5. □ Query uses SARGable predicates
6. □ No unnecessary joins/columns
7. □ Appropriate WHERE clause order
8. □ Considered partitioning for large tables

**Tools**:
- **SQL Server**: Query Store, Extended Events
- **MySQL**: Performance Schema, Slow Query Log
- **PostgreSQL**: pg_stat_statements, auto_explain
- **Oracle**: AWR Reports, SQL Tuning Advisor

**Theoretical Keywords**: Query optimization, Execution plans, Index tuning, Statistics, Partitioning, Caching strategies

**Golden Rules**:
1. Measure before optimizing
2. Index wisely (more ≠ better)
3. Write set-based operations
4. Consider data volume growth
5. Test with production-like data