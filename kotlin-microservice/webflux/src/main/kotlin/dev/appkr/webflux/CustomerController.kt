package dev.appkr.webflux

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController {

    @Autowired
    private lateinit var service: CustomerService

    @GetMapping(path = ["/customer/{id}"])
    fun getCustomer(@PathVariable id: Int): ResponseEntity<Customer> {
        val customer = service.getCustomer(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping(path = ["customers"])
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String): ResponseEntity<List<Customer>> {
        val customers = service.searchCustomers(nameFilter)
        return ResponseEntity(customers, HttpStatus.OK)
    }
}