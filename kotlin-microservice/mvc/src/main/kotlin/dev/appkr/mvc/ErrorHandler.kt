package dev.appkr.mvc

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(JsonParseException::class)
    fun jsonParseExceptionHandler(req: HttpServletRequest, e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse("JSON Error", e.message ?: "Invalid JSON"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DomainException::class)
    fun domainExceptionHandler(req: HttpServletRequest, e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse("Domain exception", e.message ?: ""), HttpStatus.BAD_REQUEST)
    }
}