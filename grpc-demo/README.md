## gRPC Demo

### Run & Test

```bash
# run mysql & jhipster-uaa
./gradlew composeUp

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

Run the cluster

```bash
./gradlew :server:jibDockerBuild
./gradlew :client:jibDockerBuild
# If it fails because of docker hub auth, provide more option to the command
# ... -Djib.from.auth.username={USERNAME} -Djib.from.auth.password={PASSWORD}

docker-compose -f docker/cluster-{PLATFORM}.yml up

# Since client depends on jhipster-uaa /oauth/token_key at start-up time
# we have to restart the client after the jhipster-uaa is completely available
docker container restart grpc-demo-client

bash test.sh
```

### Test at Kubernetes

Tag and push images to the docker registry

```bash
docker login

docker tag grpc-demo-server:latest {REGISTRY}/grpc-demo-server:latest
docker tag grpc-demo-client:latest {REGISTRY}/grpc-demo-client:latest

docker push {REGISTRY}/grpc-demo-server:latest
docker push {REGISTRY}/grpc-demo-client:latest
```

Apply the K8S manifests and test 

```bash
# Apply the K8S manifests
kubectl apply -f k8s/grpc-demo-server.yml
kubectl apply -f k8s/grpc-demo-client.yml

# Log in to a POD
kubectl exec -it {GRPC-DEMO-CLIENT_POD} -- bash

# Check if the services are up and running
curl -s http://grpc-demo-client:20001/actuator/health
curl -s http://grpc-demo-server:20002/actuator/health

# jq binary is required...
yum install jq

# Login to the UAA with a "client_credentials" grant
CLIENT_ID={}
CLIENT_SECRET={}
RESPONSE=$(curl -s -X POST --data "grant_type=client_credentials" http://$CLIENT_ID:$CLIENT_SECRET@uaa:9999/oauth/token)
ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)

# Check if all the services are OK
curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/hello?name=gRPC2 | jq
# {
#   "message": "Hello gRPC2",
#   "principal": "org.springframework.security.oauth2.jwt.Jwt@e97a6ce",
#   "luckyNumber": 928785054
# }
```

Cleanup K8S resources
```bash
kubectl delete deployment grpc-demo-server grpc-demo-client
kubectl delete service grpc-demo-server grpc-demo-client
kubectl delete configmap grpc-demo-server grpc-demo-client
```
