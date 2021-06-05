package dev.appkr.mvc

interface CustomerService {
    fun createCustomer(customer: Customer)
    fun getCustomer(id: Int): Customer?
    fun searchCustomers(nameFilter: String?): List<Customer>
    fun updateCustomer(id: Int, customer: Customer)
    fun deleteCustomer(id: Int)
}