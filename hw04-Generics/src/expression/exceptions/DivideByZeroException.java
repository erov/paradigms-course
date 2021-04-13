package expression.exceptions;

public class DivideByZeroException extends CalculateException {
    public DivideByZeroException(Number value) {
        super(String.format("division by zero: %s / 0", value.toString()));
    }
}
