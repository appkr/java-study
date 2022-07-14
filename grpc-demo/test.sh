#!/bin/bash

RESPONSE=""
ACCESS_TOKEN=""

BOLD='\e[1m'
FAIL='\e[5;31m'
SUCCESS='\e[5;32m'
RED='\e[0;31m'
YELLOW='\e[;33m'
NC='\e[0m'

ensure_dependency() {
  if ! which "$1" &>/dev/null ; then
    echo -e "${RED}$1 not found${NC}"
    exit 1
  fi
}

check_docker() {
  OK=1
  local CONTAINER1=$(docker inspect mysql --format "{{json .State.Status}}" 2> /dev/null | xargs)
  local CONTAINER2=$(docker inspect jhipster-uaa --format "{{json .State.Status}}" 2> /dev/null | xargs)
  [ "running" != "$CONTAINER1" ] && OK=0
  [ "running" != "$CONTAINER2" ] && OK=0
  if [ "1" != "$OK" ]; then
    echo -e "${RED}mysql or jhipster-uaa docker container is not running${NC}"
    echo ""
    echo -e "    ${BOLD}./gradlew composeUp${NC}"
    echo ""
    exit 1
  fi
}

ensure_dependency curl
ensure_dependency jq
ensure_dependency grpcurl
ensure_dependency docker
check_docker

print_header() {
  if [ ! -z "$1" ]; then
    echo -e "${BOLD}${1}${NC}"
    echo ""
  fi
}

print_request() {
  if [ ! -z "$1" ]; then
    echo -e "${BOLD}REQUEST:${NC}"
    echo -e "${YELLOW}${1}${NC}"
    echo ""
  fi
}

print_separator() {
  echo ""
  echo "---"
  echo ""
}

handle_fail() {
  echo -e "${BOLD}RESPONSE:${NC}"
  echo $RESPONSE
  echo ""
  echo -e "${FAIL}=> FAIL${NC}"
  exit 1;
}

handle_success() {
  echo -e "${BOLD}RESPONSE:${NC}"
  echo $RESPONSE
  echo ""
  echo -e "${SUCCESS}=> SUCCESS${NC}"
}

# Login to the UAA with a "client_credentials" grant
RESPONSE=$(curl -s -X POST --data "grant_type=client_credentials" http://internal:internal@localhost:9999/oauth/token)
ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)

# Login to the UAA with a "password" grant
RESPONSE=$(curl -s -X POST --data "username=user&password=user&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token)
ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)

print_header "Test actuator for the gRPC server"
print_request "curl -s http://localhost:8081/actuator/health"

curl -s http://localhost:8081/actuator/health | jq

print_separator

print_header "Test actuator for the gRPC client"
print_request "curl -s http://localhost:8080/actuator/health"

curl -s http://localhost:8080/actuator/health | jq

print_separator

print_header "Test gRPC server with grpcurl"

print_request "grpcurl --plaintext localhost:8090 list"
grpcurl --plaintext localhost:8090 list
echo ""

print_request "grpcurl --plaintext localhost:8090 list grpcdemo.HelloService"
grpcurl --plaintext localhost:8090 list grpcdemo.HelloService
echo ""

print_request "grpcurl --plaintext localhost:8090 list grpcdemo.HelloService"
grpcurl --plaintext localhost:8090 list grpcdemo.HelloService
echo ""

print_request "grpcurl --plaintext -H \"Authorization: bearer ACCESS_TOKEN\" -d '{\"name\": \"gRPC1\"}' localhost:8090 grpcdemo.HelloService.SayHello"
grpcurl --plaintext -H "Authorization: bearer ${ACCESS_TOKEN}" -d '{"name": "gRPC1"}' localhost:8090 grpcdemo.HelloService.SayHello

print_separator

print_header "Test gRPC server from gRPC client"
echo "+------+            +----------------------+           +-------------+"
echo "| curl |  -(http)-> | tomcat & gRPC client | -(grpc)-> | gRPC server |"
echo "+------+            +----------------------+           +-------------+"
echo ""

print_request "curl -s -H \"Authorization: bearer ACCESS_TOKEN\" http://localhost:8080/hello?name=gRPC2"
curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/hello?name=gRPC2 | jq