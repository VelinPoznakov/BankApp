package Exceptions;

public class ImpossibleLoanAmountException extends RuntimeException {
    public ImpossibleLoanAmountException(String message) {
        super(message);
    }
}
