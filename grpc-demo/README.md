## gRPC Demo

### Run & Test

```bash
# run mysql & jhipster-uaa
./gradlew clusterUp

# generate proto stub
./gradlew :proto:generateProto

./gradlew :server:bootRun
# gRPC Server started, listening on address: *, port: 8090

./gradlew :client:bootRun
# Tomcat started on port(s): 8080

bash test.sh
# Test actuator for the gRPC server
# 
# REQUEST:
# curl -s http://localhost:8081/actuator/health
# 
# {
#   "status": "UP",
#   "components": {...}
# }
# 
# ---
# 
# Test actuator for the gRPC client
# 
# REQUEST:
# curl -s http://localhost:8080/actuator/health
# 
# {
#   "status": "UP",
#   "components": {
#     "grpcChannel": {
#       "status": "UP",
#       "details": {
#         "hello-service": "READY"
#       }
#     }
#   }
# }
# 
# ---
# 
# Test gRPC server with grpcurl
# 
# REQUEST:
# grpcurl --plaintext localhost:8090 list
# 
# grpc.health.v1.Health
# grpc.reflection.v1alpha.ServerReflection
# grpcdemo.HelloService
# 
# REQUEST:
# grpcurl --plaintext localhost:8090 list grpcdemo.HelloService
# 
# grpcdemo.HelloService.SayHello
# grpcdemo.HelloService.invalidArgument
# grpcdemo.HelloService.notFound
# 
# REQUEST:
# grpcurl --plaintext localhost:8090 list grpcdemo.HelloService
# 
# grpcdemo.HelloService.SayHello
# grpcdemo.HelloService.invalidArgument
# grpcdemo.HelloService.notFound
# 
# REQUEST:
# grpcurl --plaintext -H "Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsib3BlbmlkIl0sImV4cCI6MTY1NzY5NjM4MywiaWF0IjoxNjU3Njk2MDgzLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoibmZ5bjhhc3ItMW9DckRHX2pOczBHRUh3UFNRIiwiY2xpZW50X2lkIjoid2ViX2FwcCJ9.LJdJM-xsvP8TtSflcNMQDgfW0EcUSemVdwkh-X5bRDIYrDggSO13k352EBeQv2vcXY90W_w6HQmU-9n6G4il6edaXtde0cy--LDKUtvYsi1Tkvwt4-Gzn4qYnt1rK0YPGJ14d5UZBjgHXRpeilLaQ1tstlSUvR3WmBhT8e8mqmHpTbgIUN8FgYURmo-qGln_lO8-ncO9e-HtGxd1kAfjnbDzNOjAELykRam5Ic-obOpFHHQ6AChMTuDFX7O3zYydrqv0JcfVODz1yMBfbRriK5yFaH7ejrcJzJhF_ARvo3E7SVIm6DzaPWT6KXwP3H7VriWqmJCo367a8-rYGjAXbg" -d '{"name:
# 
# {
#   "message": "Hello gRPC",
#   "luckyNumber": -1056365755,
#   "principal": "org.springframework.security.oauth2.jwt.Jwt@4330c862"
# }
# 
# ---
# 
# Test gRPC server from gRPC client
# 
# +------+            +----------------------+           +-------------+
# | curl |  -(http)-> | tomcat & gRPC client | -(grpc)-> | gRPC server |
# +------+            +----------------------+           +-------------+
# 
# REQUEST:
# curl -s http://localhost:8080/hello?name=gRPC
# 
# {
#   "message": "Hello gRPC",
#   "luckyNumber": -257438722
# }
```

### Workflow

- change `.proto`
- generate stub

```bash
./gradlew generateProto
```

- change java implementation (server and client)
