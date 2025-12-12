/*
package uz.uzum.billsplitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.uzum.billsplitter.dto.error.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> build(HttpStatus status, Exception ex) {
        return new ResponseEntity<>(
            new ErrorResponse(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                LocalDateTime.now()
            ),
            status
        );
    }

    @ExceptionHandler(ZeroGuestsException.class)
    public ResponseEntity<ErrorResponse> handleZeroGuests(ZeroGuestsException ex) {
        return build(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(InvalidDishCostException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDishCost(InvalidDishCostException ex) {
        return build(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(InvalidCommissionRateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommission(InvalidCommissionRateException ex) {
        return build(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(NullOrEmptyNameException.class)
    public ResponseEntity<ErrorResponse> handleEmptyName(NullOrEmptyNameException ex) {
        return build(HttpStatus.BAD_REQUEST, ex);
    }

    // ---- Fallback handler for unexpected errors ----
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}*/


package uz.uzum.billsplitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.uzum.billsplitter.dto.error.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String type, String message) {
        return new ResponseEntity<>(
            new ErrorResponse(
                type,
                message,
                LocalDateTime.now()
            ),
            status
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        return build(
            HttpStatus.BAD_REQUEST,
            "ValidationError",
            fieldErrors.toString()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {

        Map<String, String> violations = new HashMap<>();
        ex.getConstraintViolations().forEach(v -> {
            violations.put(v.getPropertyPath().toString(), v.getMessage());
        });

        return build(
            HttpStatus.BAD_REQUEST,
            "ConstraintViolation",
            violations.toString()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        return build(
            HttpStatus.BAD_REQUEST,
            "InvalidJson",
            "Malformed JSON or incorrect field type"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "InternalError", ex.getMessage());
    }
}