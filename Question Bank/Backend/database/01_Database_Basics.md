# **DATABASE BASICS ANSWERS**

## **Database Basics (Questions 1-20)**

### **1. What is a database?**
** Answer:**
- A **database** is an organized collection of structured data stored electronically
- Designed for efficient data storage, retrieval, and management
- Can be relational (tables with rows/columns) or non-relational
- Examples: Customer records, product inventory, financial transactions

**Characteristics**:
  - **Persistent Storage**: Data remains after program ends
  - **Multi-user Access**: Concurrent access by multiple users
  - **Data Integrity**: Maintains accuracy and consistency
  - **Security**: Access control and protection
  - **Backup & Recovery**: Data protection mechanisms

**Real-world Examples**: 
  - MySQL database for e-commerce website
  - MongoDB for storing JSON documents
  - Oracle Database for enterprise applications

**Theoretical Keywords**: Data collection, Organized storage, Electronic storage, Data persistence, Structured information

### **2. Difference between database and DBMS**
** Answer:**
| **Database** | **DBMS (Database Management System)** |
|--------------|---------------------------------------|
| **What it is**: Collection of data | **What it is**: Software to manage databases |
| **Contains**: Actual data | **Contains**: Tools to create, access, manage databases |
| **Analogy**: File cabinet with files | **Analogy**: File clerk who organizes cabinet |
| **Examples**: MySQL database, Oracle database | **Examples**: MySQL software, Oracle DBMS, PostgreSQL |
| **Purpose**: Store data | **Purpose**: Manage, manipulate, secure data |
| **Without DBMS**: Just raw data files | **Without Database**: Empty management system |

**Relationship**: DBMS creates and manages databases
```
DBMS (Software)
    ↓ creates/manages
Database (Data Storage)
    ↓ contains
Tables with data
```

**Examples**:
- **Database**: `company_data` containing employee and department tables
- **DBMS**: MySQL software used to create, query, and maintain `company_data`

**Theoretical Keywords**: Data vs management software, Storage vs management, DBMS functions, Data manipulation tools

### **3. What is RDBMS?**
** Answer:**
- **RDBMS (Relational Database Management System)**: DBMS based on relational model
- **Organizes data** into tables (relations) with rows and columns
- **Follows ACID properties**: Atomicity, Consistency, Isolation, Durability
- **Uses SQL** (Structured Query Language) for operations

**Key Features**:
1. **Tables**: Data organized in rows (records) and columns (attributes)
2. **Relationships**: Tables connected via keys (primary/foreign keys)
3. **Normalization**: Eliminates data redundancy
4. **ACID Compliance**: Ensures reliable transactions
5. **SQL**: Standard language for querying

**Examples**: MySQL, PostgreSQL, Oracle Database, SQL Server, SQLite

**RDBMS Example**:
```sql
-- Tables in RDBMS
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    amount DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);
```

**Theoretical Keywords**: Relational model, Tables and relationships, ACID properties, SQL, Normalization

### **4. Difference between SQL and NoSQL**
** Answer:**
| **SQL Databases** | **NoSQL Databases** |
|-------------------|---------------------|
| **Structure**: Tabular (rows/columns) | **Structure**: Document, key-value, graph, column-family |
| **Schema**: Fixed, predefined | **Schema**: Dynamic, flexible |
| **Scaling**: Vertical (scale-up) | **Scaling**: Horizontal (scale-out) |
| **ACID**: Fully compliant | **ACID**: Often BASE (Basically Available, Soft state, Eventually consistent) |
| **Best for**: Structured data, complex queries | **Best for**: Unstructured data, high scalability |
| **Examples**: MySQL, PostgreSQL, Oracle | **Examples**: MongoDB, Cassandra, Redis, Neo4j |
| **Query Language**: SQL | **Query Language**: Varies (API, custom query) |

**When to use SQL**:
- Financial transactions (need ACID)
- Complex queries with joins
- Structured data with clear schema
- Data integrity is critical

**When to use NoSQL**:
- Rapid development (schema changes often)
- Large-scale applications (social media, IoT)
- Unstructured/semi-structured data
- High write/read throughput needed

**Examples**:
```sql
-- SQL: Structured query with JOIN
SELECT c.name, o.amount 
FROM Customers c
JOIN Orders o ON c.id = o.customer_id;

-- NoSQL (MongoDB): Document query
db.orders.find(
    { "customer.name": "John" },
    { "amount": 1, "items": 1 }
)
```

**Theoretical Keywords**: Relational vs non-relational, Schema rigidity, Scaling approach, ACID vs BASE, Data structure

### **5. What is a table?**
** Answer:**
- A **table** is a collection of related data organized in rows and columns
- **Basic building block** of relational databases
- **Analogous to** spreadsheet sheet or Excel worksheet
- Each table has a **unique name** within database

**Table Structure**:
```
┌─────────────────────────────────┐
│         Employees Table         │
├─────┬───────────┬──────────────┤
│ ID  │ Name      │ Department   │ ← Columns/Attributes
├─────┼───────────┼──────────────┤
│ 101 │ John      │ IT           │ ← Rows/Tuples/Records
├─────┼───────────┼──────────────┤
│ 102 │ Sarah     │ HR           │
├─────┼───────────┼──────────────┤
│ 103 │ Mike      │ Finance      │
└─────┴───────────┴──────────────┘
```

**SQL Example**:
```sql
-- Create a table
CREATE TABLE Employees (
    employee_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    department VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE
);

-- Insert data into table
INSERT INTO Employees VALUES 
(101, 'John', 'Doe', 'IT', 75000, '2020-01-15'),
(102, 'Sarah', 'Smith', 'HR', 65000, '2019-03-20');
```

**Table Characteristics**:
- **Columns**: Define data types and constraints
- **Rows**: Individual records/entries
- **Primary Key**: Uniquely identifies each row
- **Foreign Keys**: Link to other tables

**Theoretical Keywords**: Relation, Rows and columns, Data organization, Database entity, Tuple collection

### **6. What is a row and column?**
** Answer:**
| **Column (Attribute/Field)** | **Row (Tuple/Record)** |
|------------------------------|------------------------|
| **Definition**: Vertical entity representing a data category | **Definition**: Horizontal entity representing a single data entry |
| **Contains**: Same type of data for all rows | **Contains**: Related data across multiple columns |
| **Has**: Name and data type (INT, VARCHAR, DATE) | **Has**: Values for each column |
| **Example**: `employee_id`, `name`, `salary` | **Example**: Complete data for one employee |
| **Fixed**: Number of columns defined at table creation | **Variable**: Number of rows can increase/decrease |
| **Also called**: Field, attribute | **Also called**: Record, tuple |

**Visual Example**:
```
Columns:  ┌─────────┬──────────┬────────────┐
          │ ID      │ Name     │ Department │  ← Column Headers
Rows:     ├─────────┼──────────┼────────────┤
          │ 101     │ John     │ IT         │  ← Row 1 (Record 1)
          ├─────────┼──────────┼────────────┤
          │ 102     │ Sarah    │ HR         │  ← Row 2 (Record 2)
          ├─────────┼──────────┼────────────┤
          │ 103     │ Mike     │ Finance    │  ← Row 3 (Record 3)
          └─────────┴──────────┴────────────┘
```

**SQL Example**:
```sql
-- Columns defined in CREATE TABLE
CREATE TABLE Students (
    student_id INT,        -- Column 1: Integer type
    name VARCHAR(100),     -- Column 2: String type
    age INT,               -- Column 3: Integer type
    grade CHAR(1)          -- Column 4: Single character
);

-- Rows inserted with INSERT
INSERT INTO Students VALUES (1, 'Alice', 20, 'A');  -- Row 1
INSERT INTO Students VALUES (2, 'Bob', 21, 'B');    -- Row 2
INSERT INTO Students VALUES (3, 'Charlie', 19, 'A'); -- Row 3
```

**Theoretical Keywords**: Tuple vs attribute, Record vs field, Horizontal vs vertical, Data organization

### **7. What is a primary key?**
** Answer:**
- A **primary key** is a column (or set of columns) that uniquely identifies each row in a table
- **Must be unique**: No two rows can have same primary key value
- **Cannot be NULL**: Primary key columns cannot contain NULL values
- **Only one per table**: A table can have only one primary key

**Characteristics**:
1. **Uniqueness**: Each value must be unique
2. **Non-nullability**: Cannot contain NULL values
3. **Permanence**: Should not change over time
4. **Minimal**: Should use minimum columns needed

**Examples**:
```sql
-- Single column primary key
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,  -- Primary key
    name VARCHAR(100),
    email VARCHAR(100)
);

-- Composite primary key (multiple columns)
CREATE TABLE OrderItems (
    order_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (order_id, product_id)  -- Composite PK
);

-- Adding primary key later
ALTER TABLE Employees 
ADD PRIMARY KEY (employee_id);
```

**Common Primary Key Types**:
- **Natural Key**: Based on existing data (SSN, email)
- **Surrogate Key**: Artificially created (auto-increment ID)
- **Composite Key**: Multiple columns together

**Theoretical Keywords**: Unique identifier, Row identification, NOT NULL constraint, Entity integrity

### **8. Why is a primary key important?**
** Answer:**
**Primary keys are crucial for**:

1. **Uniquely Identify Rows**: Ensures each row can be specifically referenced
   ```sql
   SELECT * FROM Customers WHERE customer_id = 101; -- Exactly one row
   ```

2. **Enforce Entity Integrity**: Prevents duplicate rows
   ```sql
   -- This would fail if customer_id 101 already exists
   INSERT INTO Customers (customer_id, name) VALUES (101, 'John');
   ```

3. **Establish Relationships**: Foundation for foreign keys
   ```sql
   -- Orders table references Customers primary key
   CREATE TABLE Orders (
       order_id INT PRIMARY KEY,
       customer_id INT,
       FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
   );
   ```

4. **Improve Performance**: Used for indexing (faster searches)
   ```sql
   -- Primary key creates clustered index (in most DBMS)
   -- Fast lookup: WHERE customer_id = 101
   ```

5. **Data Integrity**: Ensures accurate data retrieval and updates
   ```sql
   UPDATE Customers SET name = 'John Updated' WHERE customer_id = 101;
   -- Affects exactly one specific row
   ```

6. **Avoid Data Anomalies**: Prevents insertion/update/deletion problems

**Without Primary Key Issues**:
- Duplicate records could exist
- Cannot reliably reference specific rows
- Foreign key relationships impossible
- Poor query performance

**Theoretical Keywords**: Row identification, Referential integrity, Indexing, Data uniqueness, Relationship establishment

### **9. Can a table have multiple primary keys?**
** Answer:**
- **NO**, a table can have only **one primary key**
- **BUT** a primary key can consist of **multiple columns** (composite primary key)
- This is a common point of confusion

**Correct Understanding**:
```sql
-- WRONG: Multiple PRIMARY KEY constraints (invalid)
CREATE TABLE WrongExample (
    id1 INT PRIMARY KEY,
    id2 INT PRIMARY KEY  -- ERROR: Multiple primary keys
);

-- CORRECT: Single composite primary key
CREATE TABLE CorrectExample (
    order_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (order_id, product_id)  -- One PK with two columns
);

-- ALSO CORRECT: Single column primary key
CREATE TABLE SimpleExample (
    id INT PRIMARY KEY,  -- One PK, one column
    name VARCHAR(100)
);
```

**Composite Primary Key Example**:
```sql
-- Student_Courses table: Student can take multiple courses
CREATE TABLE Student_Courses (
    student_id INT,
    course_id INT,
    enrollment_date DATE,
    grade CHAR(1),
    PRIMARY KEY (student_id, course_id)  -- Composite PK
);

-- Valid rows (combination must be unique)
INSERT INTO Student_Courses VALUES (101, 'CS101', '2024-01-15', 'A');
INSERT INTO Student_Courses VALUES (101, 'MATH201', '2024-01-15', 'B');  -- OK
INSERT INTO Student_Courses VALUES (102, 'CS101', '2024-01-16', 'A');   -- OK
INSERT INTO Student_Courses VALUES (101, 'CS101', '2024-01-17', 'C');   -- ERROR: Duplicate PK
```

**Alternative to Multiple Keys**:
- Use **one primary key** (surrogate or natural)
- Add **unique constraints** on other columns
  ```sql
  CREATE TABLE Users (
      user_id INT PRIMARY KEY,        -- Primary key
      email VARCHAR(100) UNIQUE,      -- Unique constraint (not PK)
      username VARCHAR(50) UNIQUE,    -- Another unique constraint
      password VARCHAR(100)
  );
  ```

**Theoretical Keywords**: Single PK constraint, Composite keys, Unique constraints, Key cardinality

### **10. What is a foreign key?**
** Answer:**
- A **foreign key** is a column (or set of columns) in one table that references the primary key in another table
- **Creates relationship** between two tables (parent-child)
- **Enforces referential integrity**: Ensures values exist in referenced table

**Characteristics**:
1. **References Primary Key**: Points to PK in another table
2. **Can be NULL**: Unless specified as NOT NULL
3. **Values must match**: Must exist in referenced table's PK
4. **Multiple allowed**: Table can have multiple foreign keys

**Example**:
```sql
-- Parent table
CREATE TABLE Departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

-- Child table with foreign key
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,  -- Foreign key column
    FOREIGN KEY (dept_id) REFERENCES Departments(dept_id)
    -- Ensures dept_id exists in Departments table
);
```

**Types of Relationships**:
```sql
-- 1. One-to-Many (Most common)
-- One department has many employees
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY,
    dept_id INT REFERENCES Departments(dept_id)
);

-- 2. Self-referencing foreign key
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY,
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES Employees(emp_id)
);

-- 3. Composite foreign key
CREATE TABLE OrderItems (
    order_id INT,
    product_id INT,
    PRIMARY KEY (order_id, product_id)
);

CREATE TABLE Shipments (
    shipment_id INT PRIMARY KEY,
    order_id INT,
    product_id INT,
    FOREIGN KEY (order_id, product_id) 
        REFERENCES OrderItems(order_id, product_id)
);
```

**Theoretical Keywords**: Referential integrity, Table relationships, Parent-child tables, Cross-table references

### **11. Why is a foreign key used?**
** Answer:**
**Foreign keys are essential for**:

1. **Maintain Referential Integrity**: Ensures relationships remain valid
   ```sql
   -- This will FAIL if dept_id 999 doesn't exist in Departments
   INSERT INTO Employees (emp_id, name, dept_id) 
   VALUES (101, 'John', 999);
   ```

2. **Establish Relationships**: Links tables logically
   ```sql
   -- Query using foreign key relationship
   SELECT e.name, d.dept_name
   FROM Employees e
   JOIN Departments d ON e.dept_id = d.dept_id;
   ```

3. **Prevent Orphan Records**: Cannot delete parent if children exist
   ```sql
   -- This will FAIL if employees exist in department 101
   DELETE FROM Departments WHERE dept_id = 101;
   ```

4. **Cascade Operations**: Automate updates/deletes
   ```sql
   CREATE TABLE Employees (
       emp_id INT PRIMARY KEY,
       dept_id INT,
       FOREIGN KEY (dept_id) REFERENCES Departments(dept_id)
           ON DELETE CASCADE  -- Delete employees when department deleted
           ON UPDATE CASCADE  -- Update dept_id in employees when changed
   );
   ```

5. **Document Database Structure**: Shows intended relationships
   ```sql
   -- Foreign key clearly shows relationship intent
   FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
   ```

6. **Improve Query Optimization**: Helps query planner understand joins

**Real-world Example**:
```sql
-- Without foreign key (problematic)
INSERT INTO Orders (order_id, customer_id) VALUES (1001, 999);
-- Customer 999 might not exist - creates orphan order

-- With foreign key (protected)
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);
-- INSERT fails if customer 999 doesn't exist
```

**Referential Actions**:
- `ON DELETE CASCADE`: Delete child records when parent deleted
- `ON DELETE SET NULL`: Set foreign key to NULL when parent deleted
- `ON DELETE SET DEFAULT`: Set to default value
- `ON DELETE RESTRICT`/`NO ACTION`: Prevent deletion (default)
- `ON UPDATE CASCADE`: Update foreign key when parent key changes

**Theoretical Keywords**: Data integrity, Relationship enforcement, Orphan prevention, Cascade operations, Schema documentation

### **12. Difference between primary key and foreign key**
** Answer:**
| **Primary Key** | **Foreign Key** |
|-----------------|-----------------|
| **Purpose**: Uniquely identify each row | **Purpose**: Link to primary key in another table |
| **Uniqueness**: Must be unique | **Uniqueness**: Can have duplicate values |
| **NULL values**: Not allowed | **NULL values**: Allowed (unless NOT NULL specified) |
| **Number per table**: Only one (can be composite) | **Number per table**: Multiple foreign keys allowed |
| **Creates**: Clustered index (usually) | **Creates**: Non-clustered index (usually) |
| **Table relationship**: Parent table | **Table relationship**: Child table |
| **Value existence**: Defines new values | **Value existence**: Must exist in referenced table |
| **Example**: `customer_id` in Customers | **Example**: `customer_id` in Orders |

**Visual Comparison**:
```sql
-- Parent table: Defines primary key
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,  -- Primary Key
    name VARCHAR(100)
);

-- Child table: References with foreign key
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,  -- Foreign Key
    amount DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);
```

**Key Differences**:

1. **Uniqueness**:
   ```sql
   -- Primary Key: Must be unique
   INSERT INTO Customers VALUES (101, 'John');  -- OK
   INSERT INTO Customers VALUES (101, 'Sarah'); -- ERROR: Duplicate PK
   
   -- Foreign Key: Can repeat
   INSERT INTO Orders VALUES (1, 101, 100.00);  -- OK
   INSERT INTO Orders VALUES (2, 101, 200.00);  -- OK: Same customer
   ```

2. **NULL Values**:
   ```sql
   -- Primary Key: Cannot be NULL
   INSERT INTO Customers VALUES (NULL, 'John');  -- ERROR
   
   -- Foreign Key: Can be NULL (unless NOT NULL)
   INSERT INTO Orders VALUES (3, NULL, 150.00);  -- OK: Orphan order
   ```

3. **Number Allowed**:
   ```sql
   -- Only one PRIMARY KEY per table
   CREATE TABLE Table1 (
       id1 INT PRIMARY KEY,
       id2 INT PRIMARY KEY  -- ERROR: Multiple PKs
   );
   
   -- Multiple FOREIGN KEYS allowed
   CREATE TABLE Orders (
       order_id INT PRIMARY KEY,
       customer_id INT REFERENCES Customers(customer_id),
       product_id INT REFERENCES Products(product_id)  -- Second FK
   );
   ```

**Theoretical Keywords**: Uniqueness constraint, Referential constraint, Parent-child relationship, Index types, NULL allowance

### **13. What is a candidate key?**
** Answer:**
- A **candidate key** is a column (or set of columns) that could be chosen as the primary key
- **Properties**: Must be unique, NOT NULL, and minimal (irreducible)
- **Multiple per table**: A table can have multiple candidate keys
- **One chosen**: One candidate key becomes primary key, others become alternate keys

**Characteristics**:
1. **Uniqueness**: Each value must be unique
2. **Non-nullability**: Cannot contain NULL values
3. **Irreducible**: Cannot remove any column and still maintain uniqueness
4. **Stability**: Should not change frequently

**Example**:
```sql
CREATE TABLE Students (
    student_id INT UNIQUE NOT NULL,     -- Candidate Key 1
    email VARCHAR(100) UNIQUE NOT NULL, -- Candidate Key 2
    ssn CHAR(9) UNIQUE NOT NULL,        -- Candidate Key 3
    name VARCHAR(100),
    PRIMARY KEY (student_id)  -- One chosen as PK
    -- email and ssn are alternate keys
);
```

**Identifying Candidate Keys**:
```sql
-- Employee table potential candidate keys:
CREATE TABLE Employees (
    emp_id INT,           -- Candidate: Unique employee ID
    email VARCHAR(100),   -- Candidate: Unique company email
    ssn CHAR(9),          -- Candidate: Unique SSN
    badge_no INT,         -- Candidate: Unique badge number
    name VARCHAR(100),
    hire_date DATE
);

-- After analysis:
-- Chosen PK: emp_id (surrogate, stable)
-- Alternate keys: email, ssn, badge_no
```

**Minimality Test**:
```sql
-- Composite candidate key example
CREATE TABLE Enrollments (
    student_id INT,
    course_id INT,
    semester VARCHAR(10),
    grade CHAR(1),
    -- Potential candidate keys:
    -- 1. (student_id, course_id, semester) - Composite
    -- 2. enrollment_id (if added) - Single column
    -- Minimal test: Cannot remove any column from composite
);
```

**Theoretical Keywords**: Potential primary keys, Alternate keys, Uniqueness constraint, Minimal superkey, Key selection

### **14. What is a composite key?**
** Answer:**
- A **composite key** is a primary key made up of two or more columns
- **Also called**: Compound key or concatenated key
- **Used when**: No single column uniquely identifies rows
- **All columns combined** must be unique (individual columns can have duplicates)

**Characteristics**:
1. **Multi-column**: Consists of 2+ columns
2. **Combined uniqueness**: Columns together must be unique
3. **Individual duplicates**: Single columns can repeat
4. **Order matters**: Column order is significant

**Example**:
```sql
-- Enrollment table: Student can take same course in different semesters
CREATE TABLE Enrollments (
    student_id INT,
    course_id INT,
    semester VARCHAR(10),
    grade CHAR(1),
    PRIMARY KEY (student_id, course_id, semester)  -- Composite key
);

-- Valid data (individual columns can repeat):
INSERT INTO Enrollments VALUES (101, 'CS101', 'Fall2023', 'A');
INSERT INTO Enrollments VALUES (101, 'CS101', 'Spring2024', 'B');  -- OK
INSERT INTO Enrollments VALUES (101, 'MATH201', 'Fall2023', 'A');  -- OK
INSERT INTO Enrollments VALUES (102, 'CS101', 'Fall2023', 'B');    -- OK
INSERT INTO Enrollments VALUES (101, 'CS101', 'Fall2023', 'C');    -- ERROR: Duplicate PK
```

**When to Use Composite Keys**:
1. **Many-to-Many Relationships**:
   ```sql
   -- Student_Courses bridge table
   CREATE TABLE Student_Courses (
       student_id INT,
       course_id INT,
       PRIMARY KEY (student_id, course_id),
       FOREIGN KEY (student_id) REFERENCES Students(id),
       FOREIGN KEY (course_id) REFERENCES Courses(id)
   );
   ```

2. **Historical/Time-based Data**:
   ```sql
   -- Stock prices: Company + Date combination
   CREATE TABLE StockPrices (
       symbol VARCHAR(10),
       trade_date DATE,
       price DECIMAL(10,2),
       PRIMARY KEY (symbol, trade_date)
   );
   ```

3. **Composite Foreign Keys**:
   ```sql
   CREATE TABLE OrderItems (
       order_id INT,
       product_id INT,
       PRIMARY KEY (order_id, product_id)
   );
   
   CREATE TABLE Shipments (
       shipment_id INT PRIMARY KEY,
       order_id INT,
       product_id INT,
       FOREIGN KEY (order_id, product_id) 
           REFERENCES OrderItems(order_id, product_id)
   );
   ```

**Pros and Cons**:
- **Pros**: Natural representation, avoids surrogate keys
- **Cons**: Larger indexes, more complex queries, foreign keys more complicated

**Theoretical Keywords**: Multi-column primary key, Compound key, Combined uniqueness, Bridge table keys

### **15. What is a unique key?**
** Answer:**
- A **unique key** ensures all values in a column (or set of columns) are different
- **Similar to primary key** but allows NULL values (unless NOT NULL specified)
- **Multiple allowed**: Table can have multiple unique keys
- **Creates unique index** for faster lookups

**Characteristics**:
1. **Uniqueness**: All non-NULL values must be unique
2. **NULL allowance**: Can have multiple NULLs (depends on DBMS)
3. **Multiple constraints**: Many unique keys per table
4. **Not necessarily primary**: Can exist alongside primary key

**Examples**:
```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,        -- Primary key
    username VARCHAR(50) UNIQUE,    -- Unique key 1
    email VARCHAR(100) UNIQUE,      -- Unique key 2
    phone VARCHAR(15) UNIQUE,       -- Unique key 3
    ssn CHAR(9) UNIQUE NOT NULL     -- Unique key with NOT NULL
);

-- Alternative syntax
CREATE TABLE Products (
    product_id INT PRIMARY KEY,
    sku VARCHAR(20),
    upc VARCHAR(12),
    CONSTRAINT uk_sku UNIQUE (sku),
    CONSTRAINT uk_upc UNIQUE (upc)
);
```

**NULL Handling Variations**:
```sql
-- Different DBMS handle NULLs differently in unique constraints
-- SQL Server: Allows multiple NULLs in unique column
-- Oracle: Allows multiple NULLs
-- MySQL: Allows multiple NULLs (unless unique index)
-- PostgreSQL: Allows multiple NULLs

INSERT INTO Users (user_id, username, email) 
VALUES (1, 'john', NULL);  -- OK

INSERT INTO Users (user_id, username, email) 
VALUES (2, 'jane', NULL);  -- Usually OK (multiple NULLs allowed)
```

**Unique Key vs Unique Index**:
```sql
-- Unique key (constraint)
ALTER TABLE Employees ADD CONSTRAINT uk_email UNIQUE (email);

-- Unique index (performance + uniqueness)
CREATE UNIQUE INDEX idx_email ON Employees(email);
-- Often equivalent in function
```

**Use Cases**:
1. **Alternative Identifiers**: Email, username, SSN
2. **Business Constraints**: Product SKU, ISBN
3. **Prevent Duplicates**: Ensure no duplicate email registrations
4. **Foreign Key Candidates**: Columns referenced by other tables

**Theoretical Keywords**: Uniqueness constraint, Alternate keys, NULL handling, Multiple constraints, Business rules

### **16. Difference between unique key and primary key**
** Answer:**
| **Unique Key** | **Primary Key** |
|----------------|-----------------|
| **Number allowed**: Multiple per table | **Number allowed**: Only one per table |
| **NULL values**: Allowed (multiple NULLs usually) | **NULL values**: NOT allowed |
| **Purpose**: Ensure uniqueness of non-NULL values | **Purpose**: Uniquely identify each row |
| **Clustered index**: Usually non-clustered | **Clustered index**: Usually clustered (default) |
| **Relationship**: Cannot create foreign key to unique key (unless specified) | **Relationship**: Natural target for foreign keys |
| **Optional**: Can exist without primary key | **Mandatory**: Every table should have one |
| **Data integrity**: Prevents duplicates | **Data integrity**: Ensures entity integrity |

**Examples**:
```sql
CREATE TABLE Students (
    student_id INT PRIMARY KEY,      -- Primary Key: One, no NULLs
    email VARCHAR(100) UNIQUE,       -- Unique Key: Multiple allowed
    ssn CHAR(9) UNIQUE NOT NULL,     -- Unique Key with NOT NULL
    phone VARCHAR(15) UNIQUE         -- Another Unique Key
);

-- Valid operations:
INSERT INTO Students VALUES (1, 'a@b.com', '123456789', NULL);  -- OK
INSERT INTO Students VALUES (2, NULL, '987654321', NULL);       -- OK
INSERT INTO Students VALUES (3, 'a@b.com', '111111111', NULL);  -- ERROR: Duplicate email
INSERT INTO Students VALUES (NULL, 'c@d.com', '222222222', NULL); -- ERROR: PK NULL
```

**Key Differences Explained**:

1. **NULL Handling**:
   ```sql
   -- Primary Key: Rejects NULL
   INSERT INTO Table1 (pk_col) VALUES (NULL);  -- ERROR
   
   -- Unique Key: Accepts NULL (usually)
   INSERT INTO Table1 (unique_col) VALUES (NULL);  -- OK
   INSERT INTO Table1 (unique_col) VALUES (NULL);  -- Usually OK again
   ```

2. **Foreign Key Relationships**:
   ```sql
   -- Primary Key: Natural foreign key reference
   CREATE TABLE Orders (
       order_id INT PRIMARY KEY,
       customer_id INT REFERENCES Customers(customer_id)  -- PK reference
   );
   
   -- Unique Key: Can be referenced (with explicit syntax)
   CREATE TABLE Orders (
       order_id INT PRIMARY KEY,
       customer_email VARCHAR(100),
       FOREIGN KEY (customer_email) REFERENCES Users(email)  -- Unique key reference
   );
   ```

3. **Performance**: Primary keys usually create clustered indexes (physically orders data), unique keys create non-clustered indexes

**Choosing Between Them**:
- Use **Primary Key** for: Main row identifier, foreign key references
- Use **Unique Key** for: Alternate identifiers, business rule enforcement

**Theoretical Keywords**: Uniqueness enforcement, NULL allowance, Index types, Foreign key targets, Constraint multiplicity

### **17. What is NULL?**
** Answer:**
- **NULL** represents the absence of a value or unknown value in database
- **Not the same as** zero or empty string
- **Special marker** indicating "value not known" or "not applicable"
- **Three-valued logic**: TRUE, FALSE, UNKNOWN (NULL comparisons)

**Characteristics**:
1. **Not a value**: Represents lack of value
2. **Not equal to anything**: `NULL = NULL` is UNKNOWN (not TRUE)
3. **Special handling**: Requires IS NULL/IS NOT NULL operators
4. **Propagates in operations**: `NULL + 5 = NULL`

**Examples**:
```sql
-- NULL vs empty string vs zero
INSERT INTO Test VALUES (NULL, '', 0);
-- NULL: Unknown/not applicable
-- '': Empty but known string
-- 0: Known numeric value

-- NULL comparisons
SELECT * FROM Employees WHERE manager_id = NULL;  -- Returns NO rows
SELECT * FROM Employees WHERE manager_id IS NULL; -- Correct way

-- NULL in expressions
SELECT salary + bonus FROM Employees;
-- If bonus is NULL, result is NULL
```

**Three-Valued Logic**:
```sql
-- Comparisons with NULL return UNKNOWN
NULL = 5        -- UNKNOWN
NULL = NULL     -- UNKNOWN (not TRUE!)
NULL != NULL    -- UNKNOWN
5 > NULL        -- UNKNOWN
NULL IS NULL    -- TRUE (only IS NULL works)
```

**Functions Handling NULL**:
```sql
-- COALESCE: First non-NULL value
SELECT COALESCE(bonus, 0) FROM Employees;  -- 0 if bonus is NULL

-- NULLIF: Returns NULL if equal
SELECT NULLIF(salary, 0) FROM Employees;   -- NULL if salary = 0

-- NVL (Oracle)/ISNULL (SQL Server): Similar to COALESCE
SELECT NVL(bonus, 0) FROM Employees;       -- Oracle
SELECT ISNULL(bonus, 0) FROM Employees;    -- SQL Server
```

**Best Practices**:
1. **Use sparingly**: NULLs complicate queries and logic
2. **Consider alternatives**: Default values, separate flag columns
3. **Handle explicitly**: Always consider NULL in WHERE clauses
4. **Document**: Explain what NULL means in each column

**Theoretical Keywords**: Unknown value, Absence of data, Three-valued logic, Special marker, NULL propagation

### **18. Can a primary key contain NULL?**
** Answer:**
- **NO**, a primary key cannot contain NULL values
- **Fundamental rule**: Primary keys must have NOT NULL constraint
- **Violation**: Attempting to insert NULL into primary key causes error
- **Database enforcement**: DBMS automatically enforces this rule

**Why Primary Keys Cannot Be NULL**:
1. **Identification Purpose**: Cannot identify row with unknown/absent key
2. **Referential Integrity**: Foreign keys reference primary keys - NULL reference meaningless
3. **Index Requirements**: Primary keys create indexes that require values
4. **Entity Integrity**: Ensures each entity (row) is properly identifiable

**Examples**:
```sql
-- This will fail: PK cannot be NULL
CREATE TABLE Test (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

INSERT INTO Test VALUES (NULL, 'John');  -- ERROR: Cannot insert NULL into PK

-- Even with explicit NULL constraint removal attempt
CREATE TABLE Test2 (
    id INT NULL PRIMARY KEY  -- Still won't allow NULL in PK
);
-- Most DBMS will ignore NULL and make column NOT NULL for PK
```

**Composite Primary Keys and NULL**:
```sql
-- Composite primary key: ALL columns cannot be NULL
CREATE TABLE Enrollment (
    student_id INT,
    course_id INT,
    semester VARCHAR(10),
    PRIMARY KEY (student_id, course_id, semester)
);

-- These all fail:
INSERT INTO Enrollment VALUES (NULL, 'CS101', 'Fall2024');  -- ERROR
INSERT INTO Enrollment VALUES (101, NULL, 'Fall2024');      -- ERROR  
INSERT INTO Enrollment VALUES (101, 'CS101', NULL);         -- ERROR
```

**Difference from Unique Constraint**:
```sql
CREATE TABLE Example (
    id INT PRIMARY KEY,      -- Cannot be NULL
    email VARCHAR(100) UNIQUE  -- Can be NULL (usually)
);

INSERT INTO Example VALUES (1, NULL);  -- OK (email NULL)
INSERT INTO Example VALUES (NULL, 'a@b.com');  -- ERROR (id NULL)
```

**Theoretical Keywords**: NOT NULL constraint, Entity integrity, Row identification, Index requirement, Database enforcement

### **19. What is schema?**
** Answer:**
- A **schema** is a logical container/namespace that organizes database objects
- **Contains**: Tables, views, indexes, procedures, functions
- **Purpose**: Organize, secure, and manage database objects
- **Analogy**: Like a folder in operating system

**Types of Schema**:
1. **Logical Schema**: Structure design (tables, columns, relationships)
2. **Physical Schema**: Storage implementation (files, indexes, partitions)
3. **Database Schema**: Collection of all objects

**Schema in Different DBMS**:
```sql
-- MySQL: Schema = Database
CREATE DATABASE company_db;  -- Creates schema
USE company_db;              -- Switch to schema

-- PostgreSQL/Oracle/SQL Server: Schema within database
CREATE SCHEMA hr_schema;     -- Create schema
CREATE TABLE hr_schema.employees (...);  -- Qualified name

-- SQL Server: Default schema is dbo
SELECT * FROM dbo.Employees;  -- dbo is default schema
```

**Schema Operations**:
```sql
-- Create schema
CREATE SCHEMA sales;

-- Create table in specific schema
CREATE TABLE sales.orders (
    order_id INT PRIMARY KEY,
    amount DECIMAL(10,2)
);

-- Reference with schema qualification
SELECT * FROM sales.orders;

-- Change default schema
ALTER USER john SET DEFAULT_SCHEMA = sales;  -- SQL Server
SET search_path TO sales;  -- PostgreSQL
```

**Benefits of Using Schemas**:
1. **Organization**: Group related objects (HR, Sales, Finance schemas)
2. **Security**: Grant permissions at schema level
3. **Name isolation**: Same table names in different schemas
4. **Multi-tenancy**: Separate schemas for different clients
5. **Backup/Recovery**: Backup individual schemas

**Schema vs Database**:
- **Database**: Physical container (files on disk), highest level
- **Schema**: Logical container within database, organizes objects
- **Relationship**: Database → Multiple Schemas → Multiple Objects

**Theoretical Keywords**: Logical container, Namespace, Object organization, Security boundary, Database structure

### **20. What is index?**
** Answer:**
- An **index** is a database structure that improves data retrieval speed
- **Analogy**: Like book index or library catalog
- **Creates sorted reference** to table data for faster lookups
- **Trade-off**: Faster reads vs slower writes (index maintenance)

**How Indexes Work**:
```
Without Index:                 With Index:
Table Scan (Slow)              Index Seek (Fast)
┌─────────┐                    ┌─────────┐ Index
│ Row 1   │                    │ A → Row1│
│ Row 2   │  Sequential        │ B → Row4│
│ Row 3   │  scanning          │ C → Row2│
│ Row 4   │  all rows          │ D → Row3│
└─────────┘                    └─────────┘
```

**Types of Indexes**:
1. **Clustered Index**: Physically orders table data (one per table)
   ```sql
   -- Usually created automatically for primary key
   CREATE CLUSTERED INDEX idx_emp_id ON Employees(emp_id);
   ```

2. **Non-clustered Index**: Separate structure with pointers to data
   ```sql
   CREATE INDEX idx_email ON Employees(email);
   CREATE INDEX idx_dept_salary ON Employees(department, salary);
   ```

3. **Unique Index**: Ensures uniqueness (like unique constraint)
   ```sql
   CREATE UNIQUE INDEX uidx_ssn ON Employees(ssn);
   ```

4. **Composite Index**: Multiple columns
   ```sql
   CREATE INDEX idx_name_dept ON Employees(last_name, department);
   ```

**Creating and Using Indexes**:
```sql
-- Create index
CREATE INDEX idx_lastname ON Employees(last_name);

-- Create composite index
CREATE INDEX idx_dept_salary ON Employees(department, salary);

-- Check if index is used
EXPLAIN SELECT * FROM Employees WHERE last_name = 'Smith';

-- Drop index
DROP INDEX idx_lastname ON Employees;
```

**When to Index**:
1. **Frequently searched columns**: WHERE clause columns
2. **Join columns**: FOREIGN KEY columns
3. **Sorted/grouped columns**: ORDER BY, GROUP BY columns
4. **Columns with high selectivity**: Many unique values

**When NOT to Index**:
1. **Small tables**: Table scan may be faster
2. **Frequently updated columns**: Index maintenance overhead
3. **Columns with many NULLs**: Poor selectivity
4. **Columns rarely used in queries**: Wasted resources

**Index Pros and Cons**:
- **Pros**: Faster queries, efficient sorting, enforce uniqueness
- **Cons**: Slower inserts/updates/deletes, storage space, maintenance overhead

**Theoretical Keywords**: Performance optimization, Data structure, B-tree, Query optimization, Read vs write trade-off

---

## **Database Design Principles Summary**

### **Keys Summary**:
```
Keys Hierarchy:
Super Key (any unique combination)
    ↓
Candidate Key (minimal super key)
    ↓
Primary Key (chosen candidate key)
    ↓
Alternate Keys (other candidate keys)
    ↓
Foreign Keys (reference primary keys)
```

### **Important Rules**:
1. **Every table should have a primary key**
2. **Foreign keys maintain referential integrity**
3. **Index frequently queried columns**
4. **Normalize to eliminate redundancy**
5. **Use appropriate data types**
6. **Document schema and relationships**

**You now have comprehensive knowledge of Database Basics!** These concepts are fundamental for database design, SQL development, and system architecture . 🗄️🚀