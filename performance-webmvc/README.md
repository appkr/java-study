WebMVC vs WebFlux 컴퓨팅 리소스 사용 비교 실험

토비의 봄 TV @see https://www.youtube.com/watch?v=ScH7NZU_zvk&list=PLv-xDnFD-nnmof-yoZQN8Fs2kVljIuFyC
Java Concurrency @see https://www.youtube.com/watch?v=WH5UvQJizH0&list=PLhfHPmPYPPRk6yMrcbfafFGSbE2EPK_A6

#### Scenario

![](https://plantuml-server.kkeisuke.dev/svg/ZP8_JmCn3CNtV0gFT3YrATkfGiU0458dMec5nPTON4GkgH9VfIvyTfnh-HELWIr-yVlbsKgN6HDFerVg80Tdt043mpQYtLDcm0nOtRnGFnvDqVjAVwOnCXNTLFfJO4e1_OxIqPcvmIg5qDm1jc0IeRZqQWKyoDbc5GeckRU15S3EZPF_wZ0nc2ab4kgKAML5mK9fF-Fw5XuVzh1C9VUD_X3BegPTiW-I8GP_0WqSHuDyUVk71z0Nelacbjcqo1KEWF6Tic0o6Qah-wPfYb_2wBznwt_ZViFMPzWhs5Rc2DRJDQQxpwTm5MWf33ipRcLVcwRiQovK9tto0pu1.svg)

- Run Remote (TomcatWebServer with default setting)
- Run Webmvc (TomcatWebServer with 1 thread)
- Run LoadTest (Just a HTTP Client)

#### Observation

![](docs/visualvm.png)

```bash
2022-04-03 16:10:52.087  INFO   --- [ool-1-thread-15] dev.appkr.webmvc.LoadTest                : Thread ready: 15
...
2022-04-03 16:10:54.745  INFO   --- [ool-1-thread-44] dev.appkr.webmvc.LoadTest                : Response: 44/remote1/remote2/internalService, Elapsed: 2.616022063
...
2022-04-03 16:14:14.503  INFO   --- [ool-1-thread-61] dev.appkr.webmvc.LoadTest                : Response: 61/remote1/remote2/internalService, Elapsed: 202.381099517
2022-04-03 16:14:14.503  INFO   --- [           main] dev.appkr.webmvc.LoadTest                : Total: 202.381099517
```

#### Apache Bench Test

tomcat threads|rps|latency|error
---|---|---|---
200|117.06|8.543ms|0
10|24.83|40.272ms|0

##### server.tomcat.threads.max=200

```bash
ab -c 100 -t 60 -l  http://127.0.0.1:8082/bench

Server Software:
Server Hostname:        127.0.0.1
Server Port:            8082

Document Path:          /bench
Document Length:        Variable

Concurrency Level:      100
Time taken for tests:   60.039 seconds
Complete requests:      7028
Failed requests:        0
Total transferred:      955808 bytes
HTML transferred:       28112 bytes
Requests per second:    117.06 [#/sec] (mean)
Time per request:       854.289 [ms] (mean)
Time per request:       8.543 [ms] (mean, across all concurrent requests)
Transfer rate:          15.55 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0  285 2182.4      0   21012
Processing:   360  549 175.4    497    2291
Waiting:      360  548 175.4    496    2291
Total:        360  833 2195.8    499   21538

Percentage of the requests served within a certain time (ms)
  50%    499
  66%    524
  75%    551
  80%    577
  90%    906
  95%   1078
  98%   2291
  99%  14900
 100%  21538 (longest request)
```

##### server.tomcat.threads.max=10

```bash
ab -c 100 -t 60 -l  http://127.0.0.1:8082/bench

Server Software:
Server Hostname:        127.0.0.1
Server Port:            8082

Document Path:          /bench
Document Length:        Variable

Concurrency Level:      100
Time taken for tests:   60.005 seconds
Complete requests:      1490
Failed requests:        0
Total transferred:      202640 bytes
HTML transferred:       5960 bytes
Requests per second:    24.83 [#/sec] (mean)
Time per request:       4027.206 [ms] (mean)
Time per request:       40.272 [ms] (mean, across all concurrent requests)
Transfer rate:          3.30 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.6      0       5
Processing:   401 3866 535.7   3985    4175
Waiting:      401 3865 535.8   3985    4175
Total:        404 3866 535.2   3985    4175

Percentage of the requests served within a certain time (ms)
  50%   3985
  66%   4014
  75%   4029
  80%   4041
  90%   4085
  95%   4106
  98%   4137
  99%   4148
 100%   4175 (longest request)
```