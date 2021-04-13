package expression.exceptions;

public class IllegalOperationException extends ParseException {
    public IllegalOperationException(String message, int position) {
        super(message, position);
    }
}
