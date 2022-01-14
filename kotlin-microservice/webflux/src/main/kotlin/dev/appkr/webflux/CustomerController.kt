package dev.appkr.webflux

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CustomerController {

    @Autowired
    private lateinit var service: CustomerService

    @PostMapping(path = ["/customer"])
    fun createCustomer(@RequestBody customerMono: Mono<Customer>): ResponseEntity<Mono<*>> {
        val customer = service.createCustomer(customerMono)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping(path = ["/customer/{id}"])
    fun getCustomer(@PathVariable id: Int): ResponseEntity<Mono<Customer>> {
        val customer = service.getCustomer(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping(path = ["customers"])
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String): ResponseEntity<Flux<Customer>> {
        val customers = service.searchCustomers(nameFilter)
        return ResponseEntity(customers, HttpStatus.OK)
    }
}