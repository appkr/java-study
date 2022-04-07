WebMVC vs WebFlux 컴퓨팅 리소스 사용 비교 실험

토비의 봄 TV @see https://www.youtube.com/watch?v=ScH7NZU_zvk&list=PLv-xDnFD-nnmof-yoZQN8Fs2kVljIuFyC
Java Concurrency @see https://www.youtube.com/watch?v=WH5UvQJizH0&list=PLhfHPmPYPPRk6yMrcbfafFGSbE2EPK_A6

#### Scenario

![](https://plantuml-server.kkeisuke.dev/svg/TP6nJiGm38RtF8L7RgYu74pL2Ts24D9DN2LcDp4q8arFYGDCF3jEKGiHiCNsx--tdKDYZ9n7hzHP7iwuCmQ6uuIseyI02R2gF5B_vFDxAVHLuO76YQdaJPM_3qmne3zHV7Lc5bYb49eRm1PC99GklTq23_AsIOM2YUvvO0LmScFsYy9CmUGOIQWvKKoAWeKY_yBrBTpTTd0Po-0N-aDQDZLj6NwG8KONu7erNKa0VM6PbLMsqfAkGB2i--PuW6uQ3RB0ouuKvphZ2oNGaCX8NE7tJLFuCgR-ssxts-uVt4_ORe7PLXOCrd-xsLbm9MVQD-LCSw0EWf6V_mG0.svg)

- Run Remote (TomcatWebServer with default setting)
- Run Webflux (NettyWebServer with min 4 threads)
- Run LoadTest (Just a HTTP Client)

#### Observation

```bash
2022-04-03 19:06:07.916  INFO   --- [ool-1-thread-34] dev.appkr.webflux.LoadTest               : Thread ready: 34
...
2022-04-03 19:06:10.130  INFO   --- [ool-1-thread-62] dev.appkr.webflux.LoadTest               : Response: 62/remote1/remote2/internalService, Elapsed: 2.17912947
...
2022-04-03 19:06:10.131  INFO   --- [ool-1-thread-98] dev.appkr.webflux.LoadTest               : Response: 98/remote1/remote2/internalService, Elapsed: 2.179382616
2022-04-03 19:06:10.181  INFO   --- [           main] dev.appkr.webflux.LoadTest               : Total: 2.230679388
```

#### Apache Bench Test

tomcat threads|rps|latency|error
---|---|---|---
200|117.06|8.543ms|0
10|24.83|40.272ms|0

netty threads|rps|latency|error
---|---|---|---
1|150.74|6.634ms|343(3.8%)+
 
+ webclient pool 부족 현상으로 인한 에러 
```bash
org.springframework.web.reactive.function.client.WebClientRequestException: 
  Pending acquire queue has reached its maximum size of 1000; 
  nested exception is reactor.netty.internal.shaded.reactor.pool.PoolAcquirePendingLimitException
# @see https://stackoverflow.com/a/68658096
```

```bash
ab -c 100 -t 60 -l  http://127.0.0.1:8082/bench

Server Software:
Server Hostname:        127.0.0.1
Server Port:            8082

Document Path:          /bench
Document Length:        Variable

Concurrency Level:      100
Time taken for tests:   60.075 seconds
Complete requests:      9056
Failed requests:        0
Non-2xx responses:      343
Total transferred:      791631 bytes
HTML transferred:       80804 bytes
Requests per second:    150.74 [#/sec] (mean)
Time per request:       663.376 [ms] (mean)
Time per request:       6.634 [ms] (mean, across all concurrent requests)
Transfer rate:          12.87 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.5      0       9
Processing:    67  655 226.5    595    2167
Waiting:       63  655 226.5    595    2167
Total:         67  656 226.5    595    2168

Percentage of the requests served within a certain time (ms)
  50%    595
  66%    691
  75%    743
  80%    791
  90%    917
  95%   1118
  98%   1263
  99%   1524
 100%   2168 (longest request)
```