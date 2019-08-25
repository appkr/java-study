## API first development

#### Write API spec

- [Swagger Editor](https://swagger.io/tools/swagger-editor/)
- [SmartBear's Reference](https://swagger.io/docs/specification/about/)
- [Open Api Spec 3 (a.k.a. Swagger)](https://github.com/OAI/OpenAPI-Specification/tree/master/versions)

Or convert from Postman collection
- [APIMATIC transformer](https://www.apimatic.io/transformer)

OAS spec can be imported back to Postman collection

To make beautiful html documentation:
```bash
npm install redoc -g
npx redoc-cli bundle api.yml
```

#### Install Code Generator
```bash
brew install openapi-generator
openapi-generator version
openapi-generator help
openapi-generator list
openapi-generator help generate
```

#### Generate Server and Client library
```bash
openapi-generator generate -g spring -i api.yml -o build/spring_server
openapi-generator generate -g php -i api.yml -o build/php_client
openapi-generator generate -g javascript -i api.yml -o build/js_client
```

---

#### Spring server settings
```groovy
// build.gradle

buildscript {
    repositories {
        mavenCentral()
        maven { url "http://repo.spring.io/plugins-release" }
    }
    dependencies {
        classpath "org.openapitools:openapi-generator-gradle-plugin:3.3.0"
    }
}

apply from: 'gradle/swagger.gradle'

dependencies {
    // springfox
    compile "io.springfox:springfox-swagger2:2.9.2"
    compile "io.springfox:springfox-bean-validators:2.9.2"
}
```
```groovy
// swagger.gradle

apply plugin: 'org.openapi.generator'

openApiGenerate {
    generatorName = "spring"
    inputSpec = "$rootDir/src/main/resources/swagger/api.yml".toString()
    outputDir = "$buildDir/openapi".toString()
    apiPackage = "com.example.petservice.api"
    modelPackage = "com.example.petservice.api.model"
    modelNameSuffix = "Dto"
    apiFilesConstrainedTo = [""]
    modelFilesConstrainedTo = [""]
    supportingFilesConstrainedTo = ["ApiUtil.java"]
    validateSpec = true
    configOptions = [
            useTags: "true",
            delegatePattern: "true",
            dateLibrary: "java8",
            java8: "true",
            hideGenerationTimestamp: "true"
    ]
}

sourceSets {
    main {
        java {
            srcDir file("${project.buildDir.path}/openapi/src/main/java")
        }
    }
}

compileJava.dependsOn("openApiGenerate")
```

---

#### PHP client example
```bash
mkdir -p pet-client/src pet-client/test pet-client/packages/petapi
cp api.yml pet-client/packages/petapi/
echo "{}" > pet-client/composer.json
echo "{}" > pet-client/packages/petapi/config-php.json
```
```json
// composer.json
{
    "repositories": [
        {
            "type": "path",
            "url": "./packages/petapi/php",
            "options": {"symlink": true}
        }
    ],
    "require": {
        "php": ">=7.0"
    },
    "autoload": {
        "psr-4": {
            "App\\": "src/"
        }
    },
    "autoload-dev": {
        "psr-4": {
            "App\\": "test/"
        }
    }
}
```
```php
// config-php.json
{
    "variableNamingConvention": "camelCase",
    "packagePath": ".",
    "invokerPackage": "Example\\PetApi",
    "modelPackage": "Model",
    "apiPackage": "Service",
    "srcBasePath": "src",
    "gitUserId": "example",
    "gitRepoId": "petapi",
    "composerVendorName": "example",
    "composerProjectName": "petapi"
}
```
```bash
cd pet-client/packages/petapi
openapi-generator generate -g php -i api.yml -o php -c config-php.json
cd ../..
composer require example/petapi -vvv
composer require phpunit/phpunit --dev -vvv
```
