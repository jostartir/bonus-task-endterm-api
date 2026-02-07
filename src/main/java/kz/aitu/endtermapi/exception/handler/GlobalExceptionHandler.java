package kz.aitu.endtermapi.exception.handler;

import kz.aitu.endtermapi.exception.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ApiError> build(HttpStatus status, String message, String path){
        ApiError aper = new ApiError(Instant.now().toString(),status.value(),message,path);
        return ResponseEntity.status(status).body(aper);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException e, HttpServletRequest req){
        return build(HttpStatus.NOT_FOUND, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> badRequest(InvalidInputException e, HttpServletRequest req){
        return build(HttpStatus.BAD_REQUEST ,e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ApiError> sqlError(DatabaseOperationException e, HttpServletRequest req){
        return build(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> duplicater(DuplicateResourceException e, HttpServletRequest req){
        return build(HttpStatus.CONFLICT , e.getMessage(),req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> anyer(Exception e, HttpServletRequest req){
        return  build(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage(), req.getRequestURI());
    }
}
