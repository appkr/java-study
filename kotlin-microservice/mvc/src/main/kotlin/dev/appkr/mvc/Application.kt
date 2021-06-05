package dev.appkr.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
class Application {

    companion object {
        val initialCustomers = arrayOf(
            Customer(1, "Kotlin"),
            Customer(2, "Spring"),
            Customer(3, "Microservice", Customer.Telephone("82", "1012345678"))
        )
    }

    @Bean
    fun customers() = ConcurrentHashMap(initialCustomers.associateBy(Customer::id))
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}



