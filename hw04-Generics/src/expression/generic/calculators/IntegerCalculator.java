package expression.generic.calculators;

import expression.exceptions.InvalidConstException;

public class IntegerCalculator extends AbstractCalculator<Integer> implements IntegerTypeCalculator {
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    protected Integer divideImpl(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer negate(Integer a) {
        return -a;
    }

    @Override
    public Integer valueOf(int value) {
        return value;
    }

    @Override
    public Integer valueOf(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidConstException(value, "integer");
        }
    }

    @Override
    public int compare(Integer a, Integer b) {
        return a.compareTo(b);
    }

}
