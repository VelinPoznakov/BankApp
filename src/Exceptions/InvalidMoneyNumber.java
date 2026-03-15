package Exceptions;

public class InvalidMoneyNumber extends RuntimeException {
    public InvalidMoneyNumber(String message) {
        super(message);
    }
}
