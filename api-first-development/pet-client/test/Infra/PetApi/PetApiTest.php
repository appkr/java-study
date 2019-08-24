<?php

namespace App\Infra\PetApi;

use Example\PetApi\Model\Pet;
use Example\PetApi\Model\PetType;
use Example\PetApi\Service\PetApi;
use GuzzleHttp\Client;
use PHPUnit\Framework\TestCase;

class PetApiTest extends TestCase
{
    private $api;
    private $randomName;

    protected function setUp(): void {
        $httpClient = new Client(["debug" => true]);
        $this->api = new PetApi($httpClient);
        $this->randomName = $this->randomString(10);
    }

    public function testCreatePet()
    {
        $pet = new Pet([
            "name" => $this->randomName,
            "petType" => PetType::CAT,
        ]);

        $this->api->createPet($pet);

        $this->assertTrue(true);
    }

    public function testListPets()
    {
        /** @var \Example\PetApi\Model\ListPetResponse $response */
        $response = $this->api->listPets(1, 20, PetType::CAT, "Foo");
        foreach($response->getData() as $pet) {
            echo $pet, PHP_EOL;
        }

        $this->assertTrue(true);
    }

    public function testGetPet()
    {
        $pet = $this->api->getPet(1);
        echo $pet, PHP_EOL;

        $this->assertTrue(true);
    }

    private function randomString($length = 10)
    {
        return substr(
            str_shuffle(
                str_repeat(
                    $x='0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ',
                    ceil($length/strlen($x))
                )
            ),
            1,
            $length
        );
    }
}
