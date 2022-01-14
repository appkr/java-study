package dev.appkr.mvc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CustomerController {

    @Autowired
    private lateinit var service: CustomerService

    @PostMapping(path = ["/customer"])
    fun createCustomer(@RequestBody customer: Customer): ResponseEntity<Any> {
        return ResponseEntity(service.createCustomer(customer), HttpStatus.CREATED)
    }

    @GetMapping(path = ["/customers"])
    fun getCustomers(): ResponseEntity<List<Customer>> {
        return ResponseEntity(service.searchCustomers(null), HttpStatus.OK)
    }

    @GetMapping(path = ["/customer/{id}"])
    fun getCustomer(@PathVariable id: Int): ResponseEntity<Any> {
//        val customer = service.getCustomer(id)
//            ?: throw CustomerNotFoundException("Customer '$id' not found")
//        return ResponseEntity(customer, HttpStatus.OK)

        val customer = service.getCustomer(id)
        return if (customer != null) {
            ResponseEntity(customer, HttpStatus.OK)
        } else {
            ResponseEntity(ErrorResponse("Customer not found", "Customer '$id' not found"), HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping(path = ["/customer/{id}"])
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer): ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        if (service.getCustomer(id) != null) {
            service.updateCustomer(id, customer);
            status = HttpStatus.NO_CONTENT
        }
        return ResponseEntity(status)
    }

    @DeleteMapping(path = ["/customer/{id}"])
    fun deleteCustomer(@PathVariable id: Int): ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        if (service.getCustomer(id) != null) {
            service.deleteCustomer(id)
            status = HttpStatus.NO_CONTENT
        }
        return ResponseEntity(status)
    }
}