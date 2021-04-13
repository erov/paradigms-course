package expression.parser;

public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public boolean hasNext(int delta) {
        return pos + delta - 1 < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public char next(int delta) {
        return data.charAt(pos + delta - 1);
    }

    @Override
    public int getPosition() {
        return pos;
    }
}
