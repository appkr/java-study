## Kotlin Coroutines

### Before run
```shell
jvmArgs=-Dkotlinx.coroutines.debug
```

### ForkJoin workload test
Test case|Elapsed millis
---|---
Without Coroutine|32ms, 10ms, 11ms, 11ms, 11ms
launch & ConcurrentLiknedQueue|66ms, 64ms, 61ms, 63ms, 65ms
async|68ms, 16ms, 16ms, 14ms, 14ms
channelFlow|76ms, 36ms, 37ms, 36ms, 31ms

