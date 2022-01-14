package dev.appkr.webflux

class CustomerExistException(override val message: String): Exception(message) {
}