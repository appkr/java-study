## Spring Async

- https://www.baeldung.com/spring-async
- https://github.com/eugenp/tutorials/tree/master/spring-all/src/main/java/org/baeldung/async
- https://github.com/eugenp/tutorials/tree/master/spring-all/src/test/java/org/baeldung/async


```bash
# springstudy.async.AsyncJob.asyncMethodWithVoidReturnType()
22:14:23.180  INFO 4024 --- [  main] springstudy.async.AsyncJobTest           : Delegating job to worker method
22:14:23.207  INFO 4024 --- [  main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
22:14:23.211  INFO 4024 --- [  main] springstudy.async.AsyncJobTest           : Delegating done
22:14:23.222  INFO 4024 --- [task-1] springstudy.async.AsyncJob               : Executing method asynchronously
22:14:23.223  INFO 4024 --- [task-1] springstudy.async.AsyncJob               : Do something heavy
22:14:23.324  INFO 4024 --- [task-1] springstudy.async.AsyncJob               : Do something heavy
22:14:23.427  INFO 4024 --- [task-1] springstudy.async.AsyncJob               : Do something heavy
22:14:23.531  INFO 4024 --- [task-1] springstudy.async.AsyncJob               : Do something heavy
22:14:23.635  INFO 4024 --- [task-1] springstudy.async.AsyncJob               : Do something heavy
```

```bash
# springstudy.async.AsyncJob.asyncMethodWithReturnType()
22:17:49.088  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Delegating job to worker method
22:17:49.111  INFO 4040 --- [  main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
22:17:49.135  INFO 4040 --- [task-1] springstudy.async.AsyncJob               : Job received and handling the job
22:17:49.224  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Continue doing something else
22:17:49.326  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Continue doing something else
22:17:49.431  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Continue doing something else
22:17:49.535  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Continue doing something else
22:17:49.641  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Continue doing something else
22:17:49.641  INFO 4040 --- [  main] springstudy.async.AsyncJobTest           : Result from asynchronous process: Hello world
```

```bash
# springstudy.async.AsyncJob.asyncMethodWithException("foo")
22:20:32.777  INFO 4047 --- [  main] springstudy.async.AsyncJobTest           : Delegating job to worker method
22:20:32.804  INFO 4047 --- [  main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
22:20:32.808  INFO 4047 --- [  main] springstudy.async.AsyncJobTest           : Delegating done
22:20:32.820  INFO 4047 --- [task-1] springstudy.async.AsyncJob               : Job received and handling the job
22:20:32.821 ERROR 4047 --- [task-1] springstudy.async.AsyncExceptionHandler  : Exception message: Exception occurred while handling async job
22:20:32.822  INFO 4047 --- [task-1] springstudy.async.AsyncExceptionHandler  : Parameters: foo
```