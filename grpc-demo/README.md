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