package expression.generic.calculators;

import expression.exceptions.OverflowException;

public class CheckedIntegerCalculator extends IntegerCalculator {
    @Override
    public Integer add(Integer a, Integer b) {
        if (b > 0 && a > Integer.MAX_VALUE - b || b < 0 && a < Integer.MIN_VALUE - b) {
            throw new OverflowException("summation overflow", a, b, "+");
        }
        return super.add(a, b);
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        if (b < 0 && a > Integer.MAX_VALUE + b || b > 0 && a < Integer.MIN_VALUE + b) {
            throw new OverflowException("subtraction overflow", a, b, "-");
        }
        return super.subtract(a, b);
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (b > 0 && (a > Integer.MAX_VALUE / b || a < Integer.MIN_VALUE / b) ||
            b < 0 && (a < Integer.MIN_VALUE / -b || a < Integer.MAX_VALUE / b)) {
            throw new OverflowException("multiplication overflow", a, b, "*");
        }
        return super.multiply(a, b);
    }

    @Override
    protected Integer divideImpl(Integer a, Integer b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("division overflow", a, b, "/");
        }
        return super.divideImpl(a, b);
    }

    @Override
    public Integer negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("negation overflow", a, "-");
        }
        return super.negate(a);
    }

    @Override
    public Integer abs(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("abs overflow", a, "abs");
        }
        return super.abs(a);
    }

    @Override
    public Integer square(Integer a) {
        if (a > 0 && a > Integer.MAX_VALUE / a ||
            a < 0 && (a < Integer.MIN_VALUE / -a || a < Integer.MAX_VALUE / a)) {
            throw new OverflowException("square overflow", a, "square");
        }
        return super.square(a);
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("division overflow", a, b, "/");
        }
        return super.mod(a, b);
    }
}
