package expression.exceptions;

public class ParseException extends Exception {
    public ParseException(final String message, final int position) {
        super(String.format("%s at position %d", message, position));
    }
}
