# Caching Layer (Simple In-Memory Cache)

### Objective

The goal of this task was to improve application performance by creating a simple in-memory cache for frequently used data.

### Key Features

**Singleton Pattern:**

The CacheManager is implemented as a Singleton. This means only one instance of the cache exists in the whole application.

**In-Memory Storage:**

The data is stored in memory using ConcurrentHashMap. It is thread-safe, so it works correctly when many users send requests at the same time.

**Method Caching:**

The getAll() method in BookService is cached. When we call this method again, the data is returned from the cache instead of the database. This makes the application faster.

**Automatic Invalidation:**

The cache is cleared automatically after create, update, and delete operations. This helps to keep the data correct and updated.

### Architecture and Principles

**Layered Architecture:**

The caching logic is inside the Service layer. The Controller and Repository layers do not know about the cache. This keeps the structure clean.

**SOLID Principles:**

We created a separate CacheManager class. It has only one responsibility – managing the cache. This follows the Single Responsibility Principle.

### How to Test

**Cache Hit:**

Call GET /api/books.
The first request gets data from the database.
The second request gets data from the cache, so it is faster.

**Invalidation:**

Call POST, PUT, or DELETE for a book.
After that, when you call GET /api/books, the application will get fresh data from the database because the cache was cleared.