package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public abstract class BinaryOperation implements GenericExpression {
    private final GenericExpression left;
    private final GenericExpression right;

    public BinaryOperation(GenericExpression left, GenericExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract String getOperator();
    protected abstract <T extends Number> T calculate(AbstractCalculator<T> calculator, T a, T b);

    @Override
    public <T extends Number> T evaluate(AbstractCalculator<T> calculator, T x, T y, T z) {
        return calculate(calculator, left.evaluate(calculator, x, y, z), right.evaluate(calculator, x, y, z));
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left.toString(), getOperator(), right.toString());
    }
}
