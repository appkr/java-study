## Kotlin Coroutines

### Before run
```shell
jvmArgs=-Dkotlinx.coroutines.debug
```

### ForkJoin workload test

Simulate simple task

Test case|Elapsed millis
---|---
Without Coroutine|32ms, 10ms, 11ms, 11ms, 11ms
launch & ConcurrentLiknedQueue|66ms, 64ms, 61ms, 63ms, 65ms
async|68ms, 16ms, 16ms, 14ms, 14ms
channelFlow|76ms, 36ms, 37ms, 36ms, 31ms

Simulate IO-bound task by giving 10ms delay on each operation

Test case|Elapsed millis
---|---
Without Coroutine|11707ms, 11687ms, 11726ms, 11747ms, 11677ms
launch & ConcurrentLiknedQueue|56ms, 59ms, 60ms, 56ms, 59ms
async|31ms, 30ms, 27ms, 31ms, 44ms
channelFlow|29ms, 32ms, 29ms, 30ms, 32ms
