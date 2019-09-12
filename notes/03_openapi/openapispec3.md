## Open Api Spec 3 (a.k.a. Swagger)

#### Spec
- https://swagger.io/docs/specification/about/
- https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md
- Case Sensitive
https://blog.readme.io/an-example-filled-guide-to-swagger-3-2/

#### Tools
- https://openapi-map.apihandyman.io/
- https://editor.swagger.io/
- https://codebeautify.org/yaml-to-json-xml-csv

#### Document Structure
```bash
└── openapi.yaml # RECOMMENDED file name
    ├── foo.yaml
    ├── ...
    └── bar.yaml
```

#### Data Types
Common Name | [`type`](#dataTypes) | [`format`](#dataTypeFormat) | Comments
----------- | ------ | -------- | --------
integer | `integer` | `int32` | signed 32 bits
long | `integer` | `int64` | signed 64 bits
float | `number` | `float` | |
double | `number` | `double` | |
string | `string` | | |
byte | `string` | `byte` | base64 encoded characters
binary | `string` | `binary` | any sequence of octets
boolean | `boolean` | | |
date | `string` | `date` | As defined by `full-date` - [RFC3339](https://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14)
dateTime | `string` | `date-time` | As defined by `date-time` - [RFC3339](https://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14)
password | `string` | `password` | A hint to UIs to obscure input.

#### Fields
- `openapi`: string REQUIRED 버전
- `info`: Info REQUIRED 메타데이터
    - `title`: string REQUIRED
    - `description`: string 마크다운 사용 가능
    - `termOfService`: string
    - `contact`: Contact
        - `name`: string
        - `url`: string
        - `email`: string
    - `license`: License
        - `name`: string REQUIRED
        - `url`: string
    - `version`: string REQUIRED
- `servers`: Server[] DEFAULT(/) API 서버 정보
    - `url`: string REQUIRED
    - `description`: string
    - `variables`: Map<string, ServerVariable>[]
        - `enum`: string[]
        - `default`: string REQUIRED
        - `description`: string
- `components`: Component[] 스키마 정의 `/^[a-zA-Z0-9\.\-_]+$/`
    - `schemas`: Map<string, Schema|Reference>[]
    - `responses`: Map<string, Response|Reference>[]
        - `description`: string
        - `headers`: Map<string, Header|Reference>[]
        - `content`: Map<string, MediaType>[]
        - `links`: Map<string, Link|Reference>[]
    - `parameters`: Map<string, Parameter|Reference>[]
        - `name`: string REQUIRED
        - `in`: string(query|header|path|cookie) REQUIRED
        - `description`: string
        - `required`: boolean DEFAULT(false)
        - `schema`: Schema|Reference
        - `deprecated`: boolean DEFAULT(false)
        - `allowEmptyValue`: boolean DEFAULT(false) __Only valid when `in: "query"`__
        - `content`: Map<string, MediaType>
        - `example`: any
        - `examples`: Map<string, Example|Reference>
        - `style`: string (matrix|label|form|simple|spaceDelimited|pipeDelimited|pipeDelimited|deepObject)
    - `examples`: Map<string, Example|Reference>[]
    - `requestBodies`: Map<string, RequestBody|Reference>[]
        - `description`: string
        - `content`: Map<string, MediaType> REQUIRED
            - `schema`: Schema|Reference
            - `example`: any
            - `examples`: Map<string, Example|Reference>
            - `encoding`: Map<string, Encoding>
                - `contentType`: string
                - `headers`: Map<string, Header|Reference>
                - `style`: string
                - `explode`: boolean
                - `allowReserved`: boolean
        - `required`: boolean DEFAULT(false)
    - `headers`: Map<string, Header|Reference>[]
    - `securitySchemes`: Map<string, SecurityScheme|Reference>[]
    - `links`: Map<string, Link|Reference>[]
    - `callbacks`: Map<string, Callback|Reference>[]
- `paths`: Path[] REQUIRED
    - `/{path}`: Path
        - `$ref`: string
        - `summary`: string
        - `description`: string
        - `get`: Operation
            - `tags`: string[]
            - `summary`: string
            - `description`: string
            - `externalDocs`: ExternalDocumentation
            - `operationId`: string UNIQUE
            - `parameters`: Parameter[]|Reference[]
            - `requestBody`: RequestBody|Reference
            - `responses`: Response REQUIRED
            - `callbacks`: Map<string, Callback|Reference>[]
            - `deprecated`: boolean DEFAULT(false)
            - `security`: SecurityRequirement[] Override Root-level definition
            - `servers`: Server[] Override Root or Path level definition
        - `put`: Operation
        - `post`: Operation
        - `delete`: Operation
        - `options`: Operation
        - `head`: Operation
        - `patch`: Operation
        - `trace`: Operation
        - `servers`: Server[]
        - `parameters`: Parameter[]|Reference[]
- `security`: Security[]
    - `type`: string(apiKey|http|oauth2|openIdConnect) REQUIRED
    - `description`: string
    - `name`: string REQUIRED when type: "apiKey"
    - `i`n: string REQUIRED when type: "apiKey"
    - `scheme`: string REQUIRED when type: "http"
    - `bearerForamt`: string applies when type: "http"
    - `flows`: OAuthFlow REQUIRED when type: "oauth2"
        - authorizationUrl: string REQUIRED when oauth2 ("implicit", "authorizationCode")
        - tokenUrl: string REQUIRED when oauth2 ("password", "clientCredentials", "authorizationCode")
        - refreshUrl: string
        - scopes: Map<string, string> REQUIRED
- `tags`: Tag[]
- `externalDocs`: ExternalDocumentation[]
    - `description`: string
    - `url`: string REQUIRED

#### Reference
```yaml
# Reference Object Example
$ref: '#/components/schemas/Pet'

# Relative Schema Document Example
$ref: Pet.yaml

# Relative Documents With Embedded Schema Example
$ref: definitions.yaml#/Pet
```

#### Polymorphysm
```yaml
components:
  schemas:
    Pet:
      type: object
      discriminator:
        propertyName: petType
      properties:
        name:
          type: string
        petType:
          type: string
      required:
      - name
      - petType
    Cat:  ## "Cat" will be used as the discriminator value
      description: A representation of a cat
      allOf:
      - $ref: '#/components/schemas/Pet'
      - type: object
        properties:
          huntingSkill:
            type: string
            description: The measured skill for hunting
            enum:
            - clueless
            - lazy
            - adventurous
            - aggressive
        required:
        - huntingSkill
    Dog:  ## "Dog" will be used as the discriminator value
      description: A representation of a dog
      allOf:
      - $ref: '#/components/schemas/Pet'
      - type: object
        properties:
          packSize:
            type: integer
            format: int32
            description: the size of the pack the dog is from
            default: 0
            minimum: 0
        required:
        - packSize
```

#### Example
- Official https://github.com/OAI/OpenAPI-Specification/tree/master/examples/v3.0
- Jhipster Example https://gist.github.com/appkr/96481a171b899680d19a8cf1ab3f862c#file-api-yml
