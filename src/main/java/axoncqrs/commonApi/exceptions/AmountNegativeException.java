package axoncqrs.commonApi.exceptions;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String s) {
        super(s);
    }
}
