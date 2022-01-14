package dev.appkr.mvc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {

    @Autowired
    lateinit var customers: ConcurrentHashMap<Int, Customer>

    override fun createCustomer(customer: Customer) {
        customers[customer.id] = customer
    }

    override fun getCustomer(id: Int): Customer? {
        return customers[id]
    }

    override fun searchCustomers(nameFilter: String?): List<Customer> {
        if (nameFilter == null) {
            return customers.map(Map.Entry<Int, Customer>::value).toList()
        }

        return customers.filter {
            it.value.name.contains(nameFilter, ignoreCase = true)
        }.map(Map.Entry<Int, Customer>::value).toList()
    }

    override fun updateCustomer(id: Int, customer: Customer) {
        customers.remove(id)
        customers[id] = customer
    }

    override fun deleteCustomer(id: Int) {
        customers.remove(id)
    }
}