package expression.generic.calculators;

import expression.exceptions.InvalidConstException;

public class ByteCalculator extends AbstractCalculator<Byte> implements IntegerTypeCalculator {
    @Override
    public Byte add(Byte a, Byte b) {
        return (byte) (a + b);
    }

    @Override
    public Byte subtract(Byte a, Byte b) {
        return (byte) (a - b);
    }

    @Override
    public Byte multiply(Byte a, Byte b) {
        return (byte) (a * b);
    }

    @Override
    protected Byte divideImpl(Byte a, Byte b) {
        return (byte) (a / b);
    }

    @Override
    public Byte negate(Byte a) {
        return (byte) -a;
    }

    @Override
    public Byte valueOf(int value) {
        return (byte) value;
    }

    @Override
    public Byte valueOf(String value) {
        try {
            return Byte.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidConstException(value, "byte");
        }
    }

    @Override
    protected int compare(Byte a, Byte b) {
        return a.compareTo(b);
    }
}
