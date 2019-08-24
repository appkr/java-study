# Example\PetApi\PetApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPet**](PetApi.md#createPet) | **POST** /api/pets | createPet
[**getPet**](PetApi.md#getPet) | **GET** /api/pets/{petId} | getPet
[**listPets**](PetApi.md#listPets) | **GET** /api/pets | listPets



## createPet

> createPet($pet)

createPet

### Example

```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');


$apiInstance = new Example\PetApi\Api\PetApi(
    // If you want use custom http client, pass your client which implements `GuzzleHttp\ClientInterface`.
    // This is optional, `GuzzleHttp\Client` will be used as default.
    new GuzzleHttp\Client()
);
$pet = new \Example\PetApi\Model\Pet(); // \Example\PetApi\Model\Pet | 

try {
    $apiInstance->createPet($pet);
} catch (Exception $e) {
    echo 'Exception when calling PetApi->createPet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pet** | [**\Example\PetApi\Model\Pet**](../Model/Pet.md)|  | [optional]

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints)
[[Back to Model list]](../../README.md#documentation-for-models)
[[Back to README]](../../README.md)


## getPet

> \Example\PetApi\Model\Pet getPet($petId)

getPet

### Example

```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');


$apiInstance = new Example\PetApi\Api\PetApi(
    // If you want use custom http client, pass your client which implements `GuzzleHttp\ClientInterface`.
    // This is optional, `GuzzleHttp\Client` will be used as default.
    new GuzzleHttp\Client()
);
$petId = 56; // int | 

try {
    $result = $apiInstance->getPet($petId);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling PetApi->getPet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **petId** | **int**|  |

### Return type

[**\Example\PetApi\Model\Pet**](../Model/Pet.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints)
[[Back to Model list]](../../README.md#documentation-for-models)
[[Back to README]](../../README.md)


## listPets

> \Example\PetApi\Model\ListPetResponse listPets($page, $size, $petType, $name)

listPets

### Example

```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');


$apiInstance = new Example\PetApi\Api\PetApi(
    // If you want use custom http client, pass your client which implements `GuzzleHttp\ClientInterface`.
    // This is optional, `GuzzleHttp\Client` will be used as default.
    new GuzzleHttp\Client()
);
$page = 1; // int | 
$size = 20; // int | 
$petType = CAT; // string | 
$name = Jerry; // string | 

try {
    $result = $apiInstance->listPets($page, $size, $petType, $name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling PetApi->listPets: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **int**|  | [optional] [default to 1]
 **size** | **int**|  | [optional] [default to 20]
 **petType** | **string**|  | [optional]
 **name** | **string**|  | [optional]

### Return type

[**\Example\PetApi\Model\ListPetResponse**](../Model/ListPetResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints)
[[Back to Model list]](../../README.md#documentation-for-models)
[[Back to README]](../../README.md)

