package dev.appkr.webflux

interface CustomerService {
    fun getCustomer(id: Int): Customer?
    fun searchCustomers(nameFilter: String): List<Customer>
}