package uz.uzum.billsplitter.exception;

public class InvalidDishCostException extends RuntimeException {
    public InvalidDishCostException(String dishName, Double cost) {
        super("Cost of dish '" + dishName + "' cannot be negative. Provided: " + cost);
    }
}