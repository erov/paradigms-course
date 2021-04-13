package expression;

import expression.generic.calculators.AbstractCalculator;

public class Const implements GenericExpression {
    private final String value;

    public Const(String value) {
        this.value = value;
    }

    @Override
    public <T extends Number> T evaluate(AbstractCalculator<T> calculator, T x, T y, T z) {
        return calculator.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
