package springstudy.sbt;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BookNotFoundException.class })
    public ResponseEntity<ErrorDto> handleNotFound(Exception e, WebRequest req) {
        ErrorDto errorDto = ErrorDto.of(HttpStatus.NOT_FOUND.value(), "Book not found");
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorDto);

//        return handleExceptionInternal(e, "Book not found",
//            new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler({ BookIdMismatchException.class,
        ConstraintViolationException.class, DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(Exception e, WebRequest req) {
        ErrorDto errorDto = ErrorDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getCause().toString());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorDto);

//        return handleExceptionInternal(e, e.getLocalizedMessage(),
//            new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }
}
