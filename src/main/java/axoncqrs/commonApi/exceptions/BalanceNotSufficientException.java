package axoncqrs.commonApi.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String m) {
        super(m);
    }
}
