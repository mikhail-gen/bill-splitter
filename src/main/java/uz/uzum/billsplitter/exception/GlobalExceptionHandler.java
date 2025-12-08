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
}