## gRPC Demo

### Run
```bash
./gradlew bootRun
# Tomcat started on port(s): 8080
# gRPC Server started, listening on address: *, port: 8090

curl -s http://localhost:8080/actuator/health
# {
#   "grpcChannel": {
#     "status": "UP"
#   },
# }

curl -s http://localhost:8080/actuator/info
# {
#   "grpc.server": {
#     "port": 8090,
#     "services": {
#       "grpc.health.v1.Health": [
#         "Check",
#         "Watch"
#       ],
#       "grpc.reflection.v1alpha.ServerReflection": [
#         "ServerReflectionInfo"
#       ],
#       "grpcdemo.HelloService": [
#         "SayHello"
#       ]
#     }
#   }
# }
```

### Test with grpcurl
```bash
grpcurl --plaintext localhost:8090 list
# grpc.health.v1.Health
# grpc.reflection.v1alpha.ServerReflection
# grpcdemo.HelloService

grpcurl --plaintext localhost:8090 list grpcdemo.HelloService
# grpcdemo.HelloService.SayHello

grpcurl --plaintext -d '{"name": "gRPC"}' localhost:8090 grpcdemo.HelloService.SayHello
# {
#   "message": "Hello gRPC"
# }
```

### Test with gRPC client via HTTP
```
+------+            +----------------------+           +-------------+
| curl |  -(http)-> | tomcat & gRPC client | -(grpc)-> | gRPC server |
+------+            +----------------------+           +-------------+
```
```bash
curl -s http://localhost:8080/hello?name=gRPC
# {
#   "message": "Hello gRPC"
# }
```

### Workflow
- change .proto
- generate stub
```bash
./gradlew generateProto
```
- change java implementation (server and client)
```bash
curl -s http://localhost:8080/hello?name=gRPC | jq "."
# {
#   "luckyNumber": 1643621454,
#   "message": "Hello gRPC"
# }
```

### Exception handling

```bash
grpcurl --plaintext localhost:8090 grpcdemo.HelloService.invalidArgument 
# ERROR:
#   Code: InvalidArgument
#   Message: Invalid argument

curl -s http://localhost:8080/exception/invalid-argument 
# 2022-07-12 09:29:13.213 ERROR [grpcdemo,a334a8c714fa7495,a334a8c714fa7495] 96197 --- [nio-8080-exec-1] dev.appkr.grpcdemo.HelloClientRpc        : ERROR
# io.grpc.StatusRuntimeException: INVALID_ARGUMENT: Invalid argument
```

```bash
grpcurl --plaintext localhost:8090 grpcdemo.HelloService.notFound       
# ERROR:
#   Code: NotFound
#   Message: Not found

curl -s http://localhost:8080/exception/not-found
# 2022-07-12 09:30:55.196 ERROR [grpcdemo,7c2f5ba009b11dcf,7c2f5ba009b11dcf] 96197 --- [nio-8080-exec-3] dev.appkr.grpcdemo.HelloClientRpc        : ERROR
# io.grpc.StatusRuntimeException: NOT_FOUND: Not found
```