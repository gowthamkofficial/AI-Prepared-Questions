# **SUBQUERIES AND CLAUSES ANSWERS**

## **Subqueries and Clauses (Questions 46-56)**

### **46. What is a subquery?**
** Answer:**
- A **subquery** is a SQL query nested inside another query
- **Also called**: Inner query, nested query
- **Executes first**: Inner query executes before outer query
- **Returns data** used by outer query for filtering or calculations

**Basic Structure**:
```sql
SELECT * 
FROM table1 
WHERE column1 IN (
    SELECT column2 FROM table2  -- This is the subquery
);
```

**Examples**:
```sql
-- Subquery in WHERE clause
SELECT name, salary
FROM Employees
WHERE salary > (
    SELECT AVG(salary) FROM Employees  -- Subquery
);

-- Subquery in FROM clause (derived table)
SELECT dept_name, avg_salary
FROM (
    SELECT department, AVG(salary) as avg_salary  -- Subquery
    FROM Employees
    GROUP BY department
) AS dept_stats
WHERE avg_salary > 50000;

-- Subquery in SELECT clause
SELECT name, 
       salary,
       (SELECT AVG(salary) FROM Employees) as company_avg  -- Subquery
FROM Employees;
```

**Key Characteristics**:
1. **Enclosed in parentheses**: `(SELECT ...)`
2. **Can return**: Single value, list of values, or table
3. **Can be used in**: WHERE, FROM, SELECT, HAVING clauses
4. **Can be correlated or non-correlated**

**Theoretical Keywords**: Nested query, Inner query, Query within query, Parent-child queries

### **47. Types of subqueries**
** Answer:**
**Based on Result Type**:

1. **Scalar Subquery**: Returns single value
   ```sql
   SELECT name 
   FROM Employees 
   WHERE salary > (SELECT AVG(salary) FROM Employees);
   -- Returns: Single average salary value
   ```

2. **Row Subquery**: Returns single row (multiple columns)
   ```sql
   SELECT * FROM Employees 
   WHERE (department, salary) = (
       SELECT department, MAX(salary) 
       FROM Employees 
       GROUP BY department 
       LIMIT 1
   );
   ```

3. **Table Subquery**: Returns multiple rows and columns
   ```sql
   SELECT * 
   FROM (SELECT department, AVG(salary) as avg_sal 
         FROM Employees 
         GROUP BY department) AS dept_stats
   WHERE avg_sal > 50000;
   ```

**Based on Execution**:

4. **Non-correlated (Simple) Subquery**: Independent of outer query
   ```sql
   SELECT name FROM Employees 
   WHERE dept_id IN (SELECT id FROM Departments WHERE location = 'NY');
   -- Inner query can execute alone
   ```

5. **Correlated Subquery**: References outer query columns
   ```sql
   SELECT name, salary
   FROM Employees e1
   WHERE salary > (
       SELECT AVG(salary) 
       FROM Employees e2 
       WHERE e1.department = e2.department  -- References outer query
   );
   ```

**Based on Position**:

6. **WHERE/HAVING Subquery**: For filtering
   ```sql
   SELECT department, AVG(salary)
   FROM Employees
   GROUP BY department
   HAVING AVG(salary) > (SELECT AVG(salary) FROM Employees);
   ```

7. **FROM Subquery** (Derived Table/Inline View): As data source
   ```sql
   SELECT * FROM (
       SELECT department, COUNT(*) as emp_count 
       FROM Employees 
       GROUP BY department
   ) AS dept_counts;
   ```

8. **SELECT Subquery**: In column list
   ```sql
   SELECT name, 
          salary,
          (SELECT AVG(salary) FROM Employees) as company_avg
   FROM Employees;
   ```

**Theoretical Keywords**: Scalar/row/table subqueries, Correlated/non-correlated, Query position, Result type

### **48. Difference between subquery and join**
** Answer:**
| **Subquery** | **JOIN** |
|--------------|----------|
| **Approach**: Nested SELECT statement | **Approach**: Combine tables horizontally |
| **Performance**: Can be slower (depends) | **Performance**: Usually faster for large data |
| **Readability**: Can be clearer for complex logic | **Readability**: Simpler for basic relationships |
| **Result**: Often returns subset/single value | **Result**: Combines columns from multiple tables |
| **Use case**: When you need value from another table | **Use case**: When you need columns from multiple tables |
| **NULL handling**: IN handles NULLs differently | **JOIN**: NULLs don't match in INNER JOIN |
| **Correlation**: Can be correlated (row-by-row) | **Correlation**: Processes sets of data |

**Examples Comparison**:
```sql
-- Using SUBQUERY: Find employees earning more than department average
SELECT name, salary, department
FROM Employees e1
WHERE salary > (
    SELECT AVG(salary) 
    FROM Employees e2 
    WHERE e1.department = e2.department
);

-- Using JOIN: Find employees and their department names
SELECT e.name, d.dept_name
FROM Employees e
JOIN Departments d ON e.dept_id = d.id;
```

**When to Use Subquery**:
1. **Aggregate comparisons**: Compare to average/max/min
2. **EXISTS checks**: Check for existence of records
3. **Derived calculations**: Complex calculations in FROM
4. **Correlated logic**: Row-by-row comparisons

**When to Use JOIN**:
1. **Combine columns**: Need data from multiple tables
2. **Set operations**: Working with sets of data
3. **Performance**: Usually better for large datasets
4. **Simple relationships**: Basic table connections

**Convertibility**:
```sql
-- Many subqueries can be rewritten as JOINs
-- Subquery with IN:
SELECT * FROM Customers 
WHERE id IN (SELECT customer_id FROM Orders);

-- Equivalent JOIN:
SELECT DISTINCT c.* 
FROM Customers c
JOIN Orders o ON c.id = o.customer_id;

-- But NOT all: Correlated subqueries are harder to convert
```

**Performance Consideration**: Modern databases often optimize both similarly

**Theoretical Keywords**: Nested vs combined, Row-by-row vs set-based, Performance trade-offs, Query optimization

### **49. What is correlated subquery?**
** Answer:**
- A **correlated subquery** references columns from the outer query
- **Executes repeatedly**: Once for each row processed by outer query
- **Dependent**: Cannot execute independently
- **Performance impact**: Can be slow for large datasets

**How it Works**:
```
For each row in outer query:
    1. Get values from current row
    2. Execute subquery using those values
    3. Use result in outer query condition
    4. Move to next row
```

**Example**:
```sql
-- Find employees earning more than their department average
SELECT name, salary, department
FROM Employees e1  -- Outer query
WHERE salary > (
    SELECT AVG(salary) 
    FROM Employees e2  -- Inner query
    WHERE e1.department = e2.department  -- Correlation: references outer query
);
-- For each employee, subquery calculates their department's average salary
```

**More Examples**:
```sql
-- Find highest paid employee in each department
SELECT name, department, salary
FROM Employees e1
WHERE salary = (
    SELECT MAX(salary)
    FROM Employees e2
    WHERE e1.department = e2.department  -- Correlated
);

-- Find customers who placed orders in last 30 days
SELECT name
FROM Customers c
WHERE EXISTS (
    SELECT 1 
    FROM Orders o
    WHERE o.customer_id = c.id  -- Correlated
      AND o.order_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
);

-- Update using correlated subquery
UPDATE Employees e1
SET salary = salary * 1.10
WHERE salary < (
    SELECT AVG(salary)
    FROM Employees e2
    WHERE e1.department = e2.department  -- Correlated
);
```

**Performance Characteristics**:
- **Slower**: Executes N times (N = outer query rows)
- **Optimization**: Modern DBMS optimize, but still expensive
- **Alternatives**: Often better as JOIN or window function

**When to Use Correlated Subqueries**:
1. **Row-by-row comparisons**: Compare each row to aggregate
2. **EXISTS/NOT EXISTS checks**: Check related records
3. **Top-N per group**: Find top records in each category
4. **When JOIN is complex**: Complex logic easier with subquery

**Modern Alternative**: Window Functions (often better)
```sql
-- Correlated subquery approach
SELECT name, salary, department
FROM Employees e1
WHERE salary > (
    SELECT AVG(salary) 
    FROM Employees e2 
    WHERE e1.department = e2.department
);

-- Window function approach (better)
SELECT name, salary, department
FROM (
    SELECT name, salary, department,
           AVG(salary) OVER (PARTITION BY department) as dept_avg
    FROM Employees
) AS emp_with_avg
WHERE salary > dept_avg;
```

**Theoretical Keywords**: Outer query reference, Row-by-row execution, Dependent subquery, Performance consideration

### **50. Difference between IN and EXISTS**
** Answer:**
| **IN** | **EXISTS** |
|--------|------------|
| **Purpose**: Check if value matches any in list | **Purpose**: Check if subquery returns any rows |
| **NULL handling**: `value IN (NULL)` returns UNKNOWN | **NULL handling**: EXISTS returns TRUE if any rows (even NULL) |
| **Performance**: Can be slower with large lists | **Performance**: Often faster, stops at first match |
| **Subquery returns**: List of values | **Subquery returns**: Any rows (SELECT 1 is common) |
| **Use with**: Static lists or subqueries | **Use with**: Correlated subqueries usually |

**Examples**:
```sql
-- IN with static list
SELECT * FROM Employees 
WHERE department IN ('IT', 'HR', 'Finance');

-- IN with subquery
SELECT * FROM Customers 
WHERE id IN (SELECT customer_id FROM Orders);

-- EXISTS (usually correlated)
SELECT * FROM Customers c
WHERE EXISTS (
    SELECT 1  -- Can be SELECT 1, *, or any column
    FROM Orders o 
    WHERE o.customer_id = c.id
);

-- NOT IN vs NOT EXISTS
SELECT * FROM Customers 
WHERE id NOT IN (SELECT customer_id FROM Orders);
-- Problem: Returns no rows if any NULL in Orders.customer_id

SELECT * FROM Customers c
WHERE NOT EXISTS (
    SELECT 1 
    FROM Orders o 
    WHERE o.customer_id = c.id
);
-- Safer: Handles NULLs correctly
```

**Performance Comparison**:
```sql
-- IN might process entire subquery result
SELECT * FROM table1 
WHERE id IN (SELECT id FROM large_table);

-- EXISTS might stop after first match (semi-join)
SELECT * FROM table1 t1
WHERE EXISTS (SELECT 1 FROM large_table t2 WHERE t2.id = t1.id);
```

**NULL Handling Critical Difference**:
```sql
-- Example showing NULL issue
CREATE TABLE Table1 (id INT);
CREATE TABLE Table2 (id INT);

INSERT INTO Table1 VALUES (1), (2), (3);
INSERT INTO Table2 VALUES (1), (NULL);

-- NOT IN problem
SELECT * FROM Table1 WHERE id NOT IN (SELECT id FROM Table2);
-- Returns: Nothing! Because: 1 NOT IN (1, NULL) = FALSE
--                           2 NOT IN (1, NULL) = UNKNOWN
--                           3 NOT IN (1, NULL) = UNKNOWN

-- NOT EXISTS solution
SELECT * FROM Table1 t1 
WHERE NOT EXISTS (SELECT 1 FROM Table2 t2 WHERE t2.id = t1.id);
-- Returns: 2, 3 (Correct)
```

**When to Use Which**:
- **Use IN**: Static lists, small result sets, non-correlated
- **Use EXISTS**: Correlated subqueries, checking existence, better with NULLs
- **Use NOT EXISTS** instead of NOT IN: Safer with possible NULLs

**Theoretical Keywords**: Set membership, Semi-join, NULL semantics, Early termination, Correlated queries

### **51. What is BETWEEN?**
** Answer:**
- **BETWEEN** operator checks if a value is within a range (inclusive)
- **Syntax**: `value BETWEEN low AND high`
- **Inclusive**: Includes both boundary values
- **Equivalent to**: `value >= low AND value <= high`

**Examples**:
```sql
-- Numeric range
SELECT * FROM Employees 
WHERE salary BETWEEN 40000 AND 70000;
-- Equivalent to: salary >= 40000 AND salary <= 70000

-- Date range
SELECT * FROM Orders 
WHERE order_date BETWEEN '2024-01-01' AND '2024-01-31';
-- Includes Jan 1 and Jan 31

-- String range (alphabetical)
SELECT * FROM Products 
WHERE name BETWEEN 'A' AND 'D';
-- Includes 'A', excludes 'E', includes 'Dave'

-- NOT BETWEEN
SELECT * FROM Employees 
WHERE salary NOT BETWEEN 40000 AND 70000;
-- salary < 40000 OR salary > 70000
```

**Important Notes**:
1. **Order matters**: `BETWEEN low AND high` (low ≤ high)
   ```sql
   -- Wrong: Won't return results
   SELECT * FROM Employees WHERE salary BETWEEN 70000 AND 40000;
   
   -- Correct:
   SELECT * FROM Employees WHERE salary BETWEEN 40000 AND 70000;
   ```

2. **Data type consistency**: Range values must match column type
   ```sql
   -- Date example
   SELECT * FROM Orders 
   WHERE order_date BETWEEN '2024-01-01' AND '2024-12-31';
   ```

3. **NULL handling**: NULL values are not included
   ```sql
   -- If salary is NULL, it won't appear in either:
   SELECT * FROM Employees WHERE salary BETWEEN 40000 AND 70000;
   SELECT * FROM Employees WHERE salary NOT BETWEEN 40000 AND 70000;
   ```

**Common Use Cases**:
```sql
-- Age range
SELECT * FROM Students WHERE age BETWEEN 18 AND 25;

-- Price range
SELECT * FROM Products WHERE price BETWEEN 10.00 AND 100.00;

-- Time period
SELECT * FROM Logs 
WHERE timestamp BETWEEN '2024-03-01 00:00:00' AND '2024-03-01 23:59:59';

-- Multiple BETWEEN conditions
SELECT * FROM Employees 
WHERE salary BETWEEN 40000 AND 70000
  AND hire_date BETWEEN '2020-01-01' AND '2023-12-31';
```

**Performance**: Using BETWEEN on indexed columns is efficient

**Theoretical Keywords**: Range operator, Inclusive bounds, Interval checking, SQL predicate

### **52. What is LIKE?**
** Answer:**
- **LIKE** operator performs pattern matching on strings
- **Uses wildcards**: `%` (any sequence), `_` (single character)
- **Case sensitivity**: Depends on database collation
- **Use with**: Text searches, partial matches

**Wildcards**:
1. **`%`**: Matches any sequence of characters (0 or more)
   ```sql
   'John%'  -- Matches 'John', 'Johnny', 'Johnson'
   '%son'   -- Matches 'Johnson', 'Jackson', 'Wilson'
   '%oh%'   -- Matches 'John', 'Mohammed', 'Soho'
   ```

2. **`_`**: Matches exactly one character
   ```sql
   '_ohn'   -- Matches 'John', 'Zohn' (4 characters)
   'J_n'    -- Matches 'Jan', 'Jon', 'Jun' (3 characters)
   'A__'    -- Matches 'Amy', 'Ace', 'Art' (3 characters)
   ```

**Examples**:
```sql
-- Basic LIKE patterns
SELECT * FROM Employees WHERE name LIKE 'J%';  -- Names starting with J
SELECT * FROM Products WHERE name LIKE '%phone%';  -- Contains 'phone'
SELECT * FROM Files WHERE name LIKE '%.pdf';  -- Ends with .pdf

-- Using underscore
SELECT * FROM Employees WHERE phone LIKE '555-___-____';  -- Pattern match
SELECT * FROM Products WHERE sku LIKE 'ABC-_-___';  -- Mixed pattern

-- NOT LIKE
SELECT * FROM Employees WHERE email NOT LIKE '%@gmail.com';

-- Case-insensitive search (depends on DBMS)
SELECT * FROM Employees WHERE LOWER(name) LIKE '%john%';
-- Or use ILIKE in PostgreSQL
SELECT * FROM Employees WHERE name ILIKE '%john%';
```

**Escape Characters**:
```sql
-- Search for literal % or _
SELECT * FROM Discounts WHERE description LIKE '50\% off';  -- Escape with \
-- Or define escape character
SELECT * FROM Logs WHERE message LIKE 'error#_%' ESCAPE '#';
```

**Performance Considerations**:
```sql
-- These can use indexes (if available):
SELECT * FROM Employees WHERE name LIKE 'J%';  -- Index friendly

-- These might NOT use indexes:
SELECT * FROM Employees WHERE name LIKE '%son';  -- Leading wildcard
SELECT * FROM Employees WHERE name LIKE '%oh%';  -- Wildcards both sides

-- Solution for trailing wildcard searches:
CREATE INDEX idx_name ON Employees(name);  -- Regular index
CREATE INDEX idx_name_reverse ON Employees(REVERSE(name));  -- For ending with
SELECT * FROM Employees WHERE REVERSE(name) LIKE REVERSE('%son');
```

**Common Patterns**:
```sql
-- Email validation pattern
SELECT * FROM Users WHERE email LIKE '%_@__%.__%';

-- Find records with specific format
SELECT * FROM Products WHERE sku LIKE 'ABC-2024-___';

-- Search with multiple conditions
SELECT * FROM Employees 
WHERE name LIKE 'J%' 
   OR name LIKE 'A%'
   OR email LIKE '%@company.com';
```

**Theoretical Keywords**: Pattern matching, Wildcards, String search, Partial matching, Text queries

### **53. What is UNION?**
** Answer:**
- **UNION** combines result sets of two or more SELECT statements
- **Removes duplicates**: Only distinct rows in final result
- **Requirements**:
  1. Same number of columns
  2. Compatible data types (or implicitly convertible)
  3. Column names from first SELECT are used

**Basic Syntax**:
```sql
SELECT column1, column2 FROM table1
UNION
SELECT column1, column2 FROM table2;
```

**Examples**:
```sql
-- Combine employees from two departments
SELECT name, 'IT' as department FROM Employees WHERE dept = 'IT'
UNION
SELECT name, 'HR' as department FROM Employees WHERE dept = 'HR'
ORDER BY name;

-- Combine current and former employees
SELECT id, name, 'Current' as status FROM Current_Employees
UNION
SELECT id, name, 'Former' as status FROM Former_Employees;

-- Multiple UNIONs
SELECT name, salary FROM Managers
UNION
SELECT name, salary FROM Developers
UNION
SELECT name, salary FROM Designers
ORDER BY salary DESC;
```

**Important Rules**:
1. **Column count must match**:
   ```sql
   -- ERROR: Different column counts
   SELECT id, name FROM Table1
   UNION
   SELECT id FROM Table2;  -- Missing second column
   ```

2. **Data types must be compatible**:
   ```sql
   -- Works (implicit conversion in some DBMS)
   SELECT id, name FROM Table1
   UNION
   SELECT employee_id, CAST(salary AS VARCHAR) FROM Table2;
   ```

3. **ORDER BY applies to entire result**:
   ```sql
   SELECT name FROM Employees WHERE dept = 'IT'
   UNION
   SELECT name FROM Employees WHERE dept = 'HR'
   ORDER BY name;  -- Sorts final combined result
   ```

4. **Column names from first query**:
   ```sql
   SELECT emp_name as name FROM Table1
   UNION
   SELECT cust_name FROM Table2;
   -- Result column named 'name' (from first query)
   ```

**Use Cases**:
1. **Combine similar data from different tables**
2. **Merge data from different periods**
3. **Create reports from multiple sources**
4. **Implement OR logic across tables**

**Performance**: UNION removes duplicates (sorting/distinct operation)

**Theoretical Keywords**: Set union, Result combination, Duplicate removal, Column compatibility

### **54. Difference between UNION and UNION ALL**
** Answer:**
| **UNION** | **UNION ALL** |
|-----------|---------------|
| **Duplicates**: Removes duplicate rows | **Duplicates**: Keeps all rows (including duplicates) |
| **Performance**: Slower (needs duplicate check) | **Performance**: Faster (no duplicate check) |
| **Result size**: ≤ sum of individual results | **Result size**: = sum of individual results |
| **Use when**: Need unique results | **Use when**: Want all data or know no duplicates |
| **Sorting**: Implicit sorting for duplicate removal | **Sorting**: No implicit sorting |

**Visual Difference**:
```
Table A: 1, 2, 3
Table B: 2, 3, 4

UNION:     1, 2, 3, 4    (Duplicates removed)
UNION ALL: 1, 2, 3, 2, 3, 4  (All rows kept)
```

**Examples**:
```sql
-- UNION (removes duplicates)
SELECT dept FROM Employees WHERE salary > 50000
UNION
SELECT dept FROM Managers;
-- Result: Distinct departments from either query

-- UNION ALL (keeps all rows)
SELECT dept FROM Employees WHERE salary > 50000
UNION ALL
SELECT dept FROM Managers;
-- Result: All departments, possibly with duplicates

-- When they give same result (no duplicates possible)
SELECT id, name FROM Current_Employees
UNION
SELECT id, name FROM Former_Employees;
-- No duplicates because employees can't be current and former

SELECT id, name FROM Current_Employees
UNION ALL
SELECT id, name FROM Former_Employees;
-- Same result, but UNION ALL is faster
```

**Performance Impact**:
```sql
-- UNION: Needs to sort/distinct (slower)
SELECT * FROM LargeTable1
UNION
SELECT * FROM LargeTable2;  -- Expensive duplicate removal

-- UNION ALL: Just concatenates (faster)
SELECT * FROM LargeTable1
UNION ALL
SELECT * FROM LargeTable2;  -- Simple concatenation

-- If you know no duplicates, always use UNION ALL
SELECT * FROM Table1 WHERE condition1
UNION ALL
SELECT * FROM Table1 WHERE condition2;  -- Conditions are mutually exclusive
```

**When to Use Which**:
- **Use UNION ALL** when:
  1. You know there are no duplicates
  2. You want to preserve duplicates
  3. Performance is critical
  4. You'll filter duplicates later

- **Use UNION** when:
  1. You need distinct results
  2. Duplicates don't make sense logically
  3. Result size matters more than performance

**Converting UNION to UNION ALL with DISTINCT**:
```sql
-- These are equivalent:
SELECT * FROM Table1
UNION
SELECT * FROM Table2;

SELECT DISTINCT * FROM (
    SELECT * FROM Table1
    UNION ALL
    SELECT * FROM Table2
) AS combined;
```

**Theoretical Keywords**: Duplicate handling, Performance difference, Set operations, Result concatenation

### **55. What is LIMIT?**
** Answer:**
- **LIMIT** restricts number of rows returned by a query
- **Database specific**: Syntax varies (MySQL, PostgreSQL use LIMIT)
- **Use cases**: Pagination, top-N queries, sampling
- **Often used with ORDER BY** for consistent results

**Syntax by Database**:
```sql
-- MySQL, PostgreSQL, SQLite
SELECT * FROM Employees LIMIT 10;

-- SQL Server, MS Access
SELECT TOP 10 * FROM Employees;

-- Oracle (older syntax)
SELECT * FROM Employees WHERE ROWNUM <= 10;

-- Oracle (12c+ ANSI syntax)
SELECT * FROM Employees FETCH FIRST 10 ROWS ONLY;
```

**Examples**:
```sql
-- Basic LIMIT
SELECT * FROM Employees LIMIT 5;  -- First 5 rows

-- With ORDER BY (common pattern)
SELECT * FROM Employees 
ORDER BY salary DESC 
LIMIT 10;  -- Top 10 highest paid

-- With WHERE clause
SELECT * FROM Orders 
WHERE status = 'Shipped' 
ORDER BY order_date DESC 
LIMIT 20;  -- 20 most recent shipped orders

-- Limit with offset (pagination)
SELECT * FROM Products 
ORDER BY name 
LIMIT 10 OFFSET 20;  -- Rows 21-30 (page 3 if 10 per page)
```

**Important Behaviors**:
1. **Without ORDER BY**: Returns arbitrary rows
   ```sql
   SELECT * FROM Employees LIMIT 5;
   -- Which 5 rows? Depends on database implementation
   ```

2. **With ties** (some databases):
   ```sql
   -- PostgreSQL: Get top salaries, including ties
   SELECT * FROM Employees 
   ORDER BY salary DESC 
   FETCH FIRST 5 ROWS WITH TIES;
   ```

3. **Percentage** (SQL Server):
   ```sql
   SELECT TOP 10 PERCENT * FROM Employees 
   ORDER BY hire_date;  -- Oldest 10% of employees
   ```

**Use Cases**:
```sql
-- 1. Pagination
SELECT * FROM Products 
ORDER BY product_id 
LIMIT 20 OFFSET 40;  -- Page 3 (20 items per page)

-- 2. Top-N analysis
SELECT * FROM Sales 
ORDER BY revenue DESC 
LIMIT 5;  -- Top 5 selling products

-- 3. Sampling
SELECT * FROM LargeTable 
ORDER BY RAND() 
LIMIT 100;  -- Random sample of 100 rows

-- 4. Recent items
SELECT * FROM Logs 
ORDER BY timestamp DESC 
LIMIT 50;  -- 50 most recent log entries
```

**Performance Considerations**:
- **With ORDER BY**: Database must sort all data first
- **Without ORDER BY**: Can stop early (faster)
- **Large OFFSET**: Can be slow (must skip many rows)

**Theoretical Keywords**: Row restriction, Result limiting, Pagination, Top-N queries, Database-specific syntax

### **56. What is OFFSET?**
** Answer:**
- **OFFSET** skips specified number of rows before returning results
- **Used with LIMIT** for pagination
- **Common pattern**: `LIMIT n OFFSET m` returns rows m+1 to m+n
- **Performance note**: Large offsets can be inefficient

**Basic Syntax**:
```sql
SELECT * FROM table 
LIMIT 10 OFFSET 20;  -- Skip 20 rows, return next 10
-- Returns rows 21-30
```

**Examples**:
```sql
-- Basic pagination
SELECT * FROM Products 
ORDER BY name 
LIMIT 10 OFFSET 0;   -- Page 1: rows 1-10

SELECT * FROM Products 
ORDER BY name 
LIMIT 10 OFFSET 10;  -- Page 2: rows 11-20

SELECT * FROM Products 
ORDER BY name 
LIMIT 10 OFFSET 20;  -- Page 3: rows 21-30

-- Without LIMIT (some databases support)
SELECT * FROM Employees 
ORDER BY hire_date 
OFFSET 5;  -- Skip first 5, return all rest

-- Alternative syntax (same meaning)
SELECT * FROM Products 
ORDER BY name 
LIMIT 10, 20;  -- MySQL: LIMIT offset, count (offset=10, count=20)
```

**Pagination Implementation**:
```sql
-- Common pagination pattern
DECLARE @PageNumber INT = 3;
DECLARE @PageSize INT = 10;
DECLARE @Offset INT = (@PageNumber - 1) * @PageSize;

SELECT * FROM Products 
ORDER BY product_id 
LIMIT @PageSize OFFSET @Offset;  -- Page 3: rows 21-30

-- Or with variables in application
-- page = 3, page_size = 10
-- offset = (3-1) * 10 = 20
```

**Performance Issues with Large OFFSET**:
```sql
-- Problematic: Large offset requires skipping many rows
SELECT * FROM LargeTable 
ORDER BY id 
LIMIT 10 OFFSET 1000000;
-- Database must scan/skip 1,000,000 rows!

-- Solutions:
-- 1. Use WHERE clause with keyset pagination
SELECT * FROM LargeTable 
WHERE id > last_seen_id  -- From previous page
ORDER BY id 
LIMIT 10;

-- 2. Use indexed column for filtering
SELECT * FROM LargeTable 
WHERE created_date < '2024-01-01'
ORDER BY created_date DESC 
LIMIT 10 OFFSET 20;
```

**Keyset (Seek) Pagination Alternative**:
```sql
-- Traditional OFFSET pagination (slow for deep pages)
SELECT * FROM Users 
ORDER BY created_at, id 
LIMIT 10 OFFSET 10000;

-- Keyset pagination (faster)
-- Client remembers last seen values
SELECT * FROM Users 
WHERE (created_at, id) > ('2024-01-01', 12345)
ORDER BY created_at, id 
LIMIT 10;
```

**Database-Specific Syntax**:
```sql
-- PostgreSQL
SELECT * FROM table OFFSET 10 LIMIT 5;

-- MySQL
SELECT * FROM table LIMIT 10, 5;  -- OFFSET 10, LIMIT 5

-- SQL Server 2012+
SELECT * FROM table 
ORDER BY column 
OFFSET 10 ROWS 
FETCH NEXT 5 ROWS ONLY;

-- Oracle 12c+
SELECT * FROM table 
ORDER BY column 
OFFSET 10 ROWS 
FETCH NEXT 5 ROWS ONLY;
```

**Use Cases**:
1. **Web application pagination**
2. **Batch processing** (process in chunks)
3. **Sampling** (skip first N, then sample)
4. **Window into sorted data** (view specific segment)

**Theoretical Keywords**: Row skipping, Pagination, Result window, Performance consideration, Keyset pagination

---

## **Combined Examples and Best Practices**

### **Complex Query Combining Multiple Concepts**:
```sql
-- Find top 3 highest paid employees in each department
-- who have been with company more than 2 years
SELECT * FROM (
    SELECT 
        name,
        department,
        salary,
        DATEDIFF(YEAR, hire_date, GETDATE()) as years_with_company,
        ROW_NUMBER() OVER (PARTITION BY department ORDER BY salary DESC) as rank
    FROM Employees
    WHERE DATEDIFF(YEAR, hire_date, GETDATE()) > 2
) AS ranked_employees
WHERE rank <= 3
ORDER BY department, rank;
```

### **Performance Tips**:
1. **Use EXISTS instead of IN** for correlated subqueries
2. **Prefer JOIN over subquery** when combining columns
3. **Use UNION ALL instead of UNION** when duplicates don't matter
4. **Avoid LIKE with leading wildcards** on indexed columns
5. **Use keyset pagination** instead of OFFSET for large datasets

### **Common  Patterns**:
```sql
-- Pattern 1: Find employees without managers
SELECT e.name 
FROM Employees e
LEFT JOIN Employees m ON e.manager_id = m.emp_id
WHERE m.emp_id IS NULL;

-- Pattern 2: Find duplicate records
SELECT email, COUNT(*)
FROM Users
GROUP BY email
HAVING COUNT(*) > 1;

-- Pattern 3: Second highest salary
SELECT MAX(salary) 
FROM Employees 
WHERE salary < (SELECT MAX(salary) FROM Employees);

-- Pattern 4: Employees earning more than their department average
SELECT e1.name, e1.salary, e1.department
FROM Employees e1
WHERE e1.salary > (
    SELECT AVG(e2.salary) 
    FROM Employees e2 
    WHERE e2.department = e1.department
);
```

**You now have comprehensive knowledge of SQL Subqueries and Clauses!** These are essential for writing complex queries and are frequently tested in . Practice combining these concepts to solve real-world problems! 🗄️🚀