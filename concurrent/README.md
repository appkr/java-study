## Concurrent series from Defog Tech
- [defog1] [Java ExecutorService - Part 1 - Introduction](https://www.youtube.com/watch?v=6Oo-9Can3H8)
    - Java thread is expensive, so it is much better to use pool.
    ```
    1 Java thread = 1 OS thread
    ```
    - Pool size?
        - If the task is CPU-intensive? If there are 4 cpus, max 4 threads can run at a time, so 4 in the condition that the cpus are used solely by the tasks.
        - If the task is IO-intensive? Better-off if the pool size is bigger

- [defog2] [Java ExecutorService - Part 2 - Type of Pools](https://www.youtube.com/watch?v=sIkG0X4fqs4)
    - CachedThreadPool: If all threads are budy, then create a new thread for the task and place it in the pool. If the thread is idle for 60 sec then kill the thread.
    - ScheduledThreadPool: Schedule the tasks to run based on time delay (and retrigger for fixedRate/fixedDelay).
    - SingleThreadExecutor: Run sequentially

- [defog3] [Java ExecutorService - Part 3 - Constructor & LifeCycle methods](https://www.youtube.com/watch?v=Dma_NmOrp1c)
```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          RejectedExecutionHandler handler) {}
```

|Parameter|Type|Meaning|
|---|---|---|
|corePoolSize|int|Minimum/Base size of the pool|
|maxPoolSize|int|Maximum size of the pool|
|keepAliveTime+unit|long|Time to keep an idle thread alive (after which it is killed)|
|workQueue|BlockingQueue|Queue to store the tasks from which threads fetch them|
|threadFactory|ThreadFactory|The factory to use to create new threads|
|handler|RejectedExecutionHandler|Callback to use when tasks submitted are rejected|

- [defog4] [Java ExecutorService - Part 4 - Callable / Future](https://www.youtube.com/watch?v=NEZ2ASoP_nY)

- [defog5] [Introduction to CompletableFuture in Java 8](https://www.youtube.com/watch?v=ImtZgX1nmr8)
    - Execution order may be changed by compiler (for optimization purpose)
    - Field visibility: `volatile` keyword
> 컴파일러가 최적화를 위해 코드의 실행 순서를 변경하더라도 `volatile`, `synchronized`, `lock` 등을 써서 일관성을 보장해야 한다
```
+-----------+-----------+-----------+-----------+
| core 1    | core 2    | core 3    | core 4    |
+-----------+-----------+-----------+-----------+
| registres | registers | registres | registers |
+-----------+-----------+-----------+-----------+
| L1 cache  | L1 cache  | L1 cache  | L1 cache  |
+-----------+-----------+-----------+-----------+
| L2 cache              | L2 cache              |
+-----------+-----------+-----------+-----------+
| L3 cache                                      |
+-----------+-----------+-----------+-----------+
| RAM                                           |
+-----------+-----------+-----------+-----------+
```