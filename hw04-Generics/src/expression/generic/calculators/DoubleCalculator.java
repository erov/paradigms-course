package expression.generic.calculators;

import expression.exceptions.InvalidConstException;

public class DoubleCalculator extends AbstractCalculator<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    protected Double divideImpl(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

    @Override
    public Double mod(Double a, Double b) {
        return a % b;
    }

    @Override
    public Double valueOf(int value) {
        return (double) value;
    }

    @Override
    public Double valueOf(String value) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidConstException(value, "double");
        }
    }

    @Override
    public int compare(Double a, Double b) {
        return a.compareTo(b);
    }
}
