## Config Server

```bash
$ curl -s -X GET http://localhost:8888/config/default | jq
#{
#  "name": "config",
#  "profiles": [
#    "default"
#  ],
#  "label": null,
#  "version": null,
#  "state": null,
#  "propertySources": [
#    {
#      "name": "file:///Users/appkr/workspace/spring-study/spring-cloud/config-server/central-properties/config.yml",
#      "source": {
#        "application.message": "Hello master"
#      }
#    },
#    {
#      "name": "file:///Users/appkr/workspace/spring-study/spring-cloud/config-server/central-properties/application.yml",
#      "source": {
#        "welcome.message": "Hello Spring Cloud"
#      }
#    }
#  ]
#}
```