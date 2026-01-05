### **Real-time Scenario Questions**

#### **115. Table has millions of records, query is slow – what will you do?**
**Interviewer Answer:**
**Systematic Approach to Query Optimization**:

**Step 1: Immediate Diagnosis**
```sql
-- 1. Check Execution Plan
EXPLAIN (ANALYZE, BUFFERS) 
SELECT * FROM large_table WHERE condition;

-- 2. Check Index Usage
SELECT indexname, indexdef 
FROM pg_indexes 
WHERE tablename = 'large_table';

-- 3. Identify Table Statistics
ANALYZE large_table;  -- Update statistics
```

**Step 2: Identify Bottlenecks**
- **Full Table Scans**: Sequential scan on millions of rows
- **Missing Indexes**: WHERE/JOIN/ORDER BY without indexes
- **Index Bloat**: Fragmented/unused indexes
- **Bad Cardinality Estimates**: Outdated statistics

**Step 3: Implement Solutions**

**A. Index Optimization**:
```sql
-- Create strategic composite indexes
CREATE INDEX idx_large_table_covering 
ON large_table(column1, column2) 
INCLUDE (column3, column4);

-- Create partial indexes for filtered queries
CREATE INDEX idx_active_records 
ON large_table(id) 
WHERE status = 'active';

-- Partition large indexes
CREATE INDEX idx_partitioned 
ON large_table(created_date) 
USING btree 
WHERE created_date > '2023-01-01';
```

**B. Query Restructuring**:
```sql
-- BEFORE: Complex correlated subquery
SELECT * FROM orders o
WHERE EXISTS (
    SELECT 1 FROM order_items oi
    WHERE oi.order_id = o.id
    AND oi.product_id = 100
);

-- AFTER: Join with aggregation
WITH relevant_items AS (
    SELECT order_id FROM order_items
    WHERE product_id = 100
    GROUP BY order_id
)
SELECT o.* FROM orders o
JOIN relevant_items ri ON o.id = ri.order_id;
```

**C. Pagination Optimization**:
```sql
-- Keyset Pagination (faster than OFFSET)
SELECT * FROM large_table
WHERE id > last_seen_id  -- Use indexed column
ORDER BY id
LIMIT 100;

-- Materialized CTE for complex pagination
WITH paginated_data AS (
    SELECT id, row_number() OVER (ORDER BY created_date) as rn
    FROM large_table
    WHERE status = 'active'
)
SELECT lt.* 
FROM large_table lt
JOIN paginated_data pd ON lt.id = pd.id
WHERE pd.rn BETWEEN 1000 AND 1100;
```

**Step 4: Architectural Changes**
1. **Partitioning**:
   ```sql
   -- Range partitioning by date
   CREATE TABLE large_table_partitioned (
       LIKE large_table INCLUDING ALL
   ) PARTITION BY RANGE (created_date);
   
   CREATE TABLE large_table_2024_q1 
   PARTITION OF large_table_partitioned
   FOR VALUES FROM ('2024-01-01') TO ('2024-04-01');
   ```

2. **Archiving Strategy**:
   - Move historical data to archive tables
   - Implement data lifecycle management
   - Use partitioned views for unified access

3. **Read Replicas**:
   - Offload read queries to replicas
   - Implement read/write separation

**Monitoring Checklist**:
- [ ] Query execution time > 100ms
- [ ] Sequential scans on large tables
- [ ] Missing index recommendations
- [ ] Index hit ratio < 95%
- [ ] Buffer cache hit ratio < 90%

**Theoretical Keywords**: Index optimization, Query execution plans, Partitioning strategies, Pagination techniques, Statistics maintenance

---

#### **116. API is slow due to DB – how will you debug?**
**Interviewer Answer:**
**Systematic Debugging Approach**:

**Step 1: Isolate the Problem**
```sql
-- 1. Identify slow API endpoints
-- Application logs: Track response times per endpoint

-- 2. Database-level monitoring
SELECT pid, query_start, state, query 
FROM pg_stat_activity 
WHERE state = 'active' 
ORDER BY query_start;

-- 3. Long-running queries
SELECT query, total_time, calls
FROM pg_stat_statements 
ORDER BY total_time DESC 
LIMIT 10;
```

**Step 2: Analyze Database Impact**
```sql
-- A. Check current load
SELECT 
    datname,
    numbackends as connections,
    xact_commit as commits,
    xact_rollback as rollbacks
FROM pg_stat_database;

-- B. Identify blocking queries
SELECT 
    blocked_locks.pid AS blocked_pid,
    blocking_locks.pid AS blocking_pid,
    blocked_activity.query AS blocked_query,
    blocking_activity.query AS blocking_query
FROM pg_catalog.pg_locks blocked_locks
JOIN pg_catalog.pg_stat_activity blocked_activity 
    ON blocked_activity.pid = blocked_locks.pid
JOIN pg_catalog.pg_locks blocking_locks 
    ON blocking_locks.locktype = blocked_locks.locktype
    AND blocking_locks.DATABASE IS NOT DISTINCT FROM blocked_locks.DATABASE
    AND blocking_locks.relation IS NOT DISTINCT FROM blocked_locks.relation
    AND blocking_locks.page IS NOT DISTINCT FROM blocked_locks.page
    AND blocking_locks.tuple IS NOT DISTINCT FROM blocked_locks.tuple
    AND blocking_locks.virtualxid IS NOT DISTINCT FROM blocked_locks.virtualxid
    AND blocking_locks.transactionid IS NOT DISTINCT FROM blocked_locks.transactionid
    AND blocking_locks.classid IS NOT DISTINCT FROM blocked_locks.classid
    AND blocking_locks.objid IS NOT DISTINCT FROM blocked_locks.objid
    AND blocking_locks.objsubid IS NOT DISTINCT FROM blocked_locks.objsubid
    AND blocking_locks.pid != blocked_locks.pid
JOIN pg_catalog.pg_stat_activity blocking_activity 
    ON blocking_activity.pid = blocking_locks.pid
WHERE NOT blocked_locks.GRANTED;
```

**Step 3: API-DB Correlation Analysis**

**Common Patterns**:
1. **N+1 Query Problem**:
   ```python
   # BAD: Makes N+1 queries
   for user in users:
       orders = db.query("SELECT * FROM orders WHERE user_id = %s", user.id)
   
   # GOOD: Single query with JOIN
   users_with_orders = db.query("""
       SELECT u.*, o.* 
       FROM users u 
       LEFT JOIN orders o ON u.id = o.user_id
   """)
   ```

2. **Inefficient Data Fetching**:
   ```sql
   -- BAD: Fetching all columns
   SELECT * FROM products WHERE category = 'electronics';
   
   -- GOOD: Fetch only needed
   SELECT id, name, price FROM products 
   WHERE category = 'electronics';
   ```

**Step 4: Implement Solutions**

**A. Connection Pooling**:
```python
# Configure connection pool
pool_settings = {
    'min_connections': 5,
    'max_connections': 20,
    'connection_timeout': 30,
    'idle_timeout': 300
}
```

**B. Caching Strategy**:
```python
# API-level caching
@cache(ttl=300)  # Cache for 5 minutes
def get_user_orders(user_id):
    return db.query("SELECT * FROM orders WHERE user_id = %s", user_id)

# Application-level caching
def get_product_details(product_id):
    cache_key = f"product:{product_id}"
    cached = redis.get(cache_key)
    if cached:
        return cached
    result = db.query_product(product_id)
    redis.setex(cache_key, 300, result)  # 5-minute cache
    return result
```

**C. Query Optimization**:
```sql
-- Add covering indexes for API queries
CREATE INDEX idx_api_user_orders 
ON orders(user_id, status, created_date) 
INCLUDE (total_amount);

-- Use prepared statements
PREPARE user_orders (INT) AS
SELECT * FROM orders 
WHERE user_id = $1 AND created_date > NOW() - INTERVAL '30 days';
```

**Debugging Checklist**:
- [ ] API response time > 200ms
- [ ] Database connection pool exhausted
- [ ] N+1 query patterns detected
- [ ] Missing indexes on API query paths
- [ ] Lock contention in concurrent requests

**Theoretical Keywords**: N+1 problem, Connection pooling, Query profiling, Caching strategies, Lock contention

---

#### **117. Data inconsistency issue – how will you fix?**
**Interviewer Answer:**
**Systematic Approach to Data Consistency**:

**Step 1: Identify Inconsistency Type**
```sql
-- 1. Identify orphan records
SELECT * FROM order_items oi
LEFT JOIN orders o ON oi.order_id = o.id
WHERE o.id IS NULL;

-- 2. Check referential integrity violations
SELECT * FROM orders
WHERE customer_id NOT IN (SELECT id FROM customers);

-- 3. Find duplicate records
SELECT email, COUNT(*) as count
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

-- 4. Detect data type mismatches
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'target_table';
```

**Step 2: Root Cause Analysis**

**Common Causes**:
1. **Missing Foreign Keys**:
   ```sql
   -- Check existing constraints
   SELECT 
       tc.table_name, 
       kcu.column_name, 
       ccu.table_name AS foreign_table_name
   FROM information_schema.table_constraints tc
   JOIN information_schema.key_column_usage kcu 
       ON tc.constraint_name = kcu.constraint_name
   JOIN information_schema.constraint_column_usage ccu 
       ON ccu.constraint_name = tc.constraint_name
   WHERE tc.constraint_type = 'FOREIGN KEY';
   ```

2. **Transaction Isolation Issues**:
   ```sql
   -- Check transaction isolation level
   SHOW default_transaction_isolation;
   
   -- Common problem: READ COMMITTED with concurrent updates
   -- Solution: Use SERIALIZABLE or optimistic locking
   ```

3. **Application Logic Bugs**:
   ```python
   # BAD: Non-atomic operations
   def transfer_funds(source, target, amount):
       withdraw(source, amount)  # Could fail here
       deposit(target, amount)   # Leaving inconsistency
   
   # GOOD: Atomic transaction
   def transfer_funds(source, target, amount):
       with transaction.atomic():  # Django example
           withdraw(source, amount)
           deposit(target, amount)
   ```

**Step 3: Implement Fixes**

**A. Immediate Correction**:
```sql
-- 1. Clean orphan records
DELETE FROM order_items 
WHERE order_id NOT IN (SELECT id FROM orders);

-- 2. Fix duplicate records (keep latest)
WITH duplicates AS (
    SELECT email, MAX(created_at) as latest
    FROM users
    GROUP BY email
    HAVING COUNT(*) > 1
)
DELETE FROM users u
USING duplicates d
WHERE u.email = d.email 
  AND u.created_at < d.latest;
```

**B. Preventive Measures**:
```sql
-- 1. Add missing constraints
ALTER TABLE order_items
ADD CONSTRAINT fk_order_items_order
FOREIGN KEY (order_id) REFERENCES orders(id)
ON DELETE CASCADE;

-- 2. Add CHECK constraints
ALTER TABLE products
ADD CONSTRAINT price_positive 
CHECK (price >= 0);

-- 3. Add UNIQUE constraints
ALTER TABLE users
ADD CONSTRAINT unique_email UNIQUE (email);
```

**C. Data Validation Scripts**:
```sql
-- Daily consistency check
CREATE OR REPLACE FUNCTION validate_data_consistency()
RETURNS TABLE (issue_type TEXT, table_name TEXT, count BIGINT)
AS $$
BEGIN
    -- Check orphan records
    RETURN QUERY
    SELECT 'orphan_records'::TEXT, 'order_items', COUNT(*)
    FROM order_items oi
    LEFT JOIN orders o ON oi.order_id = o.id
    WHERE o.id IS NULL;
    
    -- Check data type consistency
    RETURN QUERY
    SELECT 'invalid_dates'::TEXT, 'events', COUNT(*)
    FROM events
    WHERE event_date > CURRENT_DATE + INTERVAL '1 year';
END;
$$ LANGUAGE plpgsql;
```

**Step 4: Long-term Strategies**

**A. Database Triggers**:
```sql
CREATE OR REPLACE FUNCTION maintain_consistency()
RETURNS TRIGGER AS $$
BEGIN
    -- Ensure denormalized data consistency
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        UPDATE user_summary 
        SET order_count = (
            SELECT COUNT(*) FROM orders 
            WHERE user_id = NEW.user_id
        )
        WHERE user_id = NEW.user_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
```

**B. Application Patterns**:
1. **Unit of Work Pattern**: Group related operations
2. **Repository Pattern**: Centralize data access logic
3. **Event Sourcing**: Rebuild state from events
4. **Sagas**: Distributed transaction management

**Consistency Checklist**:
- [ ] Foreign key constraints defined
- [ ] Transaction boundaries properly set
- [ ] Application-level validation in place
- [ ] Regular consistency checks scheduled
- [ ] Rollback procedures documented

**Theoretical Keywords**: ACID properties, Referential integrity, Transaction isolation, Data validation, Consistency patterns

---

#### **118. How to design database for large-scale application?**
**Interviewer Answer:**
**Database Design Principles for Scale**:

**Phase 1: Requirements Analysis**
```sql
-- 1. Data Volume Estimation
-- Example: Social Media Platform
- Users: 100M (growing 10%/month)
- Posts: 10B (100 posts/user average)
- Comments: 50B (5 comments/post)
- Relationships: 500M (5 connections/user)

-- 2. Access Patterns Analysis
CREATE TABLE access_patterns (
    entity_type VARCHAR(50),
    read_ratio INT,
    write_ratio INT,
    access_frequency VARCHAR(20),
    data_retention VARCHAR(20)
);
```

**Phase 2: Logical Design**

**A. Schema Design Strategies**:
```sql
-- 1. Normalization (3NF) for transactional data
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE posts (
    post_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(user_id),
    content TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);

-- 2. Denormalization for read performance
CREATE TABLE user_profiles (
    user_id BIGINT PRIMARY KEY REFERENCES users(user_id),
    username VARCHAR(50),
    post_count INT DEFAULT 0,
    follower_count INT DEFAULT 0,
    following_count INT DEFAULT 0,
    last_activity TIMESTAMP
);
```

**Phase 3: Physical Design**

**A. Partitioning Strategy**:
```sql
-- Time-based partitioning
CREATE TABLE posts (
    post_id BIGSERIAL,
    user_id BIGINT,
    content TEXT,
    created_at TIMESTAMP
) PARTITION BY RANGE (created_at);

CREATE TABLE posts_2024_q1 PARTITION OF posts
FOR VALUES FROM ('2024-01-01') TO ('2024-04-01');

-- Shard by user_id
CREATE TABLE posts_user_shard_1 (
    CHECK (user_id % 4 = 0)
) INHERITS (posts);

CREATE TABLE posts_user_shard_2 (
    CHECK (user_id % 4 = 1)
) INHERITS (posts);
```

**B. Indexing Strategy**:
```sql
-- Composite indexes for common queries
CREATE INDEX idx_user_posts 
ON posts(user_id, created_date DESC) 
INCLUDE (content_preview);

-- Partial indexes for active data
CREATE INDEX idx_active_users 
ON users(user_id) 
WHERE last_login > NOW() - INTERVAL '30 days';

-- BRIN indexes for time-series
CREATE INDEX idx_posts_created_brin 
ON posts USING BRIN(created_at);
```

**Phase 4: Scalability Architecture**

**A. Read/Write Separation**:
```sql
-- Master for writes
MASTER_CONNECTION = {
    host: 'db-master-1',
    role: 'primary'
}

-- Read replicas
REPLICA_CONNECTION = {
    host: 'db-replica-1',
    role: 'read_only'
}

-- Application routing
def get_db_connection():
    if is_read_query(current_query):
        return replica_pool.get_connection()
    else:
        return master_pool.get_connection()
```

**B. Caching Strategy**:
```python
# Multi-level caching
class CachingStrategy:
    L1_CACHE = RedisCluster()  # In-memory, 1GB
    L2_CACHE = Memcached()     # Distributed, 10GB
    L3_CACHE = CDN()           # Edge caching
    
    def get_user(self, user_id):
        # Check L1, then L2, then DB
        cache_key = f"user:{user_id}"
        data = self.L1_CACHE.get(cache_key)
        if not data:
            data = self.L2_CACHE.get(cache_key)
            if not data:
                data = self.db.get_user(user_id)
                self.L1_CACHE.setex(cache_key, 300, data)
                self.L2_CACHE.setex(cache_key, 3600, data)
        return data
```

**C. Data Lifecycle Management**:
```sql
-- Hot data (current month) - SSD storage
CREATE TABLE posts_hot (
    LIKE posts INCLUDING ALL
) TABLESPACE ssd_tablespace;

-- Warm data (3 months) - Fast HDD
-- Cold data (1+ years) - Archive/object storage
```

**Phase 5: Performance & Monitoring**

**Key Metrics**:
```sql
-- Monitor scaling factors
SELECT 
    'Connection Pool' AS metric,
    max_connections - numbackends AS available_connections
FROM pg_stat_database 
WHERE datname = current_database()

UNION ALL

SELECT 
    'Cache Hit Ratio',
    (blks_hit * 100.0 / (blks_hit + blks_read))
FROM pg_stat_database 
WHERE datname = current_database();
```

**Design Checklist**:
- [ ] Data volume projections for 3-5 years
- [ ] Read/write ratio analysis complete
- [ ] Partitioning strategy defined
- [ ] Sharding approach determined
- [ ] Caching layers designed
- [ ] Monitoring and alerting configured
- [ ] Disaster recovery plan documented

**Theoretical Keywords**: Horizontal vs vertical scaling, Sharding strategies, CAP theorem, Data partitioning, CQRS pattern, Eventual consistency

---

#### **119. When will you choose NoSQL over SQL?**
**Interviewer Answer:**
**Decision Framework: SQL vs NoSQL**

**Use Case Analysis Matrix**:
```sql
-- Decision Table
CREATE TABLE db_selection_matrix (
    use_case VARCHAR(100),
    data_structure VARCHAR(50),
    consistency_needs VARCHAR(20),
    scale_requirements VARCHAR(20),
    recommended_db VARCHAR(20)
);
```

**Scenario 1: Choose NoSQL When...**

**A. Unstructured/Semi-structured Data**:
```json
// MongoDB Document Example
{
  "user_id": "12345",
  "profile": {
    "name": "John Doe",
    "preferences": {
      "theme": "dark",
      "notifications": ["email", "push"],
      "custom_fields": {
        "favorite_color": "blue",
        "newsletter_opt_in": true
      }
    }
  },
  "activity_log": [
    {"action": "login", "timestamp": "2024-01-01T10:00:00Z"},
    {"action": "view_post", "post_id": "abc123", "timestamp": "2024-01-01T10:05:00Z"}
  ]
}
```
**Fit**: User profiles, content management, IoT sensor data

**B. Extreme Write Scalability**:
```python
# Cassandra time-series data
class TimeSeriesDB:
    def __init__(self):
        self.db = CassandraCluster(
            partition_key='device_id',
            clustering_key='timestamp'
        )
    
    def write_sensor_data(self, device_id, readings):
        # High write throughput: 100K writes/sec
        for reading in readings:
            self.db.execute("""
                INSERT INTO sensor_data (device_id, timestamp, value)
                VALUES (%s, %s, %s)
            """, (device_id, reading.timestamp, reading.value))
```
**Fit**: IoT, clickstream analytics, real-time monitoring

**C. Graph Relationships**:
```cypher
// Neo4j Graph Query
MATCH (user:User {id: '123'})-[:FOLLOWS]->(follower:User)
WITH user, COLLECT(follower) AS followers
MATCH (user)-[:LIKES]->(post:Post)
RETURN user, followers, COUNT(post) AS posts_liked
```
**Fit**: Social networks, recommendation engines, fraud detection

**D. High Availability & Global Distribution**:
```yaml
# DynamoDB Global Table Configuration
GlobalTables:
  - Regions: [us-east-1, eu-west-1, ap-southeast-1]
    Replication: Multi-region, active-active
    Consistency: Eventual
    Latency: < 100ms globally
```
**Fit**: Global applications, mobile backends, gaming leaderboards

**Scenario 2: Choose SQL When...**

**A. Complex Transactions**:
```sql
-- Banking transaction requiring ACID
BEGIN TRANSACTION;
  UPDATE accounts SET balance = balance - 1000 
  WHERE account_id = 123;
  
  UPDATE accounts SET balance = balance + 1000 
  WHERE account_id = 456;
  
  INSERT INTO transactions (from_account, to_account, amount)
  VALUES (123, 456, 1000);
COMMIT;
```

**B. Complex Queries & Reporting**:
```sql
-- Multi-table analytical query
SELECT 
    c.customer_name,
    c.customer_segment,
    EXTRACT(YEAR FROM o.order_date) AS year,
    COUNT(DISTINCT o.order_id) AS order_count,
    SUM(oi.quantity * p.price) AS total_revenue,
    RANK() OVER (PARTITION BY c.customer_segment ORDER BY SUM(oi.quantity * p.price) DESC) AS segment_rank
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
WHERE o.order_date >= '2023-01-01'
GROUP BY c.customer_id, c.customer_name, c.customer_segment, EXTRACT(YEAR FROM o.order_date)
HAVING SUM(oi.quantity * p.price) > 10000;
```

**C. Strong Data Integrity**:
```sql
-- Complex constraints and relationships
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(customer_id) ON DELETE RESTRICT,
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    total_amount DECIMAL(10,2) CHECK (total_amount >= 0),
    status VARCHAR(20) CHECK (status IN ('pending', 'processing', 'shipped', 'delivered')),
    CONSTRAINT valid_order_date CHECK (order_date <= CURRENT_DATE)
);
```

**Decision Criteria Checklist**:
```sql
-- Decision Flow
WITH decision_factors AS (
    SELECT 
        CASE 
            WHEN need_acid = true THEN 'SQL'
            WHEN unstructured_data = true THEN 'NoSQL'
            WHEN write_scale > 1000000 THEN 'NoSQL'
            WHEN complex_joins = true THEN 'SQL'
            WHEN global_distribution = true THEN 'NoSQL'
            WHEN strict_schema = true THEN 'SQL'
            ELSE 'Hybrid'
        END AS recommendation
    FROM application_requirements
)
```

**Hybrid Approach Examples**:
```python
# Polyglot Persistence
class HybridDataLayer:
    def __init__(self):
        self.sql_db = PostgreSQL()  # Users, transactions
        self.document_db = MongoDB()  # User profiles, content
        self.cache = Redis()  # Sessions, counters
        self.time_series = Cassandra()  # Analytics, logs
    
    def get_user_dashboard(self, user_id):
        # SQL for relationships
        user = self.sql_db.query("SELECT * FROM users WHERE id = %s", user_id)
        
        # NoSQL for flexible data
        profile = self.document_db.find_one({"user_id": user_id})
        
        # Cache for performance
        recent_activity = self.cache.get(f"activity:{user_id}")
        
        return {
            "user": user,
            "profile": profile,
            "activity": recent_activity
        }
```

**Key Selection Criteria**:
1. **Data Structure**: Structured → SQL, Unstructured → NoSQL
2. **Scale**: Millions+ writes/sec → NoSQL, Complex reads → SQL
3. **Consistency**: Strong → SQL, Eventual → NoSQL
4. **Query Patterns**: Ad-hoc analytics → SQL, Simple lookups → NoSQL
5. **Development Speed**: Rapid iteration → NoSQL, Enterprise → SQL

**Theoretical Keywords**: CAP theorem, BASE properties, Polyglot persistence, Data modeling, Consistency models

---

#### **120. How do you handle database migration?**
**Interviewer Answer:**
**Comprehensive Database Migration Strategy**:

**Phase 1: Pre-Migration Planning**
```sql
-- 1. Database Inventory
CREATE TABLE migration_inventory (
    schema_name VARCHAR(100),
    table_name VARCHAR(100),
    row_count BIGINT,
    size_gb DECIMAL(10,2),
    complexity_score INT,
    migration_priority INT
);

-- 2. Impact Analysis
SELECT 
    table_name,
    COUNT(*) as dependencies,
    STRING_AGG(dependent_object, ', ') as dependent_objects
FROM information_schema.view_table_usage
GROUP BY table_name
ORDER BY COUNT(*) DESC;
```

**Phase 2: Migration Strategy Selection**

**A. Migration Types**:
```sql
-- Strategy Decision Matrix
CREATE TABLE migration_strategies (
    scenario VARCHAR(100),
    downtime_tolerance VARCHAR(20),
    data_volume VARCHAR(20),
    recommended_approach VARCHAR(50)
);

-- Big Bang Migration
-- Pros: Simpler, single cutover
-- Cons: High risk, extended downtime
-- Fit: Small databases, planned maintenance windows

-- Blue-Green Deployment
-- Pros: Minimal downtime, easy rollback
-- Cons: Double infrastructure cost
-- Fit: Critical production systems

-- Parallel Run
-- Pros: Data validation, gradual cutover
-- Cons: Complexity, sync overhead
-- Fit: Large migrations, zero-downtime requirements
```

**Phase 3: Migration Execution**

**A. Schema Migration**:
```sql
-- 1. Version Control for Schema
-- migration_001_create_users.sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

-- migration_002_add_indexes.sql
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_created ON users(created_at);

-- 2. Automated Migration with Flyway/Liquibase
-- V1__Initial_schema.sql
-- V2__Add_user_profile.sql
-- V3__Create_orders_table.sql
```

**B. Data Migration Process**:
```python
class DataMigrator:
    def __init__(self, source_db, target_db):
        self.source = source_db
        self.target = target_db
        self.metrics = MigrationMetrics()
    
    def incremental_migration(self, batch_size=10000):
        """Migrate data in batches"""
        last_id = 0
        
        while True:
            # Read batch from source
            rows = self.source.execute("""
                SELECT * FROM large_table 
                WHERE id > %s 
                ORDER BY id 
                LIMIT %s
            """, (last_id, batch_size))
            
            if not rows:
                break
            
            # Transform if needed
            transformed = self.transform_batch(rows)
            
            # Write to target
            self.target.bulk_insert('large_table', transformed)
            
            # Update watermark
            last_id = rows[-1]['id']
            
            # Log progress
            self.metrics.record_batch(len(rows))
            
            # Throttle if needed
            time.sleep(0.1)
```

**C. Zero-Downtime Migration**:
```sql
-- Step 1: Create new table with changes
CREATE TABLE users_new (
    LIKE users INCLUDING ALL,
    COLUMN profile_picture_url VARCHAR(500),
    COLUMN two_factor_enabled BOOLEAN DEFAULT false
);

-- Step 2: Dual writes (application layer)
def create_user(user_data):
    # Write to both old and new
    old_db.create_user(user_data)
    new_db.create_user(transform_user_data(user_data))

-- Step 3: Backfill historical data
INSERT INTO users_new 
SELECT *, 
    NULL as profile_picture_url,  -- New columns
    false as two_factor_enabled
FROM users;

-- Step 4: Switch read traffic gradually
def get_user(user_id):
    # Start with 10% traffic to new DB
    if random.random() < 0.1:
        return new_db.get_user(user_id)
    else:
        return old_db.get_user(user_id)

-- Step 5: Complete cutover
ALTER TABLE users RENAME TO users_old;
ALTER TABLE users_new RENAME TO users;
```

**Phase 4: Validation & Testing**

**A. Data Consistency Checks**:
```sql
-- Row count validation
SELECT 
    'source' as db, 
    COUNT(*) as row_count 
FROM source_db.users
UNION ALL
SELECT 
    'target' as db, 
    COUNT(*) 
FROM target_db.users;

-- Checksum validation
SELECT 
    table_name,
    MD5(STRING_AGG(id::text, ',' ORDER BY id)) as data_hash
FROM source_db.users
GROUP BY table_name;

-- Compare with target
```

**B. Performance Benchmarking**:
```sql
-- Pre-migration baseline
EXPLAIN ANALYZE 
SELECT * FROM users WHERE email = 'test@example.com';

-- Post-migration comparison
-- Expected: Similar or better performance
```

**C. Application Testing**:
```python
class MigrationTestSuite:
    def test_read_operations(self):
        # Compare results from old and new
        old_result = old_db.query("SELECT * FROM users LIMIT 100")
        new_result = new_db.query("SELECT * FROM users LIMIT 100")
        assert old_result == new_result
    
    def test_write_operations(self):
        # Test CRUD operations
        test_user = create_test_user()
        
        # Create
        old_id = old_db.create_user(test_user)
        new_id = new_db.create_user(test_user)
        
        # Read
        old_user = old_db.get_user(old_id)
        new_user = new_db.get_user(new_id)
        assert old_user == new_user
        
        # Update
        # Delete
```

**Phase 5: Rollback Planning**

```sql
-- Rollback SQL scripts
-- rollback_001_drop_new_columns.sql
ALTER TABLE users 
DROP COLUMN profile_picture_url,
DROP COLUMN two_factor_enabled;

-- Emergency rollback procedure
CREATE PROCEDURE emergency_rollback()
AS $$
BEGIN
    -- Stop writes to new system
    UPDATE config SET write_to_new_db = false;
    
    -- Revert to old database
    UPDATE load_balancer SET active_db = 'old';
    
    -- Log rollback event
    INSERT INTO migration_log (event, timestamp)
    VALUES ('rollback_triggered', NOW());
END;
$$ LANGUAGE plpgsql;
```

**Migration Checklist**:
- [ ] Pre-migration backup completed
- [ ] Rollback procedure documented
- [ ] Stakeholders notified of maintenance window
- [ ] Performance baselines captured
- [ ] Validation scripts prepared
- [ ] Monitoring configured for new system
- [ ] Support team briefed on changes

**Post-Migration Activities**:
1. **Monitoring**: Track performance for 48 hours
2. **Optimization**: Index rebuilding, statistics update
3. **Cleanup**: Archive old database after validation period
4. **Documentation**: Update runbooks and architecture diagrams

**Theoretical Keywords**: Zero-downtime migration, Blue-green deployment, Schema evolution, Data validation, Rollback strategies, Migration patterns