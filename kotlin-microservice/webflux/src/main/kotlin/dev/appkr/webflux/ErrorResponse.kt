package dev.appkr.webflux

data class ErrorResponse(
    val error: String,
    val message: String
)