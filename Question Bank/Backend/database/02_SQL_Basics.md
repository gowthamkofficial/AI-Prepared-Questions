# **SQL BASICS ANSWERS**

## **SQL Basics (Questions 21-35)**

### **21. What is SQL?**
** Answer:**
- **SQL (Structured Query Language)**: Standard language for managing and manipulating relational databases
- **Purpose**: Create, read, update, and delete database records
- **Characteristics**:
  - Declarative language (specify **what**, not **how**)
  - Case-insensitive (though conventions vary)
  - Platform-independent (works across Oracle, MySQL, PostgreSQL, etc.)
- **Components**: DDL, DML, DCL, TCL

**Theoretical Keywords**: Structured Query Language, Relational databases, Declarative language, ANSI standard, CRUD operations

### **22. Types of SQL commands**
** Answer:**
1. **DDL (Data Definition Language)**: Define database structure
   - CREATE, ALTER, DROP, TRUNCATE, RENAME
2. **DML (Data Manipulation Language)**: Manipulate data
   - SELECT, INSERT, UPDATE, DELETE
3. **DCL (Data Control Language)**: Control access/privileges
   - GRANT, REVOKE
4. **TCL (Transaction Control Language)**: Manage transactions
   - COMMIT, ROLLBACK, SAVEPOINT

**Visual Overview**:
```
SQL Commands
├── DDL (Structure)
├── DML (Data)  
├── DCL (Access)
└── TCL (Transactions)
```

**Theoretical Keywords**: DDL, DML, DCL, TCL, Command categories, Database operations

### **23. What is DDL?**
** Answer:**
- **DDL (Data Definition Language)**: Commands that define/modify database structure
- **Auto-commit**: DDL commands auto-commit (cannot be rolled back)
- **Commands**:
  1. **CREATE**: Create new database objects
     ```sql
     CREATE TABLE Employees (
         id INT PRIMARY KEY,
         name VARCHAR(50),
         salary DECIMAL(10,2)
     );
     ```
  2. **ALTER**: Modify existing structure
     ```sql
     ALTER TABLE Employees ADD COLUMN department VARCHAR(30);
     ```
  3. **DROP**: Remove database objects
     ```sql
     DROP TABLE Employees;
     ```
  4. **TRUNCATE**: Remove all records, keep structure
     ```sql
     TRUNCATE TABLE Employees;
     ```
  5. **RENAME**: Rename database objects
     ```sql
     RENAME TABLE Employees TO Staff;
     ```

**Theoretical Keywords**: Schema definition, Auto-commit, Database structure, CREATE/ALTER/DROP

### **24. What is DML?**
** Answer:**
- **DML (Data Manipulation Language)**: Commands that work with actual data
- **Not auto-commit**: Changes can be rolled back (unless auto-commit enabled)
- **Commands**:
  1. **SELECT**: Retrieve data
     ```sql
     SELECT * FROM Employees WHERE salary > 50000;
     ```
  2. **INSERT**: Add new records
     ```sql
     INSERT INTO Employees (id, name, salary) 
     VALUES (1, 'John', 60000);
     ```
  3. **UPDATE**: Modify existing records
     ```sql
     UPDATE Employees 
     SET salary = 65000 
     WHERE id = 1;
     ```
  4. **DELETE**: Remove specific records
     ```sql
     DELETE FROM Employees WHERE id = 1;
     ```
  5. **MERGE**: Upsert operations (insert or update)

**Theoretical Keywords**: Data manipulation, CRUD operations, Transaction control, SELECT/INSERT/UPDATE/DELETE

### **25. What is DCL?**
** Answer:**
- **DCL (Data Control Language)**: Commands that control access/privileges
- **Security management**: Grant/revoke permissions
- **Commands**:
  1. **GRANT**: Give privileges to users
     ```sql
     GRANT SELECT, INSERT ON Employees TO user1;
     GRANT ALL PRIVILEGES ON Employees TO admin;
     ```
  2. **REVOKE**: Remove privileges from users
     ```sql
     REVOKE DELETE ON Employees FROM user1;
     ```
- **Privilege types**:
  - Object privileges: SELECT, INSERT, UPDATE, DELETE, EXECUTE
  - System privileges: CREATE TABLE, CREATE USER, DROP ANY TABLE

**Example**:
```sql
-- Create user and grant access
CREATE USER analyst IDENTIFIED BY 'password';
GRANT SELECT ON Employees TO analyst;
GRANT CREATE SESSION TO analyst;

-- Later, revoke access
REVOKE SELECT ON Employees FROM analyst;
```

**Theoretical Keywords**: Access control, Security, Privileges, GRANT, REVOKE, User management

### **26. What is TCL?**
** Answer:**
- **TCL (Transaction Control Language)**: Commands that manage database transactions
- **Transaction**: Logical unit of work (ACID properties)
- **Commands**:
  1. **COMMIT**: Save changes permanently
     ```sql
     UPDATE Employees SET salary = salary * 1.1;
     COMMIT; -- Changes saved permanently
     ```
  2. **ROLLBACK**: Undo changes since last commit
     ```sql
     DELETE FROM Employees;
     ROLLBACK; -- All employees restored
     ```
  3. **SAVEPOINT**: Create point to rollback to
     ```sql
     SAVEPOINT sp1;
     DELETE FROM Employees WHERE id = 1;
     SAVEPOINT sp2;
     UPDATE Employees SET salary = 0;
     ROLLBACK TO sp2; -- Undo update, keep delete
     ```

**Transaction Example**:
```sql
BEGIN TRANSACTION;
    UPDATE Accounts SET balance = balance - 100 WHERE id = 1;
    UPDATE Accounts SET balance = balance + 100 WHERE id = 2;
    
    -- Check if transfer succeeded
    IF (SELECT balance FROM Accounts WHERE id = 1) >= 0
        COMMIT; -- Save changes
    ELSE
        ROLLBACK; -- Undo everything
    END IF;
```

**Theoretical Keywords**: Transaction management, ACID properties, COMMIT, ROLLBACK, SAVEPOINT

### **27. Difference between DELETE and TRUNCATE**
** Answer:**
| **DELETE** | **TRUNCATE** |
|------------|--------------|
| DML command | DDL command |
| Can use WHERE clause | No WHERE clause (removes all) |
| Removes rows one by one | Removes all rows at once |
| Can be rolled back | Cannot be rolled back (auto-commit) |
| Fires triggers | Does not fire triggers |
| Slower for large data | Faster for large data |
| Keeps table structure | Keeps table structure |
| Resets auto-increment? No | Resets auto-increment to start |
| Can be used with indexed views | Cannot be used with indexed views |

**Examples**:
```sql
-- DELETE: Remove specific records
DELETE FROM Employees WHERE department = 'HR'; -- Can rollback

-- TRUNCATE: Remove all records
TRUNCATE TABLE Employees; -- Cannot rollback
```

**When to use**:
- **DELETE**: When you need to remove specific rows, need audit trails
- **TRUNCATE**: When you need to quickly remove all data from table

**Theoretical Keywords**: DML vs DDL, Row removal, Performance, Transaction control, Auto-increment reset

### **28. Difference between DELETE and DROP**
** Answer:**
| **DELETE** | **DROP** |
|------------|----------|
| DML command | DDL command |
| Removes data only | Removes table structure + data |
| Table structure remains | Table structure destroyed |
| Can use WHERE clause | No WHERE clause |
| Can be rolled back | Cannot be rolled back |
| Slower operation | Very fast operation |
| Fires triggers | Does not fire triggers |
| Example: `DELETE FROM table` | Example: `DROP TABLE table` |

**Examples**:
```sql
-- DELETE: Remove all data, keep table
DELETE FROM Employees; -- Empty table remains

-- DROP: Remove table completely
DROP TABLE Employees; -- Table no longer exists

-- DROP with CASCADE (removes dependencies)
DROP TABLE Departments CASCADE CONSTRAINTS;
```

**Visual Difference**:
```
DELETE: Table (Structure + Indexes) → Table (Structure + Indexes) with no rows
DROP: Table (Structure + Data) → Nothing (table removed from database)
```

**Theoretical Keywords**: Data removal vs structure removal, DML vs DDL, Table existence, Cascade constraints

### **29. What is SELECT statement?**
** Answer:**
- **SELECT**: Retrieves data from one or more tables
- **Basic syntax**: `SELECT columns FROM table WHERE conditions`
- **Most commonly used** SQL command

**Examples**:
```sql
-- 1. Select all columns
SELECT * FROM Employees;

-- 2. Select specific columns
SELECT name, salary FROM Employees;

-- 3. Select with calculation
SELECT name, salary * 12 AS annual_salary FROM Employees;

-- 4. Select distinct values
SELECT DISTINCT department FROM Employees;

-- 5. Select from multiple tables (JOIN)
SELECT e.name, d.dept_name 
FROM Employees e 
JOIN Departments d ON e.dept_id = d.id;

-- 6. Select with aggregate functions
SELECT department, AVG(salary) as avg_salary
FROM Employees
GROUP BY department;
```

**SELECT Statement Clauses**:
```sql
SELECT   -- What columns to return
FROM     -- Which tables to query
WHERE    -- Filter rows (before grouping)
GROUP BY -- Group rows
HAVING   -- Filter groups (after grouping)  
ORDER BY -- Sort results
LIMIT    -- Limit number of rows (MySQL/PostgreSQL)
OFFSET   -- Skip rows
```

**Theoretical Keywords**: Data retrieval, Query execution, Result set, SQL clauses, Column projection

### **30. What is WHERE clause?**
** Answer:**
- **WHERE**: Filters rows based on specified conditions
- **Applied before** GROUP BY (filters individual rows)
- **Cannot use** aggregate functions directly

**Examples**:
```sql
-- 1. Basic comparison
SELECT * FROM Employees WHERE salary > 50000;

-- 2. Multiple conditions (AND/OR)
SELECT * FROM Employees 
WHERE department = 'IT' AND salary > 60000;

-- 3. Pattern matching (LIKE)
SELECT * FROM Employees WHERE name LIKE 'J%'; -- Starts with J

-- 4. IN operator
SELECT * FROM Employees 
WHERE department IN ('IT', 'HR', 'Finance');

-- 5. BETWEEN operator
SELECT * FROM Employees 
WHERE salary BETWEEN 40000 AND 70000;

-- 6. NULL checking
SELECT * FROM Employees WHERE manager_id IS NULL;

-- 7. NOT operator
SELECT * FROM Employees WHERE department NOT IN ('HR');
```

**Operators used with WHERE**:
- **Comparison**: `=, <>, !=, >, <, >=, <=`
- **Logical**: `AND, OR, NOT`
- **Pattern**: `LIKE, NOT LIKE` (with `%` and `_`)
- **Range**: `BETWEEN, NOT BETWEEN`
- **List**: `IN, NOT IN`
- **NULL**: `IS NULL, IS NOT NULL`

**Theoretical Keywords**: Row filtering, Condition evaluation, Predicates, Comparison operators, NULL handling

### **31. What is ORDER BY?**
** Answer:**
- **ORDER BY**: Sorts result set by specified columns
- **Default**: ASC (ascending), can specify DESC (descending)
- **Can sort by** multiple columns

**Examples**:
```sql
-- 1. Single column ascending (default)
SELECT * FROM Employees ORDER BY name;

-- 2. Single column descending
SELECT * FROM Employees ORDER BY salary DESC;

-- 3. Multiple columns
SELECT * FROM Employees 
ORDER BY department ASC, salary DESC;

-- 4. Sort by column position
SELECT name, salary FROM Employees ORDER BY 2 DESC; -- Sort by salary

-- 5. Sort by expression
SELECT name, salary, salary * 12 AS annual
FROM Employees 
ORDER BY annual DESC;

-- 6. NULLs handling (NULLS FIRST/LAST in some DBs)
SELECT * FROM Employees 
ORDER BY commission NULLS LAST;
```

**Sorting Rules**:
- **Strings**: Alphabetical order (A-Z, a-z)
- **Numbers**: Numerical order
- **Dates**: Chronological order
- **NULLs**: Typically treated as lowest values

**Important**: ORDER BY is **last** clause executed (except LIMIT)

**Theoretical Keywords**: Result sorting, Ascending/descending, Multiple columns, NULL ordering, Sort precedence

### **32. What is GROUP BY?**
** Answer:**
- **GROUP BY**: Groups rows that have same values into summary rows
- **Used with** aggregate functions: COUNT, SUM, AVG, MAX, MIN
- **Creates** one row per distinct combination of group columns

**Examples**:
```sql
-- 1. Group by single column
SELECT department, COUNT(*) as employee_count
FROM Employees
GROUP BY department;

-- 2. Group by multiple columns
SELECT department, job_title, AVG(salary) as avg_salary
FROM Employees
GROUP BY department, job_title;

-- 3. With WHERE clause (filter before grouping)
SELECT department, AVG(salary)
FROM Employees
WHERE hire_date > '2020-01-01'
GROUP BY department;

-- 4. Group by expression
SELECT EXTRACT(YEAR FROM hire_date) as hire_year, 
       COUNT(*) as hires
FROM Employees
GROUP BY EXTRACT(YEAR FROM hire_date);

-- 5. Common errors (will fail - non-aggregated columns)
-- SELECT name, department, AVG(salary)  -- ERROR: name not in GROUP BY
-- FROM Employees
-- GROUP BY department;
```

**Key Rules**:
1. **All non-aggregated columns** in SELECT must be in GROUP BY
2. **WHERE filters rows** before grouping
3. **HAVING filters groups** after grouping
4. **ORDER BY sorts** final result

**Theoretical Keywords**: Data aggregation, Summary rows, Aggregate functions, Grouping columns, Result reduction

### **33. What is HAVING clause?**
** Answer:**
- **HAVING**: Filters groups created by GROUP BY
- **Applied after** GROUP BY (filters aggregated results)
- **Can use** aggregate functions in conditions

**Examples**:
```sql
-- 1. Filter groups by aggregate value
SELECT department, AVG(salary) as avg_salary
FROM Employees
GROUP BY department
HAVING AVG(salary) > 50000;

-- 2. Multiple conditions
SELECT department, COUNT(*) as emp_count
FROM Employees
GROUP BY department
HAVING COUNT(*) > 5 AND AVG(salary) > 45000;

-- 3. Using column aliases (not all DBs support)
SELECT department, AVG(salary) as avg_sal
FROM Employees
GROUP BY department
HAVING avg_sal > 50000; -- Some DBs allow, some don't

-- 4. Compare aggregates
SELECT department, 
       MAX(salary) as max_sal,
       MIN(salary) as min_sal
FROM Employees
GROUP BY department
HAVING MAX(salary) > 2 * MIN(salary);

-- 5. Without GROUP BY (acts like WHERE for aggregates)
SELECT COUNT(*) as total 
FROM Employees
HAVING COUNT(*) > 100; -- Valid, but unusual
```

**Important**: HAVING without GROUP BY treats entire result as single group

**Theoretical Keywords**: Group filtering, Aggregate conditions, Post-grouping filter, Group-level predicates

### **34. Difference between WHERE and HAVING**
** Answer:**
| **WHERE** | **HAVING** |
|-----------|------------|
| Filters **rows** before grouping | Filters **groups** after grouping |
| Applied **before** GROUP BY | Applied **after** GROUP BY |
| **Cannot** use aggregate functions | **Can** use aggregate functions |
| Operates on **individual rows** | Operates on **aggregated results** |
| Can be used **without** GROUP BY | Usually used **with** GROUP BY |
| Faster (filters early) | Slower (processes groups first) |
| Syntax: `WHERE condition` | Syntax: `HAVING condition` |

**Examples Comparison**:
```sql
-- WHERE: Filter individual employees earning > 50000
SELECT department, AVG(salary)
FROM Employees
WHERE salary > 50000          -- Filters rows first
GROUP BY department;

-- HAVING: Filter departments with avg salary > 50000
SELECT department, AVG(salary)
FROM Employees
GROUP BY department
HAVING AVG(salary) > 50000;   -- Filters groups after
```

**Combined Usage**:
```sql
-- Both WHERE and HAVING
SELECT department, AVG(salary) as avg_sal
FROM Employees
WHERE hire_date > '2020-01-01'   -- Filter rows (recent hires)
GROUP BY department
HAVING AVG(salary) > 50000       -- Filter groups (high avg salary)
ORDER BY avg_sal DESC;
```

**Execution Order**:
1. FROM (get tables)
2. WHERE (filter rows)
3. GROUP BY (group rows)
4. HAVING (filter groups)
5. SELECT (choose columns)
6. ORDER BY (sort results)

**Theoretical Keywords**: Row vs group filtering, Pre-grouping vs post-grouping, Aggregate function usage, Query execution order

### **35. What is DISTINCT?**
** Answer:**
- **DISTINCT**: Eliminates duplicate rows from result set
- **Applied to** entire SELECT list (all columns combined)
- **Can be used** with aggregate functions

**Examples**:
```sql
-- 1. Basic DISTINCT
SELECT DISTINCT department FROM Employees;

-- 2. DISTINCT on multiple columns
SELECT DISTINCT department, job_title 
FROM Employees;

-- 3. DISTINCT with COUNT
SELECT COUNT(DISTINCT department) as unique_depts
FROM Employees;

-- 4. DISTINCT vs GROUP BY similarity
-- These are similar:
SELECT DISTINCT department FROM Employees;
SELECT department FROM Employees GROUP BY department;

-- 5. DISTINCT with expressions
SELECT DISTINCT UPPER(name) FROM Employees;
```

**DISTINCT vs ALL**:
```sql
SELECT ALL department FROM Employees;    -- Returns all (default)
SELECT DISTINCT department FROM Employees; -- Returns unique
```

**Performance Considerations**:
- DISTINCT requires sorting/grouping (can be expensive)
- Use judiciously with large datasets
- Consider GROUP BY for more control

**Common Mistakes**:
```sql
-- WRONG: DISTINCT applies to entire row, not just first column
SELECT DISTINCT department, name 
FROM Employees;  -- Returns unique department+name combos

-- To get unique departments with names, use:
SELECT department, MIN(name) 
FROM Employees 
GROUP BY department;
```

**Theoretical Keywords**: Duplicate removal, Unique values, Result set reduction, COUNT DISTINCT, Performance impact

---

## **SQL Execution Order Summary**
```sql
-- Written order
SELECT column1, COUNT(*) 
FROM table
WHERE condition
GROUP BY column1
HAVING condition
ORDER BY column1
LIMIT 10;

-- Actual execution order
1. FROM table                 (Get data source)
2. WHERE condition            (Filter rows)
3. GROUP BY column1           (Group rows)
4. HAVING condition           (Filter groups)
5. SELECT column1, COUNT(*)   (Select columns)
6. ORDER BY column1           (Sort results)
7. LIMIT 10                   (Limit rows)
```

**You now have comprehensive knowledge of SQL Basics!** These concepts form the foundation for all SQL queries and are essential for database-related . 🗄️🚀