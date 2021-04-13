package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public class Abs extends UnaryOperation {
    public Abs(GenericExpression expression) {
        super(expression);
    }

    @Override
    protected String getOperator() {
        return "abs";
    }

    @Override
    protected <T extends Number> T calculate(AbstractCalculator<T> calculator, T a) {
        return calculator.abs(a);
    }
}
