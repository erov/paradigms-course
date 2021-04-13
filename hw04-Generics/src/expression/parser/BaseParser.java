package expression.parser;

import expression.exceptions.*;

public class BaseParser {
    public static final char END = '\0';
    protected CharSource source;
    protected char ch = 0xffff;

    protected void makeSourse(String expression) {
        source = new StringSource(expression);
        nextChar();
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : END;
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean test(String expected) {
        int len = expected.length();
        if (!source.hasNext(len - 1)) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (source.hasNext(i) && source.next(i) == expected.charAt(i) == false) {
                return false;
            }
        }
        return !Character.isLetter(expected.charAt(len - 1)) || !source.hasNext(len) ||
                (!Character.isDigit(source.next(len)) && !Character.isLetter(source.next(len)));
    }

    protected void expect(final char c) throws ParseException {
        if (ch != c) {
            if (c == ')') {
                throw new MissingBracketsException("missing closing bracket", getPosition());
            }
            throw new ItemNotFoundException(String.format("'%c'",c), String.format("'%c'", ch), getPosition());
        }
        nextChar();
    }

    protected void expect(final String value) throws ParseException {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        return test(END);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    protected boolean isDigit() {
        return between('0', '9');
    }

    protected boolean isLetter() {
        return Character.isLetter(ch);
    }


    protected boolean isBracket() {
        return ch == '(' || ch == ')';
    }

    protected int getPosition() {
        return source.getPosition();
    }
}
