package dev.appkr.webflux

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.onErrorResume
import java.net.URI

@Component
class CustomerHandler(val service: CustomerService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return service.createCustomer(req.bodyToMono())
            .flatMap {
                created(URI.create("/functional/customer/${it.id}")).build()
            }
            .onErrorResume(Exception::class) {
                badRequest().body(fromObject(
                    ErrorResponse("error creating customer", it.message ?: "error")
                ))
            }
    }

    fun get(req: ServerRequest): Mono<ServerResponse> {
        return service.getCustomer(req.pathVariable("id").toInt())
            .flatMap { ok().body(fromObject(it)) }
            .switchIfEmpty(notFound().build())
    }

    fun search(req: ServerRequest): Mono<ServerResponse> {
        val customers = service.searchCustomers(req.queryParam("nameFilter").orElse(""))
        return ok().body(customers, Customer::class.java)
    }
}