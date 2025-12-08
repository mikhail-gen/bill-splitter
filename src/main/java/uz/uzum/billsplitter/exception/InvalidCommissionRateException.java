package uz.uzum.billsplitter.exception;

public class InvalidCommissionRateException extends RuntimeException {
    public InvalidCommissionRateException(Double rate) {
        super("Commission rate must be between 0 and 100. Provided: " + rate);
    }
}