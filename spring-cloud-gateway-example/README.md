Spring cloud gateway demo

service|port
---|---
gateway|8080
backend|8081

```bash
$ curl -s http://localhost:8080/backend/hello | jq
{
  "user-agent": "curl/7.64.1",
  "accept": "*/*",
  "x-foo": "bar", # Custom request header attached by the gateway
  "forwarded": "proto=http;host=\"localhost:8080\";for=\"[0:0:0:0:0:0:0:1]:52702\"",
  "x-forwarded-for": "0:0:0:0:0:0:0:1",
  "x-forwarded-proto": "http",
  "x-forwarded-prefix": "/backend",
  "x-forwarded-port": "8080",
  "x-forwarded-host": "localhost:8080",
  "host": "localhost:8081",
  "x-b3-traceid": "0dbf27514e0c25ae",
  "x-b3-spanid": "a62221102cdc2eb9",
  "x-b3-parentspanid": "0dbf27514e0c25ae",
  "x-b3-sampled": "1",
  "content-length": "0"
}
```
```bash
2022-01-01 15:33:16.461 DEBUG [,161ab0378f3faab5,161ab0378f3faab5] 9557 --- [ctor-http-nio-3] o.s.c.g.h.RoutePredicateHandlerMapping   : Route matched: backend
2022-01-01 15:33:16.461 DEBUG [,161ab0378f3faab5,161ab0378f3faab5] 9557 --- [ctor-http-nio-3] o.s.c.g.h.RoutePredicateHandlerMapping   : Mapping [Exchange: GET http://localhost:8080/backend/hello] to Route{id='backend', uri=http://localhost:8081, order=0, predicate=Paths: [/backend/**], match trailing slash: true, gatewayFilters=[[[RewritePath /backend/(?<remaining>.*) = '/${remaining}'], order = 0]], metadata={}}
2022-01-01 15:33:16.461 DEBUG [,161ab0378f3faab5,161ab0378f3faab5] 9557 --- [ctor-http-nio-3] o.s.c.g.h.RoutePredicateHandlerMapping   : [741e5e6a-1] Mapped to org.springframework.cloud.gateway.handler.FilteringWebHandler@3c2f663e
2022-01-01 15:33:16.461 DEBUG [,161ab0378f3faab5,161ab0378f3faab5] 9557 --- [ctor-http-nio-3] o.s.c.g.handler.FilteringWebHandler      : Sorted gatewayFilterFactories: [[GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.RemoveCachedBodyFilter@31efacad}, order = -2147483648], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter@236fdf}, order = -2147482648], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.NettyWriteResponseFilter@646427f7}, order = -1], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.ForwardPathFilter@7d9c45ee}, order = 0], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.GatewayMetricsFilter@6e24ce51}, order = 0], [[RewritePath /backend/(?<remaining>.*) = '/${remaining}'], order = 0], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter@380ce5a3}, order = 10000], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.config.GatewayNoLoadBalancerClientAutoConfiguration$NoLoadBalancerClientFilter@423ed3b5}, order = 10150], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.WebsocketRoutingFilter@31da0434}, order = 2147483646], GatewayFilterAdapter{delegate=dev.appkr.gateway.CustomHeaderGlobalFilter@3dc68586}, [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.NettyRoutingFilter@60dc1a4e}, order = 2147483647], [GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.ForwardRoutingFilter@38029686}, order = 2147483647]]
2022-01-01 15:33:16.464 DEBUG [,,] 9557 --- [ctor-http-nio-2] r.netty.http.client.HttpClientConnect    : [6bdab91d-2, L:/127.0.0.1:52956 - R:localhost/127.0.0.1:8081] Handler is being applied: {uri=http://localhost:8081/hello, method=GET}
2022-01-01 15:33:16.472 DEBUG [,,] 9557 --- [ctor-http-nio-2] r.n.http.client.HttpClientOperations     : [6bdab91d-2, L:/127.0.0.1:52956 - R:localhost/127.0.0.1:8081] Received response (auto-read:false) : [Content-Type=application/json, Transfer-Encoding=chunked, Date=Sat, 01 Jan 2022 06:33:16 GMT]
2022-01-01 15:33:16.473 DEBUG [,,] 9557 --- [ctor-http-nio-2] r.n.http.client.HttpClientOperations     : [6bdab91d-2, L:/127.0.0.1:52956 - R:localhost/127.0.0.1:8081] Received last HTTP packet
```