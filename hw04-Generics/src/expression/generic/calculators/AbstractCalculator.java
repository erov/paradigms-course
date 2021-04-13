package expression.generic.calculators;

import expression.exceptions.DivideByZeroException;

public abstract class AbstractCalculator<T extends Number> {
    public abstract T add(T a, T b);
    public abstract T subtract(T a, T b);
    public abstract T multiply(T a, T b);
    protected abstract T divideImpl(T a, T b);
    public abstract T negate(T a);
    public abstract T valueOf(int value);
    public abstract T valueOf(String value);
    protected abstract int compare(T a, T b);

    public T divide(T a, T b) {
        if (this instanceof IntegerTypeCalculator) {
            if (compare(b, valueOf(0)) == 0) {
                throw new DivideByZeroException(a);
            }
        }
        return divideImpl(a, b);
    }

    public T abs(T a) {
        return compare(a, valueOf(0)) < 0 ? negate(a) : a;
    }

    public T square(T a) {
        return multiply(a, a);
    }

    public T mod(T a, T b) {
        return subtract(a, multiply(divide(a, b), b));
    }
}
