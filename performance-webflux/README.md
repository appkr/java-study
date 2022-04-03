WebMVC vs WebFlux 컴퓨팅 리소스 사용 비교 실험

@see https://www.youtube.com/watch?v=ScH7NZU_zvk&list=PLv-xDnFD-nnmof-yoZQN8Fs2kVljIuFyC

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