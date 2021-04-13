package expression.exceptions;

public class ItemNotFoundException extends ParseException {
    public ItemNotFoundException(String expected, String found, int position) {
        super(String.format("Expected %s, but found %s", expected, found), position);
    }
}
