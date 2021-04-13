package expression.exceptions;

public class MissingBracketsException extends ParseException {
    public MissingBracketsException(String message, int position) {
        super(message, position);
    }
}
