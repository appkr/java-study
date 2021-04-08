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

## wait() & notify()

이것이 자바다 p611

생산자 스레드는 소비자 스레드가 읽기 전ㅌ에 새로운 데이터를 두 번 생성하면 안되고(`setData()`를 두 번 실행하면 안됨), 소비자 스레드는 생산자 스레드가 새로운 데이터를 생성하기 전에 이전 데이터를 두 번 읽어서도 안된다(`getData()`를 두 번 실행하면 안됨). 구현 방법은 공유 객체(`DataBox`)에 데이터를 저장할 수 있는 `data` 필드의 값이 `null`이면 생산자 스레드를 실행 대기 상태로 만들고, 소비자 스레드를 일시 정지 상태로 만드는 것이다. 반대로 `data` 필드의 값이 `null`이 아니면 소비자 스레디를 실행 대기 상태로 만들고, 생산자 스레드를 일시 정지 상태로 만들면 된다
