package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public abstract class UnaryOperation implements GenericExpression {
    private final GenericExpression expression;

    public UnaryOperation(GenericExpression expression) {
        this.expression = expression;
    }

    protected abstract String getOperator();
    protected abstract <T extends Number> T calculate(AbstractCalculator<T> calculator, T a);

    @Override
    public <T extends Number> T evaluate(AbstractCalculator<T> calculator, T x, T y, T z) {
        return calculate(calculator, expression.evaluate(calculator, x, y, z));
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getOperator(), expression.toString());
    }

}
