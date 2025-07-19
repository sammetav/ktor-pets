# Kotlin Coroutines in This Project

## Project Context
This project is a Kotlin backend using Spring Boot, but it is designed to use **Kotlin coroutines** for asynchronous and non-blocking operations.

## Coroutines and `suspend` Functions
Kotlin coroutines allow you to write asynchronous code in a sequential style. Functions marked with `suspend` can perform long-running or non-blocking operations (like database or network calls) without blocking a thread.

### When to Use `suspend`
- Use `suspend` on functions that perform I/O or other asynchronous work (e.g., database queries, HTTP calls).
- Use `suspend` for functions that return a **single value** asynchronously.
- Example:
  ```kotlin
  suspend fun findPetById(id: Long): Pet? {
      return petRepository.findById(id)
  }
  ```

### When NOT to Use `suspend`
- Do **not** use `suspend` for simple, fast, in-memory operations (e.g., calculations, data transformations).
- Do **not** use `suspend` for functions that return immediately and do not perform asynchronous work.
- Example:
  ```kotlin
  fun calculatePetAge(birthYear: Int): Int {
      return currentYear - birthYear
  }
  ```

## `Flow` vs. `suspend`
- Use `Flow<T>` for functions that return **multiple values** asynchronously (like a stream or a list that is computed lazily).
- Use `suspend` for a single asynchronous result.
- Example:
  ```kotlin
  fun findAllPets(): Flow<Pet> {
      return petRepository.findAll()
  }
  ```

## Why Use Coroutines?
- Coroutines make asynchronous code easy to read and maintain.
- They allow you to write non-blocking code without the complexity of callbacks or reactive streams.
- They are well-supported in Kotlin and integrate with many libraries, including Spring Data (with coroutine extensions).

## Summary Table
| Use Case                        | Return Type   | Use `suspend`? |
|----------------------------------|--------------|----------------|
| Single async value (DB call)     | Pet?         | Yes            |
| Multiple async values (stream)   | Flow<Pet>    | No             |
| Fast, in-memory calculation      | Int, String  | No             |

---

For more, see:
- [Kotlin Coroutines Overview](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)

