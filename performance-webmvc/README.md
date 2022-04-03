WebMVC vs WebFlux 컴퓨팅 리소스 사용 비교 실험

@see https://www.youtube.com/watch?v=ScH7NZU_zvk&list=PLv-xDnFD-nnmof-yoZQN8Fs2kVljIuFyC

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