package uz.uzum.billsplitter.exception;

public class NullOrEmptyNameException extends RuntimeException {
    public NullOrEmptyNameException(String entity) {
        super(entity + " name cannot be null or empty.");
    }
}