package dev.appkr.mvc

import java.lang.RuntimeException

open class DomainException(message: String): RuntimeException(message) {
}