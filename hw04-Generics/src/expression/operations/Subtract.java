package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public class Subtract extends BinaryOperation {
    public Subtract(GenericExpression left, GenericExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    protected <T extends Number> T calculate(AbstractCalculator<T> calculator, T a, T b) {
        return calculator.subtract(a, b);
    }
}
