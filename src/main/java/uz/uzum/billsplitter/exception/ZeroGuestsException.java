package uz.uzum.billsplitter.exception;

public class ZeroGuestsException extends RuntimeException {
    public ZeroGuestsException() {
        super("At least one guest must be provided.");
    }
}