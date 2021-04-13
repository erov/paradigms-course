package expression.exceptions;

public class InvalidArgumentException extends CalculateException {
    public InvalidArgumentException(String message, Number argument) {
        super(String.format("%s: %s", message, argument.toString()));
    }
}
