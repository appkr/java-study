package dev.appkr.webflux

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.router

@Component
class CustomerRouter(private val handler: CustomerHandler) {

    @Bean
    fun customerRoutes(): RouterFunction<*> {
        return router {
            "/functional".nest {
                "/customer".nest {
                    GET("/{id}", handler::get)
                    POST("/", handler::create)
                }
                "/customers".nest {
                    GET("/", handler::search)
                }
            }
        }
    }
}