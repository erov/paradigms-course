package expression.exceptions;

public class MissingOparationException extends ParseException {
    public MissingOparationException(int position) {
        super("expected operation", position);
    }
}
