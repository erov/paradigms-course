package expression.exceptions;

public class InvalidConstException extends CalculateException {
    public InvalidConstException(String value, String type) {
        super(String.format("invalid %s constant \"%s\"", type, value));
    }
}
