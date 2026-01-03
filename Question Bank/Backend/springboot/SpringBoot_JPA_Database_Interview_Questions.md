SPRING BOOT + JPA + DATABASE COMBINED INTERVIEW QUESTIONS


BASIC LEVEL (0–2 Years)

SPRING BOOT WITH DATABASE

1. How do you connect Spring Boot with a database?
2. What dependencies are required for Spring Boot + JPA + MySQL?
3. What is DataSource in Spring Boot?
4. How database configuration is done in application.properties?
5. What is spring.jpa.hibernate.ddl-auto?
6. Difference between create, update, validate, and none
7. What happens when ddl-auto is set to create?
8. Can we use multiple databases in Spring Boot?
9. How does Spring Boot know which database to connect?
10. What is the default database connection pool in Spring Boot?


JPA BASICS WITH DB

11. What is JPA and why is it used?
12. Difference between JPA and Hibernate
13. What is EntityManager?
14. What is persistence context?
15. What is @Entity?
16. What is @Table?
17. What is @Column?
18. What is @Id?
19. What is @GeneratedValue?
20. Types of ID generation strategies



INTERMEDIATE LEVEL (2–4 Years)

ENTITY AND TABLE MAPPING

21. How entity is mapped to database table?
22. What happens if column name and field name are different?
23. What is @Transient?
24. What is @Enumerated?
25. Enum mapping STRING vs ORDINAL
26. How do you map Date and Time in JPA?
27. What is @Lob?
28. How to store JSON in database using JPA?
29. Can one entity map to multiple tables?
30. Can one table map to multiple entities?


RELATIONSHIPS AND DATABASE

31. Difference between OneToOne, OneToMany, ManyToOne, ManyToMany
32. What is owning side of relationship?
33. What is mappedBy?
34. What happens if mappedBy is not used?
35. How foreign key is created in JPA?
36. How to avoid infinite loop in bidirectional mapping?
37. Cascade types and real-time use case
38. OrphanRemoval vs CascadeType.REMOVE
39. FetchType.LAZY vs FetchType.EAGER
40. Default fetch type for all relationships


TRANSACTIONS AND DB CONSISTENCY

41. What is @Transactional?
42. What happens if exception occurs inside @Transactional?
43. Which exceptions cause rollback?
44. Can @Transactional work without database?
45. What is propagation in transaction?
46. Difference between REQUIRED and REQUIRES_NEW
47. How transactions work internally in Spring?
48. How database commit happens in Spring Boot?
49. What is isolation level?
50. Which isolation level does Spring use by default?



ADVANCED LEVEL (4+ Years)

PERFORMANCE AND QUERY OPTIMIZATION

51. What is N+1 problem in JPA?
52. How N+1 impacts database performance?
53. How to solve N+1 problem?
54. What is JOIN FETCH?
55. Difference between JOIN and JOIN FETCH
56. What is EntityGraph?
57. JPQL vs Native Query – when to use?
58. How pagination works in Spring Data JPA?
59. How limit and offset works at DB level?
60. What happens internally when PageRequest is used?


HIBERNATE AND DB INTERNals

61. First-level cache in Hibernate
62. Second-level cache
63. Difference between Hibernate cache and DB cache
64. When does Hibernate hit the database?
65. Dirty checking in Hibernate
66. What is flush?
67. Difference between flush and commit
68. What is save(), saveAndFlush(), persist(), merge()
69. How Hibernate generates SQL?
70. How indexing helps JPA queries?



EXCEPTION HANDLING AND DB ERRORS

71. How do you handle database exceptions in Spring Boot?
72. What is DataIntegrityViolationException?
73. What happens when unique constraint fails?
74. How to return proper error when DB fails?
75. How to handle constraint violations globally?
76. How validation errors differ from DB errors?
77. How do you log SQL queries?
78. How to debug slow database queries?
79. What happens if DB connection is lost?
80. How Spring Boot retries DB connection?



REAL-TIME SCENARIO QUESTIONS (VERY IMPORTANT)

81. API is slow due to database – what will you check first?
82. Large table with millions of records – how will you fetch data?
83. How do you prevent duplicate records?
84. How do you ensure data consistency across multiple tables?
85. How do you handle concurrent updates?
86. What happens if two users update same record?
87. How optimistic locking works with @Version?
88. Difference between optimistic and pessimistic locking
89. How do you design entities for performance?
90. When will you use native query instead of JPA?


PROJECT-BASED QUESTIONS (INTERVIEW FAVORITE)

91. Explain database schema of your project
92. Explain entity relationships used
93. How many tables and why?
94. How you handled transactions in project?
95. How you handled validations vs DB constraints?
96. How you optimized JPA queries?
97. How you handled pagination and sorting?
98. How you handled database migrations?
99. What challenges you faced with JPA?
100. What improvements you will do in DB design?


FINAL INTERVIEW FOCUS (SPRING BOOT + JPA + DB)

101. Request flow from Controller to Database
102. How JPA converts entity to SQL
103. How Spring manages transactions
104. How Hibernate interacts with DB
105. How errors propagate from DB to API
