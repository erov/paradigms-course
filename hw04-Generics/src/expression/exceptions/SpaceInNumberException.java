package expression.exceptions;

public class SpaceInNumberException extends ParseException {
    public SpaceInNumberException(int position) {
        super("found spaces in number", position);
    }
}
