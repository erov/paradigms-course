package expression.exceptions;

public class OverflowException extends CalculateException {
    public OverflowException(String overflowType, int a, int b, String operator) {
        super(String.format("%s: %d %s %d", overflowType, a, operator, b));
    }

    public OverflowException(String overflowType, int a, String operator) {
        super(String.format("%s: %s %d", overflowType, operator, a));
    }
}
