# Ktor, Coroutines, and Reactive Programming (WIP) in This Project

## Ktor Basics
Ktor is a Kotlin framework for building asynchronous servers and clients. However, this project uses Spring Boot with Kotlin, not Ktor itself—the package name is just `kt.ktor`. The principles of asynchronous and reactive programming still apply.

## Coroutines and `suspend` Functions
Kotlin coroutines allow you to write asynchronous, non-blocking code in a sequential style. Functions marked with `suspend` can be paused and resumed without blocking a thread, making them ideal for I/O operations (like database or network calls).

- Use `suspend` on functions that perform long-running or asynchronous operations.
- In Spring WebFlux or R2DBC, you often use reactive types (`Mono`, `Flux`) instead of `suspend`, but coroutines can interoperate with these.

## `Flow` vs. `suspend`
- `Flow<T>` is a cold asynchronous stream of values, similar to `Flux<T>` in Project Reactor.
- Use `Flow` when you want to return multiple values asynchronously.
- Use `suspend` for single asynchronous results.

In this project, we use `Mono`/`Flux` (from Reactor) for reactive database access, but you may see `suspend` and `Flow` in coroutine-based code.

## Why Not Always Use `suspend`?
- Spring Data R2DBC and WebFlux are built around reactive types (`Mono`, `Flux`), not coroutines.
- You can use coroutines with Spring, but support is still evolving. For maximum compatibility, this project uses the reactive style.

## Coroutines in This Project
- Database repositories extend `ReactiveCrudRepository` and return `Mono`/`Flux`.
- Service methods use these types for non-blocking, reactive data access.
- If you want to use coroutines, you can convert between `Mono`/`Flux` and `suspend`/`Flow` using extension functions from `kotlinx-coroutines-reactor`.

---

For more, see the official docs:
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
- [Spring Data R2DBC](https://docs.spring.io/spring-data/r2dbc/docs/current/reference/html/)

