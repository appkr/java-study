## Thread from baeldung

- https://www.baeldung.com/thread-pool-java-and-guava

![](https://www.baeldung.com/wp-content/uploads/2016/08/2016-08-10_10-16-52-1024x572.png)

## Logger 설정

- http://kwonnam.pe.kr/wiki/java/slf4j

## Mutex & Semaphore

- https://mkyong.com/java/java-thread-mutex-and-semaphore-example/
- https://www.baeldung.com/java-semaphore

> Java multi threads example to show you how to use Semaphore and Mutex to limit the number of threads to access resources.
>
> - Semaphores – Restrict the number of threads that can access a resource. Example, limit max 10 connections to access a file simultaneously.
> - Mutex – Only one thread to access a resource at once. Example, when a client is accessing a file, no one else should have access the same file at the same time.

## Thread vs Runnable

- https://www.baeldung.com/java-runnable-vs-extending-thread

> Runnable is typically a better approach than extending the Thread class.

## Runnable vs Callable

- https://www.baeldung.com/java-runnable-callable
- `Runnable` tasks can be run using the `Thread` class or `ExecutorService` whereas `Callable` can be run only using the latter.
- The `Future` object returned from `Runnable` does not contain any value, while `Callable` does.
- `Runnable` does not throw an `Exception`, while `Callable` does.
