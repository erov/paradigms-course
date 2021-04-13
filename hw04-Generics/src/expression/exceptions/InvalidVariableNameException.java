package expression.exceptions;

public class InvalidVariableNameException extends ParseException {
    public InvalidVariableNameException(String name, int position) {
        super(String.format("found invalid variable name \"%s\"", name), position);
    }
}
